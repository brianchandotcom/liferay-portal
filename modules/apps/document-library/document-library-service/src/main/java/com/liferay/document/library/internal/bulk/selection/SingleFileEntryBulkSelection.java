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

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
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
public class SingleFileEntryBulkSelection extends BaseBulkSelection {

	public SingleFileEntryBulkSelection(
		long fileEntryId, Map<String, String[]> parameterMap,
		ResourceBundleLoader resourceBundleLoader, Language language,
		DLAppService dlAppService,
		BackgroundTaskManager backgroundTaskManager) {

		super(parameterMap, backgroundTaskManager);

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
	public Serializable serialize() {
		return String.valueOf(_fileEntryId);
	}

	@Override
	public Stream<FileEntry> stream() throws PortalException {
		Set<FileEntry> set = Collections.singleton(
			_dlAppService.getFileEntry(_fileEntryId));

		return set.stream();
	}

	@Override
	protected String getBackgroundJobName() {
		return StringBundler.concat(
			"singleFileEntryBulkSelection-", PrincipalThreadLocal.getUserId(),
			StringPool.DASH, _fileEntryId);
	}

	private final DLAppService _dlAppService;
	private final long _fileEntryId;
	private final Language _language;
	private final ResourceBundleLoader _resourceBundleLoader;

}