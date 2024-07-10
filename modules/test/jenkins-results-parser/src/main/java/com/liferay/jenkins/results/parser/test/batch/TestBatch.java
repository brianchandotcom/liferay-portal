/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.batch;

/**
 * @author Kenji Heigel
 */
public interface TestBatch<T extends TestSelector>
	extends Comparable<TestBatch<T>> {

	public String getName();

	public T getTestSelector();

	public void merge(TestBatch<T> testBatch);

	public void setTestSelector(T testSelector);

}