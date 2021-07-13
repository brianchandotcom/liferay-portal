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

import com.liferay.commerce.product.exception.NoSuchCPAttachmentFileEntryException;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramEntryService;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramPinService;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramSettingService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.headless.commerce.shop.by.diagram.dto.v1_0.Diagram;
import com.liferay.headless.commerce.shop.by.diagram.dto.v1_0.DiagramEntry;
import com.liferay.headless.commerce.shop.by.diagram.dto.v1_0.Pin;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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

		final long cpDefinitionId =
			cpDefinitionDiagramSetting.getCPDefinitionId();

		Locale locale = dtoConverterContext.getLocale();

		return new Diagram() {
			{
				color = cpDefinitionDiagramSetting.getColor();
				diagramEntries = _getDiagramEntries(cpDefinitionId, locale);
				id =
					cpDefinitionDiagramSetting.
						getCPDefinitionDiagramSettingId();
				imageUrl = _getImageURL(cpDefinitionDiagramSetting);
				pins = _getPins(cpDefinitionId, locale);
				radius = cpDefinitionDiagramSetting.getRadius();
				type = cpDefinitionDiagramSetting.getType();
			}
		};
	}

	private DiagramEntry[] _getDiagramEntries(
			long cpDefinitionId, Locale locale)
		throws Exception {

		List<DiagramEntry> diagramEntries = new ArrayList<>();

		List<CPDefinitionDiagramEntry> cpDefinitionDiagramEntries =
			_cpDefinitionDiagramEntryService.getCPDefinitionDiagramEntries(
				cpDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CPDefinitionDiagramEntry cpDefinitionDiagramEntry :
				cpDefinitionDiagramEntries) {

			diagramEntries.add(
				_diagramEntryDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						cpDefinitionDiagramEntry.
							getCPDefinitionDiagramEntryId(),
						locale)));
		}

		return diagramEntries.toArray(new DiagramEntry[0]);
	}

	private String _getImageURL(
			CPDefinitionDiagramSetting cpDefinitionDiagramSetting)
		throws Exception {

		try {
			CPAttachmentFileEntry cpAttachmentFileEntry =
				cpDefinitionDiagramSetting.getCPAttachmentFileEntry();

			FileEntry fileEntry = cpAttachmentFileEntry.getFileEntry();

			return DLUtil.getDownloadURL(
				fileEntry, fileEntry.getFileVersion(), null, null);
		}
		catch (NoSuchCPAttachmentFileEntryException
					noSuchCPAttachmentFileEntryException) {

			_log.error(
				noSuchCPAttachmentFileEntryException,
				noSuchCPAttachmentFileEntryException);
		}

		return StringPool.BLANK;
	}

	private Pin[] _getPins(long cpDefinitionId, Locale locale)
		throws Exception {

		List<Pin> pins = new ArrayList<>();

		List<CPDefinitionDiagramPin> cpDefinitionDiagramPins =
			_cpDefinitionDiagramPinService.getCPDefinitionDiagramPins(
				cpDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CPDefinitionDiagramPin cpDefinitionDiagramPin :
				cpDefinitionDiagramPins) {

			pins.add(
				_pinDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						cpDefinitionDiagramPin.getCPDefinitionDiagramPinId(),
						locale)));
		}

		return pins.toArray(new Pin[0]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DiagramDTOConverter.class);

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