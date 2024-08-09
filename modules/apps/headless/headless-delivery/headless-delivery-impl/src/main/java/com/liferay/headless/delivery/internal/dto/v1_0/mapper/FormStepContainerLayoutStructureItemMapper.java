/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.mapper;

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.PageFormDefinition;
import com.liferay.headless.delivery.internal.dto.v1_0.mapper.util.StyledLayoutStructureItemUtil;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.layout.util.structure.FormStepContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.StyledLayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Portal;

/**
 * @author Víctor Galán
 */
public class FormStepContainerLayoutStructureItemMapper
	extends BaseStyledLayoutStructureItemMapper {

	public FormStepContainerLayoutStructureItemMapper(
		InfoItemServiceRegistry infoItemServiceRegistry, Portal portal) {

		super(infoItemServiceRegistry, portal);
	}

	@Override
	public PageElement getPageElement(
		long groupId, LayoutStructureItem layoutStructureItem,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		StyledLayoutStructureItem formStepContainerStyledLayoutStructureItem =
			(FormStepContainerStyledLayoutStructureItem)layoutStructureItem;

		return new PageElement() {
			{
				setDefinition(
					() -> new PageFormDefinition() {
						{
							setCssClasses(
								() ->
									StyledLayoutStructureItemUtil.getCssClasses(
										formStepContainerStyledLayoutStructureItem));
							setCustomCSS(
								() ->
									StyledLayoutStructureItemUtil.getCustomCSS(
										formStepContainerStyledLayoutStructureItem));
							setCustomCSSViewports(
								() ->
									StyledLayoutStructureItemUtil.
										getCustomCSSViewports(
											formStepContainerStyledLayoutStructureItem));
							setFragmentStyle(
								() -> {
									JSONObject itemConfigJSONObject =
										formStepContainerStyledLayoutStructureItem.
											getItemConfigJSONObject();

									return toFragmentStyle(
										itemConfigJSONObject.getJSONObject(
											"styles"),
										saveMappingConfiguration);
								});
							setFragmentViewports(
								() -> getFragmentViewPorts(
									formStepContainerStyledLayoutStructureItem.
										getItemConfigJSONObject()));
						}
					});
				setId(layoutStructureItem::getItemId);
				setType(() -> Type.FORM_STEP_CONTAINER);
			}
		};
	}

}