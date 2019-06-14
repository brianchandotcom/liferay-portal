/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.knowledge.base.markdown.converter.internal.flexmark.factory;

import com.liferay.knowledge.base.markdown.converter.MarkdownConverter;
import com.liferay.knowledge.base.markdown.converter.factory.MarkdownConverterFactory;
import com.liferay.knowledge.base.markdown.converter.internal.flexmark.LiferayFlexmarkConverter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 * @author Rich Sezov
 */
@Component(
        enabled = true, immediate = true, service = MarkdownConverterFactory.class
)
public class MarkdownConverterFactoryImpl implements MarkdownConverterFactory {

    @Override
    public MarkdownConverter create() {
        return new LiferayFlexmarkConverter();
    }

}