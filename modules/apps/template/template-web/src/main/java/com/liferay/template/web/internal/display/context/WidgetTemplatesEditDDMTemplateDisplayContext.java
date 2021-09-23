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

import com.liferay.dynamic.data.mapping.configuration.DDMWebConfiguration;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

/**
 * @author Eudaldo Alonso
 * @author Lourdes Fernández Besada
 */
public class WidgetTemplatesEditDDMTemplateDisplayContext
	extends EditDDMTemplateDisplayContext {

	public WidgetTemplatesEditDDMTemplateDisplayContext(
		DDMWebConfiguration ddmWebConfiguration,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(liferayPortletRequest, liferayPortletResponse);

		_ddmWebConfiguration = ddmWebConfiguration;

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public boolean autogenerateTemplateKey() {
		return _ddmWebConfiguration.autogenerateTemplateKey();
	}

	@Override
	public String getTemplateTypeLabel() {
		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(getClassNameId());

		return templateHandler.getName(_themeDisplay.getLocale());
	}

	public String getUpdateDDMTemplateURL() {
		return PortletURLBuilder.createActionURL(
			liferayPortletResponse
		).setActionName(
			"/template/update_ddm_template"
		).setTabs1(
			getTabs1()
		).buildString();
	}

	@Override
	protected String getDefaultScript() {
		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(getClassNameId());

		if (templateHandler != null) {
			return templateHandler.getTemplatesHelpContent(
				TemplateConstants.LANG_TYPE_FTL);
		}

		return "<#-- Empty script -->";
	}

	private final DDMWebConfiguration _ddmWebConfiguration;
	private final ThemeDisplay _themeDisplay;

}