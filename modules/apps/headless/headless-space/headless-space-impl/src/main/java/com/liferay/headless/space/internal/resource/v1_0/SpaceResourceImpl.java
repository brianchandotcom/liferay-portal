/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.space.internal.resource.v1_0;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryService;
import com.liferay.headless.space.dto.v1_0.Space;
import com.liferay.headless.space.resource.v1_0.SpaceResource;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

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
	public Space postSpace(Space space) throws Exception {
		return _toSpace(
			_depotEntryService.addDepotEntry(
				LocalizedMapUtil.getLocalizedMap(
					contextAcceptLanguage.getPreferredLocale(), space.getName(),
					space.getName_i18n()),
				LocalizedMapUtil.getLocalizedMap(
					contextAcceptLanguage.getPreferredLocale(),
					space.getDescription(), space.getDescription_i18n()),
				ServiceContextFactory.getInstance(
					DepotEntry.class.getName(), contextHttpServletRequest)));
	}

	private Space _toSpace(DepotEntry depotEntry) throws Exception {
		return _spaceDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				HashMapBuilder.put(
					"create", addAction("add", depotEntry, "postSpace")
				).build(),
				_dtoConverterRegistry, depotEntry.getDepotEntryId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference
	private DepotEntryService _depotEntryService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference(
		target = "(component.name=com.liferay.headless.space.internal.dto.v1_0.converter.SpaceDTOConverter)"
	)
	private DTOConverter<DepotEntry, Space> _spaceDTOConverter;

}