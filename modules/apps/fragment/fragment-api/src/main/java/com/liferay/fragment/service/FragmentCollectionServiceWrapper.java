/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.service;

import com.liferay.fragment.model.FragmentCollection;
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

	public FragmentCollectionServiceWrapper() {
		this(null);
	}

	public FragmentCollectionServiceWrapper(
		FragmentCollectionService fragmentCollectionService) {

		_fragmentCollectionService = fragmentCollectionService;
	}

	@Override
	public FragmentCollection addFragmentCollection(
			long groupId, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentCollectionService.addFragmentCollection(
			groupId, name, description, serviceContext);
	}

	@Override
	public FragmentCollection addFragmentCollection(
			long groupId, String fragmentCollectionKey, String name,
			String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentCollectionService.addFragmentCollection(
			groupId, fragmentCollectionKey, name, description, serviceContext);
	}

	@Override
	public FragmentCollection deleteFragmentCollection(
			long fragmentCollectionId)
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
	public FragmentCollection fetchFragmentCollection(long fragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentCollectionService.fetchFragmentCollection(
			fragmentCollectionId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getFragmentCollectionFileEntries(long fragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentCollectionService.getFragmentCollectionFileEntries(
			fragmentCollectionId);
	}

	@Override
	public java.util.List<FragmentCollection> getFragmentCollections(
		long groupId) {

		return _fragmentCollectionService.getFragmentCollections(groupId);
	}

	@Override
	public java.util.List<FragmentCollection> getFragmentCollections(
		long groupId, boolean includeSystem) {

		return _fragmentCollectionService.getFragmentCollections(
			groupId, includeSystem);
	}

	@Override
	public java.util.List<FragmentCollection> getFragmentCollections(
		long groupId, boolean includeSystem, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator) {

		return _fragmentCollectionService.getFragmentCollections(
			groupId, includeSystem, start, end, orderByComparator);
	}

	@Override
	public java.util.List<FragmentCollection> getFragmentCollections(
		long groupId, int start, int end) {

		return _fragmentCollectionService.getFragmentCollections(
			groupId, start, end);
	}

	@Override
	public java.util.List<FragmentCollection> getFragmentCollections(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator) {

		return _fragmentCollectionService.getFragmentCollections(
			groupId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<FragmentCollection> getFragmentCollections(
		long groupId, String name, boolean includeSystem, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator) {

		return _fragmentCollectionService.getFragmentCollections(
			groupId, name, includeSystem, start, end, orderByComparator);
	}

	@Override
	public java.util.List<FragmentCollection> getFragmentCollections(
		long groupId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator) {

		return _fragmentCollectionService.getFragmentCollections(
			groupId, name, start, end, orderByComparator);
	}

	@Override
	public java.util.List<FragmentCollection> getFragmentCollections(
		long[] groupIds) {

		return _fragmentCollectionService.getFragmentCollections(groupIds);
	}

	@Override
	public java.util.List<FragmentCollection> getFragmentCollections(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator) {

		return _fragmentCollectionService.getFragmentCollections(
			groupIds, start, end, orderByComparator);
	}

	@Override
	public java.util.List<FragmentCollection> getFragmentCollections(
		long[] groupIds, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator) {

		return _fragmentCollectionService.getFragmentCollections(
			groupIds, name, start, end, orderByComparator);
	}

	@Override
	public int getFragmentCollectionsCount(long groupId) {
		return _fragmentCollectionService.getFragmentCollectionsCount(groupId);
	}

	@Override
	public int getFragmentCollectionsCount(
		long groupId, boolean includeSystem) {

		return _fragmentCollectionService.getFragmentCollectionsCount(
			groupId, includeSystem);
	}

	@Override
	public int getFragmentCollectionsCount(long groupId, String name) {
		return _fragmentCollectionService.getFragmentCollectionsCount(
			groupId, name);
	}

	@Override
	public int getFragmentCollectionsCount(
		long groupId, String name, boolean includeSystem) {

		return _fragmentCollectionService.getFragmentCollectionsCount(
			groupId, name, includeSystem);
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
	public FragmentCollection updateFragmentCollection(
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