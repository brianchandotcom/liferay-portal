/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.opensaml.integration.internal.decryption;

import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.saml.opensaml.integration.metadata.MetadataManager;
import com.liferay.saml.runtime.SamlException;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;

import java.util.Collections;

import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import net.shibboleth.utilities.java.support.xml.ParserPool;

import org.opensaml.saml.saml2.encryption.Decrypter;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.DecryptionParameters;
import org.opensaml.xmlsec.DecryptionParametersResolver;
import org.opensaml.xmlsec.SecurityConfigurationSupport;
import org.opensaml.xmlsec.criterion.DecryptionConfigurationCriterion;
import org.opensaml.xmlsec.impl.BasicDecryptionParametersResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true, service = {})
public class DecrypterRegistrator {

	@Activate
	protected void activate(BundleContext bundleContext)
		throws ResolverException {

		DecryptionParametersResolver decryptionParametersResolver =
			new BasicDecryptionParametersResolver();

		DecryptionParameters decryptionParameters =
			decryptionParametersResolver.resolveSingle(
				new CriteriaSet(
					new DecryptionConfigurationCriterion(
						SecurityConfigurationSupport.
							getGlobalDecryptionConfiguration())));

		if (decryptionParameters == null) {
			throw new ResolverException(
				"Unable to resolve decryption parameters from the " +
					"configuration");
		}

		decryptionParameters.setKEKKeyInfoCredentialResolver(
			new DefaultKeyInfoCredentialResolver());

		Decrypter decrypter = new CustomParserPoolDecrypter(
			decryptionParameters);

		decrypter.setRootInNewDocument(true);

		_decrypterServiceRegistration = bundleContext.registerService(
			Decrypter.class, decrypter,
			new HashMapDictionary<String, Object>() {
				{
					put(Constants.SERVICE_RANKING, Integer.MIN_VALUE);
				}
			});
	}

	@Deactivate
	protected void deactivate() {
		_decrypterServiceRegistration.unregister();
	}

	private ServiceRegistration<Decrypter> _decrypterServiceRegistration;

	@Reference
	private MetadataManager _metadataManager;

	@Reference
	private ParserPool _parserPool;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	private class CustomParserPoolDecrypter extends Decrypter {

		public CustomParserPoolDecrypter(
			DecryptionParameters decryptionParameters) {

			super(decryptionParameters);
		}

		@Override
		protected ParserPool buildParserPool() {
			return _parserPool;
		}

	}

	private class DefaultKeyInfoCredentialResolver
		implements KeyInfoCredentialResolver {

		@Override
		public Iterable<Credential> resolve(CriteriaSet criteria)
			throws ResolverException {

			return Collections.singletonList(resolveSingle(criteria));
		}

		@Override
		public Credential resolveSingle(CriteriaSet criteria)
			throws ResolverException {

			try {
				return _metadataManager.getEncryptionCredential();
			}
			catch (SamlException se) {
				throw new ResolverException(se);
			}
		}

	}

}