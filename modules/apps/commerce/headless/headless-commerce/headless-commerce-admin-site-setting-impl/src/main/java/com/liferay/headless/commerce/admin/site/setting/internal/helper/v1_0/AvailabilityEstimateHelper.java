/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.site.setting.internal.helper.v1_0;

import com.liferay.commerce.exception.NoSuchAvailabilityEstimateException;
import com.liferay.commerce.model.CommerceAvailabilityEstimate;
import com.liferay.commerce.service.CommerceAvailabilityEstimateService;
import com.liferay.headless.commerce.admin.site.setting.dto.v1_0.AvailabilityEstimate;
import com.liferay.headless.commerce.admin.site.setting.internal.mapper.v1_0.DTOMapper;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Zoltán Takács
 */
@Component(service = AvailabilityEstimateHelper.class)
public class AvailabilityEstimateHelper {

	public AvailabilityEstimate addOrUpdateAvailabilityEstimate(
			Long groupId, AvailabilityEstimate availabilityEstimate, User user)
		throws PortalException {

		try {
			CommerceAvailabilityEstimate commerceAvailabilityEstimate =
				updateAvailabilityEstimate(
					availabilityEstimate.getId(), availabilityEstimate, user);

			return _dtoMapper.modelToDTO(commerceAvailabilityEstimate);
		}
		catch (NoSuchAvailabilityEstimateException
					noSuchAvailabilityEstimateException) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to find availabilityEstimate with ID: " +
						availabilityEstimate.getId(),
					noSuchAvailabilityEstimateException);
			}
		}

		CommerceAvailabilityEstimate commerceAvailabilityEstimate =
			_commerceAvailabilityEstimateService.
				addCommerceAvailabilityEstimate(
					LanguageUtils.getLocalizedMap(
						availabilityEstimate.getTitle()),
					GetterUtil.get(availabilityEstimate.getPriority(), 0D),
					_serviceContextHelper.getServiceContext(
						groupId, new long[0], user, true));

		return _dtoMapper.modelToDTO(commerceAvailabilityEstimate);
	}

	public void deleteAvailabilityEstimate(Long id) throws PortalException {
		_commerceAvailabilityEstimateService.deleteCommerceAvailabilityEstimate(
			id);
	}

	public AvailabilityEstimate getAvailabilityEstimate(Long id)
		throws PortalException {

		return _dtoMapper.modelToDTO(
			_commerceAvailabilityEstimateService.
				getCommerceAvailabilityEstimate(id));
	}

	public Page<AvailabilityEstimate> getAvailabilityEstimates(
			Long companyId, Pagination pagination)
		throws PortalException {

		List<CommerceAvailabilityEstimate> commerceAvailabilityEstimates =
			_commerceAvailabilityEstimateService.
				getCommerceAvailabilityEstimates(
					companyId, pagination.getStartPosition(),
					pagination.getEndPosition(), null);

		int count =
			_commerceAvailabilityEstimateService.
				getCommerceAvailabilityEstimatesCount(companyId);

		List<AvailabilityEstimate> availabilityEstimates = new ArrayList<>();

		for (CommerceAvailabilityEstimate commerceAvailabilityEstimate :
				commerceAvailabilityEstimates) {

			availabilityEstimates.add(
				_dtoMapper.modelToDTO(commerceAvailabilityEstimate));
		}

		return Page.of(availabilityEstimates, pagination, count);
	}

	public CommerceAvailabilityEstimate updateAvailabilityEstimate(
			Long id, AvailabilityEstimate availabilityEstimate, User user)
		throws PortalException {

		CommerceAvailabilityEstimate commerceAvailabilityEstimate =
			_commerceAvailabilityEstimateService.
				getCommerceAvailabilityEstimate(id);

		return _commerceAvailabilityEstimateService.
			updateCommerceAvailabilityEstimate(
				commerceAvailabilityEstimate.
					getCommerceAvailabilityEstimateId(),
				LanguageUtils.getLocalizedMap(availabilityEstimate.getTitle()),
				GetterUtil.get(
					availabilityEstimate.getPriority(),
					commerceAvailabilityEstimate.getPriority()),
				_serviceContextHelper.getServiceContext(
					0L, new long[0], user, true));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AvailabilityEstimateHelper.class);

	@Reference
	private CommerceAvailabilityEstimateService
		_commerceAvailabilityEstimateService;

	@Reference
	private DTOMapper _dtoMapper;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}