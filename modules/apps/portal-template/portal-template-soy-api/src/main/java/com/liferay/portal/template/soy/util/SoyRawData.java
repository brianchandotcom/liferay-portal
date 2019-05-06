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

package com.liferay.portal.template.soy.util;

/**
 * This interface can be implemented by any specialized class that is capable of
 * returning a low level value for the Soy template system. Such low level value
 * will be passed to Soy engine verbatim, without any smart/additional type
 * conversion.
 *
 * @author Leonardo Barros
 * @review
 */
public interface SoyRawData {

	public Object getValue();

}