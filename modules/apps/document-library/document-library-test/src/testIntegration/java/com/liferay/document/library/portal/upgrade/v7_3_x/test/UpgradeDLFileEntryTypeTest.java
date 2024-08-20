/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.portal.upgrade.v7_3_x.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.v7_3_x.UpgradeDLFileEntryType;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class UpgradeDLFileEntryTypeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, UpgradeDLFileEntryTypeTest.class.getSimpleName(), null);
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testUpgrade() throws Exception {
		DDMStructure productionDDMStructure;
		DLFileEntryType productionDLFileEntryType;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			String fileEntryTypeKey = StringUtil.randomString();

			productionDDMStructure = _addDMMStructure(fileEntryTypeKey);

			productionDLFileEntryType =
				_dlFileEntryTypeLocalService.addFileEntryType(
					null, TestPropsValues.getUserId(), _group.getGroupId(),
					productionDDMStructure.getStructureId(), fileEntryTypeKey,
					HashMapBuilder.put(
						LocaleUtil.US, StringUtil.randomString()
					).build(),
					HashMapBuilder.put(
						LocaleUtil.US, StringUtil.randomString()
					).build(),
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_SCOPE_DEFAULT,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));
		}

		DLFileEntryType ctCollectionDLFileEntryType;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			DDMStructure ctCollectionDDMStructure =
				_ddmStructureLocalService.getStructure(
					productionDLFileEntryType.getDataDefinitionId());

			_ddmStructureLocalService.updateDDMStructure(
				ctCollectionDDMStructure);

			ctCollectionDLFileEntryType =
				_dlFileEntryTypeLocalService.getDLFileEntryType(
					productionDLFileEntryType.getFileEntryTypeId());

			ctCollectionDLFileEntryType.setDataDefinitionId(0);

			ctCollectionDLFileEntryType =
				_dlFileEntryTypeLocalService.updateDLFileEntryType(
					ctCollectionDLFileEntryType);
		}

		_runUpgrade();

		_multiVMPool.clear();

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			ctCollectionDLFileEntryType =
				_dlFileEntryTypeLocalService.getDLFileEntryType(
					ctCollectionDLFileEntryType.getFileEntryTypeId());
		}

		Assert.assertNotEquals(
			0, ctCollectionDLFileEntryType.getDataDefinitionId());
		Assert.assertEquals(
			productionDLFileEntryType.getDataDefinitionId(),
			ctCollectionDLFileEntryType.getDataDefinitionId());
	}

	private DDMStructure _addDMMStructure(String ddmStructureKey)
		throws Exception {

		DDMForm ddmForm = new DDMForm();

		ddmForm.setDefaultLocale(LocaleUtil.US);
		ddmForm.addAvailableLocale(LocaleUtil.US);
		ddmForm.addDDMFormField(
			new DDMFormField("field", DDMFormFieldTypeConstants.TEXT));

		return _ddmStructureLocalService.addStructure(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			_classNameLocalService.getClassNameId(DLFileEntryMetadata.class),
			ddmStructureKey,
			HashMapBuilder.put(
				LocaleUtil.US, StringUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.US, StringUtil.randomString()
			).build(),
			ddmForm, _ddm.getDefaultDDMFormLayout(ddmForm),
			StorageType.DEFAULT.toString(), DDMStructureConstants.TYPE_DEFAULT,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = new UpgradeDLFileEntryType() {

			@Override
			protected UpgradeStep[] getPreUpgradeSteps() {
				return new UpgradeStep[0];
			}

		};

		upgradeProcess.upgrade();
	}

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@Inject
	private DDM _ddm;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@Inject
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MultiVMPool _multiVMPool;

}