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

package com.liferay.dynamic.data.mapping.service.http;

import com.liferay.dynamic.data.mapping.service.DDMFormInstanceServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>DDMFormInstanceServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.dynamic.data.mapping.model.DDMFormInstanceSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.dynamic.data.mapping.model.DDMFormInstance</code>, that is translated to a
 * <code>com.liferay.dynamic.data.mapping.model.DDMFormInstanceSoap</code>. Methods that SOAP
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
 * @see DDMFormInstanceServiceHttp
 * @generated
 */
public class DDMFormInstanceServiceSoap {

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceSoap
			addFormInstance(
				long groupId, long ddmStructureId, String[] nameMapLanguageIds,
				String[] nameMapValues, String[] descriptionMapLanguageIds,
				String[] descriptionMapValues,
				com.liferay.dynamic.data.mapping.storage.DDMFormValues
					settingsDDMFormValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.dynamic.data.mapping.model.DDMFormInstance returnValue =
				DDMFormInstanceServiceUtil.addFormInstance(
					groupId, ddmStructureId, nameMap, descriptionMap,
					settingsDDMFormValues, serviceContext);

			return com.liferay.dynamic.data.mapping.model.DDMFormInstanceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceSoap
			addFormInstance(
				long groupId, String[] nameMapLanguageIds,
				String[] nameMapValues, String[] descriptionMapLanguageIds,
				String[] descriptionMapValues,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				com.liferay.dynamic.data.mapping.model.DDMFormLayout
					ddmFormLayout,
				com.liferay.dynamic.data.mapping.storage.DDMFormValues
					settingsDDMFormValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.dynamic.data.mapping.model.DDMFormInstance returnValue =
				DDMFormInstanceServiceUtil.addFormInstance(
					groupId, nameMap, descriptionMap, ddmForm, ddmFormLayout,
					settingsDDMFormValues, serviceContext);

			return com.liferay.dynamic.data.mapping.model.DDMFormInstanceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFormInstance(long ddmFormInstanceId)
		throws RemoteException {

		try {
			DDMFormInstanceServiceUtil.deleteFormInstance(ddmFormInstanceId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceSoap
			fetchFormInstance(long ddmFormInstanceId)
		throws RemoteException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMFormInstance returnValue =
				DDMFormInstanceServiceUtil.fetchFormInstance(ddmFormInstanceId);

			return com.liferay.dynamic.data.mapping.model.DDMFormInstanceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceSoap
			getFormInstance(long ddmFormInstanceId)
		throws RemoteException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMFormInstance returnValue =
				DDMFormInstanceServiceUtil.getFormInstance(ddmFormInstanceId);

			return com.liferay.dynamic.data.mapping.model.DDMFormInstanceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceSoap[]
			getFormInstances(long companyId, long groupId, int start, int end)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMFormInstance>
					returnValue = DDMFormInstanceServiceUtil.getFormInstances(
						companyId, groupId, start, end);

			return com.liferay.dynamic.data.mapping.model.DDMFormInstanceSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFormInstancesCount(long companyId, long groupId)
		throws RemoteException {

		try {
			int returnValue = DDMFormInstanceServiceUtil.getFormInstancesCount(
				companyId, groupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceSoap[]
			search(
				long companyId, long groupId, String keywords, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.DDMFormInstance>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMFormInstance>
					returnValue = DDMFormInstanceServiceUtil.search(
						companyId, groupId, keywords, start, end,
						orderByComparator);

			return com.liferay.dynamic.data.mapping.model.DDMFormInstanceSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceSoap[]
			search(
				long companyId, long groupId, String[] names,
				String[] descriptions, boolean andOperator, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.DDMFormInstance>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMFormInstance>
					returnValue = DDMFormInstanceServiceUtil.search(
						companyId, groupId, names, descriptions, andOperator,
						start, end, orderByComparator);

			return com.liferay.dynamic.data.mapping.model.DDMFormInstanceSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int searchCount(long companyId, long groupId, String keywords)
		throws RemoteException {

		try {
			int returnValue = DDMFormInstanceServiceUtil.searchCount(
				companyId, groupId, keywords);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int searchCount(
			long companyId, long groupId, String[] names, String[] descriptions,
			boolean andOperator)
		throws RemoteException {

		try {
			int returnValue = DDMFormInstanceServiceUtil.searchCount(
				companyId, groupId, names, descriptions, andOperator);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Updates the the record set's settings.
	 *
	 * @param formInstanceId the primary key of the form instance
	 * @param settingsDDMFormValues the record set's settings. For more
	 information see <code>DDMFormValues</code> in the
	 <code>dynamic.data.mapping.api</code> module.
	 * @return the record set
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceSoap
			updateFormInstance(
				long formInstanceId,
				com.liferay.dynamic.data.mapping.storage.DDMFormValues
					settingsDDMFormValues)
		throws RemoteException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMFormInstance returnValue =
				DDMFormInstanceServiceUtil.updateFormInstance(
					formInstanceId, settingsDDMFormValues);

			return com.liferay.dynamic.data.mapping.model.DDMFormInstanceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceSoap
			updateFormInstance(
				long ddmFormInstanceId, String[] nameMapLanguageIds,
				String[] nameMapValues, String[] descriptionMapLanguageIds,
				String[] descriptionMapValues,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				com.liferay.dynamic.data.mapping.model.DDMFormLayout
					ddmFormLayout,
				com.liferay.dynamic.data.mapping.storage.DDMFormValues
					settingsDDMFormValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.dynamic.data.mapping.model.DDMFormInstance returnValue =
				DDMFormInstanceServiceUtil.updateFormInstance(
					ddmFormInstanceId, nameMap, descriptionMap, ddmForm,
					ddmFormLayout, settingsDDMFormValues, serviceContext);

			return com.liferay.dynamic.data.mapping.model.DDMFormInstanceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceServiceSoap.class);

}