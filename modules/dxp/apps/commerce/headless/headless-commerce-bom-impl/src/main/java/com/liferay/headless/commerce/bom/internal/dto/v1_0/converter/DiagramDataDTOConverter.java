/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.headless.commerce.bom.internal.dto.v1_0.converter;

import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramEntryService;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramSettingsService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.headless.commerce.bom.dto.v1_0.DiagramData;
import com.liferay.headless.commerce.bom.dto.v1_0.Product;
import com.liferay.headless.commerce.bom.dto.v1_0.Spot;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "model.class.name=cpDefinitionDiagramSettingsData",
	service = {DiagramDataDTOConverter.class, DTOConverter.class}
)
public class DiagramDataDTOConverter
	implements DTOConverter<CPDefinitionDiagramSettings, DiagramData> {

	@Override
	public String getContentType() {
		return DiagramData.class.getSimpleName();
	}

	@Override
	public DiagramData toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			_cpDefinitionDiagramSettingsService.getCPDefinitionDiagramSettings(
				(Long)dtoConverterContext.getId());

		Locale locale = dtoConverterContext.getLocale();

		Spot[] spotsArray = _getSpots(
			cpDefinitionDiagramSettings.getCPDefinitionDiagramSettingsId(),
			locale);

		return new DiagramData() {
			{
				folderId = cpDefinitionDiagramSettings.getAssetCategoryId();
				id =
					cpDefinitionDiagramSettings.
						getCPDefinitionDiagramSettingsId();
				imageUrl = _getImageURL(cpDefinitionDiagramSettings);
				name = cpDefinitionDiagramSettings.getName();
				products = _getProducts(spotsArray, locale);
				spots = spotsArray;
			}
		};
	}

	private String _getImageURL(
			CPDefinitionDiagramSettings cpDefinitionDiagramSettings)
		throws Exception {

		CPAttachmentFileEntry cpAttachmentFileEntry =
			cpDefinitionDiagramSettings.fetchCPAttachmentFileEntry();

		if (cpAttachmentFileEntry != null) {
			FileEntry fileEntry = cpAttachmentFileEntry.getFileEntry();

			return DLUtil.getDownloadURL(
				fileEntry, fileEntry.getFileVersion(), null, null);
		}

		return StringPool.BLANK;
	}

	private Product[] _getProducts(Spot[] spots, Locale locale)
		throws Exception {

		List<Product> productList = new ArrayList<>();

		for (Spot spot : spots) {
			CProduct cProduct =
				_cProductLocalService.getCProductByCPInstanceUuid(
					spot.getProductId());

			CPInstance cpInstance = _cpInstanceLocalService.getCProductInstance(
				cProduct.getCProductId(), spot.getProductId());

			productList.add(
				_productDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						cpInstance.getCPInstanceId(), locale)));
		}

		return productList.toArray(new Product[0]);
	}

	private Spot[] _getSpots(long diagramId, Locale locale) throws Exception {
		List<Spot> spotList = new ArrayList<>();

		List<CPDefinitionDiagramEntry> cpDefinitionDiagramEntries =
			_cpDefinitionDiagramEntryService.getCPDefinitionDiagramEntries(
				diagramId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CPDefinitionDiagramEntry cpDefinitionDiagramEntry :
				cpDefinitionDiagramEntries) {

			spotList.add(
				_spotDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						cpDefinitionDiagramEntry.
							getCPDefinitionDiagramEntryId(),
						locale)));
		}

		return spotList.toArray(new Spot[0]);
	}

	@Reference
	private BreadcrumbDTOConverter _breadcrumbDTOConverter;

	@Reference
	private CPDefinitionDiagramEntryService _cpDefinitionDiagramEntryService;

	@Reference
	private CPDefinitionDiagramSettingsService
		_cpDefinitionDiagramSettingsService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private CProductLocalService _cProductLocalService;

	@Reference
	private ProductDTOConverter _productDTOConverter;

	@Reference
	private SpotDTOConverter _spotDTOConverter;

}