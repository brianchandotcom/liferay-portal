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

package com.liferay.portalweb.blocks.base.actions.rc;

import com.liferay.portalweb.blocks.base.functions.rc.AssertCheckedFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.AssertElementNotPresentFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.AssertElementPresentFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.AssertNotCheckedFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.AssertTextEqualsFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.AssertTextNotEqualsFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.AssertTextNotPresentFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.AssertTextPresentFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.CheckFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.ClickFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.CloseFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.ConfirmFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.CopyFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.DragAndDropFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.MouseOverFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.OpenFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.PasteFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.SelectFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.SelectWindowFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.TypeFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.UncheckFunctions;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class BaseActionsImpl implements LiferayActions {

	public BaseActionsImpl(LiferaySelenium liferaySelenium) {
		paths = new HashMap<String, String>();
		selenium = liferaySelenium;
	}

	public void assertChecked(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		AssertCheckedFunctions assertCheckedFunctions =
			new AssertCheckedFunctions(selenium);

		assertCheckedFunctions.assertChecked(params[0], params[1]);
	}

	public void assertElementNotPresent(String param1, String param2)
		throws Exception {

		String[] params = getParams(param1, param2);

		AssertElementNotPresentFunctions assertElementNotPresentFunctions =
			new AssertElementNotPresentFunctions(selenium);

		assertElementNotPresentFunctions.assertElementNotPresent(
			params[0], params[1]);
	}

	public void assertElementPresent(String param1, String param2)
		throws Exception {

		String[] params = getParams(param1, param2);

		AssertElementPresentFunctions assertElementPresentFunctions =
			new AssertElementPresentFunctions(selenium);

		assertElementPresentFunctions.assertElementPresent(
			params[0], params[1]);
	}

	public void assertNotChecked(String param1, String param2)
		throws Exception {

		String[] params = getParams(param1, param2);

		AssertNotCheckedFunctions assertNotCheckedFunctions =
			new AssertNotCheckedFunctions(selenium);

		assertNotCheckedFunctions.assertNotChecked(params[0], params[1]);
	}

	public void assertTextEquals(String param1, String param2)
		throws Exception {

		String[] params = getParams(param1, param2);

		AssertTextEqualsFunctions assertTextEqualsFunctions =
			new AssertTextEqualsFunctions(selenium);

		assertTextEqualsFunctions.assertText(params[0], params[1]);
	}

	public void assertTextNotEquals(String param1, String param2)
		throws Exception {

		String[] params = getParams(param1, param2);

		AssertTextNotEqualsFunctions assertTextNotEqualsFunctions =
			new AssertTextNotEqualsFunctions(selenium);

		assertTextNotEqualsFunctions.assertNotText(params[0], params[1]);
	}

	public void assertTextNotPresent(String param1, String param2)
		throws Exception {

		String[] params = getParams(param1, param2);

		AssertTextNotPresentFunctions assertTextNotPresentFunctions =
			new AssertTextNotPresentFunctions(selenium);

		assertTextNotPresentFunctions.assertTextNotPresent(
			params[0], params[1]);
	}

	public void assertTextPresent(String param1, String param2)
		throws Exception {

		String[] params = getParams(param1, param2);

		AssertTextPresentFunctions assertTextPresentFunctions =
			new AssertTextPresentFunctions(selenium);

		assertTextPresentFunctions.assertTextPresent(params[0], params[1]);
	}

	public void check(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		CheckFunctions checkFunctions = new CheckFunctions(selenium);

		checkFunctions.clickAt(params[0], params[1]);
	}

	public void click(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		ClickFunctions clickFunctions = new ClickFunctions(selenium);

		clickFunctions.textClickAt(params[0], params[1]);
	}

	public void close(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		CloseFunctions closeFunctions = new CloseFunctions(selenium);

		closeFunctions.close(params[0], params[1]);
	}

	public void confirm(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		ConfirmFunctions confirmFunctions = new ConfirmFunctions(selenium);

		confirmFunctions.confirm(params[0], params[1]);
	}

	public void copy(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		CopyFunctions copyFunctions = new CopyFunctions(selenium);

		copyFunctions.textCopy(params[0], params[1]);
	}

	public void dragAndDrop(
		String path1, String value1, String path2, String value2)
			throws Exception {

		String[] params1 = getParams(path1, value1);
		String[] params2 = getParams(path2, value2);

		DragAndDropFunctions dragAndDropFunctions = new DragAndDropFunctions(
			selenium);

		dragAndDropFunctions.dragAndDrop(
			params1[0], params1[1], params2[0], params2[1]);
	}

	public String get(String key) throws Exception {
		return paths.get(key);
	}

	public boolean isElementPresent(String param1, String param2)
		throws Exception {

		String[] params = getParams(param1, param2);

		selenium.pause("1000");

		return selenium.isElementPresent(params[0]);
	}

	public boolean isVisible(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		return selenium.isVisible(params[0]);
	}

	public void mouseOver(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		MouseOverFunctions mouseOverFunctions = new MouseOverFunctions(
			selenium);

		mouseOverFunctions.textMouseOver(params[0], params[1]);
	}

	public void open(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		OpenFunctions openFunctions = new OpenFunctions(selenium);

		openFunctions.open(params[0], params[1]);
	}

	public void paste(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		PasteFunctions pasteFunctions = new PasteFunctions(selenium);

		pasteFunctions.paste(params[0], params[1]);
	}

	public void select(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		SelectFunctions selectFunctions = new SelectFunctions(selenium);

		selectFunctions.select(params[0], params[1]);
	}

	public void selectWindow(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		SelectWindowFunctions selectWindowFunctions = new SelectWindowFunctions(
			selenium);

		selectWindowFunctions.selectFrame(params[0], params[1]);
	}

	public void type(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		TypeFunctions typeFunctions = new TypeFunctions(selenium);

		typeFunctions.type(params[0], params[1]);
	}

	public void uncheck(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		UncheckFunctions uncheckFunctions = new UncheckFunctions(selenium);

		uncheckFunctions.clickAt(params[0], params[1]);
	}

	protected String[] getParams(String param1, String param2) {
		String[] params = new String[2];

		if (paths.containsKey(param1)) {
			params[0] = paths.get(param1);
		}
		else {
			params[0] = param1;
		}

		if (param2 == null) {
			params[1] = "";
		}
		else {
			params[1] = param2;
		}

		return params;
	}

	protected Map<String, String> paths;

	protected LiferaySelenium selenium;

}