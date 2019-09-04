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

package com.liferay.change.tracking.change.lists.history.web.internal.portlet.action;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Samuel Trong Tran
 */
public abstract class BaseCTProcessMVCResourceCommand
	extends BaseMVCResourceCommand {

	protected int getStatus(String type) {
		int status = 0;

		if (TYPE_ALL.equals(type)) {
			status = WorkflowConstants.STATUS_ANY;
		}
		else if (TYPE_FAILED.equals(type)) {
			status = BackgroundTaskConstants.STATUS_FAILED;
		}
		else if (TYPE_IN_PROGRESS.equals(type)) {
			status = BackgroundTaskConstants.STATUS_IN_PROGRESS;
		}
		else if (TYPE_PUBLISHED.equals(type)) {
			status = BackgroundTaskConstants.STATUS_SUCCESSFUL;
		}

		return status;
	}

	protected static final String TYPE_ALL = "all";

	protected static final String TYPE_FAILED = "failed";

	protected static final String TYPE_IN_PROGRESS = "in-progress";

	protected static final String TYPE_PUBLISHED = "published";

	protected static final String TYPE_SUCCESSFUL = "successful";

}