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

package com.liferay.change.tracking.rest.internal.resource;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.rest.internal.model.process.CTProcessModel;
import com.liferay.change.tracking.rest.internal.util.CTJaxRsUtil;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Máté Thurzó
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=change-tracking-application)",
		"osgi.jaxrs.resource=true"
	},
	scope = ServiceScope.PROTOTYPE, service = CTProcessResource.class
)
@Path("/processes")
public class CTProcessResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CTProcessModel> getCtProcessModels(
			@QueryParam("companyId") long companyId,
			@QueryParam("published") boolean published)
		throws PortalException {

		if (published) {
			CTProcessModel ctProcessModel = _getPublishedCTProcessModel(
				companyId);

			return Collections.singletonList(ctProcessModel);
		}

		List<CTProcess> ctProcesses = _ctProcessLocalService.getCTProcesses(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Stream<CTProcess> stream = ctProcesses.stream();

		return stream.filter(
			ctProcess -> ctProcess.getCompanyId() == companyId
		).map(
			this::_getCTProcessModel
		).collect(
			Collectors.toList()
		);
	}

	private CTProcessModel _getCTProcessModel(CTProcess ctProcess) {
		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctProcess.getCtCollectionId());

		User user = _userLocalService.fetchUser(ctProcess.getUserId());

		Optional<User> userOptional = Optional.ofNullable(user);

		CTProcessModel.Builder builder = CTProcessModel.forCompany(
			ctProcess.getCompanyId());

		return builder.setCTCollection(
			ctCollection
		).setDate(
			ctProcess.getCreateDate()
		).setUserInitials(
			userOptional.map(
				User::getInitials
			).orElse(
				StringPool.BLANK
			)
		).setUserName(
			userOptional.map(
				User::getFullName
			).orElse(
				StringPool.BLANK
			)
		).setUserPortraitURL(
			userOptional.map(
				this::_getPortraitURL
			).orElse(
				StringPool.BLANK
			)
		).build();
	}

	private String _getPortraitURL(User user) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay == null) {
			return StringPool.BLANK;
		}

		if (user != null) {
			return user.fetchPortraitURL(themeDisplay);
		}

		return UserConstants.getPortraitURL(
			themeDisplay.getPathImage(), true, 0, StringPool.BLANK);
	}

	private CTProcessModel _getPublishedCTProcessModel(long companyId)
		throws PortalException {

		CTJaxRsUtil.checkCompany(companyId);

		CTProcess ctProcess = _ctProcessLocalService.fetchLatestCTProcess(
			companyId);

		if (ctProcess == null) {
			return CTProcessModel.emptyCTProcessModel();
		}

		return _getCTProcessModel(ctProcess);
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTProcessLocalService _ctProcessLocalService;

	@Context
	private HttpServletRequest _httpServletRequest;

	@Reference
	private UserLocalService _userLocalService;

}