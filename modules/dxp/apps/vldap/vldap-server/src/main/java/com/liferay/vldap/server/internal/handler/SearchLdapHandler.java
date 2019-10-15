/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.vldap.server.internal.handler;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.vldap.server.internal.directory.DirectoryTree;
import com.liferay.vldap.server.internal.directory.SearchBase;
import com.liferay.vldap.server.internal.directory.ldap.Directory;
import com.liferay.vldap.server.internal.handler.util.LdapHandlerContext;
import com.liferay.vldap.server.internal.util.PortletPropsValues;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.entry.Value;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.filter.AndNode;
import org.apache.directory.api.ldap.model.filter.BranchNode;
import org.apache.directory.api.ldap.model.filter.EqualityNode;
import org.apache.directory.api.ldap.model.filter.ExprNode;
import org.apache.directory.api.ldap.model.filter.GreaterEqNode;
import org.apache.directory.api.ldap.model.filter.LeafNode;
import org.apache.directory.api.ldap.model.filter.LessEqNode;
import org.apache.directory.api.ldap.model.filter.NotNode;
import org.apache.directory.api.ldap.model.filter.OrNode;
import org.apache.directory.api.ldap.model.filter.PresenceNode;
import org.apache.directory.api.ldap.model.filter.SubstringNode;
import org.apache.directory.api.ldap.model.message.Request;
import org.apache.directory.api.ldap.model.message.Response;
import org.apache.directory.api.ldap.model.message.ResultCodeEnum;
import org.apache.directory.api.ldap.model.message.SearchRequest;
import org.apache.directory.api.ldap.model.message.SearchResultEntry;
import org.apache.directory.api.ldap.model.message.SearchResultEntryImpl;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.mina.core.session.IoSession;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class SearchLdapHandler extends BaseLdapHandler {

	@Override
	public List<Response> messageReceived(
		Request request, IoSession ioSession,
		LdapHandlerContext ldapHandlerContext) {

		SearchRequest searchRequest = (SearchRequest)request;

		List<Response> responses = new ArrayList<>();

		try {
			addObjectEntries(searchRequest, responses);

			responses.add(getResultResponse(searchRequest));
		}
		catch (SearchSizeLimitException ssle) {
			responses.add(
				getResultResponse(
					searchRequest, ResultCodeEnum.SIZE_LIMIT_EXCEEDED));
		}
		catch (SearchTimeLimitException stle) {
			responses.add(
				getResultResponse(
					searchRequest, ResultCodeEnum.TIME_LIMIT_EXCEEDED));
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return responses;
	}

	protected void addObjectEntries(
			SearchRequest searchRequest, List<Response> responses)
		throws Exception {

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getSearchBase(
			searchRequest.getBase(), getSizeLimit(searchRequest));

		if (searchBase == null) {
			return;
		}

		Directory directory = searchBase.getDirectory();

		if (directory == null) {
			return;
		}

		searchBase.setSizeLimit(getSizeLimit(searchRequest));

		SearchScope searchScope = searchRequest.getScope();

		if ((searchScope.equals(SearchScope.OBJECT) ||
			 searchScope.equals(SearchScope.SUBTREE)) &&
			isMatch(directory, searchRequest.getFilter())) {

			StopWatch stopWatch = new StopWatch();

			stopWatch.start();

			addObjectEntry(searchRequest, responses, directory, stopWatch);

			searchBase.setSizeLimit(searchBase.getSizeLimit() - 1);
		}

		if (searchScope.equals(SearchScope.ONELEVEL) ||
			searchScope.equals(SearchScope.SUBTREE)) {

			StopWatch stopWatch = new StopWatch();

			stopWatch.start();

			List<Directory> subdirectories = directoryTree.getDirectories(
				searchBase, searchRequest.getFilter(), searchScope);

			for (Directory subdirectory : subdirectories) {
				addObjectEntry(
					searchRequest, responses, subdirectory, stopWatch);
			}
		}
	}

	protected void addObjectEntry(
			SearchRequest searchRequest, List<Response> responses,
			Directory directory, StopWatch stopWatch)
		throws LdapException {

		SearchResultEntry searchResponseEntry = new SearchResultEntryImpl(
			searchRequest.getMessageId());

		Entry entry = directory.toEntry(searchRequest.getAttributes());

		searchResponseEntry.setEntry(entry);

		if (responses.size() >= getSizeLimit(searchRequest)) {
			throw new SearchSizeLimitException();
		}

		if ((stopWatch.getTime() / Time.SECOND) > getTimeLimit(searchRequest)) {
			throw new SearchTimeLimitException();
		}

		responses.add(searchResponseEntry);
	}

	protected long getSizeLimit(SearchRequest searchRequest) {
		long sizeLimit = searchRequest.getSizeLimit();

		if ((sizeLimit == 0) ||
			(sizeLimit > PortletPropsValues.SEARCH_MAX_SIZE)) {

			sizeLimit = PortletPropsValues.SEARCH_MAX_SIZE;
		}

		return sizeLimit;
	}

	protected int getTimeLimit(SearchRequest searchRequest) {
		int timeLimit = searchRequest.getTimeLimit();

		if ((timeLimit == 0) ||
			(timeLimit > PortletPropsValues.SEARCH_MAX_TIME)) {

			timeLimit = PortletPropsValues.SEARCH_MAX_TIME;
		}

		return timeLimit;
	}

	protected boolean isMatch(Directory directory, ExprNode exprNode) {
		if (exprNode.isLeaf()) {
			LeafNode leafNode = (LeafNode)exprNode;

			return isMatchLeafNode(directory, leafNode);
		}

		BranchNode branchNode = (BranchNode)exprNode;

		return isMatchBranchNode(directory, branchNode);
	}

	protected boolean isMatchBranchNode(
		Directory directory, BranchNode branchNode) {

		if (branchNode instanceof AndNode) {
			for (ExprNode exprNode : branchNode.getChildren()) {
				if (!isMatch(directory, exprNode)) {
					return false;
				}
			}

			return true;
		}
		else if (branchNode instanceof NotNode) {
			for (ExprNode exprNode : branchNode.getChildren()) {
				if (!isMatch(directory, exprNode)) {
					return true;
				}

				return false;
			}

			return false;
		}
		else if (branchNode instanceof OrNode) {
			for (ExprNode exprNode : branchNode.getChildren()) {
				if (isMatch(directory, exprNode)) {
					return true;
				}
			}

			return false;
		}

		if (_log.isWarnEnabled()) {
			_log.warn("Unsupported expression " + branchNode);
		}

		return true;
	}

	protected boolean isMatchLeafNode(Directory directory, LeafNode leafNode) {
		if (leafNode instanceof EqualityNode<?>) {
			EqualityNode<?> equalityNode = (EqualityNode<?>)leafNode;

			String attributeId = equalityNode.getAttribute();
			Value value = equalityNode.getValue();

			if (directory.hasAttribute(attributeId, value.getString())) {
				return true;
			}

			return false;
		}
		else if (leafNode instanceof GreaterEqNode<?>) {
		}
		else if (leafNode instanceof LessEqNode<?>) {
		}
		else if (leafNode instanceof PresenceNode) {
			PresenceNode presenceNode = (PresenceNode)leafNode;

			String attributeId = presenceNode.getAttribute();

			if (directory.hasAttribute(attributeId)) {
				return true;
			}

			return false;
		}
		else if (leafNode instanceof SubstringNode) {
		}

		if (_log.isWarnEnabled()) {
			_log.warn("Unsupported expression " + leafNode);
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchLdapHandler.class);

}