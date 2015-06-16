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

package com.liferay.journal.lar;

import com.liferay.journal.exception.FeedTargetLayoutFriendlyUrlException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.service.JournalFeedLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalService;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalService;
import com.liferay.portlet.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.ExportImportHelper;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;

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
			feed.getGroupId(), PortalUtil.getClassNameId(JournalArticle.class),
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
			feed.getGroupId(), PortalUtil.getClassNameId(DDMStructure.class),
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
				feed.getGroupId(),
				PortalUtil.getClassNameId(DDMStructure.class),
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

		Group group = _groupLocalService.getGroup(
			portletDataContext.getScopeGroupId());

		String newGroupFriendlyURL = group.getFriendlyURL().substring(1);

		String[] friendlyURLParts = StringUtil.split(
			feed.getTargetLayoutFriendlyUrl(), '/');

		String oldGroupFriendlyURL = friendlyURLParts[2];

		if (newGroupFriendlyURL.equals(oldGroupFriendlyURL)) {
			String targetLayoutFriendlyUrl = StringUtil.replaceFirst(
				feed.getTargetLayoutFriendlyUrl(),
				StringPool.SLASH + newGroupFriendlyURL + StringPool.SLASH,
				StringPool.SLASH +
					ExportImportHelper.DATA_HANDLER_GROUP_FRIENDLY_URL +
						StringPool.SLASH);

			feed.setTargetLayoutFriendlyUrl(targetLayoutFriendlyUrl);
		}

		Group targetLayoutGroup = _groupLocalService.fetchFriendlyURLGroup(
			portletDataContext.getCompanyId(),
			StringPool.SLASH + oldGroupFriendlyURL);

		boolean privateLayout = false;

		if (!PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING.equals(
				StringPool.SLASH + friendlyURLParts[1])) {

			privateLayout = true;
		}

		String targetLayoutFriendlyURL = StringPool.SLASH + friendlyURLParts[3];

		Layout targetLayout = _layoutLocalService.fetchLayoutByFriendlyURL(
			targetLayoutGroup.getGroupId(), privateLayout,
			targetLayoutFriendlyURL);

		portletDataContext.addReferenceElement(
			feed, feedElement, targetLayout,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);

		portletDataContext.addClassedModel(
			feedElement, ExportImportPathUtil.getModelPath(feed), feed);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, JournalFeed feed)
		throws Exception {

		long userId = portletDataContext.getUserId(feed.getUserUuid());

		JournalCreationStrategy creationStrategy =
			JournalCreationStrategyFactory.getInstance();

		long authorId = creationStrategy.getAuthorUserId(
			portletDataContext, feed);

		if (authorId != JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {
			userId = authorId;
		}

		Group group = _groupLocalService.getGroup(
			portletDataContext.getScopeGroupId());

		String newGroupFriendlyURL = group.getFriendlyURL().substring(1);

		String[] friendlyURLParts = StringUtil.split(
			feed.getTargetLayoutFriendlyUrl(), '/');

		String oldGroupFriendlyURL = friendlyURLParts[2];

		if (oldGroupFriendlyURL.equals(
				ExportImportHelper.DATA_HANDLER_GROUP_FRIENDLY_URL)) {

			feed.setTargetLayoutFriendlyUrl(
				StringUtil.replace(
					feed.getTargetLayoutFriendlyUrl(),
					ExportImportHelper.DATA_HANDLER_GROUP_FRIENDLY_URL,
					newGroupFriendlyURL));
		}

		String feedId = feed.getFeedId();

		boolean autoFeedId = false;

		if (Validator.isNumber(feedId) ||
			(_journalFeedLocalService.fetchFeed(
				portletDataContext.getScopeGroupId(), feedId) != null)) {

			autoFeedId = true;
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

		boolean addGroupPermissions = creationStrategy.addGroupPermissions(
			portletDataContext, feed);

		serviceContext.setAddGroupPermissions(addGroupPermissions);

		boolean addGuestPermissions = creationStrategy.addGuestPermissions(
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
		catch (FeedTargetLayoutFriendlyUrlException ftlfurle) {
			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(7);

				sb.append("A feed with the ID ");
				sb.append(feedId);
				sb.append(" cannot be imported because layout with friendly ");
				sb.append("URL ");
				sb.append(feed.getTargetLayoutFriendlyUrl());
				sb.append(" does not exist: ");
				sb.append(ftlfurle.getMessage());

				_log.warn(sb.toString());
			}
		}
	}

	@Reference
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference
	protected void setDDMTemplateLocalService(
		DDMTemplateLocalService ddmTemplateLocalService) {

		_ddmTemplateLocalService = ddmTemplateLocalService;
	}

	@Reference
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference
	protected void setJournalFeedLocalService(
		JournalFeedLocalService journalFeedLocalService) {

		_journalFeedLocalService = journalFeedLocalService;
	}

	@Reference
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalFeedStagedModelDataHandler.class);

	private DDMStructureLocalService _ddmStructureLocalService;
	private DDMTemplateLocalService _ddmTemplateLocalService;
	private GroupLocalService _groupLocalService;
	private JournalFeedLocalService _journalFeedLocalService;
	private LayoutLocalService _layoutLocalService;

}