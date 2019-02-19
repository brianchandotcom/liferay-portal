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
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Adolfo Pérez
 */
public class FolderFolderBulkSelection
	extends BaseContainerEntryBulkSelection<Folder> {

	public FolderFolderBulkSelection(
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

		return FolderBulkSelectionFactory.class;
	}

	@Override
	public Stream<Folder> stream() throws PortalException {
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
			new Spliterator<Folder>() {

				@Override
				public int characteristics() {
					return 0;
				}

				@Override
				public long estimateSize() {
					return Long.MAX_VALUE;
				}

				@Override
				public void forEachRemaining(Consumer<? super Folder> action) {
					_exhausted = true;

					try {
						bulkOperationCapability.execute(
							bulkFilter,
							new BaseRepositoryModelOperation() {

								@Override
								public void execute(Folder folder) {
									action.accept(folder);
								}

							});
					}
					catch (PortalException pe) {
						ReflectionUtil.throwException(pe);
					}
				}

				@Override
				public boolean tryAdvance(Consumer<? super Folder> consumer) {
					if (_exhausted) {
						return false;
					}

					forEachRemaining(consumer);

					return true;
				}

				@Override
				public Spliterator<Folder> trySplit() {
					return null;
				}

				private boolean _exhausted;

			},
			false);
	}

	@Override
	public BulkSelection<AssetEntry> toAssetEntryBulkSelection() {
		throw new UnsupportedOperationException("Folder is not an asset");
	}

	@Override
	protected int getEntriesCount() throws PortalException {
		return _dlAppService.getFoldersCount(_repositoryId, _folderId);
	}

	private final DLAppService _dlAppService;
	private final long _folderId;
	private final long _repositoryId;
	private final RepositoryProvider _repositoryProvider;

}