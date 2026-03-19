/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
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
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.compat740.model.MappingEntry;

import java.io.Serializable;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for MappingEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see MappingEntryLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface MappingEntryLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.compat740.service.impl.MappingEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the mapping entry local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link MappingEntryLocalServiceUtil} if injection and service tracking are not available.
	 */
	public boolean addBasicEntryMappingEntries(
		long basicEntryId, List<MappingEntry> mappingEntries);

	public boolean addBasicEntryMappingEntries(
		long basicEntryId, long[] mappingEntryIds);

	public boolean addBasicEntryMappingEntry(
		long basicEntryId, long mappingEntryId);

	public boolean addBasicEntryMappingEntry(
		long basicEntryId, MappingEntry mappingEntry);

	/**
	 * Adds the mapping entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MappingEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mappingEntry the mapping entry
	 * @return the mapping entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public MappingEntry addMappingEntry(MappingEntry mappingEntry);

	public void clearBasicEntryMappingEntries(long basicEntryId);

	/**
	 * Creates a new mapping entry with the primary key. Does not add the mapping entry to the database.
	 *
	 * @param mappingEntryId the primary key for the new mapping entry
	 * @return the new mapping entry
	 */
	@Transactional(enabled = false)
	public MappingEntry createMappingEntry(long mappingEntryId);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	public void deleteBasicEntryMappingEntries(
		long basicEntryId, List<MappingEntry> mappingEntries);

	public void deleteBasicEntryMappingEntries(
		long basicEntryId, long[] mappingEntryIds);

	public void deleteBasicEntryMappingEntry(
		long basicEntryId, long mappingEntryId);

	public void deleteBasicEntryMappingEntry(
		long basicEntryId, MappingEntry mappingEntry);

	/**
	 * Deletes the mapping entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MappingEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mappingEntryId the primary key of the mapping entry
	 * @return the mapping entry that was removed
	 * @throws PortalException if a mapping entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public MappingEntry deleteMappingEntry(long mappingEntryId)
		throws PortalException;

	/**
	 * Deletes the mapping entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MappingEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mappingEntry the mapping entry
	 * @return the mapping entry that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public MappingEntry deleteMappingEntry(MappingEntry mappingEntry);

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> T dslQuery(DSLQuery dslQuery);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int dslQueryCount(DSLQuery dslQuery);

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.MappingEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.MappingEntryModelImpl</code>.
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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MappingEntry fetchMappingEntry(long mappingEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MappingEntry> getBasicEntryMappingEntries(long basicEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MappingEntry> getBasicEntryMappingEntries(
		long basicEntryId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MappingEntry> getBasicEntryMappingEntries(
		long basicEntryId, int start, int end,
		OrderByComparator<MappingEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getBasicEntryMappingEntriesCount(long basicEntryId);

	/**
	 * Returns the basicEntryIds of the basic entries associated with the mapping entry.
	 *
	 * @param mappingEntryId the mappingEntryId of the mapping entry
	 * @return long[] the basicEntryIds of basic entries associated with the mapping entry
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getBasicEntryPrimaryKeys(long mappingEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns a range of all the mapping entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.MappingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mapping entries
	 * @param end the upper bound of the range of mapping entries (not inclusive)
	 * @return the range of mapping entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MappingEntry> getMappingEntries(int start, int end);

	/**
	 * Returns the number of mapping entries.
	 *
	 * @return the number of mapping entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getMappingEntriesCount();

	/**
	 * Returns the mapping entry with the primary key.
	 *
	 * @param mappingEntryId the primary key of the mapping entry
	 * @return the mapping entry
	 * @throws PortalException if a mapping entry with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MappingEntry getMappingEntry(long mappingEntryId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	/**
	 * @throws PortalException
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasBasicEntryMappingEntries(long basicEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasBasicEntryMappingEntry(
		long basicEntryId, long mappingEntryId);

	public void setBasicEntryMappingEntries(
		long basicEntryId, long[] mappingEntryIds);

	/**
	 * Updates the mapping entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MappingEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mappingEntry the mapping entry
	 * @return the mapping entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public MappingEntry updateMappingEntry(MappingEntry mappingEntry);

}
// LIFERAY-SERVICE-BUILDER-HASH:2130601044