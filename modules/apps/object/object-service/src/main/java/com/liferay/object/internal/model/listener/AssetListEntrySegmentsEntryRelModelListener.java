/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener;

import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(service = ModelListener.class)
public class AssetListEntrySegmentsEntryRelModelListener
	extends BaseModelListener<AssetListEntrySegmentsEntryRel> {

	@Override
	public void onBeforeCreate(
			AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel)
		throws ModelListenerException {

		if (!ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		String typeSettings = updateObjectDefinitionReferences(
			assetListEntrySegmentsEntryRel.getTypeSettings());

		if (StringUtil.equals(
				typeSettings,
				assetListEntrySegmentsEntryRel.getTypeSettings())) {

			return;
		}

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.load(
			typeSettings
		).build();

		String anyAssetTypeClassName = unicodeProperties.getProperty(
			"anyAssetTypeClassName");

		if (Validator.isNotNull(anyAssetTypeClassName)) {
			unicodeProperties.setProperty(
				"anyAssetType",
				String.valueOf(
					_classNameLocalService.getClassNameId(
						anyAssetTypeClassName)));
		}

		unicodeProperties.setProperty(
			"classNameIds",
			StringUtil.merge(
				TransformUtil.transformToLongArray(
					Arrays.asList(
						StringUtil.split(
							unicodeProperties.getProperty("classNames"))),
					className -> _classNameLocalService.getClassNameId(
						className))));

		assetListEntrySegmentsEntryRel.setTypeSettings(
			unicodeProperties.toString());
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

}