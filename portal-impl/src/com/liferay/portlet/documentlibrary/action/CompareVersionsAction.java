/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.action;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.DiffResult;
import com.liferay.portal.kernel.util.DiffUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.documentlibrary.util.DocumentConversionUtil;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.List;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Bruno Farache
 */
public class CompareVersionsAction extends PortletAction {

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			compareVersions(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchFileEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				setForward(renderRequest, "portlet.document_library.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			"portlet.document_library.compare_versions");
	}

	protected void compareVersions(RenderRequest renderRequest)
		throws Exception {

		long fileEntryId = ParamUtil.getLong(renderRequest, "fileEntryId");

		String sourceVersion = ParamUtil.getString(
			renderRequest, "sourceVersion");
		String targetVersion = ParamUtil.getString(
			renderRequest, "targetVersion");

		FileEntry fileEntry = DLAppServiceUtil.getFileEntry(fileEntryId);

		FileVersion sourceFileVersion = fileEntry.getFileVersion(sourceVersion);

		InputStream sourceIs = sourceFileVersion.getContentStream(false);

		String sourceExtension = sourceFileVersion.getExtension();

		if (sourceExtension.equals("css") || sourceExtension.equals("htm") ||
			sourceExtension.equals("html") || sourceExtension.equals("js") ||
			sourceExtension.equals("txt") || sourceExtension.equals("xml")) {

			String sourceContent = HtmlUtil.escape(StringUtil.read(sourceIs));

			sourceIs = new UnsyncByteArrayInputStream(
				sourceContent.getBytes(StringPool.UTF8));
		}

		FileVersion targetFileVersion = fileEntry.getFileVersion(targetVersion);

		InputStream targetIs = targetFileVersion.getContentStream(false);

		String targetExtension = targetFileVersion.getExtension();

		if (targetExtension.equals("css") || targetExtension.equals("htm") ||
			targetExtension.equals("html") || targetExtension.equals("js") ||
			targetExtension.equals("txt") || targetExtension.equals("xml")) {

			String targetContent = HtmlUtil.escape(StringUtil.read(targetIs));

			targetIs = new UnsyncByteArrayInputStream(
				targetContent.getBytes(StringPool.UTF8));
		}

		if (DocumentConversionUtil.isEnabled()) {
			if (DocumentConversionUtil.isConvertBeforeCompare(
					sourceExtension)) {

				String sourceTempFileId = DLUtil.getTempFileId(
					fileEntryId, sourceVersion);

				sourceIs = new FileInputStream(
					DocumentConversionUtil.convert(
						sourceTempFileId, sourceIs, sourceExtension, "txt"));
			}

			if (DocumentConversionUtil.isConvertBeforeCompare(
					targetExtension)) {

				String targetTempFileId = DLUtil.getTempFileId(
					fileEntryId, targetVersion);

				targetIs = new FileInputStream(
					DocumentConversionUtil.convert(
						targetTempFileId, targetIs, targetExtension, "txt"));
			}
		}

		List<DiffResult>[] diffResults = DiffUtil.diff(
			new InputStreamReader(sourceIs), new InputStreamReader(targetIs));

		renderRequest.setAttribute(
			WebKeys.SOURCE_NAME,
			sourceFileVersion.getTitle() + StringPool.SPACE + sourceVersion);
		renderRequest.setAttribute(
			WebKeys.TARGET_NAME,
			targetFileVersion.getTitle() + StringPool.SPACE + targetVersion);
		renderRequest.setAttribute(WebKeys.DIFF_RESULTS, diffResults);
	}

}