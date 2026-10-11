/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a resolved image, ready to be rendered responsively in a format
 * such as HTML or JSON.
 *
 * @author Daniel Sanz
 */
public final class ResponsiveImage {

	/**
	 * Returns a builder for a responsive image.
	 *
	 * @return the builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Returns the sources, or an empty list when nothing could support the
	 * resource. Empty is not an error, because callers fall back to the image
	 * they already have. Order is significant: a source with a narrower media
	 * condition must precede a broader one, because a browser takes the first
	 * one that matches.
	 */
	public List<ResponsiveImageSource> getSources() {
		return _responsiveImageSources;
	}

	/**
	 * Returns whether this image should be loaded lazily.
	 */
	public boolean isLazyLoading() {
		return _lazyLoading;
	}

	public static class Builder {

		public ResponsiveImage build() {
			return new ResponsiveImage(_lazyLoading, _responsiveImageSources);
		}

		public Builder lazyLoading(boolean lazyLoading) {
			_lazyLoading = lazyLoading;

			return this;
		}

		public Builder sources(
			List<ResponsiveImageSource> responsiveImageSources) {

			_responsiveImageSources = responsiveImageSources;

			return this;
		}

		private Builder() {
		}

		private boolean _lazyLoading;
		private List<ResponsiveImageSource> _responsiveImageSources =
			Collections.emptyList();

	}

	private ResponsiveImage(
		boolean lazyLoading,
		List<ResponsiveImageSource> responsiveImageSources) {

		_lazyLoading = lazyLoading;
		_responsiveImageSources = Collections.unmodifiableList(
			new ArrayList<>(responsiveImageSources));
	}

	private final boolean _lazyLoading;
	private final List<ResponsiveImageSource> _responsiveImageSources;

}