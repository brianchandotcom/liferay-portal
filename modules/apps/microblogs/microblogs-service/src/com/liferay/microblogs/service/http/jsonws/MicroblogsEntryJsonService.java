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

package com.liferay.microblogs.service.http.jsonws;

import aQute.bnd.annotation.ProviderType;

import com.liferay.microblogs.service.MicroblogsEntryService;

import com.liferay.portal.kernel.jsonwebservice.JSONWebService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the JSONWS remote service utility for MicroblogsEntry. This utility wraps
 * {@link com.liferay.microblogs.service.impl.MicroblogsEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see MicroblogsEntryService
 * @generated
 */
@Component(immediate = true, property =  {
	"json.web.service.context.name=microblogs", "json.web.service.context.path=MicroblogsEntry"}, service = MicroblogsEntryJsonService.class)
@JSONWebService
@ProviderType
public class MicroblogsEntryJsonService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.microblogs.service.impl.MicroblogsEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public com.liferay.microblogs.model.MicroblogsEntry addMicroblogsEntry(
		long userId, java.lang.String content, int type,
		long parentMicroblogsEntryId, int socialRelationType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.addMicroblogsEntry(userId, content, type,
			parentMicroblogsEntryId, socialRelationType, serviceContext);
	}

	public com.liferay.microblogs.model.MicroblogsEntry deleteMicroblogsEntry(
		long microblogsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.deleteMicroblogsEntry(microblogsEntryId);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _service.getBeanIdentifier();
	}

	public java.util.List<com.liferay.microblogs.model.MicroblogsEntry> getMicroblogsEntries(
		java.lang.String assetTagName, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getMicroblogsEntries(assetTagName, start, end);
	}

	public java.util.List<com.liferay.microblogs.model.MicroblogsEntry> getMicroblogsEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getMicroblogsEntries(start, end);
	}

	public int getMicroblogsEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getMicroblogsEntriesCount();
	}

	public int getMicroblogsEntriesCount(java.lang.String assetTagName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getMicroblogsEntriesCount(assetTagName);
	}

	public com.liferay.microblogs.model.MicroblogsEntry getMicroblogsEntry(
		long microblogsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getMicroblogsEntry(microblogsEntryId);
	}

	public java.util.List<com.liferay.microblogs.model.MicroblogsEntry> getUserMicroblogsEntries(
		long microblogsEntryUserId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getUserMicroblogsEntries(microblogsEntryUserId, start,
			end);
	}

	public java.util.List<com.liferay.microblogs.model.MicroblogsEntry> getUserMicroblogsEntries(
		long microblogsEntryUserId, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getUserMicroblogsEntries(microblogsEntryUserId, type,
			start, end);
	}

	public int getUserMicroblogsEntriesCount(long microblogsEntryUserId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getUserMicroblogsEntriesCount(microblogsEntryUserId);
	}

	public int getUserMicroblogsEntriesCount(long microblogsEntryUserId,
		int type) throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getUserMicroblogsEntriesCount(microblogsEntryUserId,
			type);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_service.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.microblogs.model.MicroblogsEntry updateMicroblogsEntry(
		long microblogsEntryId, java.lang.String content,
		int socialRelationType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.updateMicroblogsEntry(microblogsEntryId, content,
			socialRelationType, serviceContext);
	}

	@Reference
	protected void setService(MicroblogsEntryService service) {
		_service = service;
	}

	private MicroblogsEntryService _service;
}