/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts.card.template;

import com.fasterxml.jackson.core.type.TypeReference;

import com.liferay.osb.faro.contacts.model.ContactsCardTemplate;
import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.web.internal.util.JSONUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.List;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class TouchpointAttributionContactsCardTemplateDisplay
	extends ContactsCardTemplateDisplay {

	public TouchpointAttributionContactsCardTemplateDisplay() {
	}

	public TouchpointAttributionContactsCardTemplateDisplay(
			FaroProject faroProject, ContactsCardTemplate contactsCardTemplate,
			int size, ContactsEngineClient contactsEngineClient)
		throws Exception {

		super(contactsCardTemplate, size, _SUPPORTED_SIZES);

		_filters = JSONUtil.readValue(
			MapUtil.getString(settings, "filters"),
			new TypeReference<List<String>>() {
			});
		_stage = MapUtil.getInteger(settings, "stage");
		_touchpointsEntryIds = JSONUtil.readValue(
			MapUtil.getString(settings, "touchpointsEntryIds"),
			new TypeReference<List<String>>() {
			});
	}

	private static final int[] _SUPPORTED_SIZES = {4};

	private List<String> _filters;
	private int _stage;
	private List<String> _touchpointsEntryIds;

}