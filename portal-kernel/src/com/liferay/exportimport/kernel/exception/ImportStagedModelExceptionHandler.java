/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.exception;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.model.StagedModel;

/**
 * @author Alvaro Saugar
 */
public interface ImportStagedModelExceptionHandler {

	public <T extends StagedModel> void handle(
		Exception exception, PortletDataContext portletDataContext,
		T stagedModel);

}