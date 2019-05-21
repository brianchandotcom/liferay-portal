/*******************************************************************************
 * Copyright (c) 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.osgi.internal.log;

import java.util.*;
import org.eclipse.osgi.internal.log.ExtendedLogServiceFactory.EquinoxLoggerContext;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;
import org.osgi.service.log.admin.LoggerContext;

public class LoggerContextTargetMap {
	private final Map<Bundle, ExtendedLogServiceImpl> logServices = new HashMap<>();
	private final Map<String, EquinoxLoggerContext> loggerContexts = new HashMap<>();
	private final Map<Bundle, List<String>> targetToQualifiedNames = new HashMap<>();
	private final Map<String, Collection<Bundle>> qualifiedNameToTargets = new HashMap<>();

	List<String> add(Bundle b) {
		String bsn = b.getSymbolicName();
		if (bsn == null) {
			bsn = ""; //$NON-NLS-1$
		}
		Version v = b.getVersion();
		String version = v == null ? "" : v.toString(); //$NON-NLS-1$
		String location = ExtendedLogServiceFactory.secureAction.getLocation(b);

		List<String> result = new ArrayList<>(3);

		StringBuilder sb = new StringBuilder(bsn);
		getTargetsInternal(bsn).add(b);

		sb.append('|').append(version);
		String bsnVersion = sb.toString();
		getTargetsInternal(bsnVersion).add(b);

		sb.append('|').append(location);
		String bsnVersionLocation = sb.toString();
		getTargetsInternal(bsnVersionLocation).add(b);

		result.add(bsnVersionLocation);
		result.add(bsnVersion);
		result.add(bsn);

		List<String> unmodifiable = Collections.unmodifiableList(result);
		targetToQualifiedNames.put(b, unmodifiable);
		return unmodifiable;
	}

	void remove(Bundle b) {
		List<String> qualifiedNames = targetToQualifiedNames.remove(b);
		if (qualifiedNames != null) {
			for (String qualifiedName : qualifiedNames) {
				Collection<Bundle> targets = qualifiedNameToTargets.get(qualifiedName);
				if (targets != null) {
					targets.remove(b);
					if (targets.isEmpty()) {
						qualifiedNameToTargets.remove(qualifiedName);
					}
				}
			}
		}
		logServices.remove(b);
	}

	private Collection<Bundle> getTargetsInternal(String pid) {
		Collection<Bundle> targets = qualifiedNameToTargets.get(pid);
		if (targets == null) {
			targets = new ArrayList<>(1);
			qualifiedNameToTargets.put(pid, targets);
		}
		return targets;
	}

	ExtendedLogServiceImpl getLogService(Bundle bundle, ExtendedLogServiceFactory factory) {
		ExtendedLogServiceImpl logService = logServices.get(bundle);
		if (logService == null) {
			// add bundle to target maps before constructing
			add(bundle);
			logService = new ExtendedLogServiceImpl(factory, bundle);
			if (bundle != null && bundle.getState() != Bundle.UNINSTALLED)
				logServices.put(bundle, logService);
		}
		return logService;
	}

	void replaceSystemBundleLogService(Bundle previousBundle, Bundle currentBundle) {
		ExtendedLogServiceImpl existing = logServices.get(previousBundle);
		if (existing != null) {
			remove(previousBundle);
			add(currentBundle);
			logServices.put(currentBundle, existing);
			existing.applyLogLevels(getEffectiveLoggerContext(currentBundle));
		}
	}

	void clear() {
		logServices.clear();
		qualifiedNameToTargets.clear();
		targetToQualifiedNames.clear();
		loggerContexts.clear();
	}

	LoggerContext createLoggerContext(String name, ExtendedLogServiceFactory factory) {
		EquinoxLoggerContext loggerContext = loggerContexts.get(name);
		if (loggerContext == null) {
			loggerContext = factory.createEquinoxLoggerContext(name);
			loggerContexts.put(name, loggerContext);
		}
		return loggerContext;
	}

	EquinoxLoggerContext getRootLoggerContext() {
		return loggerContexts.get(null);
	}

	void applyLogLevels(EquinoxLoggerContext loggerContext) {
		Collection<Bundle> matching;
		boolean isRoot = loggerContext.getName() == null;
		if (isRoot) {
			// root applies to all loggers
			matching = logServices.keySet();
		} else {
			matching = qualifiedNameToTargets.get(loggerContext.getName());
		}

		if (matching == null) {
			return;
		}

		for (Bundle bundle : matching) {
			ExtendedLogServiceImpl logService = logServices.get(bundle);
			if (logService != null) {
				// Always apply the effective log context.
				// This may be more costly but it is more simple than checking
				// if the changed context overrides the existing settings
				logService.applyLogLevels(getEffectiveLoggerContext(bundle));
			}
		}
	}

	EquinoxLoggerContext getEffectiveLoggerContext(Bundle bundle) {
		List<String> qualifiedNames = targetToQualifiedNames.get(bundle);
		if (qualifiedNames != null) {
			for (String qualifiedName : qualifiedNames) {
				EquinoxLoggerContext loggerContext = loggerContexts.get(qualifiedName);
				if (loggerContext != null && !loggerContext.isEmpty()) {
					return loggerContext;
				}
			}
		}
		return getRootLoggerContext();
	}
}
/* @generated */