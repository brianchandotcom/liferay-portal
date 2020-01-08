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

package com.liferay.change.tracking.internal;

import com.liferay.change.tracking.exception.CTEventException;
import com.liferay.change.tracking.listener.CTEventListener;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = CTServiceRegistry.class)
public class CTServiceRegistry {

	public CTService<?> getCTService(long classNameId) {
		return _serviceTrackerMap.getService(classNameId);
	}

	public Collection<CTTableMapperHelper> getCTTableMapperHelpers() {
		Map<String, CTTableMapperHelper> ctMappingTableHelpers =
			new HashMap<>();

		for (CTService<?> ctService : _serviceTrackerMap.values()) {
			CTPersistence<?> ctPersistence = ctService.getCTPersistence();

			List<String> mappingTableNames =
				ctPersistence.getMappingTableNames();

			if (mappingTableNames.isEmpty()) {
				continue;
			}

			Set<String> primaryKeyNames = ctPersistence.getCTColumnNames(
				CTColumnResolutionType.PK);

			if (primaryKeyNames.size() != 1) {
				throw new IllegalArgumentException(
					StringBundler.concat(
						"{tableName=", ctPersistence.getTableName(),
						", primaryKeyNames=", primaryKeyNames, "}"));
			}

			Iterator<String> iterator = primaryKeyNames.iterator();

			String primaryKeyName = iterator.next();

			for (String mappingTableName : mappingTableNames) {
				ctMappingTableHelpers.compute(
					mappingTableName,
					(key, ctTableMapperHelper) -> {
						if (ctTableMapperHelper == null) {
							return new CTTableMapperHelper(
								ctService, mappingTableName, primaryKeyName);
						}

						ctTableMapperHelper.setRightColumnName(primaryKeyName);

						return ctTableMapperHelper;
					});
			}
		}

		return ctMappingTableHelpers.values();
	}

	public void onAfterCopy(
		CTCollection sourceCTCollection, CTCollection targetCTCollection) {

		for (CTEventListener ctEventListener : _serviceTrackerList) {
			try {
				ctEventListener.onAfterCopy(
					sourceCTCollection, targetCTCollection);
			}
			catch (CTEventException ctEventException) {
				_log.error(
					StringBundler.concat(
						"On after copy callback failure for change tracking ",
						"collection ", sourceCTCollection, " by ",
						ctEventListener),
					ctEventException);
			}
		}
	}

	public void onAfterPublish(long ctCollectionId) {
		for (CTEventListener ctEventListener : _serviceTrackerList) {
			try {
				ctEventListener.onAfterPublish(ctCollectionId);
			}
			catch (CTEventException ctEventException) {
				_log.error(
					StringBundler.concat(
						"On after publish callback failure for change ",
						"tracking collection ", ctCollectionId, " by ",
						ctEventListener),
					ctEventException);
			}
		}
	}

	public void onBeforeRemove(long ctCollectionId) {
		for (CTEventListener ctEventListener : _serviceTrackerList) {
			try {
				ctEventListener.onBeforeRemove(ctCollectionId);
			}
			catch (CTEventException ctEventException) {
				_log.error(
					StringBundler.concat(
						"On before remove callback failure for change ",
						"tracking collection ", ctCollectionId, " by ",
						ctEventListener),
					ctEventException);
			}
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, CTEventListener.class);

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CTService.class, null,
			(serviceReference, emitter) -> {
				CTService<?> ctService = bundleContext.getService(
					serviceReference);

				emitter.emit(
					_classNameLocalService.getClassNameId(
						ctService.getModelClass()));
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTServiceRegistry.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private ServiceTrackerList<CTEventListener, CTEventListener>
		_serviceTrackerList;
	private ServiceTrackerMap<Long, CTService> _serviceTrackerMap;

}