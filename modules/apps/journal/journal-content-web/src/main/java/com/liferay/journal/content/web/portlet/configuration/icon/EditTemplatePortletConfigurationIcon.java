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

package com.liferay.journal.content.web.portlet.configuration.icon;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.journal.content.web.configuration.JournalContentPortletInstanceConfiguration;
import com.liferay.journal.content.web.display.context.JournalContentDisplayContext;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.RenderResponseFactory;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Pavel Savinov
 */
public class EditTemplatePortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public EditTemplatePortletConfigurationIcon(PortletRequest portletRequest) {
		super(portletRequest);

		createJournalContentDisplayContext(portletRequest);
	}

	@Override
	public String getMessage() {
		return "edit-template";
	}

	@Override
	public String getOnClick() {
		StringBundler sb = new StringBundler(14);

		DDMTemplate ddmTemplate = null;

		try {
			ddmTemplate = _journalContentDisplayContext.getDDMTemplate();
		}
		catch (Exception e) {
			_log.error("Unable to get current DDM template", e);
		}

		if (ddmTemplate == null) {
			return "";
		}

		sb.append("Liferay.Util.openWindow({bodyCssClass: ");
		sb.append("'dialog-with-footer', destroyOnHide: true, id: '");
		sb.append(HtmlUtil.escape(portletDisplay.getNamespace()));
		sb.append("editAsset', namespace: '");
		sb.append(portletDisplay.getNamespace());
		sb.append("', portlet: '#p_p_id_");
		sb.append(portletDisplay.getId());
		sb.append("_', portletId: '");
		sb.append(portletDisplay.getId());
		sb.append("', title: '");
		sb.append(ddmTemplate.getName(themeDisplay.getLocale()));
		sb.append("', uri: '");
		sb.append(HtmlUtil.escapeJS(getURL()));
		sb.append("'}); return false;");

		return sb.toString();
	}

	@Override
	public String getURL() {
		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest,
			PortletProviderUtil.getPortletId(
				DDMTemplate.class.getName(), PortletProvider.Action.EDIT),
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		DDMTemplate ddmTemplate = null;

		try {
			ddmTemplate = _journalContentDisplayContext.getDDMTemplate();
		}
		catch (Exception e) {
			_log.error("Unable to get current DDM template", e);
		}

		if (ddmTemplate == null) {
			return "";
		}

		PortletURL redirectURL = PortletURLFactoryUtil.create(
			portletRequest, portletDisplay.getId(), themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
			portletURL.setPortletMode(PortletMode.VIEW);

			redirectURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (Exception e) {
			_log.error("Unable to set URL window state and portlet mode", e);
		}

		redirectURL.setParameter(
			"mvcPath", "/update_journal_article_redirect.jsp");
		redirectURL.setParameter(
			"referringPortletResource", portletDisplay.getId());

		portletURL.setParameter("mvcPath", "/edit_template.jsp");
		portletURL.setParameter("redirect", redirectURL.toString());
		portletURL.setParameter("showBackURL", Boolean.FALSE.toString());
		portletURL.setParameter("showCacheableInput", Boolean.TRUE.toString());
		portletURL.setParameter(
			"groupId", String.valueOf(ddmTemplate.getGroupId()));
		portletURL.setParameter(
			"refererPortletName",
			PortletProviderUtil.getPortletId(
				JournalArticle.class.getName(), PortletProvider.Action.EDIT));
		portletURL.setParameter(
			"templateId", String.valueOf(ddmTemplate.getTemplateId()));
		portletURL.setParameter("showHeader", Boolean.FALSE.toString());

		return portletURL.toString();
	}

	@Override
	public boolean isShow() {
		try {
			if (!_journalContentDisplayContext.isShowEditTemplateIcon()) {
				return false;
			}

			DDMTemplate ddmTemplate =
				_journalContentDisplayContext.getDDMTemplate();

			if ((ddmTemplate != null) && !ddmTemplate.isNew()) {
				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	protected void createJournalContentDisplayContext(
		PortletRequest portletRequest) {

		try {
			PortletResponse portletResponse = RenderResponseFactory.create(
				(RenderRequestImpl)portletRequest, themeDisplay.getResponse(),
				portletDisplay.getPortletName(), themeDisplay.getCompanyId());

			JournalContentPortletInstanceConfiguration
				journalContentPortletInstanceConfiguration =
					portletDisplay.getPortletInstanceConfiguration(
						JournalContentPortletInstanceConfiguration.class);

			_journalContentDisplayContext = new JournalContentDisplayContext(
				portletRequest, portletResponse,
				journalContentPortletInstanceConfiguration);
		}
		catch (Exception e) {
			_log.error("Unable to create display context", e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditTemplatePortletConfigurationIcon.class);

	private JournalContentDisplayContext _journalContentDisplayContext;

}