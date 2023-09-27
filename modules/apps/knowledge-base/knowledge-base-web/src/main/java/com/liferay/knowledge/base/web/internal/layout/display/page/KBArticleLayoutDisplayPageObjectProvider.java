/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.layout.display.page;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.util.AssetHelper;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBFolderLocalServiceUtil;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

/**
 * @author Adolfo Pérez
 */
public class KBArticleLayoutDisplayPageObjectProvider
	implements LayoutDisplayPageObjectProvider<KBArticle> {

	public KBArticleLayoutDisplayPageObjectProvider(
			KBArticle kbArticle, AssetHelper assetHelper)
		throws PortalException {

		_kbArticle = kbArticle;
		_assetHelper = assetHelper;

		_assetEntry = _getAssetEntry(kbArticle);
	}

	@Override
	public String getClassName() {
		return KBArticle.class.getName();
	}

	@Override
	public long getClassNameId() {
		return _kbArticle.getClassNameId();
	}

	@Override
	public long getClassPK() {
		return _kbArticle.getKbArticleId();
	}

	@Override
	public long getClassTypeId() {
		return 0;
	}

	@Override
	public String getDescription(Locale locale) {
		return _kbArticle.getDescription();
	}

	@Override
	public KBArticle getDisplayObject() {
		return _kbArticle;
	}

	@Override
	public long getGroupId() {
		return _kbArticle.getGroupId();
	}

	@Override
	public String getKeywords(Locale locale) {
		return _assetHelper.getAssetKeywords(
			_assetEntry.getClassName(), _assetEntry.getClassPK(), locale);
	}

	@Override
	public String getTitle(Locale locale) {
		return _kbArticle.getTitle();
	}

	@Override
	public String getURLTitle(Locale locale) {
		try {
			if (_kbArticle.getKbFolderId() ==
					KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				return _kbArticle.getUrlTitle();
			}

			KBFolder kbFolder = KBFolderLocalServiceUtil.getKBFolder(
				_kbArticle.getKbFolderId());

			return String.format(
				"%s/%s", kbFolder.getUrlTitle(), _kbArticle.getUrlTitle());
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	private AssetEntry _getAssetEntry(KBArticle kbArticle)
		throws PortalException {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
				KBArticle.class);

		return assetRendererFactory.getAssetEntry(
			KBArticle.class.getName(), kbArticle.getClassPK());
	}

	private final AssetEntry _assetEntry;
	private final AssetHelper _assetHelper;
	private final KBArticle _kbArticle;

}