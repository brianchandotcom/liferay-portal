/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.info.item.provider;

import com.liferay.friendly.url.info.item.provider.InfoItemFriendlyURLProvider;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.friendly.url.util.comparator.FriendlyURLEntryLocalizationComparator;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.List;

/**
 * @author Carolina Barbosa
 */
public class ObjectEntryInfoItemFriendlyURLProvider
	implements InfoItemFriendlyURLProvider<ObjectEntry> {

	public ObjectEntryInfoItemFriendlyURLProvider(
		FriendlyURLEntryLocalService friendlyURLEntryLocalService,
		GroupLocalService groupLocalService, ObjectDefinition objectDefinition,
		Portal portal) {

		_friendlyURLEntryLocalService = friendlyURLEntryLocalService;
		_groupLocalService = groupLocalService;
		_objectDefinition = objectDefinition;
		_portal = portal;
	}

	@Override
	public String getFriendlyURL(ObjectEntry objectEntry, String languageId) {
		if (_objectDefinition.isCMS()) {
			FriendlyURLEntry mainFriendlyURLEntry =
				_friendlyURLEntryLocalService.fetchMainFriendlyURLEntry(
					_portal.getClassNameId(objectEntry.getModelClassName()),
					objectEntry.getObjectEntryId());

			if (mainFriendlyURLEntry == null) {
				return String.valueOf(objectEntry.getObjectEntryId());
			}

			long groupId = _getGroupId();

			if ((groupId != GroupConstants.DEFAULT_LIVE_GROUP_ID) &&
				(groupId != mainFriendlyURLEntry.getGroupId())) {

				return _getGroupFriendlyURL(
					objectEntry.getObjectEntryId(), languageId,
					mainFriendlyURLEntry);
			}
		}

		String urlTitle = objectEntry.getURLTitle(
			LocaleUtil.fromLanguageId(languageId));

		if (Validator.isNotNull(urlTitle)) {
			return urlTitle;
		}

		if (!_objectDefinition.isDefaultStorageType()) {
			return objectEntry.getExternalReferenceCode();
		}

		return String.valueOf(objectEntry.getObjectEntryId());
	}

	@Override
	public List<FriendlyURLEntryLocalization> getFriendlyURLEntryLocalizations(
		ObjectEntry objectEntry, String languageId) {

		try {
			return _friendlyURLEntryLocalService.
				getFriendlyURLEntryLocalizations(
					objectEntry.getNonzeroGroupId(),
					_portal.getClassNameId(_objectDefinition.getClassName()),
					objectEntry.getObjectEntryId(), languageId,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					FriendlyURLEntryLocalizationComparator.getInstance(false));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return Collections.emptyList();
		}
	}

	private String _getGroupFriendlyURL(
		long objectEntryId, String languageId,
		FriendlyURLEntry friendlyURLEntry) {

		Group group = _groupLocalService.fetchGroup(
			friendlyURLEntry.getGroupId());

		if (group == null) {
			return String.valueOf(objectEntryId);
		}

		String groupFriendlyURL = group.getFriendlyURL();

		return groupFriendlyURL.replaceFirst(StringPool.SLASH, "") +
			StringPool.SLASH + friendlyURLEntry.getUrlTitle(languageId);
	}

	private long _getGroupId() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return GroupThreadLocal.getGroupId();
		}

		if (serviceContext.getThemeDisplay() == null) {
			return serviceContext.getScopeGroupId();
		}

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		if (themeDisplay.getSiteGroupId() !=
				GroupConstants.DEFAULT_LIVE_GROUP_ID) {

			return themeDisplay.getSiteGroupId();
		}

		return themeDisplay.getScopeGroupId();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryInfoItemFriendlyURLProvider.class);

	private final FriendlyURLEntryLocalService _friendlyURLEntryLocalService;
	private final GroupLocalService _groupLocalService;
	private final ObjectDefinition _objectDefinition;
	private final Portal _portal;

}