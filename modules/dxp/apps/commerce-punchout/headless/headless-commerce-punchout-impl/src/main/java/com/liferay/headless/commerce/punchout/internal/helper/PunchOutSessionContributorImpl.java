/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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