/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.exportimport.content.processor;

import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.exportimport.configuration.ExportImportServiceConfiguration;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.exception.ExportImportContentProcessorException;
import com.liferay.exportimport.kernel.exception.ExportImportContentValidationException;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.friendly.url.resolver.FileEntryFriendlyURLResolver;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = "content.processor.type=DLReferences",
	service = ExportImportContentProcessor.class
)
public class DLReferencesExportImportContentProcessor
	implements ExportImportContentProcessor<String> {

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent,
			boolean escapeContent)
		throws Exception {

		Group group = _groupLocalService.getGroup(
			portletDataContext.getGroupId());

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		if (group.isStaged() && !group.isStagedRemotely() &&
			!group.isStagedPortlet(PortletKeys.DOCUMENT_LIBRARY) &&
			ExportImportThreadLocal.isStagingInProcess()) {

			return content;
		}

		return _replaceExportDLReferences(
			portletDataContext, stagedModel, content, exportReferencedContent);
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		return _replaceImportDLReferences(
			portletDataContext, stagedModel, content);
	}

	@Override
	public void validateContentReferences(long groupId, String content)
		throws PortalException {

		if (_isValidateDLReferences()) {
			_validateDLReferences(groupId, content);
		}
	}

	private boolean _isValidateDLReferences() {
		try {
			ExportImportServiceConfiguration exportImportServiceConfiguration =
				_configurationProvider.getCompanyConfiguration(
					ExportImportServiceConfiguration.class,
					CompanyThreadLocal.getCompanyId());

			return exportImportServiceConfiguration.
				validateFileEntryReferences();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return true;
	}

	private String _replaceExportDLReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent)
		throws Exception {

		StringBuilder sb = new StringBuilder(content);

		DLReferencesReverseIterator dlReferencesReverseIterator =
			new DLReferencesReverseIterator(
				content, _fileEntryFriendlyURLResolver,
				portletDataContext.getScopeGroupId());

		while (dlReferencesReverseIterator.hasNext()) {
			DLReferencesReverseIterator.DLReference dlReference =
				dlReferencesReverseIterator.next();

			FileEntry fileEntry = dlReference.getFileEntry();

			if (fileEntry == null) {
				continue;
			}

			if (exportReferencedContent && !fileEntry.isInTrash()) {
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, stagedModel, fileEntry,
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
			}
			else {
				Element entityElement = portletDataContext.getExportDataElement(
					stagedModel);

				String referenceType =
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY;

				if (fileEntry.isInTrash()) {
					referenceType =
						PortletDataContext.REFERENCE_TYPE_DEPENDENCY_DISPOSABLE;
				}

				portletDataContext.addReferenceElement(
					stagedModel, entityElement, fileEntry, referenceType, true);
			}

			String path = ExportImportPathUtil.getModelPath(fileEntry);

			StringBundler exportedReferenceSB = new StringBundler(10);

			exportedReferenceSB.append("[$dl-reference=");
			exportedReferenceSB.append(path);

			if (dlReference.containsParameter("friendlyURL")) {
				exportedReferenceSB.append("$,$include-friendly-url=true");
			}
			else {
				exportedReferenceSB.append("$,$include-uuid=");
				exportedReferenceSB.append(
					dlReference.containsParameter("uuid"));
			}

			exportedReferenceSB.append("$]");

			if (fileEntry.isInTrash()) {
				String originalReference = sb.substring(
					dlReference.getBeginPos(), dlReference.getEndPos());

				exportedReferenceSB.append("[#dl-reference=");
				exportedReferenceSB.append(originalReference);

				if (dlReference.containsParameter("friendlyURL")) {
					exportedReferenceSB.append("#,#include-friendly-url=true");
				}
				else {
					exportedReferenceSB.append("#,#include-uuid=");
					exportedReferenceSB.append(
						dlReference.containsParameter("uuid"));
				}

				exportedReferenceSB.append("#]");
			}

			sb.replace(
				dlReference.getBeginPos(), dlReference.getEndPos(),
				exportedReferenceSB.toString());
		}

		return sb.toString();
	}

	private String _replaceImportDLReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		List<Element> referenceElements =
			portletDataContext.getReferenceElements(
				stagedModel, DLFileEntry.class);

		for (Element referenceElement : referenceElements) {
			Long classPK = GetterUtil.getLong(
				referenceElement.attributeValue("class-pk"));

			Element referenceDataElement =
				portletDataContext.getReferenceDataElement(
					stagedModel, DLFileEntry.class, classPK);

			String path = null;

			if (referenceDataElement != null) {
				path = referenceDataElement.attributeValue("path");
			}

			if (Validator.isNull(path)) {
				long groupId = GetterUtil.getLong(
					referenceElement.attributeValue("group-id"));
				String className = referenceElement.attributeValue(
					"class-name");

				path = ExportImportPathUtil.getModelPath(
					groupId, className, classPK);
			}

			while (content.contains(
						"[$dl-reference=" + path +
							"$,$include-friendly-url=true$]") ||
				   content.contains(
					   "[$dl-reference=" + path + "$,$include-uuid=false$]") ||
				   content.contains(
					   "[$dl-reference=" + path + "$,$include-uuid=true$]")) {

				try {
					StagedModelDataHandlerUtil.importReferenceStagedModel(
						portletDataContext, stagedModel, DLFileEntry.class,
						classPK);
				}
				catch (Exception exception) {
					StringBundler exceptionSB = new StringBundler(6);

					exceptionSB.append("Unable to process file entry ");
					exceptionSB.append(classPK);
					exceptionSB.append(" for ");
					exceptionSB.append(stagedModel.getModelClassName());
					exceptionSB.append(" with primary key ");
					exceptionSB.append(stagedModel.getPrimaryKeyObj());

					ExportImportContentProcessorException
						exportImportContentProcessorException =
							new ExportImportContentProcessorException(
								exceptionSB.toString(), exception);

					if (_log.isDebugEnabled()) {
						_log.debug(
							exceptionSB.toString(),
							exportImportContentProcessorException);
					}
					else if (_log.isWarnEnabled()) {
						_log.warn(exceptionSB.toString());
					}
				}

				Map<Long, Long> dlFileEntryIds =
					(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
						DLFileEntry.class);

				long fileEntryId = MapUtil.getLong(
					dlFileEntryIds, classPK, classPK);

				int beginPos = content.indexOf("[$dl-reference=" + path);

				int endPos = content.indexOf("$]", beginPos) + 2;

				FileEntry importedFileEntry = null;

				try {
					importedFileEntry = _dlAppLocalService.getFileEntry(
						fileEntryId);
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(portalException);
					}

					if (content.startsWith("[#dl-reference=", endPos)) {
						int prefixPos = endPos + "[#dl-reference=".length();

						int postfixPos = content.indexOf("#]", prefixPos);

						String originalReference = content.substring(
							prefixPos, postfixPos);

						String exportedReference = content.substring(
							beginPos, postfixPos + 2);

						content = StringUtil.replace(
							content, exportedReference, originalReference);
					}
					else {
						throw portalException;
					}

					continue;
				}

				boolean appendVersion = false;

				if (!content.contains("$include-friendly-url=true$")) {
					appendVersion = true;
				}

				String url = _dlURLHelper.getPreviewURL(
					importedFileEntry, importedFileEntry.getFileVersion(), null,
					StringPool.BLANK, appendVersion, false);

				if (url.contains(StringPool.QUESTION)) {
					url = url.substring(
						0, url.lastIndexOf(StringPool.QUESTION));
				}

				String urlWithoutUUID = url.substring(
					0, url.lastIndexOf(StringPool.SLASH));

				String exportedReferenceFriendlyURL =
					"[$dl-reference=" + path + "$,$include-friendly-url=true$]";
				String exportedReferenceWithoutUUID =
					"[$dl-reference=" + path + "$,$include-uuid=false$]";
				String exportedReferenceWithUUID =
					"[$dl-reference=" + path + "$,$include-uuid=true$]";

				if (content.startsWith("[#dl-reference=", endPos)) {
					int friendlyURLPosition = content.indexOf(
						"#,#include-friendly-url=true", beginPos);

					if (friendlyURLPosition != -1) {
						endPos = friendlyURLPosition + 2;
					}
					else {
						endPos =
							content.indexOf("#,#include-uuid=", beginPos) + 2;
					}

					exportedReferenceFriendlyURL =
						content.substring(beginPos, endPos) +
							"#include-friendly-url=true#]";
					exportedReferenceWithoutUUID =
						content.substring(beginPos, endPos) +
							"#include-uuid=false#]";
					exportedReferenceWithUUID =
						content.substring(beginPos, endPos) +
							"#include-uuid=true#]";
				}

				content = StringUtil.replace(
					content, exportedReferenceFriendlyURL, url);
				content = StringUtil.replace(
					content, exportedReferenceWithUUID, url);
				content = StringUtil.replace(
					content, exportedReferenceWithoutUUID, urlWithoutUUID);
			}
		}

		return content;
	}

	private void _validateDLReferences(long groupId, String content)
		throws PortalException {

		DLReferencesReverseIterator dlReferencesReverseIterator =
			new DLReferencesReverseIterator(
				content, _fileEntryFriendlyURLResolver, groupId);

		while (dlReferencesReverseIterator.hasNext()) {
			DLReferencesReverseIterator.DLReference dlReference =
				dlReferencesReverseIterator.next();

			if (dlReference.getFileEntry() == null) {
				ExportImportContentValidationException
					exportImportContentValidationException =
						new ExportImportContentValidationException(
							DLReferencesExportImportContentProcessor.class.
								getName(),
							new NoSuchFileEntryException());

				exportImportContentValidationException.setDLReferenceParameters(
					dlReference.getParameters());

				exportImportContentValidationException.setDLReference(
					dlReference.getReference());

				exportImportContentValidationException.setType(
					ExportImportContentValidationException.
						FILE_ENTRY_NOT_FOUND);

				throw exportImportContentValidationException;
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLReferencesExportImportContentProcessor.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private FileEntryFriendlyURLResolver _fileEntryFriendlyURLResolver;

	@Reference
	private GroupLocalService _groupLocalService;

}