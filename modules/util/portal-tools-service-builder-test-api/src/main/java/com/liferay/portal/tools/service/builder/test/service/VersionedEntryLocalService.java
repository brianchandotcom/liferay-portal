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

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.version.VersionService;
import com.liferay.portal.kernel.service.version.VersionServiceListener;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntry;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntryVersion;

import java.io.Serializable;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for VersionedEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntryLocalServiceUtil
 * @generated
 */
@OSGiBeanProperties(
	property = {
		"model.class.name=com.liferay.portal.tools.service.builder.test.model.VersionedEntry",
		"version.model.class.name=com.liferay.portal.tools.service.builder.test.model.VersionedEntryVersion"
	}
)
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface VersionedEntryLocalService
	extends BaseLocalService, PersistedModelLocalService,
			VersionService<VersionedEntry, VersionedEntryVersion> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link VersionedEntryLocalServiceUtil} to access the versioned entry local service. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.service.impl.VersionedEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	 * Adds the versioned entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param versionedEntry the versioned entry
	 * @return the versioned entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public VersionedEntry addVersionedEntry(VersionedEntry versionedEntry);

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public VersionedEntry checkout(
			VersionedEntry publishedVersionedEntry, int version)
		throws PortalException;

	/**
	 * Creates a new versioned entry. Does not add the versioned entry to the database.
	 *
	 * @return the new versioned entry
	 */
	@Override
	@Transactional(enabled = false)
	public VersionedEntry create();

	@Indexable(type = IndexableType.DELETE)
	@Override
	public VersionedEntry delete(VersionedEntry publishedVersionedEntry)
		throws PortalException;

	@Indexable(type = IndexableType.DELETE)
	@Override
	public VersionedEntry deleteDraft(VersionedEntry draftVersionedEntry)
		throws PortalException;

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	public VersionedEntryVersion deleteVersion(
			VersionedEntryVersion versionedEntryVersion)
		throws PortalException;

	/**
	 * Deletes the versioned entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param versionedEntryId the primary key of the versioned entry
	 * @return the versioned entry that was removed
	 * @throws PortalException if a versioned entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public VersionedEntry deleteVersionedEntry(long versionedEntryId)
		throws PortalException;

	/**
	 * Deletes the versioned entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param versionedEntry the versioned entry
	 * @return the versioned entry that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public VersionedEntry deleteVersionedEntry(VersionedEntry versionedEntry);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DynamicQuery dynamicQuery();

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.VersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end);

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.VersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection);

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public VersionedEntry fetchDraft(long primaryKey);

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public VersionedEntry fetchDraft(VersionedEntry versionedEntry);

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public VersionedEntryVersion fetchLatestVersion(
		VersionedEntry versionedEntry);

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public VersionedEntry fetchPublished(long primaryKey);

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public VersionedEntry fetchPublished(VersionedEntry versionedEntry);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public VersionedEntry fetchVersionedEntry(long versionedEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public VersionedEntry getDraft(long primaryKey) throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public VersionedEntry getDraft(VersionedEntry versionedEntry)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public VersionedEntryVersion getVersion(
			VersionedEntry versionedEntry, int version)
		throws PortalException;

	/**
	 * Returns a range of all the versioned entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.VersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of versioned entries
	 * @param end the upper bound of the range of versioned entries (not inclusive)
	 * @return the range of versioned entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<VersionedEntry> getVersionedEntries(int start, int end);

	/**
	 * Returns the number of versioned entries.
	 *
	 * @return the number of versioned entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getVersionedEntriesCount();

	/**
	 * Returns the versioned entry with the primary key.
	 *
	 * @param versionedEntryId the primary key of the versioned entry
	 * @return the versioned entry
	 * @throws PortalException if a versioned entry with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public VersionedEntry getVersionedEntry(long versionedEntryId)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<VersionedEntryVersion> getVersions(
		VersionedEntry versionedEntry);

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public VersionedEntry publishDraft(VersionedEntry draftVersionedEntry)
		throws PortalException;

	@Override
	public void registerListener(
		VersionServiceListener<VersionedEntry, VersionedEntryVersion>
			versionServiceListener);

	@Override
	public void unregisterListener(
		VersionServiceListener<VersionedEntry, VersionedEntryVersion>
			versionServiceListener);

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public VersionedEntry updateDraft(VersionedEntry draftVersionedEntry)
		throws PortalException;

	/**
	 * Updates the versioned entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param versionedEntry the versioned entry
	 * @return the versioned entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public VersionedEntry updateVersionedEntry(
			VersionedEntry draftVersionedEntry)
		throws PortalException;

}