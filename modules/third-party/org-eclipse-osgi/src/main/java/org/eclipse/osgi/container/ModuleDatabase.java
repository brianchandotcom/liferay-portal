/*******************************************************************************
 * Copyright (c) 2012, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.osgi.container;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.*;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import org.eclipse.osgi.container.Module.Settings;
import org.eclipse.osgi.container.Module.State;
import org.eclipse.osgi.container.ModuleRevisionBuilder.GenericInfo;
import org.eclipse.osgi.container.namespaces.EquinoxModuleDataNamespace;
import org.eclipse.osgi.framework.util.ObjectPool;
import org.eclipse.osgi.internal.container.Capabilities;
import org.eclipse.osgi.internal.container.ComputeNodeOrder;
import org.eclipse.osgi.internal.framework.EquinoxConfiguration;
import org.osgi.framework.Constants;
import org.osgi.framework.Version;
import org.osgi.framework.namespace.PackageNamespace;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.resource.*;
import org.osgi.service.resolver.Resolver;

/**
 * A database for storing modules, their revisions and wiring states.  The
 * database is responsible for assigning ids and providing access to the
 * capabilities provided by the revisions currently installed as well as
 * the wiring states.
 * <p>
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Implementations must be thread safe.  The database allows for concurrent
 * read operations and all read operations are protected by the
 * {@link #readLock() read} lock.  All write operations are
 * protected by the {@link #writeLock() write} lock.  The read and write
 * locks are reentrant and follow the semantics of the
 * {@link ReentrantReadWriteLock}.  Just like the {@code ReentrantReadWriteLock}
 * the lock on a database can not be upgraded from a read to a write.  Doing so will result in an
 * {@link IllegalMonitorStateException} being thrown.  This is behavior is different from
 * the {@code ReentrantReadWriteLock} which results in a deadlock if an attempt is made
 * to upgrade from a read to a write lock.
 * <p>
 * A database is associated with a {@link ModuleContainer container}.  The container
 * associated with a database provides public API for manipulating the modules 
 * and their wiring states.  For example, installing, updating, uninstalling,
 * resolving and unresolving modules.  Except for the {@link #load(DataInputStream)},
 * all other methods that perform write operations are intended to be used by
 * the associated container.
 * @since 3.10
 */
public class ModuleDatabase {
	/**
	 * The adaptor for this database
	 */
	final ModuleContainerAdaptor adaptor;

	/**
	 * A map of modules by location.
	 */
	private final Map<String, Module> modulesByLocations;

	/**
	 * A map of modules by id.
	 */
	private final Map<Long, Module> modulesById;

	/**
	 * A map of revision wiring objects.
	 */
	final Map<ModuleRevision, ModuleWiring> wirings;

	/**
	 * Holds the next id to be assigned to a module when it is installed
	 */
	final AtomicLong nextId;

	/**
	 * Holds the current timestamp for revisions of this database.
	 */
	final AtomicLong revisionsTimeStamp;

	/**
	 * Holds the current timestamp for all changes to this database.
	 * This includes changes to revisions and changes to module settings.
	 */
	final AtomicLong allTimeStamp;

	/**
	 * Holds the construction time which is used to check for empty database on
	 * load.  This is necessary to ensure the loaded database is consistent with
	 * what was persisted.
	 */
	final long constructionTime;

	private final Capabilities capabilities;

	/**
	 * A map of module settings keyed by module id.
	 */
	final Map<Long, EnumSet<Settings>> moduleSettings;

	/**
	 * The initial module start level.
	 */
	private int initialModuleStartLevel = 1;

	/**
	 * Monitors read and write access to this database
	 */
	private final ReentrantReadWriteLock monitor = new ReentrantReadWriteLock(true);

	static enum Sort {
		BY_DEPENDENCY, BY_START_LEVEL, BY_ID;
		/**
		 * Tests if this option is contained in the specified options
		 */
		public boolean isContained(Sort... options) {
			for (Sort option : options) {
				if (equals(option)) {
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * Constructs a new empty database.
	 * @param adaptor the module container adaptor
	 */
	public ModuleDatabase(ModuleContainerAdaptor adaptor) {
		this.adaptor = adaptor;
		this.modulesByLocations = new HashMap<String, Module>();
		this.modulesById = new HashMap<Long, Module>();
		this.wirings = new HashMap<ModuleRevision, ModuleWiring>();
		// Start at id 1 because 0 is reserved for the system bundle
		this.nextId = new AtomicLong(1);
		// seed with current time to avoid duplicate timestamps after using -clean
		this.constructionTime = System.currentTimeMillis();
		this.revisionsTimeStamp = new AtomicLong(constructionTime);
		this.allTimeStamp = new AtomicLong(constructionTime);
		this.moduleSettings = new HashMap<Long, EnumSet<Settings>>();
		this.capabilities = new Capabilities();
	}

	/**
	 * Returns the module at the given location or null if no module exists
	 * at the given location.
	 * <p>
	 * A read operation protected by the {@link #readLock() read} lock.
	 * @param location the location of the module.
	 * @return the module at the given location or null.
	 */
	final Module getModule(String location) {
		readLock();
		try {
			return modulesByLocations.get(location);
		} finally {
			readUnlock();
		}
	}

	/**
	 * Returns the module at the given id or null if no module exists
	 * at the given location.
	 * <p>
	 * A read operation protected by the {@link #readLock() read} lock.
	 * @param id the id of the module.
	 * @return the module at the given id or null.
	 */
	final Module getModule(long id) {
		readLock();
		try {
			return modulesById.get(id);
		} finally {
			readUnlock();
		}
	}

	/**
	 * Installs a new revision using the specified builder, location and module.
	 * <p>
	 * A write operation protected by the {@link #writeLock() write} lock.
	 * @param location the location to use for the installation
	 * @param builder the builder to use to create the new revision
	 * @param revisionInfo the revision info for the new revision, may be {@code null}.
	 * @return the installed module
	 */
	final Module install(String location, ModuleRevisionBuilder builder, Object revisionInfo) {
		writeLock();
		try {
			int startlevel = Constants.SYSTEM_BUNDLE_LOCATION.equals(location) ? 0 : getInitialModuleStartLevel();
			long id = Constants.SYSTEM_BUNDLE_LOCATION.equals(location) ? 0 : getNextIdAndIncrement();
			EnumSet<Settings> settings = getActivationPolicySettings(builder);
			Module module = load(location, builder, revisionInfo, id, settings, startlevel);
			long currentTime = System.currentTimeMillis();
			module.setlastModified(currentTime);
			setSystemLastModified(currentTime);
			incrementTimestamps(true);
			return module;
		} finally {
			writeUnlock();
		}
	}

	private EnumSet<Settings> getActivationPolicySettings(ModuleRevisionBuilder builder) {
		// do not do this for fragment bundles
		if ((builder.getTypes() & BundleRevision.TYPE_FRAGMENT) != 0) {
			return null;
		}
		for (GenericInfo info : builder.getCapabilities()) {
			if (EquinoxModuleDataNamespace.MODULE_DATA_NAMESPACE.equals(info.getNamespace())) {
				if (EquinoxModuleDataNamespace.CAPABILITY_ACTIVATION_POLICY_LAZY.equals(info.getAttributes().get(EquinoxModuleDataNamespace.CAPABILITY_ACTIVATION_POLICY))) {
					String compatibilityStartLazy = adaptor.getProperty(EquinoxConfiguration.PROP_COMPATIBILITY_START_LAZY);
					if (compatibilityStartLazy == null || Boolean.valueOf(compatibilityStartLazy)) {
						// TODO hack until p2 is fixed (bug 177641)
						EnumSet<Settings> settings = EnumSet.noneOf(Settings.class);
						settings.add(Settings.USE_ACTIVATION_POLICY);
						settings.add(Settings.AUTO_START);
						return settings;
					}
				}
				return null;
			}
		}
		return null;
	}

	final Module load(String location, ModuleRevisionBuilder builder, Object revisionInfo, long id, EnumSet<Settings> settings, int startlevel) {
		// sanity check
		checkWrite();
		if (modulesByLocations.containsKey(location))
			throw new IllegalArgumentException("Location is already used: " + location); //$NON-NLS-1$
		if (modulesById.containsKey(id))
			throw new IllegalArgumentException("Id is already used: " + id); //$NON-NLS-1$
		Module module;
		if (id == 0) {
			module = adaptor.createSystemModule();
		} else {
			module = adaptor.createModule(location, id, settings, startlevel);
		}
		builder.addRevision(module, revisionInfo);
		modulesByLocations.put(location, module);
		modulesById.put(id, module);
		if (settings != null)
			moduleSettings.put(id, settings);
		ModuleRevision newRevision = module.getCurrentRevision();
		addCapabilities(newRevision);
		return module;
	}

	/**
	 * Uninstalls the specified module from this database.
	 * Uninstalling a module will attempt to clean up any removal pending
	 * revisions possible.
	 * <p>
	 * A write operation protected by the {@link #writeLock() write} lock.
	 * @param module the module to uninstall
	 */
	final void uninstall(Module module) {
		writeLock();
		try {
			ModuleRevisions uninstalling = module.getRevisions();
			// mark the revisions as uninstalled before removing the revisions
			uninstalling.uninstall();
			// remove the location
			modulesByLocations.remove(module.getLocation());
			modulesById.remove(module.getId());
			moduleSettings.remove(module.getId());
			// remove the revisions by name
			List<ModuleRevision> revisions = uninstalling.getModuleRevisions();
			for (ModuleRevision revision : revisions) {
				// if the revision does not have a wiring it can safely be removed
				// from the revisions for the module
				ModuleWiring oldWiring = wirings.get(revision);
				if (oldWiring == null) {
					module.getRevisions().removeRevision(revision);
					removeCapabilities(revision);
				}
			}

			// attempt to cleanup any removal pendings
			cleanupRemovalPending();
			long currentTime = System.currentTimeMillis();
			module.setlastModified(currentTime);
			setSystemLastModified(currentTime);
			incrementTimestamps(true);
		} finally {
			writeUnlock();
		}
	}

	/**
	 * Updates the specified module with anew revision using the specified builder.
	 * <p>
	 * A write operation protected by the {@link #writeLock() write} lock.
	 * @param module the module for which the revision provides an update for
	 * @param builder the builder to use to create the new revision
	 * @param revisionInfo the revision info for the new revision, may be {@code null}.
	 */
	final void update(Module module, ModuleRevisionBuilder builder, Object revisionInfo) {
		writeLock();
		try {
			ModuleRevision oldRevision = module.getCurrentRevision();
			ModuleRevision newRevision = builder.addRevision(module, revisionInfo);
			addCapabilities(newRevision);

			// if the old revision does not have a wiring it can safely be removed
			ModuleWiring oldWiring = wirings.get(oldRevision);
			if (oldWiring == null) {
				module.getRevisions().removeRevision(oldRevision);
				removeCapabilities(oldRevision);
			}
			// attempt to clean up removal pendings
			cleanupRemovalPending();

			long currentTime = System.currentTimeMillis();
			module.setlastModified(currentTime);
			setSystemLastModified(currentTime);
			incrementTimestamps(true);
		} finally {
			writeUnlock();
		}
	}

	/**
	 * Examines the wirings to determine if there are any removal
	 * pending wiring objects that can be removed.  We consider
	 * a removal pending wiring as removable if all dependent
	 * wiring are also removal pending.
	 */
	void cleanupRemovalPending() {
		// sanity check
		checkWrite();
		Collection<ModuleRevision> removalPending = getRemovalPending();
		for (ModuleRevision removed : removalPending) {
			if (wirings.get(removed) == null)
				continue;
			Collection<ModuleRevision> dependencyClosure = ModuleContainer.getDependencyClosure(removed, wirings);
			boolean allPendingRemoval = true;
			for (ModuleRevision pendingRemoval : dependencyClosure) {
				if (pendingRemoval.isCurrent()) {
					allPendingRemoval = false;
					break;
				}
			}
			if (allPendingRemoval) {
				Collection<ModuleWiring> toRemoveWirings = new ArrayList<ModuleWiring>();
				Map<ModuleWiring, Collection<ModuleWire>> toRemoveWireLists = new HashMap<ModuleWiring, Collection<ModuleWire>>();
				for (ModuleRevision pendingRemoval : dependencyClosure) {
					ModuleWiring removedWiring = wirings.get(pendingRemoval);
					if (removedWiring == null) {
						continue;
					}
					toRemoveWirings.add(removedWiring);
					List<ModuleWire> removedWires = removedWiring.getRequiredModuleWires(null);
					for (ModuleWire wire : removedWires) {
						Collection<ModuleWire> providerWires = toRemoveWireLists.get(wire.getProviderWiring());
						if (providerWires == null) {
							providerWires = new ArrayList<ModuleWire>();
							toRemoveWireLists.put(wire.getProviderWiring(), providerWires);
						}
						providerWires.add(wire);
					}
				}
				for (ModuleRevision pendingRemoval : dependencyClosure) {
					pendingRemoval.getRevisions().removeRevision(pendingRemoval);
					removeCapabilities(pendingRemoval);
					wirings.remove(pendingRemoval);
				}
				// remove any wires from unresolved wirings that got removed
				for (Map.Entry<ModuleWiring, Collection<ModuleWire>> entry : toRemoveWireLists.entrySet()) {
					List<ModuleWire> provided = entry.getKey().getProvidedModuleWires(null);
					// No null checks; we are holding the write lock here.
					provided.removeAll(entry.getValue());
					entry.getKey().setProvidedWires(provided);
					for (ModuleWire removedWire : entry.getValue()) {
						// invalidate the wire
						removedWire.invalidate();
					}
				}
				// invalidate any removed wiring objects
				for (ModuleWiring moduleWiring : toRemoveWirings) {
					moduleWiring.invalidate();
				}
			}
		}
	}

	/**
	 * Gets all revisions with a removal pending wiring.
	 * <p>
	 * A read operation protected by the {@link #readLock() read} lock.
	 * @return all revisions with a removal pending wiring.
	 */
	final Collection<ModuleRevision> getRemovalPending() {
		Collection<ModuleRevision> removalPending = new ArrayList<ModuleRevision>();
		readLock();
		try {
			for (ModuleWiring wiring : wirings.values()) {
				if (!wiring.isCurrent())
					removalPending.add(wiring.getRevision());
			}
		} finally {
			readUnlock();
		}
		return removalPending;
	}

	/**
	 * Returns the current wiring for the specified revision or
	 * null of no wiring exists for the revision.
	 * <p>
	 * A read operation protected by the {@link #readLock() read} lock.
	 * @param revision the revision to get the wiring for
	 * @return the current wiring for the specified revision.
	 */
	final ModuleWiring getWiring(ModuleRevision revision) {
		readLock();
		try {
			return wirings.get(revision);
		} finally {
			readUnlock();
		}
	}

	/**
	 * Returns a snapshot of the wirings for all revisions.  This
	 * performs a shallow copy of each entry in the wirings map.
	 * <p>
	 * A read operation protected by the {@link #readLock() read} lock.
	 * @return a snapshot of the wirings for all revisions.
	 */
	final Map<ModuleRevision, ModuleWiring> getWiringsCopy() {
		readLock();
		try {
			return new HashMap<ModuleRevision, ModuleWiring>(wirings);
		} finally {
			readUnlock();
		}
	}

	/**
	 * Returns a cloned snapshot of the wirings of all revisions.  This
	 * performs a clone of each {@link ModuleWiring}.  The 
	 * {@link ModuleWiring#getRevision() revision},
	 * {@link ModuleWiring#getModuleCapabilities(String) capabilities},
	 * {@link ModuleWiring#getModuleRequirements(String) requirements},
	 * {@link ModuleWiring#getProvidedModuleWires(String) provided wires},
	 * {@link ModuleWiring#getRequiredModuleWires(String) required wires}, and
	 * {@link ModuleWiring#getSubstitutedNames()} of 
	 * each wiring are copied into a cloned copy of the wiring.
	 * <p>
	 * The returned map of wirings may be safely read from while not holding
	 * any read or write locks on this database.  This is useful for doing
	 * {@link Resolver#resolve(org.osgi.service.resolver.ResolveContext) resolve}
	 * operations without holding the read or write lock on this database.
	 * <p>
	 * A read operation protected by the {@link #readLock() read} lock.
	 * @return a cloned snapshot of the wirings of all revisions.
	 */
	final Map<ModuleRevision, ModuleWiring> getWiringsClone() {
		readLock();
		try {
			Map<ModuleRevision, ModuleWiring> clonedWirings = new HashMap<ModuleRevision, ModuleWiring>();
			for (Map.Entry<ModuleRevision, ModuleWiring> entry : wirings.entrySet()) {
				ModuleWiring wiring = new ModuleWiring(entry.getKey(), entry.getValue().getModuleCapabilities(null), entry.getValue().getModuleRequirements(null), entry.getValue().getProvidedModuleWires(null), entry.getValue().getRequiredModuleWires(null), entry.getValue().getSubstitutedNames());
				clonedWirings.put(entry.getKey(), wiring);
			}
			return clonedWirings;
		} finally {
			readUnlock();
		}
	}

	/**
	 * Replaces the complete wiring map with the specified wiring
	 * <p>
	 * A write operation protected by the {@link #writeLock() write} lock.
	 * @param newWiring the new wiring to take effect.  The values
	 * from the new wiring are copied.
	 */
	final void setWiring(Map<ModuleRevision, ModuleWiring> newWiring) {
		writeLock();
		try {
			wirings.clear();
			wirings.putAll(newWiring);
			incrementTimestamps(true);
		} finally {
			writeUnlock();
		}
	}

	/**
	 * Adds all the values from the specified delta wirings to the
	 * wirings current wirings
	 * <p>
	 * A write operation protected by the {@link #writeLock() write} lock.
	 * @param deltaWiring the new wiring values to take effect.
	 * The values from the delta wiring are copied.
	 */
	final void mergeWiring(Map<ModuleRevision, ModuleWiring> deltaWiring) {
		writeLock();
		try {
			wirings.putAll(deltaWiring);
			incrementTimestamps(true);
		} finally {
			writeUnlock();
		}
	}

	/**
	 * Returns a snapshot of all modules ordered by module ID.
	 * <p>
	 * A read operation protected by the {@link #readLock() read} lock.
	 * @return a snapshot of all modules.
	 */
	final List<Module> getModules() {
		return getSortedModules();
	}

	/**
	 * Returns a snapshot of all modules ordered according to the sort options
	 * @param sortOptions options for sorting
	 * @return a snapshot of all modules ordered according to the sort options
	 */
	final List<Module> getSortedModules(Sort... sortOptions) {
		readLock();
		try {
			List<Module> modules = new ArrayList<Module>(modulesByLocations.values());
			sortModules(modules, sortOptions);
			return modules;
		} finally {
			readUnlock();
		}
	}

	final void sortModules(List<Module> modules, Sort... sortOptions) {
		if (modules.size() < 2)
			return;
		if (sortOptions == null || Sort.BY_ID.isContained(sortOptions) || sortOptions.length == 0) {
			Collections.sort(modules, new Comparator<Module>() {
				@Override
				public int compare(Module m1, Module m2) {
					return m1.getId().compareTo(m2.getId());
				}
			});
			return;
		}
		// first sort by start-level
		if (Sort.BY_START_LEVEL.isContained(sortOptions)) {
			Collections.sort(modules);
		}
		if (Sort.BY_DEPENDENCY.isContained(sortOptions)) {
			if (Sort.BY_START_LEVEL.isContained(sortOptions)) {
				// sort each sublist that has modules of the same start level
				int currentSL = modules.get(0).getStartLevel();
				int currentSLindex = 0;
				boolean lazy = false;
				for (int i = 0; i < modules.size(); i++) {
					Module module = modules.get(i);
					if (currentSL != module.getStartLevel()) {
						if (lazy)
							sortByDependencies(modules.subList(currentSLindex, i));
						currentSL = module.getStartLevel();
						currentSLindex = i;
						lazy = false;
					}
					lazy |= module.isLazyActivate();
				}
				// sort the last set of bundles
				if (lazy)
					sortByDependencies(modules.subList(currentSLindex, modules.size()));
			} else {
				// sort the whole list by dependency
				sortByDependencies(modules);
			}
		}
	}

	private Collection<List<Module>> sortByDependencies(List<Module> toSort) {
		// Build references so we can sort
		List<Module[]> references = new ArrayList<Module[]>(toSort.size());
		for (Module module : toSort) {
			ModuleRevision current = module.getCurrentRevision();
			if (current == null) {
				continue;
			}
			ModuleWiring wiring = current.getWiring();
			if (wiring == null) {
				continue;
			}
			// No null check; we are holding the database lock here.
			for (ModuleWire wire : wiring.getRequiredModuleWires(null)) {
				ModuleRequirement req = wire.getRequirement();
				// Add all requirements that are not package requirements.
				// Only add package requirements that are not dynamic
				// TODO may want to consider only adding package, bundle and host requirements, other generic requirement are not that interesting
				if (!PackageNamespace.PACKAGE_NAMESPACE.equals(req.getNamespace()) || !PackageNamespace.RESOLUTION_DYNAMIC.equals(req.getDirectives().get(Namespace.REQUIREMENT_RESOLUTION_DIRECTIVE))) {
					references.add(new Module[] {wire.getRequirer().getRevisions().getModule(), wire.getProvider().getRevisions().getModule()});
				}
			}
		}

		// Sort an array using the references
		Module[] sorted = toSort.toArray(new Module[toSort.size()]);
		Object[][] cycles = ComputeNodeOrder.computeNodeOrder(sorted, references.toArray(new Module[references.size()][]));

		// Apply the sorted array to the list
		toSort.clear();
		toSort.addAll(Arrays.asList(sorted));

		if (cycles.length == 0)
			return Collections.emptyList();

		Collection<List<Module>> moduleCycles = new ArrayList<List<Module>>(cycles.length);
		for (Object[] cycle : cycles) {
			List<Module> moduleCycle = new ArrayList<Module>(cycle.length);
			for (Object module : cycle) {
				moduleCycle.add((Module) module);
			}
			moduleCycles.add(moduleCycle);
		}
		return moduleCycles;
	}

	/**
	 * Increments by one the next module ID
	 */
	private long getNextIdAndIncrement() {
		// sanity check
		checkWrite();
		return nextId.getAndIncrement();

	}

	private void checkWrite() {
		if (monitor.getWriteHoldCount() == 0)
			throw new IllegalMonitorStateException("Must hold the write lock."); //$NON-NLS-1$
	}

	/**
	 * returns the next module ID
	 * <p>
	 * A read operation protected by the {@link #readLock() read} lock.
	 * @return the next module ID
	 */
	public final long getNextId() {
		readLock();
		try {
			return nextId.get();
		} finally {
			readUnlock();
		}
	}

	/**
	 * Returns the current timestamp for the revisions of this database.
	 * The timestamp is incremented any time a modification
	 * is made to the revisions in this database.  For example:
	 * <ul>
	 *   <li> installing a module
	 *   <li> updating a module
	 *   <li> uninstalling a module
	 *   <li> modifying the wirings
	 * </ul>
	 * <p>
	 * A read operation protected by the {@link #readLock() read} lock.
	 * @return the current timestamp of this database.
	 */
	final public long getRevisionsTimestamp() {
		readLock();
		try {
			return revisionsTimeStamp.get();
		} finally {
			readUnlock();
		}
	}

	/**
	 * Returns the current timestamp for  this database.
	 * The timestamp is incremented any time a modification
	 * is made to this database.  This includes the modifications
	 * described in {@link #getRevisionsTimestamp() revisions timestamp}
	 * and the following modifications related to modules:
	 * <ul>
	 *   <li> modifying the initial module start level
	 *   <li> modifying a module start level
	 *   <li> modifying a module settings
	 * </ul>
	 * <p>
	 * A read operation protected by the {@link #readLock() read} lock.
	 * @return the current timestamp of this database.
	 */
	final public long getTimestamp() {
		readLock();
		try {
			return allTimeStamp.get();
		} finally {
			readUnlock();
		}
	}

	/**
	 * Increments the timestamps of this database.
	 * @param incrementRevision indicates if the revision timestamp should change
	 */
	private void incrementTimestamps(boolean incrementRevision) {
		// sanity check
		checkWrite();
		if (incrementRevision) {
			revisionsTimeStamp.incrementAndGet();
		}
		allTimeStamp.incrementAndGet();
		adaptor.updatedDatabase();
	}

	private void setSystemLastModified(long currentTime) {
		// sanity check
		checkWrite();
		Module systemModule = getModule(0);
		if (systemModule != null) {
			systemModule.setlastModified(currentTime);
		}
	}

	/**
	 * Acquires the read lock for this database.
	 * @see ReadLock#lock()
	 */
	public final void readLock() {
		monitor.readLock().lock();
	}

	/**
	 * Acquires the write lock for this database.
	 * Same as {@link WriteLock#lock()} except an illegal
	 * state exception is thrown if the current thread holds
	 * one or more read locks.
	 * @see WriteLock#lock()
	 * @throws IllegalMonitorStateException if the current thread holds
	 * one or more read locks.
	 */
	public final void writeLock() {
		if (monitor.getReadHoldCount() > 0) {
			// this is not supported and will cause deadlock if allowed to proceed.
			// fail fast instead of deadlocking
			throw new IllegalMonitorStateException("Requesting upgrade to write lock."); //$NON-NLS-1$
		}
		monitor.writeLock().lock();
	}

	/**
	 * Attempts to release the read lock for this database.
	 * @see ReadLock#unlock()
	 */
	public final void readUnlock() {
		monitor.readLock().unlock();
	}

	/**
	 * Attempts to release the write lock for this database.
	 * @see WriteLock#unlock()
	 */
	public final void writeUnlock() {
		monitor.writeLock().unlock();
	}

	/**
	 * Adds the {@link ModuleRevision#getModuleCapabilities(String) capabilities}
	 * provided by the specified revision to this database.  These capabilities must 
	 * become available for lookup with the {@link ModuleDatabase#findCapabilities(Requirement)}
	 * method.
	 * <p>
	 * This method must be called while holding the {@link #writeLock() write} lock.
	 * @param revision the revision which has capabilities to add
	 */
	final void addCapabilities(ModuleRevision revision) {
		checkWrite();
		Collection<String> packageNames = capabilities.addCapabilities(revision);
		// Clear the dynamic miss caches for all the package names added
		for (ModuleWiring wiring : wirings.values()) {
			wiring.removeDynamicPackageMisses(packageNames);
		}
	}

	/**
	 * Removes the {@link ModuleRevision#getModuleCapabilities(String) capabilities}
	 * provided by the specified revision from this database.  These capabilities
	 * must no longer be available for lookup with the 
	 * {@link ModuleDatabase#findCapabilities(Requirement)} method.
	 * <p>
	 * This method must be called while holding the {@link #writeLock() write} lock.
	 * @param revision
	 */
	protected void removeCapabilities(ModuleRevision revision) {
		checkWrite();
		capabilities.removeCapabilities(revision);
	}

	/**
	 * Returns a mutable snapshot of capabilities that are candidates for 
	 * satisfying the specified requirement.
	 * <p>
	 * A read operation protected by the {@link #readLock() read} lock.
	 * Implementers of this method should acquire the read lock while
	 * finding capabilities.
	 * @param requirement the requirement
	 * @return the candidates for the requirement
	 */
	final List<ModuleCapability> findCapabilities(Requirement requirement) {
		readLock();
		try {
			return capabilities.findCapabilities(requirement);
		} finally {
			readUnlock();
		}
	}

	/**
	 * Writes this database in a format suitable for using the {@link #load(DataInputStream)}
	 * method.  All modules are stored which have a current {@link ModuleRevision revision}.
	 * Only the current revision of each module is stored (no removal pending revisions
	 * are stored).  Optionally the {@link ModuleWiring wiring} of each current revision 
	 * may be stored.  Wiring can only be stored if there are no {@link #getRemovalPending()
	 * removal pending} revisions.
	 * <p>
	 * This method acquires the {@link #readLock() read} lock while writing this
	 * database.
	 * <p>
	 * After this database have been written, the output stream is flushed.  
	 * The output stream remains open after this method returns.
	 * @param out the data output steam.
	 * @param persistWirings true if wirings should be persisted.  This option will be ignored
	 *        if there are {@link #getRemovalPending() removal pending} revisions.
	 * @throws IOException if writing this database to the specified output stream throws an IOException
	 */
	public final void store(DataOutputStream out, boolean persistWirings) throws IOException {
		readLock();
		try {
			Persistence.store(this, out, persistWirings);
		} finally {
			readUnlock();
		}
	}

	/**
	 * Loads information into this database from the input data stream.  This data
	 * base must be empty and never been modified (the {@link #getRevisionsTimestamp() timestamp} is zero).
	 * All stored modules are loaded into this database.  If the input stream contains
	 * wiring then it will also be loaded into this database.
	 * <p>
	 * Since this method modifies this database it is considered a write operation.
	 * This method acquires the {@link #writeLock() write} lock while loading
	 * the information into this database.
	 * <p>
	 * The specified stream remains open after this method returns.
	 * @param in the data input stream.
	 * @throws IOException if an error occurred when reading from the input stream.
	 * @throws IllegalStateException if this database is not empty.
	 */
	public final void load(DataInputStream in) throws IOException {
		writeLock();
		try {
			if (allTimeStamp.get() != constructionTime)
				throw new IllegalStateException("Can only load into a empty database."); //$NON-NLS-1$
			Persistence.load(this, in);
		} finally {
			writeUnlock();
		}
	}

	final void persistSettings(EnumSet<Settings> settings, Module module) {
		writeLock();
		try {
			EnumSet<Settings> existing = moduleSettings.get(module.getId());
			if (!settings.equals(existing)) {
				moduleSettings.put(module.getId(), EnumSet.copyOf(settings));
				incrementTimestamps(false);
			}
		} finally {
			writeUnlock();
		}
	}

	final void setStartLevel(Module module, int startlevel) {
		writeLock();
		try {
			module.checkValid();
			module.storeStartLevel(startlevel);
			incrementTimestamps(false);
		} finally {
			writeUnlock();
		}
	}

	final int getInitialModuleStartLevel() {
		readLock();
		try {
			return this.initialModuleStartLevel;
		} finally {
			readUnlock();
		}
	}

	final void setInitialModuleStartLevel(int initialStartlevel) {
		writeLock();
		try {
			this.initialModuleStartLevel = initialStartlevel;
			incrementTimestamps(false);
		} finally {
			writeUnlock();
		}
	}

	private static class Persistence {
		private static final int VERSION = 1;
		private static final byte NULL = 0;
		private static final byte OBJECT = 1;
		private static final byte LONG_STRING = 3;
		private static final String UTF_8 = "UTF-8"; //$NON-NLS-1$

		private static final byte VALUE_STRING = 0;
		private static final byte VALUE_STRING_ARRAY = 1;
		private static final byte VAlUE_BOOLEAN = 2;
		private static final byte VALUE_INTEGER = 3;
		private static final byte VALUE_LONG = 4;
		private static final byte VALUE_DOUBLE = 5;
		private static final byte VALUE_VERSION = 6;
		private static final byte VALUE_URI = 7;
		private static final byte VALUE_LIST = 8;

		private static int addToWriteTable(Object object, Map<Object, Integer> objectTable) {
			if (object == null)
				throw new NullPointerException();
			Integer cur = objectTable.get(object);
			if (cur != null)
				throw new IllegalStateException("Object is already in the write table: " + object); //$NON-NLS-1$
			objectTable.put(object, new Integer(objectTable.size()));
			// return the index of the object just added (i.e. size - 1)
			return (objectTable.size() - 1);
		}

		private static void addToReadTable(Object object, int index, Map<Integer, Object> objectTable) {
			objectTable.put(new Integer(index), object);
		}

		public static void store(ModuleDatabase moduleDatabase, DataOutputStream out, boolean persistWirings) throws IOException {
			out.writeInt(VERSION);
			out.writeLong(moduleDatabase.getRevisionsTimestamp());
			out.writeLong(moduleDatabase.getTimestamp());
			out.writeLong(moduleDatabase.getNextId());
			out.writeInt(moduleDatabase.getInitialModuleStartLevel());

			List<Module> modules = moduleDatabase.getModules();
			out.writeInt(modules.size());

			ModuleContainerAdaptor moduleContainerAdaptor =
				moduleDatabase.adaptor;

			out.writeUTF(
				moduleContainerAdaptor.getProperty(
					EquinoxConfiguration.PROP_OSGI_HOME));

			Map<Object, Integer> objectTable = new HashMap<Object, Integer>();
			for (Module module : modules) {
				writeModule(module, moduleDatabase, out, objectTable);
			}

			Collection<ModuleRevision> removalPendings = moduleDatabase.getRemovalPending();
			// only persist wirings if there are no removals pending
			persistWirings &= removalPendings.isEmpty();
			out.writeBoolean(persistWirings);
			if (!persistWirings) {
				return;
			}

			Map<ModuleRevision, ModuleWiring> wirings = moduleDatabase.wirings;
			// prime the object table with all the required wires
			out.writeInt(wirings.size());
			for (ModuleWiring wiring : wirings.values()) {
				List<ModuleWire> requiredWires = wiring.getPersistentRequiredWires();
				out.writeInt(requiredWires.size());
				for (ModuleWire wire : requiredWires) {
					writeWire(wire, out, objectTable);
				}
			}

			// now write all the info about each wiring using only indexes
			for (ModuleWiring wiring : wirings.values()) {
				writeWiring(wiring, out, objectTable);
			}

			out.flush();
		}

		public static void load(ModuleDatabase moduleDatabase, DataInputStream in) throws IOException {
			int version = in.readInt();
			if (version > VERSION || VERSION / 1000 != version / 1000)
				throw new IllegalArgumentException("The version of the persistent framework data is not compatible: " + version + " expecting: " + VERSION); //$NON-NLS-1$ //$NON-NLS-2$
			long revisionsTimeStamp = in.readLong();
			long allTimeStamp = in.readLong();
			moduleDatabase.nextId.set(in.readLong());
			moduleDatabase.setInitialModuleStartLevel(in.readInt());

			int numModules = in.readInt();

			Map<Integer, Object> objectTable = new HashMap<Integer, Object>();

			String oldOsgiHome = in.readUTF();

			for (int i = 0; i < numModules; i++) {
				readModule(moduleDatabase, in, objectTable, oldOsgiHome);
			}

			moduleDatabase.revisionsTimeStamp.set(revisionsTimeStamp);
			moduleDatabase.allTimeStamp.set(allTimeStamp);
			if (!in.readBoolean())
				return; // no wires persisted

			int numWirings = in.readInt();
			// prime the table with all the required wires
			for (int i = 0; i < numWirings; i++) {
				int numWires = in.readInt();
				for (int j = 0; j < numWires; j++) {
					readWire(in, objectTable);
				}
			}

			// now read all the info about each wiring using only indexes
			Map<ModuleRevision, ModuleWiring> wirings = new HashMap<ModuleRevision, ModuleWiring>();
			for (int i = 0; i < numWirings; i++) {
				ModuleWiring wiring = readWiring(in, objectTable);
				wirings.put(wiring.getRevision(), wiring);
			}
			// TODO need to do this without incrementing the timestamp
			moduleDatabase.setWiring(wirings);

			// need to set the resolution state of the modules
			for (ModuleWiring wiring : wirings.values()) {
				wiring.getRevision().getRevisions().getModule().setState(State.RESOLVED);
			}

			// Setting the timestamp at the end since some operations increment it
			moduleDatabase.revisionsTimeStamp.set(revisionsTimeStamp);
			moduleDatabase.allTimeStamp.set(allTimeStamp);
		}

		private static void writeModule(Module module, ModuleDatabase moduleDatabase, DataOutputStream out, Map<Object, Integer> objectTable) throws IOException {
			ModuleRevision current = module.getCurrentRevision();
			if (current == null)
				return;
			out.writeInt(addToWriteTable(current, objectTable));

			writeString(module.getLocation(), out);
			out.writeLong(module.getId());

			writeString(current.getSymbolicName(), out);
			writeVersion(current.getVersion(), out);
			out.writeInt(current.getTypes());

			List<ModuleCapability> capabilities = current.getModuleCapabilities(null);
			out.writeInt(capabilities.size());
			for (ModuleCapability capability : capabilities) {
				out.writeInt(addToWriteTable(capability, objectTable));
				writeGenericInfo(capability.getNamespace(), capability.getPersistentAttributes(), capability.getDirectives(), out);
			}

			List<Requirement> requirements = current.getRequirements(null);
			out.writeInt(requirements.size());
			for (Requirement requirement : requirements) {
				out.writeInt(addToWriteTable(requirement, objectTable));
				writeGenericInfo(requirement.getNamespace(), requirement.getAttributes(), requirement.getDirectives(), out);
			}

			// settings
			EnumSet<Settings> settings = moduleDatabase.moduleSettings.get(module.getId());
			out.writeInt(settings == null ? 0 : settings.size());
			if (settings != null) {
				for (Settings setting : settings) {
					writeString(setting.name(), out);
				}
			}

			// startlevel
			out.writeInt(module.getStartLevel());

			// last modified
			out.writeLong(module.getLastModified());
		}

		private static void readModule(ModuleDatabase moduleDatabase, DataInputStream in, Map<Integer, Object> objectTable, String oldOsgiHome) throws IOException {
			ModuleRevisionBuilder builder = new ModuleRevisionBuilder();
			int moduleIndex = in.readInt();
			String location = readString(in);

			ModuleContainerAdaptor moduleContainerAdaptor =
				moduleDatabase.adaptor;

			String newOsgiHome = moduleContainerAdaptor.getProperty(
				EquinoxConfiguration.PROP_OSGI_HOME);

			if (!oldOsgiHome.equals(newOsgiHome)) {
				location = location.replace(oldOsgiHome, newOsgiHome);
			}

			long id = in.readLong();
			builder.setSymbolicName(readString(in));
			builder.setVersion(readVersion(in));
			builder.setTypes(in.readInt());

			int numCapabilities = in.readInt();
			int[] capabilityIndexes = new int[numCapabilities];
			for (int i = 0; i < numCapabilities; i++) {
				capabilityIndexes[i] = in.readInt();
				readGenericInfo(true, in, builder);
			}

			int numRequirements = in.readInt();
			int[] requirementIndexes = new int[numRequirements];
			for (int i = 0; i < numRequirements; i++) {
				requirementIndexes[i] = in.readInt();
				readGenericInfo(false, in, builder);
			}

			// settings
			EnumSet<Settings> settings = null;
			int numSettings = in.readInt();
			if (numSettings > 0) {
				settings = EnumSet.noneOf(Settings.class);
				for (int i = 0; i < numSettings; i++) {
					settings.add(Settings.valueOf(readString(in)));
				}
			}

			// startlevel
			int startlevel = in.readInt();
			Object revisionInfo = moduleDatabase.adaptor.getRevisionInfo(location, id);
			Module module = moduleDatabase.load(location, builder, revisionInfo, id, settings, startlevel);

			// last modified
			module.setlastModified(in.readLong());

			ModuleRevision current = module.getCurrentRevision();
			addToReadTable(current, moduleIndex, objectTable);

			List<ModuleCapability> capabilities = current.getModuleCapabilities(null);
			for (int i = 0; i < capabilities.size(); i++) {
				addToReadTable(capabilities.get(i), capabilityIndexes[i], objectTable);
			}

			List<ModuleRequirement> requirements = current.getModuleRequirements(null);
			for (int i = 0; i < requirements.size(); i++) {
				addToReadTable(requirements.get(i), requirementIndexes[i], objectTable);
			}
		}

		private static void writeWire(ModuleWire wire, DataOutputStream out, Map<Object, Integer> objectTable) throws IOException {
			Wire w = wire;
			Integer capability = objectTable.get(w.getCapability());
			Integer provider = objectTable.get(w.getProvider());
			Integer requirement = objectTable.get(w.getRequirement());
			Integer requirer = objectTable.get(w.getRequirer());

			if (capability == null || provider == null || requirement == null || requirer == null)
				throw new NullPointerException("Could not find the expected indexes"); //$NON-NLS-1$

			out.writeInt(addToWriteTable(wire, objectTable));

			out.writeInt(capability);
			out.writeInt(provider);
			out.writeInt(requirement);
			out.writeInt(requirer);
		}

		private static void readWire(DataInputStream in, Map<Integer, Object> objectTable) throws IOException {
			int wireIndex = in.readInt();

			ModuleCapability capability = (ModuleCapability) objectTable.get(in.readInt());
			ModuleRevision provider = (ModuleRevision) objectTable.get(in.readInt());
			ModuleRequirement requirement = (ModuleRequirement) objectTable.get(in.readInt());
			ModuleRevision requirer = (ModuleRevision) objectTable.get(in.readInt());

			if (capability == null || provider == null || requirement == null || requirer == null)
				throw new NullPointerException("Could not find the expected indexes"); //$NON-NLS-1$

			ModuleWire result = new ModuleWire(capability, provider, requirement, requirer);

			addToReadTable(result, wireIndex, objectTable);
		}

		private static void writeWiring(ModuleWiring wiring, DataOutputStream out, Map<Object, Integer> objectTable) throws IOException {
			Integer revisionIndex = objectTable.get(wiring.getRevision());
			if (revisionIndex == null)
				throw new NullPointerException("Could not find revision for wiring."); //$NON-NLS-1$
			out.writeInt(revisionIndex);

			List<ModuleCapability> capabilities = wiring.getModuleCapabilities(null);
			out.writeInt(capabilities.size());
			for (ModuleCapability capability : capabilities) {
				Integer capabilityIndex = objectTable.get(capability);
				if (capabilityIndex == null)
					throw new NullPointerException("Could not find capability for wiring."); //$NON-NLS-1$
				out.writeInt(capabilityIndex);
			}

			List<ModuleRequirement> requirements = wiring.getPersistentRequirements();
			out.writeInt(requirements.size());
			for (ModuleRequirement requirement : requirements) {
				Integer requirementIndex = objectTable.get(requirement);
				if (requirementIndex == null)
					throw new NullPointerException("Could not find requirement for wiring."); //$NON-NLS-1$
				out.writeInt(requirementIndex);
			}

			List<ModuleWire> providedWires = wiring.getPersistentProvidedWires();
			out.writeInt(providedWires.size());
			for (ModuleWire wire : providedWires) {
				Integer wireIndex = objectTable.get(wire);
				if (wireIndex == null)
					throw new NullPointerException("Could not find provided wire for wiring."); //$NON-NLS-1$
				out.writeInt(wireIndex);
			}

			List<ModuleWire> requiredWires = wiring.getPersistentRequiredWires();
			out.writeInt(requiredWires.size());
			for (ModuleWire wire : requiredWires) {
				Integer wireIndex = objectTable.get(wire);
				if (wireIndex == null)
					throw new NullPointerException("Could not find required wire for wiring."); //$NON-NLS-1$
				out.writeInt(wireIndex);
			}

			Collection<String> substituted = wiring.getSubstitutedNames();
			out.writeInt(substituted.size());
			for (String pkgName : substituted) {
				writeString(pkgName, out);
			}
		}

		private static ModuleWiring readWiring(DataInputStream in, Map<Integer, Object> objectTable) throws IOException {
			ModuleRevision revision = (ModuleRevision) objectTable.get(in.readInt());
			if (revision == null)
				throw new NullPointerException("Could not find revision for wiring."); //$NON-NLS-1$

			int numCapabilities = in.readInt();
			List<ModuleCapability> capabilities = new ArrayList<ModuleCapability>(numCapabilities);
			for (int i = 0; i < numCapabilities; i++) {
				capabilities.add((ModuleCapability) objectTable.get(in.readInt()));
			}

			int numRequirements = in.readInt();
			List<ModuleRequirement> requirements = new ArrayList<ModuleRequirement>(numRequirements);
			for (int i = 0; i < numRequirements; i++) {
				requirements.add((ModuleRequirement) objectTable.get(in.readInt()));
			}

			int numProvidedWires = in.readInt();
			List<ModuleWire> providedWires = new ArrayList<ModuleWire>(numProvidedWires);
			for (int i = 0; i < numProvidedWires; i++) {
				providedWires.add((ModuleWire) objectTable.get(in.readInt()));
			}

			int numRequiredWires = in.readInt();
			List<ModuleWire> requiredWires = new ArrayList<ModuleWire>(numRequiredWires);
			for (int i = 0; i < numRequiredWires; i++) {
				requiredWires.add((ModuleWire) objectTable.get(in.readInt()));
			}

			int numSubstitutedNames = in.readInt();
			Collection<String> substituted = new ArrayList<String>(numSubstitutedNames);
			for (int i = 0; i < numSubstitutedNames; i++) {
				substituted.add(readString(in));
			}

			return new ModuleWiring(revision, capabilities, requirements, providedWires, requiredWires, substituted);
		}

		private static void writeGenericInfo(String namespace, Map<String, ?> attributes, Map<String, String> directives, DataOutputStream out) throws IOException {
			writeString(namespace, out);
			writeMap(attributes, out);
			writeMap(directives, out);
		}

		@SuppressWarnings("unchecked")
		private static void readGenericInfo(boolean isCapability, DataInputStream in, ModuleRevisionBuilder builder) throws IOException {
			String namespace = readString(in);
			Map<String, Object> attributes = readMap(in);
			Map<String, ?> directives = readMap(in);
			if (isCapability) {
				builder.addCapability(namespace, (Map<String, String>) directives, attributes);
			} else {
				builder.addRequirement(namespace, (Map<String, String>) directives, attributes);
			}

		}

		private static void writeMap(Map<String, ?> source, DataOutputStream out) throws IOException {
			if (source == null) {
				out.writeInt(0);
			} else {
				out.writeInt(source.size());
				Iterator<String> iter = source.keySet().iterator();
				while (iter.hasNext()) {
					String key = iter.next();
					Object value = source.get(key);
					writeString(key, out);
					if (value instanceof String) {
						out.writeByte(VALUE_STRING);
						writeString((String) value, out);
					} else if (value instanceof String[]) {
						out.writeByte(VALUE_STRING_ARRAY);
						writeStringArray(out, (String[]) value);
					} else if (value instanceof Boolean) {
						out.writeByte(VAlUE_BOOLEAN);
						out.writeBoolean(((Boolean) value).booleanValue());
					} else if (value instanceof Integer) {
						out.writeByte(VALUE_INTEGER);
						out.writeInt(((Integer) value).intValue());
					} else if (value instanceof Long) {
						out.writeByte(VALUE_LONG);
						out.writeLong(((Long) value).longValue());
					} else if (value instanceof Double) {
						out.writeByte(VALUE_DOUBLE);
						out.writeDouble(((Double) value).doubleValue());
					} else if (value instanceof Version) {
						out.writeByte(VALUE_VERSION);
						writeVersion((Version) value, out);
					} else if (value instanceof URI) {
						out.writeByte(VALUE_URI);
						writeString(value.toString(), out);
					} else if (value instanceof List) {
						out.writeByte(VALUE_LIST);
						writeList(out, (List<?>) value);
					}
				}
			}
		}

		private static Map<String, Object> readMap(DataInputStream in) throws IOException {
			int count = in.readInt();
			HashMap<String, Object> result = new HashMap<String, Object>(count);
			for (int i = 0; i < count; i++) {
				String key = readString(in);
				Object value = null;
				byte type = in.readByte();
				if (type == VALUE_STRING)
					value = readString(in);
				else if (type == VALUE_STRING_ARRAY)
					value = readStringArray(in);
				else if (type == VAlUE_BOOLEAN)
					value = in.readBoolean() ? Boolean.TRUE : Boolean.FALSE;
				else if (type == VALUE_INTEGER)
					value = new Integer(in.readInt());
				else if (type == VALUE_LONG)
					value = new Long(in.readLong());
				else if (type == VALUE_DOUBLE)
					value = new Double(in.readDouble());
				else if (type == VALUE_VERSION)
					value = readVersion(in);
				else if (type == VALUE_URI)
					try {
						value = new URI(readString(in));
					} catch (URISyntaxException e) {
						value = null;
					}
				else if (type == VALUE_LIST)
					value = readList(in);

				result.put(key, value);
			}
			return result;
		}

		private static void writeStringArray(DataOutputStream out, String[] value) throws IOException {
			if (value == null) {
				out.writeInt(0);
			} else {
				out.writeInt(value.length);
				for (int i = 0; i < value.length; i++)
					writeString(value[i], out);
			}

		}

		private static String[] readStringArray(DataInputStream in) throws IOException {
			int count = in.readInt();
			if (count == 0)
				return null;
			String[] result = new String[count];
			for (int i = 0; i < count; i++)
				result[i] = readString(in);
			return result;
		}

		private static void writeList(DataOutputStream out, List<?> list) throws IOException {
			if (list.isEmpty()) {
				out.writeInt(0);
				return;
			}
			byte type = getListType(list);
			if (type < 0) {
				out.writeInt(0);
				return; // don't understand the list type
			}
			out.writeInt(list.size());
			out.writeByte(type);
			for (Object value : list) {
				switch (type) {
					case VALUE_STRING :
						writeString((String) value, out);
						break;
					case VALUE_INTEGER :
						out.writeInt(((Integer) value).intValue());
						break;
					case VALUE_LONG :
						out.writeLong(((Long) value).longValue());
						break;
					case VALUE_DOUBLE :
						out.writeDouble(((Double) value).doubleValue());
						break;
					case VALUE_VERSION :
						writeVersion((Version) value, out);
						break;
					default :
						break;
				}
			}
		}

		private static byte getListType(List<?> list) {
			if (list.size() == 0)
				return -1;
			Object type = list.get(0);
			if (type instanceof String)
				return VALUE_STRING;
			if (type instanceof Integer)
				return VALUE_INTEGER;
			if (type instanceof Long)
				return VALUE_LONG;
			if (type instanceof Double)
				return VALUE_DOUBLE;
			if (type instanceof Version)
				return VALUE_VERSION;
			return -2;
		}

		private static List<?> readList(DataInputStream in) throws IOException {

			int size = in.readInt();
			if (size == 0)
				return new ArrayList<Object>(0);
			byte listType = in.readByte();
			List<Object> list = new ArrayList<Object>(size);
			for (int i = 0; i < size; i++) {
				switch (listType) {
					case VALUE_STRING :
						list.add(readString(in));
						break;
					case VALUE_INTEGER :
						list.add(new Integer(in.readInt()));
						break;
					case VALUE_LONG :
						list.add(new Long(in.readLong()));
						break;
					case VALUE_DOUBLE :
						list.add(new Double(in.readDouble()));
						break;
					case VALUE_VERSION :
						list.add(readVersion(in));
						break;
					default :
						throw new IOException("Invalid type: " + listType); //$NON-NLS-1$
				}
			}
			return list;
		}

		private static void writeVersion(Version version, DataOutputStream out) throws IOException {
			if (version == null || version.equals(Version.emptyVersion)) {
				out.writeByte(NULL);
				return;
			}
			out.writeByte(OBJECT);
			out.writeInt(version.getMajor());
			out.writeInt(version.getMinor());
			out.writeInt(version.getMicro());
			writeQualifier(version.getQualifier(), out);
		}

		private static void writeQualifier(String string, DataOutputStream out) throws IOException {
			if (string != null && string.length() == 0)
				string = null;
			writeString(string, out);
		}

		private static Version readVersion(DataInputStream in) throws IOException {
			byte tag = in.readByte();
			if (tag == NULL)
				return Version.emptyVersion;
			int majorComponent = in.readInt();
			int minorComponent = in.readInt();
			int serviceComponent = in.readInt();
			String qualifierComponent = readString(in);
			return (Version) ObjectPool.intern(new Version(majorComponent, minorComponent, serviceComponent, qualifierComponent));
		}

		private static void writeString(String string, DataOutputStream out) throws IOException {
			if (string == null)
				out.writeByte(NULL);
			else {
				byte[] data = string.getBytes(UTF_8);

				if (data.length > 65535) {
					out.writeByte(LONG_STRING);
					out.writeInt(data.length);
					out.write(data);
				} else {
					out.writeByte(OBJECT);
					out.writeUTF(string);
				}
			}
		}

		static private String readString(DataInputStream in) throws IOException {
			byte type = in.readByte();
			if (type == NULL)
				return null;

			if (type == LONG_STRING) {
				int length = in.readInt();
				byte[] data = new byte[length];
				in.readFully(data);
				String string = new String(data, UTF_8);

				return (String) ObjectPool.intern(string);
			}

			return (String) ObjectPool.intern(in.readUTF());
		}
	}
}
