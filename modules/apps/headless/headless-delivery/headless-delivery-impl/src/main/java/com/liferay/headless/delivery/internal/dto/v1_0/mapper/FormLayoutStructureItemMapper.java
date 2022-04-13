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

package com.liferay.headless.delivery.internal.dto.v1_0.mapper;

import com.liferay.headless.delivery.dto.v1_0.FormSubtype;
import com.liferay.headless.delivery.dto.v1_0.FormType;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.PageFormDefinition;
import com.liferay.layout.util.structure.FormLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = LayoutStructureItemMapper.class)
public class FormLayoutStructureItemMapper
	implements LayoutStructureItemMapper {

	@Override
	public String getClassName() {
		return FormLayoutStructureItem.class.getName();
	}

	@Override
	public PageElement getPageElement(
		long groupId, LayoutStructureItem layoutStructureItem,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		FormLayoutStructureItem formLayoutStructureItem =
			(FormLayoutStructureItem)layoutStructureItem;

		return new PageElement() {
			{
				definition = new PageFormDefinition() {
					{
						formType = new FormType() {
							{
								className = _portal.getClassName(
									formLayoutStructureItem.getClassNameId());
							}
						};

						setFormSubtype(
							() -> {
								if (formLayoutStructureItem.getClassTypeId() ==
										0) {

									return null;
								}

								return new FormSubtype() {
									{
										subtypeId =
											formLayoutStructureItem.
												getClassTypeId();
									}
								};
							});
					}
				};
				type = Type.FORM;
			}
		};
	}

	@Reference
	private Portal _portal;

}