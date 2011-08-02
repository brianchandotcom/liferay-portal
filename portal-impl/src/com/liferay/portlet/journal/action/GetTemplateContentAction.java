/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.action;

import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;
import com.liferay.portlet.journal.model.JournalTemplateConstants;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.util.JS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class GetTemplateContentAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			String xslContent = JS.decodeURIComponent(
				ParamUtil.getString(request, "xslContent"));
			boolean formatXsl = ParamUtil.getBoolean(request, "formatXsl");
			String langType = ParamUtil.getString(
				request, "langType", JournalTemplateConstants.LANG_TYPE_XSL);

			if (formatXsl) {
				if (langType.equals(JournalTemplateConstants.LANG_TYPE_VM)) {
					xslContent = JournalUtil.formatVM(xslContent);
				}
				else {
					xslContent = DDMXMLUtil.formatXML(xslContent);
				}
			}

			String fileName = "template." + langType;
			byte[] bytes = xslContent.getBytes();

			ServletResponseUtil.sendFile(
				request, response, fileName, bytes, ContentTypes.TEXT_XML_UTF8);

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

}