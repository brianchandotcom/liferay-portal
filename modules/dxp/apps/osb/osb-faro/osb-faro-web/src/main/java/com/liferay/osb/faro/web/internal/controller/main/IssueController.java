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

package com.liferay.osb.faro.web.internal.controller.main;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.web.internal.controller.BaseFaroController;
import com.liferay.osb.faro.web.internal.controller.FaroController;
import com.liferay.osb.faro.web.internal.exception.FaroException;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;

import java.util.Date;

import javax.annotation.security.RolesAllowed;

import javax.mail.internet.InternetAddress;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Kong
 */
@Component(service = {FaroController.class, IssueController.class})
@Path("/{groupId}/issue")
@Produces(MediaType.APPLICATION_JSON)
public class IssueController extends BaseFaroController {

	@POST
	@RolesAllowed(RoleConstants.SITE_MEMBER)
	public void create(
			@PathParam("groupId") long groupId,
			@FormParam("currentURL") String currentURL,
			@FormParam("description") String description,
			@FormParam("title") String title)
		throws Exception {

		FaroProject faroProject =
			faroProjectLocalService.getFaroProjectByGroupId(groupId);

		if (!faroProject.isTrial()) {
			throw new FaroException("The workspace is not free tier");
		}

		Date submissionDate = new Date();
		User user = getUser();

		_mailService.sendEmail(
			new MailMessage(
				new InternetAddress(
					"actrial@liferay.com", "AC Trial Support Request"),
				new InternetAddress(_ISSUES_EMAIL_ADDRESS, null),
				faroProject.getName() + " - " + title,
				StringBundler.concat(
					"Account Name: ", faroProject.getAccountName(), "\n",
					"Current URL: ", currentURL, "\n", "Data Center Region: ",
					faroProject.getServerLocation(), "\n", "Issue Title: ",
					title, "\n", "Submission Date: ", submissionDate, "\n",
					"User Email: ", user.getEmailAddress(), "\n", "User Name: ",
					user.getFullName(), "\n", "Workspace Name: ",
					faroProject.getName(), "\n", "Description: ", description),
				false));
	}

	private static final String _ISSUES_EMAIL_ADDRESS = System.getenv(
		"ISSUES_EMAIL_ADDRESS");

	@Reference
	private MailService _mailService;

}