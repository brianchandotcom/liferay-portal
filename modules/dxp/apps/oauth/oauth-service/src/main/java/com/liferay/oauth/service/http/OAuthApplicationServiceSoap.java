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

package com.liferay.oauth.service.http;

import com.liferay.oauth.service.OAuthApplicationServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>OAuthApplicationServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.oauth.model.OAuthApplicationSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.oauth.model.OAuthApplication</code>, that is translated to a
 * <code>com.liferay.oauth.model.OAuthApplicationSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
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
 * @author Ivica Cardic
 * @see OAuthApplicationServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class OAuthApplicationServiceSoap {

	public static com.liferay.oauth.model.OAuthApplicationSoap
			addOAuthApplication(
				String name, String description, int accessLevel,
				boolean shareableAccessToken, String callbackURI,
				String websiteURL,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.oauth.model.OAuthApplication returnValue =
				OAuthApplicationServiceUtil.addOAuthApplication(
					name, description, accessLevel, shareableAccessToken,
					callbackURI, websiteURL, serviceContext);

			return com.liferay.oauth.model.OAuthApplicationSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteLogo(long oAuthApplicationId)
		throws RemoteException {

		try {
			OAuthApplicationServiceUtil.deleteLogo(oAuthApplicationId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.oauth.model.OAuthApplicationSoap
			deleteOAuthApplication(long oAuthApplicationId)
		throws RemoteException {

		try {
			com.liferay.oauth.model.OAuthApplication returnValue =
				OAuthApplicationServiceUtil.deleteOAuthApplication(
					oAuthApplicationId);

			return com.liferay.oauth.model.OAuthApplicationSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.oauth.model.OAuthApplicationSoap
			updateOAuthApplication(
				long oAuthApplicationId, String name, String description,
				boolean shareableAccessToken, String callbackURI,
				String websiteURL,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.oauth.model.OAuthApplication returnValue =
				OAuthApplicationServiceUtil.updateOAuthApplication(
					oAuthApplicationId, name, description, shareableAccessToken,
					callbackURI, websiteURL, serviceContext);

			return com.liferay.oauth.model.OAuthApplicationSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		OAuthApplicationServiceSoap.class);

}