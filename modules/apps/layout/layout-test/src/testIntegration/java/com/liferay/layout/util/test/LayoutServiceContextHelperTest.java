/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.LayoutServiceContextHelper;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyInheritableThreadLocalCallable;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.context.Context;
import com.liferay.segments.context.RequestContextMapper;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletRequestWrapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Georgel Pop
 */
@RunWith(Arquillian.class)
public class LayoutServiceContextHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	@TestInfo(
		{
			"LPD-79722", "LPD-99386", "LPD-102690", "LPD-103697", "LPD-103742",
			"LPD-105674", "LPD-105885", "LPD-117934"
		}
	)
	public void testGetServiceContextAutoCloseable() throws Exception {
		_testGetServiceContextAutoCloseable();
		_testGetServiceContextAutoCloseablePortalURL();
		_testGetServiceContextAutoCloseablePortalURLRequest();
		_testGetServiceContextAutoCloseablePortalURLThemeDisplay();
		_testGetServiceContextAutoCloseableWithCallerRequest();
		_testGetServiceContextAutoCloseableWithCallerRequestValues();
		_testGetServiceContextAutoCloseableWithConcurrentSwaps();
		_testGetServiceContextAutoCloseableWithLocale();
		_testGetServiceContextAutoCloseableWithNullParameterValues();
		_testGetServiceContextAutoCloseableWithRequestAttributes();
		_testGetServiceContextAutoCloseableWithThemeDisplay();
	}

	private void _assertDoesNotWrap(
		HttpServletRequest httpServletRequest, ServletRequest servletRequest) {

		ServletRequest currentServletRequest = servletRequest;

		Assert.assertNotSame(httpServletRequest, currentServletRequest);

		while (currentServletRequest instanceof
					ServletRequestWrapper servletRequestWrapper) {

			currentServletRequest = servletRequestWrapper.getRequest();

			Assert.assertNotSame(httpServletRequest, currentServletRequest);
		}
	}

	private FutureTask<Void> _getFutureTask(
		Layout layout, CountDownLatch openCountDownLatch,
		CountDownLatch otherOpenCountDownLatch, ServiceContext serviceContext) {

		return new FutureTask<>(
			new CompanyInheritableThreadLocalCallable<>(
				() -> {
					ServiceContextThreadLocal.pushServiceContext(
						(ServiceContext)serviceContext.clone());

					try (AutoCloseable autoCloseable =
							_layoutServiceContextHelper.
								getServiceContextAutoCloseable(layout)) {

						ServiceContext currentServiceContext =
							ServiceContextThreadLocal.getServiceContext();

						HttpServletRequest currentHttpServletRequest =
							currentServiceContext.getRequest();

						String includeServletPath =
							RandomTestUtil.randomString();

						currentHttpServletRequest.setAttribute(
							JavaConstants.JAKARTA_SERVLET_INCLUDE_SERVLET_PATH,
							includeServletPath);

						openCountDownLatch.countDown();

						Assert.assertTrue(
							otherOpenCountDownLatch.await(1, TimeUnit.MINUTES));

						ThemeDisplay themeDisplay =
							currentServiceContext.getThemeDisplay();

						Assert.assertEquals(
							layout.getPlid(), themeDisplay.getPlid());

						Assert.assertEquals(
							includeServletPath,
							currentHttpServletRequest.getAttribute(
								JavaConstants.
									JAKARTA_SERVLET_INCLUDE_SERVLET_PATH));
					}
					finally {
						openCountDownLatch.countDown();

						ServiceContextThreadLocal.popServiceContext();
					}

					return null;
				}));
	}

	private void _testGetServiceContextAutoCloseable() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addTypeContentLayout(group);

		try (AutoCloseable autoCloseable =
				_layoutServiceContextHelper.getServiceContextAutoCloseable(
					layout)) {

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			HttpServletRequest httpServletRequest = serviceContext.getRequest();

			Assert.assertNotNull(httpServletRequest.getContextPath());

			Cookie[] cookies = httpServletRequest.getCookies();

			Assert.assertEquals(Arrays.toString(cookies), 0, cookies.length);

			Assert.assertNotNull(httpServletRequest.getLocale());

			List<Locale> locales = Collections.list(
				httpServletRequest.getLocales());

			Assert.assertFalse(locales.isEmpty());

			Assert.assertEquals(
				HttpMethods.GET, httpServletRequest.getMethod());

			Map<String, String[]> parameterMap =
				httpServletRequest.getParameterMap();

			Assert.assertTrue(parameterMap.isEmpty());

			String[] parameterValues = httpServletRequest.getParameterValues(
				RandomTestUtil.randomString());

			Assert.assertEquals(
				Arrays.toString(parameterValues), 0, parameterValues.length);

			Assert.assertEquals(
				StringPool.SLASH, httpServletRequest.getRequestURI());

			ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

			Assert.assertEquals(
				themeDisplay.isSecure(),
				Objects.equals(Http.HTTPS, httpServletRequest.getScheme()));

			Assert.assertNotNull(httpServletRequest.getServletContext());
			Assert.assertNotNull(httpServletRequest.getSession());
		}
	}

	private void _testGetServiceContextAutoCloseablePortalURL()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(
			GroupTestUtil.addGroup());

		try (AutoCloseable autoCloseable =
				_layoutServiceContextHelper.getServiceContextAutoCloseable(
					layout)) {

			Company company = _companyLocalService.getCompany(
				layout.getCompanyId());

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

			Assert.assertEquals(
				themeDisplay.getPortalURL(), themeDisplay.getCDNBaseURL());
			Assert.assertEquals(
				company.getVirtualHostname(), themeDisplay.getServerName());
		}
	}

	private void _testGetServiceContextAutoCloseablePortalURLRequest()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(
			GroupTestUtil.addGroup());

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		String serverName = RandomTestUtil.randomString();

		mockHttpServletRequest.setServerName(serverName);

		int serverPort = RandomTestUtil.randomInt();

		mockHttpServletRequest.setServerPort(serverPort);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setRequest(mockHttpServletRequest);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try (AutoCloseable autoCloseable =
				_layoutServiceContextHelper.getServiceContextAutoCloseable(
					layout)) {

			ServiceContext currentServiceContext =
				ServiceContextThreadLocal.getServiceContext();

			ThemeDisplay themeDisplay = currentServiceContext.getThemeDisplay();

			Assert.assertEquals(serverName, themeDisplay.getServerName());
			Assert.assertEquals(serverPort, themeDisplay.getServerPort());

			String portalURL = _portal.getPortalURL(mockHttpServletRequest);

			Assert.assertEquals(portalURL, themeDisplay.getPortalURL());
			Assert.assertEquals(
				portalURL,
				_portal.getPortalURL(currentServiceContext.getRequest()));
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	private void _testGetServiceContextAutoCloseablePortalURLThemeDisplay()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(
			GroupTestUtil.addGroup());

		ServiceContext serviceContext = new ServiceContext();

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		ThemeDisplay originalThemeDisplay = new ThemeDisplay();

		Locale locale = LocaleUtil.GERMANY;

		originalThemeDisplay.setLanguageId(LocaleUtil.toLanguageId(locale));
		originalThemeDisplay.setLocale(locale);

		String serverName = RandomTestUtil.randomString();

		originalThemeDisplay.setPortalDomain(serverName);

		int serverPort = RandomTestUtil.randomInt();

		String portalURL = StringBundler.concat(
			Http.HTTPS_WITH_SLASH, serverName, StringPool.COLON, serverPort);

		originalThemeDisplay.setPortalURL(portalURL);

		originalThemeDisplay.setSecure(true);
		originalThemeDisplay.setServerName(serverName);
		originalThemeDisplay.setServerPort(serverPort);

		httpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, originalThemeDisplay);

		serviceContext.setRequest(httpServletRequest);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try (AutoCloseable autoCloseable =
				_layoutServiceContextHelper.getServiceContextAutoCloseable(
					layout)) {

			ServiceContext currentServiceContext =
				ServiceContextThreadLocal.getServiceContext();

			ThemeDisplay themeDisplay = currentServiceContext.getThemeDisplay();

			Assert.assertEquals(portalURL, themeDisplay.getCDNBaseURL());
			Assert.assertEquals(
				layout.getDefaultLanguageId(), themeDisplay.getLanguageId());
			Assert.assertEquals(
				LocaleUtil.fromLanguageId(layout.getDefaultLanguageId()),
				themeDisplay.getLocale());

			String pathThemeImages = themeDisplay.getPathThemeImages();

			Assert.assertTrue(
				pathThemeImages, pathThemeImages.startsWith(portalURL));

			Assert.assertEquals(serverName, themeDisplay.getPortalDomain());
			Assert.assertEquals(portalURL, themeDisplay.getPortalURL());
			Assert.assertEquals(serverName, themeDisplay.getServerName());
			Assert.assertEquals(serverPort, themeDisplay.getServerPort());
			Assert.assertEquals(
				portalURL + _portal.getPathContext(),
				themeDisplay.getURLPortal());
			Assert.assertNotSame(originalThemeDisplay, themeDisplay);
			Assert.assertTrue(themeDisplay.isSecure());
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		Assert.assertEquals(locale, originalThemeDisplay.getLocale());
		Assert.assertSame(
			originalThemeDisplay,
			httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY));
	}

	private void _testGetServiceContextAutoCloseableWithCallerRequest()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(
			GroupTestUtil.addGroup());

		HttpServletResponse httpServletResponse = new MockHttpServletResponse();
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setResponse(httpServletResponse);

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setRequest(httpServletRequest);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try (AutoCloseable autoCloseable =
				_layoutServiceContextHelper.getServiceContextAutoCloseable(
					layout)) {

			ServiceContext currentServiceContext =
				ServiceContextThreadLocal.getServiceContext();

			HttpServletRequest currentHttpServletRequest =
				currentServiceContext.getRequest();

			_assertDoesNotWrap(httpServletRequest, currentHttpServletRequest);

			ThemeDisplay currentThemeDisplay =
				currentServiceContext.getThemeDisplay();

			_assertDoesNotWrap(
				httpServletRequest, currentThemeDisplay.getRequest());

			Assert.assertNotSame(
				httpServletResponse, currentThemeDisplay.getResponse());

			currentHttpServletRequest.setAttribute(
				JavaConstants.JAKARTA_SERVLET_INCLUDE_SERVLET_PATH,
				RandomTestUtil.randomString());

			HttpSession httpSession = currentHttpServletRequest.getSession();

			httpSession.setAttribute(
				RandomTestUtil.randomString(), RandomTestUtil.randomString());
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		Assert.assertNull(
			httpServletRequest.getAttribute(
				JavaConstants.JAKARTA_SERVLET_INCLUDE_SERVLET_PATH));
		Assert.assertNull(httpServletRequest.getSession(false));
	}

	private void _testGetServiceContextAutoCloseableWithCallerRequestValues()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(
			GroupTestUtil.addGroup());

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(
				HttpMethods.GET,
				StringPool.SLASH + RandomTestUtil.randomString());

		String cookieValue = RandomTestUtil.randomString();

		Cookie cookie = new Cookie(RandomTestUtil.randomString(), cookieValue);

		mockHttpServletRequest.setCookies(cookie);

		String headerName = RandomTestUtil.randomString();
		String headerValue1 = RandomTestUtil.randomString();

		mockHttpServletRequest.addHeader(headerName, headerValue1);

		String headerValue2 = RandomTestUtil.randomString();

		mockHttpServletRequest.addHeader(headerName, headerValue2);

		String userAgent = RandomTestUtil.randomString();

		mockHttpServletRequest.addHeader(HttpHeaders.USER_AGENT, userAgent);

		String parameterName = RandomTestUtil.randomString();
		String parameterValue1 = RandomTestUtil.randomString();
		String parameterValue2 = RandomTestUtil.randomString();

		mockHttpServletRequest.addParameter(
			parameterName, parameterValue1, parameterValue2);

		String queryString = StringBundler.concat(
			parameterName, StringPool.EQUAL, parameterValue1,
			StringPool.AMPERSAND, parameterName, StringPool.EQUAL,
			parameterValue2);

		mockHttpServletRequest.setQueryString(queryString);

		String remoteAddr = RandomTestUtil.randomString();

		mockHttpServletRequest.setRemoteAddr(remoteAddr);

		String remoteHost = RandomTestUtil.randomString();

		mockHttpServletRequest.setRemoteHost(remoteHost);

		mockHttpServletRequest.setAttribute(WebKeys.LOCALE, LocaleUtil.GERMANY);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		List<String> attributeNames = Collections.list(
			mockHttpServletRequest.getAttributeNames());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setRequest(mockHttpServletRequest);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try (AutoCloseable autoCloseable =
				_layoutServiceContextHelper.getServiceContextAutoCloseable(
					layout)) {

			cookie.setValue(RandomTestUtil.randomString());

			mockHttpServletRequest.addParameter(
				RandomTestUtil.randomString(), RandomTestUtil.randomString());

			ServiceContext currentServiceContext =
				ServiceContextThreadLocal.getServiceContext();

			HttpServletRequest currentHttpServletRequest =
				currentServiceContext.getRequest();

			Cookie[] cookies = currentHttpServletRequest.getCookies();

			Assert.assertEquals(Arrays.toString(cookies), 1, cookies.length);

			Cookie currentCookie = cookies[0];

			Assert.assertEquals(cookie.getName(), currentCookie.getName());
			Assert.assertEquals(cookieValue, currentCookie.getValue());

			Assert.assertEquals(
				headerValue1,
				currentHttpServletRequest.getHeader(
					StringUtil.toUpperCase(headerName)));
			Assert.assertEquals(
				Arrays.asList(headerValue1, headerValue2),
				Collections.list(
					currentHttpServletRequest.getHeaders(headerName)));
			Assert.assertEquals(
				userAgent,
				currentHttpServletRequest.getHeader(HttpHeaders.USER_AGENT));

			Assert.assertEquals(
				parameterValue1,
				currentHttpServletRequest.getParameter(parameterName));

			Map<String, String[]> parameterMap =
				currentHttpServletRequest.getParameterMap();

			Assert.assertEquals(
				parameterMap.toString(), 1, parameterMap.size());

			Assert.assertArrayEquals(
				new String[] {parameterValue1, parameterValue2},
				currentHttpServletRequest.getParameterValues(parameterName));
			Assert.assertNull(
				currentHttpServletRequest.getParameterValues(
					RandomTestUtil.randomString()));

			Assert.assertEquals(
				queryString, currentHttpServletRequest.getQueryString());
			Assert.assertEquals(
				remoteAddr, currentHttpServletRequest.getRemoteAddr());
			Assert.assertEquals(
				remoteHost, currentHttpServletRequest.getRemoteHost());

			String requestURL = String.valueOf(
				mockHttpServletRequest.getRequestURL());

			Assert.assertEquals(
				requestURL,
				String.valueOf(currentHttpServletRequest.getRequestURL()));

			Assert.assertEquals(
				LocaleUtil.fromLanguageId(layout.getDefaultLanguageId()),
				currentHttpServletRequest.getAttribute(WebKeys.LOCALE));

			Object currentThemeDisplay = currentHttpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

			Assert.assertNotSame(themeDisplay, currentThemeDisplay);
			Assert.assertSame(
				currentServiceContext.getThemeDisplay(), currentThemeDisplay);

			Context context = _requestContextMapper.map(
				currentHttpServletRequest);

			Assert.assertArrayEquals(
				new String[] {
					cookie.getName() + StringPool.EQUAL + cookieValue
				},
				(String[])context.get(Context.COOKIES));
			Assert.assertEquals(
				layout.getDefaultLanguageId(),
				context.get(Context.LANGUAGE_ID));
			Assert.assertArrayEquals(
				new String[] {
					StringBundler.concat(
						parameterName, StringPool.EQUAL, parameterValue1,
						StringPool.COMMA, parameterValue2)
				},
				(String[])context.get(Context.REQUEST_PARAMETERS));
			Assert.assertEquals(
				requestURL + StringPool.QUESTION + queryString,
				context.get(Context.URL));
			Assert.assertEquals(userAgent, context.get(Context.USER_AGENT));
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		Assert.assertEquals(
			attributeNames,
			Collections.list(mockHttpServletRequest.getAttributeNames()));
		Assert.assertNull(mockHttpServletRequest.getSession(false));
	}

	private void _testGetServiceContextAutoCloseableWithConcurrentSwaps()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(group.getCompanyId());
		serviceContext.setRequest(new MockHttpServletRequest());
		serviceContext.setUserId(TestPropsValues.getUserId());

		CountDownLatch openCountDownLatch1 = new CountDownLatch(1);
		CountDownLatch openCountDownLatch2 = new CountDownLatch(1);

		FutureTask<Void> futureTask1 = _getFutureTask(
			LayoutTestUtil.addTypeContentLayout(group), openCountDownLatch1,
			openCountDownLatch2, serviceContext);
		FutureTask<Void> futureTask2 = _getFutureTask(
			LayoutTestUtil.addTypeContentLayout(group), openCountDownLatch2,
			openCountDownLatch1, serviceContext);

		Thread thread1 = new Thread(
			futureTask1, "Layout Service Context Helper Test 1");
		Thread thread2 = new Thread(
			futureTask2, "Layout Service Context Helper Test 2");

		thread1.start();
		thread2.start();

		futureTask1.get();
		futureTask2.get();
	}

	private void _testGetServiceContextAutoCloseableWithLocale()
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		Locale locale = LocaleUtil.GERMANY;

		httpServletRequest.setAttribute(WebKeys.LOCALE, locale);

		serviceContext.setRequest(httpServletRequest);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		Layout layout = LayoutTestUtil.addTypeContentLayout(
			GroupTestUtil.addGroup());

		try (AutoCloseable autoCloseable =
				_layoutServiceContextHelper.getServiceContextAutoCloseable(
					layout)) {

			ServiceContext currentServiceContext =
				ServiceContextThreadLocal.getServiceContext();

			HttpServletRequest currentHttpServletRequest =
				currentServiceContext.getRequest();

			Assert.assertEquals(
				LocaleUtil.fromLanguageId(layout.getDefaultLanguageId()),
				currentHttpServletRequest.getAttribute(WebKeys.LOCALE));

			Assert.assertEquals(
				locale, httpServletRequest.getAttribute(WebKeys.LOCALE));
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		Assert.assertEquals(
			locale, httpServletRequest.getAttribute(WebKeys.LOCALE));
	}

	private void _testGetServiceContextAutoCloseableWithNullParameterValues()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(
			GroupTestUtil.addGroup());

		String nullParameterName = RandomTestUtil.randomString();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest() {

				@Override
				public Map<String, String[]> getParameterMap() {
					Map<String, String[]> parameterMap = new HashMap<>(
						super.getParameterMap());

					parameterMap.put(nullParameterName, null);

					return parameterMap;
				}

			};

		String parameterName = RandomTestUtil.randomString();
		String parameterValue = RandomTestUtil.randomString();

		mockHttpServletRequest.addParameter(parameterName, parameterValue);

		List<String> attributeNames = Collections.list(
			mockHttpServletRequest.getAttributeNames());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setRequest(mockHttpServletRequest);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try (AutoCloseable autoCloseable =
				_layoutServiceContextHelper.getServiceContextAutoCloseable(
					layout)) {

			ServiceContext currentServiceContext =
				ServiceContextThreadLocal.getServiceContext();

			HttpServletRequest currentHttpServletRequest =
				currentServiceContext.getRequest();

			Assert.assertEquals(
				parameterValue,
				currentHttpServletRequest.getParameter(parameterName));

			String missingParameterName = RandomTestUtil.randomString();

			Assert.assertEquals(
				currentHttpServletRequest.getParameter(missingParameterName),
				currentHttpServletRequest.getParameter(nullParameterName));
			Assert.assertArrayEquals(
				currentHttpServletRequest.getParameterValues(
					missingParameterName),
				currentHttpServletRequest.getParameterValues(
					nullParameterName));
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		Assert.assertEquals(
			attributeNames,
			Collections.list(mockHttpServletRequest.getAttributeNames()));
		Assert.assertNull(mockHttpServletRequest.getSession(false));
	}

	private void _testGetServiceContextAutoCloseableWithRequestAttributes()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addTypeContentLayout(group);

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setRequest(httpServletRequest);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try (AutoCloseable autoCloseable =
				_layoutServiceContextHelper.getServiceContextAutoCloseable(
					layout)) {

			ServiceContext currentServiceContext =
				ServiceContextThreadLocal.getServiceContext();

			ThemeDisplay currentThemeDisplay =
				currentServiceContext.getThemeDisplay();

			String cdnBaseURL = currentThemeDisplay.getCDNBaseURL();

			Assert.assertFalse(cdnBaseURL, cdnBaseURL.contains("null"));

			Assert.assertEquals(
				layout.getPlid(), currentThemeDisplay.getPlid());

			String urlPortal = currentThemeDisplay.getURLPortal();

			Assert.assertFalse(urlPortal, urlPortal.contains("null"));

			Assert.assertSame(
				themeDisplay,
				httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY));
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		Assert.assertSame(
			themeDisplay,
			httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY));
	}

	private void _testGetServiceContextAutoCloseableWithThemeDisplay()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addTypeContentLayout(group);

		User user = TestPropsValues.getUser();

		try (AutoCloseable autoCloseable =
				_layoutServiceContextHelper.getServiceContextAutoCloseable(
					layout, user)) {

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

			String cdnBaseURL = themeDisplay.getCDNBaseURL();

			Assert.assertFalse(cdnBaseURL, cdnBaseURL.contains("null"));

			String pathThemeImages = themeDisplay.getPathThemeImages();

			Assert.assertFalse(
				pathThemeImages, pathThemeImages.contains("null"));

			Assert.assertEquals(
				_portal.getPathMain(), themeDisplay.getPathMain());
			Assert.assertTrue(themeDisplay.isSignedIn());
		}
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private LayoutServiceContextHelper _layoutServiceContextHelper;

	@Inject
	private Portal _portal;

	@Inject
	private RequestContextMapper _requestContextMapper;

}