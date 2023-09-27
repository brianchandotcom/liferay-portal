/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.jsonws.internal.service.access.policy.model.listener;

import com.liferay.oauth2.provider.jsonws.internal.service.access.policy.scope.SAPEntryScopeDescriptorFinderRegistrator;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(service = ModelListener.class)
public class SAPEntryModelListener extends BaseModelListener<SAPEntry> {

	@Override
	public void onAfterCreate(SAPEntry sapEntry) throws ModelListenerException {
		_sapEntryScopeDescriptorFinderRegistrator.register(
			sapEntry.getCompanyId());
	}

	@Override
	public void onAfterRemove(SAPEntry sapEntry) throws ModelListenerException {
		_sapEntryScopeDescriptorFinderRegistrator.register(
			sapEntry.getCompanyId());
	}

	@Override
	public void onAfterUpdate(SAPEntry originalSAPEntry, SAPEntry sapEntry)
		throws ModelListenerException {

		_sapEntryScopeDescriptorFinderRegistrator.register(
			sapEntry.getCompanyId());
	}

	@Reference
	private SAPEntryScopeDescriptorFinderRegistrator
		_sapEntryScopeDescriptorFinderRegistrator;

}