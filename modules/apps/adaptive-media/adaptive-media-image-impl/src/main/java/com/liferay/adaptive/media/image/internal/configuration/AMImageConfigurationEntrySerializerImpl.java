/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.configuration;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntrySerializer;
import com.liferay.adaptive.media.image.internal.configuration.util.AMImageConfigurationEntryParserUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(service = AMImageConfigurationEntrySerializer.class)
public class AMImageConfigurationEntrySerializerImpl
	implements AMImageConfigurationEntrySerializer {

	@Override
	public AMImageConfigurationEntry deserialize(String s) {
		return AMImageConfigurationEntryParserUtil.parse(s);
	}

	@Override
	public String serialize(
		AMImageConfigurationEntry amImageConfigurationEntry) {

		return AMImageConfigurationEntryParserUtil.getConfigurationString(
			amImageConfigurationEntry);
	}

}