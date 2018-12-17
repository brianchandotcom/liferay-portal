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

import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author Adolfo Pérez
 */
public class MultipleFileEntryBulkSelection extends BaseBulkSelection {

	public MultipleFileEntryBulkSelection(
		long[] fileEntryIds, Map<String, String[]> parameterMap,
		ResourceBundleLoader resourceBundleLoader, Language language,
		DLAppService dlAppService,
		BackgroundTaskManager backgroundTaskManager) {

		super(parameterMap, backgroundTaskManager);

		_fileEntryIds = fileEntryIds;
		_resourceBundleLoader = resourceBundleLoader;
		_language = language;
		_dlAppService = dlAppService;
	}

	@Override
	public String describe(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return _language.format(
			resourceBundle, "these-changes-will-be-applied-to-x-items",
			_fileEntryIds.length);
	}

	@Override
	public boolean isMultiple() {
		return true;
	}

	@Override
	public Serializable serialize() {
		return StringUtil.merge(_fileEntryIds, StringPool.COMMA);
	}

	@Override
	public Stream<FileEntry> stream() {
		LongStream longStream = Arrays.stream(_fileEntryIds);

		return longStream.mapToObj(
			this::_fetchFileEntry
		).filter(
			Objects::nonNull
		);
	}

	@Override
	protected String getBackgroundJobName() {
		return "multipleFileEntryBulkSelection-" +
			PrincipalThreadLocal.getUserId();
	}

	private FileEntry _fetchFileEntry(long fileEntryId) {
		try {
			return _dlAppService.getFileEntry(fileEntryId);
		}
		catch (NoSuchFileEntryException nsfee) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsfee, nsfee);
			}

			return null;
		}
		catch (PortalException pe) {
			return ReflectionUtil.throwException(pe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MultipleFileEntryBulkSelection.class);

	private final DLAppService _dlAppService;
	private final long[] _fileEntryIds;
	private final Language _language;
	private final ResourceBundleLoader _resourceBundleLoader;

}