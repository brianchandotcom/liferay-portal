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

import com.liferay.dynamic.data.mapping.exception.NoSuchStructureLinkException;
import com.liferay.dynamic.data.mapping.model.DDMStructureLink;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the ddm structure link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLinkUtil
 * @generated
 */
@ProviderType
public interface DDMStructureLinkPersistence
	extends BasePersistence<DDMStructureLink> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMStructureLinkUtil} to access the ddm structure link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the ddm structure links where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the matching ddm structure links
	 */
	public java.util.List<DDMStructureLink> findByClassNameId(long classNameId);

	/**
	 * Returns a range of all the ddm structure links where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @return the range of matching ddm structure links
	 */
	public java.util.List<DDMStructureLink> findByClassNameId(
		long classNameId, int start, int end);

	/**
	 * Returns an ordered range of all the ddm structure links where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure links
	 */
	public java.util.List<DDMStructureLink> findByClassNameId(
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm structure links where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure links
	 */
	public java.util.List<DDMStructureLink> findByClassNameId(
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm structure link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure link
	 * @throws NoSuchStructureLinkException if a matching ddm structure link could not be found
	 */
	public DDMStructureLink findByClassNameId_First(
			long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
				orderByComparator)
		throws NoSuchStructureLinkException;

	/**
	 * Returns the first ddm structure link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	public DDMStructureLink fetchByClassNameId_First(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
			orderByComparator);

	/**
	 * Returns the last ddm structure link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure link
	 * @throws NoSuchStructureLinkException if a matching ddm structure link could not be found
	 */
	public DDMStructureLink findByClassNameId_Last(
			long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
				orderByComparator)
		throws NoSuchStructureLinkException;

	/**
	 * Returns the last ddm structure link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	public DDMStructureLink fetchByClassNameId_Last(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
			orderByComparator);

	/**
	 * Returns the ddm structure links before and after the current ddm structure link in the ordered set where classNameId = &#63;.
	 *
	 * @param structureLinkId the primary key of the current ddm structure link
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure link
	 * @throws NoSuchStructureLinkException if a ddm structure link with the primary key could not be found
	 */
	public DDMStructureLink[] findByClassNameId_PrevAndNext(
			long structureLinkId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
				orderByComparator)
		throws NoSuchStructureLinkException;

	/**
	 * Removes all the ddm structure links where classNameId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 */
	public void removeByClassNameId(long classNameId);

	/**
	 * Returns the number of ddm structure links where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the number of matching ddm structure links
	 */
	public int countByClassNameId(long classNameId);

	/**
	 * Returns all the ddm structure links where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the matching ddm structure links
	 */
	public java.util.List<DDMStructureLink> findByStructureId(long structureId);

	/**
	 * Returns a range of all the ddm structure links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @return the range of matching ddm structure links
	 */
	public java.util.List<DDMStructureLink> findByStructureId(
		long structureId, int start, int end);

	/**
	 * Returns an ordered range of all the ddm structure links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure links
	 */
	public java.util.List<DDMStructureLink> findByStructureId(
		long structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm structure links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure links
	 */
	public java.util.List<DDMStructureLink> findByStructureId(
		long structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm structure link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure link
	 * @throws NoSuchStructureLinkException if a matching ddm structure link could not be found
	 */
	public DDMStructureLink findByStructureId_First(
			long structureId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
				orderByComparator)
		throws NoSuchStructureLinkException;

	/**
	 * Returns the first ddm structure link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	public DDMStructureLink fetchByStructureId_First(
		long structureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
			orderByComparator);

	/**
	 * Returns the last ddm structure link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure link
	 * @throws NoSuchStructureLinkException if a matching ddm structure link could not be found
	 */
	public DDMStructureLink findByStructureId_Last(
			long structureId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
				orderByComparator)
		throws NoSuchStructureLinkException;

	/**
	 * Returns the last ddm structure link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	public DDMStructureLink fetchByStructureId_Last(
		long structureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
			orderByComparator);

	/**
	 * Returns the ddm structure links before and after the current ddm structure link in the ordered set where structureId = &#63;.
	 *
	 * @param structureLinkId the primary key of the current ddm structure link
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure link
	 * @throws NoSuchStructureLinkException if a ddm structure link with the primary key could not be found
	 */
	public DDMStructureLink[] findByStructureId_PrevAndNext(
			long structureLinkId, long structureId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
				orderByComparator)
		throws NoSuchStructureLinkException;

	/**
	 * Removes all the ddm structure links where structureId = &#63; from the database.
	 *
	 * @param structureId the structure ID
	 */
	public void removeByStructureId(long structureId);

	/**
	 * Returns the number of ddm structure links where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the number of matching ddm structure links
	 */
	public int countByStructureId(long structureId);

	/**
	 * Returns all the ddm structure links where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching ddm structure links
	 */
	public java.util.List<DDMStructureLink> findByC_C(
		long classNameId, long classPK);

	/**
	 * Returns a range of all the ddm structure links where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @return the range of matching ddm structure links
	 */
	public java.util.List<DDMStructureLink> findByC_C(
		long classNameId, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the ddm structure links where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure links
	 */
	public java.util.List<DDMStructureLink> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm structure links where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure links
	 */
	public java.util.List<DDMStructureLink> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure link
	 * @throws NoSuchStructureLinkException if a matching ddm structure link could not be found
	 */
	public DDMStructureLink findByC_C_First(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
				orderByComparator)
		throws NoSuchStructureLinkException;

	/**
	 * Returns the first ddm structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	public DDMStructureLink fetchByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
			orderByComparator);

	/**
	 * Returns the last ddm structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure link
	 * @throws NoSuchStructureLinkException if a matching ddm structure link could not be found
	 */
	public DDMStructureLink findByC_C_Last(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
				orderByComparator)
		throws NoSuchStructureLinkException;

	/**
	 * Returns the last ddm structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	public DDMStructureLink fetchByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
			orderByComparator);

	/**
	 * Returns the ddm structure links before and after the current ddm structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param structureLinkId the primary key of the current ddm structure link
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure link
	 * @throws NoSuchStructureLinkException if a ddm structure link with the primary key could not be found
	 */
	public DDMStructureLink[] findByC_C_PrevAndNext(
			long structureLinkId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
				orderByComparator)
		throws NoSuchStructureLinkException;

	/**
	 * Removes all the ddm structure links where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public void removeByC_C(long classNameId, long classPK);

	/**
	 * Returns the number of ddm structure links where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching ddm structure links
	 */
	public int countByC_C(long classNameId, long classPK);

	/**
	 * Returns the ddm structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; or throws a <code>NoSuchStructureLinkException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param structureId the structure ID
	 * @return the matching ddm structure link
	 * @throws NoSuchStructureLinkException if a matching ddm structure link could not be found
	 */
	public DDMStructureLink findByC_C_S(
			long classNameId, long classPK, long structureId)
		throws NoSuchStructureLinkException;

	/**
	 * Returns the ddm structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param structureId the structure ID
	 * @return the matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	public DDMStructureLink fetchByC_C_S(
		long classNameId, long classPK, long structureId);

	/**
	 * Returns the ddm structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param structureId the structure ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm structure link, or <code>null</code> if a matching ddm structure link could not be found
	 */
	public DDMStructureLink fetchByC_C_S(
		long classNameId, long classPK, long structureId,
		boolean useFinderCache);

	/**
	 * Removes the ddm structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param structureId the structure ID
	 * @return the ddm structure link that was removed
	 */
	public DDMStructureLink removeByC_C_S(
			long classNameId, long classPK, long structureId)
		throws NoSuchStructureLinkException;

	/**
	 * Returns the number of ddm structure links where classNameId = &#63; and classPK = &#63; and structureId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param structureId the structure ID
	 * @return the number of matching ddm structure links
	 */
	public int countByC_C_S(long classNameId, long classPK, long structureId);

	/**
	 * Caches the ddm structure link in the entity cache if it is enabled.
	 *
	 * @param ddmStructureLink the ddm structure link
	 */
	public void cacheResult(DDMStructureLink ddmStructureLink);

	/**
	 * Caches the ddm structure links in the entity cache if it is enabled.
	 *
	 * @param ddmStructureLinks the ddm structure links
	 */
	public void cacheResult(java.util.List<DDMStructureLink> ddmStructureLinks);

	/**
	 * Creates a new ddm structure link with the primary key. Does not add the ddm structure link to the database.
	 *
	 * @param structureLinkId the primary key for the new ddm structure link
	 * @return the new ddm structure link
	 */
	public DDMStructureLink create(long structureLinkId);

	/**
	 * Removes the ddm structure link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param structureLinkId the primary key of the ddm structure link
	 * @return the ddm structure link that was removed
	 * @throws NoSuchStructureLinkException if a ddm structure link with the primary key could not be found
	 */
	public DDMStructureLink remove(long structureLinkId)
		throws NoSuchStructureLinkException;

	public DDMStructureLink updateImpl(DDMStructureLink ddmStructureLink);

	/**
	 * Returns the ddm structure link with the primary key or throws a <code>NoSuchStructureLinkException</code> if it could not be found.
	 *
	 * @param structureLinkId the primary key of the ddm structure link
	 * @return the ddm structure link
	 * @throws NoSuchStructureLinkException if a ddm structure link with the primary key could not be found
	 */
	public DDMStructureLink findByPrimaryKey(long structureLinkId)
		throws NoSuchStructureLinkException;

	/**
	 * Returns the ddm structure link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param structureLinkId the primary key of the ddm structure link
	 * @return the ddm structure link, or <code>null</code> if a ddm structure link with the primary key could not be found
	 */
	public DDMStructureLink fetchByPrimaryKey(long structureLinkId);

	/**
	 * Returns all the ddm structure links.
	 *
	 * @return the ddm structure links
	 */
	public java.util.List<DDMStructureLink> findAll();

	/**
	 * Returns a range of all the ddm structure links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @return the range of ddm structure links
	 */
	public java.util.List<DDMStructureLink> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the ddm structure links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm structure links
	 */
	public java.util.List<DDMStructureLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm structure links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structure links
	 * @param end the upper bound of the range of ddm structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm structure links
	 */
	public java.util.List<DDMStructureLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLink>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the ddm structure links from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of ddm structure links.
	 *
	 * @return the number of ddm structure links
	 */
	public int countAll();

}