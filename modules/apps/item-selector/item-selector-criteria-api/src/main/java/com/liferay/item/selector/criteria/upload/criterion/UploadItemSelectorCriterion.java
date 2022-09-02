/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.item.selector.criteria.upload.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Ambrín Chaudhary
 */
public class UploadItemSelectorCriterion extends BaseItemSelectorCriterion {

	public static Builder builder() {
		return new Builder() {

			@Override
			public UploadItemSelectorCriterion build() {
				return _uploadItemSelectorCriterion;
			}

			@Override
			public Builder setDesiredItemSelectorReturnTypes(
				ItemSelectorReturnType... desiredItemSelectorReturnTypes) {

				_uploadItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
					desiredItemSelectorReturnTypes);

				return this;
			}

			@Override
			public Builder setExtensions(String[] extensions) {
				_uploadItemSelectorCriterion.setExtensions(extensions);

				return this;
			}

			@Override
			public Builder setMaxFileSize(long maxFileSize) {
				_uploadItemSelectorCriterion.setMaxFileSize(maxFileSize);

				return this;
			}

			@Override
			public Builder setMimeTypeRestriction(String mimeTypeRestriction) {
				_uploadItemSelectorCriterion._setMimeTypeRestriction(
					mimeTypeRestriction);

				return this;
			}

			@Override
			public Builder setPortletId(String portletId) {
				_uploadItemSelectorCriterion.setPortletId(portletId);

				return this;
			}

			@Override
			public Builder setRepositoryName(String repositoryName) {
				_uploadItemSelectorCriterion.setRepositoryName(repositoryName);

				return this;
			}

			@Override
			public Builder setURL(String url) {
				_uploadItemSelectorCriterion.setURL(url);

				return this;
			}

			private final UploadItemSelectorCriterion
				_uploadItemSelectorCriterion =
					new UploadItemSelectorCriterion();

		};
	}

	public String[] getExtensions() {
		return _extensions;
	}

	public long getMaxFileSize() {
		return _maxFileSize;
	}

	@Override
	public String getMimeTypeRestriction() {
		if (Validator.isNull(_mimeTypeRestriction)) {
			super.getMimeTypeRestriction();
		}

		return _mimeTypeRestriction;
	}

	public String getPortletId() {
		return _portletId;
	}

	public String getRepositoryName() {
		return _repositoryName;
	}

	public String getURL() {
		return _url;
	}

	public void setExtensions(String[] extensions) {
		_extensions = extensions;
	}

	public void setMaxFileSize(long maxFileSize) {
		_maxFileSize = maxFileSize;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	public void setRepositoryName(String repositoryName) {
		_repositoryName = repositoryName;
	}

	public void setURL(String url) {
		_url = url;
	}

	public interface Builder {

		public UploadItemSelectorCriterion build();

		public Builder setDesiredItemSelectorReturnTypes(
			ItemSelectorReturnType... desiredItemSelectorReturnTypes);

		public Builder setExtensions(String[] extensions);

		public Builder setMaxFileSize(long maxFileSize);

		public Builder setMimeTypeRestriction(String mimeTypeRestriction);

		public Builder setPortletId(String portletId);

		public Builder setRepositoryName(String repositoryName);

		public Builder setURL(String url);

	}

	private void _setMimeTypeRestriction(String mimeTypeRestriction) {
		_mimeTypeRestriction = mimeTypeRestriction;
	}

	private String[] _extensions;
	private long _maxFileSize;
	private String _mimeTypeRestriction;
	private String _portletId;
	private String _repositoryName;
	private String _url;

}