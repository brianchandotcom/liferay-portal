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

package com.liferay.layout.seo.web.internal.util;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public abstract class FriendlyURLMapper {

	public static FriendlyURLMapper create(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		Layout layout = (Layout)httpServletRequest.getAttribute(WebKeys.LAYOUT);

		if ((layout != null) && !layout.isContentDisplayPage()) {
			return new DefaultFriendlyURLMapper();
		}

		AssetEntry assetEntry = (AssetEntry)httpServletRequest.getAttribute(
			WebKeys.LAYOUT_ASSET_ENTRY);

		if (Objects.equals(
				assetEntry.getClassName(), JournalArticle.class.getName())) {

			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassNameId(
						assetEntry.getClassNameId());

			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(assetEntry.getClassPK());

			return new AssetDisplayPageFriendlyURLMapper(
				(JournalArticle)assetRenderer.getAssetObject());
		}

		return new DefaultFriendlyURLMapper();
	}

	public abstract String getMappedAssetDisplayPageFriendlyURL(
			String url, Locale locale)
		throws PortalException;

	public abstract Map<Locale, String> getMappedAssetDisplayPageFriendlyURLs(
			Map<Locale, String> friendlyURLs)
		throws PortalException;

	public static class AssetDisplayPageFriendlyURLMapper
		extends FriendlyURLMapper {

		@Override
		public String getMappedAssetDisplayPageFriendlyURL(
				String url, Locale locale)
			throws PortalException {

			int beginPos = url.indexOf(
				JournalArticleConstants.CANONICAL_URL_SEPARATOR);

			if (beginPos == -1) {
				return url;
			}

			int endPos = StringUtil.indexOfAny(
				url, new char[] {CharPool.FORWARD_SLASH, CharPool.QUESTION},
				beginPos +
					JournalArticleConstants.CANONICAL_URL_SEPARATOR.length());

			if (endPos == -1) {
				endPos = url.length();
			}

			String urlTitle = _journalArticle.getUrlTitle(locale);

			if (Validator.isNull(urlTitle)) {
				return url;
			}

			return new StringBuilder(
				url
			).replace(
				beginPos, endPos,
				JournalArticleConstants.CANONICAL_URL_SEPARATOR + urlTitle
			).toString();
		}

		public Map<Locale, String> getMappedAssetDisplayPageFriendlyURLs(
				Map<Locale, String> friendlyURLs)
			throws PortalException {

			Map<Locale, String> mappedFriendlyURLs = new HashMap<>();

			for (Map.Entry<Locale, String> entry : friendlyURLs.entrySet()) {
				mappedFriendlyURLs.put(
					entry.getKey(),
					getMappedAssetDisplayPageFriendlyURL(
						entry.getValue(), entry.getKey()));
			}

			return mappedFriendlyURLs;
		}

		private AssetDisplayPageFriendlyURLMapper(
			JournalArticle journalArticle) {

			_journalArticle = journalArticle;
		}

		private final JournalArticle _journalArticle;

	}

	public static class DefaultFriendlyURLMapper extends FriendlyURLMapper {

		@Override
		public String getMappedAssetDisplayPageFriendlyURL(
				String url, Locale locale)
			throws PortalException {

			return url;
		}

		@Override
		public Map<Locale, String> getMappedAssetDisplayPageFriendlyURLs(
				Map<Locale, String> friendlyURLs)
			throws PortalException {

			return friendlyURLs;
		}

	}

}