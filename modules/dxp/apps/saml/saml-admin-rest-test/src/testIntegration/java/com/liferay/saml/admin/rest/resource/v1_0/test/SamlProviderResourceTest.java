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

package com.liferay.saml.admin.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;
import com.liferay.portal.test.rule.Inject;
import com.liferay.saml.admin.rest.client.dto.v1_0.Idp;
import com.liferay.saml.admin.rest.client.dto.v1_0.SamlProvider;
import com.liferay.saml.admin.rest.client.dto.v1_0.Sp;
import com.liferay.saml.admin.rest.client.http.HttpInvoker;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.util.PortletPropsKeys;

import java.util.Collections;
import java.util.function.Consumer;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Stian Sigvartsen
 */
@RunWith(Arquillian.class)
public class SamlProviderResourceTest extends BaseSamlProviderResourceTestCase {

	public static void assertEncryptionCredentialException(
		JSONObject jsonObject) {

		AssertUtil.assertEncryptionCredentialExceptionMessage(
			GetterUtil.getString(jsonObject.get("title")));
	}

	public static void assertSigningCredentialException(JSONObject jsonObject) {
		AssertUtil.assertSigningCredentialExceptionMessage(
			GetterUtil.getString(jsonObject.get("title")));
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_sapEntry = _sapEntryLocalService.addSAPEntry(
			TestPropsValues.getUserId(),
			"com.liferay.saml.admin.rest.internal.resource.v1_0." +
				"SamlProviderResourceImpl#*",
			true, true, "Guest",
			HashMapBuilder.put(
				LocaleUtil.getDefault(), "Guest"
			).build(),
			ServiceContextTestUtil.getServiceContext());
	}

	@Ignore
	@Override
	@Test
	public void testGetSamlProvider() throws Exception {
	}

	@Override
	@Test
	public void testPatchSamlProvider() throws Exception {
		try (TestEnvironment testEnvironment = new TestEnvironment(
				_companyLocalService, _configurationAdmin)) {

			testEnvironment.mountKeyStore();
			testEnvironment.temporarilyClearSamlProviderConfiguration();

			_testPatchSamlProvider_general();
		}

		try (TestEnvironment testEnvironment = new TestEnvironment(
				_companyLocalService, _configurationAdmin)) {

			testEnvironment.mountKeyStore();
			testEnvironment.temporarilyClearSamlProviderConfiguration();

			_testPatchSamlProvider_invalidCredentials();

			_testPatchSamlProvider_validCredentials();
		}
	}

	@Override
	@Test
	public void testPostSamlProvider() throws Exception {
		try (TestEnvironment testEnvironment = new TestEnvironment(
				_companyLocalService, _configurationAdmin)) {

			testEnvironment.temporarilyClearSamlProviderConfiguration();

			_testPostSamlProvider_role();
		}

		try (TestEnvironment testEnvironment = new TestEnvironment(
				_companyLocalService, _configurationAdmin)) {

			testEnvironment.temporarilyClearSamlProviderConfiguration();

			_testPostSamlProvider_completeConfigurationReplacement();
		}
	}

	protected void addExpectedEnrichment(SamlProvider samlProvider) {
		if (samlProvider.getIdp() != null) {
			samlProvider.setRole(SamlProvider.Role.IDP);
		}
		else if (samlProvider.getSp() != null) {
			samlProvider.setRole(SamlProvider.Role.SP);
		}
	}

	@Override
	protected SamlProvider testPostSamlProvider_addSamlProvider(
			SamlProvider samlProvider)
		throws Exception {

		return new SamlProvider();
	}

	protected final SamlProviderConfiguration defaultSamlProviderConfiguration =
		ConfigurableUtil.createConfigurable(
			SamlProviderConfiguration.class, Collections.emptyMap());

	private SamlProvider
			_configureSystemSamlProviderDifferentiatedBySslRequired(
				SamlProvider defaultSamlProvider)
		throws Exception {

		Assert.assertEquals(
			defaultSamlProviderConfiguration.sslRequired(),
			defaultSamlProvider.getSslRequired());

		ConfigurationTestUtil.createFactoryConfiguration(
			"com.liferay.saml.runtime.configuration.SamlProviderConfiguration",
			HashMapDictionaryBuilder.put(
				PortletPropsKeys.SAML_SSL_REQUIRED,
				(Object)!defaultSamlProvider.getSslRequired()
			).put(
				"companyId", CompanyConstants.SYSTEM
			).build());

		SamlProvider systemSamlProvider =
			samlProviderResource.getSamlProvider();

		Assert.assertNotEquals(
			defaultSamlProvider.getSslRequired(),
			systemSamlProvider.getSslRequired());

		return systemSamlProvider;
	}

	private void _patchAndAssertResponse(
			SamlProvider samlProvider, Response.Status expectedStatus,
			Consumer<JSONObject> consumer)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			samlProviderResource.patchSamlProviderHttpResponse(samlProvider);

		Assert.assertEquals(
			expectedStatus.getStatusCode(), httpResponse.getStatusCode());

		if (consumer != null) {
			consumer.accept(
				_jsonFactory.createJSONObject(httpResponse.getContent()));
		}
	}

	private void _testPatchSamlProvider_general() throws Exception {
		SamlProvider defaultSamlProvider =
			samlProviderResource.getSamlProvider();

		SamlProvider systemSamlProvider =
			_configureSystemSamlProviderDifferentiatedBySslRequired(
				defaultSamlProvider);

		SamlProvider patchSamlProvider = new SamlProvider() {
			{
				enabled = false;
				entityId = "testSAMLEntity";
			}
		};

		SamlProvider samlProvider = samlProviderResource.patchSamlProvider(
			patchSamlProvider);

		Assert.assertEquals(
			patchSamlProvider.getEntityId(), samlProvider.getEntityId());

		Assert.assertEquals(
			systemSamlProvider.getSslRequired(), samlProvider.getSslRequired());

		samlProvider = samlProviderResource.patchSamlProvider(
			new SamlProvider() {
				{
					enabled = false;
				}
			});

		Assert.assertEquals(
			patchSamlProvider.getEntityId(), samlProvider.getEntityId());
	}

	private void _testPatchSamlProvider_invalidCredentials() throws Exception {
		_patchAndAssertResponse(
			new SamlProvider() {
				{
					enabled = false;
					entityId = "testSAMLEntity";
					keyStoreCredentialPassword = "WRONG";
				}
			},
			Response.Status.BAD_REQUEST,
			SamlProviderResourceTest::assertSigningCredentialException);

		_patchAndAssertResponse(
			new SamlProvider() {
				{
					enabled = true;
					entityId = "testSAMLEntity";
				}
			},
			Response.Status.BAD_REQUEST,
			SamlProviderResourceTest::assertSigningCredentialException);

		_patchAndAssertResponse(
			new SamlProvider() {
				{
					enabled = false;
					entityId = "testSAMLEntity";
					sp = new Sp() {
						{
							keyStoreEncryptionCredentialPassword = "WRONG";
						}
					};
				}
			},
			Response.Status.BAD_REQUEST,
			SamlProviderResourceTest::assertEncryptionCredentialException);
	}

	private void _testPatchSamlProvider_validCredentials() throws Exception {
		_patchAndAssertResponse(
			new SamlProvider() {
				{
					enabled = true;
					entityId = "testSAMLEntity";
					keyStoreCredentialPassword = "testSAMLEntitySigning";
				}
			},
			Response.Status.OK, null);

		_patchAndAssertResponse(
			new SamlProvider() {
				{
					sp = new Sp() {
						{
							keyStoreEncryptionCredentialPassword =
								"testSAMLEntityEncryption";
						}
					};
				}
			},
			Response.Status.OK, null);
	}

	private void _testPostSamlProvider_completeConfigurationReplacement()
		throws Exception {

		SamlProvider defaultSamlProvider =
			samlProviderResource.getSamlProvider();

		SamlProvider systemSamlProvider =
			_configureSystemSamlProviderDifferentiatedBySslRequired(
				defaultSamlProvider);

		SamlProvider postSamlProvider = new SamlProvider() {
			{
				enabled = false;
				entityId = "testSAMLEntity";
				sslRequired = defaultSamlProvider.getSslRequired();
			}
		};

		SamlProvider samlProvider = samlProviderResource.postSamlProvider(
			postSamlProvider);

		Assert.assertEquals(
			postSamlProvider.getEntityId(), samlProvider.getEntityId());

		Assert.assertEquals(
			postSamlProvider.getSslRequired(), samlProvider.getSslRequired());

		samlProvider = samlProviderResource.postSamlProvider(
			new SamlProvider() {
				{
					enabled = false;
					entityId = "testSAMLEntity";
				}
			});

		Assert.assertEquals(
			systemSamlProvider.getSslRequired(), samlProvider.getSslRequired());
	}

	private void _testPostSamlProvider_role() throws Exception {
		SamlProvider defaultSamlProvider =
			samlProviderResource.getSamlProvider();

		Assert.assertEquals(
			defaultSamlProviderConfiguration.sslRequired(),
			defaultSamlProvider.getSslRequired());

		SamlProvider postSamlProvider = new SamlProvider() {
			{
				enabled = false;
				entityId = "";
				signMetadata = false;
				sp = new Sp() {
					{
						allowShowingTheLoginPortlet = false;
						assertionSignatureRequired = false;
						clockSkew = 1000L;
						ldapImportEnabled = false;
						signAuthnRequest = false;
					}
				};
				sslRequired = false;
			}
		};

		SamlProvider samlProvider = samlProviderResource.postSamlProvider(
			postSamlProvider);

		addExpectedEnrichment(postSamlProvider);

		Assert.assertEquals(postSamlProvider, samlProvider);

		postSamlProvider.setIdp(
			new Idp() {
				{
					authnRequestSignatureRequired = false;
					defaultAssertionLifetime = 10000;
					sessionMaximumAge = 60000L;
					sessionTimeout = 60000L;
				}
			});

		HttpInvoker.HttpResponse httpResponse =
			samlProviderResource.postSamlProviderHttpResponse(postSamlProvider);

		Assert.assertEquals(
			Response.Status.BAD_REQUEST.getStatusCode(),
			httpResponse.getStatusCode());

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			httpResponse.getContent());

		Assert.assertEquals(
			"Identity and service provider roles are mutually exclusive",
			jsonObject.get("title"));

		postSamlProvider.setSp((Sp)null);

		samlProvider = samlProviderResource.postSamlProvider(postSamlProvider);

		addExpectedEnrichment(postSamlProvider);

		Assert.assertEquals(postSamlProvider, samlProvider);
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	@Inject
	private JSONFactory _jsonFactory;

	@DeleteAfterTestRun
	private SAPEntry _sapEntry;

	@Inject
	private SAPEntryLocalService _sapEntryLocalService;

}