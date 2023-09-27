/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Sergio González
 */
public class DefaultLayoutSetPrototypesUtil {

	public static LayoutSet addLayoutSetPrototype(
			long companyId, long defaultUserId, String nameKey,
			String descriptionKey, List<LayoutSetPrototype> layoutSetPrototypes,
			ClassLoader classLoader)
		throws Exception {

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", LocaleUtil.getDefault(), classLoader);

		String name = LanguageUtil.get(resourceBundle, nameKey);
		String description = LanguageUtil.get(resourceBundle, descriptionKey);

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
			resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", locale, classLoader);

			nameMap.put(locale, LanguageUtil.get(resourceBundle, nameKey));
			descriptionMap.put(
				locale, LanguageUtil.get(resourceBundle, descriptionKey));
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAttribute("addDefaultLayout", Boolean.FALSE);

		LayoutSetPrototype layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.addLayoutSetPrototype(
				defaultUserId, companyId, nameMap, descriptionMap, true, true,
				serviceContext);

		return layoutSetPrototype.getLayoutSet();
	}

}