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
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.template.TemplateVariableCodeHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Eudaldo Alonso
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
	protected Map<String, TemplateVariableGroup>
		getAdditionalTemplateVariableGroups() {

		String itemClassName = PortalUtil.getClassName(getClassNameId());

		InfoItemFormProvider<?> infoItemFormProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormProvider.class, itemClassName);

		if (infoItemFormProvider == null) {
			if (log.isWarnEnabled()) {
				log.warn(
					"Unable to get info item form provider for class " +
						itemClassName);
			}

			return Collections.emptyMap();
		}

		String formVariationKey = StringPool.BLANK;

		if (getClassPK() > 0) {
			formVariationKey = String.valueOf(getClassPK());
		}

		InfoForm infoForm = null;

		try {
			infoForm = infoItemFormProvider.getInfoForm(
				formVariationKey, _themeDisplay.getScopeGroupId());
		}
		catch (NoSuchFormVariationException noSuchFormVariationException) {
			if (log.isDebugEnabled()) {
				log.debug(
					StringBundler.concat(
						"Unable to get info form for class ", itemClassName,
						" and variation: ", formVariationKey, " and groupId: ",
						_themeDisplay.getScopeGroupId()),
					noSuchFormVariationException);
			}
		}

		if (infoForm == null) {
			if (log.isWarnEnabled()) {
				log.warn("Unable to get info form for class " + itemClassName);
			}

			return Collections.emptyMap();
		}

		Map<String, TemplateVariableGroup> additionalTemplateVariableGroups =
			new TreeMap<>();

		for (InfoFieldSetEntry infoFieldSetEntry :
				infoForm.getInfoFieldSetEntries()) {

			if (infoFieldSetEntry instanceof InfoFieldSet) {
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

				additionalTemplateVariableGroups.put(
					infoFieldSet.getLabel(_themeDisplay.getLocale()),
					templateVariableGroup);
			}
		}

		return additionalTemplateVariableGroups;
	}

	@Override
	protected String getDefaultScript(long classNameId) {
		return "<#-- Empty script -->";
	}

	@Override
	protected long getTemplateHandlerClassNameId() {
		return PortalUtil.getClassNameId(InfoItemFormProvider.class);
	}

	protected static final Log log = LogFactoryUtil.getLog(
		InformationTemplatesTemplateDisplayContext.class);

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