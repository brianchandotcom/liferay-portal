/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.layout.display.page;

import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.ERCInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.BaseLayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.web.internal.util.ObjectEntryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

/**
 * @author Guilherme Camacho
 */
public class ObjectEntryLayoutDisplayPageProvider
	extends BaseLayoutDisplayPageProvider<ObjectEntry> {

	public ObjectEntryLayoutDisplayPageProvider(
		ObjectDefinition objectDefinition,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectEntryManager objectEntryManager,
		UserLocalService userLocalService) {

		_objectDefinition = objectDefinition;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryLocalService = objectEntryLocalService;
		_objectEntryManager = objectEntryManager;
		_userLocalService = userLocalService;
	}

	@Override
	public String getClassName() {
		return _objectDefinition.getClassName();
	}

	@Override
	public String getDefaultURLSeparator() {
		return FriendlyURLResolverConstants.URL_SEPARATOR_OBJECT_ENTRY;
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

			if (objectEntry == null) {
				return null;
			}

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					objectEntry.getObjectDefinitionId());

			return new ObjectEntryLayoutDisplayPageObjectProvider(
				objectDefinition, objectEntry);
		}

		ERCInfoItemIdentifier ercInfoItemIdentifier =
			(ERCInfoItemIdentifier)infoItemIdentifier;

		try {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			if (serviceContext == null) {
				return null;
			}

			long userId = serviceContext.getUserId();

			if (userId == 0) {
				userId = PrincipalThreadLocal.getUserId();
			}

			com.liferay.object.rest.dto.v1_0.ObjectEntry objectEntry =
				_objectEntryManager.getObjectEntry(
					serviceContext.getCompanyId(),
					new DefaultDTOConverterContext(
						false, null, null, null, null,
						serviceContext.getLocale(), null,
						_userLocalService.fetchUser(userId)),
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
	public LayoutDisplayPageObjectProvider<ObjectEntry>
		getLayoutDisplayPageObjectProvider(ObjectEntry objectEntry) {

		return new ObjectEntryLayoutDisplayPageObjectProvider(
			_objectDefinition, objectEntry);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryLayoutDisplayPageProvider.class);

	private final ObjectDefinition _objectDefinition;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectEntryManager _objectEntryManager;
	private final UserLocalService _userLocalService;

}