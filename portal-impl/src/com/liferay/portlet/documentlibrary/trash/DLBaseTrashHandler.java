/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.trash;

import com.liferay.portal.InvalidRepositoryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.trash.BaseTrashHandler;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.service.RepositoryServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zsolt Berentey
 */
public abstract class DLBaseTrashHandler extends BaseTrashHandler {

	@Override
	public ContainerModel getContainerModel(long containerModelId)
		throws PortalException, SystemException {

		return (ContainerModel)getDLFolder(containerModelId);
	}

	@Override
	public List<ContainerModel> getParentContainerModels(long containerModelId)
		throws PortalException, SystemException {

		List<ContainerModel> containerModels = new ArrayList<ContainerModel>();

		ContainerModel containerModel = getContainerModel(containerModelId);

		while (containerModel.getParentContainerModelId() > 0) {
			containerModel = getContainerModel(
				containerModel.getParentContainerModelId());

			if (containerModel == null) {
				break;
			}

			containerModels.add(containerModel);
		}

		return containerModels;
	}

	@Override
	public String getRootContainerModelName() {
		return "home";
	}

	protected DLFolder getDLFolder(long classPK)
		throws PortalException, SystemException {

		Repository repository = RepositoryServiceUtil.getRepositoryImpl(
			classPK, 0, 0);

		if (!(repository instanceof LiferayRepository)) {
			throw new InvalidRepositoryException(
				"Repository " + repository.getRepositoryId() +
					" does not support trash operations");
		}

		Folder folder = repository.getFolder(classPK);

		return (DLFolder)folder.getModel();
	}

}