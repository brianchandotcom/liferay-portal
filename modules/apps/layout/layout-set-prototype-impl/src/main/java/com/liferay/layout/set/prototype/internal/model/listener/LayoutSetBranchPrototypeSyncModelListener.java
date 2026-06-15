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
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(service = ModelListener.class)
public class LayoutSetBranchPrototypeSyncModelListener
	extends BaseModelListener<LayoutSetBranch> {

	@Override
	public void onAfterUpdate(
		LayoutSetBranch originalLayoutSetBranch,
		LayoutSetBranch layoutSetBranch) {

		if (!_isInitialLink(layoutSetBranch, originalLayoutSetBranch)) {
			return;
		}

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				try {
					LayoutSet layoutSet = _layoutSetLocalService.fetchLayoutSet(
						layoutSetBranch.getGroupId(),
						layoutSetBranch.isPrivateLayout());

					if (layoutSet == null) {
						return null;
					}

					_layoutSetPrototypeHelper.executeLayoutSetSync(layoutSet);
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
		LayoutSetBranch layoutSetBranch,
		LayoutSetBranch originalLayoutSetBranch) {

		if ((originalLayoutSetBranch == null) || (layoutSetBranch == null)) {
			return false;
		}

		if (Validator.isNull(
				originalLayoutSetBranch.getLayoutSetPrototypeUuid()) &&
			Validator.isNotNull(layoutSetBranch.getLayoutSetPrototypeUuid()) &&
			layoutSetBranch.isLayoutSetPrototypeLinkEnabled()) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetBranchPrototypeSyncModelListener.class);

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private LayoutSetPrototypeHelper _layoutSetPrototypeHelper;

}