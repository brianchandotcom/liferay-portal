/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.comment;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.model.StagedModel;

/**
 * @author Adolfo Pérez
 */
public interface DiscussionStagingHandler {

	public <T extends StagedModel> void exportReferenceDiscussions(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException;

	public String getClassName();

	public ActionableDynamicQuery getCommentExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	public String getResourceName();

	public Class<? extends StagedModel> getStagedModelClass();

	public <T extends StagedModel> void importReferenceDiscussions(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException;

}