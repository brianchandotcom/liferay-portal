/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.internal.model.listener;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRelTable;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joshua Cords
 */
@Component(service = ModelListener.class)
public class ObjectDefinitionModelListener
	extends BaseModelListener<ObjectDefinition> {

	@Override
	public void onBeforeRemove(ObjectDefinition objectDefinition)
		throws ModelListenerException {

		long classNameId = _portal.getClassNameId(
			objectDefinition.getClassName());

		if (classNameId <= 0) {
			return;
		}

		try {
			List<AssetListEntrySegmentsEntryRel>
				assetListEntrySegmentsEntryRels =
					_assetListEntrySegmentsEntryRelLocalService.dslQuery(
						DSLQueryFactoryUtil.select(
							AssetListEntrySegmentsEntryRelTable.INSTANCE
						).from(
							AssetListEntrySegmentsEntryRelTable.INSTANCE
						).where(
							AssetListEntrySegmentsEntryRelTable.INSTANCE.
								companyId.eq(
									objectDefinition.getCompanyId()
								).and(
									AssetListEntrySegmentsEntryRelTable.
										INSTANCE.typeSettings.like(
											"%" + classNameId + "%")
								)
						));

			if (ListUtil.isEmpty(assetListEntrySegmentsEntryRels)) {
				return;
			}

			for (AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel :
					assetListEntrySegmentsEntryRels) {

				_removeClassNameIdFromTypeSettings(
					assetListEntrySegmentsEntryRel, classNameId);
			}
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	private void _removeClassNameIdFromTypeSettings(
			AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel,
			long classNameId)
		throws PortalException {

		String typeSettings = assetListEntrySegmentsEntryRel.getTypeSettings();

		if (Validator.isNull(typeSettings)) {
			return;
		}

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.load(
			typeSettings
		).build();

		String classNameIdsValue = unicodeProperties.getProperty(
			"classNameIds");

		long[] classNameIdValues = GetterUtil.getLongValues(
			StringUtil.split(classNameIdsValue));

		if (ArrayUtil.isEmpty(classNameIdValues)) {
			return;
		}

		List<Long> classNameIds = new LinkedList<>();

		for (long curClassNameId : classNameIdValues) {
			classNameIds.add(curClassNameId);
		}

		if (!classNameIds.removeIf(
				curClassNameId -> curClassNameId == classNameId)) {

			return;
		}

		String anyAssetTypeValue = GetterUtil.getString(
			unicodeProperties.getProperty("anyAssetType"));

		if (!classNameIds.isEmpty()) {
			if ((classNameIds.size() == 1) &&
				StringUtil.equalsIgnoreCase(
					anyAssetTypeValue, Boolean.FALSE.toString())) {

				unicodeProperties.setProperty(
					"anyAssetType", String.valueOf(classNameIds.get(0)));
				unicodeProperties.setProperty(
					"classNameIds",
					StringUtil.merge(
						AssetRendererFactoryRegistryUtil.getClassNameIds(
							assetListEntrySegmentsEntryRel.getCompanyId(),
							true)));

				_assetListEntryLocalService.updateAssetListEntryTypeSettings(
					assetListEntrySegmentsEntryRel.getAssetListEntryId(),
					assetListEntrySegmentsEntryRel.getSegmentsEntryId(),
					unicodeProperties.toString());

				return;
			}

			unicodeProperties.setProperty(
				"classNameIds",
				StringUtil.merge(ArrayUtil.toLongArray(classNameIds)));
		}
		else {
			unicodeProperties.setProperty(
				"anyAssetType", Boolean.TRUE.toString());
			unicodeProperties.setProperty(
				"classNameIds",
				StringUtil.merge(
					AssetRendererFactoryRegistryUtil.getClassNameIds(
						assetListEntrySegmentsEntryRel.getCompanyId(), true)));

			_assetListEntryLocalService.updateAssetListEntryTypeSettings(
				assetListEntrySegmentsEntryRel.getAssetListEntryId(),
				assetListEntrySegmentsEntryRel.getSegmentsEntryId(),
				unicodeProperties.toString());

			return;
		}

		if (GetterUtil.getLong(anyAssetTypeValue) == classNameId) {
			unicodeProperties.setProperty(
				"anyAssetType", Boolean.TRUE.toString());
		}

		_assetListEntryLocalService.updateAssetListEntryTypeSettings(
			assetListEntrySegmentsEntryRel.getAssetListEntryId(),
			assetListEntrySegmentsEntryRel.getSegmentsEntryId(),
			unicodeProperties.toString());
	}

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Reference
	private AssetListEntrySegmentsEntryRelLocalService
		_assetListEntrySegmentsEntryRelLocalService;

	@Reference
	private Portal _portal;

}