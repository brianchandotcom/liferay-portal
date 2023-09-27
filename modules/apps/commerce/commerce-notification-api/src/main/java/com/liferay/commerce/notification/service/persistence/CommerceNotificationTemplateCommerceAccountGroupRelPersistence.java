/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.service.persistence;

import com.liferay.commerce.notification.exception.NoSuchNotificationTemplateCommerceAccountGroupRelException;
import com.liferay.commerce.notification.model.CommerceNotificationTemplateCommerceAccountGroupRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce notification template commerce account group rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateCommerceAccountGroupRelUtil
 * @generated
 */
@ProviderType
public interface CommerceNotificationTemplateCommerceAccountGroupRelPersistence
	extends BasePersistence
		<CommerceNotificationTemplateCommerceAccountGroupRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceNotificationTemplateCommerceAccountGroupRelUtil} to access the commerce notification template commerce account group rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce notification template commerce account group rels where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @return the matching commerce notification template commerce account group rels
	 */
	public java.util.List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId);

	/**
	 * Returns a range of all the commerce notification template commerce account group rels where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @return the range of matching commerce notification template commerce account group rels
	 */
	public java.util.List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce notification template commerce account group rels where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification template commerce account group rels
	 */
	public java.util.List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator);

	/**
	 * Returns an ordered range of all the commerce notification template commerce account group rels where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification template commerce account group rels
	 */
	public java.util.List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce notification template commerce account group rel in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a matching commerce notification template commerce account group rel could not be found
	 */
	public CommerceNotificationTemplateCommerceAccountGroupRel
			findByCommerceNotificationTemplateId_First(
				long commerceNotificationTemplateId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceNotificationTemplateCommerceAccountGroupRel>
						orderByComparator)
		throws NoSuchNotificationTemplateCommerceAccountGroupRelException;

	/**
	 * Returns the first commerce notification template commerce account group rel in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template commerce account group rel, or <code>null</code> if a matching commerce notification template commerce account group rel could not be found
	 */
	public CommerceNotificationTemplateCommerceAccountGroupRel
		fetchByCommerceNotificationTemplateId_First(
			long commerceNotificationTemplateId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator);

	/**
	 * Returns the last commerce notification template commerce account group rel in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a matching commerce notification template commerce account group rel could not be found
	 */
	public CommerceNotificationTemplateCommerceAccountGroupRel
			findByCommerceNotificationTemplateId_Last(
				long commerceNotificationTemplateId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceNotificationTemplateCommerceAccountGroupRel>
						orderByComparator)
		throws NoSuchNotificationTemplateCommerceAccountGroupRelException;

	/**
	 * Returns the last commerce notification template commerce account group rel in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template commerce account group rel, or <code>null</code> if a matching commerce notification template commerce account group rel could not be found
	 */
	public CommerceNotificationTemplateCommerceAccountGroupRel
		fetchByCommerceNotificationTemplateId_Last(
			long commerceNotificationTemplateId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator);

	/**
	 * Returns the commerce notification template commerce account group rels before and after the current commerce notification template commerce account group rel in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRelId the primary key of the current commerce notification template commerce account group rel
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a commerce notification template commerce account group rel with the primary key could not be found
	 */
	public CommerceNotificationTemplateCommerceAccountGroupRel[]
			findByCommerceNotificationTemplateId_PrevAndNext(
				long commerceNotificationTemplateCommerceAccountGroupRelId,
				long commerceNotificationTemplateId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceNotificationTemplateCommerceAccountGroupRel>
						orderByComparator)
		throws NoSuchNotificationTemplateCommerceAccountGroupRelException;

	/**
	 * Removes all the commerce notification template commerce account group rels where commerceNotificationTemplateId = &#63; from the database.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 */
	public void removeByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId);

	/**
	 * Returns the number of commerce notification template commerce account group rels where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @return the number of matching commerce notification template commerce account group rels
	 */
	public int countByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId);

	/**
	 * Returns all the commerce notification template commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce notification template commerce account group rels
	 */
	public java.util.List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceAccountGroupId(long commerceAccountGroupId);

	/**
	 * Returns a range of all the commerce notification template commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @return the range of matching commerce notification template commerce account group rels
	 */
	public java.util.List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceAccountGroupId(
			long commerceAccountGroupId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce notification template commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification template commerce account group rels
	 */
	public java.util.List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceAccountGroupId(
			long commerceAccountGroupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator);

	/**
	 * Returns an ordered range of all the commerce notification template commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification template commerce account group rels
	 */
	public java.util.List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceAccountGroupId(
			long commerceAccountGroupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce notification template commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a matching commerce notification template commerce account group rel could not be found
	 */
	public CommerceNotificationTemplateCommerceAccountGroupRel
			findByCommerceAccountGroupId_First(
				long commerceAccountGroupId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceNotificationTemplateCommerceAccountGroupRel>
						orderByComparator)
		throws NoSuchNotificationTemplateCommerceAccountGroupRelException;

	/**
	 * Returns the first commerce notification template commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template commerce account group rel, or <code>null</code> if a matching commerce notification template commerce account group rel could not be found
	 */
	public CommerceNotificationTemplateCommerceAccountGroupRel
		fetchByCommerceAccountGroupId_First(
			long commerceAccountGroupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator);

	/**
	 * Returns the last commerce notification template commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a matching commerce notification template commerce account group rel could not be found
	 */
	public CommerceNotificationTemplateCommerceAccountGroupRel
			findByCommerceAccountGroupId_Last(
				long commerceAccountGroupId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceNotificationTemplateCommerceAccountGroupRel>
						orderByComparator)
		throws NoSuchNotificationTemplateCommerceAccountGroupRelException;

	/**
	 * Returns the last commerce notification template commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template commerce account group rel, or <code>null</code> if a matching commerce notification template commerce account group rel could not be found
	 */
	public CommerceNotificationTemplateCommerceAccountGroupRel
		fetchByCommerceAccountGroupId_Last(
			long commerceAccountGroupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator);

	/**
	 * Returns the commerce notification template commerce account group rels before and after the current commerce notification template commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRelId the primary key of the current commerce notification template commerce account group rel
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a commerce notification template commerce account group rel with the primary key could not be found
	 */
	public CommerceNotificationTemplateCommerceAccountGroupRel[]
			findByCommerceAccountGroupId_PrevAndNext(
				long commerceNotificationTemplateCommerceAccountGroupRelId,
				long commerceAccountGroupId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceNotificationTemplateCommerceAccountGroupRel>
						orderByComparator)
		throws NoSuchNotificationTemplateCommerceAccountGroupRelException;

	/**
	 * Removes all the commerce notification template commerce account group rels where commerceAccountGroupId = &#63; from the database.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 */
	public void removeByCommerceAccountGroupId(long commerceAccountGroupId);

	/**
	 * Returns the number of commerce notification template commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce notification template commerce account group rels
	 */
	public int countByCommerceAccountGroupId(long commerceAccountGroupId);

	/**
	 * Returns the commerce notification template commerce account group rel where commerceNotificationTemplateId = &#63; and commerceAccountGroupId = &#63; or throws a <code>NoSuchNotificationTemplateCommerceAccountGroupRelException</code> if it could not be found.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a matching commerce notification template commerce account group rel could not be found
	 */
	public CommerceNotificationTemplateCommerceAccountGroupRel findByC_C(
			long commerceNotificationTemplateId, long commerceAccountGroupId)
		throws NoSuchNotificationTemplateCommerceAccountGroupRelException;

	/**
	 * Returns the commerce notification template commerce account group rel where commerceNotificationTemplateId = &#63; and commerceAccountGroupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce notification template commerce account group rel, or <code>null</code> if a matching commerce notification template commerce account group rel could not be found
	 */
	public CommerceNotificationTemplateCommerceAccountGroupRel fetchByC_C(
		long commerceNotificationTemplateId, long commerceAccountGroupId);

	/**
	 * Returns the commerce notification template commerce account group rel where commerceNotificationTemplateId = &#63; and commerceAccountGroupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce notification template commerce account group rel, or <code>null</code> if a matching commerce notification template commerce account group rel could not be found
	 */
	public CommerceNotificationTemplateCommerceAccountGroupRel fetchByC_C(
		long commerceNotificationTemplateId, long commerceAccountGroupId,
		boolean useFinderCache);

	/**
	 * Removes the commerce notification template commerce account group rel where commerceNotificationTemplateId = &#63; and commerceAccountGroupId = &#63; from the database.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the commerce notification template commerce account group rel that was removed
	 */
	public CommerceNotificationTemplateCommerceAccountGroupRel removeByC_C(
			long commerceNotificationTemplateId, long commerceAccountGroupId)
		throws NoSuchNotificationTemplateCommerceAccountGroupRelException;

	/**
	 * Returns the number of commerce notification template commerce account group rels where commerceNotificationTemplateId = &#63; and commerceAccountGroupId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce notification template commerce account group rels
	 */
	public int countByC_C(
		long commerceNotificationTemplateId, long commerceAccountGroupId);

	/**
	 * Caches the commerce notification template commerce account group rel in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRel the commerce notification template commerce account group rel
	 */
	public void cacheResult(
		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel);

	/**
	 * Caches the commerce notification template commerce account group rels in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRels the commerce notification template commerce account group rels
	 */
	public void cacheResult(
		java.util.List<CommerceNotificationTemplateCommerceAccountGroupRel>
			commerceNotificationTemplateCommerceAccountGroupRels);

	/**
	 * Creates a new commerce notification template commerce account group rel with the primary key. Does not add the commerce notification template commerce account group rel to the database.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRelId the primary key for the new commerce notification template commerce account group rel
	 * @return the new commerce notification template commerce account group rel
	 */
	public CommerceNotificationTemplateCommerceAccountGroupRel create(
		long commerceNotificationTemplateCommerceAccountGroupRelId);

	/**
	 * Removes the commerce notification template commerce account group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRelId the primary key of the commerce notification template commerce account group rel
	 * @return the commerce notification template commerce account group rel that was removed
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a commerce notification template commerce account group rel with the primary key could not be found
	 */
	public CommerceNotificationTemplateCommerceAccountGroupRel remove(
			long commerceNotificationTemplateCommerceAccountGroupRelId)
		throws NoSuchNotificationTemplateCommerceAccountGroupRelException;

	public CommerceNotificationTemplateCommerceAccountGroupRel updateImpl(
		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel);

	/**
	 * Returns the commerce notification template commerce account group rel with the primary key or throws a <code>NoSuchNotificationTemplateCommerceAccountGroupRelException</code> if it could not be found.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRelId the primary key of the commerce notification template commerce account group rel
	 * @return the commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a commerce notification template commerce account group rel with the primary key could not be found
	 */
	public CommerceNotificationTemplateCommerceAccountGroupRel findByPrimaryKey(
			long commerceNotificationTemplateCommerceAccountGroupRelId)
		throws NoSuchNotificationTemplateCommerceAccountGroupRelException;

	/**
	 * Returns the commerce notification template commerce account group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRelId the primary key of the commerce notification template commerce account group rel
	 * @return the commerce notification template commerce account group rel, or <code>null</code> if a commerce notification template commerce account group rel with the primary key could not be found
	 */
	public CommerceNotificationTemplateCommerceAccountGroupRel
		fetchByPrimaryKey(
			long commerceNotificationTemplateCommerceAccountGroupRelId);

	/**
	 * Returns all the commerce notification template commerce account group rels.
	 *
	 * @return the commerce notification template commerce account group rels
	 */
	public java.util.List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findAll();

	/**
	 * Returns a range of all the commerce notification template commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @return the range of commerce notification template commerce account group rels
	 */
	public java.util.List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findAll(int start, int end);

	/**
	 * Returns an ordered range of all the commerce notification template commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce notification template commerce account group rels
	 */
	public java.util.List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findAll(
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator);

	/**
	 * Returns an ordered range of all the commerce notification template commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce notification template commerce account group rels
	 */
	public java.util.List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findAll(
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator,
			boolean useFinderCache);

	/**
	 * Removes all the commerce notification template commerce account group rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce notification template commerce account group rels.
	 *
	 * @return the number of commerce notification template commerce account group rels
	 */
	public int countAll();

}