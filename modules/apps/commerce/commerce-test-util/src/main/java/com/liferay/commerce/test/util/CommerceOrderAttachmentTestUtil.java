/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.test.util;

import com.liferay.batch.engine.test.util.BatchEngineTestUtil;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

/**
 * @author Stefano Motta
 */
public class CommerceOrderAttachmentTestUtil {

	public static void initialize(Class<?> clazz) throws Exception {
		ListTypeDefinition listTypeDefinition =
			ListTypeDefinitionLocalServiceUtil.
				fetchListTypeDefinitionByExternalReferenceCode(
					"L_COMMERCE_ORDER_ATTACHMENT_TYPES",
					TestPropsValues.getCompanyId());

		if (listTypeDefinition != null) {
			return;
		}

		BatchEngineTestUtil.processBatchEngineUnits(
			_BUNDLE_SYMBOLIC_NAME, clazz,
			new String[] {
				".com.liferay.commerce.internal.batch.01.list.type.definition"
			});
	}

	private static final String _BUNDLE_SYMBOLIC_NAME =
		"com.liferay.commerce.service";

}