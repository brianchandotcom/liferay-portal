/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.internal.service;

import com.liferay.asset.category.property.model.AssetCategoryPropertyTable;
import com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRelTable;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetCategoryTable;
import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = ServiceWrapper.class)
public class AssetCategoryLocalServiceWrapper
	extends com.liferay.asset.kernel.service.AssetCategoryLocalServiceWrapper {

	@Override
	public List<AssetCategory> getCategories(
		long classNameId, long classPK, int start, int end) {

		JoinStep joinStep = DSLQueryFactoryUtil.select(
			AssetCategoryTable.INSTANCE
		).from(
			AssetEntryTable.INSTANCE
		);

		return _assetCategoryLocalService.dslQuery(
			_getGroupByStep(classNameId, classPK, joinStep));
	}

	@Override
	public int getCategoriesCount(long classNameId, long classPK) {
		JoinStep joinStep = DSLQueryFactoryUtil.count(
		).from(
			AssetEntryTable.INSTANCE
		);

		return _assetCategoryLocalService.dslQueryCount(
			_getGroupByStep(classNameId, classPK, joinStep));
	}

	@Override
	public List<AssetCategory> search(
		long groupId, String name, String[] categoryProperties, int start,
		int end) {

		JoinStep joinStep = DSLQueryFactoryUtil.selectDistinct(
			AssetCategoryTable.INSTANCE
		).from(
			AssetCategoryTable.INSTANCE
		);

		if (ArrayUtil.isNotEmpty(categoryProperties)) {
			for (int i = 0; i < categoryProperties.length; i++) {
				AssetCategoryPropertyTable assetCategoryPropertyTableAlias =
					AssetCategoryPropertyTable.INSTANCE.as(
						"assetCategoryProperty" + i);

				String categoryProperty = categoryProperties[i];

				Predicate predicate =
					assetCategoryPropertyTableAlias.categoryId.eq(
						AssetCategoryTable.INSTANCE.categoryId);

				String[] categoryPropertyArray = StringUtil.split(
					categoryProperty,
					AssetCategoryConstants.PROPERTY_KEY_VALUE_SEPARATOR);

				if (categoryPropertyArray.length <= 1) {
					categoryPropertyArray = StringUtil.split(
						categoryProperty, CharPool.COLON);
				}

				String key = StringPool.BLANK;

				if (categoryPropertyArray.length > 0) {
					key = GetterUtil.getString(categoryPropertyArray[0]);
				}

				String value = StringPool.BLANK;

				if (categoryPropertyArray.length > 1) {
					value = GetterUtil.getString(categoryPropertyArray[1]);
				}

				predicate = predicate.and(
					Predicate.withParentheses(
						Predicate.and(
							assetCategoryPropertyTableAlias.key.eq(key),
							assetCategoryPropertyTableAlias.value.eq(value))));

				joinStep = joinStep.innerJoinON(
					assetCategoryPropertyTableAlias, predicate);
			}
		}

		return _assetCategoryLocalService.dslQuery(
			joinStep.where(
				() -> {
					Predicate predicate =
						AssetCategoryTable.INSTANCE.groupId.eq(groupId);

					if (Validator.isNotNull(name)) {
						return Predicate.withParentheses(
							predicate.and(
								_customSQL.getKeywordsPredicate(
									DSLFunctionFactoryUtil.lower(
										AssetCategoryTable.INSTANCE.name),
									_customSQL.keywords(name, true))));
					}

					return predicate;
				}));
	}

	private GroupByStep _getGroupByStep(
		long classNameId, long classPK, JoinStep joinStep) {

		return joinStep.innerJoinON(
			AssetEntryAssetCategoryRelTable.INSTANCE,
			AssetEntryAssetCategoryRelTable.INSTANCE.assetEntryId.eq(
				AssetEntryTable.INSTANCE.entryId)
		).innerJoinON(
			AssetCategoryTable.INSTANCE,
			AssetCategoryTable.INSTANCE.categoryId.eq(
				AssetEntryAssetCategoryRelTable.INSTANCE.assetCategoryId)
		).where(
			AssetEntryTable.INSTANCE.classNameId.eq(
				classNameId
			).and(
				AssetEntryTable.INSTANCE.classPK.eq(classPK)
			).and(
				InlineSQLHelperUtil.getPermissionWherePredicate(
					AssetCategory.class, AssetCategoryTable.INSTANCE.categoryId)
			)
		);
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private CustomSQL _customSQL;

}