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

package com.liferay.frontend.js.components.sample.web.internal.display.context;

import com.liferay.frontend.js.components.sample.web.constants.ComponentsSamplePortletKeys;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Carlos Lancha
 */
public class TranslationManagerDisplayContext {

	public List<String> getActiveLanguageIds() {
        if (_activeLanguageIds != null) {
			return _activeLanguageIds;
		}

        _activeLanguageIds = new ArrayList<String>();

        _activeLanguageIds.add(getDefaultLanguageId());
        _activeLanguageIds.add("ca_ES");
        _activeLanguageIds.add("fr_FR");

        return _activeLanguageIds;
    }

    public Set<Locale> getAvailableLocales() {
        if (_availableLocales != null) {
            return _availableLocales;
        }

        _availableLocales = LanguageUtil.getAvailableLocales();

        return _availableLocales;
    }

    public String getDefaultLanguageId() {
        if (_defaultLanguageId != null) {
            return _defaultLanguageId;
        }

        _defaultLanguageId = LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault());

        return _defaultLanguageId;
    }

    public Map<String, Object> getTranslations() {
        if (_translations != null) {
            return _translations;
        }

        _translations = HashMapBuilder.<String, Object>put(
	        "ca-ES", "Lorem"
        ).build();

        return _translations;
    }

    private List<String> _activeLanguageIds;
    private Set<Locale> _availableLocales;
    private String _defaultLanguageId;
    private Map<String, Object> _translations;
}