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

package com.liferay.journal.internal.exportimport.content.processor;

import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesTransformer;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.exception.ExportImportContentValidationException;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.journal.article.dynamic.data.mapping.form.field.type.constants.JournalArticleDDMFormFieldTypeConstants;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 * @author Máté Thurzó
 */
@Component(
	property = "model.class.name=com.liferay.journal.model.JournalArticle",
	service = {
		ExportImportContentProcessor.class,
		JournalArticleExportImportContentProcessor.class
	}
)
public class JournalArticleExportImportContentProcessor
	implements ExportImportContentProcessor<String> {

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent,
			boolean escapeContent)
		throws Exception {

		JournalArticle article = (JournalArticle)stagedModel;

		DDMStructure ddmStructure = article.getDDMStructure();

		Fields fields = _getDDMStructureFields(ddmStructure, content);

		if (fields == null) {
			return content;
		}

		StringBundler sb = new StringBundler(3);

		sb.append(stagedModel.getUuid());
		sb.append(exportReferencedContent);
		sb.append(escapeContent);

		String processedContent = _journalArticleExportImportCache.get(
			sb.toString());

		String path = ExportImportPathUtil.getModelPath(stagedModel);

		if (Validator.isNotNull(processedContent) &&
			portletDataContext.hasPrimaryKey(String.class, path)) {

			Element entityElement = portletDataContext.getExportDataElement(
				stagedModel);

			entityElement.addAttribute("cached", "true");

			return processedContent;
		}

		DDMFormValues ddmFormValues = _fieldsToDDMFormValuesConverter.convert(
			ddmStructure, fields);

		ddmFormValues =
			_ddmFormValuesExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, stagedModel, ddmFormValues, true, true);

		DDMFormValuesTransformer ddmFormValuesTransformer =
			new DDMFormValuesTransformer(ddmFormValues);

		ImageExportDDMFormFieldValueTransformer
			imageExportDDMFormFieldValueTransformer =
				new ImageExportDDMFormFieldValueTransformer(
					_dlAppService, exportReferencedContent, portletDataContext,
					stagedModel);

		ddmFormValuesTransformer.addTransformer(
			imageExportDDMFormFieldValueTransformer);

		ddmFormValuesTransformer.transform();

		content = _replaceExportJournalArticleReferences(
			portletDataContext, stagedModel, content, ddmStructure, fields,
			exportReferencedContent);

		content =
			_defaultTextExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, stagedModel, content,
					exportReferencedContent, escapeContent);

		_journalArticleExportImportCache.put(sb.toString(), content);

		return content;
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		JournalArticle article = (JournalArticle)stagedModel;

		DDMStructure ddmStructure = _fetchDDMStructure(
			portletDataContext, article);

		Fields fields = _getDDMStructureFields(ddmStructure, content);

		if (fields == null) {
			return content;
		}

		Element entityElement = portletDataContext.getImportDataElement(
			stagedModel);

		if (GetterUtil.getBoolean(entityElement.attributeValue("cached"))) {
			portletDataContext.removePrimaryKey(
				ExportImportPathUtil.getModelPath(stagedModel));

			return content;
		}

		content = _replaceImportJournalArticleReferences(
			ddmStructure, fields, portletDataContext, stagedModel);

		DDMFormValues ddmFormValues = _fieldsToDDMFormValuesConverter.convert(
			ddmStructure, fields);

		List<String> originalContents = _fetchContentsFromDDMFormValues(
			ddmFormValues.getDDMFormFieldValues());

		ddmFormValues =
			_ddmFormValuesExportImportContentProcessor.
				replaceImportContentReferences(
					portletDataContext, stagedModel, ddmFormValues);

		List<String> modifiedContents = _fetchContentsFromDDMFormValues(
			ddmFormValues.getDDMFormFieldValues());

		for (int i = 0; i < originalContents.size(); i++) {
			content = StringUtil.replace(
				content, originalContents.get(i), modifiedContents.get(i));
		}

		DDMFormValuesTransformer ddmFormValuesTransformer =
			new DDMFormValuesTransformer(ddmFormValues);

		ImageImportDDMFormFieldValueTransformer
			imageImportDDMFormFieldValueTransformer =
				new ImageImportDDMFormFieldValueTransformer(
					content, _dlAppService, portletDataContext, stagedModel);

		ddmFormValuesTransformer.addTransformer(
			imageImportDDMFormFieldValueTransformer);

		ddmFormValuesTransformer.transform();

		content = imageImportDDMFormFieldValueTransformer.getContent();

		return _defaultTextExportImportContentProcessor.
			replaceImportContentReferences(
				portletDataContext, stagedModel, content);
	}

	@Override
	public void validateContentReferences(long groupId, String content)
		throws PortalException {

		content = _excludeHTMLComments(content);

		_validateJournalArticleReferences(content);

		try {
			_defaultTextExportImportContentProcessor.validateContentReferences(
				groupId, content);
		}
		catch (ExportImportContentValidationException |
			   NoSuchFileEntryException | NoSuchLayoutException exception) {

			if (ExportImportThreadLocal.isImportInProcess()) {
				if (_log.isDebugEnabled()) {
					StringBundler sb = new StringBundler(8);

					sb.append("An invalid ");

					String type = "page";

					if ((exception instanceof NoSuchFileEntryException) ||
						(exception.getCause() instanceof
							NoSuchFileEntryException)) {

						type = "file entry";
					}

					sb.append(type);

					sb.append(" was detected during import when validating ");
					sb.append("the content below. This is not an error; it ");
					sb.append("typically means the ");
					sb.append(type);
					sb.append(" was deleted.\n");
					sb.append(content);

					_log.debug(sb.toString());
				}

				return;
			}

			throw exception;
		}
	}

	private String _excludeHTMLComments(String content) {
		Matcher matcher = _htmlCommentRegexPattern.matcher(content);

		while (matcher.find()) {
			content = matcher.replaceAll(StringPool.BLANK);

			matcher = _htmlCommentRegexPattern.matcher(content);
		}

		return content;
	}

	private List<String> _fetchContentsFromDDMFormValues(
		List<DDMFormFieldValue> ddmFormFieldValues) {

		return _fetchContentsFromDDMFormValues(
			new ArrayList<String>(), ddmFormFieldValues);
	}

	private List<String> _fetchContentsFromDDMFormValues(
		List<String> contents, List<DDMFormFieldValue> ddmFormFieldValues) {

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			contents = _fetchContentsFromDDMFormValues(
				contents, ddmFormFieldValue.getNestedDDMFormFieldValues());

			Value value = ddmFormFieldValue.getValue();

			if (value == null) {
				contents.add(StringPool.BLANK);

				continue;
			}

			for (Locale locale : value.getAvailableLocales()) {
				contents.add(value.getString(locale));
			}
		}

		return contents;
	}

	private DDMStructure _fetchDDMStructure(
		PortletDataContext portletDataContext, JournalArticle article) {

		return _ddmStructureLocalService.fetchStructure(
			portletDataContext.getScopeGroupId(),
			_portal.getClassNameId(JournalArticle.class),
			article.getDDMStructureKey(), true);
	}

	private Fields _getDDMStructureFields(
		DDMStructure ddmStructure, String content) {

		if ((ddmStructure == null) || Validator.isNull(content)) {
			return null;
		}

		try {
			return _journalConverter.getDDMFields(ddmStructure, content);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}

			return null;
		}
	}

	private String _replaceExportJournalArticleReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, DDMStructure ddmStructure, Fields fields,
			boolean exportReferencedContent)
		throws Exception {

		Group group = _groupLocalService.fetchGroup(
			portletDataContext.getGroupId());

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		if (group.isStaged() && !group.isStagedRemotely() &&
			!group.isStagedPortlet(JournalPortletKeys.JOURNAL)) {

			return content;
		}

		for (Field field : fields) {
			if (!Objects.equals(
					field.getType(),
					JournalArticleDDMFormFieldTypeConstants.JOURNAL_ARTICLE)) {

				continue;
			}

			for (Locale locale : field.getAvailableLocales()) {
				String json = String.valueOf(field.getValue(locale));

				JSONObject jsonObject = null;

				try {
					jsonObject = _jsonFactory.createJSONObject(json);
				}
				catch (JSONException jsonException) {
					if (_log.isDebugEnabled()) {
						_log.debug("Unable to parse JSON", jsonException);
					}

					continue;
				}

				long classPK = GetterUtil.getLong(jsonObject.get("classPK"));

				JournalArticle journalArticle =
					_journalArticleLocalService.fetchLatestArticle(classPK);

				if (journalArticle == null) {
					if (_log.isInfoEnabled()) {
						_log.info(
							StringBundler.concat(
								"Staged model with class name ",
								stagedModel.getModelClassName(),
								" and primary key ",
								stagedModel.getPrimaryKeyObj(),
								" references missing journal article with ",
								"class primary key ", classPK));
					}

					continue;
				}

				JSONObject newArticleJSONObject = JSONUtil.put(
					"articlePrimaryKey", journalArticle.getPrimaryKey());

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Replacing ", json, " with ",
							newArticleJSONObject.toJSONString()));
				}

				field.setValue(locale, newArticleJSONObject.toJSONString());

				if (exportReferencedContent) {
					try {
						StagedModelDataHandlerUtil.exportReferenceStagedModel(
							portletDataContext, stagedModel, journalArticle,
							PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
					}
					catch (Exception exception) {
						if (_log.isDebugEnabled()) {
							String errorMessage = StringBundler.concat(
								"Staged model with class name ",
								stagedModel.getModelClassName(),
								" and primary key ",
								stagedModel.getPrimaryKeyObj(),
								" references journal article with class ",
								"primary key ", classPK,
								" that could not be exported due to ",
								exception);

							if (Validator.isNotNull(exception.getMessage())) {
								errorMessage = StringBundler.concat(
									errorMessage, ": ", exception.getMessage());
							}

							_log.debug(errorMessage, exception);
						}
					}
				}
				else {
					Element entityElement =
						portletDataContext.getExportDataElement(stagedModel);

					portletDataContext.addReferenceElement(
						stagedModel, entityElement, journalArticle,
						PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
				}
			}
		}

		return _journalConverter.getContent(
			ddmStructure, fields, ddmStructure.getGroupId());
	}

	private String _replaceImportJournalArticleReferences(
			DDMStructure ddmStructure, Fields fields,
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {

		for (Field field : fields) {
			if (!Objects.equals(
					field.getType(),
					JournalArticleDDMFormFieldTypeConstants.JOURNAL_ARTICLE)) {

				continue;
			}

			for (Locale locale : field.getAvailableLocales()) {
				JSONObject jsonObject = null;

				Serializable serializable = field.getValue(locale);

				try {
					jsonObject = _jsonFactory.createJSONObject(
						serializable.toString());
				}
				catch (JSONException jsonException) {
					if (_log.isDebugEnabled()) {
						_log.debug("Unable to parse JSON", jsonException);
					}

					continue;
				}

				JournalArticle journalArticle = null;

				long articlePrimaryKey = GetterUtil.getLong(
					portletDataContext.getNewPrimaryKey(
						JournalArticle.class + ".primaryKey",
						jsonObject.getLong("articlePrimaryKey")));

				if (articlePrimaryKey != 0) {
					journalArticle =
						_journalArticleLocalService.fetchJournalArticle(
							articlePrimaryKey);
				}

				if (journalArticle == null) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to get journal article with primary key " +
								articlePrimaryKey);
					}

					portletDataContext.removePrimaryKey(
						ExportImportPathUtil.getModelPath(stagedModel));

					continue;
				}

				JSONObject newArticleJSONObject = JSONUtil.put(
					"className", JournalArticle.class.getName()
				).put(
					"classPK", journalArticle.getResourcePrimKey()
				).put(
					"title",
					journalArticle.getTitle(
						journalArticle.getDefaultLanguageId())
				).put(
					"titleMap", journalArticle.getTitleMap()
				);

				field.setValue(locale, newArticleJSONObject.toJSONString());
			}
		}

		return _journalConverter.getContent(
			ddmStructure, fields, ddmStructure.getGroupId());
	}

	private void _validateJournalArticleReferences(String content)
		throws PortalException {

		Throwable throwable = null;

		try {
			Document document = SAXReaderUtil.read(content);

			XPath xPath = SAXReaderUtil.createXPath(
				"//dynamic-element[@type='" +
					JournalArticleDDMFormFieldTypeConstants.JOURNAL_ARTICLE +
						"']");

			List<Node> ddmJournalArticleNodes = xPath.selectNodes(document);

			for (Node ddmJournalArticleNode : ddmJournalArticleNodes) {
				Element ddmJournalArticleElement =
					(Element)ddmJournalArticleNode;

				List<Element> dynamicContentElements =
					ddmJournalArticleElement.elements("dynamic-content");

				for (Element dynamicContentElement : dynamicContentElements) {
					String json = dynamicContentElement.getStringValue();

					JSONObject jsonObject = _jsonFactory.createJSONObject(json);

					long classPK = GetterUtil.getLong(
						jsonObject.get("classPK"));

					if (classPK <= 0) {
						if (_log.isDebugEnabled()) {
							_log.debug(
								"No journal article reference is specified");
						}

						continue;
					}

					JournalArticle journalArticle =
						_journalArticleLocalService.fetchLatestArticle(classPK);

					if (journalArticle != null) {
						continue;
					}

					if (ExportImportThreadLocal.isImportInProcess()) {
						if (_log.isDebugEnabled()) {
							_log.debug(
								StringBundler.concat(
									"An invalid web content article was ",
									"detected during import when validating ",
									"the content below. This is not an error; ",
									"it typically means the web content ",
									"article was deleted.\n", content));
						}

						return;
					}

					NoSuchArticleException noSuchArticleException =
						new NoSuchArticleException(
							StringBundler.concat(
								"No JournalArticle exists with the key ",
								"{resourcePrimKey=", classPK, "}"));

					if (throwable == null) {
						throwable = noSuchArticleException;
					}
					else {
						throwable.addSuppressed(noSuchArticleException);
					}
				}
			}
		}
		catch (DocumentException documentException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Invalid content:\n" + content, documentException);
			}
		}

		if (throwable != null) {
			ExportImportContentValidationException
				exportImportContentValidationException =
					new ExportImportContentValidationException(
						JournalArticleExportImportContentProcessor.class.
							getName(),
						throwable);

			exportImportContentValidationException.setType(
				ExportImportContentValidationException.ARTICLE_NOT_FOUND);

			throw exportImportContentValidationException;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleExportImportContentProcessor.class);

	private static final Pattern _htmlCommentRegexPattern = Pattern.compile(
		"\\<!--([\\s\\S]*)--\\>");

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.mapping.storage.DDMFormValues)"
	)
	private ExportImportContentProcessor<DDMFormValues>
		_ddmFormValuesExportImportContentProcessor;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference(target = "(model.class.name=java.lang.String)")
	private ExportImportContentProcessor<String>
		_defaultTextExportImportContentProcessor;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JournalArticleExportImportProcessorCache
		_journalArticleExportImportCache;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalConverter _journalConverter;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}