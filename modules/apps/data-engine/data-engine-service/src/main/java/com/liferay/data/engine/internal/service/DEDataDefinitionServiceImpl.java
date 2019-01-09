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

import com.liferay.data.engine.constants.DEActionKeys;
import com.liferay.data.engine.exception.DEDataDefinitionException;
import com.liferay.data.engine.internal.executor.DEDataDefinitionCountRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataDefinitionDeleteRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataDefinitionGetRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataDefinitionListRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataDefinitionSaveRequestExecutor;
import com.liferay.data.engine.internal.io.DEDataDefinitionFieldsDeserializerTracker;
import com.liferay.data.engine.internal.security.permission.DEDataEnginePermissionSupport;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.service.DEDataDefinitionCountRequest;
import com.liferay.data.engine.service.DEDataDefinitionCountResponse;
import com.liferay.data.engine.service.DEDataDefinitionDeleteRequest;
import com.liferay.data.engine.service.DEDataDefinitionDeleteResponse;
import com.liferay.data.engine.service.DEDataDefinitionGetRequest;
import com.liferay.data.engine.service.DEDataDefinitionGetResponse;
import com.liferay.data.engine.service.DEDataDefinitionListRequest;
import com.liferay.data.engine.service.DEDataDefinitionListResponse;
import com.liferay.data.engine.service.DEDataDefinitionSaveRequest;
import com.liferay.data.engine.service.DEDataDefinitionSaveResponse;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
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
@Component(immediate = true, service = DEDataDefinitionService.class)
public class DEDataDefinitionServiceImpl implements DEDataDefinitionService {

	@Override
	public DEDataDefinitionCountResponse execute(
		DEDataDefinitionCountRequest deDataDefinitionCountRequest) {

		DEDataDefinitionCountRequestExecutor
			deDataDefinitionCountRequestExecutor =
				getDEDataDefinitionCountRequestExecutor();

		return deDataDefinitionCountRequestExecutor.execute(
			deDataDefinitionCountRequest);
	}

	@Override
	public DEDataDefinitionDeleteResponse execute(
			DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest)
		throws DEDataDefinitionException {

		try {
			long deDataDefinitionId =
				deDataDefinitionDeleteRequest.getDEDataDefinitionId();

			_modelResourcePermission.check(
				getPermissionChecker(), deDataDefinitionId, ActionKeys.DELETE);

			return deDataDefinitionDeleteRequestExecutor.execute(
				deDataDefinitionDeleteRequest);
		}
		catch (DEDataDefinitionException dedde) {
			if (_log.isDebugEnabled()) {
				_log.debug(dedde, dedde);
			}

			throw dedde;
		}
		catch (NoSuchStructureException nsse) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsse, nsse);
			}

			throw new DEDataDefinitionException.NoSuchDataDefinition(
				deDataDefinitionDeleteRequest.getDEDataDefinitionId(), nsse);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			throw new DEDataDefinitionException(e);
		}
	}

	@Override
	public DEDataDefinitionGetResponse execute(
			DEDataDefinitionGetRequest deDataDefinitionGetRequest)
		throws DEDataDefinitionException {

		try {
			long deDataDefinitionId =
				deDataDefinitionGetRequest.getDEDataDefinitionId();

			_modelResourcePermission.check(
				getPermissionChecker(), deDataDefinitionId, ActionKeys.VIEW);

			return deDataDefinitionGetRequestExecutor.execute(
				deDataDefinitionGetRequest);
		}
		catch (DEDataDefinitionException dedde) {
			if (_log.isDebugEnabled()) {
				_log.debug(dedde.getMessage(), dedde);
			}

			throw dedde;
		}
		catch (NoSuchStructureException nsse) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsse.getMessage(), nsse);
			}

			throw new DEDataDefinitionException.NoSuchDataDefinition(
				deDataDefinitionGetRequest.getDEDataDefinitionId(), nsse);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e.getMessage(), e);
			}

			throw new DEDataDefinitionException(e);
		}
	}

	@Override
	public DEDataDefinitionListResponse execute(
			DEDataDefinitionListRequest deDataDefinitionListRequest)
		throws DEDataDefinitionException {

		DEDataDefinitionListRequestExecutor
			deDataDefinitionListRequestExecutor =
				getDEDataDefinitionListRequestExecutor();

		try {
			return deDataDefinitionListRequestExecutor.execute(
				deDataDefinitionListRequest);
		}
		catch (DEDataDefinitionException dedde) {
			if (_log.isDebugEnabled()) {
				_log.debug(dedde, dedde);
			}

			throw dedde;
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			throw new DEDataDefinitionException(e);
		}
	}

	@Override
	public DEDataDefinitionSaveResponse execute(
			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest)
		throws DEDataDefinitionException {

		DEDataDefinition deDataDefinition =
			deDataDefinitionSaveRequest.getDEDataDefinition();

		try {
			long deDataDefinitionId = deDataDefinition.getDEDataDefinitionId();

			if (deDataDefinitionId == 0) {
				checkCreateDataDefinitionPermission(
					deDataDefinitionSaveRequest.getGroupId(),
					getPermissionChecker());
			}
			else {
				_modelResourcePermission.check(
					getPermissionChecker(), deDataDefinitionId,
					ActionKeys.UPDATE);
			}

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				deDataDefinitionSaveRequestExecutor.execute(
					deDataDefinitionSaveRequest);

			return DEDataDefinitionSaveResponse.Builder.of(
				deDataDefinitionSaveResponse.getDEDataDefinitionId());
		}
		catch (DEDataDefinitionException dedde) {
			if (_log.isDebugEnabled()) {
				_log.debug(dedde, dedde);
			}

			throw dedde;
		}
		catch (NoSuchStructureException nsse) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsse, nsse);
			}

			throw new DEDataDefinitionException.NoSuchDataDefinition(
				deDataDefinition.getDEDataDefinitionId(), nsse);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataDefinitionException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			throw new DEDataDefinitionException(e);
		}
	}

	public DEDataDefinitionCountRequestExecutor
		getDEDataDefinitionCountRequestExecutor() {

		if (_deDataDefinitionCountRequestExecutor == null) {
			_deDataDefinitionCountRequestExecutor =
				new DEDataDefinitionCountRequestExecutor(
					ddmStructureService, portal);
		}

		return _deDataDefinitionCountRequestExecutor;
	}

	public DEDataDefinitionListRequestExecutor
		getDEDataDefinitionListRequestExecutor() {

		if (_deDataDefinitionListRequestExecutor == null) {
			_deDataDefinitionListRequestExecutor =
				new DEDataDefinitionListRequestExecutor(
					ddmStructureService,
					deDataDefinitionFieldsDeserializerTracker, portal);
		}

		return _deDataDefinitionListRequestExecutor;
	}

	protected void checkCreateDataDefinitionPermission(
			long groupId, PermissionChecker permissionChecker)
		throws PortalException {

		String resourceName = DEDataEnginePermissionSupport.RESOURCE_NAME;
		String actionId = DEActionKeys.ADD_DATA_DEFINITION_ACTION;

		if (!deDataEnginePermissionSupport.contains(
				permissionChecker, resourceName, groupId, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, resourceName, groupId, actionId);
		}
	}

	protected PermissionChecker getPermissionChecker()
		throws PrincipalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			throw new PrincipalException(
				"Permission checker is not initialized");
		}

		return permissionChecker;
	}

	@Reference(
		target = "(model.class.name=com.liferay.data.engine.model.DEDataDefinition)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<DEDataDefinition> modelResourcePermission) {

		_modelResourcePermission = modelResourcePermission;
	}

	@Reference
	protected DDMStructureService ddmStructureService;

	@Reference
	protected DEDataDefinitionDeleteRequestExecutor
		deDataDefinitionDeleteRequestExecutor;

	@Reference
	protected DEDataDefinitionFieldsDeserializerTracker
		deDataDefinitionFieldsDeserializerTracker;

	@Reference
	protected DEDataDefinitionGetRequestExecutor
		deDataDefinitionGetRequestExecutor;

	@Reference
	protected DEDataDefinitionSaveRequestExecutor
		deDataDefinitionSaveRequestExecutor;

	@Reference
	protected DEDataEnginePermissionSupport deDataEnginePermissionSupport;

	@Reference
	protected Portal portal;

	private static final Log _log = LogFactoryUtil.getLog(
		DEDataDefinitionServiceImpl.class);

	private DEDataDefinitionCountRequestExecutor
		_deDataDefinitionCountRequestExecutor;
	private DEDataDefinitionListRequestExecutor
		_deDataDefinitionListRequestExecutor;
	private ModelResourcePermission<DEDataDefinition> _modelResourcePermission;

}