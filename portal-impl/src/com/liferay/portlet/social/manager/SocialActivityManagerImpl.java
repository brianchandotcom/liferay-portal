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

package com.liferay.portlet.social.manager;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.GroupedModel;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
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
			getSocialActivityHandler(model.getModelClassName());

		socialActivityManager.addActivity(
			userId, model, type, extraData, receiverUserId);
	}

	@Override
	public void addUniqueActivity(
			long userId, Date createDate, T model, int type, String extraData,
			long receiverUserId)
		throws PortalException {

		SocialActivityManager<T> socialActivityManager =
			getSocialActivityHandler(model.getModelClassName());

		socialActivityManager.addUniqueActivity(
			userId, createDate, model, type, extraData, receiverUserId);
	}

	@Override
	public void addUniqueActivity(
			long userId, T model, int type, String extraData,
			long receiverUserId)
		throws PortalException {

		SocialActivityManager<T> socialActivityManager =
			getSocialActivityHandler(model.getModelClassName());

		socialActivityManager.addUniqueActivity(
			userId, model, type, extraData, receiverUserId);
	}

	@Override
	public void deleteActivities(T model) throws PortalException {
		SocialActivityManager<T> socialActivityManager =
			getSocialActivityHandler(model.getModelClassName());

		socialActivityManager.deleteActivities(model);
	}

	@Override
	public void updateLastSocialActivity(
			long userId, T model, int type, Date createDate)
		throws PortalException {

		SocialActivityManager<T> socialActivityManager =
			getSocialActivityHandler(model.getModelClassName());

		socialActivityManager.updateLastSocialActivity(
			userId, model, type, createDate);
	}

	protected void activate() {
		_serviceTrackerMap.open();
	}

	@SuppressWarnings("unchecked")
	protected SocialActivityManager<T> getSocialActivityHandler(
		String className) {

		SocialActivityManager<T> socialActivityManager =
			_serviceTrackerMap.getService(className);

		if (socialActivityManager != null) {
			return socialActivityManager;
		}

		return _defaultSocialActivityManager;
	}

	private final SocialActivityManager<T> _defaultSocialActivityManager;

	@SuppressWarnings("rawtypes")
	private final ServiceTrackerMap<String, SocialActivityManager>
		_serviceTrackerMap = ServiceTrackerCollections.singleValueMap(
			SocialActivityManager.class, "(model.className=*)",
			new ServiceReferenceMapper<String, SocialActivityManager>() {

				@Override
				public void map(
					ServiceReference<SocialActivityManager> serviceReference,
					Emitter<String> emitter) {

					String modelClassName =
						(String)serviceReference.getProperty("model.className");

					emitter.emit(modelClassName);
				}

			});

}