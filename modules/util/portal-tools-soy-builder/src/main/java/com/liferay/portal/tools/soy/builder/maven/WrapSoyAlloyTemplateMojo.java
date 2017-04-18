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

package com.liferay.portal.tools.soy.builder.maven;

import com.liferay.portal.tools.soy.builder.commands.WrapSoyAlloyTemplateCommand;

/**
 * Wrap Soy templates into AlloyUI modules.
 *
 * @author Andrea Di Giorgi
 * @goal wrap-alloy-template
 */
public class WrapSoyAlloyTemplateMojo
	extends BaseSoyJsMojo<WrapSoyAlloyTemplateCommand> {

	/**
	 * The AlloyUI module name.
	 *
	 * @parameter
	 * @required
	 */
	public void setModuleName(String moduleName) {
		command.setModuleName(moduleName);
	}

	/**
	 * The Soy template namespace.
	 *
	 * @parameter
	 * @required
	 */
	public void setNamespace(String namespace) {
		command.setNamespace(namespace);
	}

	@Override
	protected WrapSoyAlloyTemplateCommand createCommand() {
		return new WrapSoyAlloyTemplateCommand();
	}

}