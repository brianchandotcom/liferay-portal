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

import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionBackgroundActionExecutorConsumer;
import com.liferay.document.library.bulk.selection.FileEntryBulkSelectionBackgroundActionExecutor;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.io.Serializable;

import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Adolfo Pérez
 */
public class SingleFileEntryBulkSelection
	implements BulkSelection
		<FileEntry, FileEntryBulkSelectionBackgroundActionExecutor> {

	public SingleFileEntryBulkSelection(
		long fileEntryId, ResourceBundleLoader resourceBundleLoader,
		Language language, DLAppService dlAppService) {

		_fileEntryId = fileEntryId;
		_resourceBundleLoader = resourceBundleLoader;
		_language = language;
		_dlAppService = dlAppService;
	}

	@Override
	public String describe(Locale locale) throws PortalException {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		FileEntry fileEntry = _dlAppService.getFileEntry(_fileEntryId);

		return _language.format(
			resourceBundle, "these-changes-will-be-applied-to-x",
			fileEntry.getTitle());
	}

	@Override
	public boolean isMultiple() {
		return false;
	}

	@Override
	public
		<U extends BulkSelectionBackgroundActionExecutorConsumer
			<FileEntryBulkSelectionBackgroundActionExecutor>>
				void runBackgroundAction(U consumer) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Serializable serialize() {
		return String.valueOf(_fileEntryId);
	}

	@Override
	public Stream<FileEntry> stream() throws PortalException {
		Set<FileEntry> set = Collections.singleton(
			_dlAppService.getFileEntry(_fileEntryId));

		return set.stream();
	}

	private final DLAppService _dlAppService;
	private final long _fileEntryId;
	private final Language _language;
	private final ResourceBundleLoader _resourceBundleLoader;

}