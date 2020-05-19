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

package com.liferay.social.activity.internal.manager;

import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.social.SocialActivityManager;

import java.util.Date;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Adolfo Pérez
 */
@Component(service = SocialActivityManager.class)
public class SocialActivityManagerImpl<T extends ClassedModel & GroupedModel>
	implements SocialActivityManager<T> {

	public SocialActivityManagerImpl(
		SocialActivityManager<T> defaultSocialActivityManager) {

		_defaultSocialActivityManager = defaultSocialActivityManager;
	}

	@Override
	public void addActivity(
			long userId, T model, int type, String extraData,
			long receiverUserId)
		throws PortalException {

		SocialActivityManager<T> socialActivityManager =
			getSocialActivityManager(model.getModelClassName());

		socialActivityManager.addActivity(
			userId, model, type, extraData, receiverUserId);
	}

	@Override
	public void addUniqueActivity(
			long userId, Date createDate, T model, int type, String extraData,
			long receiverUserId)
		throws PortalException {

		SocialActivityManager<T> socialActivityManager =
			getSocialActivityManager(model.getModelClassName());

		socialActivityManager.addUniqueActivity(
			userId, createDate, model, type, extraData, receiverUserId);
	}

	@Override
	public void addUniqueActivity(
			long userId, T model, int type, String extraData,
			long receiverUserId)
		throws PortalException {

		SocialActivityManager<T> socialActivityManager =
			getSocialActivityManager(model.getModelClassName());

		socialActivityManager.addUniqueActivity(
			userId, model, type, extraData, receiverUserId);
	}

	@Override
	public void deleteActivities(T model) throws PortalException {
		SocialActivityManager<T> socialActivityManager =
			getSocialActivityManager(model.getModelClassName());

		socialActivityManager.deleteActivities(model);
	}

	@Override
	public void updateLastSocialActivity(
			long userId, T model, int type, Date createDate)
		throws PortalException {

		SocialActivityManager<T> socialActivityManager =
			getSocialActivityManager(model.getModelClassName());

		socialActivityManager.updateLastSocialActivity(
			userId, model, type, createDate);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext,
			(Class<SocialActivityManager<T>>)(Class)SocialActivityManager.class,
			"(model.class.name=*)",
			new PropertyServiceReferenceMapper<>("model.class.name"));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	protected SocialActivityManager<T> getSocialActivityManager(
		String className) {

		SocialActivityManager<T> socialActivityManager =
			_serviceTrackerMap.getService(className);

		if (socialActivityManager != null) {
			return socialActivityManager;
		}

		return _defaultSocialActivityManager;
	}

	private final SocialActivityManager<T> _defaultSocialActivityManager;
	private ServiceTrackerMap<String, SocialActivityManager<T>>
		_serviceTrackerMap;

}