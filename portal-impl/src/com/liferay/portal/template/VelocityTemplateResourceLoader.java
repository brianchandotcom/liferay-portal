/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.template;

import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.util.PropsValues;

/**
 * @author Igor Spasic
 */
public class VelocityTemplateResourceLoader
	extends DefaultTemplateResourceLoader {

	public VelocityTemplateResourceLoader() {
		super(
			TemplateConstants.LANG_TYPE_VM,
			PropsValues.VELOCITY_ENGINE_RESOURCE_PARSERS,
			PropsValues.VELOCITY_ENGINE_RESOURCE_MODIFICATION_CHECK_INTERVAL);
	}

}