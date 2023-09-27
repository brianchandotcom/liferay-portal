/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.test.util;

import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Eudaldo Alonso
 */
public class DDMStructureTestUtil {

	public static DDMStructure addStructure(long groupId, String className)
		throws Exception {

		return addStructure(
			groupId, className, 0, getSampleDDMForm(),
			LocaleUtil.getSiteDefault(),
			ServiceContextTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(
			long groupId, String className, DDMForm ddmForm)
		throws Exception {

		return addStructure(
			groupId, className, 0, ddmForm, LocaleUtil.getSiteDefault(),
			ServiceContextTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(
			long groupId, String className, DDMForm ddmForm,
			Locale defaultLocale)
		throws Exception {

		return addStructure(
			groupId, className, 0, ddmForm, defaultLocale,
			ServiceContextTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(
			long groupId, String className, Locale defaultLocale)
		throws Exception {

		return addStructure(
			groupId, className, 0, getSampleDDMForm(), defaultLocale,
			ServiceContextTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(
			long groupId, String className, long parentStructureId)
		throws Exception {

		return addStructure(
			groupId, className, parentStructureId, getSampleDDMForm(),
			LocaleUtil.getSiteDefault(),
			ServiceContextTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(
			long groupId, String className, long parentStructureId,
			DDMForm ddmForm, Locale defaultLocale,
			ServiceContext serviceContext)
		throws Exception {

		Map<Locale, String> nameMap = HashMapBuilder.put(
			defaultLocale, "Test Structure"
		).build();

		DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(ddmForm);

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return DDMStructureLocalServiceUtil.addStructure(
			TestPropsValues.getUserId(), groupId, parentStructureId,
			PortalUtil.getClassNameId(className), null, nameMap, null, ddmForm,
			ddmFormLayout, StorageType.DEFAULT.toString(),
			DDMStructureConstants.TYPE_DEFAULT, serviceContext);
	}

	public static DDMStructure addStructure(String className) throws Exception {
		return addStructure(
			TestPropsValues.getGroupId(), className, 0, getSampleDDMForm(),
			LocaleUtil.getSiteDefault(),
			ServiceContextTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(String className, DDMForm ddmForm)
		throws Exception {

		return addStructure(
			TestPropsValues.getGroupId(), className, 0, ddmForm,
			LocaleUtil.getSiteDefault(),
			ServiceContextTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(
			String className, DDMForm ddmForm, Locale defaultLocale)
		throws Exception {

		return addStructure(
			TestPropsValues.getGroupId(), className, 0, ddmForm, defaultLocale,
			ServiceContextTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(
			String className, Locale defaultLocale)
		throws Exception {

		return addStructure(
			TestPropsValues.getGroupId(), className, 0,
			getSampleDDMForm(
				"name", new Locale[] {LocaleUtil.US}, defaultLocale),
			defaultLocale, ServiceContextTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(
			String className, Locale[] availableLocales, Locale defaultLocale)
		throws Exception {

		return addStructure(
			TestPropsValues.getGroupId(), className, 0,
			getSampleDDMForm("name", availableLocales, defaultLocale),
			defaultLocale, ServiceContextTestUtil.getServiceContext());
	}

	public static DDMForm getSampleDDMForm() {
		return getSampleDDMForm("name");
	}

	public static DDMForm getSampleDDMForm(
		Locale[] availableLocales, Locale defaultLocale) {

		return getSampleDDMForm("name", availableLocales, defaultLocale);
	}

	public static DDMForm getSampleDDMForm(String name) {
		return getSampleDDMForm(
			name, new Locale[] {LocaleUtil.US}, LocaleUtil.US);
	}

	public static DDMForm getSampleDDMForm(
		String name, Locale[] availableLocales, Locale defaultLocale) {

		return getSampleDDMForm(
			name, "string", "text", true, "text", availableLocales,
			defaultLocale);
	}

	public static DDMForm getSampleDDMForm(
		String name, String dataType, String indexType, boolean repeatable,
		String type, Locale[] availableLocales, Locale defaultLocale) {

		DDMForm ddmForm = new DDMForm();

		Set<Locale> availableLocalesSet = SetUtil.fromArray(availableLocales);

		ddmForm.setAvailableLocales(availableLocalesSet);

		ddmForm.setDefaultLocale(defaultLocale);

		DDMFormField ddmFormField = new DDMFormField(name, type);

		ddmFormField.setDataType(dataType);
		ddmFormField.setIndexType(indexType);
		ddmFormField.setLocalizable(true);
		ddmFormField.setRepeatable(repeatable);

		LocalizedValue label = new LocalizedValue(defaultLocale);

		label.addString(
			defaultLocale, "Field_" + LocaleUtil.toLanguageId(defaultLocale));

		for (Locale locale : availableLocalesSet) {
			label.addString(locale, "Field_" + LocaleUtil.toLanguageId(locale));
		}

		ddmFormField.setLabel(label);

		ddmForm.addDDMFormField(ddmFormField);

		return ddmForm;
	}

	public static String getSampleStructuredContent() {
		return getSampleStructuredContent("name", "title");
	}

	public static String getSampleStructuredContent(
		Map<Locale, String> contents, String defaultLocale) {

		return getSampleStructuredContent(
			"name", Collections.singletonList(contents), defaultLocale);
	}

	public static String getSampleStructuredContent(String keywords) {
		return getSampleStructuredContent("name", keywords);
	}

	public static String getSampleStructuredContent(
		String name, List<Map<Locale, String>> contents, String defaultLocale) {

		StringBundler sb = new StringBundler();

		for (Map<Locale, String> map : contents) {
			for (Locale locale : map.keySet()) {
				sb.append(LocaleUtil.toLanguageId(locale));
				sb.append(StringPool.COMMA);
			}

			sb.setIndex(sb.index() - 1);
		}

		Document document = createDocumentContent(sb.toString(), defaultLocale);

		Element rootElement = document.getRootElement();

		for (Map<Locale, String> map : contents) {
			Element dynamicElementElement = rootElement.addElement(
				"dynamic-element");

			dynamicElementElement.addAttribute("index-type", "keyword");
			dynamicElementElement.addAttribute("name", name);
			dynamicElementElement.addAttribute("type", "text");

			for (Map.Entry<Locale, String> entry : map.entrySet()) {
				Element element = dynamicElementElement.addElement(
					"dynamic-content");

				element.addAttribute(
					"language-id", LocaleUtil.toLanguageId(entry.getKey()));
				element.addCDATA(entry.getValue());
			}
		}

		return document.asXML();
	}

	public static String getSampleStructuredContent(
		String name, String keywords) {

		return getSampleStructuredContent(
			name,
			Collections.singletonList(
				HashMapBuilder.put(
					LocaleUtil.US, keywords
				).build()),
			"en_US");
	}

	protected static Document createDocumentContent(
		String availableLocales, String defaultLocale) {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute("available-locales", availableLocales);
		rootElement.addAttribute("default-locale", defaultLocale);
		rootElement.addElement("request");

		return document;
	}

}