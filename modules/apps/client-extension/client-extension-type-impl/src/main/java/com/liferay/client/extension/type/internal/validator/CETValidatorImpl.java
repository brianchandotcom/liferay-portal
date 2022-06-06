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

package com.liferay.client.extension.type.internal.validator;

import com.liferay.client.extension.exception.ClientExtensionEntryTypeException;
import com.liferay.client.extension.type.validator.CETValidator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = CETValidator.class)
public class CETValidatorImpl implements CETValidator {

	@Override
	public void validate(String newTypeSettings, String type)
		throws PortalException {

		validate(newTypeSettings, null, type);
	}

	@Override
	public void validate(
			String newTypeSettings, String oldTypeSettings, String type)
		throws PortalException {

		UnicodeProperties newTypeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.create(
				true
			).load(
				newTypeSettings
			).build();

		UnicodeProperties oldTypeSettingsUnicodeProperties = null;

		if (oldTypeSettings != null) {
			oldTypeSettingsUnicodeProperties = UnicodePropertiesBuilder.create(
				true
			).load(
				oldTypeSettings
			).build();
		}

		CETTypeValidator cetTypeValidator = _serviceTrackerMap.getService(type);

		if (cetTypeValidator == null) {
			throw new ClientExtensionEntryTypeException(
				"No CET type validator registered for type " + type);
		}

		cetTypeValidator.validate(
			newTypeSettingsUnicodeProperties, oldTypeSettingsUnicodeProperties);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CETTypeValidator.class, "type");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();

		_serviceTrackerMap = null;
	}

	private ServiceTrackerMap<String, CETTypeValidator> _serviceTrackerMap;

}