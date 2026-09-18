/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.secret;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.security.key.KeyReference;
import com.liferay.portal.security.key.KeyReferenceUtil;
import com.liferay.portal.security.key.secret.exception.SecretException;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Pedro Victor Silvestre
 */
public class SecretVaultUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);

		_fipsEnabled = PropsValues.FIPS_ENABLED;

		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "FIPS_ENABLED", true);

		ReflectionTestUtil.setFieldValue(
			SecretVaultUtil.class, "_secretManagerSnapshot",
			new Snapshot<SecretManager>(
				SecretVaultUtil.class, SecretManager.class) {

				@Override
				public SecretManager get() {
					return _secretManager;
				}

			});
	}

	@After
	public void tearDown() throws Exception {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "FIPS_ENABLED", _fipsEnabled);
	}

	@Test
	public void testGetIdentifier() throws Exception {
		String key = RandomTestUtil.randomString();
		String scope = "company/" + RandomTestUtil.randomLong();

		Assert.assertEquals(
			StringBundler.concat("preference/", scope, StringPool.SLASH, key),
			SecretVaultUtil.getIdentifier(key, scope));
	}

	@Test
	public void testVault() throws Exception {
		long companyId = RandomTestUtil.randomLong();
		String value = RandomTestUtil.randomString();

		KeyReference keyReference = new KeyReference(
			_IDENTIFIER, "provider", KeyReference.Type.SECRET);

		AtomicReference<Secret> atomicReference = new AtomicReference<>();

		Mockito.when(
			_secretManager.putSecret(Mockito.eq(companyId), Mockito.any())
		).thenAnswer(
			invocationOnMock -> {
				atomicReference.set(invocationOnMock.getArgument(1));

				return keyReference;
			}
		);

		Assert.assertEquals(
			KeyReferenceUtil.toKeyReferenceString(keyReference),
			SecretVaultUtil.vault(companyId, _IDENTIFIER, value));

		Secret secret = atomicReference.get();

		KeyReference secretKeyReference = secret.getKeyReference();

		Assert.assertEquals(_IDENTIFIER, secretKeyReference.getIdentifier());
		Assert.assertEquals(
			StringPool.STAR, secretKeyReference.getProviderId());

		Assert.assertTrue(secret.isDestroyed());
	}

	@Test
	public void testVaultWhenFIPSIsDisabled() throws Exception {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "FIPS_ENABLED", false);

		String value = RandomTestUtil.randomString();

		Assert.assertEquals(
			value,
			SecretVaultUtil.vault(
				RandomTestUtil.randomLong(), _IDENTIFIER, value));

		Mockito.verifyNoInteractions(_secretManager);
	}

	@Test
	public void testVaultWhenValueIsBlank() throws Exception {
		Assert.assertEquals(
			StringPool.BLANK,
			SecretVaultUtil.vault(
				RandomTestUtil.randomLong(), _IDENTIFIER, StringPool.BLANK));

		Mockito.verifyNoInteractions(_secretManager);
	}

	@Test
	public void testVaultWhenValueReferencesAnotherKey() throws Exception {
		String value = "${secretRef:provider:preference/portlet/1/x/password}";

		Assert.assertThrows(
			SecretException.class,
			() -> SecretVaultUtil.vault(
				RandomTestUtil.randomLong(), _IDENTIFIER, value));

		Mockito.verifyNoInteractions(_secretManager);
	}

	@Test
	public void testVaultWhenValueReferencesForeignNamespace()
		throws Exception {

		String value = "${secretRef:provider:some-other-vault/my-key}";

		Assert.assertEquals(
			value,
			SecretVaultUtil.vault(
				RandomTestUtil.randomLong(), _IDENTIFIER, value));

		Mockito.verifyNoInteractions(_secretManager);
	}

	@Test
	public void testVaultWhenValueReferencesSameKeyInBroaderScope()
		throws Exception {

		String value =
			"${secretRef:provider:preference/company/7/googleMapsAPIKey}";

		Assert.assertEquals(
			value,
			SecretVaultUtil.vault(
				RandomTestUtil.randomLong(), _IDENTIFIER, value));

		Mockito.verifyNoInteractions(_secretManager);
	}

	@Test
	public void testVaultWhenValueReferencesSameSlot() throws Exception {
		String value = "${secretRef:provider:" + _IDENTIFIER + "}";

		Assert.assertEquals(
			value,
			SecretVaultUtil.vault(
				RandomTestUtil.randomLong(), _IDENTIFIER, value));

		Mockito.verifyNoInteractions(_secretManager);
	}

	private static final String _IDENTIFIER =
		"preference/group/2/googleMapsAPIKey";

	private boolean _fipsEnabled;

	@Mock
	private SecretManager _secretManager;

}