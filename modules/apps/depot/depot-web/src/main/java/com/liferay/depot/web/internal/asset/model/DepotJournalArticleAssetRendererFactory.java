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

package com.liferay.depot.web.internal.asset.model;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.repository.model.FileEntry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"service.ranking:Integer=100"
	},
	service = AssetRendererFactory.class
)
public class DepotJournalArticleAssetRendererFactory
	extends AssetRendererFactoryWrapper<FileEntry> {

	@Override
	protected AssetRendererFactory<FileEntry> getAssetRendererFactory() {
		return _assetRendererFactory;
	}

	@Reference(
		target = "(&(javax.portlet.name=" + JournalPortletKeys.JOURNAL + ")(!(component.name=com.liferay.depot.web.internal.asset.model.DepotJournalArticleAssetRendererFactory)))"
	)
	private AssetRendererFactory<FileEntry> _assetRendererFactory;

}