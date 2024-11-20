/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.frontend.data.set.taglib.servlet.taglib.HeadlessDisplayTag;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.cms.site.initializer.internal.constants.CMSFDSNames;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = FragmentRenderer.class)
public class AllSectionFragmentRenderer implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "sections";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "all-section");
	}

	@Override
	public boolean isSelectable(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!FeatureFlagManagerUtil.isEnabled(
				themeDisplay.getCompanyId(), "LPD-17809")) {

			return false;
		}

		Group group = _groupLocalService.fetchGroup(
			themeDisplay.getScopeGroupId());

		if ((group == null) ||
			!Objects.equals(group.getGroupKey(), GroupConstants.CMS)) {

			return false;
		}

		return true;
	}

	@Override
	public void render(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		HeadlessDisplayTag headlessDisplayTag = new HeadlessDisplayTag();

		try {
			headlessDisplayTag.setApiURL(
				"/o/search/v1.0/search?emptySearch=true");
			headlessDisplayTag.setBulkActionDropdownItems(new ArrayList<>());
			headlessDisplayTag.setFdsActionDropdownItems(new ArrayList<>());
			headlessDisplayTag.setFormName("fm");
			headlessDisplayTag.setId(CMSFDSNames.CMS_ALL_SECTIONS);
			headlessDisplayTag.setItemsPerPage(10);
			headlessDisplayTag.setSelectedItemsKey("id");
			headlessDisplayTag.setSelectionType("multiple");
			headlessDisplayTag.setStyle("fluid");
			headlessDisplayTag.doTag(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

}