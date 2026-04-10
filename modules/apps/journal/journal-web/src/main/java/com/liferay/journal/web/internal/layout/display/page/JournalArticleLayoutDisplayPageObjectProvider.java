/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.layout.display.page;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.util.AssetHelper;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Locale;

/**
 * @author Jürgen Kappler
 */
public class JournalArticleLayoutDisplayPageObjectProvider
	implements LayoutDisplayPageObjectProvider<JournalArticle> {

	public JournalArticleLayoutDisplayPageObjectProvider(
			JournalArticle article, AssetHelper assetHelper)
		throws PortalException {

		_article = article;
		_assetHelper = assetHelper;

		_assetEntry = _getAssetEntry(article);
	}

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Override
	public long getClassNameId() {
		if (_assetEntry != null) {
			return _assetEntry.getClassNameId();
		}

		return PortalUtil.getClassNameId(JournalArticle.class.getName());
	}

	@Override
	public long getClassPK() {
		return _article.getResourcePrimKey();
	}

	@Override
	public long getClassTypeId() {
		if (_assetEntry != null) {
			return _assetEntry.getClassTypeId();
		}

		return _article.getDDMStructureId();
	}

	@Override
	public String getDescription(Locale locale) {
		if (_assetEntry != null) {
			return _assetEntry.getDescription(locale);
		}

		return _article.getDescription(locale);
	}

	@Override
	public JournalArticle getDisplayObject() {
		return _article;
	}

	@Override
	public String getExternalReferenceCode() {
		return _article.getExternalReferenceCode();
	}

	@Override
	public long getGroupId() {
		return _article.getGroupId();
	}

	@Override
	public String getKeywords(Locale locale) {
		if (_assetEntry != null) {
			return _assetHelper.getAssetKeywords(
				_assetEntry.getClassName(), _assetEntry.getClassPK(), locale);
		}

		return _assetHelper.getAssetKeywords(
			JournalArticle.class.getName(), _article.getResourcePrimKey(),
			locale);
	}

	@Override
	public String getTitle(Locale locale) {
		if (_assetEntry != null) {
			return _assetEntry.getTitle(locale);
		}

		return _article.getTitle(locale);
	}

	@Override
	public String getURLTitle(Locale locale) {
		if (_assetEntry != null) {
			AssetRenderer<?> assetRenderer = _assetEntry.getAssetRenderer();

			return assetRenderer.getUrlTitle(locale);
		}

		try {
			return _article.getUrlTitle(locale);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return _article.getUrlTitle();
	}

	private AssetEntry _getAssetEntry(JournalArticle journalArticle)
		throws PortalException {

		AssetRendererFactory<JournalArticle> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				JournalArticle.class.getName());

		long assetEntryClassPK = assetRendererFactory.getAssetEntryClassPK(
			journalArticle);

		if (assetEntryClassPK == 0) {
			return null;
		}

		return assetRendererFactory.getAssetEntry(
			JournalArticle.class.getName(), assetEntryClassPK);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleLayoutDisplayPageObjectProvider.class.getName());

	private final JournalArticle _article;
	private final AssetEntry _assetEntry;
	private final AssetHelper _assetHelper;

}