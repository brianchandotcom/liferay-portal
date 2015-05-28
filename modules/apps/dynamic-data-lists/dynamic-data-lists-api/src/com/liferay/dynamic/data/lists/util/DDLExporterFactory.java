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

package com.liferay.dynamic.data.lists.util;

import java.util.Collection;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import com.liferay.osgi.service.tracker.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMapFactory;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDLExporterFactory.class)
public class DDLExporterFactory {

	public Collection<String> getExporterFormats() {
		return _serviceTrackerMap.keySet();
	}
	
	public DDLExporter getDDLExporter(String exportFormat) {
		List<DDLExporter> ddlExporters = _serviceTrackerMap.getService(
			exportFormat);
		
		if (ddlExporters == null) {
			throw new IllegalArgumentException(
				"There is no DDLExporter registered that supports the format " + 
					exportFormat);
		}
		
		return ddlExporters.get(0);
	}
	
	@Activate
	protected void activate(final BundleContext bundleContext) 
		throws InvalidSyntaxException  {
		
		_serviceTrackerMap = ServiceTrackerMapFactory.multiValueMap(
			bundleContext, DDLExporter.class, null,
			new ServiceReferenceMapper<String, DDLExporter>() {

				@Override
				public void map(
					ServiceReference<DDLExporter> serviceReference,
					Emitter<String> emmitter) {
					
					DDLExporter ddlExporter = bundleContext.getService(
						serviceReference);
					
					try {
						emmitter.emit(ddlExporter.getFormat());
					}
					finally {
						bundleContext.ungetService(serviceReference);
					}
				}
			});

		_serviceTrackerMap.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, List<DDLExporter>> _serviceTrackerMap;

}