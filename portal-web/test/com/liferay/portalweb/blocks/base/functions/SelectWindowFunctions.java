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

package com.liferay.portalweb.blocks.base.functions;

import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class SelectWindowFunctions extends BaseFunctions {
	public SelectWindowFunctions(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void selectFrame(String param1, String param2)
		throws Exception {
		selenium.waitForVisible(param1);
		selenium.selectFrame(param1);
	}

	public void selectFrameTop(String param1, String param2)
		throws Exception {
		selenium.selectFrame("relative=top");
	}

	public void selectWindow(String param1, String param2)
		throws Exception {
		selenium.pause("5000");
		selenium.selectWindow(param1);
	}

	public void selectWindowTop(String param1, String param2)
		throws Exception {
		selenium.pause("5000");
		selenium.selectWindow("null");
	}
}