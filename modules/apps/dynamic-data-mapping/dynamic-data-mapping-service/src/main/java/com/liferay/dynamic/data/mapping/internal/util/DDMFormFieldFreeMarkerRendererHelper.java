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

package com.liferay.dynamic.data.mapping.internal.util;

import com.liferay.portal.kernel.editor.Editor;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Pedro Queiroz
 */
@Component(
	immediate = true, service = DDMFormFieldFreeMarkerRendererHelper.class
)
public class DDMFormFieldFreeMarkerRendererHelper {

	public static Editor getEditor(HttpServletRequest httpServletRequest) {
		if (!BrowserSnifferUtil.isRtf(httpServletRequest)) {
			return _editors.get("simple");
		}

		if (Validator.isNull(_TEXT_HTML_EDITOR_WYSIWYG_DEFAULT)) {
			return _editors.get(_EDITOR_WYSIWYG_DEFAULT);
		}

		if (!_editors.containsKey(_TEXT_HTML_EDITOR_WYSIWYG_DEFAULT)) {
			return _editors.get(_EDITOR_WYSIWYG_DEFAULT);
		}

		return _editors.get(_TEXT_HTML_EDITOR_WYSIWYG_DEFAULT);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addEditor(Editor editor) {
		_editors.put(editor.getName(), editor);
	}

	protected void removeEditor(Editor editor) {
		_editors.remove(editor.getName());
	}

	private static final String _EDITOR_WYSIWYG_DEFAULT = PropsUtil.get(
		PropsKeys.EDITOR_WYSIWYG_DEFAULT);

	private static final String _TEXT_HTML_EDITOR_WYSIWYG_DEFAULT =
		PropsUtil.get("editor.wysiwyg.portal-impl.portlet.ddm.text_html.ftl");

	private static final Map<String, Editor> _editors =
		new ConcurrentHashMap<>();

}