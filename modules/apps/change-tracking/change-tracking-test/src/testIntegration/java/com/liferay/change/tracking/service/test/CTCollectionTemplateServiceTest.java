/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2025-06
 */

package com.liferay.change.tracking.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTActionKeys;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollectionTemplate;
import com.liferay.change.tracking.service.CTCollectionTemplateLocalService;
import com.liferay.change.tracking.service.CTCollectionTemplateService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
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

	@Test
	public void testAddCTCollectionTemplate() throws Exception {
		User user = UserTestUtil.addUser();

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, PermissionCheckerFactoryUtil.create(user))) {

			_addCTCollectionTemplate(RandomTestUtil.randomString());

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			Assert.assertNotNull(principalException);
		}

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		RoleTestUtil.addResourcePermission(
			role, CTConstants.RESOURCE_NAME, ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			CTActionKeys.ADD_TEMPLATE);

		UserLocalServiceUtil.addRoleUser(role.getRoleId(), user.getUserId());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, PermissionCheckerFactoryUtil.create(user))) {

			CTCollectionTemplate ctCollectionTemplate =
				_addCTCollectionTemplate(RandomTestUtil.randomString());

			Assert.assertNotNull(ctCollectionTemplate);
		}
	}

	@Test
	public void testGetCTCollectionTemplates() throws Exception {
		_addCTCollectionTemplate(RandomTestUtil.randomString());

		String name = RandomTestUtil.randomString();

		CTCollectionTemplate ctCollectionTemplate = _addCTCollectionTemplate(
			name);

		_assertGetCTCollectionTemplates(
			_ctCollectionTemplateLocalService.getCTCollectionTemplates(
				TestPropsValues.getCompanyId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS),
			StringPool.BLANK);
		_assertGetCTCollectionTemplates(
			Collections.singletonList(ctCollectionTemplate), name);
	}

	@Test
	public void testGetCTCollectionTemplatesCount() throws Exception {
		_addCTCollectionTemplate(RandomTestUtil.randomString());

		String name = RandomTestUtil.randomString();

		_addCTCollectionTemplate(name);

		Assert.assertEquals(
			_ctCollectionTemplateLocalService.getCTCollectionTemplatesCount(),
			_ctCollectionTemplateService.getCTCollectionTemplatesCount(
				StringPool.BLANK));
		Assert.assertEquals(
			1,
			_ctCollectionTemplateService.getCTCollectionTemplatesCount(name));
	}

	private CTCollectionTemplate _addCTCollectionTemplate(String name)
		throws Exception {

		return _ctCollectionTemplateService.addCTCollectionTemplate(
			name, RandomTestUtil.randomString(),
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

	private void _assertGetCTCollectionTemplates(
		List<CTCollectionTemplate> expectedCTCollectionTemplates,
		String keywords) {

		List<CTCollectionTemplate> ctCollectionTemplates =
			_ctCollectionTemplateService.getCTCollectionTemplates(
				keywords, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertTrue(
			ctCollectionTemplates.containsAll(expectedCTCollectionTemplates));
		Assert.assertEquals(
			ctCollectionTemplates.toString(),
			expectedCTCollectionTemplates.size(), ctCollectionTemplates.size());
	}

	@Inject
	private CTCollectionTemplateLocalService _ctCollectionTemplateLocalService;

	@Inject
	private CTCollectionTemplateService _ctCollectionTemplateService;

}