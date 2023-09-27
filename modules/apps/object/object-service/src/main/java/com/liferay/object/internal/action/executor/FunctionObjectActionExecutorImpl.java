/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.action.executor;

import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.internal.configuration.FunctionObjectActionExecutorImplConfiguration;
import com.liferay.object.scope.CompanyScoped;
import com.liferay.object.scope.ObjectDefinitionScoped;
import com.liferay.osgi.util.configuration.ConfigurationFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.catapult.PortalCatapult;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.object.internal.configuration.FunctionObjectActionExecutorImplConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	service = ObjectActionExecutor.class
)
public class FunctionObjectActionExecutorImpl
	implements CompanyScoped, ObjectActionExecutor, ObjectDefinitionScoped {

	@Override
	public void execute(
			long companyId, long objectActionId,
			UnicodeProperties parametersUnicodeProperties,
			JSONObject payloadJSONObject, long userId)
		throws Exception {

		_portalCatapult.launch(
			_companyId, Http.Method.POST,
			_functionObjectActionExecutorImplConfiguration.
				oAuth2ApplicationExternalReferenceCode(),
			payloadJSONObject,
			_functionObjectActionExecutorImplConfiguration.resourcePath(),
			userId);
	}

	@Override
	public long getAllowedCompanyId() {
		return _companyId;
	}

	@Override
	public List<String> getAllowedObjectDefinitionNames() {
		return _allowedObjectDefinitionNames;
	}

	@Override
	public String getKey() {
		return _key;
	}

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		_allowedObjectDefinitionNames = StringUtil.asList(
			properties.get("allowedObjectDefinitionNames"));
		_companyId = ConfigurationFactoryUtil.getCompanyId(
			_companyLocalService, properties);
		_functionObjectActionExecutorImplConfiguration =
			ConfigurableUtil.createConfigurable(
				FunctionObjectActionExecutorImplConfiguration.class,
				properties);
		_key = StringBundler.concat(
			ObjectActionExecutorConstants.KEY_FUNCTION, StringPool.POUND,
			ConfigurationFactoryUtil.getExternalReferenceCode(properties));
	}

	private List<String> _allowedObjectDefinitionNames;
	private long _companyId;

	@Reference
	private CompanyLocalService _companyLocalService;

	private FunctionObjectActionExecutorImplConfiguration
		_functionObjectActionExecutorImplConfiguration;
	private String _key;

	@Reference
	private PortalCatapult _portalCatapult;

}