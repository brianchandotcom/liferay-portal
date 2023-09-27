/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json.web.service.client.model;

/**
 * @author Igor Beslic
 */
public class ResponseBody {

	public String getParameter1() {
		return _parameter1;
	}

	public String getParameter2() {
		return _parameter2;
	}

	public String getParameter3() {
		return _parameter3;
	}

	public void setParameter1(String parameter1) {
		_parameter1 = parameter1;
	}

	public void setParameter2(String parameter2) {
		_parameter2 = parameter2;
	}

	public void setParameter3(String parameter3) {
		_parameter3 = parameter3;
	}

	private String _parameter1;
	private String _parameter2;
	private String _parameter3;

}