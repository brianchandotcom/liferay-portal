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

package com.liferay.mail.web.portlet;

import com.liferay.mail.attachment.AttachmentHandler;
import com.liferay.mail.constants.MailPortletKeys;
import com.liferay.mail.model.Attachment;
import com.liferay.mail.service.AttachmentLocalServiceUtil;
import com.liferay.mail.web.util.MailManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Scott Lee
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=mail-portlet",
		"com.liferay.portlet.display-category=category.collaboration",
		"com.liferay.portlet.footer-portlet-javascript=/js/main.js",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/mail.png",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Mail",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + MailPortletKeys.MAIL,
		"javax.portlet.portlet-info.keywords=Mail",
		"javax.portlet.portlet-info.short-title=Mail",
		"javax.portlet.portlet-info.title=Mail",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class MailPortlet extends MVCPortlet {

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		String mvcPath = resourceRequest.getParameter("mvcPath");

		if (mvcPath.equals("/attachment.jsp")) {
			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				resourceRequest);

			long attachmentId = ParamUtil.getLong(
				resourceRequest, "attachmentId");

			AttachmentHandler attachmentHandler = null;

			try {
				MailManager mailManager = MailManager.getInstance(request);

				Attachment attachment =
					AttachmentLocalServiceUtil.getAttachment(attachmentId);

				attachmentHandler = mailManager.getAttachment(attachmentId);

				if (attachmentHandler != null) {
					String contentType = MimeTypesUtil.getContentType(
						attachment.getFileName());

					PortletResponseUtil.sendFile(
						resourceRequest, resourceResponse,
						attachment.getFileName(),
						attachmentHandler.getInputStream(), contentType);
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
			finally {
				attachmentHandler.cleanUp();
			}
		}
		else {
			super.serveResource(resourceRequest, resourceResponse);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(MailPortlet.class);

}