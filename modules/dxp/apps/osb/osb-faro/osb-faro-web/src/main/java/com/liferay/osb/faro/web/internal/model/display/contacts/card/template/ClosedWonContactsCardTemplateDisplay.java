/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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