/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image;

/**
 * @author Daniel Sanz
 */
public final class ResponsiveImageCandidate {

	public static Builder builder(String url) {
		return new Builder(url);
	}

	public String getURL() {
		return _url;
	}

	/**
	 * Returns the width in pixels that the image is served at, or
	 * <code>null</code>. A candidate with no width is left out of the markup
	 * because there is no width to advertise it by.
	 */
	public Integer getWidth() {
		return _width;
	}

	public static class Builder {

		public ResponsiveImageCandidate build() {
			return new ResponsiveImageCandidate(_url, _width);
		}

		public Builder width(Integer width) {
			_width = width;

			return this;
		}

		private Builder(String url) {
			_url = url;
		}

		private final String _url;
		private Integer _width;

	}

	private ResponsiveImageCandidate(String url, Integer width) {
		_url = url;
		_width = width;
	}

	private final String _url;
	private final Integer _width;

}