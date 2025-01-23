/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.internal.upgrade.v3_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseCTUpgradeProcessTestCase;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.DDMStorageEngineManager;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.model.LayoutSEOEntryCustomMetaTag;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.sql.Connection;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class LayoutSEOEntryCustomMetaTagUpgradeProcessTest
	extends BaseCTUpgradeProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_db = DBManagerUtil.getDB();

		_addDDMStorageIdColumn();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_dropDDMStorageIdColumn();
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testUpgrade() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(_group.getGroupId());

		Group companyGroup = _groupLocalService.getCompanyGroup(
			TestPropsValues.getCompanyId());

		Class<?> clazz = getClass();

		_defaultDDMStructureHelper.addDDMStructures(
			TestPropsValues.getUserId(), companyGroup.getGroupId(),
			_classNameLocalService.getClassNameId(LayoutSEOEntry.class),
			clazz.getClassLoader(),
			"com/liferay/layout/seo/internal/upgrade/v3_0_0/test/dependencies" +
				"/custom-meta-tags-structure.xml",
			serviceContext);

		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.updateLayoutSEOEntry(
				layout.getUserId(), layout.getGroupId(),
				layout.isPrivateLayout(), layout.getLayoutId(), false,
				Collections.emptyMap(),
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId()));

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			companyGroup.getGroupId(),
			_classNameLocalService.getClassNameId(
				LayoutSEOEntry.class.getName()),
			"custom-meta-tags");

		long ddmStorageId = _ddmStorageEngineManager.create(
			layoutSEOEntry.getCompanyId(), ddmStructure.getStructureId(),
			_createDDMFormValues(ddmStructure.getDDMForm()), serviceContext);

		_updateDDMStorageId(ddmStorageId, layoutSEOEntry.getLayoutSEOEntryId());

		runUpgrade();

		Assert.assertNull(
			_ddmStructureLocalService.fetchStructure(
				ddmStructure.getStructureId()));

		List<LayoutSEOEntryCustomMetaTag> layoutSEOEntryCustomMetaTags =
			_layoutSEOEntryLocalService.getLayoutSEOEntryCustomMetaTags(
				layoutSEOEntry.getGroupId(),
				layoutSEOEntry.getLayoutSEOEntryId());

		Assert.assertFalse(layoutSEOEntryCustomMetaTags.isEmpty());
		Assert.assertEquals(
			layoutSEOEntryCustomMetaTags.toString(), 2,
			layoutSEOEntryCustomMetaTags.size());

		LayoutSEOEntryCustomMetaTag firstLayoutSEOEntryCustomMetaTag =
			layoutSEOEntryCustomMetaTags.get(0);

		Assert.assertEquals(
			"property1", firstLayoutSEOEntryCustomMetaTag.getProperty());
		Assert.assertEquals(
			"content1",
			firstLayoutSEOEntryCustomMetaTag.getContent(
				LocaleUtil.getSiteDefault()));
		Assert.assertEquals(
			"contenido1",
			firstLayoutSEOEntryCustomMetaTag.getContent(LocaleUtil.SPAIN));

		LayoutSEOEntryCustomMetaTag secondLayoutSEOEntryCustomMetaTag =
			layoutSEOEntryCustomMetaTags.get(1);

		Assert.assertEquals(
			"property2", secondLayoutSEOEntryCustomMetaTag.getProperty());
		Assert.assertEquals(
			"content2",
			secondLayoutSEOEntryCustomMetaTag.getContent(
				LocaleUtil.getSiteDefault()));
		Assert.assertEquals(
			"contenido2",
			secondLayoutSEOEntryCustomMetaTag.getContent(LocaleUtil.SPAIN));
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		return _layoutSEOEntryLocalService.updateLayoutSEOEntry(
			layout.getUserId(), layout.getGroupId(), layout.isPrivateLayout(),
			layout.getLayoutId(), false, Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	@Override
	protected CTService<?> getCTService() {
		return _layoutSEOEntryLocalService;
	}

	@Override
	protected void runUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();
		}
	}

	@Override
	protected CTModel<?> updateCTModel(CTModel<?> ctModel) throws Exception {
		LayoutSEOEntry layoutSEOEntry = (LayoutSEOEntry)ctModel;

		return _layoutSEOEntryLocalService.updateLayoutSEOEntry(
			layoutSEOEntry.getUserId(), layoutSEOEntry.getGroupId(),
			layoutSEOEntry.isPrivateLayout(), layoutSEOEntry.getLayoutId(),
			true, RandomTestUtil.randomLocaleStringMap(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	private static void _addDDMStorageIdColumn() throws Exception {
		_ddmStorageIdColumnsAdded = false;

		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			if (!dbInspector.hasColumn("LayoutSEOEntry", "DDMStorageId")) {
				_db.runSQLTemplate(
					"alter table LayoutSEOEntry add DDMStorageId LONG;", true);

				_ddmStorageIdColumnsAdded = true;
			}
		}
	}

	private static void _dropDDMStorageIdColumn() throws Exception {
		if (!_ddmStorageIdColumnsAdded) {
			return;
		}

		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			if (dbInspector.hasColumn("LayoutSEOEntry", "DDMStorageId")) {
				_db.runSQLTemplate(
					"alter table LayoutSEOEntry drop column DDMStorageId;",
					true);
			}
		}
	}

	private DDMFormValues _createDDMFormValues(DDMForm ddmForm) {
		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue firstPropertyDDMFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"property", new UnlocalizedValue("property1"));

		Value firstValue = new LocalizedValue(LocaleUtil.getSiteDefault());

		firstValue.addString(LocaleUtil.getSiteDefault(), "content1");
		firstValue.addString(LocaleUtil.SPAIN, "contenido1");

		firstPropertyDDMFormFieldValue.setNestedDDMFormFields(
			ListUtil.fromArray(
				DDMFormValuesTestUtil.createDDMFormFieldValue(
					"content", firstValue)));

		ddmFormValues.addDDMFormFieldValue(firstPropertyDDMFormFieldValue);

		DDMFormFieldValue secondPropertyDDMFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"property", new UnlocalizedValue("property2"));

		Value secondValue = new LocalizedValue(LocaleUtil.getSiteDefault());

		secondValue.addString(LocaleUtil.getSiteDefault(), "content2");
		secondValue.addString(LocaleUtil.SPAIN, "contenido2");

		secondPropertyDDMFormFieldValue.setNestedDDMFormFields(
			ListUtil.fromArray(
				DDMFormValuesTestUtil.createDDMFormFieldValue(
					"content", secondValue)));

		ddmFormValues.addDDMFormFieldValue(secondPropertyDDMFormFieldValue);

		ddmFormValues.setAvailableLocales(
			new LinkedHashSet<>(
				Arrays.asList(LocaleUtil.getSiteDefault(), LocaleUtil.SPAIN)));

		return ddmFormValues;
	}

	private void _updateDDMStorageId(long ddmStorageId, long layoutSEOEntryId)
		throws Exception {

		_db.runSQL(
			StringBundler.concat(
				"update LayoutSEOEntry set DDMStorageId = ", ddmStorageId,
				" where layoutSEOEntryId = ", layoutSEOEntryId));

		_multiVMPool.clear();
	}

	private static final String _CLASS_NAME =
		"com.liferay.layout.seo.internal.upgrade.v3_0_0." +
			"LayoutSEOEntryCustomMetaTagUpgradeProcess";

	private static DB _db;
	private static boolean _ddmStorageIdColumnsAdded;

	@Inject(
		filter = "(&(component.name=com.liferay.layout.seo.internal.upgrade.registry.LayoutSEOServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private DDMStorageEngineManager _ddmStorageEngineManager;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@Inject
	private DefaultDDMStructureHelper _defaultDDMStructureHelper;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutSEOEntryLocalService _layoutSEOEntryLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

}