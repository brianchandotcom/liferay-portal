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

package com.liferay.layout.admin.web.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.adapter.StagedTheme;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class StagedThemeStagedModelDataHandler
	extends BaseStagedModelDataHandler<StagedTheme> {

	public static final String[] CLASS_NAMES = {StagedTheme.class.getName()};

	@Override
	public void deleteStagedModel(StagedTheme stagedTheme) {
	}

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData) {
	}

	@Override
	public List<StagedTheme> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return Collections.emptyList();
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(StagedTheme stagedTheme) {
		return stagedTheme.getThemeId();
	}

	@Override
	public void importMissingReference(
			PortletDataContext portletDataContext, Element referenceElement)
		throws PortletDataException {

		boolean importThemeSettings = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.THEME_REFERENCE);

		if (!importThemeSettings) {
			return;
		}

		Map<String, String> themeIds =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				StagedTheme.class);

		String classPK = referenceElement.attributeValue("class-pk");

		themeIds.put(classPK, classPK);
	}

	@Override
	public boolean validateReference(
		PortletDataContext portletDataContext, Element referenceElement) {

		boolean importThemeSettings = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.THEME_REFERENCE);

		if (!importThemeSettings) {
			return true;
		}

		String classPK = referenceElement.attributeValue("class-pk");

		List<Theme> themes = _themeLocalService.getThemes(
			portletDataContext.getCompanyId());

		for (Theme theme : themes) {
			String themeId = theme.getThemeId();

			if (themeId.equals(classPK)) {
				return true;
			}
		}

		return false;
	}

	@Override
	protected void doExportStagedModel(
		PortletDataContext portletDataContext, StagedTheme stagedTheme) {
	}

	@Override
	protected void doImportStagedModel(
		PortletDataContext portletDataContext, StagedTheme stagedTheme) {
	}

	@Reference(unbind = "-")
	protected void setThemeLocalService(ThemeLocalService themeLocalService) {
		_themeLocalService = themeLocalService;
	}

	private ThemeLocalService _themeLocalService;

}