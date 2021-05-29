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

package com.liferay.questions.web.internal.configuration.persistence.listener;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.questions.web.internal.asset.model.MBMessageAssetRendererFactory;
import com.liferay.questions.web.internal.constants.QuestionsPortletKeys;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.questions.web.internal.configuration.QuestionsConfiguration",
	service = ConfigurationModelListener.class
)
public class QuestionsConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onAfterSave(String pid, Dictionary<String, Object> properties) {
		boolean enabled = GetterUtil.getBoolean(
			properties.get("enableCustomAssetRenderer"));

		if (enabled) {
			_serviceRegistration =
				(ServiceRegistration)_bundleContext.registerService(
					AssetRendererFactory.class,
					new MBMessageAssetRendererFactory(
						GetterUtil.getString(
							properties.get("historyRouterBasePath")),
						_mbMessageLocalService,
						_messageModelResourcePermission),
					HashMapDictionaryBuilder.<String, Object>put(
						"javax.portlet.name", QuestionsPortletKeys.QUESTIONS
					).put(
						"service.ranking:Integer", 100
					).build());
		}
		else if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private BundleContext _bundleContext;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.message.boards.model.MBMessage)"
	)
	private ModelResourcePermission<MBMessage> _messageModelResourcePermission;

	private ServiceRegistration<AssetRendererFactory<MBMessage>>
		_serviceRegistration;

}