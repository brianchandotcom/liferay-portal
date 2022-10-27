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

package com.liferay.layout.internal.workflow;

import com.liferay.layout.content.LayoutContentProvider;
import com.liferay.layout.internal.configuration.LayoutWorkflowHandlerConfiguration;
import com.liferay.layout.service.LayoutLocalizationLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	configurationPid = "com.liferay.layout.internal.configuration.LayoutWorkflowHandlerConfiguration",
	property = "model.class.name=com.liferay.portal.kernel.model.Layout",
	service = WorkflowHandler.class
)
public class LayoutWorkflowHandler extends BaseWorkflowHandler<Layout> {

	@Override
	public String getClassName() {
		return Layout.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return _language.get(locale, "content-page");
	}

	@Override
	public WorkflowDefinitionLink getWorkflowDefinitionLink(
			long companyId, long groupId, long classPK)
		throws PortalException {

		Layout layout = _layoutLocalService.getLayout(classPK);

		if (layout.isSystem() || !layout.isTypeContent()) {
			return null;
		}

		return super.getWorkflowDefinitionLink(companyId, groupId, classPK);
	}

	@Override
	public boolean isVisible() {
		return _layoutConverterConfiguration.enabled();
	}

	@Override
	public Layout updateStatus(
			int status, Map<String, Serializable> workflowContext)
		throws PortalException {

		long userId = GetterUtil.getLong(
			(String)workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));

		long classPK = GetterUtil.getLong(
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		Layout layout = _layoutLocalService.getLayout(classPK);

		if (layout.isDenied() && (status == WorkflowConstants.STATUS_PENDING)) {
			return layout;
		}

		ServiceContext serviceContext = (ServiceContext)workflowContext.get(
			"serviceContext");

		if (status != WorkflowConstants.STATUS_APPROVED) {
			return _layoutLocalService.updateStatus(
				userId, classPK, status, serviceContext);
		}

		Layout draftLayout = layout.fetchDraftLayout();

		long originalUserId = PrincipalThreadLocal.getUserId();

		try {
			PrincipalThreadLocal.setName(userId);

			_layoutCopyHelper.copyLayout(draftLayout, layout);
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
		finally {
			PrincipalThreadLocal.setName(originalUserId);
		}

		_layoutLocalService.updateStatus(
			userId, draftLayout.getPlid(), WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		if ((serviceContext.getRequest() != null) &&
			(serviceContext.getResponse() != null)) {

			layout = _layoutLocalService.getLayout(layout.getPlid());

			_updateLayoutContent(
				serviceContext.getRequest(), serviceContext.getResponse(),
				layout, serviceContext);
		}

		return _layoutLocalService.updateStatus(
			userId, classPK, status, serviceContext);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_layoutConverterConfiguration = ConfigurableUtil.createConfigurable(
			LayoutWorkflowHandlerConfiguration.class, properties);
	}

	private void _updateLayoutContent(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Layout layout,
		ServiceContext serviceContext) {

		for (Locale locale :
				_language.getAvailableLocales(layout.getGroupId())) {

			_layoutLocalizationLocalService.updateLayoutLocalization(
				_layoutContentProvider.getLayoutContent(
					httpServletRequest, httpServletResponse, layout, locale),
				LocaleUtil.toLanguageId(locale), layout.getPlid(),
				serviceContext);
		}
	}

	@Reference
	private Language _language;

	@Reference
	private LayoutContentProvider _layoutContentProvider;

	private volatile LayoutWorkflowHandlerConfiguration
		_layoutConverterConfiguration;

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalizationLocalService _layoutLocalizationLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}