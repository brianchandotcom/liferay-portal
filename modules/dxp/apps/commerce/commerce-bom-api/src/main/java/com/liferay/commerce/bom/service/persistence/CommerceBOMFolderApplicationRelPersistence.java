/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.bom.service.persistence;

import com.liferay.commerce.bom.exception.NoSuchBOMFolderApplicationRelException;
import com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce bom folder application rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceBOMFolderApplicationRelUtil
 * @generated
 */
@ProviderType
public interface CommerceBOMFolderApplicationRelPersistence
	extends BasePersistence<CommerceBOMFolderApplicationRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceBOMFolderApplicationRelUtil} to access the commerce bom folder application rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce bom folder application rels where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @return the matching commerce bom folder application rels
	 */
	public java.util.List<CommerceBOMFolderApplicationRel>
		findByCommerceBOMFolderId(long commerceBOMFolderId);

	/**
	 * Returns a range of all the commerce bom folder application rels where commerceBOMFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderApplicationRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param start the lower bound of the range of commerce bom folder application rels
	 * @param end the upper bound of the range of commerce bom folder application rels (not inclusive)
	 * @return the range of matching commerce bom folder application rels
	 */
	public java.util.List<CommerceBOMFolderApplicationRel>
		findByCommerceBOMFolderId(long commerceBOMFolderId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce bom folder application rels where commerceBOMFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderApplicationRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param start the lower bound of the range of commerce bom folder application rels
	 * @param end the upper bound of the range of commerce bom folder application rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce bom folder application rels
	 */
	public java.util.List<CommerceBOMFolderApplicationRel>
		findByCommerceBOMFolderId(
			long commerceBOMFolderId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceBOMFolderApplicationRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce bom folder application rels where commerceBOMFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderApplicationRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param start the lower bound of the range of commerce bom folder application rels
	 * @param end the upper bound of the range of commerce bom folder application rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce bom folder application rels
	 */
	public java.util.List<CommerceBOMFolderApplicationRel>
		findByCommerceBOMFolderId(
			long commerceBOMFolderId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceBOMFolderApplicationRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce bom folder application rel in the ordered set where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce bom folder application rel
	 * @throws NoSuchBOMFolderApplicationRelException if a matching commerce bom folder application rel could not be found
	 */
	public CommerceBOMFolderApplicationRel findByCommerceBOMFolderId_First(
			long commerceBOMFolderId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceBOMFolderApplicationRel> orderByComparator)
		throws NoSuchBOMFolderApplicationRelException;

	/**
	 * Returns the first commerce bom folder application rel in the ordered set where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce bom folder application rel, or <code>null</code> if a matching commerce bom folder application rel could not be found
	 */
	public CommerceBOMFolderApplicationRel fetchByCommerceBOMFolderId_First(
		long commerceBOMFolderId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceBOMFolderApplicationRel> orderByComparator);

	/**
	 * Returns the last commerce bom folder application rel in the ordered set where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce bom folder application rel
	 * @throws NoSuchBOMFolderApplicationRelException if a matching commerce bom folder application rel could not be found
	 */
	public CommerceBOMFolderApplicationRel findByCommerceBOMFolderId_Last(
			long commerceBOMFolderId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceBOMFolderApplicationRel> orderByComparator)
		throws NoSuchBOMFolderApplicationRelException;

	/**
	 * Returns the last commerce bom folder application rel in the ordered set where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce bom folder application rel, or <code>null</code> if a matching commerce bom folder application rel could not be found
	 */
	public CommerceBOMFolderApplicationRel fetchByCommerceBOMFolderId_Last(
		long commerceBOMFolderId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceBOMFolderApplicationRel> orderByComparator);

	/**
	 * Returns the commerce bom folder application rels before and after the current commerce bom folder application rel in the ordered set where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderApplicationRelId the primary key of the current commerce bom folder application rel
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce bom folder application rel
	 * @throws NoSuchBOMFolderApplicationRelException if a commerce bom folder application rel with the primary key could not be found
	 */
	public CommerceBOMFolderApplicationRel[]
			findByCommerceBOMFolderId_PrevAndNext(
				long commerceBOMFolderApplicationRelId,
				long commerceBOMFolderId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceBOMFolderApplicationRel> orderByComparator)
		throws NoSuchBOMFolderApplicationRelException;

	/**
	 * Removes all the commerce bom folder application rels where commerceBOMFolderId = &#63; from the database.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 */
	public void removeByCommerceBOMFolderId(long commerceBOMFolderId);

	/**
	 * Returns the number of commerce bom folder application rels where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @return the number of matching commerce bom folder application rels
	 */
	public int countByCommerceBOMFolderId(long commerceBOMFolderId);

	/**
	 * Returns all the commerce bom folder application rels where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @return the matching commerce bom folder application rels
	 */
	public java.util.List<CommerceBOMFolderApplicationRel>
		findByCommerceApplicationModelId(long commerceApplicationModelId);

	/**
	 * Returns a range of all the commerce bom folder application rels where commerceApplicationModelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderApplicationRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param start the lower bound of the range of commerce bom folder application rels
	 * @param end the upper bound of the range of commerce bom folder application rels (not inclusive)
	 * @return the range of matching commerce bom folder application rels
	 */
	public java.util.List<CommerceBOMFolderApplicationRel>
		findByCommerceApplicationModelId(
			long commerceApplicationModelId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce bom folder application rels where commerceApplicationModelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderApplicationRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param start the lower bound of the range of commerce bom folder application rels
	 * @param end the upper bound of the range of commerce bom folder application rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce bom folder application rels
	 */
	public java.util.List<CommerceBOMFolderApplicationRel>
		findByCommerceApplicationModelId(
			long commerceApplicationModelId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceBOMFolderApplicationRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce bom folder application rels where commerceApplicationModelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderApplicationRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param start the lower bound of the range of commerce bom folder application rels
	 * @param end the upper bound of the range of commerce bom folder application rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce bom folder application rels
	 */
	public java.util.List<CommerceBOMFolderApplicationRel>
		findByCommerceApplicationModelId(
			long commerceApplicationModelId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceBOMFolderApplicationRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce bom folder application rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce bom folder application rel
	 * @throws NoSuchBOMFolderApplicationRelException if a matching commerce bom folder application rel could not be found
	 */
	public CommerceBOMFolderApplicationRel
			findByCommerceApplicationModelId_First(
				long commerceApplicationModelId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceBOMFolderApplicationRel> orderByComparator)
		throws NoSuchBOMFolderApplicationRelException;

	/**
	 * Returns the first commerce bom folder application rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce bom folder application rel, or <code>null</code> if a matching commerce bom folder application rel could not be found
	 */
	public CommerceBOMFolderApplicationRel
		fetchByCommerceApplicationModelId_First(
			long commerceApplicationModelId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceBOMFolderApplicationRel> orderByComparator);

	/**
	 * Returns the last commerce bom folder application rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce bom folder application rel
	 * @throws NoSuchBOMFolderApplicationRelException if a matching commerce bom folder application rel could not be found
	 */
	public CommerceBOMFolderApplicationRel
			findByCommerceApplicationModelId_Last(
				long commerceApplicationModelId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceBOMFolderApplicationRel> orderByComparator)
		throws NoSuchBOMFolderApplicationRelException;

	/**
	 * Returns the last commerce bom folder application rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce bom folder application rel, or <code>null</code> if a matching commerce bom folder application rel could not be found
	 */
	public CommerceBOMFolderApplicationRel
		fetchByCommerceApplicationModelId_Last(
			long commerceApplicationModelId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceBOMFolderApplicationRel> orderByComparator);

	/**
	 * Returns the commerce bom folder application rels before and after the current commerce bom folder application rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceBOMFolderApplicationRelId the primary key of the current commerce bom folder application rel
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce bom folder application rel
	 * @throws NoSuchBOMFolderApplicationRelException if a commerce bom folder application rel with the primary key could not be found
	 */
	public CommerceBOMFolderApplicationRel[]
			findByCommerceApplicationModelId_PrevAndNext(
				long commerceBOMFolderApplicationRelId,
				long commerceApplicationModelId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceBOMFolderApplicationRel> orderByComparator)
		throws NoSuchBOMFolderApplicationRelException;

	/**
	 * Removes all the commerce bom folder application rels where commerceApplicationModelId = &#63; from the database.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 */
	public void removeByCommerceApplicationModelId(
		long commerceApplicationModelId);

	/**
	 * Returns the number of commerce bom folder application rels where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @return the number of matching commerce bom folder application rels
	 */
	public int countByCommerceApplicationModelId(
		long commerceApplicationModelId);

	/**
	 * Caches the commerce bom folder application rel in the entity cache if it is enabled.
	 *
	 * @param commerceBOMFolderApplicationRel the commerce bom folder application rel
	 */
	public void cacheResult(
		CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel);

	/**
	 * Caches the commerce bom folder application rels in the entity cache if it is enabled.
	 *
	 * @param commerceBOMFolderApplicationRels the commerce bom folder application rels
	 */
	public void cacheResult(
		java.util.List<CommerceBOMFolderApplicationRel>
			commerceBOMFolderApplicationRels);

	/**
	 * Creates a new commerce bom folder application rel with the primary key. Does not add the commerce bom folder application rel to the database.
	 *
	 * @param commerceBOMFolderApplicationRelId the primary key for the new commerce bom folder application rel
	 * @return the new commerce bom folder application rel
	 */
	public CommerceBOMFolderApplicationRel create(
		long commerceBOMFolderApplicationRelId);

	/**
	 * Removes the commerce bom folder application rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceBOMFolderApplicationRelId the primary key of the commerce bom folder application rel
	 * @return the commerce bom folder application rel that was removed
	 * @throws NoSuchBOMFolderApplicationRelException if a commerce bom folder application rel with the primary key could not be found
	 */
	public CommerceBOMFolderApplicationRel remove(
			long commerceBOMFolderApplicationRelId)
		throws NoSuchBOMFolderApplicationRelException;

	public CommerceBOMFolderApplicationRel updateImpl(
		CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel);

	/**
	 * Returns the commerce bom folder application rel with the primary key or throws a <code>NoSuchBOMFolderApplicationRelException</code> if it could not be found.
	 *
	 * @param commerceBOMFolderApplicationRelId the primary key of the commerce bom folder application rel
	 * @return the commerce bom folder application rel
	 * @throws NoSuchBOMFolderApplicationRelException if a commerce bom folder application rel with the primary key could not be found
	 */
	public CommerceBOMFolderApplicationRel findByPrimaryKey(
			long commerceBOMFolderApplicationRelId)
		throws NoSuchBOMFolderApplicationRelException;

	/**
	 * Returns the commerce bom folder application rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceBOMFolderApplicationRelId the primary key of the commerce bom folder application rel
	 * @return the commerce bom folder application rel, or <code>null</code> if a commerce bom folder application rel with the primary key could not be found
	 */
	public CommerceBOMFolderApplicationRel fetchByPrimaryKey(
		long commerceBOMFolderApplicationRelId);

	/**
	 * Returns all the commerce bom folder application rels.
	 *
	 * @return the commerce bom folder application rels
	 */
	public java.util.List<CommerceBOMFolderApplicationRel> findAll();

	/**
	 * Returns a range of all the commerce bom folder application rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderApplicationRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce bom folder application rels
	 * @param end the upper bound of the range of commerce bom folder application rels (not inclusive)
	 * @return the range of commerce bom folder application rels
	 */
	public java.util.List<CommerceBOMFolderApplicationRel> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce bom folder application rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderApplicationRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce bom folder application rels
	 * @param end the upper bound of the range of commerce bom folder application rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce bom folder application rels
	 */
	public java.util.List<CommerceBOMFolderApplicationRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceBOMFolderApplicationRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce bom folder application rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMFolderApplicationRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce bom folder application rels
	 * @param end the upper bound of the range of commerce bom folder application rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce bom folder application rels
	 */
	public java.util.List<CommerceBOMFolderApplicationRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceBOMFolderApplicationRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce bom folder application rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce bom folder application rels.
	 *
	 * @return the number of commerce bom folder application rels
	 */
	public int countAll();

}