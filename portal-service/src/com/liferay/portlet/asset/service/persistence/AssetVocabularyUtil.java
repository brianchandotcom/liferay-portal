/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.asset.model.AssetVocabulary;

import java.util.List;

/**
 * The persistence utility for the asset vocabulary service. This utility wraps {@link AssetVocabularyPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyPersistence
 * @see AssetVocabularyPersistenceImpl
 * @generated
 */
public class AssetVocabularyUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(AssetVocabulary assetVocabulary) {
		getPersistence().clearCache(assetVocabulary);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AssetVocabulary> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetVocabulary> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetVocabulary> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static AssetVocabulary update(AssetVocabulary assetVocabulary)
		throws SystemException {
		return getPersistence().update(assetVocabulary);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static AssetVocabulary update(AssetVocabulary assetVocabulary,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(assetVocabulary, serviceContext);
	}

	/**
	* Returns all the asset vocabularies where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the asset vocabularies where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of asset vocabularies
	* @param end the upper bound of the range of asset vocabularies (not inclusive)
	* @return the range of matching asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the asset vocabularies where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of asset vocabularies
	* @param end the upper bound of the range of asset vocabularies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first asset vocabulary in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset vocabulary
	* @throws com.liferay.portlet.asset.NoSuchVocabularyException if a matching asset vocabulary could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first asset vocabulary in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last asset vocabulary in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset vocabulary
	* @throws com.liferay.portlet.asset.NoSuchVocabularyException if a matching asset vocabulary could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last asset vocabulary in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the asset vocabularies before and after the current asset vocabulary in the ordered set where uuid = &#63;.
	*
	* @param vocabularyId the primary key of the current asset vocabulary
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset vocabulary
	* @throws com.liferay.portlet.asset.NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary[] findByUuid_PrevAndNext(
		long vocabularyId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence()
				   .findByUuid_PrevAndNext(vocabularyId, uuid, orderByComparator);
	}

	/**
	* Removes all the asset vocabularies where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of asset vocabularies where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the asset vocabulary where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchVocabularyException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching asset vocabulary
	* @throws com.liferay.portlet.asset.NoSuchVocabularyException if a matching asset vocabulary could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the asset vocabulary where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the asset vocabulary where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the asset vocabulary where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the asset vocabulary that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of asset vocabularies where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the asset vocabularies where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findByUuid_C(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the asset vocabularies where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of asset vocabularies
	* @param end the upper bound of the range of asset vocabularies (not inclusive)
	* @return the range of matching asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the asset vocabularies where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of asset vocabularies
	* @param end the upper bound of the range of asset vocabularies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first asset vocabulary in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset vocabulary
	* @throws com.liferay.portlet.asset.NoSuchVocabularyException if a matching asset vocabulary could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first asset vocabulary in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last asset vocabulary in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset vocabulary
	* @throws com.liferay.portlet.asset.NoSuchVocabularyException if a matching asset vocabulary could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last asset vocabulary in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the asset vocabularies before and after the current asset vocabulary in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param vocabularyId the primary key of the current asset vocabulary
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset vocabulary
	* @throws com.liferay.portlet.asset.NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary[] findByUuid_C_PrevAndNext(
		long vocabularyId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(vocabularyId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the asset vocabularies where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of asset vocabularies where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the asset vocabularies where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the asset vocabularies where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset vocabularies
	* @param end the upper bound of the range of asset vocabularies (not inclusive)
	* @return the range of matching asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the asset vocabularies where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset vocabularies
	* @param end the upper bound of the range of asset vocabularies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first asset vocabulary in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset vocabulary
	* @throws com.liferay.portlet.asset.NoSuchVocabularyException if a matching asset vocabulary could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first asset vocabulary in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last asset vocabulary in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset vocabulary
	* @throws com.liferay.portlet.asset.NoSuchVocabularyException if a matching asset vocabulary could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last asset vocabulary in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the asset vocabularies before and after the current asset vocabulary in the ordered set where groupId = &#63;.
	*
	* @param vocabularyId the primary key of the current asset vocabulary
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset vocabulary
	* @throws com.liferay.portlet.asset.NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary[] findByGroupId_PrevAndNext(
		long vocabularyId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(vocabularyId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the asset vocabularies that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching asset vocabularies that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the asset vocabularies that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset vocabularies
	* @param end the upper bound of the range of asset vocabularies (not inclusive)
	* @return the range of matching asset vocabularies that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the asset vocabularies that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset vocabularies
	* @param end the upper bound of the range of asset vocabularies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset vocabularies that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the asset vocabularies before and after the current asset vocabulary in the ordered set of asset vocabularies that the user has permission to view where groupId = &#63;.
	*
	* @param vocabularyId the primary key of the current asset vocabulary
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset vocabulary
	* @throws com.liferay.portlet.asset.NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary[] filterFindByGroupId_PrevAndNext(
		long vocabularyId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(vocabularyId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the asset vocabularies where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of asset vocabularies where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of asset vocabularies that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching asset vocabularies that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the asset vocabularies where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the asset vocabularies where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of asset vocabularies
	* @param end the upper bound of the range of asset vocabularies (not inclusive)
	* @return the range of matching asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the asset vocabularies where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of asset vocabularies
	* @param end the upper bound of the range of asset vocabularies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first asset vocabulary in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset vocabulary
	* @throws com.liferay.portlet.asset.NoSuchVocabularyException if a matching asset vocabulary could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first asset vocabulary in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last asset vocabulary in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset vocabulary
	* @throws com.liferay.portlet.asset.NoSuchVocabularyException if a matching asset vocabulary could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last asset vocabulary in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the asset vocabularies before and after the current asset vocabulary in the ordered set where companyId = &#63;.
	*
	* @param vocabularyId the primary key of the current asset vocabulary
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset vocabulary
	* @throws com.liferay.portlet.asset.NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary[] findByCompanyId_PrevAndNext(
		long vocabularyId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(vocabularyId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the asset vocabularies where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of asset vocabularies where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the asset vocabulary where groupId = &#63; and name = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchVocabularyException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching asset vocabulary
	* @throws com.liferay.portlet.asset.NoSuchVocabularyException if a matching asset vocabulary could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary findByG_N(
		long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence().findByG_N(groupId, name);
	}

	/**
	* Returns the asset vocabulary where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary fetchByG_N(
		long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_N(groupId, name);
	}

	/**
	* Returns the asset vocabulary where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary fetchByG_N(
		long groupId, java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_N(groupId, name, retrieveFromCache);
	}

	/**
	* Removes the asset vocabulary where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the asset vocabulary that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary removeByG_N(
		long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence().removeByG_N(groupId, name);
	}

	/**
	* Returns the number of asset vocabularies where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_N(groupId, name);
	}

	/**
	* Caches the asset vocabulary in the entity cache if it is enabled.
	*
	* @param assetVocabulary the asset vocabulary
	*/
	public static void cacheResult(
		com.liferay.portlet.asset.model.AssetVocabulary assetVocabulary) {
		getPersistence().cacheResult(assetVocabulary);
	}

	/**
	* Caches the asset vocabularies in the entity cache if it is enabled.
	*
	* @param assetVocabularies the asset vocabularies
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> assetVocabularies) {
		getPersistence().cacheResult(assetVocabularies);
	}

	/**
	* Creates a new asset vocabulary with the primary key. Does not add the asset vocabulary to the database.
	*
	* @param vocabularyId the primary key for the new asset vocabulary
	* @return the new asset vocabulary
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary create(
		long vocabularyId) {
		return getPersistence().create(vocabularyId);
	}

	/**
	* Removes the asset vocabulary with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param vocabularyId the primary key of the asset vocabulary
	* @return the asset vocabulary that was removed
	* @throws com.liferay.portlet.asset.NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary remove(
		long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence().remove(vocabularyId);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary updateImpl(
		com.liferay.portlet.asset.model.AssetVocabulary assetVocabulary)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(assetVocabulary);
	}

	/**
	* Returns the asset vocabulary with the primary key or throws a {@link com.liferay.portlet.asset.NoSuchVocabularyException} if it could not be found.
	*
	* @param vocabularyId the primary key of the asset vocabulary
	* @return the asset vocabulary
	* @throws com.liferay.portlet.asset.NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary findByPrimaryKey(
		long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence().findByPrimaryKey(vocabularyId);
	}

	/**
	* Returns the asset vocabulary with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param vocabularyId the primary key of the asset vocabulary
	* @return the asset vocabulary, or <code>null</code> if a asset vocabulary with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary fetchByPrimaryKey(
		long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(vocabularyId);
	}

	/**
	* Returns all the asset vocabularies.
	*
	* @return the asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the asset vocabularies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset vocabularies
	* @param end the upper bound of the range of asset vocabularies (not inclusive)
	* @return the range of asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the asset vocabularies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset vocabularies
	* @param end the upper bound of the range of asset vocabularies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the asset vocabularies from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of asset vocabularies.
	*
	* @return the number of asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static AssetVocabularyPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AssetVocabularyPersistence)PortalBeanLocatorUtil.locate(AssetVocabularyPersistence.class.getName());

			ReferenceRegistry.registerReference(AssetVocabularyUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(AssetVocabularyPersistence persistence) {
	}

	private static AssetVocabularyPersistence _persistence;
}