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

/**
 * @author Ivica Cardic
 */
public class LCSSubscriptionEntryMessage extends Message {

	public long getCorpProjectId() {
		return _corpProjectId;
	}

	public String getLcsSubscriptionEntriesJSON() {
		return _lcsSubscriptionEntriesJSON;
	}

	public void setCorpProjectId(long corpProjectId) {
		_corpProjectId = corpProjectId;
	}

	public void setLcsSubscriptionEntriesJSON(
		String lcsSubscriptionEntriesJSON) {

		_lcsSubscriptionEntriesJSON = lcsSubscriptionEntriesJSON;
	}

	private long _corpProjectId;
	private String _lcsSubscriptionEntriesJSON;

}