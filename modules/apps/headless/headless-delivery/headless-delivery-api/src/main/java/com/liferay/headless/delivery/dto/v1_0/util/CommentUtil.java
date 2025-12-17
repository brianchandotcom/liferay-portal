/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.Comment;
import com.liferay.message.boards.exception.DiscussionMaxCommentsException;
import com.liferay.message.boards.exception.MessageSubjectException;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.DuplicateCommentException;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import jakarta.ws.rs.ClientErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class CommentUtil {

	public static Comment toComment(
			com.liferay.portal.kernel.comment.Comment comment,
			CommentManager commentManager, Portal portal)
		throws Exception {

		return _toComment(comment, commentManager, portal);
	}

	public static Comment toComment(
			UnsafeSupplier<com.liferay.portal.kernel.comment.Comment, Exception>
				addCommentUnsafeSupplier,
			CommentManager commentManager, Portal portal)
		throws Exception {

		try {
			return _toComment(
				addCommentUnsafeSupplier.get(), commentManager, portal);
		}
		catch (DiscussionMaxCommentsException discussionMaxCommentsException) {
			throw new ClientErrorException(
				"Maximum number of comments has been reached", 422,
				discussionMaxCommentsException);
		}
		catch (DuplicateCommentException duplicateCommentException) {
			throw new ClientErrorException(
				"A comment with the same text already exists", 409,
				duplicateCommentException);
		}
		catch (MessageSubjectException messageSubjectException) {
			throw new ClientErrorException(
				"Comment text is null", 422, messageSubjectException);
		}
	}

	public static List<com.liferay.portal.kernel.comment.Comment> toComments(
		String className, long classPK, CommentManager commentManager,
		Comment[] comments, long groupId, long userId) {

		Map<String, Long> toIdMap = new HashMap<>();

		return TransformUtil.transformToList(
			comments,
			comment -> {
				long parentCommentId = 0;

				String parentCommentExternalReferenceCode =
					comment.getParentCommentExternalReferenceCode();

				if (Validator.isNotNull(parentCommentExternalReferenceCode)) {
					parentCommentId = toIdMap.computeIfAbsent(
						parentCommentExternalReferenceCode,
						externalReferenceCode -> {
							com.liferay.portal.kernel.comment.Comment
								parentComment = commentManager.fetchComment(
									groupId, externalReferenceCode);

							if (parentComment == null) {
								return 0L;
							}

							return parentComment.getCommentId();
						});
				}

				return commentManager.createComment(
					0, comment.getExternalReferenceCode(), userId, groupId,
					className, classPK, parentCommentId, StringPool.BLANK,
					comment.getText());
			});
	}

	private static Comment _toComment(
		com.liferay.portal.kernel.comment.Comment comment,
		CommentManager commentManager, Portal portal) {

		if (comment == null) {
			return null;
		}

		return new Comment() {
			{
				setCreator(
					() -> CreatorUtil.toCreator(
						null, portal, comment.getUser()));
				setDateCreated(comment::getCreateDate);
				setDateModified(comment::getModifiedDate);
				setExternalReferenceCode(comment::getExternalReferenceCode);
				setId(comment::getCommentId);
				setNumberOfComments(
					() -> commentManager.getChildCommentsCount(
						comment.getCommentId(),
						WorkflowConstants.STATUS_APPROVED));
				setParentCommentExternalReferenceCode(
					() -> {
						com.liferay.portal.kernel.comment.Comment
							parentComment = commentManager.fetchComment(
								comment.getParentCommentId());

						if (parentComment != null) {
							return parentComment.getExternalReferenceCode();
						}

						return null;
					});
				setParentCommentId(comment::getParentCommentId);
				setText(comment::getBody);
			}
		};
	}

}