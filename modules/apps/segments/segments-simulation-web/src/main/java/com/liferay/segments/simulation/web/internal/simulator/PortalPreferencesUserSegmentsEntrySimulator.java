/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.simulation.web.internal.simulator;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.simulator.SegmentsEntrySimulator;

import java.util.Arrays;
import java.util.ConcurrentModificationException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.model.User",
	service = SegmentsEntrySimulator.class
)
public class PortalPreferencesUserSegmentsEntrySimulator
	implements SegmentsEntrySimulator {

	@Override
	public void deactivateSimulation(long userId) {
		while (true) {
			try {
				PortalPreferences portalPreferences =
					_portletPreferencesFactory.getPortalPreferences(
						userId, true);

				portalPreferences.setValues(
					SegmentsPortletKeys.SEGMENTS_SIMULATION,
					"simulatedSegmentsEntryIds", null);
				portalPreferences.setValue(
					SegmentsPortletKeys.SEGMENTS_SIMULATION, "simulationActive",
					Boolean.FALSE.toString());

				break;
			}
			catch (ConcurrentModificationException
						concurrentModificationException) {

				if (_log.isDebugEnabled()) {
					_log.debug(concurrentModificationException);
				}
			}
			catch (Exception exception) {
				_log.error(exception);

				break;
			}
		}
	}

	@Override
	public long[] getSimulatedSegmentsEntryIds(long userId) {
		PortalPreferences portalPreferences =
			_portletPreferencesFactory.getPortalPreferences(userId, true);

		String[] simulatedSegmentsEntryIds = portalPreferences.getValues(
			SegmentsPortletKeys.SEGMENTS_SIMULATION,
			"simulatedSegmentsEntryIds");

		if (simulatedSegmentsEntryIds == null) {
			return new long[0];
		}

		return TransformUtil.transformToLongArray(
			Arrays.asList(simulatedSegmentsEntryIds), Long::parseLong);
	}

	@Override
	public boolean isSimulationActive(long userId) {
		PortalPreferences portalPreferences =
			_portletPreferencesFactory.getPortalPreferences(userId, true);

		return GetterUtil.getBoolean(
			portalPreferences.getValue(
				SegmentsPortletKeys.SEGMENTS_SIMULATION, "simulationActive"));
	}

	@Override
	public void setSimulatedSegmentsEntryIds(
		long userId, long[] segmentsEntryIds) {

		while (true) {
			try {
				PortalPreferences portalPreferences =
					_portletPreferencesFactory.getPortalPreferences(
						userId, true);

				portalPreferences.setValues(
					SegmentsPortletKeys.SEGMENTS_SIMULATION,
					"simulatedSegmentsEntryIds",
					ArrayUtil.toStringArray(segmentsEntryIds));
				portalPreferences.setValue(
					SegmentsPortletKeys.SEGMENTS_SIMULATION, "simulationActive",
					Boolean.TRUE.toString());

				break;
			}
			catch (ConcurrentModificationException
						concurrentModificationException) {

				if (_log.isDebugEnabled()) {
					_log.debug(concurrentModificationException);
				}
			}
			catch (Exception exception) {
				_log.error(exception);

				break;
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalPreferencesUserSegmentsEntrySimulator.class);

	@Reference
	private PortletPreferencesFactory _portletPreferencesFactory;

}