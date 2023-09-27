/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static Builder builder(
		long companyId, long repositoryId, String fileName) {

		if (companyId < 0) {
			throw new IllegalArgumentException("Company ID is less than 0");
		}

		if (repositoryId < 0) {
			throw new IllegalArgumentException("Repository ID is less than 0");
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
			if ((_classPK > 0) && Validator.isNull(_className)) {
				throw new IllegalArgumentException(
					"Class name is null and class PK is greater than 0");
			}

			if (_fileExtension == null) {
				_fileExtension = StringPool.BLANK;
			}

			if (_sourceFileName == null) {
				_sourceFileName = _fileName;
			}

			if (_versionLabel == null) {
				_versionLabel = Store.VERSION_DEFAULT;
			}

			return new DLStoreRequest(
				_className, _classPK, _companyId, _entryURL, _fileExtension,
				_fileName, _newRepositoryId, _repositoryId, _size,
				_sourceFileName, _validateFileExtension, _versionLabel);
		}

		public Builder className(Object object) {
			Objects.requireNonNull(object, "Object is null");

			_className = ClassUtil.getClassName(object);

			return this;
		}

		public Builder className(String className) {
			Objects.requireNonNull(className, "Class name is null");

			_className = className;

			return this;
		}

		public Builder classPK(long classPK) {
			if (classPK < 0) {
				throw new IllegalArgumentException("Class PK is less than 0");
			}

			_classPK = classPK;

			return this;
		}

		public Builder entryURL(String entryURL) {
			_entryURL = entryURL;

			return this;
		}

		public Builder fileExtension(String fileExtension) {
			_fileExtension = fileExtension;

			return this;
		}

		public Builder newRepositoryId(long newRepositoryId) {
			_newRepositoryId = newRepositoryId;

			return this;
		}

		public Builder size(long size) {
			_size = size;

			return this;
		}

		public Builder sourceFileName(String sourceFileName) {
			_sourceFileName = sourceFileName;

			return this;
		}

		public Builder validateFileExtension(boolean validateFileExtension) {
			_validateFileExtension = validateFileExtension;

			return this;
		}

		public Builder versionLabel(String versionLabel) {
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