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

package com.liferay.analytics.settings.web.internal.display.context;

import com.liferay.analytics.settings.web.internal.model.Field;
import com.liferay.analytics.settings.web.internal.search.FieldSearch;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TreeMapBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Rachael Koestartyo
 */
public class FieldDisplayContext {

	public FieldDisplayContext(
		String mvcRenderCommandName, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_mvcRenderCommandName = mvcRenderCommandName;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public FieldSearch getFieldSearch() {
		FieldSearch fieldSearch = new FieldSearch(
			_renderRequest, getPortletURL());

		List<Field> fields = new ArrayList<>();

		if (StringUtil.equalsIgnoreCase(
				_mvcRenderCommandName,
				"/analytics_settings/edit_synced_user_fields")) {

			for (String fieldName : _requiredUserFieldNames) {
				fields.add(
					new Field(
						"Default Field", _userFieldNames.get(fieldName),
						fieldName));
			}

			for (Map.Entry<String, String> entry : _userFieldNames.entrySet()) {
				if (_requiredUserFieldNames.contains(entry.getKey())) {
					continue;
				}

				fields.add(
					new Field(
						"Default Field", entry.getValue(), entry.getKey()));
			}

			fieldSearch.setTotal(
				_userFieldNames.size() - _requiredUserFieldNames.size());
		}

		fieldSearch.setResults(fields);

		return fieldSearch;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", _mvcRenderCommandName);

		return portletURL;
	}

	private static final List<String> _requiredUserFieldNames = Arrays.asList(
		"createDate", "emailAddress", "modifiedDate", "userId", "uuid");
	private static final Map<String, String> _userFieldNames =
		TreeMapBuilder.put(
			"agreedToTermsOfUse", "Boolean"
		).put(
			"comments", "String"
		).put(
			"companyId", "Long"
		).put(
			"contactId", "Long"
		).put(
			"createDate", "Date"
		).put(
			"defaultUser", "Boolean"
		).put(
			"emailAddress", "String"
		).put(
			"emailAddressVerified", "Boolean"
		).put(
			"externalReferenceCode", "String"
		).put(
			"facebookId", "Long"
		).put(
			"firstName", "String"
		).put(
			"googleUserId", "String"
		).put(
			"greeting", "String"
		).put(
			"jobTitle", "String"
		).put(
			"languageId", "String"
		).put(
			"lastName", "String"
		).put(
			"ldapServerId", "Long"
		).put(
			"middleName", "String"
		).put(
			"modifiedDate", "Date"
		).put(
			"openId", "String"
		).put(
			"portraitId", "Long"
		).put(
			"screenName", "String"
		).put(
			"status", "Integer"
		).put(
			"timeZoneId", "String"
		).put(
			"userId", "Long"
		).put(
			"uuid", "String"
		).build();

	private final String _mvcRenderCommandName;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}