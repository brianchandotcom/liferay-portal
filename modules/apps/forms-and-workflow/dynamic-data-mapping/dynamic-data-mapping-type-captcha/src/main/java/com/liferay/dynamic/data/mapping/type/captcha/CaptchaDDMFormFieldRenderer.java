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

package com.liferay.dynamic.data.mapping.type.captcha;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.servlet.JSPSupportServlet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.taglib.servlet.PipingPageContext;
import com.liferay.taglib.ui.CaptchaTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=captcha",
	service = DDMFormFieldRenderer.class
)
public class CaptchaDDMFormFieldRenderer implements DDMFormFieldRenderer {

	@Override
	public String render(
			DDMFormField ddmFormField,
			DDMFormFieldRenderingContext ddmFormFieldRenderingContext)
		throws PortalException {

		try {
			return renderCaptchaTag(ddmFormField, ddmFormFieldRenderingContext);
		}
		catch (Exception e) {
			throw new PortalException("Unable to render capctha field", e);
		}
	}

	protected String renderCaptchaTag(
			DDMFormField ddmFormField,
			DDMFormFieldRenderingContext ddmFormFieldRenderingContext)
		throws Exception {

		CaptchaTag captchaTag = new CaptchaTag();

		captchaTag.setUrl(
			GetterUtil.getString(ddmFormField.getProperty("url")));

		JspFactory jspFactory = JspFactory.getDefaultFactory();

		HttpServletRequest httpServletRequest =
			ddmFormFieldRenderingContext.getHttpServletRequest();
		HttpServletResponse httpServletResponse =
			ddmFormFieldRenderingContext.getHttpServletResponse();

		PageContext pageContext = jspFactory.getPageContext(
			new JSPSupportServlet(httpServletRequest.getServletContext()),
			httpServletRequest, httpServletResponse, null, false, 0, false);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		unsyncStringWriter.append(
			"<div class=\"form-group\" data-fieldname=\"");
		unsyncStringWriter.append(ddmFormFieldRenderingContext.getName());
		unsyncStringWriter.append("\">");

		captchaTag.setPageContext(
			new PipingPageContext(pageContext, unsyncStringWriter));

		captchaTag.runTag();

		unsyncStringWriter.append("</div>");

		StringBundler sb = unsyncStringWriter.getStringBundler();

		return sb.toString();
	}

}