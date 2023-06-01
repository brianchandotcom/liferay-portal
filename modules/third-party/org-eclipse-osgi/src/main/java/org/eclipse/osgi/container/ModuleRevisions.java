/*******************************************************************************
 * Copyright (c) 2012, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.osgi.container;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicMarkableReference;
import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleRevisions;

/**
 * An implementation of {@link BundleRevisions} which represent a 
 * {@link Module} installed in a {@link ModuleContainer container}.
 * The ModuleRevisions provides a bridge between the revisions, the 
 * module and the container they are associated with.  The 
 * ModuleRevisions holds the information about the installation of
 * a module in a container such as the module id and location.
 * @since 3.10
 */
public final class ModuleRevisions implements BundleRevisions {
	private final Module module;
	private final ModuleContainer container;
	private final Deque<ModuleRevision> revisions = new ConcurrentLinkedDeque<>();
	private volatile ModuleRevision uninstalledCurrent;

	ModuleRevisions(Module module, ModuleContainer container) {
		this.module = module;
		this.container = container;
	}

	public Module getModule() {
		return module;
	}

	ModuleContainer getContainer() {
		return container;
	}

	@Override
	public Bundle getBundle() {
		return module.getBundle();
	}

	@Override
	public List<BundleRevision> getRevisions() {
		return new ArrayList<BundleRevision>(revisions);
	}

	/**
	 * Same as {@link ModuleRevisions#getRevisions()} except it
	 * returns a list of {@link ModuleRevision}.
	 * @return the list of module revisions
	 */
	public List<ModuleRevision> getModuleRevisions() {
		return new ArrayList<>(revisions);
	}

	/**
	 * Returns the current {@link ModuleRevision revision} associated with this revisions.
	 * 
	 * @return the current {@link ModuleRevision revision} associated with this revisions
	 *     or {@code null} if the current revision does not exist.
	 */
	ModuleRevision getCurrentRevision() {
		ModuleRevision moduleRevision = uninstalledCurrent;

		if (moduleRevision == null) {
			moduleRevision = revisions.peek();
		}

		return moduleRevision;
	}

	ModuleRevision addRevision(ModuleRevision revision) {
		revisions.push(revision);

		return revision;
	}

	boolean removeRevision(ModuleRevision revision) {
		try {
			return revisions.remove(revision);
		} finally {
			module.cleanup(revision);
		}
	}

	boolean isUninstalled() {
		return uninstalledCurrent != null;
	}

	void uninstall() {
		ModuleRevision moduleRevision = revisions.peek();

		if (moduleRevision == null) {
			throw new IllegalStateException("Revisions is empty on uninstall!"); //$NON-NLS-1$
		}

		uninstalledCurrent = moduleRevision;
	}

	public String toString() {
		return "moduleID=" + module.getId(); //$NON-NLS-1$
	}
}
/* @generated */