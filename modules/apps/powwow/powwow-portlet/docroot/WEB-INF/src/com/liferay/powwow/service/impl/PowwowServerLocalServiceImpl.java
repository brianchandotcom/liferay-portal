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

package com.liferay.powwow.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.powwow.model.PowwowServer;
import com.liferay.powwow.provider.PowwowServiceProviderUtil;
import com.liferay.powwow.service.base.PowwowServerLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Shinn Lok
 */
public class PowwowServerLocalServiceImpl
	extends PowwowServerLocalServiceBaseImpl {

	@Override
	public PowwowServer addPowwowServer(
			long userId, String name, String providerType, String url,
			String apiKey, String secret, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		long powwowServerId = counterLocalService.increment();

		PowwowServer powwowServer = powwowServerPersistence.create(
			powwowServerId);

		powwowServer.setCompanyId(user.getCompanyId());
		powwowServer.setUserId(user.getUserId());
		powwowServer.setUserName(user.getFullName());
		powwowServer.setCreateDate(serviceContext.getCreateDate(now));
		powwowServer.setModifiedDate(serviceContext.getModifiedDate(now));
		powwowServer.setName(name);
		powwowServer.setProviderType(providerType);
		powwowServer.setUrl(formatURL(url));
		powwowServer.setApiKey(apiKey);
		powwowServer.setSecret(secret);
		powwowServer.setActive(
			PowwowServiceProviderUtil.isServerActive(powwowServer));

		powwowServerPersistence.update(powwowServer);

		return powwowServer;
	}

	@Override
	public void checkPowwowServers() {
		List<PowwowServer> powwowServers = powwowServerPersistence.findAll();

		for (PowwowServer powwowServer : powwowServers) {
			powwowServer.setActive(
				PowwowServiceProviderUtil.isServerActive(powwowServer));

			powwowServerPersistence.update(powwowServer);
		}
	}

	@Override
	public PowwowServer deletePowwowServer(long powwowServerId)
		throws PortalException {

		PowwowServer powwowServer = powwowServerPersistence.findByPrimaryKey(
			powwowServerId);

		return deletePowwowServer(powwowServer);
	}

	@Override
	public PowwowServer deletePowwowServer(PowwowServer powwowServer) {
		powwowServerPersistence.remove(powwowServer);

		return powwowServer;
	}

	@Override
	public List<PowwowServer> getPowwowServers(
		int start, int end, OrderByComparator obc) {

		return powwowServerPersistence.findAll(start, end, obc);
	}

	@Override
	public List<PowwowServer> getPowwowServers(
		String providerType, boolean active) {

		return powwowServerPersistence.findByPT_A(providerType, active);
	}

	@Override
	public int getPowwowServersCount() {
		return powwowServerPersistence.countAll();
	}

	@Override
	public int getPowwowServersCount(String providerType, boolean active) {
		return powwowServerPersistence.countByPT_A(providerType, active);
	}

	@Override
	public PowwowServer updatePowwowServer(
			long powwowServerId, String name, String providerType, String url,
			String apiKey, String secret, ServiceContext serviceContext)
		throws PortalException {

		PowwowServer powwowServer = powwowServerPersistence.findByPrimaryKey(
			powwowServerId);

		powwowServer.setModifiedDate(serviceContext.getModifiedDate(null));
		powwowServer.setName(name);
		powwowServer.setProviderType(providerType);
		powwowServer.setUrl(formatURL(url));
		powwowServer.setApiKey(apiKey);
		powwowServer.setSecret(secret);
		powwowServer.setActive(
			PowwowServiceProviderUtil.isServerActive(powwowServer));

		return powwowServerPersistence.update(powwowServer);
	}

	protected String formatURL(String url) {
		if (url.equals(StringPool.BLANK)) {
			return StringPool.BLANK;
		}

		if (!url.endsWith(StringPool.SLASH)) {
			url += StringPool.SLASH;
		}

		return url;
	}

}