/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.client.dto.v1_0;

import com.liferay.exportimport.rest.client.function.UnsafeSupplier;
import com.liferay.exportimport.rest.client.serdes.v1_0.ImportUploadSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public class ImportUpload implements Cloneable, Serializable {

	public static ImportUpload toDTO(String json) {
		return ImportUploadSerDes.toDTO(json);
	}

	public Long getChunkSize() {
		return chunkSize;
	}

	public void setChunkSize(Long chunkSize) {
		this.chunkSize = chunkSize;
	}

	public void setChunkSize(
		UnsafeSupplier<Long, Exception> chunkSizeUnsafeSupplier) {

		try {
			chunkSize = chunkSizeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long chunkSize;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setErrorMessage(
		UnsafeSupplier<String, Exception> errorMessageUnsafeSupplier) {

		try {
			errorMessage = errorMessageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String errorMessage;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileName(
		UnsafeSupplier<String, Exception> fileNameUnsafeSupplier) {

		try {
			fileName = fileNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String fileName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<String, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String id;

	public ImportPreview getImportPreview() {
		return importPreview;
	}

	public void setImportPreview(ImportPreview importPreview) {
		this.importPreview = importPreview;
	}

	public void setImportPreview(
		UnsafeSupplier<ImportPreview, Exception> importPreviewUnsafeSupplier) {

		try {
			importPreview = importPreviewUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ImportPreview importPreview;

	public Integer getPercentage() {
		return percentage;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}

	public void setPercentage(
		UnsafeSupplier<Integer, Exception> percentageUnsafeSupplier) {

		try {
			percentage = percentageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer percentage;

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public void setSize(UnsafeSupplier<Long, Exception> sizeUnsafeSupplier) {
		try {
			size = sizeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long size;

	public Status getStatus() {
		return status;
	}

	public String getStatusAsString() {
		if (status == null) {
			return null;
		}

		return status.toString();
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setStatus(
		UnsafeSupplier<Status, Exception> statusUnsafeSupplier) {

		try {
			status = statusUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Status status;

	public Integer getTotalChunksCount() {
		return totalChunksCount;
	}

	public void setTotalChunksCount(Integer totalChunksCount) {
		this.totalChunksCount = totalChunksCount;
	}

	public void setTotalChunksCount(
		UnsafeSupplier<Integer, Exception> totalChunksCountUnsafeSupplier) {

		try {
			totalChunksCount = totalChunksCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer totalChunksCount;

	public Integer getUploadedChunksCount() {
		return uploadedChunksCount;
	}

	public void setUploadedChunksCount(Integer uploadedChunksCount) {
		this.uploadedChunksCount = uploadedChunksCount;
	}

	public void setUploadedChunksCount(
		UnsafeSupplier<Integer, Exception> uploadedChunksCountUnsafeSupplier) {

		try {
			uploadedChunksCount = uploadedChunksCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer uploadedChunksCount;

	@Override
	public ImportUpload clone() throws CloneNotSupportedException {
		return (ImportUpload)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ImportUpload)) {
			return false;
		}

		ImportUpload importUpload = (ImportUpload)object;

		return Objects.equals(toString(), importUpload.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ImportUploadSerDes.toJSON(this);
	}

	public static enum Status {

		UPLOADING("UPLOADING"), VALIDATING("VALIDATING"), READY("READY"),
		FAILED("FAILED");

		public static Status create(String value) {
			for (Status status : values()) {
				if (Objects.equals(status.getValue(), value) ||
					Objects.equals(status.name(), value)) {

					return status;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private Status(String value) {
			_value = value;
		}

		private final String _value;

	}

}
// LIFERAY-REST-BUILDER-HASH:1950156033