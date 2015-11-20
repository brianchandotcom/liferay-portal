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

package com.liferay.document.library.web.portlet.action;

import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.portal.kernel.diff.DiffResult;
import com.liferay.portal.kernel.diff.DiffUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.service.DLAppLocalService;
import com.liferay.portlet.documentlibrary.service.DLAppService;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.documentlibrary.util.DocumentConversionUtil;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/compare_versions"
	},
	service = MVCRenderCommand.class
)
public class CompareVersionsMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			compareVersions(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchFileEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return "/document_library/error.jsp";
			}
			else {
				throw new PortletException(e);
			}
		}

		return "/document_library/compare_versions.jsp";
	}

	protected void compareVersions(RenderRequest renderRequest)
		throws Exception {

		long sourceFileVersionId = ParamUtil.getLong(
			renderRequest, "sourceFileVersionId");
		long targetFileVersionId = ParamUtil.getLong(
			renderRequest, "targetFileVersionId");

		FileVersion sourceFileVersion = _dlAppService.getFileVersion(
			sourceFileVersionId);

		InputStream sourceIs = sourceFileVersion.getContentStream(false);

		String sourceExtension = sourceFileVersion.getExtension();

		if (sourceExtension.equals("css") || sourceExtension.equals("htm") ||
			sourceExtension.equals("html") || sourceExtension.equals("js") ||
			sourceExtension.equals("txt") || sourceExtension.equals("xml")) {

			String sourceContent = HtmlUtil.escape(StringUtil.read(sourceIs));

			sourceIs = new UnsyncByteArrayInputStream(
				sourceContent.getBytes(StringPool.UTF8));
		}

		FileVersion targetFileVersion = _dlAppLocalService.getFileVersion(
			targetFileVersionId);

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
					sourceFileVersion.getFileEntryId(),
					sourceFileVersion.getVersion());

				sourceIs = new FileInputStream(
					DocumentConversionUtil.convert(
						sourceTempFileId, sourceIs, sourceExtension, "txt"));
			}

			if (DocumentConversionUtil.isConvertBeforeCompare(
					targetExtension)) {

				String targetTempFileId = DLUtil.getTempFileId(
					targetFileVersion.getFileEntryId(),
					targetFileVersion.getVersion());

				targetIs = new FileInputStream(
					DocumentConversionUtil.convert(
						targetTempFileId, targetIs, targetExtension, "txt"));
			}
		}

		List<DiffResult>[] diffResults = DiffUtil.diff(
			new InputStreamReader(sourceIs), new InputStreamReader(targetIs));

		renderRequest.setAttribute(
			WebKeys.SOURCE_NAME,
			sourceFileVersion.getTitle() + StringPool.SPACE +
				sourceFileVersion.getVersion());
		renderRequest.setAttribute(
			WebKeys.TARGET_NAME,
			targetFileVersion.getTitle() + StringPool.SPACE +
				targetFileVersion.getVersion());
		renderRequest.setAttribute(WebKeys.DIFF_RESULTS, diffResults);
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLAppService(DLAppService dlAppService) {
		_dlAppService = dlAppService;
	}

	private volatile DLAppLocalService _dlAppLocalService;
	private volatile DLAppService _dlAppService;

}