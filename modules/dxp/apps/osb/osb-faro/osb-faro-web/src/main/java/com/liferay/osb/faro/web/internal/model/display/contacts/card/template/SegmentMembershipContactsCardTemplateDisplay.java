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
import com.liferay.osb.faro.engine.client.model.IndividualSegmentMembershipChangeAggregation;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.web.internal.model.display.main.FaroEntityDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;

/**
 * @author Matthew Kong
 */
public class SegmentMembershipContactsCardTemplateDisplay
	extends ContactsCardTemplateDisplay {

	public SegmentMembershipContactsCardTemplateDisplay() {
	}

	public SegmentMembershipContactsCardTemplateDisplay(
			String weDeployKey, ContactsCardTemplate contactsCardTemplate,
			int size, ContactsEngineClient contactsEngineClient)
		throws Exception {

		super(contactsCardTemplate, size, _SUPPORTED_SIZES);

		_interval = MapUtil.getString(settings, "interval");
		_max = MapUtil.getInteger(settings, "max");
	}

	@Override
	public Map<String, Object> getContactsCardData(
		FaroProject faroProject, FaroEntityDisplay faroEntityDisplay,
		ContactsEngineClient contactsEngineClient) {

		Results<IndividualSegmentMembershipChangeAggregation> results =
			contactsEngineClient.
				getIndividualSegmentMembershipChangeAggregations(
					faroProject, faroEntityDisplay.getId(), _interval, _max);

		return HashMapBuilder.<String, Object>put(
			"individualSegmentHistory", results.getItems()
		).build();
	}

	private static final int[] _SUPPORTED_SIZES = {1};

	private String _interval;
	private int _max;

}