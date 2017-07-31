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

package com.liferay.vulcan.liferay.scope;

/**
 * Use this interface when you want to specify that models represented in a
 * {@link com.liferay.vulcan.representor.Resource} needs a valid group ID. This
 * interface should only be implemented in resources.
 *
 * <p>
 * When scoping a {@link com.liferay.vulcan.representor.Resource} with
 * <code>GroupScoped</code>, that resource's URI starts with
 * <code>/group/{groupId}</code>.
 * </p>
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
public interface GroupScoped<T> {

	/**
	 * Returns the group ID of a model.
	 *
	 * @param  model model's instance.
	 * @return model's group ID.
	 */
	public long getGroupId(T model);

}