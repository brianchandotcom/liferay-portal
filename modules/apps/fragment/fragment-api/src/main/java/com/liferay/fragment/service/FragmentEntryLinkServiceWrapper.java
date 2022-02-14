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

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link FragmentEntryLinkService}.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryLinkService
 * @generated
 */
public class FragmentEntryLinkServiceWrapper
	implements FragmentEntryLinkService,
			   ServiceWrapper<FragmentEntryLinkService> {

	public FragmentEntryLinkServiceWrapper() {
		this(null);
	}

	public FragmentEntryLinkServiceWrapper(
		FragmentEntryLinkService fragmentEntryLinkService) {

		_fragmentEntryLinkService = fragmentEntryLinkService;
	}

	@Override
	public FragmentEntryLink addFragmentEntryLink(
			long groupId, long originalFragmentEntryLinkId,
			long fragmentEntryId, long segmentsExperienceId, long plid,
			String css, String html, String js, String configuration,
			String editableValues, String namespace, int position,
			String rendererKey,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryLinkService.addFragmentEntryLink(
			groupId, originalFragmentEntryLinkId, fragmentEntryId,
			segmentsExperienceId, plid, css, html, js, configuration,
			editableValues, namespace, position, rendererKey, serviceContext);
	}

	@Override
	public FragmentEntryLink deleteFragmentEntryLink(long fragmentEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryLinkService.deleteFragmentEntryLink(
			fragmentEntryLinkId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _fragmentEntryLinkService.getOSGiServiceIdentifier();
	}

	@Override
	public FragmentEntryLink updateFragmentEntryLink(
			long fragmentEntryLinkId, String editableValues)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryLinkService.updateFragmentEntryLink(
			fragmentEntryLinkId, editableValues);
	}

	@Override
	public FragmentEntryLink updateFragmentEntryLink(
			long fragmentEntryLinkId, String editableValues,
			boolean updateClassedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryLinkService.updateFragmentEntryLink(
			fragmentEntryLinkId, editableValues, updateClassedModel);
	}

	@Override
	public FragmentEntryLinkService getWrappedService() {
		return _fragmentEntryLinkService;
	}

	@Override
	public void setWrappedService(
		FragmentEntryLinkService fragmentEntryLinkService) {

		_fragmentEntryLinkService = fragmentEntryLinkService;
	}

	private FragmentEntryLinkService _fragmentEntryLinkService;

}