/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.portlet.element.handler;

import com.liferay.portal.kernel.model.Portlet;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Daniel Raposo
 * @author Carlos Correa
 */
@ProviderType
public interface PortletElementHandler {

	public String getDisplayName();

	public long getLayoutId();

	public String getPath();

	public Portlet getPortlet(long companyId);

	public String getPortletConfiguration();

	public String getPortletDataHandlerKey();

	public int getRank();

	public String getSchemaVersion();

	public String getSourcePortletId();

	public String getTargetPortletId(long companyId);

	public boolean isMissingPortletSupported();

	public boolean isPortletData();

	public boolean isValidateExistingDataHandler();

	public void setDisplayName(String displayName);

	public void setLayoutId(long layoutId);

	public void setMissingPortletSupported(boolean missingPortletSupported);

	public void setPath(String path);

	public void setPortletConfiguration(String portletConfiguration);

	public void setPortletData(boolean portletData);

	public void setPortletDataHandlerKey(String portletDataHandlerKey);

	public void setRank(int rank);

	public void setSchemaVersion(String schemaVersion);

	public void setSourcePortletId(String sourcePortletId);

	public void setValidateExistingDataHandler(
		boolean validateExistingDataHandler);

}