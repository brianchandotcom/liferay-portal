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

package com.liferay.headless.commerce.bom.internal.graphql.servlet.v1_0;

import com.liferay.headless.commerce.bom.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.commerce.bom.internal.graphql.query.v1_0.Query;
import com.liferay.headless.commerce.bom.resource.v1_0.AreaResource;
import com.liferay.headless.commerce.bom.resource.v1_0.FolderResource;
import com.liferay.headless.commerce.bom.resource.v1_0.ProductResource;
import com.liferay.headless.commerce.bom.resource.v1_0.SpotResource;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Component(immediate = true, service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setSpotResourceComponentServiceObjects(
			_spotResourceComponentServiceObjects);

		Query.setAreaResourceComponentServiceObjects(
			_areaResourceComponentServiceObjects);
		Query.setFolderResourceComponentServiceObjects(
			_folderResourceComponentServiceObjects);
		Query.setProductResourceComponentServiceObjects(
			_productResourceComponentServiceObjects);
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/commerce-bom-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SpotResource>
		_spotResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AreaResource>
		_areaResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<FolderResource>
		_folderResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProductResource>
		_productResourceComponentServiceObjects;

}