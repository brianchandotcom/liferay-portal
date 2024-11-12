/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.handler.test;

import com.liferay.adaptive.media.handler.AMRequestHandler;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Manuel Rives
 */
@RunWith(Arquillian.class)
public class AMImageRequestHandlerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_amImageConfigurationEntry =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				_group.getCompanyId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), _UUID,
				HashMapBuilder.put(
					"max-height", String.valueOf(RandomTestUtil.randomInt())
				).put(
					"max-width", String.valueOf(RandomTestUtil.randomInt())
				).build());
	}

	@After
	public void tearDown() throws Exception {
		_amImageConfigurationHelper.disableAMImageConfigurationEntry(
			_group.getCompanyId(), _UUID);
		_amImageConfigurationHelper.deleteAMImageConfigurationEntry(
			_group.getCompanyId(), _UUID);
	}

	@Test
	public void testAccessImageWithoutViewPermission() throws Exception {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			SystemBundleUtil.getBundleContext(),
			(Class<AMRequestHandler<?>>)(Class<?>)AMRequestHandler.class,
			"(adaptive.media.handler.pattern=*)",
			(serviceReference, emitter) -> emitter.emit(
				(String)serviceReference.getProperty(
					"adaptive.media.handler.pattern")));

		AMRequestHandler<?> amRequestHandler = _serviceTrackerMap.getService(
			"image");

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		FileEntry fileEntry = _addFileEntryWithoutViewPermission();

		mockHttpServletRequest.setPathInfo(
			StringBundler.concat(
				"/image/", fileEntry.getFileEntryId(), "/", _UUID, "/",
				fileEntry.getFileName()));

		Assert.assertNotNull(
			amRequestHandler.handleRequest(mockHttpServletRequest));
	}

	private FileEntry _addFileEntryWithoutViewPermission() throws Exception {
		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			null, null, null, null, new ServiceContext());

		Role guestRole = _roleLocalService.getRole(
			_group.getCompanyId(), RoleConstants.GUEST);

		_resourcePermissionLocalService.removeResourcePermission(
			_group.getCompanyId(), DLFileEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(fileEntry),
			guestRole.getRoleId(), ActionKeys.VIEW);

		return fileEntry;
	}

	private static final String _UUID = RandomTestUtil.randomString();

	private AMImageConfigurationEntry _amImageConfigurationEntry;

	@Inject
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	private ServiceTrackerMap<String, AMRequestHandler<?>> _serviceTrackerMap;

}