/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.service.access.policy.internal.model.listener;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(service = ModelListener.class)
public class PasswordPolicyModelListener
	extends BaseModelListener<PasswordPolicy> {

	@Override
	public void onAfterCreate(PasswordPolicy passwordPolicy)
		throws ModelListenerException {

		if (!passwordPolicy.isDefaultPolicy()) {
			return;
		}

		try {
			sapEntryLocalService.checkSystemSAPEntries(
				passwordPolicy.getCompanyId());
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Override
	public void onBeforeRemove(PasswordPolicy passwordPolicy)
		throws ModelListenerException {

		if (!passwordPolicy.isDefaultPolicy()) {
			return;
		}

		try {
			List<SAPEntry> sapEntries =
				sapEntryLocalService.getCompanySAPEntries(
					passwordPolicy.getCompanyId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

			for (SAPEntry sapEntry : sapEntries) {
				sapEntryLocalService.deleteSAPEntry(sapEntry);
			}
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Reference
	protected SAPEntryLocalService sapEntryLocalService;

}