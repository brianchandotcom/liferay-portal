/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.spring.extension.internal.mvc;

import javax.mvc.binding.BindingError;
import javax.mvc.binding.BindingResult;
import javax.mvc.binding.ValidationError;

/**
 * @author Neil Griffin
 */
public interface MutableBindingResult extends BindingResult {

	public void addBindingError(BindingError bindingError);

	public void addValidationError(ValidationError validationError);

	public boolean isConsulted();

}