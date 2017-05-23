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

package com.liferay.commerce.product.definitions.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.definitions.web.configuration.AttachmentConfiguration;
import com.liferay.commerce.product.definitions.web.internal.display.context.CPAttachmentFileEntriesDisplayContext;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	configurationPid = "com.liferay.commerce.product.definitions.web.configuration.AttachmentConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_PRODUCT_DEFINITIONS,
		"mvc.command.name=viewAttachmentFileEntries"
	},
	service = MVCRenderCommand.class
)
public class ViewCPAttachmentFileEntriesMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(renderRequest);

			CPAttachmentFileEntriesDisplayContext
				cpAttachmentFileEntriesDisplayContext =
					new CPAttachmentFileEntriesDisplayContext(
						_actionHelper, _attachmentConfiguration,
						_cpAttachmentFileEntryService,
						_cpDefinitionOptionRelService, _cpInstanceHelper,
						httpServletRequest, _itemSelector);

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				cpAttachmentFileEntriesDisplayContext);
		}
		catch (PortalException pe) {
			SessionErrors.add(renderRequest, pe.getClass());
		}

		return "/attachment_file_entries.jsp";
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_attachmentConfiguration = ConfigurableUtil.createConfigurable(
			AttachmentConfiguration.class, properties);
	}

	@Reference
	private ActionHelper _actionHelper;

	private volatile AttachmentConfiguration _attachmentConfiguration;

	@Reference
	private CPAttachmentFileEntryService _cpAttachmentFileEntryService;

	@Reference
	private CPDefinitionOptionRelService _cpDefinitionOptionRelService;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private Portal _portal;

}