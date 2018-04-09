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

package com.liferay.user.associated.data.web.internal.portlet.action;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.aggregator.UADAggregator;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
public abstract class BaseUADMVCActionCommand extends BaseMVCActionCommand {

	protected void doMultipleAction(
			ActionRequest actionRequest,
			UnsafeConsumer<Object, Exception> unsafeConsumer)
		throws Exception {

		for (Object entity : getEntities(actionRequest)) {
			unsafeConsumer.accept(entity);
		}
	}

	protected List<Object> getEntities(ActionRequest actionRequest)
		throws Exception {

		UADAggregator uadAggregator = getUADAggregator(actionRequest);

		String[] primaryKeys = ParamUtil.getStringValues(
			actionRequest, "primaryKeys");

		List<Object> entities = new ArrayList<>();

		for (String primaryKey : primaryKeys) {
			entities.add(uadAggregator.get(primaryKey));
		}

		return entities;
	}

	protected Object getEntity(ActionRequest actionRequest) throws Exception {
		UADAggregator uadAggregator = getUADAggregator(actionRequest);

		String primaryKey = ParamUtil.getString(actionRequest, "primaryKey");

		return uadAggregator.get(primaryKey);
	}

	protected User getSelectedUser(ActionRequest actionRequest)
		throws PortalException {

		return portal.getSelectedUser(actionRequest);
	}

	protected long getSelectedUserId(ActionRequest actionRequest)
		throws PortalException {

		User selectedUser = portal.getSelectedUser(actionRequest);

		return selectedUser.getUserId();
	}

	protected UADAggregator getUADAggregator(ActionRequest actionRequest) {
		return uadRegistry.getUADAggregator(getUADRegistryKey(actionRequest));
	}

	protected UADAnonymizer getUADAnonymizer(ActionRequest actionRequest) {
		return uadRegistry.getUADAnonymizer(getUADRegistryKey(actionRequest));
	}

	protected String getUADRegistryKey(ActionRequest actionRequest) {
		return ParamUtil.getString(actionRequest, "uadRegistryKey");
	}

	@Reference
	protected Portal portal;

	@Reference
	protected UADRegistry uadRegistry;

}