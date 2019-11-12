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

package com.liferay.portal.template.soy.data;

import com.liferay.portal.template.soy.util.SoyRawData;

/**
 * Objects of this type contain sanitized HTML suitable for its use in Soy
 * templates.
 *
 * Use the {@link SoyDataFactory} OSGi service to create objects of this class.
 *
 * @author     Iván Zaera Avellón
 * @deprecated As of Mueller (7.2.x), , replaced by {@link SoyRawData}
 * @review
 */
@Deprecated
public interface SoyHTMLData extends SoyRawData {
}