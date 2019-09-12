/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.document.library.opener.onedrive.web.internal.portlet.action.util;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.opener.constants.DLOpenerMimeTypes;
import com.liferay.document.library.opener.onedrive.web.internal.DLOpenerOneDriveFileReference;
import com.liferay.document.library.opener.onedrive.web.internal.DLOpenerOneDriveManager;
import com.liferay.document.library.opener.onedrive.web.internal.constants.DLOpenerOneDriveMimeTypes;
import com.liferay.document.library.opener.onedrive.web.internal.translator.Translator;
import com.liferay.document.library.opener.upload.UniqueFileEntryTitleProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.Locale;
import java.util.Objects;

import javax.portlet.PortletRequest;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = CreateInOneDriveHelper.class)
public class CreateInOneDriveHelper {

	public JSONObject executeCommand(PortletRequest portletRequest)
		throws PortalException {

		try {
			long repositoryId = ParamUtil.getLong(
				portletRequest, "repositoryId");
			long folderId = ParamUtil.getLong(portletRequest, "folderId");
			String contentType = ParamUtil.getString(
				portletRequest, "contentType",
				DLOpenerMimeTypes.APPLICATION_VND_DOCX);
			String title = ParamUtil.getString(
				portletRequest, "title",
				_translator.translateKey(
					_portal.getLocale(portletRequest), "untitled"));

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				portletRequest);

			DLOpenerOneDriveFileReference dlOpenerOneDriveFileReference =
				TransactionInvokerUtil.invoke(
					_transactionConfig,
					() -> _addDLOpenerOneDriveFileReference(
						_portal.getLocale(portletRequest),
						_portal.getUserId(portletRequest), repositoryId,
						folderId, contentType, title, serviceContext));

			String oneDriveBackgroundTaskStatusURL =
				_oneDriveURLHelper.getBackgroundTaskStatusURL(
					portletRequest, dlOpenerOneDriveFileReference);

			return JSONUtil.put(
				"dialogMessage",
				_translator.translateKey(
					_portal.getLocale(portletRequest),
					"you-are-being-redirected-to-an-external-editor-to-" +
						"create-this-document")
			).put(
				"oneDriveBackgroundTaskStatusURL",
				oneDriveBackgroundTaskStatusURL
			);
		}
		catch (PortalException | RuntimeException e) {
			throw e;
		}
		catch (Throwable throwable) {
			throw new PortalException(throwable);
		}
	}

	private DLOpenerOneDriveFileReference _addDLOpenerOneDriveFileReference(
			Locale locale, long userId, long repositoryId, long folderId,
			String mimeType, String title, ServiceContext serviceContext)
		throws PortalException {

		String uniqueTitle = _uniqueFileEntryTitleProvider.provide(
			serviceContext.getScopeGroupId(), folderId, title);

		String sourceFileName = uniqueTitle;

		sourceFileName += DLOpenerOneDriveMimeTypes.getMimeTypeExtension(
			mimeType);

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		if (Objects.equals(DLOpenerMimeTypes.APPLICATION_VND_XLSX, mimeType)) {
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook();

			xssfWorkbook.createSheet(
				_translator.translateKey(locale, "onedrive-excel-sheet"));

			try {
				xssfWorkbook.write(byteArrayOutputStream);
			}
			catch (IOException ioe) {
				throw new PortalException(ioe);
			}
		}

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		FileEntry fileEntry = _dlAppService.addFileEntry(
			repositoryId, folderId, sourceFileName, mimeType, uniqueTitle,
			StringPool.BLANK, StringPool.BLANK,
			byteArrayOutputStream.toByteArray(), serviceContext);

		_dlAppService.checkOutFileEntry(
			fileEntry.getFileEntryId(), serviceContext);

		return _dlOpenerOneDriveManager.createFile(userId, fileEntry);
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLOpenerOneDriveManager _dlOpenerOneDriveManager;

	@Reference
	private OneDriveURLHelper _oneDriveURLHelper;

	@Reference
	private Portal _portal;

	private final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private Translator _translator;

	@Reference
	private UniqueFileEntryTitleProvider _uniqueFileEntryTitleProvider;

}