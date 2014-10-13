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

package com.liferay.portal.convert;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.MaintenanceUtil;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Alexander Chow
 */
public abstract class ConvertProcess {

	public void convert() throws ConvertException {
		try {
			if (getPath() != null) {
				return;
			}

			StopWatch stopWatch = new StopWatch();

			stopWatch.start();

			if (_log.isInfoEnabled()) {
				_log.info("Starting conversion for " + getClass().getName());
			}

			doConvert();

			if (_log.isInfoEnabled()) {
				_log.info(
					"Finished conversion for " + getClass().getName() + " in " +
						stopWatch.getTime() + " ms");
			}
		}
		catch (Exception e) {
			throw new ConvertException(e);
		}
		finally {
			setParameterValues(null);

			MaintenanceUtil.cancel();
		}
	}

	public abstract String getDescription();

	public String getParameterDescription() {
		return null;
	}

	public String[] getParameterNames() {
		return null;
	}

	public String[] getParameterValues() {
		return _paramValues;
	}

	public String getPath() {
		return null;
	}

	public abstract boolean isEnabled();

	public void setParameterValues(String[] values) {
		_paramValues = values;
	}

	/**
	 * @throws ConvertException
	 */
	public void validate() throws ConvertException {
	}

	protected abstract void doConvert() throws Exception;

	private static final Log _log = LogFactoryUtil.getLog(ConvertProcess.class);

	private String[] _paramValues = null;

}