/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.override.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.language.override.exception.PLOEntryKeyException;
import com.liferay.portal.language.override.exception.PLOEntryValueException;
import com.liferay.portal.language.override.model.PLOEntry;
import com.liferay.portal.language.override.service.PLOEntryLocalService;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class PLOEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddOrUpdatePLOEntry() throws Exception {
		String newKey = RandomTestUtil.randomString();

		_assertTranslationValue(newKey, null);

		PLOEntry ploEntry = _addOrUpdatePLOEntry(
			newKey, RandomTestUtil.randomString());

		_assertTranslationValue(newKey, ploEntry.getValue());

		String existingKey = "available-languages";

		Assert.assertNotNull(
			LanguageResources.getMessage(LocaleUtil.getDefault(), existingKey));

		ploEntry = _addOrUpdatePLOEntry(
			existingKey, RandomTestUtil.randomString());

		_assertTranslationValue(existingKey, ploEntry.getValue());

		_assertException(
			PLOEntryKeyException.MustBeShorter.class,
			() -> {
				int keyMaxLength = ModelHintsUtil.getMaxLength(
					PLOEntry.class.getName(), "key");

				_addOrUpdatePLOEntry(
					RandomTestUtil.randomString(keyMaxLength + 1),
					RandomTestUtil.randomString());
			});
		_assertException(
			PLOEntryKeyException.MustNotBeNull.class,
			() -> _addOrUpdatePLOEntry(
				StringPool.BLANK, RandomTestUtil.randomString()));
		_assertException(
			PLOEntryValueException.MustNotBeNull.class,
			() -> _addOrUpdatePLOEntry(
				RandomTestUtil.randomString(), StringPool.BLANK));
	}

	private PLOEntry _addOrUpdatePLOEntry(String key, String value)
		throws PortalException {

		return _ploEntryLocalService.addOrUpdatePLOEntry(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), key,
			LanguageUtil.getLanguageId(LocaleUtil.getDefault()), value);
	}

	private void _assertException(
			Class<? extends PortalException> exceptionClass,
			UnsafeRunnable<? extends PortalException> unsafeRunnable)
		throws Exception {

		try {
			unsafeRunnable.run();

			Assert.fail();
		}
		catch (PortalException portalException) {
			Assert.assertTrue(
				exceptionClass.isAssignableFrom(portalException.getClass()));
		}
	}

	private void _assertTranslationValue(String key, String value) {
		Assert.assertEquals(
			value, LanguageResources.getMessage(LocaleUtil.getDefault(), key));
		Assert.assertEquals(
			value,
			ResourceBundleUtil.getString(
				LanguageResources.getResourceBundle(LocaleUtil.getDefault()),
				key));
	}

	@Inject
	private PLOEntryLocalService _ploEntryLocalService;

}