/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.internal.dto.v1_0.converter;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.search.experiences.rest.dto.v1_0.ElementDefinition;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;
import com.liferay.search.experiences.rest.dto.v1_0.util.ElementDefinitionUtil;
import com.liferay.search.experiences.service.SXPElementLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.search.experiences.model.SXPElement",
	service = {DTOConverter.class, SXPElementDTOConverter.class}
)
public class SXPElementDTOConverter
	implements DTOConverter
		<com.liferay.search.experiences.model.SXPElement, SXPElement> {

	@Override
	public String getContentType() {
		return SXPElement.class.getSimpleName();
	}

	@Override
	public SXPElement toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		com.liferay.search.experiences.model.SXPElement sxpElement =
			_sxpElementLocalService.getSXPElement(
				(Long)dtoConverterContext.getId());

		return toDTO(dtoConverterContext, sxpElement);
	}

	@Override
	public SXPElement toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.search.experiences.model.SXPElement sxpElement)
		throws Exception {

		return new SXPElement() {
			{
				createDate = sxpElement.getCreateDate();
				description = sxpElement.getDescription(
					dtoConverterContext.getLocale());
				description_i18n = LocalizedMapUtil.getI18nMap(
					true, sxpElement.getDescriptionMap());
				elementDefinition = _toElementDefinition(
					sxpElement.getElementDefinitionJSON());
				id = sxpElement.getSXPElementId();
				modifiedDate = sxpElement.getModifiedDate();
				readOnly = sxpElement.getReadOnly();
				schemaVersion = sxpElement.getSchemaVersion();
				title = sxpElement.getTitle(dtoConverterContext.getLocale());
				title_i18n = LocalizedMapUtil.getI18nMap(
					true, sxpElement.getTitleMap());
				type = sxpElement.getType();
				userName = sxpElement.getUserName();
			}
		};
	}

	private ElementDefinition _toElementDefinition(String json) {
		try {
			return ElementDefinitionUtil.toElementDefinition(json);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPElementDTOConverter.class);

	@Reference
	private SXPElementLocalService _sxpElementLocalService;

}