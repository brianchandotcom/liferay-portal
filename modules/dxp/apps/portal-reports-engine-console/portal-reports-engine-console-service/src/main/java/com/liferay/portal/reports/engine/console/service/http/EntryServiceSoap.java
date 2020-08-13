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
import com.liferay.portal.reports.engine.console.service.EntryServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>EntryServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.portal.reports.engine.console.model.EntrySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.portal.reports.engine.console.model.Entry</code>, that is translated to a
 * <code>com.liferay.portal.reports.engine.console.model.EntrySoap</code>. Methods that SOAP
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
 * @see EntryServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class EntryServiceSoap {

	public static com.liferay.portal.reports.engine.console.model.EntrySoap
			addEntry(
				long groupId, long definitionId, String format,
				boolean schedulerRequest, java.util.Date startDate,
				java.util.Date endDate, boolean repeating, String recurrence,
				String emailNotifications, String emailDelivery,
				String portletId, String pageURL, String reportName,
				String reportParameters,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.reports.engine.console.model.Entry returnValue =
				EntryServiceUtil.addEntry(
					groupId, definitionId, format, schedulerRequest, startDate,
					endDate, repeating, recurrence, emailNotifications,
					emailDelivery, portletId, pageURL, reportName,
					reportParameters, serviceContext);

			return com.liferay.portal.reports.engine.console.model.EntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteAttachment(
			long companyId, long entryId, String fileName)
		throws RemoteException {

		try {
			EntryServiceUtil.deleteAttachment(companyId, entryId, fileName);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.reports.engine.console.model.EntrySoap
			deleteEntry(long entryId)
		throws RemoteException {

		try {
			com.liferay.portal.reports.engine.console.model.Entry returnValue =
				EntryServiceUtil.deleteEntry(entryId);

			return com.liferay.portal.reports.engine.console.model.EntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.reports.engine.console.model.EntrySoap[]
			getEntries(
				long groupId, String definitionName, String userName,
				java.util.Date createDateGT, java.util.Date createDateLT,
				boolean andSearch, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.reports.engine.console.model.Entry>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.portal.reports.engine.console.model.Entry>
					returnValue = EntryServiceUtil.getEntries(
						groupId, definitionName, userName, createDateGT,
						createDateLT, andSearch, start, end, orderByComparator);

			return com.liferay.portal.reports.engine.console.model.EntrySoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getEntriesCount(
			long groupId, String definitionName, String userName,
			java.util.Date createDateGT, java.util.Date createDateLT,
			boolean andSearch)
		throws RemoteException {

		try {
			int returnValue = EntryServiceUtil.getEntriesCount(
				groupId, definitionName, userName, createDateGT, createDateLT,
				andSearch);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void sendEmails(
			long entryId, String fileName, String[] emailAddresses,
			boolean notification)
		throws RemoteException {

		try {
			EntryServiceUtil.sendEmails(
				entryId, fileName, emailAddresses, notification);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void unscheduleEntry(long entryId) throws RemoteException {
		try {
			EntryServiceUtil.unscheduleEntry(entryId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(EntryServiceSoap.class);

}