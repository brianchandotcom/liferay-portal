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

package com.liferay.portalweb.blocks.base.actions;

import com.liferay.portalweb.blocks.base.paths.BaseActionsPaths;
import com.liferay.portalweb.blocks.base.units.AssertCheckedUnits;
import com.liferay.portalweb.blocks.base.units.AssertElementNotPresentUnits;
import com.liferay.portalweb.blocks.base.units.AssertElementPresentUnits;
import com.liferay.portalweb.blocks.base.units.AssertNotCheckedUnits;
import com.liferay.portalweb.blocks.base.units.AssertTextEqualsUnits;
import com.liferay.portalweb.blocks.base.units.AssertTextNotEqualsUnits;
import com.liferay.portalweb.blocks.base.units.AssertTextNotPresentUnits;
import com.liferay.portalweb.blocks.base.units.AssertTextPresentUnits;
import com.liferay.portalweb.blocks.base.units.CheckUnits;
import com.liferay.portalweb.blocks.base.units.ClickUnits;
import com.liferay.portalweb.blocks.base.units.CloseUnits;
import com.liferay.portalweb.blocks.base.units.ConfirmUnits;
import com.liferay.portalweb.blocks.base.units.CopyUnits;
import com.liferay.portalweb.blocks.base.units.MouseOverUnits;
import com.liferay.portalweb.blocks.base.units.OpenUnits;
import com.liferay.portalweb.blocks.base.units.PasteUnits;
import com.liferay.portalweb.blocks.base.units.SelectUnits;
import com.liferay.portalweb.blocks.base.units.TypeUnits;
import com.liferay.portalweb.blocks.base.units.UncheckUnits;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class BaseActionsImpl implements LiferayActions {

	public BaseActionsImpl(LiferaySelenium liferaySelenium) {
		paths = BaseActionsPaths.getPaths();
		selenium = liferaySelenium;
	}

	public void assertChecked(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		AssertCheckedUnits assertCheckedUnits = new AssertCheckedUnits(
			selenium);

		assertCheckedUnits.assertChecked(params[0], params[1]);
	}

	public void assertElementNotPresent(String param1, String param2)
		throws Exception {

		String[] params = getParams(param1, param2);

		AssertElementNotPresentUnits assertElementNotPresentUnits =
			new AssertElementNotPresentUnits(selenium);

		assertElementNotPresentUnits.assertElementNotPresent(
			params[0], params[1]);
	}

	public void assertElementPresent(String param1, String param2)
		throws Exception {

		String[] params = getParams(param1, param2);

		AssertElementPresentUnits assertElementPresentUnits =
			new AssertElementPresentUnits(selenium);

		assertElementPresentUnits.assertElementPresent(params[0], params[1]);
	}

	public void assertNotChecked(String param1, String param2)
		throws Exception {

		String[] params = getParams(param1, param2);

		AssertNotCheckedUnits assertNotCheckedUnits = new AssertNotCheckedUnits(
			selenium);

		assertNotCheckedUnits.assertNotChecked(params[0], params[1]);
	}

	public void assertTextEquals(String param1, String param2)
		throws Exception {

		String[] params = getParams(param1, param2);

		AssertTextEqualsUnits assertTextEqualsUnits = new AssertTextEqualsUnits(
			selenium);

		assertTextEqualsUnits.assertText(params[0], params[1]);
	}

	public void assertTextNotEquals(String param1, String param2)
		throws Exception {

		String[] params = getParams(param1, param2);

		AssertTextNotEqualsUnits assertTextNotEqualsUnits =
			new AssertTextNotEqualsUnits(selenium);

		assertTextNotEqualsUnits.assertTextNotEquals(params[0], params[1]);
	}

	public void assertTextNotPresent(String param1, String param2)
		throws Exception {

		String[] params = getParams(param1, param2);

		AssertTextNotPresentUnits assertTextNotPresentUnits =
			new AssertTextNotPresentUnits(selenium);

		assertTextNotPresentUnits.assertTextNotPresent(params[0], params[1]);
	}

	public void assertTextPresent(String param1, String param2)
		throws Exception {

		String[] params = getParams(param1, param2);

		AssertTextPresentUnits assertTextPresentUnits =
			new AssertTextPresentUnits(selenium);

		assertTextPresentUnits.assertTextPresent(params[0], params[1]);
	}

	public void check(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		CheckUnits checkUnits = new CheckUnits(selenium);

		checkUnits.clickAt(params[0], params[1]);
	}

	public void click(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		ClickUnits clickUnits = new ClickUnits(selenium);

		if (params[0].contains("/input")) {
			clickUnits.valueClickAtAndWait(params[0], params[1]);
		}
		else {
			clickUnits.textClickAt(params[0], params[1]);
		}
	}

	public void close(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		CloseUnits closeUnits = new CloseUnits(selenium);

		closeUnits.close(params[0], params[1]);
	}

	public void confirm(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		ConfirmUnits confirmUnits = new ConfirmUnits(selenium);

		confirmUnits.confirm(params[0], params[1]);
	}

	public void copy(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		CopyUnits copyUnits = new CopyUnits(selenium);

		if (params[0].contains("/input")) {
			copyUnits.valueCopy(params[0], params[1]);
		}
		else {
			copyUnits.textCopy(params[0], params[1]);
		}
	}

	public String get(String key) throws Exception {
		return paths.get(key)[0];
	}

	public boolean isElementPresent(String param1, String param2)
		throws Exception {

		String[] params = getParams(param1, param2);

		return selenium.isElementPresent(params[0]);
	}

	public void mouseOver(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		MouseOverUnits mouseOverUnits = new MouseOverUnits(selenium);

		mouseOverUnits.textMouseOver(params[0], params[1]);
	}

	public void open(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		OpenUnits openUnits = new OpenUnits(selenium);

		openUnits.open(params[0], params[1]);
	}

	public void paste(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		PasteUnits pasteUnits = new PasteUnits(selenium);

		pasteUnits.paste(params[0], params[1]);
	}

	public void select(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		SelectUnits selectUnits = new SelectUnits(selenium);

		selectUnits.select(params[0], params[1]);
	}

	public void type(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		TypeUnits typeUnits = new TypeUnits(selenium);

		typeUnits.type(params[0], params[1]);
	}

	public void uncheck(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		UncheckUnits uncheckUnits = new UncheckUnits(selenium);

		uncheckUnits.uncheck(params[0], params[1]);
	}

	protected String[] getParams(String param1, String param2) {
		String[] params = new String[2];

		if (paths.containsKey(param1)) {
			params[0] = paths.get(param1)[0];
		}
		else {
			params[0] = param1;
		}

		if (paths.containsKey(param1) && (param2 == null)) {
			params[1] = paths.get(param1)[1];
		}
		else {
			params[1] = param2;
		}

		return params;
	}

	protected Map<String, String[]> paths;

	protected LiferaySelenium selenium;

}