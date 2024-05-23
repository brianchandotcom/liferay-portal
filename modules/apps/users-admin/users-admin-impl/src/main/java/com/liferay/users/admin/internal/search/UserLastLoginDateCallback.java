/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.internal.search;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.search.index.UpdateDocumentIndexWriter;
import com.liferay.portal.search.indexer.BaseModelDocumentFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = "key=com.liferay.portal.kernel.model.User#lastLoginDate",
	service = Indexable.Callback.class
)
public class UserLastLoginDateCallback implements Indexable.Callback {

	@Override
	public void reindex(BaseModel<?> baseModel) {
		if (!(baseModel instanceof User)) {
			return;
		}

		User user = (User)baseModel;

		Document document = _baseModelDocumentFactory.createDocument(user);

		document.addDate(Field.MODIFIED_DATE, user.getModifiedDate());
		document.addDate("lastLoginDate", user.getLastLoginDate());

		_updateDocumentIndexWriter.updateDocumentPartially(
			user.getCompanyId(), document, false);
	}

	@Reference
	private BaseModelDocumentFactory _baseModelDocumentFactory;

	@Reference
	private UpdateDocumentIndexWriter _updateDocumentIndexWriter;

}