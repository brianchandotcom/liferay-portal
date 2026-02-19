/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.portlet.element.handler;

import com.liferay.exportimport.internal.data.handler.BatchEnginePortletDataHandlerRegistryUtil;
import com.liferay.exportimport.portlet.element.handler.PortletElementHandler;
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
		return _portletElement.attributeValue(_DISPLAY_NAME);
	}

	@Override
	public long getLayoutId() {
		return GetterUtil.getLong(_portletElement.attributeValue(_LAYOUT_ID));
	}

	@Override
	public String getPath() {
		return _portletElement.attributeValue(_PATH);
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
		return _portletElement.attributeValue(_PORTLET_CONFIGURATION);
	}

	@Override
	public String getPortletDataHandlerKey() {
		return _portletElement.attributeValue(_PORTLET_DATA_HANDLER_KEY);
	}

	@Override
	public int getRank() {
		return GetterUtil.getInteger(
			_portletElement.attributeValue(_PORTLET_DATA_HANDLER_RANK));
	}

	@Override
	public String getSchemaVersion() {
		return _portletElement.attributeValue(_SCHEMA_VERSION);
	}

	@Override
	public String getSourcePortletId() {
		return _portletElement.attributeValue(_PORTLET_ID);
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
			_portletElement.attributeValue(_MISSING_PORTLET_SUPPORTED));
	}

	@Override
	public boolean isPortletData() {
		return GetterUtil.getBoolean(
			_portletElement.attributeValue(_PORTLET_DATA));
	}

	@Override
	public boolean isValidateExistingDataHandler() {
		return GetterUtil.getBoolean(
			_portletElement.attributeValue(_VALIDATE_EXISTING_DATA_HANDLER));
	}

	@Override
	public void setDisplayName(String displayName) {
		_portletElement.addAttribute(_DISPLAY_NAME, displayName);
	}

	@Override
	public void setLayoutId(long layoutId) {
		_portletElement.addAttribute(_LAYOUT_ID, String.valueOf(layoutId));
	}

	@Override
	public void setMissingPortletSupported(boolean missingPortletSupported) {
		_portletElement.addAttribute(
			_MISSING_PORTLET_SUPPORTED,
			String.valueOf(missingPortletSupported));
	}

	@Override
	public void setPath(String path) {
		_portletElement.addAttribute(_PATH, path);
	}

	@Override
	public void setPortletConfiguration(String portletConfiguration) {
		_portletElement.addAttribute(
			_PORTLET_CONFIGURATION, portletConfiguration);
	}

	@Override
	public void setPortletData(boolean portletData) {
		_portletElement.addAttribute(
			_PORTLET_DATA, String.valueOf(portletData));
	}

	@Override
	public void setPortletDataHandlerKey(String portletDataHandlerKey) {
		_portletElement.addAttribute(
			_PORTLET_DATA_HANDLER_KEY, portletDataHandlerKey);
	}

	@Override
	public void setRank(int rank) {
		_portletElement.addAttribute(
			_PORTLET_DATA_HANDLER_RANK, String.valueOf(rank));
	}

	@Override
	public void setSchemaVersion(String schemaVersion) {
		_portletElement.addAttribute(_SCHEMA_VERSION, schemaVersion);
	}

	@Override
	public void setSourcePortletId(String sourcePortletId) {
		_portletElement.addAttribute(_PORTLET_ID, sourcePortletId);
	}

	@Override
	public void setValidateExistingDataHandler(
		boolean validateExistingDataHandler) {

		_portletElement.addAttribute(
			_VALIDATE_EXISTING_DATA_HANDLER,
			String.valueOf(validateExistingDataHandler));
	}

	private static final String _DISPLAY_NAME = "display-name";

	private static final String _LAYOUT_ID = "layout-id";

	private static final String _MISSING_PORTLET_SUPPORTED =
		"missing-portlet-supported";

	private static final String _PATH = "path";

	private static final String _PORTLET_CONFIGURATION =
		"portlet-configuration";

	private static final String _PORTLET_DATA = "portlet-data";

	private static final String _PORTLET_DATA_HANDLER_KEY =
		"portlet-data-handler-key";

	private static final String _PORTLET_DATA_HANDLER_RANK =
		"portlet-data-handler-rank";

	private static final String _PORTLET_ID = "portlet-id";

	private static final String _SCHEMA_VERSION = "schema-version";

	private static final String _VALIDATE_EXISTING_DATA_HANDLER =
		"validate-existing-data-handler";

	private final Element _portletElement;
	private final PortletLocalService _portletLocalService;

}