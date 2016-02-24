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
import com.liferay.journal.content.web.constants.JournalContentPortletKeys;
import com.liferay.journal.content.web.display.context.JournalContentDisplayContext;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalContentPortletKeys.JOURNAL_CONTENT,
		"path=-"
	},
	service = PortletConfigurationIcon.class
)
public class EditTemplatePortletConfigurationIcon
	extends BaseJournalArticlePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return LanguageUtil.get(
			getResourceBundle(getLocale(portletRequest)), "edit-template");
	}

	@Override
	public String getOnClick(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		StringBundler sb = new StringBundler(14);

		JournalContentDisplayContext journalContentDisplayContext =
			getJournalContentDisplayContext(portletRequest, portletResponse);

		DDMTemplate ddmTemplate = journalContentDisplayContext.getDDMTemplate();

		if (ddmTemplate == null) {
			return StringPool.BLANK;
		}

		sb.append("Liferay.Util.openWindow({bodyCssClass: ");
		sb.append("'dialog-with-footer', destroyOnHide: true, id: '");

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

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
		sb.append(
			HtmlUtil.escapeJS(
				journalContentDisplayContext.getURLEditTemplate()));
		sb.append("'}); return false;");

		return sb.toString();
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		JournalContentDisplayContext journalContentDisplayContext =
			getJournalContentDisplayContext(portletRequest, portletResponse);

		return journalContentDisplayContext.getURLEditTemplate();
	}

	@Override
	public double getWeight() {
		return 99.0;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		JournalContentDisplayContext journalContentDisplayContext =
			getJournalContentDisplayContext(portletRequest, null);

		if (journalContentDisplayContext.isShowEditTemplateIcon()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

}