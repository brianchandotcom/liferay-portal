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

package com.liferay.fragment.internal.exportimport.data.handler;

import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class FragmentEntryLinkStagedModelDataHandler
	extends BaseStagedModelDataHandler<FragmentEntryLink> {

	public static final String[] CLASS_NAMES = {
		FragmentEntryLink.class.getName()
	};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			FragmentEntryLink fragmentEntryLink)
		throws Exception {

		FragmentEntryLink originalFragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink.getOriginalFragmentEntryLinkId());

		if (originalFragmentEntryLink != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, fragmentEntryLink,
				originalFragmentEntryLink,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		Element fragmentEntryLinkElement =
			portletDataContext.getExportDataElement(fragmentEntryLink);

		if (!MapUtil.getBoolean(
				portletDataContext.getParameterMap(),
				PortletDataHandlerKeys.PORTLET_DATA) &&
			MergeLayoutPrototypesThreadLocal.isInProgress()) {

			portletDataContext.addClassedModel(
				fragmentEntryLinkElement,
				ExportImportPathUtil.getModelPath(fragmentEntryLink),
				fragmentEntryLink);

			return;
		}

		String html = fragmentEntryLink.getHtml();

		if (Validator.isNotNull(html)) {
			html =
				_dlReferencesExportImportContentProcessor.
					replaceExportContentReferences(
						portletDataContext, fragmentEntryLink, html, true,
						false);
		}

		fragmentEntryLink.setHtml(html);

		String editableValues = fragmentEntryLink.getEditableValues();

		if (Validator.isNotNull(editableValues)) {
			editableValues =
				_fragmentEntryLinkExportImportContentProcessor.
					replaceExportContentReferences(
						portletDataContext, fragmentEntryLink, editableValues,
						true, false);
		}

		fragmentEntryLink.setEditableValues(editableValues);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(
				fragmentEntryLink.getFragmentEntryId());

		if ((fragmentEntry != null) &&
			(fragmentEntry.getGroupId() != fragmentEntryLink.getGroupId())) {

			Group group = _groupLocalService.fetchGroup(
				fragmentEntry.getGroupId());

			if (group != null) {
				fragmentEntryLinkElement.addAttribute(
					"fragment-entry-group-key", group.getGroupKey());
			}

			fragmentEntryLinkElement.addAttribute(
				"fragment-entry-key", fragmentEntry.getFragmentEntryKey());
		}

		portletDataContext.addClassedModel(
			fragmentEntryLinkElement,
			ExportImportPathUtil.getModelPath(fragmentEntryLink),
			fragmentEntryLink);
	}

	@Override
	protected void doImportMissingReference(
		PortletDataContext portletDataContext, String uuid, long groupId,
		long fragmentEntryLinkId) {

		FragmentEntryLink existingFragmentEntryLink = fetchMissingReference(
			uuid, groupId);

		if (existingFragmentEntryLink == null) {
			return;
		}

		Map<Long, Long> fragmentEntryLinkIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				FragmentEntryLink.class);

		fragmentEntryLinkIds.put(
			fragmentEntryLinkId,
			existingFragmentEntryLink.getFragmentEntryLinkId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			FragmentEntryLink fragmentEntryLink)
		throws Exception {

		Map<Long, Long> originalFragmentEntryLinkIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				FragmentEntryLink.class);

		long originalFragmentEntryLinkId = MapUtil.getLong(
			originalFragmentEntryLinkIds,
			fragmentEntryLink.getOriginalFragmentEntryLinkId(),
			fragmentEntryLink.getOriginalFragmentEntryLinkId());

		Map<Long, Long> fragmentEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				FragmentEntry.class);

		long fragmentEntryId = MapUtil.getLong(
			fragmentEntryIds, fragmentEntryLink.getFragmentEntryId());

		if ((fragmentEntryId == 0) &&
			(fragmentEntryLink.getFragmentEntryId() > 0)) {

			FragmentEntry fragmentEntry =
				_fragmentEntryLocalService.fetchFragmentEntry(
					fragmentEntryLink.getFragmentEntryId());

			if (fragmentEntry != null) {
				FragmentEntry targetFragmentEntry =
					_fragmentEntryLocalService.
						fetchFragmentEntryByUuidAndGroupId(
							fragmentEntry.getUuid(),
							portletDataContext.getGroupId());

				if (targetFragmentEntry != null) {
					fragmentEntryId = targetFragmentEntry.getFragmentEntryId();
				}
				else {
					fragmentEntryId = fragmentEntryLink.getFragmentEntryId();
				}
			}
			else {
				Element fragmentEntryLinkElement =
					portletDataContext.getImportDataStagedModelElement(
						fragmentEntryLink);

				String fragmentEntryGroupKey = GetterUtil.getString(
					fragmentEntryLinkElement.attributeValue(
						"fragment-entry-group-key"));

				Group group = _groupLocalService.fetchGroup(
					fragmentEntryLink.getCompanyId(), fragmentEntryGroupKey);

				if (group != null) {
					String fragmentEntryKey = GetterUtil.getString(
						fragmentEntryLinkElement.attributeValue(
							"fragment-entry-key"));

					fragmentEntry =
						_fragmentEntryLocalService.fetchFragmentEntry(
							group.getGroupId(), fragmentEntryKey);
				}

				if (fragmentEntry != null) {
					fragmentEntryId = fragmentEntry.getFragmentEntryId();
				}
			}
		}

		Map<Long, Long> referenceClassPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				fragmentEntryLink.getClassName());

		long referenceClassPK = MapUtil.getLong(
			referenceClassPKs, fragmentEntryLink.getClassPK(),
			fragmentEntryLink.getClassPK());

		FragmentEntryLink importedFragmentEntryLink =
			(FragmentEntryLink)fragmentEntryLink.clone();

		importedFragmentEntryLink.setGroupId(
			portletDataContext.getScopeGroupId());
		importedFragmentEntryLink.setOriginalFragmentEntryLinkId(
			originalFragmentEntryLinkId);
		importedFragmentEntryLink.setFragmentEntryId(fragmentEntryId);
		importedFragmentEntryLink.setClassPK(referenceClassPK);
		importedFragmentEntryLink.setPlid(referenceClassPK);

		String html =
			_dlReferencesExportImportContentProcessor.
				replaceImportContentReferences(
					portletDataContext, fragmentEntryLink,
					fragmentEntryLink.getHtml());

		importedFragmentEntryLink.setHtml(html);

		String editableValues =
			_fragmentEntryLinkExportImportContentProcessor.
				replaceImportContentReferences(
					portletDataContext, fragmentEntryLink,
					fragmentEntryLink.getEditableValues());

		importedFragmentEntryLink.setEditableValues(editableValues);

		FragmentEntryLink existingFragmentEntryLink =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				fragmentEntryLink.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingFragmentEntryLink == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedFragmentEntryLink = _stagedModelRepository.addStagedModel(
				portletDataContext, importedFragmentEntryLink);
		}
		else {
			importedFragmentEntryLink.setMvccVersion(
				existingFragmentEntryLink.getMvccVersion());
			importedFragmentEntryLink.setFragmentEntryLinkId(
				existingFragmentEntryLink.getFragmentEntryLinkId());

			importedFragmentEntryLink =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedFragmentEntryLink);
		}

		portletDataContext.importClassedModel(
			fragmentEntryLink, importedFragmentEntryLink);
	}

	@Override
	protected StagedModelRepository<FragmentEntryLink>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(target = "(content.processor.type=DLReferences)")
	private ExportImportContentProcessor<String>
		_dlReferencesExportImportContentProcessor;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.fragment.model.FragmentEntryLink)"
	)
	private volatile ExportImportContentProcessor<String>
		_fragmentEntryLinkExportImportContentProcessor;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(model.class.name=com.liferay.fragment.model.FragmentEntryLink)",
		unbind = "-"
	)
	private StagedModelRepository<FragmentEntryLink> _stagedModelRepository;

}