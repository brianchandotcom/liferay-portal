/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.internal.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Objects;

/**
 * @author Carlos Correa
 */
public class LaunchUtil {

	public static ObjectEntry fetchLaunchObjectEntry(long launchId) {
		ObjectEntry objectEntry = ObjectEntryLocalServiceUtil.fetchObjectEntry(
			launchId);

		if (objectEntry == null) {
			return null;
		}

		if (!_isLaunch(objectEntry)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Object entry " + launchId + " is not a launch");
			}

			return null;
		}

		return objectEntry;
	}

	public static Predicate getEqualsPredicate(
		String fieldName, long objectDefinitionId, Object value) {

		Column<?, Object> column =
			(Column<?, Object>)ObjectFieldLocalServiceUtil.getColumn(
				objectDefinitionId, fieldName);

		return column.eq(value);
	}

	public static ObjectEntry getLaunchObjectEntry(long launchId)
		throws PortalException {

		ObjectEntry objectEntry = ObjectEntryLocalServiceUtil.getObjectEntry(
			launchId);

		if (!_isLaunch(objectEntry)) {
			throw new PortalException(
				"Object entry " + launchId + " is not a launch");
		}

		return objectEntry;
	}

	private static boolean _isLaunch(ObjectEntry objectEntry) {
		ObjectDefinition objectDefinition = objectEntry.getObjectDefinition();

		if (objectDefinition == null) {
			return false;
		}

		return Objects.equals(
			objectDefinition.getExternalReferenceCode(), "L_LAUNCH");
	}

	private static final Log _log = LogFactoryUtil.getLog(LaunchUtil.class);

}