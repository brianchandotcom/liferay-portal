/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service.persistence;

import com.liferay.commerce.exception.NoSuchShippingOptionAccountEntryRelException;
import com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce shipping option account entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingOptionAccountEntryRelUtil
 * @generated
 */
@ProviderType
public interface CommerceShippingOptionAccountEntryRelPersistence
	extends BasePersistence<CommerceShippingOptionAccountEntryRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceShippingOptionAccountEntryRelUtil} to access the commerce shipping option account entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce shipping option account entry rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the matching commerce shipping option account entry rels
	 */
	public java.util.List<CommerceShippingOptionAccountEntryRel>
		findByAccountEntryId(long accountEntryId);

	/**
	 * Returns a range of all the commerce shipping option account entry rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @return the range of matching commerce shipping option account entry rels
	 */
	public java.util.List<CommerceShippingOptionAccountEntryRel>
		findByAccountEntryId(long accountEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping option account entry rels
	 */
	public java.util.List<CommerceShippingOptionAccountEntryRel>
		findByAccountEntryId(
			long accountEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingOptionAccountEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipping option account entry rels
	 */
	public java.util.List<CommerceShippingOptionAccountEntryRel>
		findByAccountEntryId(
			long accountEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingOptionAccountEntryRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce shipping option account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a matching commerce shipping option account entry rel could not be found
	 */
	public CommerceShippingOptionAccountEntryRel findByAccountEntryId_First(
			long accountEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingOptionAccountEntryRel> orderByComparator)
		throws NoSuchShippingOptionAccountEntryRelException;

	/**
	 * Returns the first commerce shipping option account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	public CommerceShippingOptionAccountEntryRel fetchByAccountEntryId_First(
		long accountEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceShippingOptionAccountEntryRel> orderByComparator);

	/**
	 * Returns the last commerce shipping option account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a matching commerce shipping option account entry rel could not be found
	 */
	public CommerceShippingOptionAccountEntryRel findByAccountEntryId_Last(
			long accountEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingOptionAccountEntryRel> orderByComparator)
		throws NoSuchShippingOptionAccountEntryRelException;

	/**
	 * Returns the last commerce shipping option account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	public CommerceShippingOptionAccountEntryRel fetchByAccountEntryId_Last(
		long accountEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceShippingOptionAccountEntryRel> orderByComparator);

	/**
	 * Returns the commerce shipping option account entry rels before and after the current commerce shipping option account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param CommerceShippingOptionAccountEntryRelId the primary key of the current commerce shipping option account entry rel
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a commerce shipping option account entry rel with the primary key could not be found
	 */
	public CommerceShippingOptionAccountEntryRel[]
			findByAccountEntryId_PrevAndNext(
				long CommerceShippingOptionAccountEntryRelId,
				long accountEntryId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceShippingOptionAccountEntryRel> orderByComparator)
		throws NoSuchShippingOptionAccountEntryRelException;

	/**
	 * Removes all the commerce shipping option account entry rels where accountEntryId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 */
	public void removeByAccountEntryId(long accountEntryId);

	/**
	 * Returns the number of commerce shipping option account entry rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the number of matching commerce shipping option account entry rels
	 */
	public int countByAccountEntryId(long accountEntryId);

	/**
	 * Returns all the commerce shipping option account entry rels where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @return the matching commerce shipping option account entry rels
	 */
	public java.util.List<CommerceShippingOptionAccountEntryRel>
		findByCommerceChannelId(long commerceChannelId);

	/**
	 * Returns a range of all the commerce shipping option account entry rels where commerceChannelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @return the range of matching commerce shipping option account entry rels
	 */
	public java.util.List<CommerceShippingOptionAccountEntryRel>
		findByCommerceChannelId(long commerceChannelId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels where commerceChannelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping option account entry rels
	 */
	public java.util.List<CommerceShippingOptionAccountEntryRel>
		findByCommerceChannelId(
			long commerceChannelId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingOptionAccountEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels where commerceChannelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipping option account entry rels
	 */
	public java.util.List<CommerceShippingOptionAccountEntryRel>
		findByCommerceChannelId(
			long commerceChannelId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingOptionAccountEntryRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce shipping option account entry rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a matching commerce shipping option account entry rel could not be found
	 */
	public CommerceShippingOptionAccountEntryRel findByCommerceChannelId_First(
			long commerceChannelId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingOptionAccountEntryRel> orderByComparator)
		throws NoSuchShippingOptionAccountEntryRelException;

	/**
	 * Returns the first commerce shipping option account entry rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	public CommerceShippingOptionAccountEntryRel fetchByCommerceChannelId_First(
		long commerceChannelId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceShippingOptionAccountEntryRel> orderByComparator);

	/**
	 * Returns the last commerce shipping option account entry rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a matching commerce shipping option account entry rel could not be found
	 */
	public CommerceShippingOptionAccountEntryRel findByCommerceChannelId_Last(
			long commerceChannelId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingOptionAccountEntryRel> orderByComparator)
		throws NoSuchShippingOptionAccountEntryRelException;

	/**
	 * Returns the last commerce shipping option account entry rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	public CommerceShippingOptionAccountEntryRel fetchByCommerceChannelId_Last(
		long commerceChannelId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceShippingOptionAccountEntryRel> orderByComparator);

	/**
	 * Returns the commerce shipping option account entry rels before and after the current commerce shipping option account entry rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param CommerceShippingOptionAccountEntryRelId the primary key of the current commerce shipping option account entry rel
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a commerce shipping option account entry rel with the primary key could not be found
	 */
	public CommerceShippingOptionAccountEntryRel[]
			findByCommerceChannelId_PrevAndNext(
				long CommerceShippingOptionAccountEntryRelId,
				long commerceChannelId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceShippingOptionAccountEntryRel> orderByComparator)
		throws NoSuchShippingOptionAccountEntryRelException;

	/**
	 * Removes all the commerce shipping option account entry rels where commerceChannelId = &#63; from the database.
	 *
	 * @param commerceChannelId the commerce channel ID
	 */
	public void removeByCommerceChannelId(long commerceChannelId);

	/**
	 * Returns the number of commerce shipping option account entry rels where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @return the number of matching commerce shipping option account entry rels
	 */
	public int countByCommerceChannelId(long commerceChannelId);

	/**
	 * Returns all the commerce shipping option account entry rels where commerceShippingOptionKey = &#63;.
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @return the matching commerce shipping option account entry rels
	 */
	public java.util.List<CommerceShippingOptionAccountEntryRel>
		findByCommerceShippingOptionKey(String commerceShippingOptionKey);

	/**
	 * Returns a range of all the commerce shipping option account entry rels where commerceShippingOptionKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @return the range of matching commerce shipping option account entry rels
	 */
	public java.util.List<CommerceShippingOptionAccountEntryRel>
		findByCommerceShippingOptionKey(
			String commerceShippingOptionKey, int start, int end);

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels where commerceShippingOptionKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping option account entry rels
	 */
	public java.util.List<CommerceShippingOptionAccountEntryRel>
		findByCommerceShippingOptionKey(
			String commerceShippingOptionKey, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingOptionAccountEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels where commerceShippingOptionKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipping option account entry rels
	 */
	public java.util.List<CommerceShippingOptionAccountEntryRel>
		findByCommerceShippingOptionKey(
			String commerceShippingOptionKey, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingOptionAccountEntryRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce shipping option account entry rel in the ordered set where commerceShippingOptionKey = &#63;.
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a matching commerce shipping option account entry rel could not be found
	 */
	public CommerceShippingOptionAccountEntryRel
			findByCommerceShippingOptionKey_First(
				String commerceShippingOptionKey,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceShippingOptionAccountEntryRel> orderByComparator)
		throws NoSuchShippingOptionAccountEntryRelException;

	/**
	 * Returns the first commerce shipping option account entry rel in the ordered set where commerceShippingOptionKey = &#63;.
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	public CommerceShippingOptionAccountEntryRel
		fetchByCommerceShippingOptionKey_First(
			String commerceShippingOptionKey,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingOptionAccountEntryRel> orderByComparator);

	/**
	 * Returns the last commerce shipping option account entry rel in the ordered set where commerceShippingOptionKey = &#63;.
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a matching commerce shipping option account entry rel could not be found
	 */
	public CommerceShippingOptionAccountEntryRel
			findByCommerceShippingOptionKey_Last(
				String commerceShippingOptionKey,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceShippingOptionAccountEntryRel> orderByComparator)
		throws NoSuchShippingOptionAccountEntryRelException;

	/**
	 * Returns the last commerce shipping option account entry rel in the ordered set where commerceShippingOptionKey = &#63;.
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	public CommerceShippingOptionAccountEntryRel
		fetchByCommerceShippingOptionKey_Last(
			String commerceShippingOptionKey,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingOptionAccountEntryRel> orderByComparator);

	/**
	 * Returns the commerce shipping option account entry rels before and after the current commerce shipping option account entry rel in the ordered set where commerceShippingOptionKey = &#63;.
	 *
	 * @param CommerceShippingOptionAccountEntryRelId the primary key of the current commerce shipping option account entry rel
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a commerce shipping option account entry rel with the primary key could not be found
	 */
	public CommerceShippingOptionAccountEntryRel[]
			findByCommerceShippingOptionKey_PrevAndNext(
				long CommerceShippingOptionAccountEntryRelId,
				String commerceShippingOptionKey,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceShippingOptionAccountEntryRel> orderByComparator)
		throws NoSuchShippingOptionAccountEntryRelException;

	/**
	 * Removes all the commerce shipping option account entry rels where commerceShippingOptionKey = &#63; from the database.
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 */
	public void removeByCommerceShippingOptionKey(
		String commerceShippingOptionKey);

	/**
	 * Returns the number of commerce shipping option account entry rels where commerceShippingOptionKey = &#63;.
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @return the number of matching commerce shipping option account entry rels
	 */
	public int countByCommerceShippingOptionKey(
		String commerceShippingOptionKey);

	/**
	 * Returns the commerce shipping option account entry rel where accountEntryId = &#63; and commerceChannelId = &#63; or throws a <code>NoSuchShippingOptionAccountEntryRelException</code> if it could not be found.
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @return the matching commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a matching commerce shipping option account entry rel could not be found
	 */
	public CommerceShippingOptionAccountEntryRel findByA_C(
			long accountEntryId, long commerceChannelId)
		throws NoSuchShippingOptionAccountEntryRelException;

	/**
	 * Returns the commerce shipping option account entry rel where accountEntryId = &#63; and commerceChannelId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @return the matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	public CommerceShippingOptionAccountEntryRel fetchByA_C(
		long accountEntryId, long commerceChannelId);

	/**
	 * Returns the commerce shipping option account entry rel where accountEntryId = &#63; and commerceChannelId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	public CommerceShippingOptionAccountEntryRel fetchByA_C(
		long accountEntryId, long commerceChannelId, boolean useFinderCache);

	/**
	 * Removes the commerce shipping option account entry rel where accountEntryId = &#63; and commerceChannelId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @return the commerce shipping option account entry rel that was removed
	 */
	public CommerceShippingOptionAccountEntryRel removeByA_C(
			long accountEntryId, long commerceChannelId)
		throws NoSuchShippingOptionAccountEntryRelException;

	/**
	 * Returns the number of commerce shipping option account entry rels where accountEntryId = &#63; and commerceChannelId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @return the number of matching commerce shipping option account entry rels
	 */
	public int countByA_C(long accountEntryId, long commerceChannelId);

	/**
	 * Caches the commerce shipping option account entry rel in the entity cache if it is enabled.
	 *
	 * @param commerceShippingOptionAccountEntryRel the commerce shipping option account entry rel
	 */
	public void cacheResult(
		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel);

	/**
	 * Caches the commerce shipping option account entry rels in the entity cache if it is enabled.
	 *
	 * @param commerceShippingOptionAccountEntryRels the commerce shipping option account entry rels
	 */
	public void cacheResult(
		java.util.List<CommerceShippingOptionAccountEntryRel>
			commerceShippingOptionAccountEntryRels);

	/**
	 * Creates a new commerce shipping option account entry rel with the primary key. Does not add the commerce shipping option account entry rel to the database.
	 *
	 * @param CommerceShippingOptionAccountEntryRelId the primary key for the new commerce shipping option account entry rel
	 * @return the new commerce shipping option account entry rel
	 */
	public CommerceShippingOptionAccountEntryRel create(
		long CommerceShippingOptionAccountEntryRelId);

	/**
	 * Removes the commerce shipping option account entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CommerceShippingOptionAccountEntryRelId the primary key of the commerce shipping option account entry rel
	 * @return the commerce shipping option account entry rel that was removed
	 * @throws NoSuchShippingOptionAccountEntryRelException if a commerce shipping option account entry rel with the primary key could not be found
	 */
	public CommerceShippingOptionAccountEntryRel remove(
			long CommerceShippingOptionAccountEntryRelId)
		throws NoSuchShippingOptionAccountEntryRelException;

	public CommerceShippingOptionAccountEntryRel updateImpl(
		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel);

	/**
	 * Returns the commerce shipping option account entry rel with the primary key or throws a <code>NoSuchShippingOptionAccountEntryRelException</code> if it could not be found.
	 *
	 * @param CommerceShippingOptionAccountEntryRelId the primary key of the commerce shipping option account entry rel
	 * @return the commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a commerce shipping option account entry rel with the primary key could not be found
	 */
	public CommerceShippingOptionAccountEntryRel findByPrimaryKey(
			long CommerceShippingOptionAccountEntryRelId)
		throws NoSuchShippingOptionAccountEntryRelException;

	/**
	 * Returns the commerce shipping option account entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CommerceShippingOptionAccountEntryRelId the primary key of the commerce shipping option account entry rel
	 * @return the commerce shipping option account entry rel, or <code>null</code> if a commerce shipping option account entry rel with the primary key could not be found
	 */
	public CommerceShippingOptionAccountEntryRel fetchByPrimaryKey(
		long CommerceShippingOptionAccountEntryRelId);

	/**
	 * Returns all the commerce shipping option account entry rels.
	 *
	 * @return the commerce shipping option account entry rels
	 */
	public java.util.List<CommerceShippingOptionAccountEntryRel> findAll();

	/**
	 * Returns a range of all the commerce shipping option account entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @return the range of commerce shipping option account entry rels
	 */
	public java.util.List<CommerceShippingOptionAccountEntryRel> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce shipping option account entry rels
	 */
	public java.util.List<CommerceShippingOptionAccountEntryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceShippingOptionAccountEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce shipping option account entry rels
	 */
	public java.util.List<CommerceShippingOptionAccountEntryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceShippingOptionAccountEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce shipping option account entry rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce shipping option account entry rels.
	 *
	 * @return the number of commerce shipping option account entry rels
	 */
	public int countAll();

}