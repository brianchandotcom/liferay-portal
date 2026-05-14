/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service.persistence;

import com.liferay.commerce.model.CommerceOrderAttachment;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce order attachment service. This utility wraps <code>com.liferay.commerce.service.persistence.impl.CommerceOrderAttachmentPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderAttachmentPersistence
 * @generated
 */
public class CommerceOrderAttachmentUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(List)
	 */
	public static void cacheResult(
		List<CommerceOrderAttachment> commerceOrderAttachments) {

		getPersistence().cacheResult(commerceOrderAttachments);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void cacheResult(
		CommerceOrderAttachment commerceOrderAttachment) {

		getPersistence().cacheResult(commerceOrderAttachment);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(
		CommerceOrderAttachment commerceOrderAttachment) {

		getPersistence().clearCache(commerceOrderAttachment);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, CommerceOrderAttachment> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceOrderAttachment> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceOrderAttachment> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceOrderAttachment> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceOrderAttachment> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceOrderAttachment update(
		CommerceOrderAttachment commerceOrderAttachment) {

		return getPersistence().update(commerceOrderAttachment);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceOrderAttachment update(
		CommerceOrderAttachment commerceOrderAttachment,
		ServiceContext serviceContext) {

		return getPersistence().update(commerceOrderAttachment, serviceContext);
	}

	/**
	 * Returns an ordered range of all the commerce order attachments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceOrderAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce order attachments
	 * @param end the upper bound of the range of commerce order attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order attachments
	 */
	public static List<CommerceOrderAttachment> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceOrderAttachment> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce order attachment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order attachment
	 * @throws NoSuchOrderAttachmentException if a matching commerce order attachment could not be found
	 */
	public static CommerceOrderAttachment findByUuid_First(
			String uuid,
			OrderByComparator<CommerceOrderAttachment> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderAttachmentException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first commerce order attachment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order attachment, or <code>null</code> if a matching commerce order attachment could not be found
	 */
	public static CommerceOrderAttachment fetchByUuid_First(
		String uuid,
		OrderByComparator<CommerceOrderAttachment> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Removes all the commerce order attachments where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of commerce order attachments where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce order attachments
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the commerce order attachment where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchOrderAttachmentException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce order attachment
	 * @throws NoSuchOrderAttachmentException if a matching commerce order attachment could not be found
	 */
	public static CommerceOrderAttachment findByUUID_G(
			String uuid, long groupId)
		throws com.liferay.commerce.exception.NoSuchOrderAttachmentException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the commerce order attachment where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce order attachment, or <code>null</code> if a matching commerce order attachment could not be found
	 */
	public static CommerceOrderAttachment fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the commerce order attachment where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce order attachment that was removed
	 */
	public static CommerceOrderAttachment removeByUUID_G(
			String uuid, long groupId)
		throws com.liferay.commerce.exception.NoSuchOrderAttachmentException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of commerce order attachments where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce order attachments
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns an ordered range of all the commerce order attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceOrderAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order attachments
	 * @param end the upper bound of the range of commerce order attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order attachments
	 */
	public static List<CommerceOrderAttachment> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceOrderAttachment> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce order attachment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order attachment
	 * @throws NoSuchOrderAttachmentException if a matching commerce order attachment could not be found
	 */
	public static CommerceOrderAttachment findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceOrderAttachment> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderAttachmentException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce order attachment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order attachment, or <code>null</code> if a matching commerce order attachment could not be found
	 */
	public static CommerceOrderAttachment fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceOrderAttachment> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce order attachments where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of commerce order attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce order attachments
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns an ordered range of all the commerce order attachments where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceOrderAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order attachments
	 * @param end the upper bound of the range of commerce order attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order attachments
	 */
	public static List<CommerceOrderAttachment> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		OrderByComparator<CommerceOrderAttachment> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommerceOrderId(
			commerceOrderId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce order attachment in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order attachment
	 * @throws NoSuchOrderAttachmentException if a matching commerce order attachment could not be found
	 */
	public static CommerceOrderAttachment findByCommerceOrderId_First(
			long commerceOrderId,
			OrderByComparator<CommerceOrderAttachment> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderAttachmentException {

		return getPersistence().findByCommerceOrderId_First(
			commerceOrderId, orderByComparator);
	}

	/**
	 * Returns the first commerce order attachment in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order attachment, or <code>null</code> if a matching commerce order attachment could not be found
	 */
	public static CommerceOrderAttachment fetchByCommerceOrderId_First(
		long commerceOrderId,
		OrderByComparator<CommerceOrderAttachment> orderByComparator) {

		return getPersistence().fetchByCommerceOrderId_First(
			commerceOrderId, orderByComparator);
	}

	/**
	 * Removes all the commerce order attachments where commerceOrderId = &#63; from the database.
	 *
	 * @param commerceOrderId the commerce order ID
	 */
	public static void removeByCommerceOrderId(long commerceOrderId) {
		getPersistence().removeByCommerceOrderId(commerceOrderId);
	}

	/**
	 * Returns the number of commerce order attachments where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @return the number of matching commerce order attachments
	 */
	public static int countByCommerceOrderId(long commerceOrderId) {
		return getPersistence().countByCommerceOrderId(commerceOrderId);
	}

	/**
	 * Returns an ordered range of all the commerce order attachments where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceOrderAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param start the lower bound of the range of commerce order attachments
	 * @param end the upper bound of the range of commerce order attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order attachments
	 */
	public static List<CommerceOrderAttachment> findByC_R(
		long commerceOrderId, boolean restricted, int start, int end,
		OrderByComparator<CommerceOrderAttachment> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_R(
			commerceOrderId, restricted, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce order attachment in the ordered set where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order attachment
	 * @throws NoSuchOrderAttachmentException if a matching commerce order attachment could not be found
	 */
	public static CommerceOrderAttachment findByC_R_First(
			long commerceOrderId, boolean restricted,
			OrderByComparator<CommerceOrderAttachment> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderAttachmentException {

		return getPersistence().findByC_R_First(
			commerceOrderId, restricted, orderByComparator);
	}

	/**
	 * Returns the first commerce order attachment in the ordered set where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order attachment, or <code>null</code> if a matching commerce order attachment could not be found
	 */
	public static CommerceOrderAttachment fetchByC_R_First(
		long commerceOrderId, boolean restricted,
		OrderByComparator<CommerceOrderAttachment> orderByComparator) {

		return getPersistence().fetchByC_R_First(
			commerceOrderId, restricted, orderByComparator);
	}

	/**
	 * Removes all the commerce order attachments where commerceOrderId = &#63; and restricted = &#63; from the database.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 */
	public static void removeByC_R(long commerceOrderId, boolean restricted) {
		getPersistence().removeByC_R(commerceOrderId, restricted);
	}

	/**
	 * Returns the number of commerce order attachments where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @return the number of matching commerce order attachments
	 */
	public static int countByC_R(long commerceOrderId, boolean restricted) {
		return getPersistence().countByC_R(commerceOrderId, restricted);
	}

	/**
	 * Returns the commerce order attachment where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchOrderAttachmentException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce order attachment
	 * @throws NoSuchOrderAttachmentException if a matching commerce order attachment could not be found
	 */
	public static CommerceOrderAttachment findByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.commerce.exception.NoSuchOrderAttachmentException {

		return getPersistence().findByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the commerce order attachment where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce order attachment, or <code>null</code> if a matching commerce order attachment could not be found
	 */
	public static CommerceOrderAttachment fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return getPersistence().fetchByERC_C(
			externalReferenceCode, companyId, useFinderCache);
	}

	/**
	 * Removes the commerce order attachment where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the commerce order attachment that was removed
	 */
	public static CommerceOrderAttachment removeByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.commerce.exception.NoSuchOrderAttachmentException {

		return getPersistence().removeByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the number of commerce order attachments where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching commerce order attachments
	 */
	public static int countByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().countByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Creates a new commerce order attachment with the primary key. Does not add the commerce order attachment to the database.
	 *
	 * @param commerceOrderAttachmentId the primary key for the new commerce order attachment
	 * @return the new commerce order attachment
	 */
	public static CommerceOrderAttachment create(
		long commerceOrderAttachmentId) {

		return getPersistence().create(commerceOrderAttachmentId);
	}

	/**
	 * Removes the commerce order attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceOrderAttachmentId the primary key of the commerce order attachment
	 * @return the commerce order attachment that was removed
	 * @throws NoSuchOrderAttachmentException if a commerce order attachment with the primary key could not be found
	 */
	public static CommerceOrderAttachment remove(long commerceOrderAttachmentId)
		throws com.liferay.commerce.exception.NoSuchOrderAttachmentException {

		return getPersistence().remove(commerceOrderAttachmentId);
	}

	public static CommerceOrderAttachment updateImpl(
		CommerceOrderAttachment commerceOrderAttachment) {

		return getPersistence().updateImpl(commerceOrderAttachment);
	}

	/**
	 * Returns the commerce order attachment with the primary key or throws a <code>NoSuchOrderAttachmentException</code> if it could not be found.
	 *
	 * @param commerceOrderAttachmentId the primary key of the commerce order attachment
	 * @return the commerce order attachment
	 * @throws NoSuchOrderAttachmentException if a commerce order attachment with the primary key could not be found
	 */
	public static CommerceOrderAttachment findByPrimaryKey(
			long commerceOrderAttachmentId)
		throws com.liferay.commerce.exception.NoSuchOrderAttachmentException {

		return getPersistence().findByPrimaryKey(commerceOrderAttachmentId);
	}

	/**
	 * Returns the commerce order attachment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceOrderAttachmentId the primary key of the commerce order attachment
	 * @return the commerce order attachment, or <code>null</code> if a commerce order attachment with the primary key could not be found
	 */
	public static CommerceOrderAttachment fetchByPrimaryKey(
		long commerceOrderAttachmentId) {

		return getPersistence().fetchByPrimaryKey(commerceOrderAttachmentId);
	}

	/**
	 * Returns the commerce order attachment where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce order attachment, or <code>null</code> if a matching commerce order attachment could not be found
	 */
	public static CommerceOrderAttachment fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the commerce order attachment where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce order attachment, or <code>null</code> if a matching commerce order attachment could not be found
	 */
	public static CommerceOrderAttachment fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().fetchByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns all the commerce order attachments where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce order attachments
	 */
	public static List<CommerceOrderAttachment> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the commerce order attachments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceOrderAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce order attachments
	 * @param end the upper bound of the range of commerce order attachments (not inclusive)
	 * @return the range of matching commerce order attachments
	 */
	public static List<CommerceOrderAttachment> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order attachments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceOrderAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce order attachments
	 * @param end the upper bound of the range of commerce order attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order attachments
	 */
	public static List<CommerceOrderAttachment> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceOrderAttachment> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns all the commerce order attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce order attachments
	 */
	public static List<CommerceOrderAttachment> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the commerce order attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceOrderAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order attachments
	 * @param end the upper bound of the range of commerce order attachments (not inclusive)
	 * @return the range of matching commerce order attachments
	 */
	public static List<CommerceOrderAttachment> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceOrderAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order attachments
	 * @param end the upper bound of the range of commerce order attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order attachments
	 */
	public static List<CommerceOrderAttachment> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceOrderAttachment> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns all the commerce order attachments where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @return the matching commerce order attachments
	 */
	public static List<CommerceOrderAttachment> findByCommerceOrderId(
		long commerceOrderId) {

		return getPersistence().findByCommerceOrderId(commerceOrderId);
	}

	/**
	 * Returns a range of all the commerce order attachments where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceOrderAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order attachments
	 * @param end the upper bound of the range of commerce order attachments (not inclusive)
	 * @return the range of matching commerce order attachments
	 */
	public static List<CommerceOrderAttachment> findByCommerceOrderId(
		long commerceOrderId, int start, int end) {

		return getPersistence().findByCommerceOrderId(
			commerceOrderId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order attachments where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceOrderAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order attachments
	 * @param end the upper bound of the range of commerce order attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order attachments
	 */
	public static List<CommerceOrderAttachment> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		OrderByComparator<CommerceOrderAttachment> orderByComparator) {

		return getPersistence().findByCommerceOrderId(
			commerceOrderId, start, end, orderByComparator);
	}

	/**
	 * Returns all the commerce order attachments where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @return the matching commerce order attachments
	 */
	public static List<CommerceOrderAttachment> findByC_R(
		long commerceOrderId, boolean restricted) {

		return getPersistence().findByC_R(commerceOrderId, restricted);
	}

	/**
	 * Returns a range of all the commerce order attachments where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceOrderAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param start the lower bound of the range of commerce order attachments
	 * @param end the upper bound of the range of commerce order attachments (not inclusive)
	 * @return the range of matching commerce order attachments
	 */
	public static List<CommerceOrderAttachment> findByC_R(
		long commerceOrderId, boolean restricted, int start, int end) {

		return getPersistence().findByC_R(
			commerceOrderId, restricted, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order attachments where commerceOrderId = &#63; and restricted = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceOrderAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param restricted the restricted
	 * @param start the lower bound of the range of commerce order attachments
	 * @param end the upper bound of the range of commerce order attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order attachments
	 */
	public static List<CommerceOrderAttachment> findByC_R(
		long commerceOrderId, boolean restricted, int start, int end,
		OrderByComparator<CommerceOrderAttachment> orderByComparator) {

		return getPersistence().findByC_R(
			commerceOrderId, restricted, start, end, orderByComparator);
	}

	public static CommerceOrderAttachmentPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CommerceOrderAttachmentPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CommerceOrderAttachmentPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1414779910