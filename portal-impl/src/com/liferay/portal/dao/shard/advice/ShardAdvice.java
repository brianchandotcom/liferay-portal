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

package com.liferay.portal.dao.shard.advice;

import com.liferay.portal.dao.shard.ShardDataSourceTargetSource;
import com.liferay.portal.dao.shard.ShardSelector;
import com.liferay.portal.dao.shard.ShardSessionFactoryTargetSource;
import com.liferay.portal.kernel.exception.LoggedExceptionInInitializerError;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.InitialThreadLocal;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Shard;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.ShardLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.sql.DataSource;

/**
 * @author Michael Young
 * @author Alexander Chow
 * @author Shuyang Zhou
 */
public class ShardAdvice {

	public void afterPropertiesSet() {
		if (_shardDataSourceTargetSource == null) {
			_shardDataSourceTargetSource =
				(ShardDataSourceTargetSource)InfrastructureUtil.
					getShardDataSourceTargetSource();
		}

		if (_shardSessionFactoryTargetSource == null) {
			_shardSessionFactoryTargetSource =
				(ShardSessionFactoryTargetSource)InfrastructureUtil.
					getShardSessionFactoryTargetSource();
		}
	}

	public String getCompanyShardName(
		String webId, String virtualHostname, String mx, String shardName) {

		Map<String, String> shardParams = new HashMap<String, String>();

		shardParams.put("webId", webId);
		shardParams.put("mx", mx);

		if (virtualHostname != null) {
			shardParams.put("virtualHostname", virtualHostname);
		}

		shardName = _shardSelector.getShardName(
			ShardSelector.COMPANY_SCOPE, shardName, shardParams);

		return shardName;
	}

	public String getCurrentShardName() {
		Stack<String> stack = _getCompanyServiceStack();

		if (stack.isEmpty()) {
			return PropsValues.SHARD_DEFAULT_NAME;
		}

		return GetterUtil.getString(
			stack.peek(), PropsValues.SHARD_DEFAULT_NAME);
	}

	public DataSource getDataSource() {
		return _shardDataSourceTargetSource.getDataSource();
	}

	public Object getGlobalCall() {
		return _globalCall.get();
	}

	public ShardDataSourceTargetSource getShardDataSourceTargetSource() {
		return _shardDataSourceTargetSource;
	}

	public String getShardName() {
		return _shardName.get();
	}

	public ShardSessionFactoryTargetSource
		getShardSessionFactoryTargetSource() {

		return _shardSessionFactoryTargetSource;
	}

	public String popCompanyService() {
		return _getCompanyServiceStack().pop();
	}

	public void pushCompanyService(long companyId) {
		try {
			Shard shard = ShardLocalServiceUtil.getShard(
				Company.class.getName(), companyId);

			String shardName = shard.getName();

			pushCompanyService(shardName);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void pushCompanyService(String shardName) {
		_getCompanyServiceStack().push(shardName);
	}

	public void setGlobalCall(Object obj) {
		_globalCall.set(obj);
	}

	public void setShardDataSourceTargetSource(
		ShardDataSourceTargetSource shardDataSourceTargetSource) {

		_shardDataSourceTargetSource = shardDataSourceTargetSource;
	}

	public String setShardNameByCompany() throws Throwable {
		Stack<String> companyServiceStack = _getCompanyServiceStack();

		if (companyServiceStack.isEmpty()) {
			long companyId = CompanyThreadLocal.getCompanyId();

			return _setShardNameByCompanyId(companyId);
		}

		String shardName = companyServiceStack.peek();

		_setShardName(shardName);

		return shardName;
	}

	public void setShardSessionFactoryTargetSource(
		ShardSessionFactoryTargetSource shardSessionFactoryTargetSource) {

		_shardSessionFactoryTargetSource = shardSessionFactoryTargetSource;
	}

	private Stack<String> _getCompanyServiceStack() {
		Stack<String> companyServiceStack = _companyServiceStack.get();

		if (companyServiceStack == null) {
			companyServiceStack = new Stack<String>();

			_companyServiceStack.set(companyServiceStack);
		}

		return companyServiceStack;
	}

	private void _setShardName(String shardName) {
		_shardName.set(shardName);
	}

	private String _setShardNameByCompanyId(long companyId)
		throws PortalException {

		String shardName = PropsValues.SHARD_DEFAULT_NAME;

		if (companyId != 0) {
			Shard shard = ShardLocalServiceUtil.getShard(
				Company.class.getName(), companyId);

			shardName = shard.getName();
		}

		_setShardName(shardName);

		return shardName;
	}

	private static final Log _log = LogFactoryUtil.getLog(ShardAdvice.class);

	private static final ThreadLocal<Stack<String>> _companyServiceStack =
		new ThreadLocal<Stack<String>>();
	private static final ThreadLocal<Object> _globalCall =
		new ThreadLocal<Object>();
	private static final ThreadLocal<String> _shardName =
		new InitialThreadLocal<String>(
			ShardAdvice.class + "._shardName", PropsValues.SHARD_DEFAULT_NAME);
	private static final ShardSelector _shardSelector;

	static {
		try {
			Class<?> clazz = Class.forName(PropsValues.SHARD_SELECTOR);

			_shardSelector = (ShardSelector)clazz.newInstance();
		}
		catch (Exception e) {
			throw new LoggedExceptionInInitializerError(e);
		}
	}

	private ShardDataSourceTargetSource _shardDataSourceTargetSource;
	private ShardSessionFactoryTargetSource _shardSessionFactoryTargetSource;

}