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

import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.ERCInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.web.internal.util.ObjectEntryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

/**
 * @author Guilherme Camacho
 */
public class ObjectEntryLayoutDisplayPageProvider
	implements LayoutDisplayPageProvider<ObjectEntry> {

	public ObjectEntryLayoutDisplayPageProvider(
		ObjectDefinition objectDefinition,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectEntryManager objectEntryManager) {

		_objectDefinition = objectDefinition;
		_objectEntryLocalService = objectEntryLocalService;
		_objectEntryManager = objectEntryManager;
	}

	@Override
	public String getClassName() {
		return _objectDefinition.getClassName();
	}

	@Override
	public LayoutDisplayPageObjectProvider<ObjectEntry>
		getLayoutDisplayPageObjectProvider(
			InfoItemReference infoItemReference) {

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier) &&
			!(infoItemIdentifier instanceof ERCInfoItemIdentifier)) {

			return null;
		}

		if (infoItemIdentifier instanceof ClassPKInfoItemIdentifier) {
			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)infoItemIdentifier;

			ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
				classPKInfoItemIdentifier.getClassPK());

			if ((objectEntry == null) || objectEntry.isDraft()) {
				return null;
			}

			return new ObjectEntryLayoutDisplayPageObjectProvider(
				_objectDefinition, objectEntry);
		}

		ERCInfoItemIdentifier ercInfoItemIdentifier =
			(ERCInfoItemIdentifier)infoItemIdentifier;

		try {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			if (serviceContext == null) {
				return null;
			}

			ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

			if (themeDisplay == null) {
				return null;
			}

			com.liferay.object.rest.dto.v1_0.ObjectEntry objectEntry =
				_objectEntryManager.getObjectEntry(
					themeDisplay.getCompanyId(),
					new DefaultDTOConverterContext(
						false, null, null, null, null, themeDisplay.getLocale(),
						null, themeDisplay.getUser()),
					ercInfoItemIdentifier.getExternalReferenceCode(),
					_objectDefinition, null);

			if (objectEntry != null) {
				return new ObjectEntryLayoutDisplayPageObjectProvider(
					_objectDefinition,
					ObjectEntryUtil.toObjectEntry(
						_objectDefinition.getObjectDefinitionId(),
						objectEntry));
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return null;
	}

	@Override
	public LayoutDisplayPageObjectProvider<ObjectEntry>
		getLayoutDisplayPageObjectProvider(long groupId, String urlTitle) {

		if (!_objectDefinition.isDefaultStorageType()) {
			return getLayoutDisplayPageObjectProvider(
				new InfoItemReference(
					ObjectEntry.class.getName(),
					new ERCInfoItemIdentifier(urlTitle)));
		}

		return getLayoutDisplayPageObjectProvider(
			new InfoItemReference(
				ObjectEntry.class.getName(),
				new ClassPKInfoItemIdentifier(GetterUtil.getLong(urlTitle))));
	}

	@Override
	public String getURLSeparator() {
		return FriendlyURLResolverConstants.URL_SEPARATOR_OBJECT_ENTRY;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryLayoutDisplayPageProvider.class);

	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectEntryManager _objectEntryManager;

}