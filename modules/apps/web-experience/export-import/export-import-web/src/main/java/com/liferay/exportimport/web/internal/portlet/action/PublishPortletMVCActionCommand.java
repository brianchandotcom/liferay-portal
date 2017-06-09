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

package com.liferay.exportimport.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.exception.StructureDuplicateStructureKeyException;
import com.liferay.exportimport.constants.ExportImportPortletKeys;
import com.liferay.exportimport.kernel.exception.LARFileException;
import com.liferay.exportimport.kernel.exception.LARFileNameException;
import com.liferay.exportimport.kernel.exception.LARFileSizeException;
import com.liferay.exportimport.kernel.exception.LARTypeException;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortletIdException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ExportImportPortletKeys.EXPORT_IMPORT,
		"mvc.command.name=publishPortlet"
	},
	service = MVCActionCommand.class
)
public class PublishPortletMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = ActionUtil.getPortlet(actionRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(actionRequest, pe.getClass());

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");

			return;
		}

		actionRequest = ActionUtil.getWrappedActionRequest(actionRequest, null);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (Validator.isNull(cmd)) {
			SessionMessages.add(
				actionRequest,
				_portal.getPortletId(actionRequest) +
					SessionMessages.KEY_SUFFIX_FORCE_SEND_REDIRECT);

			hideDefaultSuccessMessage(actionRequest);

			return;
		}

		try {
			if (cmd.equals("copy_from_live")) {
				_staging.copyFromLive(actionRequest, portlet);
			}
			else if (cmd.equals(Constants.PUBLISH_TO_LIVE)) {
				hideDefaultSuccessMessage(actionRequest);

				_staging.publishToLive(actionRequest, portlet);
			}
		}
		catch (Exception e) {
			if (e instanceof LARFileException ||
				e instanceof LARFileNameException ||
				e instanceof LARFileSizeException ||
				e instanceof LARTypeException || e instanceof LocaleException ||
				e instanceof NoSuchLayoutException ||
				e instanceof PortletIdException ||
				e instanceof PrincipalException ||
				e instanceof StructureDuplicateStructureKeyException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				_log.error(e, e);

				SessionErrors.add(
					actionRequest,
					PublishPortletMVCActionCommand.class.getName());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PublishPortletMVCActionCommand.class);

	@Reference
	private Portal _portal;

	@Reference
	private Staging _staging;

}