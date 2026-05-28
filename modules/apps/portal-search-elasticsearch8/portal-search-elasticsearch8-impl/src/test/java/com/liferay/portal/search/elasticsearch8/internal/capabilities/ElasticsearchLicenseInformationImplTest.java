/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.capabilities;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.license.ElasticsearchLicenseClient;
import co.elastic.clients.elasticsearch.license.GetLicenseResponse;
import co.elastic.clients.elasticsearch.license.LicenseStatus;
import co.elastic.clients.elasticsearch.license.LicenseType;
import co.elastic.clients.elasticsearch.license.get.LicenseInformation;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Rodrigo Guedes de Souza
 */
public class ElasticsearchLicenseInformationImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_elasticsearchLicenseInformationImpl =
			new ElasticsearchLicenseInformationImpl();

		ReflectionTestUtil.setFieldValue(
			_elasticsearchLicenseInformationImpl,
			"_elasticsearchConnectionManager", _elasticsearchConnectionManager);

		Mockito.when(
			_elasticsearchClient.license()
		).thenReturn(
			_elasticsearchLicenseClient
		);

		Mockito.when(
			_elasticsearchConnectionManager.getElasticsearchClient()
		).thenReturn(
			_elasticsearchClient
		);
	}

	@Test
	public void testSupportsInferenceAPIWhenBasic() throws IOException {
		_setLicense(LicenseType.Basic, LicenseStatus.Active);

		Assert.assertFalse(
			_elasticsearchLicenseInformationImpl.supportsInferenceAPI());
	}

	@Test
	public void testSupportsInferenceAPIWhenClientUnavailable() {
		Mockito.when(
			_elasticsearchConnectionManager.getElasticsearchClient()
		).thenReturn(
			null
		);

		Assert.assertFalse(
			_elasticsearchLicenseInformationImpl.supportsInferenceAPI());
	}

	@Test
	public void testSupportsInferenceAPIWhenEnterprise() throws IOException {
		_setLicense(LicenseType.Enterprise, LicenseStatus.Active);

		Assert.assertTrue(
			_elasticsearchLicenseInformationImpl.supportsInferenceAPI());
	}

	@Test
	public void testSupportsInferenceAPIWhenExceptionThrown()
		throws IOException {

		Mockito.when(
			_elasticsearchLicenseClient.get()
		).thenThrow(
			new IOException("boom")
		);

		Assert.assertFalse(
			_elasticsearchLicenseInformationImpl.supportsInferenceAPI());
	}

	@Test
	public void testSupportsInferenceAPIWhenLicenseExpired()
		throws IOException {

		_setLicense(LicenseType.Enterprise, LicenseStatus.Expired);

		Assert.assertFalse(
			_elasticsearchLicenseInformationImpl.supportsInferenceAPI());
	}

	@Test
	public void testSupportsInferenceAPIWhenLicenseMissing()
		throws IOException {

		Mockito.when(
			_elasticsearchLicenseClient.get()
		).thenReturn(
			Mockito.mock(GetLicenseResponse.class)
		);

		Assert.assertFalse(
			_elasticsearchLicenseInformationImpl.supportsInferenceAPI());
	}

	@Test
	public void testSupportsInferenceAPIWhenResponseNull() throws IOException {
		Mockito.when(
			_elasticsearchLicenseClient.get()
		).thenReturn(
			null
		);

		Assert.assertFalse(
			_elasticsearchLicenseInformationImpl.supportsInferenceAPI());
	}

	@Test
	public void testSupportsInferenceAPIWhenTrial() throws IOException {
		_setLicense(LicenseType.Trial, LicenseStatus.Active);

		Assert.assertTrue(
			_elasticsearchLicenseInformationImpl.supportsInferenceAPI());
	}

	private void _setLicense(LicenseType type, LicenseStatus status)
		throws IOException {

		GetLicenseResponse getLicenseResponse = Mockito.mock(
			GetLicenseResponse.class);

		LicenseInformation licenseInformation = Mockito.mock(
			LicenseInformation.class);

		Mockito.when(
			licenseInformation.status()
		).thenReturn(
			status
		);

		Mockito.when(
			licenseInformation.type()
		).thenReturn(
			type
		);

		Mockito.when(
			getLicenseResponse.license()
		).thenReturn(
			licenseInformation
		);

		Mockito.when(
			_elasticsearchLicenseClient.get()
		).thenReturn(
			getLicenseResponse
		);
	}

	private final ElasticsearchClient _elasticsearchClient = Mockito.mock(
		ElasticsearchClient.class);
	private final ElasticsearchConnectionManager
		_elasticsearchConnectionManager = Mockito.mock(
			ElasticsearchConnectionManager.class);
	private final ElasticsearchLicenseClient _elasticsearchLicenseClient =
		Mockito.mock(ElasticsearchLicenseClient.class);
	private ElasticsearchLicenseInformationImpl
		_elasticsearchLicenseInformationImpl;

}