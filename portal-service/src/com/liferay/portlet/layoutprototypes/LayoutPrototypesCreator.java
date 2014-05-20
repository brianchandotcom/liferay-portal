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

package com.liferay.portlet.layoutprototypes;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Iván Zaera
 */
public class LayoutPrototypesCreator {

	public LayoutPrototypesCreator(long companyId)
		throws PortalException, SystemException {

		_companyId = companyId;

		_defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		_layoutPrototypes = new ArrayList<LayoutPrototype>(
			LayoutPrototypeLocalServiceUtil.search(
				companyId, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null));
	}

	public LayoutPrototype addLayoutPrototype(
			String nameKey, String descriptionKey, String layoutTemplateId)
		throws PortalException, SystemException {

		String name = LanguageUtil.get(LocaleUtil.getDefault(), nameKey);
		String description = LanguageUtil.get(
			LocaleUtil.getDefault(), descriptionKey);

		for (LayoutPrototype layoutPrototype : _layoutPrototypes) {
			String curName = layoutPrototype.getName(LocaleUtil.getDefault());
			String curDescription = layoutPrototype.getDescription(
				LocaleUtil.getDefault());

			if (name.equals(curName) && description.equals(curDescription)) {
				return null;
			}
		}

		Map<Locale, String> nameMap = new HashMap<Locale, String>();
		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			nameMap.put(locale, LanguageUtil.get(locale, nameKey));
			descriptionMap.put(
				locale, LanguageUtil.get(locale, descriptionKey));
		}

		LayoutPrototype layoutPrototype =
			LayoutPrototypeLocalServiceUtil.addLayoutPrototype(
				_defaultUserId, _companyId, nameMap, descriptionMap, true,
				new ServiceContext());

		_layoutPrototypes.add(layoutPrototype);

		Layout layout = layoutPrototype.getLayout();

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(0, layoutTemplateId, false);

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		return layoutPrototype;
	}

	private long _companyId;
	private long _defaultUserId;
	private List<LayoutPrototype> _layoutPrototypes;

}