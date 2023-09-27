/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.model;

import com.liferay.portal.kernel.json.JSON;

import java.io.Serializable;

import java.util.List;

/**
 * @author Igor Spasic
 */
public class AssetTagDisplay implements Serializable {

	public AssetTagDisplay() {
	}

	public AssetTagDisplay(List<AssetTag> tags, int total, int start, int end) {
		_tags = tags;
		_total = total;
		_start = start;
		_end = end;
	}

	public int getEnd() {
		return _end;
	}

	public int getPage() {
		if ((_end > 0) && (_start >= 0)) {
			return _end / (_end - _start);
		}

		return 0;
	}

	public int getStart() {
		return _start;
	}

	public List<AssetTag> getTags() {
		return _tags;
	}

	public int getTotal() {
		return _total;
	}

	public void setEnd(int end) {
		_end = end;
	}

	public void setStart(int start) {
		_start = start;
	}

	public void setTags(List<AssetTag> tags) {
		_tags = tags;
	}

	public void setTotal(int total) {
		_total = total;
	}

	private int _end;
	private int _start;

	@JSON
	private List<AssetTag> _tags;

	private int _total;

}