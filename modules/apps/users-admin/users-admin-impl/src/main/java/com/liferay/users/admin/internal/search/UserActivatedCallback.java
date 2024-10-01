/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.internal.search;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.search.index.UpdateDocumentIndexWriter;
import com.liferay.portal.search.model.uid.UIDFactory;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = "key=com.liferay.portal.kernel.model.User#activated",
	service = Indexable.Callback.class
)
public class UserActivatedCallback implements Indexable.Callback {

	@Override
	public void reindex(BaseModel<?> baseModel) {
		if (!(baseModel instanceof User)) {
			return;
		}

		User user = (User)baseModel;

		if (Objects.equals(user.getLoginDate(), user.getLastLoginDate())) {
			Document document = new DocumentImpl();

			document.addKeyword(Field.UID, _uidFactory.getUID(user));
			document.addKeyword("activated", true);

			_updateDocumentIndexWriter.updateDocumentPartially(
				user.getCompanyId(), document, false);
		}
	}

	@Reference
	private UIDFactory _uidFactory;

	@Reference
	private UpdateDocumentIndexWriter _updateDocumentIndexWriter;

}