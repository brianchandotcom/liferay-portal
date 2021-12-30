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

import com.liferay.exportimport.configuration.ExportImportServiceConfiguration;
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
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

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

		return _replaceExportJournalFeedReferences(
			portletDataContext, stagedModel, content, exportReferencedContent);
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		return _replaceImportJournalFeedReferences(
			portletDataContext, stagedModel, content);
	}

	@Override
	public void validateContentReferences(long groupId, String content)
		throws PortalException {

		if (_isValidateJournalFeedReferences()) {
			_validateJournalFeedReferences(groupId, content);
		}
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	private JournalFeed _getJournalFeed(Map<String, String> map) {
		if (MapUtil.isEmpty(map)) {
			return null;
		}

		JournalFeed journalFeed = null;

		try {
			String feedId = MapUtil.getString(map, "feedId");

			if (Validator.isNotNull(feedId)) {
				long groupId = MapUtil.getLong(map, "groupId");

				journalFeed = _journalFeedLocalService.getFeed(groupId, feedId);
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(exception.getMessage());
			}
		}

		return journalFeed;
	}

	private Map<String, String> _getJournalFeedReferenceParameters(
		long groupId, String content, int beginPos, int endPos) {

		endPos = StringUtil.indexOfAny(
			content, _JOURNAL_FEED_REFERENCE_STOP_CHARS, beginPos, endPos);

		if (endPos == -1) {
			return null;
		}

		String journalFeedReference = content.substring(
			beginPos + _JOURNAL_FEED_FRIENDLY_URL.length(), endPos);

		String[] pathArray = journalFeedReference.split(StringPool.SLASH);

		if (pathArray.length < 2) {
			return null;
		}

		return HashMapBuilder.put(
			"endPos", String.valueOf(endPos)
		).put(
			"feedId", pathArray[1]
		).put(
			"groupId",
			() -> {
				String groupIdString = pathArray[0];

				if (groupIdString.equals("@group_id@")) {
					return String.valueOf(groupId);
				}

				return groupIdString;
			}
		).build();
	}

	private boolean _isValidateJournalFeedReferences() {
		try {
			ExportImportServiceConfiguration configuration =
				_configurationProvider.getCompanyConfiguration(
					ExportImportServiceConfiguration.class,
					CompanyThreadLocal.getCompanyId());

			return configuration.validateJournalFeedReferences();
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return true;
	}

	private String _replaceExportJournalFeedReferences(
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
				_getJournalFeedReferenceParameters(
					portletDataContext.getScopeGroupId(), content, beginPos,
					endPos);

			JournalFeed journalFeed = _getJournalFeed(
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

				sb.replace(
					beginPos, endPos,
					StringBundler.concat(
						Portal.FRIENDLY_URL_SEPARATOR,
						"[$journalfeed-reference=", path, "$]"));
			}
			catch (Exception exception) {
				StringBundler exceptionSB = new StringBundler(6);

				exceptionSB.append("Unable to process journal feed ");
				exceptionSB.append(journalFeed.getFeedId());
				exceptionSB.append(" for staged model ");
				exceptionSB.append(stagedModel.getModelClassName());
				exceptionSB.append(" with primary key ");
				exceptionSB.append(stagedModel.getPrimaryKeyObj());

				ExportImportContentProcessorException
					exportImportContentProcessorException =
						new ExportImportContentProcessorException(
							exceptionSB.toString(), exception);

				if (_log.isDebugEnabled()) {
					_log.debug(
						exceptionSB.toString(),
						exportImportContentProcessorException);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(exceptionSB.toString());
				}
			}

			endPos = beginPos - 1;
		}

		return sb.toString();
	}

	private String _replaceImportJournalFeedReferences(
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

			if (Validator.isNull(path)) {
				long groupId = GetterUtil.getLong(
					referenceElement.attributeValue("group-id"));
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
			catch (Exception exception) {
				StringBundler exceptionSB = new StringBundler(6);

				exceptionSB.append("Unable to process journal feed ");
				exceptionSB.append(classPK);
				exceptionSB.append(" for ");
				exceptionSB.append(stagedModel.getModelClassName());
				exceptionSB.append(" with primary key ");
				exceptionSB.append(stagedModel.getPrimaryKeyObj());

				ExportImportContentProcessorException
					exportImportContentProcessorException =
						new ExportImportContentProcessorException(
							exceptionSB.toString(), exception);

				if (_log.isDebugEnabled()) {
					_log.debug(
						exceptionSB.toString(),
						exportImportContentProcessorException);
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
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException, portalException);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(portalException.getMessage());
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

	private void _validateJournalFeedReferences(long groupId, String content)
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
				_getJournalFeedReferenceParameters(
					groupId, content, beginPos, endPos);

			JournalFeed journalFeed = _getJournalFeed(
				journalFeedReferenceParameters);

			if (journalFeed == null) {
				ExportImportContentValidationException
					exportImportContentValidationException =
						new ExportImportContentValidationException(
							JournalFeedReferencesExportImportContentProcessor.
								class.getName(),
							new NoSuchFeedException());

				exportImportContentValidationException.setStagedModelClassName(
					JournalFeed.class.getName());

				throw exportImportContentValidationException;
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

	private ConfigurationProvider _configurationProvider;

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