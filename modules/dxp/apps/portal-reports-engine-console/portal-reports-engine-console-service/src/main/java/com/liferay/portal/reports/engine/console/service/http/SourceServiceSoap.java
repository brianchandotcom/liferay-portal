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

package com.liferay.portal.reports.engine.console.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.reports.engine.console.service.SourceServiceUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>SourceServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.portal.reports.engine.console.model.SourceSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.portal.reports.engine.console.model.Source</code>, that is translated to a
 * <code>com.liferay.portal.reports.engine.console.model.SourceSoap</code>. Methods that SOAP
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
 * @see SourceServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class SourceServiceSoap {

	public static com.liferay.portal.reports.engine.console.model.SourceSoap
			addSource(
				long groupId, String[] nameMapLanguageIds,
				String[] nameMapValues, String driverClassName,
				String driverUrl, String driverUserName, String driverPassword,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);

			com.liferay.portal.reports.engine.console.model.Source returnValue =
				SourceServiceUtil.addSource(
					groupId, nameMap, driverClassName, driverUrl,
					driverUserName, driverPassword, serviceContext);

			return com.liferay.portal.reports.engine.console.model.SourceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.reports.engine.console.model.SourceSoap
			deleteSource(long sourceId)
		throws RemoteException {

		try {
			com.liferay.portal.reports.engine.console.model.Source returnValue =
				SourceServiceUtil.deleteSource(sourceId);

			return com.liferay.portal.reports.engine.console.model.SourceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.reports.engine.console.model.SourceSoap
			getSource(long sourceId)
		throws RemoteException {

		try {
			com.liferay.portal.reports.engine.console.model.Source returnValue =
				SourceServiceUtil.getSource(sourceId);

			return com.liferay.portal.reports.engine.console.model.SourceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.reports.engine.console.model.SourceSoap[]
			getSources(
				long groupId, String name, String driverUrl, boolean andSearch,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.reports.engine.console.model.Source>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.portal.reports.engine.console.model.Source>
					returnValue = SourceServiceUtil.getSources(
						groupId, name, driverUrl, andSearch, start, end,
						orderByComparator);

			return com.liferay.portal.reports.engine.console.model.SourceSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getSourcesCount(
			long groupId, String name, String driverUrl, boolean andSearch)
		throws RemoteException {

		try {
			int returnValue = SourceServiceUtil.getSourcesCount(
				groupId, name, driverUrl, andSearch);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.reports.engine.console.model.SourceSoap
			updateSource(
				long sourceId, String[] nameMapLanguageIds,
				String[] nameMapValues, String driverClassName,
				String driverUrl, String driverUserName, String driverPassword,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);

			com.liferay.portal.reports.engine.console.model.Source returnValue =
				SourceServiceUtil.updateSource(
					sourceId, nameMap, driverClassName, driverUrl,
					driverUserName, driverPassword, serviceContext);

			return com.liferay.portal.reports.engine.console.model.SourceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SourceServiceSoap.class);

}