/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.trash.web.internal.portlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBFolderLocalService;
import com.liferay.knowledge.base.test.util.KBTestUtil;
import com.liferay.portal.kernel.exception.NoSuchClassNameException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.constants.MVCRenderConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.test.MockLiferayPortletContext;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.service.TrashEntryLocalService;

import jakarta.portlet.Portlet;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Akhash Ramprakash
 */
@RunWith(Arquillian.class)
public class TrashPortletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		_kbFolder = KBTestUtil.addKBFolder(_group.getGroupId());

		_kbArticle = KBTestUtil.addKBArticleWithWorkflow(
			TestPropsValues.getUserId(), true, _kbFolder.getKbFolderId(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_kbFolderLocalService.moveKBFolderToTrash(
			TestPropsValues.getUserId(), _kbFolder.getKbFolderId());

		_trashEntry = _trashEntryLocalService.getEntry(
			KBFolder.class.getName(), _kbFolder.getKbFolderId());
	}

	@Test
	public void testRender() throws Exception {
		Assert.assertEquals(
			Collections.emptySet(),
			_render(
				HashMapBuilder.put(
					"classNameId", _getClassNameId(KBArticle.class)
				).put(
					"classPK", String.valueOf(_kbArticle.getResourcePrimKey())
				).build(),
				_group.getGroupId()));

		KBFolder kbFolder = KBTestUtil.addKBFolder(_group.getGroupId());

		Assert.assertEquals(
			Collections.emptySet(),
			_render(
				HashMapBuilder.put(
					"classNameId", _getClassNameId(KBFolder.class)
				).put(
					"classPK", String.valueOf(_kbFolder.getKbFolderId())
				).put(
					"containerModelId", String.valueOf(kbFolder.getKbFolderId())
				).put(
					"mvcPath", "/view_container_model.jsp"
				).build(),
				_group.getGroupId()));

		Assert.assertEquals(
			Collections.emptySet(),
			_render(
				HashMapBuilder.put(
					"trashEntryId", String.valueOf(_trashEntry.getEntryId())
				).build(),
				_group.getGroupId()));
	}

	@Test
	public void testRenderFromDifferentGroup() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Assert.assertEquals(
			_mustHavePermissionKeys,
			_render(
				HashMapBuilder.put(
					"classNameId", _getClassNameId(KBArticle.class)
				).put(
					"classPK", String.valueOf(_kbArticle.getResourcePrimKey())
				).build(),
				group.getGroupId()));
		Assert.assertEquals(
			_mustHavePermissionKeys,
			_render(
				HashMapBuilder.put(
					"classNameId", _getClassNameId(KBFolder.class)
				).put(
					"classPK", String.valueOf(_kbFolder.getKbFolderId())
				).build(),
				group.getGroupId()));
		Assert.assertEquals(
			_mustHavePermissionKeys,
			_render(
				HashMapBuilder.put(
					"trashEntryId", String.valueOf(_trashEntry.getEntryId())
				).build(),
				group.getGroupId()));
	}

	@Test
	public void testRenderWithParametersFromDifferentGroup() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Assert.assertEquals(
			_mustHavePermissionKeys,
			_render(
				HashMapBuilder.put(
					"classNameId", _getClassNameId(KBFolder.class)
				).put(
					"classPK",
					() -> {
						KBFolder kbFolder = _addTrashedKBFolder(
							ServiceContextTestUtil.getServiceContext(
								group.getGroupId()));

						return String.valueOf(kbFolder.getKbFolderId());
					}
				).put(
					"trashEntryId", String.valueOf(_trashEntry.getEntryId())
				).build(),
				_group.getGroupId()));
		Assert.assertEquals(
			_mustHavePermissionKeys,
			_render(
				HashMapBuilder.put(
					"classNameId", _getClassNameId(KBFolder.class)
				).put(
					"classPK", String.valueOf(_kbFolder.getKbFolderId())
				).put(
					"containerModelId",
					() -> {
						KBFolder kbFolder = KBTestUtil.addKBFolder(
							group.getGroupId());

						return String.valueOf(kbFolder.getKbFolderId());
					}
				).put(
					"mvcPath", "/view_container_model.jsp"
				).build(),
				_group.getGroupId()));
	}

	@Test
	public void testRenderWithoutTrashEntry() throws Exception {
		Assert.assertEquals(
			Collections.singleton(NoSuchClassNameException.class.getName()),
			_render(
				HashMapBuilder.put(
					"classNameId", String.valueOf(-1)
				).put(
					"classPK", String.valueOf(_kbFolder.getKbFolderId())
				).build(),
				_group.getGroupId()));

		KBFolder kbFolder = KBTestUtil.addKBFolder(_group.getGroupId());

		Assert.assertEquals(
			_mustHavePermissionKeys,
			_render(
				HashMapBuilder.put(
					"classNameId", _getClassNameId(KBFolder.class)
				).put(
					"classPK", String.valueOf(kbFolder.getKbFolderId())
				).build(),
				_group.getGroupId()));
	}

	@Test
	public void testRenderWithoutViewPermission() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);

		KBFolder kbFolder = _addTrashedKBFolder(serviceContext);

		TrashEntry trashEntry = _trashEntryLocalService.getEntry(
			KBFolder.class.getName(), kbFolder.getKbFolderId());

		Assert.assertEquals(
			_mustHavePermissionKeys,
			_render(
				HashMapBuilder.put(
					"trashEntryId", String.valueOf(trashEntry.getEntryId())
				).build(),
				PermissionCheckerFactoryUtil.create(UserTestUtil.addUser()),
				_group.getGroupId()));
	}

	private KBFolder _addTrashedKBFolder(ServiceContext serviceContext)
		throws Exception {

		KBFolder kbFolder = KBTestUtil.addKBFolder(
			serviceContext.getScopeGroupId(),
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);

		_kbFolderLocalService.moveKBFolderToTrash(
			TestPropsValues.getUserId(), kbFolder.getKbFolderId());

		return kbFolder;
	}

	private String _getClassNameId(Class<?> clazz) {
		return String.valueOf(_portal.getClassNameId(clazz.getName()));
	}

	private Set<String> _render(
			Map<String, String> parameterMap, long scopeGroupId)
		throws Exception {

		return _render(
			parameterMap, PermissionThreadLocal.getPermissionChecker(),
			scopeGroupId);
	}

	private Set<String> _render(
			Map<String, String> parameterMap,
			PermissionChecker permissionChecker, long scopeGroupId)
		throws Exception {

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		for (String path :
				new String[] {
					"/error.jsp", "/view_container_model.jsp",
					"/view_content.jsp"
				}) {

			mockLiferayPortletRenderRequest.setAttribute(
				MVCRenderConstants.
					PORTLET_CONTEXT_OVERRIDE_REQUEST_ATTIBUTE_NAME_PREFIX +
						path,
				new MockLiferayPortletContext(path));
		}

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setPermissionChecker(permissionChecker);
		themeDisplay.setScopeGroupId(scopeGroupId);

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockLiferayPortletRenderRequest.setParameter(
			"mvcPath", "/view_content.jsp");

		for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
			mockLiferayPortletRenderRequest.setParameter(
				entry.getKey(), entry.getValue());
		}

		_portlet.render(
			mockLiferayPortletRenderRequest,
			new MockLiferayPortletRenderResponse());

		return SessionErrors.keySet(mockLiferayPortletRenderRequest);
	}

	private static final Set<String> _mustHavePermissionKeys =
		Collections.singleton(
			PrincipalException.MustHavePermission.class.getName());

	private Group _group;
	private KBArticle _kbArticle;
	private KBFolder _kbFolder;

	@Inject
	private KBFolderLocalService _kbFolderLocalService;

	@Inject
	private Portal _portal;

	@Inject(
		filter = "component.name=com.liferay.trash.web.internal.portlet.TrashPortlet"
	)
	private Portlet _portlet;

	private TrashEntry _trashEntry;

	@Inject
	private TrashEntryLocalService _trashEntryLocalService;

}