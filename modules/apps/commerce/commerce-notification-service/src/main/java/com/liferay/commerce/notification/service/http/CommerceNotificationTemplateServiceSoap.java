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

package com.liferay.commerce.notification.service.http;

import com.liferay.commerce.notification.service.CommerceNotificationTemplateServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>CommerceNotificationTemplateServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.notification.model.CommerceNotificationTemplateSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.notification.model.CommerceNotificationTemplate</code>, that is translated to a
 * <code>com.liferay.commerce.notification.model.CommerceNotificationTemplateSoap</code>. Methods that SOAP
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
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceNotificationTemplateServiceSoap {

	public static
		com.liferay.commerce.notification.model.CommerceNotificationTemplateSoap
				addCommerceNotificationTemplate(
					long groupId, String name, String description, String from,
					String[] fromNameMapLanguageIds, String[] fromNameMapValues,
					String to, String cc, String bcc, String type,
					boolean enabled, String[] subjectMapLanguageIds,
					String[] subjectMapValues, String[] bodyMapLanguageIds,
					String[] bodyMapValues,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			Map<Locale, String> fromNameMap =
				LocalizationUtil.getLocalizationMap(
					fromNameMapLanguageIds, fromNameMapValues);
			Map<Locale, String> subjectMap =
				LocalizationUtil.getLocalizationMap(
					subjectMapLanguageIds, subjectMapValues);
			Map<Locale, String> bodyMap = LocalizationUtil.getLocalizationMap(
				bodyMapLanguageIds, bodyMapValues);

			com.liferay.commerce.notification.model.CommerceNotificationTemplate
				returnValue =
					CommerceNotificationTemplateServiceUtil.
						addCommerceNotificationTemplate(
							groupId, name, description, from, fromNameMap, to,
							cc, bcc, type, enabled, subjectMap, bodyMap,
							serviceContext);

			return com.liferay.commerce.notification.model.
				CommerceNotificationTemplateSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static
		com.liferay.commerce.notification.model.CommerceNotificationTemplateSoap
				addCommerceNotificationTemplate(
					String name, String description, String from,
					String[] fromNameMapLanguageIds, String[] fromNameMapValues,
					String to, String cc, String bcc, String type,
					boolean enabled, String[] subjectMapLanguageIds,
					String[] subjectMapValues, String[] bodyMapLanguageIds,
					String[] bodyMapValues,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			Map<Locale, String> fromNameMap =
				LocalizationUtil.getLocalizationMap(
					fromNameMapLanguageIds, fromNameMapValues);
			Map<Locale, String> subjectMap =
				LocalizationUtil.getLocalizationMap(
					subjectMapLanguageIds, subjectMapValues);
			Map<Locale, String> bodyMap = LocalizationUtil.getLocalizationMap(
				bodyMapLanguageIds, bodyMapValues);

			com.liferay.commerce.notification.model.CommerceNotificationTemplate
				returnValue =
					CommerceNotificationTemplateServiceUtil.
						addCommerceNotificationTemplate(
							name, description, from, fromNameMap, to, cc, bcc,
							type, enabled, subjectMap, bodyMap, serviceContext);

			return com.liferay.commerce.notification.model.
				CommerceNotificationTemplateSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceNotificationTemplate(
			long commerceNotificationTemplateId)
		throws RemoteException {

		try {
			CommerceNotificationTemplateServiceUtil.
				deleteCommerceNotificationTemplate(
					commerceNotificationTemplateId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.notification.model.CommerceNotificationTemplateSoap
				getCommerceNotificationTemplate(
					long commerceNotificationTemplateId)
			throws RemoteException {

		try {
			com.liferay.commerce.notification.model.CommerceNotificationTemplate
				returnValue =
					CommerceNotificationTemplateServiceUtil.
						getCommerceNotificationTemplate(
							commerceNotificationTemplateId);

			return com.liferay.commerce.notification.model.
				CommerceNotificationTemplateSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.notification.model.
			CommerceNotificationTemplateSoap[] getCommerceNotificationTemplates(
					long groupId, boolean enabled, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.notification.model.
							CommerceNotificationTemplate> orderByComparator)
				throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.notification.model.
					CommerceNotificationTemplate> returnValue =
						CommerceNotificationTemplateServiceUtil.
							getCommerceNotificationTemplates(
								groupId, enabled, start, end,
								orderByComparator);

			return com.liferay.commerce.notification.model.
				CommerceNotificationTemplateSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.notification.model.
			CommerceNotificationTemplateSoap[] getCommerceNotificationTemplates(
					long groupId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.notification.model.
							CommerceNotificationTemplate> orderByComparator)
				throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.notification.model.
					CommerceNotificationTemplate> returnValue =
						CommerceNotificationTemplateServiceUtil.
							getCommerceNotificationTemplates(
								groupId, start, end, orderByComparator);

			return com.liferay.commerce.notification.model.
				CommerceNotificationTemplateSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceNotificationTemplatesCount(long groupId)
		throws RemoteException {

		try {
			int returnValue =
				CommerceNotificationTemplateServiceUtil.
					getCommerceNotificationTemplatesCount(groupId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceNotificationTemplatesCount(
			long groupId, boolean enabled)
		throws RemoteException {

		try {
			int returnValue =
				CommerceNotificationTemplateServiceUtil.
					getCommerceNotificationTemplatesCount(groupId, enabled);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.notification.model.CommerceNotificationTemplateSoap
				updateCommerceNotificationTemplate(
					long commerceNotificationTemplateId, String name,
					String description, String from,
					String[] fromNameMapLanguageIds, String[] fromNameMapValues,
					String to, String cc, String bcc, String type,
					boolean enabled, String[] subjectMapLanguageIds,
					String[] subjectMapValues, String[] bodyMapLanguageIds,
					String[] bodyMapValues,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			Map<Locale, String> fromNameMap =
				LocalizationUtil.getLocalizationMap(
					fromNameMapLanguageIds, fromNameMapValues);
			Map<Locale, String> subjectMap =
				LocalizationUtil.getLocalizationMap(
					subjectMapLanguageIds, subjectMapValues);
			Map<Locale, String> bodyMap = LocalizationUtil.getLocalizationMap(
				bodyMapLanguageIds, bodyMapValues);

			com.liferay.commerce.notification.model.CommerceNotificationTemplate
				returnValue =
					CommerceNotificationTemplateServiceUtil.
						updateCommerceNotificationTemplate(
							commerceNotificationTemplateId, name, description,
							from, fromNameMap, to, cc, bcc, type, enabled,
							subjectMap, bodyMap, serviceContext);

			return com.liferay.commerce.notification.model.
				CommerceNotificationTemplateSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceNotificationTemplateServiceSoap.class);

}