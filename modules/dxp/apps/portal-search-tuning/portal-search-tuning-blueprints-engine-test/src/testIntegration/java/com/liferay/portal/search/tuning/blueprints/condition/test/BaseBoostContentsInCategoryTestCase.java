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

package com.liferay.portal.search.tuning.blueprints.condition.test;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.search.tuning.blueprints.test.BaseBlueprintsTestCase;

/**
 * @author Wade Cao
 */
public abstract class BaseBoostContentsInCategoryTestCase
	extends BaseBlueprintsTestCase {

	protected AssetCategory getAssetCategory(
			String categoryTitle, String roleName)
		throws Exception {

		Role role = RoleTestUtil.addRole(roleName, RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(group, role.getName());

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addDefaultVocabulary(
				group.getGroupId());

		return AssetCategoryLocalServiceUtil.addCategory(
			user.getUserId(), group.getGroupId(), categoryTitle,
			assetVocabulary.getVocabularyId(), serviceContext);
	}

	protected abstract JSONArray getConditions();

	protected abstract JSONObject getDescription();

	protected abstract JSONObject getElementTemplateJSONObject()
		throws Exception;

	protected String getIcon() {
		return "thumbs-up";
	}

	protected JSONObject getQueryElementJSONObject(int boost, long categoryId) {
		return JSONUtil.put(
			"category", "conditional"
		).put(
			"clauses",
			createJSONArray().put(
				JSONUtil.put(
					"context", "query"
				).put(
					"occur", "should"
				).put(
					"query",
					JSONUtil.put(
						"query",
						JSONUtil.put(
							"term",
							JSONUtil.put(
								"assetCategoryIds",
								JSONUtil.put(
									"boost", boost
								).put(
									"value", categoryId
								))))
				).put(
					"type", "wrapper"
				))
		).put(
			"conditions", getConditions()
		).put(
			"description", getDescription()
		).put(
			"enabled", isEnabled()
		).put(
			"icon", getIcon()
		).put(
			"title", getTitle()
		);
	}

	protected String getSelectedElementString(int boost, long categoryId)
		throws Exception {

		JSONObject elementTemplateJSONObject = getElementTemplateJSONObject();

		return JSONUtil.put(
			"query_configuration",
			createJSONArray().put(
				JSONUtil.put(
					"elementOutput",
					JSONUtil.put(
						"category", "conditional"
					).put(
						"clauses",
						createJSONArray().put(
							JSONUtil.put(
								"context", "query"
							).put(
								"occur", "should"
							).put(
								"query",
								JSONUtil.put(
									"query",
									JSONUtil.put(
										"term",
										JSONUtil.put(
											"assetCategoryIds",
											JSONUtil.put(
												"boost", boost
											).put(
												"value", categoryId
											))))
							).put(
								"type", "wrapper"
							))
					).put(
						"conditions", getConditions()
					).put(
						"description", getDescription()
					).put(
						"enabled", isEnabled()
					).put(
						"icon", getIcon()
					).put(
						"title", getTitle()
					)
				).put(
					"elementTemplateJSON",
					elementTemplateJSONObject.get("elementTemplateJSON")
				).put(
					"uiConfigurationJSON",
					elementTemplateJSONObject.get("uiConfigurationJSON")
				).put(
					"uiConfigurationValues",
					getUIConfigurationValuesJSONObject()
				))
		).toString();
	}

	protected abstract JSONObject getTitle();

	protected abstract JSONObject getUIConfigurationValuesJSONObject();

	protected boolean isEnabled() {
		return true;
	}

}