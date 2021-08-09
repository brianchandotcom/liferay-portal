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

package com.liferay.translation.web.internal.asset.model;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.translation.constants.TranslationPortletKeys;
import com.liferay.translation.info.field.TranslationInfoFieldChecker;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.service.TranslationEntryLocalService;
import com.liferay.translation.snapshot.TranslationSnapshotProvider;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "javax.portlet.name=" + TranslationPortletKeys.TRANSLATION,
	service = AssetRendererFactory.class
)
public class TranslationEntryAssetRendererFactory
	extends BaseAssetRendererFactory<TranslationEntry> {

	public TranslationEntryAssetRendererFactory() {
		setClassName(TranslationEntry.class.getName());
		setPortletId(TranslationPortletKeys.TRANSLATION);
	}

	@Override
	public AssetRenderer<TranslationEntry> getAssetRenderer(
			long classPK, int type)
		throws PortalException {

		TranslationEntry translationEntry =
			_translationEntryLocalService.fetchTranslationEntry(classPK);

		if (translationEntry != null) {
			return new TranslationEntryAssetRenderer(
				_infoItemServiceTracker, _servletContext, translationEntry,
				_translationInfoFieldChecker, _translationSnapshotProvider);
		}

		return null;
	}

	@Override
	public String getType() {
		return "translation";
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.translation.web)")
	private ServletContext _servletContext;

	@Reference
	private TranslationEntryLocalService _translationEntryLocalService;

	@Reference
	private TranslationInfoFieldChecker _translationInfoFieldChecker;

	@Reference
	private TranslationSnapshotProvider _translationSnapshotProvider;

}