/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.internal.resource.v1_0;

import com.liferay.exportimport.rest.dto.v1_0.PreviewSite;
import com.liferay.exportimport.rest.internal.odata.entity.v1_0.PreviewSiteEntityModel;
import com.liferay.exportimport.rest.internal.util.PermissionUtil;
import com.liferay.exportimport.rest.resource.v1_0.PreviewSiteResource;
import com.liferay.exportimport.site.ExportImportSiteProvider;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.comparator.GroupDescriptiveNameComparator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.staging.StagingGroupHelper;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MultivaluedMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Petteri Karttunen
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/preview-site.properties",
	scope = ServiceScope.PROTOTYPE, service = PreviewSiteResource.class
)
public class PreviewSiteResourceImpl extends BasePreviewSiteResourceImpl {

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public Page<PreviewSite> getExportPreviewSitesPage(
			String search, Pagination pagination, Sort[] sorts)
		throws Exception {

		FeatureFlagManagerUtil.checkEnabled(
			contextCompany.getCompanyId(), "LPD-85946");

		Group companyGroup = _stagingGroupHelper.fetchCompanyGroup(
			contextCompany.getCompanyId());

		if (companyGroup == null) {
			throw new NotFoundException();
		}

		PermissionUtil.checkExportPermission(
			contextCompany.getCompanyId(), companyGroup.getGroupId());

		Function<Group, String> descriptiveNameFunction =
			_getDescriptiveNameFunction();

		List<Group> groups = _exportImportSiteProvider.getSupportedGroups(
			contextCompany.getCompanyId(), search,
			_getOrderByComparator(sorts));

		return Page.of(
			transform(
				ListUtil.subList(
					groups, pagination.getStartPosition(),
					pagination.getEndPosition()),
				group -> new PreviewSite() {
					{
						setChildSiteCount(
							() -> _exportImportSiteProvider.getChildGroupCount(
								group));
						setDescriptiveName(
							() -> descriptiveNameFunction.apply(group));
						setExternalReferenceCode(
							group::getExternalReferenceCode);
						setPath(
							() -> _exportImportSiteProvider.getPath(
								group,
								contextAcceptLanguage.getPreferredLocale()));
					}
				}),
			pagination, groups.size());
	}

	private Function<Group, String> _getDescriptiveNameFunction() {
		Map<Long, String> descriptiveNames = new HashMap<>();

		return group -> descriptiveNames.computeIfAbsent(
			group.getGroupId(),
			groupId -> _exportImportSiteProvider.getDescriptiveName(
				group, contextAcceptLanguage.getPreferredLocale()));
	}

	private OrderByComparator<Group> _getOrderByComparator(Sort[] sorts) {
		if (ArrayUtil.isEmpty(sorts)) {
			return new GroupDescriptiveNameComparator(
				true, contextAcceptLanguage.getPreferredLocale());
		}

		Sort sort = sorts[0];

		if (!Objects.equals(sort.getFieldName(), "descriptiveName")) {
			throw new UnsupportedOperationException();
		}

		return new GroupDescriptiveNameComparator(
			!sort.isReverse(), contextAcceptLanguage.getPreferredLocale());
	}

	private static final EntityModel _entityModel =
		new PreviewSiteEntityModel();

	@Reference
	private ExportImportSiteProvider _exportImportSiteProvider;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

}