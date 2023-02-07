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

package com.liferay.layout.utility.page.internal.model.listener;

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.layout.importer.LayoutsImporter;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = ModelListener.class)
public class GroupModelListener extends BaseModelListener<Group> {

	@Override
	public void onAfterCreate(Group group) throws ModelListenerException {
		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-155184")) ||
			ExportImportThreadLocal.isStagingInProcess() || (group == null) ||
			(group.getClassNameId() != _portal.getClassNameId(
				Group.class.getName())) ||
			!group.isSite()) {

			return;
		}

		try {
			LayoutUtilityPageEntry layoutUtilityPageEntry =
				_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
					"LFR-404-ERROR", group.getCreatorUserId(),
					group.getGroupId(), 0, 0, true, "404 Error",
					LayoutUtilityPageEntryConstants.TYPE_SC_NOT_FOUND, 0);

			JSONObject errorCodeI18nJSONObject =
				_jsonFactory.createJSONObject();
			JSONObject instructionsI18nJSONObject =
				_jsonFactory.createJSONObject();
			JSONObject layoutUtilityPageEntryDescriptionI18nJSONObject =
				_jsonFactory.createJSONObject();
			JSONObject layoutUtilityPageEntryInstructionsI18nJSONObject =
				_jsonFactory.createJSONObject();
			JSONObject layoutUtilityPageEntryTitleI18nJSONObject =
				_jsonFactory.createJSONObject();

			Set<Locale> locales = new HashSet<>(
				_language.getAvailableLocales());

			for (Locale locale : locales) {
				errorCodeI18nJSONObject.put(
					LocaleUtil.toLanguageId(locale),
					_language.format(locale, "error-code-x", "404"));
				instructionsI18nJSONObject.put(
					LocaleUtil.toLanguageId(locale),
					_language.get(locale, "instructions"));
				layoutUtilityPageEntryDescriptionI18nJSONObject.put(
					LocaleUtil.toLanguageId(locale),
					_language.get(
						locale,
						"layout-utility-page-entry-description[SC_NOT_FOUND]"));
				layoutUtilityPageEntryInstructionsI18nJSONObject.put(
					LocaleUtil.toLanguageId(locale),
					_language.get(
						locale,
						"layout-utility-page-entry-instructions" +
							"[SC_NOT_FOUND]"));
				layoutUtilityPageEntryTitleI18nJSONObject.put(
					LocaleUtil.toLanguageId(locale),
					_language.get(
						locale,
						"layout-utility-page-entry-title[SC_NOT_FOUND]"));
			}

			String pageElementJSON = StringUtil.replace(
				StringUtil.read(
					GroupModelListener.class,
					"generic-layout-utility-page-entry-page-element.json"),
				"\"[$", "$]\"",
				HashMapBuilder.put(
					"ERROR_CODE_I18N_JSON_VALUE",
					errorCodeI18nJSONObject.toString()
				).put(
					"INSTRUCTIONS_I18N_JSON_VALUE",
					instructionsI18nJSONObject.toString()
				).put(
					"LAYOUT_UTILITY_PAGE_ENTRY_DESCRIPTION_I18N_JSON_VALUE",
					layoutUtilityPageEntryDescriptionI18nJSONObject.toString()
				).put(
					"LAYOUT_UTILITY_PAGE_ENTRY_INSTRUCTIONS_I18N_JSON_VALUE",
					layoutUtilityPageEntryInstructionsI18nJSONObject.toString()
				).put(
					"LAYOUT_UTILITY_PAGE_ENTRY_TITLE_I18N_JSON_VALUE",
					layoutUtilityPageEntryTitleI18nJSONObject.toString()
				).build());

			Layout layout = _layoutLocalService.getLayout(
				layoutUtilityPageEntry.getPlid());

			Layout draftLayout = layout.fetchDraftLayout();

			_importPageElement(draftLayout, pageElementJSON);

			_importPageElement(layout, pageElementJSON);

			_updateLayoutUtilityPageEntryLayouts(
				draftLayout.getPlid(), layout.getPlid());
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private void _importPageElement(Layout layout, String pageElementJSON)
		throws Exception {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid(), true);

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getDefaultSegmentsExperienceData());

		_layoutsImporter.importPageElement(
			layout, layoutStructure, layoutStructure.getMainItemId(),
			pageElementJSON, 0);
	}

	private void _updateLayoutUtilityPageEntryLayouts(
			long draftLayoutPlid, long layoutPlid)
		throws Exception {

		Layout draftLayout = _layoutLocalService.getLayout(draftLayoutPlid);

		UnicodeProperties typeSettingsUnicodeProperties =
			draftLayout.getTypeSettingsProperties();

		typeSettingsUnicodeProperties.put("published", Boolean.TRUE.toString());

		draftLayout.setTypeSettingsProperties(typeSettingsUnicodeProperties);

		draftLayout.setStatus(WorkflowConstants.STATUS_APPROVED);

		_layoutLocalService.updateLayout(draftLayout);

		Layout layout = _layoutLocalService.getLayout(layoutPlid);

		_layoutLocalService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			new Date());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroupModelListener.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutsImporter _layoutsImporter;

	@Reference
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Reference
	private Portal _portal;

}