/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.digital.signature.rest.internal.resource.v1_0;

import com.liferay.digital.signature.manager.DSEnvelopeManager;
import com.liferay.digital.signature.rest.dto.v1_0.DSDocument;
import com.liferay.digital.signature.rest.dto.v1_0.DSEnvelope;
import com.liferay.digital.signature.rest.internal.dto.v1_0.util.DSEnvelopeUtil;
import com.liferay.digital.signature.rest.resource.v1_0.DSEnvelopeResource;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author José Abelenda
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/ds-envelope.properties",
	scope = ServiceScope.PROTOTYPE, service = DSEnvelopeResource.class
)
public class DSEnvelopeResourceImpl extends BaseDSEnvelopeResourceImpl {

	@Override
	public DSEnvelope getSiteDSEnvelope(Long siteId, String dsEnvelopeId)
		throws Exception {

		return DSEnvelopeUtil.toDSEnvelope(
			_dsEnvelopeManager.getDSEnvelope(
				contextCompany.getCompanyId(), siteId, dsEnvelopeId));
	}

	@Override
	public DSEnvelope postSiteDSEnvelope(Long siteId, DSEnvelope dsEnvelope)
		throws Exception {

		return DSEnvelopeUtil.toDSEnvelope(
			_dsEnvelopeManager.addDSEnvelope(
				contextCompany.getCompanyId(), siteId,
				_getDSEnvelope(siteId, dsEnvelope)));
	}

	private com.liferay.digital.signature.model.DSEnvelope _getDSEnvelope(
			Long siteId, DSEnvelope dsEnvelope)
		throws Exception {

		for (DSDocument document : dsEnvelope.getDsDocument()) {
			if (Validator.isNull(
					document.getFileEntryExternalReferenceCode())) {

				continue;
			}

			DLFileEntry dlFileEntry =
				_dlFileEntryLocalService.fetchFileEntryByExternalReferenceCode(
					siteId, document.getFileEntryExternalReferenceCode());

			if (dlFileEntry == null) {
				continue;
			}

			document.setData(
				Base64.encode(
					FileUtil.getBytes(dlFileEntry.getContentStream())));
		}

		return DSEnvelopeUtil.toDSEnvelope(dsEnvelope);
	}

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DSEnvelopeManager _dsEnvelopeManager;

}