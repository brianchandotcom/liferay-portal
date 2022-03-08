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

package com.liferay.portal.language.override.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.language.override.model.PLOEntry;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for PLOEntry. This utility wraps
 * <code>com.liferay.portal.language.override.service.impl.PLOEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Drew Brokke
 * @see PLOEntryService
 * @generated
 */
public class PLOEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.language.override.service.impl.PLOEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static PLOEntry addOrUpdatePLOEntry(
			String key, String languageId, String value)
		throws PortalException {

		return getService().addOrUpdatePLOEntry(key, languageId, value);
	}

	public static void deletePLOEntries(String key) throws PortalException {
		getService().deletePLOEntries(key);
	}

	public static PLOEntry deletePLOEntry(String key, String languageId)
		throws PortalException {

		return getService().deletePLOEntry(key, languageId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<PLOEntry> getPLOEntries(long companyId)
		throws PortalException {

		return getService().getPLOEntries(companyId);
	}

	public static int getPLOEntriesCount(long companyId)
		throws PortalException {

		return getService().getPLOEntriesCount(companyId);
	}

	public static void setPLOEntries(
			String key, Map<java.util.Locale, String> localizationMap)
		throws PortalException {

		getService().setPLOEntries(key, localizationMap);
	}

	public static PLOEntryService getService() {
		return _service;
	}

	private static volatile PLOEntryService _service;

}