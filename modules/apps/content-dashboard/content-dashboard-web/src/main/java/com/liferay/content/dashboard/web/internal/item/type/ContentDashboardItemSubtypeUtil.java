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

package com.liferay.content.dashboard.web.internal.item.type;

import com.liferay.content.dashboard.info.item.ClassNameClassPKInfoItemIdentifier;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtype;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtypeFactory;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtypeFactoryRegistry;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Cristina González
 */
public class ContentDashboardItemSubtypeUtil {

	public static ContentDashboardItemSubtype toContentDashboardItemSubtype(
		ContentDashboardItemSubtypeFactoryRegistry
			contentDashboardItemSubtypeFactoryRegistry,
		InfoItemReference infoItemReference) {

		if (infoItemReference.getInfoItemIdentifier() instanceof
				ClassNameClassPKInfoItemIdentifier) {

			ClassNameClassPKInfoItemIdentifier
				classNameClassPKInfoItemIdentifier =
					(ClassNameClassPKInfoItemIdentifier)
						infoItemReference.getInfoItemIdentifier();

			return _toContentDashboardItemSubtype(
				contentDashboardItemSubtypeFactoryRegistry.
					getContentDashboardItemSubtypeFactory(
						classNameClassPKInfoItemIdentifier.getClassName()),
				classNameClassPKInfoItemIdentifier.getClassPK());
		}
		else if (infoItemReference.getInfoItemIdentifier() instanceof
					ClassPKInfoItemIdentifier) {

			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)
					infoItemReference.getInfoItemIdentifier();

			return _toContentDashboardItemSubtype(
				contentDashboardItemSubtypeFactoryRegistry.
					getContentDashboardItemSubtypeFactory(
						infoItemReference.getClassName()),
				classPKInfoItemIdentifier.getClassPK());
		}

		return null;
	}

	public static ContentDashboardItemSubtype toContentDashboardItemSubtype(
		ContentDashboardItemSubtypeFactoryRegistry
			contentDashboardItemSubtypeFactoryRegistry,
		JSONObject contentDashboardItemSubtypePayloadJSONObject) {

		String className =
			contentDashboardItemSubtypePayloadJSONObject.getString("className");

		if (Validator.isNull(className)) {
			return toContentDashboardItemSubtype(
				contentDashboardItemSubtypeFactoryRegistry,
				new InfoItemReference(
					contentDashboardItemSubtypePayloadJSONObject.getString(
						"entryClassName"),
					0));
		}

		return toContentDashboardItemSubtype(
			contentDashboardItemSubtypeFactoryRegistry,
			new InfoItemReference(
				contentDashboardItemSubtypePayloadJSONObject.getString(
					"entryClassName"),
				new ClassNameClassPKInfoItemIdentifier(
					GetterUtil.getString(className),
					GetterUtil.getLong(
						contentDashboardItemSubtypePayloadJSONObject.getLong(
							"classPK")))));
	}

	public static ContentDashboardItemSubtype toContentDashboardItemSubtype(
		ContentDashboardItemSubtypeFactoryRegistry
			contentDashboardItemSubtypeFactoryRegistry,
		String contentDashboardItemSubtypePayload) {

		try {
			return toContentDashboardItemSubtype(
				contentDashboardItemSubtypeFactoryRegistry,
				JSONFactoryUtil.createJSONObject(
					contentDashboardItemSubtypePayload));
		}
		catch (JSONException jsonException) {
			_log.error(jsonException);

			return null;
		}
	}

	private static ContentDashboardItemSubtype _toContentDashboardItemSubtype(
		ContentDashboardItemSubtypeFactory contentDashboardItemSubtypeFactory,
		Long classPK) {

		try {
			if (contentDashboardItemSubtypeFactory != null) {
				return contentDashboardItemSubtypeFactory.create(classPK);
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentDashboardItemSubtypeUtil.class);

}