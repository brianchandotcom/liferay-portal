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

package com.liferay.fragment.service.http;

import com.liferay.fragment.service.FragmentEntryLinkServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>FragmentEntryLinkServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.fragment.model.FragmentEntryLinkSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.fragment.model.FragmentEntryLink</code>, that is translated to a
 * <code>com.liferay.fragment.model.FragmentEntryLinkSoap</code>. Methods that SOAP
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
 * @see FragmentEntryLinkServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class FragmentEntryLinkServiceSoap {

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #addFragmentEntryLink(long, long, long, long, long, String,
	 String, String, String, String, String, int, String,
	 ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntryLinkSoap
			addFragmentEntryLink(
				long groupId, long originalFragmentEntryLinkId,
				long fragmentEntryId, long segmentsExperienceId,
				long classNameId, long classPK, String css, String html,
				String js, String configuration, String editableValues,
				String namespace, int position, String rendererKey,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentEntryLink returnValue =
				FragmentEntryLinkServiceUtil.addFragmentEntryLink(
					groupId, originalFragmentEntryLinkId, fragmentEntryId,
					segmentsExperienceId, classNameId, classPK, css, html, js,
					configuration, editableValues, namespace, position,
					rendererKey, serviceContext);

			return com.liferay.fragment.model.FragmentEntryLinkSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntryLinkSoap
			addFragmentEntryLink(
				long groupId, long originalFragmentEntryLinkId,
				long fragmentEntryId, long segmentsExperienceId, long plid,
				String css, String html, String js, String configuration,
				String editableValues, String namespace, int position,
				String rendererKey,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentEntryLink returnValue =
				FragmentEntryLinkServiceUtil.addFragmentEntryLink(
					groupId, originalFragmentEntryLinkId, fragmentEntryId,
					segmentsExperienceId, plid, css, html, js, configuration,
					editableValues, namespace, position, rendererKey,
					serviceContext);

			return com.liferay.fragment.model.FragmentEntryLinkSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntryLinkSoap
			deleteFragmentEntryLink(long fragmentEntryLinkId)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentEntryLink returnValue =
				FragmentEntryLinkServiceUtil.deleteFragmentEntryLink(
					fragmentEntryLinkId);

			return com.liferay.fragment.model.FragmentEntryLinkSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntryLinkSoap
			updateFragmentEntryLink(
				long fragmentEntryLinkId, String editableValues)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentEntryLink returnValue =
				FragmentEntryLinkServiceUtil.updateFragmentEntryLink(
					fragmentEntryLinkId, editableValues);

			return com.liferay.fragment.model.FragmentEntryLinkSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentEntryLinkSoap
			updateFragmentEntryLink(
				long fragmentEntryLinkId, String editableValues,
				boolean updateClassedModel)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentEntryLink returnValue =
				FragmentEntryLinkServiceUtil.updateFragmentEntryLink(
					fragmentEntryLinkId, editableValues, updateClassedModel);

			return com.liferay.fragment.model.FragmentEntryLinkSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #updateFragmentEntryLinks(long, long, long[], String,
	 ServiceContext)}
	 */
	@Deprecated
	public static void updateFragmentEntryLinks(
			long groupId, long classNameId, long classPK,
			long[] fragmentEntryIds, String editableValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			FragmentEntryLinkServiceUtil.updateFragmentEntryLinks(
				groupId, classNameId, classPK, fragmentEntryIds, editableValues,
				serviceContext);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void updateFragmentEntryLinks(
			long groupId, long plid, long[] fragmentEntryIds,
			String editableValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			FragmentEntryLinkServiceUtil.updateFragmentEntryLinks(
				groupId, plid, fragmentEntryIds, editableValues,
				serviceContext);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		FragmentEntryLinkServiceSoap.class);

}