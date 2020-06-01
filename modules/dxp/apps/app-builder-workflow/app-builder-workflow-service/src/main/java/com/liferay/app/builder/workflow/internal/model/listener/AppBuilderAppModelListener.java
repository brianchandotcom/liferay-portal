/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.app.builder.workflow.internal.model.listener;

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.workflow.service.AppBuilderWorkflowTaskLinkLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = ModelListener.class)
public class AppBuilderAppModelListener
	extends BaseModelListener<AppBuilderApp> {

	@Override
	public void onBeforeRemove(AppBuilderApp appBuilderApp)
		throws ModelListenerException {

		try {
			_appBuilderWorkflowTaskLinkLocalService.
				deleteAppBuilderWorkflowTaskLinks(
					appBuilderApp.getAppBuilderAppId());
		}
		catch (Exception exception) {
			_log.error(
				"Unable to delete app builder workflow task links", exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AppBuilderAppModelListener.class);

	@Reference
	private AppBuilderWorkflowTaskLinkLocalService
		_appBuilderWorkflowTaskLinkLocalService;

}