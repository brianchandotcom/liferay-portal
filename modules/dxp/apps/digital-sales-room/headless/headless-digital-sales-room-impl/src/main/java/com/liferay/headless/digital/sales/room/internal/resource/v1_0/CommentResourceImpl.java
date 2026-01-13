/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.internal.resource.v1_0;

import com.liferay.headless.digital.sales.room.dto.v1_0.Comment;
import com.liferay.headless.digital.sales.room.internal.odata.entity.v1_0.CommentEntityModel;
import com.liferay.headless.digital.sales.room.resource.v1_0.CommentResource;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.Discussion;
import com.liferay.portal.kernel.comment.DiscussionComment;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import jakarta.ws.rs.core.MultivaluedMap;

import java.util.function.Function;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Stefano Motta
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/comment.properties",
	scope = ServiceScope.PROTOTYPE, service = CommentResource.class
)
public class CommentResourceImpl extends BaseCommentResourceImpl {

	@Override
	public Page<Comment> getDigitalSalesRoomCommentsPage(
			Long digitalSalesRoomId, String search, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		Group group = _groupService.getGroup(digitalSalesRoomId);
		ObjectDefinition objectDefinition = _getObjectDefinition();

		ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
			group.getExternalReferenceCode(), group.getGroupId(),
			objectDefinition.getObjectDefinitionId());

		return SearchUtil.search(
			null,
			booleanQuery -> {
				Discussion discussion = _commentManager.getDiscussion(
					PrincipalThreadLocal.getUserId(), group.getGroupId(),
					objectDefinition.getClassName(),
					objectEntry.getObjectEntryId(),
					_createServiceContextFunction());

				DiscussionComment discussionComment =
					discussion.getRootDiscussionComment();

				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						"parentMessageId",
						String.valueOf(discussionComment.getCommentId())),
					BooleanClauseOccur.MUST);
			},
			null, MBMessage.class.getName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute("discussion", Boolean.TRUE);
				searchContext.setAttribute(
					"searchPermissionContext", StringPool.BLANK);
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setVulcanCheckPermissions(false);
			},
			sorts,
			document -> _toComment(
				_commentManager.fetchComment(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return new CommentEntityModel();
	}

	@Override
	public Comment postDigitalSalesRoomComment(
			Long digitalSalesRoomId, Comment comment)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		Group group = _groupService.getGroup(digitalSalesRoomId);
		ObjectDefinition objectDefinition = _getObjectDefinition();

		ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
			group.getExternalReferenceCode(), group.getGroupId(),
			objectDefinition.getObjectDefinitionId());

		return _toComment(
			_commentManager.fetchComment(
				_commentManager.addComment(
					StringPool.BLANK, contextUser.getUserId(),
					group.getGroupId(), objectDefinition.getClassName(),
					objectEntry.getObjectEntryId(), StringPool.BLANK,
					StringPool.BLANK, comment.getText(),
					_createServiceContextFunction())));
	}

	private Function<String, ServiceContext> _createServiceContextFunction() {
		return className -> {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

			return serviceContext;
		};
	}

	private ObjectDefinition _getObjectDefinition() throws Exception {
		return _objectDefinitionLocalService.
			getObjectDefinitionByExternalReferenceCode(
				"L_DSR_ROOM", contextCompany.getCompanyId());
	}

	private Comment _toComment(
			com.liferay.portal.kernel.comment.Comment serviceBuilderComment)
		throws Exception {

		return _commentDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), null,
				_dtoConverterRegistry, contextUser.getUserId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			serviceBuilderComment);
	}

	@Reference(
		target = "(component.name=com.liferay.headless.digital.sales.room.internal.dto.v1_0.converter.CommentDTOConverter)"
	)
	private DTOConverter<com.liferay.portal.kernel.comment.Comment, Comment>
		_commentDTOConverter;

	@Reference
	private CommentManager _commentManager;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private GroupService _groupService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}