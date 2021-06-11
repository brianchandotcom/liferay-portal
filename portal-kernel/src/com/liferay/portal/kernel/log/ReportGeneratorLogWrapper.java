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

package com.liferay.portal.kernel.log;

import com.liferay.portal.kernel.report.ReportEventPublisherUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SystemProperties;

/**
 * @author Sam Ziemer
 */
public class ReportGeneratorLogWrapper extends SanitizerLogWrapper {

	public static boolean isEnabled() {
		return _REPORT_GENERATION;
	}

	public ReportGeneratorLogWrapper(Log log, String name) {
		super(log);

		_className = name;

		setLogWrapperClassName(ReportGeneratorLogWrapper.class.getName());
	}

	@Override
	public void error(Object msg) {
		super.error(msg);

		ReportEventPublisherUtil.addError(_className, String.valueOf(msg));
	}

	@Override
	public void error(Object msg, Throwable throwable) {
		super.error(msg, throwable);

		ReportEventPublisherUtil.addError(_className, String.valueOf(msg));
	}

	@Override
	public void error(Throwable throwable) {
		super.error(throwable);

		ReportEventPublisherUtil.addError(_className, throwable.getMessage());
	}

	@Override
	public void warn(Object msg) {
		super.warn(msg);

		ReportEventPublisherUtil.addWarning(_className, String.valueOf(msg));
	}

	@Override
	public void warn(Object msg, Throwable throwable) {
		super.warn(msg, throwable);

		ReportEventPublisherUtil.addWarning(_className, String.valueOf(msg));
	}

	@Override
	public void warn(Throwable throwable) {
		super.warn(throwable);

		ReportEventPublisherUtil.addWarning(_className, throwable.getMessage());
	}

	private static final boolean _REPORT_GENERATION = GetterUtil.getBoolean(
		SystemProperties.get(PropsKeys.REPORT_GENERATION));

	private final String _className;

}