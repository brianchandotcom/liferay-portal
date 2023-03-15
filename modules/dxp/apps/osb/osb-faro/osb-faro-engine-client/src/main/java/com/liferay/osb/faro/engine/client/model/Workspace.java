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

/**
 * @author Matthew Kong
 */
public class Workspace {

	public String getWeDeployKey() {
		return _weDeployKey;
	}

	public boolean isReady() {
		return _ready;
	}

	public void setReady(boolean ready) {
		_ready = ready;
	}

	public void setWeDeployKey(String weDeployKey) {
		_weDeployKey = weDeployKey;
	}

	public enum Health {

		healthy, none, starting, unhealthy

	}

	private boolean _ready;
	private String _weDeployKey;

}