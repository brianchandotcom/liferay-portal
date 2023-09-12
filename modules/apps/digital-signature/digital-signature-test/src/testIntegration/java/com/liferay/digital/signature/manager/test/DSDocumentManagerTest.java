/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.digital.signature.manager.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.digital.signature.configuration.DigitalSignatureConfiguration;
import com.liferay.digital.signature.manager.DSDocumentManager;
import com.liferay.digital.signature.manager.DSEnvelopeManager;
import com.liferay.digital.signature.model.DSDocument;
import com.liferay.digital.signature.model.DSEnvelope;
import com.liferay.digital.signature.model.DSRecipient;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class DSDocumentManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_configurationProvider.saveCompanyConfiguration(
			DigitalSignatureConfiguration.class, TestPropsValues.getCompanyId(),
			HashMapDictionaryBuilder.<String, Object>put(
				"accountBaseURI",
				TestPropsUtil.get("digital.signature.account.base.uri")
			).put(
				"apiAccountId",
				TestPropsUtil.get("digital.signature.api.accountId")
			).put(
				"apiUsername",
				TestPropsUtil.get("digital.signature.api.username")
			).put(
				"enabled", true
			).put(
				"integrationKey",
				TestPropsUtil.get("digital.signature.integration.key")
			).put(
				"rsaPrivateKey",
				TestPropsUtil.get("digital.signature.rsa.private.key")
			).put(
				"siteSettingsStrategy",
				TestPropsUtil.get("digital.signature.site.settings.strategy")
			).build());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_configurationProvider.saveCompanyConfiguration(
			DigitalSignatureConfiguration.class, TestPropsValues.getCompanyId(),
			HashMapDictionaryBuilder.<String, Object>put(
				"accountBaseURI", ""
			).put(
				"apiAccountId", ""
			).put(
				"apiUsername", ""
			).put(
				"enabled", false
			).put(
				"integrationKey", ""
			).put(
				"rsaPrivateKey", ""
			).build());
	}

	@Test
	public void testGetDSDocumentsAsBytes() throws Exception {
		DSEnvelope dsEnvelope = _dsEnvelopeManager.addDSEnvelope(
			TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
			new DSEnvelope() {
				{
					dsDocuments = Collections.singletonList(
						new DSDocument() {
							{
								data = Base64.encode(
									FileUtil.getBytes(
										getClass(),
										"dependencies/Document.pdf"));
								dsDocumentId = "1";
								name = RandomTestUtil.randomString();
							}
						});
					dsRecipients = Collections.singletonList(
						new DSRecipient() {
							{
								dsRecipientId = "1";
								emailAddress = "test@liferay.com";
								name = RandomTestUtil.randomString();
							}
						});
					emailBlurb = RandomTestUtil.randomString();
					emailSubject = RandomTestUtil.randomString();
					status = "sent";
				}
			});

		Assert.assertTrue(
			ArrayUtil.isNotEmpty(
				_dsDocumentManager.getDSDocumentsAsBytes(
					TestPropsValues.getCompanyId(),
					TestPropsValues.getGroupId(),
					dsEnvelope.getDSEnvelopeId())));

		_dsEnvelopeManager.deleteDSEnvelopes(
			TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
			dsEnvelope.getDSEnvelopeId());
	}

	@Inject
	private static ConfigurationProvider _configurationProvider;

	@Inject
	private DSDocumentManager _dsDocumentManager;

	@Inject
	private DSEnvelopeManager _dsEnvelopeManager;

}