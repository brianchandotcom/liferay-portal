/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journalcontent.action;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.spring.aop.ServiceBeanMethodInvocation;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author Brian Wing Shun Chan
 * @author Douglas Wong
 * @author Raymond Augé
 */
public class ConfigurationActionImpl extends DefaultConfigurationAction {

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		// This logic has to run in a transaction which we will invoke directly
		// since this is not a Spring bean

		Method doProcessActionMethod = getDoProcessActionMethod();

		ServiceBeanMethodInvocation serviceBeanMethodInvocation =
			new ServiceBeanMethodInvocation(
				this, ConfigurationActionImpl.class, doProcessActionMethod,
				new Object[] {portletConfig, actionRequest, actionResponse});

		List<MethodInterceptor> methodInterceptors = getMethodInterceptors();

		serviceBeanMethodInvocation.setMethodInterceptors(methodInterceptors);

		try {
			serviceBeanMethodInvocation.proceed();
		}
		catch (Throwable t) {
			throw new Exception(t);
		}
	}

	/**
	 * This method is invoked in a transaction because we may result in a
	 * persistence call before and/or after the call to super.processAction()
	 * which itself results in a persistence call.
	 */
	@Transactional(
		isolation = Isolation.PORTAL, propagation = Propagation.REQUIRES_NEW,
		rollbackFor = {Exception.class}
	)
	protected void doProcessAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String[] extensions = actionRequest.getParameterValues("extensions");

		setPreference(actionRequest, "extensions", extensions);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				layout, portletResource, StringPool.BLANK);

		String articleId = getArticleId(actionRequest);

		String originalArticleId = preferences.getValue("articleId", null);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			themeDisplay.getCompanyId(), portletResource);

		PortletLayoutListener portletLayoutListener =
			portlet.getPortletLayoutListenerInstance();

		if ((portletLayoutListener != null) &&
			Validator.isNotNull(originalArticleId) &&
			!originalArticleId.equals(articleId)) {

			// Results in a persistence call

			portletLayoutListener.onRemoveFromLayout(
				portletResource, layout.getPlid());
		}

		// Results in a persistence call

		super.processAction(portletConfig, actionRequest, actionResponse);

		if (SessionErrors.isEmpty(actionRequest) &&
			(portletLayoutListener != null)) {

			// Results in a persistence call

			portletLayoutListener.onAddToLayout(
				portletResource, layout.getPlid());
		}
	}

	protected String getArticleId(PortletRequest portletRequest) {
		String articleId = getParameter(portletRequest, "articleId");

		return articleId.toUpperCase();
	}

	protected Method getDoProcessActionMethod() {
		if (_doProcessActionMethod != null) {
			return _doProcessActionMethod;
		}

		Class<?> clazz = getClass();

		try {
			_doProcessActionMethod = clazz.getDeclaredMethod(
				"doProcessAction",
				new Class<?>[] {
					PortletConfig.class, ActionRequest.class,
					ActionResponse.class});
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}

		return _doProcessActionMethod;
	}

	protected List<MethodInterceptor> getMethodInterceptors() {
		if (_methodInterceptors != null) {
			return _methodInterceptors;
		}

		List<MethodInterceptor> methodInterceptors =
			new ArrayList<MethodInterceptor>();

		MethodInterceptor methodInterceptor =
			(MethodInterceptor)PortalBeanLocatorUtil.locate(
				"transactionAdvice");

		methodInterceptors.add(methodInterceptor);

		_methodInterceptors = methodInterceptors;

		return _methodInterceptors;
	}

	private Method _doProcessActionMethod;
	private List<MethodInterceptor> _methodInterceptors;

}