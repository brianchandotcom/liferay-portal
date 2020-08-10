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

package com.liferay.commerce.machine.learning.internal.gateway;

/**
 * @author Riccardo Ferrari
 */
public class CommerceMLJobState {

	public CommerceMLJobState() {
	}

	public String getApplicationId() {
		return _applicationId;
	}

	public String getState() {
		return _state;
	}

	public void setApplicationId(String applicationId) {
		_applicationId = applicationId;
	}

	public void setState(String state) {
		_state = state;
	}

	private String _applicationId;
	private String _state;

}