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
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.template.web.internal.configuration.TemplateConfiguration;

/**
 * @author Eudaldo Alonso
 * @author Lourdes Fernández Besada
 */
public class WidgetTemplatesEditDDMTemplateDisplayContext
	extends EditDDMTemplateDisplayContext {

	public WidgetTemplatesEditDDMTemplateDisplayContext(
		DDMWebConfiguration ddmWebConfiguration,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		TemplateConfiguration templateConfiguration) {

		super(liferayPortletRequest, liferayPortletResponse);

		_ddmWebConfiguration = ddmWebConfiguration;
		_templateConfiguration = templateConfiguration;

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public boolean autogenerateTemplateKey() {
		return _ddmWebConfiguration.autogenerateTemplateKey();
	}

	@Override
	public String getLanguageType() {
		if (_languageType != null) {
			return _languageType;
		}

		_languageType = ParamUtil.getString(
			httpServletRequest, "languageType", super.getLanguageType());

		return _languageType;
	}

	@Override
	public String[] getLanguageTypes() {
		DDMTemplate ddmTemplate = getDDMTemplate();

		String[] languageTypes = ArrayUtil.filter(
			_templateConfiguration.widgetTemplatesLanguageTypes(),
			languageType -> ArrayUtil.contains(
				new String[] {
					TemplateConstants.LANG_TYPE_FTL,
					TemplateConstants.LANG_TYPE_VM
				},
				languageType));

		if ((ddmTemplate != null) &&
			!ArrayUtil.contains(languageTypes, ddmTemplate.getLanguage())) {

			languageTypes = ArrayUtil.append(
				languageTypes, ddmTemplate.getLanguage());
		}

		return languageTypes;
	}

	@Override
	public String getTemplateTypeLabel() {
		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(getClassNameId());

		return templateHandler.getName(_themeDisplay.getLocale());
	}

	@Override
	protected String getDefaultScript() {
		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(getClassNameId());

		if (templateHandler != null) {
			return templateHandler.getTemplatesHelpContent(getLanguageType());
		}

		return "<#-- Empty script -->";
	}

	private final DDMWebConfiguration _ddmWebConfiguration;
	private String _languageType;
	private final TemplateConfiguration _templateConfiguration;
	private final ThemeDisplay _themeDisplay;

}