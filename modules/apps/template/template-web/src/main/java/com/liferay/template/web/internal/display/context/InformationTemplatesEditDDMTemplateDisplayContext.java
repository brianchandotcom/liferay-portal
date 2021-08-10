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

import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Optional;

/**
 * @author Eudaldo Alonso
 * @author Lourdes Fernández Besada
 */
public class InformationTemplatesEditDDMTemplateDisplayContext
	extends EditDDMTemplateDisplayContext {

	public InformationTemplatesEditDDMTemplateDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(liferayPortletRequest, liferayPortletResponse);

		_infoItemServiceTracker =
			(InfoItemServiceTracker)liferayPortletRequest.getAttribute(
				InfoItemServiceTracker.class.getName());
		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String[] getLanguageTypes() {
		return new String[] {TemplateConstants.LANG_TYPE_FTL};
	}

	@Override
	public String getTemplateSubtypeLabel() {
		return Optional.ofNullable(
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				PortalUtil.getClassName(getClassNameId()))
		).map(
			infoItemFormVariationsProvider ->
				infoItemFormVariationsProvider.getInfoItemFormVariation(
					_themeDisplay.getScopeGroupId(),
					String.valueOf(getClassPK()))
		).map(
			infoItemFormVariation -> infoItemFormVariation.getLabel(
				_themeDisplay.getLocale())
		).orElse(
			StringPool.BLANK
		);
	}

	@Override
	public String getTemplateTypeLabel() {
		return Optional.ofNullable(
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormProvider.class,
				PortalUtil.getClassName(getClassNameId()))
		).map(
			InfoItemFormProvider::getInfoForm
		).map(
			infoForm -> infoForm.getLabel(_themeDisplay.getLocale())
		).orElse(
			StringPool.BLANK
		);
	}

	@Override
	protected long getTemplateHandlerClassNameId() {
		return PortalUtil.getClassNameId(InfoItemFormProvider.class);
	}

	private final InfoItemServiceTracker _infoItemServiceTracker;
	private final ThemeDisplay _themeDisplay;

}