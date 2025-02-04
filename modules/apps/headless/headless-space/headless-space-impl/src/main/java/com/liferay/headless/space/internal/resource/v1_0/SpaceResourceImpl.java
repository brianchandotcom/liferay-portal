/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.space.internal.resource.v1_0;

import com.liferay.depot.model.DepotAppCustomization;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.depot.service.DepotAppCustomizationLocalService;
import com.liferay.depot.service.DepotEntryGroupRelService;
import com.liferay.depot.service.DepotEntryService;
import com.liferay.headless.space.dto.v1_0.Space;
import com.liferay.headless.space.resource.v1_0.SpaceResource;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Roberto Díaz
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/space.properties",
	scope = ServiceScope.PROTOTYPE, service = SpaceResource.class
)
public class SpaceResourceImpl extends BaseSpaceResourceImpl {

	@Override
	public void deleteSpace(Long spaceId) throws Exception {
		DepotEntry depotEntry = _depotEntryService.getDepotEntry(spaceId);

		_depotEntryService.deleteDepotEntry(depotEntry.getDepotEntryId());
	}

	@Override
	public Space getSpace(Long spaceId) throws Exception {
		return _toSpace(_depotEntryService.getDepotEntry(spaceId));
	}

	@Override
	public Space patchSpace(Long spaceId, Space space) throws Exception {
		DepotEntry depotEntry = _depotEntryService.getDepotEntry(spaceId);

		Group group = depotEntry.getGroup();

		List<DepotAppCustomization> depotAppCustomizations =
			_depotAppCustomizationLocalService.getDepotAppCustomizations(
				depotEntry.getDepotEntryId());

		Map<String, Boolean> depotAppCustomizationMap = new HashMap<>();

		for (DepotAppCustomization depotAppCustomization :
				depotAppCustomizations) {

			depotAppCustomizationMap.put(
				depotAppCustomization.getPortletId(),
				depotAppCustomization.isEnabled());
		}

		return _toSpace(
			_depotEntryService.updateDepotEntry(
				depotEntry.getDepotEntryId(),
				LocalizedMapUtil.getLocalizedMap(
					contextAcceptLanguage.getPreferredLocale(), space.getName(),
					space.getName_i18n()),
				LocalizedMapUtil.getLocalizedMap(
					contextAcceptLanguage.getPreferredLocale(),
					space.getDescription(), space.getDescription_i18n()),
				depotAppCustomizationMap, group.getTypeSettingsProperties(),
				_getServiceContext()));
	}

	@Override
	public Space postSpace(Space space) throws Exception {
		return _toSpace(
			_depotEntryService.addDepotEntry(
				LocalizedMapUtil.getLocalizedMap(
					contextAcceptLanguage.getPreferredLocale(), space.getName(),
					space.getName_i18n()),
				LocalizedMapUtil.getLocalizedMap(
					contextAcceptLanguage.getPreferredLocale(),
					space.getDescription(), space.getDescription_i18n()),
				_getServiceContext()));
	}

	private ServiceContext _getServiceContext() throws Exception {
		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DepotEntry.class.getName(), contextHttpServletRequest);

		serviceContext.setModifiedDate(new Date());

		return serviceContext;
	}

	private Space _toSpace(DepotEntry depotEntry) throws Exception {
		return _spaceDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				HashMapBuilder.put(
					"create", addAction("add", depotEntry, "postSpace")
				).put(
					"delete",
					addAction(ActionKeys.DELETE, depotEntry, "deleteSpace")
				).put(
					"get", addAction(ActionKeys.VIEW, depotEntry, "getSpace")
				).put(
					"update",
					addAction(ActionKeys.UPDATE, depotEntry, "patchSpace")
				).build(),
				_dtoConverterRegistry, depotEntry.getDepotEntryId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference
	private DepotAppCustomizationLocalService
		_depotAppCustomizationLocalService;

	@Reference
	private DepotEntryGroupRelService _depotEntryGroupRelService;

	@Reference
	private DepotEntryService _depotEntryService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference(
		target = "(component.name=com.liferay.headless.space.internal.dto.v1_0.converter.SpaceDTOConverter)"
	)
	private DTOConverter<DepotEntry, Space> _spaceDTOConverter;

}