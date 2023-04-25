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

package com.liferay.osb.faro.web.internal.controller;

import com.liferay.osb.faro.web.internal.model.display.FaroResultsDisplay;
import com.liferay.osb.faro.web.internal.search.FaroSearchContext;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Matthew Kong
 */
@Component(service = FaroControllerRegistry.class)
public class FaroControllerRegistry {

	public List<FaroResultsDisplay> search(
			long groupId, List<FaroSearchContext> faroSearchContexts)
		throws Exception {

		return TransformUtil.transform(
			faroSearchContexts,
			faroSearchContext -> getFaroResultsDisplay(
				groupId, faroSearchContext));
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addFaroController(FaroController faroController) {
		_faroControllers.add(faroController);
	}

	protected FaroResultsDisplay getFaroResultsDisplay(
			long groupId, FaroSearchContext faroSearchContext)
		throws Exception {

		for (FaroController faroController : _faroControllers) {
			if (ArrayUtil.contains(
					faroController.getEntityTypes(),
					faroSearchContext.getType())) {

				return faroController.search(groupId, faroSearchContext);
			}
		}

		return new FaroResultsDisplay();
	}

	protected void removeFaroController(FaroController faroController) {
		_faroControllers.remove(faroController);
	}

	private final List<FaroController> _faroControllers = new ArrayList<>();

}