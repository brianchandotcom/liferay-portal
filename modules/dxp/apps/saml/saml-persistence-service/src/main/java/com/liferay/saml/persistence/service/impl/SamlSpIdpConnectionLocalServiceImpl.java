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

package com.liferay.saml.persistence.service.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.persistence.exception.DuplicateSamlSpIdpConnectionSamlIdpEntityIdException;
import com.liferay.saml.persistence.exception.SamlSpIdpConnectionMetadataUrlException;
import com.liferay.saml.persistence.exception.SamlSpIdpConnectionMetadataXmlException;
import com.liferay.saml.persistence.exception.SamlSpIdpConnectionSamlIdpEntityIdException;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.base.SamlSpIdpConnectionLocalServiceBaseImpl;
import com.liferay.saml.util.MetadataUtil;

import java.io.InputStream;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	property = "model.class.name=com.liferay.saml.persistence.model.SamlSpIdpConnection",
	service = AopService.class
)
public class SamlSpIdpConnectionLocalServiceImpl
	extends SamlSpIdpConnectionLocalServiceBaseImpl {

	@Override
	public SamlSpIdpConnection addSamlSpIdpConnection(
			boolean assertionSignatureRequired, long clockSkew, boolean enabled,
			boolean forceAuthn, boolean ldapImportEnabled, String metadataUrl,
			InputStream metadataXmlInputStream, String name,
			String nameIdFormat, String samlIdpEntityId,
			boolean signAuthnRequest, boolean unknownUsersAreStrangers,
			String userAttributeMappings, String userIdentifierExpression,
			ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isNull(samlIdpEntityId)) {
			throw new SamlSpIdpConnectionSamlIdpEntityIdException(
				"SAML IDP entity ID is null");
		}

		SamlSpIdpConnection existingSamlSpIdpConnection =
			samlSpIdpConnectionPersistence.fetchByC_SIEI(
				serviceContext.getCompanyId(), samlIdpEntityId);

		if (existingSamlSpIdpConnection != null) {
			throw new DuplicateSamlSpIdpConnectionSamlIdpEntityIdException(
				"Duplicate SAML SP IDP connection for " + samlIdpEntityId);
		}

		long samlSpIdpConnectionId = counterLocalService.increment(
			SamlSpIdpConnection.class.getName());

		SamlSpIdpConnection samlSpIdpConnection =
			samlSpIdpConnectionPersistence.create(samlSpIdpConnectionId);

		samlSpIdpConnection.setCompanyId(serviceContext.getCompanyId());
		samlSpIdpConnection.setAssertionSignatureRequired(
			assertionSignatureRequired);
		samlSpIdpConnection.setClockSkew(clockSkew);
		samlSpIdpConnection.setEnabled(enabled);
		samlSpIdpConnection.setSamlIdpEntityId(samlIdpEntityId);
		samlSpIdpConnection.setExpandoBridgeAttributes(serviceContext);
		samlSpIdpConnection.setForceAuthn(forceAuthn);
		samlSpIdpConnection.setLdapImportEnabled(ldapImportEnabled);
		samlSpIdpConnection.setMetadataUpdatedDate(new Date());
		samlSpIdpConnection.setUnknownUsersAreStrangers(
			unknownUsersAreStrangers);

		if ((metadataXmlInputStream == null) &&
			Validator.isNotNull(metadataUrl)) {

			samlSpIdpConnection.setMetadataUrl(metadataUrl);

			try {
				metadataXmlInputStream = _metadataUtil.getMetadata(metadataUrl);
			}
			catch (Exception exception) {
				throw new SamlSpIdpConnectionMetadataUrlException(
					StringBundler.concat(
						"Unable to get metadata from ", metadataUrl, ": ",
						exception.getMessage()),
					exception);
			}
		}

		if (metadataXmlInputStream == null) {
			throw new SamlSpIdpConnectionMetadataUrlException(
				"Unable to get metadata from " + metadataUrl);
		}

		samlSpIdpConnection.setMetadataXml(
			getMetadataXml(metadataXmlInputStream, samlIdpEntityId));
		samlSpIdpConnection.setName(name);
		samlSpIdpConnection.setNameIdFormat(nameIdFormat);
		samlSpIdpConnection.setSamlIdpEntityId(samlIdpEntityId);
		samlSpIdpConnection.setSignAuthnRequest(signAuthnRequest);
		samlSpIdpConnection.setUserAttributeMappings(userAttributeMappings);
		samlSpIdpConnection.setUserIdentifierExpression(
			userIdentifierExpression);

		return samlSpIdpConnectionPersistence.update(samlSpIdpConnection);
	}

	@Override
	public SamlSpIdpConnection getSamlSpIdpConnection(
			long companyId, String samlIdpEntityId)
		throws PortalException {

		return samlSpIdpConnectionPersistence.findByC_SIEI(
			companyId, samlIdpEntityId);
	}

	@Override
	public List<SamlSpIdpConnection> getSamlSpIdpConnections(long companyId) {
		return samlSpIdpConnectionPersistence.findByCompanyId(companyId);
	}

	@Override
	public List<SamlSpIdpConnection> getSamlSpIdpConnections(
		long companyId, int start, int end) {

		return samlSpIdpConnectionPersistence.findByCompanyId(
			companyId, start, end);
	}

	@Override
	public List<SamlSpIdpConnection> getSamlSpIdpConnections(
		long companyId, int start, int end,
		OrderByComparator<SamlSpIdpConnection> orderByComparator) {

		return samlSpIdpConnectionPersistence.findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getSamlSpIdpConnectionsCount(long companyId) {
		return samlSpIdpConnectionPersistence.countByCompanyId(companyId);
	}

	@Override
	public void updateMetadata(long samlSpIdpConnectionId)
		throws PortalException {

		SamlSpIdpConnection samlSpIdpConnection =
			samlSpIdpConnectionPersistence.findByPrimaryKey(
				samlSpIdpConnectionId);

		String metadataUrl = samlSpIdpConnection.getMetadataUrl();

		if (Validator.isNull(metadataUrl)) {
			return;
		}

		InputStream metadataXmlInputStream = null;

		try {
			metadataXmlInputStream = _metadataUtil.getMetadata(metadataUrl);
		}
		catch (Exception exception) {
			throw new SamlSpIdpConnectionMetadataUrlException(
				StringBundler.concat(
					"Unable to get metadata from ", metadataUrl, ": ",
					exception.getMessage()),
				exception);
		}

		String metadataXml = StringPool.BLANK;

		try {
			metadataXml = _metadataUtil.parseMetadataXml(
				metadataXmlInputStream,
				samlSpIdpConnection.getSamlIdpEntityId());
		}
		catch (Exception exception) {
			throw new SamlSpIdpConnectionMetadataXmlException(
				StringBundler.concat(
					"Unable to parse metadata from ", metadataUrl, ": ",
					exception.getMessage()),
				exception);
		}

		samlSpIdpConnection.setMetadataUpdatedDate(new Date());
		samlSpIdpConnection.setMetadataXml(metadataXml);

		samlSpIdpConnectionPersistence.update(samlSpIdpConnection);
	}

	@Override
	public SamlSpIdpConnection updateSamlSpIdpConnection(
			long samlSpIdpConnectionId, boolean assertionSignatureRequired,
			long clockSkew, boolean enabled, boolean forceAuthn,
			boolean ldapImportEnabled, String metadataUrl,
			InputStream metadataXmlInputStream, String name,
			String nameIdFormat, String samlIdpEntityId,
			boolean signAuthnRequest, boolean unknownUsersAreStrangers,
			String userAttributeMappings, String userIdentifierExpression,
			ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isNull(samlIdpEntityId)) {
			throw new SamlSpIdpConnectionSamlIdpEntityIdException(
				"SAML IDP entity ID is null");
		}

		SamlSpIdpConnection samlSpIdpConnection =
			samlSpIdpConnectionPersistence.findByPrimaryKey(
				samlSpIdpConnectionId);

		if (!samlIdpEntityId.equals(samlSpIdpConnection.getSamlIdpEntityId())) {
			SamlSpIdpConnection existingSamlSpIdpConnection =
				samlSpIdpConnectionPersistence.fetchByC_SIEI(
					serviceContext.getCompanyId(), samlIdpEntityId);

			if (existingSamlSpIdpConnection != null) {
				throw new DuplicateSamlSpIdpConnectionSamlIdpEntityIdException(
					"Duplicate SAML SP IDP connection for " + samlIdpEntityId);
			}
		}

		samlSpIdpConnection.setCompanyId(serviceContext.getCompanyId());
		samlSpIdpConnection.setAssertionSignatureRequired(
			assertionSignatureRequired);
		samlSpIdpConnection.setClockSkew(clockSkew);
		samlSpIdpConnection.setEnabled(enabled);
		samlSpIdpConnection.setExpandoBridgeAttributes(serviceContext);
		samlSpIdpConnection.setForceAuthn(forceAuthn);
		samlSpIdpConnection.setLdapImportEnabled(ldapImportEnabled);
		samlSpIdpConnection.setMetadataUpdatedDate(new Date());
		samlSpIdpConnection.setMetadataUrl(StringPool.BLANK);
		samlSpIdpConnection.setUnknownUsersAreStrangers(
			unknownUsersAreStrangers);

		if ((metadataXmlInputStream == null) &&
			Validator.isNotNull(metadataUrl)) {

			samlSpIdpConnection.setMetadataUrl(metadataUrl);

			try {
				metadataXmlInputStream = _metadataUtil.getMetadata(metadataUrl);
			}
			catch (Exception exception) {
				throw new SamlSpIdpConnectionMetadataUrlException(
					StringBundler.concat(
						"Unable to get metadata from ", metadataUrl, ": ",
						exception.getMessage()),
					exception);
			}
		}

		String metadataXml = StringPool.BLANK;

		if (metadataXmlInputStream != null) {
			metadataXml = getMetadataXml(
				metadataXmlInputStream, samlIdpEntityId);
		}

		if (Validator.isNotNull(metadataXml)) {
			samlSpIdpConnection.setMetadataUpdatedDate(new Date());
			samlSpIdpConnection.setMetadataXml(metadataXml);
		}

		samlSpIdpConnection.setName(name);
		samlSpIdpConnection.setNameIdFormat(nameIdFormat);
		samlSpIdpConnection.setSamlIdpEntityId(samlIdpEntityId);
		samlSpIdpConnection.setSignAuthnRequest(signAuthnRequest);
		samlSpIdpConnection.setUserAttributeMappings(userAttributeMappings);
		samlSpIdpConnection.setUserIdentifierExpression(
			userIdentifierExpression);

		return samlSpIdpConnectionPersistence.update(samlSpIdpConnection);
	}

	protected String getMetadataXml(
			InputStream metadataXmlInputStream, String samlIdpEntityId)
		throws PortalException {

		String metadataXml = StringPool.BLANK;

		try {
			metadataXml = _metadataUtil.parseMetadataXml(
				metadataXmlInputStream, samlIdpEntityId);
		}
		catch (Exception exception) {
			throw new SamlSpIdpConnectionMetadataXmlException(exception);
		}

		if (Validator.isNull(metadataXml)) {
			throw new SamlSpIdpConnectionSamlIdpEntityIdException(
				"Metadata XML is null for " + samlIdpEntityId);
		}

		return metadataXml;
	}

	@Reference
	private MetadataUtil _metadataUtil;

}