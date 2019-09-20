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

package com.liferay.portal.workflow.kaleo.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kenneth Chang
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoDefinitionModelListener
	extends BaseModelListener<KaleoDefinition> {

	@Override
	public void onAfterCreate(final KaleoDefinition kaleoDefinition)
		throws ModelListenerException {

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				Message message = new Message();

				message.put("command", "create");
				message.put("name", kaleoDefinition.getName());
				message.put(
					"serviceContext", getServiceContext(kaleoDefinition));
				message.put("version", kaleoDefinition.getVersion());

				_messageBus.sendMessage("liferay/kaleo_definition", message);

				return null;
			});
	}

	@Override
	public void onAfterRemove(final KaleoDefinition kaleoDefinition)
		throws ModelListenerException {

		if (kaleoDefinition == null) {
			return;
		}

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				try {
					Message message = new Message();

					message.put("command", "delete");
					message.put("name", kaleoDefinition.getName());
					message.put(
						"serviceContext", getServiceContext(kaleoDefinition));
					message.put("version", kaleoDefinition.getVersion());

					_messageBus.sendMessage(
						"liferay/kaleo_definition", message);
				}
				catch (Exception e) {
					throw new ModelListenerException(e);
				}

				return null;
			});
	}

	protected ServiceContext getServiceContext(
		KaleoDefinition kaleoDefinition) {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();
		}

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(kaleoDefinition.getCompanyId());
		serviceContext.setScopeGroupId(kaleoDefinition.getGroupId());
		serviceContext.setUserId(kaleoDefinition.getUserId());

		return serviceContext;
	}

	@Reference
	private MessageBus _messageBus;

}