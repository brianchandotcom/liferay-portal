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

package com.liferay.adaptive.media.document.library.thumbnails.internal.upgrade.v1_0_1;

import com.liferay.adaptive.media.exception.AMImageConfigurationException;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PrefsPropsUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author Jonathan McCann
 */
public class UpgradeDocumentLibraryThumbnailsPreviewConfiguration
	extends UpgradeProcess {

	public UpgradeDocumentLibraryThumbnailsPreviewConfiguration(
		AMImageConfigurationHelper amImageConfigurationHelper,
		CompanyLocalService companyLocalService) {

		_amImageConfigurationHelper = amImageConfigurationHelper;
		_companyLocalService = companyLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			int dlFileEntryPreviewMaxHeight = PrefsPropsUtil.getInteger(
				PropsKeys.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_HEIGHT);
			int dlFileEntryPreviewMaxWidth = PrefsPropsUtil.getInteger(
				PropsKeys.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_WIDTH);

			if ((dlFileEntryPreviewMaxHeight <= 0) &&
				(dlFileEntryPreviewMaxWidth <= 0)) {

				return;
			}

			String name = String.format(
				"%s %dx%d", "Preview", dlFileEntryPreviewMaxWidth,
				dlFileEntryPreviewMaxHeight);

			String normalizedName = _normalize(name);

			Map<String, String> properties = new HashMap<>();

			properties.put(
				"max-height", String.valueOf(dlFileEntryPreviewMaxHeight));
			properties.put(
				"max-width", String.valueOf(dlFileEntryPreviewMaxWidth));

			ActionableDynamicQuery actionableDynamicQuery =
				_companyLocalService.getActionableDynamicQuery();

			actionableDynamicQuery.setPerformActionMethod(
				(Company company) -> {
					try {
						Collection<AMImageConfigurationEntry>
							amImageConfigurationEntries =
								_amImageConfigurationHelper.
									getAMImageConfigurationEntries(
										company.getCompanyId(),
										amImageConfigurationEntry -> true);

						if (_isDuplicate(
								amImageConfigurationEntries, name,
								normalizedName)) {

							return;
						}

						_amImageConfigurationHelper.
							addAMImageConfigurationEntry(
								company.getCompanyId(), name,
								"This image resolution was automatically " +
									"added.",
								normalizedName, properties);
					}
					catch (Exception e) {
						_log.error(e, e);
					}
				});

			actionableDynamicQuery.performActions();
		}
	}

	private boolean _isDuplicate(
			Collection<AMImageConfigurationEntry> amImageConfigurationEntries,
			String name, String uuid)
		throws AMImageConfigurationException {

		Stream<AMImageConfigurationEntry> amImageConfigurationEntryStream =
			amImageConfigurationEntries.stream();

		Optional<AMImageConfigurationEntry>
			duplicateNameAMImageConfigurationEntryOptional =
				amImageConfigurationEntryStream.filter(
					amImageConfigurationEntry ->
						name.equals(amImageConfigurationEntry.getName()) ||
						uuid.equals(amImageConfigurationEntry.getUUID())
				).findFirst();

		return duplicateNameAMImageConfigurationEntryOptional.isPresent();
	}

	private String _normalize(String str) {
		Matcher matcher = _pattern.matcher(str);

		return matcher.replaceAll(StringPool.DASH);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDocumentLibraryThumbnailsPreviewConfiguration.class);

	private static final Pattern _pattern = Pattern.compile("[^\\w-]");

	private final AMImageConfigurationHelper _amImageConfigurationHelper;
	private final CompanyLocalService _companyLocalService;

}