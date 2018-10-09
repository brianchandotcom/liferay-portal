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

package com.liferay.aggregate.rating.apio.architect.identifier;

import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.portal.apio.identifier.ClassNameClassPK;

/**
 * Holds information about an {@code AggregateRating} identifier. A {@code
 * ClassNameClassPK} identifies the aggregate rating. The {@code
 * ClassNameClassPK} instance can be created with {@code
 * ClassNameClassPK#create(com.liferay.portal.kernel.model.ClassedModel)} or
 * {@code ClassNameClassPK#create(String, long)}.
 *
 * @author Javier Gamarra
 */
public interface AggregateRatingIdentifier
	extends Identifier<ClassNameClassPK> {
}