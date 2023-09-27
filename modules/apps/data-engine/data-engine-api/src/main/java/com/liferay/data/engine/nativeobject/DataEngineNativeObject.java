/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.nativeobject;

import java.util.List;

/**
 * @author Jeyvison Nascimento
 */
public interface DataEngineNativeObject {

	public String getClassName();

	public List<DataEngineNativeObjectField> getDataEngineNativeObjectFields();

	public String getName();

}