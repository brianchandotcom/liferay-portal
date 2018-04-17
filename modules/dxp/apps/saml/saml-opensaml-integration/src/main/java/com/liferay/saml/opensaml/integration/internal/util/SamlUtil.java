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

package com.liferay.saml.opensaml.integration.internal.util;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.joda.time.DateTime;

import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml2.metadata.SSODescriptor;
import org.opensaml.saml2.metadata.SingleLogoutService;
import org.opensaml.saml2.metadata.SingleSignOnService;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSAny;
import org.opensaml.xml.schema.XSBoolean;
import org.opensaml.xml.schema.XSBooleanValue;
import org.opensaml.xml.schema.XSDateTime;
import org.opensaml.xml.schema.XSInteger;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * @author Mika Koivisto
 */
public class SamlUtil {

	public static AssertionConsumerService
			getAssertionConsumerServiceForBinding(
				SPSSODescriptor spSSODescriptor, String binding)
		throws MetadataProviderException {

		AssertionConsumerService assertionConsumerService =
			spSSODescriptor.getDefaultAssertionConsumerService();

		if (binding.equals(assertionConsumerService.getBinding())) {
			return assertionConsumerService;
		}

		List<AssertionConsumerService> assertionConsumerServices =
			spSSODescriptor.getAssertionConsumerServices();

		for (AssertionConsumerService curAssertionConsumerService :
				assertionConsumerServices) {

			if (binding.equals(curAssertionConsumerService.getBinding())) {
				return curAssertionConsumerService;
			}
		}

		throw new MetadataProviderException(
			"Binding " + binding + " is not supported");
	}

	public static Attribute getAttribute(
		List<Attribute> attributes, String attributeName) {

		for (Attribute attribute : attributes) {
			if (attributeName.equals(attribute.getName())) {
				return attribute;
			}
		}

		return null;
	}

	public static Map<String, List<Serializable>> getAttributesMap(
		List<Attribute> attributes, Properties attributeMappingsProperties) {

		Map<String, List<Serializable>> attributesMap = new HashMap<>();

		for (Attribute attribute : attributes) {
			String key = attributeMappingsProperties.getProperty(
				attribute.getName());

			if (Validator.isNull(key) &&
				Validator.isNotNull(attribute.getFriendlyName())) {

				key = attributeMappingsProperties.getProperty(
					attribute.getFriendlyName());
			}

			if (Validator.isNull(key)) {
				key = attribute.getName();
			}

			List<XMLObject> xmlValues = attribute.getAttributeValues();

			List<Serializable> values = attributesMap.get(key);

			if (values == null) {
				values = new ArrayList<>(xmlValues.size());
			}

			for (XMLObject xmlObject : xmlValues) {
				Serializable value = getXMLObjectValue(xmlObject);

				if (Validator.isNotNull(value)) {
					values.add(value);
				}
			}

			attributesMap.put(key, values);
		}

		return attributesMap;
	}

	public static EntityDescriptor getEntityDescriptorById(
		String entityId, EntitiesDescriptor descriptor) {

		List<EntityDescriptor> entityDescriptors =
			descriptor.getEntityDescriptors();

		if ((entityDescriptors != null) && !entityDescriptors.isEmpty()) {
			for (EntityDescriptor entityDescriptor : entityDescriptors) {
				if (DatatypeHelper.safeEquals(
						entityDescriptor.getEntityID(), entityId)) {

					return entityDescriptor;
				}
			}
		}

		return null;
	}

	public static EntityDescriptor getEntityDescriptorById(
		String entityId, XMLObject metadata) {

		if (metadata instanceof EntityDescriptor) {
			EntityDescriptor entityDescriptor = (EntityDescriptor)metadata;

			if (DatatypeHelper.safeEquals(
					entityDescriptor.getEntityID(), entityId)) {

				return entityDescriptor;
			}
		}
		else if (metadata instanceof EntitiesDescriptor) {
			return getEntityDescriptorById(
				entityId, (EntitiesDescriptor)metadata);
		}

		return null;
	}

	public static String getNameIdFormat(NameID nameID) {
		String format = nameID.getFormat();

		if (Validator.isNull(format)) {
			return NameID.UNSPECIFIED;
		}

		return format;
	}

	public static SingleLogoutService getSingleLogoutServiceForBinding(
			SSODescriptor ssoDescriptor, String binding)
		throws MetadataProviderException {

		List<SingleLogoutService> singleLogoutServices =
			ssoDescriptor.getSingleLogoutServices();

		for (SingleLogoutService singleLogoutService : singleLogoutServices) {
			if (binding.equals(singleLogoutService.getBinding())) {
				return singleLogoutService;
			}
		}

		throw new MetadataProviderException(
			"Binding " + binding + " is not supported");
	}

	public static SingleSignOnService getSingleSignOnServiceForBinding(
			IDPSSODescriptor idpSSODescriptor, String binding)
		throws MetadataProviderException {

		List<SingleSignOnService> singleSignOnServices =
			idpSSODescriptor.getSingleSignOnServices();

		for (SingleSignOnService singleSignOnService : singleSignOnServices) {
			if (binding.equals(singleSignOnService.getBinding())) {
				return singleSignOnService;
			}
		}

		throw new MetadataProviderException(
			"Binding " + binding + " is not supported");
	}

	public static Date getValueAsDate(
		String key, Map<String, List<Serializable>> attributesMap) {

		List<Serializable> values = attributesMap.get(key);

		if (ListUtil.isEmpty(values)) {
			return null;
		}

		DateTime dateTime = new DateTime(values.get(0));

		return dateTime.toDate();
	}

	public static DateTime getValueAsDateTime(
		String key, Map<String, List<Serializable>> attributesMap) {

		List<Serializable> values = attributesMap.get(key);

		if (ListUtil.isEmpty(values)) {
			return null;
		}

		return new DateTime(values.get(0));
	}

	public static String getValueAsString(Attribute attribute) {
		if (attribute == null) {
			return null;
		}

		List<XMLObject> values = attribute.getAttributeValues();

		if (values.isEmpty()) {
			return null;
		}

		return getValueAsString(values.get(0));
	}

	public static String getValueAsString(
		String key, Map<String, List<Serializable>> attributesMap) {

		List<Serializable> values = attributesMap.get(key);

		if (ListUtil.isEmpty(values)) {
			return null;
		}

		return String.valueOf(values.get(0));
	}

	public static String getValueAsString(XMLObject xmlObject) {
		if (xmlObject instanceof XSAny) {
			XSAny xsAny = (XSAny)xmlObject;

			return xsAny.getTextContent();
		}
		else if (xmlObject instanceof XSDateTime) {
			XSDateTime xsDateTime = (XSDateTime)xmlObject;

			return String.valueOf(xsDateTime.getValue());
		}
		else if (xmlObject instanceof XSString) {
			XSString xsString = (XSString)xmlObject;

			return xsString.getValue();
		}

		return null;
	}

	public static Serializable getXMLObjectValue(XMLObject xmlObject) {
		if (xmlObject instanceof XSAny) {
			XSAny xsAny = (XSAny)xmlObject;

			return xsAny.getTextContent();
		}
		else if (xmlObject instanceof XSBoolean) {
			XSBoolean xsBoolean = (XSBoolean)xmlObject;

			XSBooleanValue xsBooleanValue = xsBoolean.getValue();

			return xsBooleanValue.getValue();
		}
		else if (xmlObject instanceof XSDateTime) {
			XSDateTime xsDateTime = (XSDateTime)xmlObject;

			return String.valueOf(xsDateTime.getValue());
		}
		else if (xmlObject instanceof XSInteger) {
			XSInteger xsInteger = (XSInteger)xmlObject;

			return xsInteger.getValue();
		}
		else if (xmlObject instanceof XSString) {
			XSString xsString = (XSString)xmlObject;

			return xsString.getValue();
		}

		return null;
	}

	public static AssertionConsumerService resolverAssertionConsumerService(
		SAMLMessageContext<AuthnRequest, ?, ?> samlMessageContext,
		String binding) {

		AuthnRequest authnRequest = samlMessageContext.getInboundSAMLMessage();

		Integer assertionConsumerServiceIndex = null;
		String assertionConsumerServiceURL = null;

		if (authnRequest != null) {
			assertionConsumerServiceIndex =
				authnRequest.getAssertionConsumerServiceIndex();
			assertionConsumerServiceURL =
				authnRequest.getAssertionConsumerServiceURL();
		}

		SPSSODescriptor spSSODescriptor =
			(SPSSODescriptor)samlMessageContext.getPeerEntityRoleMetadata();

		for (AssertionConsumerService assertionConsumerService :
				spSSODescriptor.getAssertionConsumerServices()) {

			if (!binding.equals(assertionConsumerService.getBinding())) {
				continue;
			}

			Integer curAssertionConsumerServiceIndex =
				assertionConsumerService.getIndex();

			if ((assertionConsumerServiceIndex != null) &&
				(curAssertionConsumerServiceIndex.intValue() ==
					assertionConsumerServiceIndex.intValue())) {

				return assertionConsumerService;
			}
			else if (Validator.isNotNull(assertionConsumerServiceURL) &&
					 assertionConsumerServiceURL.equals(
						 assertionConsumerService.getLocation())) {

				return assertionConsumerService;
			}
		}

		for (AssertionConsumerService assertionConsumerService :
				spSSODescriptor.getAssertionConsumerServices()) {

			if (binding.equals(assertionConsumerService.getBinding())) {
				return assertionConsumerService;
			}
		}

		return null;
	}

	public static SingleLogoutService resolveSingleLogoutService(
		SSODescriptor ssoDescriptor, String preferredBinding) {

		List<SingleLogoutService> singleLogoutServices =
			ssoDescriptor.getSingleLogoutServices();

		for (SingleLogoutService singleLogoutService : singleLogoutServices) {
			if (preferredBinding.equals(singleLogoutService.getBinding())) {
				return singleLogoutService;
			}
		}

		if (!singleLogoutServices.isEmpty()) {
			return singleLogoutServices.get(0);
		}

		return null;
	}

	public static SingleSignOnService resolveSingleSignOnService(
		IDPSSODescriptor idpSSODescriptor, String preferredBinding) {

		List<SingleSignOnService> singleSignOnServices =
			idpSSODescriptor.getSingleSignOnServices();

		for (SingleSignOnService singleSignOnService : singleSignOnServices) {
			if (preferredBinding.equals(singleSignOnService.getBinding())) {
				return singleSignOnService;
			}
		}

		if (!singleSignOnServices.isEmpty()) {
			return singleSignOnServices.get(0);
		}

		return null;
	}

}