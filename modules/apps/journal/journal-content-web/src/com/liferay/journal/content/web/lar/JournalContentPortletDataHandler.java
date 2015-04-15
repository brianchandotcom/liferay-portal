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

package com.liferay.journal.content.web.lar;

import com.liferay.journal.content.web.configuration.JournalContentWebConfigurationValues;
import com.liferay.journal.content.web.constants.JournalContentPortletKeys;
import com.liferay.journal.web.lar.JournalPortletDataHandler;
import com.liferay.portal.kernel.lar.DataLevel;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalPermission;

import java.util.Map;

import javax.portlet.PortletPreferences;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * <p>
 * Provides the Journal Content portlet export and import functionality, which
 * is to clone the article, structure, and template referenced in the Journal
 * Content portlet if the article is associated with the layout's group. Upon
 * import, a new instance of the corresponding article, structure, and template
 * will be created or updated. The author of the newly created objects are
 * determined by the JournalCreationStrategy class defined in
 * <i>portal.properties</i>.
 * </p>
 *
 * <p>
 * This <code>PortletDataHandler</code> differs from from
 * <code>JournalPortletDataHandlerImpl</code> in that it only exports articles
 * referenced in Journal Content portlets. Articles not displayed in Journal
 * Content portlets will not be exported unless
 * <code>JournalPortletDataHandlerImpl</code> is activated.
 * </p>
 *
 * @author Joel Kozikowski
 * @author Raymond Augé
 * @author Bruno Farache
 * @author Daniel Kocsis
 * @see    com.liferay.journal.web.lar.JournalPortletDataHandler
 * @see    com.liferay.portal.kernel.lar.PortletDataHandler
 * @see    com.liferay.portlet.journal.lar.JournalCreationStrategy
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalContentPortletKeys.JOURNAL_CONTENT
	},
	service = PortletDataHandler.class
)
public class JournalContentPortletDataHandler
	extends JournalPortletDataHandler {

	@Activate
	protected void activate() {
		setDataLevel(DataLevel.PORTLET_INSTANCE);
		setDataPortletPreferences("articleId", "ddmTemplateKey", "groupId");
		setExportControls(
			new PortletDataHandlerBoolean(
				null, "selected-web-content", true, true, null,
				JournalArticle.class.getName()));
		setPublishToLiveByDefault(
			JournalContentWebConfigurationValues.PUBLISH_TO_LIVE_BY_DEFAULT);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletPreferences == null) {
			return portletPreferences;
		}

		portletPreferences.setValue("articleId", StringPool.BLANK);
		portletPreferences.setValue("ddmTemplateKey", StringPool.BLANK);
		portletPreferences.setValue("groupId", StringPool.BLANK);

		return portletPreferences;
	}

	@Override
	protected PortletPreferences doProcessExportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPortletPermissions(
			JournalPermission.RESOURCE_NAME);

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

		article = JournalArticleLocalServiceUtil.fetchLatestArticle(
			articleGroupId, articleId, WorkflowConstants.STATUS_APPROVED);

		if (article == null) {
			article = JournalArticleLocalServiceUtil.fetchLatestArticle(
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

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, portletId, article);

		String defaultDDMTemplateKey = article.getDDMTemplateKey();
		String preferenceDDMTemplateKey = portletPreferences.getValue(
			"ddmTemplateKey", null);

		if (Validator.isNotNull(defaultDDMTemplateKey) &&
			Validator.isNotNull(preferenceDDMTemplateKey) &&
			!defaultDDMTemplateKey.equals(preferenceDDMTemplateKey)) {

			DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(
				article.getGroupId(),
				PortalUtil.getClassNameId(DDMStructure.class),
				preferenceDDMTemplateKey, true);

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, article, ddmTemplate,
				PortletDataContext.REFERENCE_TYPE_STRONG);
		}

		portletDataContext.setScopeGroupId(previousScopeGroupId);

		return portletPreferences;
	}

	@Override
	protected PortletPreferences doProcessImportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.importPortletPermissions(
			JournalPermission.RESOURCE_NAME);

		long previousScopeGroupId = portletDataContext.getScopeGroupId();

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long importGroupId = GetterUtil.getLong(
			portletPreferences.getValue("groupId", null));

		long groupId = MapUtil.getLong(groupIds, importGroupId, importGroupId);

		portletDataContext.setScopeGroupId(groupId);

		StagedModelDataHandlerUtil.importReferenceStagedModels(
			portletDataContext, DDMStructure.class);

		StagedModelDataHandlerUtil.importReferenceStagedModels(
			portletDataContext, DDMTemplate.class);

		StagedModelDataHandlerUtil.importReferenceStagedModels(
			portletDataContext, JournalArticle.class);

		String articleId = portletPreferences.getValue("articleId", null);

		if (Validator.isNotNull(articleId)) {
			Map<String, String> articleIds =
				(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
					JournalArticle.class + ".articleId");

			articleId = MapUtil.getString(articleIds, articleId, articleId);

			portletPreferences.setValue("articleId", articleId);

			portletPreferences.setValue("groupId", String.valueOf(groupId));

			Layout layout = LayoutLocalServiceUtil.getLayout(
				portletDataContext.getPlid());

			JournalContentSearchLocalServiceUtil.updateContentSearch(
				layout.getGroupId(), layout.getLayoutId(), portletId, articleId,
				true);
		}
		else {
			portletPreferences.setValue("groupId", StringPool.BLANK);
			portletPreferences.setValue("articleId", StringPool.BLANK);
		}

		String ddmTemplateKey = portletPreferences.getValue(
			"ddmTemplateKey", null);

		if (Validator.isNotNull(ddmTemplateKey)) {
			Map<String, String> ddmTemplateKeys =
				(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
					DDMTemplate.class + ".ddmTemplateKey");

			ddmTemplateKey = MapUtil.getString(
				ddmTemplateKeys, ddmTemplateKey, ddmTemplateKey);

			portletPreferences.setValue("ddmTemplateKey", ddmTemplateKey);
		}
		else {
			portletPreferences.setValue("ddmTemplateKey", StringPool.BLANK);
		}

		portletDataContext.setScopeGroupId(previousScopeGroupId);

		return portletPreferences;
	}

	@Reference(target = "(original.bean=*)", unbind = "-")
	protected void setServletContext(ServletContext servletContext) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalContentPortletDataHandler.class);

}