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

package com.liferay.portlet.display.template.internal;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portletdisplaytemplate.PortletDisplayTemplateManager;
import com.liferay.portlet.display.template.PortletDisplayTemplate;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(service = PortletDisplayTemplateManager.class)
public class PortletDisplayTemplateManagerImpl
	implements PortletDisplayTemplateManager {

	@Override
	public String renderDDMTemplate(
			long classNameId, Map<String, Object> contextObjects,
			String ddmTemplateKey, List<?> entries, long groupId,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, boolean useDefault)
		throws Exception {

		DDMTemplate ddmTemplate =
			_portletDisplayTemplate.getPortletDisplayTemplateDDMTemplate(
				groupId, classNameId, DISPLAY_STYLE_PREFIX + ddmTemplateKey,
				useDefault);

		if (ddmTemplate == null) {
			return StringPool.BLANK;
		}

		return _portletDisplayTemplate.renderDDMTemplate(
			httpServletRequest, httpServletResponse,
			ddmTemplate.getTemplateId(), entries, contextObjects);
	}

	@Reference
	private PortletDisplayTemplate _portletDisplayTemplate;

}