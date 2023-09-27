/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.utility.page.constants.LayoutUtilityPageActionKeys;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class LayoutUtilityPageEntryServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_role = _roleLocalService.addRole(
			TestPropsValues.getUserId(), null, 0, StringUtil.randomString(),
			null, null, RoleConstants.TYPE_SITE, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_user = UserTestUtil.addGroupUser(_group, _role.getName());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), _user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testCopyLayoutUtilityPageEntry() throws Exception {
		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryService.addLayoutUtilityPageEntry(
				RandomTestUtil.randomString(), _group.getGroupId(), 0, 0, true,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(), 0,
				_serviceContext);

		LayoutUtilityPageEntry copiedLayoutUtilityPageEntry =
			_layoutUtilityPageEntryService.copyLayoutUtilityPageEntry(
				_group.getGroupId(),
				layoutUtilityPageEntry.getLayoutUtilityPageEntryId(),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertNotEquals(
			layoutUtilityPageEntry.getExternalReferenceCode(),
			copiedLayoutUtilityPageEntry.getExternalReferenceCode());
		Assert.assertEquals(
			layoutUtilityPageEntry.getGroupId(),
			copiedLayoutUtilityPageEntry.getGroupId());
		Assert.assertEquals(
			layoutUtilityPageEntry.getCompanyId(),
			copiedLayoutUtilityPageEntry.getCompanyId());
		Assert.assertNotEquals(
			layoutUtilityPageEntry.getPlid(),
			copiedLayoutUtilityPageEntry.getPlid());
		Assert.assertEquals(
			0, copiedLayoutUtilityPageEntry.getPreviewFileEntryId());
		Assert.assertTrue(
			StringUtil.startsWith(
				copiedLayoutUtilityPageEntry.getName(),
				StringBundler.concat(
					layoutUtilityPageEntry.getName(), " (",
					_language.get(LocaleUtil.getDefault(), "copy"), ")")));
		Assert.assertEquals(
			layoutUtilityPageEntry.getType(),
			copiedLayoutUtilityPageEntry.getType());
	}

	@Test
	public void testCopyLayoutUtilityPageEntryWithPreviewFileEntry()
		throws Exception {

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryService.addLayoutUtilityPageEntry(
				RandomTestUtil.randomString(), _group.getGroupId(), 0, 0, true,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(), 0,
				_serviceContext);

		Repository repository = _portletFileRepository.addPortletRepository(
			_group.getGroupId(), LayoutAdminPortletKeys.GROUP_PAGES,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		FileEntry fileEntry = _portletFileRepository.addPortletFileEntry(
			_group.getGroupId(), TestPropsValues.getUserId(),
			FragmentCollection.class.getName(),
			layoutUtilityPageEntry.getLayoutUtilityPageEntryId(),
			LayoutAdminPortletKeys.GROUP_PAGES, repository.getDlFolderId(),
			new byte[0], "test.png", ContentTypes.IMAGE_PNG, false);

		layoutUtilityPageEntry =
			_layoutUtilityPageEntryService.updateLayoutUtilityPageEntry(
				layoutUtilityPageEntry.getLayoutUtilityPageEntryId(),
				fileEntry.getFileEntryId());

		LayoutUtilityPageEntry copiedLayoutUtilityPageEntry =
			_layoutUtilityPageEntryService.copyLayoutUtilityPageEntry(
				_group.getGroupId(),
				layoutUtilityPageEntry.getLayoutUtilityPageEntryId(),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertNotEquals(
			layoutUtilityPageEntry.getPreviewFileEntryId(),
			copiedLayoutUtilityPageEntry.getPreviewFileEntryId());

		FileEntry copiedFileEntry = _portletFileRepository.getPortletFileEntry(
			copiedLayoutUtilityPageEntry.getPreviewFileEntryId());

		Assert.assertEquals(
			copiedLayoutUtilityPageEntry.getLayoutUtilityPageEntryId() +
				"_preview.png",
			copiedFileEntry.getFileName());
		Assert.assertEquals(
			fileEntry.getExtension(), copiedFileEntry.getExtension());

		FileVersion copiedFileVersion = copiedFileEntry.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, copiedFileVersion.getStatus());
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDeleteDefaultLayoutUtilityPageEntryWithNoPermissions()
		throws Exception {

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryService.addLayoutUtilityPageEntry(
				RandomTestUtil.randomString(), _group.getGroupId(), 0, 0, true,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(), 0,
				_serviceContext);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_layoutUtilityPageEntryService.deleteLayoutUtilityPageEntry(
				layoutUtilityPageEntry.getLayoutUtilityPageEntryId());
		}
	}

	@Test
	public void testDeleteDefaultLayoutUtilityPageEntryWithPermissions()
		throws Exception {

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryService.addLayoutUtilityPageEntry(
				RandomTestUtil.randomString(), _group.getGroupId(), 0, 0, true,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(), 0,
				_serviceContext);

		_resourcePermissionLocalService.addResourcePermission(
			_group.getCompanyId(), Group.class.getName(),
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			_role.getRoleId(),
			LayoutUtilityPageActionKeys.
				ASSIGN_DEFAULT_LAYOUT_UTILITY_PAGE_ENTRY);

		_resourcePermissionLocalService.setResourcePermissions(
			_group.getCompanyId(), LayoutUtilityPageEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(
				layoutUtilityPageEntry.getLayoutUtilityPageEntryId()),
			_role.getRoleId(), new String[] {ActionKeys.DELETE});

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_layoutUtilityPageEntryService.deleteLayoutUtilityPageEntry(
				layoutUtilityPageEntry.getLayoutUtilityPageEntryId());
		}

		LayoutUtilityPageEntry persistedLayoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.fetchLayoutUtilityPageEntry(
				layoutUtilityPageEntry.getLayoutUtilityPageEntryId());

		Assert.assertNull(persistedLayoutUtilityPageEntry);
	}

	@Test
	public void testDeleteNondefaultLayoutUtilityPageEntryWithNoAssignPermissions()
		throws Exception {

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryService.addLayoutUtilityPageEntry(
				RandomTestUtil.randomString(), _group.getGroupId(), 0, 0, false,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(), 0,
				_serviceContext);

		_resourcePermissionLocalService.setResourcePermissions(
			_group.getCompanyId(), LayoutUtilityPageEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(
				layoutUtilityPageEntry.getLayoutUtilityPageEntryId()),
			_role.getRoleId(), new String[] {ActionKeys.DELETE});

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_layoutUtilityPageEntryService.deleteLayoutUtilityPageEntry(
				layoutUtilityPageEntry.getLayoutUtilityPageEntryId());
		}

		LayoutUtilityPageEntry persistedLayoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.fetchLayoutUtilityPageEntry(
				layoutUtilityPageEntry.getLayoutUtilityPageEntryId());

		Assert.assertNull(persistedLayoutUtilityPageEntry);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testSetDefaultLayoutUtilityPageEntryWithNoPermissions()
		throws Exception {

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryService.addLayoutUtilityPageEntry(
				RandomTestUtil.randomString(), _group.getGroupId(), 0, 0, true,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(), 0,
				_serviceContext);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_layoutUtilityPageEntryService.setDefaultLayoutUtilityPageEntry(
				layoutUtilityPageEntry.getLayoutUtilityPageEntryId());
		}
	}

	@Test
	public void testSetDefaultLayoutUtilityPageEntryWithPermissions()
		throws Exception {

		String type = RandomTestUtil.randomString();

		LayoutUtilityPageEntry layoutUtilityPageEntry1 =
			_layoutUtilityPageEntryService.addLayoutUtilityPageEntry(
				RandomTestUtil.randomString(), _group.getGroupId(), 0, 0, true,
				RandomTestUtil.randomString(), type, 0, _serviceContext);

		LayoutUtilityPageEntry layoutUtilityPageEntry2 =
			_layoutUtilityPageEntryService.addLayoutUtilityPageEntry(
				RandomTestUtil.randomString(), _group.getGroupId(), 0, 0, false,
				RandomTestUtil.randomString(), type, 0, _serviceContext);

		_resourcePermissionLocalService.addResourcePermission(
			_group.getCompanyId(), Group.class.getName(),
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			_role.getRoleId(),
			LayoutUtilityPageActionKeys.
				ASSIGN_DEFAULT_LAYOUT_UTILITY_PAGE_ENTRY);

		_resourcePermissionLocalService.setResourcePermissions(
			_group.getCompanyId(), LayoutUtilityPageEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(
				layoutUtilityPageEntry1.getLayoutUtilityPageEntryId()),
			_role.getRoleId(), new String[] {ActionKeys.UPDATE});
		_resourcePermissionLocalService.setResourcePermissions(
			_group.getCompanyId(), LayoutUtilityPageEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(
				layoutUtilityPageEntry2.getLayoutUtilityPageEntryId()),
			_role.getRoleId(), new String[] {ActionKeys.UPDATE});

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_layoutUtilityPageEntryService.setDefaultLayoutUtilityPageEntry(
				layoutUtilityPageEntry2.getLayoutUtilityPageEntryId());
		}

		LayoutUtilityPageEntry persistedLayoutUtilityPageEntry1 =
			_layoutUtilityPageEntryLocalService.getLayoutUtilityPageEntry(
				layoutUtilityPageEntry1.getLayoutUtilityPageEntryId());

		Assert.assertFalse(
			persistedLayoutUtilityPageEntry1.isDefaultLayoutUtilityPageEntry());

		LayoutUtilityPageEntry persistedLayoutUtilityPageEntry2 =
			_layoutUtilityPageEntryLocalService.getLayoutUtilityPageEntry(
				layoutUtilityPageEntry2.getLayoutUtilityPageEntryId());

		Assert.assertTrue(
			persistedLayoutUtilityPageEntry2.isDefaultLayoutUtilityPageEntry());
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUnsetDefaultLayoutUtilityPageEntryWithNoPermissions()
		throws Exception {

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryService.addLayoutUtilityPageEntry(
				RandomTestUtil.randomString(), _group.getGroupId(), 0, 0, true,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(), 0,
				_serviceContext);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_layoutUtilityPageEntryService.unsetDefaultLayoutUtilityPageEntry(
				layoutUtilityPageEntry.getLayoutUtilityPageEntryId());
		}
	}

	@Test
	public void testUnsetDefaultLayoutUtilityPageEntryWithPermissions()
		throws Exception {

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryService.addLayoutUtilityPageEntry(
				RandomTestUtil.randomString(), _group.getGroupId(), 0, 0, true,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(), 0,
				_serviceContext);

		_resourcePermissionLocalService.addResourcePermission(
			_group.getCompanyId(), Group.class.getName(),
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			_role.getRoleId(),
			LayoutUtilityPageActionKeys.
				ASSIGN_DEFAULT_LAYOUT_UTILITY_PAGE_ENTRY);

		_resourcePermissionLocalService.setResourcePermissions(
			_group.getCompanyId(), LayoutUtilityPageEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(
				layoutUtilityPageEntry.getLayoutUtilityPageEntryId()),
			_role.getRoleId(), new String[] {ActionKeys.UPDATE});

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_layoutUtilityPageEntryService.unsetDefaultLayoutUtilityPageEntry(
				layoutUtilityPageEntry.getLayoutUtilityPageEntryId());
		}

		LayoutUtilityPageEntry persistedLayoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.getLayoutUtilityPageEntry(
				layoutUtilityPageEntry.getLayoutUtilityPageEntryId());

		Assert.assertFalse(
			persistedLayoutUtilityPageEntry.isDefaultLayoutUtilityPageEntry());
	}

	@Test
	public void testUpdateLayoutUtilityPageEntry() throws Exception {
		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryService.addLayoutUtilityPageEntry(
				RandomTestUtil.randomString(), _group.getGroupId(), 0, 0, true,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(), 0,
				_serviceContext);

		layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.updateLayoutUtilityPageEntry(
				layoutUtilityPageEntry.getLayoutUtilityPageEntryId(),
				RandomTestUtil.randomString());

		Layout draftLayout = _layoutLocalService.fetchDraftLayout(
			layoutUtilityPageEntry.getPlid());

		Assert.assertEquals(
			layoutUtilityPageEntry.getName(),
			draftLayout.getName(LocaleUtil.getSiteDefault()));

		Layout layout = _layoutLocalService.getLayout(
			layoutUtilityPageEntry.getPlid());

		Assert.assertEquals(
			layoutUtilityPageEntry.getName(),
			layout.getName(LocaleUtil.getSiteDefault()));
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Language _language;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Inject
	private LayoutUtilityPageEntryService _layoutUtilityPageEntryService;

	@Inject
	private Portal _portal;

	@Inject
	private PortletFileRepository _portletFileRepository;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@DeleteAfterTestRun
	private Role _role;

	@Inject
	private RoleLocalService _roleLocalService;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}