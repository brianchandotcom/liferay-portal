/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.space.internal.dto.v1_0.converter;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.headless.space.dto.v1_0.Space;
import com.liferay.headless.space.internal.resource.v1_0.BaseSpaceResourceImpl;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.JaxRsLinkUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	property = "dto.class.name=com.liferay.depot.model.DepotEntry",
	service = DTOConverter.class
)
public class SpaceDTOConverter implements DTOConverter<DepotEntry, Space> {

	@Override
	public String getContentType() {
		return Space.class.getSimpleName();
	}

	@Override
	public String getJaxRsLink(long classPK, UriInfo uriInfo) {
		return JaxRsLinkUtil.getJaxRsLink(
			"headless-delivery", BaseSpaceResourceImpl.class, "getSpace",
			uriInfo, classPK);
	}

	@Override
	public Space toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		DepotEntry depotEntry = _depotEntryLocalService.getDepotEntry(
			(Long)dtoConverterContext.getId());

		Group group = depotEntry.getGroup();

		return new Space() {
			{
				setDateCreated(depotEntry::getCreateDate);
				setDateModified(depotEntry::getModifiedDate);
				setDescription(
					() -> group.getDescription(
						dtoConverterContext.getLocale()));
				setDescription_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						dtoConverterContext.isAcceptAllLanguages(),
						group.getDescriptionMap()));
				setExternalReferenceCode(group::getExternalReferenceCode);
				setGroupId(group::getGroupId);
				setId(depotEntry::getDepotEntryId);
				setLinkedSiteIds(
					() -> {
						List<DepotEntryGroupRel> depotEntryGroupRels =
							_depotEntryGroupRelLocalService.
								getDepotEntryGroupRels(depotEntry);

						Long[] groupIds = new Long[depotEntryGroupRels.size()];

						for (DepotEntryGroupRel depotEntryGroupRel :
								depotEntryGroupRels) {

							groupIds = ArrayUtil.append(
								groupIds, depotEntryGroupRel.getGroupId());
						}

						return groupIds;
					});
				setLinkedSitesExternalReferenceCodes(
					() -> {
						List<DepotEntryGroupRel> depotEntryGroupRels =
							_depotEntryGroupRelLocalService.
								getDepotEntryGroupRels(depotEntry);

						List<String> toGroupExternalReferenceCodes =
							new ArrayList<>(depotEntryGroupRels.size());

						for (DepotEntryGroupRel depotEntryGroupRel :
								depotEntryGroupRels) {

							Group toGroup = _groupLocalService.getGroup(
								depotEntryGroupRel.getToGroupId());

							toGroupExternalReferenceCodes.add(
								toGroup.getExternalReferenceCode());
						}

						return toGroupExternalReferenceCodes.toArray(
							new String[0]);
					});
				setName(() -> group.getName(dtoConverterContext.getLocale()));
				setName_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						dtoConverterContext.isAcceptAllLanguages(),
						group.getNameMap()));
			}
		};
	}

	@Reference
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}