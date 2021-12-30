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

package com.liferay.journal.internal.upgrade.v0_0_5;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLinkLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.xml.XMLUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Gergely Mathe
 * @author Eudaldo Alonso
 */
public class JournalUpgradeProcess extends UpgradeProcess {

	public JournalUpgradeProcess(
		CompanyLocalService companyLocalService,
		DDMStorageLinkLocalService ddmStorageLinkLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		DDMTemplateLinkLocalService ddmTemplateLinkLocalService,
		DefaultDDMStructureHelper defaultDDMStructureHelper,
		GroupLocalService groupLocalService,
		ResourceActionLocalService resourceActionLocalService,
		ResourceActions resourceActions,
		ResourceLocalService resourceLocalService,
		UserLocalService userLocalService) {

		_companyLocalService = companyLocalService;
		_ddmStorageLinkLocalService = ddmStorageLinkLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_ddmTemplateLinkLocalService = ddmTemplateLinkLocalService;
		_defaultDDMStructureHelper = defaultDDMStructureHelper;
		_groupLocalService = groupLocalService;
		_resourceActionLocalService = resourceActionLocalService;
		_resourceActions = resourceActions;
		_resourceLocalService = resourceLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_updateJournalArticles();

		_addDDMStorageLinks();
		_addDDMTemplateLinks();
	}

	private String _addBasicWebContentStructureAndTemplate(long companyId)
		throws Exception {

		_initJournalDDMCompositeModelsResourceActions();

		Group group = _groupLocalService.getCompanyGroup(companyId);

		long defaultUserId = _userLocalService.getDefaultUserId(companyId);

		Class<?> clazz = getClass();

		Locale oldSiteDefaultLocale = LocaleThreadLocal.getSiteDefaultLocale();

		Locale siteDefaultLocale = LocaleUtil.fromLanguageId(
			UpgradeProcessUtil.getDefaultLanguageId(companyId));

		LocaleThreadLocal.setSiteDefaultLocale(siteDefaultLocale);

		try {
			_defaultDDMStructureHelper.addDDMStructures(
				defaultUserId, group.getGroupId(),
				PortalUtil.getClassNameId(JournalArticle.class),
				clazz.getClassLoader(),
				"com/liferay/journal/internal/upgrade/v1_0_0/dependencies" +
					"/basic-web-content-structure.xml",
				new ServiceContext());
		}
		finally {
			LocaleThreadLocal.setSiteDefaultLocale(oldSiteDefaultLocale);
		}

		_addDefaultResourcePermissions(group.getGroupId());

		List<Element> structureElements = _getDDMStructures(siteDefaultLocale);

		Element structureElement = structureElements.get(0);

		return StringUtil.toUpperCase(structureElement.elementText("name"));
	}

	private void _addDDMStorageLink(Map<Long, List<Long>> ddmStructureIdsMap)
		throws Exception {

		long journalArticleClassNameId = PortalUtil.getClassNameId(
			JournalArticle.class.getName());

		for (Map.Entry<Long, List<Long>> entry :
				ddmStructureIdsMap.entrySet()) {

			long ddmStructureId = _getDDMStructureId(
				entry.getKey(), entry.getValue());

			DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
				ddmStructureId);

			DDMStructureVersion ddmStructureVersion =
				ddmStructure.getStructureVersion();

			_ddmStorageLinkLocalService.addStorageLink(
				journalArticleClassNameId, entry.getKey(),
				ddmStructureVersion.getStructureVersionId(),
				new ServiceContext());
		}
	}

	private void _addDDMStorageLinks() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						StringBundler.concat(
							"select DDMStructure.structureId, ",
							"JournalArticle.id_ from JournalArticle inner ",
							"join DDMStructure on (DDMStructure.groupId in ",
							"(select distinct Group_.groupId from Group_ ",
							"where (Group_.groupId = JournalArticle.groupId) ",
							"or (Group_.companyId = JournalArticle.companyId ",
							"and Group_.friendlyURL = ?)) and ",
							"DDMStructure.structureKey = ",
							"JournalArticle.DDMStructureKey and ",
							"JournalArticle.classNameId != ?)"))) {

				preparedStatement.setString(
					1, GroupConstants.GLOBAL_FRIENDLY_URL);
				preparedStatement.setLong(
					2, PortalUtil.getClassNameId(DDMStructure.class.getName()));

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					Map<Long, List<Long>> ddmStructureIdsMap = new HashMap<>();

					while (resultSet.next()) {
						long structureId = resultSet.getLong("structureId");

						long id = resultSet.getLong("id_");

						List<Long> ddmStructureIds = ddmStructureIdsMap.get(id);

						if (ddmStructureIds == null) {
							ddmStructureIds = new ArrayList<>();
						}

						ddmStructureIds.add(structureId);

						ddmStructureIdsMap.put(id, ddmStructureIds);
					}

					_addDDMStorageLink(ddmStructureIdsMap);
				}
			}
		}
	}

	private void _addDDMTemplateLinks() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long ddmStructureClassNameId = PortalUtil.getClassNameId(
				DDMStructure.class.getName());

			long journalArticleClassNameId = PortalUtil.getClassNameId(
				JournalArticle.class.getName());

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						StringBundler.concat(
							"select DDMTemplate.templateId, ",
							"JournalArticle.id_ from JournalArticle inner ",
							"join DDMTemplate on (DDMTemplate.groupId = ",
							"JournalArticle.groupId and ",
							"DDMTemplate.templateKey = ",
							"JournalArticle.DDMTemplateKey and ",
							"JournalArticle.classNameId != ? and ",
							"DDMTemplate.classNameId = ?)"))) {

				preparedStatement.setLong(1, ddmStructureClassNameId);
				preparedStatement.setLong(2, ddmStructureClassNameId);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						long templateId = resultSet.getLong("templateId");
						long id = resultSet.getLong("id_");

						_ddmTemplateLinkLocalService.addTemplateLink(
							journalArticleClassNameId, id, templateId);
					}
				}
			}
		}
	}

	private void _addDefaultResourcePermissions(long groupId) throws Exception {
		String modelResource = _resourceActions.getCompositeModelName(
			DDMStructure.class.getName(), JournalArticle.class.getName());

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			groupId, PortalUtil.getClassNameId(JournalArticle.class),
			"BASIC-WEB-CONTENT");

		_resourceLocalService.addResources(
			ddmStructure.getCompanyId(), 0, 0, modelResource,
			ddmStructure.getStructureId(), false, false, true);
	}

	private boolean _containsDateFieldType(String content) {
		if (content.indexOf(_TYPE_ATTRIBUTE_DDM_DATE) != -1) {
			return true;
		}

		return false;
	}

	private String _convertStaticContentToDynamic(long groupId, String content)
		throws Exception {

		Document document = SAXReaderUtil.read(content);

		Document newDocument = SAXReaderUtil.createDocument();

		Element rootElement = document.getRootElement();

		String defaultLanguageId = _getDefaultLanguageId(groupId);

		String availableLocales = GetterUtil.getString(
			rootElement.attributeValue("available-locales"), defaultLanguageId);
		String defaultLocale = GetterUtil.getString(
			rootElement.attributeValue("default-locale"), defaultLanguageId);

		Element newRootElement = SAXReaderUtil.createElement("root");

		newRootElement.addAttribute("available-locales", availableLocales);
		newRootElement.addAttribute("default-locale", defaultLocale);

		newDocument.add(newRootElement);

		Element dynamicElementElement = SAXReaderUtil.createElement(
			"dynamic-element");

		dynamicElementElement.addAttribute("name", "content");
		dynamicElementElement.addAttribute("type", "text_area");
		dynamicElementElement.addAttribute("index-type", "text");
		dynamicElementElement.addAttribute("index", String.valueOf(0));

		newRootElement.add(dynamicElementElement);

		List<Element> staticContentElements = rootElement.elements(
			"static-content");

		for (Element staticContentElement : staticContentElements) {
			String languageId = GetterUtil.getString(
				staticContentElement.attributeValue("language-id"),
				defaultLanguageId);
			String text = staticContentElement.getText();

			Element dynamicContentElement = SAXReaderUtil.createElement(
				"dynamic-content");

			dynamicContentElement.addAttribute("language-id", languageId);
			dynamicContentElement.addCDATA(text);

			dynamicElementElement.add(dynamicContentElement);
		}

		return XMLUtil.formatXML(newDocument);
	}

	private Set<String> _getArticleDynamicElements(Element rootElement) {
		List<String> dynamicElementNames = new ArrayList<>();

		List<Element> dynamicElementElements = rootElement.elements(
			"dynamic-element");

		for (Element element : dynamicElementElements) {
			dynamicElementNames.add(element.attributeValue("name"));

			dynamicElementNames.addAll(_getArticleDynamicElements(element));
		}

		return SetUtil.fromList(dynamicElementNames);
	}

	private Set<String> _getArticleFieldNames(long articleId) throws Exception {
		Set<String> articleFieldNames = new HashSet<>();

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			String sql =
				"select JournalArticle.content from JournalArticle where " +
					"JournalArticle.id_ = ?";

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(sql)) {

				preparedStatement.setLong(1, articleId);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						String content = resultSet.getString("content");

						Document document = SAXReaderUtil.read(content);

						articleFieldNames = _getArticleDynamicElements(
							document.getRootElement());
					}
				}
			}
		}

		return articleFieldNames;
	}

	private long _getBestDDMStructureIdMatch(
			long id, long ddmStructureId1, long ddmStructureId2)
		throws Exception {

		DDMStructure ddmStructure1 = _ddmStructureLocalService.getStructure(
			ddmStructureId1);

		Set<String> fieldNames1 = ddmStructure1.getFieldNames();

		Set<String> articleFieldNames = _getArticleFieldNames(id);

		fieldNames1.removeAll(articleFieldNames);

		DDMStructure ddmStructure2 = _ddmStructureLocalService.getStructure(
			ddmStructureId2);

		Set<String> fieldNames2 = ddmStructure2.getFieldNames();

		fieldNames2.removeAll(articleFieldNames);

		if (fieldNames1.size() <= fieldNames2.size()) {
			return ddmStructure1.getStructureId();
		}

		return ddmStructure2.getStructureId();
	}

	private long _getDDMStructureId(long id, List<Long> ddmStructureIds)
		throws Exception {

		if (ddmStructureIds.size() == 1) {
			return ddmStructureIds.get(0);
		}

		return _getBestDDMStructureIdMatch(
			id, ddmStructureIds.get(0), ddmStructureIds.get(1));
	}

	private List<Element> _getDDMStructures(Locale locale)
		throws DocumentException {

		String xml = StringUtil.replace(
			_BASIC_WEB_CONTENT_STRUCTURE, "[$LOCALE_DEFAULT$]",
			locale.toString());

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		return rootElement.elements("structure");
	}

	private String _getDefaultLanguageId(long groupId) throws Exception {
		String defaultLanguageId = _defaultLanguageIds.get(groupId);

		if (defaultLanguageId == null) {
			Locale defaultLocale = PortalUtil.getSiteDefaultLocale(groupId);

			defaultLanguageId = LanguageUtil.getLanguageId(defaultLocale);

			_defaultLanguageIds.put(groupId, defaultLanguageId);
		}

		return defaultLanguageId;
	}

	private Map<String, String> _getInvalidDDMFormFieldNamesMap(
		String content) {

		Map<String, String> invalidDDMFormFieldNamesMap = new HashMap<>();

		Matcher matcher = _nameAttributePattern.matcher(content);

		while (matcher.find()) {
			String oldFieldName = matcher.group(1);

			String newFieldName = oldFieldName.replaceAll(
				_INVALID_FIELD_NAME_CHARS_REGEX, StringPool.BLANK);

			if (!oldFieldName.equals(newFieldName)) {
				invalidDDMFormFieldNamesMap.put(oldFieldName, newFieldName);
			}
		}

		return invalidDDMFormFieldNamesMap;
	}

	private void _initJournalDDMCompositeModelsResourceActions()
		throws Exception {

		_resourceActions.populateModelResources(
			JournalUpgradeProcess.class.getClassLoader(),
			"/resource-actions/journal_ddm_composite_models.xml");
	}

	private void _transformDateFieldValue(Element dynamicContentElement) {
		String value = dynamicContentElement.getText();

		if (!Validator.isNumber(value)) {
			return;
		}

		Date date = new Date(GetterUtil.getLong(value));

		dynamicContentElement.clearContent();

		dynamicContentElement.addCDATA(_dateFormat.format(date));
	}

	private void _transformDateFieldValues(
		List<Element> dynamicElementElements) {

		if (ListUtil.isEmpty(dynamicElementElements)) {
			return;
		}

		for (Element dynamicElementElement : dynamicElementElements) {
			String type = GetterUtil.getString(
				dynamicElementElement.attributeValue("type"));

			if (type.equals("ddm-date")) {
				List<Element> dynamicContentElements =
					dynamicElementElement.elements("dynamic-content");

				for (Element dynamicContentElement : dynamicContentElements) {
					_transformDateFieldValue(dynamicContentElement);
				}
			}

			List<Element> childDynamicElementElements =
				dynamicElementElement.elements("dynamic-element");

			_transformDateFieldValues(childDynamicElementElements);
		}
	}

	private String _transformDateFieldValues(String content) throws Exception {
		if (!_containsDateFieldType(content)) {
			return content;
		}

		Document document = SAXReaderUtil.read(content);

		Element rootElement = document.getRootElement();

		List<Element> dynamicElementElements = rootElement.elements(
			"dynamic-element");

		_transformDateFieldValues(dynamicElementElements);

		return XMLUtil.formatXML(document);
	}

	private String _transformFieldNames(String content) {
		Map<String, String> invalidDDMFormFieldNamesMap =
			_getInvalidDDMFormFieldNamesMap(content);

		for (Map.Entry<String, String> entry :
				invalidDDMFormFieldNamesMap.entrySet()) {

			content = StringUtil.replace(
				content, "name=\"" + entry.getKey() + "\"",
				"name=\"" + entry.getValue() + "\"");
		}

		return content;
	}

	private void _updateJournalArticle(
			long id, String ddmStructureKey, String ddmTemplateKey,
			String content)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update JournalArticle set DDMStructureKey = ?, " +
					"DDMTemplateKey = ?, content = ? where id_ = ?")) {

			preparedStatement.setString(1, ddmStructureKey);
			preparedStatement.setString(2, ddmTemplateKey);
			preparedStatement.setString(3, content);
			preparedStatement.setLong(4, id);

			preparedStatement.executeUpdate();
		}
	}

	private void _updateJournalArticleContent(long id, String content)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update JournalArticle set content = ? where id_ = ?")) {

			preparedStatement.setString(1, content);
			preparedStatement.setLong(2, id);

			preparedStatement.executeUpdate();
		}
	}

	private void _updateJournalArticles() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_companyLocalService.forEachCompanyId(
				companyId -> _updateJournalArticles(companyId));
		}
	}

	private void _updateJournalArticles(long companyId) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select id_, groupId, content, DDMStructureKey from " +
					"JournalArticle where companyId = " + companyId);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			String name = _addBasicWebContentStructureAndTemplate(companyId);

			while (resultSet.next()) {
				long id = resultSet.getLong("id_");
				String content = resultSet.getString("content");

				String ddmStructureKey = resultSet.getString("DDMStructureKey");

				if (Validator.isNull(ddmStructureKey)) {
					long groupId = resultSet.getLong("groupId");

					content = _convertStaticContentToDynamic(groupId, content);

					_updateJournalArticle(id, name, name, content);

					continue;
				}

				String updatedContent = _transformDateFieldValues(content);

				updatedContent = _transformFieldNames(updatedContent);

				if (!content.equals(updatedContent)) {
					_updateJournalArticleContent(id, updatedContent);
				}
			}
		}
	}

	private static final String _BASIC_WEB_CONTENT_STRUCTURE;

	private static final String _INVALID_FIELD_NAME_CHARS_REGEX =
		"([\\p{Punct}&&[^_]]|\\p{Space})+";

	private static final String _TYPE_ATTRIBUTE_DDM_DATE = "type=\"ddm-date\"";

	private static final DateFormat _dateFormat =
		DateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd");
	private static final Pattern _nameAttributePattern = Pattern.compile(
		"name=\"([^\"]+)\"");

	static {
		try {
			_BASIC_WEB_CONTENT_STRUCTURE = StringUtil.read(
				JournalUpgradeProcess.class.getClassLoader(),
				"com/liferay/journal/internal/upgrade/v1_0_0/dependencies" +
					"/basic-web-content-structure.xml");
		}
		catch (IOException ioException) {
			throw new ExceptionInInitializerError(ioException);
		}
	}

	private final CompanyLocalService _companyLocalService;
	private final DDMStorageLinkLocalService _ddmStorageLinkLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DDMTemplateLinkLocalService _ddmTemplateLinkLocalService;
	private final DefaultDDMStructureHelper _defaultDDMStructureHelper;
	private final Map<Long, String> _defaultLanguageIds = new HashMap<>();
	private final GroupLocalService _groupLocalService;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourceActions _resourceActions;
	private final ResourceLocalService _resourceLocalService;
	private final UserLocalService _userLocalService;

}