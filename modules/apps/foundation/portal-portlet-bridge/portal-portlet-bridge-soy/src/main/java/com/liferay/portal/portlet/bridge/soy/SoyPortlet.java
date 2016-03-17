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

package com.liferay.portal.portlet.bridge.soy;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.portlet.bridge.soy.internal.SoyPortletControllersManager;
import com.liferay.portal.portlet.bridge.soy.internal.SoyTemplateResourcesCollector;

import java.io.IOException;
import java.io.Writer;

import java.net.URL;

import java.util.Map;

import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Miroslav Ligas
 * @author Bruno Basto
 */
public class SoyPortlet extends MVCPortlet {

	@Override
	public void init() throws PortletException {
		super.init();

		Bundle bundle = _getBundle();

		_controllersManager = new SoyPortletControllersManager(bundle);
		moduleName = _getModuleName();

		try {
			template = _getTemplate();
		}
		catch (TemplateException te) {
			throw new PortletException(te);
		}
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(WebKeys.TEMPLATE, template);

		super.render(renderRequest, renderResponse);
	}

	protected String getControllerName(String path) {
		return _controllersManager.getController(path);
	}

	protected String getPortletBoundaryId(PortletResponse portletResponse) {
		return "#p_p_id".concat(
			HtmlUtil.escapeJS(portletResponse.getNamespace()));
	}

	protected String getTemplateNamespaceFromPath(String path) {
		return "Templates.".concat(path).concat(".render");
	}

	@Override
	protected void include(
			String namespace, PortletRequest portletRequest,
			PortletResponse portletResponse, String lifecycle)
		throws IOException, PortletException {

		try {
			String path = getPath(portletRequest, portletResponse);

			if (Validator.isNull(path)) {
				path = namespace;
			}

			template.put(
				TemplateConstants.NAMESPACE,
				getTemplateNamespaceFromPath(path));

			injectRequestParameters(portletRequest);

			Writer writer = null;

			if (portletResponse instanceof MimeResponse) {
				MimeResponse mimeResponse = (MimeResponse)portletResponse;

				writer = UnsyncPrintWriterPool.borrow(mimeResponse.getWriter());
			}
			else {
				writer = new UnsyncStringWriter();
			}

			template.processTemplate(writer);

			writePortletComponent(portletResponse, writer, path);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		if (clearRequestParameters) {
			if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
				portletResponse.setProperty("clear-request-parameters", "true");
			}
		}
	}

	protected void writePortletComponent(
		PortletResponse portletResponse, Writer writer, String path) {

		JSONObject contextJSONObject = JSONFactoryUtil.createJSONObject();

		for (String key : template.getKeys()) {
			if (Validator.equals(key, TemplateConstants.NAMESPACE)) {
				continue;
			}

			contextJSONObject.put(key, template.get(key));
		}

		String portletBoundaryId = getPortletBoundaryId(portletResponse);

		contextJSONObject.put(
			"element", portletBoundaryId.concat(" .portlet-content-container"));

		StringBundler sb = new StringBundler(14);

		String portletNamespace = portletResponse.getNamespace();

		sb.append("<script>require('");
		sb.append(moduleName);
		sb.append(StringPool.SLASH);
		sb.append(getControllerName(path));
		sb.append("',function(Portlet){");
		sb.append("Liferay.component('");
		sb.append(portletNamespace);
		sb.append("', new Portlet.default(");
		sb.append(contextJSONObject.toJSONString());
		sb.append(").render());");
		sb.append(
			"Liferay.once('beforeScreenFlip',function(){Liferay.component('");
		sb.append(portletNamespace);
		sb.append("').dispose();});");
		sb.append("});</script>");

		try {
			writer.write(sb.toString());
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	protected String moduleName;
	protected Template template;

	private Bundle _getBundle() {
		return FrameworkUtil.getBundle(this.getClass());
	}

	private String _getModuleName() {
		Bundle bundle = _getBundle();

		String moduleName = StringPool.BLANK;

		try {
			URL url = bundle.getEntry("package.json");

			byte[] byteArray = FileUtil.getBytes(url.openStream());

			String moduleJSON = new String(byteArray);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				moduleJSON);

			moduleName = jsonObject.getString("name");
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		catch (JSONException jsone) {
			jsone.printStackTrace();
		}

		return moduleName;
	}

	private Template _getTemplate() throws TemplateException {
		Bundle bundle = _getBundle();

		SoyTemplateResourcesCollector soyTemplateResourcesCollector =
			new SoyTemplateResourcesCollector(bundle, templatePath);

		return TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_SOY,
			soyTemplateResourcesCollector.getTemplateResources(), false);
	}

	private void injectRequestParameters(PortletRequest portletRequest) {
		Map<String, String[]> parametersMap = portletRequest.getParameterMap();

		for (String parameterName : parametersMap.keySet()) {
			String[] parameterValues = parametersMap.get(parameterName);

			if (parameterValues.length == 1) {
				template.put(parameterName, parameterValues[0]);
			}
			else if (parameterValues.length > 1) {
				template.put(parameterName, parameterValues);
			}
		}
	}

	private SoyPortletControllersManager _controllersManager;

}