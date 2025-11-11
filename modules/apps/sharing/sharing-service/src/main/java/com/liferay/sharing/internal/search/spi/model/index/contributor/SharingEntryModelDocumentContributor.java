/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.internal.search.spi.model.index.contributor;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.sharing.configuration.SharingConfiguration;
import com.liferay.sharing.configuration.SharingConfigurationFactory;
import com.liferay.sharing.interpreter.SharingEntryInterpreter;
import com.liferay.sharing.interpreter.SharingEntryInterpreterProvider;
import com.liferay.sharing.model.SharingEntry;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(
	property = "indexer.class.name=com.liferay.sharing.model.SharingEntry",
	service = ModelDocumentContributor.class
)
public class SharingEntryModelDocumentContributor
	implements ModelDocumentContributor<SharingEntry> {

	@Override
	public void contribute(Document document, SharingEntry sharingEntry) {
		document.addKeyword(Field.CLASS_NAME_ID, sharingEntry.getClassNameId());
		document.addKeyword(
			Field.CLASS_PK, String.valueOf(sharingEntry.getClassPK()));
		document.addDate(Field.CREATE_DATE, sharingEntry.getCreateDate());
		document.addDate(Field.MODIFIED_DATE, sharingEntry.getModifiedDate());
		document.addLocalizedText(Field.TITLE, _getTitleMap(sharingEntry));
		document.addKeyword(Field.USER_ID, sharingEntry.getUserId());
		document.addLocalizedText(
			"localized_title", _getTitleMap(sharingEntry), true);
		document.addKeyword(
			"sharingEntryId", String.valueOf(sharingEntry.getSharingEntryId()));
		document.addKeyword(
			"spaceDepotEntry", _isSpaceDepotEntry(sharingEntry.getGroupId()));
		document.addKeyword(
			"toUserGroupId", String.valueOf(sharingEntry.getToUserGroupId()));
		document.addKeyword(
			"toUserId", String.valueOf(sharingEntry.getToUserId()));
		document.addKeyword("visible", _isVisible(sharingEntry));
	}

	private Map<Locale, String> _getTitleMap(SharingEntry sharingEntry) {
		SharingEntryInterpreter sharingEntryInterpreter =
			_sharingEntryInterpreterProvider.getSharingEntryInterpreter(
				sharingEntry);

		if (sharingEntryInterpreter == null) {
			return null;
		}

		return sharingEntryInterpreter.getTitleMap(sharingEntry);
	}

	private boolean _isSpaceDepotEntry(long groupId) {
		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get group " + groupId +
						" while indexing document");
			}

			return false;
		}

		if (group.isDepot()) {
			DepotEntry depotEntry =
				_depotEntryLocalService.fetchGroupDepotEntry(groupId);

			if ((depotEntry != null) &&
				Objects.equals(
					depotEntry.getType(), DepotConstants.TYPE_SPACE)) {

				return true;
			}
		}

		return false;
	}

	private boolean _isVisible(SharingEntry sharingEntry) {
		SharingEntryInterpreter sharingEntryInterpreter =
			_sharingEntryInterpreterProvider.getSharingEntryInterpreter(
				sharingEntry);

		try {
			if ((sharingEntryInterpreter == null) ||
				!sharingEntryInterpreter.isVisible(sharingEntry)) {

				return false;
			}

			Group group = _groupLocalService.fetchGroup(
				sharingEntry.getGroupId());

			if (group == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get group " + sharingEntry.getGroupId() +
							" while indexing document");
				}

				return false;
			}

			SharingConfiguration groupSharingConfiguration =
				_sharingConfigurationFactory.getGroupSharingConfiguration(
					group);

			return groupSharingConfiguration.isEnabled();
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SharingEntryModelDocumentContributor.class);

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private SharingConfigurationFactory _sharingConfigurationFactory;

	@Reference
	private SharingEntryInterpreterProvider _sharingEntryInterpreterProvider;

}