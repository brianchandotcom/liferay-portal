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

package com.liferay.portlet.dynamicdatamapping.service;

import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.storage.StorageType;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;

import java.io.File;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;

/**
 * @author Eduardo Garcia
 */
public class BaseDDMServiceTestCase {

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();
	}

	protected DDMTemplate addDisplayTemplate(
			long classNameId, long classPK, String name)
		throws Exception {

		String language = TemplateConstants.LANG_TYPE_VM;

		return addTemplate(
			classNameId, classPK, name,
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, StringPool.BLANK,
			language, getTestTemplateScript(language));
	}

	protected DDMTemplate addDisplayTemplate(long classPK, String name)
		throws Exception {

		return addDisplayTemplate(
			PortalUtil.getClassNameId(DDMStructure.class), classPK, name);
	}

	protected DDMTemplate addFormTemplate(long classPK, String name)
		throws Exception {

		return addFormTemplate(classPK, name, getTestTemplateScript("xsd"));
	}

	protected DDMTemplate addFormTemplate(
			long classPK, String name, String definition)
		throws Exception {

		return addTemplate(
			PortalUtil.getClassNameId(DDMStructure.class), classPK, name,
			DDMTemplateConstants.TEMPLATE_TYPE_FORM,
			DDMTemplateConstants.TEMPLATE_MODE_CREATE, "xsd", definition);
	}

	protected DDMStructure addStructure(
			long parentStructureId, long classNameId, String structureKey,
			String name, String definition, String storageType, int type)
		throws Exception {

		DDMXMLUtil.validateXML(definition);

		DDMForm ddmForm = DDMFormXSDDeserializerUtil.deserialize(definition);

		return DDMStructureLocalServiceUtil.addStructure(
			TestPropsValues.getUserId(), group.getGroupId(), parentStructureId,
			classNameId, structureKey, getDefaultLocaleMap(name), null, ddmForm,
			storageType, type,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	protected DDMStructure addStructure(long classNameId, String name)
		throws Exception {

		String storageType = StorageType.XML.getValue();

		return addStructure(
			classNameId, null, name, getTestStructureDefinition(storageType),
			storageType, DDMStructureConstants.TYPE_DEFAULT);
	}

	protected DDMStructure addStructure(
			long classNameId, String structureKey, String name,
			String definition, String storageType, int type)
		throws Exception {

		return addStructure(
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID, classNameId,
			structureKey, name, definition, storageType, type);
	}

	protected DDMTemplate addTemplate(
			long classNameId, long classPK, String name, String type,
			String mode, String language, String script)
		throws Exception {

		return addTemplate(
			classNameId, classPK, null, name, type, mode, language, script);
	}

	protected DDMTemplate addTemplate(
			long classNameId, long classPK, String templateKey, String name,
			String type, String mode, String language, String script)
		throws Exception {

		return addTemplate(
			classNameId, classPK, templateKey, name, type, mode, language,
			script, false, false, null, null);
	}

	protected DDMTemplate addTemplate(
			long classNameId, long classPK, String templateKey, String name,
			String type, String mode, String language, String script,
			boolean cacheable, boolean smallImage, String smallImageURL,
			File smallFile)
		throws Exception {

		return DDMTemplateLocalServiceUtil.addTemplate(
			TestPropsValues.getUserId(), group.getGroupId(), classNameId,
			classPK, templateKey, getDefaultLocaleMap(name), null, type, mode,
			language, script, cacheable, smallImage, smallImageURL, smallFile,
			ServiceContextTestUtil.getServiceContext());
	}

	protected String getBasePath() {
		return "com/liferay/portlet/dynamicdatamapping/dependencies/";
	}

	protected Map<Locale, String> getDefaultLocaleMap(String defaultValue) {
		Map<Locale, String> map = new HashMap<Locale, String>();

		map.put(LocaleUtil.getSiteDefault(), defaultValue);

		return map;
	}

	protected String getTestStructureDefinition(String storageType)
		throws Exception {

		String text = StringPool.BLANK;

		if (storageType.equals(StorageType.XML.getValue())) {
			text = readText("test-structure.xsd");
		}

		return text;
	}

	protected String getTestTemplateScript(String language) throws Exception {
		String text = StringPool.BLANK;

		if (language.equals(TemplateConstants.LANG_TYPE_VM)) {
			text = "#set ($preferences = $renderRequest.getPreferences())";
		}
		else if (language.equals("xsd")) {
			text = readText("test-template.xsd");
		}

		return text;
	}

	protected String readText(String fileName) throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			getBasePath() + fileName);

		return StringUtil.read(inputStream);
	}

	@DeleteAfterTestRun
	protected Group group;

}