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

package com.liferay.search.experiences.rest.internal.resource.v1_0;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.asset.SearchableAssetClassNamesProvider;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.query.contributor.QueryPreFilterContributor;
import com.liferay.search.experiences.rest.resource.v1_0.SXPAdminUtilResource;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/sxp-admin-util.properties",
	scope = ServiceScope.PROTOTYPE, service = SXPAdminUtilResource.class
)
public class SXPAdminUtilResourceImpl extends BaseSXPAdminUtilResourceImpl {

	@Override
	public String getSXPAdminUtilKeywordQueryContributors() throws Exception {
		return _getComponents(KeywordQueryContributor.class.getName());
	}

	@Override
	public String getSXPAdminUtilModelPrefilterContributors() throws Exception {
		return _getComponents(ModelPreFilterContributor.class.getName());
	}

	@Override
	public String getSXPAdminUtilQueryPrefilterContributors() throws Exception {
		return _getComponents(QueryPreFilterContributor.class.getName());
	}

	@Override
	public String getSXPAdminUtilSearchableAssetNameCompany(Long companyId)
		throws Exception {

		JSONArray jsonArray = JSONUtil.putAll(
			_searchableAssetClassNamesProvider.getClassNames(companyId));

		return jsonArray.toJSONString();
	}

	@Override
	public String getSXPAdminUtilSearchableAssetNameCompanyLanguage(
		Long companyId, String languageId) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		String[] classNames = _searchableAssetClassNamesProvider.getClassNames(
			companyId);

		for (String className : classNames) {
			jsonArray.put(
				JSONUtil.put(
					"className", className
				).put(
					"displayName", _getDisplayName(className, languageId)
				));
		}

		return jsonArray.toJSONString();
	}

	private BundleContext _getBundleContext() {
		Bundle bundle = FrameworkUtil.getBundle(SXPAdminUtilResourceImpl.class);

		return bundle.getBundleContext();
	}

	private String _getComponents(String className) {
		BundleContext bundleContext = _getBundleContext();

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		try {
			ServiceReference<?>[] references =
				bundleContext.getAllServiceReferences(className, null);

			for (ServiceReference<?> serviceReference : references) {
				jsonArray.put(
					(String)serviceReference.getProperty("component.name"));
			}
		}
		catch (InvalidSyntaxException invalidSyntaxException) {
			_log.error(
				invalidSyntaxException.getMessage(), invalidSyntaxException);
		}

		return jsonArray.toJSONString();
	}

	private String _getDisplayName(String className, String languageId) {
		String modelResource = ResourceActionsUtil.getModelResource(
			LocaleUtil.fromLanguageId(languageId), className);

		if (className.startsWith(ObjectDefinition.class.getName() + "#")) {
			String[] parts = StringUtil.split(className, "#");

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					Long.valueOf(parts[1]));

			modelResource = objectDefinition.getLabel(languageId);
		}

		return modelResource;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPAdminUtilResourceImpl.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private SearchableAssetClassNamesProvider
		_searchableAssetClassNamesProvider;

}