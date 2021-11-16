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

package com.liferay.frontend.data.set.sample.web.internal;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier de Arcos
 */
@Component(service = FrontendDataSetSampleGenerator.class)
public class FrontendDataSetSampleGenerator {

	public void generateFrontendDataSetSample(long companyId) {
		try {
			_generateFrontendDataSetSample(companyId);
		}
		catch (Exception exception) {
			_generated = null;

			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}
	}

	private void _generateFrontendDataSetSample(long companyId)
		throws Exception {

		if (_isFrontendDataSetSampleGenerated(companyId)) {
			return;
		}

		User user = _userLocalService.fetchUserByEmailAddress(
			companyId, "test@liferay.com");

		if (user == null) {
			return;
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				companyId, "C_FrontendDataSetSample");

		if (objectDefinition == null) {
			objectDefinition =
				_objectDefinitionLocalService.addCustomObjectDefinition(
					user.getUserId(),
					LocalizedMapUtil.getLocalizedMap(
						"Frontend Data Set Sample"),
					"FrontendDataSetSample", "100", null,
					LocalizedMapUtil.getLocalizedMap(
						"Frontend Data Set Samples"),
					ObjectDefinitionConstants.SCOPE_COMPANY,
					Arrays.asList(
						ObjectFieldUtil.createObjectField(
							true, false, null, "Title", "title", false,
							"String"),
						ObjectFieldUtil.createObjectField(
							true, false, null, "Description", "description",
							false, "String"),
						ObjectFieldUtil.createObjectField(
							true, false, null, "Date", "date", false, "Date")));

			objectDefinition =
				_objectDefinitionLocalService.publishCustomObjectDefinition(
					user.getUserId(), objectDefinition.getObjectDefinitionId());
		}

		for (int i = 1; i <= 100; i++) {
			_objectEntryLocalService.addObjectEntry(
				user.getUserId(), 0, objectDefinition.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"date", new Date()
				).put(
					"description", "Description of the sample " + i
				).put(
					"title", "Sample" + i
				).build(),
				new ServiceContext());
		}

		_generated = true;
	}

	private boolean _isFrontendDataSetSampleGenerated(long companyId) {
		if (_generated != null) {
			return _generated;
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				companyId, "C_FrontendDataSetSample");

		if (objectDefinition == null) {
			_generated = false;

			return false;
		}

		int samples = _objectEntryLocalService.getObjectEntriesCount(
			0L, objectDefinition.getObjectDefinitionId());

		_generated = samples > 0;

		return _generated;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FrontendDataSetSampleGenerator.class);

	private Boolean _generated;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}