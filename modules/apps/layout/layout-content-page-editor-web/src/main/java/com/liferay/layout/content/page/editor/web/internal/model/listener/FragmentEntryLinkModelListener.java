/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.model.listener;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.fragment.listener.FragmentEntryLinkListener;
import com.liferay.fragment.listener.FragmentEntryLinkListenerRegistry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.layout.content.page.editor.web.internal.util.ContentManager;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.display.template.PortletDisplayTemplate;

import java.util.Iterator;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(service = ModelListener.class)
public class FragmentEntryLinkModelListener
	extends BaseModelListener<FragmentEntryLink> {

	@Override
	public void onAfterCreate(FragmentEntryLink fragmentEntryLink)
		throws ModelListenerException {

		_updateLayoutClassedModelUsage(fragmentEntryLink);

		_updateDDMTemplateLink(fragmentEntryLink);
	}

	@Override
	public void onAfterRemove(FragmentEntryLink fragmentEntryLink)
		throws ModelListenerException {

		_layoutClassedModelUsageLocalService.deleteLayoutClassedModelUsages(
			String.valueOf(fragmentEntryLink.getFragmentEntryLinkId()),
			_getFragmentEntryLinkClassNameId(), fragmentEntryLink.getPlid());

		try {
			_deleteDDMTemplateLinks(fragmentEntryLink);

			_commentManager.deleteDiscussion(
				FragmentEntryLink.class.getName(),
				fragmentEntryLink.getFragmentEntryLinkId());

			for (FragmentEntryLinkListener fragmentEntryLinkListener :
					_fragmentEntryLinkListenerRegistry.
						getFragmentEntryLinkListeners()) {

				fragmentEntryLinkListener.onDeleteFragmentEntryLink(
					fragmentEntryLink);
			}
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Override
	public void onAfterUpdate(
			FragmentEntryLink originalFragmentEntryLink,
			FragmentEntryLink fragmentEntryLink)
		throws ModelListenerException {

		_updateLayoutClassedModelUsage(fragmentEntryLink);

		_updateDDMTemplateLink(fragmentEntryLink);
	}

	private void _deleteDDMTemplateLinks(FragmentEntryLink fragmentEntryLink)
		throws PortalException {

		_ddmTemplateLinkLocalService.deleteTemplateLink(
			_getFragmentEntryLinkClassNameId(),
			fragmentEntryLink.getFragmentEntryLinkId());

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			fragmentEntryLink.getEditableValues());

		Iterator<String> keysIterator = jsonObject.keys();

		while (keysIterator.hasNext()) {
			String key = keysIterator.next();

			JSONObject editableProcessorJSONObject = jsonObject.getJSONObject(
				key);

			if (editableProcessorJSONObject == null) {
				continue;
			}

			Iterator<String> editableKeysIterator =
				editableProcessorJSONObject.keys();

			while (editableKeysIterator.hasNext()) {
				String editableKey = editableKeysIterator.next();

				String compositeClassName =
					ResourceActionsUtil.getCompositeModelName(
						FragmentEntryLink.class.getName(), editableKey);

				ClassName className = _classNameLocalService.fetchClassName(
					compositeClassName);

				if (className == null) {
					continue;
				}

				_ddmTemplateLinkLocalService.deleteTemplateLink(
					className.getClassNameId(),
					fragmentEntryLink.getFragmentEntryLinkId());
			}
		}
	}

	private long _getDDMStructureClassNameId() {
		if (_ddmStructureClassNameId != null) {
			return _ddmStructureClassNameId;
		}

		_ddmStructureClassNameId = _portal.getClassNameId(
			DDMStructure.class.getName());

		return _ddmStructureClassNameId;
	}

	private long _getFragmentEntryLinkClassNameId() {
		if (_fragmentEntryLinkClassNameId != null) {
			return _fragmentEntryLinkClassNameId;
		}

		_fragmentEntryLinkClassNameId = _portal.getClassNameId(
			FragmentEntryLink.class.getName());

		return _fragmentEntryLinkClassNameId;
	}

	private void _updateDDMTemplateLink(FragmentEntryLink fragmentEntryLink) {
		_ddmTemplateLinkLocalService.deleteTemplateLink(
			_getFragmentEntryLinkClassNameId(),
			fragmentEntryLink.getFragmentEntryLinkId());

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				fragmentEntryLink.getEditableValues());

			Iterator<String> keysIterator = jsonObject.keys();

			while (keysIterator.hasNext()) {
				String key = keysIterator.next();

				JSONObject editableProcessorJSONObject =
					jsonObject.getJSONObject(key);

				if (editableProcessorJSONObject == null) {
					continue;
				}

				Iterator<String> editableKeysIterator =
					editableProcessorJSONObject.keys();

				while (editableKeysIterator.hasNext()) {
					String editableKey = editableKeysIterator.next();

					JSONObject editableJSONObject =
						editableProcessorJSONObject.getJSONObject(editableKey);

					if (editableJSONObject == null) {
						continue;
					}

					String fieldId = editableJSONObject.getString("fieldId");

					String mappedField = editableJSONObject.getString(
						"mappedField", fieldId);

					if (Validator.isNull(mappedField)) {
						continue;
					}

					_updateDDMTemplateLink(
						fragmentEntryLink, editableKey, mappedField);
				}
			}
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	private void _updateDDMTemplateLink(
		FragmentEntryLink fragmentEntryLink, String editableKey,
		String mappedField) {

		if (!mappedField.startsWith(
				PortletDisplayTemplate.DISPLAY_STYLE_PREFIX)) {

			return;
		}

		String ddmTemplateKey = mappedField.substring(
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX.length());

		DDMTemplate ddmTemplate = _ddmTemplateLocalService.fetchTemplate(
			fragmentEntryLink.getGroupId(), _getDDMStructureClassNameId(),
			ddmTemplateKey);

		if (ddmTemplate == null) {
			return;
		}

		String compositeClassName = ResourceActionsUtil.getCompositeModelName(
			FragmentEntryLink.class.getName(), editableKey);

		_ddmTemplateLinkLocalService.updateTemplateLink(
			_portal.getClassNameId(compositeClassName),
			fragmentEntryLink.getFragmentEntryLinkId(),
			ddmTemplate.getTemplateId());
	}

	private void _updateLayoutClassedModelUsage(
		FragmentEntryLink fragmentEntryLink) {

		_layoutClassedModelUsageLocalService.deleteLayoutClassedModelUsages(
			String.valueOf(fragmentEntryLink.getFragmentEntryLinkId()),
			_getFragmentEntryLinkClassNameId(), fragmentEntryLink.getPlid());

		Set<LayoutDisplayPageObjectProvider<?>>
			layoutDisplayPageObjectProviders =
				_contentManager.
					getFragmentEntryLinkMappedLayoutDisplayPageObjectProviders(
						fragmentEntryLink);

		for (LayoutDisplayPageObjectProvider<?>
				layoutDisplayPageObjectProvider :
					layoutDisplayPageObjectProviders) {

			LayoutClassedModelUsage layoutClassedModelUsage =
				_layoutClassedModelUsageLocalService.
					fetchLayoutClassedModelUsage(
						layoutDisplayPageObjectProvider.getClassNameId(),
						layoutDisplayPageObjectProvider.getClassPK(),
						layoutDisplayPageObjectProvider.
							getExternalReferenceCode(),
						String.valueOf(
							fragmentEntryLink.getFragmentEntryLinkId()),
						_getFragmentEntryLinkClassNameId(),
						fragmentEntryLink.getPlid());

			if (layoutClassedModelUsage != null) {
				continue;
			}

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			if (serviceContext == null) {
				serviceContext = new ServiceContext();
			}

			_layoutClassedModelUsageLocalService.addLayoutClassedModelUsage(
				fragmentEntryLink.getGroupId(),
				layoutDisplayPageObjectProvider.getClassNameId(),
				layoutDisplayPageObjectProvider.getClassPK(),
				layoutDisplayPageObjectProvider.getExternalReferenceCode(),
				String.valueOf(fragmentEntryLink.getFragmentEntryLinkId()),
				_getFragmentEntryLinkClassNameId(), fragmentEntryLink.getPlid(),
				serviceContext);
		}
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CommentManager _commentManager;

	@Reference
	private ContentManager _contentManager;

	private Long _ddmStructureClassNameId;

	@Reference
	private DDMTemplateLinkLocalService _ddmTemplateLinkLocalService;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	private Long _fragmentEntryLinkClassNameId;

	@Reference
	private FragmentEntryLinkListenerRegistry
		_fragmentEntryLinkListenerRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	@Reference
	private Portal _portal;

}