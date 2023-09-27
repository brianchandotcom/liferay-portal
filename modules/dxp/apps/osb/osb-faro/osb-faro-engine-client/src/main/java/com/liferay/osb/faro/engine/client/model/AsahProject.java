/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import java.util.Objects;

/**
 * @author André Miranda
 */
public class AsahProject {

	public AsahProject() {
	}

	public AsahProject(String id) {
		_id = id;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if ((object == null) || !(object instanceof AsahProject)) {
			return false;
		}

		AsahProject project = (AsahProject)object;

		if (Objects.equals(_id, project._id)) {
			return true;
		}

		return false;
	}

	public String getId() {
		return _id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_id);
	}

	public void setId(String id) {
		_id = id;
	}

	private String _id;

}