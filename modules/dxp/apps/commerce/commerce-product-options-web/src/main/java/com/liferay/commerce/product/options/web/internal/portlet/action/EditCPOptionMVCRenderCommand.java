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

package com.liferay.commerce.product.options.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.options.web.internal.display.context.CPOptionDisplayContext;
import com.liferay.commerce.product.service.CPOptionService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_OPTIONS,
		"mvc.command.name=cpOption"
	},
	service = MVCRenderCommand.class
)
public class EditCPOptionMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		long cpOptionId = ParamUtil.getLong(renderRequest, "cpOptionId");

		try {
			CPOption cpOption = _cpOptionService.fetchCPOption(cpOptionId);

			CPOptionDisplayContext cpOptionDisplayContext =
				new CPOptionDisplayContext(
					_configurationProvider, cpOption,
					_ddmFormFieldTypeServicesTracker);

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT, cpOptionDisplayContext);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		return "/edit_option.jsp";
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private CPOptionService _cpOptionService;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

}