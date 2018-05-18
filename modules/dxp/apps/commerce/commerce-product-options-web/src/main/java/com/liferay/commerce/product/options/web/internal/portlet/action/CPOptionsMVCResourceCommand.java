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

package com.liferay.commerce.product.options.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPOptionService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_OPTIONS,
		"mvc.command.name=cpOptions"
	},
	service = MVCResourceCommand.class
)
public class CPOptionsMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String keyword = ParamUtil.getString(resourceRequest, "keyword");

		Sort sort = SortFactoryUtil.create("title", Sort.STRING_TYPE, false);

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		BaseModelSearchResult<CPOption> cpOptionsSearchResult =
			_cpOptionService.searchCPOptions(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
				keyword, QueryUtil.ALL_POS, QueryUtil.ALL_POS, sort);

		for (CPOption cpOption : cpOptionsSearchResult.getBaseModels()) {
			JSONObject jsonObject = _jsonFactory.createJSONObject();

			jsonObject.put("cpOptionId", cpOption.getCPOptionId());
			jsonObject.put("key", cpOption.getKey());
			jsonObject.put(
				"name", cpOption.getName(themeDisplay.getLanguageId()));

			Map<String, Object> properties =
				_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeProperties(
					cpOption.getDDMFormFieldTypeName());

			String fieldTypeDataDomain = MapUtil.getString(
				properties, "ddm.form.field.type.data.domain");

			if (Validator.isNotNull(fieldTypeDataDomain)) {
				if (fieldTypeDataDomain.equals("list")) {
					jsonObject.put("hasValues", true);
				}
			}

			jsonArray.put(jsonObject);
		}

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(resourceResponse);

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		ServletResponseUtil.write(httpServletResponse, jsonArray.toString());
	}

	@Reference
	private CPOptionService _cpOptionService;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}