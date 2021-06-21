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

package com.liferay.digital.signature.manager;

import com.liferay.digital.signature.model.DSEnvelope;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface DSEnvelopeManager {

	public DSEnvelope addDSEnvelope(
		long companyId, long groupId, DSEnvelope dsEnvelope);

	public void deleteDSEnvelopes(
			long companyId, long groupId, String... dsEnvelopeIds)
		throws Exception;

	public DSEnvelope getDSEnvelope(
		long companyId, long groupId, String dsEnvelopeId);

	public DSEnvelope getDSEnvelope(
		long companyId, long groupId, String dsEnvelopeId, String include);

	public Page<DSEnvelope> getDSEnvelopesPage(
		long companyId, long groupId, String fromDateString, String keywords,
		String order, Pagination pagination, String status);

}