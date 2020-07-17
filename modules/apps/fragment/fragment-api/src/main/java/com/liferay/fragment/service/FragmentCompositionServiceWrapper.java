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

import com.liferay.fragment.model.FragmentComposition;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link FragmentCompositionService}.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentCompositionService
 * @generated
 */
public class FragmentCompositionServiceWrapper
	implements FragmentCompositionService,
			   ServiceWrapper<FragmentCompositionService> {

	public FragmentCompositionServiceWrapper(
		FragmentCompositionService fragmentCompositionService) {

		_fragmentCompositionService = fragmentCompositionService;
	}

	@Override
	public FragmentComposition addFragmentComposition(
			long groupId, long fragmentCollectionId,
			String fragmentCompositionKey, String name, String description,
			String data, long previewFileEntryId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentCompositionService.addFragmentComposition(
			groupId, fragmentCollectionId, fragmentCompositionKey, name,
			description, data, previewFileEntryId, status, serviceContext);
	}

	@Override
	public FragmentComposition deleteFragmentComposition(
			long fragmentCompositionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentCompositionService.deleteFragmentComposition(
			fragmentCompositionId);
	}

	@Override
	public FragmentComposition fetchFragmentComposition(
		long fragmentCompositionId) {

		return _fragmentCompositionService.fetchFragmentComposition(
			fragmentCompositionId);
	}

	@Override
	public FragmentComposition fetchFragmentComposition(
		long groupId, String fragmentCompositionKey) {

		return _fragmentCompositionService.fetchFragmentComposition(
			groupId, fragmentCompositionKey);
	}

	@Override
	public java.util.List<FragmentComposition> getFragmentCompositions(
		long fragmentCollectionId) {

		return _fragmentCompositionService.getFragmentCompositions(
			fragmentCollectionId);
	}

	@Override
	public java.util.List<FragmentComposition> getFragmentCompositions(
		long fragmentCollectionId, int start, int end) {

		return _fragmentCompositionService.getFragmentCompositions(
			fragmentCollectionId, start, end);
	}

	@Override
	public java.util.List<FragmentComposition> getFragmentCompositions(
		long groupId, long fragmentCollectionId, int status) {

		return _fragmentCompositionService.getFragmentCompositions(
			groupId, fragmentCollectionId, status);
	}

	@Override
	public java.util.List<FragmentComposition> getFragmentCompositions(
		long groupId, long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator) {

		return _fragmentCompositionService.getFragmentCompositions(
			groupId, fragmentCollectionId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<FragmentComposition> getFragmentCompositions(
		long groupId, long fragmentCollectionId, String name, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator) {

		return _fragmentCompositionService.getFragmentCompositions(
			groupId, fragmentCollectionId, name, start, end, orderByComparator);
	}

	@Override
	public int getFragmentCompositionsCount(long fragmentCollectionId) {
		return _fragmentCompositionService.getFragmentCompositionsCount(
			fragmentCollectionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _fragmentCompositionService.getOSGiServiceIdentifier();
	}

	@Override
	public FragmentComposition moveFragmentComposition(
			long fragmentCompositionId, long fragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentCompositionService.moveFragmentComposition(
			fragmentCompositionId, fragmentCollectionId);
	}

	@Override
	public FragmentComposition updateFragmentComposition(
			long fragmentCompositionId, long previewFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentCompositionService.updateFragmentComposition(
			fragmentCompositionId, previewFileEntryId);
	}

	@Override
	public FragmentComposition updateFragmentComposition(
			long fragmentCompositionId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentCompositionService.updateFragmentComposition(
			fragmentCompositionId, name);
	}

	@Override
	public FragmentComposition updateFragmentComposition(
			long fragmentCompositionId, String name, String description,
			String data, long previewFileEntryId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentCompositionService.updateFragmentComposition(
			fragmentCompositionId, name, description, data, previewFileEntryId,
			status);
	}

	@Override
	public FragmentCompositionService getWrappedService() {
		return _fragmentCompositionService;
	}

	@Override
	public void setWrappedService(
		FragmentCompositionService fragmentCompositionService) {

		_fragmentCompositionService = fragmentCompositionService;
	}

	private FragmentCompositionService _fragmentCompositionService;

}