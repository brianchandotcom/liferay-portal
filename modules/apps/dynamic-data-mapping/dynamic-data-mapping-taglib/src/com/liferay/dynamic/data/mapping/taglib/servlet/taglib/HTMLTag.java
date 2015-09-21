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
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.taglib.servlet.ServletContextUtil;
import com.liferay.dynamic.data.mapping.taglib.servlet.taglib.base.BaseHTMLTag;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverterUtil;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Bruno Basto
 */
public class HTMLTag extends BaseHTMLTag {

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

	protected String getDDMFormValuesInputName() {
		String fieldsNamespace = GetterUtil.getString(getFieldsNamespace());

		return fieldsNamespace + "ddmFormValues";
	}

	protected Fields getFields() {
		try {
			long ddmStructureId = getClassPK();

			if (getClassNameId() ==
					PortalUtil.getClassNameId(DDMTemplate.class)) {

				DDMTemplate ddmTemplate =
					DDMTemplateLocalServiceUtil.getTemplate(getClassPK());

				ddmStructureId = ddmTemplate.getClassPK();
			}

			DDMStructure ddmStructure = DDMStructureServiceUtil.getStructure(
				ddmStructureId);

			if (getDdmFormValues() != null) {
				return DDMFormValuesToFieldsConverterUtil.convert(
					ddmStructure, getDdmFormValues());
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

	protected String getRandomNamespace() {
		return PortalUtil.generateRandomKey(request, "taglib_ddm_init-ext");
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		setNamespacedAttribute(request, "ddmForm", getDDMForm());
		setNamespacedAttribute(
			request, "ddmFormValuesInputName", getDDMFormValuesInputName());
		setNamespacedAttribute(request, "fields", getFields());
		setNamespacedAttribute(request, "mode", getMode());
		setNamespacedAttribute(
			request, "randomNamespace", getRandomNamespace());
	}

	private static final Log _log = LogFactoryUtil.getLog(HTMLTag.class);

}