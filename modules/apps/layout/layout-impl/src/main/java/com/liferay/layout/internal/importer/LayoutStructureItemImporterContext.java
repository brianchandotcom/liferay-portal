/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.importer;

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutStructureItemImporterContext {

	public LayoutStructureItemImporterContext(
		Layout layout, double pageDefinitionVersion, String parentItemId,
		int position, boolean preserveItemIds, long segmentsExperienceId,
		GroupLocalService groupLocalService,
		InfoItemServiceRegistry infoItemServiceRegistry,
		InfoSearchClassMapperRegistry infoSearchClassMapperRegistry,
		LayoutLocalService layoutLocalService,
		LayoutPageTemplateEntryLocalService
			layoutPageTemplateEntryLocalService) {

		_layout = layout;
		_pageDefinitionVersion = pageDefinitionVersion;
		_parentItemId = parentItemId;
		_position = position;
		_preserveItemIds = preserveItemIds;
		_segmentsExperienceId = segmentsExperienceId;
		_groupLocalService = groupLocalService;
		_infoItemServiceRegistry = infoItemServiceRegistry;
		_infoSearchClassMapperRegistry = infoSearchClassMapperRegistry;
		_layoutLocalService = layoutLocalService;
		_layoutPageTemplateEntryLocalService =
			layoutPageTemplateEntryLocalService;
	}

	public GroupLocalService getGroupLocalService() {
		return _groupLocalService;
	}

	public InfoItemServiceRegistry getInfoItemServiceRegistry() {
		return _infoItemServiceRegistry;
	}

	public InfoSearchClassMapperRegistry getInfoSearchClassMapperRegistry() {
		return _infoSearchClassMapperRegistry;
	}

	public String getItemId(PageElement pageElement) {
		if (isPreserveItemIds()) {
			return pageElement.getId();
		}

		return StringPool.BLANK;
	}

	public Layout getLayout() {
		return _layout;
	}

	public LayoutLocalService getLayoutLocalService() {
		return _layoutLocalService;
	}

	public LayoutPageTemplateEntryLocalService
		getLayoutPageTemplateEntryLocalService() {

		return _layoutPageTemplateEntryLocalService;
	}

	public double getPageDefinitionVersion() {
		return _pageDefinitionVersion;
	}

	public String getParentItemId() {
		return _parentItemId;
	}

	public int getPosition() {
		return _position;
	}

	public long getSegmentsExperienceId() {
		return _segmentsExperienceId;
	}

	public boolean isPreserveItemIds() {
		return _preserveItemIds;
	}

	private final GroupLocalService _groupLocalService;
	private final InfoItemServiceRegistry _infoItemServiceRegistry;
	private final InfoSearchClassMapperRegistry _infoSearchClassMapperRegistry;
	private final Layout _layout;
	private final LayoutLocalService _layoutLocalService;
	private final LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;
	private final double _pageDefinitionVersion;
	private final String _parentItemId;
	private final int _position;
	private final boolean _preserveItemIds;
	private final long _segmentsExperienceId;

}