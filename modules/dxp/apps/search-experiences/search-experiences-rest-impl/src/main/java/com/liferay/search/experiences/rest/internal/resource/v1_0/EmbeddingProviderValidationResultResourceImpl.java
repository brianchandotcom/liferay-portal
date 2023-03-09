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

import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.ml.embedding.EmbeddingProviderStatus;
import com.liferay.search.experiences.ml.embedding.text.TextEmbeddingRetriever;
import com.liferay.search.experiences.rest.dto.v1_0.EmbeddingProviderConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.EmbeddingProviderValidationResult;
import com.liferay.search.experiences.rest.resource.v1_0.EmbeddingProviderValidationResultResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/embedding-provider-validation-result.properties",
	scope = ServiceScope.PROTOTYPE,
	service = EmbeddingProviderValidationResultResource.class
)
public class EmbeddingProviderValidationResultResourceImpl
	extends BaseEmbeddingProviderValidationResultResourceImpl {

	@Override
	public EmbeddingProviderValidationResult
		postTextEmbeddingValidateProviderConfiguration(
			EmbeddingProviderConfiguration embeddingProviderConfiguration) {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-122920")) {
			return null;
		}

		try {
			EmbeddingProviderStatus embeddingProviderStatus =
				_textEmbeddingRetriever.getEmbeddingProviderStatus(
					embeddingProviderConfiguration.toString());

			return new EmbeddingProviderValidationResult() {
				{
					if (!Validator.isBlank(
							embeddingProviderStatus.getErrorMessage())) {

						errorMessage =
							embeddingProviderStatus.getErrorMessage();
					}
					else {
						expectedDimensions =
							embeddingProviderStatus.
								getEmbeddingVectorDimensions();
					}
				}
			};
		}
		catch (Exception exception) {
			return new EmbeddingProviderValidationResult() {
				{
					errorMessage = exception.getMessage();
				}
			};
		}
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private TextEmbeddingRetriever _textEmbeddingRetriever;

}