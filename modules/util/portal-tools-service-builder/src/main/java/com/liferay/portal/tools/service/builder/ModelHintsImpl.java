/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder;

import com.liferay.portal.kernel.model.ModelHintsCallback;
import com.liferay.portal.model.BaseModelHintsImpl;
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