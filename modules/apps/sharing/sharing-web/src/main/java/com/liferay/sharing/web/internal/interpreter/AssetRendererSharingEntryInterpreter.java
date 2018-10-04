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

package com.liferay.sharing.web.internal.interpreter;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.web.internal.renderer.AssetRendererSharingEntryEditRenderer;
import com.liferay.sharing.web.internal.renderer.AssetRendererSharingEntryViewRenderer;
import com.liferay.sharing.web.interpreter.SharingEntryInterpreter;
import com.liferay.sharing.web.renderer.SharingEntryEditRenderer;
import com.liferay.sharing.web.renderer.SharingEntryViewRenderer;

import java.util.Locale;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true, service = AssetRendererSharingEntryInterpreter.class
)
public class AssetRendererSharingEntryInterpreter
	implements SharingEntryInterpreter<AssetRenderer> {

	@Override
	public String getAssetTypeTitle(SharingEntry sharingEntry, Locale locale) {
		AssetRenderer assetRenderer = getEntry(sharingEntry);

		AssetRendererFactory assetRendererFactory =
			assetRenderer.getAssetRendererFactory();

		return assetRendererFactory.getTypeName(locale);
	}

	@Override
	public AssetRenderer getEntry(SharingEntry sharingEntry) {
		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				sharingEntry.getClassName());

		if (assetRendererFactory == null) {
			return null;
		}

		AssetRenderer<?> assetRenderer = null;

		try {
			assetRenderer = assetRendererFactory.getAssetRenderer(
				sharingEntry.getClassPK());
		}
		catch (Exception e) {
		}

		return assetRenderer;
	}

	@Override
	public SharingEntryEditRenderer<AssetRenderer>
		getSharingEntryEditRenderer() {

		return _assetRendererSharingEntryEditRenderer;
	}

	@Override
	public SharingEntryViewRenderer<AssetRenderer>
		getSharingEntryViewRenderer() {

		return _assetRendererSharingEntryViewRenderer;
	}

	@Override
	public String getTitle(SharingEntry sharingEntry) {
		try {
			AssetRenderer assetRenderer = getEntry(sharingEntry);

			AssetRendererFactory assetRendererFactory =
				assetRenderer.getAssetRendererFactory();

			AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
				assetRendererFactory.getClassName(),
				assetRenderer.getClassPK());

			return assetEntry.getTitle();
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		return StringPool.BLANK;
	}

	@Activate
	protected void activate() {
		_assetRendererSharingEntryEditRenderer =
			new AssetRendererSharingEntryEditRenderer();
		_assetRendererSharingEntryViewRenderer =
			new AssetRendererSharingEntryViewRenderer(_servletContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetRendererSharingEntryInterpreter.class);

	private AssetRendererSharingEntryEditRenderer
		_assetRendererSharingEntryEditRenderer;
	private AssetRendererSharingEntryViewRenderer
		_assetRendererSharingEntryViewRenderer;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.sharing.web)")
	private ServletContext _servletContext;

}