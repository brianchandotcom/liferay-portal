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

package com.liferay.item.selector;

import java.util.List;

/**
 * Provides an interface that determines the type of entity that shall be
 * selected and information to return. The item selector uses the criterion to
 * display only the {@link ItemSelectorView} that can select that particular
 * entity type and that can support the {@link ItemSelectorReturnType}.
 *
 * <p>
 * Implementations of this interface can hold fine-grained details about
 * entities that can be selected. This detailed information should be specified
 * ideally using primitive types (or using very simple types that can be JSON
 * serialized). The implementation can set this data and make it accessible,
 * however desired. It must, however, specify a non-parametrized constructor.
 * </p>
 *
 * <p>
 * As an example, see the <a
 * href="https://github.com/liferay/liferay-portal/blob/7.0.x/modules/apps/collaboration/blogs/blogs-item-selector-api/src/main/java/com/liferay/blogs/item/selector/criterion/BlogsItemSelectorCriterion.java">BlogsItemSelectorCriterion</a>
 * class and how <a
 * href="https://github.com/liferay/liferay-portal/blob/7.0.x/modules/apps/collaboration/blogs/blogs-editor-configuration/src/main/java/com/liferay/blogs/editor/configuration/internal/BlogsContentEditorConfigContributor.java">BlogsContentEditorConfigContributor's
 * populateFileBrowserURL</a> method populates an instance of it and uses it.
 * </p>
 *
 * <p>
 * For simplicity, it is recommended that implementations extend {@link
 * BaseItemSelectorCriterion}.
 * </p>
 *
 * @author Iván Zaera
 */
public interface ItemSelectorCriterion {

	/**
	 * Returns the desired return types that the caller expects and can handle,
	 * ordered by preference.
	 *
	 * <p>
	 * The order of return types is important because the first return type that
	 * can be used will be used.
	 * </p>
	 *
	 * @return the return types ordered by preference
	 */
	public List<ItemSelectorReturnType> getDesiredItemSelectorReturnTypes();

	/**
	 * Sets a list of desired return types that the caller expects and can
	 * handle, ordered by preference.
	 *
	 * <p>
	 * The order of return types is important because the first return type that
	 * can be used will be used.
	 * </p>
	 *
	 * @param desiredItemSelectorReturnTypes a preference ordered list of the
	 *        return types the caller can handle
	 */
	public void setDesiredItemSelectorReturnTypes(
		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes);

}