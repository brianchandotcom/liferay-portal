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

package com.liferay.bulk.rest.internal.helper;

import com.liferay.bulk.rest.dto.v1_0.DocumentSelection;
import com.liferay.bulk.rest.internal.util.ParameterMapUtil;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.bulk.selection.BulkSelectionFactoryRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 */
@Component(service = {})
public class BulkSelectionHelper {

	public BulkSelection<?> getBulkSelection(
			DocumentSelection documentSelection)
		throws PortalException {

		ClassName className = _classNameLocalService.getClassName(
			FileEntry.class.getName());

		BulkSelectionFactory<Object> bulkSelectionFactory =
			_bulkSelectionFactoryRegistry.getBulkSelectionFactory(
				className.getClassNameId());

		return bulkSelectionFactory.create(
			ParameterMapUtil.getParameterMap(
				documentSelection.getDocumentIds(),
				documentSelection.getSelectionScope()));
	}

	@Reference
	private BulkSelectionFactoryRegistry _bulkSelectionFactoryRegistry;

	@Reference
	private ClassNameLocalService _classNameLocalService;

}