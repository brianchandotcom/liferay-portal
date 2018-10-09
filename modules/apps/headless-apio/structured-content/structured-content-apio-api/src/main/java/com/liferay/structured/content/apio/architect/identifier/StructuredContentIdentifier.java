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

package com.liferay.structured.content.apio.architect.identifier;

import com.liferay.apio.architect.identifier.Identifier;

/**
 * Holds information about a structured content identifier. The internal method
 * {@code com.liferay.journal.model.JournalArticle#getArticleId()} returns the
 * ID of the structured content ({@code JournalArticle}).
 *
 * @author Alejandro Hernández
 */
public interface StructuredContentIdentifier extends Identifier<Long> {
}