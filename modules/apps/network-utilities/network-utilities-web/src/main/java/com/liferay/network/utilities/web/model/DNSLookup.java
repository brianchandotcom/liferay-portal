/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.network.utilities.web.model;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public class DNSLookup implements Serializable {

	public DNSLookup() {
	}

	public DNSLookup(String domain, String results) {
		_domain = domain;
		_results = results;
	}

	public String getDomain() {
		return _domain;
	}

	public String getResults() {
		return _results;
	}

	public void setDomain(String domain) {
		_domain = domain;
	}

	public void setResults(String results) {
		_results = results;
	}

	private String _domain;
	private String _results;

}