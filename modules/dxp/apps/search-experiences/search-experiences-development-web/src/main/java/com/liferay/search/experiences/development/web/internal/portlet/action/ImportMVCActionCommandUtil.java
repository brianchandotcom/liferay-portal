/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.development.web.internal.portlet.action;

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.function.Function;

import javax.portlet.ActionRequest;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author André de Oliveira
 */
public class ImportMVCActionCommandUtil {

	public static void doProcessAction(
		ActionRequest actionRequest,
		Function<ActionRequest, Integer> function) {

		try {
			ExportImportThreadLocal.setPortletImportInProcess(true);

			try {
				StopWatch stopWatch = new StopWatch();

				stopWatch.start();

				int items = function.apply(actionRequest);

				stopWatch.stop();

				SessionMessages.add(actionRequest, "success");

				actionRequest.setAttribute(
					"success",
					StringBundler.concat(
						"Imported ", items, " items in ", stopWatch));
			}
			finally {
				ExportImportThreadLocal.setPortletImportInProcess(false);
			}
		}
		catch (Exception exception) {
			SessionErrors.add(actionRequest, "error");

			actionRequest.setAttribute("error", _toString(exception));

			throw exception;
		}
	}

	private static String _toString(Exception exception) {
		if ((exception.getClass() == RuntimeException.class) &&
			Validator.isNull(exception.getMessage()) &&
			ArrayUtil.isNotEmpty(exception.getSuppressed())) {

			return String.valueOf(Arrays.asList(exception.getSuppressed()));
		}

		return exception.toString();
	}

}