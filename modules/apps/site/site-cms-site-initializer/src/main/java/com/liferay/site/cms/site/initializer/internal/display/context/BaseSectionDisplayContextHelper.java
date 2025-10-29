/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.depot.model.DepotEntry;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectEntryFolderLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Daniel Sanz
 */
public class BaseSectionDisplayContextHelper {

	public String getAdditionalAPIURLParameters(
		String filter, HttpServletRequest httpServletRequest,
		String rootObjectEntryFolderExternalReferenceCode) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ObjectEntryFolder objectEntryFolder = _getObjectEntryFolder(
			themeDisplay.getCompanyId(),
			httpServletRequest.getAttribute(InfoDisplayWebKeys.INFO_ITEM),
			rootObjectEntryFolderExternalReferenceCode);

		StringBundler sb = new StringBundler(9);

		sb.append("emptySearch=true&filter=");

		if (objectEntryFolder != null) {
			sb.append("folderId eq ");
			sb.append(objectEntryFolder.getObjectEntryFolderId());

			if (objectEntryFolder.getStatus() ==
					WorkflowConstants.STATUS_IN_TRASH) {

				sb.append(" and status eq ");
				sb.append(WorkflowConstants.STATUS_IN_TRASH);
			}
			else {
				sb.append(" and status in (");
				sb.append(StringUtil.merge(_statuses, ", "));
				sb.append(")");
			}
		}
		else {
			sb.append(filter);
		}

		sb.append("&nestedFields=embedded,file.metadata,");
		sb.append("file.previewURL,file.thumbnailURL,");
		sb.append("systemProperties.objectDefinitionBrief");

		return sb.toString();
	}

	private ObjectEntryFolder _getObjectEntryFolder(
		long companyId, Object object,
		String rootObjectEntryFolderExternalReferenceCode) {

		if (object instanceof DepotEntry) {
			DepotEntry depotEntry = (DepotEntry)object;

			return ObjectEntryFolderLocalServiceUtil.
				fetchObjectEntryFolderByExternalReferenceCode(
					rootObjectEntryFolderExternalReferenceCode,
					depotEntry.getGroupId(), companyId);
		}
		else if (object instanceof ObjectEntryFolder) {
			return (ObjectEntryFolder)object;
		}

		return null;
	}

	private static final List<Integer> _statuses = Arrays.asList(
		WorkflowConstants.STATUS_APPROVED, WorkflowConstants.STATUS_DRAFT,
		WorkflowConstants.STATUS_EXPIRED, WorkflowConstants.STATUS_PENDING,
		WorkflowConstants.STATUS_SCHEDULED);

}