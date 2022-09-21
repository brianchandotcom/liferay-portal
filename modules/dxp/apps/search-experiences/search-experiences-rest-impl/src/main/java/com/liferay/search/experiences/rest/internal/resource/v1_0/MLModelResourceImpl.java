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

package com.liferay.search.experiences.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.search.experiences.rest.dto.v1_0.MLModel;
import com.liferay.search.experiences.rest.resource.v1_0.MLModelResource;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/ml-model.properties",
	scope = ServiceScope.PROTOTYPE, service = MLModelResource.class
)
public class MLModelResourceImpl extends BaseMLModelResourceImpl {

	@Override
	public Page<MLModel> getMLModelsPage(
			Integer limit, String pipelineTag, String query, String tag)
		throws Exception {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-163688"))) {
			return null;
		}

		return Page.of(_getMLodels(limit, pipelineTag, query, tag));
	}

	private String _getApiURL(
		Integer limit, String pipelineTag, String query, String tag) {

		String apiURL = _HUGGING_FACE_MODELS_API_URL + "?";

		if (limit != null) {
			apiURL += "limit=" + limit;
		}

		if (!Validator.isBlank(pipelineTag)) {
			apiURL += "&pipeline_tag=" + pipelineTag;
		}

		if (!Validator.isBlank(query)) {
			apiURL += "&search=" + query;
		}

		if (!Validator.isBlank(tag)) {
			apiURL += "&tag=" + tag;
		}

		return apiURL;
	}

	private List<MLModel> _getMLodels(
		Integer limit, String pipelineTag, String query, String tag) {

		List<MLModel> availableModels = new ArrayList<>();

		try {
			JSONArray jsonArray = _jsonFactory.createJSONArray(
				_http.URLtoString(_getApiURL(limit, pipelineTag, query, tag)));

			jsonArray.forEach(
				item -> {
					JSONObject jsonObject = (JSONObject)item;

					availableModels.add(
						new MLModel() {
							{
								modelId = jsonObject.getString("modelId");
							}
						});
				});
		}
		catch (IOException | JSONException exception) {
			_log.error(exception);
		}

		return availableModels;
	}

	private static final String _HUGGING_FACE_MODELS_API_URL =
		"https://huggingface.co/api/models";

	private static final Log _log = LogFactoryUtil.getLog(
		MLModelResourceImpl.class);

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

}