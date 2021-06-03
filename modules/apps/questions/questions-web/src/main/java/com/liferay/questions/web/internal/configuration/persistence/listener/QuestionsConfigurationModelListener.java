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
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.questions.web.internal.asset.model.MBCategoryAssetRendererFactory;
import com.liferay.questions.web.internal.asset.model.MBMessageAssetRendererFactory;
import com.liferay.questions.web.internal.constants.QuestionsPortletKeys;

import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 */
@Component(
	configurationPid = "com.liferay.questions.web.internal.configuration.QuestionsConfiguration",
	immediate = true,
	property = "model.class.name=com.liferay.questions.web.internal.configuration.QuestionsConfiguration",
	service = ConfigurationModelListener.class
)
public class QuestionsConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onAfterSave(String pid, Dictionary<String, Object> properties) {
		List<String> keys = Collections.list(properties.keys());

		Stream<String> stream = keys.stream();

		_enableAssetRenderer(
			stream.collect(
				Collectors.toMap(Function.identity(), properties::get)));
	}

	@Activate
	@Modified
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		_enableAssetRenderer(properties);
	}

	private void _enableAssetRenderer(Map<String, Object> properties) {
		if (GetterUtil.getBoolean(
				properties.get("enableCustomAssetRenderer"))) {

			String historyRouterBasePath = GetterUtil.getString(
				properties.get("historyRouterBasePath"));
			Dictionary<String, Object> assetRendererFactoryProperties =
				HashMapDictionaryBuilder.<String, Object>put(
					"javax.portlet.name", QuestionsPortletKeys.QUESTIONS
				).put(
					"service.ranking:Integer", 100
				).build();

			_mbCategoryServiceRegistration =
				(ServiceRegistration)_bundleContext.registerService(
					AssetRendererFactory.class,
					new MBCategoryAssetRendererFactory(
						_companyLocalService, historyRouterBasePath,
						_mbCategoryLocalService,
						_mbCategoryModelResourcePermission),
					assetRendererFactoryProperties);
			_mbMessageServiceRegistration =
				(ServiceRegistration)_bundleContext.registerService(
					AssetRendererFactory.class,
					new MBMessageAssetRendererFactory(
						_companyLocalService, historyRouterBasePath,
						_mbMessageLocalService,
						_mbMessageModelResourcePermission),
					assetRendererFactoryProperties);
		}
		else {
			if (_mbMessageServiceRegistration != null) {
				_mbMessageServiceRegistration.unregister();
			}

			if (_mbCategoryServiceRegistration != null) {
				_mbCategoryServiceRegistration.unregister();
			}
		}
	}

	private volatile BundleContext _bundleContext;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private MBCategoryLocalService _mbCategoryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.message.boards.model.MBCategory)"
	)
	private ModelResourcePermission<MBCategory>
		_mbCategoryModelResourcePermission;

	private ServiceRegistration<AssetRendererFactory<MBCategory>>
		_mbCategoryServiceRegistration;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.message.boards.model.MBMessage)"
	)
	private ModelResourcePermission<MBMessage>
		_mbMessageModelResourcePermission;

	private ServiceRegistration<AssetRendererFactory<MBMessage>>
		_mbMessageServiceRegistration;

}