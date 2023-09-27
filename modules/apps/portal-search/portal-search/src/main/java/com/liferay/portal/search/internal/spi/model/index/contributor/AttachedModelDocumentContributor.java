/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.spi.model.index.contributor;

import com.liferay.portal.kernel.model.AttachedModel;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentContributor;
import com.liferay.portal.kernel.search.DocumentHelper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = DocumentContributor.class)
public class AttachedModelDocumentContributor
	implements DocumentContributor<AttachedModel> {

	@Override
	public void contribute(
		Document document, BaseModel<AttachedModel> baseModel) {

		if (!(baseModel instanceof AttachedModel)) {
			return;
		}

		DocumentHelper documentHelper = new DocumentHelper(document);

		AttachedModel attachedModel = (AttachedModel)baseModel;

		documentHelper.setAttachmentOwnerKey(
			attachedModel.getClassNameId(), attachedModel.getClassPK());
	}

}