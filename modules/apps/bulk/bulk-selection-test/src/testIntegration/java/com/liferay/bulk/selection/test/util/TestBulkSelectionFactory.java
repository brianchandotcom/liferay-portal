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

package com.liferay.bulk.selection.test.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	service = {BulkSelectionFactory.class, TestBulkSelectionFactory.class}
)
public class TestBulkSelectionFactory implements BulkSelectionFactory<Integer> {

	@Override
	public BulkSelection<Integer> create(Map<String, String[]> parameterMap) {
		String[] integers = parameterMap.get("integers");

		return new BulkSelection<Integer>() {

			@Override
			public <E extends PortalException> void forEach(
					UnsafeConsumer<Integer, E> unsafeConsumer)
				throws PortalException {

				for (String s : integers) {
					unsafeConsumer.accept(Integer.valueOf(s));
				}
			}

			@Override
			public Class<? extends BulkSelectionFactory>
				getBulkSelectionFactoryClass() {

				return TestBulkSelectionFactory.class;
			}

			@Override
			public Map<String, String[]> getParameterMap() {
				return parameterMap;
			}

			@Override
			public long getSize() {
				return integers.length;
			}

			@Override
			public Serializable serialize() {
				return StringUtil.merge(integers, StringPool.COMMA);
			}

			@Override
			public BulkSelection<AssetEntry> toAssetEntryBulkSelection() {
				throw new UnsupportedOperationException();
			}

		};
	}

}