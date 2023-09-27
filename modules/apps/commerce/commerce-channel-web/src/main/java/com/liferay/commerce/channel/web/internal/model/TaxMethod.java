/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.channel.web.internal.model;

import com.liferay.commerce.frontend.model.LabelField;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class TaxMethod {

	public TaxMethod(
		String description, String key, String name, LabelField status,
		String taxEngine) {

		_description = description;
		_key = key;
		_name = name;
		_status = status;
		_taxEngine = taxEngine;
	}

	public String getDescription() {
		return _description;
	}

	public String getKey() {
		return _key;
	}

	public String getName() {
		return _name;
	}

	public LabelField getStatus() {
		return _status;
	}

	public String getTaxEngine() {
		return _taxEngine;
	}

	private final String _description;
	private final String _key;
	private final String _name;
	private final LabelField _status;
	private final String _taxEngine;

}