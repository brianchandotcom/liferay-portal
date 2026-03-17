/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat700.service;

import aQute.bnd.annotation.ProviderType;

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
import com.liferay.portal.tools.service.builder.test.compat700.model.BasicEntry;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for BasicEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see BasicEntryLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface BasicEntryLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.compat700.service.impl.BasicEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the basic entry local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link BasicEntryLocalServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * Adds the basic entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BasicEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param basicEntry the basic entry
	 * @return the basic entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public BasicEntry addBasicEntry(BasicEntry basicEntry);

	public void addMappingEntryBasicEntries(
		long mappingEntryId, List<BasicEntry> basicEntries);

	public void addMappingEntryBasicEntries(
		long mappingEntryId, long[] basicEntryIds);

	public void addMappingEntryBasicEntry(
		long mappingEntryId, BasicEntry basicEntry);

	public void addMappingEntryBasicEntry(
		long mappingEntryId, long basicEntryId);

	public void clearMappingEntryBasicEntries(long mappingEntryId);

	/**
	 * Creates a new basic entry with the primary key. Does not add the basic entry to the database.
	 *
	 * @param basicEntryId the primary key for the new basic entry
	 * @return the new basic entry
	 */
	@Transactional(enabled = false)
	public BasicEntry createBasicEntry(long basicEntryId);

	/**
	 * Deletes the basic entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BasicEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param basicEntry the basic entry
	 * @return the basic entry that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public BasicEntry deleteBasicEntry(BasicEntry basicEntry);

	/**
	 * Deletes the basic entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BasicEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param basicEntryId the primary key of the basic entry
	 * @return the basic entry that was removed
	 * @throws PortalException if a basic entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public BasicEntry deleteBasicEntry(long basicEntryId)
		throws PortalException;

	public void deleteMappingEntryBasicEntries(
		long mappingEntryId, List<BasicEntry> basicEntries);

	public void deleteMappingEntryBasicEntries(
		long mappingEntryId, long[] basicEntryIds);

	public void deleteMappingEntryBasicEntry(
		long mappingEntryId, BasicEntry basicEntry);

	public void deleteMappingEntryBasicEntry(
		long mappingEntryId, long basicEntryId);

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat700.model.impl.BasicEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat700.model.impl.BasicEntryModelImpl</code>.
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
	public BasicEntry fetchBasicEntry(long basicEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	 * Returns a range of all the basic entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat700.model.impl.BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @return the range of basic entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BasicEntry> getBasicEntries(int start, int end);

	/**
	 * Returns the number of basic entries.
	 *
	 * @return the number of basic entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getBasicEntriesCount();

	/**
	 * Returns the basic entry with the primary key.
	 *
	 * @param basicEntryId the primary key of the basic entry
	 * @return the basic entry
	 * @throws PortalException if a basic entry with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BasicEntry getBasicEntry(long basicEntryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BasicEntry> getMappingEntryBasicEntries(long mappingEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BasicEntry> getMappingEntryBasicEntries(
		long mappingEntryId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BasicEntry> getMappingEntryBasicEntries(
		long mappingEntryId, int start, int end,
		OrderByComparator<BasicEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getMappingEntryBasicEntriesCount(long mappingEntryId);

	/**
	 * Returns the mappingEntryIds of the mapping entries associated with the basic entry.
	 *
	 * @param basicEntryId the basicEntryId of the basic entry
	 * @return long[] the mappingEntryIds of mapping entries associated with the basic entry
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getMappingEntryPrimaryKeys(long basicEntryId);

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
	public boolean hasMappingEntryBasicEntries(long mappingEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasMappingEntryBasicEntry(
		long mappingEntryId, long basicEntryId);

	public void setMappingEntryBasicEntries(
		long mappingEntryId, long[] basicEntryIds);

	/**
	 * Updates the basic entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BasicEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param basicEntry the basic entry
	 * @return the basic entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public BasicEntry updateBasicEntry(BasicEntry basicEntry);

}
// SB-Hash:-1424733151