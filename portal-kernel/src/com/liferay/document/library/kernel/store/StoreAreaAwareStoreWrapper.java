/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		Store store = _storeSupplier.get();

		if (_isStoreAreaSupported()) {
			StoreArea.withStoreArea(
				StoreArea.NEW,
				() -> store.addFile(
					companyId, repositoryId, fileName, versionLabel,
					inputStream));
		}
		else {
			store.addFile(
				companyId, repositoryId, fileName, versionLabel, inputStream);
		}
	}

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		Store store = _storeSupplier.get();

		if (_isStoreAreaSupported()) {
			StoreAreaProcessor storeAreaProcessor =
				_storeAreaProcessorSupplier.get();

			if (storeAreaProcessor.copyDirectory(
					companyId, repositoryId, dirName, _SOURCE_STORE_AREAS,
					StoreArea.DELETED)) {

				StoreArea.runWithStoreAreas(
					() -> store.deleteDirectory(
						companyId, repositoryId, dirName),
					StoreArea.LIVE, StoreArea.NEW);
			}
		}
		else {
			store.deleteDirectory(companyId, repositoryId, dirName);
		}
	}

	@Override
	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		Store store = _storeSupplier.get();

		if (_isStoreAreaSupported()) {
			StoreAreaProcessor storeAreaProcessor =
				_storeAreaProcessorSupplier.get();

			StoreArea storeArea = StoreArea.tryRunWithStoreAreas(
				sourceStoreArea -> storeAreaProcessor.copy(
					sourceStoreArea.getPath(
						companyId, repositoryId, fileName, versionLabel),
					StoreArea.DELETED.getPath(
						companyId, repositoryId, fileName, versionLabel)),
				StoreArea.LIVE, StoreArea.NEW);

			if (storeArea != null) {
				StoreArea.withStoreArea(
					storeArea,
					() -> store.deleteFile(
						companyId, repositoryId, fileName, versionLabel));
			}
		}
		else {
			store.deleteFile(companyId, repositoryId, fileName, versionLabel);
		}
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		Store store = _storeSupplier.get();

		if (_isStoreAreaSupported()) {
			return StoreArea.tryGetWithStoreAreas(
				() -> store.getFileAsStream(
					companyId, repositoryId, fileName, versionLabel),
				Objects::nonNull, null, StoreArea.LIVE, StoreArea.NEW,
				StoreArea.DELETED);
		}

		return store.getFileAsStream(
			companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		return _getFileNames(
			companyId, repositoryId, dirName, StoreArea.LIVE, StoreArea.NEW,
			StoreArea.DELETED);
	}

	@Override
	public long getFileSize(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		Store store = _storeSupplier.get();

		if (_isStoreAreaSupported()) {
			return StoreArea.tryGetWithStoreAreas(
				() -> store.getFileSize(
					companyId, repositoryId, fileName, versionLabel),
				Objects::nonNull, 0L, StoreArea.LIVE, StoreArea.NEW,
				StoreArea.DELETED);
		}

		return store.getFileSize(
			companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public String[] getFileVersions(
		long companyId, long repositoryId, String fileName) {

		return _getFileVersions(
			companyId, repositoryId, fileName, StoreArea.LIVE, StoreArea.NEW,
			StoreArea.DELETED);
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		Store store = _storeSupplier.get();

		if (_isStoreAreaSupported()) {
			return StoreArea.tryGetWithStoreAreas(
				() -> store.hasFile(
					companyId, repositoryId, fileName, versionLabel),
				Boolean.TRUE::equals, false, StoreArea.LIVE, StoreArea.NEW);
		}

		return store.hasFile(companyId, repositoryId, fileName, versionLabel);
	}

	private String[] _getFileNames(
		long companyId, long repositoryId, String dirName,
		StoreArea... storeAreas) {

		Store store = _storeSupplier.get();

		if (_isStoreAreaSupported()) {
			String[] fileNames = StoreArea.mergeWithStoreAreas(
				() -> store.getFileNames(companyId, repositoryId, dirName),
				storeAreas);

			Arrays.sort(fileNames);

			return fileNames;
		}

		return store.getFileNames(companyId, repositoryId, dirName);
	}

	private String[] _getFileVersions(
		long companyId, long repositoryId, String fileName,
		StoreArea... storeAreas) {

		Store store = _storeSupplier.get();

		if (_isStoreAreaSupported()) {
			String[] fileVersions = StoreArea.mergeWithStoreAreas(
				() -> store.getFileVersions(companyId, repositoryId, fileName),
				storeAreas);

			Arrays.sort(fileVersions, DLUtil::compareVersions);

			return fileVersions;
		}

		return store.getFileVersions(companyId, repositoryId, fileName);
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

	private static final StoreArea[] _SOURCE_STORE_AREAS = {
		StoreArea.LIVE, StoreArea.NEW
	};

	private final Supplier<StoreAreaProcessor> _storeAreaProcessorSupplier;
	private final Supplier<Store> _storeSupplier;

}