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

package com.liferay.portal.model.impl;

/**
 * Represents a set of resources of one type that all share the same
 * permissions. This means that if a user can access one resource in a resource
 * block, they can access all of them.
 *
 * <p>
 * Resource blocks can only contain sets of resources that are all the same type
 * and in the same group. The first constraint is necessary because bitwise
 * action IDs do not always represent the same action between resource types.
 * For instance, the EDIT action could have the bitwise value 2 for bookmark
 * entries, and the value 16 for blog entries. Consequently, even if two
 * resources of different types share the same permissions on the bitwise level,
 * a user does not necessarily have permission to perform the same actions on
 * both.
 * </p>
 *
 * <p>
 * The second constraint is required because users can have different roles in
 * different groups. If resources from different groups were contained in the
 * same resource block, it would be impossible to check permissions properly
 * when loading resources from multiple groups simultaneously. A user could have
 * access to a certain resource block in one group, but not in another.
 * </p>
 *
 * <p>
 * The type of resource a resource block contains is specified by the
 * <code>name</code> attribute, which must be the fully qualified class name of
 * a model (such as a blog entry).
 * </p>
 *
 * <p>
 * The <code>permissionsHash</code> attribute holds a hashed representation of
 * the permissions shared by all the resources contained in the block. When the
 * permissions on an individual resource are changed, a new permissions hash is
 * calculated for it, and the database is checked to determine if a resource
 * block already exists with that hash. If one exists, the resource is moved
 * into that block; otherwise a new block is created.
 * </p>
 *
 * <p>
 * The <code>referenceCount</code> attribute stores the number of resources in
 * the resource block, meaning those resources whose
 * <code>resourceBlockId</code> is set to the primary key of this resource
 * block. If <code>referenceCount</code> reaches zero, the resource block is no
 * longer needed and may be safely deleted. To ensure that this reference count
 * remains accurate, resources must always call {@link
 * com.liferay.portal.service.impl.ResourceLocalServiceImpl#deleteResource} when
 * a resource is deleted.
 * </p>
 *
 * @author Connor McKay
 */
public class ResourceBlockImpl extends ResourceBlockBaseImpl {
}