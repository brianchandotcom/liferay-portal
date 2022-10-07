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

package com.liferay.list.type.service.persistence;

import com.liferay.list.type.exception.NoSuchListTypeDefinitionException;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the list type definition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see ListTypeDefinitionUtil
 * @generated
 */
@ProviderType
public interface ListTypeDefinitionPersistence
	extends BasePersistence<ListTypeDefinition> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ListTypeDefinitionUtil} to access the list type definition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the list type definitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching list type definitions
	 */
	public java.util.List<ListTypeDefinition> findByUuid(String uuid);

	/**
	 * Returns a range of all the list type definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @return the range of matching list type definitions
	 */
	public java.util.List<ListTypeDefinition> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the list type definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list type definitions
	 */
	public java.util.List<ListTypeDefinition> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeDefinition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the list type definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list type definitions
	 */
	public java.util.List<ListTypeDefinition> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeDefinition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first list type definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type definition
	 * @throws NoSuchListTypeDefinitionException if a matching list type definition could not be found
	 */
	public ListTypeDefinition findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ListTypeDefinition>
				orderByComparator)
		throws NoSuchListTypeDefinitionException;

	/**
	 * Returns the first list type definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type definition, or <code>null</code> if a matching list type definition could not be found
	 */
	public ListTypeDefinition fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeDefinition>
			orderByComparator);

	/**
	 * Returns the last list type definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type definition
	 * @throws NoSuchListTypeDefinitionException if a matching list type definition could not be found
	 */
	public ListTypeDefinition findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ListTypeDefinition>
				orderByComparator)
		throws NoSuchListTypeDefinitionException;

	/**
	 * Returns the last list type definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type definition, or <code>null</code> if a matching list type definition could not be found
	 */
	public ListTypeDefinition fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeDefinition>
			orderByComparator);

	/**
	 * Returns the list type definitions before and after the current list type definition in the ordered set where uuid = &#63;.
	 *
	 * @param listTypeDefinitionId the primary key of the current list type definition
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type definition
	 * @throws NoSuchListTypeDefinitionException if a list type definition with the primary key could not be found
	 */
	public ListTypeDefinition[] findByUuid_PrevAndNext(
			long listTypeDefinitionId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ListTypeDefinition>
				orderByComparator)
		throws NoSuchListTypeDefinitionException;

	/**
	 * Returns all the list type definitions that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching list type definitions that the user has permission to view
	 */
	public java.util.List<ListTypeDefinition> filterFindByUuid(String uuid);

	/**
	 * Returns a range of all the list type definitions that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @return the range of matching list type definitions that the user has permission to view
	 */
	public java.util.List<ListTypeDefinition> filterFindByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the list type definitions that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list type definitions that the user has permission to view
	 */
	public java.util.List<ListTypeDefinition> filterFindByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeDefinition>
			orderByComparator);

	/**
	 * Returns the list type definitions before and after the current list type definition in the ordered set of list type definitions that the user has permission to view where uuid = &#63;.
	 *
	 * @param listTypeDefinitionId the primary key of the current list type definition
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type definition
	 * @throws NoSuchListTypeDefinitionException if a list type definition with the primary key could not be found
	 */
	public ListTypeDefinition[] filterFindByUuid_PrevAndNext(
			long listTypeDefinitionId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ListTypeDefinition>
				orderByComparator)
		throws NoSuchListTypeDefinitionException;

	/**
	 * Removes all the list type definitions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of list type definitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching list type definitions
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the number of list type definitions that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching list type definitions that the user has permission to view
	 */
	public int filterCountByUuid(String uuid);

	/**
	 * Returns all the list type definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching list type definitions
	 */
	public java.util.List<ListTypeDefinition> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the list type definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @return the range of matching list type definitions
	 */
	public java.util.List<ListTypeDefinition> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the list type definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list type definitions
	 */
	public java.util.List<ListTypeDefinition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeDefinition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the list type definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list type definitions
	 */
	public java.util.List<ListTypeDefinition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeDefinition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first list type definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type definition
	 * @throws NoSuchListTypeDefinitionException if a matching list type definition could not be found
	 */
	public ListTypeDefinition findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ListTypeDefinition>
				orderByComparator)
		throws NoSuchListTypeDefinitionException;

	/**
	 * Returns the first list type definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type definition, or <code>null</code> if a matching list type definition could not be found
	 */
	public ListTypeDefinition fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeDefinition>
			orderByComparator);

	/**
	 * Returns the last list type definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type definition
	 * @throws NoSuchListTypeDefinitionException if a matching list type definition could not be found
	 */
	public ListTypeDefinition findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ListTypeDefinition>
				orderByComparator)
		throws NoSuchListTypeDefinitionException;

	/**
	 * Returns the last list type definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type definition, or <code>null</code> if a matching list type definition could not be found
	 */
	public ListTypeDefinition fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeDefinition>
			orderByComparator);

	/**
	 * Returns the list type definitions before and after the current list type definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param listTypeDefinitionId the primary key of the current list type definition
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type definition
	 * @throws NoSuchListTypeDefinitionException if a list type definition with the primary key could not be found
	 */
	public ListTypeDefinition[] findByUuid_C_PrevAndNext(
			long listTypeDefinitionId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ListTypeDefinition>
				orderByComparator)
		throws NoSuchListTypeDefinitionException;

	/**
	 * Returns all the list type definitions that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching list type definitions that the user has permission to view
	 */
	public java.util.List<ListTypeDefinition> filterFindByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the list type definitions that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @return the range of matching list type definitions that the user has permission to view
	 */
	public java.util.List<ListTypeDefinition> filterFindByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the list type definitions that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list type definitions that the user has permission to view
	 */
	public java.util.List<ListTypeDefinition> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeDefinition>
			orderByComparator);

	/**
	 * Returns the list type definitions before and after the current list type definition in the ordered set of list type definitions that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param listTypeDefinitionId the primary key of the current list type definition
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type definition
	 * @throws NoSuchListTypeDefinitionException if a list type definition with the primary key could not be found
	 */
	public ListTypeDefinition[] filterFindByUuid_C_PrevAndNext(
			long listTypeDefinitionId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ListTypeDefinition>
				orderByComparator)
		throws NoSuchListTypeDefinitionException;

	/**
	 * Removes all the list type definitions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of list type definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching list type definitions
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of list type definitions that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching list type definitions that the user has permission to view
	 */
	public int filterCountByUuid_C(String uuid, long companyId);

	/**
	 * Returns the list type definition where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchListTypeDefinitionException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching list type definition
	 * @throws NoSuchListTypeDefinitionException if a matching list type definition could not be found
	 */
	public ListTypeDefinition findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchListTypeDefinitionException;

	/**
	 * Returns the list type definition where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching list type definition, or <code>null</code> if a matching list type definition could not be found
	 */
	public ListTypeDefinition fetchByC_ERC(
		long companyId, String externalReferenceCode);

	/**
	 * Returns the list type definition where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching list type definition, or <code>null</code> if a matching list type definition could not be found
	 */
	public ListTypeDefinition fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the list type definition where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the list type definition that was removed
	 */
	public ListTypeDefinition removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchListTypeDefinitionException;

	/**
	 * Returns the number of list type definitions where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching list type definitions
	 */
	public int countByC_ERC(long companyId, String externalReferenceCode);

	/**
	 * Caches the list type definition in the entity cache if it is enabled.
	 *
	 * @param listTypeDefinition the list type definition
	 */
	public void cacheResult(ListTypeDefinition listTypeDefinition);

	/**
	 * Caches the list type definitions in the entity cache if it is enabled.
	 *
	 * @param listTypeDefinitions the list type definitions
	 */
	public void cacheResult(
		java.util.List<ListTypeDefinition> listTypeDefinitions);

	/**
	 * Creates a new list type definition with the primary key. Does not add the list type definition to the database.
	 *
	 * @param listTypeDefinitionId the primary key for the new list type definition
	 * @return the new list type definition
	 */
	public ListTypeDefinition create(long listTypeDefinitionId);

	/**
	 * Removes the list type definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param listTypeDefinitionId the primary key of the list type definition
	 * @return the list type definition that was removed
	 * @throws NoSuchListTypeDefinitionException if a list type definition with the primary key could not be found
	 */
	public ListTypeDefinition remove(long listTypeDefinitionId)
		throws NoSuchListTypeDefinitionException;

	public ListTypeDefinition updateImpl(ListTypeDefinition listTypeDefinition);

	/**
	 * Returns the list type definition with the primary key or throws a <code>NoSuchListTypeDefinitionException</code> if it could not be found.
	 *
	 * @param listTypeDefinitionId the primary key of the list type definition
	 * @return the list type definition
	 * @throws NoSuchListTypeDefinitionException if a list type definition with the primary key could not be found
	 */
	public ListTypeDefinition findByPrimaryKey(long listTypeDefinitionId)
		throws NoSuchListTypeDefinitionException;

	/**
	 * Returns the list type definition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param listTypeDefinitionId the primary key of the list type definition
	 * @return the list type definition, or <code>null</code> if a list type definition with the primary key could not be found
	 */
	public ListTypeDefinition fetchByPrimaryKey(long listTypeDefinitionId);

	/**
	 * Returns all the list type definitions.
	 *
	 * @return the list type definitions
	 */
	public java.util.List<ListTypeDefinition> findAll();

	/**
	 * Returns a range of all the list type definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @return the range of list type definitions
	 */
	public java.util.List<ListTypeDefinition> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the list type definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of list type definitions
	 */
	public java.util.List<ListTypeDefinition> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeDefinition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the list type definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of list type definitions
	 */
	public java.util.List<ListTypeDefinition> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeDefinition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the list type definitions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of list type definitions.
	 *
	 * @return the number of list type definitions
	 */
	public int countAll();

}