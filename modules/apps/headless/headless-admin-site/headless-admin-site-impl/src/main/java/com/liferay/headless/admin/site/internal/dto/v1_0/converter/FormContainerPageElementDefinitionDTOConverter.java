/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.headless.admin.site.dto.v1_0.FormContainerConfig;
import com.liferay.headless.admin.site.dto.v1_0.FormContainerPageElementDefinition;
import com.liferay.headless.delivery.dto.v1_0.ClassTypeReference;
import com.liferay.headless.delivery.dto.v1_0.ContextReference;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "dto.class.name=com.liferay.layout.util.structure.FormStyledLayoutStructureItem",
	service = DTOConverter.class
)
public class FormContainerPageElementDefinitionDTOConverter
	implements DTOConverter
		<FormStyledLayoutStructureItem, FormContainerPageElementDefinition> {

	@Override
	public String getContentType() {
		return FormContainerPageElementDefinition.class.getSimpleName();
	}

	@Override
	public FormContainerPageElementDefinition toDTO(
			DTOConverterContext dtoConverterContext,
			FormStyledLayoutStructureItem formStyledLayoutStructureItem)
		throws Exception {

		return new FormContainerPageElementDefinition() {
			{
				setCssClasses(
					() -> {
						if (SetUtil.isEmpty(
								formStyledLayoutStructureItem.
									getCssClasses())) {

							return null;
						}

						return ArrayUtil.toStringArray(
							formStyledLayoutStructureItem.getCssClasses());
					});
				setCustomCSS(formStyledLayoutStructureItem::getCustomCSS);
				setFormContainerConfig(
					() -> _toFormContainerConfig(
						formStyledLayoutStructureItem));
				setIndexed(formStyledLayoutStructureItem::isIndexed);
				setName(formStyledLayoutStructureItem::getName);
				setType(Type.FORM_CONTAINER);
			}
		};
	}

	private FormContainerConfig _toFormContainerConfig(
		FormStyledLayoutStructureItem formStyledLayoutStructureItem) {

		if ((formStyledLayoutStructureItem.getFormConfig() !=
				FormStyledLayoutStructureItem.
					FORM_CONFIG_DISPLAY_PAGE_ITEM_TYPE) &&
			(formStyledLayoutStructureItem.getFormConfig() !=
				FormStyledLayoutStructureItem.FORM_CONFIG_OTHER_ITEM_TYPE)) {

			return null;
		}

		return new FormContainerConfig() {
			{
				setFormContainerReference(
					() -> {
						if (formStyledLayoutStructureItem.getFormConfig() ==
								FormStyledLayoutStructureItem.
									FORM_CONFIG_OTHER_ITEM_TYPE) {

							return new ClassTypeReference() {
								{
									setClassName(
										formStyledLayoutStructureItem::
											getClassName);
									setClassType(
										formStyledLayoutStructureItem::
											getClassTypeId);
								}
							};
						}

						return new ContextReference() {
							{
								setContextSource(
									() -> ContextSource.DISPLAY_PAGE_ITEM);
							}
						};
					});
				setFormContainerType(
					() -> {
						if (Objects.equals(
								formStyledLayoutStructureItem.getFormType(),
								"simple")) {

							return FormContainerType.SIMPLE;
						}

						return FormContainerType.MULTISTEP;
					});
				setNumberOfSteps(
					formStyledLayoutStructureItem::getNumberOfSteps);
			}
		};
	}

}