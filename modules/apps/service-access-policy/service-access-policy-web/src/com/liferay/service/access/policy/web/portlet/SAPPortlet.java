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

package com.liferay.service.access.policy.web.portlet;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionMapping;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManager;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.service.access.policy.service.SAPEntryService;
import com.liferay.service.access.policy.web.constants.SAPPortletKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.lang.reflect.Method;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	property = {
		"com.liferay.portlet.css-class-wrapper=service-access-policy-portlet",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.instanceable=false",
		"javax.portlet.display-name=Service Access Policy",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.info.keywords=Service Access Policy",
		"javax.portlet.info.short-title=Service Access Policy",
		"javax.portlet.info.title=Service Access Policy",
		"javax.portlet.init-param.clear-request-parameters=true",
		"javax.portlet.init-param.copy-request-parameters=true",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + SAPPortletKeys.SERVICE_ACCESS_POLICY,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class SAPPortlet extends MVCPortlet {

	public void deleteSAPEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long sapEntryId = ParamUtil.getLong(actionRequest, "sapEntryId");

		_sapEntryService.deleteSAPEntry(sapEntryId);
	}

	public void getMethods(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		String contextName = ParamUtil.get(
			resourceRequest, "context", StringPool.BLANK);
		String serviceClass = ParamUtil.get(
			resourceRequest, "serviceClass", StringPool.BLANK);

		Map<String, Set> jsonWebServiceClasses = getJsonWebServiceClasses(
			contextName);

		Set<JSONWebServiceActionMapping> jsonWebServiceActionMappings =
			jsonWebServiceClasses.get(serviceClass);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (JSONWebServiceActionMapping jsonWebServiceActionMapping :
				jsonWebServiceActionMappings) {

			Method method = jsonWebServiceActionMapping.getActionMethod();

			jsonArray.put(method.getName());
		}

		PrintWriter writer = resourceResponse.getWriter();

		writer.write(jsonArray.toString());
	}

	public void getServices(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		Set<String> contextNames =
			_jsonWebServiceActionsManager.getContextNames();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (String contextName : contextNames) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonArray.put(jsonObject);

			jsonObject.put("context", contextName);

			JSONArray jsonArrayServices = JSONFactoryUtil.createJSONArray();

			jsonObject.put("serviceClasses", jsonArrayServices);

			Map<String, Set> jsonWebServiceClasses = getJsonWebServiceClasses(
				contextName);

			for (String className : jsonWebServiceClasses.keySet()) {
				jsonArrayServices.put(className);
			}
		}

		PrintWriter writer = resourceResponse.getWriter();

		writer.write(jsonArray.toString());
	}

	public void updateSACPEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long sapEntryId = ParamUtil.getLong(actionRequest, "sapEntryId");

		String allowedServiceSignatures = ParamUtil.getString(
			actionRequest, "allowedServiceSignatures");
		boolean defaultSAPEntry = ParamUtil.getBoolean(
			actionRequest, "defaultSAPEntry");
		boolean enabled = ParamUtil.getBoolean(actionRequest, "enabled");
		String name = ParamUtil.getString(actionRequest, "name");
		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (sapEntryId > 0) {
			_sapEntryService.updateSAPEntry(
				sapEntryId, allowedServiceSignatures, defaultSAPEntry, enabled,
				name, titleMap, serviceContext);
		}
		else {
			_sapEntryService.addSAPEntry(
				allowedServiceSignatures, defaultSAPEntry, enabled, name,
				titleMap, serviceContext);
		}
	}

	protected Map<String, Set> getJsonWebServiceClasses(String contextName) {
		Map<String, Set> jsonWebServiceClasses = new LinkedHashMap<>();

		List<JSONWebServiceActionMapping> jsonWebServiceActionMappings =
			_jsonWebServiceActionsManager.getJSONWebServiceActionMappings(
				contextName);

		for (JSONWebServiceActionMapping jsonWebServiceActionMapping :
				jsonWebServiceActionMappings) {

			Class<?> actionClass = jsonWebServiceActionMapping.getActionClass();

			String className = actionClass.getName();

			className = StringUtil.replace(className, ".impl.", ".");

			if (className.endsWith("Impl")) {
				className = className.substring(0, className.length() - 4);
			}

			Set<JSONWebServiceActionMapping> jsonWebServiceMappings =
				jsonWebServiceClasses.get(className);

			if (jsonWebServiceMappings == null) {
				jsonWebServiceMappings = new LinkedHashSet<>();

				jsonWebServiceClasses.put(className, jsonWebServiceMappings);
			}

			jsonWebServiceMappings.add(jsonWebServiceActionMapping);
		}

		return jsonWebServiceClasses;
	}

	@Reference(unbind = "-")
	protected void setJSONWebServiceActionsManager(
		JSONWebServiceActionsManager jsonWebServiceActionsManager) {

		_jsonWebServiceActionsManager = jsonWebServiceActionsManager;
	}

	@Reference(unbind = "-")
	protected void setSAPEntryService(SAPEntryService sapEntryService) {
		_sapEntryService = sapEntryService;
	}

	private JSONWebServiceActionsManager _jsonWebServiceActionsManager;
	private SACPEntryService _sacpEntryService;

}