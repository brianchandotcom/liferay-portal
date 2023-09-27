/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.criteria.info.item.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Eudaldo Alonso
 */
public class InfoItemItemSelectorCriterion extends BaseItemSelectorCriterion {

	public String getItemSubtype() {
		return _itemSubtype;
	}

	public String getItemType() {
		return _itemType;
	}

	public String[] getMimeTypes() {
		return _mimeTypes;
	}

	public long getRefererClassPK() {
		return _refererClassPK;
	}

	public int getStatus() {
		return _status;
	}

	public boolean isMultiSelection() {
		return _multiSelection;
	}

	public void setItemSubtype(String itemSubtype) {
		_itemSubtype = itemSubtype;
	}

	public void setItemType(String itemType) {
		_itemType = itemType;
	}

	public void setMimeTypes(String[] mimeTypes) {
		_mimeTypes = mimeTypes;
	}

	public void setMultiSelection(boolean multiSelection) {
		_multiSelection = multiSelection;
	}

	public void setRefererClassPK(long refererClassPK) {
		_refererClassPK = refererClassPK;
	}

	public void setStatus(int status) {
		_status = status;
	}

	private String _itemSubtype;
	private String _itemType;
	private String[] _mimeTypes;
	private boolean _multiSelection;
	private long _refererClassPK;
	private int _status = WorkflowConstants.STATUS_APPROVED;

}