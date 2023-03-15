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

package com.liferay.osb.faro.web.internal.model.display.contacts;

/**
 * @author Geyson Silva
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class JoinableProjectDisplay {

	public JoinableProjectDisplay(
		long groupId, String name, boolean requested) {

		_groupId = groupId;
		_name = name;
		_requested = requested;
	}

	private final long _groupId;
	private final String _name;
	private final boolean _requested;

}