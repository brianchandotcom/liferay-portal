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

package com.liferay.search.experiences.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.search.experiences.rest.client.dto.v1_0.SXPBlueprint;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class SXPBlueprintResourceTest extends BaseSXPBlueprintResourceTestCase {

	@Ignore
	@Override
	@Test
	public void testGraphQLGetSXPBlueprint() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetSXPBlueprintNotFound() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testPatchSXPBlueprint() throws Exception {
	}

	@Override
	protected SXPBlueprint testDeleteSXPBlueprint_addSXPBlueprint()
		throws Exception {

		return _addSXPBlueprint(randomSXPBlueprint());
	}

	@Override
	protected SXPBlueprint testGetSXPBlueprint_addSXPBlueprint()
		throws Exception {

		return _addSXPBlueprint(randomSXPBlueprint());
	}

	@Override
	protected SXPBlueprint testGetSXPBlueprintsPage_addSXPBlueprint(
			SXPBlueprint sxpBlueprint)
		throws Exception {

		return _addSXPBlueprint(sxpBlueprint);
	}

	@Override
	protected SXPBlueprint testGraphQLSXPBlueprint_addSXPBlueprint()
		throws Exception {

		return _addSXPBlueprint(randomSXPBlueprint());
	}

	@Override
	protected SXPBlueprint testPatchSXPBlueprint_addSXPBlueprint()
		throws Exception {

		return _addSXPBlueprint(randomSXPBlueprint());
	}

	@Override
	protected SXPBlueprint testPostSXPBlueprint_addSXPBlueprint(
			SXPBlueprint sxpBlueprint)
		throws Exception {

		return _addSXPBlueprint(sxpBlueprint);
	}

	private SXPBlueprint _addSXPBlueprint(SXPBlueprint sxpBlueprint)
		throws Exception {

		return sxpBlueprintResource.postSXPBlueprint(sxpBlueprint);
	}

}