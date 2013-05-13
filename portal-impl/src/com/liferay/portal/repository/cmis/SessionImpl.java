/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.repository.cmis;

import com.liferay.portal.kernel.repository.cmis.Session;

import java.util.Set;

import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.runtime.OperationContextImpl;
import org.apache.chemistry.opencmis.commons.enums.IncludeRelationships;

/**
 * @author Alexander Chow
 */
public class SessionImpl implements Session {

	public SessionImpl(
		org.apache.chemistry.opencmis.client.api.Session session) {

		_session = session;
	}

	public org.apache.chemistry.opencmis.client.api.Session getSession() {
		return _session;
	}

	@Override
	public void setDefaultContext(
		Set<String> filter, boolean includeAcls,
		boolean includeAllowableActions, boolean includePolicies,
		String includeRelationships, Set<String> renditionFilter,
		boolean includePathSegments, String orderBy, boolean cacheEnabled,
		int maxItemsPerPage) {

		IncludeRelationships includeRelationshipsObj = null;

		if (includeRelationships != null) {
			includeRelationshipsObj = IncludeRelationships.fromValue(
				includeRelationships);
		}

		OperationContext operationContext = new OperationContextImpl(
			filter, includeAcls, includeAllowableActions, includePolicies,
			includeRelationshipsObj, renditionFilter, includePathSegments,
			orderBy, cacheEnabled, maxItemsPerPage);

		_session.setDefaultContext(operationContext);
	}

	private org.apache.chemistry.opencmis.client.api.Session _session;

}