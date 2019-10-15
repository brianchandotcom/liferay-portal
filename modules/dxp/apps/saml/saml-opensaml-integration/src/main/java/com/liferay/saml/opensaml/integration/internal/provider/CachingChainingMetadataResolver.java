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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;

import org.opensaml.saml.metadata.resolver.MetadataResolver;
import org.opensaml.saml.metadata.resolver.impl.AbstractMetadataResolver;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;

/**
 * @author Mika Koivisto
 */
public class CachingChainingMetadataResolver extends AbstractMetadataResolver {

	public void addMetadataResolver(MetadataResolver metadataResolver) {
		Lock lock = _readWriteLock.writeLock();

		lock.lock();

		try {
			if (metadataResolver != null) {
				metadataResolver.setRequireValidMetadata(
					isRequireValidMetadata());

				_metadataResolvers.add(metadataResolver);
			}
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public void doDestroy() {
		Lock lock = _readWriteLock.writeLock();

		lock.lock();

		try {
			_metadataResolversMap.clear();

			for (MetadataResolver metadataProvider : _metadataResolvers) {
				if (metadataProvider instanceof AbstractMetadataResolver) {
					AbstractMetadataResolver baseMetadataProvider =
						(AbstractMetadataResolver)metadataProvider;

					baseMetadataProvider.destroy();
				}
			}

			_metadataResolvers.clear();
		}
		finally {
			lock.unlock();
		}
	}

	public void removeMetadataResolver(MetadataResolver metadataResolver) {
		Lock lock = _readWriteLock.writeLock();

		lock.lock();

		try {
			_metadataResolvers.remove(metadataResolver);

			_metadataResolversMap.clear();
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public Iterable<EntityDescriptor> resolve(CriteriaSet criteria)
		throws ResolverException {

		ArrayList<EntityDescriptor> entityDescriptors = new ArrayList<>();

		Lock lock = _readWriteLock.writeLock();

		lock.lock();

		try {
			for (MetadataResolver metadataResolver : _metadataResolvers) {
				Iterable<EntityDescriptor> iterable = metadataResolver.resolve(
					criteria);

				iterable.forEach(entityDescriptors::add);
			}

			return entityDescriptors;
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
			for (MetadataResolver metadataProvider : _metadataResolvers) {
				metadataProvider.setRequireValidMetadata(requireValidMetadata);
			}
		}
		finally {
			lock.unlock();
		}
	}

	private final List<MetadataResolver> _metadataResolvers =
		new CopyOnWriteArrayList<>();
	private final Map<String, MetadataResolver> _metadataResolversMap =
		new ConcurrentHashMap<>();
	private final ReadWriteLock _readWriteLock = new ReentrantReadWriteLock(
		true);

}