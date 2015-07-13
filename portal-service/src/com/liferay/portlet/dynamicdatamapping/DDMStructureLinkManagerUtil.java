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

package com.liferay.portlet.dynamicdatamapping;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.List;

/**
 * @author Rafael Praxedes
 */
public class DDMStructureLinkManagerUtil {

	public static List<DDMStructureLink> getClassNameStructureLinks(
		long classNameId) {

		DDMStructureLinkManager ddmStructureLinkManager =
			_getDDMStructureLinkManager();

		return ddmStructureLinkManager.getClassNameStructureLinks(classNameId);
	}

	public static List<DDMStructureLink> getStructureLinks(
		long classNameId, long classPK) {

		DDMStructureLinkManager ddmStructureLinkManager =
			_getDDMStructureLinkManager();

		return ddmStructureLinkManager.getStructureLinks(classNameId, classPK);
	}

	private static DDMStructureLinkManager _getDDMStructureLinkManager() {
		DDMStructureLinkManager ddmStructureLinkManager =
			_instance._serviceTracker.getService();

		if (ddmStructureLinkManager == null) {
			return _dummyDDMStructureLinkManagerImpl;
		}

		return ddmStructureLinkManager;
	}

	private DDMStructureLinkManagerUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(DDMStructureLinkManager.class);

		_serviceTracker.open();
	}

	private static final DDMStructureLinkManagerUtil _instance =
		new DDMStructureLinkManagerUtil();

	private static final DummyDDMStructureLinkManagerImpl
		_dummyDDMStructureLinkManagerImpl =
			new DummyDDMStructureLinkManagerImpl();

	private final
		ServiceTracker<DDMStructureLinkManager, DDMStructureLinkManager>
			_serviceTracker;

}