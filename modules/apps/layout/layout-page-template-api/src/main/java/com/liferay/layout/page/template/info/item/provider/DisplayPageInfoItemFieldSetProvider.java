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

package com.liferay.layout.page.template.info.item.provider;

import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Eudaldo Alonso
 */
@ProviderType
public interface DisplayPageInfoItemFieldSetProvider {

	public InfoFieldSet getInfoFieldSet(
		String itemClassName, String infoItemFormVariationKey,
		long scopeGroupId);

	public List<InfoFieldValue<Object>> getInfoFieldValues(
			String itemClassName, long itemClassPK,
			String infoItemFormVariationKey, ThemeDisplay themeDisplay)
		throws Exception;

}