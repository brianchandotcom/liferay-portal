/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.service.FragmentCompositionLocalService;
import com.liferay.fragment.test.util.FragmentCompositionTestUtil;
import com.liferay.fragment.test.util.FragmentTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class DeleteFragmentCompositionPreviewMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = _groupLocalService.fetchGroup(TestPropsValues.getGroupId());
	}

	@Test
	@TestInfo("LPD-73558")
	public void testDoProcessAction() throws Exception {
		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentComposition fragmentComposition =
			FragmentCompositionTestUtil.addFragmentComposition(
				fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString());

		Repository repository = _portletFileRepository.addPortletRepository(
			_group.getGroupId(), FragmentPortletKeys.FRAGMENT,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		FileEntry fileEntry = _portletFileRepository.addPortletFileEntry(
			_group.getGroupId(), TestPropsValues.getUserId(),
			FragmentComposition.class.getName(),
			fragmentComposition.getFragmentCompositionId(),
			FragmentPortletKeys.FRAGMENT, repository.getDlFolderId(),
			new byte[0], "test.png", ContentTypes.IMAGE_PNG, false);

		fragmentComposition =
			_fragmentCompositionLocalService.updateFragmentComposition(
				fragmentComposition.getFragmentCompositionId(),
				fileEntry.getFileEntryId());

		ReflectionTestUtil.invoke(
			_deleteFragmentCompositionPreviewMVCActionCommand,
			"doProcessAction",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			_getMockLiferayPortletActionRequest(
				fragmentComposition, TestPropsValues.getUser()),
			new MockLiferayPortletActionResponse());

		long previewFileEntryId = fragmentComposition.getPreviewFileEntryId();

		Assert.assertThrows(
			NoSuchFileEntryException.class,
			() -> _portletFileRepository.getPortletFileEntry(
				previewFileEntryId));

		fragmentComposition =
			_fragmentCompositionLocalService.fetchFragmentComposition(
				fragmentComposition.getFragmentCompositionId());

		Assert.assertEquals(0, fragmentComposition.getPreviewFileEntryId());
	}

	@Test
	@TestInfo("LPD-73558")
	public void testDoProcessActionWithoutPermissions() throws Exception {
		User user = UserTestUtil.addGroupUser(_group, RoleConstants.POWER_USER);

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				TestPropsValues.getGroupId());

		FragmentComposition fragmentComposition =
			FragmentCompositionTestUtil.addFragmentComposition(
				fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, PermissionCheckerFactoryUtil.create(user))) {

			Assert.assertThrows(
				PrincipalException.class,
				() -> ReflectionTestUtil.invoke(
					_deleteFragmentCompositionPreviewMVCActionCommand,
					"doProcessAction",
					new Class<?>[] {ActionRequest.class, ActionResponse.class},
					_getMockLiferayPortletActionRequest(
						fragmentComposition, user),
					new MockLiferayPortletActionResponse()));
		}
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
			FragmentComposition fragmentComposition, User user)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		ThemeDisplay themeDisplay = _getThemeDisplay();

		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));
		themeDisplay.setUser(user);

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockLiferayPortletActionRequest.setParameter(
			"fragmentCompositionId",
			String.valueOf(fragmentComposition.getFragmentCompositionId()));

		return mockLiferayPortletActionRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Layout controlPanelLayout = LayoutLocalServiceUtil.getLayout(
			PortalUtil.getControlPanelPlid(_group.getCompanyId()));

		themeDisplay.setLayout(controlPanelLayout);

		LayoutSet layoutSet = _group.getPublicLayoutSet();

		themeDisplay.setLookAndFeel(layoutSet.getTheme(), null);

		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());

		return themeDisplay;
	}

	@Inject(
		filter = "mvc.command.name=/fragment/delete_fragment_composition_preview"
	)
	private MVCActionCommand _deleteFragmentCompositionPreviewMVCActionCommand;

	@Inject
	private FragmentCompositionLocalService _fragmentCompositionLocalService;

	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private PortletFileRepository _portletFileRepository;

}