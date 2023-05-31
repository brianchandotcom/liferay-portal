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

package com.liferay.document.library.kernel.store;

import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;

import java.io.InputStream;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Adolfo Pérez
 */
public class StoreAreaAwareStoreWrapper implements Store {

	public StoreAreaAwareStoreWrapper(
		Supplier<Store> storeSupplier,
		Supplier<StoreAreaProcessor> storeAreaProcessorSupplier) {

		_storeSupplier = storeSupplier;
		_storeAreaProcessorSupplier = storeAreaProcessorSupplier;
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream inputStream)
		throws PortalException {

		StoreArea.withStoreArea(
			StoreArea.NEW,
			() -> {
				Store store = _storeSupplier.get();

				store.addFile(
					companyId, repositoryId, fileName, versionLabel,
					inputStream);
			});
	}

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		Store store = _storeSupplier.get();

		if (_isStoreAreaSupported()) {
			StoreAreaProcessor storeAreaProcessor =
				_storeAreaProcessorSupplier.get();

			for (String fileName :
					store.getFileNames(companyId, repositoryId, dirName)) {

				for (String versionLabel :
						store.getFileVersions(
							companyId, repositoryId, fileName)) {

					StoreArea.tryRunWithStoreAreas(
						sourceStoreArea -> storeAreaProcessor.copy(
							sourceStoreArea.getPath(
								companyId, repositoryId, fileName,
								versionLabel),
							StoreArea.DELETED.getPath(
								companyId, repositoryId, fileName,
								versionLabel)),
						StoreArea.LIVE, StoreArea.NEW);
				}
			}
		}

		StoreArea.runWithStoreAreas(
			() -> store.deleteDirectory(companyId, repositoryId, dirName),
			StoreArea.LIVE, StoreArea.NEW);
	}

	@Override
	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		StoreArea storeArea = StoreArea.LIVE;

		if (_isStoreAreaSupported()) {
			StoreAreaProcessor storeAreaProcessor =
				_storeAreaProcessorSupplier.get();

			storeArea = StoreArea.tryRunWithStoreAreas(
				sourceStoreArea -> storeAreaProcessor.copy(
					sourceStoreArea.getPath(
						companyId, repositoryId, fileName, versionLabel),
					StoreArea.DELETED.getPath(
						companyId, repositoryId, fileName, versionLabel)),
				StoreArea.LIVE, StoreArea.NEW);
		}

		StoreArea.withStoreArea(
			storeArea,
			() -> {
				Store store = _storeSupplier.get();

				store.deleteFile(
					companyId, repositoryId, fileName, versionLabel);
			});
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		Store store = _storeSupplier.get();

		return StoreArea.tryGetWithStoreAreas(
			() -> store.getFileAsStream(
				companyId, repositoryId, fileName, versionLabel),
			Objects::nonNull, null, StoreArea.LIVE, StoreArea.NEW);
	}

	@Override
	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		Store store = _storeSupplier.get();

		String[] fileNames = StoreArea.mergeWithStoreAreas(
			() -> store.getFileNames(companyId, repositoryId, dirName),
			StoreArea.LIVE, StoreArea.NEW);

		Arrays.sort(fileNames);

		return fileNames;
	}

	@Override
	public long getFileSize(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		Store store = _storeSupplier.get();

		return StoreArea.tryGetWithStoreAreas(
			() -> store.getFileSize(
				companyId, repositoryId, fileName, versionLabel),
			Objects::nonNull, 0L, StoreArea.LIVE, StoreArea.NEW);
	}

	@Override
	public String[] getFileVersions(
		long companyId, long repositoryId, String fileName) {

		Store store = _storeSupplier.get();

		String[] fileVersions = StoreArea.mergeWithStoreAreas(
			() -> store.getFileVersions(companyId, repositoryId, fileName),
			StoreArea.LIVE, StoreArea.NEW);

		Arrays.sort(fileVersions, DLUtil::compareVersions);

		return fileVersions;
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		Store store = _storeSupplier.get();

		return StoreArea.tryGetWithStoreAreas(
			() -> store.hasFile(
				companyId, repositoryId, fileName, versionLabel),
			Boolean.TRUE::equals, false, StoreArea.LIVE, StoreArea.NEW);
	}

	private boolean _isStoreAreaSupported() {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-174816")) {
			return false;
		}

		StoreAreaProcessor storeAreaProcessor =
			_storeAreaProcessorSupplier.get();

		if (storeAreaProcessor != null) {
			return true;
		}

		return false;
	}

	private final Supplier<StoreAreaProcessor> _storeAreaProcessorSupplier;
	private final Supplier<Store> _storeSupplier;

}