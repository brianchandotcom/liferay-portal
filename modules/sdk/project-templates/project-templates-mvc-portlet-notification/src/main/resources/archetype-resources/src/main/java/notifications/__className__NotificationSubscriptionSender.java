package ${package}.notifications;

import ${package}.constants.${className}PortletKeys;
import ${package}.portlet.${className}Portlet;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.SubscriptionLocalServiceUtil;

import java.util.List;

/**
 * @author ${author}
 */
public class ${className}NotificationSubscriptionSender extends SubscriptionSender {

	public void setValue(String value) {
		_value = value;
	}

	public void setSender(String sender) {
		_sender = sender;
	}

	@Override
	protected void populateNotificationEventJSONObject(
		JSONObject notificationEventJSONObject) {

		super.populateNotificationEventJSONObject(notificationEventJSONObject);

		notificationEventJSONObject.put(
			${className}PortletKeys.SAMPLE_VALUE, _value);
		notificationEventJSONObject.put(
			${className}PortletKeys.SENDER, _sender);

	}


	@Override
	public void flushNotifications() throws Exception {
		List<Subscription> subscriptions =
			SubscriptionLocalServiceUtil.getSubscriptions(
				companyId, ${className}Portlet.class.getName(), 0);

		for (Subscription subscription : subscriptions) {
			try {
				User user = UserLocalServiceUtil.fetchUserById(
					subscription.getUserId());

				sendNotification(user);
			}
			catch (Exception exception) {
				_log.error(
					"Unable to process subscription: " + subscription,
					exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		${className}NotificationSubscriptionSender.class);

	private String _value;
	private String _sender;

}