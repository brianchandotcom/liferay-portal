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

package com.liferay.blog.apio.architect.identifier;

import com.liferay.apio.architect.identifier.Identifier;

/**
 * Holds information about a {@code BlogPosting} identifier. The internal method
 * {@code com.liferay.blogs.model.BlogsEntry#getEntryId()} returns the blog
 * entry's ID.
 *
 * @author Javier Gamarra
 */
public interface BlogPostingIdentifier extends Identifier<Long> {
}