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

package com.liferay.object.web.internal.item.selector;

import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Guilherme Camacho
 */
public class ObjectEntryItemDescriptor
	implements ItemSelectorViewDescriptor.ItemDescriptor {

	public ObjectEntryItemDescriptor(
		HttpServletRequest httpServletRequest,
		ObjectDefinition objectDefinition, ObjectEntry objectEntry,
		Portal portal) {

		_httpServletRequest = httpServletRequest;
		_objectDefinition = objectDefinition;
		_objectEntry = objectEntry;
		_portal = portal;
	}

	@Override
	public String getIcon() {
		return null;
	}

	@Override
	public String getImageURL() {
		return null;
	}

	@Override
	public Date getModifiedDate() {
		if (Objects.equals(
				_objectDefinition.getStorageType(),
				ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE)) {

			return null;
		}

		return _objectEntry.getModifiedDate();
	}

	@Override
	public String getPayload() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return JSONUtil.put(
			"className", _objectDefinition.getClassName()
		).put(
			"classNameId",
			_portal.getClassNameId(_objectDefinition.getClassName())
		).put(
			"classPK", _getId()
		).put(
			"title",
			StringBundler.concat(
				_objectDefinition.getLabel(themeDisplay.getLocale()),
				StringPool.SPACE, _getId())
		).toString();
	}

	@Override
	public String getSubtitle(Locale locale) {
		return _getId();
	}

	@Override
	public String getTitle(Locale locale) {
		if (Objects.equals(
				_objectDefinition.getStorageType(),
				ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE)) {

			return _objectEntry.getExternalReferenceCode();
		}

		try {
			return _objectEntry.getTitleValue();
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public long getUserId() {
		if (Objects.equals(
				_objectDefinition.getStorageType(),
				ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE)) {

			return 0;
		}

		return _objectEntry.getUserId();
	}

	@Override
	public String getUserName() {
		if (Objects.equals(
				_objectDefinition.getStorageType(),
				ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE)) {

			return StringPool.BLANK;
		}

		return _objectEntry.getUserName();
	}

	private String _getId() {
		if (Objects.equals(
				_objectDefinition.getStorageType(),
				ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE)) {

			return _objectEntry.getExternalReferenceCode();
		}

		return String.valueOf(_objectEntry.getObjectEntryId());
	}

	private final HttpServletRequest _httpServletRequest;
	private final ObjectDefinition _objectDefinition;
	private final ObjectEntry _objectEntry;
	private final Portal _portal;

}