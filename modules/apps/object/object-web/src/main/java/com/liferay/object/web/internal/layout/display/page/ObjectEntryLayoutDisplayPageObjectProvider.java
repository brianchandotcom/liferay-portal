/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.layout.display.page;

import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
	public String getClassName() {
		return _objectDefinition.getClassName();
	}

	@Override
	public long getClassNameId() {
		return PortalUtil.getClassNameId(_objectDefinition.getClassName());
	}

	@Override
	public long getClassPK() {
		return _objectEntry.getObjectEntryId();
	}

	@Override
	public long getClassTypeId() {
		return 0;
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
	public String getExternalReferenceCode() {
		if (!_objectDefinition.isDefaultStorageType()) {
			return _objectEntry.getExternalReferenceCode();
		}

		return StringPool.BLANK;
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
		if (!_objectDefinition.isDefaultStorageType()) {
			return _objectEntry.getExternalReferenceCode();
		}

		try {
			return _objectEntry.getTitleValue();
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return StringPool.BLANK;
	}

	@Override
	public String getURLTitle(Locale locale) {
		if (!_objectDefinition.isDefaultStorageType()) {
			return _objectEntry.getExternalReferenceCode();
		}

		return String.valueOf(_objectEntry.getObjectEntryId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryLayoutDisplayPageObjectProvider.class);

	private final ObjectDefinition _objectDefinition;
	private final ObjectEntry _objectEntry;

}