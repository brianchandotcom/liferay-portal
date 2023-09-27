/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.servlet.taglib.VerticalCard;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Objects;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalArticleItemSelectorVerticalCard implements VerticalCard {

	public JournalArticleItemSelectorVerticalCard(
		JournalArticle article, RenderRequest renderRequest,
		boolean selectable) {

		_article = article;
		_selectable = selectable;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getCssClass() {
		return "card-interactive card-interactive-secondary";
	}

	@Override
	public String getIcon() {
		return "web-content";
	}

	@Override
	public String getImageSrc() {
		return HtmlUtil.escape(_article.getArticleImageURL(_themeDisplay));
	}

	@Override
	public String getSubtitle() {
		try {
			if (!Objects.equals(
					ParamUtil.getString(_httpServletRequest, "scope"),
					"everywhere")) {

				return StringPool.BLANK;
			}

			Group group = GroupServiceUtil.getGroup(_article.getGroupId());

			return group.getDescriptiveName(_themeDisplay.getLocale());
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	@Override
	public String getTitle() {
		String title = _article.getTitle(_themeDisplay.getLocale());

		if (Validator.isNotNull(title)) {
			return title;
		}

		Locale defaultLanguage = LocaleUtil.fromLanguageId(
			_article.getDefaultLanguageId());

		return _article.getTitle(defaultLanguage);
	}

	@Override
	public boolean isSelectable() {
		return _selectable;
	}

	private final JournalArticle _article;
	private final HttpServletRequest _httpServletRequest;
	private final boolean _selectable;
	private final ThemeDisplay _themeDisplay;

}