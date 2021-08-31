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

package com.liferay.template.web.internal.display.context;

import com.liferay.dynamic.data.mapping.template.DDMTemplateVariableCodeHandler;
import com.liferay.info.exception.NoSuchFormVariationException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateVariableCodeHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author Eudaldo Alonso
 * @author Lourdes Fernández Besada
 */
public class InformationTemplatesEditDDMTemplateDisplayContext
	extends EditDDMTemplateDisplayContext {

	public InformationTemplatesEditDDMTemplateDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(liferayPortletRequest, liferayPortletResponse);

		_infoItemServiceTracker =
			(InfoItemServiceTracker)liferayPortletRequest.getAttribute(
				InfoItemServiceTracker.class.getName());
		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String[] getLanguageTypes() {
		return new String[] {TemplateConstants.LANG_TYPE_FTL};
	}

	@Override
	public String getTemplateSubtypeLabel() {
		return Optional.ofNullable(
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				PortalUtil.getClassName(getClassNameId()))
		).map(
			infoItemFormVariationsProvider ->
				infoItemFormVariationsProvider.getInfoItemFormVariation(
					_themeDisplay.getScopeGroupId(),
					String.valueOf(getClassPK()))
		).map(
			infoItemFormVariation -> infoItemFormVariation.getLabel(
				_themeDisplay.getLocale())
		).orElse(
			StringPool.BLANK
		);
	}

	@Override
	public String getTemplateTypeLabel() {
		return Optional.ofNullable(
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemDetailsProvider.class,
				PortalUtil.getClassName(getClassNameId()))
		).map(
			InfoItemDetailsProvider::getInfoItemClassDetails
		).map(
			infoItemDetails -> infoItemDetails.getLabel(
				_themeDisplay.getLocale())
		).orElse(
			ResourceActionsUtil.getModelResource(
				_themeDisplay.getLocale(),
				PortalUtil.getClassName(getClassNameId()))
		);
	}

	@Override
	protected long getTemplateHandlerClassNameId() {
		return PortalUtil.getClassNameId(InfoItemFormProvider.class);
	}

	@Override
	protected Collection<TemplateVariableGroup> getTemplateVariableGroups()
		throws Exception {

		String itemClassName = PortalUtil.getClassName(getClassNameId());

		InfoItemFormProvider<?> infoItemFormProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormProvider.class, itemClassName);

		if (infoItemFormProvider == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get info item form provider for class name " +
						itemClassName);
			}

			return super.getTemplateVariableGroups();
		}

		InfoForm infoForm = null;

		String formVariationKey = StringPool.BLANK;

		if (getClassPK() > 0) {
			formVariationKey = String.valueOf(getClassPK());
		}

		try {
			infoForm = infoItemFormProvider.getInfoForm(
				formVariationKey, _themeDisplay.getScopeGroupId());
		}
		catch (NoSuchFormVariationException noSuchFormVariationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get form variation with key " + formVariationKey,
					noSuchFormVariationException);
			}

			return super.getTemplateVariableGroups();
		}

		List<TemplateVariableGroup> templateVariableGroups = new LinkedList<>(
			super.getTemplateVariableGroups());

		for (InfoFieldSetEntry infoFieldSetEntry :
				infoForm.getInfoFieldSetEntries()) {

			if (!(infoFieldSetEntry instanceof InfoFieldSet)) {
				continue;
			}

			InfoFieldSet infoFieldSet = (InfoFieldSet)infoFieldSetEntry;

			TemplateVariableGroup templateVariableGroup =
				new TemplateVariableGroup(
					infoFieldSet.getLabel(_themeDisplay.getLocale()));

			for (InfoField<?> infoField : infoFieldSet.getAllInfoFields()) {
				InfoFieldType infoFieldType = infoField.getInfoFieldType();

				templateVariableGroup.addFieldVariable(
					infoField.getLabel(_themeDisplay.getLocale()),
					TemplateNode.class, infoField.getName(),
					infoField.getLabel(_themeDisplay.getLocale()),
					infoFieldType.getName(), infoField.isMultivalued(),
					_templateVariableCodeHandler);
			}

			templateVariableGroups.add(templateVariableGroup);
		}

		return templateVariableGroups;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InformationTemplatesEditDDMTemplateDisplayContext.class);

	private final InfoItemServiceTracker _infoItemServiceTracker;
	private final TemplateVariableCodeHandler _templateVariableCodeHandler =
		new DDMTemplateVariableCodeHandler(
			InformationTemplatesTemplateDisplayContext.class.getClassLoader(),
			"com/liferay/template/web/internal/portlet/template/dependencies/",
			SetUtil.fromArray(
				new String[] {
					"boolean", "date", "document-library", "geolocation",
					"image", "journal-article", "link-to-page"
				}));
	private final ThemeDisplay _themeDisplay;

}