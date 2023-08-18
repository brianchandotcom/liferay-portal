/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.session.util;

import com.liferay.saml.opensaml.integration.internal.util.OpenSamlUtil;
import com.liferay.saml.persistence.model.SamlSpSession;

import org.joda.time.DateTime;

import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Conditions;

/**
 * @author Michael Bowerman
 */
public class SamlSessionUtil {

	public static boolean isSamlSpSessionStillValid(SamlSpSession samlSpSession)
		throws Exception {

		Assertion assertion = (Assertion)OpenSamlUtil.unmarshall(
			samlSpSession.getAssertionXml());

		Conditions conditions = assertion.getConditions();

		if (conditions == null) {
			return true;
		}

		DateTime dateTime = conditions.getNotOnOrAfter();

		if ((dateTime == null) || dateTime.isAfterNow()) {
			return true;
		}

		return false;
	}

}