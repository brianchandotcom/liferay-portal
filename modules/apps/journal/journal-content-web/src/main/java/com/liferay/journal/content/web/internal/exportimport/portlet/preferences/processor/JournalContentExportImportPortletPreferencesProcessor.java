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

package com.liferay.journal.content.web.internal.exportimport.portlet.preferences.processor;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.journal.constants.JournalConstants;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.journal.service.JournalContentSearchLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.PortletPreferencesImpl;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + JournalContentPortletKeys.JOURNAL_CONTENT,
	service = ExportImportPortletPreferencesProcessor.class
)
public class JournalContentExportImportPortletPreferencesProcessor
	implements ExportImportPortletPreferencesProcessor {

	@Override
	public List<Capability> getExportCapabilities() {
		return ListUtil.fromArray(
			_journalContentMetadataExporterImporterCapability);
	}

	@Override
	public List<Capability> getImportCapabilities() {
		return ListUtil.fromArray(
			_journalContentMetadataExporterImporterCapability, _capability);
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		String portletId = portletDataContext.getPortletId();

		try {
			portletDataContext.addPortletPermissions(
				JournalConstants.RESOURCE_NAME);
		}
		catch (PortalException pe) {
			PortletDataException pde = new PortletDataException(pe);

			pde.setPortletId(JournalContentPortletKeys.JOURNAL_CONTENT);
			pde.setType(PortletDataException.EXPORT_PORTLET_PERMISSIONS);

			throw pde;
		}

		String articleId = portletPreferences.getValue("articleId", null);

		if (articleId == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No article ID found in preferences of portlet " +
						portletId);
			}

			return portletPreferences;
		}

		long articleGroupId = GetterUtil.getLong(
			portletPreferences.getValue("groupId", StringPool.BLANK));

		if (articleGroupId <= 0) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No group ID found in preferences of portlet " + portletId);
			}

			return portletPreferences;
		}

		Group group = _groupLocalService.fetchGroup(articleGroupId);

		if (group == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No group found with group ID " + articleGroupId);
			}

			return portletPreferences;
		}

		if (ExportImportThreadLocal.isStagingInProcess() &&
			!group.isStagedPortlet(JournalPortletKeys.JOURNAL)) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Web content is not staged in the site " + group.getName());
			}

			return portletPreferences;
		}

		long previousScopeGroupId = portletDataContext.getScopeGroupId();

		if (articleGroupId != previousScopeGroupId) {
			portletDataContext.setScopeGroupId(articleGroupId);
		}

		JournalArticle article = null;

		JournalArticleResource journalArticleResource =
			_journalArticleResourceLocalService.fetchArticleResource(
				articleGroupId, articleId);

		if (journalArticleResource != null) {
			int[] statuses = {
				WorkflowConstants.STATUS_APPROVED,
				WorkflowConstants.STATUS_EXPIRED,
				WorkflowConstants.STATUS_SCHEDULED
			};

			article = _journalArticleLocalService.fetchLatestArticle(
				journalArticleResource.getResourcePrimKey(), statuses);
		}

		if (article == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Portlet ", portletId,
						" refers to an invalid article ID ", articleId));
			}

			portletDataContext.setScopeGroupId(previousScopeGroupId);

			return portletPreferences;
		}

		if (!MapUtil.getBoolean(
				portletDataContext.getParameterMap(),
				PortletDataHandlerKeys.PORTLET_DATA) &&
			MergeLayoutPrototypesThreadLocal.isInProgress()) {

			portletDataContext.setScopeGroupId(previousScopeGroupId);

			return portletPreferences;
		}

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()});

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, portletId, article);

		String defaultDDMTemplateKey = article.getDDMTemplateKey();
		String preferenceDDMTemplateKey = portletPreferences.getValue(
			"ddmTemplateKey", null);

		if (Validator.isNotNull(defaultDDMTemplateKey) &&
			Validator.isNotNull(preferenceDDMTemplateKey) &&
			!defaultDDMTemplateKey.equals(preferenceDDMTemplateKey)) {

			try {
				DDMTemplate ddmTemplate =
					_ddmTemplateLocalService.fetchTemplate(
						article.getGroupId(),
						_portal.getClassNameId(DDMStructure.class),
						preferenceDDMTemplateKey, true);

				if (ddmTemplate == null) {
					ddmTemplate = _ddmTemplateLocalService.getTemplate(
						article.getGroupId(),
						_portal.getClassNameId(DDMStructure.class),
						defaultDDMTemplateKey, true);

					portletPreferences.setValue(
						"ddmTemplateKey", defaultDDMTemplateKey);
				}

				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, article, ddmTemplate,
					PortletDataContext.REFERENCE_TYPE_STRONG);
			}
			catch (PortalException | ReadOnlyException e) {
				PortletDataException pde = new PortletDataException(e);

				pde.setPortletId(JournalContentPortletKeys.JOURNAL_CONTENT);
				pde.setType(PortletDataException.EXPORT_REFERENCED_TEMPLATE);

				throw pde;
			}
		}

		portletDataContext.setScopeGroupId(previousScopeGroupId);

		return portletPreferences;
	}

	@Override
	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
			portletDataContext.importPortletPermissions(
				JournalConstants.RESOURCE_NAME);
		}
		catch (PortalException pe) {
			PortletDataException pde = new PortletDataException(pe);

			pde.setPortletId(JournalContentPortletKeys.JOURNAL_CONTENT);
			pde.setType(PortletDataException.IMPORT_PORTLET_PERMISSIONS);

			throw pde;
		}

		long previousScopeGroupId = portletDataContext.getScopeGroupId();

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long importGroupId = GetterUtil.getLong(
			portletPreferences.getValue("groupId", null));

		if ((importGroupId == portletDataContext.getCompanyGroupId()) &&
			MergeLayoutPrototypesThreadLocal.isInProgress()) {

			portletDataContext.setScopeType("company");
		}

		long groupId = MapUtil.getLong(groupIds, importGroupId, importGroupId);

		String articleId = portletPreferences.getValue("articleId", null);

		Map<String, Long> articleGroupIds =
			(Map<String, Long>)portletDataContext.getNewPrimaryKeysMap(
				JournalArticle.class + ".groupId");

		if (articleGroupIds.containsKey(articleId)) {
			groupId = articleGroupIds.get(articleId);
		}

		portletDataContext.setScopeGroupId(groupId);

		try {
			if (Validator.isNotNull(articleId) && (groupId != 0)) {
				Group importedArticleGroup = _groupLocalService.fetchGroup(
					groupId);

				if (importedArticleGroup == null) {
					if (_log.isDebugEnabled()) {
						_log.debug("No group found with group ID " + groupId);
					}

					return portletPreferences;
				}

				if (!ExportImportThreadLocal.isStagingInProcess() ||
					importedArticleGroup.isStagedPortlet(
						JournalPortletKeys.JOURNAL)) {

					Map<String, String> articleIds =
						(Map<String, String>)
							portletDataContext.getNewPrimaryKeysMap(
								JournalArticle.class + ".articleId");

					articleId = MapUtil.getString(
						articleIds, articleId, articleId);

					portletPreferences.setValue("articleId", articleId);

					portletPreferences.setValue(
						"groupId", String.valueOf(groupId));

					JournalArticle article =
						_journalArticleLocalService.fetchLatestArticle(
							groupId, articleId, WorkflowConstants.STATUS_ANY);

					if (article != null) {
						AssetEntry assetEntry =
							_assetEntryLocalService.fetchEntry(
								JournalArticle.class.getName(),
								article.getResourcePrimKey());

						if (assetEntry != null) {
							portletPreferences.setValue(
								"assetEntryId",
								String.valueOf(assetEntry.getEntryId()));
						}
					}

					int prefOwnerType = -1;

					if (portletPreferences instanceof PortletPreferencesImpl) {
						PortletPreferencesImpl portletPreferencesImpl =
							(PortletPreferencesImpl)portletPreferences;

						prefOwnerType = portletPreferencesImpl.getOwnerType();
					}

					if ((portletDataContext.getPlid() > 0) &&
						(prefOwnerType !=
							PortletKeys.PREFS_OWNER_TYPE_ARCHIVED)) {

						Layout layout = _layoutLocalService.fetchLayout(
							portletDataContext.getPlid());

						_journalContentSearchLocalService.updateContentSearch(
							layout.getGroupId(), layout.isPrivateLayout(),
							layout.getLayoutId(),
							portletDataContext.getPortletId(), articleId, true);
					}
				}
			}

			String ddmTemplateKey = portletPreferences.getValue(
				"ddmTemplateKey", null);

			if (Validator.isNotNull(ddmTemplateKey)) {
				Map<String, String> ddmTemplateKeys =
					(Map<String, String>)
						portletDataContext.getNewPrimaryKeysMap(
							DDMTemplate.class + ".ddmTemplateKey");

				ddmTemplateKey = MapUtil.getString(
					ddmTemplateKeys, ddmTemplateKey, ddmTemplateKey);

				portletPreferences.setValue("ddmTemplateKey", ddmTemplateKey);
			}
		}
		catch (PortalException pe) {
			PortletDataException pde = new PortletDataException(pe);

			pde.setPortletId(JournalContentPortletKeys.JOURNAL_CONTENT);
			pde.setType(
				PortletDataException.UPDATE_JOURNAL_CONTENT_SEARCH_DATA);

			throw pde;
		}
		catch (ReadOnlyException roe) {
			PortletDataException pde = new PortletDataException(roe);

			pde.setPortletId(JournalContentPortletKeys.JOURNAL_CONTENT);
			pde.setType(PortletDataException.UPDATE_PORTLET_PREFERENCES);

			throw pde;
		}

		portletDataContext.setScopeGroupId(previousScopeGroupId);

		return portletPreferences;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalContentExportImportPortletPreferencesProcessor.class);

	@Reference(unbind = "-")
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference(target = "(name=ReferencedStagedModelImporter)")
	private Capability _capability;

	@Reference(unbind = "-")
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference(unbind = "-")
	private GroupLocalService _groupLocalService;

	@Reference(unbind = "-")
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalArticleResourceLocalService
		_journalArticleResourceLocalService;

	@Reference
	private JournalContentMetadataExporterImporterCapability
		_journalContentMetadataExporterImporterCapability;

	@Reference(unbind = "-")
	private JournalContentSearchLocalService _journalContentSearchLocalService;

	@Reference(unbind = "-")
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}