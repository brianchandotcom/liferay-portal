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

package com.liferay.portal.search.tuning.blueprints.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.search.tuning.blueprints.service.BlueprintServiceUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>BlueprintServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.portal.search.tuning.blueprints.model.BlueprintSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.portal.search.tuning.blueprints.model.Blueprint</code>, that is translated to a
 * <code>com.liferay.portal.search.tuning.blueprints.model.BlueprintSoap</code>. Methods that SOAP
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
 * @see BlueprintServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class BlueprintServiceSoap {

	public static
		com.liferay.portal.search.tuning.blueprints.model.BlueprintSoap
				addCompanyBlueprint(
					String[] titleMapLanguageIds, String[] titleMapValues,
					String[] descriptionMapLanguageIds,
					String[] descriptionMapValues, String configuration,
					String selectedElements, int type,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.portal.search.tuning.blueprints.model.Blueprint
				returnValue = BlueprintServiceUtil.addCompanyBlueprint(
					titleMap, descriptionMap, configuration, selectedElements,
					type, serviceContext);

			return com.liferay.portal.search.tuning.blueprints.model.
				BlueprintSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.portal.search.tuning.blueprints.model.BlueprintSoap
				addGroupBlueprint(
					String[] titleMapLanguageIds, String[] titleMapValues,
					String[] descriptionMapLanguageIds,
					String[] descriptionMapValues, String configuration,
					String selectedElements, int type,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.portal.search.tuning.blueprints.model.Blueprint
				returnValue = BlueprintServiceUtil.addGroupBlueprint(
					titleMap, descriptionMap, configuration, selectedElements,
					type, serviceContext);

			return com.liferay.portal.search.tuning.blueprints.model.
				BlueprintSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.portal.search.tuning.blueprints.model.BlueprintSoap
				deleteBlueprint(long blueprintId)
			throws RemoteException {

		try {
			com.liferay.portal.search.tuning.blueprints.model.Blueprint
				returnValue = BlueprintServiceUtil.deleteBlueprint(blueprintId);

			return com.liferay.portal.search.tuning.blueprints.model.
				BlueprintSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.portal.search.tuning.blueprints.model.BlueprintSoap
				getBlueprint(long blueprintId)
			throws RemoteException {

		try {
			com.liferay.portal.search.tuning.blueprints.model.Blueprint
				returnValue = BlueprintServiceUtil.getBlueprint(blueprintId);

			return com.liferay.portal.search.tuning.blueprints.model.
				BlueprintSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.portal.search.tuning.blueprints.model.BlueprintSoap[]
				getGroupBlueprints(long companyId, int type, int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.portal.search.tuning.blueprints.model.Blueprint>
					returnValue = BlueprintServiceUtil.getGroupBlueprints(
						companyId, type, start, end);

			return com.liferay.portal.search.tuning.blueprints.model.
				BlueprintSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.portal.search.tuning.blueprints.model.BlueprintSoap[]
				getGroupBlueprints(
					long companyId, int status, int type, int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.portal.search.tuning.blueprints.model.Blueprint>
					returnValue = BlueprintServiceUtil.getGroupBlueprints(
						companyId, status, type, start, end);

			return com.liferay.portal.search.tuning.blueprints.model.
				BlueprintSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.portal.search.tuning.blueprints.model.BlueprintSoap[]
				getGroupBlueprints(
					long companyId, int status, int type, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.portal.search.tuning.blueprints.model.
							Blueprint> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.portal.search.tuning.blueprints.model.Blueprint>
					returnValue = BlueprintServiceUtil.getGroupBlueprints(
						companyId, status, type, start, end, orderByComparator);

			return com.liferay.portal.search.tuning.blueprints.model.
				BlueprintSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.portal.search.tuning.blueprints.model.BlueprintSoap[]
				getGroupBlueprints(
					long companyId, int type, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.portal.search.tuning.blueprints.model.
							Blueprint> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.portal.search.tuning.blueprints.model.Blueprint>
					returnValue = BlueprintServiceUtil.getGroupBlueprints(
						companyId, type, start, end, orderByComparator);

			return com.liferay.portal.search.tuning.blueprints.model.
				BlueprintSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getGroupBlueprintsCount(long companyId, int type)
		throws RemoteException {

		try {
			int returnValue = BlueprintServiceUtil.getGroupBlueprintsCount(
				companyId, type);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getGroupBlueprintsCount(
			long companyId, int status, int type)
		throws RemoteException {

		try {
			int returnValue = BlueprintServiceUtil.getGroupBlueprintsCount(
				companyId, status, type);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.portal.search.tuning.blueprints.model.BlueprintSoap
				updateBlueprint(
					long blueprintId, String[] titleMapLanguageIds,
					String[] titleMapValues, String[] descriptionMapLanguageIds,
					String[] descriptionMapValues, String configuration,
					String selectedElements,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.portal.search.tuning.blueprints.model.Blueprint
				returnValue = BlueprintServiceUtil.updateBlueprint(
					blueprintId, titleMap, descriptionMap, configuration,
					selectedElements, serviceContext);

			return com.liferay.portal.search.tuning.blueprints.model.
				BlueprintSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(BlueprintServiceSoap.class);

}