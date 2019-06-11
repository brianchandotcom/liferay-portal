/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.application.list.deploy.hot;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.adapter.PortletPanelAppAdapter;
import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.deploy.hot.BaseHotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.deploy.hot.HotDeployListener;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.util.PortletCategoryUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = HotDeployListener.class)
public class LegacyPortletPanelAppHotDeployListener
	extends BaseHotDeployListener {

	public int getServiceRegistrationsSize() {
		return _serviceRegistrations.size();
	}

	@Override
	public void invokeDeploy(HotDeployEvent hotDeployEvent)
		throws HotDeployException {

		try {
			for (Dictionary<String, Object> properties :
					getPropertiesList(hotDeployEvent)) {

				String portletId = (String)properties.get(
					"panel.app.portlet.id");

				ServiceRegistration<PanelApp> serviceRegistration =
					_bundleContext.registerService(
						PanelApp.class, new PortletPanelAppAdapter(portletId),
						properties);

				_serviceRegistrations.put(portletId, serviceRegistration);
			}
		}
		catch (DocumentException | IOException e) {
			throw new HotDeployException(e);
		}
	}

	@Override
	public void invokeUndeploy(HotDeployEvent hotDeployEvent)
		throws HotDeployException {

		try {
			for (Dictionary<String, Object> properties :
					getPropertiesList(hotDeployEvent)) {

				String portletId = (String)properties.get(
					"panel.app.portlet.id");

				ServiceRegistration<PanelApp> serviceRegistration =
					_serviceRegistrations.remove(portletId);

				if (serviceRegistration != null) {
					serviceRegistration.unregister();
				}
			}
		}
		catch (DocumentException | IOException e) {
			throw new HotDeployException(e);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	protected String getPortletId(
		String servletContextName, String portletName) {

		if (servletContextName == null) {
			return portletName;
		}

		return _portal.getJsSafePortletId(
			portletName + PortletConstants.WAR_SEPARATOR + servletContextName);
	}

	protected List<Dictionary<String, Object>> getPropertiesList(
			HotDeployEvent hotDeployEvent)
		throws DocumentException, IOException {

		ServletContext servletContext = hotDeployEvent.getServletContext();

		String xml = StreamUtil.toString(
			servletContext.getResourceAsStream("/WEB-INF/liferay-portlet.xml"));

		if (xml == null) {
			return Collections.emptyList();
		}

		List<Dictionary<String, Object>> propertiesList = new ArrayList<>();

		Document document = UnsecureSAXReaderUtil.read(xml, true);

		Element rootElement = document.getRootElement();

		Iterator<Element> iterator = rootElement.elementIterator("portlet");

		while (iterator.hasNext()) {
			Element portletElement = iterator.next();

			String controlPanelEntryCategory = portletElement.elementText(
				"control-panel-entry-category");

			if (Validator.isNull(controlPanelEntryCategory)) {
				continue;
			}

			controlPanelEntryCategory =
				PortletCategoryUtil.getPortletCategoryKey(
					controlPanelEntryCategory);

			Dictionary<String, Object> properties = new HashMapDictionary<>();

			String portletName = portletElement.elementText("portlet-name");

			String portletId = getPortletId(
				hotDeployEvent.getServletContextName(), portletName);

			properties.put("panel.app.portlet.id", portletId);

			properties.put("panel.category.key", controlPanelEntryCategory);

			String controlPanelEntryWeight = portletElement.elementText(
				"control-panel-entry-weight");

			if (Validator.isNotNull(controlPanelEntryWeight)) {
				int panelAppOrder = (int)Math.ceil(
					GetterUtil.getDouble(controlPanelEntryWeight) * 100);

				properties.put("panel.app.order", panelAppOrder);
			}

			propertiesList.add(properties);
		}

		return propertiesList;
	}

	private BundleContext _bundleContext;

	@Reference
	private Portal _portal;

	private final Map<String, ServiceRegistration<PanelApp>>
		_serviceRegistrations = new ConcurrentHashMap<>();

}