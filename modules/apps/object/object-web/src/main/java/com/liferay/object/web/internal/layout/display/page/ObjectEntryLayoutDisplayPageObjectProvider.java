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

package com.liferay.object.web.internal.layout.display.page;

import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Locale;

/**
 * @author Guilherme Camacho
 */
public class ObjectEntryLayoutDisplayPageObjectProvider
	implements LayoutDisplayPageObjectProvider<ObjectEntry> {

	public ObjectEntryLayoutDisplayPageObjectProvider(
		ObjectDefinition objectDefinition, ObjectEntry objectEntry) {

		_objectDefinition = objectDefinition;
		_objectEntry = objectEntry;
	}

	@Override
	public long getClassNameId() {
		return PortalUtil.getClassNameId(ObjectEntry.class.getName());
	}

	@Override
	public long getClassPK() {
		return _objectEntry.getObjectEntryId();
	}

	@Override
	public long getClassTypeId() {
		return _objectEntry.getObjectDefinitionId();
	}

	@Override
	public String getDescription(Locale locale) {
		return null;
	}

	@Override
	public ObjectEntry getDisplayObject() {
		return _objectEntry;
	}

	@Override
	public long getGroupId() {
		return _objectEntry.getGroupId();
	}

	@Override
	public String getKeywords(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getTitle(Locale locale) {
		return _objectDefinition.getLabel(locale);
	}

	@Override
	public String getURLTitle(Locale locale) {
		return String.valueOf(_objectEntry.getObjectEntryId());
	}

	private final ObjectDefinition _objectDefinition;
	private final ObjectEntry _objectEntry;

}