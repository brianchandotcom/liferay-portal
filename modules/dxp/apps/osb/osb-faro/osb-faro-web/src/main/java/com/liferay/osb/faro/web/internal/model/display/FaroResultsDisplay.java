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

package com.liferay.osb.faro.web.internal.model.display;

import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.petra.function.transform.TransformUtil;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * @author Shinn Lok
 */
public class FaroResultsDisplay<T> {

	public FaroResultsDisplay() {
	}

	public FaroResultsDisplay(List<?> items, int total) {
		_items = items;
		_total = total;
	}

	public FaroResultsDisplay(Results<?> results) {
		_items = results.getItems();
		_total = results.getTotal();
	}

	public FaroResultsDisplay(Results<T> results, Function<T, ?> function) {
		this(results, function, false);
	}

	public FaroResultsDisplay(
		Results<T> results, Function<T, ?> function, boolean disableSearch) {

		_disableSearch = disableSearch;

		_items = TransformUtil.transform(
			results.getItems(), item -> function.apply(item));

		_total = results.getTotal();
	}

	public List<?> getItems() {
		return _items;
	}

	public int getTotal() {
		return _total;
	}

	private boolean _disableSearch;
	private List<?> _items = Collections.emptyList();
	private int _total;

}