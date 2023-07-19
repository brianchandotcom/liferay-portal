/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class KBArticleInfoItemPermissionProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addGroupUser(_group, RoleConstants.SITE_MEMBER);
	}

	@Test
	public void testHasPermission() throws Exception {
		_kbArticle = _kbArticleLocalService.addKBArticle(
			null, _user.getUserId(),
			ClassNameLocalServiceUtil.getClassNameId(
				KBFolderConstants.getClassName()),
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), null,
			StringPool.BLANK, new Date(), null, null, null,
			ServiceContextTestUtil.getServiceContext());

		RoleTestUtil.removeResourcePermission(
			RoleConstants.GUEST, KBArticle.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_kbArticle.getResourcePrimKey()), ActionKeys.VIEW);

		InfoItemPermissionProvider<KBArticle> infoItemPermissionProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemPermissionProvider.class, KBArticle.class.getName());

		Assert.assertFalse(
			infoItemPermissionProvider.hasPermission(
				PermissionCheckerFactoryUtil.create(
					UserLocalServiceUtil.getGuestUser(_group.getCompanyId())),
				_kbArticle, ActionKeys.VIEW));
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@DeleteAfterTestRun
	private KBArticle _kbArticle;

	@Inject
	private KBArticleLocalService _kbArticleLocalService;

	@DeleteAfterTestRun
	private User _user;

}