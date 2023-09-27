/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		catch (AMImageConfigurationException amImageConfigurationException) {
			_log.error(
				"Unable to add image adaptive media configuration",
				amImageConfigurationException);
		}

		return amImageConfigurationEntry;
	}

	@Override
	public void delete() throws IOException {
		for (Map.Entry<Long, List<String>> entry :
				_configurationUuids.entrySet()) {

			Long companyId = entry.getKey();
			List<String> uuids = entry.getValue();

			for (String uuid : uuids) {
				_amImageConfigurationHelper.
					forceDeleteAMImageConfigurationEntry(companyId, uuid);

				uuids.remove(uuid);
			}
		}
	}

	private void _addConfigurationUuid(long companyId, String uuid) {
		_configurationUuids.computeIfAbsent(
			companyId, k -> new CopyOnWriteArrayList<>());

		List<String> uuids = _configurationUuids.get(companyId);

		uuids.add(uuid);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AMImageConfigurationDemoDataCreatorImpl.class);

	@Reference
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	private final Map<Long, List<String>> _configurationUuids = new HashMap<>();

}