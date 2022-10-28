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

package com.liferay.dispatch.rest.internal.resource.v1_0;

import com.liferay.dispatch.constants.DispatchConstants;
import com.liferay.dispatch.rest.dto.v1_0.DispatchTrigger;
import com.liferay.dispatch.rest.internal.dto.v1_0.util.DispatchTriggerUtil;
import com.liferay.dispatch.rest.resource.v1_0.DispatchTriggerResource;
import com.liferay.dispatch.service.DispatchTriggerService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.vulcan.pagination.Page;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Nilton Vieira
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/dispatch-trigger.properties",
	scope = ServiceScope.PROTOTYPE, service = DispatchTriggerResource.class
)
public class DispatchTriggerResourceImpl
	extends BaseDispatchTriggerResourceImpl {

	public Page<DispatchTrigger> getDispatchTriggersPage() throws Exception {
		return Page.of(
			TransformUtil.transform(
				_dispatchTriggerService.getDispatchTriggers(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS),
				dispatchTrigger -> DispatchTriggerUtil.toDispatchTrigger(
					dispatchTrigger)));
	}

	public DispatchTrigger postDispatchTrigger(DispatchTrigger dispatchTrigger)
		throws Exception {

		return DispatchTriggerUtil.toDispatchTrigger(
			_dispatchTriggerService.addDispatchTrigger(
				dispatchTrigger.getExternalReferenceCode(),
				contextUser.getUserId(),
				dispatchTrigger.getDispatchTaskExecutorType(),
				DispatchTriggerUtil.toSettingsUnicodeProperties(
					dispatchTrigger.getDispatchTaskSettings()),
				dispatchTrigger.getName()));
	}

	public void postDispatchTriggerRun(Long dispatchTriggerId)
		throws Exception {

		_dispatchTriggerModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dispatchTriggerId,
			ActionKeys.UPDATE);

		Message message = new Message();

		message.setPayload(
			JSONUtil.put(
				"dispatchTriggerId", dispatchTriggerId
			).toString());

		_destination.send(message);
	}

	@Reference(
		target = "(model.class.name=com.liferay.dispatch.model.DispatchTrigger)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<com.liferay.dispatch.model.DispatchTrigger>
			modelResourcePermission) {

		_dispatchTriggerModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission
		<com.liferay.dispatch.model.DispatchTrigger>
			_dispatchTriggerModelResourcePermission;

	@Reference(
		target = "(destination.name=" + DispatchConstants.EXECUTOR_DESTINATION_NAME + ")"
	)
	private Destination _destination;

	@Reference
	private DispatchTriggerService _dispatchTriggerService;

}