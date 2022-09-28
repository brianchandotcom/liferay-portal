/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.federation.liferay.customer.portal.internal.portlet.action;

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.search.experiences.federation.ingestion.FederatorFactory;
import com.liferay.search.experiences.federation.liferay.customer.portal.internal.constants.BulkloaderPortletKeys;
import com.liferay.search.experiences.federation.liferay.customer.portal.internal.constants.ImportTypeKeys;
import com.liferay.search.experiences.federation.liferay.customer.portal.internal.constants.MVCActionCommandNames;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + BulkloaderPortletKeys.BULK_LOADER,
		"mvc.command.name=" + MVCActionCommandNames.IMPORT
	},
	service = MVCActionCommand.class
)
public class ImportMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String importType = ParamUtil.getString(actionRequest, "type");

		// Disable link validation

		ExportImportThreadLocal.setPortletImportInProcess(true);

		long timeMillis = System.currentTimeMillis();

		if (importType.equals(ImportTypeKeys.FEDERATED_CONTENT)) {
			_federatorFactory.builder(
			).build(
			).federate();
		}

		ExportImportThreadLocal.setPortletImportInProcess(false);

		if (_log.isInfoEnabled()) {
			_log.info("Finished data import in " + (timeMillis / 1000) + " s");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImportMVCActionCommand.class);

	@Reference
	private FederatorFactory _federatorFactory;

}