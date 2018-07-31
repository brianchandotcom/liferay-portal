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
import com.liferay.exportimport.kernel.exception.ExportImportContentProcessorException;
import com.liferay.exportimport.kernel.exception.ExportImportContentValidationException;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.exception.NoSuchFeedException;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFeedLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Díaz
 */
@Component(
	immediate = true, property = "content.processor.type=JournalFeedReferences",
	service = ExportImportContentProcessor.class
)
public class JournalFeedReferencesExportImportContentProcessor
	implements ExportImportContentProcessor<String> {

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent,
			boolean escapeContent)
		throws Exception {

		return replaceExportJournalFeedReferences(
			portletDataContext, stagedModel, content, exportReferencedContent);
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		return replaceImportJournalFeedReferences(
			portletDataContext, stagedModel, content);
	}

	@Override
	public void validateContentReferences(long groupId, String content)
		throws PortalException {

		validateJournalFeedReferences(groupId, content);
	}

	protected JournalFeed getJournalFeed(Map<String, String> map) {
		if (MapUtil.isEmpty(map)) {
			return null;
		}

		JournalFeed journalFeed = null;

		try {
			String feedId = MapUtil.getString(map, "feedId");
			long groupId = MapUtil.getLong(map, "groupId");

			if (Validator.isNotNull(feedId)) {
				journalFeed = _journalFeedLocalService.getFeed(groupId, feedId);
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage());
			}
		}

		return journalFeed;
	}

	protected Map<String, String> getJournalFeedReferenceParameters(
		long groupId, String content, int beginPos, int endPos) {

		endPos = StringUtil.indexOfAny(
			content, _JOURNAL_FEED_REFERENCE_STOP_CHARS, beginPos, endPos);

		if (endPos == -1) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		String journalFeedReference = content.substring(
			beginPos + _JOURNAL_FEED_FRIENDLY_URL.length(), endPos);

		String[] pathArray = journalFeedReference.split(StringPool.SLASH);

		if (pathArray.length < 2) {
			return null;
		}

		map.put("endPos", String.valueOf(endPos));
		map.put("feedId", pathArray[1]);
		map.put("groupId", pathArray[0]);

		String groupIdString = MapUtil.getString(map, "groupId");

		if (groupIdString.equals("@group_id@")) {
			groupIdString = String.valueOf(groupId);

			map.put("groupId", groupIdString);
		}

		return map;
	}

	protected String replaceExportJournalFeedReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent)
		throws Exception {

		Group group = _groupLocalService.getGroup(
			portletDataContext.getGroupId());

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		if (group.isStaged() && !group.isStagedRemotely() &&
			!group.isStagedPortlet(JournalPortletKeys.JOURNAL)) {

			return content;
		}

		StringBuilder sb = new StringBuilder(content);

		String[] patterns = {_JOURNAL_FEED_FRIENDLY_URL};

		int beginPos = -1;
		int endPos = content.length();

		while (true) {
			beginPos = StringUtil.lastIndexOfAny(content, patterns, endPos);

			if (beginPos == -1) {
				break;
			}

			Map<String, String> journalFeedReferenceParameters =
				getJournalFeedReferenceParameters(
					portletDataContext.getScopeGroupId(), content, beginPos,
					endPos);

			JournalFeed journalFeed = getJournalFeed(
				journalFeedReferenceParameters);

			if (journalFeed == null) {
				endPos = beginPos - 1;

				continue;
			}

			endPos = MapUtil.getInteger(
				journalFeedReferenceParameters, "endPos");

			try {
				if (exportReferencedContent) {
					StagedModelDataHandlerUtil.exportReferenceStagedModel(
						portletDataContext, stagedModel, journalFeed,
						PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
				}
				else {
					Element entityElement =
						portletDataContext.getExportDataElement(stagedModel);

					String referenceType =
						PortletDataContext.REFERENCE_TYPE_DEPENDENCY;

					portletDataContext.addReferenceElement(
						stagedModel, entityElement, journalFeed, referenceType,
						true);
				}

				String path = ExportImportPathUtil.getModelPath(journalFeed);

				StringBundler exportedReferenceSB = new StringBundler(4);

				exportedReferenceSB.append(Portal.FRIENDLY_URL_SEPARATOR);
				exportedReferenceSB.append("[$journalfeed-reference=");
				exportedReferenceSB.append(path);
				exportedReferenceSB.append("$]");

				sb.replace(beginPos, endPos, exportedReferenceSB.toString());
			}
			catch (Exception e) {
				StringBundler exceptionSB = new StringBundler(6);

				exceptionSB.append("Unable to process journal feed ");
				exceptionSB.append(journalFeed.getFeedId());
				exceptionSB.append(" for staged model ");
				exceptionSB.append(stagedModel.getModelClassName());
				exceptionSB.append(" with primary key ");
				exceptionSB.append(stagedModel.getPrimaryKeyObj());

				ExportImportContentProcessorException eicpe =
					new ExportImportContentProcessorException(
						exceptionSB.toString(), e);

				if (_log.isDebugEnabled()) {
					_log.debug(exceptionSB.toString(), eicpe);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(exceptionSB.toString());
				}
			}

			endPos = beginPos - 1;
		}

		return sb.toString();
	}

	protected String replaceImportJournalFeedReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		List<Element> referenceElements =
			portletDataContext.getReferenceElements(
				stagedModel, JournalFeed.class);

		for (Element referenceElement : referenceElements) {
			Long classPK = GetterUtil.getLong(
				referenceElement.attributeValue("class-pk"));

			Element referenceDataElement =
				portletDataContext.getReferenceDataElement(
					stagedModel, JournalFeed.class, classPK);

			String path = null;

			if (referenceDataElement != null) {
				path = referenceDataElement.attributeValue("path");
			}

			long groupId = GetterUtil.getLong(
				referenceElement.attributeValue("group-id"));

			if (Validator.isNull(path)) {
				String className = referenceElement.attributeValue(
					"class-name");

				path = ExportImportPathUtil.getModelPath(
					groupId, className, classPK);
			}

			String exportedReference = StringBundler.concat(
				Portal.FRIENDLY_URL_SEPARATOR, "[$journalfeed-reference=", path,
				"$]");

			if (!content.contains(exportedReference)) {
				continue;
			}

			try {
				StagedModelDataHandlerUtil.importReferenceStagedModel(
					portletDataContext, stagedModel, JournalFeed.class,
					classPK);
			}
			catch (Exception e) {
				StringBundler exceptionSB = new StringBundler(6);

				exceptionSB.append("Unable to process journal feed ");
				exceptionSB.append(classPK);
				exceptionSB.append(" for ");
				exceptionSB.append(stagedModel.getModelClassName());
				exceptionSB.append(" with primary key ");
				exceptionSB.append(stagedModel.getPrimaryKeyObj());

				ExportImportContentProcessorException eicpe =
					new ExportImportContentProcessorException(
						exceptionSB.toString(), e);

				if (_log.isDebugEnabled()) {
					_log.debug(exceptionSB.toString(), eicpe);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(exceptionSB.toString());
				}
			}

			Map<Long, Long> journalFeedIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					JournalFeed.class);

			long journalFeedId = MapUtil.getLong(
				journalFeedIds, classPK, classPK);

			JournalFeed importedJournalFeed = null;

			try {
				importedJournalFeed = _journalFeedLocalService.getFeed(
					journalFeedId);
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(pe.getMessage());
				}

				continue;
			}

			String url = StringBundler.concat(
				_JOURNAL_FEED_FRIENDLY_URL, importedJournalFeed.getGroupId(),
				"/", importedJournalFeed.getFeedId());

			content = StringUtil.replace(content, exportedReference, url);
		}

		return content;
	}

	protected void validateJournalFeedReferences(long groupId, String content)
		throws PortalException {

		String[] patterns = {_JOURNAL_FEED_FRIENDLY_URL};

		int beginPos = -1;
		int endPos = content.length();

		while (true) {
			beginPos = StringUtil.lastIndexOfAny(content, patterns, endPos);

			if (beginPos == -1) {
				break;
			}

			Map<String, String> journalFeedReferenceParameters =
				getJournalFeedReferenceParameters(
					groupId, content, beginPos, endPos);

			JournalFeed journalFeed = getJournalFeed(
				journalFeedReferenceParameters);

			if (journalFeed == null) {
				ExportImportContentValidationException eicve =
					new ExportImportContentValidationException(
						JournalFeedReferencesExportImportContentProcessor.
							class.getName(),
						new NoSuchFeedException());

				eicve.setStagedModelClassName(JournalFeed.class.getName());

				throw eicve;
			}

			endPos = beginPos - 1;
		}
	}

	private static final String _JOURNAL_FEED_FRIENDLY_URL = "/-/journal/rss/";

	private static final char[] _JOURNAL_FEED_REFERENCE_STOP_CHARS = {
		CharPool.APOSTROPHE, CharPool.CLOSE_BRACKET, CharPool.CLOSE_CURLY_BRACE,
		CharPool.CLOSE_PARENTHESIS, CharPool.GREATER_THAN, CharPool.LESS_THAN,
		CharPool.PIPE, CharPool.POUND, CharPool.QUESTION, CharPool.QUOTE,
		CharPool.SPACE
	};

	private static final Log _log = LogFactoryUtil.getLog(
		JournalFeedReferencesExportImportContentProcessor.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalFeedLocalService _journalFeedLocalService;

	@Reference
	private Portal _portal;

}