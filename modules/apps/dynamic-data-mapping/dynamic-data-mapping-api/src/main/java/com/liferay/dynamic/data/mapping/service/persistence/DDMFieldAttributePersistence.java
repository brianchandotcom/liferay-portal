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

import com.liferay.dynamic.data.mapping.exception.NoSuchFieldAttributeException;
import com.liferay.dynamic.data.mapping.model.DDMFieldAttribute;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the ddm field attribute service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMFieldAttributeUtil
 * @generated
 */
@ProviderType
public interface DDMFieldAttributePersistence
	extends BasePersistence<DDMFieldAttribute>,
			CTPersistence<DDMFieldAttribute> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMFieldAttributeUtil} to access the ddm field attribute persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the ddm field attributes where storageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @return the matching ddm field attributes
	 */
	public java.util.List<DDMFieldAttribute> findByStorageId(long storageId);

	/**
	 * Returns a range of all the ddm field attributes where storageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param storageId the storage ID
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @return the range of matching ddm field attributes
	 */
	public java.util.List<DDMFieldAttribute> findByStorageId(
		long storageId, int start, int end);

	/**
	 * Returns an ordered range of all the ddm field attributes where storageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param storageId the storage ID
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm field attributes
	 */
	public java.util.List<DDMFieldAttribute> findByStorageId(
		long storageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFieldAttribute>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm field attributes where storageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param storageId the storage ID
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm field attributes
	 */
	public java.util.List<DDMFieldAttribute> findByStorageId(
		long storageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFieldAttribute>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm field attribute in the ordered set where storageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm field attribute
	 * @throws NoSuchFieldAttributeException if a matching ddm field attribute could not be found
	 */
	public DDMFieldAttribute findByStorageId_First(
			long storageId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMFieldAttribute>
				orderByComparator)
		throws NoSuchFieldAttributeException;

	/**
	 * Returns the first ddm field attribute in the ordered set where storageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm field attribute, or <code>null</code> if a matching ddm field attribute could not be found
	 */
	public DDMFieldAttribute fetchByStorageId_First(
		long storageId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFieldAttribute>
			orderByComparator);

	/**
	 * Returns the last ddm field attribute in the ordered set where storageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm field attribute
	 * @throws NoSuchFieldAttributeException if a matching ddm field attribute could not be found
	 */
	public DDMFieldAttribute findByStorageId_Last(
			long storageId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMFieldAttribute>
				orderByComparator)
		throws NoSuchFieldAttributeException;

	/**
	 * Returns the last ddm field attribute in the ordered set where storageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm field attribute, or <code>null</code> if a matching ddm field attribute could not be found
	 */
	public DDMFieldAttribute fetchByStorageId_Last(
		long storageId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFieldAttribute>
			orderByComparator);

	/**
	 * Returns the ddm field attributes before and after the current ddm field attribute in the ordered set where storageId = &#63;.
	 *
	 * @param fieldAttributeId the primary key of the current ddm field attribute
	 * @param storageId the storage ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm field attribute
	 * @throws NoSuchFieldAttributeException if a ddm field attribute with the primary key could not be found
	 */
	public DDMFieldAttribute[] findByStorageId_PrevAndNext(
			long fieldAttributeId, long storageId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMFieldAttribute>
				orderByComparator)
		throws NoSuchFieldAttributeException;

	/**
	 * Removes all the ddm field attributes where storageId = &#63; from the database.
	 *
	 * @param storageId the storage ID
	 */
	public void removeByStorageId(long storageId);

	/**
	 * Returns the number of ddm field attributes where storageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @return the number of matching ddm field attributes
	 */
	public int countByStorageId(long storageId);

	/**
	 * Returns all the ddm field attributes where storageId = &#63; and languageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param languageId the language ID
	 * @return the matching ddm field attributes
	 */
	public java.util.List<DDMFieldAttribute> findByS_L(
		long storageId, String languageId);

	/**
	 * Returns a range of all the ddm field attributes where storageId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param storageId the storage ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @return the range of matching ddm field attributes
	 */
	public java.util.List<DDMFieldAttribute> findByS_L(
		long storageId, String languageId, int start, int end);

	/**
	 * Returns an ordered range of all the ddm field attributes where storageId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param storageId the storage ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm field attributes
	 */
	public java.util.List<DDMFieldAttribute> findByS_L(
		long storageId, String languageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFieldAttribute>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm field attributes where storageId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param storageId the storage ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm field attributes
	 */
	public java.util.List<DDMFieldAttribute> findByS_L(
		long storageId, String languageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFieldAttribute>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm field attribute in the ordered set where storageId = &#63; and languageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm field attribute
	 * @throws NoSuchFieldAttributeException if a matching ddm field attribute could not be found
	 */
	public DDMFieldAttribute findByS_L_First(
			long storageId, String languageId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMFieldAttribute>
				orderByComparator)
		throws NoSuchFieldAttributeException;

	/**
	 * Returns the first ddm field attribute in the ordered set where storageId = &#63; and languageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm field attribute, or <code>null</code> if a matching ddm field attribute could not be found
	 */
	public DDMFieldAttribute fetchByS_L_First(
		long storageId, String languageId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFieldAttribute>
			orderByComparator);

	/**
	 * Returns the last ddm field attribute in the ordered set where storageId = &#63; and languageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm field attribute
	 * @throws NoSuchFieldAttributeException if a matching ddm field attribute could not be found
	 */
	public DDMFieldAttribute findByS_L_Last(
			long storageId, String languageId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMFieldAttribute>
				orderByComparator)
		throws NoSuchFieldAttributeException;

	/**
	 * Returns the last ddm field attribute in the ordered set where storageId = &#63; and languageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm field attribute, or <code>null</code> if a matching ddm field attribute could not be found
	 */
	public DDMFieldAttribute fetchByS_L_Last(
		long storageId, String languageId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFieldAttribute>
			orderByComparator);

	/**
	 * Returns the ddm field attributes before and after the current ddm field attribute in the ordered set where storageId = &#63; and languageId = &#63;.
	 *
	 * @param fieldAttributeId the primary key of the current ddm field attribute
	 * @param storageId the storage ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm field attribute
	 * @throws NoSuchFieldAttributeException if a ddm field attribute with the primary key could not be found
	 */
	public DDMFieldAttribute[] findByS_L_PrevAndNext(
			long fieldAttributeId, long storageId, String languageId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMFieldAttribute>
				orderByComparator)
		throws NoSuchFieldAttributeException;

	/**
	 * Removes all the ddm field attributes where storageId = &#63; and languageId = &#63; from the database.
	 *
	 * @param storageId the storage ID
	 * @param languageId the language ID
	 */
	public void removeByS_L(long storageId, String languageId);

	/**
	 * Returns the number of ddm field attributes where storageId = &#63; and languageId = &#63;.
	 *
	 * @param storageId the storage ID
	 * @param languageId the language ID
	 * @return the number of matching ddm field attributes
	 */
	public int countByS_L(long storageId, String languageId);

	/**
	 * Returns all the ddm field attributes where attributeName = &#63; and smallAttributeValue = &#63;.
	 *
	 * @param attributeName the attribute name
	 * @param smallAttributeValue the small attribute value
	 * @return the matching ddm field attributes
	 */
	public java.util.List<DDMFieldAttribute> findByAN_SAV(
		String attributeName, String smallAttributeValue);

	/**
	 * Returns a range of all the ddm field attributes where attributeName = &#63; and smallAttributeValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param attributeName the attribute name
	 * @param smallAttributeValue the small attribute value
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @return the range of matching ddm field attributes
	 */
	public java.util.List<DDMFieldAttribute> findByAN_SAV(
		String attributeName, String smallAttributeValue, int start, int end);

	/**
	 * Returns an ordered range of all the ddm field attributes where attributeName = &#63; and smallAttributeValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param attributeName the attribute name
	 * @param smallAttributeValue the small attribute value
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm field attributes
	 */
	public java.util.List<DDMFieldAttribute> findByAN_SAV(
		String attributeName, String smallAttributeValue, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFieldAttribute>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm field attributes where attributeName = &#63; and smallAttributeValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param attributeName the attribute name
	 * @param smallAttributeValue the small attribute value
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm field attributes
	 */
	public java.util.List<DDMFieldAttribute> findByAN_SAV(
		String attributeName, String smallAttributeValue, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFieldAttribute>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm field attribute in the ordered set where attributeName = &#63; and smallAttributeValue = &#63;.
	 *
	 * @param attributeName the attribute name
	 * @param smallAttributeValue the small attribute value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm field attribute
	 * @throws NoSuchFieldAttributeException if a matching ddm field attribute could not be found
	 */
	public DDMFieldAttribute findByAN_SAV_First(
			String attributeName, String smallAttributeValue,
			com.liferay.portal.kernel.util.OrderByComparator<DDMFieldAttribute>
				orderByComparator)
		throws NoSuchFieldAttributeException;

	/**
	 * Returns the first ddm field attribute in the ordered set where attributeName = &#63; and smallAttributeValue = &#63;.
	 *
	 * @param attributeName the attribute name
	 * @param smallAttributeValue the small attribute value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm field attribute, or <code>null</code> if a matching ddm field attribute could not be found
	 */
	public DDMFieldAttribute fetchByAN_SAV_First(
		String attributeName, String smallAttributeValue,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFieldAttribute>
			orderByComparator);

	/**
	 * Returns the last ddm field attribute in the ordered set where attributeName = &#63; and smallAttributeValue = &#63;.
	 *
	 * @param attributeName the attribute name
	 * @param smallAttributeValue the small attribute value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm field attribute
	 * @throws NoSuchFieldAttributeException if a matching ddm field attribute could not be found
	 */
	public DDMFieldAttribute findByAN_SAV_Last(
			String attributeName, String smallAttributeValue,
			com.liferay.portal.kernel.util.OrderByComparator<DDMFieldAttribute>
				orderByComparator)
		throws NoSuchFieldAttributeException;

	/**
	 * Returns the last ddm field attribute in the ordered set where attributeName = &#63; and smallAttributeValue = &#63;.
	 *
	 * @param attributeName the attribute name
	 * @param smallAttributeValue the small attribute value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm field attribute, or <code>null</code> if a matching ddm field attribute could not be found
	 */
	public DDMFieldAttribute fetchByAN_SAV_Last(
		String attributeName, String smallAttributeValue,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFieldAttribute>
			orderByComparator);

	/**
	 * Returns the ddm field attributes before and after the current ddm field attribute in the ordered set where attributeName = &#63; and smallAttributeValue = &#63;.
	 *
	 * @param fieldAttributeId the primary key of the current ddm field attribute
	 * @param attributeName the attribute name
	 * @param smallAttributeValue the small attribute value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm field attribute
	 * @throws NoSuchFieldAttributeException if a ddm field attribute with the primary key could not be found
	 */
	public DDMFieldAttribute[] findByAN_SAV_PrevAndNext(
			long fieldAttributeId, String attributeName,
			String smallAttributeValue,
			com.liferay.portal.kernel.util.OrderByComparator<DDMFieldAttribute>
				orderByComparator)
		throws NoSuchFieldAttributeException;

	/**
	 * Removes all the ddm field attributes where attributeName = &#63; and smallAttributeValue = &#63; from the database.
	 *
	 * @param attributeName the attribute name
	 * @param smallAttributeValue the small attribute value
	 */
	public void removeByAN_SAV(
		String attributeName, String smallAttributeValue);

	/**
	 * Returns the number of ddm field attributes where attributeName = &#63; and smallAttributeValue = &#63;.
	 *
	 * @param attributeName the attribute name
	 * @param smallAttributeValue the small attribute value
	 * @return the number of matching ddm field attributes
	 */
	public int countByAN_SAV(String attributeName, String smallAttributeValue);

	/**
	 * Returns the ddm field attribute where fieldId = &#63; and languageId = &#63; and attributeName = &#63; or throws a <code>NoSuchFieldAttributeException</code> if it could not be found.
	 *
	 * @param fieldId the field ID
	 * @param languageId the language ID
	 * @param attributeName the attribute name
	 * @return the matching ddm field attribute
	 * @throws NoSuchFieldAttributeException if a matching ddm field attribute could not be found
	 */
	public DDMFieldAttribute findByF_L_AN(
			long fieldId, String languageId, String attributeName)
		throws NoSuchFieldAttributeException;

	/**
	 * Returns the ddm field attribute where fieldId = &#63; and languageId = &#63; and attributeName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fieldId the field ID
	 * @param languageId the language ID
	 * @param attributeName the attribute name
	 * @return the matching ddm field attribute, or <code>null</code> if a matching ddm field attribute could not be found
	 */
	public DDMFieldAttribute fetchByF_L_AN(
		long fieldId, String languageId, String attributeName);

	/**
	 * Returns the ddm field attribute where fieldId = &#63; and languageId = &#63; and attributeName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param fieldId the field ID
	 * @param languageId the language ID
	 * @param attributeName the attribute name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm field attribute, or <code>null</code> if a matching ddm field attribute could not be found
	 */
	public DDMFieldAttribute fetchByF_L_AN(
		long fieldId, String languageId, String attributeName,
		boolean useFinderCache);

	/**
	 * Removes the ddm field attribute where fieldId = &#63; and languageId = &#63; and attributeName = &#63; from the database.
	 *
	 * @param fieldId the field ID
	 * @param languageId the language ID
	 * @param attributeName the attribute name
	 * @return the ddm field attribute that was removed
	 */
	public DDMFieldAttribute removeByF_L_AN(
			long fieldId, String languageId, String attributeName)
		throws NoSuchFieldAttributeException;

	/**
	 * Returns the number of ddm field attributes where fieldId = &#63; and languageId = &#63; and attributeName = &#63;.
	 *
	 * @param fieldId the field ID
	 * @param languageId the language ID
	 * @param attributeName the attribute name
	 * @return the number of matching ddm field attributes
	 */
	public int countByF_L_AN(
		long fieldId, String languageId, String attributeName);

	/**
	 * Caches the ddm field attribute in the entity cache if it is enabled.
	 *
	 * @param ddmFieldAttribute the ddm field attribute
	 */
	public void cacheResult(DDMFieldAttribute ddmFieldAttribute);

	/**
	 * Caches the ddm field attributes in the entity cache if it is enabled.
	 *
	 * @param ddmFieldAttributes the ddm field attributes
	 */
	public void cacheResult(
		java.util.List<DDMFieldAttribute> ddmFieldAttributes);

	/**
	 * Creates a new ddm field attribute with the primary key. Does not add the ddm field attribute to the database.
	 *
	 * @param fieldAttributeId the primary key for the new ddm field attribute
	 * @return the new ddm field attribute
	 */
	public DDMFieldAttribute create(long fieldAttributeId);

	/**
	 * Removes the ddm field attribute with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fieldAttributeId the primary key of the ddm field attribute
	 * @return the ddm field attribute that was removed
	 * @throws NoSuchFieldAttributeException if a ddm field attribute with the primary key could not be found
	 */
	public DDMFieldAttribute remove(long fieldAttributeId)
		throws NoSuchFieldAttributeException;

	public DDMFieldAttribute updateImpl(DDMFieldAttribute ddmFieldAttribute);

	/**
	 * Returns the ddm field attribute with the primary key or throws a <code>NoSuchFieldAttributeException</code> if it could not be found.
	 *
	 * @param fieldAttributeId the primary key of the ddm field attribute
	 * @return the ddm field attribute
	 * @throws NoSuchFieldAttributeException if a ddm field attribute with the primary key could not be found
	 */
	public DDMFieldAttribute findByPrimaryKey(long fieldAttributeId)
		throws NoSuchFieldAttributeException;

	/**
	 * Returns the ddm field attribute with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fieldAttributeId the primary key of the ddm field attribute
	 * @return the ddm field attribute, or <code>null</code> if a ddm field attribute with the primary key could not be found
	 */
	public DDMFieldAttribute fetchByPrimaryKey(long fieldAttributeId);

	/**
	 * Returns all the ddm field attributes.
	 *
	 * @return the ddm field attributes
	 */
	public java.util.List<DDMFieldAttribute> findAll();

	/**
	 * Returns a range of all the ddm field attributes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @return the range of ddm field attributes
	 */
	public java.util.List<DDMFieldAttribute> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the ddm field attributes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm field attributes
	 */
	public java.util.List<DDMFieldAttribute> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFieldAttribute>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm field attributes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFieldAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm field attributes
	 * @param end the upper bound of the range of ddm field attributes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm field attributes
	 */
	public java.util.List<DDMFieldAttribute> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFieldAttribute>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the ddm field attributes from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of ddm field attributes.
	 *
	 * @return the number of ddm field attributes
	 */
	public int countAll();

}