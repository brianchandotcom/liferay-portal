/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.struts;

import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.ObjectRelationshipService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	property = "path=/cms/delete-structure", service = StrutsAction.class
)
public class DeleteStructureStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		long objectDefinitionId = ParamUtil.getLong(
			httpServletRequest, "objectDefinitionId");

		try {
			_deleteStructures(objectDefinitionId);
		}
		catch (Exception exception) {
			jsonObject = _jsonFactory.createJSONObject();

			jsonObject.put(
				"error",
				_language.get(
					httpServletRequest.getLocale(),
					"an-unexpected-error-occurred"));

			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);

		ServletResponseUtil.write(httpServletResponse, jsonObject.toString());

		return null;
	}

	private void _deleteStructures(long objectDefinitionId) throws Exception {
		Callable<Void> callable = new DeleteStructuresCallable(
			objectDefinitionId);

		try {
			TransactionInvokerUtil.invoke(_transactionConfig, callable);
		}
		catch (Throwable throwable) {
			if (throwable instanceof Exception) {
				throw (Exception)throwable;
			}

			throw new Exception(throwable);
		}
	}

	private void _getObjectDefinitionIds(
			long objectDefinitionId, List<Long> objectDefinitionIds,
			Map<Long, ObjectRelationship> objectRelationshipsMap,
			Set<Long> visitedObjectDefinitionIds)
		throws Exception {

		if (visitedObjectDefinitionIds.contains(objectDefinitionId)) {
			return;
		}

		visitedObjectDefinitionIds.add(objectDefinitionId);

		List<ObjectRelationship> objectRelationships =
			_objectRelationshipService.getObjectRelationships(
				objectDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (ObjectRelationship objectRelationship : objectRelationships) {
			if (!objectRelationship.isEdge()) {
				continue;
			}

			objectRelationshipsMap.put(
				objectRelationship.getObjectRelationshipId(),
				objectRelationship);

			_getObjectDefinitionIds(
				objectRelationship.getObjectDefinitionId2(),
				objectDefinitionIds, objectRelationshipsMap,
				visitedObjectDefinitionIds);
		}

		objectDefinitionIds.add(objectDefinitionId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeleteStructureStrutsAction.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private ObjectDefinitionService _objectDefinitionService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private ObjectRelationshipService _objectRelationshipService;

	private class DeleteStructuresCallable implements Callable<Void> {

		@Override
		public Void call() throws Exception {
			List<Long> objectDefinitionIds = new ArrayList<>();
			Map<Long, ObjectRelationship> objectRelationshipsMap =
				new HashMap<>();
			Set<Long> visitedObjectDefinitionIds = new HashSet<>();

			_getObjectDefinitionIds(
				_objectDefinitionId, objectDefinitionIds,
				objectRelationshipsMap, visitedObjectDefinitionIds);

			for (ObjectRelationship objectRelationship :
					objectRelationshipsMap.values()) {

				_objectRelationshipLocalService.updateObjectRelationship(
					objectRelationship.getExternalReferenceCode(),
					objectRelationship.getObjectRelationshipId(),
					objectRelationship.getParameterObjectFieldId(),
					objectRelationship.getDeletionType(), false,
					objectRelationship.getLabelMap(), null);
			}

			for (Long objectDefinitionId : objectDefinitionIds) {
				_objectDefinitionService.deleteObjectDefinition(
					objectDefinitionId);
			}

			return null;
		}

		private DeleteStructuresCallable(long objectDefinitionId) {
			_objectDefinitionId = objectDefinitionId;
		}

		private final long _objectDefinitionId;

	}

}