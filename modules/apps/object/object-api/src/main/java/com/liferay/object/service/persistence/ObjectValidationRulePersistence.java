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

package com.liferay.object.service.persistence;

import com.liferay.object.exception.NoSuchObjectValidationRuleException;
import com.liferay.object.model.ObjectValidationRule;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the object validation rule service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectValidationRuleUtil
 * @generated
 */
@ProviderType
public interface ObjectValidationRulePersistence
	extends BasePersistence<ObjectValidationRule> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ObjectValidationRuleUtil} to access the object validation rule persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the object validation rules where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object validation rules
	 */
	public java.util.List<ObjectValidationRule> findByUuid(String uuid);

	/**
	 * Returns a range of all the object validation rules where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @return the range of matching object validation rules
	 */
	public java.util.List<ObjectValidationRule> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the object validation rules where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rules
	 */
	public java.util.List<ObjectValidationRule> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectValidationRule>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object validation rules where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rules
	 */
	public java.util.List<ObjectValidationRule> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectValidationRule>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object validation rule in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	public ObjectValidationRule findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException;

	/**
	 * Returns the first object validation rule in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	public ObjectValidationRule fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectValidationRule>
			orderByComparator);

	/**
	 * Returns the last object validation rule in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	public ObjectValidationRule findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException;

	/**
	 * Returns the last object validation rule in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	public ObjectValidationRule fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectValidationRule>
			orderByComparator);

	/**
	 * Returns the object validation rules before and after the current object validation rule in the ordered set where uuid = &#63;.
	 *
	 * @param objectValidationRuleId the primary key of the current object validation rule
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule
	 * @throws NoSuchObjectValidationRuleException if a object validation rule with the primary key could not be found
	 */
	public ObjectValidationRule[] findByUuid_PrevAndNext(
			long objectValidationRuleId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException;

	/**
	 * Removes all the object validation rules where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of object validation rules where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object validation rules
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the object validation rules where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object validation rules
	 */
	public java.util.List<ObjectValidationRule> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the object validation rules where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @return the range of matching object validation rules
	 */
	public java.util.List<ObjectValidationRule> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the object validation rules where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rules
	 */
	public java.util.List<ObjectValidationRule> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectValidationRule>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object validation rules where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rules
	 */
	public java.util.List<ObjectValidationRule> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectValidationRule>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object validation rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	public ObjectValidationRule findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException;

	/**
	 * Returns the first object validation rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	public ObjectValidationRule fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectValidationRule>
			orderByComparator);

	/**
	 * Returns the last object validation rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	public ObjectValidationRule findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException;

	/**
	 * Returns the last object validation rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	public ObjectValidationRule fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectValidationRule>
			orderByComparator);

	/**
	 * Returns the object validation rules before and after the current object validation rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectValidationRuleId the primary key of the current object validation rule
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule
	 * @throws NoSuchObjectValidationRuleException if a object validation rule with the primary key could not be found
	 */
	public ObjectValidationRule[] findByUuid_C_PrevAndNext(
			long objectValidationRuleId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException;

	/**
	 * Removes all the object validation rules where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object validation rules where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object validation rules
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the object validation rules where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @return the matching object validation rules
	 */
	public java.util.List<ObjectValidationRule> findByObjectDefinitionId(
		long objectDefinitionId);

	/**
	 * Returns a range of all the object validation rules where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @return the range of matching object validation rules
	 */
	public java.util.List<ObjectValidationRule> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end);

	/**
	 * Returns an ordered range of all the object validation rules where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rules
	 */
	public java.util.List<ObjectValidationRule> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectValidationRule>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object validation rules where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rules
	 */
	public java.util.List<ObjectValidationRule> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectValidationRule>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object validation rule in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	public ObjectValidationRule findByObjectDefinitionId_First(
			long objectDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException;

	/**
	 * Returns the first object validation rule in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	public ObjectValidationRule fetchByObjectDefinitionId_First(
		long objectDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectValidationRule>
			orderByComparator);

	/**
	 * Returns the last object validation rule in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	public ObjectValidationRule findByObjectDefinitionId_Last(
			long objectDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException;

	/**
	 * Returns the last object validation rule in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	public ObjectValidationRule fetchByObjectDefinitionId_Last(
		long objectDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectValidationRule>
			orderByComparator);

	/**
	 * Returns the object validation rules before and after the current object validation rule in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectValidationRuleId the primary key of the current object validation rule
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule
	 * @throws NoSuchObjectValidationRuleException if a object validation rule with the primary key could not be found
	 */
	public ObjectValidationRule[] findByObjectDefinitionId_PrevAndNext(
			long objectValidationRuleId, long objectDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException;

	/**
	 * Removes all the object validation rules where objectDefinitionId = &#63; from the database.
	 *
	 * @param objectDefinitionId the object definition ID
	 */
	public void removeByObjectDefinitionId(long objectDefinitionId);

	/**
	 * Returns the number of object validation rules where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @return the number of matching object validation rules
	 */
	public int countByObjectDefinitionId(long objectDefinitionId);

	/**
	 * Returns all the object validation rules where objectDefinitionId = &#63; and active = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @return the matching object validation rules
	 */
	public java.util.List<ObjectValidationRule> findByODI_A(
		long objectDefinitionId, boolean active);

	/**
	 * Returns a range of all the object validation rules where objectDefinitionId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @return the range of matching object validation rules
	 */
	public java.util.List<ObjectValidationRule> findByODI_A(
		long objectDefinitionId, boolean active, int start, int end);

	/**
	 * Returns an ordered range of all the object validation rules where objectDefinitionId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rules
	 */
	public java.util.List<ObjectValidationRule> findByODI_A(
		long objectDefinitionId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectValidationRule>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object validation rules where objectDefinitionId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rules
	 */
	public java.util.List<ObjectValidationRule> findByODI_A(
		long objectDefinitionId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectValidationRule>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object validation rule in the ordered set where objectDefinitionId = &#63; and active = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	public ObjectValidationRule findByODI_A_First(
			long objectDefinitionId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException;

	/**
	 * Returns the first object validation rule in the ordered set where objectDefinitionId = &#63; and active = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	public ObjectValidationRule fetchByODI_A_First(
		long objectDefinitionId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectValidationRule>
			orderByComparator);

	/**
	 * Returns the last object validation rule in the ordered set where objectDefinitionId = &#63; and active = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	public ObjectValidationRule findByODI_A_Last(
			long objectDefinitionId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException;

	/**
	 * Returns the last object validation rule in the ordered set where objectDefinitionId = &#63; and active = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	public ObjectValidationRule fetchByODI_A_Last(
		long objectDefinitionId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectValidationRule>
			orderByComparator);

	/**
	 * Returns the object validation rules before and after the current object validation rule in the ordered set where objectDefinitionId = &#63; and active = &#63;.
	 *
	 * @param objectValidationRuleId the primary key of the current object validation rule
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule
	 * @throws NoSuchObjectValidationRuleException if a object validation rule with the primary key could not be found
	 */
	public ObjectValidationRule[] findByODI_A_PrevAndNext(
			long objectValidationRuleId, long objectDefinitionId,
			boolean active,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException;

	/**
	 * Removes all the object validation rules where objectDefinitionId = &#63; and active = &#63; from the database.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 */
	public void removeByODI_A(long objectDefinitionId, boolean active);

	/**
	 * Returns the number of object validation rules where objectDefinitionId = &#63; and active = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @return the number of matching object validation rules
	 */
	public int countByODI_A(long objectDefinitionId, boolean active);

	/**
	 * Returns all the object validation rules where objectDefinitionId = &#63; and outputType = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param outputType the output type
	 * @return the matching object validation rules
	 */
	public java.util.List<ObjectValidationRule> findByODI_O(
		long objectDefinitionId, String outputType);

	/**
	 * Returns a range of all the object validation rules where objectDefinitionId = &#63; and outputType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param outputType the output type
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @return the range of matching object validation rules
	 */
	public java.util.List<ObjectValidationRule> findByODI_O(
		long objectDefinitionId, String outputType, int start, int end);

	/**
	 * Returns an ordered range of all the object validation rules where objectDefinitionId = &#63; and outputType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param outputType the output type
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rules
	 */
	public java.util.List<ObjectValidationRule> findByODI_O(
		long objectDefinitionId, String outputType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectValidationRule>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object validation rules where objectDefinitionId = &#63; and outputType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param outputType the output type
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rules
	 */
	public java.util.List<ObjectValidationRule> findByODI_O(
		long objectDefinitionId, String outputType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectValidationRule>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object validation rule in the ordered set where objectDefinitionId = &#63; and outputType = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param outputType the output type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	public ObjectValidationRule findByODI_O_First(
			long objectDefinitionId, String outputType,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException;

	/**
	 * Returns the first object validation rule in the ordered set where objectDefinitionId = &#63; and outputType = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param outputType the output type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	public ObjectValidationRule fetchByODI_O_First(
		long objectDefinitionId, String outputType,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectValidationRule>
			orderByComparator);

	/**
	 * Returns the last object validation rule in the ordered set where objectDefinitionId = &#63; and outputType = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param outputType the output type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule
	 * @throws NoSuchObjectValidationRuleException if a matching object validation rule could not be found
	 */
	public ObjectValidationRule findByODI_O_Last(
			long objectDefinitionId, String outputType,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException;

	/**
	 * Returns the last object validation rule in the ordered set where objectDefinitionId = &#63; and outputType = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param outputType the output type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule, or <code>null</code> if a matching object validation rule could not be found
	 */
	public ObjectValidationRule fetchByODI_O_Last(
		long objectDefinitionId, String outputType,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectValidationRule>
			orderByComparator);

	/**
	 * Returns the object validation rules before and after the current object validation rule in the ordered set where objectDefinitionId = &#63; and outputType = &#63;.
	 *
	 * @param objectValidationRuleId the primary key of the current object validation rule
	 * @param objectDefinitionId the object definition ID
	 * @param outputType the output type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule
	 * @throws NoSuchObjectValidationRuleException if a object validation rule with the primary key could not be found
	 */
	public ObjectValidationRule[] findByODI_O_PrevAndNext(
			long objectValidationRuleId, long objectDefinitionId,
			String outputType,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRule> orderByComparator)
		throws NoSuchObjectValidationRuleException;

	/**
	 * Removes all the object validation rules where objectDefinitionId = &#63; and outputType = &#63; from the database.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param outputType the output type
	 */
	public void removeByODI_O(long objectDefinitionId, String outputType);

	/**
	 * Returns the number of object validation rules where objectDefinitionId = &#63; and outputType = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param outputType the output type
	 * @return the number of matching object validation rules
	 */
	public int countByODI_O(long objectDefinitionId, String outputType);

	/**
	 * Caches the object validation rule in the entity cache if it is enabled.
	 *
	 * @param objectValidationRule the object validation rule
	 */
	public void cacheResult(ObjectValidationRule objectValidationRule);

	/**
	 * Caches the object validation rules in the entity cache if it is enabled.
	 *
	 * @param objectValidationRules the object validation rules
	 */
	public void cacheResult(
		java.util.List<ObjectValidationRule> objectValidationRules);

	/**
	 * Creates a new object validation rule with the primary key. Does not add the object validation rule to the database.
	 *
	 * @param objectValidationRuleId the primary key for the new object validation rule
	 * @return the new object validation rule
	 */
	public ObjectValidationRule create(long objectValidationRuleId);

	/**
	 * Removes the object validation rule with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectValidationRuleId the primary key of the object validation rule
	 * @return the object validation rule that was removed
	 * @throws NoSuchObjectValidationRuleException if a object validation rule with the primary key could not be found
	 */
	public ObjectValidationRule remove(long objectValidationRuleId)
		throws NoSuchObjectValidationRuleException;

	public ObjectValidationRule updateImpl(
		ObjectValidationRule objectValidationRule);

	/**
	 * Returns the object validation rule with the primary key or throws a <code>NoSuchObjectValidationRuleException</code> if it could not be found.
	 *
	 * @param objectValidationRuleId the primary key of the object validation rule
	 * @return the object validation rule
	 * @throws NoSuchObjectValidationRuleException if a object validation rule with the primary key could not be found
	 */
	public ObjectValidationRule findByPrimaryKey(long objectValidationRuleId)
		throws NoSuchObjectValidationRuleException;

	/**
	 * Returns the object validation rule with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectValidationRuleId the primary key of the object validation rule
	 * @return the object validation rule, or <code>null</code> if a object validation rule with the primary key could not be found
	 */
	public ObjectValidationRule fetchByPrimaryKey(long objectValidationRuleId);

	/**
	 * Returns all the object validation rules.
	 *
	 * @return the object validation rules
	 */
	public java.util.List<ObjectValidationRule> findAll();

	/**
	 * Returns a range of all the object validation rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @return the range of object validation rules
	 */
	public java.util.List<ObjectValidationRule> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the object validation rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object validation rules
	 */
	public java.util.List<ObjectValidationRule> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectValidationRule>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object validation rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object validation rules
	 * @param end the upper bound of the range of object validation rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object validation rules
	 */
	public java.util.List<ObjectValidationRule> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectValidationRule>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the object validation rules from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of object validation rules.
	 *
	 * @return the number of object validation rules
	 */
	public int countAll();

}