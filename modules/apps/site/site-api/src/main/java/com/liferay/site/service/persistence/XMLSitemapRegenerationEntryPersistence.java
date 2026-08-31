/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.site.exception.NoSuchXMLSitemapRegenerationEntryException;
import com.liferay.site.model.XMLSitemapRegenerationEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the xml sitemap regeneration entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see XMLSitemapRegenerationEntryUtil
 * @generated
 */
@ProviderType
public interface XMLSitemapRegenerationEntryPersistence
	extends BasePersistence<XMLSitemapRegenerationEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link XMLSitemapRegenerationEntryUtil} to access the xml sitemap regeneration entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns an ordered range of all the xml sitemap regeneration entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.site.model.impl.XMLSitemapRegenerationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of xml sitemap regeneration entries
	 * @param end the upper bound of the range of xml sitemap regeneration entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching xml sitemap regeneration entries
	 */
	public java.util.List<XMLSitemapRegenerationEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<XMLSitemapRegenerationEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first xml sitemap regeneration entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching xml sitemap regeneration entry
	 * @throws NoSuchXMLSitemapRegenerationEntryException if a matching xml sitemap regeneration entry could not be found
	 */
	public XMLSitemapRegenerationEntry findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<XMLSitemapRegenerationEntry> orderByComparator)
		throws NoSuchXMLSitemapRegenerationEntryException;

	/**
	 * Returns the first xml sitemap regeneration entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching xml sitemap regeneration entry, or <code>null</code> if a matching xml sitemap regeneration entry could not be found
	 */
	public XMLSitemapRegenerationEntry fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<XMLSitemapRegenerationEntry> orderByComparator);

	/**
	 * Removes all the xml sitemap regeneration entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of xml sitemap regeneration entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching xml sitemap regeneration entries
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns an ordered range of all the xml sitemap regeneration entries where groupId = &#63; and companyId = &#63; and assetTypeKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.site.model.impl.XMLSitemapRegenerationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param assetTypeKey the asset type key
	 * @param start the lower bound of the range of xml sitemap regeneration entries
	 * @param end the upper bound of the range of xml sitemap regeneration entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching xml sitemap regeneration entries
	 */
	public java.util.List<XMLSitemapRegenerationEntry> findByG_C_A(
		long groupId, long companyId, String assetTypeKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<XMLSitemapRegenerationEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first xml sitemap regeneration entry in the ordered set where groupId = &#63; and companyId = &#63; and assetTypeKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param assetTypeKey the asset type key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching xml sitemap regeneration entry
	 * @throws NoSuchXMLSitemapRegenerationEntryException if a matching xml sitemap regeneration entry could not be found
	 */
	public XMLSitemapRegenerationEntry findByG_C_A_First(
			long groupId, long companyId, String assetTypeKey,
			com.liferay.portal.kernel.util.OrderByComparator
				<XMLSitemapRegenerationEntry> orderByComparator)
		throws NoSuchXMLSitemapRegenerationEntryException;

	/**
	 * Returns the first xml sitemap regeneration entry in the ordered set where groupId = &#63; and companyId = &#63; and assetTypeKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param assetTypeKey the asset type key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching xml sitemap regeneration entry, or <code>null</code> if a matching xml sitemap regeneration entry could not be found
	 */
	public XMLSitemapRegenerationEntry fetchByG_C_A_First(
		long groupId, long companyId, String assetTypeKey,
		com.liferay.portal.kernel.util.OrderByComparator
			<XMLSitemapRegenerationEntry> orderByComparator);

	/**
	 * Removes all the xml sitemap regeneration entries where groupId = &#63; and companyId = &#63; and assetTypeKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param assetTypeKey the asset type key
	 */
	public void removeByG_C_A(
		long groupId, long companyId, String assetTypeKey);

	/**
	 * Returns the number of xml sitemap regeneration entries where groupId = &#63; and companyId = &#63; and assetTypeKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param assetTypeKey the asset type key
	 * @return the number of matching xml sitemap regeneration entries
	 */
	public int countByG_C_A(long groupId, long companyId, String assetTypeKey);

	/**
	 * Creates a new xml sitemap regeneration entry with the primary key. Does not add the xml sitemap regeneration entry to the database.
	 *
	 * @param xmlSitemapRegenerationEntryId the primary key for the new xml sitemap regeneration entry
	 * @return the new xml sitemap regeneration entry
	 */
	public XMLSitemapRegenerationEntry create(
		long xmlSitemapRegenerationEntryId);

	/**
	 * Removes the xml sitemap regeneration entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param xmlSitemapRegenerationEntryId the primary key of the xml sitemap regeneration entry
	 * @return the xml sitemap regeneration entry that was removed
	 * @throws NoSuchXMLSitemapRegenerationEntryException if a xml sitemap regeneration entry with the primary key could not be found
	 */
	public XMLSitemapRegenerationEntry remove(
			long xmlSitemapRegenerationEntryId)
		throws NoSuchXMLSitemapRegenerationEntryException;

	public XMLSitemapRegenerationEntry updateImpl(
		XMLSitemapRegenerationEntry xmlSitemapRegenerationEntry);

	/**
	 * Returns the xml sitemap regeneration entry with the primary key or throws a <code>NoSuchXMLSitemapRegenerationEntryException</code> if it could not be found.
	 *
	 * @param xmlSitemapRegenerationEntryId the primary key of the xml sitemap regeneration entry
	 * @return the xml sitemap regeneration entry
	 * @throws NoSuchXMLSitemapRegenerationEntryException if a xml sitemap regeneration entry with the primary key could not be found
	 */
	public XMLSitemapRegenerationEntry findByPrimaryKey(
			long xmlSitemapRegenerationEntryId)
		throws NoSuchXMLSitemapRegenerationEntryException;

	/**
	 * Returns the xml sitemap regeneration entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param xmlSitemapRegenerationEntryId the primary key of the xml sitemap regeneration entry
	 * @return the xml sitemap regeneration entry, or <code>null</code> if a xml sitemap regeneration entry with the primary key could not be found
	 */
	public XMLSitemapRegenerationEntry fetchByPrimaryKey(
		long xmlSitemapRegenerationEntryId);

	/**
	 * Returns all the xml sitemap regeneration entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching xml sitemap regeneration entries
	 */
	public default java.util.List<XMLSitemapRegenerationEntry> findByCompanyId(
		long companyId) {

		return findByCompanyId(
			companyId, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the xml sitemap regeneration entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.site.model.impl.XMLSitemapRegenerationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of xml sitemap regeneration entries
	 * @param end the upper bound of the range of xml sitemap regeneration entries (not inclusive)
	 * @return the range of matching xml sitemap regeneration entries
	 */
	public default java.util.List<XMLSitemapRegenerationEntry> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the xml sitemap regeneration entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.site.model.impl.XMLSitemapRegenerationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of xml sitemap regeneration entries
	 * @param end the upper bound of the range of xml sitemap regeneration entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching xml sitemap regeneration entries
	 */
	public default java.util.List<XMLSitemapRegenerationEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<XMLSitemapRegenerationEntry> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the xml sitemap regeneration entries where groupId = &#63; and companyId = &#63; and assetTypeKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param assetTypeKey the asset type key
	 * @return the matching xml sitemap regeneration entries
	 */
	public default java.util.List<XMLSitemapRegenerationEntry> findByG_C_A(
		long groupId, long companyId, String assetTypeKey) {

		return findByG_C_A(
			groupId, companyId, assetTypeKey,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the xml sitemap regeneration entries where groupId = &#63; and companyId = &#63; and assetTypeKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.site.model.impl.XMLSitemapRegenerationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param assetTypeKey the asset type key
	 * @param start the lower bound of the range of xml sitemap regeneration entries
	 * @param end the upper bound of the range of xml sitemap regeneration entries (not inclusive)
	 * @return the range of matching xml sitemap regeneration entries
	 */
	public default java.util.List<XMLSitemapRegenerationEntry> findByG_C_A(
		long groupId, long companyId, String assetTypeKey, int start, int end) {

		return findByG_C_A(
			groupId, companyId, assetTypeKey, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the xml sitemap regeneration entries where groupId = &#63; and companyId = &#63; and assetTypeKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.site.model.impl.XMLSitemapRegenerationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param assetTypeKey the asset type key
	 * @param start the lower bound of the range of xml sitemap regeneration entries
	 * @param end the upper bound of the range of xml sitemap regeneration entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching xml sitemap regeneration entries
	 */
	public default java.util.List<XMLSitemapRegenerationEntry> findByG_C_A(
		long groupId, long companyId, String assetTypeKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<XMLSitemapRegenerationEntry> orderByComparator) {

		return findByG_C_A(
			groupId, companyId, assetTypeKey, start, end, orderByComparator,
			true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1633962874