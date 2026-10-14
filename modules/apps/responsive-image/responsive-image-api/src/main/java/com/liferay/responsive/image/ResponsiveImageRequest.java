/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Daniel Sanz
 */
public final class ResponsiveImageRequest {

	public static Builder builder(ImageResource imageResource) {
		return new Builder(imageResource);
	}

	public static ResponsiveImageRequest of(ImageResource imageResource) {
		return builder(
			imageResource
		).build();
	}

	/**
	 * Returns the site the caller declared, or <code>0</code> to have one
	 * derived. The group is the site that renders the image, not the site that
	 * stores it.
	 *
	 * <p>
	 * When the group ID is <code>0</code>, the framework takes the rendering
	 * site from the theme display of {@link #getHttpServletRequest}, then from
	 * the service context. Failing both, it resolves the configuration at the
	 * company scope.
	 * </p>
	 *
	 * <p>
	 * Declare it only when there is no request to derive it from, as in export
	 * and import, staging, and scheduled work.
	 * </p>
	 */
	public long getGroupId() {
		return _groupId;
	}

	/**
	 * Returns the request being served, or <code>null</code> if the caller has
	 * none.
	 *
	 * <p>
	 * Callers that have a request should pass it because it resolves the CDN
	 * host exactly, tells whether the connection is secure, and identifies the
	 * company. Without it, the company comes from the ambient one and the CDN
	 * host is resolved per company rather than per request.
	 * </p>
	 */
	public HttpServletRequest getHttpServletRequest() {
		return _httpServletRequest;
	}

	public ImageResource getImageResource() {
		return _imageResource;
	}

	/**
	 * Returns the name of the configured preset that describes where this image
	 * sits in the page layout, such as <code>card</code>, or <code>null</code>
	 * to use the default.
	 */
	public String getPresetName() {
		return _presetName;
	}

	public static class Builder {

		public ResponsiveImageRequest build() {
			return new ResponsiveImageRequest(
				_groupId, _httpServletRequest, _imageResource, _presetName);
		}

		public Builder groupId(long groupId) {
			_groupId = groupId;

			return this;
		}

		public Builder httpServletRequest(
			HttpServletRequest httpServletRequest) {

			_httpServletRequest = httpServletRequest;

			return this;
		}

		public Builder presetName(String presetName) {
			_presetName = presetName;

			return this;
		}

		private Builder(ImageResource imageResource) {
			if (imageResource == null) {
				throw new IllegalArgumentException("Image resource is null");
			}

			_imageResource = imageResource;
		}

		private long _groupId;
		private HttpServletRequest _httpServletRequest;
		private final ImageResource _imageResource;
		private String _presetName;

	}

	private ResponsiveImageRequest(
		long groupId, HttpServletRequest httpServletRequest,
		ImageResource imageResource, String presetName) {

		_groupId = groupId;
		_httpServletRequest = httpServletRequest;
		_imageResource = imageResource;
		_presetName = presetName;
	}

	private final long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private final ImageResource _imageResource;
	private final String _presetName;

}