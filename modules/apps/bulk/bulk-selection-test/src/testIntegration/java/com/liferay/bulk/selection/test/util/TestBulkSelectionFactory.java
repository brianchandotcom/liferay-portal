/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = BulkSelectionFactory.class)
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