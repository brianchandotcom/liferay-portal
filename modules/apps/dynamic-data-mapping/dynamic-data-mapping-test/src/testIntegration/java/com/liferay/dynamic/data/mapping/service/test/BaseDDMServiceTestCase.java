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

package com.liferay.dynamic.data.mapping.service.test;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureLayoutTestHelper;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.text.DateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;

/**
 * @author Eduardo Garcia
 * @author Rafael Praxedes
 */
public class BaseDDMServiceTestCase {

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();

		ddmStructureTestHelper = new DDMStructureTestHelper(
			PortalUtil.getClassNameId(DDL_RECORD_SET_CLASS_NAME), group);
		ddmStructureLayoutTestHelper = new DDMStructureLayoutTestHelper(group);
	}

	protected DDMTemplate addDisplayTemplate(
			long classNameId, long classPK, long resourceClassNameId,
			String name, String description, int status)
		throws Exception {

		String language = TemplateConstants.LANG_TYPE_VM;

		return addTemplate(
			classNameId, classPK, resourceClassNameId, StringPool.BLANK, name,
			description, DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY,
			StringPool.BLANK, language, getTestTemplateScript(language),
			status);
	}

	protected DDMTemplate addDisplayTemplate(
			long classNameId, long classPK, String name, int status)
		throws Exception {

		String language = TemplateConstants.LANG_TYPE_VM;

		return addTemplate(
			classNameId, classPK, name,
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, StringPool.BLANK,
			language, getTestTemplateScript(language), status);
	}

	protected DDMTemplate addDisplayTemplate(
			long classPK, String name, int status)
		throws Exception {

		return addDisplayTemplate(
			PortalUtil.getClassNameId(DDMStructure.class), classPK, name,
			status);
	}

	protected DDMTemplate addFormTemplate(long classPK, String name, int status)
		throws Exception {

		return addFormTemplate(
			classPK, name, getTestTemplateScript("xsd"), status);
	}

	protected DDMTemplate addFormTemplate(
			long classPK, String name, String definition, int status)
		throws Exception {

		return addTemplate(
			PortalUtil.getClassNameId(DDMStructure.class), classPK, name,
			DDMTemplateConstants.TEMPLATE_TYPE_FORM,
			DDMTemplateConstants.TEMPLATE_MODE_CREATE, "xsd", definition,
			status);
	}

	protected DDMStructure addStructure(
			long parentStructureId, long classNameId, String structureKey,
			String name, String definition, String storageType, int type)
		throws Exception {

		return addStructure(
			parentStructureId, classNameId, structureKey, name,
			StringPool.BLANK, definition, storageType, type);
	}

	protected DDMStructure addStructure(
			long parentStructureId, long classNameId, String structureKey,
			String name, String description, String definition,
			String storageType, int type)
		throws Exception {

		return addStructure(
			parentStructureId, classNameId, structureKey, name, description,
			definition, storageType, type, WorkflowConstants.STATUS_APPROVED);
	}

	protected DDMStructure addStructure(
			long parentStructureId, long classNameId, String structureKey,
			String name, String description, String definition,
			String storageType, int type, int status)
		throws Exception {

		DDMForm ddmForm = ddmStructureTestHelper.toDDMForm(definition);

		DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(ddmForm);

		return ddmStructureTestHelper.addStructure(
			parentStructureId, classNameId, structureKey, name, description,
			ddmForm, ddmFormLayout, storageType, type, status);
	}

	protected DDMStructure addStructure(long classNameId, String name)
		throws Exception {

		return addStructure(classNameId, name, null);
	}

	protected DDMStructure addStructure(
			long classNameId, String name, String description)
		throws Exception {

		return addStructure(
			0, classNameId, null, name, description, read("test-structure.xsd"),
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);
	}

	protected DDMStructure addStructure(
			long classNameId, String structureKey, String name,
			String definition, String storageType, int type)
		throws Exception {

		DDMForm ddmForm = ddmStructureTestHelper.toDDMForm(definition);

		return ddmStructureTestHelper.addStructure(
			classNameId, structureKey, name, ddmForm, storageType, type);
	}

	protected DDMTemplate addTemplate(
			long classNameId, long classPK, long resourceClassNameId,
			String templateKey, String name, String description, String type,
			String mode, String language, String script, int status)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAttribute("status", status);

		return DDMTemplateLocalServiceUtil.addTemplate(
			TestPropsValues.getUserId(), group.getGroupId(), classNameId,
			classPK, resourceClassNameId, templateKey,
			getDefaultLocaleMap(name), getDefaultLocaleMap(description), type,
			mode, language, script, false, false, null, null, serviceContext);
	}

	protected DDMTemplate addTemplate(
			long classNameId, long classPK, String name, String type,
			String mode, String language, String script, int status)
		throws Exception {

		return addTemplate(
			classNameId, classPK, null, name, type, mode, language, script,
			status);
	}

	protected DDMTemplate addTemplate(
			long classNameId, long classPK, String templateKey, String name,
			String type, String mode, String language, String script,
			int status)
		throws Exception {

		return addTemplate(
			classNameId, classPK,
			PortalUtil.getClassNameId(DDL_RECORD_SET_CLASS_NAME), templateKey,
			name, StringPool.BLANK, type, mode, language, script, status);
	}

	protected String getBasePath() {
		return "com/liferay/dynamic/data/mapping/dependencies/";
	}

	protected Serializable getDateFieldValue(
		int month, int day, int year, Locale locale) {

		Date dateValue = PortalUtil.getDate(month, day, year);

		DateFormat rawDateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd", locale);

		return rawDateFormat.format(dateValue);
	}

	protected Map<Locale, String> getDefaultLocaleMap(String value) {
		Map<Locale, String> map = new HashMap<>();

		map.put(LocaleUtil.getSiteDefault(), value);

		return map;
	}

	protected String getTestTemplateScript(String language) throws Exception {
		String text = StringPool.BLANK;

		if (language.equals(TemplateConstants.LANG_TYPE_FTL)) {
			text = "${variable}";
		}
		else if (language.equals(TemplateConstants.LANG_TYPE_VM)) {
			text = "#set ($preferences = $renderRequest.getPreferences())";
		}
		else if (language.equals("xsd")) {
			text = read("test-template.xsd");
		}

		return text;
	}

	protected String read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getClassLoader(), getBasePath() + fileName);
	}

	protected static final String DDL_RECORD_CLASS_NAME =
		"com.liferay.dynamic.data.lists.model.DDLRecord";

	protected static final String DDL_RECORD_SET_CLASS_NAME =
		"com.liferay.dynamic.data.lists.model.DDLRecordSet";

	protected DDMStructureLayoutTestHelper ddmStructureLayoutTestHelper;
	protected DDMStructureTestHelper ddmStructureTestHelper;

	@DeleteAfterTestRun
	protected Group group;

}