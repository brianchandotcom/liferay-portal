/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.test.rule;

import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.portal.kernel.test.rule.AbstractTestRule;

import java.util.Arrays;
import java.util.List;

import org.junit.runner.Description;

/**
 * @author Alberto Javier Moreno Lage
 */
public class ExportImportScopesTestRule extends AbstractTestRule<Void, Void> {

	public static final ExportImportScopesTestRule INSTANCE =
		new ExportImportScopesTestRule();

	public List<Scope> getScopes() {
		return _scopes;
	}

	@Override
	protected void afterClass(Description description, Void previousValue) {
	}

	@Override
	protected void afterMethod(
		Description description, Void previousValue, Object target) {
	}

	@Override
	protected Void beforeClass(Description description) {
		ExportImportScopes exportImportScopes = description.getAnnotation(
			ExportImportScopes.class);

		_scopes = Arrays.asList(exportImportScopes.value());

		return null;
	}

	@Override
	protected Void beforeMethod(Description description, Object target) {
		return null;
	}

	private ExportImportScopesTestRule() {
	}

	private List<Scope> _scopes;

}