/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.vocabulary.item.selector;

import com.liferay.item.selector.ItemSelectorReturnType;

/**
 * This return type should return the following information of a vocabulary as a
 * JSON object:
 *
 * <ul>
 * <li>
 * <code>assetVocabularyId</code>: The vocabularyId of the selected vocabulary
 * </li>
 * <li>
 * <code>groupId</code>: The vocabularyId of the selected vocabulary
 * </li>
 * <li>
 * <code>title</code>: The localized title of the selected vocabulary
 * </li>
 * <li>
 * <code>uuid</code>: The uuid of the selected vocabulary
 * </li>
 * </ul>
 *
 * @author Lourdes Fernández Besada
 */
public class AssetVocabularyItemSelectorReturnType
	implements ItemSelectorReturnType {
}