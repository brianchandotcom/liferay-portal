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

package com.liferay.oauth2.provider.shortcut.internal.endpoint.register.extension.sap;

import com.liferay.oauth2.provider.shortcut.internal.endpoint.register.extension.Extension;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.security.service.access.policy.exception.SAPEntryNameException;
import com.liferay.portal.security.service.access.policy.exception.SAPEntryTitleException;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryService;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(
	configurationPid = "com.liferay.oauth2.provider.jsonws.internal.configuration.OAuth2JSONWSConfiguration",
	immediate = true, property = "type=sap", service = Extension.class
)
public class SAPExtension implements Extension {

	@Override
	public void postProcess(Object object, User user) throws Exception {
	}

	@Override
	public void preProcess(Object object, User user) throws Exception {
		if (object instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray)object;

			for (int i = 0; i < jsonArray.length(); i++) {
				process(jsonArray.getJSONObject(i), user);
			}
		}
		else if (object instanceof JSONObject) {
			process((JSONObject)object, user);
		}
		else {
			throw new Exception("SAP extension must be an object or an array");
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_sapEntryOAuth2Prefix = (String)properties.getOrDefault(
			"oauth2.sap.entry.oauth2.prefix", "OAUTH2_");
	}

	protected void process(JSONObject sapExtensionEntryJSONObject, User user)
		throws PortalException {

		SAPExtensionEntry sapExtensionEntry = _jsonFactory.looseDeserialize(
			sapExtensionEntryJSONObject.toString(), SAPExtensionEntry.class);

		String name = _sapEntryOAuth2Prefix + sapExtensionEntry.getName();

		SAPEntry sapEntry = _sapEntryService.fetchSAPEntry(
			user.getCompanyId(), name);

		if (sapEntry != null) {
			return;
		}

		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(LocaleUtil.getDefault(), sapExtensionEntry.getTitle());

		try {
			_sapEntryService.addSAPEntry(
				sapExtensionEntry.getSignatures(), false, true, name, titleMap,
				new ServiceContext());
		}
		catch (SAPEntryNameException sapene) {
			throw new PortalException(
				StringBundler.concat(
					"Invalid sap extension name  ", sapExtensionEntry.getName(),
					": ", sapene.getMessage()));
		}
		catch (SAPEntryTitleException sapete) {
			throw new PortalException("Missing sap extension title", sapete);
		}
	}

	@Reference
	private JSONFactory _jsonFactory;

	private String _sapEntryOAuth2Prefix;

	@Reference
	private SAPEntryService _sapEntryService;

}