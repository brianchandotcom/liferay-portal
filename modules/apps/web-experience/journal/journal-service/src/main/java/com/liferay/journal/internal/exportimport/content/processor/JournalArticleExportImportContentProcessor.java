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

import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.content.processor.base.BaseTextExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.exception.BulkException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = {"model.class.name=com.liferay.journal.model.JournalArticle"},
	service = {
		ExportImportContentProcessor.class,
		JournalArticleExportImportContentProcessor.class
	}
)
public class JournalArticleExportImportContentProcessor
	extends BaseTextExportImportContentProcessor {

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent,
			boolean escapeContent)
		throws Exception {

		content = replaceExportJournalArticleReferences(
			portletDataContext, stagedModel, content, exportReferencedContent);

		content = super.replaceExportContentReferences(
			portletDataContext, stagedModel, content, exportReferencedContent,
			escapeContent);

		return content;
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		content = replaceImportJournalArticleReferences(
			portletDataContext, stagedModel, content);

		content = super.replaceImportContentReferences(
			portletDataContext, stagedModel, content);

		return content;
	}

	@Override
	public void validateContentReferences(long groupId, String content)
		throws PortalException {

		validateJournalArticleReferences(groupId, content);

		super.validateContentReferences(groupId, content);
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

		StringBuilder sb = new StringBuilder(content);

		int beginPos = 0;
		int endPos = 0;

		while (true) {
			beginPos = sb.indexOf(_DDM_JOURNAL_ARTICLE_TYPE, endPos);

			if (beginPos == -1) {
				break;
			}

			endPos = beginPos;

			while (true) {
				beginPos = sb.indexOf(_CDATA_BEGIN, endPos);

				if (beginPos == -1) {
					break;
				}

				beginPos += _CDATA_BEGIN.length();

				endPos = sb.indexOf(_CDATA_END, beginPos);

				String jsonData = sb.substring(beginPos, endPos);

				JSONObject jsonObject = _jsonFactory.createJSONObject(jsonData);

				long classPK = GetterUtil.getLong(jsonObject.get("classPK"));

				JournalArticle journalArticle =
					_journalArticleLocalService.fetchLatestArticle(classPK);

				if (journalArticle == null) {
					if (_log.isInfoEnabled()) {
						StringBundler messageSB = new StringBundler();

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
						"Replacing " + jsonData + " with " +
							journalArticleReference);
				}

				sb.replace(beginPos, endPos, journalArticleReference);

				endPos = beginPos + journalArticleReference.length();

				if (exportReferencedContent) {
					StagedModelDataHandlerUtil.exportReferenceStagedModel(
						portletDataContext, stagedModel, journalArticle,
						PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
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

		return sb.toString();
	}

	protected String replaceImportJournalArticleReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		List<Element> referenceElements =
			portletDataContext.getReferenceElements(
				stagedModel, JournalArticle.class);

		for (Element referenceElement : referenceElements) {
			long classPK = GetterUtil.getLong(
				referenceElement.attributeValue("class-pk"));

			Map<Long, Long> articlePrimaryKeys =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					JournalArticle.class + ".primaryKey");

			long articlePrimaryKey = MapUtil.getLong(
				articlePrimaryKeys, classPK, classPK);

			JournalArticle journalArticle =
				_journalArticleLocalService.fetchJournalArticle(
					articlePrimaryKey);

			if (journalArticle == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get journal article with primary key " +
							articlePrimaryKey);
				}

				throw new NoSuchArticleException(
					"No JournalArticle exists with the key {id= " +
						articlePrimaryKey + "}");
			}
			else {
				String journalArticleReference =
					"[$journal-article-reference=" + classPK + "$]";

				JSONObject jsonObject = _jsonFactory.createJSONObject();

				jsonObject.put("className", JournalArticle.class.getName());
				jsonObject.put("classPK", journalArticle.getResourcePrimKey());

				content = StringUtil.replace(
					content, journalArticleReference, jsonObject.toString());
			}
		}

		return content;
	}

	protected void validateJournalArticleReferences(
			long groupId, String content)
		throws PortalException {

		StringBuilder sb = new StringBuilder(content);

		int beginPos = 0;
		int endPos = 0;

		List<Throwable> throwables = new ArrayList<>();

		while (true) {
			beginPos = sb.indexOf(_DDM_JOURNAL_ARTICLE_TYPE, endPos);

			if (beginPos == -1) {
				break;
			}

			endPos = beginPos;

			while (true) {
				beginPos = sb.indexOf(_CDATA_BEGIN, endPos);

				if (beginPos == -1) {
					break;
				}

				beginPos += _CDATA_BEGIN.length();

				endPos = sb.indexOf(_CDATA_END, beginPos);

				String json = sb.substring(beginPos, endPos);

				if (Validator.isNull(json)) {
					if (_log.isDebugEnabled()) {
						_log.debug("No journal article reference is specified");
					}

					continue;
				}

				JSONObject jsonObject = _jsonFactory.createJSONObject(json);

				long classPK = GetterUtil.getLong(jsonObject.get("classPK"));

				JournalArticle journalArticle =
					_journalArticleLocalService.fetchLatestArticle(classPK);

				if (journalArticle == null) {
					Throwable throwable = new NoSuchArticleException(
						"No JournalArticle exists with the key " +
							"{resourcePrimKey=" + classPK + "}");

					throwables.add(throwable);
				}
			}
		}

		if (!throwables.isEmpty()) {
			throw new PortalException(
				new BulkException(
					"Unable to validate jounral article references",
					throwables));
		}
	}

	private static final String _CDATA_BEGIN = "<![CDATA[";

	private static final String _CDATA_END = "]]>";

	private static final String _DDM_JOURNAL_ARTICLE_TYPE =
		"type=\"ddm-journal-article\"";

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleExportImportContentProcessor.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JSONFactory _jsonFactory;

}