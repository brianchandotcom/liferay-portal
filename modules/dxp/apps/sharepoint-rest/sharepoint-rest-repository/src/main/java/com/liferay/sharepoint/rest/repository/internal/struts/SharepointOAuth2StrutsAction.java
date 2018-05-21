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

package com.liferay.sharepoint.rest.repository.internal.struts;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.repository.authorization.capability.AuthorizationCapability;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.sharepoint.rest.repository.internal.document.library.repository.authorization.oauth2.SharepointRepositoryRequestState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true, property = "path=/document_library/sharepoint/oauth2",
	service = StrutsAction.class
)
public class SharepointOAuth2StrutsAction extends BaseStrutsAction {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		Repository repository = _getRepository(request);

		if (repository.isCapabilityProvided(AuthorizationCapability.class)) {
			AuthorizationCapability authorizationCapability =
				repository.getCapability(AuthorizationCapability.class);

			authorizationCapability.authorize(request, response);
		}

		return null;
	}

	private Repository _getRepository(HttpServletRequest request)
		throws PortalException {

		SharepointRepositoryRequestState sharepointRepositoryRequestState =
			SharepointRepositoryRequestState.get(request);

		Folder folder = _dlAppLocalService.getFolder(
			sharepointRepositoryRequestState.getFolderId());

		long repositoryId = folder.getRepositoryId();

		return RepositoryProviderUtil.getRepository(repositoryId);
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

}