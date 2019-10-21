/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.asah.connector.internal.client.model;

import java.util.Collections;
import java.util.List;

/**
 * @author Shinn Lok
 * @author David Arques
 */
public class Results<T> {

	public Results() {
	}

	public Results(List<T> items, int total) {
		_items = items;
		_total = total;
	}

	public List<T> getItems() {
		return _items;
	}

	public int getTotal() {
		return _total;
	}

	public void setItems(List<T> items) {
		_items = items;
	}

	public void setTotal(int total) {
		_total = total;
	}

	private List<T> _items = Collections.emptyList();
	private int _total;

}