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

package com.liferay.oauth2.provider.jsonws.internal.service.access.policy.scope;

import com.liferay.oauth2.provider.jsonws.internal.configuration.OAuth2JSONWSConfiguration;
import com.liferay.oauth2.provider.jsonws.internal.constants.OAuth2JSONWSConstants;
import com.liferay.oauth2.provider.scope.spi.scope.descriptor.ScopeDescriptor;
import com.liferay.oauth2.provider.scope.spi.scope.finder.ScopeFinder;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(
	configurationPid = "com.liferay.oauth2.provider.jsonws.internal.configuration.OAuth2JSONWSConfiguration",
	immediate = true, service = SAPEntryScopeDescriptorFinderRegistrator.class
)
public class SAPEntryScopeDescriptorFinderRegistrator {

	public List<SAPEntryScope> getRegisteredSAPEntryScopes(long companyId) {
		return new ArrayList<>(_registeredSAPEntryScopes.get(companyId));
	}

	public void register(long companyId) {
		try {
			List<SAPEntryScope> sapEntryScopes = loadSAPEntryScopes(companyId);

			Dictionary<String, Object> properties = new HashMapDictionary<>();

			properties.put("companyId", String.valueOf(companyId));
			properties.put(
				"osgi.jaxrs.name", OAuth2JSONWSConstants.APPLICATION_NAME);

			_serviceRegistrations.compute(
				companyId,
				(key, serviceRegistration) -> {
					if (serviceRegistration != null) {
						serviceRegistration.unregister();
					}

					serviceRegistration = _bundleContext.registerService(
						new String[] {
							ScopeDescriptor.class.getName(),
							ScopeFinder.class.getName()
						},
						new SAPEntryScopeDescriptorFinder(sapEntryScopes),
						properties);

					_registeredSAPEntryScopes.put(companyId, sapEntryScopes);

					return serviceRegistration;
				});
		}
		catch (Exception e) {
			_log.error(
				"Unable to register SAP entry scope descriptor finder for " +
					"company " + companyId,
				e);
		}
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		OAuth2JSONWSConfiguration oAuth2JSONWSConfiguration =
			ConfigurableUtil.createConfigurable(
				OAuth2JSONWSConfiguration.class, properties);

		_removeSAPEntryOAuth2Prefix =
			oAuth2JSONWSConfiguration.removeSAPEntryOAuth2Prefix();

		_sapEntryOAuth2Prefix =
			oAuth2JSONWSConfiguration.sapEntryOAuth2Prefix();

		for (long companyId : _serviceRegistrations.keySet()) {
			register(companyId);
		}
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration serviceRegistration :
				_serviceRegistrations.values()) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();
	}

	protected boolean isOAuth2ExportedSAPEntry(SAPEntry sapEntry) {
		return StringUtil.startsWith(sapEntry.getName(), _sapEntryOAuth2Prefix);
	}

	protected List<SAPEntryScope> loadSAPEntryScopes(long companyId) {
		List<SAPEntry> sapEntries = _sapEntryLocalService.getCompanySAPEntries(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Stream<SAPEntry> stream = sapEntries.stream();

		return stream.filter(
			this::isOAuth2ExportedSAPEntry
		).filter(
			SAPEntry::isEnabled
		).map(
			sapEntry -> new SAPEntryScope(sapEntry, _parseScope(sapEntry))
		).collect(
			Collectors.toList()
		);
	}

	private String _parseScope(SAPEntry sapEntry) {
		String sapEntryName = sapEntry.getName();

		if (!_removeSAPEntryOAuth2Prefix) {
			return sapEntryName;
		}

		return sapEntryName.substring(_sapEntryOAuth2Prefix.length());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SAPEntryScopeDescriptorFinderRegistrator.class);

	private BundleContext _bundleContext;
	private final Map<Long, List<SAPEntryScope>> _registeredSAPEntryScopes =
		new ConcurrentHashMap<>();
	private boolean _removeSAPEntryOAuth2Prefix = true;

	@Reference
	private SAPEntryLocalService _sapEntryLocalService;

	private String _sapEntryOAuth2Prefix = "OAUTH2_";
	private final Map<Long, ServiceRegistration> _serviceRegistrations =
		new ConcurrentHashMap<>();

}