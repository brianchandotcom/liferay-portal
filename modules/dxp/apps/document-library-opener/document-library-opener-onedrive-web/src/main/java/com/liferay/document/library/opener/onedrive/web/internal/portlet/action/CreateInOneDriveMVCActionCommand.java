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

package com.liferay.document.library.opener.onedrive.web.internal.portlet.action;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.opener.constants.DLOpenerMimeTypes;
import com.liferay.document.library.opener.onedrive.web.internal.DLOpenerOneDriveFileReference;
import com.liferay.document.library.opener.onedrive.web.internal.DLOpenerOneDriveManager;
import com.liferay.document.library.opener.onedrive.web.internal.constants.DLOpenerOneDriveMimeTypes;
import com.liferay.document.library.opener.onedrive.web.internal.constants.DLOpenerOneDriveWebKeys;
import com.liferay.document.library.opener.onedrive.web.internal.oauth.OAuth2FlowHelper;
import com.liferay.document.library.opener.onedrive.web.internal.oauth.OAuth2Manager;
import com.liferay.document.library.opener.onedrive.web.internal.util.DLOpenerTimestampUtil;
import com.liferay.document.library.opener.upload.UniqueFileEntryTitleProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	property = {
		"auth.token.ignore.mvc.action=true",
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"mvc.command.name=/document_library/create_in_office365"
	},
	service = MVCActionCommand.class
)
public class CreateInOneDriveMVCActionCommand extends BaseMVCActionCommand {

	@Activate
	public void activate() {
		_oAuth2FlowHelper = new OAuth2FlowHelper(
			_oAuth2Manager, _portal, _portletURLFactory);
	}

	@Deactivate
	public void deactivate() {
		_oAuth2FlowHelper = null;
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		OAuth2FlowHelper.OAuth2FlowResult oAuth2FlowResult =
			_oAuth2FlowHelper.execute(
				actionRequest, this::_executeCommand,
				_getSuccessURL(actionRequest));

		if (oAuth2FlowResult.isRedirect()) {
			actionResponse.sendRedirect(oAuth2FlowResult.getRedirectURL());
		}
	}

	private DLOpenerOneDriveFileReference _addDLOpenerOneDriveFileReference(
			Locale locale, long userId, long repositoryId, long folderId,
			String mimeType, ServiceContext serviceContext)
		throws PortalException {

		String title = _uniqueFileEntryTitleProvider.provide(
			serviceContext.getScopeGroupId(), folderId,
			serviceContext.getLocale());

		String sourceFileName = title;

		sourceFileName += DLOpenerOneDriveMimeTypes.getMimeTypeExtension(
			mimeType);

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		if (Objects.equals(DLOpenerMimeTypes.APPLICATION_VND_XLSX, mimeType)) {
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook();

			ResourceBundle resourceBundle =
				_resourceBundleLoader.loadResourceBundle(locale);

			xssfWorkbook.createSheet(
				_language.get(resourceBundle, "onedrive-excel-sheet"));

			try {
				xssfWorkbook.write(byteArrayOutputStream);
			}
			catch (IOException ioe) {
				throw new PortalException(ioe);
			}
		}

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		FileEntry fileEntry = _dlAppService.addFileEntry(
			repositoryId, folderId, sourceFileName, mimeType, title,
			StringPool.BLANK, StringPool.BLANK,
			byteArrayOutputStream.toByteArray(), serviceContext);

		_dlAppService.checkOutFileEntry(
			fileEntry.getFileEntryId(), serviceContext);

		return _dlOpenerOneDriveManager.createFile(userId, fileEntry);
	}

	private JSONObject _executeCommand(ActionRequest actionRequest)
		throws PortalException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		String timestamp = ParamUtil.getString(actionRequest, "timestamp");

		if (DLOpenerTimestampUtil.contains(
				httpServletRequest, Constants.ADD, timestamp)) {

			return JSONFactoryUtil.createJSONObject();
		}

		try {
			long repositoryId = ParamUtil.getLong(
				actionRequest, "repositoryId");
			long folderId = ParamUtil.getLong(actionRequest, "folderId");
			String contentType = ParamUtil.getString(
				actionRequest, "contentType",
				DLOpenerMimeTypes.APPLICATION_VND_DOCX);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			DLOpenerOneDriveFileReference dlOpenerOneDriveFileReference =
				TransactionInvokerUtil.invoke(
					_transactionConfig,
					() -> _addDLOpenerOneDriveFileReference(
						_portal.getLocale(actionRequest),
						_portal.getUserId(actionRequest), repositoryId,
						folderId, contentType, serviceContext));

			hideDefaultSuccessMessage(actionRequest);

			DLOpenerTimestampUtil.add(
				httpServletRequest, Constants.ADD, timestamp);

			return _saveDLOpenerOneDriveFileReference(
				actionRequest, dlOpenerOneDriveFileReference);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Throwable throwable) {
			throw new PortalException(throwable);
		}
	}

	private String _getSuccessURL(PortletRequest portletRequest) {
		return _portal.getCurrentURL(
			_portal.getHttpServletRequest(portletRequest));
	}

	private JSONObject _saveDLOpenerOneDriveFileReference(
		PortletRequest portletRequest,
		DLOpenerOneDriveFileReference dlOpenerOneDriveFileReference) {

		portletRequest.setAttribute(
			DLOpenerOneDriveWebKeys.DL_OPENER_ONE_DRIVE_FILE_REFERENCE,
			dlOpenerOneDriveFileReference);

		return JSONUtil.put(
			DLOpenerOneDriveWebKeys.DL_OPENER_ONE_DRIVE_FILE_REFERENCE,
			dlOpenerOneDriveFileReference);
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLOpenerOneDriveManager _dlOpenerOneDriveManager;

	@Reference
	private Language _language;

	private OAuth2FlowHelper _oAuth2FlowHelper;

	@Reference
	private OAuth2Manager _oAuth2Manager;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.document.library.opener.onedrive.web)"
	)
	private ResourceBundleLoader _resourceBundleLoader;

	private final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private UniqueFileEntryTitleProvider _uniqueFileEntryTitleProvider;

}