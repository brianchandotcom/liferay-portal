/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletConfigFactory;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ConcurrentHashMapBuilder;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portlet.PortletPreferencesImpl;

import jakarta.portlet.PortletConfig;
import jakarta.portlet.PortletPreferences;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Jorge Avalos
 */
public class RenderResponseImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		LanguageUtil languageUtil = new LanguageUtil();

		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.isAvailableLocale(LocaleUtil.US)
		).thenReturn(
			true
		);

		languageUtil.setLanguage(language);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());
	}

	@Test
	public void testSetTitle() throws Exception {
		RenderResponseImpl renderResponseImpl = new RenderResponseImpl();

		PortletRequestImpl portletRequestImpl = Mockito.mock(
			PortletRequestImpl.class);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		PortletDisplay portletDisplay = Mockito.mock(PortletDisplay.class);

		PortletConfigFactory portletConfigFactory = Mockito.mock(
			PortletConfigFactory.class);

		PortletConfig portletConfig = Mockito.mock(PortletConfig.class);

		Mockito.when(
			portletConfigFactory.get(_portletId)
		).thenReturn(
			portletConfig
		);

		ResourceBundle testResourceBundle = new TestResourceBundle();

		Mockito.when(
			portletConfig.getResourceBundle(Mockito.any(Locale.class))
		).thenReturn(
			testResourceBundle
		);

		Mockito.when(
			portletDisplay.getId()
		).thenReturn(
			_portletId
		);

		PortletPreferences portletPreferences = new PortletPreferencesImpl();

		portletPreferences.setValue("portletSetupUseCustomTitle", "true");

		Mockito.when(
			portletDisplay.getPortletPreferences()
		).thenReturn(
			portletPreferences
		);

		Mockito.when(
			themeDisplay.getLanguageId()
		).thenReturn(
			"en_US"
		);

		Mockito.when(
			portletRequestImpl.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		Mockito.when(
			themeDisplay.getPortletDisplay()
		).thenReturn(
			portletDisplay
		);

		Map<String, PortletConfig> portletConfigs =
			HashMapBuilder.<String, PortletConfig>put(
				_portletId, portletConfig
			).build();

		Map<String, Map<String, PortletConfig>> pool =
			ConcurrentHashMapBuilder.<String, Map<String, PortletConfig>>put(
				StringBundler.concat(
					JavaConstants.JAKARTA_PORTLET_TITLE, StringPool.PERIOD,
					_portletId),
				portletConfigs
			).build();

		ReflectionTestUtil.setFieldValue(
			new PortletConfigFactoryUtil(), "_portletConfigFactory",
			portletConfigFactory);

		ReflectionTestUtil.setFieldValue(
			new PortletConfigFactoryImpl(), "_pool", pool);

		ReflectionTestUtil.setFieldValue(
			renderResponseImpl, "portletRequestImpl", portletRequestImpl);

		renderResponseImpl.setTitle("");

		Assert.assertEquals(_portletTitle, renderResponseImpl.getTitle());
	}

	public class TestResourceBundle extends ResourceBundle {

		public TestResourceBundle() {
			_data.put(JavaConstants.JAKARTA_PORTLET_TITLE, _portletTitle);
		}

		@Override
		public Enumeration<String> getKeys() {
			return new Vector<>(
				_data.keySet()
			).elements();
		}

		@Override
		protected Object handleGetObject(String key) {
			return _data.get(key);
		}

		private Map<String, Object> _data = new HashMap<>();

	}

	private String _portletId = RandomTestUtil.randomString();
	private String _portletTitle = RandomTestUtil.randomString();


}