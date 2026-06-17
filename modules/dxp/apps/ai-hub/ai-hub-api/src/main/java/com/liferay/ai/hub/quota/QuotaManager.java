/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.quota;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Carolina Barbosa
 */
public interface QuotaManager {

	public String acquireAgentInstancePermit(long userId)
		throws PortalException;

	public void addQuotas(long accountEntryId, long companyId, long userId)
		throws PortalException;

	public void checkTokensUsage(long companyId, long userId)
		throws PortalException;

	public void releaseAgentInstancePermit(String permit);

	public void updateUsage(long companyId, Usage usage, long userId)
		throws PortalException;

}