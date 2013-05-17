/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoaderUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.StrutsUtil;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.MimeResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.velocity.io.VelocityWriter;
import org.apache.velocity.util.SimplePool;

/**
 * @author Brian Wing Shun Chan
 * @author Steven P. Goldsmith
 * @author Raymond Augé
 */
public class VelocityPortlet extends GenericPortlet {

	@Override
	public void doEdit(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (renderRequest.getPreferences() == null) {
			super.doEdit(renderRequest, renderResponse);

			return;
		}

		try {
			mergeTemplate(_editTemplateId, renderRequest, renderResponse);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	@Override
	public void doHelp(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			mergeTemplate(_helpTemplateId, renderRequest, renderResponse);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			mergeTemplate(_viewTemplateId, renderRequest, renderResponse);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	@Override
	public void init(PortletConfig portletConfig) throws PortletException {
		super.init(portletConfig);

		PortletContext portletContext = portletConfig.getPortletContext();

		_portletContextName = portletContext.getPortletContextName();

		_actionTemplateId = getTemplateId(getInitParameter("action-template"));
		_editTemplateId = getTemplateId(getInitParameter("edit-template"));
		_helpTemplateId = getTemplateId(getInitParameter("help-template"));
		_resourceTemplateId = getTemplateId(
			getInitParameter("resource-template"));
		_viewTemplateId = getTemplateId(getInitParameter("view-template"));
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		if (Validator.isNull(_actionTemplateId)) {
			return;
		}

		try {
			mergeTemplate(_actionTemplateId, actionRequest, actionResponse);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		if (Validator.isNull(_resourceTemplateId)) {
			super.serveResource(resourceRequest, resourceResponse);

			return;
		}

		try {
			mergeTemplate(
				_resourceTemplateId, resourceRequest, resourceResponse);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	protected String getTemplateId(String name) {
		if (Validator.isNull(name)) {
			return name;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_portletContextName);
		sb.append(TemplateConstants.SERVLET_SEPARATOR);
		sb.append(StrutsUtil.TEXT_HTML_DIR);
		sb.append(name);

		return sb.toString();
	}

	protected void mergeTemplate(
			String templateId, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws Exception {

		TemplateResource templateResource =
			TemplateResourceLoaderUtil.getTemplateResource(
				TemplateConstants.LANG_TYPE_VM, templateId);

		if (templateResource == null) {
			throw new Exception(
				"Unable to load template resource " + templateId);
		}

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_VM, templateResource, false);

		prepareTemplate(template, portletRequest, portletResponse);

		mergeTemplate(templateId, template, portletRequest, portletResponse);
	}

	protected void mergeTemplate(
			String templateId, Template template, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws Exception {

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse)portletResponse;

			mimeResponse.setContentType(
				portletRequest.getResponseContentType());
		}

		VelocityWriter velocityWriter = null;

		try {
			velocityWriter = (VelocityWriter)_writerPool.get();

			PrintWriter output = null;

			if (portletResponse instanceof MimeResponse) {
				MimeResponse mimeResponse = (MimeResponse)portletResponse;

				output = mimeResponse.getWriter();
			}
			else {
				output = UnsyncPrintWriterPool.borrow(System.out);
			}

			if (velocityWriter == null) {
				velocityWriter = new VelocityWriter(output, 4 * 1024, true);
			}
			else {
				velocityWriter.recycle(output);
			}

			template.processTemplate(velocityWriter);
		}
		finally {
			try {
				if (velocityWriter != null) {
					velocityWriter.flush();
					velocityWriter.recycle(null);

					_writerPool.put(velocityWriter);
				}
			}
			catch (Exception e) {
			}
		}
	}

	protected void prepareTemplate(
		Template template, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		template.put("portletConfig", getPortletConfig());
		template.put("portletContext", getPortletContext());
		template.put("preferences", portletRequest.getPreferences());
		template.put(
			"userInfo", portletRequest.getAttribute(PortletRequest.USER_INFO));

		template.put("portletRequest", portletRequest);

		if (portletRequest instanceof ActionRequest) {
			template.put("actionRequest", portletRequest);
		}
		else if (portletRequest instanceof RenderRequest) {
			template.put("renderRequest", portletRequest);
		}
		else {
			template.put("resourceRequest", portletRequest);
		}

		template.put("portletResponse", portletResponse);

		if (portletResponse instanceof ActionResponse) {
			template.put("actionResponse", portletResponse);
		}
		else if (portletRequest instanceof RenderResponse) {
			template.put("renderResponse", portletResponse);
		}
		else {
			template.put("resourceResponse", portletResponse);
		}
	}

	private static SimplePool _writerPool = new SimplePool(40);

	private String _actionTemplateId;
	private String _editTemplateId;
	private String _helpTemplateId;
	private String _portletContextName;
	private String _resourceTemplateId;
	private String _viewTemplateId;

}