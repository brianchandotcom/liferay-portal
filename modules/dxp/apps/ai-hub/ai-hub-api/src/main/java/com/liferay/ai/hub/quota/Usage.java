/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.quota;

/**
 * @author Carolina Barbosa
 */
public class Usage {

	public static Usage.Builder builder() {
		return new Usage.Builder();
	}

	public Source getSource() {
		return _source;
	}

	public String getText() {
		return _text;
	}

	public long getTokenCount() {
		return _tokenCount;
	}

	public static class Builder {

		public Usage build() {
			return new Usage(this);
		}

		public Builder source(Source source) {
			_source = source;

			return this;
		}

		public Builder text(String text) {
			_text = text;

			return this;
		}

		public Builder tokenCount(long tokenCount) {
			_tokenCount = tokenCount;

			return this;
		}

		private Source _source;
		private String _text;
		private long _tokenCount;

	}

	private Usage(Usage.Builder builder) {
		_source = builder._source;
		_text = builder._text;
		_tokenCount = builder._tokenCount;
	}

	private final Source _source;
	private final String _text;
	private final long _tokenCount;

}