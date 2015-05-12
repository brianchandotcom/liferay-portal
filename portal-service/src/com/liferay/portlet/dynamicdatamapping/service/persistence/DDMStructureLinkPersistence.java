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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink;

/**
 * The persistence interface for the d d m structure link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.dynamicdatamapping.service.persistence.impl.DDMStructureLinkPersistenceImpl
 * @see DDMStructureLinkUtil
 * @generated
 */
@ProviderType
public interface DDMStructureLinkPersistence extends BasePersistence<DDMStructureLink> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMStructureLinkUtil} to access the d d m structure link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the d d m structure links where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the matching d d m structure links
	*/
	public java.util.List<DDMStructureLink> findByStructureId(long structureId);

	/**
	* Returns a range of all the d d m structure links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @return the range of matching d d m structure links
	*/
	public java.util.List<DDMStructureLink> findByStructureId(
		long structureId, int start, int end);

	/**
	* Returns an ordered range of all the d d m structure links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structure links
	*/
	public java.util.List<DDMStructureLink> findByStructureId(
		long structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink> orderByComparator);

	/**
	* Returns the first d d m structure link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure link
	* @throws NoSuchStructureLinkException if a matching d d m structure link could not be found
	*/
	public DDMStructureLink findByStructureId_First(long structureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;

	/**
	* Returns the first d d m structure link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	*/
	public DDMStructureLink fetchByStructureId_First(long structureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink> orderByComparator);

	/**
	* Returns the last d d m structure link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure link
	* @throws NoSuchStructureLinkException if a matching d d m structure link could not be found
	*/
	public DDMStructureLink findByStructureId_Last(long structureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;

	/**
	* Returns the last d d m structure link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	*/
	public DDMStructureLink fetchByStructureId_Last(long structureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink> orderByComparator);

	/**
	* Returns the d d m structure links before and after the current d d m structure link in the ordered set where structureId = &#63;.
	*
	* @param structureLinkId the primary key of the current d d m structure link
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure link
	* @throws NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	*/
	public DDMStructureLink[] findByStructureId_PrevAndNext(
		long structureLinkId, long structureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;

	/**
	* Removes all the d d m structure links where structureId = &#63; from the database.
	*
	* @param structureId the structure ID
	*/
	public void removeByStructureId(long structureId);

	/**
	* Returns the number of d d m structure links where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the number of matching d d m structure links
	*/
	public int countByStructureId(long structureId);

	/**
	* Returns all the d d m structure links where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching d d m structure links
	*/
	public java.util.List<DDMStructureLink> findByC_C(long classNameId,
		long classPK);

	/**
	* Returns a range of all the d d m structure links where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @return the range of matching d d m structure links
	*/
	public java.util.List<DDMStructureLink> findByC_C(long classNameId,
		long classPK, int start, int end);

	/**
	* Returns an ordered range of all the d d m structure links where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structure links
	*/
	public java.util.List<DDMStructureLink> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink> orderByComparator);

	/**
	* Returns the first d d m structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure link
	* @throws NoSuchStructureLinkException if a matching d d m structure link could not be found
	*/
	public DDMStructureLink findByC_C_First(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;

	/**
	* Returns the first d d m structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	*/
	public DDMStructureLink fetchByC_C_First(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink> orderByComparator);

	/**
	* Returns the last d d m structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure link
	* @throws NoSuchStructureLinkException if a matching d d m structure link could not be found
	*/
	public DDMStructureLink findByC_C_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;

	/**
	* Returns the last d d m structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	*/
	public DDMStructureLink fetchByC_C_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink> orderByComparator);

	/**
	* Returns the d d m structure links before and after the current d d m structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param structureLinkId the primary key of the current d d m structure link
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure link
	* @throws NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	*/
	public DDMStructureLink[] findByC_C_PrevAndNext(long structureLinkId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;

	/**
	* Removes all the d d m structure links where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public void removeByC_C(long classNameId, long classPK);

	/**
	* Returns the number of d d m structure links where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching d d m structure links
	*/
	public int countByC_C(long classNameId, long classPK);

	/**
	* Returns the d d m structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; or throws a {@link NoSuchStructureLinkException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param structureId the structure ID
	* @return the matching d d m structure link
	* @throws NoSuchStructureLinkException if a matching d d m structure link could not be found
	*/
	public DDMStructureLink findByC_C_S(long classNameId, long classPK,
		long structureId)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;

	/**
	* Returns the d d m structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param structureId the structure ID
	* @return the matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	*/
	public DDMStructureLink fetchByC_C_S(long classNameId, long classPK,
		long structureId);

	/**
	* Returns the d d m structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param structureId the structure ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	*/
	public DDMStructureLink fetchByC_C_S(long classNameId, long classPK,
		long structureId, boolean retrieveFromCache);

	/**
	* Removes the d d m structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param structureId the structure ID
	* @return the d d m structure link that was removed
	*/
	public DDMStructureLink removeByC_C_S(long classNameId, long classPK,
		long structureId)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;

	/**
	* Returns the number of d d m structure links where classNameId = &#63; and classPK = &#63; and structureId = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param structureId the structure ID
	* @return the number of matching d d m structure links
	*/
	public int countByC_C_S(long classNameId, long classPK, long structureId);

	/**
	* Caches the d d m structure link in the entity cache if it is enabled.
	*
	* @param ddmStructureLink the d d m structure link
	*/
	public void cacheResult(DDMStructureLink ddmStructureLink);

	/**
	* Caches the d d m structure links in the entity cache if it is enabled.
	*
	* @param ddmStructureLinks the d d m structure links
	*/
	public void cacheResult(java.util.List<DDMStructureLink> ddmStructureLinks);

	/**
	* Creates a new d d m structure link with the primary key. Does not add the d d m structure link to the database.
	*
	* @param structureLinkId the primary key for the new d d m structure link
	* @return the new d d m structure link
	*/
	public DDMStructureLink create(long structureLinkId);

	/**
	* Removes the d d m structure link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureLinkId the primary key of the d d m structure link
	* @return the d d m structure link that was removed
	* @throws NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	*/
	public DDMStructureLink remove(long structureLinkId)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;

	public DDMStructureLink updateImpl(DDMStructureLink ddmStructureLink);

	/**
	* Returns the d d m structure link with the primary key or throws a {@link NoSuchStructureLinkException} if it could not be found.
	*
	* @param structureLinkId the primary key of the d d m structure link
	* @return the d d m structure link
	* @throws NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	*/
	public DDMStructureLink findByPrimaryKey(long structureLinkId)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;

	/**
	* Returns the d d m structure link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param structureLinkId the primary key of the d d m structure link
	* @return the d d m structure link, or <code>null</code> if a d d m structure link with the primary key could not be found
	*/
	public DDMStructureLink fetchByPrimaryKey(long structureLinkId);

	@Override
	public java.util.Map<java.io.Serializable, DDMStructureLink> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the d d m structure links.
	*
	* @return the d d m structure links
	*/
	public java.util.List<DDMStructureLink> findAll();

	/**
	* Returns a range of all the d d m structure links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @return the range of d d m structure links
	*/
	public java.util.List<DDMStructureLink> findAll(int start, int end);

	/**
	* Returns an ordered range of all the d d m structure links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m structure links
	*/
	public java.util.List<DDMStructureLink> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink> orderByComparator);

	/**
	* Removes all the d d m structure links from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of d d m structure links.
	*
	* @return the number of d d m structure links
	*/
	public int countAll();
}