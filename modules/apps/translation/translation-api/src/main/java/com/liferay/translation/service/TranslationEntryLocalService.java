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

package com.liferay.translation.service;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.petra.sql.dsl.query.DSLQuery;
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
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.translation.model.TranslationEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for TranslationEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see TranslationEntryLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface TranslationEntryLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.translation.service.impl.TranslationEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the translation entry local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link TranslationEntryLocalServiceUtil} if injection and service tracking are not available.
	 */
	@Indexable(type = IndexableType.REINDEX)
	public TranslationEntry addOrUpdateTranslationEntry(
			long groupId, String languageId,
			InfoItemReference infoItemReference,
			InfoItemFieldValues infoItemFieldValues,
			ServiceContext serviceContext)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public TranslationEntry addOrUpdateTranslationEntry(
			long groupId, String className, long classPK, String content,
			String contentType, String languageId,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * Adds the translation entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TranslationEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param translationEntry the translation entry
	 * @return the translation entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public TranslationEntry addTranslationEntry(
		TranslationEntry translationEntry);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Creates a new translation entry with the primary key. Does not add the translation entry to the database.
	 *
	 * @param translationEntryId the primary key for the new translation entry
	 * @return the new translation entry
	 */
	@Transactional(enabled = false)
	public TranslationEntry createTranslationEntry(long translationEntryId);

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	public void deleteTranslationEntries(long classNameId, long classPK);

	public void deleteTranslationEntries(String className, long classPK);

	/**
	 * Deletes the translation entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TranslationEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param translationEntryId the primary key of the translation entry
	 * @return the translation entry that was removed
	 * @throws PortalException if a translation entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public TranslationEntry deleteTranslationEntry(long translationEntryId)
		throws PortalException;

	/**
	 * Deletes the translation entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TranslationEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param translationEntry the translation entry
	 * @return the translation entry that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public TranslationEntry deleteTranslationEntry(
		TranslationEntry translationEntry);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> T dslQuery(DSLQuery dslQuery);

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.translation.model.impl.TranslationEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.translation.model.impl.TranslationEntryModelImpl</code>.
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
	public TranslationEntry fetchTranslationEntry(long translationEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public TranslationEntry fetchTranslationEntry(
		String className, long classPK, String languageId);

	/**
	 * Returns the translation entry matching the UUID and group.
	 *
	 * @param uuid the translation entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public TranslationEntry fetchTranslationEntryByUuidAndGroupId(
		String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public InfoItemFieldValues getInfoItemFieldValues(
			long groupId, String className, long classPK, String content)
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

	/**
	 * Returns a range of all the translation entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.translation.model.impl.TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @return the range of translation entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<TranslationEntry> getTranslationEntries(int start, int end);

	/**
	 * Returns all the translation entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the translation entries
	 * @param companyId the primary key of the company
	 * @return the matching translation entries, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<TranslationEntry> getTranslationEntriesByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	 * Returns a range of translation entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the translation entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching translation entries, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<TranslationEntry> getTranslationEntriesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<TranslationEntry> orderByComparator);

	/**
	 * Returns the number of translation entries.
	 *
	 * @return the number of translation entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getTranslationEntriesCount();

	/**
	 * Returns the translation entry with the primary key.
	 *
	 * @param translationEntryId the primary key of the translation entry
	 * @return the translation entry
	 * @throws PortalException if a translation entry with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public TranslationEntry getTranslationEntry(long translationEntryId)
		throws PortalException;

	/**
	 * Returns the translation entry matching the UUID and group.
	 *
	 * @param uuid the translation entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching translation entry
	 * @throws PortalException if a matching translation entry could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public TranslationEntry getTranslationEntryByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public TranslationEntry updateStatus(
			long userId, long translationEntryId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException;

	/**
	 * Updates the translation entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TranslationEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param translationEntry the translation entry
	 * @return the translation entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public TranslationEntry updateTranslationEntry(
		TranslationEntry translationEntry);

}