/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.facet.display.context;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Bryan Engler
 */
public class BucketDisplayContext {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #getFilterValue()}
	 */
	@Deprecated
	public long getAssetCategoryId() {
		return GetterUtil.getLong(_filterValue);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #getFilterValue()}
	 */
	@Deprecated
	public String getAssetType() {
		return _filterValue;
	}

	public String getBucketText() {
		return _bucketText;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #getFrequency()}
	 */
	@Deprecated
	public int getCount() {
		return _frequency;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #getBucketText()}
	 */
	@Deprecated
	public String getDescriptiveName() {
		return _bucketText;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #getBucketText()}
	 */
	@Deprecated
	public String getDisplayName() {
		return _bucketText;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #getBucketText()} or {@link #getFilterValue()}
	 */
	@Deprecated
	public String getFieldName() {
		return _bucketText;
	}

	public String getFilterValue() {
		return _filterValue;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #getFilterValue()}
	 */
	@Deprecated
	public long getFolderId() {
		return GetterUtil.getLong(_filterValue);
	}

	public int getFrequency() {
		return _frequency;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #getFilterValue()}
	 */
	@Deprecated
	public long getGroupId() {
		return GetterUtil.getLong(_filterValue);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #getBucketText()}
	 */
	@Deprecated
	public String getLabel() {
		return _bucketText;
	}

	public int getPopularity() {
		return _popularity;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #getFilterValue()}
	 */
	@Deprecated
	public String getRangeURL() {
		return _filterValue;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #getBucketText()}
	 */
	@Deprecated
	public String getTypeName() {
		return _bucketText;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #getBucketText()}
	 */
	@Deprecated
	public String getUserName() {
		return _bucketText;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #getFilterValue()}
	 */
	@Deprecated
	public String getValue() {
		return _filterValue;
	}

	public boolean isFrequencyVisible() {
		return _frequencyVisible;
	}

	public boolean isSelected() {
		return _selected;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #isFrequencyVisible()}
	 */
	@Deprecated
	public boolean isShowCount() {
		return _frequencyVisible;
	}

	public void setBucketText(String bucketText) {
		_bucketText = bucketText;
	}

	public void setFilterValue(String filterValue) {
		_filterValue = filterValue;
	}

	public void setFrequency(int frequency) {
		_frequency = frequency;
	}

	public void setFrequencyVisible(boolean frequencyVisible) {
		_frequencyVisible = frequencyVisible;
	}

	public void setPopularity(int popularity) {
		_popularity = popularity;
	}

	public void setSelected(boolean selected) {
		_selected = selected;
	}

	private String _bucketText;
	private String _filterValue;
	private int _frequency;
	private boolean _frequencyVisible;
	private int _popularity;
	private boolean _selected;

}