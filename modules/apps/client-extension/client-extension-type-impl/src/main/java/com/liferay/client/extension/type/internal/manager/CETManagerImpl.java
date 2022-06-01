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

package com.liferay.client.extension.type.internal.manager;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.exception.ClientExtensionEntryTypeException;
import com.liferay.client.extension.service.ClientExtensionEntryLocalService;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.deployer.CETDeployer;
import com.liferay.client.extension.type.factory.CETFactory;
import com.liferay.client.extension.type.internal.CETCustomElementImpl;
import com.liferay.client.extension.type.internal.CETGlobalCSSImpl;
import com.liferay.client.extension.type.internal.CETGlobalJSImpl;
import com.liferay.client.extension.type.internal.CETIFrameImpl;
import com.liferay.client.extension.type.internal.CETThemeCSSImpl;
import com.liferay.client.extension.type.internal.CETThemeFaviconImpl;
import com.liferay.client.extension.type.internal.CETThemeJSImpl;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = CETManager.class)
public class CETManagerImpl implements CETManager {

	@Override
	public CET addCET(
			String baseURL, long companyId, String description, String name,
			String primaryKey, Properties properties, String sourceCodeURL,
			String type, String typeSettings)
		throws PortalException {

		CET cet = null;

		if (Objects.equals(
				type, ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT)) {

			cet = new CETCustomElementImpl(
				baseURL, companyId, description, name, primaryKey, properties,
				sourceCodeURL, typeSettings);
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_GLOBAL_CSS)) {

			cet = new CETGlobalCSSImpl(
				baseURL, companyId, description, name, primaryKey, properties,
				sourceCodeURL, typeSettings);
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_GLOBAL_JS)) {

			cet = new CETGlobalJSImpl(
				baseURL, companyId, description, name, primaryKey, properties,
				sourceCodeURL, typeSettings);
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_IFRAME)) {

			cet = new CETIFrameImpl(
				baseURL, companyId, description, name, primaryKey, properties,
				sourceCodeURL, typeSettings);
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_THEME_CSS)) {

			cet = new CETThemeCSSImpl(
				baseURL, companyId, description, name, primaryKey, properties,
				sourceCodeURL, typeSettings);
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_THEME_FAVICON)) {

			cet = new CETThemeFaviconImpl(
				baseURL, companyId, description, name, primaryKey, properties,
				sourceCodeURL, typeSettings);
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_THEME_JS)) {

			cet = new CETThemeJSImpl(
				baseURL, companyId, description, name, primaryKey, properties,
				sourceCodeURL, typeSettings);
		}
		else {
			throw new ClientExtensionEntryTypeException("Invalid type " + type);
		}

		Map<String, CET> cetsMap = _getCETsMap(cet.getCompanyId());

		cetsMap.put(primaryKey, cet);

		_serviceRegistrationsMaps.put(primaryKey, _cetDeployer.deploy(cet));

		return cet;
	}

	@Override
	public void deleteCET(CET cet) {
		Map<String, CET> cetsMap = _getCETsMap(cet.getCompanyId());

		cetsMap.remove(cet.getPrimaryKey());

		_undeployCET(cet);
	}

	@Override
	public List<CET> getCETs(
			long companyId, String keywords, String type, Pagination pagination,
			Sort sort)
		throws PortalException {

		// TODO Account for pagination

		List<CET> cets = TransformUtil.transform(
			_clientExtensionEntryLocalService.search(
				companyId, keywords, pagination.getStartPosition(),
				pagination.getEndPosition(), sort),
			clientExtensionEntry -> _cetFactory.cet(clientExtensionEntry));

		Map<String, CET> cetsMap = _getCETsMap(companyId);

		for (Map.Entry<String, CET> entry : cetsMap.entrySet()) {
			cets.add(0, entry.getValue());
		}

		if (Validator.isNotNull(type)) {
			cets = ListUtil.filter(
				cets, cet -> Objects.equals(type, cet.getType()));
		}

		return cets;
	}

	@Override
	public int getCETsCount(long companyId, String keywords)
		throws PortalException {

		Map<String, CET> cetsMap = _getCETsMap(companyId);

		int count = _clientExtensionEntryLocalService.searchCount(
			companyId, keywords);

		return cetsMap.size() + count;
	}

	@Deactivate
	protected void deactivate() {
		for (Map.Entry<Long, Map<String, CET>> entry1 : _cetsMaps.entrySet()) {
			Map<String, CET> cetsMap = entry1.getValue();

			for (Map.Entry<String, CET> entry2 : cetsMap.entrySet()) {
				CET cet = entry2.getValue();

				_undeployCET(cet);
			}
		}
	}

	private Map<String, CET> _getCETsMap(long companyId) {
		Map<String, CET> cetsMap = _cetsMaps.get(companyId);

		if (cetsMap == null) {
			cetsMap = new ConcurrentHashMap<>();

			_cetsMaps.put(companyId, cetsMap);
		}

		return cetsMap;
	}

	private void _undeployCET(CET cet) {
		List<ServiceRegistration<?>> serviceRegistrations =
			_serviceRegistrationsMaps.remove(cet.getPrimaryKey());

		if (serviceRegistrations != null) {
			for (ServiceRegistration<?> serviceRegistration :
					serviceRegistrations) {

				serviceRegistration.unregister();
			}
		}
	}

	@Reference
	private CETDeployer _cetDeployer;

	@Reference
	private CETFactory _cetFactory;

	private final Map<Long, Map<String, CET>> _cetsMaps =
		new ConcurrentHashMap<>();

	@Reference
	private ClientExtensionEntryLocalService _clientExtensionEntryLocalService;

	private final Map<String, List<ServiceRegistration<?>>>
		_serviceRegistrationsMaps = new ConcurrentHashMap<>();

}