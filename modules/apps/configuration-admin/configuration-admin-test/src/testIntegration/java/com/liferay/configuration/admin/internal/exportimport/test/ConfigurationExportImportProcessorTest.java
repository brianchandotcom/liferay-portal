/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.internal.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.configuration.admin.exportimport.ConfigurationExportImportProcessor;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class ConfigurationExportImportProcessorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testPrepareForExport() throws Exception {
		String pid = RandomTestUtil.randomString();

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		Assert.assertFalse(
			_configurationExportImportProcessor.prepareForExport(
				pid, dictionary));

		ExtendedObjectClassDefinition.Scope companyScope =
			ExtendedObjectClassDefinition.Scope.COMPANY;

		String companyScopePropertyKey = companyScope.getPropertyKey();

		dictionary = HashMapDictionaryBuilder.<String, Object>put(
			companyScopePropertyKey, _company.getCompanyId()
		).build();

		Assert.assertTrue(
			_configurationExportImportProcessor.prepareForExport(
				pid, dictionary));

		Assert.assertNull(dictionary.get(companyScopePropertyKey));
		Assert.assertEquals(
			_getCompanyPortableIdentifier(),
			dictionary.get(companyScope.getPortablePropertyKey()));

		ExtendedObjectClassDefinition.Scope groupScope =
			ExtendedObjectClassDefinition.Scope.GROUP;

		String groupScopePropertyKey = groupScope.getPropertyKey();

		dictionary = HashMapDictionaryBuilder.<String, Object>put(
			groupScopePropertyKey, _group.getGroupId()
		).build();

		Assert.assertTrue(
			_configurationExportImportProcessor.prepareForExport(
				pid, dictionary));

		Assert.assertNull(dictionary.get(groupScopePropertyKey));
		Assert.assertEquals(
			_getGroupPortableIdentifier(),
			dictionary.get(groupScope.getPortablePropertyKey()));
	}

	@Test
	public void testPrepareForImport() throws Exception {
		String pid = RandomTestUtil.randomString();

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		Assert.assertFalse(
			_configurationExportImportProcessor.prepareForImport(
				pid, dictionary));

		ExtendedObjectClassDefinition.Scope companyScope =
			ExtendedObjectClassDefinition.Scope.COMPANY;

		String companyScopePortablePropertyKey =
			companyScope.getPortablePropertyKey();

		dictionary = HashMapDictionaryBuilder.<String, Object>put(
			companyScopePortablePropertyKey, _getCompanyPortableIdentifier()
		).build();

		Assert.assertTrue(
			_configurationExportImportProcessor.prepareForImport(
				pid, dictionary));

		Assert.assertNull(dictionary.get(companyScopePortablePropertyKey));
		Assert.assertEquals(
			_company.getCompanyId(),
			dictionary.get(companyScope.getPropertyKey()));

		ExtendedObjectClassDefinition.Scope groupScope =
			ExtendedObjectClassDefinition.Scope.GROUP;

		String groupScopePortablePropertyKey =
			groupScope.getPortablePropertyKey();

		dictionary = HashMapDictionaryBuilder.<String, Object>put(
			groupScopePortablePropertyKey, _getGroupPortableIdentifier()
		).build();

		Assert.assertTrue(
			_configurationExportImportProcessor.prepareForImport(
				pid, dictionary));

		Assert.assertNull(dictionary.get(groupScopePortablePropertyKey));
		Assert.assertEquals(
			_group.getGroupId(), dictionary.get(groupScope.getPropertyKey()));
	}

	private String _getCompanyPortableIdentifier() {
		return _company.getWebId();
	}

	private String _getGroupPortableIdentifier() {
		return _getCompanyPortableIdentifier() + "--" + _group.getGroupKey();
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private ConfigurationExportImportProcessor
		_configurationExportImportProcessor;

	private Group _group;

}