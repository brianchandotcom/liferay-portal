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

package com.liferay.headless.commerce.shop.by.diagram.internal.dto.v1_0.converter;

import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramEntryService;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramPinService;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramSettingService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.headless.commerce.shop.by.diagram.dto.v1_0.Diagram;
import com.liferay.headless.commerce.shop.by.diagram.dto.v1_0.DiagramEntry;
import com.liferay.headless.commerce.shop.by.diagram.dto.v1_0.Pin;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.util.TransformUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting",
	service = {DiagramDTOConverter.class, DTOConverter.class}
)
public class DiagramDTOConverter
	implements DTOConverter<CPDefinitionDiagramSetting, Diagram> {

	@Override
	public String getContentType() {
		return Diagram.class.getSimpleName();
	}

	@Override
	public Diagram toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			_cpDefinitionDiagramSettingService.getCPDefinitionDiagramSetting(
				(Long)dtoConverterContext.getId());

		return new Diagram() {
			{
				color = cpDefinitionDiagramSetting.getColor();
				diagramEntries = TransformUtil.transformToArray(
					_cpDefinitionDiagramEntryService.
						getCPDefinitionDiagramEntries(
							cpDefinitionDiagramSetting.getCPDefinitionId(),
							QueryUtil.ALL_POS, QueryUtil.ALL_POS),
					cpDefinitionDiagramEntry -> _diagramEntryDTOConverter.toDTO(
						new DefaultDTOConverterContext(
							cpDefinitionDiagramEntry.
								getCPDefinitionDiagramEntryId(),
							dtoConverterContext.getLocale())),
					DiagramEntry.class);
				id =
					cpDefinitionDiagramSetting.
						getCPDefinitionDiagramSettingId();
				pins = TransformUtil.transformToArray(
					_cpDefinitionDiagramPinService.getCPDefinitionDiagramPins(
						cpDefinitionDiagramSetting.getCPDefinitionId(),
						QueryUtil.ALL_POS, QueryUtil.ALL_POS),
					cpDefinitionDiagramPin -> _pinDTOConverter.toDTO(
						new DefaultDTOConverterContext(
							cpDefinitionDiagramPin.
								getCPDefinitionDiagramPinId(),
							dtoConverterContext.getLocale())),
					Pin.class);
				radius = cpDefinitionDiagramSetting.getRadius();
				type = cpDefinitionDiagramSetting.getType();

				setImageURL(
					() -> {
						CPAttachmentFileEntry cpAttachmentFileEntry =
							cpDefinitionDiagramSetting.
								getCPAttachmentFileEntry();

						FileEntry fileEntry =
							cpAttachmentFileEntry.fetchFileEntry();

						if (fileEntry == null) {
							return null;
						}

						return DLUtil.getDownloadURL(
							fileEntry, fileEntry.getFileVersion(), null, null);
					});
			}
		};
	}

	@Reference
	private CPDefinitionDiagramEntryService _cpDefinitionDiagramEntryService;

	@Reference
	private CPDefinitionDiagramPinService _cpDefinitionDiagramPinService;

	@Reference
	private CPDefinitionDiagramSettingService
		_cpDefinitionDiagramSettingService;

	@Reference
	private DiagramEntryDTOConverter _diagramEntryDTOConverter;

	@Reference
	private PinDTOConverter _pinDTOConverter;

}