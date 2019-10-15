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

package com.liferay.oauth.service.impl;

import com.liferay.oauth.exception.OAuthApplicationCallbackURIException;
import com.liferay.oauth.exception.OAuthApplicationNameException;
import com.liferay.oauth.exception.OAuthApplicationWebsiteURLException;
import com.liferay.oauth.model.OAuthApplication;
import com.liferay.oauth.model.OAuthUser;
import com.liferay.oauth.service.base.OAuthApplicationLocalServiceBaseImpl;
import com.liferay.oauth.util.OAuthUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.InputStream;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
@Component(
	property = "model.class.name=com.liferay.oauth.model.OAuthApplication",
	service = AopService.class
)
public class OAuthApplicationLocalServiceImpl
	extends OAuthApplicationLocalServiceBaseImpl {

	@Override
	public OAuthApplication addOAuthApplication(
			long userId, String name, String description, int accessLevel,
			boolean shareableAccessToken, String callbackURI, String websiteURL,
			ServiceContext serviceContext)
		throws PortalException {

		// OAuth application

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		validate(name, callbackURI, websiteURL);

		long oAuthApplicationId = counterLocalService.increment();

		OAuthApplication oAuthApplication = oAuthApplicationPersistence.create(
			oAuthApplicationId);

		oAuthApplication.setCompanyId(user.getCompanyId());
		oAuthApplication.setUserId(user.getUserId());
		oAuthApplication.setUserName(user.getFullName());
		oAuthApplication.setCreateDate(serviceContext.getCreateDate(now));
		oAuthApplication.setModifiedDate(serviceContext.getModifiedDate(now));
		oAuthApplication.setName(name);
		oAuthApplication.setDescription(description);

		String consumerKey = serviceContext.getUuid();

		if (Validator.isNull(consumerKey)) {
			consumerKey = PortalUUIDUtil.generate();
		}

		oAuthApplication.setConsumerKey(consumerKey);

		oAuthApplication.setConsumerSecret(
			OAuthUtil.randomizeToken(consumerKey));
		oAuthApplication.setAccessLevel(accessLevel);
		oAuthApplication.setShareableAccessToken(shareableAccessToken);
		oAuthApplication.setCallbackURI(callbackURI);
		oAuthApplication.setWebsiteURL(websiteURL);

		oAuthApplicationPersistence.update(oAuthApplication);

		// Resources

		resourceLocalService.addModelResources(
			oAuthApplication, serviceContext);

		return oAuthApplication;
	}

	@Override
	public void deleteLogo(long oAuthApplicationId) throws PortalException {
		OAuthApplication oAuthApplication =
			oAuthApplicationPersistence.findByPrimaryKey(oAuthApplicationId);

		long logoId = oAuthApplication.getLogoId();

		if (logoId > 0) {
			oAuthApplication.setLogoId(0);

			oAuthApplicationPersistence.update(oAuthApplication);

			imageLocalService.deleteImage(logoId);
		}
	}

	@Override
	public OAuthApplication deleteOAuthApplication(long oAuthApplicationId)
		throws PortalException {

		OAuthApplication oAuthApplication =
			oAuthApplicationPersistence.findByPrimaryKey(oAuthApplicationId);

		return deleteOAuthApplication(oAuthApplication);
	}

	@Override
	public OAuthApplication deleteOAuthApplication(
			OAuthApplication oAuthApplication)
		throws PortalException {

		// OAuth application

		oAuthApplicationPersistence.remove(oAuthApplication);

		// OAuth users

		List<OAuthUser> oAuthUsers =
			oAuthUserPersistence.findByOAuthApplicationId(
				oAuthApplication.getOAuthApplicationId());

		for (OAuthUser oAuthUser : oAuthUsers) {
			oAuthUserPersistence.remove(oAuthUser);
		}

		// Resources

		resourceLocalService.deleteResource(
			oAuthApplication, ResourceConstants.SCOPE_INDIVIDUAL);

		// Image

		imageLocalService.deleteImage(oAuthApplication.getLogoId());

		return oAuthApplication;
	}

	@Override
	public OAuthApplication fetchOAuthApplication(String consumerKey) {
		return oAuthApplicationPersistence.fetchByConsumerKey(consumerKey);
	}

	@Override
	public OAuthApplication getOAuthApplication(String consumerKey)
		throws PortalException {

		return oAuthApplicationPersistence.findByConsumerKey(consumerKey);
	}

	@Override
	public List<OAuthApplication> getOAuthApplications(
		long companyId, int start, int end,
		OrderByComparator orderByComparator) {

		return oAuthApplicationPersistence.findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getOAuthApplicationsCount(long companyId) {
		return oAuthApplicationPersistence.countByCompanyId(companyId);
	}

	@Override
	public List<OAuthApplication> search(
		long companyId, String keywords, LinkedHashMap<String, Object> params,
		int start, int end, OrderByComparator orderByComparator) {

		keywords = _customSQL.keywords(keywords)[0];

		if ((params != null) && params.containsKey("userId")) {
			long userId = (Long)params.get("userId");

			if (Validator.isNotNull(keywords)) {
				return oAuthApplicationPersistence.findByU_N(
					userId, keywords, start, end, orderByComparator);
			}

			return oAuthApplicationPersistence.findByUserId(
				userId, start, end, orderByComparator);
		}

		if (Validator.isNotNull(keywords)) {
			return oAuthApplicationPersistence.findByC_N(
				companyId, keywords, start, end, orderByComparator);
		}

		return oAuthApplicationPersistence.findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int searchCount(
		long companyId, String keywords, LinkedHashMap<String, Object> params) {

		keywords = _customSQL.keywords(keywords)[0];

		if ((params != null) && params.containsKey("userId")) {
			long userId = (Long)params.get("userId");

			if (Validator.isNotNull(keywords)) {
				return oAuthApplicationPersistence.countByU_N(userId, keywords);
			}

			return oAuthApplicationPersistence.countByUserId(userId);
		}

		if (Validator.isNotNull(keywords)) {
			return oAuthApplicationPersistence.countByC_N(companyId, keywords);
		}

		return oAuthApplicationPersistence.countByCompanyId(companyId);
	}

	@Override
	public OAuthApplication updateLogo(
			long oAuthApplicationId, InputStream inputStream)
		throws PortalException {

		OAuthApplication oAuthApplication =
			oAuthApplicationPersistence.findByPrimaryKey(oAuthApplicationId);

		long logoId = oAuthApplication.getLogoId();

		if (logoId <= 0) {
			logoId = counterLocalService.increment();

			oAuthApplication.setLogoId(logoId);

			oAuthApplicationPersistence.update(oAuthApplication);
		}

		imageLocalService.updateImage(logoId, inputStream);

		return oAuthApplication;
	}

	@Override
	public OAuthApplication updateOAuthApplication(
			long oAuthApplicationId, String name, String description,
			boolean shareableAccessToken, String callbackURI, String websiteURL,
			ServiceContext serviceContext)
		throws PortalException {

		validate(name, callbackURI, websiteURL);

		OAuthApplication oAuthApplication =
			oAuthApplicationPersistence.findByPrimaryKey(oAuthApplicationId);

		oAuthApplication.setModifiedDate(serviceContext.getModifiedDate(null));
		oAuthApplication.setName(name);
		oAuthApplication.setDescription(description);
		oAuthApplication.setShareableAccessToken(shareableAccessToken);
		oAuthApplication.setCallbackURI(callbackURI);
		oAuthApplication.setWebsiteURL(websiteURL);

		oAuthApplicationPersistence.update(oAuthApplication);

		return oAuthApplication;
	}

	protected void validate(String name, String callbackURI, String websiteURL)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new OAuthApplicationNameException();
		}

		if (!Validator.isUri(callbackURI)) {
			throw new OAuthApplicationCallbackURIException();
		}

		if (!Validator.isUrl(websiteURL)) {
			throw new OAuthApplicationWebsiteURLException();
		}
	}

	@Reference
	private CustomSQL _customSQL;

}