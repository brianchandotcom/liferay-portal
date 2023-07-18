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

package com.liferay.osb.faro.web.internal.application;

import com.liferay.osb.faro.web.internal.constants.FaroConstants;
import com.liferay.osb.faro.web.internal.controller.main.BlockedKeywordsController;
import com.liferay.osb.faro.web.internal.controller.main.ChannelController;
import com.liferay.osb.faro.web.internal.controller.main.DefinitionsController;
import com.liferay.osb.faro.web.internal.controller.main.IssueController;
import com.liferay.osb.faro.web.internal.controller.main.MainController;
import com.liferay.osb.faro.web.internal.controller.main.NotificationController;
import com.liferay.osb.faro.web.internal.controller.main.OAuth2Controller;
import com.liferay.osb.faro.web.internal.controller.main.PreferencesController;
import com.liferay.osb.faro.web.internal.controller.main.ProjectController;
import com.liferay.osb.faro.web.internal.controller.main.UserController;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Kong
 */
@ApplicationPath("/" + FaroConstants.APPLICATION_MAIN)
@Component(
	property = {
		"jaxrs.application=true",
		"osgi.http.whiteboard.filter.dispatcher=FORWARD",
		"osgi.http.whiteboard.filter.dispatcher=REQUEST"
	},
	service = Application.class
)
public class MainApplication extends BaseApplication {

	@Override
	public Set<Object> getControllers() {
		Set<Object> controllers = new HashSet<>();

		controllers.add(_blockedKeywordsController);
		controllers.add(_channelController);
		controllers.add(_definitionsController);
		controllers.add(_issueController);
		controllers.add(_mainController);
		controllers.add(_notificationController);
		controllers.add(_oAuth2Controller);
		controllers.add(_preferencesController);
		controllers.add(_projectController);
		controllers.add(_userController);

		return controllers;
	}

	@Reference
	private BlockedKeywordsController _blockedKeywordsController;

	@Reference
	private ChannelController _channelController;

	@Reference
	private DefinitionsController _definitionsController;

	@Reference
	private IssueController _issueController;

	@Reference
	private MainController _mainController;

	@Reference
	private NotificationController _notificationController;

	@Reference
	private OAuth2Controller _oAuth2Controller;

	@Reference
	private PreferencesController _preferencesController;

	@Reference
	private ProjectController _projectController;

	@Reference
	private UserController _userController;

}