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

package com.liferay.portal.tools.service.builder;

import com.liferay.portal.model.BaseModelHintsImpl;
import com.liferay.portal.model.ModelHintsCallback;
import com.liferay.portal.xml.SAXReaderFactory;

import org.dom4j.io.SAXReader;

/**
 * @author Raymond Augé
 */
public class ModelHintsImpl extends BaseModelHintsImpl {

	@Override
	public ModelHintsCallback getModelHintsCallback() {
		return _modelHintsCallback;
	}

	@Override
	public String[] getModelHintsConfigs() {
		return _modelHintsConfigs;
	}

	@Override
	public SAXReader getSAXReader() {
		return SAXReaderFactory.getSAXReader(null, true, true);
	}

	public void setModelHintsConfigs(String[] modelHintsConfigs) {
		_modelHintsConfigs = modelHintsConfigs;
	}

	private final ModelHintsCallback _modelHintsCallback =
		new CompileTimeModelHintsCallback();
	private String[] _modelHintsConfigs;

	private static class CompileTimeModelHintsCallback
		implements ModelHintsCallback {

		@Override
		public void execute(ClassLoader classLoader, String name) {
		}

	}

}