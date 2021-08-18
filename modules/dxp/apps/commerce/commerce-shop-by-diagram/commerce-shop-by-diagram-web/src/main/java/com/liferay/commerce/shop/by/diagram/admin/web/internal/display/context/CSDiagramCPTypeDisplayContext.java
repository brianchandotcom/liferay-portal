package com.liferay.commerce.shop.by.diagram.admin.web.internal.display.context;

import com.liferay.commerce.product.exception.NoSuchCPAttachmentFileEntryException;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramSettingService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;


public class CSDiagramCPTypeDisplayContext {

	public CSDiagramCPTypeDisplayContext(
		CPDefinitionDiagramSettingService cpDefinitionDiagramSettingService,
		DLURLHelper dlURLHelper) {
		_cpDefinitionDiagramSettingService = cpDefinitionDiagramSettingService;
		_dlURLHelper = dlURLHelper;
	}

	public String getImageURL(long cpDefinitionId) throws Exception {
		FileEntry fileEntry = _fetchFileEntry(cpDefinitionId);

		if (fileEntry != null) {
			return _dlURLHelper.getDownloadURL(
				fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK);
		}

		return StringPool.BLANK;
	}

	private FileEntry _fetchFileEntry(long cpDefinitionId) throws Exception {
		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			_cpDefinitionDiagramSettingService.
				fetchCPDefinitionDiagramSettingByCPDefinitionId(cpDefinitionId);

		if (cpDefinitionDiagramSetting == null) {
			return null;
		}

		try {
			CPAttachmentFileEntry cpAttachmentFileEntry =
				cpDefinitionDiagramSetting.getCPAttachmentFileEntry();

			return cpAttachmentFileEntry.fetchFileEntry();
		}
		catch (NoSuchCPAttachmentFileEntryException
			noSuchCPAttachmentFileEntryException) {

			if (_log.isInfoEnabled()) {
				_log.info(
					noSuchCPAttachmentFileEntryException,
					noSuchCPAttachmentFileEntryException);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CSDiagramCPTypeDisplayContext.class);

	private CPDefinitionDiagramSettingService
		_cpDefinitionDiagramSettingService;

	private DLURLHelper _dlURLHelper;
}