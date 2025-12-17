/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.comment;

import java.io.Serializable;

/**
 * @author Guilherme Camacho
 */
public class ObjectEntryComment implements Serializable {

	public ObjectEntryComment(
		String externalReferenceCode, String parentCommentExternalReferenceCode,
		long parentCommentId, String text) {

		_externalReferenceCode = externalReferenceCode;
		_parentCommentExternalReferenceCode =
			parentCommentExternalReferenceCode;
		_parentCommentId = parentCommentId;
		_text = text;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public String getParentCommentExternalReferenceCode() {
		return _parentCommentExternalReferenceCode;
	}

	public long getParentCommentId() {
		return _parentCommentId;
	}

	public String getText() {
		return _text;
	}

	private final String _externalReferenceCode;
	private final String _parentCommentExternalReferenceCode;
	private final long _parentCommentId;
	private final String _text;

}