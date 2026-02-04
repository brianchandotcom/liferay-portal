/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.cmp.site.initializer.internal.util.ActionUtil;
import com.liferay.subscription.service.SubscriptionLocalService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(service = FragmentRenderer.class)
public class ProjectBreadcrumbComponentSectionFragmentRenderer
	extends BaseComponentSectionFragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "project-breadcrumb";
	}

	@Override
	protected String getComponentName(HttpServletRequest httpServletRequest) {
		return "Breadcrumb";
	}

	@Override
	protected String getLabelKey() {
		return "project-breadcrumb";
	}

	@Override
	protected String getModuleName() {
		return "site-cms-site-initializer";
	}

	@Override
	protected Map<String, Object> getProps(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest)
		throws Exception {

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			(LayoutDisplayPageObjectProvider<?>)httpServletRequest.getAttribute(
				LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER);

		if (layoutDisplayPageObjectProvider == null) {
			return null;
		}

		Object displayObject =
			layoutDisplayPageObjectProvider.getDisplayObject();

		if (!(displayObject instanceof ObjectEntry)) {
			return null;
		}

		ObjectEntry objectEntry = (ObjectEntry)displayObject;

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectEntry.getObjectDefinitionId());
		String title = MapUtil.getString(objectEntry.getValues(), "title");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return HashMapBuilder.<String, Object>put(
			"actionItems",
			() -> {
				JSONArray jsonArray = _jsonFactory.createJSONArray();

				if (_objectEntryService.hasModelResourcePermission(
						objectEntry, ActionKeys.UPDATE)) {

					jsonArray.put(
						JSONUtil.put(
							"href",
							StringBundler.concat(
								ActionUtil.getBaseEditProjectURL(
									objectDefinition, themeDisplay),
								objectEntry.getObjectEntryId(), "?redirect=",
								themeDisplay.getURLCurrent())
						).put(
							"label",
							LanguageUtil.get(httpServletRequest, "edit")
						).put(
							"symbolLeft", "pencil"
						));
				}

				if (_objectEntryService.hasModelResourcePermission(
						objectEntry, ActionKeys.SUBSCRIBE)) {

					String restContextPath = StringBundler.concat(
						"/o", objectDefinition.getRESTContextPath(), "/scopes/",
						objectEntry.getGroupId(),
						"/by-external-reference-code/",
						objectEntry.getExternalReferenceCode());
					String viewProjectURL = ActionUtil.getBaseViewProjectURL(
						objectDefinition, themeDisplay);

					if (!_subscriptionLocalService.isSubscribed(
							objectEntry.getCompanyId(),
							themeDisplay.getUserId(),
							objectEntry.getModelClassName(),
							objectEntry.getObjectEntryId())) {

						jsonArray.put(
							JSONUtil.put(
								"href", restContextPath + "/subscribe"
							).put(
								"label",
								LanguageUtil.get(
									httpServletRequest, "watch-project")
							).put(
								"redirect",
								viewProjectURL + objectEntry.getObjectEntryId()
							).put(
								"successMessage",
								LanguageUtil.format(
									httpServletRequest,
									"you-are-successfully-watching-x",
									StringBundler.concat(
										"<strong>", title, "</strong>"))
							).put(
								"symbolLeft", "bell-on"
							).put(
								"target", "asyncPost"
							));
					}
					else {
						jsonArray.put(
							JSONUtil.put(
								"href", restContextPath + "/unsubscribe"
							).put(
								"label",
								LanguageUtil.get(
									httpServletRequest, "stop-watching-project")
							).put(
								"redirect",
								viewProjectURL + objectEntry.getObjectEntryId()
							).put(
								"symbolLeft", "bell-off"
							).put(
								"target", "asyncPost"
							));
					}
				}

				if (_objectEntryService.hasModelResourcePermission(
						objectEntry, ActionKeys.DELETE)) {

					jsonArray.put(
						JSONUtil.put(
							"confirmationMessage",
							LanguageUtil.format(
								httpServletRequest,
								"delete-project-confirmation-body", title)
						).put(
							"confirmationTitle",
							LanguageUtil.format(
								httpServletRequest,
								"delete-asset-confirmation-title", title)
						).put(
							"href",
							StringBundler.concat(
								"/o", objectDefinition.getRESTContextPath(),
								StringPool.SLASH,
								objectEntry.getObjectEntryId())
						).put(
							"label",
							LanguageUtil.get(httpServletRequest, "delete")
						).put(
							"redirect", ActionUtil.getProjectsURL(themeDisplay)
						).put(
							"successMessage",
							LanguageUtil.format(
								httpServletRequest,
								"x-was-successfully-deleted",
								StringBundler.concat(
									"<strong>", title, "</strong>"))
						).put(
							"symbolLeft", "trash"
						).put(
							"target", "asyncDelete"
						));
				}

				if (jsonArray.length() == 0) {
					return null;
				}

				return jsonArray;
			}
		).put(
			"breadcrumbItems",
			JSONUtil.putAll(
				JSONUtil.put(
					"active", false
				).put(
					"href", ActionUtil.getProjectsURL(themeDisplay)
				).put(
					"label", LanguageUtil.get(httpServletRequest, "projects")
				),
				JSONUtil.put(
					"active", true
				).put(
					"href", StringPool.BLANK
				).put(
					"label", title
				))
		).put(
			"hideSpace", true
		).put(
			"size", "lg"
		).build();
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryService _objectEntryService;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}