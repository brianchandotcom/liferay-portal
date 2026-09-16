/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.internal.configuration.persistence.listener;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.metatype.definitions.ExtendedMetaTypeInformation;
import com.liferay.portal.configuration.metatype.definitions.ExtendedMetaTypeService;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.key.KeyReference;
import com.liferay.portal.security.key.KeyReferenceUtil;
import com.liferay.portal.security.key.secret.Secret;
import com.liferay.portal.security.key.secret.SecretManager;
import com.liferay.portal.security.key.secret.SecretResolver;
import com.liferay.portal.security.key.spi.profile.KeyManagerProfileRegistry;

import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.ObjectClassDefinition;

/**
 * @author Pedro Victor Silvestre
 */
@Component(
	property = "model.class.name=*", service = ConfigurationModelListener.class
)
public class ConfigurationSecretConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		KeyManagerProfileRegistry keyManagerProfileRegistry =
			_keyManagerProfileRegistrySnapshot.get();

		if ((keyManagerProfileRegistry == null) ||
			(keyManagerProfileRegistry.getActiveKeyManagerProfile() == null)) {

			return;
		}

		ObjectClassDefinition objectClassDefinition = _getObjectClassDefinition(
			pid, properties);

		if (objectClassDefinition == null) {
			return;
		}

		long companyId = GetterUtil.getLong(
			properties.get(
				ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey()),
			CompanyConstants.SYSTEM);

		for (AttributeDefinition attributeDefinition :
				objectClassDefinition.getAttributeDefinitions(
					ObjectClassDefinition.ALL)) {

			if (attributeDefinition.getType() != AttributeDefinition.PASSWORD) {
				continue;
			}

			String id = attributeDefinition.getID();

			if (!(properties.get(id) instanceof String value) ||
				Validator.isNull(value)) {

				continue;
			}

			String identifier = StringBundler.concat(
				_IDENTIFIER_PREFIX, pid, StringPool.SLASH, companyId,
				StringPool.SLASH, id);

			KeyReference keyReference = KeyReferenceUtil.parseKeyReference(
				value);

			if (keyReference != null) {
				_validateKeyReference(identifier, keyReference, pid);

				continue;
			}

			try {
				properties.put(
					id, _getKeyReferenceString(companyId, identifier, value));
			}
			catch (Exception exception) {
				throw new ConfigurationModelListenerException(
					exception, Object.class,
					ConfigurationSecretConfigurationModelListener.class,
					properties);
			}
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private String _getKeyReferenceString(
			long companyId, String identifier, String value)
		throws Exception {

		SecretManager secretManager = _secretManagerSnapshot.get();

		if (secretManager == null) {
			throw new IllegalStateException("Secret manager is unavailable");
		}

		try (Secret secret = new Secret(
				new KeyReference(
					identifier, StringPool.STAR, KeyReference.Type.SECRET),
				value)) {

			return KeyReferenceUtil.toKeyReferenceString(
				secretManager.putSecret(companyId, secret));
		}
	}

	private ObjectClassDefinition _getObjectClassDefinition(
		String pid, Dictionary<String, Object> properties) {

		String factoryPid = GetterUtil.getString(
			properties.get(ConfigurationAdmin.SERVICE_FACTORYPID));
		String metaTypePid = pid;

		if (Validator.isNotNull(factoryPid)) {
			metaTypePid = factoryPid;
		}

		for (Bundle bundle : _bundleContext.getBundles()) {
			ExtendedMetaTypeInformation extendedMetaTypeInformation =
				_extendedMetaTypeService.getMetaTypeInformation(bundle);

			if ((extendedMetaTypeInformation == null) ||
				(!ArrayUtil.contains(
					extendedMetaTypeInformation.getFactoryPids(),
					metaTypePid) &&
				 !ArrayUtil.contains(
					 extendedMetaTypeInformation.getPids(), metaTypePid))) {

				continue;
			}

			if (!_isSecretResolverClassLoaded(bundle)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Not vaulting configuration \"", pid,
							"\" because bundle \"", bundle.getSymbolicName(),
							"\" cannot resolve a key reference"));
				}

				return null;
			}

			return extendedMetaTypeInformation.getObjectClassDefinition(
				metaTypePid, null);
		}

		return null;
	}

	private boolean _isSecretResolverClassLoaded(Bundle bundle) {
		try {
			bundle.loadClass(SecretResolver.class.getName());

			return true;
		}
		catch (ClassNotFoundException classNotFoundException) {
			if (_log.isDebugEnabled()) {
				_log.debug(classNotFoundException);
			}
		}

		return false;
	}

	private void _validateKeyReference(
			String identifier, KeyReference keyReference, String pid)
		throws ConfigurationModelListenerException {

		String valueIdentifier = keyReference.getIdentifier();

		if (!valueIdentifier.startsWith(_IDENTIFIER_PREFIX) ||
			valueIdentifier.equals(identifier)) {

			return;
		}

		throw new ConfigurationModelListenerException(
			StringBundler.concat(
				"Configuration \"", pid,
				"\" cannot reference a value belonging to another ",
				"configuration"),
			Object.class, ConfigurationSecretConfigurationModelListener.class,
			null);
	}

	private static final String _IDENTIFIER_PREFIX = "config/";

	private static final Log _log = LogFactoryUtil.getLog(
		ConfigurationSecretConfigurationModelListener.class);

	private static final Snapshot<KeyManagerProfileRegistry>
		_keyManagerProfileRegistrySnapshot = new Snapshot<>(
			ConfigurationSecretConfigurationModelListener.class,
			KeyManagerProfileRegistry.class, null, true);
	private static final Snapshot<SecretManager> _secretManagerSnapshot =
		new Snapshot<>(
			ConfigurationSecretConfigurationModelListener.class,
			SecretManager.class, null, true);

	private BundleContext _bundleContext;

	@Reference
	private ExtendedMetaTypeService _extendedMetaTypeService;

}