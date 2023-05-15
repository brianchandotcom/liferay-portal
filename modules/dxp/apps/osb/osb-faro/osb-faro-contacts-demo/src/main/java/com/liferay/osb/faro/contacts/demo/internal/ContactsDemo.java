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

package com.liferay.osb.faro.contacts.demo.internal;

import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;

import java.util.concurrent.FutureTask;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shinn Lok
 */
@Component(service = {})
public class ContactsDemo {

	@Activate
	protected void activate() {
		if (Validator.isBlank(_FARO_DEMO_CREATOR_METHOD) ||
			StringUtil.equals(_FARO_DEMO_CREATOR_METHOD, "none")) {

			if (_log.isDebugEnabled()) {
				_log.debug("Skip demo data creation");
			}

			return;
		}

		_futureTask = new FutureTask<>(
			() -> {
				long startTime = System.currentTimeMillis();

				while ((System.currentTimeMillis() - startTime) <
							(Time.MINUTE * 5)) {

					try {
						FaroProject faroProject =
							_faroProjectLocalService.createFaroProject(0);

						faroProject.setWeDeployKey(_WE_DEPLOY_KEY);

						break;
					}
					catch (Exception exception) {
						_log.error(exception);

						Thread.sleep(Time.SECOND * 30);
					}
				}

				if (StringUtil.equals(_FARO_DEMO_CREATOR_METHOD, "nanite")) {
					_naniteDemoCreatorService.createDemo();
				}
				else {
					_snapshotDemoCreatorService.createDemo();
				}

				if (_log.isInfoEnabled()) {
					_log.info("Completed demo data creation");
				}

				return null;
			});

		Thread thread = new Thread(
			_futureTask, "Contacts Demo Creation Thread");

		thread.setDaemon(true);

		thread.start();
	}

	@Deactivate
	protected void deactivate() {
		if (_futureTask != null) {
			_futureTask.cancel(true);
		}
	}

	private static final String _FARO_DEMO_CREATOR_METHOD = System.getenv(
		"FARO_DEMO_CREATOR_METHOD");

	private static final String _WE_DEPLOY_KEY = System.getenv(
		"FARO_DEFAULT_WE_DEPLOY_KEY");

	private static final Log _log = LogFactoryUtil.getLog(ContactsDemo.class);

	@Reference
	private FaroProjectLocalService _faroProjectLocalService;

	private FutureTask<Void> _futureTask;

	@Reference
	private NaniteDemoCreatorService _naniteDemoCreatorService;

	@Reference(target = "(javax.portlet.name=faro_portlet)")
	private Portlet _portlet;

	@Reference
	private SnapshotDemoCreatorService _snapshotDemoCreatorService;

}