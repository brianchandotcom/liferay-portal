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