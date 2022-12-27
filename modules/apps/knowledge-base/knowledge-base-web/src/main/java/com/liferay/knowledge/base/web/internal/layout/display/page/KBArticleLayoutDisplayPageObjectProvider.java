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
import com.liferay.portal.kernel.util.PortalUtil;

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
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(
					PortalUtil.getClassNameId(KBArticle.class));

		return assetRendererFactory.getAssetEntry(
			KBArticle.class.getName(), kbArticle.getClassPK());
	}

	private final AssetEntry _assetEntry;
	private final AssetHelper _assetHelper;
	private final KBArticle _kbArticle;

}