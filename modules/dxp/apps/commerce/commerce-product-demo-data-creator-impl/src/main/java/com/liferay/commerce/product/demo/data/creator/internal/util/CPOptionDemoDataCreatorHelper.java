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

package com.liferay.commerce.product.demo.data.creator.internal.util;

import com.liferay.commerce.product.exception.NoSuchCPOptionException;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPOptionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CPOptionDemoDataCreatorHelper.class)
public class CPOptionDemoDataCreatorHelper extends BaseCPDemoDataCreatorHelper {

	public void addCPOptions(
			Locale locale, long userId, long groupId, long cpDefinitionId,
			JSONArray options)
		throws PortalException {

		for (int i = 0; i < options.length(); i++) {
			JSONObject option = options.getJSONObject(i);

			String name = option.getString("name");
			String title = option.getString("title");
			String description = option.getString("description");
			String ddmFormFieldTypeName = option.getString(
				"ddmFormFieldTypeName");
			int priority = option.getInt("priority");
			boolean facetable = option.getBoolean("facetable");
			boolean skuContributor = option.getBoolean("skuContributor");

			Map<Locale, String> titleMap = new HashMap<>();
			Map<Locale, String> descriptionMap = new HashMap<>();

			titleMap.put(locale, title);
			descriptionMap.put(locale, description);

			CPOption cpOption = getCPOption(locale, userId, groupId, option);

			long cpOptionId = cpOption.getCPOptionId();

			CPDefinitionOptionRel cpDefinitionOptionRel =
				_cpDefinitionOptionRelDemoDataCreatorHelper.
					createCPDefinitionOptionRel(
						userId, groupId, cpDefinitionId, cpOptionId, name,
						titleMap, descriptionMap, ddmFormFieldTypeName,
						priority, facetable, skuContributor);

			long cpDefinitionOptionRelId =
				cpDefinitionOptionRel.getCPDefinitionOptionRelId();

			JSONArray values = option.getJSONArray("values");

			_cpDefinitionOptionValueRelDemoDataCreatorHelper.
				addCPDefinitionOptionValueRels(
					locale, userId, groupId, cpDefinitionOptionRelId, values);
		}
	}

	public CPOption createCPOption(
			long userId, long groupId, String name,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String ddmFormFieldTypeName, boolean facetable,
			boolean skuContributor)
		throws PortalException {

		ServiceContext serviceContext = getServiceContext(userId, groupId);

		CPOption cpOption = _cpOptionLocalService.addCPOption(
			name, titleMap, descriptionMap, ddmFormFieldTypeName, facetable,
			false, skuContributor, serviceContext);

		_cpOptionIds.add(cpOption.getCPOptionId());

		return cpOption;
	}

	public void deleteCPOptions() throws PortalException {
		for (long cpOptionId : _cpOptionIds) {
			try {
				_cpOptionLocalService.deleteCPOption(cpOptionId);
			}
			catch (NoSuchCPOptionException nscpoe) {
				if (_log.isWarnEnabled()) {
					_log.warn(nscpoe);
				}
			}

			_cpOptionIds.remove(cpOptionId);
		}
	}

	public CPOption getCPOption(
			Locale locale, long userId, long groupId, JSONObject option)
		throws PortalException {

		CPOption cpOption = null;

		String name = option.getString("name");

		cpOption = _cpOptions.get(name);

		if (cpOption != null) {
			return cpOption;
		}

		String title = option.getString("title");
		String description = option.getString("description");
		String ddmFormFieldTypeName = option.getString("ddmFormFieldTypeName");
		boolean facetable = option.getBoolean("facetable");
		boolean skuContributor = option.getBoolean("skuContributor");

		Map<Locale, String> titleMap = new HashMap<>();
		Map<Locale, String> descriptionMap = new HashMap<>();

		titleMap.put(locale, title);
		descriptionMap.put(locale, description);

		cpOption = createCPOption(
			userId, groupId, name, titleMap, descriptionMap,
			ddmFormFieldTypeName, facetable, skuContributor);

		_cpOptions.put(name, cpOption);

		return cpOption;
	}

	public void init() {
		_cpOptions = new HashMap<>();
	}

	@Activate
	protected void activate() throws PortalException {
		init();
	}

	@Deactivate
	protected void deactivate() throws PortalException {
		_cpOptions = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPOptionDemoDataCreatorHelper.class);

	@Reference
	private CPDefinitionOptionRelDemoDataCreatorHelper
		_cpDefinitionOptionRelDemoDataCreatorHelper;

	@Reference
	private CPDefinitionOptionValueRelDemoDataCreatorHelper
		_cpDefinitionOptionValueRelDemoDataCreatorHelper;

	private final List<Long> _cpOptionIds = new CopyOnWriteArrayList<>();

	@Reference
	private CPOptionLocalService _cpOptionLocalService;

	private Map<String, CPOption> _cpOptions;

}