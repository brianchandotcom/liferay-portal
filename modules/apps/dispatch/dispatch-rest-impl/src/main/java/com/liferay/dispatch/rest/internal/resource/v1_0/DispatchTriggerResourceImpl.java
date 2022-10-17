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

import com.liferay.dispatch.rest.dto.v1_0.DispatchTrigger;
import com.liferay.dispatch.rest.internal.dto.v1_0.util.DispatchTriggerUtil;
import com.liferay.dispatch.rest.resource.v1_0.DispatchTriggerResource;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.ArrayList;
import java.util.List;

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
		List<DispatchTrigger> dispatchTriggers1 = new ArrayList<>();

		List<com.liferay.dispatch.model.DispatchTrigger> dispatchTriggers2 =
			_dispatchTriggerLocalService.getDispatchTriggers(
				contextCompany.getCompanyId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (com.liferay.dispatch.model.DispatchTrigger dispatchTrigger :
				dispatchTriggers2) {

			dispatchTriggers1.add(
				DispatchTriggerUtil.toDispatchTrigger(dispatchTrigger));
		}

		return Page.of(dispatchTriggers1);
	}

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

}