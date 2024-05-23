/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.conflict.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.conflict.CTEntryConflictHelper;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTEntryTable;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.test.util.JournalFolderFixture;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class CTEntryConflictHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, CTColumnResolutionMaxTest.class.getSimpleName(), null);

		_group = GroupTestUtil.addGroup();

		Bundle bundle = FrameworkUtil.getBundle(
			CTEntryConflictHelperTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_journalFolderCTEntryConflictHelper =
			new JournalFolderCTEntryConflictHelper();

		_serviceRegistration = bundleContext.registerService(
			CTEntryConflictHelper.class, _journalFolderCTEntryConflictHelper,
			null);
	}

	@After
	public void tearDown() throws Exception {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Test
	public void testHasDeletionModificationConflict() throws Exception {
		JournalFolderFixture journalFolderFixture = new JournalFolderFixture(
			_journalFolderLocalService);

		JournalFolder journalFolder = journalFolderFixture.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			journalFolderFixture.addFolder(
				_group.getGroupId(), RandomTestUtil.randomString());

			_journalFolderLocalService.updateFolder(
				journalFolder.getUserId(), journalFolder.getGroupId(),
				journalFolder.getFolderId(), journalFolder.getParentFolderId(),
				journalFolder.getName(), RandomTestUtil.randomString(), false,
				ServiceContextTestUtil.getServiceContext());
		}

		_ctCollectionLocalService.checkConflicts(_ctCollection);

		Assert.assertEquals(
			_ctEntryLocalService.dslQueryCount(
				DSLQueryFactoryUtil.countDistinct(
					CTEntryTable.INSTANCE.ctEntryId
				).from(
					CTEntryTable.INSTANCE
				).where(
					CTEntryTable.INSTANCE.ctCollectionId.eq(
						_ctCollection.getCtCollectionId()
					).and(
						CTEntryTable.INSTANCE.modelClassNameId.eq(
							_classNameLocalService.getClassNameId(
								JournalFolder.class))
					).and(
						CTEntryTable.INSTANCE.changeType.eq(
							CTConstants.CT_CHANGE_TYPE_MODIFICATION)
					)
				)),
			_journalFolderCTEntryConflictHelper.getDeletionModificationCount());
	}

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTEntryLocalService _ctEntryLocalService;

	@Inject
	private static JournalFolderLocalService _journalFolderLocalService;

	private CTCollection _ctCollection;
	private Group _group;
	private JournalFolderCTEntryConflictHelper
		_journalFolderCTEntryConflictHelper;
	private ServiceRegistration<CTEntryConflictHelper> _serviceRegistration;

	private class JournalFolderCTEntryConflictHelper
		implements CTEntryConflictHelper {

		public int getDeletionModificationCount() {
			return _deletionModificationCount;
		}

		@Override
		public Class<? extends CTModel<?>> getModelClass() {
			return JournalFolder.class;
		}

		@Override
		public boolean hasDeletionModificationConflict(
			CTEntry ctEntry, long targetCTCollectionId) {

			_deletionModificationCount++;

			Assert.assertEquals(
				CTConstants.CT_CHANGE_TYPE_MODIFICATION,
				ctEntry.getChangeType());

			return false;
		}

		private int _deletionModificationCount;

	}

}