/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.user.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.user.service.CommerceUserServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CommerceUserServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceUserServiceHttp
 * @see CommerceUserServiceUtil
 * @generated
 */
@ProviderType
public class CommerceUserServiceSoap {
	public static com.liferay.portal.kernel.model.User getUser(long userId)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.model.User returnValue = CommerceUserServiceUtil.getUser(userId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.User updateActive(
		long userId, boolean active) throws RemoteException {
		try {
			com.liferay.portal.kernel.model.User returnValue = CommerceUserServiceUtil.updateActive(userId,
					active);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.User updatePassword(
		long userId, String password1, String password2, boolean passwordReset)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.model.User returnValue = CommerceUserServiceUtil.updatePassword(userId,
					password1, password2, passwordReset);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.User updatePasswordReset(
		long userId, boolean passwordReset) throws RemoteException {
		try {
			com.liferay.portal.kernel.model.User returnValue = CommerceUserServiceUtil.updatePasswordReset(userId,
					passwordReset);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.User updateReminderQuery(
		long userId, String question, String answer) throws RemoteException {
		try {
			com.liferay.portal.kernel.model.User returnValue = CommerceUserServiceUtil.updateReminderQuery(userId,
					question, answer);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.User updateUser(long userId,
		String screenName, String emailAddress, boolean portrait,
		byte[] portraitBytes, String languageId, String firstName,
		String middleName, String lastName, long prefixId, long suffixId,
		boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
		String jobTitle,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.model.User returnValue = CommerceUserServiceUtil.updateUser(userId,
					screenName, emailAddress, portrait, portraitBytes,
					languageId, firstName, middleName, lastName, prefixId,
					suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
					jobTitle, serviceContext);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void updateUserRoles(long userId, long groupId, long[] roleIds)
		throws RemoteException {
		try {
			CommerceUserServiceUtil.updateUserRoles(userId, groupId, roleIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceUserServiceSoap.class);
}