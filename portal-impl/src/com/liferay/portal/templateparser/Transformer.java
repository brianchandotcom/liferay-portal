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

package com.liferay.portal.templateparser;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.kernel.mobile.device.UnknownDevice;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.templateparser.TransformException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Wesley Gong
 * @author Angelo Jefferson
 * @author Hugo Huijser
 * @author Marcellus Tavares
 * @author Juan Fernández
 */
public class Transformer {

	public Transformer(String errorTemplatePropertyKey, boolean restricted) {
		_restricted = restricted;

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		for (String langType : TemplateManagerUtil.getTemplateManagerNames()) {
			String errorTemplateId = getErrorTemplateId(
				errorTemplatePropertyKey, langType);

			if (Validator.isNotNull(errorTemplateId)) {
				URL url = classLoader.getResource(errorTemplateId);

				if (url != null) {
					_errorTemplateResources.put(
						langType,
						new URLTemplateResource(errorTemplateId, url));
				}
			}
		}
	}

	public Transformer(
		String transformerListenerPropertyKey, String errorTemplatePropertyKey,
		boolean restricted) {

		this(errorTemplatePropertyKey, restricted);
	}

	public String transform(
			ThemeDisplay themeDisplay, Map<String, Object> contextObjects,
			String script, String langType,
			UnsyncStringWriter unsyncStringWriter)
		throws Exception {

		if (Validator.isNull(langType)) {
			return null;
		}

		long companyId = 0;
		long companyGroupId = 0;
		long scopeGroupId = 0;
		long siteGroupId = 0;

		if (themeDisplay != null) {
			companyId = themeDisplay.getCompanyId();
			companyGroupId = themeDisplay.getCompanyGroupId();
			scopeGroupId = themeDisplay.getScopeGroupId();
			siteGroupId = themeDisplay.getSiteGroupId();
		}

		String templateId = String.valueOf(contextObjects.get("template_id"));

		templateId = getTemplateId(
			templateId, companyId, companyGroupId, scopeGroupId);

		Template template = getTemplate(templateId, script, langType);

		try {
			prepareTemplate(themeDisplay, template);

			template.putAll(contextObjects);

			long classNameId = GetterUtil.getLong(
				contextObjects.get(TemplateConstants.CLASS_NAME_ID));

			template.put("company", getCompany(themeDisplay, companyId));
			template.put("companyId", companyId);
			template.put("device", getDevice(themeDisplay));

			String templatesPath = getTemplatesPath(
				companyId, scopeGroupId, classNameId);

			template.put(
				"permissionChecker",
				PermissionThreadLocal.getPermissionChecker());
			template.put(
				"randomNamespace",
				StringUtil.randomId() + StringPool.UNDERLINE);
			template.put("scopeGroupId", scopeGroupId);
			template.put("siteGroupId", siteGroupId);
			template.put("templatesPath", templatesPath);

			// Deprecated variables

			template.put("groupId", scopeGroupId);
			template.put("journalTemplatesPath", templatesPath);

			template.processTemplate(unsyncStringWriter);
		}
		catch (Exception e) {
			throw new TransformException("Unhandled exception", e);
		}

		return unsyncStringWriter.toString();
	}

	protected Company getCompany(ThemeDisplay themeDisplay, long companyId)
		throws Exception {

		if (themeDisplay != null) {
			return themeDisplay.getCompany();
		}

		return CompanyLocalServiceUtil.getCompany(companyId);
	}

	protected Device getDevice(ThemeDisplay themeDisplay) {
		if (themeDisplay != null) {
			return themeDisplay.getDevice();
		}

		return UnknownDevice.getInstance();
	}

	protected String getErrorTemplateId(
		String errorTemplatePropertyKey, String langType) {

		return PropsUtil.get(errorTemplatePropertyKey, new Filter(langType));
	}

	protected TemplateResource getErrorTemplateResource(String langType) {
		return _errorTemplateResources.get(langType);
	}

	protected Template getTemplate(
			String templateId, String script, String langType)
		throws Exception {

		TemplateResource templateResource = new StringTemplateResource(
			templateId, script);

		TemplateResource errorTemplateResource = getErrorTemplateResource(
			langType);

		return TemplateManagerUtil.getTemplate(
			langType, templateResource, errorTemplateResource, _restricted);
	}

	protected String getTemplateId(
		String templateId, long companyId, long companyGroupId, long groupId) {

		StringBundler sb = new StringBundler(5);

		sb.append(companyId);
		sb.append(StringPool.POUND);

		if (companyGroupId > 0) {
			sb.append(companyGroupId);
		}
		else {
			sb.append(groupId);
		}

		sb.append(StringPool.POUND);
		sb.append(templateId);

		return sb.toString();
	}

	protected String getTemplatesPath(
		long companyId, long groupId, long classNameId) {

		StringBundler sb = new StringBundler(7);

		sb.append(TemplateConstants.TEMPLATE_SEPARATOR);
		sb.append(StringPool.SLASH);
		sb.append(companyId);
		sb.append(StringPool.SLASH);
		sb.append(groupId);
		sb.append(StringPool.SLASH);
		sb.append(classNameId);

		return sb.toString();
	}

	protected void prepareTemplate(ThemeDisplay themeDisplay, Template template)
		throws Exception {

		if (themeDisplay == null) {
			return;
		}

		template.prepare(themeDisplay.getRequest());
	}

	private final Map<String, TemplateResource> _errorTemplateResources =
		new HashMap<>();
	private final boolean _restricted;

}