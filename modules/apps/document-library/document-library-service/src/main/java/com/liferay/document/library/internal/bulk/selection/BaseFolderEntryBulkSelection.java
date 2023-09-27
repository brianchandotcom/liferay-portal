/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.bulk.selection;

import com.liferay.bulk.selection.BaseContainerEntryBulkSelection;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.RepositoryProvider;
import com.liferay.portal.kernel.repository.capabilities.BulkOperationCapability;
import com.liferay.portal.kernel.repository.model.RepositoryModel;
import com.liferay.portal.kernel.repository.model.RepositoryModelOperation;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseFolderEntryBulkSelection<T extends RepositoryModel<T>>
	extends BaseContainerEntryBulkSelection<T> {

	public BaseFolderEntryBulkSelection(
		long repositoryId, long folderId, Map<String, String[]> parameterMap,
		RepositoryProvider repositoryProvider) {

		super(folderId, parameterMap);

		_repositoryId = repositoryId;
		_folderId = folderId;
		_repositoryProvider = repositoryProvider;
	}

	@Override
	public <E extends PortalException> void forEach(
			UnsafeConsumer<T, E> unsafeConsumer)
		throws PortalException {

		DocumentRepository documentRepository =
			_repositoryProvider.getLocalRepository(_repositoryId);

		if (!documentRepository.isCapabilityProvided(
				BulkOperationCapability.class)) {

			return;
		}

		BulkOperationCapability bulkOperationCapability =
			documentRepository.getCapability(BulkOperationCapability.class);

		BulkOperationCapability.Filter<Long> bulkFilter =
			new BulkOperationCapability.Filter<>(
				BulkOperationCapability.Field.FolderId.class,
				BulkOperationCapability.Operator.EQ, _folderId);

		bulkOperationCapability.execute(
			bulkFilter, getRepositoryModelOperation(unsafeConsumer));
	}

	protected abstract <E extends PortalException> RepositoryModelOperation
		getRepositoryModelOperation(UnsafeConsumer<? super T, E> action);

	private final long _folderId;
	private final long _repositoryId;
	private final RepositoryProvider _repositoryProvider;

}