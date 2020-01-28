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
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesTransformer;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.exception.ExportImportContentValidationException;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

		if (fields != null) {
			DDMFormValues ddmFormValues = _journalConverter.getDDMFormValues(
				ddmStructure, fields);

			ddmFormValues =
				_ddmFormValuesExportImportContentProcessor.
					replaceExportContentReferences(
						portletDataContext, stagedModel, ddmFormValues, true,
						true);

			DDMFormValuesTransformer ddmFormValuesTransformer =
				new DDMFormValuesTransformer(ddmFormValues);

			ImageExportDDMFormFieldValueTransformer
				imageExportDDMFormFieldValueTransformer =
					new ImageExportDDMFormFieldValueTransformer(
						content, _dlAppService, exportReferencedContent,
						portletDataContext, stagedModel);

			ddmFormValuesTransformer.addTransformer(
				imageExportDDMFormFieldValueTransformer);

			ddmFormValuesTransformer.transform();
		}

		content = replaceExportJournalArticleReferences(
			portletDataContext, stagedModel, content, exportReferencedContent);

		content =
			_defaultTextExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, stagedModel, content,
					exportReferencedContent, escapeContent);

		return content;
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		JournalArticle article = (JournalArticle)stagedModel;

		content = replaceImportJournalArticleReferences(
			portletDataContext, stagedModel, content);

		DDMStructure ddmStructure = _fetchDDMStructure(
			portletDataContext, article);

		content = replaceImportJournalArticleReferences(
			portletDataContext, stagedModel, content);

		Fields fields = _getDDMStructureFields(ddmStructure, content);

		if (fields != null) {
			DDMFormValues ddmFormValues = _journalConverter.getDDMFormValues(
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
						content, _dlAppService, portletDataContext,
						stagedModel);

			ddmFormValuesTransformer.addTransformer(
				imageImportDDMFormFieldValueTransformer);

			ddmFormValuesTransformer.transform();

			content = imageImportDDMFormFieldValueTransformer.getContent();
		}

		content =
			_defaultTextExportImportContentProcessor.
				replaceImportContentReferences(
					portletDataContext, stagedModel, content);

		return content;
	}

	@Override
	public void validateContentReferences(long groupId, String content)
		throws PortalException {

		content = _excludeHTMLComments(content);

		validateJournalArticleReferences(content);

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

	protected String replaceExportJournalArticleReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent)
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

		Document document = null;

		try {
			document = SAXReaderUtil.read(content);
		}
		catch (DocumentException documentException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Invalid content:\n" + content);
			}

			return content;
		}

		XPath xPath = SAXReaderUtil.createXPath(
			"//dynamic-element[@type='ddm-journal-article']");

		List<Node> ddmJournalArticleNodes = xPath.selectNodes(document);

		for (Node ddmJournalArticleNode : ddmJournalArticleNodes) {
			Element ddmJournalArticleElement = (Element)ddmJournalArticleNode;

			List<Element> dynamicContentElements =
				ddmJournalArticleElement.elements("dynamic-content");

			for (Element dynamicContentElement : dynamicContentElements) {
				String jsonData = dynamicContentElement.getStringValue();

				JSONObject jsonObject = null;

				try {
					jsonObject = _jsonFactory.createJSONObject(jsonData);
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
						StringBundler messageSB = new StringBundler(7);

						messageSB.append("Staged model with class name ");
						messageSB.append(stagedModel.getModelClassName());
						messageSB.append(" and primary key ");
						messageSB.append(stagedModel.getPrimaryKeyObj());
						messageSB.append(" references missing journal ");
						messageSB.append("article with class primary key ");
						messageSB.append(classPK);

						_log.info(messageSB.toString());
					}

					continue;
				}

				String journalArticleReference =
					"[$journal-article-reference=" +
						journalArticle.getPrimaryKey() + "$]";

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Replacing ", jsonData, " with ",
							journalArticleReference));
				}

				dynamicContentElement.clearContent();

				dynamicContentElement.addCDATA(journalArticleReference);

				if (exportReferencedContent) {
					try {
						StagedModelDataHandlerUtil.exportReferenceStagedModel(
							portletDataContext, stagedModel, journalArticle,
							PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
					}
					catch (Exception exception) {
						if (_log.isDebugEnabled()) {
							StringBundler messageSB = new StringBundler(10);

							messageSB.append("Staged model with class name ");
							messageSB.append(stagedModel.getModelClassName());
							messageSB.append(" and primary key ");
							messageSB.append(stagedModel.getPrimaryKeyObj());
							messageSB.append(" references journal article ");
							messageSB.append("with class primary key ");
							messageSB.append(classPK);
							messageSB.append(" that could not be exported ");
							messageSB.append("due to ");
							messageSB.append(exception);

							String errorMessage = messageSB.toString();

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

		return document.asXML();
	}

	protected String replaceImportJournalArticleReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		List<Element> referenceElements =
			portletDataContext.getReferenceElements(
				stagedModel, JournalArticle.class);

		for (Element referenceElement : referenceElements) {
			JournalArticle journalArticle = null;

			long classPK = GetterUtil.getLong(
				referenceElement.attributeValue("class-pk"));

			long articlePrimaryKey = GetterUtil.getLong(
				portletDataContext.getNewPrimaryKey(
					JournalArticle.class + ".primaryKey", classPK));

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

			String journalArticleReference =
				"[$journal-article-reference=" + classPK + "$]";

			JSONObject jsonObject = _jsonFactory.createJSONObject();

			jsonObject.put(
				"className", JournalArticle.class.getName()
			).put(
				"classPK", journalArticle.getResourcePrimKey()
			).put(
				"title",
				journalArticle.getTitle(journalArticle.getDefaultLanguageId())
			).put(
				"titleMap", journalArticle.getTitleMap()
			);

			content = StringUtil.replace(
				content, journalArticleReference, jsonObject.toString());
		}

		return content;
	}

	protected void validateJournalArticleReferences(String content)
		throws PortalException {

		Throwable throwable = null;

		try {
			Document document = SAXReaderUtil.read(content);

			XPath xPath = SAXReaderUtil.createXPath(
				"//dynamic-element[@type='ddm-journal-article']");

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
							StringBundler sb = new StringBundler(6);

							sb.append("An invalid web content article was ");
							sb.append("detected during import when ");
							sb.append("validating the content below. This is ");
							sb.append("not an error; it typically means the ");
							sb.append("web content article was deleted.\n");
							sb.append(content);

							_log.debug(sb.toString());
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
				_log.debug("Invalid content:\n" + content);
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

		long formerGroupId = article.getGroupId();

		article.setGroupId(portletDataContext.getScopeGroupId());

		DDMStructure ddmStructure = article.getDDMStructure();

		article.setGroupId(formerGroupId);

		return ddmStructure;
	}

	private Fields _getDDMStructureFields(
		DDMStructure ddmStructure, String content) {

		if (ddmStructure == null) {
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

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleExportImportContentProcessor.class);

	private static final Pattern _htmlCommentRegexPattern = Pattern.compile(
		"\\<![ \\r\\n\\t]*(--([^\\-]|[\\r\\n]|-[^\\-])*--" +
			"[ \\r\\n\\t]*)\\>");

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.mapping.storage.DDMFormValues)"
	)
	private ExportImportContentProcessor<DDMFormValues>
		_ddmFormValuesExportImportContentProcessor;

	@Reference(target = "(model.class.name=java.lang.String)")
	private ExportImportContentProcessor<String>
		_defaultTextExportImportContentProcessor;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalConverter _journalConverter;

	@Reference
	private JSONFactory _jsonFactory;

}