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

package com.liferay.dynamic.data.mapping.service.persistence;

import com.liferay.dynamic.data.mapping.model.DDMField;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the ddm field service. This utility wraps <code>com.liferay.dynamic.data.mapping.service.persistence.impl.DDMFieldPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMFieldPersistence
 * @generated
 */
public class DDMFieldUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(DDMField ddmField) {
		getPersistence().clearCache(ddmField);
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
	public static Map<Serializable, DDMField> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DDMField> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDMField> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDMField> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DDMField> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DDMField update(DDMField ddmField) {
		return getPersistence().update(ddmField);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DDMField update(
		DDMField ddmField, ServiceContext serviceContext) {

		return getPersistence().update(ddmField, serviceContext);
	}

	/**
	 * Returns all the ddm fields where storageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @return the matching ddm fields
	 */
	public static List<DDMField> findByStorageId(long storageId) {
		return getPersistence().findByStorageId(storageId);
	}

	/**
	 * Returns a range of all the ddm fields where storageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldModelImpl</code>.
	 * </p>
	 *
	 * @param storageId the storage ID
	 * @param start the lower bound of the range of ddm fields
	 * @param end the upper bound of the range of ddm fields (not inclusive)
	 * @return the range of matching ddm fields
	 */
	public static List<DDMField> findByStorageId(
		long storageId, int start, int end) {

		return getPersistence().findByStorageId(storageId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm fields where storageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldModelImpl</code>.
	 * </p>
	 *
	 * @param storageId the storage ID
	 * @param start the lower bound of the range of ddm fields
	 * @param end the upper bound of the range of ddm fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm fields
	 */
	public static List<DDMField> findByStorageId(
		long storageId, int start, int end,
		OrderByComparator<DDMField> orderByComparator) {

		return getPersistence().findByStorageId(
			storageId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm fields where storageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldModelImpl</code>.
	 * </p>
	 *
	 * @param storageId the storage ID
	 * @param start the lower bound of the range of ddm fields
	 * @param end the upper bound of the range of ddm fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm fields
	 */
	public static List<DDMField> findByStorageId(
		long storageId, int start, int end,
		OrderByComparator<DDMField> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByStorageId(
			storageId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm field in the ordered set where storageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm field
	 * @throws NoSuchFieldException if a matching ddm field could not be found
	 */
	public static DDMField findByStorageId_First(
			long storageId, OrderByComparator<DDMField> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchFieldException {

		return getPersistence().findByStorageId_First(
			storageId, orderByComparator);
	}

	/**
	 * Returns the first ddm field in the ordered set where storageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm field, or <code>null</code> if a matching ddm field could not be found
	 */
	public static DDMField fetchByStorageId_First(
		long storageId, OrderByComparator<DDMField> orderByComparator) {

		return getPersistence().fetchByStorageId_First(
			storageId, orderByComparator);
	}

	/**
	 * Returns the last ddm field in the ordered set where storageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm field
	 * @throws NoSuchFieldException if a matching ddm field could not be found
	 */
	public static DDMField findByStorageId_Last(
			long storageId, OrderByComparator<DDMField> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchFieldException {

		return getPersistence().findByStorageId_Last(
			storageId, orderByComparator);
	}

	/**
	 * Returns the last ddm field in the ordered set where storageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm field, or <code>null</code> if a matching ddm field could not be found
	 */
	public static DDMField fetchByStorageId_Last(
		long storageId, OrderByComparator<DDMField> orderByComparator) {

		return getPersistence().fetchByStorageId_Last(
			storageId, orderByComparator);
	}

	/**
	 * Returns the ddm fields before and after the current ddm field in the ordered set where storageId = &#63;.
	 *
	 * @param fieldId the primary key of the current ddm field
	 * @param storageId the storage ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm field
	 * @throws NoSuchFieldException if a ddm field with the primary key could not be found
	 */
	public static DDMField[] findByStorageId_PrevAndNext(
			long fieldId, long storageId,
			OrderByComparator<DDMField> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchFieldException {

		return getPersistence().findByStorageId_PrevAndNext(
			fieldId, storageId, orderByComparator);
	}

	/**
	 * Removes all the ddm fields where storageId = &#63; from the database.
	 *
	 * @param storageId the storage ID
	 */
	public static void removeByStorageId(long storageId) {
		getPersistence().removeByStorageId(storageId);
	}

	/**
	 * Returns the number of ddm fields where storageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @return the number of matching ddm fields
	 */
	public static int countByStorageId(long storageId) {
		return getPersistence().countByStorageId(storageId);
	}

	/**
	 * Returns all the ddm fields where structureVersionId = &#63;.
	 *
	 * @param structureVersionId the structure version ID
	 * @return the matching ddm fields
	 */
	public static List<DDMField> findByStructureVersionId(
		long structureVersionId) {

		return getPersistence().findByStructureVersionId(structureVersionId);
	}

	/**
	 * Returns a range of all the ddm fields where structureVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldModelImpl</code>.
	 * </p>
	 *
	 * @param structureVersionId the structure version ID
	 * @param start the lower bound of the range of ddm fields
	 * @param end the upper bound of the range of ddm fields (not inclusive)
	 * @return the range of matching ddm fields
	 */
	public static List<DDMField> findByStructureVersionId(
		long structureVersionId, int start, int end) {

		return getPersistence().findByStructureVersionId(
			structureVersionId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm fields where structureVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldModelImpl</code>.
	 * </p>
	 *
	 * @param structureVersionId the structure version ID
	 * @param start the lower bound of the range of ddm fields
	 * @param end the upper bound of the range of ddm fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm fields
	 */
	public static List<DDMField> findByStructureVersionId(
		long structureVersionId, int start, int end,
		OrderByComparator<DDMField> orderByComparator) {

		return getPersistence().findByStructureVersionId(
			structureVersionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm fields where structureVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldModelImpl</code>.
	 * </p>
	 *
	 * @param structureVersionId the structure version ID
	 * @param start the lower bound of the range of ddm fields
	 * @param end the upper bound of the range of ddm fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm fields
	 */
	public static List<DDMField> findByStructureVersionId(
		long structureVersionId, int start, int end,
		OrderByComparator<DDMField> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByStructureVersionId(
			structureVersionId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm field in the ordered set where structureVersionId = &#63;.
	 *
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm field
	 * @throws NoSuchFieldException if a matching ddm field could not be found
	 */
	public static DDMField findByStructureVersionId_First(
			long structureVersionId,
			OrderByComparator<DDMField> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchFieldException {

		return getPersistence().findByStructureVersionId_First(
			structureVersionId, orderByComparator);
	}

	/**
	 * Returns the first ddm field in the ordered set where structureVersionId = &#63;.
	 *
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm field, or <code>null</code> if a matching ddm field could not be found
	 */
	public static DDMField fetchByStructureVersionId_First(
		long structureVersionId,
		OrderByComparator<DDMField> orderByComparator) {

		return getPersistence().fetchByStructureVersionId_First(
			structureVersionId, orderByComparator);
	}

	/**
	 * Returns the last ddm field in the ordered set where structureVersionId = &#63;.
	 *
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm field
	 * @throws NoSuchFieldException if a matching ddm field could not be found
	 */
	public static DDMField findByStructureVersionId_Last(
			long structureVersionId,
			OrderByComparator<DDMField> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchFieldException {

		return getPersistence().findByStructureVersionId_Last(
			structureVersionId, orderByComparator);
	}

	/**
	 * Returns the last ddm field in the ordered set where structureVersionId = &#63;.
	 *
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm field, or <code>null</code> if a matching ddm field could not be found
	 */
	public static DDMField fetchByStructureVersionId_Last(
		long structureVersionId,
		OrderByComparator<DDMField> orderByComparator) {

		return getPersistence().fetchByStructureVersionId_Last(
			structureVersionId, orderByComparator);
	}

	/**
	 * Returns the ddm fields before and after the current ddm field in the ordered set where structureVersionId = &#63;.
	 *
	 * @param fieldId the primary key of the current ddm field
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm field
	 * @throws NoSuchFieldException if a ddm field with the primary key could not be found
	 */
	public static DDMField[] findByStructureVersionId_PrevAndNext(
			long fieldId, long structureVersionId,
			OrderByComparator<DDMField> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchFieldException {

		return getPersistence().findByStructureVersionId_PrevAndNext(
			fieldId, structureVersionId, orderByComparator);
	}

	/**
	 * Removes all the ddm fields where structureVersionId = &#63; from the database.
	 *
	 * @param structureVersionId the structure version ID
	 */
	public static void removeByStructureVersionId(long structureVersionId) {
		getPersistence().removeByStructureVersionId(structureVersionId);
	}

	/**
	 * Returns the number of ddm fields where structureVersionId = &#63;.
	 *
	 * @param structureVersionId the structure version ID
	 * @return the number of matching ddm fields
	 */
	public static int countByStructureVersionId(long structureVersionId) {
		return getPersistence().countByStructureVersionId(structureVersionId);
	}

	/**
	 * Returns all the ddm fields where companyId = &#63; and fieldType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param fieldType the field type
	 * @return the matching ddm fields
	 */
	public static List<DDMField> findByC_F(long companyId, String fieldType) {
		return getPersistence().findByC_F(companyId, fieldType);
	}

	/**
	 * Returns a range of all the ddm fields where companyId = &#63; and fieldType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param fieldType the field type
	 * @param start the lower bound of the range of ddm fields
	 * @param end the upper bound of the range of ddm fields (not inclusive)
	 * @return the range of matching ddm fields
	 */
	public static List<DDMField> findByC_F(
		long companyId, String fieldType, int start, int end) {

		return getPersistence().findByC_F(companyId, fieldType, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm fields where companyId = &#63; and fieldType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param fieldType the field type
	 * @param start the lower bound of the range of ddm fields
	 * @param end the upper bound of the range of ddm fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm fields
	 */
	public static List<DDMField> findByC_F(
		long companyId, String fieldType, int start, int end,
		OrderByComparator<DDMField> orderByComparator) {

		return getPersistence().findByC_F(
			companyId, fieldType, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm fields where companyId = &#63; and fieldType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param fieldType the field type
	 * @param start the lower bound of the range of ddm fields
	 * @param end the upper bound of the range of ddm fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm fields
	 */
	public static List<DDMField> findByC_F(
		long companyId, String fieldType, int start, int end,
		OrderByComparator<DDMField> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByC_F(
			companyId, fieldType, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first ddm field in the ordered set where companyId = &#63; and fieldType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param fieldType the field type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm field
	 * @throws NoSuchFieldException if a matching ddm field could not be found
	 */
	public static DDMField findByC_F_First(
			long companyId, String fieldType,
			OrderByComparator<DDMField> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchFieldException {

		return getPersistence().findByC_F_First(
			companyId, fieldType, orderByComparator);
	}

	/**
	 * Returns the first ddm field in the ordered set where companyId = &#63; and fieldType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param fieldType the field type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm field, or <code>null</code> if a matching ddm field could not be found
	 */
	public static DDMField fetchByC_F_First(
		long companyId, String fieldType,
		OrderByComparator<DDMField> orderByComparator) {

		return getPersistence().fetchByC_F_First(
			companyId, fieldType, orderByComparator);
	}

	/**
	 * Returns the last ddm field in the ordered set where companyId = &#63; and fieldType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param fieldType the field type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm field
	 * @throws NoSuchFieldException if a matching ddm field could not be found
	 */
	public static DDMField findByC_F_Last(
			long companyId, String fieldType,
			OrderByComparator<DDMField> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchFieldException {

		return getPersistence().findByC_F_Last(
			companyId, fieldType, orderByComparator);
	}

	/**
	 * Returns the last ddm field in the ordered set where companyId = &#63; and fieldType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param fieldType the field type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm field, or <code>null</code> if a matching ddm field could not be found
	 */
	public static DDMField fetchByC_F_Last(
		long companyId, String fieldType,
		OrderByComparator<DDMField> orderByComparator) {

		return getPersistence().fetchByC_F_Last(
			companyId, fieldType, orderByComparator);
	}

	/**
	 * Returns the ddm fields before and after the current ddm field in the ordered set where companyId = &#63; and fieldType = &#63;.
	 *
	 * @param fieldId the primary key of the current ddm field
	 * @param companyId the company ID
	 * @param fieldType the field type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm field
	 * @throws NoSuchFieldException if a ddm field with the primary key could not be found
	 */
	public static DDMField[] findByC_F_PrevAndNext(
			long fieldId, long companyId, String fieldType,
			OrderByComparator<DDMField> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchFieldException {

		return getPersistence().findByC_F_PrevAndNext(
			fieldId, companyId, fieldType, orderByComparator);
	}

	/**
	 * Removes all the ddm fields where companyId = &#63; and fieldType = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param fieldType the field type
	 */
	public static void removeByC_F(long companyId, String fieldType) {
		getPersistence().removeByC_F(companyId, fieldType);
	}

	/**
	 * Returns the number of ddm fields where companyId = &#63; and fieldType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param fieldType the field type
	 * @return the number of matching ddm fields
	 */
	public static int countByC_F(long companyId, String fieldType) {
		return getPersistence().countByC_F(companyId, fieldType);
	}

	/**
	 * Returns the ddm field where storageId = &#63; and instanceId = &#63; or throws a <code>NoSuchFieldException</code> if it could not be found.
	 *
	 * @param storageId the storage ID
	 * @param instanceId the instance ID
	 * @return the matching ddm field
	 * @throws NoSuchFieldException if a matching ddm field could not be found
	 */
	public static DDMField findByS_I(long storageId, String instanceId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchFieldException {

		return getPersistence().findByS_I(storageId, instanceId);
	}

	/**
	 * Returns the ddm field where storageId = &#63; and instanceId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param storageId the storage ID
	 * @param instanceId the instance ID
	 * @return the matching ddm field, or <code>null</code> if a matching ddm field could not be found
	 */
	public static DDMField fetchByS_I(long storageId, String instanceId) {
		return getPersistence().fetchByS_I(storageId, instanceId);
	}

	/**
	 * Returns the ddm field where storageId = &#63; and instanceId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param storageId the storage ID
	 * @param instanceId the instance ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm field, or <code>null</code> if a matching ddm field could not be found
	 */
	public static DDMField fetchByS_I(
		long storageId, String instanceId, boolean useFinderCache) {

		return getPersistence().fetchByS_I(
			storageId, instanceId, useFinderCache);
	}

	/**
	 * Removes the ddm field where storageId = &#63; and instanceId = &#63; from the database.
	 *
	 * @param storageId the storage ID
	 * @param instanceId the instance ID
	 * @return the ddm field that was removed
	 */
	public static DDMField removeByS_I(long storageId, String instanceId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchFieldException {

		return getPersistence().removeByS_I(storageId, instanceId);
	}

	/**
	 * Returns the number of ddm fields where storageId = &#63; and instanceId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param instanceId the instance ID
	 * @return the number of matching ddm fields
	 */
	public static int countByS_I(long storageId, String instanceId) {
		return getPersistence().countByS_I(storageId, instanceId);
	}

	/**
	 * Caches the ddm field in the entity cache if it is enabled.
	 *
	 * @param ddmField the ddm field
	 */
	public static void cacheResult(DDMField ddmField) {
		getPersistence().cacheResult(ddmField);
	}

	/**
	 * Caches the ddm fields in the entity cache if it is enabled.
	 *
	 * @param ddmFields the ddm fields
	 */
	public static void cacheResult(List<DDMField> ddmFields) {
		getPersistence().cacheResult(ddmFields);
	}

	/**
	 * Creates a new ddm field with the primary key. Does not add the ddm field to the database.
	 *
	 * @param fieldId the primary key for the new ddm field
	 * @return the new ddm field
	 */
	public static DDMField create(long fieldId) {
		return getPersistence().create(fieldId);
	}

	/**
	 * Removes the ddm field with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fieldId the primary key of the ddm field
	 * @return the ddm field that was removed
	 * @throws NoSuchFieldException if a ddm field with the primary key could not be found
	 */
	public static DDMField remove(long fieldId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchFieldException {

		return getPersistence().remove(fieldId);
	}

	public static DDMField updateImpl(DDMField ddmField) {
		return getPersistence().updateImpl(ddmField);
	}

	/**
	 * Returns the ddm field with the primary key or throws a <code>NoSuchFieldException</code> if it could not be found.
	 *
	 * @param fieldId the primary key of the ddm field
	 * @return the ddm field
	 * @throws NoSuchFieldException if a ddm field with the primary key could not be found
	 */
	public static DDMField findByPrimaryKey(long fieldId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchFieldException {

		return getPersistence().findByPrimaryKey(fieldId);
	}

	/**
	 * Returns the ddm field with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fieldId the primary key of the ddm field
	 * @return the ddm field, or <code>null</code> if a ddm field with the primary key could not be found
	 */
	public static DDMField fetchByPrimaryKey(long fieldId) {
		return getPersistence().fetchByPrimaryKey(fieldId);
	}

	/**
	 * Returns all the ddm fields.
	 *
	 * @return the ddm fields
	 */
	public static List<DDMField> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the ddm fields.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm fields
	 * @param end the upper bound of the range of ddm fields (not inclusive)
	 * @return the range of ddm fields
	 */
	public static List<DDMField> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the ddm fields.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm fields
	 * @param end the upper bound of the range of ddm fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm fields
	 */
	public static List<DDMField> findAll(
		int start, int end, OrderByComparator<DDMField> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm fields.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm fields
	 * @param end the upper bound of the range of ddm fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm fields
	 */
	public static List<DDMField> findAll(
		int start, int end, OrderByComparator<DDMField> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ddm fields from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ddm fields.
	 *
	 * @return the number of ddm fields
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DDMFieldPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(DDMFieldPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile DDMFieldPersistence _persistence;

}