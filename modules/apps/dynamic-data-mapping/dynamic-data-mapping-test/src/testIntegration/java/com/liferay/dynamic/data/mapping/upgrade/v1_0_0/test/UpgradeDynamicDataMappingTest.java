/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.dynamic.data.mapping.upgrade.v1_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStorageLink;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplateVersion;
import com.liferay.dynamic.data.mapping.service.DDMContentLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateVersionLocalServiceUtil;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoRow;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoRowLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoValueLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 * @author Inácio Nery
 */
@Ignore
@RunWith(Arquillian.class)
public class UpgradeDynamicDataMappingTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_timestamp = new Timestamp(System.currentTimeMillis());

		setUpClassNameIds();
		setUpModelResourceNames();
		setUpPrimaryKeys();
		setUpUpgradeDynamicDataMapping();
	}

	@After
	public void tearDown() throws Exception {
		deleteStructure(_structureId);

		deleteStructure(_parentStructureId);

		deleteTemplate(_templateId);

		deleteContent(_contentId);

		deleteStorageLink(_storageLinkId);
	}

	@Test
	public void testCreateStructureLayout() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			null, read("ddm-structure-text-field.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();

		getDDMFormLayout(_structureId, DDMStructureConstants.VERSION_DEFAULT);
	}

	@Test
	public void testCreateStructureVersion() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			null, read("ddm-structure-text-field.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();

		getStructureVersion(
			_structureId, DDMStructureConstants.VERSION_DEFAULT);
	}

	@Test
	public void testCreateTemplateVersion() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			null, read("ddm-structure-text-field.xsd"), "xml");

		addTemplate(
			_templateId, _structureId, null,
			read("ddm-template-text-field.ftl"), "ftl",
			DDMTemplateConstants.TEMPLATE_MODE_CREATE);

		_upgradeDynamicDataMapping.upgrade();

		getTemplateVersion(_templateId, DDMTemplateConstants.VERSION_DEFAULT);
	}

	@Test
	public void testUpgradeExpandoWithLocalized() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-localized.xsd"), "expando");

		long classPK = RandomTestUtil.randomLong();

		ExpandoTable expandoTable = ExpandoTableLocalServiceUtil.addTable(
			_group.getCompanyId(), _classNameIdExpandoStorageAdapter,
			String.valueOf(_structureId));

		ExpandoRow expandoRow = ExpandoRowLocalServiceUtil.addRow(
			expandoTable.getTableId(), classPK);

		addStorageLink(_storageLinkId, expandoRow.getRowId(), _structureId);

		ExpandoColumn expandoColumnName =
			ExpandoColumnLocalServiceUtil.addColumn(
				expandoTable.getTableId(), "Name",
				ExpandoColumnConstants.STRING_LOCALIZED);

		ExpandoValueLocalServiceUtil.addValue(
			expandoTable.getClassNameId(), expandoTable.getTableId(),
			expandoColumnName.getColumnId(), classPK,
			read("expando-localized-field.xsd"));

		ExpandoColumn expandoColumnFieldsDisplay =
			ExpandoColumnLocalServiceUtil.addColumn(
				expandoTable.getTableId(), "_fieldsDisplay",
				ExpandoColumnConstants.STRING_LOCALIZED);

		ExpandoValueLocalServiceUtil.addValue(
			expandoTable.getClassNameId(), expandoTable.getTableId(),
			expandoColumnFieldsDisplay.getColumnId(), classPK,
			read("expando-localized-field-display.xsd"));

		_upgradeDynamicDataMapping.upgrade();

		String expectedData = read("ddm-content-localized.json");

		String actualData = getContentData(expandoRow.getRowId());

		JSONAssert.assertEquals(expectedData, actualData, false);
	}

	@Test
	public void testUpgradeExpandoWithNestedAndRepeatableFields()
		throws Exception {

		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-nested-repeatable.xsd"), "expando");

		long classPK = RandomTestUtil.randomLong();

		ExpandoTable expandoTable = ExpandoTableLocalServiceUtil.addTable(
			_group.getCompanyId(), _classNameIdExpandoStorageAdapter,
			String.valueOf(_structureId));

		ExpandoRow expandoRow = ExpandoRowLocalServiceUtil.addRow(
			expandoTable.getTableId(), classPK);

		addStorageLink(_storageLinkId, expandoRow.getRowId(), _structureId);

		ExpandoColumn expandoColumnTextNestedUpper =
			ExpandoColumnLocalServiceUtil.addColumn(
				expandoTable.getTableId(), "TextNestedUpper",
				ExpandoColumnConstants.STRING_LOCALIZED);

		ExpandoValueLocalServiceUtil.addValue(
			expandoTable.getClassNameId(), expandoTable.getTableId(),
			expandoColumnTextNestedUpper.getColumnId(), classPK,
			read("expando-nested-repeatable-field-1.xsd"));

		ExpandoColumn expandoColumnTextParent =
			ExpandoColumnLocalServiceUtil.addColumn(
				expandoTable.getTableId(), "TextParent",
				ExpandoColumnConstants.STRING_LOCALIZED);

		ExpandoValueLocalServiceUtil.addValue(
			expandoTable.getClassNameId(), expandoTable.getTableId(),
			expandoColumnTextParent.getColumnId(), classPK,
			read("expando-nested-repeatable-field-2.xsd"));

		ExpandoColumn expandoColumnTextRepeateable =
			ExpandoColumnLocalServiceUtil.addColumn(
				expandoTable.getTableId(), "TextRepeateable",
				ExpandoColumnConstants.STRING_ARRAY_LOCALIZED);

		ExpandoValueLocalServiceUtil.addValue(
			expandoTable.getClassNameId(), expandoTable.getTableId(),
			expandoColumnTextRepeateable.getColumnId(), classPK,
			read("expando-nested-repeatable-field-3.xsd"));

		ExpandoColumn expandoColumnTextNestedBottom =
			ExpandoColumnLocalServiceUtil.addColumn(
				expandoTable.getTableId(), "TextNestedBottom",
				ExpandoColumnConstants.STRING_LOCALIZED);

		ExpandoValueLocalServiceUtil.addValue(
			expandoTable.getClassNameId(), expandoTable.getTableId(),
			expandoColumnTextNestedBottom.getColumnId(), classPK,
			read("expando-nested-repeatable-field-4.xsd"));

		ExpandoColumn expandoColumnFieldsDisplay =
			ExpandoColumnLocalServiceUtil.addColumn(
				expandoTable.getTableId(), "_fieldsDisplay",
				ExpandoColumnConstants.STRING_LOCALIZED);

		ExpandoValueLocalServiceUtil.addValue(
			expandoTable.getClassNameId(), expandoTable.getTableId(),
			expandoColumnFieldsDisplay.getColumnId(), classPK,
			read("expando-nested-repeatable-field-display.xsd"));

		_upgradeDynamicDataMapping.upgrade();

		String expectedData = read("ddm-content-nested-repeatable.json");

		String actualData = getContentData(expandoRow.getRowId());

		JSONAssert.assertEquals(expectedData, actualData, false);
	}

	@Test
	public void testUpgradeExpandoWithRepeatableFields() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-text-repeatable-field.xsd"), "expando");

		long classPK = RandomTestUtil.randomLong();

		ExpandoTable expandoTable = ExpandoTableLocalServiceUtil.addTable(
			_group.getCompanyId(), _classNameIdExpandoStorageAdapter,
			String.valueOf(_structureId));

		ExpandoRow expandoRow = ExpandoRowLocalServiceUtil.addRow(
			expandoTable.getTableId(), classPK);

		addStorageLink(_storageLinkId, expandoRow.getRowId(), _structureId);

		ExpandoColumn expandoColumnTextRepeatable =
			ExpandoColumnLocalServiceUtil.addColumn(
				expandoTable.getTableId(), "TextRepeatable",
				ExpandoColumnConstants.STRING_ARRAY_LOCALIZED);

		ExpandoValueLocalServiceUtil.addValue(
			expandoTable.getClassNameId(), expandoTable.getTableId(),
			expandoColumnTextRepeatable.getColumnId(), classPK,
			read("expando-value-text-repeatable-field.xsd"));

		ExpandoColumn expandoColumnFieldsDisplay =
			ExpandoColumnLocalServiceUtil.addColumn(
				expandoTable.getTableId(), "_fieldsDisplay",
				ExpandoColumnConstants.STRING_LOCALIZED);

		ExpandoValueLocalServiceUtil.addValue(
			expandoTable.getClassNameId(), expandoTable.getTableId(),
			expandoColumnFieldsDisplay.getColumnId(), classPK,
			read("expando-value-text-repeatable-field-display.xsd"));

		_upgradeDynamicDataMapping.upgrade();

		String expectedData = read("ddm-content-text-repeatable-field.json");

		String actualData = getContentData(expandoRow.getRowId());

		JSONAssert.assertEquals(expectedData, actualData, false);
	}

	@Test
	public void testUpgradeExpandoWithTransientRepeatableParent()
		throws Exception {

		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-transient-repeatable-parent.xsd"), "expando");

		long classPK = RandomTestUtil.randomLong();

		ExpandoTable expandoTable = ExpandoTableLocalServiceUtil.addTable(
			_group.getCompanyId(), _classNameIdExpandoStorageAdapter,
			String.valueOf(_structureId));

		ExpandoRow expandoRow = ExpandoRowLocalServiceUtil.addRow(
			expandoTable.getTableId(), classPK);

		addStorageLink(_storageLinkId, expandoRow.getRowId(), _structureId);

		ExpandoColumn expandoColumnText =
			ExpandoColumnLocalServiceUtil.addColumn(
				expandoTable.getTableId(), "Text",
				ExpandoColumnConstants.STRING_ARRAY_LOCALIZED);

		ExpandoValueLocalServiceUtil.addValue(
			expandoTable.getClassNameId(), expandoTable.getTableId(),
			expandoColumnText.getColumnId(), classPK,
			read("expando-transient-repeatable-parent-field.xsd"));

		ExpandoColumn expandoColumnFieldsDisplay =
			ExpandoColumnLocalServiceUtil.addColumn(
				expandoTable.getTableId(), "_fieldsDisplay",
				ExpandoColumnConstants.STRING_LOCALIZED);

		ExpandoValueLocalServiceUtil.addValue(
			expandoTable.getClassNameId(), expandoTable.getTableId(),
			expandoColumnFieldsDisplay.getColumnId(), classPK,
			read("expando-transient-repeatable-parent-field-display.xsd"));

		_upgradeDynamicDataMapping.upgrade();

		String expectedData = read(
			"ddm-content-transient-repeatable-parent.json");

		String actualData = getContentData(expandoRow.getRowId());

		JSONAssert.assertEquals(expectedData, actualData, false);
	}

	@Test
	public void testUpgradeHierarchyStructures() throws Exception {
		addStructure(
			_parentStructureId,
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-parent.xsd"), "xml");

		addStructure(
			_structureId, _parentStructureId,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-child.xsd"), "xml");

		long recordSetId = RandomTestUtil.randomLong();

		addRecordSet(recordSetId, _structureId);

		addContent(_contentId, read("ddm-content-hierarchy.xsd"));

		addStorageLink(_storageLinkId, _contentId, _structureId);

		long recordId = RandomTestUtil.randomLong();

		addRecord(recordId, recordSetId, _contentId);

		_upgradeDynamicDataMapping.upgrade();

		String content = getContentData(_contentId);

		String expectedContent = read("ddm-content-hierarchy.json");

		JSONAssert.assertEquals(expectedContent, content, false);

		deleteRecord(recordId);

		deleteRecordSet(recordSetId);
	}

	@Test
	public void testUpgradeStructureLocalized() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-localized.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();

		String expectedDefinition = read("ddm-structure-localized.json");

		String actualDefinition = getStructureDefinition(_structureId);

		JSONAssert.assertEquals(expectedDefinition, actualDefinition, false);
	}

	@Test
	public void testUpgradeStructurePermissions() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-boolean-field.xsd"), "xml");

		long resourcePermissionId = RandomTestUtil.randomLong();

		addResourcePermission(
			resourcePermissionId, _structureId, DDMStructure.class.getName());

		_upgradeDynamicDataMapping.upgrade();

		String expectedResourceName = getStructureModelResourceName(
			_classNameIdDDLRecordSet);

		ResourcePermission resourcePermission =
			ResourcePermissionLocalServiceUtil.getResourcePermission(
				resourcePermissionId);

		String actualResourceName = resourcePermission.getName();

		Assert.assertEquals(expectedResourceName, actualResourceName);
	}

	@Test
	public void testUpgradeStructureReferencesDueUpdatedStructureFieldName()
		throws Exception {

		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-references-field-name.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();

		String expectedDefinition = read(
			"ddm-structure-references-field-name.json");

		String actualDefinition = getStructureDefinition(_structureId);

		JSONAssert.assertEquals(expectedDefinition, actualDefinition, false);
	}

	@Test
	public void testUpgradeStructureWithBooleanField() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-boolean-field.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();

		String expectedDefinition = read("ddm-structure-boolean-field.json");

		String actualDefinition = getStructureDefinition(_structureId);

		JSONAssert.assertEquals(expectedDefinition, actualDefinition, false);
	}

	@Test
	public void testUpgradeStructureWithDateField() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-date-field.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();

		String expectedDefinition = read("ddm-structure-date-field.json");

		String actualDefinition = getStructureDefinition(_structureId);

		JSONAssert.assertEquals(expectedDefinition, actualDefinition, false);
	}

	@Test
	public void testUpgradeStructureWithDecimalField() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-decimal-field.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();

		String expectedDefinition = read("ddm-structure-decimal-field.json");

		String actualDefinition = getStructureDefinition(_structureId);

		JSONAssert.assertEquals(expectedDefinition, actualDefinition, false);
	}

	@Test
	public void testUpgradeStructureWithDocumentLibraryField()
		throws Exception {

		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-document-library-field.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();

		String expectedDefinition = read(
			"ddm-structure-document-library-field.json");

		String actualDefinition = getStructureDefinition(_structureId);

		JSONAssert.assertEquals(expectedDefinition, actualDefinition, false);
	}

	@Test
	public void testUpgradeStructureWithFileUploadField() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-file-upload-field.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();

		String expectedDefinition = read(
			"ddm-structure-file-upload-field.json");

		String actualDefinition = getStructureDefinition(_structureId);

		JSONAssert.assertEquals(expectedDefinition, actualDefinition, false);
	}

	@Test
	public void testUpgradeStructureWithGeolocationField() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-geolocation-field.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();

		String expectedDefinition = read(
			"ddm-structure-geolocation-field.json");

		String actualDefinition = getStructureDefinition(_structureId);

		JSONAssert.assertEquals(expectedDefinition, actualDefinition, false);
	}

	@Test
	public void testUpgradeStructureWithHtmlField() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-html-field.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();

		String expectedDefinition = read("ddm-structure-html-field.json");

		String actualDefinition = getStructureDefinition(_structureId);

		JSONAssert.assertEquals(expectedDefinition, actualDefinition, false);
	}

	@Test
	public void testUpgradeStructureWithImageField() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-image-field.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();

		String expectedDefinition = read("ddm-structure-image-field.json");

		String actualDefinition = getStructureDefinition(_structureId);

		JSONAssert.assertEquals(expectedDefinition, actualDefinition, false);
	}

	@Test
	public void testUpgradeStructureWithIntegerField() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-integer-field.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();

		String expectedDefinition = read("ddm-structure-integer-field.json");

		String actualDefinition = getStructureDefinition(_structureId);

		JSONAssert.assertEquals(expectedDefinition, actualDefinition, false);
	}

	@Test(expected = UpgradeException.class)
	public void testUpgradeStructureWithInvalidFieldNameCharacters1()
		throws Exception {

		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-invalid-field-name-characters-1.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();
	}

	@Test(expected = UpgradeException.class)
	public void testUpgradeStructureWithInvalidFieldNameCharacters2()
		throws Exception {

		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-invalid-field-name-characters-2.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();
	}

	@Test(expected = UpgradeException.class)
	public void testUpgradeStructureWithInvalidFieldNameCharacters3()
		throws Exception {

		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-invalid-field-name-characters-3.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();
	}

	@Test(expected = UpgradeException.class)
	public void testUpgradeStructureWithInvalidFieldNameCharacters4()
		throws Exception {

		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-invalid-field-name-characters-4.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();
	}

	@Test(expected = UpgradeException.class)
	public void testUpgradeStructureWithInvalidFieldNameCharacters5()
		throws Exception {

		addStructure(
			_parentStructureId,
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-parent.xsd"), "xml");

		addStructure(
			_structureId, _parentStructureId,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-invalid-field-name-characters-5.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();
	}

	@Test
	public void testUpgradeStructureWithLinkToPageField() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-link-to-page-field.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();

		String expectedDefinition = read(
			"ddm-structure-link-to-page-field.json");

		String actualDefinition = getStructureDefinition(_structureId);

		JSONAssert.assertEquals(expectedDefinition, actualDefinition, false);
	}

	@Test
	public void testUpgradeStructureWithNestedFields() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-nested-fields.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();

		String expectedDefinition = read("ddm-structure-nested-fields.json");

		String actualDefinition = getStructureDefinition(_structureId);

		JSONAssert.assertEquals(expectedDefinition, actualDefinition, false);
	}

	@Test
	public void testUpgradeStructureWithNumberField() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-number-field.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();

		String expectedDefinition = read("ddm-structure-number-field.json");

		String actualDefinition = getStructureDefinition(_structureId);

		JSONAssert.assertEquals(expectedDefinition, actualDefinition, false);
	}

	@Test
	public void testUpgradeStructureWithParentStructure() throws Exception {
		addStructure(
			_parentStructureId,
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-parent.xsd"), "xml");

		addStructure(
			_structureId, _parentStructureId,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-child.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();

		String expectedDefinition = read("ddm-structure-parent.json");

		String actualDefinition = getStructureDefinition(_parentStructureId);

		JSONAssert.assertEquals(expectedDefinition, actualDefinition, false);

		expectedDefinition = read("ddm-structure-child.json");

		actualDefinition = getStructureDefinition(_structureId);

		JSONAssert.assertEquals(expectedDefinition, actualDefinition, false);
	}

	@Test
	public void testUpgradeStructureWithRadioField() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-radio-field.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();

		String expectedDefinition = read("ddm-structure-radio-field.json");

		String actualDefinition = getStructureDefinition(_structureId);

		JSONAssert.assertEquals(expectedDefinition, actualDefinition, false);
	}

	@Test(expected = UpgradeException.class)
	public void testUpgradeStructureWithSameStructure() throws Exception {
		addStructure(
			_parentStructureId,
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-parent.xsd"), "xml");

		addStructure(
			_structureId, _parentStructureId,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-parent.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();
	}

	@Test
	public void testUpgradeStructureWithSelectField() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-select-field.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();

		String expectedDefinition = read("ddm-structure-select-field.json");

		String actualDefinition = getStructureDefinition(_structureId);

		JSONAssert.assertEquals(expectedDefinition, actualDefinition, false);
	}

	@Test
	public void testUpgradeStructureWithSeparatorField() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-separator-field.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();

		String expectedDefinition = read("ddm-structure-separator-field.json");

		String actualDefinition = getStructureDefinition(_structureId);

		JSONAssert.assertEquals(expectedDefinition, actualDefinition, false);
	}

	@Test
	public void testUpgradeStructureWithTextAreaField() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-text-area-field.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();

		String expectedDefinition = read("ddm-structure-text-area-field.json");

		String actualDefinition = getStructureDefinition(_structureId);

		JSONAssert.assertEquals(expectedDefinition, actualDefinition, false);
	}

	@Test
	public void testUpgradeStructureWithTextField() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-text-field.xsd"), "xml");

		_upgradeDynamicDataMapping.upgrade();

		String expectedDefinition = read("ddm-structure-text-field.json");

		String actualDefinition = getStructureDefinition(_structureId);

		JSONAssert.assertEquals(expectedDefinition, actualDefinition, false);
	}

	@Test
	public void testUpgradeTemplateFreemarkerScriptDateFields()
		throws Exception {

		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-with-two-date-fields.xsd"), "xml");

		addTemplate(
			_templateId, _structureId, null,
			read("ddm-template-with-two-date-fields.ftl"), "ftl",
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY);

		_upgradeDynamicDataMapping.upgrade();

		String actualDefinition = getTemplateScript(_templateId);

		String[] dateFieldNames = {"date1", "date2"};

		StringBundler sb = null;

		for (String dateFieldName : dateFieldNames) {

			// Assign statement

			sb = new StringBundler(5);

			sb.append("<#assign ");
			sb.append(dateFieldName);
			sb.append("_Data = getterUtil.getString(");
			sb.append(dateFieldName);
			sb.append(".getData()) />");

			Assert.assertTrue(
				actualDefinition, actualDefinition.contains(sb.toString()));

			// If statement

			sb = new StringBundler(3);

			sb.append("<#if validator.isNotNull(");
			sb.append(dateFieldName);
			sb.append("_Data)>");

			Assert.assertTrue(
				actualDefinition, actualDefinition.contains(sb.toString()));

			// Date parse statement

			sb = new StringBundler(5);

			sb.append("<#assign ");
			sb.append(dateFieldName);
			sb.append("_DateObj = dateUtil.parseDate(\"yyyy-MM-dd\", ");
			sb.append(dateFieldName);
			sb.append("_Data, locale) />");

			Assert.assertTrue(
				actualDefinition, actualDefinition.contains(sb.toString()));
		}
	}

	@Test
	public void testUpgradeTemplateFreemarkerScriptUTCDateFields()
		throws Exception {

		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-date-field.xsd"), "xml");

		addTemplate(
			_templateId, _structureId, null,
			read("ddm-template-with-utc-date-field.ftl"), "ftl",
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY);

		_upgradeDynamicDataMapping.upgrade();

		String actualDefinition = getTemplateScript(_templateId);

		String dateFieldName = "date1";

		// Date getDate statement

		StringBundler sb = new StringBundler(3);

		sb.append("dateUtil.getDate(");
		sb.append(dateFieldName);
		sb.append("_DateObj, \"dd MMM yyyy - HH:mm:ss\", locale)");

		Assert.assertTrue(
			actualDefinition, actualDefinition.contains(sb.toString()));
	}

	@Test
	public void testUpgradeTemplatePermissions() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			null, read("ddm-structure-text-field.xsd"), "xml");

		addTemplate(
			_templateId, _structureId, null,
			read("ddm-template-text-field.ftl"), "ftl",
			DDMTemplateConstants.TEMPLATE_MODE_CREATE);

		long resourcePermissionId = RandomTestUtil.randomLong();

		addResourcePermission(
			resourcePermissionId, _templateId, DDMTemplate.class.getName());

		_upgradeDynamicDataMapping.upgrade();

		String expectedResourceName = getTemplateModelResourceName(
			_classNameIdDDLRecordSet);

		ResourcePermission resourcePermission =
			ResourcePermissionLocalServiceUtil.getResourcePermission(
				resourcePermissionId);

		String actualResourceName = resourcePermission.getName();

		Assert.assertEquals(expectedResourceName, actualResourceName);
	}

	@Test
	public void testUpgradeTemplateReferencesDueUpdatedStructureFieldName1()
		throws Exception {

		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-references-field-name.xsd"), "xml");

		addTemplate(
			_templateId, _structureId, DDMTemplateConstants.VERSION_DEFAULT,
			read("ddm-template-references-field-name-1.ftl"), "ftl",
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY);

		_upgradeDynamicDataMapping.upgrade();

		String expectedDefinition = read(
			"ddm-template-references-valid-field-name-1.ftl");

		String actualDefinition = getTemplateScript(_templateId);

		Assert.assertEquals(expectedDefinition, actualDefinition);
	}

	@Test
	public void testUpgradeTemplateReferencesDueUpdatedStructureFieldName2()
		throws Exception {

		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-references-field-name.xsd"), "xml");

		addTemplate(
			_templateId, _structureId, DDMTemplateConstants.VERSION_DEFAULT,
			read("ddm-template-references-field-name-2.vm"), "ftl",
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY);

		_upgradeDynamicDataMapping.upgrade();

		String expectedDefinition = read(
			"ddm-template-references-valid-field-name-2.vm");

		String actualDefinition = getTemplateScript(_templateId);

		Assert.assertEquals(expectedDefinition, actualDefinition);
	}

	@Test
	public void testUpgradeTemplateVelocityScriptDateFields() throws Exception {
		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-with-two-date-fields.xsd"), "xml");

		addTemplate(
			_templateId, _structureId, null,
			read("ddm-template-with-two-date-fields.vm"), "vm",
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY);

		_upgradeDynamicDataMapping.upgrade();

		String actualDefinition = getTemplateScript(_templateId);

		String[] dateFieldNames = {"$date1", "$date2"};

		StringBundler sb = null;

		for (String dateFieldName : dateFieldNames) {

			// Assign statement

			sb = new StringBundler(5);

			sb.append("#set (");
			sb.append(dateFieldName);
			sb.append("_Data = $getterUtil.getString(");
			sb.append(dateFieldName);
			sb.append(".getData()))");

			Assert.assertTrue(
				actualDefinition, actualDefinition.contains(sb.toString()));

			// If statement

			sb = new StringBundler(3);

			sb.append("#if ($validator.isNotNull(");
			sb.append(dateFieldName);
			sb.append("_Data))");

			Assert.assertTrue(
				actualDefinition, actualDefinition.contains(sb.toString()));

			// Date parse statement

			sb = new StringBundler(5);

			sb.append("#set (");
			sb.append(dateFieldName);
			sb.append("_DateObj = $dateUtil.parseDate(\"yyyy-MM-dd\", ");
			sb.append(dateFieldName);
			sb.append("_Data, $locale))");

			Assert.assertTrue(
				actualDefinition, actualDefinition.contains(sb.toString()));
		}
	}

	@Test
	public void testUpgradeTemplateVelocityScriptDateUTCFields()
		throws Exception {

		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-date-field.xsd"), "xml");

		addTemplate(
			_templateId, _structureId, null,
			read("ddm-template-with-utc-date-field.vm"), "vm",
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY);

		_upgradeDynamicDataMapping.upgrade();

		String actualDefinition = getTemplateScript(_templateId);

		String dateFieldName = "$date1";

		// Date getDate statement

		StringBundler sb = new StringBundler(3);

		sb.append("$dateUtil.getDate(");
		sb.append(dateFieldName);
		sb.append("_DateObj, \"dd MMM yyyy - HH:mm:ss\", $locale)");

		Assert.assertTrue(
			actualDefinition, actualDefinition.contains(sb.toString()));
	}

	@Test
	public void testUpgradeXMLStorageAdapterWithNestedAndRepeatableFields()
		throws Exception {

		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-nested-repeatable.xsd"), "xml");

		addContent(_contentId, read("ddm-content-nested-repeatable.xsd"));

		addStorageLink(_storageLinkId, _contentId, _structureId);

		_upgradeDynamicDataMapping.upgrade();

		String expectedData = read("ddm-content-nested-repeatable.json");

		String actualData = getContentData(_contentId);

		JSONAssert.assertEquals(expectedData, actualData, false);
	}

	@Test
	public void testUpgradeXMLStorageAdapterWithRepeatableFields()
		throws Exception {

		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-text-repeatable-field.xsd"), "xml");

		addContent(_contentId, read("ddm-content-text-repeatable-field.xsd"));

		addStorageLink(_storageLinkId, _contentId, _structureId);

		_upgradeDynamicDataMapping.upgrade();

		String expectedData = read("ddm-content-text-repeatable-field.json");

		String actualData = getContentData(_contentId);

		JSONAssert.assertEquals(expectedData, actualData, false);
	}

	@Test
	public void testUpgradeXMLStorageAdapterWithSimpleFields()
		throws Exception {

		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-text-field.xsd"), "xml");

		addContent(_contentId, read("ddm-content-text-field.xsd"));

		addStorageLink(_storageLinkId, _contentId, _structureId);

		_upgradeDynamicDataMapping.upgrade();

		String expectedData = read("ddm-content-text-field.json");

		String actualData = getContentData(_contentId);

		JSONAssert.assertEquals(expectedData, actualData, false);
	}

	@Test
	public void testUpgradeXMLStorageAdapterWithTransientRepeatableParent()
		throws Exception {

		addStructure(
			_structureId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			DDMStructureConstants.VERSION_DEFAULT,
			read("ddm-structure-transient-repeatable-parent.xsd"), "xml");

		addContent(
			_contentId, read("ddm-content-transient-repeatable-parent.xsd"));

		addStorageLink(_storageLinkId, _contentId, _structureId);

		_upgradeDynamicDataMapping.upgrade();

		String expectedData = read(
			"ddm-content-transient-repeatable-parent.json");

		String actualData = getContentData(_contentId);

		JSONAssert.assertEquals(expectedData, actualData, false);
	}

	protected void addContent(long contentId, String data) throws Exception {
		StringBundler sb = new StringBundler(4);

		sb.append("insert into DDMContent (uuid_, contentId, groupId, ");
		sb.append("companyId, userId, userName, createDate, modifiedDate, ");
		sb.append("name, description, data_) values (?, ?, ?, ?, ?, ?, ?, ?, ");
		sb.append("?, ?, ?)");

		String sql = sb.toString();

		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, PortalUUIDUtil.generate());
			ps.setLong(2, contentId);
			ps.setLong(3, _group.getGroupId());
			ps.setLong(4, _group.getCompanyId());
			ps.setLong(5, TestPropsValues.getUserId());
			ps.setString(6, null);
			ps.setTimestamp(7, _timestamp);
			ps.setTimestamp(8, _timestamp);
			ps.setString(9, DDMStorageLink.class.getName());
			ps.setString(10, StringPool.BLANK);
			ps.setString(11, data);

			ps.executeUpdate();
		}
	}

	protected void addRecord(long recordId, long recordSetId, long storageId)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("insert into DDLRecord (uuid_, recordId, groupId, ");
		sb.append("companyId, userId, userName, versionUserId, ");
		sb.append("versionUserName, createDate, modifiedDate, DDMStorageId, ");
		sb.append("recordSetId, version, displayIndex) values (?, ?, ?, ?, ");
		sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

		String sql = sb.toString();

		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, PortalUUIDUtil.generate());
			ps.setLong(2, recordId);
			ps.setLong(3, _group.getGroupId());
			ps.setLong(4, _group.getCompanyId());
			ps.setLong(5, TestPropsValues.getUserId());
			ps.setString(6, null);
			ps.setLong(7, TestPropsValues.getUserId());
			ps.setString(8, null);
			ps.setTimestamp(9, _timestamp);
			ps.setTimestamp(10, _timestamp);
			ps.setLong(11, storageId);
			ps.setLong(12, recordSetId);
			ps.setString(13, "1.0");
			ps.setInt(14, 0);

			ps.executeUpdate();
		}

		long recordVersionId = RandomTestUtil.randomLong();

		addRecordVersion(recordVersionId, recordId, recordSetId, storageId);
	}

	protected void addRecordSet(long recordSetId, long structureId)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("insert into DDLRecordSet (uuid_, recordSetId, groupId, ");
		sb.append("companyId, userId, userName, createDate, modifiedDate, ");
		sb.append("DDMStructureId, recordSetKey, name, description, ");
		sb.append("minDisplayRows, scope) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ");
		sb.append("?, ?, ?, ?, ?) ");

		String sql = sb.toString();

		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, PortalUUIDUtil.generate());
			ps.setLong(2, recordSetId);
			ps.setLong(3, _group.getGroupId());
			ps.setLong(4, _group.getCompanyId());
			ps.setLong(5, TestPropsValues.getUserId());
			ps.setString(6, null);
			ps.setTimestamp(7, _timestamp);
			ps.setTimestamp(8, _timestamp);
			ps.setLong(9, structureId);
			ps.setString(10, null);
			ps.setString(11, StringUtil.randomString());
			ps.setString(12, StringPool.BLANK);
			ps.setInt(13, 10);
			ps.setInt(14, 0);

			ps.executeUpdate();
		}
	}

	protected void addRecordVersion(
			long recordVersionId, long recordId, long recordSetId,
			long storageId)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("insert into DDLRecordVersion (recordVersionId, groupId, ");
		sb.append("companyId, userId, userName, createDate, DDMStorageId, ");
		sb.append("recordSetId, recordId, version, displayIndex, status, ");
		sb.append("statusByUserId, statusByUserName, statusDate) values (?, ");
		sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

		String sql = sb.toString();

		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setLong(1, recordVersionId);
			ps.setLong(2, _group.getGroupId());
			ps.setLong(3, _group.getCompanyId());
			ps.setLong(4, TestPropsValues.getUserId());
			ps.setString(5, null);
			ps.setTimestamp(6, _timestamp);
			ps.setLong(7, storageId);
			ps.setLong(8, recordSetId);
			ps.setLong(9, recordId);
			ps.setString(10, "1.0");
			ps.setInt(11, 0);
			ps.setInt(12, 0);
			ps.setLong(13, TestPropsValues.getUserId());
			ps.setString(14, null);
			ps.setTimestamp(15, _timestamp);

			ps.executeUpdate();
		}
	}

	protected void addResourcePermission(
			long resourcePermissionId, long structureId, String name)
		throws Exception {

		StringBundler sb = new StringBundler(4);

		sb.append("insert into ResourcePermission (mvccVersion, ");
		sb.append("resourcePermissionId, companyId, name, scope,  primKey, ");
		sb.append("primKeyId, roleId, ownerId, actionIds ) values (?, ?, ?, ");
		sb.append("?, ?, ?, ?, ?, ?, ?)");

		String sql = sb.toString();

		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setLong(1, 0);
			ps.setLong(2, resourcePermissionId);
			ps.setLong(3, _group.getCompanyId());
			ps.setString(4, name);
			ps.setInt(5, ResourceConstants.SCOPE_INDIVIDUAL);
			ps.setString(6, String.valueOf(structureId));
			ps.setLong(7, structureId);
			ps.setLong(8, RandomTestUtil.randomLong());
			ps.setLong(9, TestPropsValues.getUserId());
			ps.setLong(10, 1);

			ps.executeUpdate();
		}
	}

	protected void addStorageLink(
			long storageLinkId, long classPK, long structureId)
		throws Exception {

		StringBundler sb = new StringBundler(2);

		sb.append("insert into DDMStorageLink (storageLinkId, companyId, ");
		sb.append("classNameId, classPK, structureId) values (?, ?, ?, ?, ?)");

		String sql = sb.toString();

		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setLong(1, storageLinkId);
			ps.setLong(2, _group.getCompanyId());
			ps.setLong(3, _classNameIdDDMContent);
			ps.setLong(4, classPK);
			ps.setLong(5, structureId);

			ps.executeUpdate();
		}
	}

	protected void addStructure(
			long structureId, long parentStructureId, String version,
			String definition, String storageType)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append("insert into DDMStructure (uuid_, structureId, groupId, ");
		sb.append("companyId, userId, userName, versionUserId, ");
		sb.append("versionUserName, createDate, modifiedDate, ");
		sb.append("parentStructureId, classNameId, structureKey, version, ");
		sb.append("name, description, definition, storageType, type_) values ");
		sb.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		String sql = sb.toString();

		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, PortalUUIDUtil.generate());
			ps.setLong(2, structureId);
			ps.setLong(3, _group.getGroupId());
			ps.setLong(4, _group.getCompanyId());
			ps.setLong(5, TestPropsValues.getUserId());
			ps.setString(6, null);
			ps.setLong(7, TestPropsValues.getUserId());
			ps.setString(8, null);
			ps.setTimestamp(9, _timestamp);
			ps.setTimestamp(10, _timestamp);
			ps.setLong(11, parentStructureId);
			ps.setLong(12, _classNameIdDDLRecordSet);
			ps.setString(13, StringUtil.randomString());
			ps.setString(14, version);
			ps.setString(15, StringUtil.randomString());
			ps.setString(16, StringPool.BLANK);
			ps.setString(17, definition);
			ps.setString(18, storageType);
			ps.setInt(19, DDMStructureConstants.TYPE_DEFAULT);

			ps.executeUpdate();
		}
	}

	protected void addTemplate(
			Long templateId, long structureId, String version, String script,
			String language, String type)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append("insert into DDMTemplate (uuid_, templateId, groupId, ");
		sb.append("companyId, userId, userName, versionUserId, ");
		sb.append("versionUserName, createDate, modifiedDate, classNameId, ");
		sb.append("classPK, resourceClassNameId, templateKey, version, name, ");
		sb.append("mode_, language, script, type_) values (?, ?, ?, ?, ?, ?, ");
		sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		String sql = sb.toString();

		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, PortalUUIDUtil.generate());
			ps.setLong(2, templateId);
			ps.setLong(3, _group.getGroupId());
			ps.setLong(4, _group.getCompanyId());
			ps.setLong(5, TestPropsValues.getUserId());
			ps.setString(6, null);
			ps.setLong(7, TestPropsValues.getUserId());
			ps.setString(8, null);
			ps.setTimestamp(9, _timestamp);
			ps.setTimestamp(10, _timestamp);
			ps.setLong(11, _classNameIdDDMStructure);
			ps.setLong(12, structureId);
			ps.setLong(13, _classNameIdDDLRecordSet);
			ps.setString(14, StringUtil.randomString());
			ps.setString(15, version);
			ps.setString(16, StringUtil.randomString());
			ps.setString(17, DDMTemplateConstants.TEMPLATE_MODE_CREATE);
			ps.setString(18, language);
			ps.setString(19, script);
			ps.setString(20, type);

			ps.executeUpdate();
		}
	}

	protected void deleteContent(long contentId) throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL("delete from DDMContent where contentId = " + contentId);
	}

	protected void deleteRecord(long recordId) throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL("delete from DDLRecordVersion where recordId = " + recordId);
		db.runSQL("delete from DDLRecord where recordId = " + recordId);
	}

	protected void deleteRecordSet(long recordSetId) throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL(
			"delete from DDLRecordSet where recordSetId = " + recordSetId);
	}

	protected void deleteStorageLink(long storageLinkId) throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL(
			"delete from DDMStorageLink where storageLinkId = " +
				storageLinkId);
	}

	protected void deleteStructure(long structureId) throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL(
			"delete from DDMStructure where structureId = " + structureId);
	}

	protected void deleteTemplate(long templateId) throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL("delete from DDMTemplate where templateId = " + templateId);
	}

	protected String getBasePath() {
		return "com/liferay/dynamic/data/mapping/dependencies/upgrade/v1_0_0/";
	}

	protected String getContentData(long contentId) throws Exception {
		DDMContent content = DDMContentLocalServiceUtil.getContent(contentId);

		return content.getData();
	}

	protected DDMFormLayout getDDMFormLayout(long structureId, String version)
		throws Exception {

		DDMStructureVersion structureVersion = getStructureVersion(
			structureId, version);

		DDMStructureLayout ddmStructureLayout =
			DDMStructureLayoutLocalServiceUtil.
				getStructureLayoutByStructureVersionId(
					structureVersion.getStructureVersionId());

		return ddmStructureLayout.getDDMFormLayout();
	}

	protected String getStructureDefinition(long structureId) throws Exception {
		DDMStructure structure = DDMStructureLocalServiceUtil.getStructure(
			structureId);

		return structure.getDefinition();
	}

	protected String getStructureModelResourceName(long classNameId)
		throws UpgradeException {

		String className = PortalUtil.getClassName(classNameId);

		String structureModelResourceName = _structureModelResourceNames.get(
			className);

		if (structureModelResourceName == null) {
			throw new UpgradeException(
				StringBundler.concat(
					"Model ", className, " does not support dynamic data ",
					"mapping structure permission checking"));
		}

		return structureModelResourceName;
	}

	protected DDMStructureVersion getStructureVersion(
			long structureId, String version)
		throws Exception {

		return DDMStructureVersionLocalServiceUtil.getStructureVersion(
			structureId, version);
	}

	protected String getTemplateModelResourceName(long classNameId)
		throws UpgradeException {

		String className = PortalUtil.getClassName(classNameId);

		String templateModelResourceName = _templateModelResourceNames.get(
			className);

		if (templateModelResourceName == null) {
			throw new UpgradeException(
				StringBundler.concat(
					"Model ", className, " does not support dynamic data ",
					"mapping template permission checking"));
		}

		return templateModelResourceName;
	}

	protected String getTemplateScript(long templateId) throws Exception {
		DDMTemplate template = DDMTemplateLocalServiceUtil.getTemplate(
			templateId);

		return template.getScript();
	}

	protected DDMTemplateVersion getTemplateVersion(
			long templateId, String version)
		throws Exception {

		return DDMTemplateVersionLocalServiceUtil.getTemplateVersion(
			templateId, version);
	}

	protected Map<Class<?>, UpgradeStep> mapByClass(
		UpgradeStep[] upgradeSteps) {

		Map<Class<?>, UpgradeStep> upgradeStepsMap = new HashMap<>();

		for (UpgradeStep upgradeStep : upgradeSteps) {
			upgradeStepsMap.put(upgradeStep.getClass(), upgradeStep);
		}

		return upgradeStepsMap;
	}

	protected String read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getClassLoader(), getBasePath() + fileName);
	}

	protected void setUpClassNameIds() {
		_classNameIdDDLRecordSet = PortalUtil.getClassNameId(
			"com.liferay.portlet.dynamicdatalists.model.DDLRecordSet");
		_classNameIdDDMStructure = PortalUtil.getClassNameId(
			"com.liferay.dynamic.data.mapping.model.DDMStructure");
		_classNameIdDDMContent = PortalUtil.getClassNameId(
			"com.liferay.dynamic.data.mapping.model.DDMContent");
		_classNameIdExpandoStorageAdapter = PortalUtil.getClassNameId(
			"com.liferay.portlet.dynamicdatamapping.storage." +
				"ExpandoStorageAdapter");
	}

	protected void setUpModelResourceNames() {
		_structureModelResourceNames.put(
			"com.liferay.document.library.kernel.model.DLFileEntryMetadata",
			ResourceActionsUtil.getCompositeModelName(
				"com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata",
				DDMStructure.class.getName()));

		_structureModelResourceNames.put(
			"com.liferay.document.library.kernel.util.RawMetadataProcessor",
			DDMStructure.class.getName());

		_structureModelResourceNames.put(
			"com.liferay.portlet.dynamicdatalists.model.DDLRecordSet",
			ResourceActionsUtil.getCompositeModelName(
				"com.liferay.dynamic.data.lists.model.DDLRecordSet",
				DDMStructure.class.getName()));

		_structureModelResourceNames.put(
			"com.liferay.portlet.journal.model.JournalArticle",
			ResourceActionsUtil.getCompositeModelName(
				"com.liferay.journal.model.JournalArticle",
				DDMStructure.class.getName()));

		_templateModelResourceNames.put(
			"com.liferay.portlet.display.template.PortletDisplayTemplate",
			DDMTemplate.class.getName());

		_templateModelResourceNames.put(
			"com.liferay.portlet.dynamicdatalists.model.DDLRecordSet",
			ResourceActionsUtil.getCompositeModelName(
				"com.liferay.dynamic.data.lists.model.DDLRecordSet",
				DDMTemplate.class.getName()));

		_templateModelResourceNames.put(
			"com.liferay.portlet.journal.model.JournalArticle",
			ResourceActionsUtil.getCompositeModelName(
				"com.liferay.journal.model.JournalArticle",
				DDMTemplate.class.getName()));
	}

	protected void setUpPrimaryKeys() {
		_structureId = RandomTestUtil.randomLong();
		_parentStructureId = RandomTestUtil.randomLong();
		_templateId = RandomTestUtil.randomLong();
		_storageLinkId = RandomTestUtil.randomLong();
		_contentId = RandomTestUtil.randomLong();
	}

	protected void setUpUpgradeDynamicDataMapping() {
		Registry registry = RegistryUtil.getRegistry();

		UpgradeStepRegistrator upgradeStepRegistror = registry.getService(
			"com.liferay.dynamic.data.mapping.internal.upgrade." +
				"DDMServiceUpgrade");

		upgradeStepRegistror.register(
			new UpgradeStepRegistrator.Registry() {

				@Override
				public void register(
					String bundleSymbolicName, String fromSchemaVersionString,
					String toSchemaVersionString, UpgradeStep... upgradeSteps) {

					register(
						fromSchemaVersionString, toSchemaVersionString,
						upgradeSteps);
				}

				@Override
				public void register(
					String fromSchemaVersionString,
					String toSchemaVersionString, UpgradeStep... upgradeSteps) {

					for (UpgradeStep upgradeStep : upgradeSteps) {
						Class<?> clazz = upgradeStep.getClass();

						if (Objects.equals(clazz.getName(), _CLASS_NAME)) {
							_upgradeDynamicDataMapping =
								(UpgradeProcess)upgradeStep;
						}
					}
				}

			});
	}

	private static final String _CLASS_NAME =
		"com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_0." +
			"UpgradeDynamicDataMapping";

	private long _classNameIdDDLRecordSet;
	private long _classNameIdDDMContent;
	private long _classNameIdDDMStructure;
	private long _classNameIdExpandoStorageAdapter;
	private long _contentId;

	@DeleteAfterTestRun
	private Group _group;

	private long _parentStructureId;
	private long _storageLinkId;
	private long _structureId;
	private final Map<String, String> _structureModelResourceNames =
		new HashMap<>();
	private long _templateId;
	private final Map<String, String> _templateModelResourceNames =
		new HashMap<>();
	private Timestamp _timestamp;
	private UpgradeProcess _upgradeDynamicDataMapping;

}