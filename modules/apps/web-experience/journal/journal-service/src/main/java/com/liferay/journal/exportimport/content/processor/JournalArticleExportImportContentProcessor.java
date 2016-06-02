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

package com.liferay.journal.exportimport.content.processor;

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
						_log.info(
							"Skipping JournalArticle with classPK = " +
								classPK + " not found; referenced by " +
								stagedModel.getModelClassName() + ":" +
								stagedModel.getPrimaryKeyObj());
					}

					continue;
				}

				String jaReference =
					"[$ja-reference=" + journalArticle.getPrimaryKey() + "$]";

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Replacing: " + jsonData + " with " + jaReference);
				}

				sb.replace(beginPos, endPos, jaReference);

				endPos = beginPos + jaReference.length();

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

			try {
				StagedModelDataHandlerUtil.importReferenceStagedModel(
					portletDataContext, stagedModel, JournalArticle.class,
					classPK);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
				else if (_log.isWarnEnabled()) {
					StringBundler sb = new StringBundler(6);

					sb.append("Unable to process journal article ");
					sb.append(classPK);
					sb.append(" for ");
					sb.append(stagedModel.getModelClassName());
					sb.append(" with primary key ");
					sb.append(stagedModel.getPrimaryKeyObj());

					_log.warn(sb.toString());
				}
			}

			Map<Long, Long> primaryKeys =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					JournalArticle.class + ".primaryKey");

			long primaryKey = MapUtil.getLong(primaryKeys, classPK, classPK);

			JournalArticle journalArticle =
				_journalArticleLocalService.fetchJournalArticle(primaryKey);

			if (journalArticle == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to fetch article with id: " + primaryKey);
				}

				throw new NoSuchArticleException("id = " + primaryKey);
			}
			else {
				String journalArticleReference =
					"[$ja-reference=" + classPK + "$]";

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
						_log.debug("No reference specified.");
					}

					continue;
				}

				JSONObject jsonObject = _jsonFactory.createJSONObject(json);

				long classPK = GetterUtil.getLong(jsonObject.get("classPK"));

				JournalArticle journalArticle =
					_journalArticleLocalService.fetchLatestArticle(classPK);

				if (journalArticle == null) {
					Throwable throwable = new NoSuchArticleException(
						"resourcePrimKey=" + classPK);

					throwables.add(throwable);
				}
			}
		}

		if (!throwables.isEmpty()) {
			throw new PortalException(
				new BulkException("Content validation failure", throwables));
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