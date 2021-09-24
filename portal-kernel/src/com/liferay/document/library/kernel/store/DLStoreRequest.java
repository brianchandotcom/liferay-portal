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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Raymond Augé
 */
public final class DLStoreRequest implements Serializable {

	/**
	 * The details about a file being added or updated in the {@link DLStore}.
	 *
	 * @param companyId the primary key of the company
	 * @param repositoryId the primary key of the data repository (optionally
	 *        {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param fileName the file name
	 * @return a builder for {@link DLStoreRequest}
	 * @throws IllegalArgumentException if either companyId or repositoryId
	 *                                  are negative
	 */
	public static Builder builder(
		long companyId, long repositoryId, String fileName) {

		if (companyId < 0) {
			throw new IllegalArgumentException("companyId must be positive");
		}

		if (repositoryId < 0) {
			throw new IllegalArgumentException("repositoryId must be positive");
		}

		Objects.requireNonNull(fileName);

		return new Builder(companyId, repositoryId, fileName);
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public String getEntryURL() {
		return _entryURL;
	}

	public String getFileExtension() {
		return _fileExtension;
	}

	public String getFileName() {
		return _fileName;
	}

	public long getNewRepositoryId() {
		return _newRepositoryId;
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	public long getSize() {
		return _size;
	}

	public String getSourceFileName() {
		return _sourceFileName;
	}

	public String getVersionLabel() {
		return _versionLabel;
	}

	public boolean isValidateFileExtension() {
		return _validateFileExtension;
	}

	public static class Builder {

		public DLStoreRequest build() {
			if (_sourceFileName == null) {
				_sourceFileName = _fileName;
			}

			if (_fileExtension == null) {
				_fileExtension = StringPool.BLANK;
			}

			if (_versionLabel == null) {
				_versionLabel = Store.VERSION_DEFAULT;
			}

			if ((_classPK > -1) && Validator.isNull(_className)) {
				throw new IllegalArgumentException(
					"setClassName must be called with a non-null value if " +
						"setClassPK was called with a positive value");
			}

			return new DLStoreRequest(
				_className, _classPK, _companyId, _entryURL, _fileExtension,
				_fileName, _newRepositoryId, _repositoryId, _size,
				_sourceFileName, _validateFileExtension, _versionLabel);
		}

		public Builder setClassName(Object object) {
			Objects.requireNonNull(object, "object must not be null");

			_className = ClassUtil.getClassName(object);

			return this;
		}

		public Builder setClassName(String className) {
			Objects.requireNonNull(className, "className must not be null");

			_className = className;

			return this;
		}

		public Builder setClassPK(long classPK) {
			if (classPK < 0) {
				throw new IllegalArgumentException(
					"setClassPK must be called with a positive value");
			}

			_classPK = classPK;

			return this;
		}

		public Builder setEntryURL(String entryURL) {
			_entryURL = entryURL;

			return this;
		}

		public Builder setFileExtension(String fileExtension) {
			_fileExtension = fileExtension;

			return this;
		}

		public Builder setNewRepositoryId(long newRepositoryId) {
			_newRepositoryId = newRepositoryId;

			return this;
		}

		public Builder setSize(long size) {
			_size = size;

			return this;
		}

		public Builder setSourceFileName(String sourceFileName) {
			_sourceFileName = sourceFileName;

			return this;
		}

		public Builder setValidateFileExtension(boolean validateFileExtension) {
			_validateFileExtension = validateFileExtension;

			return this;
		}

		public Builder setVersionLabel(String versionLabel) {
			_versionLabel = versionLabel;

			return this;
		}

		private Builder(long companyId, long repositoryId, String fileName) {
			_companyId = companyId;
			_repositoryId = repositoryId;
			_fileName = fileName;
		}

		private String _className = StringPool.BLANK;
		private long _classPK = -1;
		private long _companyId;
		private String _entryURL = StringPool.BLANK;
		private String _fileExtension;
		private String _fileName;
		private long _newRepositoryId = -1;
		private long _repositoryId;
		private long _size = -1;
		private String _sourceFileName;
		private boolean _validateFileExtension;
		private String _versionLabel;

	}

	private DLStoreRequest(
		String className, long classPK, long companyId, String entryURL,
		String fileExtension, String fileName, long newRepositoryId,
		long repositoryId, long size, String sourceFileName,
		boolean validateFileExtension, String versionLabel) {

		_className = className;
		_classPK = classPK;
		_companyId = companyId;
		_entryURL = entryURL;
		_fileExtension = fileExtension;
		_fileName = fileName;
		_newRepositoryId = newRepositoryId;
		_repositoryId = repositoryId;
		_size = size;
		_sourceFileName = sourceFileName;
		_validateFileExtension = validateFileExtension;
		_versionLabel = versionLabel;
	}

	private static final long serialVersionUID = -3103134314128654067L;

	private final String _className;
	private final long _classPK;
	private final long _companyId;
	private final String _entryURL;
	private final String _fileExtension;
	private final String _fileName;
	private final long _newRepositoryId;
	private final long _repositoryId;
	private final long _size;
	private final String _sourceFileName;
	private final boolean _validateFileExtension;
	private final String _versionLabel;

}