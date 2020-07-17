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

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for FragmentEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryServiceUtil
 * @generated
 */
@AccessControlled
@CTAware
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface FragmentEntryService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.fragment.service.impl.FragmentEntryServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the fragment entry remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link FragmentEntryServiceUtil} if injection and service tracking are not available.
	 */
	public FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String fragmentEntryKey,
			String name, long previewFileEntryId, int type, int status,
			ServiceContext serviceContext)
		throws PortalException;

	public FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String fragmentEntryKey,
			String name, String css, String html, String js, boolean cacheable,
			String configuration, long previewFileEntryId, int type, int status,
			ServiceContext serviceContext)
		throws PortalException;

	public FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String fragmentEntryKey,
			String name, String css, String html, String js,
			String configuration, long previewFileEntryId, int type, int status,
			ServiceContext serviceContext)
		throws PortalException;

	public FragmentEntry copyFragmentEntry(
			long groupId, long fragmentEntryId, long fragmentCollectionId,
			ServiceContext serviceContext)
		throws PortalException;

	public void deleteFragmentEntries(long[] fragmentEntriesIds)
		throws PortalException;

	public FragmentEntry deleteFragmentEntry(long fragmentEntryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FragmentEntry fetchDraft(long primaryKey);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FragmentEntry fetchFragmentEntry(long fragmentEntryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FragmentEntry getDraft(long primaryKey) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Object> getFragmentCompositionsAndFragmentEntries(
		long groupId, long fragmentCollectionId, int status, int start, int end,
		OrderByComparator<?> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Object> getFragmentCompositionsAndFragmentEntries(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end, OrderByComparator<?> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFragmentCompositionsAndFragmentEntriesCount(
		long groupId, long fragmentCollectionId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFragmentCompositionsAndFragmentEntriesCount(
		long groupId, long fragmentCollectionId, String name, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentEntry> getFragmentEntries(long fragmentCollectionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentEntry> getFragmentEntriesByName(
		long groupId, long fragmentCollectionId, String name, int start,
		int end, OrderByComparator<FragmentEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentEntry> getFragmentEntriesByNameAndStatus(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end, OrderByComparator<FragmentEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentEntry> getFragmentEntriesByStatus(
		long groupId, long fragmentCollectionId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentEntry> getFragmentEntriesByStatus(
		long groupId, long fragmentCollectionId, int status, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentEntry> getFragmentEntriesByType(
		long groupId, long fragmentCollectionId, int type, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentEntry> getFragmentEntriesByTypeAndStatus(
		long groupId, long fragmentCollectionId, int type, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentEntry> getFragmentEntriesByTypeAndStatus(
		long groupId, long fragmentCollectionId, int type, int status,
		int start, int end, OrderByComparator<FragmentEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFragmentEntriesCount(long groupId, long fragmentCollectionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFragmentEntriesCountByName(
		long groupId, long fragmentCollectionId, String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFragmentEntriesCountByNameAndStatus(
		long groupId, long fragmentCollectionId, String name, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFragmentEntriesCountByStatus(
		long groupId, long fragmentCollectionId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFragmentEntriesCountByType(
		long groupId, long fragmentCollectionId, int type);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFragmentEntriesCountByTypeAndStatus(
		long groupId, long fragmentCollectionId, int type, int status);

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String[] getTempFileNames(long groupId, String folderName)
		throws PortalException;

	public FragmentEntry moveFragmentEntry(
			long fragmentEntryId, long fragmentCollectionId)
		throws PortalException;

	public FragmentEntry publishDraft(FragmentEntry draftFragmentEntry)
		throws PortalException;

	public FragmentEntry updateDraft(FragmentEntry draftFragmentEntry)
		throws PortalException;

	public FragmentEntry updateFragmentEntry(FragmentEntry fragmentEntry)
		throws PortalException;

	public FragmentEntry updateFragmentEntry(
			long fragmentEntryId, long previewFileEntryId)
		throws PortalException;

	public FragmentEntry updateFragmentEntry(long fragmentEntryId, String name)
		throws PortalException;

	public FragmentEntry updateFragmentEntry(
			long fragmentEntryId, String name, String css, String html,
			String js, boolean cacheable, String configuration, int status)
		throws PortalException;

	public FragmentEntry updateFragmentEntry(
			long fragmentEntryId, String name, String css, String html,
			String js, boolean cacheable, String configuration,
			long previewFileEntryId, int status)
		throws PortalException;

	public FragmentEntry updateFragmentEntry(
			long fragmentEntryId, String name, String css, String html,
			String js, String configuration, int status)
		throws PortalException;

	public FragmentEntry updateFragmentEntry(
			long fragmentEntryId, String name, String css, String html,
			String js, String configuration, long previewFileEntryId,
			int status)
		throws PortalException;

}