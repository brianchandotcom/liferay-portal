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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link FragmentCollectionService}.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentCollectionService
 * @generated
 */
public class FragmentCollectionServiceWrapper
	implements FragmentCollectionService,
			   ServiceWrapper<FragmentCollectionService> {

	public FragmentCollectionServiceWrapper(
		FragmentCollectionService fragmentCollectionService) {

		_fragmentCollectionService = fragmentCollectionService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FragmentCollectionServiceUtil} to access the fragment collection remote service. Add custom service methods to <code>com.liferay.fragment.service.impl.FragmentCollectionServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.fragment.model.FragmentCollection addFragmentCollection(
			long groupId, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentCollectionService.addFragmentCollection(
			groupId, name, description, serviceContext);
	}

	@Override
	public com.liferay.fragment.model.FragmentCollection addFragmentCollection(
			long groupId, String fragmentCollectionKey, String name,
			String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentCollectionService.addFragmentCollection(
			groupId, fragmentCollectionKey, name, description, serviceContext);
	}

	@Override
	public com.liferay.fragment.model.FragmentCollection
			deleteFragmentCollection(long fragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentCollectionService.deleteFragmentCollection(
			fragmentCollectionId);
	}

	@Override
	public void deleteFragmentCollections(long[] fragmentCollectionIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_fragmentCollectionService.deleteFragmentCollections(
			fragmentCollectionIds);
	}

	@Override
	public com.liferay.fragment.model.FragmentCollection
			fetchFragmentCollection(long fragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentCollectionService.fetchFragmentCollection(
			fragmentCollectionId);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentCollection>
		getFragmentCollections(long groupId) {

		return _fragmentCollectionService.getFragmentCollections(groupId);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentCollection>
		getFragmentCollections(long groupId, int start, int end) {

		return _fragmentCollectionService.getFragmentCollections(
			groupId, start, end);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentCollection>
		getFragmentCollections(
			long groupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentCollection>
					orderByComparator) {

		return _fragmentCollectionService.getFragmentCollections(
			groupId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentCollection>
		getFragmentCollections(
			long groupId, String name, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentCollection>
					orderByComparator) {

		return _fragmentCollectionService.getFragmentCollections(
			groupId, name, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentCollection>
		getFragmentCollections(long[] groupIds) {

		return _fragmentCollectionService.getFragmentCollections(groupIds);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentCollection>
		getFragmentCollections(
			long[] groupIds, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentCollection>
					orderByComparator) {

		return _fragmentCollectionService.getFragmentCollections(
			groupIds, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentCollection>
		getFragmentCollections(
			long[] groupIds, String name, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentCollection>
					orderByComparator) {

		return _fragmentCollectionService.getFragmentCollections(
			groupIds, name, start, end, orderByComparator);
	}

	@Override
	public int getFragmentCollectionsCount(long groupId) {
		return _fragmentCollectionService.getFragmentCollectionsCount(groupId);
	}

	@Override
	public int getFragmentCollectionsCount(long groupId, String name) {
		return _fragmentCollectionService.getFragmentCollectionsCount(
			groupId, name);
	}

	@Override
	public int getFragmentCollectionsCount(long[] groupIds) {
		return _fragmentCollectionService.getFragmentCollectionsCount(groupIds);
	}

	@Override
	public int getFragmentCollectionsCount(long[] groupIds, String name) {
		return _fragmentCollectionService.getFragmentCollectionsCount(
			groupIds, name);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _fragmentCollectionService.getOSGiServiceIdentifier();
	}

	@Override
	public String[] getTempFileNames(long groupId, String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentCollectionService.getTempFileNames(groupId, folderName);
	}

	@Override
	public com.liferay.fragment.model.FragmentCollection
			updateFragmentCollection(
				long fragmentCollectionId, String name, String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentCollectionService.updateFragmentCollection(
			fragmentCollectionId, name, description);
	}

	@Override
	public FragmentCollectionService getWrappedService() {
		return _fragmentCollectionService;
	}

	@Override
	public void setWrappedService(
		FragmentCollectionService fragmentCollectionService) {

		_fragmentCollectionService = fragmentCollectionService;
	}

	private FragmentCollectionService _fragmentCollectionService;

}