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

package com.liferay.dynamic.data.mapping.taglib.servlet.taglib;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.taglib.internal.servlet.ServletContextUtil;
import com.liferay.dynamic.data.mapping.taglib.servlet.taglib.base.BaseHTMLTag;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * @author Bruno Basto
 */
public class HTMLTag extends BaseHTMLTag {

	@Override
	public int doStartTag() throws JspException {
		if (!getIgnoreRequestValue()) {
			DDMFormValues ddmFormValues = getDDMFormValuesFromRequest();

			if (ddmFormValues != null) {
				setDdmFormValues(ddmFormValues);
			}
		}

		return super.doStartTag();
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	protected DDMForm getDDMForm() {
		try {
			return DDMUtil.getDDMForm(getClassNameId(), getClassPK());
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(getLogMessage(), pe);
			}
		}

		return null;
	}

	protected DDMFormValues getDDMFormValuesFromRequest() {
		String serializedDDMFormValues = ParamUtil.getString(
			request, getDDMFormValuesInputName());

		if (Validator.isNotNull(serializedDDMFormValues)) {
			DDMForm ddmForm = getDDMForm();

			try {
				return DDMUtil.getDDMFormValues(
					ddmForm, serializedDDMFormValues);
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}
			}
		}

		return null;
	}

	protected String getDDMFormValuesInputName() {
		String fieldsNamespace = GetterUtil.getString(getFieldsNamespace());

		return fieldsNamespace + "ddmFormValues";
	}

	protected Fields getFields() {
		try {
			long ddmStructureId = getClassPK();

			if (getClassNameId() == PortalUtil.getClassNameId(
					DDMTemplate.class)) {

				DDMTemplate ddmTemplate =
					DDMTemplateLocalServiceUtil.getTemplate(getClassPK());

				ddmStructureId = ddmTemplate.getClassPK();
			}

			if (getDdmFormValues() != null) {
				return DDMUtil.getFields(ddmStructureId, getDdmFormValues());
			}
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(getLogMessage(), pe);
			}
		}

		return null;
	}

	protected String getLogMessage() {
		if (getClassNameId() == PortalUtil.getClassNameId(DDMTemplate.class)) {
			return "Unable to retrieve DDM template with class PK " +
				getClassPK();
		}

		return "Unable to retrieve DDM structure with class PK " + getClassPK();
	}

	protected String getMode() {
		if (getClassNameId() != PortalUtil.getClassNameId(DDMTemplate.class)) {
			return null;
		}

		try {
			DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(
				getClassPK());

			return ddmTemplate.getMode();
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(getLogMessage(), pe);
			}
		}

		return null;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected String getRandomNamespace() {
		return PortalUtil.generateRandomKey(request, "taglib_ddm_init-ext");
	}

	@Override
	protected int processEndTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("</div>");

		return EVAL_PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		super.setAttributes(httpServletRequest);

		setNamespacedAttribute(httpServletRequest, "ddmForm", getDDMForm());
		setNamespacedAttribute(
			httpServletRequest, "ddmFormValuesInputName",
			getDDMFormValuesInputName());
		setNamespacedAttribute(httpServletRequest, "fields", getFields());

		if (getGroupId() <= 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			setNamespacedAttribute(
				httpServletRequest, "groupId",
				String.valueOf(themeDisplay.getSiteGroupId()));
		}

		setNamespacedAttribute(httpServletRequest, "mode", getMode());
		setNamespacedAttribute(
			httpServletRequest, "randomNamespace", getRandomNamespace());
	}

	private static final Log _log = LogFactoryUtil.getLog(HTMLTag.class);

}