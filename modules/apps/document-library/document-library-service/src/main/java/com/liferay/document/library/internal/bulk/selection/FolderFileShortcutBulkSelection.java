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
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.RepositoryProvider;
import com.liferay.portal.kernel.repository.capabilities.BulkOperationCapability;
import com.liferay.portal.kernel.repository.model.BaseRepositoryModelOperation;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Adolfo Pérez
 */
public class FolderFileShortcutBulkSelection
	extends BaseContainerEntryBulkSelection<FileShortcut> {

	public FolderFileShortcutBulkSelection(
		long repositoryId, long folderId, Map<String, String[]> parameterMap,
		ResourceBundleLoader resourceBundleLoader, Language language,
		RepositoryProvider repositoryProvider, DLAppService dlAppService) {

		super(folderId, parameterMap, resourceBundleLoader, language);

		_repositoryId = repositoryId;
		_folderId = folderId;
		_repositoryProvider = repositoryProvider;
		_dlAppService = dlAppService;
	}

	@Override
	public Class<? extends BulkSelectionFactory>
		getBulkSelectionFactoryClass() {

		return FileShortcutBulkSelectionFactory.class;
	}

	@Override
	public Stream<FileShortcut> stream() throws PortalException {
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
			new Spliterator<FileShortcut>() {

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
					Consumer<? super FileShortcut> action) {

					_exhausted = true;

					try {
						bulkOperationCapability.execute(
							bulkFilter,
							new BaseRepositoryModelOperation() {

								@Override
								public void execute(FileShortcut fileShortcut) {
									action.accept(fileShortcut);
								}

							});
					}
					catch (PortalException pe) {
						ReflectionUtil.throwException(pe);
					}
				}

				@Override
				public boolean tryAdvance(
					Consumer<? super FileShortcut> consumer) {

					if (_exhausted) {
						return false;
					}

					forEachRemaining(consumer);

					return true;
				}

				@Override
				public Spliterator<FileShortcut> trySplit() {
					return null;
				}

				private boolean _exhausted;

			},
			false);
	}

	@Override
	public BulkSelection<AssetEntry> toAssetEntryBulkSelection() {
		throw new UnsupportedOperationException("FileShortcut is not an asset");
	}

	@Override
	protected int getEntriesCount() throws PortalException {
		int fileEntriesAndFileShortcutsCount =
			_dlAppService.getFileEntriesAndFileShortcutsCount(
				_repositoryId, _folderId, WorkflowConstants.STATUS_APPROVED);
		int fileEntriesCount = _dlAppService.getFileEntriesCount(
			_repositoryId, _folderId);

		return fileEntriesAndFileShortcutsCount - fileEntriesCount;
	}

	private final DLAppService _dlAppService;
	private final long _folderId;
	private final long _repositoryId;
	private final RepositoryProvider _repositoryProvider;

}