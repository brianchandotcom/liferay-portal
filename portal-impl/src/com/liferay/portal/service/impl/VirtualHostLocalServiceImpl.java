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

package com.liferay.portal.service.impl;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.exception.AvailableLocaleException;
import com.liferay.portal.kernel.exception.NoSuchVirtualHostException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.service.persistence.CompanyPersistence;
import com.liferay.portal.kernel.service.persistence.GroupPersistence;
import com.liferay.portal.kernel.service.persistence.LayoutSetPersistence;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.impl.LayoutSetImpl;
import com.liferay.portal.service.base.VirtualHostLocalServiceBaseImpl;
import com.liferay.portal.util.PropsValues;

import java.net.IDN;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class VirtualHostLocalServiceImpl
	extends VirtualHostLocalServiceBaseImpl {

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #getVirtualHosts(long, long)}
	 */
	@Deprecated
	@Override
	public VirtualHost fetchVirtualHost(long companyId, long layoutSetId) {
		return virtualHostPersistence.fetchByC_L_D(
			companyId, layoutSetId, true);
	}

	@Override
	public VirtualHost fetchVirtualHost(String hostname) {
		if (Validator.isIPv6Address(hostname)) {
			try {
				Inet6Address inet6Address = (Inet6Address)InetAddress.getByName(
					hostname);

				hostname = inet6Address.getHostAddress();
			}
			catch (UnknownHostException unknownHostException) {
				if (_log.isDebugEnabled()) {
					_log.debug(unknownHostException, unknownHostException);
				}
			}
		}

		VirtualHost virtualHost = virtualHostPersistence.fetchByHostname(
			hostname);

		if ((virtualHost == null) && hostname.contains("xn--")) {
			virtualHost = virtualHostPersistence.fetchByHostname(
				IDN.toUnicode(hostname));
		}

		return virtualHost;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #getVirtualHosts(long, long)}
	 */
	@Deprecated
	@Override
	public VirtualHost getVirtualHost(long companyId, long layoutSetId)
		throws PortalException {

		return virtualHostPersistence.findByC_L_D(companyId, layoutSetId, true);
	}

	@Override
	public VirtualHost getVirtualHost(String hostname) throws PortalException {
		try {
			return virtualHostPersistence.findByHostname(hostname);
		}
		catch (NoSuchVirtualHostException noSuchVirtualHostException) {
			if (hostname.contains("xn--")) {
				return virtualHostPersistence.findByHostname(
					IDN.toUnicode(hostname));
			}

			throw noSuchVirtualHostException;
		}
	}

	@Override
	public List<VirtualHost> getVirtualHosts(long companyId, long layoutSetId)
		throws PortalException {

		return virtualHostPersistence.findByC_L(companyId, layoutSetId);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #updateVirtualHosts(long, long, TreeMap)}
	 */
	@Deprecated
	@Override
	public VirtualHost updateVirtualHost(
		long companyId, long layoutSetId, String hostname) {

		List<VirtualHost> virtualHosts = updateVirtualHosts(
			companyId, layoutSetId,
			TreeMapBuilder.put(
				hostname, StringPool.BLANK
			).build());

		if (virtualHosts.isEmpty()) {
			return null;
		}

		return virtualHosts.get(0);
	}

	@Override
	public List<VirtualHost> updateVirtualHosts(
		long companyId, long layoutSetId, TreeMap<String, String> hostnames) {

		LayoutSet layoutSet = _layoutSetPersistence.fetchByPrimaryKey(
			layoutSetId);

		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales();

		if (layoutSet != null) {
			availableLocales = LanguageUtil.getAvailableLocales(
				layoutSet.getGroupId());
		}

		List<VirtualHost> virtualHosts = new ArrayList<>(
			virtualHostPersistence.findByC_L(companyId, layoutSetId));

		boolean first = true;

		for (String curHostname : hostnames.navigableKeySet()) {
			VirtualHost virtualHost = null;

			for (VirtualHost curVirtualHost : virtualHosts) {
				if (curHostname.equals(curVirtualHost.getHostname())) {
					virtualHost = curVirtualHost;

					break;
				}
			}

			if (virtualHost == null) {
				long virtualHostId = counterLocalService.increment();

				virtualHost = virtualHostPersistence.create(virtualHostId);

				virtualHost.setCompanyId(companyId);
				virtualHost.setLayoutSetId(layoutSetId);
				virtualHost.setHostname(curHostname);

				virtualHosts.add(virtualHost);
			}

			String languageId = hostnames.get(curHostname);

			Locale locale = LocaleUtil.fromLanguageId(languageId, true, false);

			if (locale == null) {
				locale = LocaleUtil.getSiteDefault();
			}

			if (!availableLocales.contains(locale)) {
				ReflectionUtil.throwException(
					new AvailableLocaleException(languageId));
			}

			virtualHost.setDefaultVirtualHost(first);
			virtualHost.setLanguageId(languageId);

			first = false;

			virtualHostPersistence.update(virtualHost);
		}

		Iterator<VirtualHost> iterator = virtualHosts.iterator();

		while (iterator.hasNext()) {
			VirtualHost virtualHost = iterator.next();

			if (!hostnames.containsKey(virtualHost.getHostname())) {
				iterator.remove();

				virtualHostPersistence.remove(virtualHost);
			}
		}

		virtualHostPersistence.cacheResult(virtualHosts);

		Company company = _companyPersistence.fetchByPrimaryKey(companyId);

		if (company != null) {
			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					EntityCacheUtil.removeResult(
						company.getClass(), company.getPrimaryKeyObj());

					return null;
				});

			_companyPersistence.clearCache(company);
		}

		if ((layoutSet == null) &&
			Validator.isNotNull(PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME)) {

			Group group = _groupPersistence.fetchByC_GK(
				companyId, PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

			if (group != null) {
				layoutSet = _layoutSetPersistence.fetchByG_P(
					group.getGroupId(), false);
			}
		}

		if (layoutSet != null) {
			_layoutSetPersistence.clearCache(layoutSet);

			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					EntityCacheUtil.removeResult(
						LayoutSetImpl.class, layoutSetId);

					return null;
				});
		}

		return virtualHosts;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VirtualHostLocalServiceImpl.class);

	@BeanReference(type = CompanyPersistence.class)
	private CompanyPersistence _companyPersistence;

	@BeanReference(type = GroupPersistence.class)
	private GroupPersistence _groupPersistence;

	@BeanReference(type = LayoutSetPersistence.class)
	private LayoutSetPersistence _layoutSetPersistence;

}