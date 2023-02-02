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

import com.liferay.layout.importer.LayoutsImporter;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = ModelListener.class)
public class GroupModelListener extends BaseModelListener<Group> {

	@Override
	public void onAfterCreate(Group group) throws ModelListenerException {
		try {
			LayoutUtilityPageEntry layoutUtilityPageEntry =
				_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
					null, group.getCreatorUserId(), group.getGroupId(), 0, 0,
					true, "404 Error",
					LayoutUtilityPageEntryConstants.SC_NOT_FOUND, 0);

			String pageElementJSON = StringUtil.read(
				GroupModelListener.class,
				"generic-layout-utility-page-entry-page-element.json");

			if (Validator.isNotNull(pageElementJSON)) {
				Layout layout = _layoutLocalService.getLayout(
					layoutUtilityPageEntry.getPlid());

				Layout draftLayout = layout.fetchDraftLayout();

				_importPageElement(draftLayout, pageElementJSON);

				_importPageElement(layout, pageElementJSON);

				_updateLayoutUtilityPageEntryLayouts(
					draftLayout.getPlid(), layout.getPlid());
			}
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

}