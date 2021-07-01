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

package com.liferay.remote.web.component.admin.web.internal.portlet;

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.remote.web.component.admin.web.internal.configuration.RemoteWebComponentConfiguration;

import java.io.PrintWriter;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.HeaderPortlet;
import javax.portlet.MimeResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.remote.web.component.admin.web.internal.configuration.RemoteWebComponentConfiguration",
	factory = "remote.web.component.portlet", service = Portlet.class
)
public class RemoteWebComponentPortlet
	extends MVCPortlet implements HeaderPortlet {

	public String getName() {
		return _remoteWebComponentConfiguration.name();
	}

	public String getName(Locale locale) {
		return _remoteWebComponentConfiguration.name();
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		String elementName = _remoteWebComponentConfiguration.elementName();

		_headers(renderRequest, renderResponse, elementName);

		try {
			PrintWriter printWriter = renderResponse.getWriter();

			LiferayPortletURL renderURL =
				(LiferayPortletURL)renderResponse.createRenderURL(
					MimeResponse.Copy.PUBLIC);

			renderURL.setWindowState(WindowState.NORMAL);

			String routerBaseSelf = _http.removeDomain(renderURL.toString());

			int portletURLSeparator = routerBaseSelf.indexOf("/-/");

			String routerBasePage = routerBaseSelf.substring(
				0, portletURLSeparator);

			int pageSeparator = routerBasePage.lastIndexOf("/");

			String routerBaseSite = routerBasePage.substring(0, pageSeparator);

			printWriter.append(StringPool.LESS_THAN);
			printWriter.append(elementName);
			printWriter.append(" id=\"");
			printWriter.append(renderResponse.getNamespace());
			printWriter.append("\" router-base-self=\"");
			printWriter.append(routerBaseSelf);
			printWriter.append("\" router-base-page=\"");
			printWriter.append(routerBasePage);
			printWriter.append("\" router-base-site=\"");
			printWriter.append(routerBaseSite);
			printWriter.append("\" statemanager-descriptor=\"StateManager\"");
			printWriter.append(" authtoken-descriptor=\"Liferay.authToken\"");

			StringBuffer sb = new StringBuffer();

			_webComponentConfigurationAttributes.forEach(
				(k, v) -> {
					sb.append(" data-config-");
					sb.append(k.replaceAll("\\.", "-"));
					sb.append("=\"");

					Class<?> attributeValueClass = v.getClass();

					if (attributeValueClass.isArray()) {
						List<Object> list = Arrays.asList(v);

						Stream<?> stream = list.stream();

						sb.append(
							stream.map(
								String::valueOf
							).collect(
								Collectors.joining(" ")
							));
					}
					else if (v instanceof Collection) {
						Collection<?> collection = (Collection)v;

						Stream<?> stream = collection.stream();

						sb.append(
							stream.map(
								String::valueOf
							).collect(
								Collectors.joining(" ")
							));
					}
					else {
						sb.append(String.valueOf(v));
					}

					sb.append("\"");
				});

			printWriter.append(sb.toString());

			printWriter.append(StringPool.GREATER_THAN);
			printWriter.append("</");
			printWriter.append(elementName);
			printWriter.append(StringPool.GREATER_THAN);

			printWriter.flush();
		}
		catch (Throwable throwable) {
			_log.error(
				String.format(
					"Unable to render web Component <%s>", elementName),
				throwable);
		}
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_remoteWebComponentConfiguration = ConfigurableUtil.createConfigurable(
			RemoteWebComponentConfiguration.class, properties);

		Set<Map.Entry<String, Object>> entrySet = properties.entrySet();

		Stream<Map.Entry<String, Object>> stream = entrySet.stream();

		_webComponentConfigurationAttributes = stream.filter(
			e -> {
				Stream<Predicate<String>> keyFilterStream =
					_keyFilters.stream();

				return keyFilterStream.noneMatch(p -> p.test(e.getKey()));
			}
		).collect(
			Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
		);
	}

	private void _headers(
			RenderRequest renderRequest, MimeResponse mimeResponse,
			String elementName)
		throws PortletException {

		String routerBaseSelf = _http.removeDomain(
			PortletURLBuilder.createRenderURL(
				mimeResponse, MimeResponse.Copy.PUBLIC
			).setWindowState(
				WindowState.NORMAL
			).buildString());

		String headerContent = StringUtil.replace(
			StringUtil.read(RemoteWebComponentPortlet.class, "header.html"),
			new String[] {
				"$[ELEMENT_NAME]$", "$[INSTANCE_ID]$", "$[NAME]$",
				"$[ROUTER_BASE_SELF]$", "$[ROUTER_BASE_SELF_NORMAL]$",
				"$[ROUTER_BASE_SELF_MAXIMIZED]$",
				"$[ROUTER_BASE_SELF_EXCLUSIVE]$", "$[ROUTER_BASE_SELF_POPUP]$"
			},
			new String[] {
				_remoteWebComponentConfiguration.elementName(),
				mimeResponse.getNamespace(),
				_remoteWebComponentConfiguration.name(),
				StringUtil.removeSubstring(routerBaseSelf, "/s/normal"),
				routerBaseSelf,
				StringUtil.replace(routerBaseSelf, "/s/normal", "/s/maximized"),
				StringUtil.replace(routerBaseSelf, "/s/normal", "/s/exclusive"),
				StringUtil.replace(routerBaseSelf, "/s/normal", "/s/pop_up")
			});

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		@SuppressWarnings("unchecked")
		List<String> markupHeadElements =
			(List<String>)httpServletRequest.getAttribute(
				MimeResponse.MARKUP_HEAD_ELEMENT);

		if (markupHeadElements == null) {
			markupHeadElements = new ArrayList<>();

			httpServletRequest.setAttribute(
				MimeResponse.MARKUP_HEAD_ELEMENT, markupHeadElements);
		}

		markupHeadElements.add(headerContent);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RemoteWebComponentPortlet.class);

	private static final List<String> _configMethodNames = Stream.of(
		RemoteWebComponentConfiguration.class.getDeclaredMethods()
	).map(
		Method::getName
	).collect(
		Collectors.toList()
	);
	private static final List<Predicate<String>> _keyFilters = Arrays.asList(
		"component.id"::equals, "component.name"::equals,
		"configuration.cleaner.ignore"::equals, "service.pid"::equals,
		"service.factoryPid"::equals, k -> k.endsWith(".target"),
		k -> k.startsWith("com.liferay.portlet."), k -> k.startsWith("felix."),
		k -> k.startsWith("javax.portlet."), _configMethodNames::contains);

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

	private volatile RemoteWebComponentConfiguration
		_remoteWebComponentConfiguration;
	private volatile Map<String, Object> _webComponentConfigurationAttributes;

}