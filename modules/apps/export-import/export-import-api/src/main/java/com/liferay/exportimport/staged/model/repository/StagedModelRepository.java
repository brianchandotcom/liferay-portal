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

package com.liferay.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.StagedModel;

import java.util.List;

/**
 * Provides an interface for CRUD operations for a given staged model. This
 * interface simplifies staged model data handlers because the CRUD operations
 * and code logic related to those operations can be managed here instead. The
 * get and fetch methods are typically used during an export process to
 * query/find staged models.
 *
 * <p>
 * The staged model parameter can be either
 * </p>
 *
 * <ul>
 * <li>
 * the old staged model that was serialized at export time and deserialized at
 * import time. This means it is more like a POJO and its key attributes
 * (primary key and foreign keys) should not be used during the CRUD operations.
 * </li>
 * <li>
 * the new staged model currently being called from the portal. This means the
 * staged model can be used as a regular entity.
 * </li>
 * </ul>
 *
 * @author Daniel Kocsis
 * @param  <T> the staged model type on which this staged model repository
 *         operates
 */
public interface StagedModelRepository<T extends StagedModel> {

	/**
	 * Persists a new staged model. When setting the staged model parameter, the
	 * primary key attribute should not be used. Foreign keys should be set
	 * before calling this method. For example, suppose a bookmarks entry is
	 * being imported. It has a group ID and a folder ID as foreign keys which
	 * are required by the local service. These keys should be set on the staged
	 * model before calling this method because this method will forward these
	 * values to the service method like this:
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * _bookmarksEntryLocalService.addEntry(userId, bookmarksEntry.getGroupId(),
	 *         bookmarksEntry.getFolderId(), bookmarksEntry.getName(),
	 *         bookmarksEntry.getUrl(), bookmarksEntry.getDescription(),
	 *         serviceContext);
	 * </code>
	 * </pre></p>
	 *
	 * @param  portletDataContext the portlet data context
	 * @param  stagedModel the old staged model being copied
	 * @return the newly persisted staged model
	 * @throws PortalException if a portal exception occurred
	 */
	public T addStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortalException;

	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException;

	public void deleteStagedModel(T stagedModel) throws PortalException;

	/**
	 * Deletes all the staged models this repository handles. This usually
	 * includes the data from the portlet data context's scope group.
	 *
	 * @param  portletDataContext the portlet data context
	 * @throws PortalException if a portal exception occurred
	 */
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException;

	/**
	 * Fetches the model being handled by this repository as a missing
	 * reference. If possible, use {@link
	 * StagedModelRepositoryHelper#fetchMissingReference(String, long,
	 * StagedModelRepository)} as the implementation. This method does not just
	 * fetch a staged model matching the UUID and group ID; it also has a set of
	 * rules defining where to find the missing reference. It searches sites in
	 * the following order:
	 *
	 * <ol>
	 * <li>
	 * Importing site
	 * </li>
	 * <li>
	 * Importing site's parent sites
	 * </li>
	 * <li>
	 * Global site
	 * </li>
	 * </ol>
	 *
	 * @param  uuid the universal unique ID
	 * @param  groupId the importing group's ID
	 * @return the missing reference entity, or <code>null</code> if it is not
	 *         found
	 */
	public default T fetchMissingReference(String uuid, long groupId) {
		return null;
	}

	public T fetchStagedModelByUuidAndGroupId(String uuid, long groupId);

	public List<T> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	 * Returns a dynamic query that queries the entities from the database based
	 * on the parameters in the portlet data context. This method is a wrapper
	 * for the same method in the Service Builder generated local service. If
	 * possible, use the local service's
	 * <code>getExportActionableDynamicQuery(PortletDataContext)</code> as the
	 * implementation.
	 *
	 * <p>
	 * The {@link
	 * com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery#getPerformActionMethod}
	 * is an API call (uses {@link
	 * com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil#exportStagedModel(
	 * PortletDataContext, StagedModel)}) to export each entity that is
	 * returned.
	 * </p>
	 *
	 * @param  portletDataContext the portlet data context
	 * @return returns the export actionable dynamic query
	 */
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	public default T getStagedModel(long classPK) throws PortalException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Restores the staged model from the portal instance's trash.
	 *
	 * @param  portletDataContext the portlet data context
	 * @param  stagedModel the old staged model restored from the trash
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public default void restoreStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException {
	}

	public T saveStagedModel(T stagedModel) throws PortalException;

	/**
	 * Returns the updated staged model.
	 *
	 * @param  portletDataContext the portlet data context
	 * @param  stagedModel the new staged model currently being called from the
	 *         portal
	 * @return returns the updated staged model
	 * @throws PortalException if a portal exception occurred
	 */
	public T updateStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortalException;

}