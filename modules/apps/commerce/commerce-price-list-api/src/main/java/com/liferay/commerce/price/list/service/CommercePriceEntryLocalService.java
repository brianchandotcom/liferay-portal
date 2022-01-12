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

package com.liferay.commerce.price.list.service;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
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
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.List;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for CommercePriceEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceEntryLocalServiceUtil
 * @generated
 */
@CTAware
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommercePriceEntryLocalService
	extends BaseLocalService, CTService<CommercePriceEntry>,
			PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.price.list.service.impl.CommercePriceEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce price entry local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommercePriceEntryLocalServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * Adds the commerce price entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePriceEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePriceEntry the commerce price entry
	 * @return the commerce price entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public CommercePriceEntry addCommercePriceEntry(
		CommercePriceEntry commercePriceEntry);

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public CommercePriceEntry addCommercePriceEntry(
			long cpInstanceId, long commercePriceListId, BigDecimal price,
			BigDecimal promoPrice, ServiceContext serviceContext)
		throws PortalException;

	public CommercePriceEntry addCommercePriceEntry(
			long cProductId, String cpInstanceUuid, long commercePriceListId,
			BigDecimal price, BigDecimal promoPrice,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addCommercePriceEntry(String, long, String, long,
	 BigDecimal, BigDecimal, boolean, BigDecimal, BigDecimal,
	 BigDecimal, BigDecimal, int, int, int, int, int, int, int,
	 int, int, int, boolean, ServiceContext)}
	 */
	@Deprecated
	@Indexable(type = IndexableType.REINDEX)
	public CommercePriceEntry addCommercePriceEntry(
			long cProductId, String cpInstanceUuid, long commercePriceListId,
			String externalReferenceCode, BigDecimal price,
			BigDecimal promoPrice, boolean discountDiscovery,
			BigDecimal discountLevel1, BigDecimal discountLevel2,
			BigDecimal discountLevel3, BigDecimal discountLevel4,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addCommercePriceEntry(String, long, String, long,
	 BigDecimal, BigDecimal, ServiceContext)}
	 */
	@Deprecated
	@Indexable(type = IndexableType.REINDEX)
	public CommercePriceEntry addCommercePriceEntry(
			long cProductId, String cpInstanceUuid, long commercePriceListId,
			String externalReferenceCode, BigDecimal price,
			BigDecimal promoPrice, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addCommercePriceEntry(String, long, String, long,
	 BigDecimal, boolean, BigDecimal, BigDecimal, BigDecimal,
	 BigDecimal, int, int, int, int, int, int, int, int, int,
	 int, boolean, ServiceContext)}
	 */
	@Deprecated
	public CommercePriceEntry addCommercePriceEntry(
			long cProductId, String cpInstanceUuid, long commercePriceListId,
			String externalReferenceCode, BigDecimal price,
			boolean discountDiscovery, BigDecimal discountLevel1,
			BigDecimal discountLevel2, BigDecimal discountLevel3,
			BigDecimal discountLevel4, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public CommercePriceEntry addCommercePriceEntry(
			String externalReferenceCode, long cpInstanceId,
			long commercePriceListId, BigDecimal price, BigDecimal promoPrice,
			ServiceContext serviceContext)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommercePriceEntry addCommercePriceEntry(
			String externalReferenceCode, long cProductId,
			String cpInstanceUuid, long commercePriceListId, BigDecimal price,
			BigDecimal promoPrice, boolean discountDiscovery,
			BigDecimal discountLevel1, BigDecimal discountLevel2,
			BigDecimal discountLevel3, BigDecimal discountLevel4,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommercePriceEntry addCommercePriceEntry(
			String externalReferenceCode, long cProductId,
			String cpInstanceUuid, long commercePriceListId, BigDecimal price,
			BigDecimal promoPrice, ServiceContext serviceContext)
		throws PortalException;

	public CommercePriceEntry addCommercePriceEntry(
			String externalReferenceCode, long cProductId,
			String cpInstanceUuid, long commercePriceListId, BigDecimal price,
			boolean discountDiscovery, BigDecimal discountLevel1,
			BigDecimal discountLevel2, BigDecimal discountLevel3,
			BigDecimal discountLevel4, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException;

	public CommercePriceEntry addOrUpdateCommercePriceEntry(
			String externalReferenceCode, long commercePriceEntryId,
			long cProductId, String cpInstanceUuid, long commercePriceListId,
			BigDecimal price, BigDecimal promoPrice, boolean discountDiscovery,
			BigDecimal discountLevel1, BigDecimal discountLevel2,
			BigDecimal discountLevel3, BigDecimal discountLevel4,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String skuExternalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * This method is used to insert a new CommercePriceEntry or update an
	 * existing one
	 *
	 * @param externalReferenceCode - The external identifier code from a 3rd
	 party system to be able to locate the same entity in the portal
	 <b>Only</b> used when updating an entity; the first entity with a
	 matching reference code one will be updated
	 * @param commercePriceEntryId - <b>Only</b> used when updating an entity
	 the matching one will be updated
	 * @param cProductId - <b>Only</b> used when adding a new entity
	 * @param commercePriceListId - <b>Only</b> used when adding a new entity
	 to a price list
	 * @param price
	 * @param promoPrice
	 * @param skuExternalReferenceCode - <b>Only</b> used when adding a new
	 entity, similar as <code>cpInstanceId</code> but the external
	 identifier code from a 3rd party system. If cpInstanceId is used,
	 it doesn't have any effect, otherwise it tries to fetch the
	 CPInstance against the external code reference
	 * @param serviceContext
	 * @return CommercePriceEntry
	 * @throws PortalException
	 * @review
	 */
	@Indexable(type = IndexableType.REINDEX)
	public CommercePriceEntry addOrUpdateCommercePriceEntry(
			String externalReferenceCode, long commercePriceEntryId,
			long cProductId, String cpInstanceUuid, long commercePriceListId,
			BigDecimal price, BigDecimal promoPrice,
			String skuExternalReferenceCode, ServiceContext serviceContext)
		throws PortalException;

	public CommercePriceEntry addOrUpdateCommercePriceEntry(
			String externalReferenceCode, long commercePriceEntryId,
			long cProductId, String cpInstanceUuid, long commercePriceListId,
			BigDecimal price, boolean discountDiscovery,
			BigDecimal discountLevel1, BigDecimal discountLevel2,
			BigDecimal discountLevel3, BigDecimal discountLevel4,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String skuExternalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException;

	public void checkCommercePriceEntries() throws PortalException;

	/**
	 * Creates a new commerce price entry with the primary key. Does not add the commerce price entry to the database.
	 *
	 * @param commercePriceEntryId the primary key for the new commerce price entry
	 * @return the new commerce price entry
	 */
	@Transactional(enabled = false)
	public CommercePriceEntry createCommercePriceEntry(
		long commercePriceEntryId);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	public void deleteCommercePriceEntries(long commercePriceListId)
		throws PortalException;

	public void deleteCommercePriceEntries(String cpInstanceUuid)
		throws PortalException;

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public void deleteCommercePriceEntriesByCPInstanceId(long cpInstanceId)
		throws PortalException;

	/**
	 * Deletes the commerce price entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePriceEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePriceEntry the commerce price entry
	 * @return the commerce price entry that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommercePriceEntry deleteCommercePriceEntry(
			CommercePriceEntry commercePriceEntry)
		throws PortalException;

	/**
	 * Deletes the commerce price entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePriceEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePriceEntryId the primary key of the commerce price entry
	 * @return the commerce price entry that was removed
	 * @throws PortalException if a commerce price entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public CommercePriceEntry deleteCommercePriceEntry(
			long commercePriceEntryId)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.price.list.model.impl.CommercePriceEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.price.list.model.impl.CommercePriceEntryModelImpl</code>.
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

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #fetchByExternalReferenceCode(String, long)}
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry fetchByExternalReferenceCode(
		long companyId, String externalReferenceCode);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry fetchByExternalReferenceCode(
		String externalReferenceCode, long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry fetchCommercePriceEntry(
		long commercePriceEntryId);

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry fetchCommercePriceEntry(
		long cpInstanceId, long commercePriceListId);

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry fetchCommercePriceEntry(
		long cpInstanceId, long commercePriceListId, boolean useAncestor);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry fetchCommercePriceEntry(
		long commercePriceListId, String cpInstanceUuid);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry fetchCommercePriceEntry(
		long commercePriceListId, String cpInstanceUuid, boolean useAncestor);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry fetchCommercePriceEntry(
		long commercePriceListId, String cpInstanceUuid, int status);

	/**
	 * Returns the commerce price entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce price entry's external reference code
	 * @return the matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry fetchCommercePriceEntryByExternalReferenceCode(
		long companyId, String externalReferenceCode);

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchCommercePriceEntryByExternalReferenceCode(long, String)}
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry fetchCommercePriceEntryByReferenceCode(
		long companyId, String externalReferenceCode);

	/**
	 * Returns the commerce price entry with the matching UUID and company.
	 *
	 * @param uuid the commerce price entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry fetchCommercePriceEntryByUuidAndCompanyId(
		String uuid, long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	 * Returns a range of all the commerce price entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.price.list.model.impl.CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of commerce price entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getCommercePriceEntries(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getCommercePriceEntries(
		long commercePriceListId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getCommercePriceEntries(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getCommercePriceEntriesByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns the number of commerce price entries.
	 *
	 * @return the number of commerce price entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePriceEntriesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePriceEntriesCount(long commercePriceListId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePriceEntriesCountByCompanyId(long companyId);

	/**
	 * Returns the commerce price entry with the primary key.
	 *
	 * @param commercePriceEntryId the primary key of the commerce price entry
	 * @return the commerce price entry
	 * @throws PortalException if a commerce price entry with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry getCommercePriceEntry(long commercePriceEntryId)
		throws PortalException;

	/**
	 * Returns the commerce price entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce price entry's external reference code
	 * @return the matching commerce price entry
	 * @throws PortalException if a matching commerce price entry could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry getCommercePriceEntryByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException;

	/**
	 * Returns the commerce price entry with the matching UUID and company.
	 *
	 * @param uuid the commerce price entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching commerce price entry
	 * @throws PortalException if a matching commerce price entry could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry getCommercePriceEntryByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry getInstanceBaseCommercePriceEntry(
		String cpInstanceUuid, String priceListType);

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getInstanceCommercePriceEntries(
		long cpInstanceId, int start, int end);

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getInstanceCommercePriceEntries(
		long cpInstanceId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getInstanceCommercePriceEntries(
		String cpInstanceUuid, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getInstanceCommercePriceEntries(
		String cpInstanceUuid, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator);

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getInstanceCommercePriceEntriesCount(long cpInstanceId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getInstanceCommercePriceEntriesCount(String cpInstanceUuid);

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
	public Hits search(SearchContext searchContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CommercePriceEntry> searchCommercePriceEntries(
			long companyId, long commercePriceListId, String keywords,
			int start, int end, Sort sort)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCommercePriceEntriesCount(
			long companyId, long commercePriceListId, String keywords)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommercePriceEntry setHasTierPrice(
			long commercePriceEntryId, boolean hasTierPrice)
		throws PortalException;

	public CommercePriceEntry setHasTierPrice(
			long commercePriceEntryId, boolean hasTierPrice,
			boolean bulkPricing)
		throws PortalException;

	/**
	 * Updates the commerce price entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePriceEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePriceEntry the commerce price entry
	 * @return the commerce price entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public CommercePriceEntry updateCommercePriceEntry(
		CommercePriceEntry commercePriceEntry);

	@Indexable(type = IndexableType.REINDEX)
	public CommercePriceEntry updateCommercePriceEntry(
			long commercePriceEntryId, BigDecimal price, BigDecimal promoPrice,
			boolean discountDiscovery, BigDecimal discountLevel1,
			BigDecimal discountLevel2, BigDecimal discountLevel3,
			BigDecimal discountLevel4, boolean bulkPricing,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException;

	public CommercePriceEntry updateCommercePriceEntry(
			long commercePriceEntryId, BigDecimal price, BigDecimal promoPrice,
			boolean discountDiscovery, BigDecimal discountLevel1,
			BigDecimal discountLevel2, BigDecimal discountLevel3,
			BigDecimal discountLevel4, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommercePriceEntry updateCommercePriceEntry(
			long commercePriceEntryId, BigDecimal price, BigDecimal promoPrice,
			ServiceContext serviceContext)
		throws PortalException;

	public CommercePriceEntry updateCommercePriceEntry(
			long commercePriceEntryId, BigDecimal price,
			boolean discountDiscovery, BigDecimal discountLevel1,
			BigDecimal discountLevel2, BigDecimal discountLevel3,
			BigDecimal discountLevel4, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #updateExternalReferenceCode(String, CommercePriceEntry)}
	 */
	@Deprecated
	@Indexable(type = IndexableType.REINDEX)
	public CommercePriceEntry updateExternalReferenceCode(
			CommercePriceEntry commercePriceEntry, String externalReferenceCode)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommercePriceEntry updateExternalReferenceCode(
			String externalReferenceCode, CommercePriceEntry commercePriceEntry)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommercePriceEntry updateStatus(
			long userId, long commercePriceEntryId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException;

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public CommercePriceEntry upsertCommercePriceEntry(
			long commercePriceEntryId, long cpInstanceId,
			long commercePriceListId, String externalReferenceCode,
			BigDecimal price, BigDecimal promoPrice,
			String skuExternalReferenceCode, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addOrUpdateCommercePriceEntry(String, long, long, String, long,
	 BigDecimal, BigDecimal, boolean, BigDecimal, BigDecimal,
	 BigDecimal, BigDecimal, int, int, int, int, int, int, int,
	 int, int, int, boolean, String, ServiceContext)}
	 */
	@Deprecated
	public CommercePriceEntry upsertCommercePriceEntry(
			long commercePriceEntryId, long cProductId, String cpInstanceUuid,
			long commercePriceListId, String externalReferenceCode,
			BigDecimal price, BigDecimal promoPrice, boolean discountDiscovery,
			BigDecimal discountLevel1, BigDecimal discountLevel2,
			BigDecimal discountLevel3, BigDecimal discountLevel4,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String skuExternalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addOrUpdateCommercePriceEntry(String, long, long, String, long,
	 BigDecimal, BigDecimal, String, ServiceContext)}
	 */
	@Deprecated
	@Indexable(type = IndexableType.REINDEX)
	public CommercePriceEntry upsertCommercePriceEntry(
			long commercePriceEntryId, long cProductId, String cpInstanceUuid,
			long commercePriceListId, String externalReferenceCode,
			BigDecimal price, BigDecimal promoPrice,
			String skuExternalReferenceCode, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addOrUpdateCommercePriceEntry(String, long, long, String, long,
	 BigDecimal, BigDecimal, BigDecimal, BigDecimal, int, int,
	 int, int, int, int, int, int, int, int, boolean, String,
	 ServiceContext)}
	 */
	@Deprecated
	public CommercePriceEntry upsertCommercePriceEntry(
			long commercePriceEntryId, long cProductId, String cpInstanceUuid,
			long commercePriceListId, String externalReferenceCode,
			BigDecimal price, boolean discountDiscovery,
			BigDecimal discountLevel1, BigDecimal discountLevel2,
			BigDecimal discountLevel3, BigDecimal discountLevel4,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String skuExternalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException;

	@Override
	@Transactional(enabled = false)
	public CTPersistence<CommercePriceEntry> getCTPersistence();

	@Override
	@Transactional(enabled = false)
	public Class<CommercePriceEntry> getModelClass();

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<CommercePriceEntry>, R, E>
				updateUnsafeFunction)
		throws E;

}