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

package com.liferay.portal.mobile.device.detection.fiftyonedegrees.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mobile.device.Capability;
import com.liferay.portal.kernel.mobile.device.KnownDevices;
import com.liferay.portal.kernel.mobile.device.NoKnownDevices;
import com.liferay.portal.kernel.mobile.device.VersionableName;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.mobile.device.detection.fiftyonedegrees.internal.constants.FiftyOneDegreesPropertyNames;

import fiftyone.mobile.detection.Dataset;
import fiftyone.mobile.detection.entities.Profile;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Greenwald
 * @author Prathima Shreenath
 */
@Component(
	immediate = true,
	service = {FiftyOneDegreesKnownDevices.class, KnownDevices.class}
)
public class FiftyOneDegreesKnownDevices implements KnownDevices {

	@Override
	public Set<VersionableName> getBrands() {
		if (!_initialized) {
			NoKnownDevices noKnownDevices = NoKnownDevices.getInstance();

			return noKnownDevices.getBrands();
		}

		return _brands;
	}

	@Override
	public Set<VersionableName> getBrowsers() {
		if (!_initialized) {
			NoKnownDevices noKnownDevices = NoKnownDevices.getInstance();

			return noKnownDevices.getBrowsers();
		}

		return _browswers;
	}

	/**
	 * @deprecated As of 1.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public Map<Capability, Set<String>> getDeviceIds() {
		return null;
	}

	@Override
	public Set<VersionableName> getOperatingSystems() {
		if (!_initialized) {
			NoKnownDevices noKnownDevices = NoKnownDevices.getInstance();

			return noKnownDevices.getOperatingSystems();
		}

		return _operatingSystems;
	}

	@Override
	public Set<String> getPointingMethods() {
		return _pointingMethods;
	}

	@Override
	public synchronized void reload() throws Exception {
		_initialized = false;

		Dataset dataset = _fiftyOneDegreesEngineProxy.getDataset();

		addProperties(
			_brands, dataset.getHardware(),
			FiftyOneDegreesPropertyNames.HARDWARE_VENDOR,
			FiftyOneDegreesPropertyNames.HARDWARE_MODEL,
			FiftyOneDegreesPropertyNames.HARDWARE_NAME);
		addProperties(
			_browswers, dataset.getBrowsers(),
			FiftyOneDegreesPropertyNames.BROWSER_NAME,
			FiftyOneDegreesPropertyNames.BROWSER_VERSION, null);
		addProperties(
			_operatingSystems, dataset.getSoftware(),
			FiftyOneDegreesPropertyNames.PLATFORM_NAME,
			FiftyOneDegreesPropertyNames.PLATFORM_VERSION, null);

		List<String> pointingMethodsList = Arrays.asList(
			FiftyOneDegreesPropertyNames.TOUCH_EVENTS);

		_pointingMethods = new TreeSet<>(pointingMethodsList);

		_initialized = true;
	}

	protected void addProperties(
			Set<VersionableName> propertiesSet,
			fiftyone.mobile.detection.entities.Component component,
			String namePropertyName, String versionPropertyName,
			String secondaryVersionPropertyName)
		throws IOException {

		try {
			for (Profile profile : component.getProfiles()) {
				String propertyValue = String.valueOf(
					profile.getValues(namePropertyName));
				String propertyVersion = String.valueOf(
					profile.getValues(versionPropertyName));

				VersionableName versionableName = new VersionableName(
					propertyValue, propertyVersion);

				if (Validator.isNotNull(secondaryVersionPropertyName)) {
					String secondaryVersion = String.valueOf(
						profile.getValues(secondaryVersionPropertyName));

					versionableName.addVersion(secondaryVersion);
				}

				propertiesSet.add(versionableName);
			}
		}
		catch (NullPointerException npe) {
			if (_log.isInfoEnabled()) {
				StringBundler sb = new StringBundler(6);

				sb.append("Data file does not support property: ");
				sb.append(namePropertyName);
				sb.append(", ");
				sb.append(versionPropertyName);
				sb.append(". Please upgrade to the premium Liferay Device ");
				sb.append("Detection module");

				_log.info(sb.toString());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FiftyOneDegreesKnownDevices.class);

	private final Set<VersionableName> _brands = new TreeSet<>();
	private final Set<VersionableName> _browswers = new TreeSet<>();

	@Reference
	private FiftyOneDegreesEngineProxy _fiftyOneDegreesEngineProxy;

	private boolean _initialized;
	private final Set<VersionableName> _operatingSystems = new TreeSet<>();
	private Set<String> _pointingMethods;

}