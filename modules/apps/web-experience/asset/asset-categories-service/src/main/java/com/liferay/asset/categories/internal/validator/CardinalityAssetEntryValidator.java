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

package com.liferay.asset.categories.internal.validator;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.asset.kernel.validator.AssetEntryValidator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Juan Fernández
 */
@Component(
	immediate = true, property = {"model.class.name=*"},
	service = AssetEntryValidator.class
)
public class CardinalityAssetEntryValidator implements AssetEntryValidator {

	@Override
	public void validate(
			long groupId, String className, long classPK, long classTypePK,
			long[] categoryIds, String[] entryNames)
		throws PortalException {

		List<AssetVocabulary> assetVocabularies =
			_assetVocabularyLocalService.getGroupVocabularies(groupId, false);

		Group group = _groupLocalService.getGroup(groupId);

		if (!group.isCompany()) {
			Group companyGroup = _groupLocalService.fetchCompanyGroup(
				group.getCompanyId());

			if (companyGroup != null) {
				assetVocabularies = ListUtil.copy(assetVocabularies);

				assetVocabularies.addAll(
					_assetVocabularyLocalService.getGroupVocabularies(
						companyGroup.getGroupId()));
			}
		}

		long classNameId = _classNameLocalService.getClassNameId(className);

		if (isCategorizable(groupId, classNameId, classPK)) {
			for (AssetVocabulary assetVocabulary : assetVocabularies) {
				validate(
					classNameId, classTypePK, categoryIds, assetVocabulary);
			}
		}
	}

	/**
	 * @deprecated As of 1.1.0
	 */
	@Deprecated
	@Override
	public void validate(
			long groupId, String className, long classTypePK,
			long[] categoryIds, String[] entryNames)
		throws PortalException {

		validate(groupId, className, 0L, classTypePK, categoryIds, entryNames);
	}

	protected boolean isCategorizable(
		long groupId, long classNameId, long classPK) {

		String className = PortalUtil.getClassName(classNameId);

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if ((assetRendererFactory == null) ||
			!assetRendererFactory.isCategorizable()) {

			return false;
		}

		if (classPK != 0L) {
			try {
				AssetRenderer<?> assetRenderer =
					assetRendererFactory.getAssetRenderer(classPK);

				if (!assetRenderer.isCategorizable(groupId)) {
					return false;
				}
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Entity with ClassPK: " + classPK +
							" and ClassNameId: " + classNameId +
								" is not categorizable",
						pe);
				}

				return false;
			}
		}

		return true;
	}

	@Reference(unbind = "-")
	protected void setAssetVocabularyLocalService(
		AssetVocabularyLocalService assetVocabularyLocalService) {

		_assetVocabularyLocalService = assetVocabularyLocalService;
	}

	@Reference(unbind = "-")
	protected void setClassNameLocalService(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	protected void validate(
			long classNameId, long classTypePK, final long[] categoryIds,
			AssetVocabulary assetVocabulary)
		throws PortalException {

		if (!assetVocabulary.isAssociatedToClassNameIdAndClassTypePK(
				classNameId, classTypePK)) {

			return;
		}

		if (assetVocabulary.isMissingRequiredCategory(
				classNameId, classTypePK, categoryIds)) {

			throw new AssetCategoryException(
				assetVocabulary, AssetCategoryException.AT_LEAST_ONE_CATEGORY);
		}

		if (!assetVocabulary.isMultiValued() &&
			assetVocabulary.hasMoreThanOneCategorySelected(categoryIds)) {

			throw new AssetCategoryException(
				assetVocabulary, AssetCategoryException.TOO_MANY_CATEGORIES);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CardinalityAssetEntryValidator.class.getName());

	private AssetVocabularyLocalService _assetVocabularyLocalService;
	private ClassNameLocalService _classNameLocalService;
	private GroupLocalService _groupLocalService;

}