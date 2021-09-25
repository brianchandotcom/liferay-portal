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

package com.liferay.object.internal.action;

import com.liferay.object.action.ObjectAction;
import com.liferay.object.action.ObjectActionEngine;
import com.liferay.object.action.ObjectActionRequest;
import com.liferay.object.model.ObjectActionEntry;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectActionEntryLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.Serializable;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = ObjectActionEngine.class)
public class ObjectActionEngineImpl implements ObjectActionEngine {

	@Override
	public void executeObjectActions(
		long userId, String className, String triggerName,
		Serializable payload) {

		try {
			User user = _userLocalService.getUser(userId);

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.getObjectDefinitionByClassName(
					user.getCompanyId(), className);

			List<ObjectActionEntry> objectActionEntries =
				_objectActionEntryLocalService.getObjectActionEntries(
					objectDefinition.getObjectDefinitionId(), triggerName);

			for (ObjectActionEntry objectActionEntry : objectActionEntries) {
				ObjectAction objectAction = _serviceTrackerMap.getService(
					objectActionEntry.getName());

				ObjectActionRequest objectActionRequest =
					new ObjectActionRequest(userId);

				objectActionRequest.setProperties(
					HashMapBuilder.<String, Serializable>putAll(
						objectActionEntry.getSettingsUnicodeProperties()
					).build());

				objectAction.execute(objectActionRequest);
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ObjectAction.class, null,
			(serviceReference, emitter) -> {
				ObjectAction objectAction = bundleContext.getService(
					serviceReference);

				emitter.emit(objectAction.getName());
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectActionEngineImpl.class);

	@Reference
	private ObjectActionEntryLocalService _objectActionEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private ServiceTrackerMap<String, ObjectAction> _serviceTrackerMap;

	@Reference
	private UserLocalService _userLocalService;

}