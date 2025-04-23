/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.headless.admin.site.dto.v1_0.PageDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageFormStepContainerDefinition;
import com.liferay.layout.util.structure.FormStepContainerStyledLayoutStructureItem;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "dto.class.name=com.liferay.layout.util.structure.FormStepContainerStyledLayoutStructureItem",
	service = DTOConverter.class
)
public class PageFormStepContainerDefinitionDTOConverter
	implements DTOConverter
		<FormStepContainerStyledLayoutStructureItem,
		 PageFormStepContainerDefinition> {

	@Override
	public String getContentType() {
		return PageFormStepContainerDefinition.class.getSimpleName();
	}

	@Override
	public PageFormStepContainerDefinition toDTO(
			DTOConverterContext dtoConverterContext,
			FormStepContainerStyledLayoutStructureItem
				formStepContainerStyledLayoutStructureItem)
		throws Exception {

		return new PageFormStepContainerDefinition() {
			{
				setCssClasses(
					() -> {
						if (SetUtil.isEmpty(
								formStepContainerStyledLayoutStructureItem.
									getCssClasses())) {

							return null;
						}

						return ArrayUtil.toStringArray(
							formStepContainerStyledLayoutStructureItem.
								getCssClasses());
					});
				setCustomCSS(
					formStepContainerStyledLayoutStructureItem::getCustomCSS);
				setName(formStepContainerStyledLayoutStructureItem::getName);
				setType(PageDefinition.Type.FORM_STEP_CONTAINER);
			}
		};
	}

}