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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.tuning.blueprints.constants.json.values.EvaluationType;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.test.BaseBlueprintsTestCase;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.Collections;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@RunWith(Arquillian.class)
public class BoostContentInCategoryForAUserSegmentTest
	extends BaseBlueprintsTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testContainsCondition() throws Exception {
		Role role = RoleTestUtil.addRole(
			"Customers", RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(group, role.getName());

		SegmentsEntry segmentsEntry = _addSegmentsEntry(role);

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addDefaultVocabulary(
				group.getGroupId());

		AssetCategory assetCategory = AssetCategoryLocalServiceUtil.addCategory(
			user.getUserId(), group.getGroupId(), "Promoted",
			assetVocabulary.getVocabularyId(), serviceContext);

		addJournalArticle("Coca Cola", "cola cola");

		serviceContext.setAssetCategoryIds(
			new long[] {assetCategory.getCategoryId()});

		addJournalArticle("Pepsi Cola", "");

		Blueprint blueprint = addCompanyBlueprint(
			Collections.singletonMap(
				LocaleUtil.US, getClass().getName() + "Blueprint"),
			Collections.singletonMap(LocaleUtil.US, ""),
			getConfigurationString(null), "", 1);

		assertSearch(blueprint, null, "[coca cola, pepsi cola]", "cola", null);

		String configurationString = getConfigurationString(
			_getQueryElementJSONObject(
				1000, assetCategory.getCategoryId(),
				EvaluationType.CONTAINS.getjsonValue(),
				segmentsEntry.getSegmentsEntryId()));

		String selectedElementString = _getSelectedElementString(
			1000, assetCategory.getCategoryId(),
			EvaluationType.CONTAINS.getjsonValue(),
			segmentsEntry.getSegmentsEntryId());

		assertSearch(
			blueprint, configurationString, "[pepsi cola, coca cola]", "cola",
			selectedElementString);
	}

	private SegmentsEntry _addSegmentsEntry(Role role) throws Exception {
		Criteria criteria = new Criteria();

		_userSegmentsCriteriaContributor.contribute(
			criteria, String.format("(roleId eq '%s')", role.getRoleId()),
			Criteria.Conjunction.AND);

		return SegmentsTestUtil.addSegmentsEntry(
			group.getGroupId(), CriteriaSerializer.serialize(criteria),
			User.class.getName());
	}

	private JSONObject _getQueryElementJSONObject(
		int boost, long categoryId, String evaluationType, long segmentId) {

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
			"conditions",
			createJSONArray().put(
				JSONUtil.put(
					"configuration",
					JSONUtil.put(
						"evaluation_type", evaluationType
					).put(
						"parameter_name", "${user.user_segment_entry_ids}"
					).put(
						"value", segmentId
					)))
		).put(
			"description",
			JSONUtil.put(
				"en_US",
				"Boost contents in a category for users belonging to a user " +
					"segment")
		).put(
			"enabled", true
		).put(
			"icon", "thumbs-up"
		).put(
			"title",
			JSONUtil.put(
				"en_US", "Boost Contents in a Category for a User Segment")
		);
	}

	private String _getSelectedElementString(
			int boost, long categoryId, String evaluationType, long segmentId)
		throws Exception {

		JSONObject elementTemplateJSONObject = getElementTemplateJSONObject(
			"/elements/boost-content-in-category-for-a-user-segment-test.json");

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
						"conditions",
						createJSONArray().put(
							JSONUtil.put(
								"configuration",
								JSONUtil.put(
									"evaluation_type", evaluationType
								).put(
									"parameter_name",
									"${user.user_segment_entry_ids}"
								).put(
									"value", createJSONArray().put(segmentId)
								)))
					).put(
						"description",
						JSONUtil.put(
							"en_US",
							"Boost contents in a category for users " +
								"belonging to a user segment")
					).put(
						"enabled", true
					).put(
						"icon", "thumbs-up"
					).put(
						"title",
						JSONUtil.put(
							"en_US",
							"Boost Contents in a Category for a User Segment")
					)
				).put(
					"elementTemplateJSON",
					elementTemplateJSONObject.get("elementTemplateJSON")
				).put(
					"uiConfigurationJSON",
					elementTemplateJSONObject.get("uiConfigurationJSON")
				).put(
					"uiConfigurationValues",
					_getUIConfigurationValuesJSONObject(
						categoryId, boost, segmentId)
				))
		).toString();
	}

	private JSONObject _getUIConfigurationValuesJSONObject(
		long categoryId, int boost, long segmentId) {

		return JSONUtil.put(
			"asset_category_id", categoryId
		).put(
			"boost", boost
		).put(
			"user_segment_id", segmentId
		);
	}

	@Inject(
		filter = "segments.criteria.contributor.key=user",
		type = SegmentsCriteriaContributor.class
	)
	private SegmentsCriteriaContributor _userSegmentsCriteriaContributor;

}