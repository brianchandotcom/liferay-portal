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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;

/**
 * @author Michael C. Han
 */
public class ExportImportThreadLocal {

	public static boolean isExportInProcess() {
		if (isLayoutExportInProcess() || isPortletExportInProcess()) {
			return true;
		}

		return false;
	}

	public static boolean isImportInProcess() {
		if (isLayoutImportInProcess() || isLayoutValidationInProcess() ||
			isPortletImportInProcess() || isPortletValidationInProcess()) {

			return true;
		}

		return false;
	}

	public static boolean isLayoutExportInProcess() {
		return _layoutExportInProcess.get();
	}

	public static boolean isLayoutImportInProcess() {
		return _layoutImportInProcess.get();
	}

	public static boolean isLayoutValidationInProcess() {
		return _layoutValidationInProcess.get();
	}

	public static boolean isPortletExportInProcess() {
		return _portletExportInProcess.get();
	}

	public static boolean isPortletImportInProcess() {
		return _portletImportInProcess.get();
	}

	public static boolean isPortletValidationInProcess() {
		return _portletValidationInProcess.get();
	}

	public static void setLayoutExportInProcess(boolean layoutExportInProcess) {
		_layoutExportInProcess.set(layoutExportInProcess);
	}

	public static void setLayoutImportInProcess(boolean layoutImportInProcess) {
		_layoutImportInProcess.set(layoutImportInProcess);
	}

	public static void setLayoutValidationInProcess(
		boolean layoutValidationInProcess) {

		_layoutValidationInProcess.set(layoutValidationInProcess);
	}

	public static void setPortletExportInProcess(
		boolean portletExportInProcess) {

		_portletExportInProcess.set(portletExportInProcess);
	}

	public static void setPortletImportInProcess(
		boolean portletImportInProcess) {

		_portletImportInProcess.set(portletImportInProcess);
	}

	public static void setPortletValidationInProcess(
		boolean portletValidationInProcess) {

		_portletValidationInProcess.set(portletValidationInProcess);
	}

	private static final ThreadLocal<Boolean> _layoutExportInProcess =
		new AutoResetThreadLocal<Boolean>(
			ExportImportThreadLocal.class + "._layoutExportInProcess", false);
	private static final ThreadLocal<Boolean> _layoutImportInProcess =
		new AutoResetThreadLocal<Boolean>(
			ExportImportThreadLocal.class + "._layoutImportInProcess", false);
	private static final ThreadLocal<Boolean> _layoutValidationInProcess =
		new AutoResetThreadLocal<Boolean>(
			ExportImportThreadLocal.class + "._layoutValidationInProcess",
			false);
	private static final ThreadLocal<Boolean> _portletExportInProcess =
		new AutoResetThreadLocal<Boolean>(
			ExportImportThreadLocal.class + "._portletExportInProcess", false);
	private static final ThreadLocal<Boolean> _portletImportInProcess =
		new AutoResetThreadLocal<Boolean>(
			ExportImportThreadLocal.class + "._portletImportInProcess", false);
	private static final ThreadLocal<Boolean> _portletValidationInProcess =
		new AutoResetThreadLocal<Boolean>(
			ExportImportThreadLocal.class + "._portletValidationInProcess",
			false);

}