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

package com.liferay.document.library.video.external.shortcut;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides an external video shortcut to be used by the document library to
 * link to and render videos hosted in external services.
 *
 * <p>
 * Implementations of this interface will be returned by an implementation of
 * {@link
 * com.liferay.document.library.video.external.shortcut.provider.DLVideoExternalShortcutProvider}
 * </p>
 *
 * @author Alejandro Tardín
 * @review
 */
public interface DLVideoExternalShortcut {

	/**
	 * Returns the description of the external video (if any).
	 *
	 * <p>
	 * It will be used to fill the document's description field when saving it
	 * to the document library.
	 * </p>
	 *
	 * @return the description
	 * @review
	 */
	public default String getDescription() {
		return null;
	}

	/**
	 * Returns the thumbnail URL of the external video (if any).
	 *
	 * <p>
	 * It will be used inside the Documents and Media UI (cards, lists, etc...).
	 * </p>
	 *
	 * @return the thumbnail URL.
	 * @review
	 */
	public default String getThumbnailURL() {
		return null;
	}

	/**
	 * Returns the title of the external video (if any).
	 *
	 * <p>
	 * It will be used to fill the document's title field when saving it to the
	 * document library.
	 * </p>
	 *
	 * @return the title
	 * @review
	 */
	public default String getTitle() {
		return null;
	}

	/**
	 * Returns the URL of the external video.
	 *
	 * @return the URL
	 * @review
	 */
	public String getURL();

	/**
	 * Returns a snippet of HTML that will be used to embed the video inside a
	 * specific content.
	 *
	 * <p>
	 * This will typically render an <code>iframe</code> or a <code>video</code>
	 * element.
	 * </p>
	 *
	 * @return the HTML
	 * @review
	 */
	public String renderHTML(HttpServletRequest httpServletRequest);

}