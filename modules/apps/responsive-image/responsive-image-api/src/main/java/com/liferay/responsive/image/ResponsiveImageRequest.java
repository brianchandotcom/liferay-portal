/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Represents a request for a responsive image, carrying everything one call
 * needs: which image and the placement context it is being rendered in.
 *
 * @author Daniel Sanz
 */
public final class ResponsiveImageRequest {

	public static Builder builder(ImageResource imageResource) {
		return new Builder(imageResource);
	}

	/**
	 * Returns a request for the given image, when placement information is not
	 * available to the caller.
	 *
	 * @param  imageResource the image to render responsively
	 * @return the request
	 */
	public static ResponsiveImageRequest of(ImageResource imageResource) {
		return builder(
			imageResource
		).build();
	}

	/**
	 * Returns the site the caller declared, or <code>0</code> to have one
	 * derived. The group is the <em>rendering</em> site, not the site the image
	 * is stored in.
	 *
	 * <p>
	 * Left at <code>0</code> the framework takes the rendering site from the
	 * theme display of {@link #getHttpServletRequest}, then from the service
	 * context, and failing both resolves the configuration at company scope.
	 * </p>
	 *
	 * <p>
	 * It is worth declaring only when there is no request to derive it from,
	 * which is the case for export and import, staging, and scheduled work.
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
	 * It is worth supplying when available, because it resolves the CDN host
	 * exactly, knows whether the connection is secure, and identifies the
	 * company. Callers that have one should pass it.
	 * </p>
	 *
	 * <p>
	 * It is optional rather than required, because several rendering paths
	 * genuinely have none to give. Without it, the company comes from the
	 * ambient one and the CDN host is resolved per company rather than per
	 * request.
	 * </p>
	 */
	public HttpServletRequest getHttpServletRequest() {
		return _httpServletRequest;
	}

	/**
	 * Returns the image to render responsively.
	 */
	public ImageResource getImageResource() {
		return _imageResource;
	}

	/**
	 * Returns the name of the configured preset describing where this image
	 * sits in the page layout (for example <code>card</code>), or
	 * <code>null</code> to use the default.
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