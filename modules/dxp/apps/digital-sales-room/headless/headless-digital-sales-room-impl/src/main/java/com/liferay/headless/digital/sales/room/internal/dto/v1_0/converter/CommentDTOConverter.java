/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.internal.dto.v1_0.converter;

import com.liferay.headless.digital.sales.room.dto.v1_0.Comment;
import com.liferay.object.rest.dto.v1_0.util.CreatorUtil;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(
	property = {
		"application.name=Liferay.Headless.Digital.Sales.Room",
		"dto.class.name=com.liferay.headless.digital.sales.room.dto.v1_0.Comment",
		"version=v1.0"
	},
	service = DTOConverter.class
)
public class CommentDTOConverter
	implements DTOConverter
		<com.liferay.portal.kernel.comment.Comment, Comment> {

	@Override
	public String getContentType() {
		return com.liferay.portal.kernel.comment.Comment.class.getSimpleName();
	}

	@Override
	public Comment toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.portal.kernel.comment.Comment serviceBuilderComment)
		throws Exception {

		return new Comment() {
			{
				setCreator(
					() -> CreatorUtil.toCreator(
						_portal, dtoConverterContext.getUriInfo(),
						serviceBuilderComment.getUser()));
				setDateCreated(serviceBuilderComment::getCreateDate);
				setDateModified(serviceBuilderComment::getModifiedDate);
				setExternalReferenceCode(
					serviceBuilderComment::getExternalReferenceCode);
				setId(serviceBuilderComment::getCommentId);
				setNumberOfComments(
					() -> _commentManager.getChildCommentsCount(
						serviceBuilderComment.getCommentId(),
						WorkflowConstants.STATUS_APPROVED));
				setParentCommentId(serviceBuilderComment::getParentCommentId);
				setText(serviceBuilderComment::getBody);
			}
		};
	}

	@Reference
	private CommentManager _commentManager;

	@Reference
	private Portal _portal;

}