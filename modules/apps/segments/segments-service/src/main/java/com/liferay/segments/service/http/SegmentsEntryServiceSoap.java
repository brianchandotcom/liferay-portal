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

package com.liferay.segments.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.segments.service.SegmentsEntryServiceUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>SegmentsEntryServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.segments.model.SegmentsEntrySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.segments.model.SegmentsEntry</code>, that is translated to a
 * <code>com.liferay.segments.model.SegmentsEntrySoap</code>. Methods that SOAP
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
 * @author Eduardo Garcia
 * @see SegmentsEntryServiceHttp
 * @generated
 */
public class SegmentsEntryServiceSoap {

	public static com.liferay.segments.model.SegmentsEntrySoap addSegmentsEntry(
			String segmentsEntryKey, String[] nameMapLanguageIds,
			String[] nameMapValues, String[] descriptionMapLanguageIds,
			String[] descriptionMapValues, boolean active, String criteria,
			String source, String type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.segments.model.SegmentsEntry returnValue =
				SegmentsEntryServiceUtil.addSegmentsEntry(
					segmentsEntryKey, nameMap, descriptionMap, active, criteria,
					source, type, serviceContext);

			return com.liferay.segments.model.SegmentsEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsEntrySoap
			deleteSegmentsEntry(long segmentsEntryId)
		throws RemoteException {

		try {
			com.liferay.segments.model.SegmentsEntry returnValue =
				SegmentsEntryServiceUtil.deleteSegmentsEntry(segmentsEntryId);

			return com.liferay.segments.model.SegmentsEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsEntrySoap[]
			getSegmentsEntries(
				long groupId, boolean includeAncestorSegmentsEntries)
		throws RemoteException {

		try {
			java.util.List<com.liferay.segments.model.SegmentsEntry>
				returnValue = SegmentsEntryServiceUtil.getSegmentsEntries(
					groupId, includeAncestorSegmentsEntries);

			return com.liferay.segments.model.SegmentsEntrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsEntrySoap[]
			getSegmentsEntries(
				long groupId, boolean includeAncestorSegmentsEntries, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.segments.model.SegmentsEntry>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.segments.model.SegmentsEntry>
				returnValue = SegmentsEntryServiceUtil.getSegmentsEntries(
					groupId, includeAncestorSegmentsEntries, start, end,
					orderByComparator);

			return com.liferay.segments.model.SegmentsEntrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getSegmentsEntriesCount(
			long groupId, boolean includeAncestorSegmentsEntries)
		throws RemoteException {

		try {
			int returnValue = SegmentsEntryServiceUtil.getSegmentsEntriesCount(
				groupId, includeAncestorSegmentsEntries);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsEntrySoap getSegmentsEntry(
			long segmentsEntryId)
		throws RemoteException {

		try {
			com.liferay.segments.model.SegmentsEntry returnValue =
				SegmentsEntryServiceUtil.getSegmentsEntry(segmentsEntryId);

			return com.liferay.segments.model.SegmentsEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsEntrySoap
			updateSegmentsEntry(
				long segmentsEntryId, String segmentsEntryKey,
				String[] nameMapLanguageIds, String[] nameMapValues,
				String[] descriptionMapLanguageIds,
				String[] descriptionMapValues, boolean active, String criteria,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.segments.model.SegmentsEntry returnValue =
				SegmentsEntryServiceUtil.updateSegmentsEntry(
					segmentsEntryId, segmentsEntryKey, nameMap, descriptionMap,
					active, criteria, serviceContext);

			return com.liferay.segments.model.SegmentsEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SegmentsEntryServiceSoap.class);

}