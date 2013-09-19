/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.StagedModel;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 * @author Zsolt Berentey
 */
public interface StagedModelDataHandler<T extends StagedModel> {

	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException, SystemException;

	public void exportStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException;

	public String[] getClassNames();

	public String getDisplayName(T StagedModel);

	public int[] getExportableStatuses();

	public void importCompanyStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException;

	public void importStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException;

	public void restoreStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException;

	public boolean validateReference(
		PortletDataContext portletDataContext, Element rootElement,
		Element referenceElement);

}