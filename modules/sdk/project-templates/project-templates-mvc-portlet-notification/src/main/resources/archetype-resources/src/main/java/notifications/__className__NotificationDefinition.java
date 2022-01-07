package ${package}.notifications;

import ${package}.constants.${className}PortletKeys;

import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationDeliveryType;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${author}
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + ${className}PortletKeys.${className.toUpperCase()},
	service = UserNotificationDefinition.class
)
public class ${className}NotificationDefinition extends UserNotificationDefinition {

	public  ${className}NotificationDefinition() {
		super(
			${className}PortletKeys.${className.toUpperCase()}, 0,
			${className}NotificationType.NOTIFICATION_TYPE_${className.toUpperCase()},
			"received-a-notification-from-${className.toLowerCase()}");

		addUserNotificationDeliveryType(new UserNotificationDeliveryType(
			"email", UserNotificationDeliveryConstants.TYPE_EMAIL, true,true));

		addUserNotificationDeliveryType(
			new UserNotificationDeliveryType(
			"website", UserNotificationDeliveryConstants.TYPE_WEBSITE, true,
			true));
	}

}