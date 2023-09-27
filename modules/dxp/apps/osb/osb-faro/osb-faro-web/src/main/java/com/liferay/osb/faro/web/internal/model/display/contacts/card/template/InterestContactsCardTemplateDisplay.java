/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts.card.template;

import com.liferay.osb.faro.contacts.model.ContactsCardTemplate;
import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.constants.TimeConstants;
import com.liferay.osb.faro.engine.client.util.OrderByField;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.web.internal.model.display.FaroResultsDisplay;
import com.liferay.osb.faro.web.internal.model.display.main.FaroEntityDisplay;
import com.liferay.osb.faro.web.internal.util.InterestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Collections;
import java.util.Map;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class InterestContactsCardTemplateDisplay
	extends ContactsCardTemplateDisplay {

	public InterestContactsCardTemplateDisplay() {
	}

	public InterestContactsCardTemplateDisplay(
			FaroProject faroProject, ContactsCardTemplate contactsCardTemplate,
			int size, ContactsEngineClient contactsEngineClient)
		throws Exception {

		super(contactsCardTemplate, size, _SUPPORTED_SIZES);

		_size = MapUtil.getInteger(settings, "size");

		_filterType = MapUtil.getString(settings, "filterType");
		_interval = MapUtil.getString(
			settings, "interval", TimeConstants.INTERVAL_MONTH);
		_max = MapUtil.getInteger(settings, "max");
	}

	@Override
	public Map<String, Object> getContactsCardData(
		FaroProject faroProject, FaroEntityDisplay faroEntityDisplay,
		ContactsEngineClient contactsEngineClient) {

		FaroResultsDisplay results = InterestUtil.getInterests(
			faroProject, null, faroEntityDisplay.getId(), null, 1, _max,
			Collections.singletonList(new OrderByField("score", "desc", true)),
			contactsEngineClient);

		return HashMapBuilder.<String, Object>put(
			"interests", results.getItems()
		).build();
	}

	private static final int[] _SUPPORTED_SIZES = {1};

	private String _filterType;
	private String _interval;
	private int _max;
	private int _size;

}