/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.data.handler;

import com.liferay.exportimport.data.handler.PortletElementHandler;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.xml.Element;

/**
 * @author Daniel Raposo
 * @author Carlos Correa
 */
public class PortletElementHandlerImpl implements PortletElementHandler {

	public PortletElementHandlerImpl(
		Element portletElement, PortletLocalService portletLocalService) {

		_portletElement = portletElement;
		_portletLocalService = portletLocalService;
	}

	@Override
	public String getDisplayName() {
		return _portletElement.attributeValue("display-name");
	}

	@Override
	public long getLayoutId() {
		return GetterUtil.getLong(_portletElement.attributeValue("layout-id"));
	}

	@Override
	public String getPath() {
		return _portletElement.attributeValue("path");
	}

	@Override
	public Portlet getPortlet(long companyId) {
		Portlet portlet = _portletLocalService.getPortletById(
			companyId, getSourcePortletId());

		if (!portlet.isActive() || portlet.isUndeployedPortlet()) {
			return null;
		}

		return portlet;
	}

	@Override
	public String getPortletConfiguration() {
		return _portletElement.attributeValue("portlet-configuration");
	}

	@Override
	public String getPortletDataHandlerKey() {
		return _portletElement.attributeValue("portlet-data-handler-key");
	}

	@Override
	public int getRank() {
		return GetterUtil.getInteger(
			_portletElement.attributeValue("portlet-data-handler-rank"));
	}

	@Override
	public String getSchemaVersion() {
		return _portletElement.attributeValue("schema-version");
	}

	@Override
	public String getSourcePortletId() {
		return _portletElement.attributeValue("portlet-id");
	}

	@Override
	public String getTargetPortletId(long companyId) {
		if (!isMissingPortletSupported()) {
			return getSourcePortletId();
		}

		String portletDataHandlerKey = getPortletDataHandlerKey();

		if (portletDataHandlerKey == null) {
			return null;
		}

		return BatchEnginePortletDataHandlerRegistryUtil.getPortletId(
			companyId, portletDataHandlerKey);
	}

	@Override
	public boolean isMissingPortletSupported() {
		return GetterUtil.getBoolean(
			_portletElement.attributeValue("missing-portlet-supported"));
	}

	@Override
	public boolean isPortletData() {
		return GetterUtil.getBoolean(
			_portletElement.attributeValue("portlet-data"));
	}

	private final Element _portletElement;
	private final PortletLocalService _portletLocalService;

}