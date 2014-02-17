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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.util.HtmlImpl;
import com.liferay.portal.util.HttpImpl;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import org.testng.Assert;

/**
 * @author Carlos Sierra Andrés
 */
@RunWith(PowerMockRunner.class)
public class ComboServletURLGeneratorTest extends PowerMockito {

	public static final String COMBO_URL_PREFIX = "/combo?minifier=";

	@Before
	public void setUp() {
		new HtmlUtil().setHtml(new HtmlImpl());
		new HttpUtil().setHttp(new HttpImpl());
		new PortalUtil().setPortal(new PortalImpl());
	}

	@Test
	public void testDoNotReturnSomeVisitedResources() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "/css/main.css", "/css/main1.css");

		ComboServletURLGenerator comboServletURLGenerator =
			new ComboServletURLGenerator();

		comboServletURLGenerator.setPortletResourcesAccessors(
			PortletResourcesAccessor.headerPortalCss);
		comboServletURLGenerator.setVisited(new HashSet<String>()
			{{add("/css/main.css");}}
		);
		comboServletURLGenerator.setComboURLPrefix(COMBO_URL_PREFIX);

		Collection<String> urls = comboServletURLGenerator.generateURLsFor(
			portlets(portlet));

		assertURLs(urls, COMBO_URL_PREFIX + "&/css/main1.css&t=0");
	}

	@Test
	public void testDoNotReturnVisitedExternalResources() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "/css/main.css", "/css/main1.css",
			"http://www.terminus.com/main.css",
			"http://www.terminus.com/main2.css");

		ComboServletURLGenerator comboServletURLGenerator =
			new ComboServletURLGenerator();

		comboServletURLGenerator.setPortletResourcesAccessors(
			PortletResourcesAccessor.headerPortalCss);

		comboServletURLGenerator.setVisited(new HashSet<String>() { {
			add("http://www.terminus.com/main.css");
			add("/css/main.css");
		}}
		);
		comboServletURLGenerator.setComboURLPrefix(COMBO_URL_PREFIX);

		Collection<String> urls = comboServletURLGenerator.generateURLsFor(
			portlets(portlet));

		assertURLs(
			urls, "http://www.terminus.com/main2.css",
			COMBO_URL_PREFIX + "&/css/main1.css&t=0");
	}

	@Test
	public void testDoNotReturnVisitedResources() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "/css/main.css");

		ComboServletURLGenerator comboServletURLGenerator =
			new ComboServletURLGenerator();

		comboServletURLGenerator.setPortletResourcesAccessors(
			PortletResourcesAccessor.headerPortalCss);
		comboServletURLGenerator.setVisited(new HashSet<String>() { {
			add("/css/main.css");
		}}
		);
		comboServletURLGenerator.setComboURLPrefix(COMBO_URL_PREFIX);

		Collection<String> urls = comboServletURLGenerator.generateURLsFor(
			portlets(portlet));

		Assert.assertEquals(urls, new ArrayList<String>());
	}

	@Test
	public void testFilterIsHonored() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "/css/main.css");
		final Portlet portlet2 = createPortletWithContextPathAndResources(
			"/pluginContextPath2", "/css/main2.css");

		ComboServletURLGenerator comboServletURLGenerator =
			new ComboServletURLGenerator();

		comboServletURLGenerator.setPortletResourcesAccessors(
			PortletResourcesAccessor.headerPortalCss);

		HashSet<String> visited = new HashSet<String>();

		comboServletURLGenerator.setVisited(visited);
		comboServletURLGenerator.setComboURLPrefix(COMBO_URL_PREFIX);

		comboServletURLGenerator.setPredicateFilter(PredicateFilter.NONE);

		Collection<String> urls = comboServletURLGenerator.generateURLsFor(
			portlets(portlet));

		Assert.assertTrue(urls.isEmpty());
	}

	@Test
	public void testPortletWithStaticContextPortletResource() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "/css/main.css", "/css/main1.css");

		ComboServletURLGenerator comboServletURLGenerator =
			new ComboServletURLGenerator();

		comboServletURLGenerator.setPortletResourcesAccessors(
			PortletResourcesAccessor.headerPortletCss);
		comboServletURLGenerator.setVisited(new HashSet<String>());
		comboServletURLGenerator.setComboURLPrefix(COMBO_URL_PREFIX);

		Collection<String> urls = comboServletURLGenerator.generateURLsFor(
			portlets(portlet));

		assertURLs(
			urls, COMBO_URL_PREFIX + "&/pluginContextPath:/css/main.css" +
			"&/pluginContextPath:/css/main1.css&t=0");
	}

	@Test
	public void testPortletWithStaticPortalExternalResource() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "http://www.someserver.com/someResource.css",
			"/css/main.css");

		ComboServletURLGenerator comboServletURLGenerator =
			new ComboServletURLGenerator();

		comboServletURLGenerator.setPortletResourcesAccessors(
			PortletResourcesAccessor.headerPortalCss);
		comboServletURLGenerator.setVisited(new HashSet<String>());
		comboServletURLGenerator.setComboURLPrefix(COMBO_URL_PREFIX);

		Collection<String> urls = comboServletURLGenerator.generateURLsFor(
			portlets(portlet));

		assertURLs(
			urls, "http://www.someserver.com/someResource.css",
			COMBO_URL_PREFIX + "&/css/main.css&t=0");
	}

	@Test
	public void testPortletWithStaticPortalOnlyExternalResource() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "http://www.someserver.com/someResource.css");

		ComboServletURLGenerator comboServletURLGenerator =
			new ComboServletURLGenerator();

		comboServletURLGenerator.setPortletResourcesAccessors(
			PortletResourcesAccessor.headerPortalCss);
		comboServletURLGenerator.setVisited(new HashSet<String>());
		comboServletURLGenerator.setComboURLPrefix(COMBO_URL_PREFIX);

		Collection<String> urls = comboServletURLGenerator.generateURLsFor(
			portlets(portlet));

		assertURLs(urls, "http://www.someserver.com/someResource.css");
	}

	@Test
	public void testPortletWithStaticPortalResource() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "/css/main.css", "/css/main1.css");

		ComboServletURLGenerator comboServletURLGenerator =
			new ComboServletURLGenerator();

		comboServletURLGenerator.setPortletResourcesAccessors(
			PortletResourcesAccessor.headerPortalCss);
		comboServletURLGenerator.setVisited(new HashSet<String>());
		comboServletURLGenerator.setComboURLPrefix(COMBO_URL_PREFIX);

		Collection<String> urls = comboServletURLGenerator.generateURLsFor(
			portlets(portlet));

		assertURLs(
			urls, COMBO_URL_PREFIX + "&/css/main.css&/css/main1.css&t=0");
	}

	@Test
	public void testPortletWithStaticPortalResourceNotAffectedByOrder() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "/css/main1.css", "/css/main.css");

		ComboServletURLGenerator comboServletURLGenerator =
			new ComboServletURLGenerator();

		comboServletURLGenerator.setPortletResourcesAccessors(
			PortletResourcesAccessor.headerPortalCss);
		comboServletURLGenerator.setVisited(new HashSet<String>());
		comboServletURLGenerator.setComboURLPrefix(COMBO_URL_PREFIX);

		Collection<String> urls = comboServletURLGenerator.generateURLsFor(
			portlets(portlet));

		assertURLs(
			urls, COMBO_URL_PREFIX + "&/css/main.css&/css/main1.css&t=0");
	}

	@Test
	public void testPortletWithStaticPortalResourceNotAffectedByPortletOrder() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "/css/main1.css", "/css/main.css");
		final Portlet portlet2 = createPortletWithContextPathAndResources(
			"/pluginContextPath2", "/css/main2.css", "/css/main3.css");

		ComboServletURLGenerator comboServletURLGenerator =
			new ComboServletURLGenerator();

		comboServletURLGenerator.setPortletResourcesAccessors(
			PortletResourcesAccessor.headerPortalCss);
		comboServletURLGenerator.setVisited(new HashSet<String>());
		comboServletURLGenerator.setComboURLPrefix(COMBO_URL_PREFIX);

		Collection<String> first = comboServletURLGenerator.generateURLsFor(
			portlets(portlet, portlet2));

		comboServletURLGenerator.setVisited(new HashSet<String>()); //clean

		Collection<String> second = comboServletURLGenerator.generateURLsFor(
			portlets(portlet2, portlet));

		Assert.assertEquals(first, second);
	}

	@Test
	public void testTimestamp() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "/css/main.css");
		portlet.setTimestamp(10000L);

		ComboServletURLGenerator comboServletURLGenerator =
			new ComboServletURLGenerator();

		comboServletURLGenerator.setPortletResourcesAccessors(
			PortletResourcesAccessor.headerPortalCss);
		comboServletURLGenerator.setVisited(new HashSet<String>());
		comboServletURLGenerator.setComboURLPrefix(COMBO_URL_PREFIX);

		Collection<String> urls = comboServletURLGenerator.generateURLsFor(
			portlets(portlet));

		assertURLs(urls, COMBO_URL_PREFIX + "&/css/main.css&t=10000");
	}

	@Test
	public void testTimestampReverse() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "/css/main.css");
		portlet.setTimestamp(10000L);

		ComboServletURLGenerator comboServletURLGenerator =
			new ComboServletURLGenerator();

		comboServletURLGenerator.setMinTimestamp(20000L);
		comboServletURLGenerator.setPortletResourcesAccessors(
			PortletResourcesAccessor.headerPortalCss);
		comboServletURLGenerator.setVisited(new HashSet<String>());
		comboServletURLGenerator.setComboURLPrefix(COMBO_URL_PREFIX);

		Collection<String> urls = comboServletURLGenerator.generateURLsFor(
			portlets(portlet));

		assertURLs(urls, COMBO_URL_PREFIX + "&/css/main.css&t=20000");
	}

	@Test
	public void testUpdateVisitedResources() {
		final Portlet portlet = createPortletWithContextPathAndResources(
			"/pluginContextPath", "/css/main.css");

		ComboServletURLGenerator comboServletURLGenerator =
			new ComboServletURLGenerator();

		comboServletURLGenerator.setPortletResourcesAccessors(
			PortletResourcesAccessor.headerPortalCss);

		HashSet<String> visited = new HashSet<String>();
		comboServletURLGenerator.setVisited(visited);
		comboServletURLGenerator.setComboURLPrefix(COMBO_URL_PREFIX);

		Collection<String> urls = comboServletURLGenerator.generateURLsFor(
			portlets(portlet));

		Assert.assertTrue(visited.contains("/css/main.css"));
	}

	private void assertURLs(Collection<String> urlCollection, String ... urls) {
		Assert.assertEquals(urlCollection, Arrays.asList(urls));
	}

	private Portlet createPortletWithContextPathAndResources(
		String contextPath, String... resources) {

		PortletImpl portlet = spy(new PortletImpl());
		portlet.setPortletName(contextPath);
		doReturn(contextPath).when(portlet).getContextPath();

		List resourcesList = Arrays.asList(resources);
		portlet.setHeaderPortalCss(resourcesList);
		portlet.setFooterPortalCss(resourcesList);
		portlet.setHeaderPortletCss(resourcesList);
		portlet.setFooterPortletCss(resourcesList);
		portlet.setHeaderPortalJavaScript(resourcesList);
		portlet.setFooterPortalJavaScript(resourcesList);
		portlet.setHeaderPortletJavaScript(resourcesList);
		portlet.setFooterPortletJavaScript(resourcesList);

		return portlet;
	}

	private <T> List<T> portlets(T ... elements) {
		return Arrays.asList(elements);
	}

}