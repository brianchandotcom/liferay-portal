/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.data.handler;

import com.liferay.exportimport.data.handler.PortletElementUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.xml.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Raposo
 */
@Component(service = PortletElementUtil.class)
public class PortletElementUtilImpl implements PortletElementUtil {

	@Override
	public Portlet getPortlet(long companyId, Element portletElement) {
		Portlet portlet = _portletLocalService.getPortletById(
			companyId, getSourcePortletId(portletElement));

		if (!portlet.isActive() || portlet.isUndeployedPortlet()) {
			return null;
		}

		return portlet;
	}

	@Override
	public String getPortletDataHandlerKey(Element portletElement) {
		return portletElement.attributeValue("portlet-data-handler-key");
	}

	@Override
	public int getRank(Element portletElement) {
		return 100;
	}

	@Override
	public String getSourcePortletId(Element portletElement) {
		return portletElement.attributeValue("portlet-id");
	}

	@Override
	public String getTargetPortletId(long companyId, Element portletElement) {
		if (!isMissingPortletSupported(portletElement)) {
			return getSourcePortletId(portletElement);
		}

		BatchEnginePortletDataHandler batchEnginePortletDataHandler =
			BatchEnginePortletDataHandlerRegistryUtil.getByKey(
				companyId, getPortletDataHandlerKey(portletElement));

		if (batchEnginePortletDataHandler == null) {
			return null;
		}

		return batchEnginePortletDataHandler.getPortletId();
	}

	@Override
	public boolean isMissingPortlet(
		PortletDataContext portletDataContext, Element portletElement) {

		Portlet portlet = getPortlet(
			portletDataContext.getCompanyId(), portletElement);

		if (portlet == null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isMissingPortletSupported(Element portletElement) {
		return GetterUtil.getBoolean(
			portletElement.attributeValue("missing-portlet-supported"));
	}

	@Reference
	private PortletLocalService _portletLocalService;

}