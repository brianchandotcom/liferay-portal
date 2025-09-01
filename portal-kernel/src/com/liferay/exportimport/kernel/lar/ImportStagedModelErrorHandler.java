/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lar;

import com.liferay.portal.kernel.group.capability.GroupCapability;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;

/**
 * @author Alvaro Saugar
 */
public interface ImportStagedModelErrorHandler {

	public <T extends StagedModel>  void addErrorImportReportEntry( Exception exception, PortletDataContext portletDataContext, T stagedModel);

}