/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.info.item.provider;

import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemReference;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Eudaldo Alonso
 */
@ProviderType
public interface DisplayPageInfoItemFieldSetProvider {

	public InfoFieldSet getInfoFieldSet(
		String itemClassName, String infoItemFormVariationKey, String namespace,
		long scopeGroupId);

	public List<InfoFieldValue<Object>> getInfoFieldValues(
			InfoItemReference infoItemReference,
			String infoItemFormVariationKey, String namespace,
			ThemeDisplay themeDisplay)
		throws Exception;

}