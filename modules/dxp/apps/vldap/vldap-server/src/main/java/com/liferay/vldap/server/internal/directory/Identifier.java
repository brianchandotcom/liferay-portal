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

package com.liferay.vldap.server.internal.directory;

/**
 * @author Jonathan McCann
 */
public class Identifier {

	public Identifier(String rdnType, String rdnValue) {
		_rdnType = rdnType;
		_rdnValue = rdnValue;
	}

	public String getRdnType() {
		return _rdnType;
	}

	public String getRdnValue() {
		return _rdnValue;
	}

	private final String _rdnType;
	private final String _rdnValue;

}