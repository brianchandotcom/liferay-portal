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

package com.liferay.headless.commerce.punchout.internal.helper;

import com.liferay.commerce.punchout.constants.PunchOutConstants;
import com.liferay.headless.commerce.punchout.dto.v1_0.PunchOutSession;
import com.liferay.headless.commerce.punchout.helper.PunchOutContext;
import com.liferay.headless.commerce.punchout.helper.PunchOutSessionContributor;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.HashMap;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jaclyn Ong
 */
@Component(service = PunchOutSessionContributor.class)
public class PunchOutSessionContributorImpl
	implements PunchOutSessionContributor {

	@Override
	public HashMap<String, Object> getPunchOutSessionAttributes(
		PunchOutContext punchOutContext) {

		return HashMapBuilder.<String, Object>put(
			PunchOutConstants.PUNCH_OUT_RETURN_URL_ATTRIBUTE_NAME,
			() -> {
				PunchOutSession punchOutSession =
					punchOutContext.getPunchOutSession();

				return punchOutSession.getPunchOutReturnURL();
			}
		).build();
	}

}