/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class FileEntryInfoItemFormVariationsProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	@TestInfo("LPD-56469")
	public void testInfoItemFormVariationsNoDLFileEntryTypeNameForDefaultLanguage()
		throws Exception {

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				FileEntry.class.getName());

		Collection<InfoItemFormVariation> infoItemFormVariations =
			infoItemFormVariationsProvider.getInfoItemFormVariationsByCompanyId(
				TestPropsValues.getCompanyId());

		Assert.assertFalse(
			infoItemFormVariations.toString(),
			infoItemFormVariations.isEmpty());

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		User user = company.getGuestUser();

		String originalLanguageId = user.getLanguageId();

		Map<String, String> infoItemFormVariationsMap = new HashMap<>();

		for (InfoItemFormVariation infoItemFormVariation :
				infoItemFormVariations) {

			String label = infoItemFormVariation.getLabel(
				LocaleUtil.fromLanguageId(originalLanguageId));

			Assert.assertTrue(Validator.isNotNull(label));

			infoItemFormVariationsMap.put(
				infoItemFormVariation.getKey(), label);
		}

		_companyLocalService.updatePreferences(
			company.getCompanyId(),
			UnicodePropertiesBuilder.put(
				PropsKeys.LOCALES, "en_CA,en_US,fr_CA"
			).build());
		_companyLocalService.updateDisplay(
			company.getCompanyId(), "en_CA", user.getTimeZoneId());
		_companyLocalService.updatePreferences(
			company.getCompanyId(),
			UnicodePropertiesBuilder.put(
				PropsKeys.LOCALES, "en_CA,fr_CA"
			).build());

		try {
			Collection<InfoItemFormVariation> curInfoItemFormVariations =
				infoItemFormVariationsProvider.
					getInfoItemFormVariationsByCompanyId(
						TestPropsValues.getCompanyId());

			Assert.assertEquals(
				curInfoItemFormVariations.toString(),
				infoItemFormVariations.size(),
				curInfoItemFormVariations.size());

			for (InfoItemFormVariation curInfoItemFormVariation :
					curInfoItemFormVariations) {

				Assert.assertTrue(
					Validator.isNotNull(
						curInfoItemFormVariation.getLabel(LocaleUtil.CANADA)));
				Assert.assertTrue(
					Validator.isNotNull(
						curInfoItemFormVariation.getLabel(
							LocaleUtil.CANADA_FRENCH)));
				Assert.assertEquals(
					infoItemFormVariationsMap.get(
						curInfoItemFormVariation.getKey()),
					curInfoItemFormVariation.getLabel(LocaleUtil.CANADA));
			}
		}
		finally {
			_companyLocalService.updatePreferences(
				company.getCompanyId(),
				UnicodePropertiesBuilder.put(
					PropsKeys.LOCALES, "en_CA,fr_CA," + originalLanguageId
				).build());
			_companyLocalService.updateDisplay(
				company.getCompanyId(), originalLanguageId,
				user.getTimeZoneId());
			_companyLocalService.updatePreferences(
				company.getCompanyId(),
				UnicodePropertiesBuilder.put(
					PropsKeys.LOCALES, String.join(",", PropsValues.LOCALES)
				).build());
		}
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private InfoItemServiceRegistry _infoItemServiceRegistry;

}