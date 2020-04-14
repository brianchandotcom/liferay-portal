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
import com.liferay.dynamic.data.mapping.uad.util.DDMUADUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
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
import java.util.Comparator;
import java.util.HashMap;
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

		HttpServletRequest httpServletRequest =
			liferayPortletRequest.getHttpServletRequest();
		String portletNamespace = _portal.getPortletNamespace(
			DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN);

		return _portal.getSiteAdminURL(
			getThemeDisplay(httpServletRequest),
			DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN,
			HashMapBuilder.put(
				portletNamespace.concat("formInstanceRecordId"),
				new String[] {
					String.valueOf(
						ddmFormInstanceRecord.getFormInstanceRecordId())
				}
			).put(
				portletNamespace.concat("formInstanceId"),
				new String[] {
					String.valueOf(ddmFormInstanceRecord.getFormInstanceId())
				}
			).put(
				portletNamespace.concat("mvcPath"),
				new String[] {"/admin/edit_form_instance_record.jsp"}
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
			).build());
	}

	@Override
	public Map<String, Object> getFieldValues(
		DDMFormInstanceRecord ddmFormInstanceRecord, String[] fieldNames,
		Locale locale) {

		Map<String, Object> fieldValues = super.getFieldValues(
			ddmFormInstanceRecord, fieldNames, locale);

		DDMUADUtil.formatCreateDate(fieldValues);

		return fieldValues;
	}

	@Override
	public String getName(
		DDMFormInstanceRecord ddmFormInstanceRecord, Locale locale) {

		try {
			StringBundler sb = new StringBundler(6);

			DDMFormInstance ddmFormInstance =
				ddmFormInstanceRecord.getFormInstance();

			String ddmFormInstanceName = DDMUADUtil.getFormattedName(
				ddmFormInstance);

			sb.append(ddmFormInstanceName);

			sb.append(StringPool.SPACE);
			sb.append(
				LanguageUtil.get(
					ResourceBundleUtil.getBundle(
						locale, DDMFormInstanceRecordUADDisplay.class),
					"record"));
			sb.append(StringPool.SPACE);
			sb.append(StringPool.POUND);

			int ddmFormInstanceRecordIndex = _getDDMFormInstanceRecordIndex(
				ddmFormInstanceRecord);

			sb.append(ddmFormInstanceRecordIndex + 1);

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
		DDMFormInstanceRecord ddmFormInstanceRecord, long userId) {

		if (ddmFormInstanceRecord.getUserId() == userId) {
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

	private int _getDDMFormInstanceRecordIndex(
		DDMFormInstanceRecord ddmFormInstanceRecord) {

		List<DDMFormInstanceRecord> ddmFormInstanceRecords =
			_getDDMFormInstanceRecords(
				ddmFormInstanceRecord.getFormInstanceId(),
				ddmFormInstanceRecord.getUserId());

		int index = 0;

		for (DDMFormInstanceRecord currentDDMFormInstanceRecord :
				ddmFormInstanceRecords) {

			if (currentDDMFormInstanceRecord.getFormInstanceRecordId() ==
					ddmFormInstanceRecord.getFormInstanceRecordId()) {

				return index;
			}

			index++;
		}

		return -1;
	}

	private List<DDMFormInstanceRecord> _getDDMFormInstanceRecords(
		long formInstanceId, long userId) {

		DDMFormInstanceRecordUADUserCache ddmFormInstanceRecordUADUserCache =
			null;

		if (_ddmFormInstanceRecordUADUserCacheMap.get(formInstanceId) == null) {
			ddmFormInstanceRecordUADUserCache =
				new DDMFormInstanceRecordUADUserCache(formInstanceId);

			ddmFormInstanceRecordUADUserCache.putDDMFormInstanceRecords(userId);

			_ddmFormInstanceRecordUADUserCacheMap.put(
				formInstanceId, ddmFormInstanceRecordUADUserCache);
		}

		ddmFormInstanceRecordUADUserCache =
			_ddmFormInstanceRecordUADUserCacheMap.get(formInstanceId);

		return ddmFormInstanceRecordUADUserCache.getDDMFormInstanceRecords(
			userId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordUADDisplay.class);

	@Reference
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	private final Map<Long, DDMFormInstanceRecordUADUserCache>
		_ddmFormInstanceRecordUADUserCacheMap = new HashMap<>();

	@Reference
	private DDMFormInstanceUADDisplay _ddmFormInstanceUADDisplay;

	@Reference
	private Portal _portal;

	private class DDMFormInstanceRecordUADUserCache {

		public DDMFormInstanceRecordUADUserCache(long formInstanceId) {
			_ddmFormInstanceRecordUADUserMap = new HashMap<>();
			_formInstanceId = formInstanceId;
		}

		public List<DDMFormInstanceRecord> getDDMFormInstanceRecords(
			long userId) {

			if (_ddmFormInstanceRecordUADUserMap.get(userId) == null) {
				putDDMFormInstanceRecords(userId);
			}

			return _ddmFormInstanceRecordUADUserMap.get(userId);
		}

		public void putDDMFormInstanceRecords(long userId) {
			List<DDMFormInstanceRecord> ddmFormInstanceRecords =
				new ArrayList<>();

			ddmFormInstanceRecords.addAll(
				ddmFormInstanceRecordLocalService.getFormInstanceRecords(
					_formInstanceId, userId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null));

			ddmFormInstanceRecords.sort(
				Comparator.comparing(DDMFormInstanceRecord::getCreateDate));

			_ddmFormInstanceRecordUADUserMap.put(
				userId, ddmFormInstanceRecords);
		}

		private final Map<Long, List<DDMFormInstanceRecord>>
			_ddmFormInstanceRecordUADUserMap;
		private final long _formInstanceId;

	}

}