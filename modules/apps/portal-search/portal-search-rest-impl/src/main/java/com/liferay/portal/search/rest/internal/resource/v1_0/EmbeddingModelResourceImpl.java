/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.rest.internal.resource.v1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.rest.dto.v1_0.EmbeddingModel;
import com.liferay.portal.search.rest.resource.v1_0.EmbeddingModelResource;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Petteri Karttunen
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/embedding-model.properties",
	scope = ServiceScope.PROTOTYPE, service = EmbeddingModelResource.class
)
public class EmbeddingModelResourceImpl extends BaseEmbeddingModelResourceImpl {

	@Override
	public Page<EmbeddingModel> getEmbeddingEmbeddingModelsPage(
			String provider, String search, Pagination pagination)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-122920")) {
			throw new NotFoundException();
		}

		if (Validator.isBlank(provider)) {
			return null;
		}

		if (provider.equals("huggingFaceInferenceAPI")) {
			return Page.of(
				_getHuggingFaceModels(
					_getHuggingFaceAPIURL(pagination, search)));
		}

		return Page.of(Collections.emptyList());
	}

	private String _getHuggingFaceAPIURL(Pagination pagination, String search) {
		StringBundler sb = new StringBundler(5);

		sb.append("https://huggingface.co/api/models?limit=");
		sb.append(pagination.getPageSize());
		sb.append("&pipeline_tag=feature-extraction");

		if (!Validator.isBlank(search)) {
			sb.append("&search=");
			sb.append(URLCodec.encodeURL(search, false));
		}

		return sb.toString();
	}

	private List<EmbeddingModel> _getHuggingFaceModels(String apiURL) {
		List<EmbeddingModel> embeddingModels = new ArrayList<>();

		try {
			JSONArray jsonArray = _jsonFactory.createJSONArray(
				_http.URLtoString(apiURL));

			jsonArray.forEach(
				object -> {
					JSONObject jsonObject = (JSONObject)object;

					embeddingModels.add(
						new EmbeddingModel() {
							{
								setModelId(
									() -> jsonObject.getString("modelId"));
							}
						});
				});
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return embeddingModels;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EmbeddingModelResourceImpl.class);

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

}