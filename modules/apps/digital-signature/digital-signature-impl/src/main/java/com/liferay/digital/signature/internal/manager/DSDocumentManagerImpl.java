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

package com.liferay.digital.signature.internal.manager;

import com.liferay.digital.signature.internal.http.DSHttp;
import com.liferay.digital.signature.manager.DSDocumentManager;
import com.liferay.petra.string.StringBundler;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = DSDocumentManager.class)
public class DSDocumentManagerImpl implements DSDocumentManager {

	@Override
	public byte[] getDSDocumentsAsBytes(
		long companyId, long groupId, String dsEnvelopeId) {

		return _dsHttp.getAsBytes(
			companyId, groupId,
			StringBundler.concat(
				"envelopes/", dsEnvelopeId,
				"/documents/archive?escape_non_ascii_filenames=true",
				"&include=document,summary,voice_print&language=en"));
	}

	@Reference
	private DSHttp _dsHttp;

}