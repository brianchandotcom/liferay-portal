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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.NotificationThreadLocal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.VerifyThreadLocal;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.exportimport.staging.StagingAdvicesThreadLocal;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Raymond Augé
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Deprecated
public class VerifyProcessUtil {

	public static boolean verifyProcess(
			boolean ranUpgradeProcess, boolean verified)
		throws VerifyException {

		int verifyFrequency = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VERIFY_FREQUENCY));

		if ((verifyFrequency == VerifyProcess.ALWAYS) ||
			((verifyFrequency == VerifyProcess.ONCE) && !verified) ||
			ranUpgradeProcess) {

			return _verifyProcess();
		}

		return false;
	}

	private static boolean _verifyProcess() throws VerifyException {
		boolean ranVerifyProcess = false;

		boolean tempIndexReadOnly = IndexWriterHelperUtil.isIndexReadOnly();

		IndexWriterHelperUtil.setIndexReadOnly(true);

		NotificationThreadLocal.setEnabled(false);
		StagingAdvicesThreadLocal.setEnabled(false);
		VerifyThreadLocal.setVerifyInProgress(true);
		WorkflowThreadLocal.setEnabled(false);

		try {
			String[] verifyProcessClassNames = PropsUtil.getArray(
				PropsKeys.VERIFY_PROCESSES);

			for (String verifyProcessClassName : verifyProcessClassNames) {
				boolean tempRanVerifyProcess = _verifyProcess(
					verifyProcessClassName);

				if (tempRanVerifyProcess) {
					ranVerifyProcess = true;
				}
			}
		}
		finally {
			IndexWriterHelperUtil.setIndexReadOnly(tempIndexReadOnly);
			NotificationThreadLocal.setEnabled(true);
			StagingAdvicesThreadLocal.setEnabled(true);
			VerifyThreadLocal.setVerifyInProgress(false);
			WorkflowThreadLocal.setEnabled(true);
		}

		return ranVerifyProcess;
	}

	private static boolean _verifyProcess(String verifyProcessClassName)
		throws VerifyException {

		if (_log.isDebugEnabled()) {
			_log.debug("Initializing verification " + verifyProcessClassName);
		}

		try {
			Class<?> clazz = Class.forName(verifyProcessClassName);

			VerifyProcess verifyProcess = (VerifyProcess)clazz.newInstance();

			if (_log.isDebugEnabled()) {
				_log.debug("Running verification " + verifyProcessClassName);
			}

			verifyProcess.verify();

			if (_log.isDebugEnabled()) {
				_log.debug("Finished verification " + verifyProcessClassName);
			}

			return true;
		}
		catch (ClassNotFoundException classNotFoundException) {
			_log.error(verifyProcessClassName + " cannot be found");
		}
		catch (IllegalAccessException illegalAccessException) {
			_log.error(verifyProcessClassName + " cannot be accessed");
		}
		catch (InstantiationException instantiationException) {
			_log.error(verifyProcessClassName + " cannot be initiated");
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyProcessUtil.class);

}