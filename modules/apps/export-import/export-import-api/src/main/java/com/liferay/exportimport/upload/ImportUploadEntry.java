/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.upload;

/**
 * @author Jaime León
 */
public class ImportUploadEntry {

	public ImportUploadEntry(
		long chunkSize, String contentType, String fileName,
		String importUploadId, long plid, String portletId, long size,
		int uploadedChunksCount) {

		_chunkSize = chunkSize;
		_contentType = contentType;
		_fileName = fileName;
		_importUploadId = importUploadId;
		_plid = plid;
		_portletId = portletId;
		_size = size;
		_uploadedChunksCount = uploadedChunksCount;
	}

	public long getChunkSize() {
		return _chunkSize;
	}

	public String getContentType() {
		return _contentType;
	}

	public String getFileName() {
		return _fileName;
	}

	public String getImportUploadId() {
		return _importUploadId;
	}

	public long getPlid() {
		return _plid;
	}

	public String getPortletId() {
		return _portletId;
	}

	public long getSize() {
		return _size;
	}

	public int getTotalChunksCount() {
		return (int)((_size + _chunkSize - 1) / _chunkSize);
	}

	public int getUploadedChunksCount() {
		return _uploadedChunksCount;
	}

	public boolean isComplete() {
		if (_uploadedChunksCount == getTotalChunksCount()) {
			return true;
		}

		return false;
	}

	private final long _chunkSize;
	private final String _contentType;
	private final String _fileName;
	private final String _importUploadId;
	private final long _plid;
	private final String _portletId;
	private final long _size;
	private final int _uploadedChunksCount;

}