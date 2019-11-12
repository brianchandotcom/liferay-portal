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
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryModel;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.repository.model.RepositoryModel",
	service = {
		BulkSelectionFactory.class, RepositoryModelBulkSelectionFactory.class
	}
)
public class RepositoryModelBulkSelectionFactory
	implements BulkSelectionFactory<RepositoryModel> {

	public BulkSelection<RepositoryModel> create(
		Map<String, String[]> parameterMap) {

		return _combine(
			parameterMap, _fileEntryBulkSelectionFactory.create(parameterMap),
			_fileShortcutBulkSelectionFactory.create(parameterMap),
			_folderBulkSelectionFactory.create(parameterMap));
	}

	private BulkSelection<RepositoryModel> _combine(
		Map<String, String[]> parameterMap,
		BulkSelection<? extends RepositoryModel>... bulkSelections) {

		return new BulkSelection<RepositoryModel>() {

			@Override
			public <E extends PortalException> void forEach(
					UnsafeConsumer<RepositoryModel, E> unsafeConsumer)
				throws PortalException {

				for (BulkSelection bulkSelection : bulkSelections) {
					bulkSelection.forEach(unsafeConsumer);
				}
			}

			@Override
			public Class<? extends BulkSelectionFactory>
				getBulkSelectionFactoryClass() {

				return RepositoryModelBulkSelectionFactory.class;
			}

			@Override
			public Map<String, String[]> getParameterMap() {
				return parameterMap;
			}

			@Override
			public long getSize() throws PortalException {
				long size = 0;

				for (BulkSelection<? extends RepositoryModel> bulkSelection :
						bulkSelections) {

					size += bulkSelection.getSize();
				}

				return size;
			}

			@Override
			public Serializable serialize() {
				return null;
			}

			@Override
			public BulkSelection<AssetEntry> toAssetEntryBulkSelection() {
				List<BulkSelection> assetEntryBulkSelections =
					new ArrayList<>();

				for (BulkSelection bulkSelection : bulkSelections) {
					assetEntryBulkSelections.add(
						bulkSelection.toAssetEntryBulkSelection());
				}

				return _combine(
					parameterMap,
					assetEntryBulkSelections.toArray(new BulkSelection[0]));
			}

		};
	}

	@Reference(
		target = "(model.class.name=com.liferay.document.library.kernel.model.DLFileEntry)"
	)
	private BulkSelectionFactory<FileEntry> _fileEntryBulkSelectionFactory;

	@Reference(
		target = "(model.class.name=com.liferay.document.library.kernel.model.DLFileShortcut)"
	)
	private BulkSelectionFactory<FileShortcut>
		_fileShortcutBulkSelectionFactory;

	@Reference(
		target = "(model.class.name=com.liferay.document.library.kernel.model.DLFolder)"
	)
	private BulkSelectionFactory<Folder> _folderBulkSelectionFactory;

}