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

package com.liferay.portlet.display.template.web.ddm;

import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMPermissionHandler;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplate;

/**
 * @author Marcellus Tavares
 */
public class PortletDisplayTemplateDDMPermissionHandler
	implements DDMPermissionHandler {

	public static final long[] RESOURCE_CLASS_NAME_IDS = new long[] {
		PortalUtil.getClassNameId(PortletDisplayTemplate.class)
	};

	@Override
	public String getAddStructureActionId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getAddTemplateActionId() {
		return ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE;
	}

	@Override
	public long[] getResourceClassNameIds() {
		return RESOURCE_CLASS_NAME_IDS;
	}

	@Override
	public String getResourceName(long classNameId) {
		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(classNameId);

		if (templateHandler != null) {
			return templateHandler.getResourceName();
		}

		return StringPool.BLANK;
	}

}