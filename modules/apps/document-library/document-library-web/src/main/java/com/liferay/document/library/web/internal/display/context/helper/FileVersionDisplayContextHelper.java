/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.display.context.helper;

import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;

/**
 * @author Iván Zaera
 */
public class FileVersionDisplayContextHelper {

	public FileVersionDisplayContextHelper(FileVersion fileVersion) {
		_fileVersion = fileVersion;

		if (fileVersion == null) {
			_approved = false;
			_draft = false;
			_officeDoc = false;
			_pending = false;
		}
	}

	public FileVersion getFileVersion() {
		return _fileVersion;
	}

	public boolean isApproved() {
		if (_approved == null) {
			_approved = _fileVersion.isApproved();
		}

		return _approved;
	}

	public boolean isDLFileVersion() {
		if (_fileVersion.getModel() instanceof DLFileVersion) {
			return true;
		}

		return false;
	}

	public boolean isDraft() {
		if (_draft == null) {
			_draft = _fileVersion.isDraft();
		}

		return _draft;
	}

	public boolean isMsOffice() {
		if (_officeDoc == null) {
			_officeDoc = DLUtil.isOfficeExtension(_fileVersion.getExtension());
		}

		return _officeDoc;
	}

	public boolean isPending() {
		if (_pending == null) {
			_pending = _fileVersion.isPending();
		}

		return _pending;
	}

	private Boolean _approved;
	private Boolean _draft;
	private final FileVersion _fileVersion;
	private Boolean _officeDoc;
	private Boolean _pending;

}