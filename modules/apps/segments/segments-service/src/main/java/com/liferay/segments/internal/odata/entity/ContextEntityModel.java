/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.odata.entity;

import com.liferay.portal.odata.entity.BooleanEntityField;
import com.liferay.portal.odata.entity.CollectionEntityField;
import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.DateEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.DoubleEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.segments.context.Context;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provides the entity data model for the context that segments users.
 *
 * @author Eduardo García
 */
public class ContextEntityModel implements EntityModel {

	public static final String NAME = "Context";

	public ContextEntityModel(List<EntityField> customEntityFields) {
		_entityFieldsMap = new ConcurrentHashMap<>(
			EntityModel.toEntityFieldsMap(
				new BooleanEntityField(
					Context.SIGNED_IN, locale -> Context.SIGNED_IN),
				new CollectionEntityField(
					new StringEntityField(
						Context.COOKIES, locale -> Context.COOKIES)),
				new CollectionEntityField(
					new StringEntityField(
						Context.REQUEST_PARAMETERS,
						locale -> Context.REQUEST_PARAMETERS)),
				new ComplexEntityField("customContext", customEntityFields),
				new DateEntityField(
					Context.LOCAL_DATE, locale -> Context.LOCAL_DATE,
					locale -> Context.LOCAL_DATE),
				new DateTimeEntityField(
					Context.LAST_SIGN_IN_DATE_TIME,
					locale -> Context.LAST_SIGN_IN_DATE_TIME,
					locale -> Context.LAST_SIGN_IN_DATE_TIME),
				new DoubleEntityField(
					Context.DEVICE_SCREEN_RESOLUTION_HEIGHT,
					locale -> Context.DEVICE_SCREEN_RESOLUTION_HEIGHT),
				new DoubleEntityField(
					Context.DEVICE_SCREEN_RESOLUTION_WIDTH,
					locale -> Context.DEVICE_SCREEN_RESOLUTION_WIDTH),
				new StringEntityField(
					Context.BROWSER, locale -> Context.BROWSER),
				new StringEntityField(
					Context.DEVICE_BRAND, locale -> Context.DEVICE_BRAND),
				new StringEntityField(
					Context.DEVICE_MODEL, locale -> Context.DEVICE_MODEL),
				new StringEntityField(
					Context.HOSTNAME, locale -> Context.HOSTNAME),
				new StringEntityField(
					Context.LANGUAGE_ID, locale -> Context.LANGUAGE_ID),
				new StringEntityField(
					Context.REFERRER_URL, locale -> Context.REFERRER_URL),
				new StringEntityField(Context.URL, locale -> Context.URL),
				new StringEntityField(
					Context.USER_AGENT, locale -> Context.USER_AGENT)));
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	@Override
	public String getName() {
		return NAME;
	}

	private final Map<String, EntityField> _entityFieldsMap;

}