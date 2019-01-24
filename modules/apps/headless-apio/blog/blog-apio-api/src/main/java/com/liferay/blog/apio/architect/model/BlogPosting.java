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

package com.liferay.blog.apio.architect.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Represents a blog posting exposed through the API. See <a
 * href="https://schema.org/BlogPosting">BlogPosting </a> for more information.
 *
 * @author Alejandro Hernández
 * @author Víctor Galán
 * @review
 */
public interface BlogPosting {

	/**
	 * Returns the blog posting's alternate headline. See <a
	 * href="https://schema.org/alternativeHeadline">alternativeHeadline </a>
	 * for more information.
	 *
	 * @return the alternate headline
	 * @review
	 */
	public String getAlternativeHeadline();

	/**
	 * Returns the blog posting's body. See <a
	 * href="https://schema.org/articleBody">articleBody </a> for more
	 * information.
	 *
	 * @return the blog posting's body
	 * @review
	 */
	public String getArticleBody();

	/**
	 * Returns the blog posting's cover image caption.
	 *
	 * @return the image caption
	 * @review
	 */
	public String getCaption();

	/**
	 * Returns a list of the blog posting's categories
	 *
	 * @return the categories
	 * @review
	 */
	public List<Long> getCategories();

	/**
	 * Returns the blog posting's creation date. See <a
	 * href="https://schema.org/dateCreated">dateCreated </a> for more
	 * information.
	 *
	 * @return the creation date
	 * @review
	 */
	public Date getCreatedDate();

	/**
	 * Returns the ID of the blog posting's creator. See <a
	 * href="https://schema.org/creator">creator </a> for more information.
	 *
	 * @return the creator's ID
	 * @review
	 */
	public Long getCreatorId();

	/**
	 * Returns the blog posting's description. See <a
	 * href="https://schema.org/description">description </a> for more
	 * information.
	 *
	 * @return the headline
	 * @review
	 */
	public String getDescription();

	/**
	 * Returns the the blog posting's friendly URL
	 *
	 * @return the friendly URL
	 * @review
	 */
	public String getFriendlyURLPath();

	/**
	 * Returns the blog posting's headline. See <a
	 * href="https://schema.org/headline">headline </a> for more information.
	 *
	 * @return the headline
	 * @review
	 */
	public String getHeadline();

	/**
	 * Returns the the blog posting's cover image ID
	 *
	 * @return the keywords
	 * @review
	 */
	public Long getImageId();

	/**
	 * Returns a list of the blog posting's keywords
	 *
	 * @return the keywords
	 * @review
	 */
	public List<String> getKeywords();

	/**
	 * Returns the blog posting's modification date. See <a
	 * href="https://schema.org/dateModified">dateModified </a> for more
	 * information.
	 *
	 * @return the modification date
	 * @review
	 */
	public Date getModifiedDate();

	/**
	 * Returns the blog posting's publication date. See <a
	 * href="https://schema.org/datePublished">datePublished </a> for more
	 * information.
	 *
	 * @return the modification date
	 * @review
	 */
	public Optional<LocalDateTime> getPublishedDateOptional();

}