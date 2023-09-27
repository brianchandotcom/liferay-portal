/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import com.liferay.petra.function.UnsafeConsumer;

import java.util.ArrayList;

/**
 * @author Kresimir Coko
 */
public class MultiselectItemList extends ArrayList<MultiselectItem> {

	public void add(UnsafeConsumer<MultiselectItem, Exception> unsafeConsumer) {
		MultiselectItem multiselectItem = new MultiselectItem();

		try {
			unsafeConsumer.accept(multiselectItem);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		add(multiselectItem);
	}

}