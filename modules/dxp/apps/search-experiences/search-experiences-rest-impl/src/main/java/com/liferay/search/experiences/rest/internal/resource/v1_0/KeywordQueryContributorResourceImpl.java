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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.search.experiences.rest.dto.v1_0.KeywordQueryContributor;
import com.liferay.search.experiences.rest.resource.v1_0.KeywordQueryContributorResource;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/keyword-query-contributor.properties",
	scope = ServiceScope.PROTOTYPE,
	service = KeywordQueryContributorResource.class
)
public class KeywordQueryContributorResourceImpl
	extends BaseKeywordQueryContributorResourceImpl {

	@Override
	public Page<KeywordQueryContributor> getKeywordQueryContributorsPage()
		throws Exception {

		return _getContributors(
			com.liferay.portal.search.spi.model.query.contributor.
				KeywordQueryContributor.class.getName());
	}

	private BundleContext _getBundleContext() {
		Bundle bundle = FrameworkUtil.getBundle(
			KeywordQueryContributorResourceImpl.class);

		return bundle.getBundleContext();
	}

	private Page<KeywordQueryContributor> _getContributors(String className) {
		List<KeywordQueryContributor> keywordQueryContributors =
			new ArrayList<>();

		BundleContext bundleContext = _getBundleContext();

		try {
			ServiceReference<?>[] references =
				bundleContext.getAllServiceReferences(className, null);

			for (ServiceReference<?> serviceReference : references) {
				KeywordQueryContributor keywordQueryContributor =
					new KeywordQueryContributor();

				keywordQueryContributor.setClassName(
					(String)serviceReference.getProperty("component.name"));

				keywordQueryContributors.add(keywordQueryContributor);
			}
		}
		catch (InvalidSyntaxException invalidSyntaxException) {
			_log.error(
				invalidSyntaxException.getMessage(), invalidSyntaxException);
		}

		return Page.of(keywordQueryContributors);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KeywordQueryContributorResourceImpl.class);

}