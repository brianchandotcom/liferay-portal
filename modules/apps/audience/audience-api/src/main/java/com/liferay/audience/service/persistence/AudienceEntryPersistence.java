/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.service.persistence;

import com.liferay.audience.exception.NoSuchAudienceEntryException;
import com.liferay.audience.model.AudienceEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the audience entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AudienceEntryUtil
 * @generated
 */
@ProviderType
public interface AudienceEntryPersistence
	extends BasePersistence<AudienceEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AudienceEntryUtil} to access the audience entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns an ordered range of all the audience entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching audience entries
	 */
	public java.util.List<AudienceEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AudienceEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first audience entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audience entry
	 * @throws NoSuchAudienceEntryException if a matching audience entry could not be found
	 */
	public AudienceEntry findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AudienceEntry>
				orderByComparator)
		throws NoSuchAudienceEntryException;

	/**
	 * Returns the first audience entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audience entry, or <code>null</code> if a matching audience entry could not be found
	 */
	public AudienceEntry fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AudienceEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the audience entries that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audience entries that the user has permission to view
	 */
	public java.util.List<AudienceEntry> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AudienceEntry>
			orderByComparator);

	/**
	 * Removes all the audience entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of audience entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching audience entries
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the number of audience entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching audience entries that the user has permission to view
	 */
	public int filterCountByCompanyId(long companyId);

	/**
	 * Returns all the audience entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching audience entries
	 */
	public java.util.List<AudienceEntry> findByC_LikeN(
		long companyId, String name);

	/**
	 * Returns a range of all the audience entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @return the range of matching audience entries
	 */
	public java.util.List<AudienceEntry> findByC_LikeN(
		long companyId, String name, int start, int end);

	/**
	 * Returns an ordered range of all the audience entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audience entries
	 */
	public java.util.List<AudienceEntry> findByC_LikeN(
		long companyId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AudienceEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the audience entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching audience entries
	 */
	public java.util.List<AudienceEntry> findByC_LikeN(
		long companyId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AudienceEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first audience entry in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audience entry
	 * @throws NoSuchAudienceEntryException if a matching audience entry could not be found
	 */
	public AudienceEntry findByC_LikeN_First(
			long companyId, String name,
			com.liferay.portal.kernel.util.OrderByComparator<AudienceEntry>
				orderByComparator)
		throws NoSuchAudienceEntryException;

	/**
	 * Returns the first audience entry in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audience entry, or <code>null</code> if a matching audience entry could not be found
	 */
	public AudienceEntry fetchByC_LikeN_First(
		long companyId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<AudienceEntry>
			orderByComparator);

	/**
	 * Returns all the audience entries that the user has permission to view where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching audience entries that the user has permission to view
	 */
	public java.util.List<AudienceEntry> filterFindByC_LikeN(
		long companyId, String name);

	/**
	 * Returns a range of all the audience entries that the user has permission to view where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @return the range of matching audience entries that the user has permission to view
	 */
	public java.util.List<AudienceEntry> filterFindByC_LikeN(
		long companyId, String name, int start, int end);

	/**
	 * Returns an ordered range of all the audience entries that the user has permissions to view where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audience entries that the user has permission to view
	 */
	public java.util.List<AudienceEntry> filterFindByC_LikeN(
		long companyId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AudienceEntry>
			orderByComparator);

	/**
	 * Removes all the audience entries where companyId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 */
	public void removeByC_LikeN(long companyId, String name);

	/**
	 * Returns the number of audience entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching audience entries
	 */
	public int countByC_LikeN(long companyId, String name);

	/**
	 * Returns the number of audience entries that the user has permission to view where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching audience entries that the user has permission to view
	 */
	public int filterCountByC_LikeN(long companyId, String name);

	/**
	 * Returns the audience entry where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchAudienceEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching audience entry
	 * @throws NoSuchAudienceEntryException if a matching audience entry could not be found
	 */
	public AudienceEntry findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchAudienceEntryException;

	/**
	 * Returns the audience entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching audience entry, or <code>null</code> if a matching audience entry could not be found
	 */
	public AudienceEntry fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache);

	/**
	 * Removes the audience entry where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the audience entry that was removed
	 */
	public AudienceEntry removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchAudienceEntryException;

	/**
	 * Returns the number of audience entries where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching audience entries
	 */
	public int countByERC_C(String externalReferenceCode, long companyId);

	/**
	 * Creates a new audience entry with the primary key. Does not add the audience entry to the database.
	 *
	 * @param audienceEntryId the primary key for the new audience entry
	 * @return the new audience entry
	 */
	public AudienceEntry create(long audienceEntryId);

	/**
	 * Removes the audience entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param audienceEntryId the primary key of the audience entry
	 * @return the audience entry that was removed
	 * @throws NoSuchAudienceEntryException if a audience entry with the primary key could not be found
	 */
	public AudienceEntry remove(long audienceEntryId)
		throws NoSuchAudienceEntryException;

	public AudienceEntry updateImpl(AudienceEntry audienceEntry);

	/**
	 * Returns the audience entry with the primary key or throws a <code>NoSuchAudienceEntryException</code> if it could not be found.
	 *
	 * @param audienceEntryId the primary key of the audience entry
	 * @return the audience entry
	 * @throws NoSuchAudienceEntryException if a audience entry with the primary key could not be found
	 */
	public AudienceEntry findByPrimaryKey(long audienceEntryId)
		throws NoSuchAudienceEntryException;

	/**
	 * Returns the audience entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param audienceEntryId the primary key of the audience entry
	 * @return the audience entry, or <code>null</code> if a audience entry with the primary key could not be found
	 */
	public AudienceEntry fetchByPrimaryKey(long audienceEntryId);

	/**
	 * Returns the audience entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching audience entry, or <code>null</code> if a matching audience entry could not be found
	 */
	public default AudienceEntry fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return fetchByERC_C(externalReferenceCode, companyId, true);
	}

	/**
	 * Returns all the audience entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching audience entries
	 */
	public default java.util.List<AudienceEntry> findByCompanyId(
		long companyId) {

		return findByCompanyId(
			companyId, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the audience entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @return the range of matching audience entries
	 */
	public default java.util.List<AudienceEntry> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the audience entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audience entries
	 */
	public default java.util.List<AudienceEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AudienceEntry>
			orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the audience entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching audience entries that the user has permission to view
	 */
	public default java.util.List<AudienceEntry> filterFindByCompanyId(
		long companyId) {

		return filterFindByCompanyId(
			companyId, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the audience entries that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @return the range of matching audience entries that the user has permission to view
	 */
	public default java.util.List<AudienceEntry> filterFindByCompanyId(
		long companyId, int start, int end) {

		return filterFindByCompanyId(companyId, start, end, null);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-488266632