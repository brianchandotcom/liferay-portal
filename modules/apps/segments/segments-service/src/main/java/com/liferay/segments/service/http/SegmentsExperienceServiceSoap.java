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
import com.liferay.segments.service.SegmentsExperienceServiceUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>SegmentsExperienceServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.segments.model.SegmentsExperienceSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.segments.model.SegmentsExperience</code>, that is translated to a
 * <code>com.liferay.segments.model.SegmentsExperienceSoap</code>. Methods that SOAP
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
 * @see SegmentsExperienceServiceHttp
 * @generated
 */
public class SegmentsExperienceServiceSoap {

	public static com.liferay.segments.model.SegmentsExperienceSoap
			addSegmentsExperience(
				long segmentsEntryId, long classNameId, long classPK,
				String[] nameMapLanguageIds, String[] nameMapValues,
				boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);

			com.liferay.segments.model.SegmentsExperience returnValue =
				SegmentsExperienceServiceUtil.addSegmentsExperience(
					segmentsEntryId, classNameId, classPK, nameMap, active,
					serviceContext);

			return com.liferay.segments.model.SegmentsExperienceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsExperienceSoap
			deleteSegmentsExperience(long segmentsExperienceId)
		throws RemoteException {

		try {
			com.liferay.segments.model.SegmentsExperience returnValue =
				SegmentsExperienceServiceUtil.deleteSegmentsExperience(
					segmentsExperienceId);

			return com.liferay.segments.model.SegmentsExperienceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsExperienceSoap
			fetchSegmentsExperience(long groupId, String segmentsExperienceKey)
		throws RemoteException {

		try {
			com.liferay.segments.model.SegmentsExperience returnValue =
				SegmentsExperienceServiceUtil.fetchSegmentsExperience(
					groupId, segmentsExperienceKey);

			return com.liferay.segments.model.SegmentsExperienceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsExperienceSoap
			getSegmentsExperience(long segmentsExperienceId)
		throws RemoteException {

		try {
			com.liferay.segments.model.SegmentsExperience returnValue =
				SegmentsExperienceServiceUtil.getSegmentsExperience(
					segmentsExperienceId);

			return com.liferay.segments.model.SegmentsExperienceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsExperienceSoap[]
			getSegmentsExperiences(
				long groupId, long classNameId, long classPK, boolean active)
		throws RemoteException {

		try {
			java.util.List<com.liferay.segments.model.SegmentsExperience>
				returnValue =
					SegmentsExperienceServiceUtil.getSegmentsExperiences(
						groupId, classNameId, classPK, active);

			return com.liferay.segments.model.SegmentsExperienceSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsExperienceSoap[]
			getSegmentsExperiences(
				long groupId, long classNameId, long classPK, boolean active,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.segments.model.SegmentsExperience>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.segments.model.SegmentsExperience>
				returnValue =
					SegmentsExperienceServiceUtil.getSegmentsExperiences(
						groupId, classNameId, classPK, active, start, end,
						orderByComparator);

			return com.liferay.segments.model.SegmentsExperienceSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getSegmentsExperiencesCount(
			long groupId, long classNameId, long classPK, boolean active)
		throws RemoteException {

		try {
			int returnValue =
				SegmentsExperienceServiceUtil.getSegmentsExperiencesCount(
					groupId, classNameId, classPK, active);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.segments.model.SegmentsExperienceSoap
			updateSegmentsExperience(
				long segmentsExperienceId, long segmentsEntryId,
				String[] nameMapLanguageIds, String[] nameMapValues,
				boolean active)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);

			com.liferay.segments.model.SegmentsExperience returnValue =
				SegmentsExperienceServiceUtil.updateSegmentsExperience(
					segmentsExperienceId, segmentsEntryId, nameMap, active);

			return com.liferay.segments.model.SegmentsExperienceSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void updateSegmentsExperiencePriority(
			long segmentsExperienceId, int newPriority)
		throws RemoteException {

		try {
			SegmentsExperienceServiceUtil.updateSegmentsExperiencePriority(
				segmentsExperienceId, newPriority);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SegmentsExperienceServiceSoap.class);

}