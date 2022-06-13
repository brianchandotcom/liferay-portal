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

package com.liferay.client.extension.type.internal.factory;

import com.liferay.client.extension.exception.ClientExtensionEntryTypeException;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.configuration.CETConfiguration;
import com.liferay.client.extension.type.factory.CETFactory;
import com.liferay.client.extension.type.factory.CETImplFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapListener;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.portlet.PortletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = CETFactory.class)
public class CETFactoryImpl implements CETFactory {

	@Override
	public CET cet(
			CETConfiguration cetConfiguration, long companyId,
			String externalReferenceCode)
		throws PortalException {

		CETImplFactory cetImplFactory = _getCETImplFactory(
			cetConfiguration.type());

		try {
			return cetImplFactory.cet(
				cetConfiguration.baseURL(), companyId,
				cetConfiguration.description(), externalReferenceCode,
				cetConfiguration.name(), _loadProperties(cetConfiguration),
				cetConfiguration.sourceCodeURL(),
				_toTypeSettingsUnicodeProperties(cetConfiguration));
		}
		catch (IOException ioException) {
			throw new PortalException(ioException);
		}
	}

	@Override
	public CET cet(ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		CETImplFactory cetImplFactory = _getCETImplFactory(
			clientExtensionEntry.getType());

		return cetImplFactory.cet(clientExtensionEntry);
	}

	@Override
	public CET cet(PortletRequest portletRequest, String type)
		throws PortalException {

		CETImplFactory cetImplFactory = _getCETImplFactory(type);

		return cetImplFactory.cet(portletRequest);
	}

	@Override
	public Collection<String> getTypes() {
		return Collections.unmodifiableCollection(_types);
	}

	@Override
	public void validate(
			UnicodeProperties newTypeSettingsUnicodeProperties,
			UnicodeProperties oldTypeSettingsUnicodeProperties, String type)
		throws PortalException {

		CETImplFactory cetImplFactory = _getCETImplFactory(type);

		cetImplFactory.validate(
			newTypeSettingsUnicodeProperties, oldTypeSettingsUnicodeProperties);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CETImplFactory.class, "type",
			_serviceTrackerMapListener);
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceTrackerMap != null) {
			_serviceTrackerMap.close();
		}

		_serviceTrackerMap = null;
	}

	private CETImplFactory _getCETImplFactory(String type)
		throws ClientExtensionEntryTypeException {

		CETImplFactory cetImplFactory = _serviceTrackerMap.getService(type);

		if (cetImplFactory == null) {
			throw new ClientExtensionEntryTypeException(
				"No CET impl factory registered for type " + type);
		}

		return cetImplFactory;
	}

	private Properties _loadProperties(CETConfiguration cetConfiguration)
		throws IOException {

		String[] properties = cetConfiguration.properties();

		if (properties == null) {
			return new Properties();
		}

		return PropertiesUtil.load(
			StringUtil.merge(properties, StringPool.NEW_LINE));
	}

	private UnicodeProperties _toTypeSettingsUnicodeProperties(
		CETConfiguration cetConfiguration) {

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.create(
				true
			).build();

		String[] typeSettings = cetConfiguration.typeSettings();

		if (typeSettings == null) {
			return typeSettingsUnicodeProperties;
		}

		for (String typeSetting : typeSettings) {
			typeSettingsUnicodeProperties.put(typeSetting);
		}

		return typeSettingsUnicodeProperties;
	}

	private ServiceTrackerMap<String, CETImplFactory> _serviceTrackerMap;

	private final ServiceTrackerMapListener
		<String, CETImplFactory, CETImplFactory> _serviceTrackerMapListener =
			new ServiceTrackerMapListener
				<String, CETImplFactory, CETImplFactory>() {

				@Override
				public void keyEmitted(
					ServiceTrackerMap<String, CETImplFactory> serviceTrackerMap,
					String key, CETImplFactory service,
					CETImplFactory content) {

					synchronized (_types) {
						_types.add(key);

						Collections.sort(_types);
					}
				}

				@Override
				public void keyRemoved(
					ServiceTrackerMap<String, CETImplFactory> serviceTrackerMap,
					String key, CETImplFactory service,
					CETImplFactory content) {

					synchronized (_types) {
						_types.remove(key);
					}
				}

			};

	private final List<String> _types = new ArrayList<>();

}