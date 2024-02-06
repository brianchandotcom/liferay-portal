/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.test.system.TestSystemObjectDefinitionManager;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class UserModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testOnAfterUpdate() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		CompanyTestUtil.resetCompanyLocales(
			company.getCompanyId(),
			ListUtil.fromArray(LocaleUtil.SPAIN, LocaleUtil.US), LocaleUtil.US);

		Bundle bundle = FrameworkUtil.getBundle(UserModelListenerTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		String name = ObjectDefinitionTestUtil.getRandomName();

		bundleContext.registerService(
			SystemObjectDefinitionManager.class,
			new TestSystemObjectDefinitionManager(
				ObjectEntry.class, name, "/o/test-endpoint/entries"),
			new HashMapDictionary<>());

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				company.getCompanyId(), name);

		_assertEquals(LocaleUtil.SPAIN, objectDefinition);
		_assertEquals(LocaleUtil.US, objectDefinition);
		_assertEqualsSorted(new String[] {"es_ES", "en_US"}, objectDefinition);

		CompanyTestUtil.resetCompanyLocales(
			company.getCompanyId(),
			ListUtil.fromArray(LocaleUtil.BRAZIL, LocaleUtil.SPAIN),
			LocaleUtil.BRAZIL);

		objectDefinition = _objectDefinitionLocalService.fetchObjectDefinition(
			company.getCompanyId(), name);

		_assertEquals(LocaleUtil.BRAZIL, objectDefinition);
		_assertEqualsSorted(
			new String[] {"es_ES", "en_US", "pt_BR"}, objectDefinition);

		_objectDefinitionLocalService.deleteCompanyObjectDefinitions(
			company.getCompanyId());

		_companyLocalService.deleteCompany(company.getCompanyId());
	}

	private void _assertEquals(
		Locale locale, ObjectDefinition objectDefinition) {

		Assert.assertEquals(
			_language.get(locale, "test"), objectDefinition.getLabel(locale));
		Assert.assertEquals(
			_language.get(locale, "tests"),
			objectDefinition.getPluralLabel(locale));
	}

	private void _assertEqualsSorted(
		String[] availableLanguageIds, ObjectDefinition objectDefinition) {

		AssertUtils.assertEqualsSorted(
			availableLanguageIds,
			_localization.getAvailableLanguageIds(objectDefinition.getLabel()));
		AssertUtils.assertEqualsSorted(
			availableLanguageIds,
			_localization.getAvailableLanguageIds(
				objectDefinition.getPluralLabel()));
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private Language _language;

	@Inject
	private Localization _localization;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}