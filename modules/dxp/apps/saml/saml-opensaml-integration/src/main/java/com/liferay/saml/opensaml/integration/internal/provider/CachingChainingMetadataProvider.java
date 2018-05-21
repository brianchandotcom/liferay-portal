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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
import org.opensaml.xml.schema.XSBooleanValue;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.util.IDIndex;
import org.opensaml.xml.util.LazySet;
import org.opensaml.xml.validation.Validator;

import org.w3c.dom.Element;

/**
 * @author Mika Koivisto
 */
public class CachingChainingMetadataProvider extends BaseMetadataProvider {

	public void addMetadataProvider(MetadataProvider metadataProvider) {
		Lock lock = _readWriteLock.writeLock();

		lock.lock();

		try {
			if (metadataProvider != null) {
				metadataProvider.setRequireValidMetadata(
					requireValidMetadata());

				_metadataProviders.add(metadataProvider);
			}
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public void destroy() {
		Lock lock = _readWriteLock.writeLock();

		lock.lock();

		try {
			_metadataProvidersMap.clear();

			for (MetadataProvider metadataProvider : _metadataProviders) {
				if (metadataProvider instanceof BaseMetadataProvider) {
					BaseMetadataProvider baseMetadataProvider =
						(BaseMetadataProvider)metadataProvider;

					baseMetadataProvider.destroy();
				}
			}

			_metadataProviders.clear();
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public EntitiesDescriptor getEntitiesDescriptor(String name) {
		Lock lock = _readWriteLock.readLock();

		lock.lock();

		try {
			for (MetadataProvider metadataProvider : _metadataProviders) {
				try {
					EntitiesDescriptor entitiesDescriptor =
						metadataProvider.getEntitiesDescriptor(name);

					if (entitiesDescriptor != null) {
						return entitiesDescriptor;
					}
				}
				catch (MetadataProviderException mpe) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to retrieve metadata from provider " +
								metadataProvider.getClass(),
							mpe);
					}
				}
			}

			return null;
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public EntityDescriptor getEntityDescriptor(String entityId) {
		Lock lock = _readWriteLock.readLock();

		lock.lock();

		try {
			MetadataProvider metadataProvider = _metadataProvidersMap.get(
				entityId);

			if (metadataProvider != null) {
				try {
					EntityDescriptor entityDescriptor =
						metadataProvider.getEntityDescriptor(entityId);

					if (entityDescriptor != null) {
						return entityDescriptor;
					}
				}
				catch (MetadataProviderException mpe) {
				}

				_metadataProvidersMap.remove(entityId);
			}

			for (int i = 0; i < _metadataProviders.size(); i++) {
				metadataProvider = _metadataProviders.get(i);

				try {
					EntityDescriptor entityDescriptor =
						metadataProvider.getEntityDescriptor(entityId);

					if (entityDescriptor != null) {
						_metadataProvidersMap.put(entityId, metadataProvider);

						return entityDescriptor;
					}
				}
				catch (MetadataProviderException mpe) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to retrieve metadata from provider " +
								metadataProvider.getClass(),
							mpe);
					}
				}
			}

			return null;
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public XMLObject getMetadata() {
		return new ChainingEntitiesDescriptor();
	}

	public List<MetadataProvider> getProviders() {
		return Collections.unmodifiableList(_metadataProviders);
	}

	@Override
	public List<RoleDescriptor> getRole(String entityId, QName qName) {
		Lock lock = _readWriteLock.readLock();

		lock.lock();

		try {
			MetadataProvider metadataProvider = _metadataProvidersMap.get(
				entityId);

			if (metadataProvider != null) {
				try {
					List<RoleDescriptor> roleDescriptors =
						metadataProvider.getRole(entityId, qName);

					if (roleDescriptors != null) {
						return roleDescriptors;
					}
				}
				catch (MetadataProviderException mpe) {
				}

				_metadataProvidersMap.remove(entityId);
			}

			for (int i = 0; i < _metadataProviders.size(); i++) {
				metadataProvider = _metadataProviders.get(i);

				try {
					List<RoleDescriptor> roleDescriptors =
						metadataProvider.getRole(entityId, qName);

					if ((roleDescriptors != null) &&
						!roleDescriptors.isEmpty()) {

						_metadataProvidersMap.put(entityId, metadataProvider);

						return roleDescriptors;
					}
				}
				catch (MetadataProviderException mpe) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to retrieve metadata from provider " +
								metadataProvider.getClass(),
							mpe);
					}
				}
			}

			return null;
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public RoleDescriptor getRole(
		String entityId, QName qName, String supportedProtocol) {

		Lock lock = _readWriteLock.readLock();

		lock.lock();

		try {
			MetadataProvider metadataProvider = _metadataProvidersMap.get(
				entityId);

			if (metadataProvider != null) {
				try {
					RoleDescriptor roleDescriptor = metadataProvider.getRole(
						entityId, qName, supportedProtocol);

					if (roleDescriptor != null) {
						return roleDescriptor;
					}
				}
				catch (MetadataProviderException mpe) {
				}

				_metadataProvidersMap.remove(entityId);
			}

			for (int i = 0; i < _metadataProviders.size(); i++) {
				metadataProvider = _metadataProviders.get(i);

				try {
					RoleDescriptor roleDescriptor = metadataProvider.getRole(
						entityId, qName, supportedProtocol);

					if (roleDescriptor != null) {
						_metadataProvidersMap.put(entityId, metadataProvider);

						return roleDescriptor;
					}
				}
				catch (MetadataProviderException mpe) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to retrieve metadata from provider " +
								metadataProvider.getClass(),
							mpe);
					}
				}
			}

			return null;
		}
		finally {
			lock.unlock();
		}
	}

	public void removeMetadataProvider(MetadataProvider metadataProvider) {
		Lock lock = _readWriteLock.writeLock();

		lock.lock();

		try {
			_metadataProvidersMap.clear();

			_metadataProviders.remove(metadataProvider);
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public void setRequireValidMetadata(boolean requireValidMetadata) {
		super.setRequireValidMetadata(requireValidMetadata);

		Lock lock = _readWriteLock.writeLock();

		lock.lock();

		try {
			for (MetadataProvider metadataProvider : _metadataProviders) {
				metadataProvider.setRequireValidMetadata(requireValidMetadata);
			}
		}
		finally {
			lock.unlock();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CachingChainingMetadataProvider.class);

	private final List<MetadataProvider> _metadataProviders =
		new CopyOnWriteArrayList<>();
	private final Map<String, MetadataProvider> _metadataProvidersMap =
		new ConcurrentHashMap<>();
	private final ReadWriteLock _readWriteLock = new ReentrantReadWriteLock(
		true);

	private class ChainingEntitiesDescriptor implements EntitiesDescriptor {

		public ChainingEntitiesDescriptor() {
			_xmlObjects = new ArrayList<>();

			Lock lock = _readWriteLock.readLock();

			lock.lock();

			try {
				for (MetadataProvider metadataProvider : _metadataProviders) {
					_xmlObjects.add(metadataProvider.getMetadata());
				}
			}
			catch (MetadataProviderException mpe) {
				_log.error(
					"Unable to get metadata from metadata provider", mpe);
			}
			finally {
				lock.unlock();
			}
		}

		@Override
		public void addNamespace(Namespace namespace) {
		}

		@Override
		@SuppressWarnings("rawtypes")
		public void deregisterValidator(Validator validator) {
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
			List<XMLObject> xmlObjects = new ArrayList<>();

			try {
				for (MetadataProvider metadataProvider : _metadataProviders) {
					XMLObject xmlObject = metadataProvider.getMetadata();

					xmlObjects.add(xmlObject);
				}
			}
			catch (MetadataProviderException mpe) {
				_log.error(
					"Unable to build a list of metadata descriptors", mpe);
			}

			return xmlObjects;
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
		public List<Validator> getValidators() {
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
		public void registerValidator(Validator validator) {
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

		private final List<XMLObject> _xmlObjects;

	}

}