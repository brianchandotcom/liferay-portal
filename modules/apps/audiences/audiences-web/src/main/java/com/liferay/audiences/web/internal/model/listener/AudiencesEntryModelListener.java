/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audiences.web.internal.model.listener;

import com.liferay.audiences.model.AudiencesEntry;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.KeyValuePair;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ModelListener.class)
public class AudiencesEntryModelListener
	extends BaseModelListener<AudiencesEntry> {

	@Override
	public void onAfterCreate(AudiencesEntry audiencesEntry) {
		_portalCache.remove(audiencesEntry.getCompanyId());
	}

	@Override
	public void onAfterRemove(AudiencesEntry audiencesEntry) {
		_portalCache.remove(audiencesEntry.getCompanyId());
	}

	@Override
	public void onAfterUpdate(
		AudiencesEntry originalAudiencesEntry, AudiencesEntry audiencesEntry) {

		_portalCache.remove(audiencesEntry.getCompanyId());
	}

	@Activate
	protected void activate() {
		_portalCache =
			(PortalCache<Long, KeyValuePair>)_multiVMPool.getPortalCache(
				AudiencesEntry.class.getName());
	}

	@Reference
	private MultiVMPool _multiVMPool;

	private PortalCache<Long, KeyValuePair> _portalCache;

}