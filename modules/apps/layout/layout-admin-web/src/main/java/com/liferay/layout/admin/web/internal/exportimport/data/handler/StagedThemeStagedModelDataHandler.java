/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = StagedModelDataHandler.class)
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

	@Reference
	private ThemeLocalService _themeLocalService;

}