/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.internal.configuration.persistence.listener;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.definitions.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.definitions.ExtendedMetaTypeInformation;
import com.liferay.portal.configuration.metatype.definitions.ExtendedMetaTypeService;
import com.liferay.portal.configuration.metatype.definitions.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.security.key.KeyReference;
import com.liferay.portal.security.key.KeyReferenceUtil;
import com.liferay.portal.security.key.secret.Secret;
import com.liferay.portal.security.key.secret.SecretManager;
import com.liferay.portal.security.key.secret.SecretResolver;
import com.liferay.portal.security.key.spi.profile.KeyManagerProfile;
import com.liferay.portal.security.key.spi.profile.KeyManagerProfileRegistry;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Dictionary;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.ObjectClassDefinition;

/**
 * @author Pedro Victor Silvestre
 */
public class ConfigurationSecretConfigurationModelListenerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);

		_setUpKeyManagerProfileRegistrySnapshot(_keyManagerProfile);

		Mockito.when(
			_bundleContext.getBundles()
		).thenReturn(
			new Bundle[] {_bundle}
		);

		Mockito.doReturn(
			SecretResolver.class
		).when(
			_bundle
		).loadClass(
			Mockito.anyString()
		);

		Mockito.when(
			_extendedMetaTypeService.getMetaTypeInformation(_bundle)
		).thenReturn(
			_extendedMetaTypeInformation
		);

		Mockito.when(
			_extendedMetaTypeInformation.getFactoryPids()
		).thenReturn(
			new String[0]
		);

		Mockito.when(
			_extendedMetaTypeInformation.getObjectClassDefinition(_PID, null)
		).thenReturn(
			_objectClassDefinition
		);

		Mockito.when(
			_extendedMetaTypeInformation.getPids()
		).thenReturn(
			new String[] {_PID}
		);

		ExtendedAttributeDefinition[] extendedAttributeDefinitions = {
			_toAttributeDefinition("credential", AttributeDefinition.PASSWORD),
			_toAttributeDefinition("host", AttributeDefinition.STRING)
		};

		Mockito.when(
			_objectClassDefinition.getAttributeDefinitions(
				ObjectClassDefinition.ALL)
		).thenReturn(
			extendedAttributeDefinitions
		);

		ReflectionTestUtil.setFieldValue(
			_configurationSecretConfigurationModelListener, "_bundleContext",
			_bundleContext);
		ReflectionTestUtil.setFieldValue(
			_configurationSecretConfigurationModelListener,
			"_extendedMetaTypeService", _extendedMetaTypeService);
		_setUpSecretManagerSnapshot(_secretManager);
	}

	@Test
	public void testOnBeforeSave() throws Exception {
		String host = RandomTestUtil.randomString();
		String value = RandomTestUtil.randomString();

		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"credential", value
			).put(
				"host", host
			).build();

		KeyReference keyReference = new KeyReference(
			"config/" + _PID + "/0/credential", "provider",
			KeyReference.Type.SECRET);

		AtomicReference<Secret> atomicReference = new AtomicReference<>();

		Mockito.when(
			_secretManager.putSecret(
				Mockito.eq(CompanyConstants.SYSTEM), Mockito.any())
		).thenAnswer(
			invocationOnMock -> {
				atomicReference.set(invocationOnMock.getArgument(1));

				return keyReference;
			}
		);

		_configurationSecretConfigurationModelListener.onBeforeSave(
			_PID, properties);

		String keyReferenceString = KeyReferenceUtil.toKeyReferenceString(
			keyReference);

		Assert.assertEquals(host, properties.get("host"));
		Assert.assertEquals(keyReferenceString, properties.get("credential"));

		Secret secret = atomicReference.get();

		KeyReference secretKeyReference = secret.getKeyReference();

		Assert.assertEquals(
			"config/" + _PID + "/0/credential",
			secretKeyReference.getIdentifier());
		Assert.assertEquals(
			StringPool.STAR, secretKeyReference.getProviderId());

		Assert.assertTrue(secret.isDestroyed());
	}

	@Test
	public void testOnBeforeSaveWhenBundleCannotResolveKeyReference()
		throws Exception {

		Mockito.doThrow(
			ClassNotFoundException.class
		).when(
			_bundle
		).loadClass(
			Mockito.anyString()
		);

		String value = RandomTestUtil.randomString();

		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"credential", value
			).build();

		_configurationSecretConfigurationModelListener.onBeforeSave(
			_PID, properties);

		Assert.assertEquals(value, properties.get("credential"));

		Mockito.verifyNoInteractions(_secretManager);
	}

	@Test
	public void testOnBeforeSaveWhenKeyManagerProfileIsInactive()
		throws Exception {

		_setUpKeyManagerProfileRegistrySnapshot(null);

		String value = RandomTestUtil.randomString();

		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"credential", value
			).build();

		_configurationSecretConfigurationModelListener.onBeforeSave(
			_PID, properties);

		Assert.assertEquals(value, properties.get("credential"));

		Mockito.verifyNoInteractions(_secretManager);
	}

	@Test
	public void testOnBeforeSaveWhenReferenceNamesAnotherConfiguration() {
		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"credential",
				"${secretRef:provider:config/com.liferay.other/0/credential}"
			).build();

		Assert.assertThrows(
			ConfigurationModelListenerException.class,
			() -> _configurationSecretConfigurationModelListener.onBeforeSave(
				_PID, properties));
	}

	@Test
	public void testOnBeforeSaveWhenScopeIsCompany() throws Exception {
		long companyId = RandomTestUtil.randomLong();

		AtomicReference<Secret> atomicReference = new AtomicReference<>();

		Mockito.when(
			_secretManager.putSecret(Mockito.eq(companyId), Mockito.any())
		).thenAnswer(
			invocationOnMock -> {
				atomicReference.set(invocationOnMock.getArgument(1));

				return new KeyReference(
					StringBundler.concat(
						"config/", _PID, StringPool.SLASH, companyId,
						"/credential"),
					"provider", KeyReference.Type.SECRET);
			}
		);

		_configurationSecretConfigurationModelListener.onBeforeSave(
			_PID,
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", companyId
			).put(
				"credential", RandomTestUtil.randomString()
			).build());

		Mockito.verify(
			_secretManager
		).putSecret(
			Mockito.eq(companyId),
			Mockito.argThat(
				secret -> {
					atomicReference.set(secret);

					return true;
				})
		);

		Secret secret = atomicReference.get();

		KeyReference keyReference = secret.getKeyReference();

		Assert.assertEquals(
			StringBundler.concat(
				"config/", _PID, StringPool.SLASH, companyId, "/credential"),
			keyReference.getIdentifier());
	}

	@Test
	public void testOnBeforeSaveWhenSecretManagerIsUnavailable()
		throws Exception {

		_setUpSecretManagerSnapshot(null);

		String value = RandomTestUtil.randomString();

		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"credential", value
			).build();

		Assert.assertThrows(
			ConfigurationModelListenerException.class,
			() -> _configurationSecretConfigurationModelListener.onBeforeSave(
				_PID, properties));

		Assert.assertEquals(value, properties.get("credential"));
	}

	@Test
	public void testOnBeforeSaveWhenValueIsAlreadyVaulted() throws Exception {
		String value = "${secretRef:provider:config/" + _PID + "/0/credential}";

		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"credential", value
			).build();

		_configurationSecretConfigurationModelListener.onBeforeSave(
			_PID, properties);

		Assert.assertEquals(value, properties.get("credential"));

		Mockito.verifyNoInteractions(_secretManager);
	}

	private void _setUpKeyManagerProfileRegistrySnapshot(
		KeyManagerProfile keyManagerProfile) {

		KeyManagerProfileRegistry keyManagerProfileRegistry = Mockito.mock(
			KeyManagerProfileRegistry.class);

		Mockito.when(
			keyManagerProfileRegistry.getActiveKeyManagerProfile()
		).thenReturn(
			keyManagerProfile
		);

		ReflectionTestUtil.setFieldValue(
			ConfigurationSecretConfigurationModelListener.class,
			"_keyManagerProfileRegistrySnapshot",
			new Snapshot<KeyManagerProfileRegistry>(
				ConfigurationSecretConfigurationModelListener.class,
				KeyManagerProfileRegistry.class) {

				@Override
				public KeyManagerProfileRegistry get() {
					return keyManagerProfileRegistry;
				}

			});
	}

	private void _setUpSecretManagerSnapshot(SecretManager secretManager) {
		ReflectionTestUtil.setFieldValue(
			ConfigurationSecretConfigurationModelListener.class,
			"_secretManagerSnapshot",
			new Snapshot<SecretManager>(
				ConfigurationSecretConfigurationModelListener.class,
				SecretManager.class) {

				@Override
				public SecretManager get() {
					return secretManager;
				}

			});
	}

	private ExtendedAttributeDefinition _toAttributeDefinition(
		String id, int type) {

		ExtendedAttributeDefinition attributeDefinition = Mockito.mock(
			ExtendedAttributeDefinition.class);

		Mockito.when(
			attributeDefinition.getID()
		).thenReturn(
			id
		);

		Mockito.when(
			attributeDefinition.getType()
		).thenReturn(
			type
		);

		return attributeDefinition;
	}

	private static final String _PID = "com.liferay.test.Configuration";

	@Mock
	private Bundle _bundle;

	@Mock
	private BundleContext _bundleContext;

	private final ConfigurationSecretConfigurationModelListener
		_configurationSecretConfigurationModelListener =
			new ConfigurationSecretConfigurationModelListener();

	@Mock
	private ExtendedMetaTypeInformation _extendedMetaTypeInformation;

	@Mock
	private ExtendedMetaTypeService _extendedMetaTypeService;

	@Mock
	private KeyManagerProfile _keyManagerProfile;

	@Mock
	private ExtendedObjectClassDefinition _objectClassDefinition;

	@Mock
	private SecretManager _secretManager;

}