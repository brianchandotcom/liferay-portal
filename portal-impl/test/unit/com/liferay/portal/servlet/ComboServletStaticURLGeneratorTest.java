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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.servlet.ServletContextUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.impl.PortletAppImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.util.HtmlImpl;
import com.liferay.portal.util.HttpImpl;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletResourceAccessor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockServletContext;

/**
 * @author Carlos Sierra Andrés
 */
public class ComboServletStaticURLGeneratorTest {

	@Before
	public void setUp() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());

		HttpUtil httpUtil = new HttpUtil();

		httpUtil.setHttp(new HttpImpl());

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());
	}

	@Test
	public void testGenerateDoesNotAffectResourceOrder() {
		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.HEADER_PORTAL_CSS);
		comboServletStaticURLGenerator.setURLPrefix(_URL_PREFIX);
		comboServletStaticURLGenerator.setVisitedURLs(new HashSet<String>());

		Portlet portlet1 = buildPortlet(
			"portlet1", "/css/main.css", "/css/more.css");

		setPortletTimestamp("portlet1", 0);

		Portlet portlet2 = buildPortlet(
			"portlet2", "/css/more.css", "/css/main.css");

		setPortletTimestamp("portlet2", 0);

		List<String> urls1 = comboServletStaticURLGenerator.generate(
			toList(portlet1));

		comboServletStaticURLGenerator.setVisitedURLs(new HashSet<String>());

		List<String> urls2 = comboServletStaticURLGenerator.generate(
			toList(portlet2));

		assertURLs(
			urls1, _URL_PREFIX + "&%2Fcss%2Fmain.css&%2Fcss%2Fmore.css&t=0");
		assertURLs(
			urls2, _URL_PREFIX + "&%2Fcss%2Fmore.css&%2Fcss%2Fmain.css&t=0");
	}

	@Test
	public void testGenerateDoesNotReturnVisitedAbsoluteResources() {
		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.HEADER_PORTAL_CSS);
		comboServletStaticURLGenerator.setURLPrefix(_URL_PREFIX);
		comboServletStaticURLGenerator.setVisitedURLs(
			SetUtil.fromArray(
				new String[] {
					"http://www.test.com/test1.css", "/css/main.css"
				}));

		Portlet portlet = buildPortlet(
			"/portlet", "/css/main.css", "/css/more.css",
			"http://www.test.com/test1.css", "http://www.test.com/test2.css");

		setPortletTimestamp("/portlet", 0);

		List<String> urls = comboServletStaticURLGenerator.generate(
			toList(portlet));

		assertURLs(
			urls, "http://www.test.com/test2.css",
			_URL_PREFIX + "&%2Fcss%2Fmore.css&t=0");
	}

	@Test
	public void testGenerateDoesNotReturnVisitedResources() {
		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.HEADER_PORTAL_CSS);
		comboServletStaticURLGenerator.setURLPrefix(_URL_PREFIX);
		comboServletStaticURLGenerator.setVisitedURLs(
			SetUtil.fromArray(new String[] {"/css/main.css"}));

		Portlet portlet = buildPortlet(
			"portlet", "/css/main.css", "/css/more.css");

		setPortletTimestamp("portlet", 0);

		List<String> urls = comboServletStaticURLGenerator.generate(
			toList(portlet));

		assertURLs(urls, _URL_PREFIX + "&%2Fcss%2Fmore.css&t=0");
	}

	@Test
	public void testGenerateIsNotAffectedByPortletOrder() {
		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.HEADER_PORTAL_CSS);
		comboServletStaticURLGenerator.setURLPrefix(_URL_PREFIX);
		comboServletStaticURLGenerator.setVisitedURLs(new HashSet<String>());

		Portlet portlet1 = buildPortlet(
			"portlet1", "/css/main2.css", "/css/main1.css");

		setPortletTimestamp("portlet1", 0);

		Portlet portlet2 = buildPortlet(
			"portlet2", "/css/main3.css", "/css/main4.css");

		setPortletTimestamp("portlet2", 0);

		List<String> urls1 = comboServletStaticURLGenerator.generate(
			toList(portlet1, portlet2));

		comboServletStaticURLGenerator.setVisitedURLs(new HashSet<String>());

		List<String> urls2 = comboServletStaticURLGenerator.generate(
			toList(portlet2, portlet1));

		Assert.assertEquals(urls1, urls2);
	}

	@Test
	public void testGenerateUpdatesVisitedURLs() {
		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.HEADER_PORTAL_CSS);

		comboServletStaticURLGenerator.setURLPrefix(_URL_PREFIX);

		Set<String> visitedURLs = new HashSet<String>();

		comboServletStaticURLGenerator.setVisitedURLs(visitedURLs);

		Portlet portlet = buildPortlet("portlet", "/css/main.css");

		setPortletTimestamp("portlet", 0);

		comboServletStaticURLGenerator.generate(toList(portlet));

		Assert.assertTrue(visitedURLs.contains("/css/main.css"));
	}

	@Test
	public void testGenerateWhenPortletTimestampIsHigher() {
		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.HEADER_PORTAL_CSS);
		comboServletStaticURLGenerator.setURLPrefix(_URL_PREFIX);
		comboServletStaticURLGenerator.setVisitedURLs(new HashSet<String>());

		Portlet portlet = buildPortlet("portlet", "/css/main.css");

		setPortletTimestamp("portlet", 10000);

		List<String> urls = comboServletStaticURLGenerator.generate(
			toList(portlet));

		assertURLs(urls, _URL_PREFIX + "&%2Fcss%2Fmain.css&t=10000");
	}

	@Test
	public void testGenerateWhenThemeTimestampIsHigher() {
		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.HEADER_PORTAL_CSS);
		comboServletStaticURLGenerator.setTimestamp(20000);
		comboServletStaticURLGenerator.setURLPrefix(_URL_PREFIX);
		comboServletStaticURLGenerator.setVisitedURLs(new HashSet<String>());

		Portlet portlet = buildPortlet("portlet", "/css/main.css");

		setPortletTimestamp("portlet", 2000);

		List<String> urls = comboServletStaticURLGenerator.generate(
			toList(portlet));

		assertURLs(urls, _URL_PREFIX + "&%2Fcss%2Fmain.css&t=20000");
	}

	@Test
	public void testGenerateWithAbsoluteAndRelativePortalResources() {
		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.HEADER_PORTAL_CSS);
		comboServletStaticURLGenerator.setURLPrefix(_URL_PREFIX);
		comboServletStaticURLGenerator.setVisitedURLs(new HashSet<String>());

		Portlet portlet = buildPortlet(
			"portlet", "http://www.test.com/test.css", "/css/main.css");

		setPortletTimestamp("portlet", 0);

		List<String> urls = comboServletStaticURLGenerator.generate(
			toList(portlet));

		assertURLs(
			urls, "http://www.test.com/test.css",
			_URL_PREFIX + "&%2Fcss%2Fmain.css&t=0");
	}

	@Test
	public void testGenerateWithAbsolutePortalResource() {
		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.HEADER_PORTAL_CSS);
		comboServletStaticURLGenerator.setURLPrefix(_URL_PREFIX);
		comboServletStaticURLGenerator.setVisitedURLs(new HashSet<String>());

		Portlet portlet = buildPortlet(
			"portlet", "http://www.test.com/test.css");

		setPortletTimestamp("portlet", 0);

		List<String> urls = comboServletStaticURLGenerator.generate(
			toList(portlet));

		assertURLs(urls, "http://www.test.com/test.css");
	}

	@Test
	public void testGenerateWithPredicateFilter() {
		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.HEADER_PORTAL_CSS);
		comboServletStaticURLGenerator.setPredicateFilter(PredicateFilter.NONE);
		comboServletStaticURLGenerator.setURLPrefix(_URL_PREFIX);
		comboServletStaticURLGenerator.setVisitedURLs(new HashSet<String>());

		Portlet portlet = buildPortlet("portlet", "/css/main.css");

		setPortletTimestamp("portlet", 0);

		List<String> urls = comboServletStaticURLGenerator.generate(
			toList(portlet));

		Assert.assertTrue(urls.isEmpty());
	}

	@Test
	public void testGenerateWithRelativeAndPortletResources() {
		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.HEADER_PORTLET_CSS);
		comboServletStaticURLGenerator.setURLPrefix(_URL_PREFIX);
		comboServletStaticURLGenerator.setVisitedURLs(new HashSet<String>());

		Portlet portlet = buildPortlet(
			"portlet", "/css/main.css", "/css/more.css");

		setPortletTimestamp("portlet", 0);

		List<String> urls = comboServletStaticURLGenerator.generate(
			toList(portlet));

		assertURLs(
			urls,
			_URL_PREFIX + "&" + PortletKeys.PORTAL +
				":%2Fcss%2Fmain.css&" + PortletKeys.PORTAL +
				":%2Fcss%2Fmore.css&t=0");
	}

	@Test
	public void testGenerateWithRelativePortalResources() {
		ComboServletStaticURLGenerator comboServletStaticURLGenerator =
			new ComboServletStaticURLGenerator();

		comboServletStaticURLGenerator.setPortletResourceAccessors(
			PortletResourceAccessor.HEADER_PORTAL_CSS);
		comboServletStaticURLGenerator.setURLPrefix(_URL_PREFIX);
		comboServletStaticURLGenerator.setVisitedURLs(new HashSet<String>());

		Portlet portlet = buildPortlet(
			"portlet", "/css/main.css", "/css/more.css");

		setPortletTimestamp("portlet", 0);

		List<String> urls = comboServletStaticURLGenerator.generate(
			toList(portlet));

		assertURLs(
			urls, _URL_PREFIX + "&%2Fcss%2Fmain.css&%2Fcss%2Fmore.css&t=0");
	}

	protected void assertURLs(List<String> urlsList, String... urls) {
		Assert.assertEquals(urlsList, Arrays.asList(urls));
	}

	protected Portlet buildPortlet(
		String contextName, String... portletResources) {

		PortletImpl portlet = new PortletImpl();

		List<String> portletResourcesList = Arrays.asList(portletResources);

		portlet.setFooterPortalCss(portletResourcesList);
		portlet.setFooterPortalJavaScript(portletResourcesList);
		portlet.setFooterPortletCss(portletResourcesList);
		portlet.setFooterPortletJavaScript(portletResourcesList);
		portlet.setHeaderPortalCss(portletResourcesList);
		portlet.setHeaderPortalJavaScript(portletResourcesList);
		portlet.setHeaderPortletCss(portletResourcesList);
		portlet.setHeaderPortletJavaScript(portletResourcesList);
		portlet.setPortletId(PortletKeys.PORTAL);
		portlet.setPortletName(contextName);

		PortletAppImpl portletApp = new PortletAppImpl(contextName);

		ServletContext servletContext = new MockServletContext();

		ServletContextPool.put(contextName, servletContext);

		portletApp.setServletContext(servletContext);

		portlet.setPortletApp(portletApp);

		return portlet;
	}

	protected void setPortletTimestamp(
		String servletContextName, long timestamp) {

		ServletContext servletContext = ServletContextPool.get(
			servletContextName);

		servletContext.setAttribute(
			ServletContextUtil.class.getName() + "./", timestamp);
	}

	private <T> List<T> toList(T... t) {
		return Arrays.asList(t);
	}

	private static final String _URL_PREFIX = "/combo?minifier=";

}