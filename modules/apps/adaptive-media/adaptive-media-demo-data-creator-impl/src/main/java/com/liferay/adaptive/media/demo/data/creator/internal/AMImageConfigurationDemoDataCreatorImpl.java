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

package com.liferay.adaptive.media.demo.data.creator.internal;

import com.liferay.adaptive.media.demo.data.creator.AMImageConfigurationDemoDataCreator;
import com.liferay.adaptive.media.demo.data.creator.DemoAMImageConfigurationVariant;
import com.liferay.adaptive.media.exception.AMImageConfigurationException;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(service = AMImageConfigurationDemoDataCreator.class)
public class AMImageConfigurationDemoDataCreatorImpl
	implements AMImageConfigurationDemoDataCreator {

	@Override
	public Collection<AMImageConfigurationEntry> create(long companyId)
		throws IOException {

		Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
			new ArrayList<>();

		for (DemoAMImageConfigurationVariant demoAMImageConfigurationVariant :
				DemoAMImageConfigurationVariant.values()) {

			AMImageConfigurationEntry amImageConfigurationEntry = create(
				companyId, demoAMImageConfigurationVariant);

			amImageConfigurationEntries.add(amImageConfigurationEntry);
		}

		return amImageConfigurationEntries;
	}

	@Override
	public AMImageConfigurationEntry create(
			long companyId,
			DemoAMImageConfigurationVariant demoAMImageConfigurationVariant)
		throws IOException {

		AMImageConfigurationEntry amImageConfigurationEntry = null;

		try {
			amImageConfigurationEntry =
				_amImageConfigurationHelper.addAMImageConfigurationEntry(
					companyId, demoAMImageConfigurationVariant.getName(),
					demoAMImageConfigurationVariant.getDescription(),
					demoAMImageConfigurationVariant.getUuid(),
					demoAMImageConfigurationVariant.getProperties());

			_addConfigurationUuid(
				companyId, amImageConfigurationEntry.getUUID());
		}
		catch (AMImageConfigurationException amice) {
			_log.error(
				"Unable to add image adaptive media configuration", amice);
		}

		return amImageConfigurationEntry;
	}

	@Override
	public void delete() throws IOException {
		for (Long companyId : _configurationIds.keySet()) {
			List<String> uuids = _configurationIds.get(companyId);

			for (String uuid : uuids) {
				_amImageConfigurationHelper.
					forceDeleteAMImageConfigurationEntry(companyId, uuid);

				uuids.remove(uuid);
			}
		}
	}

	private void _addConfigurationUuid(long companyId, String uuid) {
		_configurationIds.computeIfAbsent(
			companyId, k -> new CopyOnWriteArrayList<>());

		List<String> uuids = _configurationIds.get(companyId);

		uuids.add(uuid);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AMImageConfigurationDemoDataCreatorImpl.class);

	@Reference
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	private final Map<Long, List<String>> _configurationIds = new HashMap<>();

}