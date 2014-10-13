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

package com.liferay.portlet.documentlibrary.context.helper;

import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.util.DLUtil;

/**
 * @author Iván Zaera
 */
public class FileVersionDisplayContextHelper {

	public FileVersionDisplayContextHelper(FileVersion fileVersion) {
		_fileVersion = fileVersion;

		if (_fileVersion == null) {
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