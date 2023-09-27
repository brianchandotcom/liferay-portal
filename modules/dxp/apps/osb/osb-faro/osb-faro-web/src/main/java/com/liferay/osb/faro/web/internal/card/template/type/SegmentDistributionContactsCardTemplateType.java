/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.card.template.type;

import com.liferay.osb.faro.contacts.model.constants.ContactsCardTemplateConstants;
import com.liferay.osb.faro.web.internal.model.display.contacts.card.template.ContactsCardTemplateDisplay;
import com.liferay.osb.faro.web.internal.model.display.contacts.card.template.SegmentDistributionContactsCardTemplateDisplay;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shinn Lok
 */
@Component(service = ContactsCardTemplateType.class)
public class SegmentDistributionContactsCardTemplateType
	extends BaseContactsCardTemplateType {

	@Override
	public String getDefaultName() {
		return _DEFAULT_NAME;
	}

	@Override
	public Map<String, Object> getDefaultSettings() {
		return _defaultSettings;
	}

	@Override
	public Class<? extends ContactsCardTemplateDisplay> getDisplayClass() {
		return SegmentDistributionContactsCardTemplateDisplay.class;
	}

	@Override
	public int getType() {
		return ContactsCardTemplateConstants.TYPE_SEGMENT_DISTRIBUTION;
	}

	private static final String _DEFAULT_NAME = "Segment Distribution";

	private static final Map<String, Object> _defaultSettings =
		HashMapBuilder.<String, Object>put(
			"binSize", -1
		).put(
			"fieldMappingId", StringPool.BLANK
		).put(
			"graphType", ContactsCardTemplateConstants.SETTINGS_GRAPH_TYPE_BAR
		).put(
			"max", -1
		).put(
			"numberOfBins", 6
		).build();

}