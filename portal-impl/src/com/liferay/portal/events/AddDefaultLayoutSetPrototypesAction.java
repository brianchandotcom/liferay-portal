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

package com.liferay.portal.events;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Sergio González
 */
public class AddDefaultLayoutSetPrototypesAction
	extends BaseDefaultLayoutPrototypesAction {

	@Override
	public void run(String[] ids) throws ActionException {
		try {
			doRun(GetterUtil.getLong(ids[0]));
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected LayoutSet addLayoutSetPrototype(
			long companyId, long defaultUserId, String nameKey,
			String descriptionKey, List<LayoutSetPrototype> layoutSetPrototypes)
		throws Exception {

		String name = LanguageUtil.get(LocaleUtil.getDefault(), nameKey);
		String description = LanguageUtil.get(
			LocaleUtil.getDefault(), descriptionKey);

		for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
			String curName = layoutSetPrototype.getName(
				LocaleUtil.getDefault());
			String curDescription = layoutSetPrototype.getDescription(
				LocaleUtil.getDefault());

			if (name.equals(curName) && description.equals(curDescription)) {
				return null;
			}
		}

		Map<Locale, String> nameMap = new HashMap<>();
		Map<Locale, String> descriptionMap = new HashMap<>();

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			nameMap.put(locale, LanguageUtil.get(locale, nameKey));
			descriptionMap.put(
				locale, LanguageUtil.get(locale, descriptionKey));
		}

		LayoutSetPrototype layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.addLayoutSetPrototype(
				defaultUserId, companyId, nameMap, descriptionMap, true, true,
				new ServiceContext());

		LayoutSet layoutSet = layoutSetPrototype.getLayoutSet();

		ServiceContext serviceContext = new ServiceContext();

		LayoutLocalServiceUtil.deleteLayouts(
			layoutSet.getGroupId(), serviceContext);

		return layoutSetPrototype.getLayoutSet();
	}

	protected void addPrivateSite(
			long companyId, long defaultUserId,
			List<LayoutSetPrototype> layoutSetPrototypes)
		throws Exception {

		LayoutSet layoutSet = addLayoutSetPrototype(
			companyId, defaultUserId,
			"layout-set-prototype-intranet-site-title",
			"layout-set-prototype-intranet-site-description",
			layoutSetPrototypes);

		if (layoutSet == null) {
			return;
		}

		// Home layout

		Layout layout = addLayout(layoutSet, "home", "/home", "2_columns_i");

		// Documents layout

		layout = addLayout(
			layoutSet, "documents-and-media", "/documents", "1_column");

		String portletId = addPortletId(
			layout, PortletKeys.DOCUMENT_LIBRARY, "column-1");

		Map<String, String> preferences = new HashMap<>();

		preferences.put("portletSetupShowBorders", Boolean.FALSE.toString());

		updatePortletSetup(layout, portletId, preferences);
	}

	protected void addPublicSite(
			long companyId, long defaultUserId,
			List<LayoutSetPrototype> layoutSetPrototypes)
		throws Exception {

		LayoutSet layoutSet = addLayoutSetPrototype(
			companyId, defaultUserId,
			"layout-set-prototype-community-site-title",
			"layout-set-prototype-community-site-description",
			layoutSetPrototypes);

		if (layoutSet == null) {
			return;
		}

		// Home layout

		Layout layout = addLayout(layoutSet, "home", "/home", "2_columns_iii");

		addPortletId(layout, PortletKeys.MESSAGE_BOARDS, "column-1");

		addPortletId(layout, PortletKeys.USER_STATISTICS, "column-2");

		// Wiki layout

		addLayout(layoutSet, "wiki", "/wiki", "2_columns_iii");
	}

	protected void doRun(long companyId) throws Exception {
		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		List<LayoutSetPrototype> layoutSetPrototypes =
			LayoutSetPrototypeLocalServiceUtil.search(
				companyId, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		addPublicSite(companyId, defaultUserId, layoutSetPrototypes);
		addPrivateSite(companyId, defaultUserId, layoutSetPrototypes);
	}

}