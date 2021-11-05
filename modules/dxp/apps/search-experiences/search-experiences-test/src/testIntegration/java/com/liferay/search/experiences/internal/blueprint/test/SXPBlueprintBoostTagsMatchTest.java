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
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@RunWith(Arquillian.class)
public class SXPBlueprintBoostTagsMatchTest extends BaseSXPBlueprintsTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		super.setUp();

		addJournalArticle(group.getGroupId(), "coca cola", "");

		setUpSXPBlueprint(getClass());
	}

	@Test
	public void testKeywoardMatchWithAssetTagName() throws Exception {
		AssetTag assetTag = AssetTagLocalServiceUtil.addTag(
			user.getUserId(), group.getGroupId(), "cola", serviceContext);

		serviceContext.setAssetTagNames(new String[] {assetTag.getName()});

		addJournalArticle(group.getGroupId(), "pepsi cola", "");

		updateSXPBlueprint(getConfigurationJSONString(getClass()));

		assertSearch("[pepsi cola, coca cola]", "cola");
	}

	@Test
	public void testSearch() throws Exception {
		serviceContext.setAssetTagNames(new String[0]);

		addJournalArticle(group.getGroupId(), "pepsi cola", "");

		updateSXPBlueprint(getEmptyConfigurationJSONString());

		assertSearch("[coca cola, pepsi cola]", "cola");
	}

}