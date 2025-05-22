/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.upgrade.v3_2_11.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeService;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.Arrays;
import java.util.Collections;

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
public class DLFileEntryTypesDDMStructureUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		_globalGroup = company.getGroup();

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testUpgrade() throws Exception {
		DLFileEntryType dlFileEntryType = _addFileEntryType();

		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			dlFileEntryType.getDataDefinitionId());

		ddmStructure.setType(DDMStructureConstants.TYPE_AUTO);

		_ddmStructureLocalService.updateDDMStructure(ddmStructure);

		_runUpgrade();

		_assertDDMStructure(
			_ddmStructureLocalService.getDDMStructure(
				dlFileEntryType.getDataDefinitionId()),
			DDMStructureConstants.TYPE_DEFAULT);

		for (String structureKey :
				Arrays.asList("DL_VIDEO_EXTERNAL_SHORTCUT", "GOOGLE_DOCS")) {

			_assertDDMStructure(
				_ddmStructureLocalService.getStructure(
					_globalGroup.getGroupId(),
					_portal.getClassNameId(DLFileEntryMetadata.class.getName()),
					structureKey),
				DDMStructureConstants.TYPE_AUTO);
		}
	}

	private DLFileEntryType _addFileEntryType() throws Exception {
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), DLFileEntryMetadata.class.getName());

		return _dlFileEntryTypeService.addFileEntryType(
			null, _group.getGroupId(), ddmStructure.getStructureId(), null,
			Collections.singletonMap(LocaleUtil.US, "New File Entry Type"),
			Collections.singletonMap(LocaleUtil.US, "New File Entry Type"),
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId()));
	}

	private void _assertDDMStructure(
		DDMStructure ddmStructure, int expectedType) {

		Assert.assertEquals(expectedType, ddmStructure.getType());
	}

	private void _runUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			_entityCache.clearCache();
			_multiVMPool.clear();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.document.library.internal.upgrade.v3_2_11." +
			"DLFileEntryTypesDDMStructureUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.document.library.internal.upgrade.registry.DLServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@Inject
	private DLFileEntryTypeService _dlFileEntryTypeService;

	@Inject
	private EntityCache _entityCache;

	private Group _globalGroup;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private Portal _portal;

}