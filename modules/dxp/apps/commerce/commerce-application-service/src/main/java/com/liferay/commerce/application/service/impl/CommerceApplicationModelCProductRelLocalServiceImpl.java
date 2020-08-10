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

package com.liferay.commerce.application.service.impl;

import com.liferay.commerce.application.model.CommerceApplicationModelCProductRel;
import com.liferay.commerce.application.service.base.CommerceApplicationModelCProductRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import java.util.List;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class CommerceApplicationModelCProductRelLocalServiceImpl
	extends CommerceApplicationModelCProductRelLocalServiceBaseImpl {

	@Override
	public CommerceApplicationModelCProductRel
			addCommerceApplicationModelCProductRel(
				long userId, long commerceApplicationModelId, long cProductId)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long caModelCProductRelId = counterLocalService.increment();

		CommerceApplicationModelCProductRel caModelCProductRel =
			commerceApplicationModelCProductRelPersistence.create(
				caModelCProductRelId);

		caModelCProductRel.setCompanyId(user.getCompanyId());
		caModelCProductRel.setUserId(user.getUserId());
		caModelCProductRel.setUserName(user.getFullName());
		caModelCProductRel.setCommerceApplicationModelId(
			commerceApplicationModelId);
		caModelCProductRel.setCProductId(cProductId);

		return commerceApplicationModelCProductRelPersistence.update(
			caModelCProductRel);
	}

	@Override
	public void deleteCommerceApplicationModelCProductRels(
		long commerceApplicationModelId) {

		commerceApplicationModelCProductRelPersistence.
			removeByCommerceApplicationModelId(commerceApplicationModelId);
	}

	@Override
	public void deleteCommerceApplicationModelCProductRelsByCProductId(
		long cProductId) {

		commerceApplicationModelCProductRelPersistence.removeByCProductId(
			cProductId);
	}

	@Override
	public List<CommerceApplicationModelCProductRel>
		getCommerceApplicationModelCProductRels(
			long commerceApplicationModelId, int start, int end) {

		return commerceApplicationModelCProductRelPersistence.
			findByCommerceApplicationModelId(
				commerceApplicationModelId, start, end);
	}

	@Override
	public int getCommerceApplicationModelCProductRelsCount(
		long commerceApplicationModelId) {

		return commerceApplicationModelCProductRelPersistence.
			countByCommerceApplicationModelId(commerceApplicationModelId);
	}

}