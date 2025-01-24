/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.service.persistence;

import com.liferay.layout.seo.exception.NoSuchEntryCustomMetaTagException;
import com.liferay.layout.seo.model.LayoutSEOEntryCustomMetaTag;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the layout seo entry custom meta tag service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOEntryCustomMetaTagUtil
 * @generated
 */
@ProviderType
public interface LayoutSEOEntryCustomMetaTagPersistence
	extends BasePersistence<LayoutSEOEntryCustomMetaTag>,
			CTPersistence<LayoutSEOEntryCustomMetaTag> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutSEOEntryCustomMetaTagUtil} to access the layout seo entry custom meta tag persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the layout seo entry custom meta tags where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @return the matching layout seo entry custom meta tags
	 */
	public java.util.List<LayoutSEOEntryCustomMetaTag> findByG_L(
		long groupId, long layoutSEOEntryId);

	/**
	 * Returns a range of all the layout seo entry custom meta tags where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryCustomMetaTagModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @param start the lower bound of the range of layout seo entry custom meta tags
	 * @param end the upper bound of the range of layout seo entry custom meta tags (not inclusive)
	 * @return the range of matching layout seo entry custom meta tags
	 */
	public java.util.List<LayoutSEOEntryCustomMetaTag> findByG_L(
		long groupId, long layoutSEOEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the layout seo entry custom meta tags where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryCustomMetaTagModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @param start the lower bound of the range of layout seo entry custom meta tags
	 * @param end the upper bound of the range of layout seo entry custom meta tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo entry custom meta tags
	 */
	public java.util.List<LayoutSEOEntryCustomMetaTag> findByG_L(
		long groupId, long layoutSEOEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutSEOEntryCustomMetaTag> orderByComparator);

	/**
	 * Returns an ordered range of all the layout seo entry custom meta tags where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryCustomMetaTagModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @param start the lower bound of the range of layout seo entry custom meta tags
	 * @param end the upper bound of the range of layout seo entry custom meta tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo entry custom meta tags
	 */
	public java.util.List<LayoutSEOEntryCustomMetaTag> findByG_L(
		long groupId, long layoutSEOEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutSEOEntryCustomMetaTag> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout seo entry custom meta tag in the ordered set where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo entry custom meta tag
	 * @throws NoSuchEntryCustomMetaTagException if a matching layout seo entry custom meta tag could not be found
	 */
	public LayoutSEOEntryCustomMetaTag findByG_L_First(
			long groupId, long layoutSEOEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutSEOEntryCustomMetaTag> orderByComparator)
		throws NoSuchEntryCustomMetaTagException;

	/**
	 * Returns the first layout seo entry custom meta tag in the ordered set where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo entry custom meta tag, or <code>null</code> if a matching layout seo entry custom meta tag could not be found
	 */
	public LayoutSEOEntryCustomMetaTag fetchByG_L_First(
		long groupId, long layoutSEOEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutSEOEntryCustomMetaTag> orderByComparator);

	/**
	 * Returns the last layout seo entry custom meta tag in the ordered set where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo entry custom meta tag
	 * @throws NoSuchEntryCustomMetaTagException if a matching layout seo entry custom meta tag could not be found
	 */
	public LayoutSEOEntryCustomMetaTag findByG_L_Last(
			long groupId, long layoutSEOEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutSEOEntryCustomMetaTag> orderByComparator)
		throws NoSuchEntryCustomMetaTagException;

	/**
	 * Returns the last layout seo entry custom meta tag in the ordered set where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo entry custom meta tag, or <code>null</code> if a matching layout seo entry custom meta tag could not be found
	 */
	public LayoutSEOEntryCustomMetaTag fetchByG_L_Last(
		long groupId, long layoutSEOEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutSEOEntryCustomMetaTag> orderByComparator);

	/**
	 * Returns the layout seo entry custom meta tags before and after the current layout seo entry custom meta tag in the ordered set where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * @param layoutSEOEntryCustomMetaTagId the primary key of the current layout seo entry custom meta tag
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo entry custom meta tag
	 * @throws NoSuchEntryCustomMetaTagException if a layout seo entry custom meta tag with the primary key could not be found
	 */
	public LayoutSEOEntryCustomMetaTag[] findByG_L_PrevAndNext(
			long layoutSEOEntryCustomMetaTagId, long groupId,
			long layoutSEOEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutSEOEntryCustomMetaTag> orderByComparator)
		throws NoSuchEntryCustomMetaTagException;

	/**
	 * Removes all the layout seo entry custom meta tags where groupId = &#63; and layoutSEOEntryId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 */
	public void removeByG_L(long groupId, long layoutSEOEntryId);

	/**
	 * Returns the number of layout seo entry custom meta tags where groupId = &#63; and layoutSEOEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutSEOEntryId the layout seo entry ID
	 * @return the number of matching layout seo entry custom meta tags
	 */
	public int countByG_L(long groupId, long layoutSEOEntryId);

	/**
	 * Caches the layout seo entry custom meta tag in the entity cache if it is enabled.
	 *
	 * @param layoutSEOEntryCustomMetaTag the layout seo entry custom meta tag
	 */
	public void cacheResult(
		LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag);

	/**
	 * Caches the layout seo entry custom meta tags in the entity cache if it is enabled.
	 *
	 * @param layoutSEOEntryCustomMetaTags the layout seo entry custom meta tags
	 */
	public void cacheResult(
		java.util.List<LayoutSEOEntryCustomMetaTag>
			layoutSEOEntryCustomMetaTags);

	/**
	 * Creates a new layout seo entry custom meta tag with the primary key. Does not add the layout seo entry custom meta tag to the database.
	 *
	 * @param layoutSEOEntryCustomMetaTagId the primary key for the new layout seo entry custom meta tag
	 * @return the new layout seo entry custom meta tag
	 */
	public LayoutSEOEntryCustomMetaTag create(
		long layoutSEOEntryCustomMetaTagId);

	/**
	 * Removes the layout seo entry custom meta tag with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOEntryCustomMetaTagId the primary key of the layout seo entry custom meta tag
	 * @return the layout seo entry custom meta tag that was removed
	 * @throws NoSuchEntryCustomMetaTagException if a layout seo entry custom meta tag with the primary key could not be found
	 */
	public LayoutSEOEntryCustomMetaTag remove(
			long layoutSEOEntryCustomMetaTagId)
		throws NoSuchEntryCustomMetaTagException;

	public LayoutSEOEntryCustomMetaTag updateImpl(
		LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag);

	/**
	 * Returns the layout seo entry custom meta tag with the primary key or throws a <code>NoSuchEntryCustomMetaTagException</code> if it could not be found.
	 *
	 * @param layoutSEOEntryCustomMetaTagId the primary key of the layout seo entry custom meta tag
	 * @return the layout seo entry custom meta tag
	 * @throws NoSuchEntryCustomMetaTagException if a layout seo entry custom meta tag with the primary key could not be found
	 */
	public LayoutSEOEntryCustomMetaTag findByPrimaryKey(
			long layoutSEOEntryCustomMetaTagId)
		throws NoSuchEntryCustomMetaTagException;

	/**
	 * Returns the layout seo entry custom meta tag with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutSEOEntryCustomMetaTagId the primary key of the layout seo entry custom meta tag
	 * @return the layout seo entry custom meta tag, or <code>null</code> if a layout seo entry custom meta tag with the primary key could not be found
	 */
	public LayoutSEOEntryCustomMetaTag fetchByPrimaryKey(
		long layoutSEOEntryCustomMetaTagId);

	/**
	 * Returns all the layout seo entry custom meta tags.
	 *
	 * @return the layout seo entry custom meta tags
	 */
	public java.util.List<LayoutSEOEntryCustomMetaTag> findAll();

	/**
	 * Returns a range of all the layout seo entry custom meta tags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryCustomMetaTagModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo entry custom meta tags
	 * @param end the upper bound of the range of layout seo entry custom meta tags (not inclusive)
	 * @return the range of layout seo entry custom meta tags
	 */
	public java.util.List<LayoutSEOEntryCustomMetaTag> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the layout seo entry custom meta tags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryCustomMetaTagModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo entry custom meta tags
	 * @param end the upper bound of the range of layout seo entry custom meta tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout seo entry custom meta tags
	 */
	public java.util.List<LayoutSEOEntryCustomMetaTag> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutSEOEntryCustomMetaTag> orderByComparator);

	/**
	 * Returns an ordered range of all the layout seo entry custom meta tags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryCustomMetaTagModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo entry custom meta tags
	 * @param end the upper bound of the range of layout seo entry custom meta tags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout seo entry custom meta tags
	 */
	public java.util.List<LayoutSEOEntryCustomMetaTag> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutSEOEntryCustomMetaTag> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the layout seo entry custom meta tags from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of layout seo entry custom meta tags.
	 *
	 * @return the number of layout seo entry custom meta tags
	 */
	public int countAll();

}