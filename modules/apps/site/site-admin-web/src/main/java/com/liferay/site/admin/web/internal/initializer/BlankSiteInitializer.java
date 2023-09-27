/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.admin.web.internal.initializer;

import com.liferay.layout.importer.LayoutsImporter;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.provider.LayoutUtilityPageEntryDefaultPageElementDefinitionProvider;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;

import java.util.Date;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "site.initializer.key=" + BlankSiteInitializer.KEY,
	service = SiteInitializer.class
)
public class BlankSiteInitializer implements SiteInitializer {

	public static final String KEY = "blank-site-initializer";

	@Override
	public String getDescription(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName(Locale locale) {
		return _language.get(locale, "blank-site");
	}

	@Override
	public String getThumbnailSrc() {
		return StringPool.BLANK;
	}

	@Override
	public void initialize(long groupId) throws InitializationException {
		_addLayoutUtilityPageEntry(
			404, groupId, "404 Error",
			LayoutUtilityPageEntryConstants.TYPE_SC_NOT_FOUND);

		_addLayoutUtilityPageEntry(
			500, groupId, "500 Error",
			LayoutUtilityPageEntryConstants.TYPE_SC_INTERNAL_SERVER_ERROR);
	}

	@Override
	public boolean isActive(long companyId) {
		return true;
	}

	private void _addLayoutUtilityPageEntry(
		int errorCode, long groupId, String name, String type) {

		try {
			String pageElementJSON =
				_layoutUtilityPageEntryDefaultPageElementDefinitionProvider.
					getDefaultPageElementJSON(type);

			if (Validator.isNull(pageElementJSON)) {
				return;
			}

			LayoutUtilityPageEntry layoutUtilityPageEntry =
				_layoutUtilityPageEntryService.addLayoutUtilityPageEntry(
					"LFR-" + errorCode + "-ERROR", groupId, 0, 0, true, name,
					type, 0, ServiceContextThreadLocal.getServiceContext());

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
					layout.getGroupId(), layout.getPlid());

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
		BlankSiteInitializer.class);

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutsImporter _layoutsImporter;

	@Reference
	private LayoutUtilityPageEntryDefaultPageElementDefinitionProvider
		_layoutUtilityPageEntryDefaultPageElementDefinitionProvider;

	@Reference
	private LayoutUtilityPageEntryService _layoutUtilityPageEntryService;

}