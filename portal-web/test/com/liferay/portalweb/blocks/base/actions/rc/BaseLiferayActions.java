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

import com.liferay.portalweb.blocks.base.actions.rc.ActionsUtil;
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
import com.liferay.portalweb.blocks.base.functions.rc.IsElementPresentFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.IsVisibleFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.MouseOverFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.OpenFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.PasteFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.SelectFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.SelectWindowFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.TypeFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.UncheckFunctions;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class BaseLiferayActions extends BaseActions {
	public BaseLiferayActions(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
		paths = BaseLiferayPaths.getPaths();
	}

	public void assertChecked(String target, String value)
		throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		AssertCheckedFunctions assertCheckedFunctions = new AssertCheckedFunctions(selenium);

		assertCheckedFunctions.assertChecked(params[0], params[1]);
	}

	public void assertElementNotPresent(String target, String value)
		throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		AssertElementNotPresentFunctions assertElementNotPresentFunctions = new AssertElementNotPresentFunctions(selenium);

		assertElementNotPresentFunctions.assertElementNotPresent(params[0],
			params[1]);
	}

	public void assertElementPresent(String target, String value)
		throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		AssertElementPresentFunctions assertElementPresentFunctions = new AssertElementPresentFunctions(selenium);

		assertElementPresentFunctions.assertElementPresent(params[0], params[1]);
	}

	public void assertNotChecked(String target, String value)
		throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		AssertNotCheckedFunctions assertNotCheckedFunctions = new AssertNotCheckedFunctions(selenium);

		assertNotCheckedFunctions.assertNotChecked(params[0], params[1]);
	}

	public void assertTextEquals(String target, String value)
		throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		AssertTextEqualsFunctions assertTextEqualsFunctions = new AssertTextEqualsFunctions(selenium);

		assertTextEqualsFunctions.assertText(params[0], params[1]);
	}

	public void assertTextNotEquals(String target, String value)
		throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		AssertTextNotEqualsFunctions assertTextNotEqualsFunctions = new AssertTextNotEqualsFunctions(selenium);

		assertTextNotEqualsFunctions.assertNotText(params[0], params[1]);
	}

	public void assertTextNotPresent(String target, String value)
		throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		AssertTextNotPresentFunctions assertTextNotPresentFunctions = new AssertTextNotPresentFunctions(selenium);

		assertTextNotPresentFunctions.assertTextNotPresent(params[0], params[1]);
	}

	public void assertTextPresent(String target, String value)
		throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		AssertTextPresentFunctions assertTextPresentFunctions = new AssertTextPresentFunctions(selenium);

		assertTextPresentFunctions.assertTextPresent(params[0], params[1]);
	}

	public void check(String target, String value) throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		CheckFunctions checkFunctions = new CheckFunctions(selenium);

		checkFunctions.clickAt(params[0], params[1]);
	}

	public void click(String target, String value) throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		ClickFunctions clickFunctions = new ClickFunctions(selenium);

		if (params[0].contains("/input")) {
			clickFunctions.valueClickAtAndWait(params[0], params[1]);
		}
		else {
			clickFunctions.textClickAt(params[0], params[1]);
		}
	}

	public void close(String target, String value) throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		CloseFunctions closeFunctions = new CloseFunctions(selenium);

		closeFunctions.close(params[0], params[1]);
	}

	public void confirm(String target, String value) throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		ConfirmFunctions confirmFunctions = new ConfirmFunctions(selenium);

		confirmFunctions.confirm(params[0], params[1]);
	}

	public void copy(String target, String value) throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		CopyFunctions copyFunctions = new CopyFunctions(selenium);

		copyFunctions.textCopy(params[0], params[1]);
	}

	public void dragAndDrop(String target1, String value1, String target2,
		String value2) throws Exception {
		String[] params1 = ActionsUtil.getParams(paths, target1, value1);
		String[] params2 = ActionsUtil.getParams(paths, target2, value2);

		DragAndDropFunctions dragAndDropFunctions = new DragAndDropFunctions(selenium);

		dragAndDropFunctions.dragAndDrop(params1[0], params1[1], params2[0],
			params2[1]);
	}

	public boolean isElementPresent(String target, String value)
		throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		IsElementPresentFunctions isElementPresentFunctions = new IsElementPresentFunctions(selenium);

		return isElementPresentFunctions.isElementPresent(params[0], params[1]);
	}

	public boolean isVisible(String target, String value)
		throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		IsVisibleFunctions isVisibleFunctions = new IsVisibleFunctions(selenium);

		return isVisibleFunctions.isVisible(params[0], params[1]);
	}

	public void mouseOver(String target, String value)
		throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		MouseOverFunctions mouseOverFunctions = new MouseOverFunctions(selenium);

		mouseOverFunctions.textMouseOver(params[0], params[1]);
	}

	public void open(String target, String value) throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		OpenFunctions openFunctions = new OpenFunctions(selenium);

		openFunctions.open(params[0], params[1]);
	}

	public void paste(String target, String value) throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		PasteFunctions pasteFunctions = new PasteFunctions(selenium);

		pasteFunctions.paste(params[0], params[1]);
	}

	public void select(String target, String value) throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		SelectFunctions selectFunctions = new SelectFunctions(selenium);

		selectFunctions.select(params[0], params[1]);
	}

	public void selectWindow(String target, String value)
		throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		SelectWindowFunctions selectWindowFunctions = new SelectWindowFunctions(selenium);

		if (params[0].contains("/iframe")) {
			selectWindowFunctions.selectFrame(params[0], params[1]);
		}
		else if ((target.equals("TOP"))) {
			selectWindowFunctions.selectFrameTop(params[0], params[1]);
		}
		else {
			selectWindowFunctions.selectWindow(params[0], params[1]);
		}
	}

	public void type(String target, String value) throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		TypeFunctions typeFunctions = new TypeFunctions(selenium);

		typeFunctions.type(params[0], params[1]);
	}

	public void uncheck(String target, String value) throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		UncheckFunctions uncheckFunctions = new UncheckFunctions(selenium);

		uncheckFunctions.clickAt(params[0], params[1]);
	}
}