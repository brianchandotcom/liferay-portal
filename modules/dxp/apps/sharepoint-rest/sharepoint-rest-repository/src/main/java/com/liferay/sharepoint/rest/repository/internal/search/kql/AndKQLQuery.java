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

package com.liferay.sharepoint.rest.repository.internal.search.kql;

/**
 * @author Adolfo Pérez
 */
public class AndKQLQuery implements KQLQuery {

	public AndKQLQuery(KQLQuery kqlQuery1, KQLQuery kqlQuery2) {
		_kqlQuery1 = kqlQuery1;
		_kqlQuery2 = kqlQuery2;
	}

	@Override
	public String toString() {
		return String.format(
			"(%s AND %s)", _kqlQuery1.toString(), _kqlQuery2.toString());
	}

	private final KQLQuery _kqlQuery1;
	private final KQLQuery _kqlQuery2;

}