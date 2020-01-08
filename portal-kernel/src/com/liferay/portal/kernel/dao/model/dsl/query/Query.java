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

package com.liferay.portal.kernel.dao.model.dsl.query;

import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNode;
import com.liferay.portal.kernel.dao.model.dsl.set.Union;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Preston Crary
 */
@ProviderType
public interface Query extends ASTNode {

	public default NamedQuery as(String name) {
		return new NamedQuery(this, name);
	}

	public default Union union(Query query) {
		return new Union(this, false, query);
	}

	public default Union unionAll(Query query) {
		return new Union(this, true, query);
	}

}