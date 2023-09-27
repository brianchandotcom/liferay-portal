/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.search.experiences.exception.NoSuchSXPBlueprintException;
import com.liferay.search.experiences.model.SXPBlueprint;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the sxp blueprint service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SXPBlueprintUtil
 * @generated
 */
@ProviderType
public interface SXPBlueprintPersistence extends BasePersistence<SXPBlueprint> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SXPBlueprintUtil} to access the sxp blueprint persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the sxp blueprints where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching sxp blueprints
	 */
	public java.util.List<SXPBlueprint> findByUuid(String uuid);

	/**
	 * Returns a range of all the sxp blueprints where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @return the range of matching sxp blueprints
	 */
	public java.util.List<SXPBlueprint> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the sxp blueprints where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sxp blueprints
	 */
	public java.util.List<SXPBlueprint> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sxp blueprints where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sxp blueprints
	 */
	public java.util.List<SXPBlueprint> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sxp blueprint in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a matching sxp blueprint could not be found
	 */
	public SXPBlueprint findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
				orderByComparator)
		throws NoSuchSXPBlueprintException;

	/**
	 * Returns the first sxp blueprint in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp blueprint, or <code>null</code> if a matching sxp blueprint could not be found
	 */
	public SXPBlueprint fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
			orderByComparator);

	/**
	 * Returns the last sxp blueprint in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a matching sxp blueprint could not be found
	 */
	public SXPBlueprint findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
				orderByComparator)
		throws NoSuchSXPBlueprintException;

	/**
	 * Returns the last sxp blueprint in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp blueprint, or <code>null</code> if a matching sxp blueprint could not be found
	 */
	public SXPBlueprint fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
			orderByComparator);

	/**
	 * Returns the sxp blueprints before and after the current sxp blueprint in the ordered set where uuid = &#63;.
	 *
	 * @param sxpBlueprintId the primary key of the current sxp blueprint
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a sxp blueprint with the primary key could not be found
	 */
	public SXPBlueprint[] findByUuid_PrevAndNext(
			long sxpBlueprintId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
				orderByComparator)
		throws NoSuchSXPBlueprintException;

	/**
	 * Returns all the sxp blueprints that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching sxp blueprints that the user has permission to view
	 */
	public java.util.List<SXPBlueprint> filterFindByUuid(String uuid);

	/**
	 * Returns a range of all the sxp blueprints that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @return the range of matching sxp blueprints that the user has permission to view
	 */
	public java.util.List<SXPBlueprint> filterFindByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the sxp blueprints that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sxp blueprints that the user has permission to view
	 */
	public java.util.List<SXPBlueprint> filterFindByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
			orderByComparator);

	/**
	 * Returns the sxp blueprints before and after the current sxp blueprint in the ordered set of sxp blueprints that the user has permission to view where uuid = &#63;.
	 *
	 * @param sxpBlueprintId the primary key of the current sxp blueprint
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a sxp blueprint with the primary key could not be found
	 */
	public SXPBlueprint[] filterFindByUuid_PrevAndNext(
			long sxpBlueprintId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
				orderByComparator)
		throws NoSuchSXPBlueprintException;

	/**
	 * Removes all the sxp blueprints where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of sxp blueprints where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching sxp blueprints
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the number of sxp blueprints that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching sxp blueprints that the user has permission to view
	 */
	public int filterCountByUuid(String uuid);

	/**
	 * Returns all the sxp blueprints where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching sxp blueprints
	 */
	public java.util.List<SXPBlueprint> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the sxp blueprints where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @return the range of matching sxp blueprints
	 */
	public java.util.List<SXPBlueprint> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the sxp blueprints where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sxp blueprints
	 */
	public java.util.List<SXPBlueprint> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sxp blueprints where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sxp blueprints
	 */
	public java.util.List<SXPBlueprint> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sxp blueprint in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a matching sxp blueprint could not be found
	 */
	public SXPBlueprint findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
				orderByComparator)
		throws NoSuchSXPBlueprintException;

	/**
	 * Returns the first sxp blueprint in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp blueprint, or <code>null</code> if a matching sxp blueprint could not be found
	 */
	public SXPBlueprint fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
			orderByComparator);

	/**
	 * Returns the last sxp blueprint in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a matching sxp blueprint could not be found
	 */
	public SXPBlueprint findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
				orderByComparator)
		throws NoSuchSXPBlueprintException;

	/**
	 * Returns the last sxp blueprint in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp blueprint, or <code>null</code> if a matching sxp blueprint could not be found
	 */
	public SXPBlueprint fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
			orderByComparator);

	/**
	 * Returns the sxp blueprints before and after the current sxp blueprint in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param sxpBlueprintId the primary key of the current sxp blueprint
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a sxp blueprint with the primary key could not be found
	 */
	public SXPBlueprint[] findByUuid_C_PrevAndNext(
			long sxpBlueprintId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
				orderByComparator)
		throws NoSuchSXPBlueprintException;

	/**
	 * Returns all the sxp blueprints that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching sxp blueprints that the user has permission to view
	 */
	public java.util.List<SXPBlueprint> filterFindByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the sxp blueprints that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @return the range of matching sxp blueprints that the user has permission to view
	 */
	public java.util.List<SXPBlueprint> filterFindByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the sxp blueprints that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sxp blueprints that the user has permission to view
	 */
	public java.util.List<SXPBlueprint> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
			orderByComparator);

	/**
	 * Returns the sxp blueprints before and after the current sxp blueprint in the ordered set of sxp blueprints that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param sxpBlueprintId the primary key of the current sxp blueprint
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a sxp blueprint with the primary key could not be found
	 */
	public SXPBlueprint[] filterFindByUuid_C_PrevAndNext(
			long sxpBlueprintId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
				orderByComparator)
		throws NoSuchSXPBlueprintException;

	/**
	 * Removes all the sxp blueprints where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of sxp blueprints where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching sxp blueprints
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of sxp blueprints that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching sxp blueprints that the user has permission to view
	 */
	public int filterCountByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the sxp blueprints where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching sxp blueprints
	 */
	public java.util.List<SXPBlueprint> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the sxp blueprints where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @return the range of matching sxp blueprints
	 */
	public java.util.List<SXPBlueprint> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the sxp blueprints where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sxp blueprints
	 */
	public java.util.List<SXPBlueprint> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sxp blueprints where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sxp blueprints
	 */
	public java.util.List<SXPBlueprint> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sxp blueprint in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a matching sxp blueprint could not be found
	 */
	public SXPBlueprint findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
				orderByComparator)
		throws NoSuchSXPBlueprintException;

	/**
	 * Returns the first sxp blueprint in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp blueprint, or <code>null</code> if a matching sxp blueprint could not be found
	 */
	public SXPBlueprint fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
			orderByComparator);

	/**
	 * Returns the last sxp blueprint in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a matching sxp blueprint could not be found
	 */
	public SXPBlueprint findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
				orderByComparator)
		throws NoSuchSXPBlueprintException;

	/**
	 * Returns the last sxp blueprint in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp blueprint, or <code>null</code> if a matching sxp blueprint could not be found
	 */
	public SXPBlueprint fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
			orderByComparator);

	/**
	 * Returns the sxp blueprints before and after the current sxp blueprint in the ordered set where companyId = &#63;.
	 *
	 * @param sxpBlueprintId the primary key of the current sxp blueprint
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a sxp blueprint with the primary key could not be found
	 */
	public SXPBlueprint[] findByCompanyId_PrevAndNext(
			long sxpBlueprintId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
				orderByComparator)
		throws NoSuchSXPBlueprintException;

	/**
	 * Returns all the sxp blueprints that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching sxp blueprints that the user has permission to view
	 */
	public java.util.List<SXPBlueprint> filterFindByCompanyId(long companyId);

	/**
	 * Returns a range of all the sxp blueprints that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @return the range of matching sxp blueprints that the user has permission to view
	 */
	public java.util.List<SXPBlueprint> filterFindByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the sxp blueprints that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sxp blueprints that the user has permission to view
	 */
	public java.util.List<SXPBlueprint> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
			orderByComparator);

	/**
	 * Returns the sxp blueprints before and after the current sxp blueprint in the ordered set of sxp blueprints that the user has permission to view where companyId = &#63;.
	 *
	 * @param sxpBlueprintId the primary key of the current sxp blueprint
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a sxp blueprint with the primary key could not be found
	 */
	public SXPBlueprint[] filterFindByCompanyId_PrevAndNext(
			long sxpBlueprintId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
				orderByComparator)
		throws NoSuchSXPBlueprintException;

	/**
	 * Removes all the sxp blueprints where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of sxp blueprints where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching sxp blueprints
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the number of sxp blueprints that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching sxp blueprints that the user has permission to view
	 */
	public int filterCountByCompanyId(long companyId);

	/**
	 * Returns the sxp blueprint where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchSXPBlueprintException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a matching sxp blueprint could not be found
	 */
	public SXPBlueprint findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchSXPBlueprintException;

	/**
	 * Returns the sxp blueprint where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching sxp blueprint, or <code>null</code> if a matching sxp blueprint could not be found
	 */
	public SXPBlueprint fetchByERC_C(
		String externalReferenceCode, long companyId);

	/**
	 * Returns the sxp blueprint where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching sxp blueprint, or <code>null</code> if a matching sxp blueprint could not be found
	 */
	public SXPBlueprint fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache);

	/**
	 * Removes the sxp blueprint where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the sxp blueprint that was removed
	 */
	public SXPBlueprint removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchSXPBlueprintException;

	/**
	 * Returns the number of sxp blueprints where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching sxp blueprints
	 */
	public int countByERC_C(String externalReferenceCode, long companyId);

	/**
	 * Caches the sxp blueprint in the entity cache if it is enabled.
	 *
	 * @param sxpBlueprint the sxp blueprint
	 */
	public void cacheResult(SXPBlueprint sxpBlueprint);

	/**
	 * Caches the sxp blueprints in the entity cache if it is enabled.
	 *
	 * @param sxpBlueprints the sxp blueprints
	 */
	public void cacheResult(java.util.List<SXPBlueprint> sxpBlueprints);

	/**
	 * Creates a new sxp blueprint with the primary key. Does not add the sxp blueprint to the database.
	 *
	 * @param sxpBlueprintId the primary key for the new sxp blueprint
	 * @return the new sxp blueprint
	 */
	public SXPBlueprint create(long sxpBlueprintId);

	/**
	 * Removes the sxp blueprint with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param sxpBlueprintId the primary key of the sxp blueprint
	 * @return the sxp blueprint that was removed
	 * @throws NoSuchSXPBlueprintException if a sxp blueprint with the primary key could not be found
	 */
	public SXPBlueprint remove(long sxpBlueprintId)
		throws NoSuchSXPBlueprintException;

	public SXPBlueprint updateImpl(SXPBlueprint sxpBlueprint);

	/**
	 * Returns the sxp blueprint with the primary key or throws a <code>NoSuchSXPBlueprintException</code> if it could not be found.
	 *
	 * @param sxpBlueprintId the primary key of the sxp blueprint
	 * @return the sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a sxp blueprint with the primary key could not be found
	 */
	public SXPBlueprint findByPrimaryKey(long sxpBlueprintId)
		throws NoSuchSXPBlueprintException;

	/**
	 * Returns the sxp blueprint with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param sxpBlueprintId the primary key of the sxp blueprint
	 * @return the sxp blueprint, or <code>null</code> if a sxp blueprint with the primary key could not be found
	 */
	public SXPBlueprint fetchByPrimaryKey(long sxpBlueprintId);

	/**
	 * Returns all the sxp blueprints.
	 *
	 * @return the sxp blueprints
	 */
	public java.util.List<SXPBlueprint> findAll();

	/**
	 * Returns a range of all the sxp blueprints.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @return the range of sxp blueprints
	 */
	public java.util.List<SXPBlueprint> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the sxp blueprints.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of sxp blueprints
	 */
	public java.util.List<SXPBlueprint> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sxp blueprints.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of sxp blueprints
	 */
	public java.util.List<SXPBlueprint> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPBlueprint>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the sxp blueprints from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of sxp blueprints.
	 *
	 * @return the number of sxp blueprints
	 */
	public int countAll();

}