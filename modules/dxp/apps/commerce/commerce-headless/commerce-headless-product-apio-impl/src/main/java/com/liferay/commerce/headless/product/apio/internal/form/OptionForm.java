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

package com.liferay.commerce.headless.product.apio.internal.form;

import com.liferay.apio.architect.form.Form;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * @author Rodrigo Guedes de Souza
 */
public class OptionForm {

    public static Form<OptionForm> buildForm(
            Form.Builder<OptionForm> formBuilder) {

        return formBuilder.title(
                __ -> "The option form"
        ).description(
                __ -> "This form can be used to create a option"
        ).constructor(
                OptionForm::new
        ).addRequiredString(
                "name", OptionForm::_setName
        ).addOptionalString(
                "description", OptionForm::_setDescription
        ).addRequiredString(
                "fieldType", OptionForm::_setFieldType
        ).addRequiredString(
                "key", OptionForm::_setKey
        ).build();
    }

    public String getName() {
        return _name;
    }

    public Map<Locale, String> getNameMap() {
        return Collections.singletonMap(Locale.getDefault(), _name);
    }

    public String getDescritpion() {
        return _descritpion;
    }

    public Map<Locale, String> getDescriptionMap() {
        return Collections.singletonMap(Locale.getDefault(), _descritpion);
    }

    public String getFieldType() {
        return _fieldType;
    }

    public String getKey() {
        return _key;
    }

    private void _setName(String name) {
        _name = name;
    }

    private void _setDescription(String descritpion) {
        _descritpion = descritpion;
    }

    private void _setFieldType(String fieldType) {
        _fieldType = fieldType;
    }

    private void _setKey(String key) {
        _key = key;
    }

    private String _name;
    private String _descritpion;
    private String _fieldType;
    private String _key;

}