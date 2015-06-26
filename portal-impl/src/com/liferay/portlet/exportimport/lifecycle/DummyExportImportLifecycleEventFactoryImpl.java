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

package com.liferay.portlet.exportimport.lifecycle;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;

/**
 * @author Daniel Kocsis
 */
public class DummyExportImportLifecycleEventFactoryImpl
	implements ExportImportLifecycleEventFactory {

	@Override
	public ExportImportLifecycleEvent create(
		int code, int processFlag, Serializable... attributes) {

		return new ExportImportLifecycleEvent() {

			@Override
			public List<Serializable> getAttributes() {
				return Collections.<Serializable>emptyList();
			}

			@Override
			public int getCode() {
				return 0;
			}

			@Override
			public int getProcessFlag() {
				return 0;
			}

			@Override
			public void setAttributes(Serializable... attributes) {
			}

			@Override
			public void setCode(int eventCode) {
			}

			@Override
			public void setProcessFlag(int processFlag) {
			}

		};
	}

}