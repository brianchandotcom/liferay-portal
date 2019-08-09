/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.fragment.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.concurrent.Callable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/move_fragment_entry"
	},
	service = MVCActionCommand.class
)
public class MoveFragmentEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long fragmentCollectionId = ParamUtil.getLong(
			actionRequest, "fragmentCollectionId");

		Callable<Void> callable = new MoveFragmentEntryCallable(actionRequest);

		try {
			TransactionInvokerUtil.invoke(_transactionConfig, callable);

			LiferayPortletResponse liferayPortletResponse =
				_portal.getLiferayPortletResponse(actionResponse);

			PortletURL redirectURL = liferayPortletResponse.createRenderURL();

			redirectURL.setParameter(
				"fragmentCollectionId", String.valueOf(fragmentCollectionId));

			sendRedirect(actionRequest, actionResponse, redirectURL.toString());
		}
		catch (Throwable t) {
			_log.error(t, t);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MoveFragmentEntryMVCActionCommand.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private FragmentEntryService _fragmentEntryService;

	@Reference
	private Portal _portal;

	private class MoveFragmentEntryCallable implements Callable<Void> {

		@Override
		public Void call() throws Exception {
			long[] fragmentEntryIds = StringUtil.split(
				ParamUtil.getString(_actionRequest, "fragmentEntryIds"), 0L);

			long fragmentCollectionId = ParamUtil.getLong(
				_actionRequest, "fragmentCollectionId");

			for (long fragmentEntryId : fragmentEntryIds) {
				_fragmentEntryService.moveFragmentEntry(
					fragmentEntryId, fragmentCollectionId);
			}

			return null;
		}

		private MoveFragmentEntryCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}