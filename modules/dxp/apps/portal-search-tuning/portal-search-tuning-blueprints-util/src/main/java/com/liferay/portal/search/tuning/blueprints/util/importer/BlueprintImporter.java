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

package com.liferay.portal.search.tuning.blueprints.util.importer;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import javax.portlet.PortletRequest;

/**
 * @author Petteri Karttunen
 */
public interface BlueprintImporter {

	public void importBlueprint(
			long companyId, long groupId, long userId, JSONObject jsonObject)
		throws PortalException;

	public void importBlueprint(
			PortletRequest portletRequest, InputStream inputStream)
		throws IOException, PortalException;

}