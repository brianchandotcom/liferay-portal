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

package com.liferay.saml.opensaml.integration.internal.resolver;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.saml.opensaml.integration.internal.util.SamlUtil;
import com.liferay.saml.opensaml.integration.resolver.AttributeResolver;
import com.liferay.saml.opensaml.integration.resolver.Resolver;
import com.liferay.saml.opensaml.integration.resolver.UserResolver;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.opensaml.messaging.context.InOutOperationContext;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.messaging.context.SAMLMetadataContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.common.messaging.context.SAMLSubjectNameIdentifierContext;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AttributeStatement;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml.saml2.metadata.SingleSignOnService;

/**
 * @author Carlos Sierra Andrés
 */
public interface SAMLCommands {

	public static Resolver.SAMLCommand
		<Map<String, List<Serializable>>, UserResolver>
			bearerAssertionAttributesWithMapping(
				Properties userAttributeMappingsProperties) {

		return new UserResolverSAMLCommand<>(
			messageContext -> {
				InOutOperationContext<?, ?> inOutOperationContext =
					messageContext.getSubcontext(
						InOutOperationContext.class, false);

				if (inOutOperationContext == null) {
					return Collections.emptyMap();
				}

				MessageContext<?> inboundMessageContext =
					inOutOperationContext.getInboundMessageContext();

				if (inboundMessageContext == null) {
					return Collections.emptyMap();
				}

				SubjectAssertionContext subjectAssertionContext =
					inboundMessageContext.getSubcontext(
						SubjectAssertionContext.class, false);

				if (subjectAssertionContext == null) {
					return Collections.emptyMap();
				}

				Assertion assertion = subjectAssertionContext.getAssertion();

				List<Attribute> attributes = new ArrayList<>();

				for (AttributeStatement attributeStatement :
						assertion.getAttributeStatements()) {

					attributes.addAll(attributeStatement.getAttributes());
				}

				return SamlUtil.getAttributesMap(
					attributes, userAttributeMappingsProperties);
			});
	}

	public static Resolver.SAMLCommand<List<String>, AttributeResolver>
		ssoServicesLocationForBinding(String binding) {

		return new AttributeResolverSAMLCommand<>(
			messageContext -> {
				SAMLMetadataContext samlMetadataContext =
					messageContext.getSubcontext(
						SAMLMetadataContext.class, false);

				IDPSSODescriptor idpSSODescriptor =
					(IDPSSODescriptor)samlMetadataContext.getRoleDescriptor();

				if (idpSSODescriptor == null) {
					return null;
				}

				List<SingleSignOnService> singleSignOnServices =
					idpSSODescriptor.getSingleSignOnServices();

				if (singleSignOnServices == null) {
					return null;
				}

				return TransformUtil.transform(
					ListUtil.filter(
						singleSignOnServices,
						ssos -> binding.equals(ssos.getBinding())),
					SingleSignOnService::getLocation);
			});
	}

	public Resolver.SAMLCommand<String, Resolver> peerEntityId =
		new SAMLCommandImpl<>(
			messageContext -> {
				SAMLPeerEntityContext samlPeerEntityContext =
					messageContext.getSubcontext(
						SAMLPeerEntityContext.class, false);

				return samlPeerEntityContext.getEntityId();
			});

	public Resolver.SAMLCommand<String, Resolver> subjectNameFormat =
		new SAMLCommandImpl<>(
			messageContext -> {
				SAMLSubjectNameIdentifierContext
					samlSubjectNameIdentifierContext =
						messageContext.getSubcontext(
							SAMLSubjectNameIdentifierContext.class, false);

				NameID nameID =
					samlSubjectNameIdentifierContext.getSAML2SubjectNameID();

				if (nameID == null) {
					return null;
				}

				return nameID.getFormat();
			});

	public Resolver.SAMLCommand<String, Resolver> subjectNameIdentifier =
		new SAMLCommandImpl<>(
			messageContext -> {
				SAMLSubjectNameIdentifierContext
					samlSubjectNameIdentifierContext =
						messageContext.getSubcontext(
							SAMLSubjectNameIdentifierContext.class, false);

				NameID nameID =
					samlSubjectNameIdentifierContext.getSAML2SubjectNameID();

				if (nameID == null) {
					return null;
				}

				return nameID.getValue();
			});

	public Resolver.SAMLCommand<String, Resolver> subjectNameQualifier =
		new SAMLCommandImpl<>(
			messageContext -> {
				SAMLSubjectNameIdentifierContext
					samlSubjectNameIdentifierContext =
						messageContext.getSubcontext(
							SAMLSubjectNameIdentifierContext.class, false);

				NameID nameID =
					samlSubjectNameIdentifierContext.getSAML2SubjectNameID();

				if (nameID == null) {
					return null;
				}

				return nameID.getNameQualifier();
			});

}