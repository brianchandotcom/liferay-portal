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

package com.liferay.roles.admin.internal.panel.category.role.type.mapper;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.roles.admin.panel.category.role.type.mapper.PanelCategoryRoleTypeMapper;
import com.liferay.roles.admin.panel.category.role.type.mapper.PanelCategoryRoleTypeMapperRegistry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Jiaxu Wei
 */
@Component(service = PanelCategoryRoleTypeMapperRegistry.class)
public class PanelCategoryRoleTypeMapperRegistryImpl
	implements PanelCategoryRoleTypeMapperRegistry {

	@Override
	public String[] getPanelCategoryKeys(int type) {
		Set<String> panelCategoryKeys = new HashSet<>();

		for (PanelCategoryRoleTypeMapper panelCategoryRoleTypeMapper :
				_serviceTrackerList) {

			if (ArrayUtil.contains(
					panelCategoryRoleTypeMapper.getRoleTypes(), type)) {

				panelCategoryKeys.add(
					panelCategoryRoleTypeMapper.getPanelCategoryKey());
			}
		}

		return panelCategoryKeys.toArray(new String[0]);
	}

	@Override
	public List<PanelCategoryRoleTypeMapper> getPanelCategoryRoleTypeMappers() {
		return _serviceTrackerList.toList();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, PanelCategoryRoleTypeMapper.class);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	private ServiceTrackerList<PanelCategoryRoleTypeMapper> _serviceTrackerList;

}