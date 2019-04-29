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

package com.liferay.talend.configuration;

import com.liferay.talend.dataset.InputDataSet;

import java.io.Serializable;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.constraint.Max;
import org.talend.sdk.component.api.configuration.constraint.Min;
import org.talend.sdk.component.api.configuration.ui.DefaultValue;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayouts;

/**
 * @author Zoltán Takács
 * @review
 */
@GridLayouts(
	{
		@GridLayout(
			names = GridLayout.FormType.MAIN,
			value = @GridLayout.Row("_inputDataSet")
		),
		@GridLayout(
			names = GridLayout.FormType.ADVANCED,
			value = {
				@GridLayout.Row("_timeout"), @GridLayout.Row("_batchSize"),
				@GridLayout.Row("_sort")
			}
		)
	}
)
public class LiferayInputMapperConfiguration implements Serializable {

	public LiferayInputMapperConfiguration() {
	}

	public LiferayInputMapperConfiguration(
		LiferayInputMapperConfiguration liferayInputMapperConfiguration) {

		_batchSize = liferayInputMapperConfiguration._batchSize;
		_inputDataSet = liferayInputMapperConfiguration._inputDataSet;
		_page = liferayInputMapperConfiguration._page;
		_pageSize = liferayInputMapperConfiguration._pageSize;
		_segmentPages = liferayInputMapperConfiguration._segmentPages;
		_sort = liferayInputMapperConfiguration._sort;
		_timeout = liferayInputMapperConfiguration._timeout;
	}

	public int getBatchSize() {
		return _batchSize;
	}

	public InputDataSet getInputDataSet() {
		return _inputDataSet;
	}

	public int getPage() {
		return _page;
	}

	public int getPageSize() {
		return _pageSize;
	}

	public int getSegmentPages() {
		return _segmentPages;
	}

	public String getSort() {
		return _sort;
	}

	public int getTimeout() {
		return _timeout;
	}

	public LiferayInputMapperConfiguration setBatchSize(int batchSize) {
		_batchSize = batchSize;

		return this;
	}

	public LiferayInputMapperConfiguration setInputDataSet(
		InputDataSet inputDataSet) {

		_inputDataSet = inputDataSet;

		return this;
	}

	public LiferayInputMapperConfiguration setPage(int page) {
		_page = page;

		return this;
	}

	public LiferayInputMapperConfiguration setPageSize(int pageSize) {
		_pageSize = pageSize;

		return this;
	}

	public LiferayInputMapperConfiguration setSegmentPages(int segmentPages) {
		_segmentPages = segmentPages;

		return this;
	}

	public LiferayInputMapperConfiguration setSort(String sort) {
		_sort = sort;

		return this;
	}

	public LiferayInputMapperConfiguration setTimeout(int timeout) {
		_timeout = timeout;

		return this;
	}

	@Max(10000)
	@Min(1)
	@Option
	private int _batchSize = 100;

	@Option
	private InputDataSet _inputDataSet;

	private int _page = 1;
	private int _pageSize = _batchSize;
	private int _segmentPages = 1;

	@DefaultValue("dateCreated")
	@Option
	private String _sort;

	@Option
	private int _timeout = 60000;

}