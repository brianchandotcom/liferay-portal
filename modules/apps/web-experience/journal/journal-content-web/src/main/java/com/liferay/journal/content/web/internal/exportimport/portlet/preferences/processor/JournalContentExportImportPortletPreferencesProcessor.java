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

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.exportimport.portlet.preferences.processor.capability.ReferencedStagedModelImporterCapability;
import com.liferay.journal.content.web.constants.JournalContentPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalContentSearchLocalService;
import com.liferay.journal.service.permission.JournalPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalContentPortletKeys.JOURNAL_CONTENT
	},
	service = ExportImportPortletPreferencesProcessor.class
)
public class JournalContentExportImportPortletPreferencesProcessor
	implements ExportImportPortletPreferencesProcessor {

	@Override
	public List<Capability> getExportCapabilities() {
		return null;
	}

	@Override
	public List<Capability> getImportCapabilities() {
		return ListUtil.toList(
			new Capability[] {_referencedStagedModelImporterCapability});
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		String portletId = portletDataContext.getPortletId();

		try {
			portletDataContext.addPortletPermissions(
				JournalPermission.RESOURCE_NAME);
		}
		catch (PortalException pe) {
			throw new PortletDataException(
				"Unable to export portlet permissions", pe);
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

		long previousScopeGroupId = portletDataContext.getScopeGroupId();

		if (articleGroupId != previousScopeGroupId) {
			portletDataContext.setScopeGroupId(articleGroupId);
		}

		JournalArticle article = null;

		article = _journalArticleLocalService.fetchLatestArticle(
			articleGroupId, articleId, WorkflowConstants.STATUS_APPROVED);

		if (article == null) {
			article = _journalArticleLocalService.fetchLatestArticle(
				articleGroupId, articleId, WorkflowConstants.STATUS_EXPIRED);
		}

		if (article == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Portlet " + portletId +
						" refers to an invalid article ID " + articleId);
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
						PortalUtil.getClassNameId(DDMStructure.class),
						preferenceDDMTemplateKey, true);

				if (ddmTemplate == null) {
					ddmTemplate = _ddmTemplateLocalService.getTemplate(
						article.getGroupId(),
						PortalUtil.getClassNameId(DDMStructure.class),
						defaultDDMTemplateKey, true);

					portletPreferences.setValue(
						"ddmTemplateKey", defaultDDMTemplateKey);
				}

				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, article, ddmTemplate,
					PortletDataContext.REFERENCE_TYPE_STRONG);
			}
			catch (PortalException | ReadOnlyException e) {
				throw new PortletDataException(
					"Unable to export referenced article template", e);
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
				JournalPermission.RESOURCE_NAME);
		}
		catch (PortalException pe) {
			throw new PortletDataException(
				"Unable to import portlet permissions", pe);
		}

		long previousScopeGroupId = portletDataContext.getScopeGroupId();
		String previousScopeType = portletDataContext.getScopeType();

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

		portletDataContext.setScopeGroupId(groupId);

		String articleId = portletPreferences.getValue("articleId", null);

		try {
			if (Validator.isNotNull(articleId)) {
				Map<String, String> articleIds =
					(Map<String, String>)
						portletDataContext.getNewPrimaryKeysMap(
							JournalArticle.class + ".articleId");

				articleId = MapUtil.getString(articleIds, articleId, articleId);

				portletPreferences.setValue("articleId", articleId);

				portletPreferences.setValue("groupId", String.valueOf(groupId));

				if (portletDataContext.getPlid() > 0) {
					Layout layout = _layoutLocalService.fetchLayout(
						portletDataContext.getPlid());

					_journalContentSearchLocalService.updateContentSearch(
						layout.getGroupId(), layout.isPrivateLayout(),
						layout.getLayoutId(), portletDataContext.getPortletId(),
						articleId, true);
				}
			}
			else {
				portletPreferences.setValue("groupId", StringPool.BLANK);
				portletPreferences.setValue("articleId", StringPool.BLANK);
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
			else {
				portletPreferences.setValue("ddmTemplateKey", StringPool.BLANK);
			}
		}
		catch (PortalException pe) {
			throw new PortletDataException(
				"Unable to update journal content search data during import",
				pe);
		}
		catch (ReadOnlyException roe) {
			throw new PortletDataException(
				"Unable to update portlet preferences during import", roe);
		}

		portletDataContext.setScopeGroupId(previousScopeGroupId);
		portletDataContext.setScopeType(previousScopeType);

		return portletPreferences;
	}

	@Reference(unbind = "-")
	protected void setDDMTemplateLocalService(
		DDMTemplateLocalService ddmTemplateLocalService) {

		_ddmTemplateLocalService = ddmTemplateLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalContentSearchLocalService(
		JournalContentSearchLocalService journalContentSearchLocalService) {

		_journalContentSearchLocalService = journalContentSearchLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setReferencedStagedModelImporterCapability(
		ReferencedStagedModelImporterCapability
			referencedStagedModelImporterCapability) {

		_referencedStagedModelImporterCapability =
			referencedStagedModelImporterCapability;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalContentExportImportPortletPreferencesProcessor.class);

	private DDMTemplateLocalService _ddmTemplateLocalService;
	private JournalArticleLocalService _journalArticleLocalService;
	private JournalContentSearchLocalService _journalContentSearchLocalService;
	private LayoutLocalService _layoutLocalService;
	private ReferencedStagedModelImporterCapability
		_referencedStagedModelImporterCapability;

}