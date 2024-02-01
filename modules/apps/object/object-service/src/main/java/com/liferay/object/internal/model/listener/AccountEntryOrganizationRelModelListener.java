/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener;

import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.object.internal.search.ObjectEntryBatchReindexer;
import com.liferay.object.internal.search.ObjectEntryBatchReindexerRegistry;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 * @author Gabriel Albuquerque
 */
@Component(service = ModelListener.class)
public class AccountEntryOrganizationRelModelListener
	extends BaseModelListener<AccountEntryOrganizationRel> {

	@Override
	public void onAfterCreate(
			AccountEntryOrganizationRel accountEntryOrganizationRel)
		throws ModelListenerException {

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				_reindex(accountEntryOrganizationRel);

				return null;
			});
	}

	@Override
	public void onAfterRemove(
			AccountEntryOrganizationRel accountEntryOrganizationRel)
		throws ModelListenerException {

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				_reindex(accountEntryOrganizationRel);

				return null;
			});
	}

	private void _reindex(
		AccountEntryOrganizationRel accountEntryOrganizationRel) {

		for (ObjectEntryBatchReindexer objectEntryBatchReindexer :
				_objectEntryBatchReindexerRegistry.
					getObjectEntryBatchReindexers()) {

			objectEntryBatchReindexer.reindex(
				accountEntryOrganizationRel.getAccountEntryId(),
				accountEntryOrganizationRel.getCompanyId());
		}
	}

	@Reference
	private ObjectEntryBatchReindexerRegistry
		_objectEntryBatchReindexerRegistry;

}