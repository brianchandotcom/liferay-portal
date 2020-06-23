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

package com.liferay.app.builder.workflow.service.impl;

import com.liferay.app.builder.workflow.exception.DuplicateAppBuilderWorkflowTaskLinkException;
import com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink;
import com.liferay.app.builder.workflow.service.base.AppBuilderWorkflowTaskLinkLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink",
	service = AopService.class
)
public class AppBuilderWorkflowTaskLinkLocalServiceImpl
	extends AppBuilderWorkflowTaskLinkLocalServiceBaseImpl {

	@Override
	public AppBuilderWorkflowTaskLink addAppBuilderWorkflowTaskLink(
			long companyId, long appBuilderAppId, long ddmStructureLayoutId,
			boolean readOnly, String workflowTaskName)
		throws PortalException {

		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink =
			appBuilderWorkflowTaskLinkPersistence.fetchByA_D_W(
				appBuilderAppId, ddmStructureLayoutId, workflowTaskName);

		if (Objects.nonNull(appBuilderWorkflowTaskLink)) {
			throw new DuplicateAppBuilderWorkflowTaskLinkException();
		}

		appBuilderWorkflowTaskLink =
			appBuilderWorkflowTaskLinkPersistence.create(
				counterLocalService.increment());

		appBuilderWorkflowTaskLink.setCompanyId(companyId);
		appBuilderWorkflowTaskLink.setAppBuilderAppId(appBuilderAppId);
		appBuilderWorkflowTaskLink.setDdmStructureLayoutId(
			ddmStructureLayoutId);
		appBuilderWorkflowTaskLink.setReadOnly(readOnly);
		appBuilderWorkflowTaskLink.setWorkflowTaskName(workflowTaskName);

		return appBuilderWorkflowTaskLinkPersistence.update(
			appBuilderWorkflowTaskLink);
	}

	@Override
	public void deleteAppBuilderWorkflowTaskLinks(long appBuilderAppId) {
		appBuilderWorkflowTaskLinkPersistence.removeByAppBuilderAppId(
			appBuilderAppId);
	}

	@Override
	public List<AppBuilderWorkflowTaskLink> getAppBuilderWorkflowTaskLinks(
		long appBuilderAppId) {

		return appBuilderWorkflowTaskLinkPersistence.findByAppBuilderAppId(
			appBuilderAppId);
	}

	@Override
	public List<AppBuilderWorkflowTaskLink> getAppBuilderWorkflowTaskLinks(
		long appBuilderAppId, String workflowTaskName) {

		return appBuilderWorkflowTaskLinkPersistence.findByA_W(
			appBuilderAppId, workflowTaskName);
	}

}