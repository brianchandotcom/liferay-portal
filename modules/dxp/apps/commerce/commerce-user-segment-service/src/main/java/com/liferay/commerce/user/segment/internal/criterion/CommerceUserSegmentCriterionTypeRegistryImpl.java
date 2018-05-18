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

package com.liferay.commerce.user.segment.internal.criterion;

import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionType;
import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionTypeRegistry;
import com.liferay.commerce.user.segment.internal.criterion.comparator.CommerceUserSegmentCriterionTypeOrderComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true)
public class CommerceUserSegmentCriterionTypeRegistryImpl
	implements CommerceUserSegmentCriterionTypeRegistry {

	@Override
	public CommerceUserSegmentCriterionType getCommerceUserSegmentCriterionType(
		String key) {

		ServiceWrapper<CommerceUserSegmentCriterionType>
			commerceUserSegmentCriterionTypeServiceWrapper =
				_serviceTrackerMap.getService(key);

		if (commerceUserSegmentCriterionTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No CommerceUserSegmentCriterionType registered with key " +
						key);
			}

			return null;
		}

		return commerceUserSegmentCriterionTypeServiceWrapper.getService();
	}

	@Override
	public List<CommerceUserSegmentCriterionType>
		getCommerceUserSegmentCriterionTypes() {

		List<CommerceUserSegmentCriterionType>
			commerceUserSegmentCriterionTypes = new ArrayList<>();

		List<ServiceWrapper<CommerceUserSegmentCriterionType>>
			commerceUserSegmentCriterionTypeServiceWrappers =
				ListUtil.fromCollection(_serviceTrackerMap.values());

		Collections.sort(
			commerceUserSegmentCriterionTypeServiceWrappers,
			_commerceUserSegmentCriterionTypeServiceWrapperOrderComparator);

		for (ServiceWrapper<CommerceUserSegmentCriterionType>
				commerceUserSegmentCriterionTypeServiceWrapper :
					commerceUserSegmentCriterionTypeServiceWrappers) {

			commerceUserSegmentCriterionTypes.add(
				commerceUserSegmentCriterionTypeServiceWrapper.getService());
		}

		return Collections.unmodifiableList(commerceUserSegmentCriterionTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceUserSegmentCriterionType.class,
			"commerce.user.segment.criterion.type.key",
			ServiceTrackerCustomizerFactory.
				<CommerceUserSegmentCriterionType>serviceWrapper(
					bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceUserSegmentCriterionTypeRegistryImpl.class);

	private final Comparator<ServiceWrapper<CommerceUserSegmentCriterionType>>
		_commerceUserSegmentCriterionTypeServiceWrapperOrderComparator =
			new CommerceUserSegmentCriterionTypeOrderComparator();
	private ServiceTrackerMap<String,
		ServiceWrapper<CommerceUserSegmentCriterionType>> _serviceTrackerMap;

}