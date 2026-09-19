/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.group;

/**
 * @author Petteri Karttunen
 */
public class ExportedGroup {

	public ExportedGroup(
		int childGroupCount, String descriptiveName,
		String externalReferenceCode, long groupId,
		String parentGroupExternalReferenceCode, String path) {

		_childGroupCount = childGroupCount;
		_descriptiveName = descriptiveName;
		_externalReferenceCode = externalReferenceCode;
		_groupId = groupId;
		_parentGroupExternalReferenceCode = parentGroupExternalReferenceCode;
		_path = path;
	}

	public int getChildGroupCount() {
		return _childGroupCount;
	}

	public String getDescriptiveName() {
		return _descriptiveName;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public long getGroupId() {
		return _groupId;
	}

	public String getParentGroupExternalReferenceCode() {
		return _parentGroupExternalReferenceCode;
	}

	public String getPath() {
		return _path;
	}

	private final int _childGroupCount;
	private final String _descriptiveName;
	private final String _externalReferenceCode;
	private final long _groupId;
	private final String _parentGroupExternalReferenceCode;
	private final String _path;

}