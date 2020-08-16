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

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rachael Koestartyo
 */
public class FieldManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public FieldManagementToolbarDisplayContext(
		FieldDisplayContext fieldDisplayContext,
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			fieldDisplayContext.getFieldSearch());

		_fieldDisplayContext = fieldDisplayContext;
	}

	@Override
	public String getComponentId() {
		if (StringUtil.equalsIgnoreCase(
				_fieldDisplayContext.getMVCRenderCommandName(),
				"/analytics_settings/edit_synced_contact_fields")) {

			return "contactFieldsManagementToolbar";
		}

		return "userFieldsManagementToolbar";
	}

	@Override
	public String getSearchContainerId() {
		if (StringUtil.equalsIgnoreCase(
				_fieldDisplayContext.getMVCRenderCommandName(),
				"/analytics_settings/edit_synced_contact_fields")) {

			return "selectContactFields";
		}

		return "selectUserFields";
	}

	private final FieldDisplayContext _fieldDisplayContext;

}