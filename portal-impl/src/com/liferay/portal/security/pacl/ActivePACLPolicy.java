/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.pacl;

import com.liferay.portal.security.pacl.checker.BaseChecker;
import com.liferay.portal.security.pacl.checker.FileChecker;
import com.liferay.portal.security.pacl.checker.HookChecker;
import com.liferay.portal.security.pacl.checker.RuntimeChecker;
import com.liferay.portal.security.pacl.checker.SQLChecker;
import com.liferay.portal.security.pacl.checker.ServiceChecker;
import com.liferay.portal.security.pacl.checker.SocketChecker;
import com.liferay.portal.security.pacl.permission.PortalHookPermission;
import com.liferay.portal.security.pacl.permission.PortalServicePermission;

import java.io.FilePermission;

import java.net.SocketPermission;

import java.security.Permission;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public class ActivePACLPolicy extends BasePACLPolicy {

	public ActivePACLPolicy(
		String servletContextName, ClassLoader classLoader,
		Properties properties) {

		super(servletContextName, classLoader, properties);

		// We can probably instantiate these from a props list?

		_fileChecker = new FileChecker(this);
		_hookChecker = new HookChecker(this);
		_runtimeChecker = new RuntimeChecker(this);
		_serviceChecker = new ServiceChecker(this);
		_socketChecker = new SocketChecker(this);
		_sqlChecker = new SQLChecker(this);

		//_checkerMap.put(AllPermission.class.getName(), _allChecker);
		//_checkerMap.put(AuthPermission.class.getName(), _allChecker);
		//_checkerMap.put(AWTPermission.class.getName(), _allChecker);
		//_checkerMap.put(DelegationPermission.class.getName(), _allChecker);
		_checkerMap.put(FilePermission.class.getName(), _fileChecker);
		_checkerMap.put(PortalHookPermission.class.getName(), _hookChecker);
		//_checkerMap.put(LoggingPermission.class.getName(), _allChecker);
		//_checkerMap.put(ManagementPermission.class.getName(), _allChecker);
		//_checkerMap.put(MBeanPermission.class.getName(), _allChecker);
		//_checkerMap.put(MBeanServerPermission.class.getName(), _allChecker);
		//_checkerMap.put(MBeanTrustPermission.class.getName(), _allChecker);
		//_checkerMap.put(NetPermission.class.getName(), _allChecker);
		_checkerMap.put(
			PortalServicePermission.class.getName(), _serviceChecker);
		//_checkerMap.put(PortalSQLPermission.class.getName(), _sqlChecker);
		//_checkerMap.put(PrivateCredentialPermission.class.getName(), _allChecker);
		//_checkerMap.put(PropertyPermission.class.getName(), _allChecker);
		//_checkerMap.put(ReflectPermission.class.getName(), _allChecker);
		_checkerMap.put(RuntimePermission.class.getName(), _runtimeChecker);
		//_checkerMap.put(SecurityPermission.class.getName(), _allChecker);
		//_checkerMap.put(SerializablePermission.class.getName(), _allChecker);
		//_checkerMap.put(ServicePermission.class.getName(), _allChecker);
		//_checkerMap.put(SQLPermission.class.getName(), _allChecker);
		_checkerMap.put(SocketPermission.class.getName(), _socketChecker);
		//_checkerMap.put(SSLPermission.class.getName(), _allChecker);
		//_checkerMap.put(SubjectDelegationPermission.class.getName(), _allChecker);
		//_checkerMap.put(UnresolvedPermission.class.getName(), _allChecker);
	}

	public void checkPermission(Permission permission) {
		BaseChecker checker = getChecker(permission.getClass().getName());

		if (checker != null) {
			checker.checkPermission(permission);
		}
	}

	public BaseChecker getChecker(String permissionClassName) {
		return _checkerMap.get(permissionClassName);
	}

	public boolean hasSQL(String sql) {
		return _sqlChecker.hasSQL(sql);
	}

	public boolean isActive() {
		return true;
	}

	private Map<String, BaseChecker> _checkerMap =
		new HashMap<String, BaseChecker>();
	private FileChecker _fileChecker;
	private HookChecker _hookChecker;
	private RuntimeChecker _runtimeChecker;
	private ServiceChecker _serviceChecker;
	private SocketChecker _socketChecker;
	private SQLChecker _sqlChecker;

}