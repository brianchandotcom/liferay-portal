/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.cmis.internal;

import com.liferay.document.library.repository.cmis.Session;

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

	private final org.apache.chemistry.opencmis.client.api.Session _session;

}