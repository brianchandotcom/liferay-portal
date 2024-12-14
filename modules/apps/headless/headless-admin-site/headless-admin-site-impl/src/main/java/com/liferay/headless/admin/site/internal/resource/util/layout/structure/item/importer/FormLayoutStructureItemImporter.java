/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.util.layout.structure.item.importer;

import com.liferay.headless.admin.site.dto.v1_0.FormConfig;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.dto.v1_0.PageFormDefinition;
import com.liferay.headless.admin.site.internal.resource.util.layout.structure.LayoutStructureUtil;
import com.liferay.headless.admin.site.internal.resource.util.layout.structure.item.importer.context.LayoutStructureItemImporterContext;
import com.liferay.headless.delivery.dto.v1_0.ClassTypeReference;
import com.liferay.headless.delivery.dto.v1_0.ContextReference;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class FormLayoutStructureItemImporter
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

		PageFormDefinition pageFormDefinition =
			(PageFormDefinition)pageElement.getDefinition();

		if (pageFormDefinition == null) {
			return formStyledLayoutStructureItem;
		}

		formStyledLayoutStructureItem.setCssClasses(
			SetUtil.fromArray(pageFormDefinition.getCssClasses()));
		formStyledLayoutStructureItem.setCustomCSS(
			pageFormDefinition.getCustomCSS());

		FormConfig formConfig = pageFormDefinition.getFormConfig();

		formConfig.getFormReference();

		if (formConfig.getFormReference() instanceof ContextReference) {
			formStyledLayoutStructureItem.setFormConfig(
				FormStyledLayoutStructureItem.
					FORM_CONFIG_DISPLAY_PAGE_ITEM_TYPE);
		}
		else {
			ClassTypeReference classTypeReference =
				(ClassTypeReference)formConfig.getFormReference();

			formStyledLayoutStructureItem.setClassNameId(
				PortalUtil.getClassNameId(classTypeReference.getClassName()));
			formStyledLayoutStructureItem.setClassTypeId(
				classTypeReference.getClassType());

			formStyledLayoutStructureItem.setFormConfig(
				FormStyledLayoutStructureItem.FORM_CONFIG_OTHER_ITEM_TYPE);
		}

		formStyledLayoutStructureItem.setFormType(
			_toFormType(formConfig.getFormType()));
		formStyledLayoutStructureItem.setNumberOfSteps(
			formConfig.getNumberOfSteps());

		formStyledLayoutStructureItem.setIndexed(
			pageFormDefinition.getIndexed());
		formStyledLayoutStructureItem.setName(pageFormDefinition.getName());

		return formStyledLayoutStructureItem;
	}

	private String _toFormType(FormConfig.FormType formType) {
		if (Objects.equals(formType, FormConfig.FormType.SIMPLE)) {
			return "simple";
		}

		return "multistep";
	}

}