/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.document.library.opener.onedrive.web.internal.lock.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.osgi.util.service.OSGiServiceUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.lock.LockListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class DLFileEntryLockListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testOnAfterExpireWithCancelCheckOutPolicy() throws Exception {
		testWithOneDriveConfigurationDisabled(
			() -> testWithCancelCheckOutAsPolicy(
				() -> {
					FileEntry fileEntry = _dlAppLocalService.addFileEntry(
						null, TestPropsValues.getUserId(), _group.getGroupId(),
						DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
						RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN,
						TestDataConstants.TEST_BYTE_ARRAY, null, null,
						ServiceContextTestUtil.getServiceContext(
							_group, TestPropsValues.getUserId()));

					_dlFileEntryLocalService.checkOutFileEntry(
						TestPropsValues.getUserId(), fileEntry.getFileEntryId(),
						ServiceContextTestUtil.getServiceContext());

					_lockListener.onAfterExpire(
						String.valueOf(fileEntry.getFileEntryId()));

					FileEntry finalFileEntry = _dlAppLocalService.getFileEntry(
						fileEntry.getFileEntryId());

					Assert.assertFalse(finalFileEntry.isCheckedOut());

					FileVersion latestFileVersion =
						finalFileEntry.getLatestFileVersion();

					Assert.assertEquals(
						fileEntry.getVersion(), latestFileVersion.getVersion());
				}));
	}

	@Test
	public void testOnAfterExpireWithCheckInPolicy() throws Exception {
		testWithOneDriveConfigurationDisabled(
			() -> testWithCheckInAsPolicy(
				() -> {
					FileEntry fileEntry = _dlAppLocalService.addFileEntry(
						null, TestPropsValues.getUserId(), _group.getGroupId(),
						DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
						RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN,
						TestDataConstants.TEST_BYTE_ARRAY, null, null,
						ServiceContextTestUtil.getServiceContext(
							_group, TestPropsValues.getUserId()));

					fileEntry.getVersion();

					_dlFileEntryLocalService.checkOutFileEntry(
						TestPropsValues.getUserId(), fileEntry.getFileEntryId(),
						ServiceContextTestUtil.getServiceContext());

					_lockListener.onAfterExpire(
						String.valueOf(fileEntry.getFileEntryId()));

					FileEntry finalFileEntry = _dlAppLocalService.getFileEntry(
						fileEntry.getFileEntryId());

					Assert.assertFalse(finalFileEntry.isCheckedOut());

					FileVersion latestFileVersion =
						finalFileEntry.getLatestFileVersion();

					Assert.assertNotEquals(
						fileEntry.getVersion(), latestFileVersion.getVersion());
				}));
	}

	protected void testWithCancelCheckOutAsPolicy(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"DL_FILE_ENTRY_LOCK_POLICY", 0)) {

			unsafeRunnable.run();
		}
	}

	protected void testWithCheckInAsPolicy(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"DL_FILE_ENTRY_LOCK_POLICY", 1)) {

			unsafeRunnable.run();
		}
	}

	protected void testWithOneDriveConfigurationDisabled(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(
			ConfigurationTemporarySwapper.class);

		BundleContext bundleContext = bundle.getBundleContext();

		String pid =
			"com.liferay.document.library.opener.onedrive.web.internal." +
				"configuration.DLOneDriveCompanyConfiguration";

		Configuration configuration = OSGiServiceUtil.callService(
			bundleContext, ConfigurationAdmin.class,
			configurationAdmin -> configurationAdmin.getConfiguration(
				pid, StringPool.QUESTION));

		if (configuration.getProperties() == null) {
			unsafeRunnable.run();

			return;
		}

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(pid, null)) {

			unsafeRunnable.run();
		}
	}

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "component.name=com.liferay.document.library.opener.onedrive.web.internal.lock.DLFileEntryLockListener"
	)
	private LockListener _lockListener;

}