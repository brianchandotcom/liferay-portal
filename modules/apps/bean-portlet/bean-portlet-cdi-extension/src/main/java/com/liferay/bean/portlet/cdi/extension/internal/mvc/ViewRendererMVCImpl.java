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

package com.liferay.bean.portlet.cdi.extension.internal.mvc;

import com.liferay.bean.portlet.extension.ViewRenderer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.annotation.Annotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.TypeLiteral;

import javax.mvc.Models;
import javax.mvc.binding.ParamError;
import javax.mvc.engine.ViewEngine;
import javax.mvc.engine.ViewEngineException;

import javax.portlet.MimeResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import javax.ws.rs.core.Configuration;

/**
 * @author Neil Griffin
 */
public class ViewRendererMVCImpl implements ViewRenderer {

	public ViewRendererMVCImpl(
		BeanManager beanManager, boolean importsMvcBindingPackage,
		boolean importsMvcPackage) {

		_beanManager = beanManager;
		_importsMvcBindingPackage = importsMvcBindingPackage;
		_importsMvcPackage = importsMvcPackage;
	}

	@Override
	public void render(
			MimeResponse mimeResponse, PortletConfig portletConfig,
			PortletRequest portletRequest)
		throws PortletException {

		if (!_importsMvcPackage) {
			return;
		}

		Models models = _getModels(_beanManager);

		Map<String, Object> modelMap = models.asMap();

		for (Map.Entry<String, Object> entry : modelMap.entrySet()) {
			portletRequest.setAttribute(entry.getKey(), entry.getValue());
		}

		String viewName = (String)portletRequest.getAttribute(VIEW_NAME);

		if (viewName == null) {
			@SuppressWarnings("deprecation")
			String redirectedView = portletRequest.getParameter(
				REDIRECTED_VIEW);

			if (redirectedView != null) {
				PortletSession portletSession =
					portletRequest.getPortletSession(true);

				viewName = (String)portletSession.getAttribute(VIEW_NAME);

				if (viewName != null) {
					portletSession.removeAttribute(VIEW_NAME);
					portletRequest.setAttribute(VIEW_NAME, viewName);
				}
			}
		}

		if (viewName != null) {
			Configuration configuration = _getConfiguration(_beanManager);

			if (!viewName.contains(".")) {
				String defaultViewExtension = (String)configuration.getProperty(
					ConfigurationImpl.DEFAULT_VIEW_EXTENSION);

				viewName = viewName.concat(
					"."
				).concat(
					defaultViewExtension
				);
			}

			ViewEngine supportingViewEngine = null;

			List<ViewEngine> viewEngines = _getViewEngines(_beanManager);

			for (ViewEngine viewEngine : viewEngines) {
				if (viewEngine.supports(viewName)) {
					supportingViewEngine = viewEngine;

					break;
				}
			}

			if (supportingViewEngine == null) {
				throw new PortletException(
					new ViewEngineException(
						"No ViewEngine found that supports " + viewName));
			}

			try {
				_beanManager.fireEvent(
					new BeforeProcessViewEventImpl(
						viewName, supportingViewEngine.getClass()));

				supportingViewEngine.processView(
					new ViewEngineContextImpl(
						configuration, portletRequest.getLocale(), mimeResponse,
						models, portletRequest));

				_beanManager.fireEvent(
					new AfterProcessViewEventImpl(
						viewName, supportingViewEngine.getClass()));
			}
			catch (ViewEngineException viewEngineException) {
				throw new PortletException(viewEngineException);
			}
		}

		if (_importsMvcBindingPackage) {
			MutableBindingResult mutableBindingResult =
				BeanUtil.getMutableBindingResult(_beanManager);

			if ((mutableBindingResult != null) &&
				!mutableBindingResult.isConsulted()) {

				Set<ParamError> allErrors = mutableBindingResult.getAllErrors();

				for (ParamError paramError : allErrors) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"A BindingResult error was not processed for ",
								paramError.getParamName(), ": ",
								paramError.getMessage()));
					}
				}
			}
		}
	}

	private static Configuration _getConfiguration(BeanManager beanManager) {
		Bean<?> bean = beanManager.resolve(
			beanManager.getBeans(Configuration.class));

		return (Configuration)beanManager.getReference(
			bean, Configuration.class,
			beanManager.createCreationalContext(bean));
	}

	private static Models _getModels(BeanManager beanManager) {
		Bean<?> bean = beanManager.resolve(beanManager.getBeans(Models.class));

		return (Models)beanManager.getReference(
			bean, Models.class, beanManager.createCreationalContext(bean));
	}

	private static List<ViewEngine> _getViewEngines(BeanManager beanManager) {
		Set<Bean<?>> beans = beanManager.getBeans(
			_viewEnginesTypeLiteral.getType(), _viewEngines);

		List<ViewEngine> viewEngines = new ArrayList<>();

		Bean<?> bean = beanManager.resolve(beans);

		CreationalContext<?> creationalContext =
			beanManager.createCreationalContext(bean);

		Object reference = beanManager.getReference(
			bean, _viewEnginesTypeLiteral.getType(), creationalContext);

		if (reference instanceof List) {
			List<?> list = (List)reference;

			for (Object o : list) {
				if (o instanceof ViewEngine) {
					viewEngines.add((ViewEngine)o);
				}
			}
		}

		return viewEngines;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewRendererMVCImpl.class);

	private static final Annotation _viewEngines = new ViewEngines() {

		@Override
		public Class<? extends Annotation> annotationType() {
			return ViewEngines.class;
		}

	};

	private static final TypeLiteral<?> _viewEnginesTypeLiteral =
		new TypeLiteral<List<ViewEngine>>() {
		};

	private final BeanManager _beanManager;
	private final boolean _importsMvcBindingPackage;
	private final boolean _importsMvcPackage;

}