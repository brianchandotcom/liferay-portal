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

import com.liferay.fragment.service.FragmentCompositionServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>FragmentCompositionServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.fragment.model.FragmentCompositionSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.fragment.model.FragmentComposition</code>, that is translated to a
 * <code>com.liferay.fragment.model.FragmentCompositionSoap</code>. Methods that SOAP
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
 * @see FragmentCompositionServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class FragmentCompositionServiceSoap {

	public static com.liferay.fragment.model.FragmentCompositionSoap
			addFragmentComposition(
				long groupId, long fragmentCollectionId,
				String fragmentCompositionKey, String name, String description,
				String data, long previewFileEntryId, int status,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentComposition returnValue =
				FragmentCompositionServiceUtil.addFragmentComposition(
					groupId, fragmentCollectionId, fragmentCompositionKey, name,
					description, data, previewFileEntryId, status,
					serviceContext);

			return com.liferay.fragment.model.FragmentCompositionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentCompositionSoap
			deleteFragmentComposition(long fragmentCompositionId)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentComposition returnValue =
				FragmentCompositionServiceUtil.deleteFragmentComposition(
					fragmentCompositionId);

			return com.liferay.fragment.model.FragmentCompositionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentCompositionSoap
			fetchFragmentComposition(long fragmentCompositionId)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentComposition returnValue =
				FragmentCompositionServiceUtil.fetchFragmentComposition(
					fragmentCompositionId);

			return com.liferay.fragment.model.FragmentCompositionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentCompositionSoap
			fetchFragmentComposition(
				long groupId, String fragmentCompositionKey)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentComposition returnValue =
				FragmentCompositionServiceUtil.fetchFragmentComposition(
					groupId, fragmentCompositionKey);

			return com.liferay.fragment.model.FragmentCompositionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentCompositionSoap[]
			getFragmentCompositions(long fragmentCollectionId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.fragment.model.FragmentComposition>
				returnValue =
					FragmentCompositionServiceUtil.getFragmentCompositions(
						fragmentCollectionId);

			return com.liferay.fragment.model.FragmentCompositionSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentCompositionSoap[]
			getFragmentCompositions(
				long fragmentCollectionId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.fragment.model.FragmentComposition>
				returnValue =
					FragmentCompositionServiceUtil.getFragmentCompositions(
						fragmentCollectionId, start, end);

			return com.liferay.fragment.model.FragmentCompositionSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentCompositionSoap[]
			getFragmentCompositions(
				long groupId, long fragmentCollectionId, int status)
		throws RemoteException {

		try {
			java.util.List<com.liferay.fragment.model.FragmentComposition>
				returnValue =
					FragmentCompositionServiceUtil.getFragmentCompositions(
						groupId, fragmentCollectionId, status);

			return com.liferay.fragment.model.FragmentCompositionSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentCompositionSoap[]
			getFragmentCompositions(
				long groupId, long fragmentCollectionId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.fragment.model.FragmentComposition>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.fragment.model.FragmentComposition>
				returnValue =
					FragmentCompositionServiceUtil.getFragmentCompositions(
						groupId, fragmentCollectionId, start, end,
						orderByComparator);

			return com.liferay.fragment.model.FragmentCompositionSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentCompositionSoap[]
			getFragmentCompositions(
				long groupId, long fragmentCollectionId, String name, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.fragment.model.FragmentComposition>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.fragment.model.FragmentComposition>
				returnValue =
					FragmentCompositionServiceUtil.getFragmentCompositions(
						groupId, fragmentCollectionId, name, start, end,
						orderByComparator);

			return com.liferay.fragment.model.FragmentCompositionSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getFragmentCompositionsCount(long fragmentCollectionId)
		throws RemoteException {

		try {
			int returnValue =
				FragmentCompositionServiceUtil.getFragmentCompositionsCount(
					fragmentCollectionId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentCompositionSoap
			moveFragmentComposition(
				long fragmentCompositionId, long fragmentCollectionId)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentComposition returnValue =
				FragmentCompositionServiceUtil.moveFragmentComposition(
					fragmentCompositionId, fragmentCollectionId);

			return com.liferay.fragment.model.FragmentCompositionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentCompositionSoap
			updateFragmentComposition(
				long fragmentCompositionId, long previewFileEntryId)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentComposition returnValue =
				FragmentCompositionServiceUtil.updateFragmentComposition(
					fragmentCompositionId, previewFileEntryId);

			return com.liferay.fragment.model.FragmentCompositionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentCompositionSoap
			updateFragmentComposition(long fragmentCompositionId, String name)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentComposition returnValue =
				FragmentCompositionServiceUtil.updateFragmentComposition(
					fragmentCompositionId, name);

			return com.liferay.fragment.model.FragmentCompositionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.fragment.model.FragmentCompositionSoap
			updateFragmentComposition(
				long fragmentCompositionId, String name, String description,
				String data, long previewFileEntryId, int status)
		throws RemoteException {

		try {
			com.liferay.fragment.model.FragmentComposition returnValue =
				FragmentCompositionServiceUtil.updateFragmentComposition(
					fragmentCompositionId, name, description, data,
					previewFileEntryId, status);

			return com.liferay.fragment.model.FragmentCompositionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		FragmentCompositionServiceSoap.class);

}