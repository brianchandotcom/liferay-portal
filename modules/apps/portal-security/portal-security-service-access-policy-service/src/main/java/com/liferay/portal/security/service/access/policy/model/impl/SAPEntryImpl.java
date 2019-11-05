/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.security.service.access.policy.model.impl;

import com.liferay.osgi.util.StringPlus;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.service.access.policy.configuration.SAPConfiguration;
import com.liferay.portal.security.service.access.policy.constants.SAPConstants;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Brian Wing Shun Chan
 */
public class SAPEntryImpl extends SAPEntryBaseImpl {

	@Override
	public List<String> getAllowedServiceSignaturesList() {
		String[] allowedServiceSignatures = StringUtil.split(
			getAllowedServiceSignatures(), StringPool.NEW_LINE);

		return ListUtil.fromArray(allowedServiceSignatures);
	}

	@Override
	public boolean isSystem() throws ConfigurationException {
		SAPConfiguration sapConfiguration =
			ConfigurationProviderUtil.getConfiguration(
				SAPConfiguration.class,
				new CompanyServiceSettingsLocator(
					getCompanyId(), SAPConstants.SERVICE_NAME));

		if (Objects.equals(
				getName(), sapConfiguration.systemDefaultSAPEntryName())) {

			return true;
		}

		if (Objects.equals(
				getName(), sapConfiguration.systemUserPasswordSAPEntryName())) {

			return true;
		}

		if (SAPSystemEntriesHolder.contains(getName())) {
			return true;
		}

		return false;
	}

	private static class SAPSystemEntriesHolder {

		public static boolean contains(String name) {
			return _sapSystemEntries.contains(name);
		}

		private static final Set<String> _sapSystemEntries =
			Collections.newSetFromMap(new ConcurrentHashMap<>());

		private static class SAPSystemEntryServiceTrackerCustomizer
			implements ServiceTrackerCustomizer<Object, Set<String>> {

			@Override
			public Set<String> addingService(
				ServiceReference<Object> serviceReference) {

				Set<String> sapSystemEntries = new HashSet<>(
					StringPlus.asList(
						serviceReference.getProperty("sap.system.entry")));

				synchronized (this) {
					_serviceReferenceMap.put(
						serviceReference, sapSystemEntries);

					_sapSystemEntries.addAll(sapSystemEntries);
				}

				return sapSystemEntries;
			}

			@Override
			public void modifiedService(
				ServiceReference<Object> serviceReference,
				Set<String> sapSystemEntries) {

				Set<String> newSAPSystemEntries = new HashSet<>(
					StringPlus.asList(
						serviceReference.getProperty("sap.system.entry")));

				if (newSAPSystemEntries.equals(sapSystemEntries)) {
					return;
				}

				synchronized (this) {
					_serviceReferenceMap.put(
						serviceReference, newSAPSystemEntries);

					_sapSystemEntries.addAll(newSAPSystemEntries);

					_reindexSAPSystemEntries(sapSystemEntries);
				}

				sapSystemEntries.clear();
				sapSystemEntries.addAll(newSAPSystemEntries);
			}

			@Override
			public void removedService(
				ServiceReference<Object> serviceReference,
				Set<String> sapSystemEntries) {

				synchronized (this) {
					_serviceReferenceMap.remove(serviceReference);

					_reindexSAPSystemEntries(sapSystemEntries);
				}
			}

			private void _reindexSAPSystemEntries(
				Set<String> removedSAPSystemEntries) {

				for (Set<String> sapSystemEntries :
						_serviceReferenceMap.values()) {

					removedSAPSystemEntries.removeAll(sapSystemEntries);

					if (removedSAPSystemEntries.isEmpty()) {
						return;
					}
				}

				_sapSystemEntries.removeAll(removedSAPSystemEntries);
			}

			private static final Map<Object, Set<String>> _serviceReferenceMap =
				new ConcurrentHashMap<>();

		}

		static {
			try {
				Bundle bundle = FrameworkUtil.getBundle(SAPEntryImpl.class);

				BundleContext bundleContext = bundle.getBundleContext();

				ServiceTracker<Object, Set<String>> serviceTracker =
					new ServiceTracker<>(
						bundleContext,
						bundleContext.createFilter("(sap.system.entry=*)"),
						new SAPSystemEntryServiceTrackerCustomizer());

				serviceTracker.open();
			}
			catch (InvalidSyntaxException ise) {
				throw new ExceptionInInitializerError(ise);
			}
		}

	}

}