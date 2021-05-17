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

package com.liferay.saml.web.internal.opensaml.integration.field.expression.handler;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.opensaml.integration.field.expression.handler.SamlSpIdpConnectionFieldExpressionHandler;
import com.liferay.saml.opensaml.integration.field.expression.handler.registry.UserFieldExpressionHandlerRegistry;
import com.liferay.saml.opensaml.integration.processor.context.SamlSpIdpConnectionProcessorContext;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 */
@Component(
	property = {"prefix=", "processing.index:Integer=" + Integer.MAX_VALUE},
	service = SamlSpIdpConnectionFieldExpressionHandler.class
)
public class DefaultSamlSpIdpConnectionFieldExpressionHandler
	implements SamlSpIdpConnectionFieldExpressionHandler {

	@Override
	public void bindProcessorContext(
		SamlSpIdpConnectionProcessorContext processorContext) {

		SamlSpIdpConnectionProcessorContext.SamlSpIdpConnectionBind
			<SamlSpIdpConnection> fieldExpressionMapper = processorContext.bind(
				Integer.MIN_VALUE, this::_update);

		fieldExpressionMapper.mapBoolean(
			"assertionSignatureRequired",
			SamlSpIdpConnection::setAssertionSignatureRequired);
		fieldExpressionMapper.mapLong(
			"clockSkew", SamlSpIdpConnection::setClockSkew);
		fieldExpressionMapper.mapBoolean(
			"enabled", SamlSpIdpConnection::setEnabled);
		fieldExpressionMapper.mapBoolean(
			"forceAuthn", SamlSpIdpConnection::setForceAuthn);
		fieldExpressionMapper.mapBoolean(
			"ldapImportEnabled", SamlSpIdpConnection::setLdapImportEnabled);
		fieldExpressionMapper.mapBoolean(
			"unknownUsersAreStrangers",
			SamlSpIdpConnection::setUnknownUsersAreStrangers);
		fieldExpressionMapper.mapString(
			"metadataUrl", SamlSpIdpConnection::setMetadataUrl);

		fieldExpressionMapper.handleFileItemArray(
			"metadataXml",
			(samlSpIdpConnection, fileItems) -> {
				if ((fileItems == null) || (fileItems.length == 0)) {

					// This allows for metadataUrl to be dereferenced

					samlSpIdpConnection.setMetadataXml(null);

					return;
				}

				FileItem fileItem = fileItems[0];

				samlSpIdpConnection.setMetadataXml(fileItem.getString());
			});

		fieldExpressionMapper.mapString("name", SamlSpIdpConnection::setName);
		fieldExpressionMapper.mapString(
			"nameIdFormat", SamlSpIdpConnection::setNameIdFormat);
		fieldExpressionMapper.mapString(
			"samlIdpEntityId", SamlSpIdpConnection::setSamlIdpEntityId);
		fieldExpressionMapper.mapBoolean(
			"signAuthnRequest", SamlSpIdpConnection::setSignAuthnRequest);
		fieldExpressionMapper.mapString(
			"userIdentifierExpression",
			SamlSpIdpConnection::setUserIdentifierExpression);

		processorContext.bind(_processingIndex, this::_persist);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_processingIndex = GetterUtil.getInteger(
			properties.get("processing.index"));
	}

	private SamlSpIdpConnection _persist(
			SamlSpIdpConnection currentSamlSpIdpConnection,
			SamlSpIdpConnection newSamlSpIdpConnection,
			ServiceContext serviceContext)
		throws PortalException {

		String metadataXml = newSamlSpIdpConnection.getMetadataXml();
		InputStream metadataXmlInputStream = null;

		if (Validator.isNotNull(metadataXml)) {
			metadataXmlInputStream = new ByteArrayInputStream(
				metadataXml.getBytes(StandardCharsets.UTF_8));
		}

		if (newSamlSpIdpConnection.getSamlSpIdpConnectionId() <= 0) {
			_samlSpIdpConnectionLocalService.addSamlSpIdpConnection(
				newSamlSpIdpConnection.isAssertionSignatureRequired(),
				newSamlSpIdpConnection.getClockSkew(),
				newSamlSpIdpConnection.isEnabled(),
				newSamlSpIdpConnection.isForceAuthn(),
				newSamlSpIdpConnection.isLdapImportEnabled(),
				newSamlSpIdpConnection.getMetadataUrl(), metadataXmlInputStream,
				newSamlSpIdpConnection.getName(),
				newSamlSpIdpConnection.getNameIdFormat(),
				newSamlSpIdpConnection.getSamlIdpEntityId(),
				newSamlSpIdpConnection.isSignAuthnRequest(),
				newSamlSpIdpConnection.isUnknownUsersAreStrangers(),
				newSamlSpIdpConnection.getUserAttributeMappings(),
				newSamlSpIdpConnection.getUserIdentifierExpression(),
				serviceContext);
		}
		else {
			_samlSpIdpConnectionLocalService.updateSamlSpIdpConnection(
				newSamlSpIdpConnection.getSamlSpIdpConnectionId(),
				newSamlSpIdpConnection.isAssertionSignatureRequired(),
				newSamlSpIdpConnection.getClockSkew(),
				newSamlSpIdpConnection.isEnabled(),
				newSamlSpIdpConnection.isForceAuthn(),
				newSamlSpIdpConnection.isLdapImportEnabled(),
				newSamlSpIdpConnection.getMetadataUrl(), metadataXmlInputStream,
				newSamlSpIdpConnection.getName(),
				newSamlSpIdpConnection.getNameIdFormat(),
				newSamlSpIdpConnection.getSamlIdpEntityId(),
				newSamlSpIdpConnection.isSignAuthnRequest(),
				newSamlSpIdpConnection.isUnknownUsersAreStrangers(),
				newSamlSpIdpConnection.getUserAttributeMappings(),
				newSamlSpIdpConnection.getUserIdentifierExpression(),
				serviceContext);
		}

		return null;
	}

	private SamlSpIdpConnection _update(
			SamlSpIdpConnection currentSamlSpIdpConnection,
			SamlSpIdpConnection newSamlSpIdpConnection,
			ServiceContext serviceContext)
		throws PortalException {

		return newSamlSpIdpConnection;
	}

	private int _processingIndex;

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

	@Reference
	private UserFieldExpressionHandlerRegistry
		_userFieldExpressionHandlerRegistry;

}