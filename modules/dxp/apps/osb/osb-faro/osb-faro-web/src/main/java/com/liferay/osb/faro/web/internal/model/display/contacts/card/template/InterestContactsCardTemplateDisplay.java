/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
			faroProject, faroEntityDisplay.getId(), null, 1, _max,
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