/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.model.listener;

import com.liferay.account.manager.CurrentAccountEntryManager;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(service = ModelListener.class)
public class AccountEntryUserRelModelListener
	extends BaseModelListener<AccountEntryUserRel> {

	@Override
	public void onAfterCreate(AccountEntryUserRel accountEntryUserRel)
		throws ModelListenerException {

		try {
			_onAfterCreate(accountEntryUserRel);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private void _onAfterCreate(AccountEntryUserRel accountEntryUserRel)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return;
		}

		long groupId = serviceContext.getScopeGroupId();

		if ((groupId <= 0) ||
			(_layoutUtilityPageEntryLocalService.
				fetchLayoutUtilityPageEntryByExternalReferenceCode(
					"L_AI_HUB_CREATE_ACCOUNT_UTILITY_PAGE", groupId) == null)) {

			return;
		}

		_currentAccountEntryManager.setCurrentAccountEntry(
			accountEntryUserRel.getAccountEntryId(), groupId,
			accountEntryUserRel.getAccountUserId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountEntryUserRelModelListener.class);

	@Reference
	private CurrentAccountEntryManager _currentAccountEntryManager;

	@Reference
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

}