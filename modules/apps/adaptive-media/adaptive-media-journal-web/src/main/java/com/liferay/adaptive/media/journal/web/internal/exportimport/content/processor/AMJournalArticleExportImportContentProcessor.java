/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.journal.web.internal.exportimport.content.processor;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"model.class.name=com.liferay.journal.model.JournalArticle",
		"service.ranking:Integer=100"
	},
	service = ExportImportContentProcessor.class
)
public class AMJournalArticleExportImportContentProcessor
	implements ExportImportContentProcessor<String> {

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent,
			boolean escapeContent)
		throws Exception {

		String replacedContent =
			_journalArticleExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, stagedModel, content,
					exportReferencedContent, escapeContent);

		if (!_hasTextHTMLDDMFormField(portletDataContext, stagedModel)) {
			return replacedContent;
		}

		return _amJournalArticleContentHTMLReplacer.replace(
			replacedContent,
			html ->
				_htmlExportImportContentProcessor.
					replaceExportContentReferences(
						portletDataContext, stagedModel, html,
						exportReferencedContent, escapeContent));
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		String replacedContent =
			_journalArticleExportImportContentProcessor.
				replaceImportContentReferences(
					portletDataContext, stagedModel, content);

		if (!_hasTextHTMLDDMFormField(portletDataContext, stagedModel)) {
			return replacedContent;
		}

		return _amJournalArticleContentHTMLReplacer.replace(
			replacedContent,
			html ->
				_htmlExportImportContentProcessor.
					replaceImportContentReferences(
						portletDataContext, stagedModel, html));
	}

	@Override
	public void validateContentReferences(long groupId, String content)
		throws PortalException {

		_journalArticleExportImportContentProcessor.validateContentReferences(
			groupId, content);

		try {
			_amJournalArticleContentHTMLReplacer.replace(
				content,
				html -> {
					_htmlExportImportContentProcessor.validateContentReferences(
						groupId, html);

					return html;
				});
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
	}

	private boolean _hasTextHTMLDDMFormField(
		PortletDataContext portletDataContext, StagedModel stagedModel) {

		JournalArticle journalArticle = (JournalArticle)stagedModel;

		Map<Long, Long> ddmStructureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		long ddmStructureId = MapUtil.getLong(
			ddmStructureIds, journalArticle.getDDMStructureId(),
			journalArticle.getDDMStructureId());

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			ddmStructureId);

		if (ddmStructure == null) {
			return true;
		}

		List<DDMFormField> ddmFormFields = ddmStructure.getDDMFormFields(false);

		for (DDMFormField ddmFormField : ddmFormFields) {
			if (Objects.equals(
					ddmFormField.getType(),
					DDMFormFieldTypeConstants.RICH_TEXT) ||
				Objects.equals(
					ddmFormField.getType(), DDMFormFieldTypeConstants.TEXT)) {

				return true;
			}
		}

		return false;
	}

	@Reference
	private AMJournalArticleContentHTMLReplacer
		_amJournalArticleContentHTMLReplacer;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference(target = "(adaptive.media.format=html)")
	private ExportImportContentProcessor<String>
		_htmlExportImportContentProcessor;

	@Reference(
		target = "(&(model.class.name=com.liferay.journal.model.JournalArticle)(!(component.name=com.liferay.adaptive.media.journal.web.internal.exportimport.content.processor.AMJournalArticleExportImportContentProcessor)))"
	)
	private ExportImportContentProcessor<String>
		_journalArticleExportImportContentProcessor;

	@Reference
	private Portal _portal;

}