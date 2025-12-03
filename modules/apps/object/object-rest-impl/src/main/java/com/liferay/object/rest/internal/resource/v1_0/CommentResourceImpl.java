/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.resource.v1_0;

import com.liferay.headless.delivery.dto.v1_0.Comment;
import com.liferay.headless.delivery.dto.v1_0.Creator;
import com.liferay.headless.delivery.dto.v1_0.util.CommentUtil;
import com.liferay.headless.delivery.resource.v1_0.util.CommentResourceUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.internal.odata.entity.v1_0.provider.CommentEntityModel;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManagerProvider;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.Discussion;
import com.liferay.portal.kernel.comment.DiscussionComment;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MultivaluedMap;

import java.util.function.Function;

/**
 * @author Jhosseph Gonzalez
 */
public class CommentResourceImpl extends BaseCommentResourceImpl {

	public CommentResourceImpl(
		CommentManager commentManager,
		DiscussionPermission discussionPermission,
		DTOConverterRegistry dtoConverterRegistry,
		ObjectDefinition objectDefinition,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectEntryManagerRegistry objectEntryManagerRegistry) {

		_commentManager = commentManager;
		_discussionPermission = discussionPermission;
		_dtoConverterRegistry = dtoConverterRegistry;
		_objectDefinition = objectDefinition;
		_objectEntryLocalService = objectEntryLocalService;
		_objectEntryManagerRegistry = objectEntryManagerRegistry;
	}

	@Override
	public void deleteByExternalReferenceCodeComment(
			String externalReferenceCode, String commentExternalReferenceCode)
		throws Exception {

		if (!_objectDefinition.isEnableComments() ||
			!FeatureFlagManagerUtil.isEnabled(
				_objectDefinition.getCompanyId(), "LPD-69419")) {

			throw new UnsupportedOperationException();
		}

		ObjectEntry objectEntry = _getObjectEntry(externalReferenceCode, null);

		com.liferay.portal.kernel.comment.Comment serviceBuilderComment =
			_fetchComment(
				_objectDefinition.getClassName(), objectEntry.getId(),
				commentExternalReferenceCode,
				_getNonzeroGroupId(objectEntry.getId()));

		if (serviceBuilderComment == null) {
			throw new NotFoundException();
		}

		_deleteComment(serviceBuilderComment.getCommentId());
	}

	@Override
	public void deleteScopeScopeKeyByExternalReferenceCodeComment(
			String scopeKey, String externalReferenceCode,
			String commentExternalReferenceCode)
		throws Exception {

		if (!_objectDefinition.isEnableComments() ||
			!FeatureFlagManagerUtil.isEnabled(
				_objectDefinition.getCompanyId(), "LPD-69419")) {

			throw new UnsupportedOperationException();
		}

		ObjectEntry objectEntry = _getObjectEntry(
			externalReferenceCode, scopeKey);

		com.liferay.portal.kernel.comment.Comment serviceBuilderComment =
			_fetchComment(
				_objectDefinition.getClassName(), objectEntry.getId(),
				commentExternalReferenceCode, objectEntry.getScopeId());

		if (serviceBuilderComment == null) {
			throw new NotFoundException();
		}

		_deleteComment(serviceBuilderComment.getCommentId());
	}

	@Override
	public Comment getByExternalReferenceCodeComment(
			String externalReferenceCode, String commentExternalReferenceCode)
		throws Exception {

		if (!_objectDefinition.isEnableComments() ||
			!FeatureFlagManagerUtil.isEnabled(
				_objectDefinition.getCompanyId(), "LPD-69419")) {

			throw new UnsupportedOperationException();
		}

		ObjectEntry objectEntry = _getObjectEntry(externalReferenceCode, null);

		com.liferay.portal.kernel.comment.Comment serviceBuilderComment =
			_fetchComment(
				_objectDefinition.getClassName(), objectEntry.getId(),
				commentExternalReferenceCode,
				_getNonzeroGroupId(objectEntry.getId()));

		if (serviceBuilderComment == null) {
			throw new NoSuchModelException(
				StringBundler.concat(
					"No comment exists with object entry ERC ",
					externalReferenceCode, " and ERC ",
					commentExternalReferenceCode));
		}

		_discussionPermission.checkViewPermission(
			PermissionThreadLocal.getPermissionChecker(),
			contextCompany.getCompanyId(), serviceBuilderComment.getGroupId(),
			serviceBuilderComment.getClassName(),
			serviceBuilderComment.getClassPK());

		return CommentUtil.toComment(
			serviceBuilderComment, _commentManager, PortalUtil.getPortal());
	}

	@Override
	public Page<Comment> getByExternalReferenceCodeCommentChildCommentsPage(
			String externalReferenceCode, String commentExternalReferenceCode,
			String search, Aggregation aggregation, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		if (!_objectDefinition.isEnableComments() ||
			!FeatureFlagManagerUtil.isEnabled(
				_objectDefinition.getCompanyId(), "LPD-69419")) {

			throw new UnsupportedOperationException();
		}

		ObjectEntry objectEntry = _getObjectEntry(externalReferenceCode, null);

		com.liferay.portal.kernel.comment.Comment serviceBuilderComment =
			_fetchComment(
				_objectDefinition.getClassName(), objectEntry.getId(),
				commentExternalReferenceCode,
				_getNonzeroGroupId(objectEntry.getId()));

		if (serviceBuilderComment == null) {
			throw new NotFoundException();
		}

		return CommentResourceUtil.getComments(
			HashMapBuilder.put(
				"create",
				addAction(
					ActionKeys.ADD_DISCUSSION,
					"postByExternalReferenceCodeCommentChildComment",
					_objectDefinition.getClassName(), null)
			).put(
				"delete",
				addAction(
					ActionKeys.DELETE, "deleteByExternalReferenceCodeComment",
					_objectDefinition.getClassName(), null)
			).build(),
			serviceBuilderComment.getCommentId(), contextCompany.getCompanyId(),
			_commentManager, search, aggregation, filter, pagination,
			PortalUtil.getPortal(), sorts);
	}

	@Override
	public Page<Comment> getByExternalReferenceCodeCommentsPage(
			String externalReferenceCode, String search,
			Aggregation aggregation, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		if (!_objectDefinition.isEnableComments() ||
			!FeatureFlagManagerUtil.isEnabled(
				_objectDefinition.getCompanyId(), "LPD-69419")) {

			throw new UnsupportedOperationException();
		}

		ObjectEntry objectEntry = _getObjectEntry(externalReferenceCode, null);

		long groupId = _getNonzeroGroupId(objectEntry.getId());

		long objectEntryId = objectEntry.getId();

		Discussion discussion = _commentManager.getDiscussion(
			PrincipalThreadLocal.getUserId(), groupId,
			_objectDefinition.getClassName(), objectEntry.getId(),
			_createServiceContextFunction());

		DiscussionComment rootDiscussionComment =
			discussion.getRootDiscussionComment();

		Creator creator = objectEntry.getCreator();

		return CommentResourceUtil.getComments(
			HashMapBuilder.put(
				"add-discussion",
				addAction(
					ActionKeys.ADD_DISCUSSION, objectEntryId,
					"postByExternalReferenceCodeComment", creator.getId(),
					_objectDefinition.getClassName(), groupId)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, objectEntryId,
					"getByExternalReferenceCodeCommentsPage", creator.getId(),
					_objectDefinition.getClassName(), groupId)
			).build(),
			rootDiscussionComment.getCommentId(), contextCompany.getCompanyId(),
			_commentManager, search, aggregation, filter, pagination,
			PortalUtil.getPortal(), sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return new CommentEntityModel();
	}

	@Override
	public Comment getScopeScopeKeyByExternalReferenceCodeComment(
			String scopeKey, String externalReferenceCode,
			String commentExternalReferenceCode)
		throws Exception {

		if (!_objectDefinition.isEnableComments() ||
			!FeatureFlagManagerUtil.isEnabled(
				_objectDefinition.getCompanyId(), "LPD-69419")) {

			throw new UnsupportedOperationException();
		}

		ObjectEntry objectEntry = _getObjectEntry(
			externalReferenceCode, scopeKey);

		com.liferay.portal.kernel.comment.Comment serviceBuilderComment =
			_fetchComment(
				_objectDefinition.getClassName(), objectEntry.getId(),
				commentExternalReferenceCode, objectEntry.getScopeId());

		if (serviceBuilderComment == null) {
			throw new NoSuchModelException(
				StringBundler.concat(
					"No comment exists with scopeKey ", scopeKey,
					", object entry ERC ", externalReferenceCode, " and ERC ",
					commentExternalReferenceCode));
		}

		_discussionPermission.checkViewPermission(
			PermissionThreadLocal.getPermissionChecker(),
			contextCompany.getCompanyId(), serviceBuilderComment.getGroupId(),
			serviceBuilderComment.getClassName(),
			serviceBuilderComment.getClassPK());

		return CommentUtil.toComment(
			serviceBuilderComment, _commentManager, PortalUtil.getPortal());
	}

	@Override
	public Page<Comment>
			getScopeScopeKeyByExternalReferenceCodeCommentChildCommentsPage(
				String scopeKey, String externalReferenceCode,
				String commentExternalReferenceCode, String search,
				Aggregation aggregation, Filter filter, Pagination pagination,
				Sort[] sorts)
		throws Exception {

		if (!_objectDefinition.isEnableComments() ||
			!FeatureFlagManagerUtil.isEnabled(
				_objectDefinition.getCompanyId(), "LPD-69419")) {

			throw new UnsupportedOperationException();
		}

		ObjectEntry objectEntry = _getObjectEntry(
			externalReferenceCode, scopeKey);

		com.liferay.portal.kernel.comment.Comment serviceBuilderComment =
			_fetchComment(
				_objectDefinition.getClassName(), objectEntry.getId(),
				commentExternalReferenceCode, objectEntry.getScopeId());

		if (serviceBuilderComment == null) {
			throw new NotFoundException();
		}

		return CommentResourceUtil.getComments(
			HashMapBuilder.put(
				"create",
				addAction(
					ActionKeys.ADD_DISCUSSION,
					"postScopeScopeKeyByExternalReferenceCodeComment" +
						"ChildComment",
					_objectDefinition.getClassName(), null)
			).put(
				"delete",
				addAction(
					ActionKeys.DELETE, "deleteByExternalReferenceCodeComment",
					_objectDefinition.getClassName(), null)
			).build(),
			serviceBuilderComment.getCommentId(), contextCompany.getCompanyId(),
			_commentManager, search, aggregation, filter, pagination,
			PortalUtil.getPortal(), sorts);
	}

	@Override
	public Page<Comment> getScopeScopeKeyByExternalReferenceCodeCommentsPage(
			String scopeKey, String externalReferenceCode, String search,
			Aggregation aggregation, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		if (!_objectDefinition.isEnableComments() ||
			!FeatureFlagManagerUtil.isEnabled(
				_objectDefinition.getCompanyId(), "LPD-69419")) {

			throw new UnsupportedOperationException();
		}

		ObjectEntry objectEntry = _getObjectEntry(
			externalReferenceCode, scopeKey);

		long groupId = objectEntry.getScopeId();

		long objectEntryId = objectEntry.getId();

		Creator creator = objectEntry.getCreator();

		Discussion discussion = _commentManager.getDiscussion(
			PrincipalThreadLocal.getUserId(), groupId,
			_objectDefinition.getClassName(), objectEntry.getId(),
			_createServiceContextFunction());

		DiscussionComment rootDiscussionComment =
			discussion.getRootDiscussionComment();

		return CommentResourceUtil.getComments(
			HashMapBuilder.put(
				"add-discussion",
				addAction(
					ActionKeys.ADD_DISCUSSION, objectEntryId,
					"postScopeScopeKeyByExternalReferenceCodeComment",
					creator.getId(), _objectDefinition.getClassName(), groupId)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, objectEntryId,
					"getScopeScopeKeyByExternalReferenceCodeCommentsPage",
					creator.getId(), _objectDefinition.getClassName(), groupId)
			).build(),
			rootDiscussionComment.getCommentId(), contextCompany.getCompanyId(),
			_commentManager, search, aggregation, filter, pagination,
			PortalUtil.getPortal(), sorts);
	}

	@Override
	public Comment postByExternalReferenceCodeComment(
			String externalReferenceCode, Comment comment)
		throws Exception {

		if (!_objectDefinition.isEnableComments() ||
			!FeatureFlagManagerUtil.isEnabled(
				_objectDefinition.getCompanyId(), "LPD-69419")) {

			throw new UnsupportedOperationException();
		}

		ObjectEntry objectEntry = _getObjectEntry(externalReferenceCode, null);

		return _addComment(
			comment.getExternalReferenceCode(),
			_getNonzeroGroupId(objectEntry.getId()), null, objectEntry.getId(),
			comment.getText());
	}

	@Override
	public Comment postByExternalReferenceCodeCommentChildComment(
			String externalReferenceCode, String commentExternalReferenceCode,
			Comment comment)
		throws Exception {

		if (!_objectDefinition.isEnableComments() ||
			!FeatureFlagManagerUtil.isEnabled(
				_objectDefinition.getCompanyId(), "LPD-69419")) {

			throw new UnsupportedOperationException();
		}

		ObjectEntry objectEntry = _getObjectEntry(externalReferenceCode, null);

		com.liferay.portal.kernel.comment.Comment serviceBuilderComment =
			_fetchComment(
				_objectDefinition.getClassName(), objectEntry.getId(),
				commentExternalReferenceCode,
				_getNonzeroGroupId(objectEntry.getId()));

		if (serviceBuilderComment == null) {
			throw new NotFoundException();
		}

		return _addComment(
			comment.getExternalReferenceCode(),
			serviceBuilderComment.getGroupId(),
			serviceBuilderComment.getCommentId(), objectEntry.getId(),
			comment.getText());
	}

	@Override
	public Comment postScopeScopeKeyByExternalReferenceCodeComment(
			String scopeKey, String externalReferenceCode, Comment comment)
		throws Exception {

		if (!_objectDefinition.isEnableComments() ||
			!FeatureFlagManagerUtil.isEnabled(
				_objectDefinition.getCompanyId(), "LPD-69419")) {

			throw new UnsupportedOperationException();
		}

		ObjectEntry objectEntry = _getObjectEntry(
			externalReferenceCode, scopeKey);

		return _addComment(
			comment.getExternalReferenceCode(), objectEntry.getScopeId(), null,
			objectEntry.getId(), comment.getText());
	}

	@Override
	public Comment postScopeScopeKeyByExternalReferenceCodeCommentChildComment(
			String scopeKey, String externalReferenceCode,
			String commentExternalReferenceCode, Comment comment)
		throws Exception {

		if (!_objectDefinition.isEnableComments() ||
			!FeatureFlagManagerUtil.isEnabled(
				_objectDefinition.getCompanyId(), "LPD-69419")) {

			throw new UnsupportedOperationException();
		}

		ObjectEntry objectEntry = _getObjectEntry(
			externalReferenceCode, scopeKey);

		com.liferay.portal.kernel.comment.Comment serviceBuilderComment =
			_fetchComment(
				_objectDefinition.getClassName(), objectEntry.getId(),
				commentExternalReferenceCode, objectEntry.getScopeId());

		if (serviceBuilderComment == null) {
			throw new NotFoundException();
		}

		return _addComment(
			comment.getExternalReferenceCode(),
			serviceBuilderComment.getGroupId(),
			serviceBuilderComment.getCommentId(), objectEntry.getId(),
			comment.getText());
	}

	@Override
	public Comment putByExternalReferenceCodeComment(
			String externalReferenceCode, String commentExternalReferenceCode,
			Comment comment)
		throws Exception {

		if (!_objectDefinition.isEnableComments() ||
			!FeatureFlagManagerUtil.isEnabled(
				_objectDefinition.getCompanyId(), "LPD-69419")) {

			throw new UnsupportedOperationException();
		}

		ObjectEntry objectEntry = _getObjectEntry(externalReferenceCode, null);

		long groupId = _getNonzeroGroupId(objectEntry.getId());

		com.liferay.portal.kernel.comment.Comment serviceBuilderComment =
			_fetchComment(
				_objectDefinition.getClassName(), objectEntry.getId(),
				commentExternalReferenceCode, groupId);

		if (serviceBuilderComment != null) {
			return _updateComment(comment, serviceBuilderComment);
		}

		return _addComment(
			commentExternalReferenceCode, groupId, null, objectEntry.getId(),
			comment.getText());
	}

	@Override
	public Comment putScopeScopeKeyByExternalReferenceCodeComment(
			String scopeKey, String externalReferenceCode,
			String commentExternalReferenceCode, Comment comment)
		throws Exception {

		if (!_objectDefinition.isEnableComments() ||
			!FeatureFlagManagerUtil.isEnabled(
				_objectDefinition.getCompanyId(), "LPD-69419")) {

			throw new UnsupportedOperationException();
		}

		ObjectEntry objectEntry = _getObjectEntry(
			externalReferenceCode, scopeKey);

		com.liferay.portal.kernel.comment.Comment serviceBuilderComment =
			_fetchComment(
				_objectDefinition.getClassName(), objectEntry.getId(),
				commentExternalReferenceCode, objectEntry.getScopeId());

		if (serviceBuilderComment != null) {
			return _updateComment(comment, serviceBuilderComment);
		}

		return _addComment(
			commentExternalReferenceCode, objectEntry.getScopeId(), null,
			objectEntry.getId(), comment.getText());
	}

	private Comment _addComment(
			String externalReferenceCode, long groupId, Long parentCommentId,
			long objectEntryId, String text)
		throws Exception {

		_discussionPermission.checkAddPermission(
			PermissionThreadLocal.getPermissionChecker(),
			contextCompany.getCompanyId(), groupId,
			_objectDefinition.getClassName(), objectEntryId);

		if (parentCommentId != null) {
			return CommentUtil.toComment(
				() -> _commentManager.fetchComment(
					_commentManager.addComment(
						externalReferenceCode, PrincipalThreadLocal.getUserId(),
						_objectDefinition.getClassName(), objectEntryId,
						StringPool.BLANK, parentCommentId, StringPool.BLANK,
						StringBundler.concat("<p>", text, "</p>"),
						_createServiceContextFunction())),
				_commentManager, PortalUtil.getPortal());
		}

		return CommentUtil.toComment(
			() -> _commentManager.fetchComment(
				_commentManager.addComment(
					externalReferenceCode, PrincipalThreadLocal.getUserId(),
					groupId, _objectDefinition.getClassName(), objectEntryId,
					StringPool.BLANK, StringPool.BLANK,
					StringBundler.concat("<p>", text, "</p>"),
					_createServiceContextFunction())),
			_commentManager, PortalUtil.getPortal());
	}

	private Function<String, ServiceContext> _createServiceContextFunction() {
		return className -> {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

			return serviceContext;
		};
	}

	private void _deleteComment(Long commentId) throws Exception {
		_discussionPermission.checkDeletePermission(
			PermissionThreadLocal.getPermissionChecker(), commentId);

		_commentManager.deleteComment(commentId);
	}

	private com.liferay.portal.kernel.comment.Comment _fetchComment(
		String className, long classPK, String externalReferenceCode,
		long groupId) {

		com.liferay.portal.kernel.comment.Comment serviceBuilderComment =
			_commentManager.fetchComment(groupId, externalReferenceCode);

		if ((serviceBuilderComment != null) &&
			CommentResourceUtil.isAssociated(
				className, classPK, serviceBuilderComment)) {

			return serviceBuilderComment;
		}

		return null;
	}

	private DefaultDTOConverterContext _getDTOConverterContext(
		Long objectEntryId) {

		return new DefaultDTOConverterContext(
			contextAcceptLanguage.isAcceptAllLanguages(), null,
			_dtoConverterRegistry, contextHttpServletRequest, objectEntryId,
			contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
			contextUser);
	}

	private long _getNonzeroGroupId(long objectEntryId) throws Exception {
		com.liferay.object.model.ObjectEntry objectEntry =
			_objectEntryLocalService.getObjectEntry(objectEntryId);

		return objectEntry.getNonzeroGroupId();
	}

	private ObjectEntry _getObjectEntry(
			String objectEntryExternalReferenceCode, String scopeKey)
		throws Exception {

		DefaultObjectEntryManager defaultObjectEntryManager =
			DefaultObjectEntryManagerProvider.provide(
				_objectEntryManagerRegistry.getObjectEntryManager(
					_objectDefinition.getCompanyId(),
					_objectDefinition.getStorageType()));

		return defaultObjectEntryManager.getObjectEntry(
			contextCompany.getCompanyId(), _getDTOConverterContext(null),
			objectEntryExternalReferenceCode, _objectDefinition, scopeKey);
	}

	private Comment _updateComment(
			Comment comment,
			com.liferay.portal.kernel.comment.Comment serviceBuilderComment)
		throws Exception {

		_discussionPermission.checkUpdatePermission(
			PermissionThreadLocal.getPermissionChecker(),
			serviceBuilderComment.getCommentId());

		return CommentUtil.toComment(
			() -> _commentManager.fetchComment(
				_commentManager.updateComment(
					PrincipalThreadLocal.getUserId(),
					_objectDefinition.getClassName(),
					serviceBuilderComment.getClassPK(),
					serviceBuilderComment.getCommentId(), StringPool.BLANK,
					StringBundler.concat("<p>", comment.getText(), "</p>"),
					_createServiceContextFunction())),
			_commentManager, PortalUtil.getPortal());
	}

	private final CommentManager _commentManager;
	private final DiscussionPermission _discussionPermission;
	private final DTOConverterRegistry _dtoConverterRegistry;
	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectEntryManagerRegistry _objectEntryManagerRegistry;

}