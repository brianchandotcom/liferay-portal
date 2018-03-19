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

package com.liferay.fragment.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.fragment.model.FragmentEntryLink;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for FragmentEntryLink. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryLinkLocalServiceUtil
 * @see com.liferay.fragment.service.base.FragmentEntryLinkLocalServiceBaseImpl
 * @see com.liferay.fragment.service.impl.FragmentEntryLinkLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface FragmentEntryLinkLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FragmentEntryLinkLocalServiceUtil} to access the fragment entry link local service. Add custom service methods to {@link com.liferay.fragment.service.impl.FragmentEntryLinkLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the fragment entry link to the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntryLink the fragment entry link
	* @return the fragment entry link that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public FragmentEntryLink addFragmentEntryLink(
		FragmentEntryLink fragmentEntryLink);

	public FragmentEntryLink addFragmentEntryLink(long groupId,
		long originalFragmentEntryLinkId, long fragmentEntryId,
		long classNameId, long classPK, java.lang.String css,
		java.lang.String html, java.lang.String js,
		java.lang.String editableValues, int position);

	public FragmentEntryLink addFragmentEntryLink(long groupId,
		long fragmentEntryId, long classNameId, long classPK,
		java.lang.String css, java.lang.String html, java.lang.String js,
		java.lang.String editableValues, int position);

	/**
	* Creates a new fragment entry link with the primary key. Does not add the fragment entry link to the database.
	*
	* @param fragmentEntryLinkId the primary key for the new fragment entry link
	* @return the new fragment entry link
	*/
	public FragmentEntryLink createFragmentEntryLink(long fragmentEntryLinkId);

	/**
	* Deletes the fragment entry link from the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntryLink the fragment entry link
	* @return the fragment entry link that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public FragmentEntryLink deleteFragmentEntryLink(
		FragmentEntryLink fragmentEntryLink);

	/**
	* Deletes the fragment entry link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntryLinkId the primary key of the fragment entry link
	* @return the fragment entry link that was removed
	* @throws PortalException if a fragment entry link with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public FragmentEntryLink deleteFragmentEntryLink(long fragmentEntryLinkId)
		throws PortalException;

	public List<FragmentEntryLink> deleteLayoutPageTemplateEntryFragmentEntryLinks(
		long groupId, long classNameId, long classPK);

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	public DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.fragment.model.impl.FragmentEntryLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.fragment.model.impl.FragmentEntryLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FragmentEntryLink fetchFragmentEntryLink(long fragmentEntryLinkId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the fragment entry link with the primary key.
	*
	* @param fragmentEntryLinkId the primary key of the fragment entry link
	* @return the fragment entry link
	* @throws PortalException if a fragment entry link with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FragmentEntryLink getFragmentEntryLink(long fragmentEntryLinkId)
		throws PortalException;

	/**
	* Returns a range of all the fragment entry links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.fragment.model.impl.FragmentEntryLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of fragment entry links
	* @param end the upper bound of the range of fragment entry links (not inclusive)
	* @return the range of fragment entry links
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentEntryLink> getFragmentEntryLinks(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentEntryLink> getFragmentEntryLinks(long groupId,
		long classNameId, long classPK);

	/**
	* Returns the number of fragment entry links.
	*
	* @return the number of fragment entry links
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFragmentEntryLinksCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	* Updates the fragment entry link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntryLink the fragment entry link
	* @return the fragment entry link that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public FragmentEntryLink updateFragmentEntryLink(
		FragmentEntryLink fragmentEntryLink);

	public FragmentEntryLink updateFragmentEntryLink(long fragmentEntryLinkId,
		int position);

	public FragmentEntryLink updateFragmentEntryLink(long fragmentEntryLinkId,
		java.lang.String editableValues);

	public void updateFragmentEntryLinks(long groupId, long classNameId,
		long classPK, long[] fragmentEntryIds, java.lang.String editableValues)
		throws JSONException;
}