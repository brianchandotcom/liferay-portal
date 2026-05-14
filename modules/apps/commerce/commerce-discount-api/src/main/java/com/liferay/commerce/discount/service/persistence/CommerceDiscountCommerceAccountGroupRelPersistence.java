/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.service.persistence;

import com.liferay.commerce.discount.exception.NoSuchDiscountCommerceAccountGroupRelException;
import com.liferay.commerce.discount.model.CommerceDiscountCommerceAccountGroupRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce discount commerce account group rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountCommerceAccountGroupRelUtil
 * @generated
 */
@ProviderType
public interface CommerceDiscountCommerceAccountGroupRelPersistence
	extends BasePersistence<CommerceDiscountCommerceAccountGroupRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceDiscountCommerceAccountGroupRelUtil} to access the commerce discount commerce account group rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns an ordered range of all the commerce discount commerce account group rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.discount.model.impl.CommerceDiscountCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount commerce account group rels
	 * @param end the upper bound of the range of commerce discount commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount commerce account group rels
	 */
	public java.util.List<CommerceDiscountCommerceAccountGroupRel>
		findByCommerceDiscountId(
			long commerceDiscountId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountCommerceAccountGroupRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce discount commerce account group rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount commerce account group rel
	 * @throws NoSuchDiscountCommerceAccountGroupRelException if a matching commerce discount commerce account group rel could not be found
	 */
	public CommerceDiscountCommerceAccountGroupRel
			findByCommerceDiscountId_First(
				long commerceDiscountId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceDiscountCommerceAccountGroupRel> orderByComparator)
		throws NoSuchDiscountCommerceAccountGroupRelException;

	/**
	 * Returns the first commerce discount commerce account group rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount commerce account group rel, or <code>null</code> if a matching commerce discount commerce account group rel could not be found
	 */
	public CommerceDiscountCommerceAccountGroupRel
		fetchByCommerceDiscountId_First(
			long commerceDiscountId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountCommerceAccountGroupRel> orderByComparator);

	/**
	 * Removes all the commerce discount commerce account group rels where commerceDiscountId = &#63; from the database.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 */
	public void removeByCommerceDiscountId(long commerceDiscountId);

	/**
	 * Returns the number of commerce discount commerce account group rels where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the number of matching commerce discount commerce account group rels
	 */
	public int countByCommerceDiscountId(long commerceDiscountId);

	/**
	 * Returns an ordered range of all the commerce discount commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.discount.model.impl.CommerceDiscountCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce discount commerce account group rels
	 * @param end the upper bound of the range of commerce discount commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount commerce account group rels
	 */
	public java.util.List<CommerceDiscountCommerceAccountGroupRel>
		findByCommerceAccountGroupId(
			long commerceAccountGroupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountCommerceAccountGroupRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce discount commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount commerce account group rel
	 * @throws NoSuchDiscountCommerceAccountGroupRelException if a matching commerce discount commerce account group rel could not be found
	 */
	public CommerceDiscountCommerceAccountGroupRel
			findByCommerceAccountGroupId_First(
				long commerceAccountGroupId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceDiscountCommerceAccountGroupRel> orderByComparator)
		throws NoSuchDiscountCommerceAccountGroupRelException;

	/**
	 * Returns the first commerce discount commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount commerce account group rel, or <code>null</code> if a matching commerce discount commerce account group rel could not be found
	 */
	public CommerceDiscountCommerceAccountGroupRel
		fetchByCommerceAccountGroupId_First(
			long commerceAccountGroupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountCommerceAccountGroupRel> orderByComparator);

	/**
	 * Removes all the commerce discount commerce account group rels where commerceAccountGroupId = &#63; from the database.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 */
	public void removeByCommerceAccountGroupId(long commerceAccountGroupId);

	/**
	 * Returns the number of commerce discount commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce discount commerce account group rels
	 */
	public int countByCommerceAccountGroupId(long commerceAccountGroupId);

	/**
	 * Returns the commerce discount commerce account group rel where commerceDiscountId = &#63; and commerceAccountGroupId = &#63; or throws a <code>NoSuchDiscountCommerceAccountGroupRelException</code> if it could not be found.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce discount commerce account group rel
	 * @throws NoSuchDiscountCommerceAccountGroupRelException if a matching commerce discount commerce account group rel could not be found
	 */
	public CommerceDiscountCommerceAccountGroupRel findByCDI_CAGI(
			long commerceDiscountId, long commerceAccountGroupId)
		throws NoSuchDiscountCommerceAccountGroupRelException;

	/**
	 * Returns the commerce discount commerce account group rel where commerceDiscountId = &#63; and commerceAccountGroupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce discount commerce account group rel, or <code>null</code> if a matching commerce discount commerce account group rel could not be found
	 */
	public CommerceDiscountCommerceAccountGroupRel fetchByCDI_CAGI(
		long commerceDiscountId, long commerceAccountGroupId,
		boolean useFinderCache);

	/**
	 * Removes the commerce discount commerce account group rel where commerceDiscountId = &#63; and commerceAccountGroupId = &#63; from the database.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the commerce discount commerce account group rel that was removed
	 */
	public CommerceDiscountCommerceAccountGroupRel removeByCDI_CAGI(
			long commerceDiscountId, long commerceAccountGroupId)
		throws NoSuchDiscountCommerceAccountGroupRelException;

	/**
	 * Returns the number of commerce discount commerce account group rels where commerceDiscountId = &#63; and commerceAccountGroupId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce discount commerce account group rels
	 */
	public int countByCDI_CAGI(
		long commerceDiscountId, long commerceAccountGroupId);

	/**
	 * Creates a new commerce discount commerce account group rel with the primary key. Does not add the commerce discount commerce account group rel to the database.
	 *
	 * @param commerceDiscountCommerceAccountGroupRelId the primary key for the new commerce discount commerce account group rel
	 * @return the new commerce discount commerce account group rel
	 */
	public CommerceDiscountCommerceAccountGroupRel create(
		long commerceDiscountCommerceAccountGroupRelId);

	/**
	 * Removes the commerce discount commerce account group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceDiscountCommerceAccountGroupRelId the primary key of the commerce discount commerce account group rel
	 * @return the commerce discount commerce account group rel that was removed
	 * @throws NoSuchDiscountCommerceAccountGroupRelException if a commerce discount commerce account group rel with the primary key could not be found
	 */
	public CommerceDiscountCommerceAccountGroupRel remove(
			long commerceDiscountCommerceAccountGroupRelId)
		throws NoSuchDiscountCommerceAccountGroupRelException;

	public CommerceDiscountCommerceAccountGroupRel updateImpl(
		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel);

	/**
	 * Returns the commerce discount commerce account group rel with the primary key or throws a <code>NoSuchDiscountCommerceAccountGroupRelException</code> if it could not be found.
	 *
	 * @param commerceDiscountCommerceAccountGroupRelId the primary key of the commerce discount commerce account group rel
	 * @return the commerce discount commerce account group rel
	 * @throws NoSuchDiscountCommerceAccountGroupRelException if a commerce discount commerce account group rel with the primary key could not be found
	 */
	public CommerceDiscountCommerceAccountGroupRel findByPrimaryKey(
			long commerceDiscountCommerceAccountGroupRelId)
		throws NoSuchDiscountCommerceAccountGroupRelException;

	/**
	 * Returns the commerce discount commerce account group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceDiscountCommerceAccountGroupRelId the primary key of the commerce discount commerce account group rel
	 * @return the commerce discount commerce account group rel, or <code>null</code> if a commerce discount commerce account group rel with the primary key could not be found
	 */
	public CommerceDiscountCommerceAccountGroupRel fetchByPrimaryKey(
		long commerceDiscountCommerceAccountGroupRelId);

	/**
	 * Returns the commerce discount commerce account group rel where commerceDiscountId = &#63; and commerceAccountGroupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce discount commerce account group rel, or <code>null</code> if a matching commerce discount commerce account group rel could not be found
	 */
	public default CommerceDiscountCommerceAccountGroupRel fetchByCDI_CAGI(
		long commerceDiscountId, long commerceAccountGroupId) {

		return fetchByCDI_CAGI(
			commerceDiscountId, commerceAccountGroupId, true);
	}

	/**
	 * Returns all the commerce discount commerce account group rels where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount commerce account group rels
	 */
	public default java.util.List<CommerceDiscountCommerceAccountGroupRel>
		findByCommerceDiscountId(long commerceDiscountId) {

		return findByCommerceDiscountId(
			commerceDiscountId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the commerce discount commerce account group rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.discount.model.impl.CommerceDiscountCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount commerce account group rels
	 * @param end the upper bound of the range of commerce discount commerce account group rels (not inclusive)
	 * @return the range of matching commerce discount commerce account group rels
	 */
	public default java.util.List<CommerceDiscountCommerceAccountGroupRel>
		findByCommerceDiscountId(long commerceDiscountId, int start, int end) {

		return findByCommerceDiscountId(
			commerceDiscountId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount commerce account group rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.discount.model.impl.CommerceDiscountCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount commerce account group rels
	 * @param end the upper bound of the range of commerce discount commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount commerce account group rels
	 */
	public default java.util.List<CommerceDiscountCommerceAccountGroupRel>
		findByCommerceDiscountId(
			long commerceDiscountId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountCommerceAccountGroupRel> orderByComparator) {

		return findByCommerceDiscountId(
			commerceDiscountId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the commerce discount commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce discount commerce account group rels
	 */
	public default java.util.List<CommerceDiscountCommerceAccountGroupRel>
		findByCommerceAccountGroupId(long commerceAccountGroupId) {

		return findByCommerceAccountGroupId(
			commerceAccountGroupId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the commerce discount commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.discount.model.impl.CommerceDiscountCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce discount commerce account group rels
	 * @param end the upper bound of the range of commerce discount commerce account group rels (not inclusive)
	 * @return the range of matching commerce discount commerce account group rels
	 */
	public default java.util.List<CommerceDiscountCommerceAccountGroupRel>
		findByCommerceAccountGroupId(
			long commerceAccountGroupId, int start, int end) {

		return findByCommerceAccountGroupId(
			commerceAccountGroupId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.discount.model.impl.CommerceDiscountCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce discount commerce account group rels
	 * @param end the upper bound of the range of commerce discount commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount commerce account group rels
	 */
	public default java.util.List<CommerceDiscountCommerceAccountGroupRel>
		findByCommerceAccountGroupId(
			long commerceAccountGroupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountCommerceAccountGroupRel> orderByComparator) {

		return findByCommerceAccountGroupId(
			commerceAccountGroupId, start, end, orderByComparator, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1399600446