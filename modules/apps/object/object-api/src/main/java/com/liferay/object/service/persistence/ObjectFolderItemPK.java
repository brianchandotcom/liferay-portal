/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import java.io.Serializable;

/**
 * @author Marco Leo
 * @generated
 */
public class ObjectFolderItemPK
	implements Comparable<ObjectFolderItemPK>, Serializable {

	public long objectDefinitionId;
	public long objectFolderId;

	public ObjectFolderItemPK() {
	}

	public ObjectFolderItemPK(long objectDefinitionId, long objectFolderId) {
		this.objectDefinitionId = objectDefinitionId;
		this.objectFolderId = objectFolderId;
	}

	public long getObjectDefinitionId() {
		return objectDefinitionId;
	}

	public void setObjectDefinitionId(long objectDefinitionId) {
		this.objectDefinitionId = objectDefinitionId;
	}

	public long getObjectFolderId() {
		return objectFolderId;
	}

	public void setObjectFolderId(long objectFolderId) {
		this.objectFolderId = objectFolderId;
	}

	@Override
	public int compareTo(ObjectFolderItemPK pk) {
		if (pk == null) {
			return -1;
		}

		int value = 0;

		if (objectDefinitionId < pk.objectDefinitionId) {
			value = -1;
		}
		else if (objectDefinitionId > pk.objectDefinitionId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (objectFolderId < pk.objectFolderId) {
			value = -1;
		}
		else if (objectFolderId > pk.objectFolderId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectFolderItemPK)) {
			return false;
		}

		ObjectFolderItemPK pk = (ObjectFolderItemPK)object;

		if ((objectDefinitionId == pk.objectDefinitionId) &&
			(objectFolderId == pk.objectFolderId)) {

			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hashCode = 0;

		hashCode = HashUtil.hash(hashCode, objectDefinitionId);
		hashCode = HashUtil.hash(hashCode, objectFolderId);

		return hashCode;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(6);

		sb.append("{");

		sb.append("objectDefinitionId=");

		sb.append(objectDefinitionId);
		sb.append(", objectFolderId=");

		sb.append(objectFolderId);

		sb.append("}");

		return sb.toString();
	}

}