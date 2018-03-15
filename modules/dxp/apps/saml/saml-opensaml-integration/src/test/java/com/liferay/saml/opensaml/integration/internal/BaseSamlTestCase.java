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

package com.liferay.saml.opensaml.integration.internal;

import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactory;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.opensaml.integration.SamlBinding;
import com.liferay.saml.opensaml.integration.internal.binding.HttpPostBinding;
import com.liferay.saml.opensaml.integration.internal.binding.HttpRedirectBinding;
import com.liferay.saml.opensaml.integration.internal.binding.HttpSoap11Binding;
import com.liferay.saml.opensaml.integration.internal.bootstrap.OpenSamlBootstrap;
import com.liferay.saml.opensaml.integration.internal.credential.FileSystemKeyStoreManagerImpl;
import com.liferay.saml.opensaml.integration.internal.credential.KeyStoreCredentialResolver;
import com.liferay.saml.opensaml.integration.internal.identifier.SamlIdentifierGenerator;
import com.liferay.saml.opensaml.integration.internal.metadata.MetadataGeneratorUtil;
import com.liferay.saml.opensaml.integration.internal.metadata.MetadataManagerImpl;
import com.liferay.saml.opensaml.integration.internal.provider.DBMetadataProvider;
import com.liferay.saml.opensaml.integration.internal.velocity.VelocityEngineFactory;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.util.PortletPropsKeys;

import java.io.UnsupportedEncodingException;

import java.lang.reflect.Field;

import java.net.URLDecoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.HttpClient;
import org.apache.velocity.app.VelocityEngine;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.opensaml.common.IdentifierGenerator;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.SingleLogoutService;
import org.opensaml.saml2.metadata.SingleSignOnService;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.criteria.EntityIDCriteria;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Mika Koivisto
 */
@RunWith(PowerMockRunner.class)
public abstract class BaseSamlTestCase extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		setupProps();

		OpenSamlBootstrap.bootstrap();

		setupConfiguration();
		setupIdentifiers();
		setupMetadata();
		setupParserPool();
		setupPortal();

		setupSamlBindings();
	}

	@After
	public void tearDown() {
		identifiers.clear();

		for (Class<?> serviceUtilClass : serviceUtilClasses) {
			clearService(serviceUtilClass);
		}

		ClassLoaderPool.unregister("saml-portlet");
	}

	protected void clearService(Class<?> serviceUtilClass) {
		try {
			Field field = serviceUtilClass.getDeclaredField("_service");

			field.setAccessible(true);

			field.set(serviceUtilClass, null);
		}
		catch (Exception e) {
		}
	}

	protected Credential getCredential(String entityId) throws Exception {
		EntityIDCriteria entityIdCriteria = new EntityIDCriteria(entityId);

		CriteriaSet criteriaSet = new CriteriaSet();

		criteriaSet.add(entityIdCriteria);

		if (entityId.equals(samlProviderConfiguration.entityId())) {
			return credentialResolver.resolveSingle(criteriaSet);
		}
		else {
			KeyStoreCredentialResolver keyStoreCredentialResolver =
				getKeyStoreCredentialResolver(entityId);

			return keyStoreCredentialResolver.resolveSingle(criteriaSet);
		}
	}

	protected KeyStoreCredentialResolver getKeyStoreCredentialResolver(
		String entityId) {

		KeyStoreCredentialResolver keyStoreCredentialResolver =
			new KeyStoreCredentialResolver();

		keyStoreCredentialResolver.setKeyStoreManager(keyStoreManager);

		SamlProviderConfigurationHelper peerSamlProviderConfigurationHelper =
			Mockito.mock(SamlProviderConfigurationHelper.class);

		SamlProviderConfiguration peerSamlProviderConfiguration = Mockito.mock(
			SamlProviderConfiguration.class);

		when(
			peerSamlProviderConfiguration.entityId()
		).thenReturn(
			entityId
		);

		String keyStoreCredentialPassword =
			samlProviderConfiguration.keyStoreCredentialPassword();

		when(
			peerSamlProviderConfiguration.keyStoreCredentialPassword()
		).thenReturn(
			keyStoreCredentialPassword
		);

		when(
			peerSamlProviderConfigurationHelper.getSamlProviderConfiguration()
		).thenReturn(
			peerSamlProviderConfiguration
		);

		keyStoreCredentialResolver.setSamlProviderConfigurationHelper(
			peerSamlProviderConfigurationHelper);

		return keyStoreCredentialResolver;
	}

	protected MockHttpServletRequest getMockHttpServletRequest(String url) {
		String protocol = url.substring(0, url.indexOf(":"));
		String queryString = StringPool.BLANK;
		String requestURI = StringPool.BLANK;
		String serverName = StringPool.BLANK;
		int serverPort = 80;

		if (url.indexOf(StringPool.COLON, protocol.length() + 3) > 0) {
			serverName = url.substring(
				protocol.length() + 3,
				url.indexOf(StringPool.COLON, protocol.length() + 3));
			serverPort = GetterUtil.getInteger(
				url.substring(
					url.indexOf(StringPool.COLON, protocol.length() + 3) + 1,
					url.indexOf(StringPool.SLASH, protocol.length() + 3)));
		}
		else {
			serverName = url.substring(
				protocol.length() + 3,
				url.indexOf(StringPool.SLASH, protocol.length() + 3));
		}

		if (url.indexOf(StringPool.QUESTION) > 0) {
			queryString = url.substring(url.indexOf(StringPool.QUESTION) + 1);
			requestURI = url.substring(
				url.indexOf(StringPool.SLASH, protocol.length() + 3),
				url.indexOf(StringPool.QUESTION));
		}
		else {
			requestURI = url.substring(
				url.indexOf(StringPool.SLASH, protocol.length() + 3));
		}

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest("GET", requestURI);

		mockHttpServletRequest.setQueryString(queryString);
		mockHttpServletRequest.setContextPath(StringPool.SLASH);
		mockHttpServletRequest.setSecure(protocol.equals("https"));
		mockHttpServletRequest.setServerPort(serverPort);
		mockHttpServletRequest.setServerName(serverName);

		if (Validator.isNull(queryString)) {
			return mockHttpServletRequest;
		}

		String[] parameters = StringUtil.split(
			queryString, StringPool.AMPERSAND);

		for (String parameter : parameters) {
			String[] kvp = StringUtil.split(parameter, StringPool.EQUAL);

			try {
				String value = URLDecoder.decode(kvp[1], StringPool.UTF8);

				mockHttpServletRequest.setParameter(kvp[0], value);
			}
			catch (UnsupportedEncodingException uee) {
			}
		}

		return mockHttpServletRequest;
	}

	protected <T> T getMockPortalService(
		Class<?> serviceUtilClass, Class<T> serviceClass) {

		return getMockService(
			serviceUtilClass, serviceClass, portalBeanLocator);
	}

	protected <T> T getMockPortletService(
		Class<?> serviceUtilClass, Class<T> serviceClass) {

		return getMockService(
			serviceUtilClass, serviceClass, portletBeanLocator);
	}

	protected <T> T getMockService(
		Class<?> serviceUtilClass, Class<T> serviceClass,
		BeanLocator beanLocator) {

		clearService(serviceUtilClass);

		serviceUtilClasses.add(serviceUtilClass);

		T service = mock(serviceClass);

		when(
			beanLocator.locate(serviceClass.getName())
		).thenReturn(
			service
		);

		return service;
	}

	protected void prepareIdentityProvider(String entityId) {
		when(
			samlProviderConfiguration.entityId()
		).thenReturn(
			entityId
		);

		when(
			samlProviderConfiguration.role()
		).thenReturn(
			"idp"
		);

		when(
			samlProviderConfigurationHelper.isRoleIdp()
		).thenReturn(
			true
		);
	}

	protected void prepareServiceProvider(String entityId) {
		when(
			samlProviderConfiguration.entityId()
		).thenReturn(
			entityId
		);

		when(
			samlProviderConfiguration.defaultIdPEntityId()
		).thenReturn(
			IDP_ENTITY_ID
		);

		when(
			samlProviderConfiguration.role()
		).thenReturn(
			"sp"
		);

		when(
			samlProviderConfigurationHelper.isRoleSp()
		).thenReturn(
			true
		);
	}

	protected void setupConfiguration() {
		Thread currentThread = Thread.currentThread();

		ClassLoaderPool.register(
			"saml-portlet", currentThread.getContextClassLoader());

		PortletClassLoaderUtil.setServletContextName("saml-portlet");

		Configuration configuration = mock(Configuration.class);

		ConfigurationFactory configurationFactory = mock(
			ConfigurationFactory.class);

		ConfigurationFactoryUtil.setConfigurationFactory(configurationFactory);

		when(
			configurationFactory.getConfiguration(
				Mockito.any(ClassLoader.class), Mockito.eq("portlet"))
		).thenReturn(
			configuration
		);

		when(
			configurationFactory.getConfiguration(
				Mockito.any(ClassLoader.class), Mockito.eq("service"))
		).thenReturn(
			configuration
		);

		when(
			configuration.get(PortletPropsKeys.SAML_KEYSTORE_MANAGER_IMPL)
		).thenReturn(
			FileSystemKeyStoreManagerImpl.class.getName()
		);

		samlProviderConfigurationHelper = mock(
			SamlProviderConfigurationHelper.class);

		when(
			samlProviderConfigurationHelper.isEnabled()
		).thenReturn(
			true
		);

		samlProviderConfiguration = mock(SamlProviderConfiguration.class);

		when(
			samlProviderConfiguration.defaultAssertionLifetime()
		).thenReturn(
			1800
		);

		when(
			samlProviderConfigurationHelper.getSamlProviderConfiguration()
		).thenReturn(
			samlProviderConfiguration
		);

		when(
			samlProviderConfiguration.enabled()
		).thenReturn(
			true
		);

		when(
			samlProviderConfiguration.keyStoreCredentialPassword()
		).thenReturn(
			"liferay"
		);
	}

	protected void setupIdentifiers() {
		identifierGenerator = mock(IdentifierGenerator.class);

		when(
			identifierGenerator.generateIdentifier(Mockito.anyInt())
		).thenAnswer(
			new Answer<String>() {

				@Override
				public String answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					int length = GetterUtil.getInteger(
						invocationOnMock.getArguments()[0]);

					String identifier =
						samlIdentifierGenerator.generateIdentifier(length);

					identifiers.add(identifier);

					return identifier;
				}

			}
		);
	}

	protected void setupMetadata() throws Exception {
		metadataManagerImpl = new MetadataManagerImpl();

		keyStoreManager = new FileSystemKeyStoreManagerImpl();

		Map<String, Object> properties = new HashMap<>();

		properties.put(
			"saml.keystore.path",
			"classpath:/com/liferay/saml/opensaml/integration/internal" +
				"/credential/dependencies/keystore.jks");

		keyStoreManager.activate(properties);

		credentialResolver = new KeyStoreCredentialResolver();

		credentialResolver.setKeyStoreManager(keyStoreManager);

		credentialResolver.setSamlProviderConfigurationHelper(
			samlProviderConfigurationHelper);

		metadataManagerImpl.setCredentialResolver(credentialResolver);

		metadataManagerImpl.setParserPool(parserPool);

		metadataManagerImpl.setMetadataProvider(new MockMetadataProvider());

		metadataManagerImpl.setSamlProviderConfigurationHelper(
			samlProviderConfigurationHelper);

		metadataManagerImpl.setHttp(HttpUtil.getHttp());
	}

	protected void setupParserPool() throws Exception {
		parserPool = org.opensaml.Configuration.getParserPool();
	}

	protected void setupPortal() throws Exception {
		httpClient = mock(HttpClient.class);

		PortalUtil portalUtil = new PortalUtil();

		portal = mock(Portal.class);

		portalUtil.setPortal(portal);

		when(
			portal.getCompanyId(Mockito.any(HttpServletRequest.class))
		).thenReturn(
			COMPANY_ID
		);

		when(
			portal.getPathContext()
		).thenReturn(
			""
		);

		when(
			portal.getPathMain()
		).thenReturn(
			Portal.PATH_MAIN
		);

		when(
			portal.getPortalURL(Mockito.any(MockHttpServletRequest.class))
		).thenReturn(
			PORTAL_URL
		);

		when(
			portal.getPortalURL(
				Mockito.any(MockHttpServletRequest.class), Mockito.eq(false))
		).thenReturn(
			PORTAL_URL
		);

		when(
			portal.getPortalURL(
				Mockito.any(MockHttpServletRequest.class), Mockito.eq(true))
		).thenReturn(
			StringUtil.replace(
				PORTAL_URL, new String[] {"http://", "https://"},
				new String[] {"8080", "8443"})
		);

		portalBeanLocator = mock(BeanLocator.class);

		PortalBeanLocatorUtil.setBeanLocator(portalBeanLocator);

		portletBeanLocator = mock(BeanLocator.class);

		PortletBeanLocatorUtil.setBeanLocator(
			"saml-portlet", portletBeanLocator);

		groupLocalService = getMockPortalService(
			GroupLocalServiceUtil.class, GroupLocalService.class);

		Group guestGroup = mock(Group.class);

		when(
			groupLocalService.getGroup(
				Mockito.anyLong(), Mockito.eq(GroupConstants.GUEST))
		).thenReturn(
			guestGroup
		);

		LayoutLocalService layoutLocalService = getMockPortalService(
			LayoutLocalServiceUtil.class, LayoutLocalService.class);

		when(
			layoutLocalService.getDefaultPlid(
				Mockito.anyLong(), Mockito.anyBoolean())
		).thenReturn(
			1L
		);

		userLocalService = getMockPortalService(
			UserLocalServiceUtil.class, UserLocalService.class);
	}

	protected void setupProps() {
		props = mock(Props.class);

		PropsUtil.setProps(props);

		when(
			props.get(PropsKeys.LIFERAY_HOME)
		).thenReturn(
			System.getProperty("java.io.tmpdir")
		);
	}

	protected void setupSamlBindings() {
		VelocityEngineFactory velocityEngineFactory =
			new VelocityEngineFactory();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		VelocityEngine velocityEngine = velocityEngineFactory.getVelocityEngine(
			contextClassLoader);

		samlBindings = new ArrayList<>();

		samlBindings.add(new HttpPostBinding(parserPool, velocityEngine));
		samlBindings.add(new HttpRedirectBinding(parserPool));
		samlBindings.add(new HttpSoap11Binding(parserPool, httpClient));
	}

	protected static final String ACS_URL =
		"http://localhost:8080/c/portal/saml/acs";

	protected static final long COMPANY_ID = 1;

	protected static final String IDP_ENTITY_ID = "testidp";

	protected static final String LOGIN_URL =
		"http://localhost:8080/c/portal/login";

	protected static final String LOGOUT_URL =
		"http://localhost:8080/c/portal/logout";

	protected static final String METADATA_URL =
		"http://localhost:8080/c/portal/saml/metadata";

	protected static final String PORTAL_URL = "http://localhost:8080";

	protected static final String RELAY_STATE =
		"http://localhost:8080/relaystate";

	protected static final long SESSION_ID = 2;

	protected static final String SLO_LOGOUT_URL =
		"http://localhost:8080/c/portal/saml/slo_logout";

	protected static final String SP_ENTITY_ID = "testsp";

	protected static final String SSO_URL =
		"http://localhost:8080/c/portal/saml/sso";

	protected static final String UNKNOWN_ENTITY_ID = "testunknown";

	protected KeyStoreCredentialResolver credentialResolver;
	protected GroupLocalService groupLocalService;
	protected HttpClient httpClient;
	protected IdentifierGenerator identifierGenerator;
	protected List<String> identifiers = new ArrayList<>();
	protected FileSystemKeyStoreManagerImpl keyStoreManager;
	protected MetadataManagerImpl metadataManagerImpl;
	protected ParserPool parserPool;
	protected Portal portal;
	protected BeanLocator portalBeanLocator;
	protected BeanLocator portletBeanLocator;
	protected Props props;
	protected List<SamlBinding> samlBindings;
	protected IdentifierGenerator samlIdentifierGenerator =
		new SamlIdentifierGenerator();
	protected SamlProviderConfiguration samlProviderConfiguration;
	protected SamlProviderConfigurationHelper samlProviderConfigurationHelper;
	protected List<Class<?>> serviceUtilClasses = new ArrayList<>();
	protected UserLocalService userLocalService;

	private class MockMetadataProvider extends DBMetadataProvider {

		public MockMetadataProvider() {
		}

		@Override
		public EntityDescriptor getEntityDescriptor(String entityId)
			throws MetadataProviderException {

			try {
				return doGetEntityDecriptor(entityId);
			}
			catch (Exception e) {
				throw new MetadataProviderException(e);
			}
		}

		protected EntityDescriptor doGetEntityDecriptor(String entityId)
			throws Exception {

			MockHttpServletRequest mockHttpServletRequest =
				getMockHttpServletRequest(
					"http://localhost:8080/c/portal/saml/metadata");

			KeyStoreCredentialResolver keyStoreCredentialResolver =
				getKeyStoreCredentialResolver(entityId);

			CriteriaSet criteriaSet = new CriteriaSet();

			EntityIDCriteria entityIdCriteria = new EntityIDCriteria(entityId);

			criteriaSet.add(entityIdCriteria);

			Credential credential = keyStoreCredentialResolver.resolveSingle(
				criteriaSet);

			if (entityId.equals(IDP_ENTITY_ID)) {
				EntityDescriptor entityDescriptor =
					MetadataGeneratorUtil.buildIdpEntityDescriptor(
						mockHttpServletRequest, entityId, true, true, false,
						credential);

				IDPSSODescriptor idpSsoDescriptor =
					entityDescriptor.getIDPSSODescriptor(
						SAMLConstants.SAML20P_NS);

				List<SingleLogoutService> singleLogoutServices =
					idpSsoDescriptor.getSingleLogoutServices();

				for (SingleLogoutService singleLogoutService :
						singleLogoutServices) {

					String binding = singleLogoutService.getBinding();

					if (binding.equals(SAMLConstants.SAML2_POST_BINDING_URI)) {
						singleLogoutServices.remove(singleLogoutService);

						break;
					}
				}

				List<SingleSignOnService> singleSignOnServices =
					idpSsoDescriptor.getSingleSignOnServices();

				for (SingleSignOnService singleSignOnService :
						singleSignOnServices) {

					String binding = singleSignOnService.getBinding();

					if (binding.equals(SAMLConstants.SAML2_POST_BINDING_URI)) {
						singleSignOnServices.remove(singleSignOnService);

						break;
					}
				}

				return entityDescriptor;
			}
			else if (entityId.equals(SP_ENTITY_ID)) {
				return MetadataGeneratorUtil.buildSpEntityDescriptor(
					mockHttpServletRequest, entityId, true, true, false, false,
					credential);
			}

			return null;
		}

	}

}