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

package com.liferay.layout.seo.web.internal.servlet.taglib;

import com.liferay.layout.seo.kernel.LayoutSEOLink;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListMergeable;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(service = DynamicInclude.class)
public class OpenGraphTopHeadDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		try {
			if (!themeDisplay.isSignedIn() && layout.isPublicLayout()) {
				String completeURL = _portal.getCurrentCompleteURL(
					httpServletRequest);

				String canonicalURL = _portal.getCanonicalURL(
					completeURL, themeDisplay, layout, false, false);

				Map<Locale, String> alternateURLs = Collections.emptyMap();

				Set<Locale> availableLocales = _language.getAvailableLocales(
					themeDisplay.getSiteGroupId());

				if (availableLocales.size() > 1) {
					alternateURLs = _portal.getAlternateURLs(
						canonicalURL, themeDisplay, layout);
				}

				PrintWriter printWriter = httpServletResponse.getWriter();

				for (LayoutSEOLink layoutSEOLink :
						_layoutSEOLinkManager.getLocalizedLayoutSEOLinks(
							layout, _portal.getLocale(httpServletRequest),
							canonicalURL, alternateURLs)) {

					printWriter.println(_addLinkTag(layoutSEOLink));
				}

				if (_layoutSEOLinkManager.isOpenGraphEnabled(layout)) {
					Group group = layout.getGroup();
					LayoutSEOLink layoutSEOLink =
						_layoutSEOLinkManager.getCanonicalLayoutSEOLink(
							layout, themeDisplay.getLocale(), canonicalURL,
							alternateURLs);

					printWriter.println(
						_getOpenGraphTag(
							"og:description",
							layout.getDescription(
								themeDisplay.getLanguageId())));
					printWriter.println(
						_getOpenGraphTag(
							"og:locale", themeDisplay.getLanguageId()));

					availableLocales.forEach(
						locale -> printWriter.println(
							_getOpenGraphTag(
								"og:locale:alternate",
								LocaleUtil.toLanguageId(locale))));

					printWriter.println(
						_getOpenGraphTag(
							"og:site_name", group.getDescriptiveName()));
					printWriter.println(
						_getOpenGraphTag(
							"og:title", _getTitle(httpServletRequest)));
					printWriter.println(
						_getOpenGraphTag("og:url", layoutSEOLink.getHref()));
				}
			}
		}
		catch (PortalException pe) {
			throw new IOException(pe);
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/top_head.jsp#pre");
	}

	private String _addLinkTag(LayoutSEOLink layoutSEOLink) {
		StringBuilder sb = new StringBuilder(10);

		sb.append("<link data-senna-track=\"temporary\" ");
		sb.append("href=\"");
		sb.append(layoutSEOLink.getHref());
		sb.append("\" ");

		if (Validator.isNotNull(layoutSEOLink.getHrefLang())) {
			sb.append("hreflang=\"");
			sb.append(layoutSEOLink.getHrefLang());
			sb.append("\" ");
		}

		sb.append("rel=\"");
		sb.append(layoutSEOLink.getRelationship());
		sb.append("\" />");

		return sb.toString();
	}

	private String _getOpenGraphTag(String property, String content) {
		if (Validator.isNull(content)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(5);

		sb.append("<meta property=\"");
		sb.append(property);
		sb.append("\" content=\"");
		sb.append(content);
		sb.append("\">");

		return sb.toString();
	}

	private String _getTitle(HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		String title = layout.getHTMLTitle(themeDisplay.getLanguageId());

		Group group = layout.getGroup();

		String portletId = (String)httpServletRequest.getAttribute(
			WebKeys.PORTLET_ID);

		if (Validator.isNotNull(portletId) && layout.isSystem() &&
			!layout.isTypeControlPanel() &&
			StringUtil.equals(layout.getFriendlyURL(), "/manage")) {

			title = _portal.getPortletTitle(
				portletId, themeDisplay.getLocale());
		}
		else if (Validator.isNotNull(themeDisplay.getTilesTitle())) {
			title = _language.get(
				themeDisplay.getLocale(), themeDisplay.getTilesTitle());
		}
		else {
			if (group.isLayoutPrototype()) {
				title = group.getDescriptiveName(themeDisplay.getLocale());
			}
			else {
				if (Validator.isNotNull(
						httpServletRequest.getAttribute(WebKeys.PAGE_TITLE))) {

					ListMergeable<String> titleListMergeable =
						(ListMergeable<String>)httpServletRequest.getAttribute(
							WebKeys.PAGE_TITLE);

					title = titleListMergeable.mergeToString(StringPool.SPACE);
				}

				if (Validator.isNotNull(
						httpServletRequest.getAttribute(
							WebKeys.PAGE_SUBTITLE))) {

					ListMergeable<String> titleListMergeable =
						(ListMergeable<String>)httpServletRequest.getAttribute(
							WebKeys.PAGE_SUBTITLE);

					title =
						titleListMergeable.mergeToString(StringPool.SPACE) +
							" - " + title;
				}
			}

			if (HtmlUtil.getHtml() != null) {
				title = HtmlUtil.escape(title);
			}
		}

		Company company = themeDisplay.getCompany();
		String siteName = HtmlUtil.escape(group.getDescriptiveName());

		if (Validator.isNotNull(title) &&
			!StringUtil.equals(company.getName(), siteName) &&
			!group.isLayoutPrototype()) {

			title = title + " - " + siteName;
		}

		return title + " - " + company.getName();
	}

	@Reference
	private Language _language;

	@Reference
	private LayoutSEOLinkManager _layoutSEOLinkManager;

	@Reference
	private Portal _portal;

}