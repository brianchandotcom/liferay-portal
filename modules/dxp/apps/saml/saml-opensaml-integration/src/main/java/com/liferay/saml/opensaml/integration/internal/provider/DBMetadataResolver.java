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

package com.liferay.saml.opensaml.integration.internal.provider;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.opensaml.integration.internal.util.SamlUtil;
import com.liferay.saml.persistence.exception.NoSuchIdpSpConnectionException;
import com.liferay.saml.persistence.exception.NoSuchSpIdpConnectionException;
import com.liferay.saml.persistence.model.SamlIdpSpConnection;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlIdpSpConnectionLocalService;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;

import java.io.StringReader;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import net.shibboleth.utilities.java.support.xml.ParserPool;

import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.metadata.resolver.MetadataResolver;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.opensaml.saml.metadata.resolver.impl.AbstractMetadataResolver;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Mika Koivisto
 */
@Component(scope = ServiceScope.PROTOTYPE, service = MetadataResolver.class)
public class DBMetadataResolver extends AbstractMetadataResolver {

	@Nonnull
	@Override
	public Iterable<EntityDescriptor> resolve(@Nullable CriteriaSet criteriaSet)
		throws ResolverException {

		if (criteriaSet == null) {
			return Collections.emptyList();
		}

		EntityIdCriterion entityIdCriterion = criteriaSet.get(
			EntityIdCriterion.class);

		if (entityIdCriterion == null) {
			throw new ResolverException("Entity ID criterion is null");
		}

		try {
			EntityDescriptor entityDescriptor = _getEntityDescriptor(
				entityIdCriterion.getEntityId());

			if (isValid(entityDescriptor)) {
				return Collections.singletonList(entityDescriptor);
			}

			return Collections.emptyList();
		}
		catch (Exception exception) {
			throw new ResolverException(exception);
		}
	}

	@Override
	public void setParserPool(ParserPool parserPool) {
		super.setParserPool(parserPool);

		_parserPool = parserPool;
	}

	@Nonnull
	@Override
	protected List<EntityDescriptor> lookupEntityID(@Nonnull String entityID)
		throws ResolverException {

		try {
			EntityDescriptor entityDescriptor = _getEntityDescriptor(entityID);

			if (entityDescriptor == null) {
				return Collections.emptyList();
			}

			return Collections.singletonList(entityDescriptor);
		}
		catch (Exception exception) {
			throw new ResolverException(exception);
		}
	}

	private EntityDescriptor _getEntityDescriptor(String entityID)
		throws Exception {

		return SamlUtil.getEntityDescriptorById(
			entityID, _getMetadata(entityID));
	}

	private XMLObject _getMetadata(String entityID) throws Exception {
		String metadataXml = _getMetadataXml(entityID);

		if (Validator.isNull(metadataXml)) {
			return null;
		}

		XMLObject metadataXMLObject = XMLObjectSupport.unmarshallFromReader(
			_parserPool, new StringReader(metadataXml));

		MetadataFilter metadataFilter = getMetadataFilter();

		if (metadataFilter != null) {
			metadataXMLObject = metadataFilter.filter(metadataXMLObject);
		}

		return metadataXMLObject;
	}

	private String _getMetadataXml(String entityId) throws Exception {
		long companyId = CompanyThreadLocal.getCompanyId();

		if (_samlProviderConfigurationHelper.isRoleIdp()) {
			try {
				SamlIdpSpConnection samlIdpSpConnection =
					_samlIdpSpConnectionLocalService.getSamlIdpSpConnection(
						companyId, entityId);

				if (!samlIdpSpConnection.isEnabled()) {
					return null;
				}

				return samlIdpSpConnection.getMetadataXml();
			}
			catch (NoSuchIdpSpConnectionException
						noSuchIdpSpConnectionException) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						noSuchIdpSpConnectionException,
						noSuchIdpSpConnectionException);
				}

				return null;
			}
		}
		else if (_samlProviderConfigurationHelper.isRoleSp()) {
			try {
				SamlSpIdpConnection samlSpIdpConnection =
					_samlSpIdpConnectionLocalService.getSamlSpIdpConnection(
						companyId, entityId);

				if (!samlSpIdpConnection.isEnabled()) {
					return null;
				}

				return samlSpIdpConnection.getMetadataXml();
			}
			catch (NoSuchSpIdpConnectionException
						noSuchSpIdpConnectionException) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						noSuchSpIdpConnectionException,
						noSuchSpIdpConnectionException);
				}

				return null;
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DBMetadataResolver.class);

	@Reference
	private ParserPool _parserPool;

	@Reference
	private SamlIdpSpConnectionLocalService _samlIdpSpConnectionLocalService;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

}