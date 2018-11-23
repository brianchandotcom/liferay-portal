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

package com.liferay.data.engine.internal.service;

import com.liferay.data.engine.constants.DataDefinitionConstants;
import com.liferay.data.engine.exception.DataDefinitionException;
import com.liferay.data.engine.internal.security.permission.DataEnginePermissionSupport;
import com.liferay.data.engine.model.DataDefinition;
import com.liferay.data.engine.service.DataDefinitionDeleteRequest;
import com.liferay.data.engine.service.DataDefinitionDeleteResponse;
import com.liferay.data.engine.service.DataDefinitionGetRequest;
import com.liferay.data.engine.service.DataDefinitionGetResponse;
import com.liferay.data.engine.service.DataDefinitionLocalService;
import com.liferay.data.engine.service.DataDefinitionSaveRequest;
import com.liferay.data.engine.service.DataDefinitionSaveResponse;
import com.liferay.data.engine.service.DataDefinitionService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DataDefinitionService.class)
public class DataDefinitionServiceImpl implements DataDefinitionService {

	@Override
	public DataDefinitionDeleteResponse delete(
			DataDefinitionDeleteRequest dataDefinitionDeleteRequest)
		throws DataDefinitionException {

		try {
			long dataDefinitionId =
				dataDefinitionDeleteRequest.getDataDefinitionId();

			_modelResourcePermission.check(
				getPermissionChecker(), dataDefinitionId, ActionKeys.DELETE);

			return dataDefinitionLocalService.delete(
				dataDefinitionDeleteRequest);
		}
		catch (DataDefinitionException dde)
		{
			_log.error(dde, dde);

			throw dde;
		}
		catch (Exception e)
		{
			_log.error(e, e);

			throw new DataDefinitionException(e);
		}
	}

	@Override
	public DataDefinitionGetResponse get(
			DataDefinitionGetRequest dataDefinitionGetRequest)
		throws DataDefinitionException {

		try {
			long dataDefinitionId =
				dataDefinitionGetRequest.getDataDefinitionId();

			_modelResourcePermission.check(
				getPermissionChecker(), dataDefinitionId, ActionKeys.VIEW);

			return dataDefinitionLocalService.get(dataDefinitionGetRequest);
		}
		catch (DataDefinitionException dde)
		{
			_log.error(dde, dde);

			throw dde;
		}
		catch (Exception e)
		{
			_log.error(e, e);

			throw new DataDefinitionException(e);
		}
	}

	@Override
	public DataDefinitionSaveResponse save(
			DataDefinitionSaveRequest dataDefinitionSaveRequest)
		throws DataDefinitionException {

		try {
			long groupId = dataDefinitionSaveRequest.getGroupId();

			DataDefinition dataDefinition =
				dataDefinitionSaveRequest.getDataDefinition();

			long dataDefinitionId = dataDefinition.getDataDefinitionId();

			if (dataDefinitionId == 0) {
				checkCreateDataDefinitionPermission(
					groupId, getPermissionChecker());
			}
			else {
				_modelResourcePermission.check(
					getPermissionChecker(), dataDefinitionId,
					ActionKeys.UPDATE);
			}

			DataDefinitionSaveResponse dataDefinitionSaveResponse =
				dataDefinitionLocalService.save(dataDefinitionSaveRequest);

			return DataDefinitionSaveResponse.Builder.of(
				dataDefinitionSaveResponse.getDataDefinitionId());
		}
		catch (DataDefinitionException dde)
		{
			_log.error(dde, dde);

			throw dde;
		}
		catch (Exception e)
		{
			_log.error(e, e);

			throw new DataDefinitionException(e);
		}
	}

	protected void checkCreateDataDefinitionPermission(
			long groupId, PermissionChecker permissionChecker)
		throws PortalException {

		String resourceName = DataEnginePermissionSupport.RESOURCE_NAME;
		String action = DataDefinitionConstants.ADD_DATA_DEFINITION_ACTION;

		if (!dataEnginePermissionSupport.contains(
				permissionChecker, resourceName, groupId, action)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, resourceName, groupId, action);
		}
	}

	protected PermissionChecker getPermissionChecker()
		throws PrincipalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			throw new PrincipalException("PermissionChecker not initialized");
		}

		return permissionChecker;
	}

	@Reference(
		target = "(model.class.name=com.liferay.data.engine.model.DataDefinition)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<DataDefinition> modelResourcePermission) {

		_modelResourcePermission = modelResourcePermission;
	}

	@Reference
	protected DataDefinitionLocalService dataDefinitionLocalService;

	@Reference
	protected DataEnginePermissionSupport dataEnginePermissionSupport;

	@Reference
	protected Portal portal;

	private static final Log _log = LogFactoryUtil.getLog(
		DataDefinitionServiceImpl.class);

	private ModelResourcePermission<DataDefinition> _modelResourcePermission;

}