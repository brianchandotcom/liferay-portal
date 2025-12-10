/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.exportimport.content.processor;

import com.liferay.document.library.util.DLURLHelper;
import com.liferay.exportimport.content.processor.ExportImportContentParser;
import com.liferay.exportimport.content.processor.constants.ExportImportContentParserConstants;
import com.liferay.exportimport.kernel.lar.ExportImportProcessCallbackRegistry;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.report.model.ExportImportReportEntry;
import com.liferay.exportimport.report.service.ExportImportReportEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.friendly.url.resolver.FileEntryFriendlyURLResolver;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.staging.StagingGroupHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(
	property = ExportImportContentParserConstants.CONTENT_PARSER_TYPE + "=" + ExportImportContentParserConstants.DOCUMENT_LIBRARY,
	service = ExportImportContentParser.class
)
public class DLReferencesExportImportContentParser
	implements ExportImportContentParser {

	@Override
	public String parseExportContent(
		String content, PortletDataContext portletDataContext) {

		StringBuilder sb = new StringBuilder(content);

		DLReferencesReverseIterator dlReferencesReverseIterator =
			new DLReferencesReverseIterator(
				content, _fileEntryFriendlyURLResolver,
				portletDataContext.getScopeGroupId());

		while (dlReferencesReverseIterator.hasNext()) {
			DLReferencesReverseIterator.DLReference dlReference =
				dlReferencesReverseIterator.next();

			FileEntry fileEntry = dlReference.getFileEntry();

			String externalReferenceCode = null;
			String uuid = null;

			if (fileEntry != null) {
				externalReferenceCode = fileEntry.getExternalReferenceCode();
				uuid = fileEntry.getUuid();
			}
			else if (_log.isDebugEnabled()) {
				_log.debug(
					"The file entry with friendly URL " +
						dlReference.getParameter("friendlyURL") +
							" does not exist");
			}

			Group group = dlReference.getGroup();

			DocumentLibraryReference documentLibraryReference =
				new DocumentLibraryReference(
					externalReferenceCode,
					dlReference.getParameter("friendlyURL"),
					group.getFriendlyURL(), group.getGroupId(), uuid);

			sb.replace(
				dlReference.getBeginPos(), dlReference.getEndPos(),
				documentLibraryReference.toString());
		}

		return sb.toString();
	}

	@Override
	public String parseImportContent(
			String content, PortletDataContext portletDataContext)
		throws Exception {

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		for (DocumentLibraryReference documentLibraryReference :
				DocumentLibraryReference.parse(content)) {

			Group group = _getGroup(
				portletDataContext.getCompanyId(), documentLibraryReference,
				groupIds);

			long groupId = (group != null) ? group.getGroupId() : 0L;

			FileEntry fileEntry =
				_fileEntryFriendlyURLResolver.resolveFriendlyURL(
					groupId,
					documentLibraryReference.getFileEntryFriendlyURL());

			if (!_isSameFileEntry(documentLibraryReference, fileEntry)) {
				if (_log.isDebugEnabled()) {
					if (fileEntry == null) {
						_log.debug(
							StringBundler.concat(
								"The file entry with friendly URL ",
								documentLibraryReference.
									getFileEntryFriendlyURL(),
								" and group friendly URL ",
								documentLibraryReference.getGroupFriendlyURL(),
								" does not exist"));
					}
					else {
						_log.debug(
							StringBundler.concat(
								"The file entry with friendly URL ",
								documentLibraryReference.
									getFileEntryFriendlyURL(),
								" and group with friendly URL ",
								documentLibraryReference.getGroupFriendlyURL(),
								" exists but it may contain wrong data"));
					}
				}

				boolean companyGroup = false;

				if (group != null) {
					companyGroup = _stagingGroupHelper.isCompanyGroup(group);
				}

				ExportImportReportEntry exportImportReportEntry =
					_exportImportReportEntryLocalService.
						addEmptyExportImportReportEntry(
							companyGroup ? 0L : groupId,
							portletDataContext.getCompanyId(),
							documentLibraryReference.getExternalReferenceCode(),
							_classNameLocalService.getClassNameId(
								FileEntry.class),
							GetterUtil.getLong(
								ExportImportThreadLocal.
									getExportImportConfigurationId()),
							FileEntry.class.getName());

				_exportImportProcessCallbackRegistry.registerCallback(
					portletDataContext.getExportImportProcessId(),
					() -> {
						FileEntry afterImportFileEntry =
							_fileEntryFriendlyURLResolver.resolveFriendlyURL(
								groupId,
								documentLibraryReference.
									getFileEntryFriendlyURL());

						if (_isSameFileEntry(
								documentLibraryReference,
								afterImportFileEntry)) {

							_exportImportReportEntryLocalService.
								deleteExportImportReportEntry(
									exportImportReportEntry.
										getExportImportReportEntryId());
						}

						return null;
					});
			}

			content = StringUtil.replaceLast(
				content, documentLibraryReference.getReferenceString(),
				_getUrl(documentLibraryReference, fileEntry, group));
		}

		return content;
	}

	private Group _getGroup(
		long companyId, DocumentLibraryReference documentLibraryReference,
		Map<Long, Long> groupIds) {

		try {
			if (groupIds.containsKey(documentLibraryReference.getGroupId())) {
				return _groupLocalService.getGroup(
					groupIds.get(documentLibraryReference.getGroupId()));
			}

			return _groupLocalService.fetchFriendlyURLGroup(
				companyId, documentLibraryReference.getGroupFriendlyURL());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return null;
	}

	private String _getUrl(
			DocumentLibraryReference documentLibraryReference,
			FileEntry fileEntry, Group group)
		throws Exception {

		if (fileEntry == null) {
			return _dlURLHelper.getPreviewURL(
				documentLibraryReference.getFileEntryFriendlyURL(),
				(group != null) ? group.getFriendlyURL() :
					documentLibraryReference.getGroupFriendlyURL());
		}

		return _dlURLHelper.getPreviewURL(
			fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK,
			false, false);
	}

	private boolean _isSameFileEntry(
		DocumentLibraryReference documentLibraryReference,
		FileEntry fileEntry) {

		if ((fileEntry == null) ||
			!StringUtil.equals(
				documentLibraryReference.getExternalReferenceCode(),
				fileEntry.getExternalReferenceCode()) ||
			!StringUtil.equals(
				documentLibraryReference.getUuid(), fileEntry.getUuid())) {

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLReferencesExportImportContentParser.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private ExportImportProcessCallbackRegistry
		_exportImportProcessCallbackRegistry;

	@Reference
	private ExportImportReportEntryLocalService
		_exportImportReportEntryLocalService;

	@Reference
	private FileEntryFriendlyURLResolver _fileEntryFriendlyURLResolver;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

	private static class DocumentLibraryReference {

		public static List<DocumentLibraryReference> parse(String value) {
			List<DocumentLibraryReference> documentLibraryReferences =
				new ArrayList<>();

			int startIndex = -1;

			while ((startIndex = value.indexOf(_BEGIN)) != -1) {
				int endIndex = value.indexOf(_END, startIndex);

				String reference = value.substring(
					startIndex, endIndex + _END.length());

				String externalReferenceCode = _decode(
					_getArgumentValue(_EXTERNAL_REFERENCE_CODE, reference));
				String fileEntryFriendlyURL = _getArgumentValue(
					_FILE_ENTRY_FRIENDLY_URL, reference);
				String groupFriendlyURL = _getArgumentValue(
					_GROUP_FRIENDLY_URL, reference);
				long groupId = GetterUtil.getLong(
					_getArgumentValue(_GROUP_ID, reference));
				String uuid = _getArgumentValue(_UUID, reference);

				documentLibraryReferences.add(
					new DocumentLibraryReference(
						externalReferenceCode, fileEntryFriendlyURL,
						groupFriendlyURL, groupId, reference, uuid));

				value = value.substring(endIndex + _END.length());
			}

			return documentLibraryReferences;
		}

		public String getExternalReferenceCode() {
			return _externalReferenceCode;
		}

		public String getFileEntryFriendlyURL() {
			return _fileEntryFriendlyURL;
		}

		public String getGroupFriendlyURL() {
			return _groupFriendlyURL;
		}

		public long getGroupId() {
			return _groupId;
		}

		public String getReferenceString() {
			return _referenceString;
		}

		public String getUuid() {
			return _uuid;
		}

		@Override
		public String toString() {
			return StringBundler.concat(
				_BEGIN, " $", _EXTERNAL_REFERENCE_CODE, "=",
				_encode(_externalReferenceCode), "$,$",
				_FILE_ENTRY_FRIENDLY_URL, "=", _fileEntryFriendlyURL, "$,$",
				_GROUP_FRIENDLY_URL, "=", _groupFriendlyURL, "$,$", _GROUP_ID,
				"=", _groupId, "$,$", _UUID, "=", _uuid, _END);
		}

		private static String _decode(String text) {
			return StringUtil.replace(
				text, new String[] {"\\$\\", "\\[\\", "\\]\\"},
				new String[] {"$", "[", "]"});
		}

		private static String _getArgumentValue(String name, String reference) {
			String key = "$" + name + "=";

			int beginIndex = reference.indexOf(key);

			int endIndex = StringUtil.indexOfAny(
				reference, new String[] {"$,$", _END}, beginIndex,
				reference.length());

			return reference.substring(beginIndex + key.length(), endIndex);
		}

		private DocumentLibraryReference(
			String externalReferenceCode, String fileEntryFriendlyURL,
			String groupFriendlyURL, long groupId, String uuid) {

			this(
				externalReferenceCode, fileEntryFriendlyURL, groupFriendlyURL,
				groupId, null, uuid);
		}

		private DocumentLibraryReference(
			String externalReferenceCode, String fileEntryFriendlyURL,
			String groupFriendlyURL, long groupId, String referenceString,
			String uuid) {

			_externalReferenceCode = GetterUtil.getString(
				externalReferenceCode);
			_fileEntryFriendlyURL = fileEntryFriendlyURL;
			_groupFriendlyURL = groupFriendlyURL;
			_groupId = groupId;
			_referenceString = referenceString;
			_uuid = GetterUtil.getString(uuid);
		}

		private String _encode(String text) {
			return StringUtil.replace(
				text, new String[] {"$", "[", "]"},
				new String[] {"\\$\\", "\\[\\", "\\]\\"});
		}

		private static final String _BEGIN = "[$dl-reference$";

		private static final String _END = "$]";

		private static final String _EXTERNAL_REFERENCE_CODE =
			"external-reference-code";

		private static final String _FILE_ENTRY_FRIENDLY_URL =
			"file-entry-friendly-url";

		private static final String _GROUP_FRIENDLY_URL = "group-friendly-url";

		private static final String _GROUP_ID = "group-id";

		private static final String _UUID = "uuid";

		private final String _externalReferenceCode;
		private final String _fileEntryFriendlyURL;
		private final String _groupFriendlyURL;
		private final long _groupId;
		private String _referenceString;
		private final String _uuid;

	}

}