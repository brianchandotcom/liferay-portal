/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PwdEncryptorException;
import com.liferay.portal.kernel.model.RememberMeToken;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptorUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.service.base.RememberMeTokenLocalServiceBaseImpl;

import java.util.Date;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Brian Wing Shun Chan
 * @author Manuele Castro
 * @author Pedro Silvestre
 */
public class RememberMeTokenLocalServiceImpl
	extends RememberMeTokenLocalServiceBaseImpl {

	@Override
	public RememberMeToken addRememberMeToken(
			long companyId, long userId, Date expirationDate,
			Consumer<String> tokenConsumer)
		throws PwdEncryptorException {

		RememberMeToken rememberMeToken = rememberMeTokenPersistence.create(
			counterLocalService.increment());

		rememberMeToken.setCompanyId(companyId);
		rememberMeToken.setUserId(userId);
		rememberMeToken.setExpirationDate(expirationDate);

		String generate = PortalUUIDUtil.generate();

		rememberMeToken.setValue(PasswordEncryptorUtil.encrypt(generate));

		rememberMeToken = rememberMeTokenPersistence.update(rememberMeToken);

		tokenConsumer.accept(generate);

		return rememberMeToken;
	}

	@Override
	public void deleteExpiredRememberMeTokens(long userId) {
		for (RememberMeToken rememberMeToken :
				rememberMeTokenPersistence.findByUserId(userId)) {

			if (rememberMeToken.isExpired()) {
				deleteRememberMeToken(rememberMeToken);
			}
		}
	}

	@Override
	public RememberMeToken fetchRememberMeToken(
			long rememberMeTokenId, String value)
		throws PwdEncryptorException {

		RememberMeToken rememberMeToken = fetchRememberMeToken(
			rememberMeTokenId);

		if ((rememberMeToken == null) ||
			!Objects.equals(
				rememberMeToken.getValue(),
				PasswordEncryptorUtil.encrypt(
					value, rememberMeToken.getValue()))) {

			return null;
		}

		return rememberMeToken;
	}

	@BeanReference(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}