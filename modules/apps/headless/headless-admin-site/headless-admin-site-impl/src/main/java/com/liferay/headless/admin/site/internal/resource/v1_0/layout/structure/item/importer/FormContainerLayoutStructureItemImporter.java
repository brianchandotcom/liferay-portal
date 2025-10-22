/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer;

import com.liferay.headless.admin.site.dto.v1_0.FormContainerConfig;
import com.liferay.headless.admin.site.dto.v1_0.FormContainerPageElementDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.context.LayoutStructureItemImporterContext;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.LayoutStructureUtil;
import com.liferay.headless.delivery.dto.v1_0.ClassTypeReference;
import com.liferay.headless.delivery.dto.v1_0.ContextReference;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class FormContainerLayoutStructureItemImporter
	implements LayoutStructureItemImporter {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			LayoutStructure layoutStructure,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext,
			PageElement pageElement)
		throws Exception {

		FormStyledLayoutStructureItem formStyledLayoutStructureItem =
			(FormStyledLayoutStructureItem)
				layoutStructure.addFormStyledLayoutStructureItem(
					pageElement.getExternalReferenceCode(),
					LayoutStructureUtil.getParentExternalReferenceCode(
						pageElement, layoutStructure),
					pageElement.getPosition());

		FormContainerPageElementDefinition formContainerPageElementDefinition =
			(FormContainerPageElementDefinition)
				pageElement.getPageElementDefinition();

		if (formContainerPageElementDefinition == null) {
			return formStyledLayoutStructureItem;
		}

		formStyledLayoutStructureItem.setCssClasses(
			SetUtil.fromArray(
				formContainerPageElementDefinition.getCssClasses()));
		formStyledLayoutStructureItem.setCustomCSS(
			formContainerPageElementDefinition.getCustomCSS());

		FormContainerConfig formContainerConfig =
			formContainerPageElementDefinition.getFormContainerConfig();

		if (formContainerConfig == null) {
			formStyledLayoutStructureItem.setIndexed(
				GetterUtil.getBoolean(
					formContainerPageElementDefinition.getIndexed(), true));
			formStyledLayoutStructureItem.setName(
				formContainerPageElementDefinition.getName());

			return formStyledLayoutStructureItem;
		}

		if (formContainerConfig.getFormContainerReference() instanceof
				ContextReference) {

			formStyledLayoutStructureItem.setFormConfig(
				FormStyledLayoutStructureItem.
					FORM_CONFIG_DISPLAY_PAGE_ITEM_TYPE);
		}
		else {
			ClassTypeReference classTypeReference =
				(ClassTypeReference)
					formContainerConfig.getFormContainerReference();

			formStyledLayoutStructureItem.setClassNameId(
				PortalUtil.getClassNameId(classTypeReference.getClassName()));
			formStyledLayoutStructureItem.setClassTypeId(
				classTypeReference.getClassType());

			formStyledLayoutStructureItem.setFormConfig(
				FormStyledLayoutStructureItem.FORM_CONFIG_OTHER_ITEM_TYPE);
		}

		formStyledLayoutStructureItem.setFormType(
			_toFormContainerType(formContainerConfig.getFormContainerType()));
		formStyledLayoutStructureItem.setIndexed(
			GetterUtil.getBoolean(
				formContainerPageElementDefinition.getIndexed(), true));
		formStyledLayoutStructureItem.setName(
			formContainerPageElementDefinition.getName());
		formStyledLayoutStructureItem.setNumberOfSteps(
			formContainerConfig.getNumberOfSteps());

		return formStyledLayoutStructureItem;
	}

	private String _toFormContainerType(
		FormContainerConfig.FormContainerType formType) {

		if (Objects.equals(
				formType, FormContainerConfig.FormContainerType.SIMPLE)) {

			return "simple";
		}

		return "multistep";
	}

}