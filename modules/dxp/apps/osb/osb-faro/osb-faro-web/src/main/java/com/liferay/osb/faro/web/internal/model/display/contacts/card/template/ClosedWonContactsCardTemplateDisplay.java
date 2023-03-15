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
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.web.internal.util.JSONUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Time;

import java.util.Date;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class ClosedWonContactsCardTemplateDisplay
	extends ContactsCardTemplateDisplay {

	public ClosedWonContactsCardTemplateDisplay() {
	}

	public ClosedWonContactsCardTemplateDisplay(
			FaroProject faroProject, ContactsCardTemplate contactsCardTemplate,
			int size, ContactsEngineClient contactsEngineClient)
		throws Exception {

		super(contactsCardTemplate, size, _SUPPORTED_SIZES);

		String endDateString = MapUtil.getString(
			settings, "endDate", String.valueOf(System.currentTimeMillis()));

		_endDate = JSONUtil.readValue(endDateString, Date.class);

		String startDateString = MapUtil.getString(
			settings, "startDate",
			String.valueOf(System.currentTimeMillis() - Time.YEAR));

		_startDate = JSONUtil.readValue(startDateString, Date.class);
	}

	private static final int[] _SUPPORTED_SIZES = {1};

	private Date _endDate;
	private Date _startDate;

}