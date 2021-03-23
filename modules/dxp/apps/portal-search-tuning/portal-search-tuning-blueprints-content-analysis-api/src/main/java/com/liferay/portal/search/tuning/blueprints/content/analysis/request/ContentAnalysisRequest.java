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

package com.liferay.portal.search.tuning.blueprints.content.analysis.request;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class ContentAnalysisRequest {

	public ContentAnalysisRequest(
		ContentAnalysisRequest contentAnalysisRequest) {

		_mimeType = contentAnalysisRequest._mimeType;
		_parameters = contentAnalysisRequest._parameters;
		_payload = contentAnalysisRequest._payload;
	}

	public String getMimeType() {
		return _mimeType;
	}

	public String getParameter(String key) {
		return _parameters.get(key);
	}

	public Map<String, String> getParameters() {
		return _parameters;
	}

	public Object getPayload() {
		return _payload;
	}

	public String getPayloadAsString() {
		return (String)_payload;
	}

	public static class Builder {

		public Builder() {
			_contentAnalysisRequest = new ContentAnalysisRequest();
		}

		public Builder(ContentAnalysisRequest contentAnalysisRequest) {
			_contentAnalysisRequest = contentAnalysisRequest;
		}

		public Builder addParameter(String key, String value) {
			_contentAnalysisRequest._parameters.put(key, value);

			return this;
		}

		public ContentAnalysisRequest build() {
			return new ContentAnalysisRequest(_contentAnalysisRequest);
		}

		public Builder mimeType(String mimeType) {
			_contentAnalysisRequest._mimeType = mimeType;

			return this;
		}

		public Builder payload(Object payload) {
			_contentAnalysisRequest._payload = payload;

			return this;
		}

		private final ContentAnalysisRequest _contentAnalysisRequest;

	}

	private ContentAnalysisRequest() {
	}

	private String _mimeType;
	private Map<String, String> _parameters = new HashMap<>();
	private Object _payload;

}