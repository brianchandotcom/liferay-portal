package ${package}.action;

import ${package}.constants.${className}PortletKeys;
import ${package}.notifications.${className}NotificationSubscriptionSender;
import ${package}.notifications.${className}NotificationType;
import ${package}.portlet.${className}Portlet;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.SubscriptionLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author ${author}
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ${className}PortletKeys.${className.toUpperCase()},
		"mvc.command.name=/send_notification"
	},
	service = MVCActionCommand.class
)
public class NotifyMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (Validator.isNull(cmd)) {
			return;
		}

		if (${className}PortletKeys.NOTIFY.equals(cmd)) {
			long companyId = _portal.getCompanyId(actionRequest);
			String email =  ParamUtil.getString(
				actionRequest, ${className}PortletKeys.USER_EMAIL);

			notifySubscriber(
				companyId, email,
				ServiceContextFactory.getInstance(actionRequest));
		}
	}

	protected void notifySubscriber(
		long companyId, String userEmail, ServiceContext serviceContext) {

		String entryTitle = "${className} notification";

		String fromName = "${className} Notification Sender";
		String fromAddress = "joebloggs@liferay.com";

		${className}NotificationSubscriptionSender subscriptionSender =
			new ${className}NotificationSubscriptionSender();

		subscriptionSender.setValue("sample value");

		User sender = _userLocalService.fetchUserById(
			serviceContext.getUserId());

		subscriptionSender.setSender(sender.getScreenName());

		subscriptionSender.setBody("${className} Notification Body Text");
		subscriptionSender.setClassPK(0);
		subscriptionSender.setClassName(${className}Portlet.class.getName());
		subscriptionSender.setCompanyId(companyId);
		subscriptionSender.setCurrentUserId(serviceContext.getUserId());
		subscriptionSender.setEntryTitle(entryTitle);
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setMailId("${className} Notification", 0);
		subscriptionSender.setNotificationType(
			${className}NotificationType.NOTIFICATION_TYPE_${className.toUpperCase()});
		subscriptionSender.setPortletId(
			${className}PortletKeys.${className.toUpperCase()});
		subscriptionSender.setReplyToAddress(fromAddress);
		subscriptionSender.setServiceContext(serviceContext);
		subscriptionSender.setSubject("${className} Notification Subject");

		subscriptionSender.addPersistedSubscribers(
			${className}Portlet.class.getName(), 0);

		subscriptionSender.flushNotificationsAsync();
	}

	@Reference(unbind = "-")
	protected void setSubscriptionLocalService(
		final SubscriptionLocalService subscriptionLocalService) {

		_subscriptionLocalService = subscriptionLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(
		final UserLocalService userLocalService) {

		_userLocalService = userLocalService;
	}

	@Reference
	private Portal _portal;

	private SubscriptionLocalService _subscriptionLocalService;
	private UserLocalService _userLocalService;

}