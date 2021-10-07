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

package com.liferay.object.internal.model.listener;

import com.liferay.object.action.engine.ObjectActionEngine;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = ModelListener.class)
public class ObjectEntryModelListener extends BaseModelListener<ObjectEntry> {

	@Override
	public void onAfterCreate(ObjectEntry objectEntry)
		throws ModelListenerException {

		_executeObjectActions(
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD, null, objectEntry);
	}

	@Override
	public void onAfterRemove(ObjectEntry objectEntry)
		throws ModelListenerException {

		_executeObjectActions(
			ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE, null,
			objectEntry);
	}

	@Override
	public void onAfterUpdate(
			ObjectEntry originalObjectEntry, ObjectEntry objectEntry)
		throws ModelListenerException {

		_executeObjectActions(
			ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE,
			originalObjectEntry, objectEntry);
	}

	private void _executeObjectActions(
			String objectActionTriggerKey, ObjectEntry originalObjectEntry,
			ObjectEntry objectEntry)
		throws ModelListenerException {

		try {
			long userId = PrincipalThreadLocal.getUserId();

			if (userId == 0) {
				userId = objectEntry.getUserId();
			}

			_objectActionEngine.executeObjectActions(
				objectEntry.getCompanyId(), userId,
				objectEntry.getModelClassName(), objectActionTriggerKey,
				HashMapBuilder.<String, Serializable>put(
					"payload",
					_getPayload(
						objectActionTriggerKey, originalObjectEntry,
						objectEntry)
				).build());
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	private Serializable _getPayload(
			String objectActionTriggerKey, ObjectEntry originalObjectEntry,
			ObjectEntry objectEntry)
		throws JSONException {

		JSONObject payloadJSONObject = JSONUtil.put(
			"objectActionTriggerKey", objectActionTriggerKey);

		JSONObject objectEntryJSONObject = _jsonFactory.createJSONObject(
			objectEntry.toString());

		objectEntryJSONObject.put("values", objectEntry.getValues());

		payloadJSONObject.put("objectEntry", objectEntryJSONObject);

		if (originalObjectEntry != null) {
			JSONObject originalObjectEntryJSONObject =
				_jsonFactory.createJSONObject(originalObjectEntry.toString());

			originalObjectEntryJSONObject.put(
				"values", originalObjectEntry.getValues());

			payloadJSONObject.put(
				"originalObjectEntry", originalObjectEntryJSONObject);
		}

		return payloadJSONObject.toString();
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectActionEngine _objectActionEngine;

}