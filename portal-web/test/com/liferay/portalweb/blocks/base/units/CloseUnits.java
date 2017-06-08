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

package com.liferay.portalweb.blocks.base.units;

import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CloseUnits extends BaseActionsUnits {
	public CloseUnits(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void close(String param1, String param2) throws Exception {
	}

	public void closeWindow(String param1, String param2) throws Exception {
		selenium.pause("1000");
		selenium.close();
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
	}

}