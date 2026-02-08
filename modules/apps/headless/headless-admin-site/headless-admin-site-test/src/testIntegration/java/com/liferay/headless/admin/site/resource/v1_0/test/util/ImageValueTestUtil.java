/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.headless.admin.site.client.dto.v1_0.DirectFragmentImageValue;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentImageValue;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentMappedValueItemContextReference;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentMappedValueItemReference;
import com.liferay.headless.admin.site.client.dto.v1_0.ImageValue;
import com.liferay.headless.admin.site.client.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.client.dto.v1_0.ItemImageValue;
import com.liferay.headless.admin.site.client.dto.v1_0.MappedFragmentImageValue;
import com.liferay.headless.admin.site.client.dto.v1_0.URLImageValue;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

/**
 * @author Lourdes Fernández Besada
 */
public class ImageValueTestUtil {

	public static FragmentImageValue getDirectFragmentImageValue(
		ItemExternalReference itemExternalReference, String url) {

		DirectFragmentImageValue directFragmentImageValue =
			new DirectFragmentImageValue();

		directFragmentImageValue.setType(() -> FragmentImageValue.Type.DIRECT);
		directFragmentImageValue.setValue_i18n(
			HashMapBuilder.put(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.getDefault()),
				() -> {
					if (itemExternalReference == null) {
						URLImageValue urlImageValue = new URLImageValue();

						urlImageValue.setType(() -> ImageValue.Type.URL);
						urlImageValue.setUrl(() -> url);

						return urlImageValue;
					}

					ItemImageValue itemImageValue = new ItemImageValue();

					itemImageValue.setItemExternalReference(
						() -> itemExternalReference);
					itemImageValue.setType(() -> ImageValue.Type.ITEM);

					return itemImageValue;
				}
			).build());

		return directFragmentImageValue;
	}

	public static FragmentImageValue getMappedFragmentImageValue(
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		String fieldKey,
		FragmentMappedValueItemReference.Type
			fragmentMappedValueItemReferenceType) {

		MappedFragmentImageValue mappedFragmentImageValue =
			new MappedFragmentImageValue();

		mappedFragmentImageValue.setFragmentMappedValue(
			() -> FragmentMappedValueTestUtil.getFragmentMappedValue(
				contextSource, fieldKey, fragmentMappedValueItemReferenceType));
		mappedFragmentImageValue.setType(() -> FragmentImageValue.Type.MAPPED);

		return mappedFragmentImageValue;
	}

	public static FragmentImageValue getMappedFragmentImageValue(
		String className, String externalReferenceCode, String fieldKey,
		String scopeExternalReferenceCode) {

		MappedFragmentImageValue mappedFragmentImageValue =
			new MappedFragmentImageValue();

		mappedFragmentImageValue.setFragmentMappedValue(
			() -> FragmentMappedValueTestUtil.getFragmentMappedValue(
				className, externalReferenceCode, fieldKey,
				scopeExternalReferenceCode));
		mappedFragmentImageValue.setType(() -> FragmentImageValue.Type.MAPPED);

		return mappedFragmentImageValue;
	}

}