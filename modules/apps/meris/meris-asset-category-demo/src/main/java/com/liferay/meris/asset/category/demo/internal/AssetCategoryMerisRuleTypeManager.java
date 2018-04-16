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

package com.liferay.meris.asset.category.demo.internal;

import com.liferay.meris.MerisRuleType;
import com.liferay.meris.MerisRuleTypeManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Eduardo Garcia
 */
@Component(immediate = true, service = MerisRuleTypeManager.class)
public class AssetCategoryMerisRuleTypeManager implements MerisRuleTypeManager {

	@Override
	public MerisRuleType getMerisRuleType(String merisRuleTypeId) {
		return _merisRuleTypes.get(merisRuleTypeId);
	}

	@Override
	public List<MerisRuleType> getMerisRuleTypes(
		int start, int end, Comparator<MerisRuleType> comparator) {

		List<MerisRuleType> merisRuleTypes = new ArrayList(
			_merisRuleTypes.values());

		merisRuleTypes = ListUtil.sort(merisRuleTypes, comparator);

		return ListUtil.subList(merisRuleTypes, start, end);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeMerisRuleType"
	)
	protected void addMerisRuleType(MerisRuleType merisRuleType) {
		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format(
					"Adding meris rule type with ID %s",
					merisRuleType.getMerisRuleTypeId()));
		}

		_merisRuleTypes.put(merisRuleType.getMerisRuleTypeId(), merisRuleType);
	}

	protected void removeMerisRuleType(MerisRuleType merisRuleType) {
		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format(
					"Removing meris rule type with ID %s",
					merisRuleType.getMerisRuleTypeId()));
		}

		_merisRuleTypes.remove(merisRuleType.getMerisRuleTypeId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetCategoryMerisRuleTypeManager.class);

	private final Map<String, MerisRuleType> _merisRuleTypes =
		new ConcurrentHashMap<>();

}