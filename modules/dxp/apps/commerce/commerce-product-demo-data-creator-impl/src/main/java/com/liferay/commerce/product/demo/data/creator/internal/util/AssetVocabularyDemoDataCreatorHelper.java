/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.demo.data.creator.internal.util;

import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = AssetVocabularyDemoDataCreatorHelper.class)
public class AssetVocabularyDemoDataCreatorHelper
	extends BaseCPDemoDataCreatorHelper {

	public void addAssetVocabularies(long userId, long groupId)
		throws Exception {

		ServiceContext serviceContext = getServiceContext(userId, groupId);

		// Asset vocabularies

		JSONArray assetVocabulariesJSONArray = getAssetVocabulariesJSONArray();

		for (int i = 0; i < assetVocabulariesJSONArray.length(); i++) {
			JSONObject assetVocabularyJSONObject =
				assetVocabulariesJSONArray.getJSONObject(i);

			createAssetVocabulary(assetVocabularyJSONObject, serviceContext);
		}
	}

	public AssetVocabulary createAssetVocabulary(
			JSONObject assetVocabularyJSONObject, ServiceContext serviceContext)
		throws Exception {

		String title = assetVocabularyJSONObject.getString("vocabulary");

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.fetchGroupVocabulary(
				serviceContext.getScopeGroupId(), title);

		if (assetVocabulary != null) {
			return assetVocabulary;
		}

		assetVocabulary = _assetVocabularyLocalService.addVocabulary(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), title,
			serviceContext);

		JSONArray categoriesJSONArray = assetVocabularyJSONObject.getJSONArray(
			"categories");

		_assetCategoryDemoDataCreatorHelper.addAssetCategories(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			assetVocabulary.getVocabularyId(),
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			categoriesJSONArray);

		return assetVocabulary;
	}

	protected JSONArray getAssetVocabulariesJSONArray() throws Exception {
		Class<?> clazz = getClass();

		String assetVocabulariesPath =
			"com/liferay/commerce/product/demo/data/creator/internal" +
				"/dependencies/categories.json";

		String assetVocabulariesJSON = StringUtil.read(
			clazz.getClassLoader(), assetVocabulariesPath, false);

		return _jsonFactory.createJSONArray(assetVocabulariesJSON);
	}

	@Reference
	private AssetCategoryDemoDataCreatorHelper
		_assetCategoryDemoDataCreatorHelper;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private JSONFactory _jsonFactory;

}