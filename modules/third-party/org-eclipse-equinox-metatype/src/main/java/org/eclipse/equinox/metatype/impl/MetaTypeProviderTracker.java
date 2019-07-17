/*******************************************************************************
 * Copyright (c) 2005, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.equinox.metatype.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import org.eclipse.equinox.metatype.*;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.*;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.log.LogService;
import org.osgi.service.metatype.*;
import org.osgi.util.tracker.ServiceTracker;

public class MetaTypeProviderTracker implements EquinoxMetaTypeInformation {
	private final Bundle _bundle;
	private final LogService log;
	private final Map<Bundle, List<Map.Entry<ServiceReference<Object>, Object>>> _metaTypeProviders;

	/**
	 * Constructs a MetaTypeProviderTracker which tracks all MetaTypeProviders
	 * registered by the specified bundle.
	 * @param context The BundleContext of the MetaTypeService implementation
	 * @param bundle The bundle to track all MetaTypeProviders for.
	 * @param log The {@code LogService} to use for logging messages.
	 */
	public MetaTypeProviderTracker(Bundle bundle, LogService log, Map<Bundle, List<Map.Entry<ServiceReference<Object>, Object>>> metaTypeProviders) {
		this._bundle = bundle;
		_metaTypeProviders = metaTypeProviders;
		this.log = log;
	}

	private String[] getPids(boolean factory) {
		if (_bundle.getState() != Bundle.ACTIVE)
			return new String[0]; // return none if not active
		MetaTypeProviderWrapper[] wrappers = getMetaTypeProviders();
		ArrayList<String> results = new ArrayList<String>();
		for (int i = 0; i < wrappers.length; i++) {
			// return only the correct type of pids (regular or factory)
			if (factory == wrappers[i].factory)
				results.add(wrappers[i].pid);
		}
		return results.toArray(new String[results.size()]);
	}

	public String[] getPids() {
		return getPids(false);
	}

	public String[] getFactoryPids() {
		return getPids(true);
	}

	public Bundle getBundle() {
		return _bundle;
	}

	public EquinoxObjectClassDefinition getObjectClassDefinition(String id, String locale) {
		if (_bundle.getState() != Bundle.ACTIVE)
			return null; // return none if not active
		MetaTypeProviderWrapper[] wrappers = getMetaTypeProviders();
		for (int i = 0; i < wrappers.length; i++) {
			if (id.equals(wrappers[i].pid))
				// found a matching pid now call the actual provider
				return wrappers[i].getObjectClassDefinition(id, locale);
		}
		return null;
	}

	public String[] getLocales() {
		if (_bundle.getState() != Bundle.ACTIVE)
			return new String[0]; // return none if not active
		MetaTypeProviderWrapper[] wrappers = getMetaTypeProviders();
		ArrayList<String> locales = new ArrayList<String>();
		// collect all the unique locales from all providers we found
		for (int i = 0; i < wrappers.length; i++) {
			String[] wrappedLocales = wrappers[i].getLocales();
			if (wrappedLocales == null)
				continue;
			for (int j = 0; j < wrappedLocales.length; j++)
				if (!locales.contains(wrappedLocales[j]))
					locales.add(wrappedLocales[j]);
		}
		return locales.toArray(new String[locales.size()]);
	}

	private MetaTypeProviderWrapper[] getMetaTypeProviders() {
		List<Map.Entry<ServiceReference<Object>, Object>> entries = _metaTypeProviders.get(_bundle);

		if ((entries == null) || entries.isEmpty()) {
			return new MetaTypeProviderWrapper[0];
		}

		Set<MetaTypeProviderWrapper> result = new HashSet<MetaTypeProviderWrapper>();

		for (Map.Entry<ServiceReference<Object>, Object> entry : entries) {
			Object service = entry.getValue();
			// If the service is not a MetaTypeProvider, we're not interested in it.
			if (service instanceof MetaTypeProvider) {
				ServiceReference<Object> serviceReference = entry.getKey();

				// Include the METATYPE_PID, if present, to return as part of getPids(). Also, include the
				// METATYPE_FACTORY_PID, if present, to return as part of getFactoryPids().
				// The filter ensures at least one of these properties was set for a standalone MetaTypeProvider.
				addMetaTypeProviderWrappers(MetaTypeProvider.METATYPE_PID, serviceReference, (MetaTypeProvider) service, false, result);
				addMetaTypeProviderWrappers(MetaTypeProvider.METATYPE_FACTORY_PID, serviceReference, (MetaTypeProvider) service, true, result);
				// If the service is a ManagedService, include the SERVICE_PID to return as part of getPids().
				// The filter ensures the SERVICE_PID property was set.
				if (service instanceof ManagedService) {
					addMetaTypeProviderWrappers(Constants.SERVICE_PID, serviceReference, (MetaTypeProvider) service, false, result);
				}
				// If the service is a ManagedServiceFactory, include the SERVICE_PID to return as part of getFactoryPids().
				// The filter ensures the SERVICE_PID property was set.
				else if (service instanceof ManagedServiceFactory) {
					addMetaTypeProviderWrappers(Constants.SERVICE_PID, serviceReference, (MetaTypeProvider) service, true, result);
				}
			}
		}
		return result.toArray(new MetaTypeProviderWrapper[result.size()]);
	}

	private void addMetaTypeProviderWrappers(String servicePropertyName, ServiceReference<Object> serviceReference, MetaTypeProvider service, boolean factory, Set<MetaTypeProviderWrapper> wrappers) {
		String[] pids = getStringProperty(servicePropertyName, serviceReference.getProperty(servicePropertyName));
		for (String pid : pids) {
			wrappers.add(new MetaTypeProviderWrapper(service, pid, factory));
		}
	}

	private String[] getStringProperty(String name, Object value) {
		// Don't log a warning if the value is null. The filter guarantees at least one of the necessary properties
		// is there. If others are not, this method will get called with value equal to null.
		if (value == null)
			return new String[0];
		if (value instanceof String) {
			return new String[] {(String) value};
		}
		if (value instanceof String[]) {
			return (String[]) value;
		}
		Exception e = null;
		if (value instanceof Collection) {
			@SuppressWarnings("unchecked")
			Collection<String> temp = (Collection<String>) value;
			try {
				return temp.toArray(new String[temp.size()]);
			} catch (ArrayStoreException ase) {
				e = ase;
			}
		}
		log.log(LogService.LOG_WARNING, NLS.bind(MetaTypeMsg.INVALID_PID_METATYPE_PROVIDER_IGNORED, new Object[] {_bundle.getSymbolicName(), _bundle.getBundleId(), name, value}), e);
		return new String[0];
	}

	// this is a simple class just used to temporarily store information about a provider
	public class MetaTypeProviderWrapper implements MetaTypeProvider {
		private final MetaTypeProvider provider;
		final String pid;
		final boolean factory;

		MetaTypeProviderWrapper(MetaTypeProvider provider, String pid, boolean factory) {
			this.provider = provider;
			this.pid = pid;
			this.factory = factory;
		}

		@Override
		public boolean equals(Object object) {
			if (object == this)
				return true;
			if (!(object instanceof MetaTypeProviderWrapper))
				return false;
			MetaTypeProviderWrapper that = (MetaTypeProviderWrapper) object;
			return this.provider.equals(that.provider) && this.pid.equals(that.pid) && this.factory == that.factory;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 31 * result + provider.hashCode();
			result = 31 * result + pid.hashCode();
			result = 31 * result + (factory ? 1 : 0);
			return result;
		}

		public EquinoxObjectClassDefinition getObjectClassDefinition(String id, String locale) {
			final ObjectClassDefinition ocd = provider.getObjectClassDefinition(id, locale);
			if (ocd == null)
				return null;
			return new EquinoxObjectClassDefinition() {
				public String getName() {
					return ocd.getName();
				}

				public String getID() {
					return ocd.getID();
				}

				public String getDescription() {
					return ocd.getDescription();
				}

				public InputStream getIcon(int size) throws IOException {
					return ocd.getIcon(size);
				}

				public Map<String, String> getExtensionAttributes(String schema) {
					return Collections.<String, String> emptyMap();
				}

				public Set<String> getExtensionUris() {
					return Collections.<String> emptySet();
				}

				public EquinoxAttributeDefinition[] getAttributeDefinitions(int filter) {
					AttributeDefinition[] ads = ocd.getAttributeDefinitions(filter);
					if (ads == null || ads.length == 0)
						return new EquinoxAttributeDefinition[0];
					Collection<EquinoxAttributeDefinition> result = new ArrayList<EquinoxAttributeDefinition>(ads.length);
					for (final AttributeDefinition ad : ads) {
						result.add(new EquinoxAttributeDefinition() {
							public String getName() {
								return ad.getName();
							}

							public String getID() {
								return ad.getID();
							}

							public String getDescription() {
								return ad.getDescription();
							}

							public int getCardinality() {
								return ad.getCardinality();
							}

							public int getType() {
								return ad.getType();
							}

							public String[] getOptionValues() {
								return ad.getOptionValues();
							}

							public String[] getOptionLabels() {
								return ad.getOptionLabels();
							}

							public String validate(String value) {
								return ad.validate(value);
							}

							public String[] getDefaultValue() {
								return ad.getDefaultValue();
							}

							public Map<String, String> getExtensionAttributes(String schema) {
								return Collections.<String, String> emptyMap();
							}

							public Set<String> getExtensionUris() {
								return Collections.<String> emptySet();
							}

							public String getMax() {
								return null;
							}

							public String getMin() {
								return null;
							}
						});
					}
					return result.toArray(new EquinoxAttributeDefinition[result.size()]);
				}
			};
		}

		public String[] getLocales() {
			return provider.getLocales();
		}
	}
}
/* @generated */