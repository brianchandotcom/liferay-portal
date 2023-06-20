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

package com.liferay.info.item.provider;

import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;

/**
 * @author Jorge Ferrer
 */
public interface InfoItemObjectProvider<T> {

	public default T getInfoItem(InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #getInfoItem(InfoItemIdentifier)}
	 */
	@Deprecated
	public default T getInfoItem(long classPK) throws NoSuchInfoItemException {
		InfoItemIdentifier infoItemIdentifier = new ClassPKInfoItemIdentifier(
			classPK);

		return getInfoItem(infoItemIdentifier);
	}

}