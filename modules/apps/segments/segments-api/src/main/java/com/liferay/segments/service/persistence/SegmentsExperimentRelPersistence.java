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

package com.liferay.segments.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.segments.exception.NoSuchExperimentRelException;
import com.liferay.segments.model.SegmentsExperimentRel;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the segments experiment rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsExperimentRelUtil
 * @generated
 */
@ProviderType
public interface SegmentsExperimentRelPersistence
	extends BasePersistence<SegmentsExperimentRel> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SegmentsExperimentRelUtil} to access the segments experiment rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the segments experiment rels where segmentsExperimentId = &#63;.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @return the matching segments experiment rels
	 */
	public java.util.List<SegmentsExperimentRel> findBySegmentsExperimentId(
		long segmentsExperimentId);

	/**
	 * Returns a range of all the segments experiment rels where segmentsExperimentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentRelModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param start the lower bound of the range of segments experiment rels
	 * @param end the upper bound of the range of segments experiment rels (not inclusive)
	 * @return the range of matching segments experiment rels
	 */
	public java.util.List<SegmentsExperimentRel> findBySegmentsExperimentId(
		long segmentsExperimentId, int start, int end);

	/**
	 * Returns an ordered range of all the segments experiment rels where segmentsExperimentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentRelModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param start the lower bound of the range of segments experiment rels
	 * @param end the upper bound of the range of segments experiment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiment rels
	 */
	public java.util.List<SegmentsExperimentRel> findBySegmentsExperimentId(
		long segmentsExperimentId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperimentRel>
			orderByComparator);

	/**
	 * Returns an ordered range of all the segments experiment rels where segmentsExperimentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentRelModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param start the lower bound of the range of segments experiment rels
	 * @param end the upper bound of the range of segments experiment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments experiment rels
	 */
	public java.util.List<SegmentsExperimentRel> findBySegmentsExperimentId(
		long segmentsExperimentId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperimentRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first segments experiment rel in the ordered set where segmentsExperimentId = &#63;.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment rel
	 * @throws NoSuchExperimentRelException if a matching segments experiment rel could not be found
	 */
	public SegmentsExperimentRel findBySegmentsExperimentId_First(
			long segmentsExperimentId,
			com.liferay.portal.kernel.util.OrderByComparator
				<SegmentsExperimentRel> orderByComparator)
		throws NoSuchExperimentRelException;

	/**
	 * Returns the first segments experiment rel in the ordered set where segmentsExperimentId = &#63;.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment rel, or <code>null</code> if a matching segments experiment rel could not be found
	 */
	public SegmentsExperimentRel fetchBySegmentsExperimentId_First(
		long segmentsExperimentId,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperimentRel>
			orderByComparator);

	/**
	 * Returns the last segments experiment rel in the ordered set where segmentsExperimentId = &#63;.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment rel
	 * @throws NoSuchExperimentRelException if a matching segments experiment rel could not be found
	 */
	public SegmentsExperimentRel findBySegmentsExperimentId_Last(
			long segmentsExperimentId,
			com.liferay.portal.kernel.util.OrderByComparator
				<SegmentsExperimentRel> orderByComparator)
		throws NoSuchExperimentRelException;

	/**
	 * Returns the last segments experiment rel in the ordered set where segmentsExperimentId = &#63;.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment rel, or <code>null</code> if a matching segments experiment rel could not be found
	 */
	public SegmentsExperimentRel fetchBySegmentsExperimentId_Last(
		long segmentsExperimentId,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperimentRel>
			orderByComparator);

	/**
	 * Returns the segments experiment rels before and after the current segments experiment rel in the ordered set where segmentsExperimentId = &#63;.
	 *
	 * @param segmentsExperimentRelId the primary key of the current segments experiment rel
	 * @param segmentsExperimentId the segments experiment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experiment rel
	 * @throws NoSuchExperimentRelException if a segments experiment rel with the primary key could not be found
	 */
	public SegmentsExperimentRel[] findBySegmentsExperimentId_PrevAndNext(
			long segmentsExperimentRelId, long segmentsExperimentId,
			com.liferay.portal.kernel.util.OrderByComparator
				<SegmentsExperimentRel> orderByComparator)
		throws NoSuchExperimentRelException;

	/**
	 * Removes all the segments experiment rels where segmentsExperimentId = &#63; from the database.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 */
	public void removeBySegmentsExperimentId(long segmentsExperimentId);

	/**
	 * Returns the number of segments experiment rels where segmentsExperimentId = &#63;.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @return the number of matching segments experiment rels
	 */
	public int countBySegmentsExperimentId(long segmentsExperimentId);

	/**
	 * Returns the segments experiment rel where segmentsExperimentId = &#63; and segmentsExperienceId = &#63; or throws a <code>NoSuchExperimentRelException</code> if it could not be found.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param segmentsExperienceId the segments experience ID
	 * @return the matching segments experiment rel
	 * @throws NoSuchExperimentRelException if a matching segments experiment rel could not be found
	 */
	public SegmentsExperimentRel findByS_S(
			long segmentsExperimentId, long segmentsExperienceId)
		throws NoSuchExperimentRelException;

	/**
	 * Returns the segments experiment rel where segmentsExperimentId = &#63; and segmentsExperienceId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param segmentsExperienceId the segments experience ID
	 * @return the matching segments experiment rel, or <code>null</code> if a matching segments experiment rel could not be found
	 */
	public SegmentsExperimentRel fetchByS_S(
		long segmentsExperimentId, long segmentsExperienceId);

	/**
	 * Returns the segments experiment rel where segmentsExperimentId = &#63; and segmentsExperienceId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching segments experiment rel, or <code>null</code> if a matching segments experiment rel could not be found
	 */
	public SegmentsExperimentRel fetchByS_S(
		long segmentsExperimentId, long segmentsExperienceId,
		boolean useFinderCache);

	/**
	 * Removes the segments experiment rel where segmentsExperimentId = &#63; and segmentsExperienceId = &#63; from the database.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param segmentsExperienceId the segments experience ID
	 * @return the segments experiment rel that was removed
	 */
	public SegmentsExperimentRel removeByS_S(
			long segmentsExperimentId, long segmentsExperienceId)
		throws NoSuchExperimentRelException;

	/**
	 * Returns the number of segments experiment rels where segmentsExperimentId = &#63; and segmentsExperienceId = &#63;.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param segmentsExperienceId the segments experience ID
	 * @return the number of matching segments experiment rels
	 */
	public int countByS_S(long segmentsExperimentId, long segmentsExperienceId);

	/**
	 * Caches the segments experiment rel in the entity cache if it is enabled.
	 *
	 * @param segmentsExperimentRel the segments experiment rel
	 */
	public void cacheResult(SegmentsExperimentRel segmentsExperimentRel);

	/**
	 * Caches the segments experiment rels in the entity cache if it is enabled.
	 *
	 * @param segmentsExperimentRels the segments experiment rels
	 */
	public void cacheResult(
		java.util.List<SegmentsExperimentRel> segmentsExperimentRels);

	/**
	 * Creates a new segments experiment rel with the primary key. Does not add the segments experiment rel to the database.
	 *
	 * @param segmentsExperimentRelId the primary key for the new segments experiment rel
	 * @return the new segments experiment rel
	 */
	public SegmentsExperimentRel create(long segmentsExperimentRelId);

	/**
	 * Removes the segments experiment rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsExperimentRelId the primary key of the segments experiment rel
	 * @return the segments experiment rel that was removed
	 * @throws NoSuchExperimentRelException if a segments experiment rel with the primary key could not be found
	 */
	public SegmentsExperimentRel remove(long segmentsExperimentRelId)
		throws NoSuchExperimentRelException;

	public SegmentsExperimentRel updateImpl(
		SegmentsExperimentRel segmentsExperimentRel);

	/**
	 * Returns the segments experiment rel with the primary key or throws a <code>NoSuchExperimentRelException</code> if it could not be found.
	 *
	 * @param segmentsExperimentRelId the primary key of the segments experiment rel
	 * @return the segments experiment rel
	 * @throws NoSuchExperimentRelException if a segments experiment rel with the primary key could not be found
	 */
	public SegmentsExperimentRel findByPrimaryKey(long segmentsExperimentRelId)
		throws NoSuchExperimentRelException;

	/**
	 * Returns the segments experiment rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param segmentsExperimentRelId the primary key of the segments experiment rel
	 * @return the segments experiment rel, or <code>null</code> if a segments experiment rel with the primary key could not be found
	 */
	public SegmentsExperimentRel fetchByPrimaryKey(
		long segmentsExperimentRelId);

	/**
	 * Returns all the segments experiment rels.
	 *
	 * @return the segments experiment rels
	 */
	public java.util.List<SegmentsExperimentRel> findAll();

	/**
	 * Returns a range of all the segments experiment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments experiment rels
	 * @param end the upper bound of the range of segments experiment rels (not inclusive)
	 * @return the range of segments experiment rels
	 */
	public java.util.List<SegmentsExperimentRel> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the segments experiment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments experiment rels
	 * @param end the upper bound of the range of segments experiment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of segments experiment rels
	 */
	public java.util.List<SegmentsExperimentRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperimentRel>
			orderByComparator);

	/**
	 * Returns an ordered range of all the segments experiment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments experiment rels
	 * @param end the upper bound of the range of segments experiment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of segments experiment rels
	 */
	public java.util.List<SegmentsExperimentRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperimentRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the segments experiment rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of segments experiment rels.
	 *
	 * @return the number of segments experiment rels
	 */
	public int countAll();

}