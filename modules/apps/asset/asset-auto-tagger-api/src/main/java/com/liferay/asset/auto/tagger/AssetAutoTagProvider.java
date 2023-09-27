/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger;

import java.util.Collection;

/**
 * Tags assets in conjunction with {@link AssetAutoTagger}. Implementations of
 * this interface are called from {@link AssetAutoTagger#tag(AssetEntry)} to
 * automatically tag assets.
 *
 * <p>
 * Implementations must specify a value for the OSGi property {@code
 * model.class.name} so they can be called only for the models they can handle.
 * For example, an {@code AssetAutoTagProvider} that can analyze images from the
 * Document Library and generate tags according to the image's content would
 * have this OSGi property setting:
 * </p>
 *
 * <p>
 * {@code
 * model.class.name=com.liferay.document.library.kernel.model.DLFileEntry}
 * </p>
 *
 * @author Alejandro Tardín
 */
public interface AssetAutoTagProvider<T> {

	/**
	 * Returns the tag names for a given model. To avoid a runtime error, the
	 * model's type must match that of the {@code model.class.name} property
	 * setting. For example, an {@code AssetAutoTagProvider} with {@code
	 * model.class.name} set to {@code DLFileEntry} would implement this method
	 * as:
	 *
	 * <p>
	 * {@code public List<String> getTagNames(DLFileEntry dlFileEntry)}
	 * </p>
	 *
	 * @param  model the model
	 * @return the tag names for the model
	 */
	public Collection<String> getTagNames(T model);

}