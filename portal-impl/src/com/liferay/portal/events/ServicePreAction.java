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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Brian Wing Shun Chan
 * @author Felix Ventero
 * @author Jorge Ferrer
 */
public class ServicePreAction extends Action {

	public ServicePreAction() {
		_initImportLARFiles();
	}

	@Override
	public void run(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws ActionException {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			ServicePreActionLogic servicePreActionLogic =
				new ServicePreActionLogic();

			servicePreActionLogic.servicePre(
				true, privateLARFile, publicLARFile, httpServletRequest,
				httpServletResponse);
		}
		catch (Exception e) {
			throw new ActionException(e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Running takes " + stopWatch.getTime() + " ms");
		}
	}

	protected File privateLARFile;
	protected File publicLARFile;

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	protected class LayoutComposite {

		protected LayoutComposite(Layout layout, List<Layout> layouts) {
			_layout = layout;
			_layouts = layouts;
		}

		protected Layout getLayout() {
			return _layout;
		}

		protected List<Layout> getLayouts() {
			return _layouts;
		}

		private final Layout _layout;
		private final List<Layout> _layouts;

	}

	private void _initImportLARFiles() {
		String privateLARFileName =
			PropsValues.DEFAULT_USER_PRIVATE_LAYOUTS_LAR;

		if (_log.isDebugEnabled()) {
			_log.debug("Reading private LAR file " + privateLARFileName);
		}

		if (Validator.isNotNull(privateLARFileName)) {
			privateLARFile = new File(privateLARFileName);

			if (!privateLARFile.exists()) {
				_log.error(
					"Private LAR file " + privateLARFile + " does not exist");

				privateLARFile = null;
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Using private LAR file " + privateLARFileName);
				}
			}
		}

		String publicLARFileName = PropsValues.DEFAULT_USER_PUBLIC_LAYOUTS_LAR;

		if (_log.isDebugEnabled()) {
			_log.debug("Reading public LAR file " + publicLARFileName);
		}

		if (Validator.isNotNull(publicLARFileName)) {
			publicLARFile = new File(publicLARFileName);

			if (!publicLARFile.exists()) {
				_log.error(
					"Public LAR file " + publicLARFile + " does not exist");

				publicLARFile = null;
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Using public LAR file " + publicLARFileName);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServicePreAction.class);

}