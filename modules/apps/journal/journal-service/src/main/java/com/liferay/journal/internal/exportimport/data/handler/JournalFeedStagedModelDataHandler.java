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

package com.liferay.journal.internal.exportimport.data.handler;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.journal.exception.FeedTargetLayoutFriendlyUrlException;
import com.liferay.journal.internal.exportimport.content.processor.JournalFeedExportImportContentProcessor;
import com.liferay.journal.internal.exportimport.creation.strategy.JournalCreationStrategy;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.service.JournalFeedLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class JournalFeedStagedModelDataHandler
	extends BaseStagedModelDataHandler<JournalFeed> {

	public static final String[] CLASS_NAMES = {JournalFeed.class.getName()};

	@Override
	public void deleteStagedModel(JournalFeed feed) throws PortalException {
		_journalFeedLocalService.deleteFeed(feed);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		JournalFeed feed = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (feed != null) {
			deleteStagedModel(feed);
		}
	}

	@Override
	public JournalFeed fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _journalFeedLocalService.fetchJournalFeedByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<JournalFeed> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _journalFeedLocalService.getJournalFeedsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<JournalFeed>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, JournalFeed feed)
		throws Exception {

		Element feedElement = portletDataContext.getExportDataElement(feed);

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			feed.getGroupId(), _portal.getClassNameId(JournalArticle.class),
			feed.getDDMStructureKey(), true);

		if (ddmStructure != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, feed, ddmStructure,
				PortletDataContext.REFERENCE_TYPE_STRONG);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find DDM structure with key " +
						feed.getDDMStructureKey());
			}
		}

		DDMTemplate ddmTemplate = _ddmTemplateLocalService.fetchTemplate(
			feed.getGroupId(), _portal.getClassNameId(DDMStructure.class),
			feed.getDDMTemplateKey());

		if (ddmTemplate != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, feed, ddmTemplate,
				PortletDataContext.REFERENCE_TYPE_STRONG);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find DDM template with key " +
						feed.getDDMTemplateKey());
			}
		}

		DDMTemplate rendererDDMTemplate =
			_ddmTemplateLocalService.fetchTemplate(
				feed.getGroupId(), _portal.getClassNameId(DDMStructure.class),
				feed.getDDMRendererTemplateKey());

		if (rendererDDMTemplate != null) {
			Element rendererDDMTemplateElement =
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, feed, rendererDDMTemplate,
					PortletDataContext.REFERENCE_TYPE_STRONG);

			rendererDDMTemplateElement.addAttribute(
				"rendererDDMTemplate", "true");
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find DDM template with key " +
						feed.getDDMRendererTemplateKey());
			}
		}

		_journalFeedExportImportContentProcessor.replaceExportContentReferences(
			portletDataContext, feed, StringPool.BLANK, true, true);

		portletDataContext.addClassedModel(
			feedElement, ExportImportPathUtil.getModelPath(feed), feed);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, JournalFeed feed)
		throws Exception {

		long userId = portletDataContext.getUserId(feed.getUserUuid());

		long authorId = _journalCreationStrategy.getAuthorUserId(
			portletDataContext, feed);

		if (authorId != JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {
			userId = authorId;
		}

		_journalFeedExportImportContentProcessor.replaceImportContentReferences(
			portletDataContext, feed, StringPool.BLANK);

		String feedId = feed.getFeedId();

		boolean autoFeedId = false;

		if (Validator.isNumber(feedId)) {
			autoFeedId = true;
		}
		else {
			JournalFeed existingFeed = _journalFeedLocalService.fetchFeed(
				portletDataContext.getScopeGroupId(), feedId);

			if (existingFeed != null) {
				autoFeedId = true;
			}
		}

		Map<String, String> ddmStructureKeys =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class + ".ddmStructureKey");

		String parentDDMStructureKey = MapUtil.getString(
			ddmStructureKeys, feed.getDDMStructureKey(),
			feed.getDDMStructureKey());

		Map<String, String> ddmTemplateKeys =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DDMTemplate.class + ".ddmTemplateKey");

		String parentDDMTemplateKey = MapUtil.getString(
			ddmTemplateKeys, feed.getDDMTemplateKey(),
			feed.getDDMTemplateKey());
		String parentRendererDDMTemplateKey = MapUtil.getString(
			ddmTemplateKeys, feed.getDDMRendererTemplateKey(),
			feed.getDDMRendererTemplateKey());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			feed);

		boolean addGroupPermissions =
			_journalCreationStrategy.addGroupPermissions(
				portletDataContext, feed);

		serviceContext.setAddGroupPermissions(addGroupPermissions);

		boolean addGuestPermissions =
			_journalCreationStrategy.addGuestPermissions(
				portletDataContext, feed);

		serviceContext.setAddGuestPermissions(addGuestPermissions);

		JournalFeed importedFeed = null;

		try {
			if (portletDataContext.isDataStrategyMirror()) {
				JournalFeed existingFeed = fetchStagedModelByUuidAndGroupId(
					feed.getUuid(), portletDataContext.getScopeGroupId());

				if (existingFeed == null) {
					serviceContext.setUuid(feed.getUuid());

					importedFeed = _journalFeedLocalService.addFeed(
						userId, portletDataContext.getScopeGroupId(), feedId,
						autoFeedId, feed.getName(), feed.getDescription(),
						parentDDMStructureKey, parentDDMTemplateKey,
						parentRendererDDMTemplateKey, feed.getDelta(),
						feed.getOrderByCol(), feed.getOrderByType(),
						feed.getTargetLayoutFriendlyUrl(),
						feed.getTargetPortletId(), feed.getContentField(),
						feed.getFeedFormat(), feed.getFeedVersion(),
						serviceContext);
				}
				else {
					importedFeed = _journalFeedLocalService.updateFeed(
						existingFeed.getGroupId(), existingFeed.getFeedId(),
						feed.getName(), feed.getDescription(),
						parentDDMStructureKey, parentDDMTemplateKey,
						parentRendererDDMTemplateKey, feed.getDelta(),
						feed.getOrderByCol(), feed.getOrderByType(),
						feed.getTargetLayoutFriendlyUrl(),
						feed.getTargetPortletId(), feed.getContentField(),
						feed.getFeedFormat(), feed.getFeedVersion(),
						serviceContext);
				}
			}
			else {
				importedFeed = _journalFeedLocalService.addFeed(
					userId, portletDataContext.getScopeGroupId(), feedId,
					autoFeedId, feed.getName(), feed.getDescription(),
					parentDDMStructureKey, parentDDMTemplateKey,
					parentRendererDDMTemplateKey, feed.getDelta(),
					feed.getOrderByCol(), feed.getOrderByType(),
					feed.getTargetLayoutFriendlyUrl(),
					feed.getTargetPortletId(), feed.getContentField(),
					feed.getFeedFormat(), feed.getFeedVersion(),
					serviceContext);
			}

			portletDataContext.importClassedModel(feed, importedFeed);

			if (!feedId.equals(importedFeed.getFeedId())) {
				if (_log.isWarnEnabled()) {
					StringBundler sb = new StringBundler(5);

					sb.append("A feed with the ID ");
					sb.append(feedId);
					sb.append(" already exists. The new generated ID is ");
					sb.append(importedFeed.getFeedId());
					sb.append(".");

					_log.warn(sb.toString());
				}
			}
		}
		catch (FeedTargetLayoutFriendlyUrlException ftlfue) {
			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(7);

				sb.append("A feed with the ID ");
				sb.append(feedId);
				sb.append(" cannot be imported because layout with friendly ");
				sb.append("URL ");
				sb.append(feed.getTargetLayoutFriendlyUrl());
				sb.append(" does not exist: ");
				sb.append(ftlfue.getMessage());

				_log.warn(sb.toString());
			}
		}
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMTemplateLocalService(
		DDMTemplateLocalService ddmTemplateLocalService) {

		_ddmTemplateLocalService = ddmTemplateLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalCreationStrategy(
		JournalCreationStrategy journalCreationStrategy) {

		_journalCreationStrategy = journalCreationStrategy;
	}

	@Reference(unbind = "-")
	protected void setJournalFeedExportImportContentProcessor(
		JournalFeedExportImportContentProcessor
			journalFeedExportImportContentProcessor) {

		_journalFeedExportImportContentProcessor =
			journalFeedExportImportContentProcessor;
	}

	@Reference(unbind = "-")
	protected void setJournalFeedLocalService(
		JournalFeedLocalService journalFeedLocalService) {

		_journalFeedLocalService = journalFeedLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalFeedStagedModelDataHandler.class);

	private DDMStructureLocalService _ddmStructureLocalService;
	private DDMTemplateLocalService _ddmTemplateLocalService;
	private JournalCreationStrategy _journalCreationStrategy;
	private JournalFeedExportImportContentProcessor
		_journalFeedExportImportContentProcessor;
	private JournalFeedLocalService _journalFeedLocalService;

	@Reference
	private Portal _portal;

}