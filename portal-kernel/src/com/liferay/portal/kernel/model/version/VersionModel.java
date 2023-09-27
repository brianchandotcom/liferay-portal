/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model.version;

/**
 * @author Preston Crary
 */
public interface VersionModel<E extends VersionedModel> {

	public long getPrimaryKey();

	public int getVersion();

	public long getVersionedModelId();

	public void populateVersionedModel(E versionedModel);

	public void setPrimaryKey(long primaryKey);

	public void setVersion(int version);

	public void setVersionedModelId(long id);

	public E toVersionedModel();

}