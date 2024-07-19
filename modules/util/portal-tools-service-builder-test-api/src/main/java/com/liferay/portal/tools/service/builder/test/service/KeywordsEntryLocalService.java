/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service;

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
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.KeywordsEntry;

import java.io.Serializable;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for KeywordsEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see KeywordsEntryLocalServiceUtil
 * @generated
 */
@OSGiBeanProperties(
	property = {
		"model.class.name=com.liferay.portal.tools.service.builder.test.model.KeywordsEntry"
	}
)
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface KeywordsEntryLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.service.impl.KeywordsEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the keywords entry local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link KeywordsEntryLocalServiceUtil} if injection and service tracking are not available.
	 */
	public boolean addIndexEntryKeywordsEntries(
		long indexEntryId, List<KeywordsEntry> keywordsEntries);

	public boolean addIndexEntryKeywordsEntries(
		long indexEntryId, long[] keywordsEntryIds);

	public boolean addIndexEntryKeywordsEntry(
		long indexEntryId, KeywordsEntry keywordsEntry);

	public boolean addIndexEntryKeywordsEntry(
		long indexEntryId, long keywordsEntryId);

	/**
	 * Adds the keywords entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KeywordsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param keywordsEntry the keywords entry
	 * @return the keywords entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public KeywordsEntry addKeywordsEntry(KeywordsEntry keywordsEntry);

	public void clearIndexEntryKeywordsEntries(long indexEntryId);

	/**
	 * Creates a new keywords entry with the primary key. Does not add the keywords entry to the database.
	 *
	 * @param keywordsEntryId the primary key for the new keywords entry
	 * @return the new keywords entry
	 */
	@Transactional(enabled = false)
	public KeywordsEntry createKeywordsEntry(long keywordsEntryId);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	public void deleteIndexEntryKeywordsEntries(
		long indexEntryId, List<KeywordsEntry> keywordsEntries);

	public void deleteIndexEntryKeywordsEntries(
		long indexEntryId, long[] keywordsEntryIds);

	public void deleteIndexEntryKeywordsEntry(
		long indexEntryId, KeywordsEntry keywordsEntry);

	public void deleteIndexEntryKeywordsEntry(
		long indexEntryId, long keywordsEntryId);

	/**
	 * Deletes the keywords entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KeywordsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param keywordsEntry the keywords entry
	 * @return the keywords entry that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public KeywordsEntry deleteKeywordsEntry(KeywordsEntry keywordsEntry);

	/**
	 * Deletes the keywords entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KeywordsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param keywordsEntryId the primary key of the keywords entry
	 * @return the keywords entry that was removed
	 * @throws PortalException if a keywords entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public KeywordsEntry deleteKeywordsEntry(long keywordsEntryId)
		throws PortalException;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.KeywordsEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.KeywordsEntryModelImpl</code>.
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
	public KeywordsEntry fetchKeywordsEntry(long keywordsEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<KeywordsEntry> getIndexEntryKeywordsEntries(long indexEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<KeywordsEntry> getIndexEntryKeywordsEntries(
		long indexEntryId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<KeywordsEntry> getIndexEntryKeywordsEntries(
		long indexEntryId, int start, int end,
		OrderByComparator<KeywordsEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getIndexEntryKeywordsEntriesCount(long indexEntryId);

	/**
	 * Returns the indexEntryIds of the index entries associated with the keywords entry.
	 *
	 * @param keywordsEntryId the keywordsEntryId of the keywords entry
	 * @return long[] the indexEntryIds of index entries associated with the keywords entry
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getIndexEntryPrimaryKeys(long keywordsEntryId);

	/**
	 * Returns a range of all the keywords entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.KeywordsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of keywords entries
	 * @param end the upper bound of the range of keywords entries (not inclusive)
	 * @return the range of keywords entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<KeywordsEntry> getKeywordsEntries(int start, int end);

	/**
	 * Returns the number of keywords entries.
	 *
	 * @return the number of keywords entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getKeywordsEntriesCount();

	/**
	 * Returns the keywords entry with the primary key.
	 *
	 * @param keywordsEntryId the primary key of the keywords entry
	 * @return the keywords entry
	 * @throws PortalException if a keywords entry with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public KeywordsEntry getKeywordsEntry(long keywordsEntryId)
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
	public boolean hasIndexEntryKeywordsEntries(long indexEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasIndexEntryKeywordsEntry(
		long indexEntryId, long keywordsEntryId);

	public void setIndexEntryKeywordsEntries(
		long indexEntryId, long[] keywordsEntryIds);

	/**
	 * Updates the keywords entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KeywordsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param keywordsEntry the keywords entry
	 * @return the keywords entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public KeywordsEntry updateKeywordsEntry(KeywordsEntry keywordsEntry);

}