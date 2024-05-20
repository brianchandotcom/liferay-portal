/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.batch;

/**
 * @author Kenji Heigel
 */
public abstract class BaseTestBatch<T extends BaseTestSelector>
	implements TestBatch<T> {

	public BaseTestBatch(String name) {
		this(name, null);
	}

	public BaseTestBatch(String name, T testSelector) {
		_name = name;
		_testSelector = testSelector;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public T getTestSelector() {
		return _testSelector;
	}

	@Override
	public void merge(TestBatch testBatch) {
		if (_name.equals(testBatch.getName())) {
			T testSelector = getTestSelector();

			testSelector.merge(testBatch.getTestSelector());
		}
	}

	@Override
	public void setTestSelector(T testSelector) {
		_testSelector = testSelector;
	}

	private final String _name;
	private T _testSelector;

}