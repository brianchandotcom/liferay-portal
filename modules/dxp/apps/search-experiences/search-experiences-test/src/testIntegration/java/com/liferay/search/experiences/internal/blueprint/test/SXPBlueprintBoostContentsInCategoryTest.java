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

package com.liferay.search.experiences.internal.blueprint.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.search.experiences.internal.blueprint.util.SXPBlueprintTestUtil;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@RunWith(Arquillian.class)
public class SXPBlueprintBoostContentsInCategoryTest
	extends BaseSXPBlueprintsTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_assetCategory = addAssetCategory(
			"Important", group,
			SXPBlueprintTestUtil.addGroupUser(group, "Custmers"));

		addJournalArticle(group.getGroupId(), "Coca Cola", "cola cola");

		serviceContext.setAssetCategoryIds(
			new long[] {_assetCategory.getCategoryId()});

		addJournalArticle(group.getGroupId(), "Pepsi Cola", "");

		setUpSXPBlueprint(getClass());
	}

	@Test
	public void testSearch() throws Exception {
		updateSXPBlueprint(getEmptyConfigurationJSONString());

		assertSearchIgnoreRelevance("[coca cola, pepsi cola]", "cola");
	}

	@Test
	public void testSearchWithBoostContentsInCategory() throws Exception {
		updateSXPBlueprint(_getConfigurationJSONString());

		assertSearch("[pepsi cola, coca cola]", "cola");
	}

	private String _getConfigurationJSONString() {
		return StringUtil.replace(
			getConfigurationJSONString(getClass()),
			"${configuration.asset_category_ids}",
			String.valueOf(_assetCategory.getCategoryId()));
	}

	private AssetCategory _assetCategory;

}