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

package com.liferay.dynamic.data.mapping.service.http;

import com.liferay.dynamic.data.mapping.service.DDMTemplateServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>DDMTemplateServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.dynamic.data.mapping.model.DDMTemplateSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.dynamic.data.mapping.model.DDMTemplate</code>, that is translated to a
 * <code>com.liferay.dynamic.data.mapping.model.DDMTemplateSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class DDMTemplateServiceSoap {

	/**
	 * Adds a template.
	 *
	 * @param groupId the primary key of the group
	 * @param classNameId the primary key of the class name for template's
	 related model
	 * @param classPK the primary key of the template's related entity
	 * @param resourceClassNameId the primary key of the class name for
	 template's resource model
	 * @param nameMap the template's locales and localized names
	 * @param descriptionMap the template's locales and localized descriptions
	 * @param type the template's type. For more information, see
	 DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param mode the template's mode. For more information, see
	 DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param language the template's script language. For more information,
	 see DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param script the template's script
	 * @param serviceContext the service context to be applied. Must have the
	 <code>ddmResource</code> attribute to check permissions. Can set
	 the UUID, creation date, modification date, guest permissions,
	 and group permissions for the template.
	 * @return the template
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateSoap
			addTemplate(
				long groupId, long classNameId, long classPK,
				long resourceClassNameId, String[] nameMapLanguageIds,
				String[] nameMapValues, String[] descriptionMapLanguageIds,
				String[] descriptionMapValues, String type, String mode,
				String language, String script,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.dynamic.data.mapping.model.DDMTemplate returnValue =
				DDMTemplateServiceUtil.addTemplate(
					groupId, classNameId, classPK, resourceClassNameId, nameMap,
					descriptionMap, type, mode, language, script,
					serviceContext);

			return com.liferay.dynamic.data.mapping.model.DDMTemplateSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Copies the template, creating a new template with all the values
	 * extracted from the original one. This method supports defining a new name
	 * and description.
	 *
	 * @param templateId the primary key of the template to be copied
	 * @param nameMap the new template's locales and localized names
	 * @param descriptionMap the new template's locales and localized
	 descriptions
	 * @param serviceContext the service context to be applied. Must have the
	 <code>ddmResource</code> attribute to check permissions. Can set
	 the UUID, creation date, modification date, guest permissions,
	 and group permissions for the template.
	 * @return the new template
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateSoap
			copyTemplate(
				long templateId, String[] nameMapLanguageIds,
				String[] nameMapValues, String[] descriptionMapLanguageIds,
				String[] descriptionMapValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.dynamic.data.mapping.model.DDMTemplate returnValue =
				DDMTemplateServiceUtil.copyTemplate(
					templateId, nameMap, descriptionMap, serviceContext);

			return com.liferay.dynamic.data.mapping.model.DDMTemplateSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateSoap
			copyTemplate(
				long templateId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMTemplate returnValue =
				DDMTemplateServiceUtil.copyTemplate(templateId, serviceContext);

			return com.liferay.dynamic.data.mapping.model.DDMTemplateSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Copies all the templates matching the class name ID, class PK, and type.
	 * This method creates new templates, extracting all the values from the old
	 * ones and updating their class PKs.
	 *
	 * @param classNameId the primary key of the class name for template's
	 related model
	 * @param oldClassPK the primary key of the old template's related entity
	 * @param resourceClassNameId the primary key of the class name for
	 template's resource model
	 * @param newClassPK the primary key of the new template's related entity
	 * @param type the template's type. For more information, see
	 DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param serviceContext the service context to be applied. Must have the
	 <code>ddmResource</code> attribute to check permissions. Can set
	 the UUID, creation date, modification date, guest permissions,
	 and group permissions for the template.
	 * @return the new template
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateSoap[]
			copyTemplates(
				long classNameId, long oldClassPK, long resourceClassNameId,
				long newClassPK, String type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
				returnValue = DDMTemplateServiceUtil.copyTemplates(
					classNameId, oldClassPK, resourceClassNameId, newClassPK,
					type, serviceContext);

			return com.liferay.dynamic.data.mapping.model.DDMTemplateSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Deletes the template and its resources.
	 *
	 * @param templateId the primary key of the template to be deleted
	 */
	public static void deleteTemplate(long templateId) throws RemoteException {
		try {
			DDMTemplateServiceUtil.deleteTemplate(templateId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the template matching the group and template key.
	 *
	 * @param groupId the primary key of the group
	 * @param classNameId the primary key of the class name for template's
	 related model
	 * @param templateKey the unique string identifying the template
	 * @return the matching template, or <code>null</code> if a matching
	 template could not be found
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateSoap
			fetchTemplate(long groupId, long classNameId, String templateKey)
		throws RemoteException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMTemplate returnValue =
				DDMTemplateServiceUtil.fetchTemplate(
					groupId, classNameId, templateKey);

			return com.liferay.dynamic.data.mapping.model.DDMTemplateSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the template with the ID.
	 *
	 * @param templateId the primary key of the template
	 * @return the template with the ID
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateSoap
			getTemplate(long templateId)
		throws RemoteException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMTemplate returnValue =
				DDMTemplateServiceUtil.getTemplate(templateId);

			return com.liferay.dynamic.data.mapping.model.DDMTemplateSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the template matching the group and template key.
	 *
	 * @param groupId the primary key of the group
	 * @param classNameId the primary key of the class name for template's
	 related model
	 * @param templateKey the unique string identifying the template
	 * @return the matching template
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateSoap
			getTemplate(long groupId, long classNameId, String templateKey)
		throws RemoteException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMTemplate returnValue =
				DDMTemplateServiceUtil.getTemplate(
					groupId, classNameId, templateKey);

			return com.liferay.dynamic.data.mapping.model.DDMTemplateSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the template matching the group and template key, optionally
	 * searching ancestor sites (that have sharing enabled) and global scoped
	 * sites.
	 *
	 * <p>
	 * This method first searches in the group. If the template is still not
	 * found and <code>includeAncestorTemplates</code> is set to
	 * <code>true</code>, this method searches the group's ancestor sites (that
	 * have sharing enabled) and lastly searches global scoped sites.
	 * </p>
	 *
	 * @param groupId the primary key of the group
	 * @param classNameId the primary key of the class name for template's
	 related model
	 * @param templateKey the unique string identifying the template
	 * @param includeAncestorTemplates whether to include ancestor sites (that
	 have sharing enabled) and include global scoped sites in the
	 search
	 * @return the matching template
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateSoap
			getTemplate(
				long groupId, long classNameId, String templateKey,
				boolean includeAncestorTemplates)
		throws RemoteException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMTemplate returnValue =
				DDMTemplateServiceUtil.getTemplate(
					groupId, classNameId, templateKey,
					includeAncestorTemplates);

			return com.liferay.dynamic.data.mapping.model.DDMTemplateSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateSoap[]
			getTemplates(
				long companyId, long groupId, long classNameId,
				long resourceClassNameId, int status)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
				returnValue = DDMTemplateServiceUtil.getTemplates(
					companyId, groupId, classNameId, resourceClassNameId,
					status);

			return com.liferay.dynamic.data.mapping.model.DDMTemplateSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateSoap[]
			getTemplates(
				long companyId, long groupId, long classNameId, long classPK,
				long resourceClassNameId, boolean includeAncestorTemplates,
				int status)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
				returnValue = DDMTemplateServiceUtil.getTemplates(
					companyId, groupId, classNameId, classPK,
					resourceClassNameId, includeAncestorTemplates, status);

			return com.liferay.dynamic.data.mapping.model.DDMTemplateSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateSoap[]
			getTemplates(
				long companyId, long groupId, long classNameId, long classPK,
				long resourceClassNameId, int status)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
				returnValue = DDMTemplateServiceUtil.getTemplates(
					companyId, groupId, classNameId, classPK,
					resourceClassNameId, status);

			return com.liferay.dynamic.data.mapping.model.DDMTemplateSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns all the templates matching the group, class name ID, class PK,
	 * resource class name ID, and type.
	 *
	 * @param companyId the primary key of the template's company
	 * @param groupId the primary key of the group
	 * @param classNameId the primary key of the class name for the template's
	 related model
	 * @param classPK the primary key of the template's related entity
	 * @param resourceClassNameId the primary key of the class name for the
	 template's resource model
	 * @param type the template's type. For more information, see
	 DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @return the matching templates
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateSoap[]
			getTemplates(
				long companyId, long groupId, long classNameId, long classPK,
				long resourceClassNameId, String type, int status)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
				returnValue = DDMTemplateServiceUtil.getTemplates(
					companyId, groupId, classNameId, classPK,
					resourceClassNameId, type, status);

			return com.liferay.dynamic.data.mapping.model.DDMTemplateSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateSoap[]
			getTemplates(
				long companyId, long groupId, long classNameId, long classPK,
				long resourceClassNameId, String type, String mode, int status)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
				returnValue = DDMTemplateServiceUtil.getTemplates(
					companyId, groupId, classNameId, classPK,
					resourceClassNameId, type, mode, status);

			return com.liferay.dynamic.data.mapping.model.DDMTemplateSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateSoap[]
			getTemplates(
				long companyId, long[] groupIds, long[] classNameIds,
				long[] classPKs, long resourceClassNameId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.DDMTemplate>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
				returnValue = DDMTemplateServiceUtil.getTemplates(
					companyId, groupIds, classNameIds, classPKs,
					resourceClassNameId, start, end, orderByComparator);

			return com.liferay.dynamic.data.mapping.model.DDMTemplateSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns all the templates matching the group, class PK, and resource
	 * class name ID.
	 *
	 * @param companyId the primary key of the template's company
	 * @param groupId the primary key of the group
	 * @param classPK the primary key of the template's related entity
	 * @param resourceClassNameId the primary key of the class name for the
	 template's resource model
	 * @return the matching templates
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateSoap[]
			getTemplatesByClassPK(
				long companyId, long groupId, long classPK,
				long resourceClassNameId, int status)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
				returnValue = DDMTemplateServiceUtil.getTemplatesByClassPK(
					companyId, groupId, classPK, resourceClassNameId, status);

			return com.liferay.dynamic.data.mapping.model.DDMTemplateSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns an ordered range of all the templates matching the group and
	 * structure class name ID and all the generic templates matching the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param groupId the primary key of the group
	 * @param structureClassNameId the primary key of the class name for the
	 template's related structure (optionally <code>0</code>). Specify
	 <code>0</code> to return generic templates only.
	 * @param start the lower bound of the range of templates to return
	 * @param end the upper bound of the range of templates to return (not
	 inclusive)
	 * @param orderByComparator the comparator to order the templates
	 (optionally <code>null</code>)
	 * @return the range of matching templates ordered by the comparator
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateSoap[]
			getTemplatesByStructureClassNameId(
				long groupId, long structureClassNameId, int status, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.DDMTemplate>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
				returnValue =
					DDMTemplateServiceUtil.getTemplatesByStructureClassNameId(
						groupId, structureClassNameId, status, start, end,
						orderByComparator);

			return com.liferay.dynamic.data.mapping.model.DDMTemplateSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of templates matching the group and structure class
	 * name ID plus the number of generic templates matching the group.
	 *
	 * @param groupId the primary key of the group
	 * @param structureClassNameId the primary key of the class name for the
	 template's related structure (optionally <code>0</code>). Specify
	 <code>0</code> to count generic templates only.
	 * @return the number of matching templates plus the number of matching
	 generic templates
	 */
	public static int getTemplatesByStructureClassNameIdCount(
			long groupId, long structureClassNameId, int status)
		throws RemoteException {

		try {
			int returnValue =
				DDMTemplateServiceUtil.getTemplatesByStructureClassNameIdCount(
					groupId, structureClassNameId, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getTemplatesCount(
			long companyId, long[] groupIds, long[] classNameIds,
			long[] classPKs, long resourceClassNameId)
		throws RemoteException {

		try {
			int returnValue = DDMTemplateServiceUtil.getTemplatesCount(
				companyId, groupIds, classNameIds, classPKs,
				resourceClassNameId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void revertTemplate(
			long templateId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			DDMTemplateServiceUtil.revertTemplate(
				templateId, version, serviceContext);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns an ordered range of all the templates matching the group, class
	 * name ID, class PK, type, and mode, and matching the keywords in the
	 * template names and descriptions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param companyId the primary key of the template's company
	 * @param groupId the primary key of the group
	 * @param classNameId the primary key of the class name for template's
	 related model
	 * @param classPK the primary key of the template's related entity
	 * @param resourceClassNameId the primary key of the class name for
	 template's resource model
	 * @param keywords the keywords (space separated), which may occur in the
	 template's name or description (optionally <code>null</code>)
	 * @param type the template's type (optionally <code>null</code>). For more
	 information, see DDMTemplateConstants in the
	 dynamic-data-mapping-api module.
	 * @param mode the template's mode (optionally <code>null</code>). For more
	 information, see DDMTemplateConstants in the
	 dynamic-data-mapping-api module.
	 * @param start the lower bound of the range of templates to return
	 * @param end the upper bound of the range of templates to return (not
	 inclusive)
	 * @param orderByComparator the comparator to order the templates
	 (optionally <code>null</code>)
	 * @return the matching templates ordered by the comparator
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateSoap[]
			search(
				long companyId, long groupId, long classNameId, long classPK,
				long resourceClassNameId, String keywords, String type,
				String mode, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.DDMTemplate>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
				returnValue = DDMTemplateServiceUtil.search(
					companyId, groupId, classNameId, classPK,
					resourceClassNameId, keywords, type, mode, status, start,
					end, orderByComparator);

			return com.liferay.dynamic.data.mapping.model.DDMTemplateSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns an ordered range of all the templates matching the group, class
	 * name ID, class PK, name keyword, description keyword, type, mode, and
	 * language.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param companyId the primary key of the template's company
	 * @param groupId the primary key of the group
	 * @param classNameId the primary key of the class name for template's
	 related model
	 * @param classPK the primary key of the template's related entity
	 * @param resourceClassNameId the primary key of the class name for
	 template's resource model
	 * @param name the name keywords (optionally <code>null</code>)
	 * @param description the description keywords (optionally
	 <code>null</code>)
	 * @param type the template's type (optionally <code>null</code>). For more
	 information, see DDMTemplateConstants in the
	 dynamic-data-mapping-api module.
	 * @param mode the template's mode (optionally <code>null</code>). For more
	 information, see DDMTemplateConstants in the
	 dynamic-data-mapping-api module.
	 * @param language the template's script language (optionally
	 <code>null</code>). For more information, see
	 DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param andOperator whether every field must match its keywords, or just
	 one field.
	 * @param start the lower bound of the range of templates to return
	 * @param end the upper bound of the range of templates to return (not
	 inclusive)
	 * @param orderByComparator the comparator to order the templates
	 (optionally <code>null</code>)
	 * @return the matching templates ordered by the comparator
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateSoap[]
			search(
				long companyId, long groupId, long classNameId, long classPK,
				long resourceClassNameId, String name, String description,
				String type, String mode, String language, int status,
				boolean andOperator, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.DDMTemplate>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
				returnValue = DDMTemplateServiceUtil.search(
					companyId, groupId, classNameId, classPK,
					resourceClassNameId, name, description, type, mode,
					language, status, andOperator, start, end,
					orderByComparator);

			return com.liferay.dynamic.data.mapping.model.DDMTemplateSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns an ordered range of all the templates matching the group IDs,
	 * class name IDs, class PK, type, and mode, and matching the keywords in
	 * the template names and descriptions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param companyId the primary key of the template's company
	 * @param groupIds the primary keys of the groups
	 * @param classNameIds the primary keys of the entity's instances the
	 templates are related to
	 * @param classPKs the primary keys of the template's related entities
	 * @param resourceClassNameId the primary key of the class name for
	 template's resource model
	 * @param keywords the keywords (space separated), which may occur in the
	 template's name or description (optionally <code>null</code>)
	 * @param type the template's type (optionally <code>null</code>). For more
	 information, see DDMTemplateConstants in the
	 dynamic-data-mapping-api module.
	 * @param mode the template's mode (optionally <code>null</code>). For more
	 information, see DDMTemplateConstants in the
	 dynamic-data-mapping-api module.
	 * @param start the lower bound of the range of templates to return
	 * @param end the upper bound of the range of templates to return (not
	 inclusive)
	 * @param orderByComparator the comparator to order the templates
	 (optionally <code>null</code>)
	 * @return the matching templates ordered by the comparator
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateSoap[]
			search(
				long companyId, long[] groupIds, long[] classNameIds,
				long[] classPKs, long resourceClassNameId, String keywords,
				String type, String mode, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.DDMTemplate>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
				returnValue = DDMTemplateServiceUtil.search(
					companyId, groupIds, classNameIds, classPKs,
					resourceClassNameId, keywords, type, mode, status, start,
					end, orderByComparator);

			return com.liferay.dynamic.data.mapping.model.DDMTemplateSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns an ordered range of all the templates matching the group IDs,
	 * class name IDs, class PK, name keyword, description keyword, type, mode,
	 * and language.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param companyId the primary key of the template's company
	 * @param groupIds the primary keys of the groups
	 * @param classNameIds the primary keys of the entity's instances the
	 templates are related to
	 * @param classPKs the primary keys of the template's related entities
	 * @param resourceClassNameId the primary key of the class name for
	 template's resource model
	 * @param name the name keywords (optionally <code>null</code>)
	 * @param description the description keywords (optionally
	 <code>null</code>)
	 * @param type the template's type (optionally <code>null</code>). For more
	 information, see DDMTemplateConstants in the
	 dynamic-data-mapping-api module.
	 * @param mode the template's mode (optionally <code>null</code>). For more
	 information, see DDMTemplateConstants in the
	 dynamic-data-mapping-api module.
	 * @param language the template's script language (optionally
	 <code>null</code>). For more information, see
	 DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param andOperator whether every field must match its keywords, or just
	 one field.
	 * @param start the lower bound of the range of templates to return
	 * @param end the upper bound of the range of templates to return (not
	 inclusive)
	 * @param orderByComparator the comparator to order the templates
	 (optionally <code>null</code>)
	 * @return the matching templates ordered by the comparator
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateSoap[]
			search(
				long companyId, long[] groupIds, long[] classNameIds,
				long[] classPKs, long resourceClassNameId, String name,
				String description, String type, String mode, String language,
				int status, boolean andOperator, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.DDMTemplate>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate>
				returnValue = DDMTemplateServiceUtil.search(
					companyId, groupIds, classNameIds, classPKs,
					resourceClassNameId, name, description, type, mode,
					language, status, andOperator, start, end,
					orderByComparator);

			return com.liferay.dynamic.data.mapping.model.DDMTemplateSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of templates matching the group, class name ID, class
	 * PK, type, and mode, and matching the keywords in the template names and
	 * descriptions.
	 *
	 * @param companyId the primary key of the template's company
	 * @param groupId the primary key of the group
	 * @param classNameId the primary key of the class name for template's
	 related model
	 * @param classPK the primary key of the template's related entity
	 * @param resourceClassNameId the primary key of the class name for
	 template's resource model
	 * @param keywords the keywords (space separated), which may occur in the
	 template's name or description (optionally <code>null</code>)
	 * @param type the template's type (optionally <code>null</code>). For more
	 information, see DDMTemplateConstants in the
	 dynamic-data-mapping-api module.
	 * @param mode the template's mode (optionally <code>null</code>). For more
	 information, see DDMTemplateConstants in the
	 dynamic-data-mapping-api module.
	 * @return the number of matching templates
	 */
	public static int searchCount(
			long companyId, long groupId, long classNameId, long classPK,
			long resourceClassNameId, String keywords, String type, String mode,
			int status)
		throws RemoteException {

		try {
			int returnValue = DDMTemplateServiceUtil.searchCount(
				companyId, groupId, classNameId, classPK, resourceClassNameId,
				keywords, type, mode, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of templates matching the group, class name ID, class
	 * PK, name keyword, description keyword, type, mode, and language.
	 *
	 * @param companyId the primary key of the template's company
	 * @param groupId the primary key of the group
	 * @param classNameId the primary key of the class name for template's
	 related model
	 * @param classPK the primary key of the template's related entity
	 * @param resourceClassNameId the primary key of the class name for
	 template's resource model
	 * @param name the name keywords (optionally <code>null</code>)
	 * @param description the description keywords (optionally
	 <code>null</code>)
	 * @param type the template's type (optionally <code>null</code>). For more
	 information, see DDMTemplateConstants in the
	 dynamic-data-mapping-api module.
	 * @param mode the template's mode (optionally <code>null</code>). For more
	 information, see DDMTemplateConstants in the
	 dynamic-data-mapping-api module.
	 * @param language the template's script language (optionally
	 <code>null</code>). For more information, see
	 DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param andOperator whether every field must match its keywords, or just
	 one field.
	 * @return the number of matching templates
	 */
	public static int searchCount(
			long companyId, long groupId, long classNameId, long classPK,
			long resourceClassNameId, String name, String description,
			String type, String mode, String language, int status,
			boolean andOperator)
		throws RemoteException {

		try {
			int returnValue = DDMTemplateServiceUtil.searchCount(
				companyId, groupId, classNameId, classPK, resourceClassNameId,
				name, description, type, mode, language, status, andOperator);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of templates matching the group IDs, class name IDs,
	 * class PK, type, and mode, and matching the keywords in the template names
	 * and descriptions.
	 *
	 * @param companyId the primary key of the template's company
	 * @param groupIds the primary keys of the groups
	 * @param classNameIds the primary keys of the entity's instances the
	 templates are related to
	 * @param classPKs the primary keys of the template's related entities
	 * @param resourceClassNameId the primary key of the class name for
	 template's resource model
	 * @param keywords the keywords (space separated), which may occur in the
	 template's name or description (optionally <code>null</code>)
	 * @param type the template's type (optionally <code>null</code>). For more
	 information, see DDMTemplateConstants in the
	 dynamic-data-mapping-api module.
	 * @param mode the template's mode (optionally <code>null</code>). For more
	 information, see DDMTemplateConstants in the
	 dynamic-data-mapping-api module.
	 * @return the number of matching templates
	 */
	public static int searchCount(
			long companyId, long[] groupIds, long[] classNameIds,
			long[] classPKs, long resourceClassNameId, String keywords,
			String type, String mode, int status)
		throws RemoteException {

		try {
			int returnValue = DDMTemplateServiceUtil.searchCount(
				companyId, groupIds, classNameIds, classPKs,
				resourceClassNameId, keywords, type, mode, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of templates matching the group IDs, class name IDs,
	 * class PK, name keyword, description keyword, type, mode, and language.
	 *
	 * @param companyId the primary key of the template's company
	 * @param groupIds the primary keys of the groups
	 * @param classNameIds the primary keys of the entity's instances the
	 templates are related to
	 * @param classPKs the primary keys of the template's related entities
	 * @param resourceClassNameId the primary key of the class name for
	 template's resource model
	 * @param name the name keywords (optionally <code>null</code>)
	 * @param description the description keywords (optionally
	 <code>null</code>)
	 * @param type the template's type (optionally <code>null</code>). For more
	 information, see DDMTemplateConstants in the
	 dynamic-data-mapping-api module.
	 * @param mode the template's mode (optionally <code>null</code>). For more
	 information, see DDMTemplateConstants in the
	 dynamic-data-mapping-api module.
	 * @param language the template's script language (optionally
	 <code>null</code>). For more information, see
	 DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param andOperator whether every field must match its keywords, or just
	 one field.
	 * @return the number of matching templates
	 */
	public static int searchCount(
			long companyId, long[] groupIds, long[] classNameIds,
			long[] classPKs, long resourceClassNameId, String name,
			String description, String type, String mode, String language,
			int status, boolean andOperator)
		throws RemoteException {

		try {
			int returnValue = DDMTemplateServiceUtil.searchCount(
				companyId, groupIds, classNameIds, classPKs,
				resourceClassNameId, name, description, type, mode, language,
				status, andOperator);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Updates the template matching the ID.
	 *
	 * @param templateId the primary key of the template
	 * @param classPK the primary key of the template's related entity
	 * @param nameMap the template's new locales and localized names
	 * @param descriptionMap the template's new locales and localized
	 description
	 * @param type the template's type. For more information, see
	 DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param mode the template's mode. For more information, see
	 DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param language the template's script language. For more information,
	 see DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param script the template's script
	 * @param cacheable whether the template is cacheable
	 * @param serviceContext the service context to be applied. Can set the
	 modification date.
	 * @return the updated template
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateSoap
			updateTemplate(
				long templateId, long classPK, String[] nameMapLanguageIds,
				String[] nameMapValues, String[] descriptionMapLanguageIds,
				String[] descriptionMapValues, String type, String mode,
				String language, String script, boolean cacheable,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.dynamic.data.mapping.model.DDMTemplate returnValue =
				DDMTemplateServiceUtil.updateTemplate(
					templateId, classPK, nameMap, descriptionMap, type, mode,
					language, script, cacheable, serviceContext);

			return com.liferay.dynamic.data.mapping.model.DDMTemplateSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDMTemplateServiceSoap.class);

}