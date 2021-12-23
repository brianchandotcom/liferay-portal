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

package com.liferay.search.experiences.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.search.experiences.service.SXPElementServiceUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>SXPElementServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.search.experiences.model.SXPElementSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.search.experiences.model.SXPElement</code>, that is translated to a
 * <code>com.liferay.search.experiences.model.SXPElementSoap</code>. Methods that SOAP
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
 * @author Brian Wing Shun Chan
 * @see SXPElementServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class SXPElementServiceSoap {

	public static com.liferay.search.experiences.model.SXPElementSoap
			addSXPElement(
				String[] descriptionMapLanguageIds,
				String[] descriptionMapValues, String elementDefinitionJSON,
				boolean readOnly, String schemaVersion,
				String[] titleMapLanguageIds, String[] titleMapValues, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);

			com.liferay.search.experiences.model.SXPElement returnValue =
				SXPElementServiceUtil.addSXPElement(
					descriptionMap, elementDefinitionJSON, readOnly,
					schemaVersion, titleMap, type, serviceContext);

			return com.liferay.search.experiences.model.SXPElementSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.search.experiences.model.SXPElementSoap
			deleteSXPElement(long sxpElementId)
		throws RemoteException {

		try {
			com.liferay.search.experiences.model.SXPElement returnValue =
				SXPElementServiceUtil.deleteSXPElement(sxpElementId);

			return com.liferay.search.experiences.model.SXPElementSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.search.experiences.model.SXPElementSoap
			getSXPElement(long sxpElementId)
		throws RemoteException {

		try {
			com.liferay.search.experiences.model.SXPElement returnValue =
				SXPElementServiceUtil.getSXPElement(sxpElementId);

			return com.liferay.search.experiences.model.SXPElementSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.search.experiences.model.SXPElementSoap
			updateSXPElement(
				long sxpElementId, String[] descriptionMapLanguageIds,
				String[] descriptionMapValues, String elementDefinitionJSON,
				String schemaVersion, boolean hidden,
				String[] titleMapLanguageIds, String[] titleMapValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);

			com.liferay.search.experiences.model.SXPElement returnValue =
				SXPElementServiceUtil.updateSXPElement(
					sxpElementId, descriptionMap, elementDefinitionJSON,
					schemaVersion, hidden, titleMap, serviceContext);

			return com.liferay.search.experiences.model.SXPElementSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SXPElementServiceSoap.class);

}