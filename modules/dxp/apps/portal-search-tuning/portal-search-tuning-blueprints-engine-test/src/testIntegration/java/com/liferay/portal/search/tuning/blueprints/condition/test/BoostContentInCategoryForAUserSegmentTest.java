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
import com.liferay.portal.kernel.json.JSONArray;
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
	extends BaseBoostContentInCategoryTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testContainsCondition() throws Exception {
		Role role = RoleTestUtil.addRole("User A", RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(group, role.getName());

		_segmentsEntry = _addSegmentsEntry(role);

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addDefaultVocabulary(
				group.getGroupId());

		_assetCategory = AssetCategoryLocalServiceUtil.addCategory(
			user.getUserId(), group.getGroupId(), "Promoted",
			assetVocabulary.getVocabularyId(), serviceContext);

		addJournalArticle("Coca Cola", "cola cola");

		serviceContext.setAssetCategoryIds(
			new long[] {_assetCategory.getCategoryId()});

		addJournalArticle("Pepsi Cola", "");

		Blueprint blueprint = addCompanyBlueprint(
			Collections.singletonMap(
				LocaleUtil.US, getClass().getName() + "Blueprint"),
			Collections.singletonMap(LocaleUtil.US, ""),
			getConfigurationString((JSONObject[])null), "", 1);

		assertSearch(blueprint, null, "[coca cola, pepsi cola]", "cola", null);

		String configurationString = getConfigurationString(
			getQueryElementJSONObject(1000, _assetCategory.getCategoryId()));

		String selectedElementString = getSelectedElementString(
			1000, _assetCategory.getCategoryId());

		assertSearch(
			blueprint, configurationString, "[pepsi cola, coca cola]", "cola",
			selectedElementString);
	}

	@Override
	protected JSONArray getConditions() {
		return createJSONArray().put(
			JSONUtil.put(
				"configuration",
				JSONUtil.put(
					"evaluation_type", EvaluationType.CONTAINS.getjsonValue()
				).put(
					"parameter_name", "${user.user_segment_entry_ids}"
				).put(
					"value", _segmentsEntry.getSegmentsEntryId()
				)));
	}

	@Override
	protected JSONObject getDescription() {
		return JSONUtil.put(
			"en_US",
			"Boost contents in a category for users belonging to a user " +
				"segment");
	}

	@Override
	protected JSONObject getElementTemplateJSONObject() throws Exception {
		return getElementTemplateJSONObject(
			"/elements/boost-content-in-category-for-a-user-segment-test.json");
	}

	@Override
	protected JSONObject getTitle() {
		return JSONUtil.put(
			"en_US", "Boost Contents in a Category for a User Segment");
	}

	@Override
	protected JSONObject getUIConfigurationValuesJSONObject() {
		return JSONUtil.put(
			"asset_category_id", _assetCategory.getCategoryId()
		).put(
			"boost", 1000
		).put(
			"user_segment_id", _segmentsEntry.getSegmentsEntryId()
		);
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

	private AssetCategory _assetCategory;
	private SegmentsEntry _segmentsEntry;

	@Inject(
		filter = "segments.criteria.contributor.key=user",
		type = SegmentsCriteriaContributor.class
	)
	private SegmentsCriteriaContributor _userSegmentsCriteriaContributor;

}