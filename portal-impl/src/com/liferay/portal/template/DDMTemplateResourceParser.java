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

package com.liferay.portal.template;

import com.liferay.dynamic.data.mapping.kernel.DDMStructureManagerUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMTemplate;
import com.liferay.dynamic.data.mapping.kernel.DDMTemplateManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.DDMTemplateResource;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

/**
 * @author Tina Tian
 * @author Juan Fernández
 */
public class DDMTemplateResourceParser implements TemplateResourceParser {

	@Override
	@SuppressWarnings("deprecation")
	public TemplateResource getTemplateResource(String templateId)
		throws TemplateException {

		int pos = templateId.indexOf(
			TemplateConstants.TEMPLATE_SEPARATOR + StringPool.SLASH);

		if (pos == -1) {

			// Backwards compatibility

			pos = templateId.indexOf(
				TemplateConstants.JOURNAL_SEPARATOR + StringPool.SLASH);

			if (pos == -1) {
				return null;
			}
		}

		try {
			int w = templateId.indexOf(CharPool.SLASH, pos);
			int x = templateId.indexOf(CharPool.SLASH, w + 1);
			int y = templateId.indexOf(CharPool.SLASH, x + 1);
			int z = templateId.indexOf(CharPool.SLASH, y + 1);

			long companyId = GetterUtil.getLong(templateId.substring(w + 1, x));
			long groupId = GetterUtil.getLong(templateId.substring(x + 1, y));
			long classNameId = GetterUtil.getLong(
				templateId.substring(y + 1, z));
			String ddmTemplateKey = templateId.substring(z + 1);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Loading {companyId=" + companyId + ", groupId=" +
						groupId + ", classNameId=" + classNameId +
							", ddmTemplateKey=" + ddmTemplateKey + "}");
			}

			DDMTemplate ddmTemplate = DDMTemplateManagerUtil.fetchTemplate(
				groupId, classNameId, ddmTemplateKey);

			if (ddmTemplate == null) {
				Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
					companyId);

				ddmTemplate = DDMTemplateManagerUtil.fetchTemplate(
					companyGroup.getGroupId(), classNameId, ddmTemplateKey);

				if (ddmTemplate == null) {
					classNameId = PortalUtil.getClassNameId(
						DDMStructureManagerUtil.getDDMStructureModelClass());

					ddmTemplate = DDMTemplateManagerUtil.fetchTemplate(
						groupId, classNameId, ddmTemplateKey);
				}

				if (ddmTemplate == null) {
					ddmTemplate = DDMTemplateManagerUtil.fetchTemplate(
						companyGroup.getGroupId(), classNameId, ddmTemplateKey);
				}
			}

			if (ddmTemplate == null) {
				return null;
			}
			else {
				return new DDMTemplateResource(
					ddmTemplate.getTemplateKey(), ddmTemplate);
			}
		}
		catch (Exception e) {
			throw new TemplateException(
				"Unable to find template " + templateId, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMTemplateResourceParser.class);

}