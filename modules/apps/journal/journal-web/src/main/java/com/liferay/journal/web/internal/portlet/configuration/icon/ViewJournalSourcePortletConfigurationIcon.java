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

package com.liferay.journal.web.internal.portlet.configuration.icon;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.web.internal.portlet.action.ActionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"path=/edit_article.jsp"
	},
	service = PortletConfigurationIcon.class
)
public class ViewJournalSourcePortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return LanguageUtil.get(
			getResourceBundle(getLocale(portletRequest)), "view-source");
	}

	@Override
	public String getOnClick(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		JournalArticle article = null;

		try {
			article = ActionUtil.getArticle(portletRequest);
		}
		catch (Exception e) {
		}

		if (article == null) {
			return StringPool.BLANK;
		}

		return "var sourceModal = " + getWindowJS(portletRequest, article) +
			" return false;";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundle classResourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return new AggregateResourceBundle(
			classResourceBundle, _portal.getResourceBundle(locale));
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return "javascript:;";
	}

	@Override
	public double getWeight() {
		return 100.0;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		try {
			JournalArticle article = ActionUtil.getArticle(portletRequest);

			if (article != null) {
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

	protected String getDialogJS(JournalArticle article) {
		StringBundler sb = new StringBundler(6);

		sb.append("{bodyContent: '<pre>");
		sb.append(HtmlUtil.escapeJS(HtmlUtil.escape(article.getContent())));
		sb.append("</pre>', modal: true, toolbars: {footer: ");
		sb.append("[{label:Liferay.Language.get('close'), on: {click: ");
		sb.append("function(event) {event.domEvent.preventDefault(); ");
		sb.append("sourceModal.hide();}}}]}}");

		return sb.toString();
	}

	protected String getWindowJS(
		PortletRequest portletRequest, JournalArticle article) {

		StringBundler sb = new StringBundler(14);

		sb.append("Liferay.Util.Window.getWindow({bodyCssClass: ");
		sb.append("'dialog-with-footer', destroyOnHide: true, dialog: ");
		sb.append(getDialogJS(article));
		sb.append(", id: '");

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		sb.append(HtmlUtil.escape(portletDisplay.getNamespace()));

		sb.append("viewSource', namespace: '");
		sb.append(portletDisplay.getNamespace());
		sb.append("', portlet: '#p_p_id_");
		sb.append(portletDisplay.getId());
		sb.append("_', portletId: '");
		sb.append(portletDisplay.getId());
		sb.append("', title: '");
		sb.append(
			HtmlUtil.escapeJS(
				HtmlUtil.escape(article.getTitle(themeDisplay.getLocale()))));
		sb.append("'});");

		return sb.toString();
	}

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private Portal _portal;

}