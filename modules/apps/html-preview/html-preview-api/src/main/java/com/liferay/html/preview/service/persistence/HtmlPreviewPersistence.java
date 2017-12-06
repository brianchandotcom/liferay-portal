/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.html.preview.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.html.preview.exception.NoSuchHtmlPreviewException;
import com.liferay.html.preview.model.HtmlPreview;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the html preview service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.html.preview.service.persistence.impl.HtmlPreviewPersistenceImpl
 * @see HtmlPreviewUtil
 * @generated
 */
@ProviderType
public interface HtmlPreviewPersistence extends BasePersistence<HtmlPreview> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link HtmlPreviewUtil} to access the html preview persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns the html preview where groupId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchHtmlPreviewException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching html preview
	* @throws NoSuchHtmlPreviewException if a matching html preview could not be found
	*/
	public HtmlPreview findByG_C_C(long groupId, long classNameId, long classPK)
		throws NoSuchHtmlPreviewException;

	/**
	* Returns the html preview where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching html preview, or <code>null</code> if a matching html preview could not be found
	*/
	public HtmlPreview fetchByG_C_C(long groupId, long classNameId, long classPK);

	/**
	* Returns the html preview where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching html preview, or <code>null</code> if a matching html preview could not be found
	*/
	public HtmlPreview fetchByG_C_C(long groupId, long classNameId,
		long classPK, boolean retrieveFromCache);

	/**
	* Removes the html preview where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the html preview that was removed
	*/
	public HtmlPreview removeByG_C_C(long groupId, long classNameId,
		long classPK) throws NoSuchHtmlPreviewException;

	/**
	* Returns the number of html previews where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching html previews
	*/
	public int countByG_C_C(long groupId, long classNameId, long classPK);

	/**
	* Caches the html preview in the entity cache if it is enabled.
	*
	* @param htmlPreview the html preview
	*/
	public void cacheResult(HtmlPreview htmlPreview);

	/**
	* Caches the html previews in the entity cache if it is enabled.
	*
	* @param htmlPreviews the html previews
	*/
	public void cacheResult(java.util.List<HtmlPreview> htmlPreviews);

	/**
	* Creates a new html preview with the primary key. Does not add the html preview to the database.
	*
	* @param htmlPreviewId the primary key for the new html preview
	* @return the new html preview
	*/
	public HtmlPreview create(long htmlPreviewId);

	/**
	* Removes the html preview with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param htmlPreviewId the primary key of the html preview
	* @return the html preview that was removed
	* @throws NoSuchHtmlPreviewException if a html preview with the primary key could not be found
	*/
	public HtmlPreview remove(long htmlPreviewId)
		throws NoSuchHtmlPreviewException;

	public HtmlPreview updateImpl(HtmlPreview htmlPreview);

	/**
	* Returns the html preview with the primary key or throws a {@link NoSuchHtmlPreviewException} if it could not be found.
	*
	* @param htmlPreviewId the primary key of the html preview
	* @return the html preview
	* @throws NoSuchHtmlPreviewException if a html preview with the primary key could not be found
	*/
	public HtmlPreview findByPrimaryKey(long htmlPreviewId)
		throws NoSuchHtmlPreviewException;

	/**
	* Returns the html preview with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param htmlPreviewId the primary key of the html preview
	* @return the html preview, or <code>null</code> if a html preview with the primary key could not be found
	*/
	public HtmlPreview fetchByPrimaryKey(long htmlPreviewId);

	@Override
	public java.util.Map<java.io.Serializable, HtmlPreview> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the html previews.
	*
	* @return the html previews
	*/
	public java.util.List<HtmlPreview> findAll();

	/**
	* Returns a range of all the html previews.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link HtmlPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of html previews
	* @param end the upper bound of the range of html previews (not inclusive)
	* @return the range of html previews
	*/
	public java.util.List<HtmlPreview> findAll(int start, int end);

	/**
	* Returns an ordered range of all the html previews.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link HtmlPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of html previews
	* @param end the upper bound of the range of html previews (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of html previews
	*/
	public java.util.List<HtmlPreview> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<HtmlPreview> orderByComparator);

	/**
	* Returns an ordered range of all the html previews.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link HtmlPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of html previews
	* @param end the upper bound of the range of html previews (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of html previews
	*/
	public java.util.List<HtmlPreview> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<HtmlPreview> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the html previews from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of html previews.
	*
	* @return the number of html previews
	*/
	public int countAll();
}