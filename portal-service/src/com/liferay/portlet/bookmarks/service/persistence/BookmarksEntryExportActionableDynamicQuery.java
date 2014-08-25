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

package com.liferay.portlet.bookmarks.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.bookmarks.model.BookmarksEntry;

/**
 * @author Brian Wing Shun Chan
 * @deprecated As of 7.0.0, replaced by {@link com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil#getExportActionableDynamicQuery()}
 * @generated
 */
@Deprecated
public class BookmarksEntryExportActionableDynamicQuery
	extends BookmarksEntryActionableDynamicQuery {
	public BookmarksEntryExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {
		_portletDataContext = portletDataContext;

		setCompanyId(_portletDataContext.getCompanyId());

		setGroupId(_portletDataContext.getScopeGroupId());
	}

	@Override
	public long performCount() throws PortalException {
		ManifestSummary manifestSummary = _portletDataContext.getManifestSummary();

		StagedModelType stagedModelType = getStagedModelType();

		long modelAdditionCount = super.performCount();

		manifestSummary.addModelAdditionCount(stagedModelType.toString(),
			modelAdditionCount);

		long modelDeletionCount = ExportImportHelperUtil.getModelDeletionCount(_portletDataContext,
				stagedModelType);

		manifestSummary.addModelDeletionCount(stagedModelType.toString(),
			modelDeletionCount);

		return modelAdditionCount;
	}

	@Override
	protected void addCriteria(DynamicQuery dynamicQuery) {
		_portletDataContext.addDateRangeCriteria(dynamicQuery, "modifiedDate");

		StagedModelDataHandler<?> stagedModelDataHandler = StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(BookmarksEntry.class.getName());

		Property workflowStatusProperty = PropertyFactoryUtil.forName("status");

		dynamicQuery.add(workflowStatusProperty.in(
				stagedModelDataHandler.getExportableStatuses()));
	}

	protected StagedModelType getStagedModelType() {
		return new StagedModelType(PortalUtil.getClassNameId(
				BookmarksEntry.class.getName()));
	}

	@Override
	protected void performAction(Object object) throws PortalException {
		BookmarksEntry stagedModel = (BookmarksEntry)object;

		StagedModelDataHandlerUtil.exportStagedModel(_portletDataContext,
			stagedModel);
	}

	private PortletDataContext _portletDataContext;
}