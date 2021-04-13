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
import com.liferay.portal.search.tuning.blueprints.service.ElementServiceUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>ElementServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.portal.search.tuning.blueprints.model.ElementSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.portal.search.tuning.blueprints.model.Element</code>, that is translated to a
 * <code>com.liferay.portal.search.tuning.blueprints.model.ElementSoap</code>. Methods that SOAP
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
 * @see ElementServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ElementServiceSoap {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use <code>com.liferay.portal.search.tuning.elements.service.ElementServiceUtil</code> to access the element remote service.
	 */
	public static com.liferay.portal.search.tuning.blueprints.model.ElementSoap
			addCompanyElement(
				String[] titleMapLanguageIds, String[] titleMapValues,
				String[] descriptionMapLanguageIds,
				String[] descriptionMapValues, String configuration, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.portal.search.tuning.blueprints.model.Element
				returnValue = ElementServiceUtil.addCompanyElement(
					titleMap, descriptionMap, configuration, type,
					serviceContext);

			return com.liferay.portal.search.tuning.blueprints.model.
				ElementSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.search.tuning.blueprints.model.ElementSoap
			addGroupElement(
				String[] titleMapLanguageIds, String[] titleMapValues,
				String[] descriptionMapLanguageIds,
				String[] descriptionMapValues, String configuration, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.portal.search.tuning.blueprints.model.Element
				returnValue = ElementServiceUtil.addGroupElement(
					titleMap, descriptionMap, configuration, type,
					serviceContext);

			return com.liferay.portal.search.tuning.blueprints.model.
				ElementSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.search.tuning.blueprints.model.ElementSoap
			deleteElement(long elementId)
		throws RemoteException {

		try {
			com.liferay.portal.search.tuning.blueprints.model.Element
				returnValue = ElementServiceUtil.deleteElement(elementId);

			return com.liferay.portal.search.tuning.blueprints.model.
				ElementSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.search.tuning.blueprints.model.ElementSoap
			getElement(long elementId)
		throws RemoteException {

		try {
			com.liferay.portal.search.tuning.blueprints.model.Element
				returnValue = ElementServiceUtil.getElement(elementId);

			return com.liferay.portal.search.tuning.blueprints.model.
				ElementSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.portal.search.tuning.blueprints.model.ElementSoap[]
				getGroupElements(long companyId, int type, int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.portal.search.tuning.blueprints.model.Element>
					returnValue = ElementServiceUtil.getGroupElements(
						companyId, type, start, end);

			return com.liferay.portal.search.tuning.blueprints.model.
				ElementSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.portal.search.tuning.blueprints.model.ElementSoap[]
				getGroupElements(
					long companyId, int status, int type, int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.portal.search.tuning.blueprints.model.Element>
					returnValue = ElementServiceUtil.getGroupElements(
						companyId, status, type, start, end);

			return com.liferay.portal.search.tuning.blueprints.model.
				ElementSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.portal.search.tuning.blueprints.model.ElementSoap[]
				getGroupElements(
					long companyId, int status, int type, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.portal.search.tuning.blueprints.model.
							Element> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.portal.search.tuning.blueprints.model.Element>
					returnValue = ElementServiceUtil.getGroupElements(
						companyId, status, type, start, end, orderByComparator);

			return com.liferay.portal.search.tuning.blueprints.model.
				ElementSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.portal.search.tuning.blueprints.model.ElementSoap[]
				getGroupElements(
					long companyId, int type, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.portal.search.tuning.blueprints.model.
							Element> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.portal.search.tuning.blueprints.model.Element>
					returnValue = ElementServiceUtil.getGroupElements(
						companyId, type, start, end, orderByComparator);

			return com.liferay.portal.search.tuning.blueprints.model.
				ElementSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getGroupElementsCount(long companyId, int type)
		throws RemoteException {

		try {
			int returnValue = ElementServiceUtil.getGroupElementsCount(
				companyId, type);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getGroupElementsCount(
			long companyId, int status, int type)
		throws RemoteException {

		try {
			int returnValue = ElementServiceUtil.getGroupElementsCount(
				companyId, status, type);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.search.tuning.blueprints.model.ElementSoap
			updateElement(
				long elementId, String[] titleMapLanguageIds,
				String[] titleMapValues, String[] descriptionMapLanguageIds,
				String[] descriptionMapValues, String configuration,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.portal.search.tuning.blueprints.model.Element
				returnValue = ElementServiceUtil.updateElement(
					elementId, titleMap, descriptionMap, configuration,
					serviceContext);

			return com.liferay.portal.search.tuning.blueprints.model.
				ElementSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ElementServiceSoap.class);

}