/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.service;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.saved.content.exception.NoSuchSavedContentEntryException;
import com.liferay.saved.content.model.SavedContentEntry;

import java.io.Serializable;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for SavedContentEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see SavedContentEntryLocalServiceUtil
 * @generated
 */
@CTAware
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface SavedContentEntryLocalService
	extends BaseLocalService, CTService<SavedContentEntry>,
			PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.saved.content.service.impl.SavedContentEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the saved content entry local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link SavedContentEntryLocalServiceUtil} if injection and service tracking are not available.
	 */
	@Indexable(type = IndexableType.REINDEX)
	public SavedContentEntry addSavedContentEntry(
			long userId, long groupId, String className, long classPK,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * Adds the saved content entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SavedContentEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param savedContentEntry the saved content entry
	 * @return the saved content entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public SavedContentEntry addSavedContentEntry(
		SavedContentEntry savedContentEntry);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Creates a new saved content entry with the primary key. Does not add the saved content entry to the database.
	 *
	 * @param savedContentEntryId the primary key for the new saved content entry
	 * @return the new saved content entry
	 */
	@Transactional(enabled = false)
	public SavedContentEntry createSavedContentEntry(long savedContentEntryId);

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	public void deleteSavedContentEntries(
		long groupId, long classNameId, long classPK);

	public void deleteSavedContentEntriesByUserId(long userId);

	/**
	 * Deletes the saved content entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SavedContentEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param savedContentEntryId the primary key of the saved content entry
	 * @return the saved content entry that was removed
	 * @throws PortalException if a saved content entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public SavedContentEntry deleteSavedContentEntry(long savedContentEntryId)
		throws PortalException;

	/**
	 * Deletes the saved content entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SavedContentEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param savedContentEntry the saved content entry
	 * @return the saved content entry that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public SavedContentEntry deleteSavedContentEntry(
		SavedContentEntry savedContentEntry);

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.saved.content.model.impl.SavedContentEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.saved.content.model.impl.SavedContentEntryModelImpl</code>.
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
	public SavedContentEntry fetchSavedContentEntry(long savedContentEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SavedContentEntry fetchSavedContentEntry(
		long userId, long groupId, String className, long classPK);

	/**
	 * Returns the saved content entry matching the UUID and group.
	 *
	 * @param uuid the saved content entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SavedContentEntry fetchSavedContentEntryByUuidAndGroupId(
		String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

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

	/**
	 * Returns a range of all the saved content entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.saved.content.model.impl.SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @return the range of saved content entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SavedContentEntry> getSavedContentEntries(int start, int end);

	/**
	 * Returns all the saved content entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the saved content entries
	 * @param companyId the primary key of the company
	 * @return the matching saved content entries, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SavedContentEntry> getSavedContentEntriesByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	 * Returns a range of saved content entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the saved content entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching saved content entries, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SavedContentEntry> getSavedContentEntriesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SavedContentEntry> orderByComparator);

	/**
	 * Returns the number of saved content entries.
	 *
	 * @return the number of saved content entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSavedContentEntriesCount();

	/**
	 * Returns the saved content entry with the primary key.
	 *
	 * @param savedContentEntryId the primary key of the saved content entry
	 * @return the saved content entry
	 * @throws PortalException if a saved content entry with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SavedContentEntry getSavedContentEntry(long savedContentEntryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SavedContentEntry getSavedContentEntry(
			long userId, long groupId, String className, long classPK)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the saved content entry matching the UUID and group.
	 *
	 * @param uuid the saved content entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching saved content entry
	 * @throws PortalException if a matching saved content entry could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SavedContentEntry getSavedContentEntryByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException;

	/**
	 * Updates the saved content entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SavedContentEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param savedContentEntry the saved content entry
	 * @return the saved content entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public SavedContentEntry updateSavedContentEntry(
		SavedContentEntry savedContentEntry);

	@Override
	@Transactional(enabled = false)
	public CTPersistence<SavedContentEntry> getCTPersistence();

	@Override
	@Transactional(enabled = false)
	public Class<SavedContentEntry> getModelClass();

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<SavedContentEntry>, R, E>
				updateUnsafeFunction)
		throws E;

}