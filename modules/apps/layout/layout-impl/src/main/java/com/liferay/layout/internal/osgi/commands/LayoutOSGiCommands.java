/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.osgi.commands;

import com.liferay.layout.friendly.url.verifier.LayoutFriendlyURLPublicMappingConflict;
import com.liferay.layout.friendly.url.verifier.LayoutFriendlyURLPublicMappingVerifier;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.serializer.LayoutStructureItemJSONSerializer;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.osgi.util.osgi.commands.OSGiCommands;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.List;

import org.apache.felix.service.command.Descriptor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(
	property = {
		"osgi.command.function=exportAsJSON",
		"osgi.command.function=verifyPublicMapping", "osgi.command.scope=layout"
	},
	service = OSGiCommands.class
)
public class LayoutOSGiCommands implements OSGiCommands {

	@Descriptor("Get page definition JSON for a given layout by its PLID")
	public String exportAsJSON(long plid) throws PortalException {
		Layout layout = _layoutLocalService.fetchLayout(plid);

		if (layout == null) {
			return "Layout with PLID " + plid + " does not exist";
		}

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(layout.getGroupId(), plid);

		if (layoutPageTemplateStructure == null) {
			return "Layout with PLID " + plid +
				" does not have a layout page template structure";
		}

		long defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				plid);

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(defaultSegmentsExperienceId));

		return _layoutStructureItemJSONSerializer.toJSONString(
			layout, layoutStructure.getMainItemId(), false, false,
			defaultSegmentsExperienceId);
	}

	@Descriptor(
		"Report default-site layouts whose friendly URL would collide if layout.friendly.url.public.servlet.mapping.enabled=false"
	)
	public String verifyPublicMapping() {
		StringBundler sb = new StringBundler(1);

		_companyLocalService.forEachCompany(
			company -> sb.append(_verifyPublicMapping(company.getCompanyId())));

		return sb.toString();
	}

	@Descriptor(
		"Report default-site layouts whose friendly URL would collide if layout.friendly.url.public.servlet.mapping.enabled=false"
	)
	public String verifyPublicMapping(long companyId) {
		return _verifyPublicMapping(companyId);
	}

	private String _verifyPublicMapping(long companyId) {
		List<LayoutFriendlyURLPublicMappingConflict> conflicts =
			_layoutFriendlyURLPublicMappingVerifier.getConflicts(companyId);

		if (conflicts.isEmpty()) {
			return StringBundler.concat(
				"No friendly URL conflicts were found for company ", companyId,
				StringPool.PERIOD, StringPool.NEW_LINE);
		}

		StringBundler sb = new StringBundler(conflicts.size() + 1);

		sb.append(
			StringBundler.concat(
				"Friendly URL conflicts were found for company ", companyId,
				StringPool.COLON, StringPool.NEW_LINE));

		for (LayoutFriendlyURLPublicMappingConflict conflict : conflicts) {
			sb.append(
				StringBundler.concat(
					conflict.getType(), StringPool.PIPE, conflict.getPageURL(),
					StringPool.PIPE, conflict.getLayoutName(), " (plid ",
					conflict.getLayoutPlid(), ")"));

			if (conflict.getConflictingGroupId() != null) {
				sb.append(
					StringBundler.concat(
						StringPool.PIPE, conflict.getConflictingGroupName(),
						" (", conflict.getConflictingGroupId(), ")"));
			}

			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private LayoutFriendlyURLPublicMappingVerifier
		_layoutFriendlyURLPublicMappingVerifier;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutStructureItemJSONSerializer
		_layoutStructureItemJSONSerializer;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}