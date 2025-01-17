/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.web.internal.struts;

import com.liferay.layout.seo.kernel.LayoutSEOLink;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListMergeable;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.struts.Definition;
import com.liferay.portal.struts.TilesUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Pedro Victor Silvestre
 */
public class SamlLoginActionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testPageTitleIsSameWhenRedirectMessageIsDisabled()
		throws Exception {

		SamlLoginAction samlLoginAction = _mockSamlLoginAction();

		String companyName = RandomTestUtil.randomString();
		String htmlTitle = RandomTestUtil.randomString();

		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest(companyName, htmlTitle);

		samlLoginAction.execute(
			mockHttpServletRequest, new MockHttpServletResponse());

		Definition definition = (Definition)mockHttpServletRequest.getAttribute(
			TilesUtil.DEFINITION);

		Map<String, String> definitionAttributes = definition.getAttributes();

		Assert.assertEquals(
			StringUtil.merge(new String[] {htmlTitle, companyName}, _SEPARATOR),
			definitionAttributes.get("title"));
	}

	private LayoutSEOLinkManager _getLayoutSEOLinkManager() {
		return new LayoutSEOLinkManager() {

			@Override
			public LayoutSEOLink getCanonicalLayoutSEOLink(
					Layout layout, Locale locale, String canonicalURL,
					ThemeDisplay themeDisplay)
				throws PortalException {

				return null;
			}

			@Override
			public String getFullPageTitle(
				Layout layout, String portletId, String tilesTitle,
				ListMergeable<String> titleListMergeable,
				ListMergeable<String> subtitleListMergeable, String companyName,
				Locale locale) {

				return StringUtil.merge(
					new String[] {layout.getHTMLTitle(locale), companyName},
					_SEPARATOR);
			}

			@Override
			public List<LayoutSEOLink> getLocalizedLayoutSEOLinks(
					Layout layout, Locale locale, String canonicalURL,
					Set<Locale> availableLocales)
				throws PortalException {

				return null;
			}

		};
	}

	private MockHttpServletRequest _getMockHttpServletRequest(
		String companyName, String htmlTitle) {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		Layout layout = Mockito.mock(Layout.class);

		Mockito.when(
			layout.getHTMLTitle(Mockito.any(Locale.class))
		).thenReturn(
			htmlTitle
		);

		Company company = Mockito.mock(Company.class);

		Mockito.when(
			company.getName()
		).thenReturn(
			companyName
		);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getCompany()
		).thenReturn(
			company
		);

		Mockito.when(
			themeDisplay.getLayout()
		).thenReturn(
			layout
		);

		Mockito.when(
			themeDisplay.getLocale()
		).thenReturn(
			LocaleUtil.ENGLISH
		);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	private Portal _mockPortal() throws Exception {
		Portal portal = Mockito.mock(Portal.class);

		Mockito.when(
			portal.getCompanyId(Mockito.any(HttpServletRequest.class))
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		return portal;
	}

	private SamlLoginAction _mockSamlLoginAction() throws Exception {
		SamlLoginAction samlLoginAction = new SamlLoginAction();

		ReflectionTestUtil.setFieldValue(
			samlLoginAction, "_samlProviderConfigurationHelper",
			_mockSamlProviderConfigurationHelper());

		Props props = Mockito.mock(Props.class);

		Mockito.when(
			props.get("saml.idp.redirect.message.enabled")
		).thenReturn(
			"false"
		);

		ReflectionTestUtil.setFieldValue(samlLoginAction, "_props", props);

		ReflectionTestUtil.setFieldValue(
			samlLoginAction, "_portal", _mockPortal());

		ReflectionTestUtil.setFieldValue(
			samlLoginAction, "_samlSpIdpConnectionLocalService",
			_mockSamlSpIdpConnectionLocalService());

		JSONFactory jsonFactory = Mockito.mock(JSONFactory.class);

		Mockito.when(
			jsonFactory.createJSONArray()
		).thenReturn(
			JSONFactoryUtil.createJSONArray()
		);

		ReflectionTestUtil.setFieldValue(
			samlLoginAction, "_jsonFactory", jsonFactory);

		ReflectionTestUtil.setFieldValue(
			samlLoginAction, "_layoutSEOLinkManager",
			_getLayoutSEOLinkManager());

		return samlLoginAction;
	}

	private SamlProviderConfigurationHelper
		_mockSamlProviderConfigurationHelper() {

		SamlProviderConfigurationHelper samlProviderConfigurationHelper =
			Mockito.mock(SamlProviderConfigurationHelper.class);

		Mockito.when(
			samlProviderConfigurationHelper.isRoleSp()
		).thenReturn(
			true
		);

		Mockito.when(
			samlProviderConfigurationHelper.isEnabled()
		).thenReturn(
			true
		);

		return samlProviderConfigurationHelper;
	}

	private SamlSpIdpConnectionLocalService
		_mockSamlSpIdpConnectionLocalService() {

		SamlSpIdpConnectionLocalService samlSpIdpConnectionLocalService =
			Mockito.mock(SamlSpIdpConnectionLocalService.class);

		List<SamlSpIdpConnection> samlSpIdpConnections = new ArrayList<>();

		SamlSpIdpConnection samlSpIdpConnection = Mockito.mock(
			SamlSpIdpConnection.class);

		samlSpIdpConnections.add(samlSpIdpConnection);

		Mockito.when(
			samlSpIdpConnectionLocalService.getSamlSpIdpConnections(
				Mockito.anyLong())
		).thenReturn(
			samlSpIdpConnections
		);

		Mockito.when(
			samlSpIdpConnection.isEnabled()
		).thenReturn(
			true
		);

		_listUtilMockedStatic.when(
			() -> ListUtil.filter(
				Mockito.anyList(), Mockito.any(Predicate.class))
		).thenReturn(
			samlSpIdpConnections
		);

		return samlSpIdpConnectionLocalService;
	}

	private static final String _SEPARATOR = " - ";

	private static final MockedStatic<ListUtil> _listUtilMockedStatic =
		Mockito.mockStatic(ListUtil.class);

}