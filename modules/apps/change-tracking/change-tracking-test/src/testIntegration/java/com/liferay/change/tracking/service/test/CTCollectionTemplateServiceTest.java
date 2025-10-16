/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2025-06
 */

package com.liferay.change.tracking.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollectionTemplate;
import com.liferay.change.tracking.service.CTCollectionTemplateService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Noor Najjar
 */
@RunWith(Arquillian.class)
public class CTCollectionTemplateServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_templateName = RandomTestUtil.randomString();

		_ctCollectionTemplateService.addCTCollectionTemplate(
			_templateName, RandomTestUtil.randomString(),
			JSONUtil.put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"publicationsUserRoleUserIds", Collections.emptyList()
			).put(
				"roleValues", Collections.emptyList()
			).put(
				"userIds", new long[] {RandomTestUtil.randomLong()}
			).toString());
	}

	@Test
	public void testGetCTCollectionTemplatesWithSearch() {
		_assertGetCTCollectionTemplates(StringPool.BLANK);

		_assertGetCTCollectionTemplates(_templateName);

		_assertGetCTCollectionTemplatesCount(StringPool.BLANK);

		_assertGetCTCollectionTemplatesCount(_templateName);
	}

	private void _assertGetCTCollectionTemplates(String keywords) {
		List<CTCollectionTemplate> ctCollectionTemplateList =
			_ctCollectionTemplateService.getCTCollectionTemplates(
				keywords, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			ctCollectionTemplateList.toString(), 1,
			ctCollectionTemplateList.size());

		CTCollectionTemplate ctCollectionTemplate =
			ctCollectionTemplateList.get(0);

		Assert.assertEquals(_templateName, ctCollectionTemplate.getName());
	}

	private void _assertGetCTCollectionTemplatesCount(String keywords) {
		long count = _ctCollectionTemplateService.getCTCollectionTemplatesCount(
			keywords);

		Assert.assertEquals(1, count);
	}

	@Inject
	private static CTCollectionTemplateService _ctCollectionTemplateService;

	private String _templateName;

}