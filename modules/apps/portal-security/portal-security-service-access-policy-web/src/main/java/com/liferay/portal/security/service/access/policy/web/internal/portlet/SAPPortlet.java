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

package com.liferay.portal.security.service.access.policy.web.internal.portlet;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionMapping;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManager;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.service.access.policy.service.SAPEntryService;
import com.liferay.portal.security.service.access.policy.web.internal.constants.SAPPortletKeys;
import com.liferay.portal.security.service.access.policy.web.internal.constants.SAPWebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	property = {
		"com.liferay.portlet.css-class-wrapper=portal-security-service-access-policy-portlet",
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
		"javax.portlet.version=3.0"
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

	public void getActionMethodNames(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		PrintWriter printWriter = resourceResponse.getWriter();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		String contextName = ParamUtil.getString(
			resourceRequest, "contextName");

		Map<String, Set<JSONWebServiceActionMapping>>
			jsonWebServiceActionMappingsMap =
				_getServiceJSONWebServiceActionMappingsMap(contextName);

		String serviceClassName = ParamUtil.getString(
			resourceRequest, "serviceClassName");

		Set<JSONWebServiceActionMapping> jsonWebServiceActionMappingsSet =
			jsonWebServiceActionMappingsMap.get(serviceClassName);

		for (JSONWebServiceActionMapping jsonWebServiceActionMapping :
				jsonWebServiceActionMappingsSet) {

			JSONObject jsonObject = JSONUtil.put(
				"actionMethodName",
				() -> {
					Method method =
						jsonWebServiceActionMapping.getActionMethod();

					return method.getName();
				});

			jsonArray.put(jsonObject);
		}

		printWriter.write(jsonArray.toString());
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		String mvcPath = ParamUtil.getString(renderRequest, "mvcPath");

		if (mvcPath.equals("/edit_entry.jsp")) {
			renderRequest.setAttribute(
				SAPWebKeys.SERVICE_CLASS_NAMES_TO_CONTEXT_NAMES,
				_getServiceClassNamesToContextNamesJSONArray());
		}

		super.render(renderRequest, renderResponse);
	}

	public void updateSAPEntry(
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

	@Reference(unbind = "-")
	protected void setJSONWebServiceActionsManager(
		JSONWebServiceActionsManager jsonWebServiceActionsManager) {

		_jsonWebServiceActionsManager = jsonWebServiceActionsManager;
	}

	@Reference(unbind = "-")
	protected void setSAPEntryService(SAPEntryService sapEntryService) {
		_sapEntryService = sapEntryService;
	}

	private JSONArray _getServiceClassNamesToContextNamesJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		Set<String> contextNames =
			_jsonWebServiceActionsManager.getContextNames();

		for (String contextName : contextNames) {
			Map<String, Set<JSONWebServiceActionMapping>>
				jsonWebServiceActionMappingsMap =
					_getServiceJSONWebServiceActionMappingsMap(contextName);

			for (Map.Entry<String, Set<JSONWebServiceActionMapping>> entry :
					jsonWebServiceActionMappingsMap.entrySet()) {

				jsonArray.put(
					JSONUtil.put(
						"contextName",
						() -> {
							Set<JSONWebServiceActionMapping>
								jsonWebServiceActionMappingsSet =
									entry.getValue();

							Iterator<JSONWebServiceActionMapping> iterator =
								jsonWebServiceActionMappingsSet.iterator();

							JSONWebServiceActionMapping
								firstJSONWebServiceActionMapping =
									iterator.next();

							return firstJSONWebServiceActionMapping.
								getContextName();
						}
					).put(
						"serviceClassName", entry.getKey()
					));
			}
		}

		return jsonArray;
	}

	private Map<String, Set<JSONWebServiceActionMapping>>
		_getServiceJSONWebServiceActionMappingsMap(String contextName) {

		Map<String, Set<JSONWebServiceActionMapping>>
			jsonWebServiceActionMappingsMap = new LinkedHashMap<>();

		List<JSONWebServiceActionMapping> jsonWebServiceActionMappings =
			_jsonWebServiceActionsManager.getJSONWebServiceActionMappings(
				contextName);

		for (JSONWebServiceActionMapping jsonWebServiceActionMapping :
				jsonWebServiceActionMappings) {

			Object actionObject = jsonWebServiceActionMapping.getActionObject();

			Class<?> serviceClass = actionObject.getClass();

			Class<?>[] serviceInterfaces = serviceClass.getInterfaces();

			for (Class<?> serviceInterface : serviceInterfaces) {
				Annotation[] declaredAnnotations =
					serviceInterface.getDeclaredAnnotations();

				for (Annotation declaredAnnotation : declaredAnnotations) {
					if (!(declaredAnnotation instanceof AccessControlled)) {
						continue;
					}

					String serviceClassName = serviceInterface.getName();

					Set<JSONWebServiceActionMapping>
						jsonWebServiceActionMappingsSet =
							jsonWebServiceActionMappingsMap.get(
								serviceClassName);

					if (jsonWebServiceActionMappingsSet == null) {
						jsonWebServiceActionMappingsSet = new LinkedHashSet<>();

						jsonWebServiceActionMappingsMap.put(
							serviceClassName, jsonWebServiceActionMappingsSet);
					}

					jsonWebServiceActionMappingsSet.add(
						jsonWebServiceActionMapping);
				}
			}
		}

		return jsonWebServiceActionMappingsMap;
	}

	private JSONWebServiceActionsManager _jsonWebServiceActionsManager;
	private SAPEntryService _sapEntryService;

}