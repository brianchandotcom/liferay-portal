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

package com.liferay.portlet.asset.util;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;

import java.util.List;

/**
 * @author Juan Fernández
 */
public class BaseAssetEntryValidator implements AssetEntryValidator {

	@Override
	public void validate(
			long groupId, String className, long classTypeId,
			long[] categoryIds, String[] entryNames)
		throws PortalException, SystemException {

		List<AssetVocabulary> vocabularies =
			AssetVocabularyLocalServiceUtil.getGroupVocabularies(
				groupId, false);

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (!group.isCompany()) {
			try {
				Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
					group.getCompanyId());

				vocabularies = ListUtil.copy(vocabularies);

				vocabularies.addAll(
					AssetVocabularyLocalServiceUtil.getGroupVocabularies(
						companyGroup.getGroupId()));
			}
			catch (NoSuchGroupException nsge) {
			}
		}

		long classNameId = ClassNameLocalServiceUtil.getClassNameId(className);

		for (AssetVocabulary vocabulary : vocabularies) {
			validate(classNameId, classTypeId, categoryIds, vocabulary);
		}
	}

	protected void validate(
			long classNameId, long classTypeId, final long[] categoryIds,
			AssetVocabulary vocabulary)
		throws PortalException, SystemException {

		if (!vocabulary.isAssociatedToAsset(classNameId, classTypeId)) {
			return;
		}

		if (!_isAssetCategorizable(classNameId)) {
			return;
		}

		if (vocabulary.isMissingRequiredCategory(
				classNameId, classTypeId, categoryIds)) {

			throw new AssetCategoryException(
				vocabulary, AssetCategoryException.AT_LEAST_ONE_CATEGORY);
		}

		if (!vocabulary.isMultiValued() &&
			vocabulary.isDuplicatedCategory(categoryIds)) {

			throw new AssetCategoryException(
				vocabulary, AssetCategoryException.TOO_MANY_CATEGORIES);
		}
	}

	private boolean _isAssetCategorizable(long assetClassNameId) {
		String className = PortalUtil.getClassName(assetClassNameId);

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if ((assetRendererFactory != null) &&
			assetRendererFactory.isCategorizable()) {

			return true;
		}

		return false;
	}

}