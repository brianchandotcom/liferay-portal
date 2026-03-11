/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceRegistryUtil;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Mikel Lorza
 */
public class SubtypeUtil {

	public static ItemExternalReference getSubtypeItemExternalReference(
		String className, long classTypeId, String classTypeKey, long groupId) {

		if ((classTypeId < 0) && Validator.isNull(classTypeKey)) {
			return null;
		}

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			InfoItemServiceRegistryUtil.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class, className);

		if (infoItemFormVariationsProvider == null) {
			if (Validator.isNull(classTypeKey)) {
				return null;
			}

			return new ItemExternalReference() {
				{
					setExternalReferenceCode(() -> classTypeKey);
				}
			};
		}

		if (Validator.isNotNull(classTypeKey)) {
			return new ItemExternalReference() {
				{
					setClassName(
						infoItemFormVariationsProvider::
							getInfoItemFormVariationClassName);
					setExternalReferenceCode(() -> classTypeKey);
				}
			};
		}

		InfoItemFormVariation infoItemFormVariation =
			infoItemFormVariationsProvider.getInfoItemFormVariation(
				groupId, String.valueOf(classTypeId));

		if (infoItemFormVariation == null) {
			return null;
		}

		return new ItemExternalReference() {
			{
				setClassName(
					infoItemFormVariationsProvider::
						getInfoItemFormVariationClassName);
				setExternalReferenceCode(
					infoItemFormVariation::getExternalReferenceCode);
			}
		};
	}

}