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

package com.liferay.portal.workflow.instance.tracker.web.internal.url.provider;

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.BeanProperties;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.workflow.instance.tracker.url.provider.WorkflowInstanceTrackerURLProvider;
import com.liferay.portal.workflow.instance.tracker.web.internal.constants.WorkflowInstanceTrackerPortletKeys;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(immediate = true, service = WorkflowInstanceTrackerURLProvider.class)
public class WorkflowInstanceTrackerURLProviderImpl
	implements WorkflowInstanceTrackerURLProvider {

	@Override
	public String getURL(
		Object bean, HttpServletRequest httpServletRequest, Class<?> modelClass,
		boolean useDialog) {

		String portletURL = PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, null,
				WorkflowInstanceTrackerPortletKeys.WORKFLOW_INSTANCE_TRACKER, 0,
				0, PortletRequest.RENDER_PHASE)
		).setParameter(
			"instanceId",
			() -> {
				try {
					WorkflowInstanceLink workflowInstanceLink =
						_workflowInstanceLinkLocalService.
							getWorkflowInstanceLink(
								_beanProperties.getLong(bean, "companyId"),
								_beanProperties.getLong(bean, "groupId"),
								modelClass.getName(),
								_beanProperties.getLong(bean, "primaryKey"));

					return workflowInstanceLink.getWorkflowInstanceId();
				}
				catch (PortalException portalException) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							portalException.getMessage(), portalException);
					}
				}

				return null;
			}
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();

		if (useDialog) {
			return StringBundler.concat(
				"javascript:",
				"Liferay.Util.openModal({iframeBodyCssClass: '', title: '",
				UnicodeLanguageUtil.get(httpServletRequest, "track-workflow"),
				"', url: '", portletURL, "'});;");
		}

		return portletURL;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WorkflowInstanceTrackerURLProviderImpl.class);

	@Reference
	private BeanProperties _beanProperties;

	@Reference
	private Portal _portal;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}