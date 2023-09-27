/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.item.selector.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutPageTemplateEntryItemSelectorCriterion
	extends BaseItemSelectorCriterion {

	public LayoutPageTemplateEntryItemSelectorCriterion() {
		_groupId = 0;
		_layoutTypes = new int[0];
		_status = WorkflowConstants.STATUS_APPROVED;
	}

	public long getGroupId() {
		return _groupId;
	}

	public int[] getLayoutTypes() {
		return _layoutTypes;
	}

	public int getStatus() {
		return _status;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setLayoutTypes(int... layoutTypes) {
		_layoutTypes = layoutTypes;
	}

	public void setStatus(int status) {
		_status = status;
	}

	private long _groupId;
	private int[] _layoutTypes;
	private int _status;

}