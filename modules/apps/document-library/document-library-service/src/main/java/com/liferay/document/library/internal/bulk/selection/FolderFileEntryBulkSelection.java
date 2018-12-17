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
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.RepositoryProvider;
import com.liferay.portal.kernel.repository.capabilities.BulkOperationCapability;
import com.liferay.portal.kernel.repository.model.BaseRepositoryModelOperation;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.io.Serializable;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Adolfo Pérez
 */
public class FolderFileEntryBulkSelection
	implements BulkSelection
		<FileEntry, FileEntryBulkSelectionBackgroundActionExecutor> {

	public FolderFileEntryBulkSelection(
		long repositoryId, long folderId,
		ResourceBundleLoader resourceBundleLoader, Language language,
		RepositoryProvider repositoryProvider, DLAppService dlAppService) {

		_repositoryId = repositoryId;
		_folderId = folderId;
		_resourceBundleLoader = resourceBundleLoader;
		_language = language;
		_repositoryProvider = repositoryProvider;
		_dlAppService = dlAppService;
	}

	@Override
	public String describe(Locale locale) throws PortalException {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return _language.format(
			resourceBundle, "these-changes-will-be-applied-to-x-items",
			_dlAppService.getFileEntriesCount(_repositoryId, _folderId));
	}

	@Override
	public boolean isMultiple() {
		return true;
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
		return "all:" + _folderId;
	}

	@Override
	public Stream<FileEntry> stream() throws PortalException {
		DocumentRepository documentRepository =
			_repositoryProvider.getLocalRepository(_repositoryId);

		if (!documentRepository.isCapabilityProvided(
				BulkOperationCapability.class)) {

			return Stream.empty();
		}

		BulkOperationCapability bulkOperationCapability =
			documentRepository.getCapability(BulkOperationCapability.class);

		BulkOperationCapability.Filter<Long> bulkFilter =
			new BulkOperationCapability.Filter<>(
				BulkOperationCapability.Field.FolderId.class,
				BulkOperationCapability.Operator.EQ, _folderId);

		return StreamSupport.stream(
			new Spliterator<FileEntry>() {

				@Override
				public int characteristics() {
					return 0;
				}

				@Override
				public long estimateSize() {
					return Long.MAX_VALUE;
				}

				@Override
				public void forEachRemaining(
					Consumer<? super FileEntry> action) {

					_exhausted = true;

					try {
						bulkOperationCapability.execute(
							bulkFilter,
							new BaseRepositoryModelOperation() {

								@Override
								public void execute(FileEntry fileEntry) {
									action.accept(fileEntry);
								}

							});
					}
					catch (PortalException pe) {
						ReflectionUtil.throwException(pe);
					}
				}

				@Override
				public boolean tryAdvance(
					Consumer<? super FileEntry> consumer) {

					if (_exhausted) {
						return false;
					}

					forEachRemaining(consumer);

					return true;
				}

				@Override
				public Spliterator<FileEntry> trySplit() {
					return null;
				}

				private boolean _exhausted;

			},
			false);
	}

	private final DLAppService _dlAppService;
	private final long _folderId;
	private final Language _language;
	private final long _repositoryId;
	private final RepositoryProvider _repositoryProvider;
	private final ResourceBundleLoader _resourceBundleLoader;

}