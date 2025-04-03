/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.model.SystemEvent;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Vendel Toreki
 */
@ProviderType
public interface BatchEngineDeletionHelper {

	public void addDeletionEvent(
		PortletDataContext portletDataContext, SystemEvent systemEvent);

	public void exportDeletions(PortletDataContext portletDataContext);

	public void importDeletions(
			PortletDataContext portletDataContext, String portletId)
		throws Exception;

	public boolean isBatchDeleteSupported(String className);

	public boolean isBatchPortlet(String portletId);

}