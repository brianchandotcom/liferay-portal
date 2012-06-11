/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.storage.StorageType;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Eduardo Garcia
 */
public class BaseDDMServiceTestCase {

	protected DDMTemplate addDetailTemplate(long classPK, String name)
		throws Exception {

		long classNameId = PortalUtil.getClassNameId(DDMStructure.class);

		String type = DDMTemplateConstants.TEMPLATE_TYPE_DETAIL;

		String mode = DDMTemplateConstants.TEMPLATE_MODE_CREATE;

		String language = "xsd";

		String script = getTestTemplateScript(language);

		return addTemplate(
			classNameId, classPK, name, type, mode, language, script);
	}

	protected DDMTemplate addListTemplate(
			long classNameId, long classPK, String name)
		throws Exception {

		String type = DDMTemplateConstants.TEMPLATE_TYPE_LIST;

		String mode = StringPool.BLANK;

		String language = DDMTemplateConstants.LANG_TYPE_VM;

		String script = getTestTemplateScript(language);

		return addTemplate(
				classNameId, classPK, name, type, mode, language, script);
	}

	protected DDMTemplate addListTemplate(long classPK, String name)
		throws Exception {

		long classNameId = PortalUtil.getClassNameId(DDMStructure.class);

		return addListTemplate(classNameId, classPK, name);
	}

	protected DDMStructure addStructure(long classNameId, String name)
		throws Exception {

		String storageType = StorageType.XML.getValue();

		String xsd = getTestStructureXsd(storageType);

		int type =  DDMStructureConstants.TYPE_DEFAULT;

		return addStructure(classNameId, null, name, xsd, storageType, type);
	}

	protected DDMStructure addStructure(
			long classNameId, String structureKey, String name, String xsd,
			String storageType, int type)
		throws Exception {

		long userId = TestPropsValues.getUserId();

		long groupId = TestPropsValues.getGroupId();

		Map<Locale, String> nameMap = getDefaultLocaleMap(name);

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		return DDMStructureLocalServiceUtil.addStructure(
			userId, groupId, classNameId, structureKey, nameMap, null, xsd,
			storageType, type, serviceContext);
	}

	protected DDMTemplate addTemplate(
			long classNameId, long classPK, String name, String type,
			String mode, String language, String script)
		throws Exception {

		return addTemplate(
			classNameId, classPK,  null, name, type, mode, language, script);
	}

	protected DDMTemplate addTemplate(
			long classNameId, long classPK, String templateKey, String name,
			String type, String mode, String language, String script)
		throws Exception {

		long userId = TestPropsValues.getUserId();

		long groupId = TestPropsValues.getGroupId();

		Map<Locale, String> nameMap = getDefaultLocaleMap(name);

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		return DDMTemplateLocalServiceUtil.addTemplate(
			userId, groupId, classNameId, classPK, templateKey, nameMap, null,
			type, mode, language, script, serviceContext);
	}

	protected Map<Locale, String> getDefaultLocaleMap(String defaultValue) {
		Map<Locale, String> map = new HashMap<Locale, String>();
		map.put(LocaleUtil.getDefault(), defaultValue);

		return map;
	}

	protected String getTestStructureXsd(String storageType) {
		StringBundler sb = new StringBundler();

		if (storageType.equals(StorageType.XML.getValue())) {
			sb.append("<?xml version='1.0' encoding='UTF-8'?>");
			sb.append("<root available-locales=\"en_US\" ");
			sb.append("default-locale=\"en_US\">");
			sb.append("<dynamic-element dataType=\"boolean\" ");
			sb.append("name=\"checkbox1234\" type=\"checkbox\">\"");
			sb.append("<meta-data locale=\"en_US\">");
			sb.append("<entry name=\"label\"><![CDATA[Boolean]]></entry>");
			sb.append("<entry name=\"showLabel\"><![CDATA[true]]></entry>");
			sb.append("<entry name=\"tip\"><![CDATA[]]></entry>");
			sb.append("</meta-data>");
			sb.append("</dynamic-element>");
			sb.append("</root>");
		}

		return sb.toString();
	}

	protected String getTestTemplateScript(String language) {
		StringBundler sb = new StringBundler();

		if (language.equals(DDMTemplateConstants.LANG_TYPE_VM)) {
			sb.append("#set ($preferences = $renderRequest.getPreferences())");
		}
		else if (language.equals("xsd")) {
			sb.append("<?xml version='1.0' encoding='UTF-8'?>");
			sb.append("<root available-locales=\"en_US\" ");
			sb.append("default-locale=\"en_US\">");
			sb.append("<dynamic-element dataType=\"string\" ");
			sb.append("name=\"radio2112\" type=\"radio\">");
			sb.append("<dynamic-element name=\"option_1\" type=\"option\" ");
			sb.append("value=\"value 1\">");
			sb.append("<meta-data locale=\"en_US\">");
			sb.append("<entry name=\"label\"><![CDATA[option 1]]></entry>");
			sb.append("</meta-data>");
			sb.append("</dynamic-element>");
			sb.append("<dynamic-element name=\"option_2\" type=\"option\" ");
			sb.append("value=\"value 2\">");
			sb.append("<meta-data locale=\"en_US\">");
			sb.append("<entry name=\"label\"><![CDATA[option 2]]></entry>");
			sb.append("</meta-data>");
			sb.append("</dynamic-element>");
			sb.append("<dynamic-element name=\"option_3\" type=\"option\" ");
			sb.append("value=\"value 3\">");
			sb.append("<meta-data locale=\"en_US\">");
			sb.append("<entry name=\"label\"><![CDATA[option 3]]></entry>");
			sb.append("</meta-data>");
			sb.append("</dynamic-element>");
			sb.append("<meta-data locale=\"en_US\">");
			sb.append("<entry name=\"label\"><![CDATA[Radio]]></entry>");
			sb.append("<entry name=\"showLabel\"><![CDATA[true]]></entry>");
			sb.append("<entry name=\"required\"><![CDATA[false]]></entry>");
			sb.append("<entry name=\"predefinedValue\">");
			sb.append("<![CDATA[[\"\"]]]></entry>");
			sb.append("<entry name=\"tip\"><![CDATA[]]></entry>");
			sb.append("</meta-data>");
			sb.append("</dynamic-element>");
			sb.append("</root>");
		}

		return sb.toString();
	}

}