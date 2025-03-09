/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.layout.display.page;

import com.liferay.friendly.url.info.item.provider.InfoItemFriendlyURLProvider;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Locale;

/**
 * @author Marco Leo
 */
public class ObjectEntryFolderLayoutDisplayPageObjectProvider
	implements LayoutDisplayPageObjectProvider<ObjectEntryFolder> {

	public ObjectEntryFolderLayoutDisplayPageObjectProvider(
		InfoItemFriendlyURLProvider<ObjectEntryFolder>
			infoItemFriendlyURLProvider,
		Language language, ObjectEntryFolder objectEntryFolder) {

		_infoItemFriendlyURLProvider = infoItemFriendlyURLProvider;
		_language = language;
		_objectEntryFolder = objectEntryFolder;
	}

	@Override
	public String getClassName() {
		return ObjectEntryFolder.class.getName();
	}

	@Override
	public long getClassNameId() {
		return PortalUtil.getClassNameId(ObjectEntryFolder.class.getName());
	}

	@Override
	public long getClassPK() {
		return _objectEntryFolder.getObjectEntryFolderId();
	}

	@Override
	public long getClassTypeId() {
		return 0;
	}

	@Override
	public String getDescription(Locale locale) {
		return _objectEntryFolder.getName();
	}

	@Override
	public ObjectEntryFolder getDisplayObject() {
		return _objectEntryFolder;
	}

	@Override
	public long getGroupId() {
		return _objectEntryFolder.getGroupId();
	}

	@Override
	public String getKeywords(Locale locale) {
		return null;
	}

	@Override
	public String getTitle(Locale locale) {
		return _objectEntryFolder.getName();
	}

	@Override
	public String getURLTitle(Locale locale) {
		return _infoItemFriendlyURLProvider.getFriendlyURL(
			_objectEntryFolder, _language.getLanguageId(locale));
	}

	private final InfoItemFriendlyURLProvider<ObjectEntryFolder>
		_infoItemFriendlyURLProvider;
	private final Language _language;
	private final ObjectEntryFolder _objectEntryFolder;

}