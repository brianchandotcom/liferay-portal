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

import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Adolfo Pérez
 */
public class NullKQLQuery implements KQLQuery {

	public static final KQLQuery INSTANCE = new NullKQLQuery();

	@Override
	public KQLQuery and(KQLQuery kqlQuery) {
		return kqlQuery;
	}

	@Override
	public KQLQuery not() {
		return this;
	}

	@Override
	public KQLQuery or(KQLQuery kqlQuery) {
		return kqlQuery;
	}

	@Override
	public String toString() {
		return StringPool.BLANK;
	}

}