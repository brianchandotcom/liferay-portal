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

package com.liferay.document.library.internal.versioning;

import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.versioning.VersioningStrategy;
import com.liferay.document.library.versioning.VersioningPolicy;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;
import java.util.Optional;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.DLConfiguration",
	service = VersioningStrategy.class
)
public class VersioningPolicyVersioningStrategy implements VersioningStrategy {

	@Override
	public DLVersionNumberIncrease computeDLVersionNumberIncrease(
		DLFileVersion previousDLFileVersion, DLFileVersion nextDLFileVersion) {

		for (VersioningPolicy versioningPolicy : _serviceTrackerList) {
			Optional<DLVersionNumberIncrease> dlVersionNumberIncreaseOptional =
				versioningPolicy.computeDLVersionNumberIncrease(
					previousDLFileVersion, nextDLFileVersion);

			if (dlVersionNumberIncreaseOptional.isPresent()) {
				return dlVersionNumberIncreaseOptional.get();
			}
		}

		return DLVersionNumberIncrease.NONE;
	}

	@Override
	public boolean isOverridable() {
		return _dlConfiguration.versioningStrategyOverridable();
	}

	@Activate
	@Modified
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_dlConfiguration = ConfigurableUtil.createConfigurable(
			DLConfiguration.class, properties);
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, VersioningPolicy.class);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	private volatile DLConfiguration _dlConfiguration;
	private ServiceTrackerList<VersioningPolicy, VersioningPolicy>
		_serviceTrackerList;

}