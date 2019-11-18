/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.opensaml.integration.internal;

import com.liferay.petra.lang.ClassLoaderPool;
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
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
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
import com.liferay.saml.opensaml.integration.internal.identifier.SamlIdentifierGeneratorStrategyFactory;
import com.liferay.saml.opensaml.integration.internal.metadata.MetadataGeneratorUtil;
import com.liferay.saml.opensaml.integration.internal.metadata.MetadataManagerImpl;
import com.liferay.saml.opensaml.integration.internal.servlet.profile.IdentifierGenerationStrategyFactory;
import com.liferay.saml.opensaml.integration.internal.velocity.VelocityEngineFactory;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.metadata.LocalEntityManager;
import com.liferay.saml.util.PortletPropsKeys;

import java.io.UnsupportedEncodingException;

import java.lang.reflect.Field;

import java.net.URLDecoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import net.shibboleth.utilities.java.support.security.IdentifierGenerationStrategy;
import net.shibboleth.utilities.java.support.xml.ParserPool;

import org.apache.http.client.HttpClient;
import org.apache.velocity.app.VelocityEngine;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.core.xml.config.XMLObjectProviderRegistry;
import org.opensaml.saml.common.xml.SAMLConstants;
import org.opensaml.saml.metadata.resolver.impl.AbstractMetadataResolver;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml.saml2.metadata.SingleLogoutService;
import org.opensaml.saml.saml2.metadata.SingleSignOnService;
import org.opensaml.security.credential.Credential;

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
		setupParserPool();

		setupPortal();

		setupMetadata();

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
		EntityIdCriterion entityIdCriterion = new EntityIdCriterion(entityId);

		CriteriaSet criteriaSet = new CriteriaSet();

		criteriaSet.add(entityIdCriterion);

		if (entityId.equals(samlProviderConfiguration.entityId())) {
			return credentialResolver.resolveSingle(criteriaSet);
		}

		KeyStoreCredentialResolver keyStoreCredentialResolver =
			getKeyStoreCredentialResolver(entityId);

		return keyStoreCredentialResolver.resolveSingle(criteriaSet);
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
		SamlIdentifierGeneratorStrategyFactory
			samlIdentifierGeneratorStrategyFactory =
				new SamlIdentifierGeneratorStrategyFactory();

		samlIdentifierGenerator = samlIdentifierGeneratorStrategyFactory.create(
			16);

		IdentifierGenerationStrategy identifierGenerationStrategy = mock(
			IdentifierGenerationStrategy.class);

		identifierGenerationStrategyFactory = mock(
			IdentifierGenerationStrategyFactory.class);

		when(
			identifierGenerationStrategyFactory.create(Mockito.anyInt())
		).thenReturn(
			identifierGenerationStrategy
		);

		when(
			identifierGenerationStrategy.generateIdentifier()
		).thenAnswer(
			new Answer<String>() {

				@Override
				public String answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					String identifier =
						samlIdentifierGenerator.generateIdentifier();

					identifiers.add(identifier);

					return identifier;
				}

			}
		);

		when(
			identifierGenerationStrategy.generateIdentifier(
				Mockito.anyBoolean())
		).thenAnswer(
			new Answer<String>() {

				@Override
				public String answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					boolean xmlSafe = GetterUtil.getBoolean(
						invocationOnMock.getArguments()[0]);

					String identifier =
						samlIdentifierGenerator.generateIdentifier(xmlSafe);

					identifiers.add(identifier);

					return identifier;
				}

			}
		);
	}

	protected void setupMetadata() throws Exception {
		metadataManagerImpl = new MetadataManagerImpl();

		keyStoreManager = new FileSystemKeyStoreManagerImpl();

		Map<String, Object> properties = HashMapBuilder.<String, Object>put(
			"saml.keystore.path",
			"classpath:/com/liferay/saml/opensaml/integration/internal" +
				"/credential/dependencies/keystore.jks"
		).build();

		ReflectionTestUtil.invoke(
			keyStoreManager, "activate", new Class<?>[] {Map.class},
			properties);

		credentialResolver = new KeyStoreCredentialResolver();

		credentialResolver.setKeyStoreManager(keyStoreManager);

		credentialResolver.setSamlProviderConfigurationHelper(
			samlProviderConfigurationHelper);

		metadataManagerImpl.setCredentialResolver(credentialResolver);

		metadataManagerImpl.setParserPool(parserPool);

		metadataManagerImpl.setMetadataResolver(new MockMetadataResolver());

		metadataManagerImpl.setSamlProviderConfigurationHelper(
			samlProviderConfigurationHelper);

		metadataManagerImpl.setHttp(HttpUtil.getHttp());

		metadataManagerImpl.setPortal(portal);

		metadataManagerImpl.setLocalEntityManager(credentialResolver);

		ReflectionTestUtil.invoke(
			metadataManagerImpl, "activate", new Class<?>[0]);
	}

	protected void setupParserPool() {
		XMLObjectProviderRegistry xmlObjectProviderRegistry =
			ConfigurationService.get(XMLObjectProviderRegistry.class);

		parserPool = xmlObjectProviderRegistry.getParserPool();
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

		VelocityEngine velocityEngine = velocityEngineFactory.getVelocityEngine(
			currentThread.getContextClassLoader());

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
	protected IdentifierGenerationStrategyFactory
		identifierGenerationStrategyFactory;
	protected List<String> identifiers = new ArrayList<>();
	protected FileSystemKeyStoreManagerImpl keyStoreManager;
	protected LocalEntityManager localEntityManager;
	protected MetadataManagerImpl metadataManagerImpl;
	protected ParserPool parserPool;
	protected Portal portal;
	protected BeanLocator portalBeanLocator;
	protected BeanLocator portletBeanLocator;
	protected Props props;
	protected List<SamlBinding> samlBindings;
	protected IdentifierGenerationStrategy samlIdentifierGenerator;
	protected SamlProviderConfiguration samlProviderConfiguration;
	protected SamlProviderConfigurationHelper samlProviderConfigurationHelper;
	protected List<Class<?>> serviceUtilClasses = new ArrayList<>();
	protected UserLocalService userLocalService;

	protected class MockMetadataResolver extends AbstractMetadataResolver {

		public MockMetadataResolver() {
		}

		public MockMetadataResolver(boolean idpNeedsSignature) {
			_idpNeedsSignature = idpNeedsSignature;
		}

		@Override
		public Iterable<EntityDescriptor> resolve(CriteriaSet criteriaSet)
			throws ResolverException {

			try {
				return Collections.singleton(doResolve(criteriaSet));
			}
			catch (Exception e) {
				throw new ResolverException(e);
			}
		}

		protected EntityDescriptor doResolve(CriteriaSet criteriaSet)
			throws Exception {

			EntityIdCriterion entityIdCriterion = criteriaSet.get(
				EntityIdCriterion.class);

			if (entityIdCriterion == null) {
				throw new ResolverException("Entity ID criterion is null");
			}

			String entityId = entityIdCriterion.getEntityId();

			KeyStoreCredentialResolver keyStoreCredentialResolver =
				getKeyStoreCredentialResolver(entityId);

			Credential credential = keyStoreCredentialResolver.resolveSingle(
				criteriaSet);

			if (entityId.equals(IDP_ENTITY_ID)) {
				EntityDescriptor entityDescriptor =
					MetadataGeneratorUtil.buildIdpEntityDescriptor(
						PORTAL_URL, entityId, _idpNeedsSignature, true,
						credential, null);

				IDPSSODescriptor idpSSODescriptor =
					entityDescriptor.getIDPSSODescriptor(
						SAMLConstants.SAML20P_NS);

				List<SingleLogoutService> singleLogoutServices =
					idpSSODescriptor.getSingleLogoutServices();

				for (SingleLogoutService singleLogoutService :
						singleLogoutServices) {

					String binding = singleLogoutService.getBinding();

					if (binding.equals(SAMLConstants.SAML2_POST_BINDING_URI)) {
						singleLogoutServices.remove(singleLogoutService);

						break;
					}
				}

				List<SingleSignOnService> singleSignOnServices =
					idpSSODescriptor.getSingleSignOnServices();

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
					PORTAL_URL, entityId, true, true, false, credential, null);
			}

			return null;
		}

		private boolean _idpNeedsSignature = true;

	}

}