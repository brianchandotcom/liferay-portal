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

package com.liferay.portal.kernel.atom;

import java.io.InputStream;

/**
 * @author Igor Spasic
 */
public abstract class BaseMediaAtomCollectionAdapter<E>
	extends BaseAtomCollectionAdapter<E> {

	@Override
	public abstract String getMediaContentType(E entry);

	@Override
	public abstract String getMediaName(E entry) throws AtomException;

	@Override
	public abstract InputStream getMediaStream(E entry) throws AtomException;

	@Override
	protected abstract E doPostMedia(
			String mimeType, String slug, InputStream inputStream,
			AtomRequestContext atomRequestContext)
		throws Exception;

	@Override
	protected abstract void doPutMedia(
			E entry, String mimeType, String slug, InputStream inputStream,
			AtomRequestContext atomRequestContext)
		throws Exception;

}