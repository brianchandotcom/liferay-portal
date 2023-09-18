/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.service;

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
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.segments.model.SegmentsExperience;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for SegmentsExperience. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Eduardo Garcia
 * @see SegmentsExperienceServiceUtil
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
public interface SegmentsExperienceService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.segments.service.impl.SegmentsExperienceServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the segments experience remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link SegmentsExperienceServiceUtil} if injection and service tracking are not available.
	 */
	public SegmentsExperience addSegmentsExperience(
			long groupId, long segmentsEntryId, long plid,
			Map<Locale, String> nameMap, boolean active,
			UnicodeProperties typeSettingsUnicodeProperties,
			ServiceContext serviceContext)
		throws PortalException;

	public SegmentsExperience appendSegmentsExperience(
			long groupId, long segmentsEntryId, long plid,
			Map<Locale, String> nameMap, boolean active,
			ServiceContext serviceContext)
		throws PortalException;

	public SegmentsExperience appendSegmentsExperience(
			long groupId, long segmentsEntryId, long plid,
			Map<Locale, String> nameMap, boolean active,
			UnicodeProperties typeSettingsUnicodeProperties,
			ServiceContext serviceContext)
		throws PortalException;

	public SegmentsExperience deleteSegmentsExperience(
			long segmentsExperienceId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SegmentsExperience fetchSegmentsExperience(
			long groupId, String segmentsExperienceKey, long plid)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SegmentsExperience getSegmentsExperience(long segmentsExperienceId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SegmentsExperience getSegmentsExperience(
			long groupId, String segmentsExperienceKey, long plid)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SegmentsExperience> getSegmentsExperiences(
			long groupId, long plid, boolean active)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SegmentsExperience> getSegmentsExperiences(
			long groupId, long plid, boolean active, int start, int end,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSegmentsExperiencesCount(
			long groupId, long plid, boolean active)
		throws PortalException;

	public SegmentsExperience updateSegmentsExperience(
			long segmentsExperienceId, long segmentsEntryId,
			Map<Locale, String> nameMap, boolean active)
		throws PortalException;

	public SegmentsExperience updateSegmentsExperience(
			long segmentsExperienceId, long segmentsEntryId,
			Map<Locale, String> nameMap, boolean active,
			UnicodeProperties typeSettingsUnicodeProperties)
		throws PortalException;

	public void updateSegmentsExperiencePriority(
			long segmentsExperienceId, int newPriority)
		throws PortalException;

}