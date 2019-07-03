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

package com.liferay.frontend.editor.tinymce.web.internal.constants;

/**
 * @author Iván Zaera Avellón
 */
public class TinyMCEEditorConstants {

	/**
	 * The namespace prefix added to the taglib attributes when they are put in
	 * the {@link javax.servlet.http.HttpServletRequest}.
	 *
	 * <p>
	 * Do not change this value unless you stop implementing the legacy {@link
	 * com.liferay.portal.kernel.editor.Editor} interface and leave the {@link
	 * com.liferay.frontend.editor.EditorRenderer} alone; otherwise, the
	 * former will fail because it hard codes the use of this namespace.
	 * </p>
	 */
	public static final String ATTRIBUTE_NAMESPACE = "liferay-ui:input-editor";

}