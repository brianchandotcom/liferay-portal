/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.model.listener;

import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.staging.model.listener.StagingModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(service = ModelListener.class)
public class KBTemplateStagingModelListener
	extends BaseModelListener<KBTemplate> {

	@Override
	public void onAfterCreate(KBTemplate kbTemplate)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(kbTemplate);
	}

	@Override
	public void onAfterRemove(KBTemplate kbTemplate)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(kbTemplate);
	}

	@Override
	public void onAfterUpdate(
			KBTemplate originalKBTemplate, KBTemplate kbTemplate)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(kbTemplate);
	}

	@Reference
	private StagingModelListener<KBTemplate> _stagingModelListener;

}