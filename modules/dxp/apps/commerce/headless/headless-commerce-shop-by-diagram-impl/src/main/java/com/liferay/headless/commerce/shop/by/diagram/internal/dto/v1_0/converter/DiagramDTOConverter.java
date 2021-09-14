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
import com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryService;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramPinService;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramSettingService;
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
	property = "model.class.name=com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting",
	service = {DiagramDTOConverter.class, DTOConverter.class}
)
public class DiagramDTOConverter
	implements DTOConverter<CSDiagramSetting, Diagram> {

	@Override
	public String getContentType() {
		return Diagram.class.getSimpleName();
	}

	@Override
	public Diagram toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CSDiagramSetting csDiagramSetting =
			_csDiagramSettingService.getCSDiagramSetting(
				(Long)dtoConverterContext.getId());

		return new Diagram() {
			{
				color = csDiagramSetting.getColor();
				diagramEntries = TransformUtil.transformToArray(
					_csDiagramEntryService.getCSDiagramEntries(
						csDiagramSetting.getCPDefinitionId(), QueryUtil.ALL_POS,
						QueryUtil.ALL_POS),
					csDiagramEntry -> _diagramEntryDTOConverter.toDTO(
						new DefaultDTOConverterContext(
							csDiagramEntry.getCSDiagramEntryId(),
							dtoConverterContext.getLocale())),
					DiagramEntry.class);
				id = csDiagramSetting.getCSDiagramSettingId();
				pins = TransformUtil.transformToArray(
					_csDiagramPinService.getCSDiagramPins(
						csDiagramSetting.getCPDefinitionId(), QueryUtil.ALL_POS,
						QueryUtil.ALL_POS),
					csDiagramPin -> _pinDTOConverter.toDTO(
						new DefaultDTOConverterContext(
							csDiagramPin.getCSDiagramPinId(),
							dtoConverterContext.getLocale())),
					Pin.class);
				radius = csDiagramSetting.getRadius();
				type = csDiagramSetting.getType();

				setImageURL(
					() -> {
						CPAttachmentFileEntry cpAttachmentFileEntry =
							csDiagramSetting.getCPAttachmentFileEntry();

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
	private CSDiagramEntryService _csDiagramEntryService;

	@Reference
	private CSDiagramPinService _csDiagramPinService;

	@Reference
	private CSDiagramSettingService _csDiagramSettingService;

	@Reference
	private DiagramEntryDTOConverter _diagramEntryDTOConverter;

	@Reference
	private PinDTOConverter _pinDTOConverter;

}