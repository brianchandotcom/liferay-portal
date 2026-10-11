/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the image candidates that apply under one media condition,
 * together with the media query itself and how wide the image renders under it.
 *
 * @author Daniel Sanz
 */
public final class ResponsiveImageSource {

	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Returns the candidates a browser chooses between.
	 */
	public List<ResponsiveImageCandidate> getCandidates() {
		return _responsiveImageCandidates;
	}

	/**
	 * Returns the media query this applies under, or <code>null</code> when it
	 * applies unconditionally, in which case it is rendered as the
	 * <code>&lt;img&gt;</code> rather than as a <code>&lt;source&gt;</code>.
	 */
	public String getQuery() {
		return _query;
	}

	/**
	 * Returns the <code>sizes</code> attribute describing how wide the image
	 * renders under this query, or <code>null</code>.
	 */
	public String getSizes() {
		return _sizes;
	}

	public static class Builder {

		public ResponsiveImageSource build() {
			return new ResponsiveImageSource(
				_query, _responsiveImageCandidates, _sizes);
		}

		public Builder candidates(
			List<ResponsiveImageCandidate> responsiveImageCandidates) {

			_responsiveImageCandidates = responsiveImageCandidates;

			return this;
		}

		public Builder query(String query) {
			_query = query;

			return this;
		}

		public Builder sizes(String sizes) {
			_sizes = sizes;

			return this;
		}

		private Builder() {
		}

		private String _query;
		private List<ResponsiveImageCandidate> _responsiveImageCandidates =
			Collections.emptyList();
		private String _sizes;

	}

	private ResponsiveImageSource(
		String query, List<ResponsiveImageCandidate> responsiveImageCandidates,
		String sizes) {

		_query = query;
		_responsiveImageCandidates = Collections.unmodifiableList(
			new ArrayList<>(responsiveImageCandidates));
		_sizes = sizes;
	}

	private final String _query;
	private final List<ResponsiveImageCandidate> _responsiveImageCandidates;
	private final String _sizes;

}