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

package com.liferay.message.boards.workflow;

import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alexander Chow
 * @author Jorge Ferrer
 * @author Julio Camarero
 */
@Component(
	property = {"model.class.name=com.liferay.portlet.messageboards.model.MBMessage"},
	service = WorkflowHandler.class
)
public class MBMessageWorkflowHandler extends BaseMBWorkflowHandler {

	@Override
	public String getClassName() {
		return MBMessage.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return ResourceActionsUtil.getModelResource(locale, getClassName());
	}

	@Override
	protected MBMessageLocalService getMBMessageLocalService() {
		return _mbMessageLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {

		_mbMessageLocalService = mbMessageLocalService;
	}

	private volatile MBMessageLocalService _mbMessageLocalService;

}