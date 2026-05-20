/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence;

import com.liferay.commerce.product.exception.NoSuchCPInstanceOptionValueRelException;
import com.liferay.commerce.product.model.CPInstanceOptionValueRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cp instance option value rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPInstanceOptionValueRelUtil
 * @generated
 */
@ProviderType
public interface CPInstanceOptionValueRelPersistence
	extends BasePersistence<CPInstanceOptionValueRel>,
			CTPersistence<CPInstanceOptionValueRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPInstanceOptionValueRelUtil} to access the cp instance option value rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns an ordered range of all the cp instance option value rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance option value rels
	 */
	public java.util.List<CPInstanceOptionValueRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceOptionValueRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp instance option value rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	public CPInstanceOptionValueRel findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceOptionValueRel> orderByComparator)
		throws NoSuchCPInstanceOptionValueRelException;

	/**
	 * Returns the first cp instance option value rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public CPInstanceOptionValueRel fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceOptionValueRel> orderByComparator);

	/**
	 * Removes all the cp instance option value rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of cp instance option value rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp instance option value rels
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the cp instance option value rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPInstanceOptionValueRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	public CPInstanceOptionValueRel findByUUID_G(String uuid, long groupId)
		throws NoSuchCPInstanceOptionValueRelException;

	/**
	 * Returns the cp instance option value rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public CPInstanceOptionValueRel fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the cp instance option value rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp instance option value rel that was removed
	 */
	public CPInstanceOptionValueRel removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPInstanceOptionValueRelException;

	/**
	 * Returns the number of cp instance option value rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp instance option value rels
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns an ordered range of all the cp instance option value rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance option value rels
	 */
	public java.util.List<CPInstanceOptionValueRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceOptionValueRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp instance option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	public CPInstanceOptionValueRel findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceOptionValueRel> orderByComparator)
		throws NoSuchCPInstanceOptionValueRelException;

	/**
	 * Returns the first cp instance option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public CPInstanceOptionValueRel fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceOptionValueRel> orderByComparator);

	/**
	 * Removes all the cp instance option value rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of cp instance option value rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp instance option value rels
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns an ordered range of all the cp instance option value rels where CPDefinitionOptionRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance option value rels
	 */
	public java.util.List<CPInstanceOptionValueRel>
		findByCPDefinitionOptionRelId(
			long CPDefinitionOptionRelId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceOptionValueRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	public CPInstanceOptionValueRel findByCPDefinitionOptionRelId_First(
			long CPDefinitionOptionRelId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceOptionValueRel> orderByComparator)
		throws NoSuchCPInstanceOptionValueRelException;

	/**
	 * Returns the first cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public CPInstanceOptionValueRel fetchByCPDefinitionOptionRelId_First(
		long CPDefinitionOptionRelId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceOptionValueRel> orderByComparator);

	/**
	 * Removes all the cp instance option value rels where CPDefinitionOptionRelId = &#63; from the database.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 */
	public void removeByCPDefinitionOptionRelId(long CPDefinitionOptionRelId);

	/**
	 * Returns the number of cp instance option value rels where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @return the number of matching cp instance option value rels
	 */
	public int countByCPDefinitionOptionRelId(long CPDefinitionOptionRelId);

	/**
	 * Returns an ordered range of all the cp instance option value rels where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance option value rels
	 */
	public java.util.List<CPInstanceOptionValueRel> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceOptionValueRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp instance option value rel in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	public CPInstanceOptionValueRel findByCPInstanceId_First(
			long CPInstanceId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceOptionValueRel> orderByComparator)
		throws NoSuchCPInstanceOptionValueRelException;

	/**
	 * Returns the first cp instance option value rel in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public CPInstanceOptionValueRel fetchByCPInstanceId_First(
		long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceOptionValueRel> orderByComparator);

	/**
	 * Removes all the cp instance option value rels where CPInstanceId = &#63; from the database.
	 *
	 * @param CPInstanceId the cp instance ID
	 */
	public void removeByCPInstanceId(long CPInstanceId);

	/**
	 * Returns the number of cp instance option value rels where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @return the number of matching cp instance option value rels
	 */
	public int countByCPInstanceId(long CPInstanceId);

	/**
	 * Returns an ordered range of all the cp instance option value rels where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance option value rels
	 */
	public java.util.List<CPInstanceOptionValueRel> findByCDORI_CII(
		long CPDefinitionOptionRelId, long CPInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceOptionValueRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	public CPInstanceOptionValueRel findByCDORI_CII_First(
			long CPDefinitionOptionRelId, long CPInstanceId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceOptionValueRel> orderByComparator)
		throws NoSuchCPInstanceOptionValueRelException;

	/**
	 * Returns the first cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public CPInstanceOptionValueRel fetchByCDORI_CII_First(
		long CPDefinitionOptionRelId, long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceOptionValueRel> orderByComparator);

	/**
	 * Removes all the cp instance option value rels where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63; from the database.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 */
	public void removeByCDORI_CII(
		long CPDefinitionOptionRelId, long CPInstanceId);

	/**
	 * Returns the number of cp instance option value rels where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the number of matching cp instance option value rels
	 */
	public int countByCDORI_CII(
		long CPDefinitionOptionRelId, long CPInstanceId);

	/**
	 * Returns the cp instance option value rel where CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; or throws a <code>NoSuchCPInstanceOptionValueRelException</code> if it could not be found.
	 *
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	public CPInstanceOptionValueRel findByCDOVRI_CII(
			long CPDefinitionOptionValueRelId, long CPInstanceId)
		throws NoSuchCPInstanceOptionValueRelException;

	/**
	 * Returns the cp instance option value rel where CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public CPInstanceOptionValueRel fetchByCDOVRI_CII(
		long CPDefinitionOptionValueRelId, long CPInstanceId,
		boolean useFinderCache);

	/**
	 * Removes the cp instance option value rel where CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; from the database.
	 *
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the cp instance option value rel that was removed
	 */
	public CPInstanceOptionValueRel removeByCDOVRI_CII(
			long CPDefinitionOptionValueRelId, long CPInstanceId)
		throws NoSuchCPInstanceOptionValueRelException;

	/**
	 * Returns the number of cp instance option value rels where CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the number of matching cp instance option value rels
	 */
	public int countByCDOVRI_CII(
		long CPDefinitionOptionValueRelId, long CPInstanceId);

	/**
	 * Returns the cp instance option value rel where CPDefinitionOptionRelId = &#63; and CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; or throws a <code>NoSuchCPInstanceOptionValueRelException</code> if it could not be found.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	public CPInstanceOptionValueRel findByCDORI_CDOVRI_CII(
			long CPDefinitionOptionRelId, long CPDefinitionOptionValueRelId,
			long CPInstanceId)
		throws NoSuchCPInstanceOptionValueRelException;

	/**
	 * Returns the cp instance option value rel where CPDefinitionOptionRelId = &#63; and CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public CPInstanceOptionValueRel fetchByCDORI_CDOVRI_CII(
		long CPDefinitionOptionRelId, long CPDefinitionOptionValueRelId,
		long CPInstanceId, boolean useFinderCache);

	/**
	 * Removes the cp instance option value rel where CPDefinitionOptionRelId = &#63; and CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; from the database.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the cp instance option value rel that was removed
	 */
	public CPInstanceOptionValueRel removeByCDORI_CDOVRI_CII(
			long CPDefinitionOptionRelId, long CPDefinitionOptionValueRelId,
			long CPInstanceId)
		throws NoSuchCPInstanceOptionValueRelException;

	/**
	 * Returns the number of cp instance option value rels where CPDefinitionOptionRelId = &#63; and CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the number of matching cp instance option value rels
	 */
	public int countByCDORI_CDOVRI_CII(
		long CPDefinitionOptionRelId, long CPDefinitionOptionValueRelId,
		long CPInstanceId);

	/**
	 * Creates a new cp instance option value rel with the primary key. Does not add the cp instance option value rel to the database.
	 *
	 * @param CPInstanceOptionValueRelId the primary key for the new cp instance option value rel
	 * @return the new cp instance option value rel
	 */
	public CPInstanceOptionValueRel create(long CPInstanceOptionValueRelId);

	/**
	 * Removes the cp instance option value rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPInstanceOptionValueRelId the primary key of the cp instance option value rel
	 * @return the cp instance option value rel that was removed
	 * @throws NoSuchCPInstanceOptionValueRelException if a cp instance option value rel with the primary key could not be found
	 */
	public CPInstanceOptionValueRel remove(long CPInstanceOptionValueRelId)
		throws NoSuchCPInstanceOptionValueRelException;

	public CPInstanceOptionValueRel updateImpl(
		CPInstanceOptionValueRel cpInstanceOptionValueRel);

	/**
	 * Returns the cp instance option value rel with the primary key or throws a <code>NoSuchCPInstanceOptionValueRelException</code> if it could not be found.
	 *
	 * @param CPInstanceOptionValueRelId the primary key of the cp instance option value rel
	 * @return the cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a cp instance option value rel with the primary key could not be found
	 */
	public CPInstanceOptionValueRel findByPrimaryKey(
			long CPInstanceOptionValueRelId)
		throws NoSuchCPInstanceOptionValueRelException;

	/**
	 * Returns the cp instance option value rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPInstanceOptionValueRelId the primary key of the cp instance option value rel
	 * @return the cp instance option value rel, or <code>null</code> if a cp instance option value rel with the primary key could not be found
	 */
	public CPInstanceOptionValueRel fetchByPrimaryKey(
		long CPInstanceOptionValueRelId);

	/**
	 * Returns the cp instance option value rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public default CPInstanceOptionValueRel fetchByUUID_G(
		String uuid, long groupId) {

		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp instance option value rel where CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public default CPInstanceOptionValueRel fetchByCDOVRI_CII(
		long CPDefinitionOptionValueRelId, long CPInstanceId) {

		return fetchByCDOVRI_CII(
			CPDefinitionOptionValueRelId, CPInstanceId, true);
	}

	/**
	 * Returns the cp instance option value rel where CPDefinitionOptionRelId = &#63; and CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public default CPInstanceOptionValueRel fetchByCDORI_CDOVRI_CII(
		long CPDefinitionOptionRelId, long CPDefinitionOptionValueRelId,
		long CPInstanceId) {

		return fetchByCDORI_CDOVRI_CII(
			CPDefinitionOptionRelId, CPDefinitionOptionValueRelId, CPInstanceId,
			true);
	}

	/**
	 * Returns all the cp instance option value rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp instance option value rels
	 */
	public default java.util.List<CPInstanceOptionValueRel> findByUuid(
		String uuid) {

		return findByUuid(
			uuid, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the cp instance option value rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @return the range of matching cp instance option value rels
	 */
	public default java.util.List<CPInstanceOptionValueRel> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance option value rels
	 */
	public default java.util.List<CPInstanceOptionValueRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceOptionValueRel> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the cp instance option value rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp instance option value rels
	 */
	public default java.util.List<CPInstanceOptionValueRel> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the cp instance option value rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @return the range of matching cp instance option value rels
	 */
	public default java.util.List<CPInstanceOptionValueRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance option value rels
	 */
	public default java.util.List<CPInstanceOptionValueRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceOptionValueRel> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the cp instance option value rels where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @return the matching cp instance option value rels
	 */
	public default java.util.List<CPInstanceOptionValueRel>
		findByCPDefinitionOptionRelId(long CPDefinitionOptionRelId) {

		return findByCPDefinitionOptionRelId(
			CPDefinitionOptionRelId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the cp instance option value rels where CPDefinitionOptionRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @return the range of matching cp instance option value rels
	 */
	public default java.util.List<CPInstanceOptionValueRel>
		findByCPDefinitionOptionRelId(
			long CPDefinitionOptionRelId, int start, int end) {

		return findByCPDefinitionOptionRelId(
			CPDefinitionOptionRelId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where CPDefinitionOptionRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance option value rels
	 */
	public default java.util.List<CPInstanceOptionValueRel>
		findByCPDefinitionOptionRelId(
			long CPDefinitionOptionRelId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceOptionValueRel> orderByComparator) {

		return findByCPDefinitionOptionRelId(
			CPDefinitionOptionRelId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the cp instance option value rels where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @return the matching cp instance option value rels
	 */
	public default java.util.List<CPInstanceOptionValueRel> findByCPInstanceId(
		long CPInstanceId) {

		return findByCPInstanceId(
			CPInstanceId, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the cp instance option value rels where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @return the range of matching cp instance option value rels
	 */
	public default java.util.List<CPInstanceOptionValueRel> findByCPInstanceId(
		long CPInstanceId, int start, int end) {

		return findByCPInstanceId(CPInstanceId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance option value rels
	 */
	public default java.util.List<CPInstanceOptionValueRel> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceOptionValueRel> orderByComparator) {

		return findByCPInstanceId(
			CPInstanceId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the cp instance option value rels where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the matching cp instance option value rels
	 */
	public default java.util.List<CPInstanceOptionValueRel> findByCDORI_CII(
		long CPDefinitionOptionRelId, long CPInstanceId) {

		return findByCDORI_CII(
			CPDefinitionOptionRelId, CPInstanceId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the cp instance option value rels where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @return the range of matching cp instance option value rels
	 */
	public default java.util.List<CPInstanceOptionValueRel> findByCDORI_CII(
		long CPDefinitionOptionRelId, long CPInstanceId, int start, int end) {

		return findByCDORI_CII(
			CPDefinitionOptionRelId, CPInstanceId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance option value rels
	 */
	public default java.util.List<CPInstanceOptionValueRel> findByCDORI_CII(
		long CPDefinitionOptionRelId, long CPInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceOptionValueRel> orderByComparator) {

		return findByCDORI_CII(
			CPDefinitionOptionRelId, CPInstanceId, start, end,
			orderByComparator, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:71316452