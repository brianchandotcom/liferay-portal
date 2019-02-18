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

package com.liferay.document.library.internal.bulk.selection;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.io.Serializable;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Adolfo Pérez
 */
public class SingleFolderBulkSelection implements BulkSelection<Folder> {

	public SingleFolderBulkSelection(
		long folderId, Map<String, String[]> parameterMap,
		ResourceBundleLoader resourceBundleLoader, Language language,
		DLAppService dlAppService) {

		_folderId = folderId;
		_parameterMap = parameterMap;
		_resourceBundleLoader = resourceBundleLoader;
		_language = language;
		_dlAppService = dlAppService;
	}

	@Override
	public String describe(Locale locale) throws PortalException {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		Folder folder = _dlAppService.getFolder(_folderId);

		return _language.format(
			resourceBundle, "these-changes-will-be-applied-to-x",
			folder.getName());
	}

	@Override
	public Class<? extends BulkSelectionFactory>
		getBulkSelectionFactoryClass() {

		return FolderBulkSelectionFactory.class;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return _parameterMap;
	}

	@Override
	public boolean isMultiple() {
		return false;
	}

	@Override
	public Serializable serialize() {
		return String.valueOf(_folderId);
	}

	@Override
	public Stream<Folder> stream() throws PortalException {
		Set<Folder> set = Collections.singleton(
			_dlAppService.getFolder(_folderId));

		return set.stream();
	}

	@Override
	public BulkSelection<AssetEntry> toAssetEntryBulkSelection() {
		throw new UnsupportedOperationException("Folder is not an asset");
	}

	private final DLAppService _dlAppService;
	private final long _folderId;
	private final Language _language;
	private final Map<String, String[]> _parameterMap;
	private final ResourceBundleLoader _resourceBundleLoader;

}