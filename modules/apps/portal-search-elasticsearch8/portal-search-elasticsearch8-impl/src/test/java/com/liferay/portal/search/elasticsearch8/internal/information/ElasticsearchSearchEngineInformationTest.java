/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.information;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.license.ElasticsearchLicenseClient;
import co.elastic.clients.elasticsearch.license.GetLicenseResponse;
import co.elastic.clients.elasticsearch.license.LicenseStatus;
import co.elastic.clients.elasticsearch.license.LicenseType;
import co.elastic.clients.elasticsearch.license.get.LicenseInformation;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
public class ElasticsearchSearchEngineInformationTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_elasticsearchSearchEngineInformation =
			new ElasticsearchSearchEngineInformation();

		_elasticsearchSearchEngineInformation.elasticsearchConnectionManager =
			_elasticsearchConnectionManager;

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
	public void testIsInferenceAPISupported() throws IOException {
		_testIsInferenceAPISupported(
			false, LicenseStatus.Active, LicenseType.Basic);
		_testIsInferenceAPISupported(
			true, LicenseStatus.Active, LicenseType.Enterprise);
		_testIsInferenceAPISupported(
			true, LicenseStatus.Active, LicenseType.Trial);
		_testIsInferenceAPISupported(
			false, LicenseStatus.Expired, LicenseType.Enterprise);

		Mockito.when(
			_elasticsearchLicenseClient.get()
		).thenReturn(
			null
		);

		Assert.assertFalse(
			_elasticsearchSearchEngineInformation.isInferenceAPISupported());

		Mockito.when(
			_elasticsearchLicenseClient.get()
		).thenReturn(
			Mockito.mock(GetLicenseResponse.class)
		);

		Assert.assertFalse(
			_elasticsearchSearchEngineInformation.isInferenceAPISupported());

		Mockito.when(
			_elasticsearchLicenseClient.get()
		).thenThrow(
			new IOException(RandomTestUtil.randomString())
		);

		Assert.assertFalse(
			_elasticsearchSearchEngineInformation.isInferenceAPISupported());

		Mockito.when(
			_elasticsearchConnectionManager.getElasticsearchClient()
		).thenReturn(
			null
		);

		Assert.assertFalse(
			_elasticsearchSearchEngineInformation.isInferenceAPISupported());
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

	private void _testIsInferenceAPISupported(
			boolean expectedInferenceAPISupported, LicenseStatus licenseStatus,
			LicenseType licenseType)
		throws IOException {

		_setLicense(licenseType, licenseStatus);

		Assert.assertEquals(
			expectedInferenceAPISupported,
			_elasticsearchSearchEngineInformation.isInferenceAPISupported());
	}

	private final ElasticsearchClient _elasticsearchClient = Mockito.mock(
		ElasticsearchClient.class);
	private final ElasticsearchConnectionManager
		_elasticsearchConnectionManager = Mockito.mock(
			ElasticsearchConnectionManager.class);
	private final ElasticsearchLicenseClient _elasticsearchLicenseClient =
		Mockito.mock(ElasticsearchLicenseClient.class);
	private ElasticsearchSearchEngineInformation
		_elasticsearchSearchEngineInformation;

}