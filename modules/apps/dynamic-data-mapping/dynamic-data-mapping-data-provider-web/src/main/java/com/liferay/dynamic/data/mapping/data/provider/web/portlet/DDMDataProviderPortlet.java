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

package com.liferay.dynamic.data.mapping.data.provider.web.portlet;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderSettings;
import com.liferay.dynamic.data.mapping.data.provider.web.constants.DDMDataProviderPortletKeys;
import com.liferay.dynamic.data.mapping.data.provider.web.display.context.DDMDataProviderDisplayContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.UserLocalService;

import java.io.IOException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-dynamic-data-mapping-data-provider",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.instanceable=false",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Dynamic Data Mapping Data Provider Web",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + DDMDataProviderPortletKeys.DYNAMIC_DATA_MAPPING_DATA_PROVIDER,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class DDMDataProviderPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		DDMDataProviderDisplayContext ddmDataProviderDisplayContext =
			new DDMDataProviderDisplayContext(
				renderRequest, renderResponse, _ddmDataProviderInstanceService,
				_ddmDataProviderInstanceLocalService, _ddmFormRenderer,
				_userLocalService, _ddmDataProvidersMap);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, ddmDataProviderDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "unregisterDDMDataProviderSettings"
	)
	protected synchronized void registerDDMDataProviderSettings(
		DDMDataProviderSettings ddmDataProviderSettings,
		Map<String, Object> properties) {

		Object value = properties.get("ddm.data.provider.name");

		_ddmDataProvidersMap.put(value.toString(), ddmDataProviderSettings);
	}

	@Reference(unbind = "-")
	protected void setDDMDataProviderInstanceLocalService(
		DDMDataProviderInstanceLocalService
			ddmDataProviderInstanceLocalService) {

		_ddmDataProviderInstanceLocalService =
			ddmDataProviderInstanceLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMDataProviderInstanceService(
		DDMDataProviderInstanceService ddmDataProviderInstanceService) {

		_ddmDataProviderInstanceService = ddmDataProviderInstanceService;
	}

	@Reference(unbind = "-")
	protected void setDDMFormRenderer(DDMFormRenderer ddmFormRenderer) {
		_ddmFormRenderer = ddmFormRenderer;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	protected synchronized void unregisterDDMDataProviderSettings(
		DDMDataProviderSettings ddmDataProviderSettings,
		Map<String, Object> properties) {

		Object value = properties.get("ddm.data.provider.name");

		_ddmDataProvidersMap.remove(value);
	}

	private DDMDataProviderInstanceLocalService
		_ddmDataProviderInstanceLocalService;
	private DDMDataProviderInstanceService _ddmDataProviderInstanceService;
	private final Map<String, DDMDataProviderSettings> _ddmDataProvidersMap =
		new ConcurrentHashMap<>();
	private DDMFormRenderer _ddmFormRenderer;
	private UserLocalService _userLocalService;

}