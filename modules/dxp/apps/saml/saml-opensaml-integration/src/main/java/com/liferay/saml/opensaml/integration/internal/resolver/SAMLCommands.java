/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.saml.opensaml.integration.internal.resolver;

import com.liferay.saml.opensaml.integration.internal.util.SamlUtil;
import com.liferay.saml.opensaml.integration.resolver.AttributeResolver;
import com.liferay.saml.opensaml.integration.resolver.Resolver;
import com.liferay.saml.opensaml.integration.resolver.Resolver.SAMLCommand;
import com.liferay.saml.opensaml.integration.resolver.UserResolver;

import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.core.SubjectConfirmation;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.SingleSignOnService;

/**
 * @author Carlos Sierra Andrés
 */
public interface SAMLCommands {

	public static SAMLCommand<Map<String, List<Serializable>>, UserResolver>
		bearerAssertionAttributesWithMapping(
			Properties userAttributeMappingsProperties) {

		return new UserResolverSAMLCommand<>(
			samlMessageContext
				-> {
				Response response = samlMessageContext.getInboundSAMLMessage();

				if (response == null) {
					return Collections.<String, List<Serializable>>emptyMap();
				}

				List<Assertion> assertions = response.getAssertions();

				Stream<Assertion> assertionsStream = assertions.stream();

				List<Attribute> bearerAssertionAttributes =
					assertionsStream.filter(
						assertion -> {
							Subject subject = assertion.getSubject();

							if (subject == null) {
								return false;
							}

							List<SubjectConfirmation> subjectConfirmations =
								subject.getSubjectConfirmations();

							if (subjectConfirmations == null) {
								return false;
							}

							Stream<SubjectConfirmation>
								subjectConfirmationsStream =
									subjectConfirmations.stream();

							long count = subjectConfirmationsStream.filter(
								subjectConfirmation ->
									SubjectConfirmation.METHOD_BEARER.equals(
										subjectConfirmation.getMethod())
							).count();

							if (count > 0) {
								return true;
							}

							return false;
						}
					).map(
						Assertion::getAttributeStatements
					).flatMap(
						Collection::stream
					).map(
						AttributeStatement::getAttributes
					).flatMap(
						Collection::stream
					).collect(
						Collectors.toList()
					);

				return SamlUtil.getAttributesMap(
					bearerAssertionAttributes, userAttributeMappingsProperties);
			});
	}

	public static SAMLCommand<List<String>, AttributeResolver>
		ssoServicesLocationForBinding(String binding) {

		return new AttributeResolverSAMLCommand<>(
			samlMessageContext -> {
				IDPSSODescriptor idpSSODescriptor =
					(IDPSSODescriptor)
						samlMessageContext.getLocalEntityRoleMetadata();

				if (idpSSODescriptor == null) {
					return null;
				}

				List<SingleSignOnService> singleSignOnServices =
					idpSSODescriptor.getSingleSignOnServices();

				if (singleSignOnServices == null) {
					return null;
				}

				Stream<SingleSignOnService> singleSignOnServicesStream =
					singleSignOnServices.stream();

				return singleSignOnServicesStream.filter(
					ssos -> binding.equals(ssos.getBinding())
				).map(
					SingleSignOnService::getLocation
				).collect(
					Collectors.toList()
				);
			});
	}

	public SAMLCommand<String, Resolver> peerEntityId = new SAMLCommandImpl<>(
		SAMLMessageContext::getPeerEntityId);

	public SAMLCommand<String, Resolver> subjectNameFormat =
		new SAMLCommandImpl<>(
			samlMessageContext -> {
				NameID nameID = samlMessageContext.getSubjectNameIdentifier();

				if (nameID == null) {
					return null;
				}

				return nameID.getFormat();
			});

	public SAMLCommand<String, Resolver> subjectNameIdentifier =
		new SAMLCommandImpl<>(
			samlMessageContext -> {
				NameID nameID = samlMessageContext.getSubjectNameIdentifier();

				if (nameID == null) {
					return null;
				}

				return nameID.getValue();
			});

}