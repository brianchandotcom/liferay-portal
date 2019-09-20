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

package com.liferay.blogs.web.internal.template;

import com.liferay.blogs.configuration.BlogsConfiguration;
import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.blogs.web.internal.security.permission.resource.BlogsEntryPermission;
import com.liferay.blogs.web.internal.util.BlogsEntryUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.portletdisplaytemplate.PortletDisplayTemplateManager;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.taglib.security.PermissionsURLTag;
import com.liferay.trash.TrashHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Juan Fernández
 */
@Component(
	configurationPid = "com.liferay.blogs.configuration.BlogsConfiguration",
	immediate = true, property = "javax.portlet.name=" + BlogsPortletKeys.BLOGS,
	service = TemplateHandler.class
)
public class BlogsPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return BlogsEntry.class.getName();
	}

	@Override
	public Map<String, Object> getCustomContextObjects() {
		Map<String, Object> contextObjects = new HashMap<>();

		contextObjects.put("blogsEntryPermission", _blogsEntryPermission);
		contextObjects.put("blogsEntryUtil", _blogsEntryUtil);
		contextObjects.put("commentManager", _commentManager);
		contextObjects.put("language", _language);
		contextObjects.put("permissionsURLTag", new PermissionsURLTag());
		contextObjects.put("trashHelper", _trashHelper);

		return contextObjects;
	}

	@Override
	public String getName(Locale locale) {
		String portletTitle = _portal.getPortletTitle(
			BlogsPortletKeys.BLOGS,
			ResourceBundleUtil.getBundle(
				"content.Language", locale, getClass()));

		return LanguageUtil.format(locale, "x-template", portletTitle, false);
	}

	@Override
	public String getResourceName() {
		return BlogsPortletKeys.BLOGS;
	}

	@Override
	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
			long classPK, String language, Locale locale)
		throws Exception {

		Map<String, TemplateVariableGroup> templateVariableGroups =
			super.getTemplateVariableGroups(classPK, language, locale);

		String[] restrictedVariables = getRestrictedVariables(language);

		TemplateVariableGroup blogsUtilTemplateVariableGroup =
			new TemplateVariableGroup("blogs-util", restrictedVariables);

		templateVariableGroups.put(
			"blogs-util", blogsUtilTemplateVariableGroup);

		TemplateVariableGroup blogServicesTemplateVariableGroup =
			new TemplateVariableGroup("blog-services", restrictedVariables);

		blogServicesTemplateVariableGroup.setAutocompleteEnabled(false);

		blogServicesTemplateVariableGroup.addServiceLocatorVariables(
			BlogsEntryLocalService.class, BlogsEntryService.class);

		templateVariableGroups.put(
			blogServicesTemplateVariableGroup.getLabel(),
			blogServicesTemplateVariableGroup);

		TemplateVariableGroup fieldsTemplateVariableGroup =
			templateVariableGroups.get("fields");

		fieldsTemplateVariableGroup.empty();

		fieldsTemplateVariableGroup.addCollectionVariable(
			"blog-entries", List.class, PortletDisplayTemplateManager.ENTRIES,
			"blog-entry", BlogsEntry.class, "curBlogEntry", "title");

		return templateVariableGroups;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_blogsConfiguration = ConfigurableUtil.createConfigurable(
			BlogsConfiguration.class, properties);
	}

	@Override
	protected String getTemplatesConfigPath() {
		return _blogsConfiguration.displayTemplatesConfig();
	}

	private volatile BlogsConfiguration _blogsConfiguration;

	@Reference
	private BlogsEntryPermission _blogsEntryPermission;

	@Reference
	private BlogsEntryUtil _blogsEntryUtil;

	@Reference
	private CommentManager _commentManager;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.blogs.service)(&(release.schema.version>=2.1.0)(!(release.schema.version>=3.0.0))))"
	)
	private Release _release;

	@Reference
	private TrashHelper _trashHelper;

}