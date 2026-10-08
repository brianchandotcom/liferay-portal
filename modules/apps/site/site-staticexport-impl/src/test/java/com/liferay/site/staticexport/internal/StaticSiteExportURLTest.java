/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.staticexport.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Víctor Galán
 */
public class StaticSiteExportURLTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetArchivePath() {
		Assert.assertEquals(
			"o/frontend-js-web/main.css",
			_getArchivePath("/o/frontend-js-web/main.css"));
		Assert.assertEquals(
			"o/layout-common-styles/main." + _getDigest("plid=1") + ".css",
			_getArchivePath("/o/layout-common-styles/main.css?plid=1"));
		Assert.assertEquals(
			"documents/20121/0/photo.jpg/uuid.jpg",
			_getArchivePath("/documents/20121/0/photo.jpg/uuid"));
		Assert.assertEquals(
			"documents/20121/0/photo.jpg/uuid." + _getDigest("t=1") + ".jpg",
			_getArchivePath("/documents/20121/0/photo.jpg/uuid?t=1"));
		Assert.assertEquals(
			"combo." + _getDigest("minifierType=js&/o/a/b.js") + ".js",
			_getArchivePath("/combo?minifierType=js&/o/a/b.js"));
		Assert.assertEquals(
			"image/company_logo." + _getDigest("img_id=1"),
			_getArchivePath("/image/company_logo?img_id=1"));
	}

	@Test
	public void testGetDispatchPath() {
		StaticSiteExportURL staticSiteExportURL = new StaticSiteExportURL(
			"/o/frontend-js-web/some%20file.css?t=1");

		Assert.assertEquals(
			"/some file.css", staticSiteExportURL.getDispatchPath());

		staticSiteExportURL = new StaticSiteExportURL("/documents/20121/0/a");

		Assert.assertEquals(
			"/documents/20121/0/a", staticSiteExportURL.getDispatchPath());
	}

	@Test
	public void testGetHostName() {
		StaticSiteExportURL staticSiteExportURL = new StaticSiteExportURL(
			"https://CDN.example.com:8443/lib/a.js?v=2");

		Assert.assertEquals(
			"cdn.example.com", staticSiteExportURL.getHostName());

		staticSiteExportURL = new StaticSiteExportURL("//user@cdn.example.com");

		Assert.assertEquals(
			"cdn.example.com", staticSiteExportURL.getHostName());

		staticSiteExportURL = new StaticSiteExportURL("/o/a/b.js");

		Assert.assertNull(staticSiteExportURL.getHostName());
	}

	@Test
	public void testGetModuleName() {
		StaticSiteExportURL staticSiteExportURL = new StaticSiteExportURL(
			"/o/frontend-js-web/main.css?t=1");

		Assert.assertEquals(
			"frontend-js-web", staticSiteExportURL.getModuleName());

		staticSiteExportURL = new StaticSiteExportURL("/documents/20121/0/a");

		Assert.assertNull(staticSiteExportURL.getModuleName());

		staticSiteExportURL = new StaticSiteExportURL("/o/a");

		Assert.assertNull(staticSiteExportURL.getModuleName());
	}

	@Test
	public void testGetPathInfo() {
		StaticSiteExportURL staticSiteExportURL = new StaticSiteExportURL(
			"/documents/20121/0/a");

		Assert.assertEquals("/20121/0/a", staticSiteExportURL.getPathInfo());

		staticSiteExportURL = new StaticSiteExportURL("/combo?/o/a/b.js");

		Assert.assertNull(staticSiteExportURL.getPathInfo());
	}

	@Test
	public void testGetQueryString() {
		StaticSiteExportURL staticSiteExportURL = new StaticSiteExportURL(
			"/web/guest/home?p=1#main");

		Assert.assertEquals("p=1", staticSiteExportURL.getQueryString());

		staticSiteExportURL = new StaticSiteExportURL("/web/guest/home");

		Assert.assertNull(staticSiteExportURL.getQueryString());
	}

	@Test
	public void testGetServletPath() {
		StaticSiteExportURL staticSiteExportURL = new StaticSiteExportURL(
			"/documents/20121/0/a");

		Assert.assertEquals("/documents", staticSiteExportURL.getServletPath());

		staticSiteExportURL = new StaticSiteExportURL("/combo?/o/a/b.js");

		Assert.assertEquals("/combo", staticSiteExportURL.getServletPath());
	}

	@Test
	public void testGetURIFragment() {
		StaticSiteExportURL staticSiteExportURL = new StaticSiteExportURL(
			"/web/guest/home?p=1#main");

		Assert.assertEquals("#main", staticSiteExportURL.getURIFragment());

		staticSiteExportURL = new StaticSiteExportURL("/web/guest/home");

		Assert.assertEquals(
			StringPool.BLANK, staticSiteExportURL.getURIFragment());
	}

	@Test
	public void testGetURL() {
		StaticSiteExportURL staticSiteExportURL = new StaticSiteExportURL(
			" /web/guest/home?p=1#main ");

		Assert.assertEquals(
			"/web/guest/home?p=1", staticSiteExportURL.getURL());

		staticSiteExportURL = new StaticSiteExportURL(
			"https://cdn.example.com/a.js#top");

		Assert.assertEquals(
			"https://cdn.example.com/a.js", staticSiteExportURL.getURL());
	}

	@Test
	public void testHasScheme() {
		StaticSiteExportURL staticSiteExportURL = new StaticSiteExportURL(
			"https://cdn.example.com/a.js");

		Assert.assertTrue(staticSiteExportURL.hasScheme());

		staticSiteExportURL = new StaticSiteExportURL(
			"mailto:test@liferay.com");

		Assert.assertTrue(staticSiteExportURL.hasScheme());

		staticSiteExportURL = new StaticSiteExportURL("//cdn.example.com/a.js");

		Assert.assertFalse(staticSiteExportURL.hasScheme());

		staticSiteExportURL = new StaticSiteExportURL("/a.png?x=a:b");

		Assert.assertFalse(staticSiteExportURL.hasScheme());
	}

	@Test
	public void testIsExternal() {
		StaticSiteExportURL staticSiteExportURL = new StaticSiteExportURL(
			"https://cdn.example.com:8443/lib/a.js?v=2");

		Assert.assertTrue(staticSiteExportURL.isExternal());

		staticSiteExportURL = new StaticSiteExportURL("//cdn.example.com");

		Assert.assertTrue(staticSiteExportURL.isExternal());

		staticSiteExportURL = new StaticSiteExportURL("/o/a/b.js");

		Assert.assertFalse(staticSiteExportURL.isExternal());
	}

	@Test
	public void testIsExternalWithPortalHostNames() {
		Set<String> portalHostNames = SetUtil.fromArray(
			"localhost", "www.example.com");

		_assertPortalURL(
			portalHostNames, "http://localhost:8080/o/a/b.css?t=1#top");
		_assertPortalURL(
			portalHostNames, "https://LOCALHOST/o/a/b.css?t=1#top");
		_assertPortalURL(
			portalHostNames, "//www.example.com/o/a/b.css?t=1#top");
		_assertPortalURL(
			portalHostNames, "http://user@localhost/o/a/b.css?t=1#top");

		StaticSiteExportURL staticSiteExportURL = new StaticSiteExportURL(
			portalHostNames, "http://localhost:8080");

		Assert.assertEquals("/", staticSiteExportURL.getURL());

		staticSiteExportURL = new StaticSiteExportURL(
			portalHostNames, "https://cdn.example.com/o/a/b.css");

		Assert.assertTrue(staticSiteExportURL.isExternal());
		Assert.assertEquals(
			"https://cdn.example.com/o/a/b.css", staticSiteExportURL.getURL());

		staticSiteExportURL = new StaticSiteExportURL(
			portalHostNames, "/o/classic-theme/css/main.css");

		Assert.assertEquals(
			"/o/classic-theme/images/a.png",
			_resolve(
				staticSiteExportURL,
				"https://localhost/o/classic-theme/images/a.png"));
	}

	@Test
	public void testIsResource() {
		Assert.assertTrue(_isResource("/combo?/o/a.js"));
		Assert.assertTrue(_isResource("/documents/1/2/a.png"));
		Assert.assertTrue(_isResource("/image/logo?img_id=1"));
		Assert.assertTrue(_isResource("/o/a/b.css"));
		Assert.assertTrue(_isResource("/webserver/a.png"));

		Assert.assertFalse(_isResource("//cdn.example.com/o/a.js"));
		Assert.assertFalse(_isResource("mailto:test@liferay.com"));
		Assert.assertFalse(_isResource("/web/guest/home"));
	}

	@Test
	public void testResolve() {
		StaticSiteExportURL staticSiteExportURL = new StaticSiteExportURL(
			"/o/classic-theme/css/main.css?t=1");

		Assert.assertEquals(
			"/o/classic-theme/images/a.png",
			_resolve(staticSiteExportURL, "../images/a.png"));
		Assert.assertEquals(
			"/o/classic-theme/css/b.png",
			_resolve(staticSiteExportURL, "./b.png"));
		Assert.assertEquals(
			"/o/classic-theme/css/fonts/c.woff2?v=3",
			_resolve(staticSiteExportURL, "fonts/c.woff2?v=3#iefix"));
		Assert.assertEquals(
			"/o/other/d.png", _resolve(staticSiteExportURL, "/o/other/d.png"));
		Assert.assertEquals(
			"/o/classic-theme/css/a.png?x=a:b",
			_resolve(staticSiteExportURL, "a.png?x=a:b"));

		Assert.assertNull(_resolve(staticSiteExportURL, StringPool.BLANK));
		Assert.assertNull(_resolve(staticSiteExportURL, "#symbol"));
		Assert.assertNull(_resolve(staticSiteExportURL, "../../../../a.png"));
		Assert.assertNull(
			_resolve(staticSiteExportURL, "//cdn.example.com/a.png"));
		Assert.assertNull(
			_resolve(staticSiteExportURL, "data:image/png;base64,AAAA"));
		Assert.assertNull(
			_resolve(staticSiteExportURL, "https://cdn.example.com/a.png"));
	}

	private void _assertPortalURL(Set<String> portalHostNames, String url) {
		StaticSiteExportURL staticSiteExportURL = new StaticSiteExportURL(
			portalHostNames, url);

		Assert.assertFalse(url, staticSiteExportURL.hasScheme());
		Assert.assertFalse(url, staticSiteExportURL.isExternal());
		Assert.assertTrue(url, staticSiteExportURL.isResource());
		Assert.assertEquals(
			url, "/o/a/b.css?t=1", staticSiteExportURL.getURL());
		Assert.assertEquals(url, "#top", staticSiteExportURL.getURIFragment());
	}

	private String _getArchivePath(String url) {
		StaticSiteExportURL staticSiteExportURL = new StaticSiteExportURL(url);

		return staticSiteExportURL.getArchivePath();
	}

	private String _getDigest(String queryString) {
		return StringUtil.toHexString(queryString.hashCode());
	}

	private boolean _isResource(String url) {
		StaticSiteExportURL staticSiteExportURL = new StaticSiteExportURL(url);

		return staticSiteExportURL.isResource();
	}

	private String _resolve(
		StaticSiteExportURL staticSiteExportURL, String relativeURL) {

		StaticSiteExportURL resolvedStaticSiteExportURL =
			staticSiteExportURL.resolve(relativeURL);

		if (resolvedStaticSiteExportURL == null) {
			return null;
		}

		return resolvedStaticSiteExportURL.getURL();
	}

}