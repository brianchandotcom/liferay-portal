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

package com.liferay.social.kernel.service;

import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.model.SocialActivityFeedEntry;
import com.liferay.social.kernel.model.SocialActivityInterpreter;
import com.liferay.social.kernel.model.SocialActivitySet;

import java.util.List;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for SocialActivityInterpreter. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityInterpreterLocalServiceUtil
 * @generated
 */
@CTAware
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface SocialActivityInterpreterLocalService
	extends BaseLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.portlet.social.service.impl.SocialActivityInterpreterLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the social activity interpreter local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link SocialActivityInterpreterLocalServiceUtil} if injection and service tracking are not available.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<String, List<SocialActivityInterpreter>>
		getActivityInterpreters();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SocialActivityInterpreter> getActivityInterpreters(
		String selector);

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	/**
	 * Creates a human readable activity feed entry for the activity using an
	 * available compatible activity interpreter.
	 *
	 * <p>
	 * This method finds the appropriate interpreter for the activity by going
	 * through the available interpreters and asking them if they can handle the
	 * asset type of the activity.
	 * </p>
	 *
	 * @param selector the context in which the activity interpreter is used
	 * @param activity the activity to be translated to human readable form
	 * @param serviceContext the service context to be applied
	 * @return the activity feed that is a human readable form of the activity
	 record or <code>null</code> if a compatible interpreter is not
	 found
	 */
	public SocialActivityFeedEntry interpret(
		String selector, SocialActivity activity,
		ServiceContext serviceContext);

	public SocialActivityFeedEntry interpret(
		String selector, SocialActivitySet activitySet,
		ServiceContext serviceContext);

	public void updateActivitySet(long activityId) throws PortalException;

}