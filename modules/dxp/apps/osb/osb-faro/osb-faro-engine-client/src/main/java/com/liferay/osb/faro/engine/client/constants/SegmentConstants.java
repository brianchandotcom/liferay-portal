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

package com.liferay.osb.faro.engine.client.constants;

import com.liferay.osb.faro.engine.client.model.IndividualSegment;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Shinn Lok
 */
public class SegmentConstants {

	public static Map<String, String> getSegmentStates() {
		return _segmentStates;
	}

	public static Map<String, String> getSegmentTypes() {
		return _segmentTypes;
	}

	private static final Map<String, String> _segmentStates =
		HashMapBuilder.put(
			"disabled", IndividualSegment.State.DISABLED.name()
		).put(
			"inProgress", IndividualSegment.State.IN_PROGRESS.name()
		).put(
			"ready", IndividualSegment.State.READY.name()
		).build();
	private static final Map<String, String> _segmentTypes = HashMapBuilder.put(
		"dynamic", IndividualSegment.Type.DYNAMIC.name()
	).put(
		"static", IndividualSegment.Type.STATIC.name()
	).build();

}