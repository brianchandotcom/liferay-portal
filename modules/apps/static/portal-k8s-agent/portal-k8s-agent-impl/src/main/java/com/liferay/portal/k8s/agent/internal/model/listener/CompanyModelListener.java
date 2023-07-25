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

package com.liferay.portal.k8s.agent.internal.model.listener;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.k8s.agent.PortalK8sConfigMapModifier;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.util.PropsValues;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gregory Amerson
 */
@Component(service = ModelListener.class)
public class CompanyModelListener extends BaseModelListener<Company> {

	@Override
	public void onAfterRemove(Company company) throws ModelListenerException {
		if (Objects.equals(
				company.getWebId(), PropsValues.COMPANY_DEFAULT_WEB_ID)) {

			return;
		}

		PortalK8sConfigMapModifier portalK8sConfigMapModifier =
			_portalK8sConfigMapModifierSnapshot.get();

		portalK8sConfigMapModifier.modifyConfigMap(
			configMapModel -> {
				Map<String, String> data = configMapModel.data();

				data.clear();

				Map<String, String> labels = configMapModel.labels();

				labels.put(
					"dxp.lxc.liferay.com/virtualInstanceId",
					company.getWebId());
			},
			_getConfigMapName(company));
	}

	private String _getConfigMapName(Company company) {
		return company.getWebId() + "-lxc-dxp-metadata";
	}

	private static final Snapshot<PortalK8sConfigMapModifier>
		_portalK8sConfigMapModifierSnapshot = new Snapshot<>(
			CompanyModelListener.class, PortalK8sConfigMapModifier.class, null,
			true);

}