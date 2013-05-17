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

package com.liferay.portal.bean;

import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.BeanLocatorException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.bean.Renderer;
import com.liferay.portal.kernel.bean.RendererException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.lang.reflect.Method;

import java.net.URL;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 */
@DoPrivileged
public class RendererImpl implements Renderer {

	public String renderBean(
			HttpServletRequest request, HttpServletResponse response,
			Object bean)
		throws RendererException {

		return renderBean(request, response, null, bean, null);
	}

	public String renderBean(
			HttpServletRequest request, HttpServletResponse response,
			Object bean, String varientSuffix)
		throws RendererException {

		return renderBean(request, response, null, bean, varientSuffix);
	}

	public String renderBean(
			HttpServletRequest request, HttpServletResponse response,
			String servletContextName, Object bean)
		throws RendererException {

		return renderBean(request, response, servletContextName, bean, null);
	}

	public String renderBean(
			HttpServletRequest request, HttpServletResponse response,
			String servletContextName, Object bean, String varientSuffix)
		throws RendererException {

		if (bean == null) {
			return null;
		}

		long companyId = PortalUtil.getCompanyId(request);

		String className = normalizeClassName(bean.getClass().getName());

		if (Validator.isNotNull(varientSuffix)) {
			className = varientSuffix;
		}

		TemplateResource templateResource = null;

		PortletPreferences preferences = getPortletPreferences(request);

		if (preferences != null) {
			String velocityTemplateContent = preferences.getValue(
				RENDERER_TEMPLATE_PREFIX + className, StringPool.BLANK);

			if (Validator.isNotNull(velocityTemplateContent)) {
				templateResource = new StringTemplateResource(
					className, velocityTemplateContent);
			}
		}

		if ((templateResource == null) &&
			Validator.isNotNull(servletContextName)) {

			if (servletContextName.startsWith(StringPool.SLASH)) {
				servletContextName = servletContextName.substring(1);
			}

			try {
				BeanLocator beanLocator = PortletBeanLocatorUtil.getBeanLocator(
					servletContextName);

				ClassLoader classLoader = beanLocator.getClassLoader();

				URL url = classLoader.getResource(
					PropsUtil.get(RENDERER_TEMPLATE_PREFIX + className));

				templateResource = new URLTemplateResource(className, url);
			}
			catch (Exception e) {
			}
		}

		if (templateResource == null) {
			try {
				String velocityTemplateContent = PrefsPropsUtil.getContent(
					companyId, RENDERER_TEMPLATE_PREFIX + className);

				if (Validator.isNotNull(velocityTemplateContent)) {
					templateResource = new StringTemplateResource(
						className, velocityTemplateContent);
				}
			}
			catch (Exception e) {
			}
		}

		if (templateResource == null) {
			_log.warn("No entity renderer template found for " + className);

			return null;
		}

		try {
			Template template = TemplateManagerUtil.getTemplate(
				TemplateConstants.LANG_TYPE_VM, templateResource, false);

			template.prepare(request);

			template.put(_BEAN, bean);

			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

			template.processTemplate(unsyncStringWriter);

			return unsyncStringWriter.toString();
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RendererException(e);
		}
	}

	public String renderBean(
			PortletRequest portletRequest, PortletResponse portletResponse,
			Object bean)
		throws RendererException {

		return renderBean(portletRequest, portletResponse, null, bean, null);
	}

	public String renderBean(
			PortletRequest portletRequest, PortletResponse portletResponse,
			Object bean, String varientSuffix)
		throws RendererException {

		return renderBean(
			portletRequest, portletResponse, null, bean, varientSuffix);
	}

	public String renderBean(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String servletContextName, Object bean)
		throws RendererException {

		return renderBean(
			portletRequest, portletResponse, servletContextName, bean, null);
	}

	public String renderBean(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String servletContextName, Object bean, String varientSuffix)
		throws RendererException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			portletResponse);

		return renderBean(
			request, response, servletContextName, bean, varientSuffix);
	}

	public String renderEntity(
			HttpServletRequest request, HttpServletResponse response,
			String className, Object classPK)
		throws RendererException {

		return renderEntity(request, response, null, className, classPK, null);
	}

	public String renderEntity(
			HttpServletRequest request, HttpServletResponse response,
			String className, Object classPK, String varientSuffix)
		throws RendererException {

		return renderEntity(
			request, response, null, className, classPK, varientSuffix);
	}

	public String renderEntity(
			HttpServletRequest request, HttpServletResponse response,
			String servletContextName, String className, Object classPK)
		throws RendererException {

		return renderEntity(
			request, response, servletContextName, className, classPK, null);
	}

	public String renderEntity(
			HttpServletRequest request, HttpServletResponse response,
			String servletContextName, String className, Object classPK,
			String varientSuffix)
		throws RendererException {

		if (Validator.isNull(className)) {
			return null;
		}

		className = normalizeClassName(className);

		String[] beanNameParts = StringUtil.split(className, _MODEL);

		Object serviceBean = null;

		if (Validator.isNotNull(servletContextName)) {
			if (servletContextName.startsWith(StringPool.SLASH)) {
				servletContextName = servletContextName.substring(1);
			}

			try {
				serviceBean = PortletBeanLocatorUtil.locate(
					servletContextName,
					beanNameParts[0] + _SERVICE + beanNameParts[1] +
						_LOCAL_SERVICE_UTIL);
			}
			catch (BeanLocatorException ble) {
			}
		}
		else {
			try {
				serviceBean = PortalBeanLocatorUtil.locate(
					beanNameParts[0] + _SERVICE + beanNameParts[1] +
						_LOCAL_SERVICE_UTIL);
			}
			catch (BeanLocatorException ble) {
			}
		}

		Object bean = null;

		if (serviceBean != null) {
			Method getMethod = null;

			try {
				getMethod = serviceBean.getClass().getDeclaredMethod(
					"get" + beanNameParts[1], classPK.getClass());
			}
			catch (Exception e) {
			}

			if (getMethod == null) {
				try {
					getMethod = serviceBean.getClass().getDeclaredMethod(
						"get" + beanNameParts[1],
						mapToPrimitive(classPK.getClass()));
				}
				catch (Exception e) {
				}
			}

			if (getMethod != null) {
				try {
					bean = getMethod.invoke(null, classPK);
				}
				catch (Exception e) {
					_log.warn(e.getMessage());
				}
			}
		}

		return renderBean(
			request, response, servletContextName, bean, varientSuffix);
	}

	public String renderEntity(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String className, Object classPK)
		throws RendererException {

		return renderEntity(
			portletRequest, portletResponse, null, className, classPK, null);
	}

	public String renderEntity(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String className, Object classPK, String varientSuffix)
		throws RendererException {

		return renderEntity(
			portletRequest, portletResponse, null, className, classPK,
			varientSuffix);
	}

	public String renderEntity(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String servletContextName, String className, Object classPK)
		throws RendererException {

		return renderEntity(
			portletRequest, portletResponse, servletContextName, className,
			classPK, null);
	}

	public String renderEntity(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String servletContextName, String className, Object classPK,
			String varientSuffix)
		throws RendererException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			portletResponse);

		return renderEntity(
			request, response, servletContextName, className, classPK,
			varientSuffix);
	}

	protected PortletPreferences getPortletPreferences(
		HttpServletRequest request) {

		PortletPreferences preferences = PortalUtil.getPreferences(request);

		String portletResource = ParamUtil.getString(
			request, "portletResource");

		if (Validator.isNotNull(portletResource)) {
			try {
				preferences = PortletPreferencesFactoryUtil.getPortletSetup(
					request, portletResource);
			}
			catch (PortalException pe) {
			}
			catch (SystemException se) {
			}
		}

		return preferences;
	}

	protected Class<?> mapToPrimitive(Class<?> clazz) {
		Class<?> mapping = clazz;

		if (clazz == Integer.class) {
			mapping = int.class;
		}
		else if (clazz == Long.class) {
			mapping = long.class;
		}

		return mapping;
	}

	protected String normalizeClassName(String className) {
		className = StringUtil.replace(
			className,
			new String[] {
				".impl.", "Impl"
			},
			new String[] {
				StringPool.PERIOD, StringPool.BLANK
			}
		);

		return className;
	}

	private static final String _BEAN = "bean";

	private static final String _LOCAL_SERVICE_UTIL = "LocalServiceUtil";

	private static final String _MODEL = ".model.";

	private static final String _SERVICE = ".service.";

	private static Log _log = LogFactoryUtil.getLog(RendererImpl.class);

}