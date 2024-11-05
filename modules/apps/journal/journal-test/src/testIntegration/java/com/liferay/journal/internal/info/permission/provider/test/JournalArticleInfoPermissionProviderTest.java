/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.info.permission.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.info.permission.provider.InfoPermissionProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class JournalArticleInfoPermissionProviderTest {

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
	public void testHasViewPermission() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);

		DDMStructure ddmStructure1 = _addDDMStructure(serviceContext);

		serviceContext.setAddGroupPermissions(true);

		DDMStructure ddmStructure2 = _addDDMStructure(serviceContext);

		InfoItemFormVariationsProvider<JournalArticle>
			infoItemFormVariationsProvider =
				_infoItemServiceRegistry.getFirstInfoItemService(
					InfoItemFormVariationsProvider.class,
					JournalArticle.class.getName());

		Assert.assertNotNull(infoItemFormVariationsProvider);

		InfoPermissionProvider<JournalArticle> infoPermissionProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoPermissionProvider.class, JournalArticle.class.getName());

		Assert.assertNotNull(infoPermissionProvider);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user);

		Assert.assertFalse(
			infoPermissionProvider.hasViewPermission(
				String.valueOf(ddmStructure1.getStructureId()),
				_group.getGroupId(), permissionChecker));
		Assert.assertFalse(
			infoPermissionProvider.hasViewPermission(
				ddmStructure1.getStructureKey(), _group.getGroupId(),
				permissionChecker));
		Assert.assertTrue(
			infoPermissionProvider.hasViewPermission(
				String.valueOf(ddmStructure2.getStructureId()),
				_group.getGroupId(), permissionChecker));
		Assert.assertTrue(
			infoPermissionProvider.hasViewPermission(
				ddmStructure2.getStructureKey(), _group.getGroupId(),
				permissionChecker));
	}

	private DDMStructure _addDDMStructure(ServiceContext serviceContext)
		throws Exception {

		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm();

		DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(ddmForm);

		return _ddmStructureLocalService.addStructure(
			null, TestPropsValues.getUserId(), _group.getGroupId(), 0,
			_portal.getClassNameId(JournalArticle.class.getName()), null,
			RandomTestUtil.randomLocaleStringMap(), null, ddmForm,
			ddmFormLayout, StorageType.DEFAULT.toString(),
			DDMStructureConstants.TYPE_DEFAULT, serviceContext);
	}

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Inject
	private Portal _portal;

	@DeleteAfterTestRun
	private User _user;

}