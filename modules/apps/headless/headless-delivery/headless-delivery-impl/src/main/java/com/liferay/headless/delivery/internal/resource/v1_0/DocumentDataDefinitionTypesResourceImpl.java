/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeService;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.headless.delivery.dto.v1_0.DocumentDataDefinitionType;
import com.liferay.headless.delivery.resource.v1_0.DocumentDataDefinitionTypesResource;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/document-data-definition-types.properties",
	scope = ServiceScope.PROTOTYPE,
	service = DocumentDataDefinitionTypesResource.class
)
public class DocumentDataDefinitionTypesResourceImpl
	extends BaseDocumentDataDefinitionTypesResourceImpl {

	@Override
	public DocumentDataDefinitionType
			postAssetLibraryDocumentDataDefinitionTypes(
				Long assetLibraryId,
				DocumentDataDefinitionType documentDataDefinitionType)
		throws Exception {

		return postSiteDocumentDataDefinitionTypes(
			assetLibraryId, documentDataDefinitionType);
	}

	@Override
	public DocumentDataDefinitionType postSiteDocumentDataDefinitionTypes(
			Long siteId, DocumentDataDefinitionType documentDataDefinitionType)
		throws Exception {

		DataDefinitionResource.Builder dataDefinitionResourceBuilder =
			_dataDefinitionResourceFactory.create();

		DataDefinitionResource dataDefinitionResource =
			dataDefinitionResourceBuilder.user(
				contextUser
			).build();

		Map<Locale, String> descriptionMap = LocalizedMapUtil.getLocalizedMap(
			contextAcceptLanguage.getPreferredLocale(),
			documentDataDefinitionType.getDescription(),
			documentDataDefinitionType.getDescription_i18n());

		Map<Locale, String> nameMap = LocalizedMapUtil.getLocalizedMap(
			contextAcceptLanguage.getPreferredLocale(),
			documentDataDefinitionType.getName(),
			documentDataDefinitionType.getName_i18n());

		DataDefinition dataDefinition =
			dataDefinitionResource.postSiteDataDefinitionByContentType(
				siteId, "document-library",
				new DataDefinition() {
					{
						setAvailableLanguageIds(
							documentDataDefinitionType::getAvailableLanguages);
						setDataDefinitionFields(
							documentDataDefinitionType::
								getDataDefinitionFields);
						setDefaultDataLayout(
							documentDataDefinitionType::getDataLayout);
						setDescription(
							() -> LocalizedValueUtil.toStringObjectMap(
								descriptionMap));
						setName(
							() -> LocalizedValueUtil.toStringObjectMap(
								nameMap));
						setSiteId(() -> siteId);
						setUserId(contextUser::getUserId);
					}
				});

		DLFileEntryType dlFileEntryType =
			_dlFileEntryTypeService.addFileEntryType(
				documentDataDefinitionType.getExternalReferenceCode(), siteId,
				dataDefinition.getId(), null, nameMap, descriptionMap,
				ServiceContextBuilder.create(
					siteId, contextHttpServletRequest,
					documentDataDefinitionType.getViewableByAsString()
				).build());

		if (ArrayUtil.isNotEmpty(
				documentDataDefinitionType.getDocumentMetadataSetIds())) {

			_dlFileEntryTypeLocalService.addDDMStructureLinks(
				dlFileEntryType.getFileEntryTypeId(),
				SetUtil.fromArray(
					documentDataDefinitionType.getDocumentMetadataSetIds()));
		}

		return _toDocumentDataDefinitionType(dlFileEntryType);
	}

	private DocumentDataDefinitionType _toDocumentDataDefinitionType(
			DLFileEntryType dlFileEntryType)
		throws Exception {

		return _documentDataDefinitionTypeDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), new HashMap<>(),
				_dtoConverterRegistry, dlFileEntryType.getFileEntryTypeId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			dlFileEntryType);
	}

	@Reference
	private DataDefinitionResource.Factory _dataDefinitionResourceFactory;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private DLFileEntryTypeService _dlFileEntryTypeService;

	@Reference(
		target = "(component.name=com.liferay.headless.delivery.internal.dto.v1_0.converter.DocumentDataDefinitionTypeDTOConverter)"
	)
	private DTOConverter<DLFileEntryType, DocumentDataDefinitionType>
		_documentDataDefinitionTypeDTOConverter;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

}