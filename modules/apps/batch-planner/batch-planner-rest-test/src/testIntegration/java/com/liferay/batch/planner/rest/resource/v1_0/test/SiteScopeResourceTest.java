/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.planner.batch.engine.task.TaskItemUtil;
import com.liferay.batch.planner.rest.client.dto.v1_0.SiteScope;
import com.liferay.batch.planner.rest.client.pagination.Page;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Matija Petanjek
 * @author Carlos Correa
 */
@RunWith(Arquillian.class)
public class SiteScopeResourceTest extends BaseSiteScopeResourceTestCase {

	@Override
	@Test
	public void testGetPlanInternalClassNameKeySiteScopesPage()
		throws Exception {

		Group group = GroupTestUtil.addGroupToCompany(
			testCompany.getCompanyId());

		// Object definition (company scoped)

		ObjectDefinition objectDefinition1 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", true, true, null,
						RandomTestUtil.randomString(),
						"a" + RandomTestUtil.randomString(), false)),
				ObjectDefinitionConstants.SCOPE_COMPANY);

		String internalClassNameKey = _getInternalClassNameKey(
			objectDefinition1);

		_testGetPlanInternalClassNameKeySiteScopesPage(
			Collections.emptyList(), null, internalClassNameKey);

		_testGetPlanInternalClassNameKeySiteScopesPage(
			Collections.emptyList(), false, internalClassNameKey);

		_testGetPlanInternalClassNameKeySiteScopesPage(
			Collections.emptyList(), true, internalClassNameKey);

		// Object definition (site scoped)

		ObjectDefinition objectDefinition2 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", true, true, null,
						RandomTestUtil.randomString(),
						"a" + RandomTestUtil.randomString(), false)),
				ObjectDefinitionConstants.SCOPE_SITE);

		internalClassNameKey = _getInternalClassNameKey(objectDefinition2);

		_testGetPlanInternalClassNameKeySiteScopesPage(
			Arrays.asList(group, testGroup), null, internalClassNameKey);

		_testGetPlanInternalClassNameKeySiteScopesPage(
			Arrays.asList(group, testGroup), false, internalClassNameKey);

		_testGetPlanInternalClassNameKeySiteScopesPage(
			Arrays.asList(group, testGroup), true, internalClassNameKey);

		// Service builder entity (company scoped)

		internalClassNameKey =
			"com.liferay.headless.admin.user.dto.v1_0.UserAccount";

		_testGetPlanInternalClassNameKeySiteScopesPage(
			Collections.emptyList(), null, internalClassNameKey);

		_testGetPlanInternalClassNameKeySiteScopesPage(
			Collections.emptyList(), false, internalClassNameKey);

		_testGetPlanInternalClassNameKeySiteScopesPage(
			Collections.singletonList(testGroup), true, internalClassNameKey);

		// Service builder entity (site scoped)

		internalClassNameKey =
			"com.liferay.headless.delivery.dto.v1_0.BlogPosting";

		_testGetPlanInternalClassNameKeySiteScopesPage(
			Arrays.asList(group, testGroup), null, internalClassNameKey);

		_testGetPlanInternalClassNameKeySiteScopesPage(
			Arrays.asList(group, testGroup), false, internalClassNameKey);

		_testGetPlanInternalClassNameKeySiteScopesPage(
			Arrays.asList(group, testGroup), true, internalClassNameKey);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"label", "value"};
	}

	private String _getInternalClassNameKey(ObjectDefinition objectDefinition) {
		return URLCodec.encodeURL(
			TaskItemUtil.getInternalClassNameKey(
				"com.liferay.object.rest.dto.v1_0.ObjectEntry",
				objectDefinition.getName()));
	}

	private SiteScope _getSiteScope(String name, Page<SiteScope> page) {
		for (SiteScope siteScope : page.getItems()) {
			if (StringUtil.equals(siteScope.getLabel(), name)) {
				return siteScope;
			}
		}

		return null;
	}

	private void _testGetPlanInternalClassNameKeySiteScopesPage(
			List<Group> expectedGroups, Boolean export,
			String internalClassNameKey)
		throws Exception {

		Page<SiteScope> page =
			siteScopeResource.getPlanInternalClassNameKeySiteScopesPage(
				internalClassNameKey, export);

		if (expectedGroups.isEmpty()) {
			Assert.assertEquals(0, page.getTotalCount());
		}
		else {
			Assert.assertTrue(page.getTotalCount() >= expectedGroups.size());
		}

		for (Group group : expectedGroups) {
			assertEquals(
				_toSiteScope(group), _getSiteScope(group.getGroupKey(), page));
		}
	}

	private SiteScope _toSiteScope(Group group) {
		SiteScope siteScope = new SiteScope();

		siteScope.setLabel(group.getGroupKey());
		siteScope.setValue(group.getGroupId());

		return siteScope;
	}

}