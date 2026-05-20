/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.web.internal.portlet.action;

import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import jakarta.portlet.ResourceRequest;
import jakarta.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Olivia Yu
 */
@Component(
	property = {
		"jakarta.portlet.name=" + AssetListPortletKeys.ASSET_LIST,
		"mvc.command.name=/asset_list/get_type_properties"
	},
	service = MVCResourceCommand.class
)
public class GetTypePropertiesMVCResourceCommand
	extends BaseMVCResourceCommand {

	public static JSONArray getTypePropertiesJSONArray(
		long[] classNameIds, long[] classTypeIds) {

		return JSONUtil.putAll(
			JSONUtil.put(
				"label", "Title"
			).put(
				"name", "title"
			).put(
				"type", "string"
			),
			JSONUtil.put(
				"label", "Views"
			).put(
				"name", "views"
			).put(
				"type", "integer"
			),
			JSONUtil.put(
				"label", "Published"
			).put(
				"name", "published"
			).put(
				"type", "boolean"
			),
			JSONUtil.put(
				"label", "Publish Date"
			).put(
				"name", "publishDate"
			).put(
				"type", "date"
			),
			JSONUtil.put(
				"label", "Status"
			).put(
				"name", "status"
			).put(
				"options",
				JSONUtil.putAll(
					JSONUtil.put(
						"label", "Approved"
					).put(
						"value", "approved"
					),
					JSONUtil.put(
						"label", "Draft"
					).put(
						"value", "draft"
					),
					JSONUtil.put(
						"label", "Pending"
					).put(
						"value", "pending"
					))
			).put(
				"type", "picklist"
			));
	}

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long[] classNameIds = GetterUtil.getLongValues(
			StringUtil.split(
				ParamUtil.getString(resourceRequest, "classNameIds")));
		long[] classTypeIds = GetterUtil.getLongValues(
			StringUtil.split(
				ParamUtil.getString(resourceRequest, "classTypeIds")));

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			getTypePropertiesJSONArray(classNameIds, classTypeIds));
	}

}