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

package com.liferay.template.web.internal.display.context;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portlet.display.template.PortletDisplayTemplate;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Lourdes Fernández Besada
 */
public class WidgetTemplatesTemplateDisplayContext
	extends BaseTemplateDisplayContext {

	public WidgetTemplatesTemplateDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(liferayPortletRequest, liferayPortletResponse);

		_portletDisplayTemplate =
			(PortletDisplayTemplate)liferayPortletRequest.getAttribute(
				PortletDisplayTemplate.class.getName());
	}

	@Override
	public long[] getClassNameIds() {
		if (_classNameIds != null) {
			return _classNameIds;
		}

		List<TemplateHandler> templateHandlers =
			_portletDisplayTemplate.getPortletDisplayTemplateHandlers();

		Stream<TemplateHandler> templateHandlersStream =
			templateHandlers.stream();

		_classNameIds = templateHandlersStream.mapToLong(
			templateHandler -> PortalUtil.getClassNameId(
				templateHandler.getClassName())
		).toArray();

		return _classNameIds;
	}

	@Override
	public long getResourceClassNameId() {
		if (_resourceClassNameId != null) {
			return _resourceClassNameId;
		}

		_resourceClassNameId = PortalUtil.getClassNameId(
			PortletDisplayTemplate.class);

		return _resourceClassNameId;
	}

	@Override
	public String getTemplateTypeLabel(long classNameId) {
		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(classNameId);

		return templateHandler.getName(themeDisplay.getLocale());
	}

	private long[] _classNameIds;
	private final PortletDisplayTemplate _portletDisplayTemplate;
	private Long _resourceClassNameId;

}