/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.site.cms.site.initializer.internal.constants.CMSSpaceConstants;
import com.liferay.site.cms.site.initializer.internal.util.ActionUtil;
import com.liferay.site.cms.site.initializer.internal.util.SpaceSummaryHeaderUtil;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporterRegistry;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.Map;

/**
 * @author Roberto Díaz
 */
public class ViewSpaceFilesSummarySectionDisplayContext
	extends BaseFilesSectionDisplayContext {

	public ViewSpaceFilesSummarySectionDisplayContext(
		DepotEntryLocalService depotEntryLocalService,
		DLConfiguration dlConfiguration, long groupId,
		GroupLocalService groupLocalService,
		HttpServletRequest httpServletRequest, Language language,
		ObjectDefinitionService objectDefinitionService,
		ObjectEntryFolderLocalService objectEntryFolderLocalService,
		Portal portal,
		TranslationInfoItemFieldValuesExporterRegistry
			translationInfoItemFieldValuesExporterRegistry) {

		super(
			depotEntryLocalService, dlConfiguration, groupLocalService,
			httpServletRequest, language, objectDefinitionService, portal,
			translationInfoItemFieldValuesExporterRegistry);

		_groupId = groupId;
		_objectEntryFolderLocalService = objectEntryFolderLocalService;
	}

	@Override
	public String getAdditionalAPIURLParameters() {
		return StringBundler.concat(
			super.getAdditionalAPIURLParameters(), "&page=",
			CMSSpaceConstants.SPACE_SUMMARY_PAGE, "&pageSize=",
			CMSSpaceConstants.SPACE_SUMMARY_PAGE_SIZE);
	}

	@Override
	public Map<String, Object> getAdditionalProps() {
		Map<String, Object> additionalProps = super.getAdditionalProps();

		try {
			additionalProps.put("breadcrumbProps", getBreadcrumbProps());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return additionalProps;
	}

	public Map<String, Object> getHeaderProps() throws Exception {
		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					getRootObjectEntryFolderExternalReferenceCode(), _groupId,
					themeDisplay.getCompanyId());

		return SpaceSummaryHeaderUtil.getSpaceSummaryHeaderProps(
			getAPIURL() + StringPool.AMPERSAND +
				getAdditionalAPIURLParameters(),
			getCreationMenu(), httpServletRequest, "view-all-files",
			Collections.emptyMap(), Collections.emptyMap(), "files",
			ActionUtil.getBaseViewFolderURL(themeDisplay) +
				objectEntryFolder.getObjectEntryFolderId());
	}

	@Override
	protected String getCMSSectionFilterString() {
		return String.format(
			"cmsRoot eq true and rootDescendantNode eq false and cmsSection " +
				"eq 'files' and groupIds/any(g:g eq %s)",
			_groupId);
	}

	@Override
	protected String getEmptyStateDescriptionKey() {
		return "create-and-manage-files-within-this-space";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewSpaceFilesSummarySectionDisplayContext.class);

	private final long _groupId;
	private final ObjectEntryFolderLocalService _objectEntryFolderLocalService;

}