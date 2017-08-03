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

package com.liferay.lcs.messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class SendPatchesResponseMessage extends ResponseMessage {

	public List<String> getFixedIssues() {
		return _fixedIssues;
	}

	public String getHashCode() {
		return _hashCode;
	}

	public Map<String, Integer> getPatchIdsStatuses() {
		return _patchIdsStatuses;
	}

	public void setFixedIssues(List<String> fixedIssues) {
		_fixedIssues = fixedIssues;
	}

	public void setHashCode(String hashCode) {
		_hashCode = hashCode;
	}

	public void setPatchIdsStatuses(Map<String, Integer> patchIdsStatuses) {
		_patchIdsStatuses = patchIdsStatuses;
	}

	private List<String> _fixedIssues = new ArrayList<String>();
	private String _hashCode;
	private Map<String, Integer> _patchIdsStatuses =
		new HashMap<String, Integer>();

}