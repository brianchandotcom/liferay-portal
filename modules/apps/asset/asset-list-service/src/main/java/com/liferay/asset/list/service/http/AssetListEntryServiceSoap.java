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

package com.liferay.asset.list.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.list.service.AssetListEntryServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link AssetListEntryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.asset.list.model.AssetListEntrySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.asset.list.model.AssetListEntry}, that is translated to a
 * {@link com.liferay.asset.list.model.AssetListEntrySoap}. Methods that SOAP cannot
 * safely wire are skipped.
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
 * @see AssetListEntryServiceHttp
 * @see com.liferay.asset.list.model.AssetListEntrySoap
 * @see AssetListEntryServiceUtil
 * @generated
 */
@ProviderType
public class AssetListEntryServiceSoap {
	public static com.liferay.asset.list.model.AssetListEntrySoap addAssetListEntry(
		long userId, long groupId, String[] titleMapLanguageIds,
		String[] titleMapValues, String[] descriptionMapLanguageIds,
		String[] descriptionMapValues, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.asset.list.model.AssetListEntry returnValue = AssetListEntryServiceUtil.addAssetListEntry(userId,
					groupId, titleMap, descriptionMap, type, serviceContext);

			return com.liferay.asset.list.model.AssetListEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.list.model.AssetListEntrySoap deleteAssetListEntry(
		long assetListEntryId) throws RemoteException {
		try {
			com.liferay.asset.list.model.AssetListEntry returnValue = AssetListEntryServiceUtil.deleteAssetListEntry(assetListEntryId);

			return com.liferay.asset.list.model.AssetListEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.list.model.AssetListEntrySoap fetchAssetListEntry(
		long assetListEntryId) throws RemoteException {
		try {
			com.liferay.asset.list.model.AssetListEntry returnValue = AssetListEntryServiceUtil.fetchAssetListEntry(assetListEntryId);

			return com.liferay.asset.list.model.AssetListEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.list.model.AssetListEntrySoap updateAssetListEntry(
		long assetListEntryId, String[] titleMapLanguageIds,
		String[] titleMapValues, String[] descriptionMapLanguageIds,
		String[] descriptionMapValues) throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.asset.list.model.AssetListEntry returnValue = AssetListEntryServiceUtil.updateAssetListEntry(assetListEntryId,
					titleMap, descriptionMap);

			return com.liferay.asset.list.model.AssetListEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AssetListEntryServiceSoap.class);
}