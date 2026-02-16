/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.util;

import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceRegistryUtil;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Mikel Lorza
 */
public class LayoutPageTemplateEntryUtil {

	public static long getClassTypeId(
		long classNameId, String classTypeKey, long groupId) {

		if (Validator.isNull(classTypeKey)) {
			return -1;
		}

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			InfoItemServiceRegistryUtil.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				PortalUtil.getClassName(classNameId));

		if (infoItemFormVariationsProvider == null) {
			return -2;
		}

		InfoItemFormVariation infoItemFormVariation =
			infoItemFormVariationsProvider.
				getInfoItemFormVariationByExternalReferenceCode(
					classTypeKey, groupId);

		if (infoItemFormVariation == null) {
			return -2;
		}

		return GetterUtil.getLong(infoItemFormVariation.getKey());
	}

	public static String getClassTypeKey(
		long classNameId, long classTypeId, long groupId) {

		if (classTypeId < 0) {
			return null;
		}

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			InfoItemServiceRegistryUtil.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				PortalUtil.getClassName(classNameId));

		if (infoItemFormVariationsProvider == null) {
			return null;
		}

		InfoItemFormVariation infoItemFormVariation =
			infoItemFormVariationsProvider.getInfoItemFormVariation(
				groupId, String.valueOf(classTypeId));

		if (infoItemFormVariation == null) {
			return null;
		}

		return infoItemFormVariation.getExternalReferenceCode();
	}

}