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

package com.liferay.info.internal.request.struts;

import com.liferay.captcha.util.CaptchaUtil;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.info.exception.InfoFormException;
import com.liferay.info.exception.InfoFormInvalidGroupException;
import com.liferay.info.exception.InfoFormInvalidLayoutModeException;
import com.liferay.info.exception.InfoFormPrincipalException;
import com.liferay.info.exception.InfoFormValidationException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.RelationshipInfoFieldType;
import com.liferay.info.internal.request.helper.InfoRequestFieldValuesProviderHelper;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.creator.InfoItemCreator;
import com.liferay.layout.page.template.util.LayoutStructureUtil;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.captcha.CaptchaException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true, property = "path=/portal/add_info_item",
	service = StrutsAction.class
)
public class AddInfoItemStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		String formItemId = ParamUtil.getString(
			httpServletRequest, "formItemId");

		List<InfoFieldValue<Object>> infoFieldValues = null;

		try {
			infoFieldValues =
				_infoRequestFieldValuesProviderHelper.getInfoFieldValues(
					httpServletRequest);
		}
		catch (InfoFormException infoFormException) {
			if (_log.isDebugEnabled()) {
				_log.debug(infoFormException);
			}

			SessionErrors.add(
				httpServletRequest, formItemId, infoFormException);

			httpServletResponse.sendRedirect(
				httpServletRequest.getHeader(HttpHeaders.REFERER));

			return null;
		}

		String redirect = null;
		boolean success = false;

		try {
			if (!Objects.equals(
					Constants.VIEW,
					ParamUtil.getString(httpServletRequest, "p_l_mode"))) {

				throw new InfoFormInvalidLayoutModeException();
			}

			Layout layout = _layoutLocalService.fetchLayout(
				ParamUtil.getLong(httpServletRequest, "plid"));

			if ((layout == null) || layout.isDraftLayout()) {
				throw new InfoFormInvalidLayoutModeException();
			}

			long groupId = ParamUtil.getLong(httpServletRequest, "groupId");

			Group group = _groupLocalService.fetchGroup(groupId);

			if ((group == null) || !group.isSite()) {
				throw new InfoFormInvalidGroupException();
			}

			if (_isCaptchaLayoutStructureItem(formItemId, httpServletRequest)) {
				CaptchaUtil.check(httpServletRequest);
			}

			String className = _portal.getClassName(
				ParamUtil.getLong(httpServletRequest, "classNameId"));

			InfoItemCreator<Object> infoItemCreator =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemCreator.class, className);

			if (infoItemCreator == null) {
				throw new InfoFormException();
			}

			infoItemCreator.createFromInfoItemFieldValues(
				groupId,
				InfoItemFieldValues.builder(
				).infoFieldValues(
					infoFieldValues
				).infoItemReference(
					new InfoItemReference(className, 0)
				).build());

			redirect = ParamUtil.getString(httpServletRequest, "redirect");

			if (Validator.isNull(redirect)) {
				redirect = ParamUtil.getString(httpServletRequest, "backURL");

				SessionMessages.add(httpServletRequest, formItemId);
			}

			success = true;
		}
		catch (CaptchaException captchaException) {
			if (_log.isDebugEnabled()) {
				_log.debug(captchaException);
			}

			SessionErrors.add(
				httpServletRequest, formItemId,
				new InfoFormValidationException.InvalidCaptcha());
		}
		catch (InfoFormValidationException infoFormValidationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(infoFormValidationException);
			}

			SessionErrors.add(
				httpServletRequest, formItemId, infoFormValidationException);

			if (Validator.isNotNull(
					infoFormValidationException.getInfoFieldUniqueId())) {

				SessionErrors.add(
					httpServletRequest,
					infoFormValidationException.getInfoFieldUniqueId(),
					infoFormValidationException);
			}
		}
		catch (InfoFormException infoFormException) {
			if (_log.isDebugEnabled()) {
				_log.debug(infoFormException);
			}

			SessionErrors.add(
				httpServletRequest, formItemId, infoFormException);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			InfoFormException infoFormException = new InfoFormException();

			if (exception instanceof PrincipalException) {
				infoFormException = new InfoFormPrincipalException();
			}

			SessionErrors.add(
				httpServletRequest, formItemId, infoFormException);
		}

		if (!success && (infoFieldValues != null)) {
			Map<String, String> infoFormParameterMap = new HashMap<>();

			for (InfoFieldValue<Object> infoFieldValue : infoFieldValues) {
				InfoField<?> infoField = infoFieldValue.getInfoField();

				infoFormParameterMap.put(
					infoField.getName(), _getValue(infoFieldValue));

				if (infoField.getInfoFieldType() ==
						RelationshipInfoFieldType.INSTANCE) {

					UploadServletRequest uploadServletRequest =
						_portal.getUploadServletRequest(httpServletRequest);

					String labelParameterName = infoField.getName() + "-label";

					String label = ParamUtil.getString(
						uploadServletRequest, labelParameterName);

					infoFormParameterMap.put(labelParameterName, label);
				}
			}

			SessionMessages.add(
				httpServletRequest, "infoFormParameterMap" + formItemId,
				infoFormParameterMap);
		}

		if (Validator.isNull(redirect)) {
			redirect = httpServletRequest.getHeader(HttpHeaders.REFERER);
		}

		httpServletResponse.sendRedirect(redirect);

		return null;
	}

	@Activate
	@Modified
	protected void activate() {
		_infoRequestFieldValuesProviderHelper =
			new InfoRequestFieldValuesProviderHelper(_infoItemServiceTracker);
	}

	private String _getValue(InfoFieldValue<?> infoFieldValue) {
		if (infoFieldValue == null) {
			return null;
		}

		InfoField<?> infoField = infoFieldValue.getInfoField();

		if (infoField.getInfoFieldType() == DateInfoFieldType.INSTANCE) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");

			return simpleDateFormat.format(infoFieldValue.getValue());
		}

		return String.valueOf(infoFieldValue.getValue());
	}

	private boolean _hasCaptcha(
		List<String> childrenItemIds, LayoutStructure layoutStructure) {

		for (String childrenItemId : childrenItemIds) {
			LayoutStructureItem layoutStructureItem =
				layoutStructure.getLayoutStructureItem(childrenItemId);

			if (_hasCaptcha(
					layoutStructureItem.getChildrenItemIds(),
					layoutStructure)) {

				return true;
			}

			if (!(layoutStructureItem instanceof
					FragmentStyledLayoutStructureItem)) {

				continue;
			}

			FragmentStyledLayoutStructureItem
				fragmentStyledLayoutStructureItem =
					(FragmentStyledLayoutStructureItem)layoutStructureItem;

			if (fragmentStyledLayoutStructureItem.getFragmentEntryLinkId() <=
					0) {

				continue;
			}

			FragmentEntryLink fragmentEntryLink =
				_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
					fragmentStyledLayoutStructureItem.getFragmentEntryLinkId());

			if ((fragmentEntryLink != null) &&
				fragmentEntryLink.isTypeInput() &&
				_isCaptchaFragmentEntry(
					fragmentEntryLink.getFragmentEntryId(),
					fragmentEntryLink.getRendererKey())) {

				return true;
			}
		}

		return false;
	}

	private boolean _isCaptchaFragmentEntry(
		long fragmentEntryId, String rendererKey) {

		FragmentEntry fragmentEntry = null;

		if (Validator.isNotNull(rendererKey)) {
			fragmentEntry =
				_fragmentCollectionContributorTracker.getFragmentEntry(
					rendererKey);
		}

		if ((fragmentEntry == null) && (fragmentEntryId > 0)) {
			fragmentEntry = _fragmentEntryLocalService.fetchFragmentEntry(
				fragmentEntryId);
		}

		if ((fragmentEntry == null) ||
			Validator.isNull(fragmentEntry.getTypeOptions())) {

			return false;
		}

		try {
			JSONObject typeOptionsJSONObject = JSONFactoryUtil.createJSONObject(
				fragmentEntry.getTypeOptions());

			JSONArray fieldTypesJSONArray = typeOptionsJSONObject.getJSONArray(
				"fieldTypes");

			if ((fieldTypesJSONArray != null) &&
				JSONUtil.hasValue(fieldTypesJSONArray, "captcha")) {

				return true;
			}
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}
		}

		return false;
	}

	private boolean _isCaptchaLayoutStructureItem(
			String formItemId, HttpServletRequest httpServletRequest)
		throws InfoFormException {

		LayoutStructure layoutStructure =
			LayoutStructureUtil.getLayoutStructure(
				ParamUtil.getLong(httpServletRequest, "plid"),
				ParamUtil.getLong(httpServletRequest, "segmentsExperienceId"));

		if (layoutStructure == null) {
			throw new InfoFormException();
		}

		LayoutStructureItem formLayoutStructureItem =
			layoutStructure.getLayoutStructureItem(formItemId);

		if (formLayoutStructureItem == null) {
			throw new InfoFormException();
		}

		return _hasCaptcha(
			formLayoutStructureItem.getChildrenItemIds(), layoutStructure);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddInfoItemStrutsAction.class);

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	private volatile InfoRequestFieldValuesProviderHelper
		_infoRequestFieldValuesProviderHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}