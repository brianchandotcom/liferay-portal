/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		return mbMessageLocalService;
	}

	@Reference
	protected MBMessageLocalService mbMessageLocalService;

}