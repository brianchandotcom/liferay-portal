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

package com.liferay.portal.workflow.kaleo.designer.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.workflow.kaleo.designer.exception.NoSuchKaleoDraftDefinitionException;
import com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition;

/**
 * The persistence interface for the kaleo draft definition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Lundgren
 * @see com.liferay.portal.workflow.kaleo.designer.service.persistence.impl.KaleoDraftDefinitionPersistenceImpl
 * @see KaleoDraftDefinitionUtil
 * @generated
 */
@ProviderType
public interface KaleoDraftDefinitionPersistence extends BasePersistence<KaleoDraftDefinition> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link KaleoDraftDefinitionUtil} to access the kaleo draft definition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the kaleo draft definitions where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching kaleo draft definitions
	*/
	public java.util.List<KaleoDraftDefinition> findByCompanyId(long companyId);

	/**
	* Returns a range of all the kaleo draft definitions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDraftDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of kaleo draft definitions
	* @param end the upper bound of the range of kaleo draft definitions (not inclusive)
	* @return the range of matching kaleo draft definitions
	*/
	public java.util.List<KaleoDraftDefinition> findByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the kaleo draft definitions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDraftDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of kaleo draft definitions
	* @param end the upper bound of the range of kaleo draft definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching kaleo draft definitions
	*/
	public java.util.List<KaleoDraftDefinition> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDraftDefinition> orderByComparator);

	/**
	* Returns an ordered range of all the kaleo draft definitions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDraftDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of kaleo draft definitions
	* @param end the upper bound of the range of kaleo draft definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching kaleo draft definitions
	*/
	public java.util.List<KaleoDraftDefinition> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDraftDefinition> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first kaleo draft definition in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo draft definition
	* @throws NoSuchKaleoDraftDefinitionException if a matching kaleo draft definition could not be found
	*/
	public KaleoDraftDefinition findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDraftDefinition> orderByComparator)
		throws NoSuchKaleoDraftDefinitionException;

	/**
	* Returns the first kaleo draft definition in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo draft definition, or <code>null</code> if a matching kaleo draft definition could not be found
	*/
	public KaleoDraftDefinition fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDraftDefinition> orderByComparator);

	/**
	* Returns the last kaleo draft definition in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo draft definition
	* @throws NoSuchKaleoDraftDefinitionException if a matching kaleo draft definition could not be found
	*/
	public KaleoDraftDefinition findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDraftDefinition> orderByComparator)
		throws NoSuchKaleoDraftDefinitionException;

	/**
	* Returns the last kaleo draft definition in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo draft definition, or <code>null</code> if a matching kaleo draft definition could not be found
	*/
	public KaleoDraftDefinition fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDraftDefinition> orderByComparator);

	/**
	* Returns the kaleo draft definitions before and after the current kaleo draft definition in the ordered set where companyId = &#63;.
	*
	* @param kaleoDraftDefinitionId the primary key of the current kaleo draft definition
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next kaleo draft definition
	* @throws NoSuchKaleoDraftDefinitionException if a kaleo draft definition with the primary key could not be found
	*/
	public KaleoDraftDefinition[] findByCompanyId_PrevAndNext(
		long kaleoDraftDefinitionId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDraftDefinition> orderByComparator)
		throws NoSuchKaleoDraftDefinitionException;

	/**
	* Removes all the kaleo draft definitions where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of kaleo draft definitions where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching kaleo draft definitions
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the kaleo draft definitions where companyId = &#63; and name = &#63; and version = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @return the matching kaleo draft definitions
	*/
	public java.util.List<KaleoDraftDefinition> findByC_N_V(long companyId,
		java.lang.String name, int version);

	/**
	* Returns a range of all the kaleo draft definitions where companyId = &#63; and name = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDraftDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param start the lower bound of the range of kaleo draft definitions
	* @param end the upper bound of the range of kaleo draft definitions (not inclusive)
	* @return the range of matching kaleo draft definitions
	*/
	public java.util.List<KaleoDraftDefinition> findByC_N_V(long companyId,
		java.lang.String name, int version, int start, int end);

	/**
	* Returns an ordered range of all the kaleo draft definitions where companyId = &#63; and name = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDraftDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param start the lower bound of the range of kaleo draft definitions
	* @param end the upper bound of the range of kaleo draft definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching kaleo draft definitions
	*/
	public java.util.List<KaleoDraftDefinition> findByC_N_V(long companyId,
		java.lang.String name, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDraftDefinition> orderByComparator);

	/**
	* Returns an ordered range of all the kaleo draft definitions where companyId = &#63; and name = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDraftDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param start the lower bound of the range of kaleo draft definitions
	* @param end the upper bound of the range of kaleo draft definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching kaleo draft definitions
	*/
	public java.util.List<KaleoDraftDefinition> findByC_N_V(long companyId,
		java.lang.String name, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDraftDefinition> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first kaleo draft definition in the ordered set where companyId = &#63; and name = &#63; and version = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo draft definition
	* @throws NoSuchKaleoDraftDefinitionException if a matching kaleo draft definition could not be found
	*/
	public KaleoDraftDefinition findByC_N_V_First(long companyId,
		java.lang.String name, int version,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDraftDefinition> orderByComparator)
		throws NoSuchKaleoDraftDefinitionException;

	/**
	* Returns the first kaleo draft definition in the ordered set where companyId = &#63; and name = &#63; and version = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo draft definition, or <code>null</code> if a matching kaleo draft definition could not be found
	*/
	public KaleoDraftDefinition fetchByC_N_V_First(long companyId,
		java.lang.String name, int version,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDraftDefinition> orderByComparator);

	/**
	* Returns the last kaleo draft definition in the ordered set where companyId = &#63; and name = &#63; and version = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo draft definition
	* @throws NoSuchKaleoDraftDefinitionException if a matching kaleo draft definition could not be found
	*/
	public KaleoDraftDefinition findByC_N_V_Last(long companyId,
		java.lang.String name, int version,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDraftDefinition> orderByComparator)
		throws NoSuchKaleoDraftDefinitionException;

	/**
	* Returns the last kaleo draft definition in the ordered set where companyId = &#63; and name = &#63; and version = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo draft definition, or <code>null</code> if a matching kaleo draft definition could not be found
	*/
	public KaleoDraftDefinition fetchByC_N_V_Last(long companyId,
		java.lang.String name, int version,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDraftDefinition> orderByComparator);

	/**
	* Returns the kaleo draft definitions before and after the current kaleo draft definition in the ordered set where companyId = &#63; and name = &#63; and version = &#63;.
	*
	* @param kaleoDraftDefinitionId the primary key of the current kaleo draft definition
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next kaleo draft definition
	* @throws NoSuchKaleoDraftDefinitionException if a kaleo draft definition with the primary key could not be found
	*/
	public KaleoDraftDefinition[] findByC_N_V_PrevAndNext(
		long kaleoDraftDefinitionId, long companyId, java.lang.String name,
		int version,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDraftDefinition> orderByComparator)
		throws NoSuchKaleoDraftDefinitionException;

	/**
	* Removes all the kaleo draft definitions where companyId = &#63; and name = &#63; and version = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	*/
	public void removeByC_N_V(long companyId, java.lang.String name, int version);

	/**
	* Returns the number of kaleo draft definitions where companyId = &#63; and name = &#63; and version = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @return the number of matching kaleo draft definitions
	*/
	public int countByC_N_V(long companyId, java.lang.String name, int version);

	/**
	* Returns the kaleo draft definition where companyId = &#63; and name = &#63; and version = &#63; and draftVersion = &#63; or throws a {@link NoSuchKaleoDraftDefinitionException} if it could not be found.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param draftVersion the draft version
	* @return the matching kaleo draft definition
	* @throws NoSuchKaleoDraftDefinitionException if a matching kaleo draft definition could not be found
	*/
	public KaleoDraftDefinition findByC_N_V_D(long companyId,
		java.lang.String name, int version, int draftVersion)
		throws NoSuchKaleoDraftDefinitionException;

	/**
	* Returns the kaleo draft definition where companyId = &#63; and name = &#63; and version = &#63; and draftVersion = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param draftVersion the draft version
	* @return the matching kaleo draft definition, or <code>null</code> if a matching kaleo draft definition could not be found
	*/
	public KaleoDraftDefinition fetchByC_N_V_D(long companyId,
		java.lang.String name, int version, int draftVersion);

	/**
	* Returns the kaleo draft definition where companyId = &#63; and name = &#63; and version = &#63; and draftVersion = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param draftVersion the draft version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching kaleo draft definition, or <code>null</code> if a matching kaleo draft definition could not be found
	*/
	public KaleoDraftDefinition fetchByC_N_V_D(long companyId,
		java.lang.String name, int version, int draftVersion,
		boolean retrieveFromCache);

	/**
	* Removes the kaleo draft definition where companyId = &#63; and name = &#63; and version = &#63; and draftVersion = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param draftVersion the draft version
	* @return the kaleo draft definition that was removed
	*/
	public KaleoDraftDefinition removeByC_N_V_D(long companyId,
		java.lang.String name, int version, int draftVersion)
		throws NoSuchKaleoDraftDefinitionException;

	/**
	* Returns the number of kaleo draft definitions where companyId = &#63; and name = &#63; and version = &#63; and draftVersion = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param draftVersion the draft version
	* @return the number of matching kaleo draft definitions
	*/
	public int countByC_N_V_D(long companyId, java.lang.String name,
		int version, int draftVersion);

	/**
	* Caches the kaleo draft definition in the entity cache if it is enabled.
	*
	* @param kaleoDraftDefinition the kaleo draft definition
	*/
	public void cacheResult(KaleoDraftDefinition kaleoDraftDefinition);

	/**
	* Caches the kaleo draft definitions in the entity cache if it is enabled.
	*
	* @param kaleoDraftDefinitions the kaleo draft definitions
	*/
	public void cacheResult(
		java.util.List<KaleoDraftDefinition> kaleoDraftDefinitions);

	/**
	* Creates a new kaleo draft definition with the primary key. Does not add the kaleo draft definition to the database.
	*
	* @param kaleoDraftDefinitionId the primary key for the new kaleo draft definition
	* @return the new kaleo draft definition
	*/
	public KaleoDraftDefinition create(long kaleoDraftDefinitionId);

	/**
	* Removes the kaleo draft definition with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param kaleoDraftDefinitionId the primary key of the kaleo draft definition
	* @return the kaleo draft definition that was removed
	* @throws NoSuchKaleoDraftDefinitionException if a kaleo draft definition with the primary key could not be found
	*/
	public KaleoDraftDefinition remove(long kaleoDraftDefinitionId)
		throws NoSuchKaleoDraftDefinitionException;

	public KaleoDraftDefinition updateImpl(
		KaleoDraftDefinition kaleoDraftDefinition);

	/**
	* Returns the kaleo draft definition with the primary key or throws a {@link NoSuchKaleoDraftDefinitionException} if it could not be found.
	*
	* @param kaleoDraftDefinitionId the primary key of the kaleo draft definition
	* @return the kaleo draft definition
	* @throws NoSuchKaleoDraftDefinitionException if a kaleo draft definition with the primary key could not be found
	*/
	public KaleoDraftDefinition findByPrimaryKey(long kaleoDraftDefinitionId)
		throws NoSuchKaleoDraftDefinitionException;

	/**
	* Returns the kaleo draft definition with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param kaleoDraftDefinitionId the primary key of the kaleo draft definition
	* @return the kaleo draft definition, or <code>null</code> if a kaleo draft definition with the primary key could not be found
	*/
	public KaleoDraftDefinition fetchByPrimaryKey(long kaleoDraftDefinitionId);

	@Override
	public java.util.Map<java.io.Serializable, KaleoDraftDefinition> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the kaleo draft definitions.
	*
	* @return the kaleo draft definitions
	*/
	public java.util.List<KaleoDraftDefinition> findAll();

	/**
	* Returns a range of all the kaleo draft definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDraftDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of kaleo draft definitions
	* @param end the upper bound of the range of kaleo draft definitions (not inclusive)
	* @return the range of kaleo draft definitions
	*/
	public java.util.List<KaleoDraftDefinition> findAll(int start, int end);

	/**
	* Returns an ordered range of all the kaleo draft definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDraftDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of kaleo draft definitions
	* @param end the upper bound of the range of kaleo draft definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of kaleo draft definitions
	*/
	public java.util.List<KaleoDraftDefinition> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDraftDefinition> orderByComparator);

	/**
	* Returns an ordered range of all the kaleo draft definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDraftDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of kaleo draft definitions
	* @param end the upper bound of the range of kaleo draft definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of kaleo draft definitions
	*/
	public java.util.List<KaleoDraftDefinition> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoDraftDefinition> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the kaleo draft definitions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of kaleo draft definitions.
	*
	* @return the number of kaleo draft definitions
	*/
	public int countAll();
}