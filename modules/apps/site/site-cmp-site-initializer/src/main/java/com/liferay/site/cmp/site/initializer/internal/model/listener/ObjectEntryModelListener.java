/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.model.listener;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.time.LocalDate;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(service = ModelListener.class)
public class ObjectEntryModelListener extends BaseModelListener<ObjectEntry> {

	@Override
	public void onAfterCreate(ObjectEntry objectEntry)
		throws ModelListenerException {

		try {
			_updateProjectCompletionRate(objectEntry);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Override
	public void onAfterRemove(ObjectEntry objectEntry)
		throws ModelListenerException {

		try {
			_updateProjectCompletionRate(objectEntry);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Override
	public void onAfterUpdate(
			ObjectEntry originalObjectEntry, ObjectEntry objectEntry)
		throws ModelListenerException {

		try {
			_updateProjectCompletionRate(objectEntry);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private int _getCount(
			String filterString, ObjectDefinition objectDefinition,
			ObjectEntry objectEntry)
		throws Exception {

		return _objectEntryLocalService.getValuesListCount(
			new Long[] {objectEntry.getGroupId()}, 0, 0,
			objectEntry.getObjectDefinitionId(),
			_filterFactory.create(filterString, objectDefinition), false, null);
	}

	private void _updateProjectCompletionRate(ObjectEntry objectEntry)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectEntry.getObjectDefinitionId());

		if (!StringUtil.equals(
				objectDefinition.getExternalReferenceCode(), "L_CMP_TASK")) {

			return;
		}

		ObjectEntry parentObjectEntry =
			_objectEntryLocalService.fetchObjectEntry(
				MapUtil.getLong(
					objectEntry.getValues(),
					"r_cmpProjectToCMPTasks_c_cmpProjectId"));

		if (parentObjectEntry == null) {
			return;
		}

		int totalCount = _getCount(null, objectDefinition, objectEntry);

		if (totalCount == 0) {
			return;
		}

		int blockedCount = _getCount(
			"state eq 'blocked'", objectDefinition, objectEntry);
		int doneCount = _getCount(
			"state eq 'done'", objectDefinition, objectEntry);
		int inProgressCount = _getCount(
			"state eq 'inProgress'", objectDefinition, objectEntry);
		int overdueCount = _getCount(
			StringBundler.concat(
				"dueDate lt ", LocalDate.now(), " and state ne 'done'"),
			objectDefinition, objectEntry);

		int completionRate = (doneCount * 100) / totalCount;

		if (Objects.equals(
				MapUtil.getInteger(
					parentObjectEntry.getValues(), "blockedCount"),
				blockedCount) &&
			Objects.equals(
				MapUtil.getInteger(
					parentObjectEntry.getValues(), "completionRate"),
				completionRate) &&
			Objects.equals(
				MapUtil.getInteger(parentObjectEntry.getValues(), "doneCount"),
				doneCount) &&
			Objects.equals(
				MapUtil.getInteger(
					parentObjectEntry.getValues(), "inProgressCount"),
				inProgressCount) &&
			Objects.equals(
				MapUtil.getInteger(
					parentObjectEntry.getValues(), "overdueCount"),
				overdueCount) &&
			Objects.equals(
				MapUtil.getInteger(parentObjectEntry.getValues(), "totalCount"),
				totalCount)) {

			return;
		}

		_objectEntryLocalService.partialUpdateObjectEntry(
			parentObjectEntry.getUserId(), parentObjectEntry.getObjectEntryId(),
			parentObjectEntry.getObjectEntryFolderId(),
			HashMapBuilder.<String, Serializable>put(
				"blockedCount", blockedCount
			).put(
				"completionRate", completionRate
			).put(
				"doneCount", doneCount
			).put(
				"inProgressCount", inProgressCount
			).put(
				"overdueCount", overdueCount
			).put(
				"totalCount", totalCount
			).build(),
			new ServiceContext());
	}

	@Reference(
		target = "(filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT + ")"
	)
	private FilterFactory<Predicate> _filterFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}