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

package com.liferay.notification.web.internal.portlet.action;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;
import java.util.Set;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Mateus Santana
 */
public abstract class BaseNotificationTemplateTermsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		JSONArray jsonArray = jsonFactory.createJSONArray();

		user = userLocalService.getUser(PrincipalThreadLocal.getUserId());

		for (Map.Entry<String, String> entry : getTermNamesEntries()) {
			jsonArray.put(
				JSONUtil.put(
					"termLabel", language.get(user.getLocale(), entry.getKey())
				).put(
					"termName", entry.getValue()
				));
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonArray);
	}

	protected abstract Set<Map.Entry<String, String>> getTermNamesEntries();

	protected final Map<String, String> authorObjectFieldNames =
		HashMapBuilder.put(
			"author-email-address", "AUTHOR_EMAIL_ADDRESS"
		).put(
			"author-first-name", "AUTHOR_FIRST_NAME"
		).put(
			"author-id", "AUTHOR_ID"
		).put(
			"author-last-name", "AUTHOR_LAST_NAME"
		).put(
			"author-middle-name", "AUTHOR_MIDDLE_NAME"
		).put(
			"author-prefix", "AUTHOR_PREFIX"
		).put(
			"author-suffix", "AUTHOR_SUFFIX"
		).build();

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected Language language;

	protected User user;

	@Reference
	protected UserLocalService userLocalService;

}