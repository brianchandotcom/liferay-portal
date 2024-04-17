/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.system.info.item.provider;

import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.object.entry.util.ObjectEntryDTOConverterUtil;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.object.system.SystemObjectEntry;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import java.util.Collections;
import java.util.Map;

/**
 * @author Carolina Barbosa
 */
public class SystemObjectEntryInfoItemObjectProvider
	implements InfoItemObjectProvider<SystemObjectEntry> {

	public SystemObjectEntryInfoItemObjectProvider(
		DTOConverterRegistry dtoConverterRegistry,
		SystemObjectDefinitionManager systemObjectDefinitionManager) {

		_dtoConverterRegistry = dtoConverterRegistry;
		_systemObjectDefinitionManager = systemObjectDefinitionManager;
	}

	@Override
	public SystemObjectEntry getInfoItem(InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)infoItemIdentifier;

		try {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

			if (themeDisplay == null) {
				return new SystemObjectEntry(
					classPKInfoItemIdentifier.getClassPK(),
					Collections.emptyMap());
			}

			DTOConverter<?, ?> dtoConverter =
				ObjectEntryDTOConverterUtil.getDTOConverter(
					_dtoConverterRegistry, _systemObjectDefinitionManager);

			Object dto = dtoConverter.toDTO(
				new DefaultDTOConverterContext(
					false, Collections.emptyMap(), _dtoConverterRegistry,
					classPKInfoItemIdentifier.getClassPK(),
					themeDisplay.getLocale(), null, themeDisplay.getUser()));

			if (dto == null) {
				return new SystemObjectEntry(
					classPKInfoItemIdentifier.getClassPK(),
					Collections.emptyMap());
			}

			return new SystemObjectEntry(
				classPKInfoItemIdentifier.getClassPK(),
				ObjectMapperUtil.readValue(Map.class, dto.toString()));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		throw new NoSuchInfoItemException(
			"Unable to get info item for " +
				classPKInfoItemIdentifier.getClassPK());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SystemObjectEntryInfoItemObjectProvider.class);

	private final DTOConverterRegistry _dtoConverterRegistry;
	private final SystemObjectDefinitionManager _systemObjectDefinitionManager;

}