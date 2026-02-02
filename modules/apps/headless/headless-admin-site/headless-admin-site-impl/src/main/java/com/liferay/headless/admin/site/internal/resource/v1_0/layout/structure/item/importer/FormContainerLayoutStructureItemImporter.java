/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer;

import com.liferay.headless.admin.site.dto.v1_0.DisplayPageFormContainerSubmissionResult;
import com.liferay.headless.admin.site.dto.v1_0.EmbeddedMessageFormContainerSubmissionResult;
import com.liferay.headless.admin.site.dto.v1_0.FormContainerClassSubtypeReference;
import com.liferay.headless.admin.site.dto.v1_0.FormContainerConfig;
import com.liferay.headless.admin.site.dto.v1_0.FormContainerContextReference;
import com.liferay.headless.admin.site.dto.v1_0.FormContainerPageElementDefinition;
import com.liferay.headless.admin.site.dto.v1_0.FragmentInlineValue;
import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.Layout;
import com.liferay.headless.admin.site.dto.v1_0.LocalizationConfig;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.dto.v1_0.SitePageFormContainerSubmissionResult;
import com.liferay.headless.admin.site.dto.v1_0.StayInPageFormContainerSubmissionResult;
import com.liferay.headless.admin.site.dto.v1_0.SuccessFormContainerSubmissionResult;
import com.liferay.headless.admin.site.dto.v1_0.SuccessNotificationMessage;
import com.liferay.headless.admin.site.dto.v1_0.URLFormContainerSubmissionResult;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.FragmentViewportUtil;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.ItemScopeUtil;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.LayoutUtil;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.LocalizedValueUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.context.LayoutStructureItemImporterContext;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.LayoutStructureUtil;
import com.liferay.headless.admin.site.internal.util.LogUtil;
import com.liferay.layout.converter.AlignConverter;
import com.liferay.layout.converter.ContentDisplayConverter;
import com.liferay.layout.converter.FlexWrapConverter;
import com.liferay.layout.converter.JustifyConverter;
import com.liferay.layout.converter.WidthTypeConverter;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.object.model.ObjectEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class FormContainerLayoutStructureItemImporter
	implements LayoutStructureItemImporter {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			LayoutStructure layoutStructure,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext,
			PageElement pageElement)
		throws Exception {

		FormStyledLayoutStructureItem formStyledLayoutStructureItem =
			(FormStyledLayoutStructureItem)
				layoutStructure.addFormStyledLayoutStructureItem(
					pageElement.getExternalReferenceCode(),
					LayoutStructureUtil.getParentExternalReferenceCode(
						pageElement, layoutStructure),
					pageElement.getPosition());

		FormContainerPageElementDefinition formContainerPageElementDefinition =
			(FormContainerPageElementDefinition)
				pageElement.getPageElementDefinition();

		if (formContainerPageElementDefinition == null) {
			return formStyledLayoutStructureItem;
		}

		formStyledLayoutStructureItem.setCssClasses(
			_getCssClasses(formContainerPageElementDefinition.getCssClasses()));
		formStyledLayoutStructureItem.setIndexed(
			GetterUtil.getBoolean(
				formContainerPageElementDefinition.getIndexed(), true));
		formStyledLayoutStructureItem.setName(
			formContainerPageElementDefinition.getName());

		JSONObject fragmentViewportsJSONObject =
			FragmentViewportUtil.toFragmentViewportsJSONObject(
				formContainerPageElementDefinition.getFragmentViewports());

		if (fragmentViewportsJSONObject != null) {
			formStyledLayoutStructureItem.updateItemConfig(
				fragmentViewportsJSONObject);
		}

		FormContainerConfig formContainerConfig =
			formContainerPageElementDefinition.getFormContainerConfig();

		if (formContainerConfig == null) {
			return formStyledLayoutStructureItem;
		}

		if (formContainerConfig.getFormContainerReference() instanceof
				FormContainerContextReference) {

			formStyledLayoutStructureItem.setFormConfig(
				FormStyledLayoutStructureItem.
					FORM_CONFIG_DISPLAY_PAGE_ITEM_TYPE);
		}
		else {
			FormContainerClassSubtypeReference
				formContainerClassSubtypeReference =
					(FormContainerClassSubtypeReference)
						formContainerConfig.getFormContainerReference();

			formStyledLayoutStructureItem.setClassNameId(
				PortalUtil.getClassNameId(
					formContainerClassSubtypeReference.getClassName()));

			formStyledLayoutStructureItem.setFormConfig(
				FormStyledLayoutStructureItem.FORM_CONFIG_OTHER_ITEM_TYPE);
		}

		formStyledLayoutStructureItem.setFormType(
			_toFormContainerType(formContainerConfig.getFormContainerType()));

		Layout layout = formContainerPageElementDefinition.getLayout();

		if (layout != null) {
			String align = layout.getAlignAsString();

			if (align != null) {
				formStyledLayoutStructureItem.setAlign(
					AlignConverter.convertToInternalValue(align));
			}

			String contentDisplay = layout.getContentDisplayAsString();

			if (contentDisplay != null) {
				formStyledLayoutStructureItem.setContentDisplay(
					ContentDisplayConverter.convertToInternalValue(
						contentDisplay));
			}

			String flexWrap = layout.getFlexWrapAsString();

			if (flexWrap != null) {
				formStyledLayoutStructureItem.setFlexWrap(
					FlexWrapConverter.convertToInternalValue(flexWrap));
			}

			String justify = layout.getJustifyAsString();

			if (justify != null) {
				formStyledLayoutStructureItem.setJustify(
					JustifyConverter.convertToInternalValue(justify));
			}

			String widthType = layout.getWidthTypeAsString();

			if (widthType != null) {
				formStyledLayoutStructureItem.setWidthType(
					WidthTypeConverter.convertToInternalValue(widthType));
			}
		}
		else {
			formStyledLayoutStructureItem.setAlign(null);
			formStyledLayoutStructureItem.setContentDisplay(null);
			formStyledLayoutStructureItem.setFlexWrap(null);
			formStyledLayoutStructureItem.setJustify(null);
			formStyledLayoutStructureItem.setWidthType(null);
		}

		LocalizationConfig localizationConfig =
			formContainerConfig.getLocalizationConfig();

		if (localizationConfig != null) {
			formStyledLayoutStructureItem.setLocalizationConfigJSONObject(
				JSONUtil.put(
					"unlocalizedFieldsMessage",
					_toFragmentInlineValueJSONObject(
						localizationConfig.
							getUnlocalizedFieldsMessageFragmentInlineValue())
				).put(
					"unlocalizedFieldsState",
					() -> {
						LocalizationConfig.UnlocalizedFieldsState
							unlocalizedFieldsState =
								localizationConfig.getUnlocalizedFieldsState();

						if ((unlocalizedFieldsState == null) ||
							Objects.equals(
								unlocalizedFieldsState,
								LocalizationConfig.UnlocalizedFieldsState.
									DISABLED)) {

							return "disabled";
						}

						return "read-only";
					}
				));
		}

		formStyledLayoutStructureItem.setNumberOfSteps(
			formContainerConfig.getNumberOfSteps());

		_setSuccessMessageJSONObject(
			formStyledLayoutStructureItem, layoutStructureItemImporterContext,
			formContainerConfig.getSuccessFormContainerSubmissionResult());

		return formStyledLayoutStructureItem;
	}

	private LinkedHashSet<String> _getCssClasses(String[] cssClasses) {
		if (cssClasses == null) {
			return null;
		}

		return new LinkedHashSet<>(Arrays.asList(cssClasses));
	}

	private void _setSuccessMessageJSONObject(
			FormStyledLayoutStructureItem formStyledLayoutStructureItem,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext,
			SuccessFormContainerSubmissionResult
				successFormContainerSubmissionResult)
		throws Exception {

		if (successFormContainerSubmissionResult == null) {
			formStyledLayoutStructureItem.setSuccessMessageJSONObject(
				JSONUtil.put("type", "embedded"));
		}
		else {
			JSONObject jsonObject = null;

			if (successFormContainerSubmissionResult instanceof
					DisplayPageFormContainerSubmissionResult) {

				jsonObject =
					_toDisplayPageFormContainerSubmissionResultJSONObject(
						(DisplayPageFormContainerSubmissionResult)
							successFormContainerSubmissionResult,
						layoutStructureItemImporterContext);
			}
			else if (successFormContainerSubmissionResult instanceof
						EmbeddedMessageFormContainerSubmissionResult) {

				jsonObject =
					_toEmbeddedMessageFormContainerSubmissionResultJSONObject(
						(EmbeddedMessageFormContainerSubmissionResult)
							successFormContainerSubmissionResult);
			}
			else if (successFormContainerSubmissionResult instanceof
						StayInPageFormContainerSubmissionResult) {

				jsonObject =
					_toStayInPageFormContainerSubmissionResultJSONObject(
						(StayInPageFormContainerSubmissionResult)
							successFormContainerSubmissionResult);
			}
			else if (successFormContainerSubmissionResult instanceof
						SitePageFormContainerSubmissionResult) {

				jsonObject = _toSitePageFormContainerSubmissionResultJSONObject(
					layoutStructureItemImporterContext,
					(SitePageFormContainerSubmissionResult)
						successFormContainerSubmissionResult);
			}
			else {
				jsonObject = _toURLFormContainerSubmissionResultJSONObject(
					(URLFormContainerSubmissionResult)
						successFormContainerSubmissionResult);
			}

			formStyledLayoutStructureItem.setSuccessMessageJSONObject(
				jsonObject);
		}
	}

	private JSONObject _toDisplayPageFormContainerSubmissionResultJSONObject(
			DisplayPageFormContainerSubmissionResult
				displayPageFormContainerSubmissionResult,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext)
		throws Exception {

		JSONObject jsonObject = _toSuccessNotificationMessageJSONObject(
			displayPageFormContainerSubmissionResult.
				getSuccessNotificationMessage());

		if (GetterUtil.getBoolean(
				displayPageFormContainerSubmissionResult.
					getDefaultDisplayPage())) {

			return jsonObject.put(
				"displayPage",
				ObjectEntry.class.getSimpleName() + "_displayPageURL"
			).put(
				"type", "displayPage"
			);
		}

		ItemExternalReference itemExternalReference =
			displayPageFormContainerSubmissionResult.getItemExternalReference();

		if (itemExternalReference == null) {
			return null;
		}

		return JSONUtil.merge(
			_toMappedLayoutPageTemplateEntryJSONObject(
				layoutStructureItemImporterContext.getCompanyId(),
				itemExternalReference,
				layoutStructureItemImporterContext.getGroupId()),
			jsonObject.put("type", "displayPage"));
	}

	private JSONObject
		_toEmbeddedMessageFormContainerSubmissionResultJSONObject(
			EmbeddedMessageFormContainerSubmissionResult
				embeddedMessageFormContainerSubmissionResult) {

		return _toSuccessNotificationMessageJSONObject(
			embeddedMessageFormContainerSubmissionResult.
				getSuccessNotificationMessage()
		).put(
			"message",
			() -> _toFragmentInlineValueJSONObject(
				embeddedMessageFormContainerSubmissionResult.getMessage())
		).put(
			"type", "embedded"
		);
	}

	private String _toFormContainerType(
		FormContainerConfig.FormContainerType formType) {

		if (Objects.equals(
				formType, FormContainerConfig.FormContainerType.SIMPLE)) {

			return "simple";
		}

		return "multistep";
	}

	private JSONObject _toFragmentInlineValueJSONObject(
		FragmentInlineValue fragmentInlineValue) {

		if (fragmentInlineValue == null) {
			return null;
		}

		return LocalizedValueUtil.toJSONObject(
			fragmentInlineValue.getValue_i18n());
	}

	private JSONObject _toMappedLayoutPageTemplateEntryJSONObject(
			long companyId, ItemExternalReference itemExternalReference,
			long scopeGroupId)
		throws Exception {

		String scopeExternalReferenceCode =
			ItemScopeUtil.getItemScopeExternalReferenceCode(
				itemExternalReference.getScope(), scopeGroupId);

		JSONObject jsonObject = JSONUtil.put(
			"layoutPageTemplateEntryExternalReferenceCode",
			itemExternalReference.getExternalReferenceCode()
		).put(
			"layoutPageTemplateEntryScopeExternalReferenceCode",
			scopeExternalReferenceCode
		);

		Long groupId = ItemScopeUtil.getItemGroupId(
			companyId, itemExternalReference.getScope(), scopeGroupId);

		if (groupId == null) {
			LogUtil.logOptionalReference(
				itemExternalReference.getClassName(),
				itemExternalReference.getExternalReferenceCode(),
				itemExternalReference.getScope(), scopeGroupId);

			return jsonObject;
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByExternalReferenceCode(
					itemExternalReference.getExternalReferenceCode(), groupId);

		if (layoutPageTemplateEntry == null) {
			LogUtil.logOptionalReference(
				itemExternalReference.getClassName(),
				itemExternalReference.getExternalReferenceCode(),
				itemExternalReference.getScope(), scopeGroupId);

			return jsonObject;
		}

		return JSONUtil.put(
			"displayPage",
			LayoutPageTemplateEntry.class.getSimpleName() +
				StringPool.UNDERLINE +
					layoutPageTemplateEntry.getLayoutPageTemplateEntryId()
		).put(
			"layoutPageTemplateEntryExternalReferenceCode",
			itemExternalReference.getExternalReferenceCode()
		).put(
			"layoutPageTemplateEntryScopeExternalReferenceCode",
			scopeExternalReferenceCode
		);
	}

	private JSONObject _toSitePageFormContainerSubmissionResultJSONObject(
		LayoutStructureItemImporterContext layoutStructureItemImporterContext,
		SitePageFormContainerSubmissionResult
			sitePageFormContainerSubmissionResult) {

		ItemExternalReference itemExternalReference =
			sitePageFormContainerSubmissionResult.getItemExternalReference();

		if (itemExternalReference == null) {
			return null;
		}

		return _toSuccessNotificationMessageJSONObject(
			sitePageFormContainerSubmissionResult.
				getSuccessNotificationMessage()
		).put(
			"layout",
			() -> LayoutUtil.getMappedLayoutJSONObject(
				layoutStructureItemImporterContext.getCompanyId(),
				itemExternalReference.getExternalReferenceCode(),
				itemExternalReference.getScope(),
				layoutStructureItemImporterContext.getGroupId())
		).put(
			"type", "page"
		);
	}

	private JSONObject _toStayInPageFormContainerSubmissionResultJSONObject(
		StayInPageFormContainerSubmissionResult
			stayInPageFormContainerSubmissionResult) {

		return _toSuccessNotificationMessageJSONObject(
			stayInPageFormContainerSubmissionResult.
				getSuccessNotificationMessage()
		).put(
			"type", "none"
		);
	}

	private JSONObject _toSuccessNotificationMessageJSONObject(
		SuccessNotificationMessage successNotificationMessage) {

		if (successNotificationMessage == null) {
			return JSONFactoryUtil.createJSONObject();
		}

		return JSONUtil.put(
			"notificationText",
			() -> _toFragmentInlineValueJSONObject(
				successNotificationMessage.getMessage())
		).put(
			"showNotification",
			() -> GetterUtil.getBoolean(
				successNotificationMessage.getShowNotification())
		);
	}

	private JSONObject _toURLFormContainerSubmissionResultJSONObject(
		URLFormContainerSubmissionResult urlFormContainerSubmissionResult) {

		return JSONUtil.put(
			"type", "url"
		).put(
			"url",
			() -> _toFragmentInlineValueJSONObject(
				urlFormContainerSubmissionResult.getUrl())
		);
	}

}