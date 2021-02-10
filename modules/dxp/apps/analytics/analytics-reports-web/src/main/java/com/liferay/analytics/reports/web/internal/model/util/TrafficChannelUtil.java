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

package com.liferay.analytics.reports.web.internal.model.util;

import com.liferay.analytics.reports.web.internal.model.AcquisitionChannel;
import com.liferay.analytics.reports.web.internal.model.CountrySearchKeywords;
import com.liferay.analytics.reports.web.internal.model.DirectTrafficChannelImpl;
import com.liferay.analytics.reports.web.internal.model.OrganicTrafficChannelImpl;
import com.liferay.analytics.reports.web.internal.model.PaidTrafficChannelImpl;
import com.liferay.analytics.reports.web.internal.model.ReferralTrafficChannelImpl;
import com.liferay.analytics.reports.web.internal.model.ReferringSocialMedia;
import com.liferay.analytics.reports.web.internal.model.ReferringURL;
import com.liferay.analytics.reports.web.internal.model.SocialTrafficChannelImpl;
import com.liferay.analytics.reports.web.internal.model.TrafficChannel;
import com.liferay.analytics.reports.web.internal.model.TrafficSource;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author David Arques
 */
public final class TrafficChannelUtil {

	public static JSONObject toJSONObject(
		boolean error, String helpMessage, String name, String title,
		long trafficAmount, double trafficShare) {

		JSONObject jsonObject = JSONUtil.put(
			"helpMessage", helpMessage
		).put(
			"name", name
		);

		if (!error) {
			jsonObject.put("share", String.format("%.1f", trafficShare));
		}

		jsonObject.put("title", title);

		if (!error) {
			jsonObject.put("value", Math.toIntExact(trafficAmount));
		}

		return jsonObject;
	}

	public static TrafficChannel toTrafficChannel(
		AcquisitionChannel acquisitionChannel,
		List<ReferringURL> domainReferringURLs,
		List<ReferringURL> pageReferringURLs,
		List<ReferringSocialMedia> referringSocialMediaList,
		Map<String, TrafficSource> trafficSourceMap) {

		if (Objects.equals("direct", acquisitionChannel.getName())) {
			return new DirectTrafficChannelImpl(
				acquisitionChannel.getTrafficAmount(),
				acquisitionChannel.getTrafficShare());
		}
		else if (Objects.equals("organic", acquisitionChannel.getName())) {
			return new OrganicTrafficChannelImpl(
				_getCountrySearchKeywords(
					trafficSourceMap, acquisitionChannel.getName()),
				acquisitionChannel.getTrafficAmount(),
				acquisitionChannel.getTrafficShare());
		}
		else if (Objects.equals("paid", acquisitionChannel.getName())) {
			return new PaidTrafficChannelImpl(
				_getCountrySearchKeywords(
					trafficSourceMap, acquisitionChannel.getName()),
				acquisitionChannel.getTrafficAmount(),
				acquisitionChannel.getTrafficShare());
		}
		else if (Objects.equals("referral", acquisitionChannel.getName())) {
			return new ReferralTrafficChannelImpl(
				domainReferringURLs, pageReferringURLs,
				acquisitionChannel.getTrafficAmount(),
				acquisitionChannel.getTrafficShare());
		}
		else if (Objects.equals("social", acquisitionChannel.getName())) {
			return new SocialTrafficChannelImpl(
				referringSocialMediaList, acquisitionChannel.getTrafficAmount(),
				acquisitionChannel.getTrafficShare());
		}

		throw new IllegalArgumentException(
			"Invalid acquisition channel name " + acquisitionChannel.getName());
	}

	private static List<CountrySearchKeywords> _getCountrySearchKeywords(
		Map<String, TrafficSource> trafficSourceMap, String name) {

		return Optional.ofNullable(
			trafficSourceMap.get(name)
		).map(
			TrafficSource::getCountrySearchKeywordsList
		).orElse(
			Collections.emptyList()
		);
	}

	private TrafficChannelUtil() {
	}

}