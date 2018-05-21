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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.joda.time.DateTime;

import org.opensaml.saml2.common.Extensions;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.provider.BaseMetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.xml.Namespace;
import org.opensaml.xml.NamespaceManager;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.schema.XSBooleanValue;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.util.IDIndex;
import org.opensaml.xml.util.LazySet;
import org.opensaml.xml.util.XMLObjectHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.w3c.dom.Element;

/**
 * @author Mika Koivisto
 */
@Component(immediate = true, service = MetadataProvider.class)
public class DBMetadataProvider extends BaseMetadataProvider {

	@Override
	public EntitiesDescriptor getEntitiesDescriptor(String name) {
		return null;
	}

	@Override
	public EntityDescriptor getEntityDescriptor(String entityId)
		throws MetadataProviderException {

		try {
			String metadataXml = getMetadataXml(entityId);

			if (Validator.isNull(metadataXml)) {
				return null;
			}

			XMLObject metadataXmlObject = XMLObjectHelper.unmarshallFromReader(
				_parserPool, new StringReader(metadataXml));

			EntityDescriptor entityDescriptor =
				SamlUtil.getEntityDescriptorById(entityId, metadataXmlObject);

			return entityDescriptor;
		}
		catch (Exception e) {
			throw new MetadataProviderException(e);
		}
	}

	@Override
	public XMLObject getMetadata() {
		return new DBEntitiesDescriptor();
	}

	@Override
	public List<RoleDescriptor> getRole(String entityId, QName qName)
		throws MetadataProviderException {

		EntityDescriptor entityDescriptor = getEntityDescriptor(entityId);

		if (entityDescriptor != null) {
			return entityDescriptor.getRoleDescriptors(qName);
		}

		return null;
	}

	@Override
	public RoleDescriptor getRole(
			String entityId, QName qName, String supportedProtocol)
		throws MetadataProviderException {

		List<RoleDescriptor> roleDescriptors = getRole(entityId, qName);

		if ((roleDescriptors == null) || roleDescriptors.isEmpty()) {
			return null;
		}

		for (RoleDescriptor roleDescriptor : roleDescriptors) {
			if (roleDescriptor.isSupportedProtocol(supportedProtocol)) {
				return roleDescriptor;
			}
		}

		return null;
	}

	public void setParserPool(ParserPool parserPool) {
		_parserPool = parserPool;
	}

	protected String getMetadataXml(String entityId) throws Exception {
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
			catch (NoSuchIdpSpConnectionException nsisce) {
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
			catch (NoSuchSpIdpConnectionException nssice) {
				return null;
			}
		}

		return null;
	}

	@Reference
	private ParserPool _parserPool;

	@Reference
	private SamlIdpSpConnectionLocalService _samlIdpSpConnectionLocalService;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

	private class DBEntitiesDescriptor implements EntitiesDescriptor {

		public DBEntitiesDescriptor() {
		}

		@Override
		public void addNamespace(Namespace namespace) {
		}

		@Override
		@SuppressWarnings("rawtypes")
		public void deregisterValidator(
			org.opensaml.xml.validation.Validator validator) {
		}

		@Override
		public void detach() {
		}

		@Override
		public Long getCacheDuration() {
			return null;
		}

		@Override
		public Element getDOM() {
			return null;
		}

		@Override
		public QName getElementQName() {
			return EntitiesDescriptor.DEFAULT_ELEMENT_NAME;
		}

		@Override
		public List<EntitiesDescriptor> getEntitiesDescriptors() {
			List<EntitiesDescriptor> entitiesDescriptors = new ArrayList<>();

			for (XMLObject xmlObject : _xmlObjects) {
				if (xmlObject instanceof EntitiesDescriptor) {
					EntitiesDescriptor entitiesDescriptor =
						(EntitiesDescriptor)xmlObject;

					entitiesDescriptors.add(entitiesDescriptor);
				}
			}

			return entitiesDescriptors;
		}

		@Override
		public List<EntityDescriptor> getEntityDescriptors() {
			List<EntityDescriptor> entityDescriptors = new ArrayList<>();

			for (XMLObject xmlObject : _xmlObjects) {
				if (xmlObject instanceof EntityDescriptor) {
					EntityDescriptor entityDescriptor =
						(EntityDescriptor)xmlObject;

					entityDescriptors.add(entityDescriptor);
				}
			}

			return entityDescriptors;
		}

		@Override
		public Extensions getExtensions() {
			return null;
		}

		@Override
		public String getID() {
			return null;
		}

		@Override
		public IDIndex getIDIndex() {
			return null;
		}

		@Override
		public String getName() {
			return null;
		}

		@Override
		public NamespaceManager getNamespaceManager() {
			return null;
		}

		@Override
		public Set<Namespace> getNamespaces() {
			return new LazySet<>();
		}

		@Override
		public String getNoNamespaceSchemaLocation() {
			return null;
		}

		@Override
		public List<XMLObject> getOrderedChildren() {
			return new ArrayList<>();
		}

		@Override
		public XMLObject getParent() {
			return null;
		}

		@Override
		public String getSchemaLocation() {
			return null;
		}

		@Override
		public QName getSchemaType() {
			return EntitiesDescriptor.TYPE_NAME;
		}

		@Override
		public Signature getSignature() {
			return null;
		}

		@Override
		public String getSignatureReferenceID() {
			return null;
		}

		@Override
		@SuppressWarnings("rawtypes")
		public List<org.opensaml.xml.validation.Validator> getValidators() {
			return new ArrayList<>();
		}

		@Override
		public DateTime getValidUntil() {
			return null;
		}

		@Override
		public boolean hasChildren() {
			List<XMLObject> xmlObjects = getOrderedChildren();

			return !xmlObjects.isEmpty();
		}

		@Override
		public boolean hasParent() {
			return false;
		}

		@Override
		public Boolean isNil() {
			return Boolean.FALSE;
		}

		@Override
		public XSBooleanValue isNilXSBoolean() {
			return new XSBooleanValue(Boolean.FALSE, false);
		}

		@Override
		public boolean isSigned() {
			return false;
		}

		@Override
		public boolean isValid() {
			return true;
		}

		@Override
		@SuppressWarnings("rawtypes")
		public void registerValidator(
			org.opensaml.xml.validation.Validator validator) {
		}

		@Override
		public void releaseChildrenDOM(boolean propagateRelease) {
		}

		@Override
		public void releaseDOM() {
		}

		@Override
		public void releaseParentDOM(boolean propagateRelease) {
		}

		@Override
		public void removeNamespace(Namespace namespace) {
		}

		@Override
		public XMLObject resolveID(String id) {
			return null;
		}

		@Override
		public XMLObject resolveIDFromRoot(String id) {
			return null;
		}

		@Override
		public void setCacheDuration(Long duration) {
		}

		@Override
		public void setDOM(Element element) {
		}

		@Override
		public void setExtensions(Extensions extensions) {
		}

		@Override
		public void setID(String id) {
		}

		@Override
		public void setName(String name) {
		}

		@Override
		public void setNil(Boolean value) {
		}

		@Override
		public void setNil(XSBooleanValue value) {
		}

		@Override
		public void setNoNamespaceSchemaLocation(String location) {
		}

		@Override
		public void setParent(XMLObject xmlObject) {
		}

		@Override
		public void setSchemaLocation(String location) {
		}

		@Override
		public void setSignature(Signature signature) {
		}

		@Override
		public void setValidUntil(DateTime validUntil) {
		}

		@Override
		public void validate(boolean validateDescendants) {
		}

		private final List<XMLObject> _xmlObjects = new ArrayList<>();

	}

}