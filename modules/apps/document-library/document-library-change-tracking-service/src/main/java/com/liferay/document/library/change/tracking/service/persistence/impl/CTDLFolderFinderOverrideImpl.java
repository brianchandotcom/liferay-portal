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

package com.liferay.document.library.change.tracking.service.persistence.impl;

import com.liferay.change.tracking.CTEngineManager;
import com.liferay.change.tracking.CTManager;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.document.library.change.tracking.service.persistence.CTDLFolderFinderOverride;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionImpl;
import com.liferay.portlet.documentlibrary.service.persistence.impl.DLFolderFinderImpl;

import java.util.List;
import java.util.Optional;

/**
 * @author Luiz Marins
 */
public class CTDLFolderFinderOverrideImpl
	extends DLFolderFinderImpl implements CTDLFolderFinderOverride {

	public static final String COUNT_FE_BY_G_F =
		CTDLFolderFinderOverride.class.getName() + ".countFE_ByG_F";

	public static final String FIND_FE_BY_G_F =
		CTDLFolderFinderOverride.class.getName() + ".findFE_ByG_F";

	@Override
	public int filterCountF_FE_FS_ByG_F_M_M(
		long groupId, long folderId, String[] mimeTypes,
		boolean includeMountFolders, QueryDefinition<?> queryDefinition) {

		return doCountF_FE_FS_ByG_F_M_M(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition,
			true);
	}

	@Override
	public List<Object> filterFindF_FE_FS_ByG_F_M_M(
		long groupId, long folderId, String[] mimeTypes,
		boolean includeMountFolders, QueryDefinition<?> queryDefinition) {

		return doFindF_FE_FS_ByG_F_M_M(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition,
			true);
	}

	@Override
	protected String doGetFileEntriesSQL(QueryDefinition<?> queryDefinition) {
		return _customSQL.get(
			getClass(), FIND_FE_BY_G_F, queryDefinition,
			DLFileVersionImpl.TABLE_NAME);
	}

	@Override
	protected String doGetFileVersionSQL(QueryDefinition<?> queryDefinition) {
		return _customSQL.get(
			getClass(), COUNT_FE_BY_G_F, queryDefinition,
			DLFileVersionImpl.TABLE_NAME);
	}

	@Override
	protected void setFileVersionQueryParameters(
		QueryDefinition<?> queryDefinition, QueryPos qPos, long groupId,
		long folderId, String[] mimeTypes) {

		super.setFileVersionQueryParameters(
			queryDefinition, qPos, groupId, folderId, mimeTypes);

		long userId = queryDefinition.getOwnerUserId();
		Group group = _groupLocalService.fetchGroup(groupId);

		Optional<CTCollection> ctCollectionOptional =
			_ctManager.getActiveCTCollectionOptional(userId);

		if (!ctCollectionOptional.isPresent()) {
			return;
		}

		CTCollection ctCollection = ctCollectionOptional.get();

		Optional<CTCollection> productionCTCollectionOptional =
			_ctEngineManager.getProductionCTCollectionOptional(
				group.getCompanyId());

		if (!productionCTCollectionOptional.isPresent()) {
			return;
		}

		CTCollection productionCollection =
			productionCTCollectionOptional.get();

		qPos.add(userId);
		qPos.add(ctCollection.getCtCollectionId());
		qPos.add(productionCollection.getCtCollectionId());
	}

	@ServiceReference(type = CTEngineManager.class)
	private CTEngineManager _ctEngineManager;

	@ServiceReference(type = CTManager.class)
	private CTManager _ctManager;

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

	@ServiceReference(type = GroupLocalService.class)
	private GroupLocalService _groupLocalService;

}