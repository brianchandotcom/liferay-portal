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

package com.liferay.commerce.product.internal.links;

import com.liferay.commerce.product.configuration.CPDefinitionLinkTypeConfiguration;
import com.liferay.commerce.product.links.CPDefinitionLinkType;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Marco Leo
 */
@Component(
	configurationPid = "com.liferay.commerce.product.configuration.CPDefinitionLinkTypeConfiguration",
	immediate = true
)
public class CPDefinitionLinkTypeImpl implements CPDefinitionLinkType {

	@Override
	public String getType() {
		return _cpDefinitionLinkTypeConfiguration.type();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_cpDefinitionLinkTypeConfiguration =
			ConfigurableUtil.createConfigurable(
				CPDefinitionLinkTypeConfiguration.class, properties);
	}

	private volatile CPDefinitionLinkTypeConfiguration
		_cpDefinitionLinkTypeConfiguration;

}