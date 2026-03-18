/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.service.persistence;

import com.liferay.commerce.tax.exception.NoSuchTaxCategoryMappingException;
import com.liferay.commerce.tax.model.CommerceTaxCategoryMapping;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce tax category mapping service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceTaxCategoryMappingUtil
 * @generated
 */
@ProviderType
public interface CommerceTaxCategoryMappingPersistence
	extends BasePersistence<CommerceTaxCategoryMapping> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceTaxCategoryMappingUtil} to access the commerce tax category mapping persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce tax category mappings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce tax category mappings
	 */
	public java.util.List<CommerceTaxCategoryMapping> findByUuid(String uuid);

	/**
	 * Returns a range of all the commerce tax category mappings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @return the range of matching commerce tax category mappings
	 */
	public java.util.List<CommerceTaxCategoryMapping> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce tax category mappings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tax category mappings
	 */
	public java.util.List<CommerceTaxCategoryMapping> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceTaxCategoryMapping> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce tax category mappings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tax category mappings
	 */
	public java.util.List<CommerceTaxCategoryMapping> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceTaxCategoryMapping> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce tax category mapping in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	public CommerceTaxCategoryMapping findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceTaxCategoryMapping> orderByComparator)
		throws NoSuchTaxCategoryMappingException;

	/**
	 * Returns the first commerce tax category mapping in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public CommerceTaxCategoryMapping fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceTaxCategoryMapping> orderByComparator);

	/**
	 * Returns the last commerce tax category mapping in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	public CommerceTaxCategoryMapping findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceTaxCategoryMapping> orderByComparator)
		throws NoSuchTaxCategoryMappingException;

	/**
	 * Returns the last commerce tax category mapping in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public CommerceTaxCategoryMapping fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceTaxCategoryMapping> orderByComparator);

	/**
	 * Returns the commerce tax category mappings before and after the current commerce tax category mapping in the ordered set where uuid = &#63;.
	 *
	 * @param commerceTaxCategoryMappingId the primary key of the current commerce tax category mapping
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a commerce tax category mapping with the primary key could not be found
	 */
	public CommerceTaxCategoryMapping[] findByUuid_PrevAndNext(
			long commerceTaxCategoryMappingId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceTaxCategoryMapping> orderByComparator)
		throws NoSuchTaxCategoryMappingException;

	/**
	 * Removes all the commerce tax category mappings where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce tax category mappings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce tax category mappings
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the commerce tax category mapping where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchTaxCategoryMappingException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	public CommerceTaxCategoryMapping findByUUID_G(String uuid, long groupId)
		throws NoSuchTaxCategoryMappingException;

	/**
	 * Returns the commerce tax category mapping where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public CommerceTaxCategoryMapping fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the commerce tax category mapping where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public CommerceTaxCategoryMapping fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the commerce tax category mapping where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce tax category mapping that was removed
	 */
	public CommerceTaxCategoryMapping removeByUUID_G(String uuid, long groupId)
		throws NoSuchTaxCategoryMappingException;

	/**
	 * Returns the number of commerce tax category mappings where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce tax category mappings
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the commerce tax category mappings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce tax category mappings
	 */
	public java.util.List<CommerceTaxCategoryMapping> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce tax category mappings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @return the range of matching commerce tax category mappings
	 */
	public java.util.List<CommerceTaxCategoryMapping> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce tax category mappings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tax category mappings
	 */
	public java.util.List<CommerceTaxCategoryMapping> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceTaxCategoryMapping> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce tax category mappings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tax category mappings
	 */
	public java.util.List<CommerceTaxCategoryMapping> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceTaxCategoryMapping> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce tax category mapping in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	public CommerceTaxCategoryMapping findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceTaxCategoryMapping> orderByComparator)
		throws NoSuchTaxCategoryMappingException;

	/**
	 * Returns the first commerce tax category mapping in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public CommerceTaxCategoryMapping fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceTaxCategoryMapping> orderByComparator);

	/**
	 * Returns the last commerce tax category mapping in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	public CommerceTaxCategoryMapping findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceTaxCategoryMapping> orderByComparator)
		throws NoSuchTaxCategoryMappingException;

	/**
	 * Returns the last commerce tax category mapping in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public CommerceTaxCategoryMapping fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceTaxCategoryMapping> orderByComparator);

	/**
	 * Returns the commerce tax category mappings before and after the current commerce tax category mapping in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceTaxCategoryMappingId the primary key of the current commerce tax category mapping
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a commerce tax category mapping with the primary key could not be found
	 */
	public CommerceTaxCategoryMapping[] findByUuid_C_PrevAndNext(
			long commerceTaxCategoryMappingId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceTaxCategoryMapping> orderByComparator)
		throws NoSuchTaxCategoryMappingException;

	/**
	 * Removes all the commerce tax category mappings where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce tax category mappings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce tax category mappings
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the commerce tax category mappings where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the matching commerce tax category mappings
	 */
	public java.util.List<CommerceTaxCategoryMapping> findByCommerceTaxMethodId(
		long commerceTaxMethodId);

	/**
	 * Returns a range of all the commerce tax category mappings where commerceTaxMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @return the range of matching commerce tax category mappings
	 */
	public java.util.List<CommerceTaxCategoryMapping> findByCommerceTaxMethodId(
		long commerceTaxMethodId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce tax category mappings where commerceTaxMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tax category mappings
	 */
	public java.util.List<CommerceTaxCategoryMapping> findByCommerceTaxMethodId(
		long commerceTaxMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceTaxCategoryMapping> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce tax category mappings where commerceTaxMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tax category mappings
	 */
	public java.util.List<CommerceTaxCategoryMapping> findByCommerceTaxMethodId(
		long commerceTaxMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceTaxCategoryMapping> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce tax category mapping in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	public CommerceTaxCategoryMapping findByCommerceTaxMethodId_First(
			long commerceTaxMethodId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceTaxCategoryMapping> orderByComparator)
		throws NoSuchTaxCategoryMappingException;

	/**
	 * Returns the first commerce tax category mapping in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public CommerceTaxCategoryMapping fetchByCommerceTaxMethodId_First(
		long commerceTaxMethodId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceTaxCategoryMapping> orderByComparator);

	/**
	 * Returns the last commerce tax category mapping in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	public CommerceTaxCategoryMapping findByCommerceTaxMethodId_Last(
			long commerceTaxMethodId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceTaxCategoryMapping> orderByComparator)
		throws NoSuchTaxCategoryMappingException;

	/**
	 * Returns the last commerce tax category mapping in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public CommerceTaxCategoryMapping fetchByCommerceTaxMethodId_Last(
		long commerceTaxMethodId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceTaxCategoryMapping> orderByComparator);

	/**
	 * Returns the commerce tax category mappings before and after the current commerce tax category mapping in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxCategoryMappingId the primary key of the current commerce tax category mapping
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a commerce tax category mapping with the primary key could not be found
	 */
	public CommerceTaxCategoryMapping[] findByCommerceTaxMethodId_PrevAndNext(
			long commerceTaxCategoryMappingId, long commerceTaxMethodId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceTaxCategoryMapping> orderByComparator)
		throws NoSuchTaxCategoryMappingException;

	/**
	 * Removes all the commerce tax category mappings where commerceTaxMethodId = &#63; from the database.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 */
	public void removeByCommerceTaxMethodId(long commerceTaxMethodId);

	/**
	 * Returns the number of commerce tax category mappings where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the number of matching commerce tax category mappings
	 */
	public int countByCommerceTaxMethodId(long commerceTaxMethodId);

	/**
	 * Returns the commerce tax category mapping where commerceTaxMethodId = &#63; and CPTaxCategoryId = &#63; or throws a <code>NoSuchTaxCategoryMappingException</code> if it could not be found.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	public CommerceTaxCategoryMapping findByC_C(
			long commerceTaxMethodId, long CPTaxCategoryId)
		throws NoSuchTaxCategoryMappingException;

	/**
	 * Returns the commerce tax category mapping where commerceTaxMethodId = &#63; and CPTaxCategoryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public CommerceTaxCategoryMapping fetchByC_C(
		long commerceTaxMethodId, long CPTaxCategoryId);

	/**
	 * Returns the commerce tax category mapping where commerceTaxMethodId = &#63; and CPTaxCategoryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public CommerceTaxCategoryMapping fetchByC_C(
		long commerceTaxMethodId, long CPTaxCategoryId, boolean useFinderCache);

	/**
	 * Removes the commerce tax category mapping where commerceTaxMethodId = &#63; and CPTaxCategoryId = &#63; from the database.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the commerce tax category mapping that was removed
	 */
	public CommerceTaxCategoryMapping removeByC_C(
			long commerceTaxMethodId, long CPTaxCategoryId)
		throws NoSuchTaxCategoryMappingException;

	/**
	 * Returns the number of commerce tax category mappings where commerceTaxMethodId = &#63; and CPTaxCategoryId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the number of matching commerce tax category mappings
	 */
	public int countByC_C(long commerceTaxMethodId, long CPTaxCategoryId);

	/**
	 * Returns the commerce tax category mapping where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchTaxCategoryMappingException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	public CommerceTaxCategoryMapping findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchTaxCategoryMappingException;

	/**
	 * Returns the commerce tax category mapping where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public CommerceTaxCategoryMapping fetchByERC_C(
		String externalReferenceCode, long companyId);

	/**
	 * Returns the commerce tax category mapping where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public CommerceTaxCategoryMapping fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache);

	/**
	 * Removes the commerce tax category mapping where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the commerce tax category mapping that was removed
	 */
	public CommerceTaxCategoryMapping removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchTaxCategoryMappingException;

	/**
	 * Returns the number of commerce tax category mappings where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching commerce tax category mappings
	 */
	public int countByERC_C(String externalReferenceCode, long companyId);

	/**
	 * Caches the commerce tax category mapping in the entity cache if it is enabled.
	 *
	 * @param commerceTaxCategoryMapping the commerce tax category mapping
	 */
	public void cacheResult(
		CommerceTaxCategoryMapping commerceTaxCategoryMapping);

	/**
	 * Caches the commerce tax category mappings in the entity cache if it is enabled.
	 *
	 * @param commerceTaxCategoryMappings the commerce tax category mappings
	 */
	public void cacheResult(
		java.util.List<CommerceTaxCategoryMapping> commerceTaxCategoryMappings);

	/**
	 * Creates a new commerce tax category mapping with the primary key. Does not add the commerce tax category mapping to the database.
	 *
	 * @param commerceTaxCategoryMappingId the primary key for the new commerce tax category mapping
	 * @return the new commerce tax category mapping
	 */
	public CommerceTaxCategoryMapping create(long commerceTaxCategoryMappingId);

	/**
	 * Removes the commerce tax category mapping with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceTaxCategoryMappingId the primary key of the commerce tax category mapping
	 * @return the commerce tax category mapping that was removed
	 * @throws NoSuchTaxCategoryMappingException if a commerce tax category mapping with the primary key could not be found
	 */
	public CommerceTaxCategoryMapping remove(long commerceTaxCategoryMappingId)
		throws NoSuchTaxCategoryMappingException;

	public CommerceTaxCategoryMapping updateImpl(
		CommerceTaxCategoryMapping commerceTaxCategoryMapping);

	/**
	 * Returns the commerce tax category mapping with the primary key or throws a <code>NoSuchTaxCategoryMappingException</code> if it could not be found.
	 *
	 * @param commerceTaxCategoryMappingId the primary key of the commerce tax category mapping
	 * @return the commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a commerce tax category mapping with the primary key could not be found
	 */
	public CommerceTaxCategoryMapping findByPrimaryKey(
			long commerceTaxCategoryMappingId)
		throws NoSuchTaxCategoryMappingException;

	/**
	 * Returns the commerce tax category mapping with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceTaxCategoryMappingId the primary key of the commerce tax category mapping
	 * @return the commerce tax category mapping, or <code>null</code> if a commerce tax category mapping with the primary key could not be found
	 */
	public CommerceTaxCategoryMapping fetchByPrimaryKey(
		long commerceTaxCategoryMappingId);

	/**
	 * Returns all the commerce tax category mappings.
	 *
	 * @return the commerce tax category mappings
	 */
	public java.util.List<CommerceTaxCategoryMapping> findAll();

	/**
	 * Returns a range of all the commerce tax category mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @return the range of commerce tax category mappings
	 */
	public java.util.List<CommerceTaxCategoryMapping> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce tax category mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce tax category mappings
	 */
	public java.util.List<CommerceTaxCategoryMapping> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceTaxCategoryMapping> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce tax category mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce tax category mappings
	 */
	public java.util.List<CommerceTaxCategoryMapping> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceTaxCategoryMapping> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce tax category mappings from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce tax category mappings.
	 *
	 * @return the number of commerce tax category mappings
	 */
	public int countAll();

}
// LIFERAY-SERVICE-BUILDER-HASH:597152426