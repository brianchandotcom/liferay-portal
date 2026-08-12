/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.site;

import java.io.Serializable;

/**
 * A site a LAR carries as a whole unit.
 *
 * @author Petteri Karttunen
 */
public class LARSite implements Serializable {

	public LARSite(
		int childSiteCount, String descriptiveName,
		String externalReferenceCode, long groupId,
		String parentExternalReferenceCode, String path) {

		_childSiteCount = childSiteCount;
		_descriptiveName = descriptiveName;
		_externalReferenceCode = externalReferenceCode;
		_groupId = groupId;
		_parentExternalReferenceCode = parentExternalReferenceCode;
		_path = path;
	}

	public int getChildSiteCount() {
		return _childSiteCount;
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

	public String getParentExternalReferenceCode() {
		return _parentExternalReferenceCode;
	}

	public String getPath() {
		return _path;
	}

	private final int _childSiteCount;
	private final String _descriptiveName;
	private final String _externalReferenceCode;
	private final long _groupId;
	private final String _parentExternalReferenceCode;
	private final String _path;

}