/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the d d m template remote service. This utility wraps {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMTemplateServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateService
 * @see com.liferay.portlet.dynamicdatamapping.service.base.DDMTemplateServiceBaseImpl
 * @see com.liferay.portlet.dynamicdatamapping.service.impl.DDMTemplateServiceImpl
 * @generated
 */
public class DDMTemplateServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMTemplateServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate addTemplate(
		long groupId, long structureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, java.lang.String script,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addTemplate(groupId, structureId, nameMap, descriptionMap,
			type, mode, language, script, serviceContext);
	}

	public static void deleteTemplate(long templateId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTemplate(templateId);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate getTemplate(
		long templateId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTemplate(templateId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplates(
		long structureId, java.lang.String type, java.lang.String mode)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTemplates(structureId, type, mode);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate updateTemplate(
		long templateId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, java.lang.String script,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateTemplate(templateId, nameMap, descriptionMap, type,
			mode, language, script, serviceContext);
	}

	public static DDMTemplateService getService() {
		if (_service == null) {
			_service = (DDMTemplateService)PortalBeanLocatorUtil.locate(DDMTemplateService.class.getName());

			ReferenceRegistry.registerReference(DDMTemplateServiceUtil.class,
				"_service");
			MethodCache.remove(DDMTemplateService.class);
		}

		return _service;
	}

	public void setService(DDMTemplateService service) {
		MethodCache.remove(DDMTemplateService.class);

		_service = service;

		ReferenceRegistry.registerReference(DDMTemplateServiceUtil.class,
			"_service");
		MethodCache.remove(DDMTemplateService.class);
	}

	private static DDMTemplateService _service;
}