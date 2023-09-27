/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector;

import com.liferay.item.selector.constants.ItemSelectorCriterionConstants;

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
 * href="https://github.com/liferay/liferay-portal/blob/7.0.x/modules/apps/blogs/blogs-item-selector-api/src/main/java/com/liferay/blogs/item/selector/criterion/BlogsItemSelectorCriterion.java">BlogsItemSelectorCriterion</a>
 * class and how <a
 * href="https://github.com/liferay/liferay-portal/blob/7.0.x/modules/apps/blogs/blogs-editor-configuration/src/main/java/com/liferay/blogs/editor/configuration/internal/BlogsContentEditorConfigContributor.java">BlogsContentEditorConfigContributor's
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

	public default String getMimeTypeRestriction() {
		return ItemSelectorCriterionConstants.MIME_TYPE_RESTRICTION_DEFAULT;
	}

	public void setDesiredItemSelectorReturnTypes(
		ItemSelectorReturnType... desiredItemSelectorReturnType);

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