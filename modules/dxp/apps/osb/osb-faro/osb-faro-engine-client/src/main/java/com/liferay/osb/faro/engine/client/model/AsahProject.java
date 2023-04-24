/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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