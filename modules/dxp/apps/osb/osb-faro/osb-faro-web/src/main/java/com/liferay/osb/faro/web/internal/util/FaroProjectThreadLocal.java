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

package com.liferay.osb.faro.web.internal.util;

import com.liferay.osb.faro.model.FaroProject;
import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Marcellus Tavares
 */
public class FaroProjectThreadLocal {

	public static FaroProject getFaroProject() {
		return _faroProject.get();
	}

	public static void removeFaroProject() {
		_faroProject.remove();
	}

	public static void setFaroProject(FaroProject faroProject) {
		_faroProject.set(faroProject);
	}

	private static final ThreadLocal<FaroProject> _faroProject =
		new CentralizedThreadLocal<>(
			FaroProjectThreadLocal.class + "._faroProject");

}