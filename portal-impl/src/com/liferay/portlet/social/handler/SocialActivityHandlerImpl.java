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

package com.liferay.portlet.social.handler;

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
public class SocialActivityHandlerImpl<T extends ClassedModel & GroupedModel>
	implements SocialActivityHandler<T> {

	public SocialActivityHandlerImpl(
		SocialActivityHandler<T> defaultSocialActivityHandler) {

		_defaultSocialActivityHandler = defaultSocialActivityHandler;
	}

	@Override
	public void addActivity(
			long userId, T classedModel, int type, String extraData,
			long receiverUserId)
		throws PortalException {

		SocialActivityHandler<T> socialActivityHandler =
			getSocialActivityHandler(classedModel.getModelClassName());

		socialActivityHandler.addActivity(
			userId, classedModel, type, extraData, receiverUserId);
	}

	@Override
	public void addUniqueActivity(
			long userId, long groupId, Date createDate, T classedModel,
			int type, String extraData, long receiverUserId)
		throws PortalException {

		SocialActivityHandler<T> socialActivityHandler =
			getSocialActivityHandler(classedModel.getModelClassName());

		socialActivityHandler.addUniqueActivity(
			userId, groupId, createDate, classedModel, type, extraData,
			receiverUserId);
	}

	@Override
	public void addUniqueActivity(
			long userId, long groupId, T classedModel, int type,
			String extraData, long receiverUserId)
		throws PortalException {

		SocialActivityHandler<T> socialActivityHandler =
			getSocialActivityHandler(classedModel.getModelClassName());

		socialActivityHandler.addUniqueActivity(
			userId, groupId, classedModel, type, extraData, receiverUserId);
	}

	@Override
	public void deleteActivities(T classedModel) throws PortalException {
		SocialActivityHandler<T> socialActivityHandler =
			getSocialActivityHandler(classedModel.getModelClassName());

		socialActivityHandler.deleteActivities(classedModel);
	}

	@Override
	public void updateLastSocialActivity(
			long userId, long groupId, T classedModel, int type,
			Date createDate)
		throws PortalException {

		SocialActivityHandler<T> socialActivityHandler =
			getSocialActivityHandler(classedModel.getModelClassName());

		socialActivityHandler.updateLastSocialActivity(
			userId, groupId, classedModel, type, createDate);
	}

	protected void activate() {
		_serviceTrackerMap.open();
	}

	@SuppressWarnings("unchecked")
	protected SocialActivityHandler<T> getSocialActivityHandler(
		String className) {

		SocialActivityHandler<T> socialActivityHandler =
			_serviceTrackerMap.getService(className);

		if (socialActivityHandler != null) {
			return socialActivityHandler;
		}

		return _defaultSocialActivityHandler;
	}

	private final SocialActivityHandler<T> _defaultSocialActivityHandler;

	@SuppressWarnings("rawtypes")
	private final ServiceTrackerMap<String, SocialActivityHandler>
		_serviceTrackerMap = ServiceTrackerCollections.singleValueMap(
			SocialActivityHandler.class, "(model.className=*)",
			new ServiceReferenceMapper<String, SocialActivityHandler>() {

				@Override
				public void map(
					ServiceReference<SocialActivityHandler> serviceReference,
					Emitter<String> emitter) {

					String modelClassName =
						(String)serviceReference.getProperty("model.className");

					emitter.emit(modelClassName);
				}

			});

}