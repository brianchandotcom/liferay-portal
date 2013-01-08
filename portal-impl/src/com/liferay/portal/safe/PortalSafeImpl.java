/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.safe;

import com.liferay.portal.kernel.safe.PortalSafe;
import com.liferay.portal.kernel.safe.PortalSafeException;
import com.liferay.portal.kernel.safe.model.Item;

import java.util.List;

/**
 * Dummy implementation throwing UnsupportedOperationException
 *
 * @author Tomas Polesovsky
 */
public class PortalSafeImpl implements PortalSafe {

	public List<String> listItemsNames(long companyId, long groupId)
		throws PortalSafeException {

		throw new UnsupportedOperationException("");
	}

	public Item loadItem(long companyId, long groupId, String name)
		throws PortalSafeException {

		throw new UnsupportedOperationException("");
	}

	public void removeItem(long companyId, long groupId, String name)
		throws PortalSafeException {

		throw new UnsupportedOperationException("");
	}

	public void saveItem(long companyId, long groupId, Item item)
		throws PortalSafeException {

		throw new UnsupportedOperationException("");
	}

}