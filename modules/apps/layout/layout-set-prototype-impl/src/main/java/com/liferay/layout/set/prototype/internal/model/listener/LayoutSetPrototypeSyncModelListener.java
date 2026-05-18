/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.set.prototype.internal.model.listener;

import com.liferay.layout.set.prototype.helper.LayoutSetPrototypeHelper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(service = ModelListener.class)
public class LayoutSetPrototypeSyncModelListener
	extends BaseModelListener<LayoutSet> {

	@Override
	public void onAfterUpdate(
		LayoutSet originalLayoutSet, LayoutSet layoutSet) {

		if (!_isInitialLink(originalLayoutSet, layoutSet)) {
			return;
		}

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				try {
					_layoutSetPrototypeHelper.executeLayoutSetSync(
						true, layoutSet);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to force sync from site template to site",
							exception);
					}
				}

				return null;
			});
	}

	private boolean _isInitialLink(
		LayoutSet originalLayoutSet, LayoutSet layoutSet) {

		if ((originalLayoutSet == null) || (layoutSet == null)) {
			return false;
		}

		if (Validator.isNull(originalLayoutSet.getLayoutSetPrototypeUuid()) &&
			Validator.isNotNull(layoutSet.getLayoutSetPrototypeUuid()) &&
			layoutSet.isLayoutSetPrototypeLinkEnabled()) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetPrototypeSyncModelListener.class);

	@Reference
	private LayoutSetPrototypeHelper _layoutSetPrototypeHelper;

}