/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.admin.content.internal.dto.v1_0.converter;

import com.liferay.headless.admin.content.dto.v1_0.DisplayPageTemplate;
import com.liferay.headless.admin.content.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.admin.content.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.admin.content.internal.dto.v1_0.util.DisplayPageTemplateSettingsUtil;
import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = "dto.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateEntry",
	service = {DisplayPageTemplateDTOConverter.class, DTOConverter.class}
)
public class DisplayPageTemplateDTOConverter
	implements DTOConverter<LayoutPageTemplateEntry, DisplayPageTemplate> {

	@Override
	public String getContentType() {
		return DisplayPageTemplate.class.getSimpleName();
	}

	@Override
	public DisplayPageTemplate toDTO(
			DTOConverterContext dtoConverterContext,
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws Exception {

		Layout layout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());

		return new DisplayPageTemplate() {
			{
				actions = dtoConverterContext.getActions();
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					layout.getAvailableLanguageIds());
				creator = CreatorUtil.toCreator(
					_portal, dtoConverterContext.getUriInfoOptional(),
					_userLocalService.fetchUser(
						layoutPageTemplateEntry.getUserId()));
				customFields = CustomFieldsUtil.toCustomFields(
					dtoConverterContext.isAcceptAllLanguages(),
					Layout.class.getName(), layout.getPlid(),
					layout.getCompanyId(), dtoConverterContext.getLocale());
				dateCreated = layout.getCreateDate();
				dateModified = layout.getModifiedDate();
				displayPageTemplateKey =
					layoutPageTemplateEntry.getLayoutPageTemplateEntryKey();
				displayPageTemplateSettings =
					DisplayPageTemplateSettingsUtil.
						getDisplayPageTemplateSettings(
							dtoConverterContext, _infoItemServiceTracker,
							layout, layoutPageTemplateEntry, _portal);
				markedAsDefault = layoutPageTemplateEntry.isDefaultTemplate();
				siteId = layout.getGroupId();
				title = layoutPageTemplateEntry.getName();
				uuid = layoutPageTemplateEntry.getUuid();

				setPageDefinition(
					() -> {
						dtoConverterContext.setAttribute("layout", layout);

						LayoutPageTemplateStructure
							layoutPageTemplateStructure =
								_layoutPageTemplateStructureLocalService.
									fetchLayoutPageTemplateStructure(
										layout.getGroupId(), layout.getPlid());

						if (layoutPageTemplateStructure == null) {
							return null;
						}

						DTOConverterRegistry dtoConverterRegistry =
							dtoConverterContext.getDTOConverterRegistry();

						DTOConverter<LayoutStructure, PageDefinition>
							dtoConverter =
								(DTOConverter<LayoutStructure, PageDefinition>)
									dtoConverterRegistry.getDTOConverter(
										LayoutStructure.class.getName());

						if (dtoConverter == null) {
							return null;
						}

						LayoutStructure layoutStructure = LayoutStructure.of(
							layoutPageTemplateStructure.getData(0L));

						return dtoConverter.toDTO(
							dtoConverterContext, layoutStructure);
					});
			}
		};
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}