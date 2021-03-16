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

package com.liferay.portal.search.tuning.blueprints.resources.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.tuning.blueprints.util.importer.BlueprintImporter;

import java.net.URL;

import java.util.Enumeration;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = ImportHelper.class)
public class ImportHelper {

	public void importDefaultBlueprints(
			long companyId, long groupId, long userId)
		throws PortalException {

		_importBlueprints(companyId, groupId, userId, _listBlueprints());
	}

	private Bundle _getBundle() {
		return FrameworkUtil.getBundle(getClass());
	}

	private JSONObject _getJSONObject(URL url) throws JSONException {
		return _jsonFactory.createJSONObject(
			StringUtil.read(getClass(), "/" + url.getPath()));
	}

	private void _importBlueprints(
			long companyId, long groupId, long userId,
			Enumeration<URL> urlEnumeration)
		throws PortalException {

		if (_log.isInfoEnabled()) {
			_log.info("Importing default Blueprints");
		}

		if ((urlEnumeration == null) || !urlEnumeration.hasMoreElements()) {
			return;
		}

		while (urlEnumeration.hasMoreElements()) {
			URL url = urlEnumeration.nextElement();

			_importToCompany(companyId, groupId, userId, _getJSONObject(url));
		}
	}

	private void _importToCompany(
		long companyId, long groupId, long userId, JSONObject jsonObject) {

		try {
			_blueprintImporter.importBlueprint(
				companyId, groupId, userId, jsonObject);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private Enumeration<URL> _listBlueprints() {
		Bundle bundle = _getBundle();

		return bundle.findEntries(_BLUEPRINTS_PATH, "*.json", false);
	}

	private static final String _BLUEPRINTS_PATH =
		"/META-INF/search/blueprints";

	private static final Log _log = LogFactoryUtil.getLog(ImportHelper.class);

	@Reference
	private BlueprintImporter _blueprintImporter;

	@Reference
	private JSONFactory _jsonFactory;

}