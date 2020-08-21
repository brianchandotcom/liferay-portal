package com.liferay.dispatch.internal.messaging;

import com.liferay.dispatch.constants.DispatchConstants;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.dispatch.service.ScheduledTaskExecutorService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(
	immediate = true,
	property = "destination.name=" + DispatchConstants.EXECUTOR_DESTINATION_NAME,
	service = MessageListener.class
)
public class DispatchMessageListener implements MessageListener {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_scheduledTaskExecutorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ScheduledTaskExecutorService.class,
				"dispatch.executor.key");
	}

	protected ScheduledTaskExecutorService getScheduledTaskExecutorService(
			long dispatchTriggerId)
		throws PortalException {

		ScheduledTaskExecutorService scheduledTaskExecutorService = null;

		DispatchTrigger dispatchTrigger =
			_dispatchTriggerLocalService.getDispatchTrigger(dispatchTriggerId);

		if (_scheduledTaskExecutorServiceTrackerMap != null) {
			for (String key :
				_scheduledTaskExecutorServiceTrackerMap.keySet()) {

				if (key.equals(dispatchTrigger.getType())) {
					scheduledTaskExecutorService =
						_scheduledTaskExecutorServiceTrackerMap.getService(key);

					break;
				}
			}
		}

		return scheduledTaskExecutorService;
	}

	@Override
	public void receive(Message message) throws MessageListenerException {
		String payLoadString = (String)message.getPayload();

		JSONObject payLoad = null;

		try {
			payLoad = JSONFactoryUtil.createJSONObject(payLoadString);
		}
		catch (JSONException jsone) {
			_log.error(jsone, jsone);

			throw new MessageListenerException(jsone);
		}

		long dispatchTriggerId = payLoad.getLong("dispatchTriggerId");

		ScheduledTaskExecutorService scheduledTaskExecutorService = null;

		try {
			scheduledTaskExecutorService = getScheduledTaskExecutorService(
				dispatchTriggerId);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		if (scheduledTaskExecutorService != null) {
			try {
				scheduledTaskExecutorService.runProcess(dispatchTriggerId);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DispatchMessageListener.class);

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	private ServiceTrackerMap<String, ScheduledTaskExecutorService>
		_scheduledTaskExecutorServiceTrackerMap;


}