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

package com.liferay.message.boards.internal.workflow;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alexander Chow
 * @author Jorge Ferrer
 * @author Julio Camarero
 */
@Component(
	property = "model.class.name=com.liferay.message.boards.model.MBMessage",
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

	private MBMessageLocalService _mbMessageLocalService;

}