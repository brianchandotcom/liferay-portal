/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.quota;

import com.liferay.ai.hub.quota.QuotaManager;
import com.liferay.ai.hub.quota.Usage;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.Closeable;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carolina Barbosa
 */
@Component(
	property = "service.ranking:Integer=-1", service = QuotaManager.class
)
public class DummyQuotaManager implements QuotaManager {

	@Override
	public void addQuotas(long accountEntryId, long companyId, long userId) {
	}

	@Override
	public Closeable checkConcurrentRequests(long userId)
		throws PortalException {

		return () -> {
		};
	}

	@Override
	public void checkTokensUsage(long companyId, long userId) {
	}

	@Override
	public void updateUsage(long companyId, Usage usage, long userId) {
	}

}