/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.internal;

import java.util.Map;

import javax.portlet.MutableResourceParameters;

/**
 * @author Neil Griffin
 */
public class MutableResourceParametersImpl
	extends BaseMutablePortletParameters<MutableResourceParameters>
	implements LiferayMutablePortletParameters, MutableResourceParameters {

	public MutableResourceParametersImpl(Map<String, String[]> parameterMap) {
		super(parameterMap, MutableResourceParametersImpl::new);
	}

}