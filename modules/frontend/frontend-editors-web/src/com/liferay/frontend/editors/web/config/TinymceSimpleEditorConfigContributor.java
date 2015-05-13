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

package com.liferay.frontend.editors.web.config;

import com.liferay.portal.kernel.editor.config.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.theme.ThemeDisplay;

import org.osgi.service.component.annotations.Component;

import java.util.Map;

/**
 * @author Ambrin Chaudhary
 */
@Component(
        property = {"editor.name=tinymce_simple"},
        service = EditorConfigContributor.class
)
public class TinymceSimpleEditorConfigContributor extends BaseTinymceEditorConfigConfigurator {

    @Override
    public void populateConfigJSONObject(
            JSONObject jsonObject,
            Map<String, Object> inputEditorTaglibAttributes,
            ThemeDisplay themeDisplay,
            LiferayPortletResponse liferayPortletResponse) {

        super.populateConfigJSONObject(jsonObject, inputEditorTaglibAttributes, themeDisplay, liferayPortletResponse);

        boolean showSource = GetterUtil.getBoolean(
                (String) inputEditorTaglibAttributes.get(
                        "liferay-ui:input-editor:showSource"));

        String plugins = "contextmenu preview print";

        if (showSource) {
            plugins+= " code";
        }

        jsonObject.put("plugins", plugins);

        StringBundler sb = new StringBundler(3);

        sb.append("bold italic underline | alignleft aligncenter alignright alignjustify | ");

        if (showSource) {
            sb.append("code ");
        }

        sb.append("preview print");

        jsonObject.put("toolbar", sb.toString());
    }
}
