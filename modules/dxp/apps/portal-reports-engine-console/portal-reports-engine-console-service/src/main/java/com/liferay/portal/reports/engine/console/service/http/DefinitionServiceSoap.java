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
import com.liferay.portal.reports.engine.console.service.DefinitionServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>DefinitionServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.portal.reports.engine.console.model.DefinitionSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.portal.reports.engine.console.model.Definition</code>, that is translated to a
 * <code>com.liferay.portal.reports.engine.console.model.DefinitionSoap</code>. Methods that SOAP
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
 * @see DefinitionServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class DefinitionServiceSoap {

	public static com.liferay.portal.reports.engine.console.model.DefinitionSoap
			deleteDefinition(long definitionId)
		throws RemoteException {

		try {
			com.liferay.portal.reports.engine.console.model.Definition
				returnValue = DefinitionServiceUtil.deleteDefinition(
					definitionId);

			return com.liferay.portal.reports.engine.console.model.
				DefinitionSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.reports.engine.console.model.DefinitionSoap
			getDefinition(long definitionId)
		throws RemoteException {

		try {
			com.liferay.portal.reports.engine.console.model.Definition
				returnValue = DefinitionServiceUtil.getDefinition(definitionId);

			return com.liferay.portal.reports.engine.console.model.
				DefinitionSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.portal.reports.engine.console.model.DefinitionSoap[]
				getDefinitions(
					long groupId, String definitionName, String description,
					String sourceId, String reportName, boolean andSearch,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.portal.reports.engine.console.model.
							Definition> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.portal.reports.engine.console.model.Definition>
					returnValue = DefinitionServiceUtil.getDefinitions(
						groupId, definitionName, description, sourceId,
						reportName, andSearch, start, end, orderByComparator);

			return com.liferay.portal.reports.engine.console.model.
				DefinitionSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getDefinitionsCount(
			long groupId, String definitionName, String description,
			String sourceId, String reportName, boolean andSearch)
		throws RemoteException {

		try {
			int returnValue = DefinitionServiceUtil.getDefinitionsCount(
				groupId, definitionName, description, sourceId, reportName,
				andSearch);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DefinitionServiceSoap.class);

}