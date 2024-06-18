/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v2_10_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.test.util.FragmentTestUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

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
public class FragmentCollectionUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_fragmentCollection = FragmentTestUtil.addFragmentCollection(
			_group.getGroupId());
	}

	@Test
	public void testUpgrade() throws Exception {
		DLFolder dlFolder = _dlFolderLocalService.getFolder(
			_fragmentCollection.getResourcesFolderId(true));

		Assert.assertEquals(
			_fragmentCollection.getFragmentCollectionKey(), dlFolder.getName());

		dlFolder.setName(
			String.valueOf(_fragmentCollection.getFragmentCollectionId()));

		dlFolder = _dlFolderLocalService.updateDLFolder(dlFolder);

		Assert.assertEquals(
			String.valueOf(_fragmentCollection.getFragmentCollectionId()),
			dlFolder.getName());

		_runUpgrade();

		dlFolder = _dlFolderLocalService.getFolder(dlFolder.getFolderId());

		Assert.assertEquals(
			_fragmentCollection.getFragmentCollectionKey(), dlFolder.getName());
	}

	@Test
	public void testUpgradeWithoutPortletRepository() throws Exception {
		Assert.assertEquals(0, _fragmentCollection.getResourcesFolderId(false));

		_portletFileRepository.deletePortletRepository(
			_fragmentCollection.getGroupId(), FragmentPortletKeys.FRAGMENT);

		Assert.assertNull(
			_portletFileRepository.fetchPortletRepository(
				_fragmentCollection.getGroupId(),
				FragmentPortletKeys.FRAGMENT));

		_runUpgrade();

		Assert.assertNull(
			_portletFileRepository.fetchPortletRepository(
				_fragmentCollection.getGroupId(),
				FragmentPortletKeys.FRAGMENT));

		Assert.assertEquals(0, _fragmentCollection.getResourcesFolderId(false));
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess[] upgradeProcesses = UpgradeTestUtil.getUpgradeSteps(
			_upgradeStepRegistrator, new Version(2, 10, 1));

		for (UpgradeProcess upgradeProcess : upgradeProcesses) {
			upgradeProcess.upgrade();
		}

		_multiVMPool.clear();
	}

	@Inject(
		filter = "(&(component.name=com.liferay.fragment.internal.upgrade.registry.FragmentServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private DLFolderLocalService _dlFolderLocalService;

	private FragmentCollection _fragmentCollection;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private PortletFileRepository _portletFileRepository;

}