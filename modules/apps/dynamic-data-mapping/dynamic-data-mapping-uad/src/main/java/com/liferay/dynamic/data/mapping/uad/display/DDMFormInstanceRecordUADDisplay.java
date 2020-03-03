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

package com.liferay.dynamic.data.mapping.uad.display;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.uad.util.DDMFormInstanceRecordUADUserCacheHelper;
import com.liferay.dynamic.data.mapping.uad.util.DDMUADHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.user.associated.data.display.UADDisplay;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	service = {DDMFormInstanceRecordUADDisplay.class, UADDisplay.class}
)
public class DDMFormInstanceRecordUADDisplay
	extends BaseDDMFormInstanceRecordUADDisplay {

	@Override
	public String getEditURL(
			DDMFormInstanceRecord ddmFormInstanceRecord,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		String portletNamespace = _portal.getPortletNamespace(
			DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN);

		HttpServletRequest httpServletRequest =
			liferayPortletRequest.getHttpServletRequest();

		Map<String, String[]> params = HashMapBuilder.put(
			portletNamespace.concat("mvcPath"),
			new String[] {"/admin/edit_form_instance_record.jsp"}
		).put(
			portletNamespace.concat("formInstanceRecordId"),
			new String[] {
				String.valueOf(ddmFormInstanceRecord.getFormInstanceRecordId())
			}
		).put(
			portletNamespace.concat("formInstanceId"),
			new String[] {
				String.valueOf(ddmFormInstanceRecord.getFormInstanceId())
			}
		).put(
			portletNamespace.concat("readOnly"),
			new String[] {Boolean.FALSE.toString()}
		).put(
			portletNamespace.concat("redirect"),
			new String[] {_portal.getCurrentURL(httpServletRequest)}
		).put(
			portletNamespace.concat("title"),
			new String[] {
				StringBundler.concat(
					ddmFormInstanceRecord.getUserName(), " - ",
					LanguageUtil.get(
						httpServletRequest, "personal-data-erasure"))
			}
		).build();

		return _portal.getSiteAdminURL(
			getThemeDisplay(httpServletRequest),
			DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN, params);
	}

	@Override
	public Map<String, Object> getFieldValues(
		DDMFormInstanceRecord ddmFormInstanceRecord, String[] fieldNames,
		Locale locale) {

		Map<String, Object> fieldValues = super.getFieldValues(
			ddmFormInstanceRecord, fieldNames, locale);

		_ddmUADHelper.formatCreateDateIfExist(fieldValues);

		return fieldValues;
	}

	@Override
	public String getName(
		DDMFormInstanceRecord ddmFormInstanceRecord, Locale locale) {

		try {
			int formInstanceRecordIndex =
				_ddmFormInstanceRecordUADCacheHelper.getFormInstanceRecordIndex(
					ddmFormInstanceRecord);

			DDMFormInstance ddmFormInstance =
				ddmFormInstanceRecord.getFormInstance();

			String formInstanceName =
				_ddmUADHelper.getFormInstanceFormattedName(ddmFormInstance);

			StringBundler sb = new StringBundler(6);

			sb.append(formInstanceName);

			sb.append(StringPool.SPACE);

			sb.append(
				LanguageUtil.get(
					ResourceBundleUtil.getBundle(
						locale, DDMFormInstanceRecordUADDisplay.class),
					"record"));

			sb.append(StringPool.SPACE);

			sb.append(StringPool.POUND);

			sb.append(formInstanceRecordIndex + 1);

			return sb.toString();
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return StringPool.BLANK;
	}

	@Override
	public Class<?> getParentContainerClass() {
		return DDMFormInstance.class;
	}

	@Override
	public Serializable getParentContainerId(
		DDMFormInstanceRecord ddmFormInstanceRecord) {

		return ddmFormInstanceRecord.getFormInstanceId();
	}

	@Override
	public boolean isUserOwned(
		DDMFormInstanceRecord formInstanceRecord, long userId) {

		if (formInstanceRecord.getUserId() == userId) {
			return true;
		}

		return false;
	}

	@Override
	public List<DDMFormInstanceRecord> search(
		long userId, long[] groupIds, String keywords, String orderByField,
		String orderByType, int start, int end) {

		List<DDMFormInstanceRecord> ddmFormInstanceRecords = new ArrayList<>();

		ddmFormInstanceRecords.addAll(
			super.search(
				userId, groupIds, StringPool.BLANK, orderByField, orderByType,
				start, end));

		if (Validator.isNull(keywords)) {
			return ddmFormInstanceRecords;
		}

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		Stream<DDMFormInstanceRecord> ddmFormInstanceRecordsStream =
			ddmFormInstanceRecords.stream();

		return ddmFormInstanceRecordsStream.filter(
			ddmFormInstanceRecord -> {
				String formattedName = getName(ddmFormInstanceRecord, locale);

				return StringUtil.toLowerCase(
					formattedName
				).contains(
					StringUtil.toLowerCase(keywords)
				);
			}
		).collect(
			Collectors.toList()
		);
	}

	protected ThemeDisplay getThemeDisplay(
		HttpServletRequest httpServletRequest) {

		return (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordUADDisplay.class);

	@Reference
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	@Reference
	private DDMFormInstanceRecordUADUserCacheHelper
		_ddmFormInstanceRecordUADCacheHelper;

	@Reference
	private DDMFormInstanceUADDisplay _ddmFormInstanceUADDisplay;

	@Reference
	private DDMUADHelper _ddmUADHelper;

	@Reference
	private Portal _portal;

}