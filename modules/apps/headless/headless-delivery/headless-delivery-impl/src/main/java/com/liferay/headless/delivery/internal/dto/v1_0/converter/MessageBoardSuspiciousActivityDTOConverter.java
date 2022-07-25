package com.liferay.headless.delivery.internal.dto.v1_0.converter;

import com.liferay.headless.delivery.dto.v1_0.MessageBoardMessage;
import com.liferay.headless.delivery.dto.v1_0.MessageBoardSuspiciousActivity;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBSuspiciousActivity;
import com.liferay.message.boards.service.MBSuspiciousActivityService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
	property = "dto.class.name=com.liferay.message.boards.model.MBSuspiciousActivity",
	service = {DTOConverter.class, MessageBoardSuspiciousActivityDTOConverter.class}
)

public class MessageBoardSuspiciousActivityDTOConverter implements
	DTOConverter<MBSuspiciousActivity, MessageBoardSuspiciousActivity> {

	@Override
	public MessageBoardSuspiciousActivity toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		MBSuspiciousActivity mbSuspiciousActivity = _mbSuspiciousActivityService.getSuspiciousActivity(
			(Long)dtoConverterContext.getId());


		return new MessageBoardSuspiciousActivity(){
			{

				createDate = mbSuspiciousActivity.getCreateDate();
				description = mbSuspiciousActivity.getDescription();
				messageId = mbSuspiciousActivity.getMessageId();
				modifiedDate = mbSuspiciousActivity.getModifiedDate();
				suspiciousActivityId = mbSuspiciousActivity.getSuspiciousActivityId();
				threadId = mbSuspiciousActivity.getThreadId();
				type = mbSuspiciousActivity.getType();
				userId = mbSuspiciousActivity.getUserId();
				validated = mbSuspiciousActivity.getValidated();




			}

		};

	}

	@Override
	public String getContentType() {

		return MessageBoardSuspiciousActivity.class.getSimpleName();
	}

	@Reference
	private MBSuspiciousActivityService _mbSuspiciousActivityService;
}