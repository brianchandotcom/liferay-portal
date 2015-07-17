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

package com.liferay.portal.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Sergio González
 */
public class DefaultLayoutSetPrototypesUtil {

	public static LayoutSet addLayoutSetPrototype(
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

		for (Locale locale : LanguageUtil.getAvailableLocales()) {
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
			layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
			serviceContext);

		return layoutSetPrototype.getLayoutSet();
	}

}