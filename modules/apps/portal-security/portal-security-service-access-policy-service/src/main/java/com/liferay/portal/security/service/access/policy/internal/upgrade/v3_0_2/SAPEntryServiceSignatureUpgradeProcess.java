/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.service.access.policy.internal.upgrade.v3_0_2;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Mauricio Valdivia
 */
public class SAPEntryServiceSignatureUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateObjectEntryRelationshipServiceSignature();
	}

	private void _updateObjectEntryRelationshipServiceSignature()
		throws Exception {

		String oldAllowedServiceSignatures = StringBundler.concat(
			"com.liferay.object.rest.internal.resource.v1_0.",
			"ObjectEntryResourceImpl#",
			"putByExternalReferenceCodeCurrentExternalReferenceCode",
			"ObjectRelationshipNameRelatedExternalReferenceCode");
		String newAllowedServiceSignatures = StringBundler.concat(
			"com.liferay.object.rest.internal.resource.v1_0.",
			"ObjectEntryRelatedObjectsResourceImpl#",
			"putByExternalReferenceCodeCurrentExternalReferenceCode",
			"ObjectRelationshipNameRelatedExternalReferenceCode");

		runSQL(
			StringBundler.concat(
				"update SAPEntry set allowedServiceSignatures = ",
				"REPLACE(allowedServiceSignatures, '",
				oldAllowedServiceSignatures, "', '",
				newAllowedServiceSignatures,
				"') where allowedServiceSignatures like '%",
				oldAllowedServiceSignatures, "%'"));
	}

}