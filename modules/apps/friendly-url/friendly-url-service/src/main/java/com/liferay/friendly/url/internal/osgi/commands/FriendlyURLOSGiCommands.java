/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.internal.osgi.commands;

import com.liferay.friendly.url.checker.FriendlyURLPublicMappingChecker;
import com.liferay.friendly.url.checker.FriendlyURLPublicMappingConflict;
import com.liferay.osgi.util.osgi.commands.OSGiCommands;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.CompanyLocalService;

import java.util.List;

import org.apache.felix.service.command.Descriptor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(
	property = {
		"osgi.command.function=checkPublicMappingConflicts",
		"osgi.command.scope=friendlyURL"
	},
	service = OSGiCommands.class
)
public class FriendlyURLOSGiCommands implements OSGiCommands {

	@Descriptor(
		"Report friendly URL conflicts that would surface if layout.friendly.url.public.servlet.mapping.enabled=false"
	)
	public String checkPublicMappingConflicts() {
		StringBundler sb = new StringBundler(1);

		_companyLocalService.forEachCompany(
			company -> sb.append(
				_checkPublicMappingConflicts(company.getCompanyId())));

		return sb.toString();
	}

	@Descriptor(
		"Report friendly URL conflicts for a single company that would surface if layout.friendly.url.public.servlet.mapping.enabled=false"
	)
	public String checkPublicMappingConflicts(long companyId) {
		return _checkPublicMappingConflicts(companyId);
	}

	private String _checkPublicMappingConflicts(long companyId) {
		List<FriendlyURLPublicMappingConflict>
			friendlyURLPublicMappingConflicts =
				_friendlyURLPublicMappingChecker.
					getFriendlyURLPublicMappingConflicts(companyId);

		if (friendlyURLPublicMappingConflicts.isEmpty()) {
			return StringBundler.concat(
				"No friendly URL conflicts were found for company ", companyId,
				StringPool.PERIOD, StringPool.NEW_LINE);
		}

		StringBundler sb = new StringBundler(
			friendlyURLPublicMappingConflicts.size() + 1);

		sb.append(
			StringBundler.concat(
				"Friendly URL conflicts were found for company ", companyId,
				StringPool.COLON, StringPool.NEW_LINE));

		for (FriendlyURLPublicMappingConflict friendlyURLPublicMappingConflict :
				friendlyURLPublicMappingConflicts) {

			sb.append(
				StringBundler.concat(
					friendlyURLPublicMappingConflict.getType(), StringPool.PIPE,
					friendlyURLPublicMappingConflict.getFriendlyURL(),
					StringPool.PIPE,
					friendlyURLPublicMappingConflict.getClassName(),
					StringPool.SPACE,
					friendlyURLPublicMappingConflict.getTitle(), " (",
					friendlyURLPublicMappingConflict.getClassPK(), ")"));

			if (friendlyURLPublicMappingConflict.getConflictingGroupId() !=
					null) {

				sb.append(
					StringBundler.concat(
						StringPool.PIPE,
						friendlyURLPublicMappingConflict.
							getConflictingGroupName(),
						" (",
						friendlyURLPublicMappingConflict.
							getConflictingGroupId(),
						")"));
			}

			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private FriendlyURLPublicMappingChecker _friendlyURLPublicMappingChecker;

}