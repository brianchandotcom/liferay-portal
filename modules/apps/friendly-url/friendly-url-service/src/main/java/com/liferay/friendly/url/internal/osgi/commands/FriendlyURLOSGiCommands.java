/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.internal.osgi.commands;

import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.friendly.url.util.FriendlyURLEntryReportUtil;
import com.liferay.osgi.util.osgi.commands.OSGiCommands;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.service.CompanyLocalService;

import org.apache.felix.service.command.Descriptor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(
	property = {
		"osgi.command.function=check", "osgi.command.scope=friendlyurl"
	},
	service = OSGiCommands.class
)
public class FriendlyURLOSGiCommands implements OSGiCommands {

	@Descriptor("Run the public friendly URL mapping conflict check")
	public String check() throws PortalException {
		StringBundler sb = new StringBundler(2);

		_companyLocalService.forEachCompany(
			company -> {
				JSONArray conflictsJSONArray =
					_friendlyURLEntryLocalService.
						getFriendlyURLPublicMappingConflicts(
							company.getCompanyId());

				if (sb.length() > 0) {
					sb.append("\n");
				}

				sb.append(
					FriendlyURLEntryReportUtil.
						renderFriendlyURLPublicMappingConflictReport(
							conflictsJSONArray, company.getCompanyId()));
			});

		return sb.toString();
	}

	@Descriptor(
		"Run the public friendly URL mapping conflict check for a company"
	)
	public String check(@Descriptor("companyId") long companyId)
		throws PortalException {

		JSONArray conflictsJSONArray =
			_friendlyURLEntryLocalService.getFriendlyURLPublicMappingConflicts(
				companyId);

		return FriendlyURLEntryReportUtil.
			renderFriendlyURLPublicMappingConflictReport(
				conflictsJSONArray, companyId);
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

}