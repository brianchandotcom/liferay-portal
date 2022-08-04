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

package com.liferay.document.library.google.docs.internal.display.context;

import com.liferay.document.library.display.context.BaseIGViewFileVersionDisplayContext;
import com.liferay.document.library.display.context.IGViewFileVersionDisplayContext;
import com.liferay.document.library.google.docs.internal.helper.GoogleDocsMetadataHelper;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class GoogleDocsIGViewFileVersionDisplayContext
	extends BaseIGViewFileVersionDisplayContext
	implements IGViewFileVersionDisplayContext {

	public GoogleDocsIGViewFileVersionDisplayContext(
		IGViewFileVersionDisplayContext parentIGViewFileVersionDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FileVersion fileVersion,
		GoogleDocsMetadataHelper googleDocsMetadataHelper) {

		super(
			_UUID, parentIGViewFileVersionDisplayContext, httpServletRequest,
			httpServletResponse, fileVersion);

		_googleDocsUIItemsProcessor = new GoogleDocsUIItemsProcessor(
			httpServletRequest, googleDocsMetadataHelper);
	}

	private static final UUID _UUID = UUID.fromString(
		"D60D21C4-9626-4EDF-A658-336198DB4A34");

	private final GoogleDocsUIItemsProcessor _googleDocsUIItemsProcessor;

}