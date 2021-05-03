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

package com.liferay.portal.search.tuning.blueprints.content.analysis.response;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.tuning.blueprints.content.analysis.constants.ModerationReason;

/**
 * @author Petteri Karttunen
 */
public class ModerationAnalysisResponse {

	public ModerationAnalysisResponse(
		ModerationAnalysisResponse moderationAnalysisResponse) {

		_detailsJSONObject = moderationAnalysisResponse._detailsJSONObject;
		_moderationReason = moderationAnalysisResponse._moderationReason;
		_moderationRecommended =
			moderationAnalysisResponse._moderationRecommended;
		_reporter = moderationAnalysisResponse._reporter;
	}

	public JSONObject getDetailsJSONObject() {
		return _detailsJSONObject;
	}

	public ModerationReason getModerationReason() {
		return _moderationReason;
	}

	public String getReporter() {
		return _reporter;
	}

	public boolean isModerationRecommended() {
		return _moderationRecommended;
	}

	public void setModerationReason(ModerationReason moderationReason) {
		_moderationReason = moderationReason;
	}

	public void setModerationRecommended(boolean moderationRecommended) {
		_moderationRecommended = moderationRecommended;
	}

	public void setReporter(String reporter) {
		_reporter = reporter;
	}

	public static class ModerationAnalysisResponseBuilder {

		public ModerationAnalysisResponseBuilder() {
			_moderationAnalysisResponse = new ModerationAnalysisResponse();
		}

		public ModerationAnalysisResponseBuilder(
			ModerationAnalysisResponse moderationAnalysisResponse) {

			_moderationAnalysisResponse = moderationAnalysisResponse;
		}

		public ModerationAnalysisResponse build() {
			return new ModerationAnalysisResponse(_moderationAnalysisResponse);
		}

		public ModerationAnalysisResponseBuilder details(
			JSONObject detailsJSONObject) {

			_moderationAnalysisResponse._detailsJSONObject = detailsJSONObject;

			return this;
		}

		public ModerationAnalysisResponseBuilder moderationReason(
			ModerationReason moderationReason) {

			_moderationAnalysisResponse._moderationReason = moderationReason;

			return this;
		}

		public ModerationAnalysisResponseBuilder moderationRecommended(
			boolean moderationRecommended) {

			_moderationAnalysisResponse._moderationRecommended =
				moderationRecommended;

			return this;
		}

		public ModerationAnalysisResponseBuilder reporter(String reporter) {
			_moderationAnalysisResponse._reporter = reporter;

			return this;
		}

		private final ModerationAnalysisResponse _moderationAnalysisResponse;

	}

	private ModerationAnalysisResponse() {
	}

	private JSONObject _detailsJSONObject;
	private ModerationReason _moderationReason;
	private boolean _moderationRecommended;
	private String _reporter;

}