/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audiences.service.persistence;

import com.liferay.audiences.exception.NoSuchAudiencesEntryException;
import com.liferay.audiences.model.AudiencesEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the audiences entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AudiencesEntryUtil
 * @generated
 */
@ProviderType
public interface AudiencesEntryPersistence
	extends BasePersistence<AudiencesEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AudiencesEntryUtil} to access the audiences entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns an ordered range of all the audiences entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audiences.model.impl.AudiencesEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audiences entries
	 * @param end the upper bound of the range of audiences entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching audiences entries
	 */
	public java.util.List<AudiencesEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AudiencesEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first audiences entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audiences entry
	 * @throws NoSuchAudiencesEntryException if a matching audiences entry could not be found
	 */
	public AudiencesEntry findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AudiencesEntry>
				orderByComparator)
		throws NoSuchAudiencesEntryException;

	/**
	 * Returns the first audiences entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audiences entry, or <code>null</code> if a matching audiences entry could not be found
	 */
	public AudiencesEntry fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AudiencesEntry>
			orderByComparator);

	/**
	 * Removes all the audiences entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of audiences entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching audiences entries
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the audiences entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching audiences entries
	 */
	public java.util.List<AudiencesEntry> findByC_LikeN(
		long companyId, String name);

	/**
	 * Returns a range of all the audiences entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audiences.model.impl.AudiencesEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of audiences entries
	 * @param end the upper bound of the range of audiences entries (not inclusive)
	 * @return the range of matching audiences entries
	 */
	public java.util.List<AudiencesEntry> findByC_LikeN(
		long companyId, String name, int start, int end);

	/**
	 * Returns an ordered range of all the audiences entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audiences.model.impl.AudiencesEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of audiences entries
	 * @param end the upper bound of the range of audiences entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audiences entries
	 */
	public java.util.List<AudiencesEntry> findByC_LikeN(
		long companyId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AudiencesEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the audiences entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audiences.model.impl.AudiencesEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of audiences entries
	 * @param end the upper bound of the range of audiences entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching audiences entries
	 */
	public java.util.List<AudiencesEntry> findByC_LikeN(
		long companyId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AudiencesEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first audiences entry in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audiences entry
	 * @throws NoSuchAudiencesEntryException if a matching audiences entry could not be found
	 */
	public AudiencesEntry findByC_LikeN_First(
			long companyId, String name,
			com.liferay.portal.kernel.util.OrderByComparator<AudiencesEntry>
				orderByComparator)
		throws NoSuchAudiencesEntryException;

	/**
	 * Returns the first audiences entry in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audiences entry, or <code>null</code> if a matching audiences entry could not be found
	 */
	public AudiencesEntry fetchByC_LikeN_First(
		long companyId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<AudiencesEntry>
			orderByComparator);

	/**
	 * Removes all the audiences entries where companyId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 */
	public void removeByC_LikeN(long companyId, String name);

	/**
	 * Returns the number of audiences entries where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching audiences entries
	 */
	public int countByC_LikeN(long companyId, String name);

	/**
	 * Returns the audiences entry where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchAudiencesEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching audiences entry
	 * @throws NoSuchAudiencesEntryException if a matching audiences entry could not be found
	 */
	public AudiencesEntry findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchAudiencesEntryException;

	/**
	 * Returns the audiences entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching audiences entry, or <code>null</code> if a matching audiences entry could not be found
	 */
	public AudiencesEntry fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache);

	/**
	 * Removes the audiences entry where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the audiences entry that was removed
	 */
	public AudiencesEntry removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchAudiencesEntryException;

	/**
	 * Returns the number of audiences entries where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching audiences entries
	 */
	public int countByERC_C(String externalReferenceCode, long companyId);

	/**
	 * Creates a new audiences entry with the primary key. Does not add the audiences entry to the database.
	 *
	 * @param audiencesEntryId the primary key for the new audiences entry
	 * @return the new audiences entry
	 */
	public AudiencesEntry create(long audiencesEntryId);

	/**
	 * Removes the audiences entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param audiencesEntryId the primary key of the audiences entry
	 * @return the audiences entry that was removed
	 * @throws NoSuchAudiencesEntryException if a audiences entry with the primary key could not be found
	 */
	public AudiencesEntry remove(long audiencesEntryId)
		throws NoSuchAudiencesEntryException;

	public AudiencesEntry updateImpl(AudiencesEntry audiencesEntry);

	/**
	 * Returns the audiences entry with the primary key or throws a <code>NoSuchAudiencesEntryException</code> if it could not be found.
	 *
	 * @param audiencesEntryId the primary key of the audiences entry
	 * @return the audiences entry
	 * @throws NoSuchAudiencesEntryException if a audiences entry with the primary key could not be found
	 */
	public AudiencesEntry findByPrimaryKey(long audiencesEntryId)
		throws NoSuchAudiencesEntryException;

	/**
	 * Returns the audiences entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param audiencesEntryId the primary key of the audiences entry
	 * @return the audiences entry, or <code>null</code> if a audiences entry with the primary key could not be found
	 */
	public AudiencesEntry fetchByPrimaryKey(long audiencesEntryId);

	/**
	 * Returns the audiences entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching audiences entry, or <code>null</code> if a matching audiences entry could not be found
	 */
	public default AudiencesEntry fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return fetchByERC_C(externalReferenceCode, companyId, true);
	}

	/**
	 * Returns all the audiences entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching audiences entries
	 */
	public default java.util.List<AudiencesEntry> findByCompanyId(
		long companyId) {

		return findByCompanyId(
			companyId, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the audiences entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audiences.model.impl.AudiencesEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audiences entries
	 * @param end the upper bound of the range of audiences entries (not inclusive)
	 * @return the range of matching audiences entries
	 */
	public default java.util.List<AudiencesEntry> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the audiences entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audiences.model.impl.AudiencesEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audiences entries
	 * @param end the upper bound of the range of audiences entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audiences entries
	 */
	public default java.util.List<AudiencesEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AudiencesEntry>
			orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-105000841