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

package com.liferay.portal.security.sso.openid.connect.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.openid.connect.internal.user.info.handler.spi.DefaultOpenIdConnectUserMapperProcessor;
import com.liferay.portal.security.sso.openid.connect.internal.user.info.handler.spi.OpenIdConnectAddressUserMapperProcessor;
import com.liferay.portal.security.sso.openid.connect.internal.user.info.handler.spi.OpenIdConnectPhoneUserMapperProcessor;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = OpenIdConnectUserInfoProcessor.class)
public class OpenIdConnectUserInfoProcessor {

	public long processUserInfo(
			long companyId, String issuer, ServiceContext serviceContext,
			String userInfoJSON, String userInfoMapperJSON)
		throws PortalException {

		long userId = 0;

		try {

			// TODO: Service tracker to find a suitable custom
			// UserMapperProcessor(MUST), once which becomes SPI.

			userId =
				_defaultOpenIdConnectUserMapperProcessor.
					getUserIdByEmailAddress(
						companyId, userInfoJSON, userInfoMapperJSON);

			if (userId > 0) {
				return userId;
			}

			// TODO: Remove propertyRoleIds from signature once LXC migrates to
			//  use UserInfoMapper Configuration.

			userId = _defaultOpenIdConnectUserMapperProcessor.generateUser(
				companyId, _getRoleIds(companyId, issuer), serviceContext,
				userInfoJSON, userInfoMapperJSON);
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}

		try {

			// TODO: Service tracker to find a list of suitable custom
			// UserOptionalMapperProcessors(OPTIONAL), once which become SPI.

			_openIdConnectAddressUserMapperProcessor.process(
				userId, serviceContext, userInfoJSON, userInfoMapperJSON);

			_openIdConnectPhoneUserMapperProcessor.process(
				userId, serviceContext, userInfoJSON, userInfoMapperJSON);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to process optional user mapper" + exception);
			}
		}

		return userId;
	}

	private long[] _getRoleIds(long companyId, String issuer) {
		if (Validator.isNull(issuer) ||
			!Objects.equals(
				issuer,
				_props.get(
					"open.id.connect.user.info.processor.impl.issuer"))) {

			return null;
		}

		String roleName = _props.get(
			"open.id.connect.user.info.processor.impl.regular.role");

		if (Validator.isNull(roleName)) {
			return null;
		}

		Role role = _roleLocalService.fetchRole(companyId, roleName);

		if (role == null) {
			return null;
		}

		if (role.getType() == RoleConstants.TYPE_REGULAR) {
			return new long[] {role.getRoleId()};
		}

		if (_log.isInfoEnabled()) {
			_log.info("Role " + roleName + " is not a regular role");
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectUserInfoProcessor.class);

	@Reference
	private DefaultOpenIdConnectUserMapperProcessor
		_defaultOpenIdConnectUserMapperProcessor;

	@Reference
	private OpenIdConnectAddressUserMapperProcessor
		_openIdConnectAddressUserMapperProcessor;

	@Reference
	private OpenIdConnectPhoneUserMapperProcessor
		_openIdConnectPhoneUserMapperProcessor;

	@Reference
	private Props _props;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}