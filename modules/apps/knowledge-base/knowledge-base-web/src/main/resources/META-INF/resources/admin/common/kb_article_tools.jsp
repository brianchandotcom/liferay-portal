<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/admin/common/init.jsp" %>

<%
KBArticle kbArticle = (KBArticle)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);

int status = (Integer)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_STATUS);
%>

<div class="kb-article-tools">
	<c:if test="<%= kbGroupServiceConfiguration.sourceURLEnabled() && Validator.isUrl(kbArticle.getSourceURL()) %>">
		<a href="<%= HtmlUtil.escapeAttribute(kbArticle.getSourceURL()) %>" target="_blank">
			<clay:label
				cssClass="kb-article-source-url"
				displayType="success"
				label="<%= HtmlUtil.escape(kbGroupServiceConfiguration.sourceURLEditMessageKey()) %>"
			/>
		</a>
	</c:if>

	<c:if test="<%= enableRSS && (kbArticle.isApproved() || !kbArticle.isFirstVersion()) && !Objects.equals(portletDisplay.getRootPortletId(), KBPortletKeys.KNOWLEDGE_BASE_ADMIN) %>">
		<liferay-portlet:resourceURL id="kbArticleRSS" varImpl="kbArticleRSSURL">
			<portlet:param name="resourceClassNameId" value="<%= String.valueOf(kbArticle.getClassNameId()) %>" />
			<portlet:param name="resourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
		</liferay-portlet:resourceURL>

		<liferay-rss:rss
			delta="<%= rssDelta %>"
			displayStyle="<%= rssDisplayStyle %>"
			feedType="<%= rssFeedType %>"
			resourceURL="<%= kbArticleRSSURL %>"
		/>
	</c:if>

	<c:if test="<%= !rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ADMIN) %>">
		<c:if test="<%= enableKBArticleSubscriptions && (kbArticle.isApproved() || !kbArticle.isFirstVersion()) && KBArticlePermission.contains(permissionChecker, kbArticle, KBActionKeys.SUBSCRIBE) %>">
			<c:choose>
				<c:when test="<%= SubscriptionLocalServiceUtil.isSubscribed(user.getCompanyId(), user.getUserId(), KBArticle.class.getName(), kbArticle.getResourcePrimKey()) %>">
					<liferay-portlet:actionURL name="/knowledge_base/unsubscribe_kb_article" var="unsubscribeKBArticleURL">
						<portlet:param name="redirect" value="<%= redirect %>" />
						<portlet:param name="resourceClassNameId" value="<%= String.valueOf(kbArticle.getClassNameId()) %>" />
						<portlet:param name="resourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
					</liferay-portlet:actionURL>

					<liferay-ui:icon
						icon="times-circle"
						label="<%= true %>"
						markupView="lexicon"
						message="unsubscribe"
						url="<%= unsubscribeKBArticleURL %>"
					/>
				</c:when>
				<c:otherwise>
					<liferay-portlet:actionURL name="/knowledge_base/subscribe_kb_article" var="subscribeKBArticleURL">
						<portlet:param name="redirect" value="<%= redirect %>" />
						<portlet:param name="resourceClassNameId" value="<%= String.valueOf(kbArticle.getClassNameId()) %>" />
						<portlet:param name="resourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
					</liferay-portlet:actionURL>

					<liferay-ui:icon
						icon="check-circle-full"
						label="<%= true %>"
						markupView="lexicon"
						message="subscribe"
						url="<%= subscribeKBArticleURL %>"
					/>
				</c:otherwise>
			</c:choose>
		</c:if>

		<c:if test="<%= enableKBArticleHistory && (kbArticle.isApproved() || !kbArticle.isFirstVersion()) %>">
			<liferay-portlet:renderURL var="historyURL">
				<portlet:param name="mvcPath" value="/admin/common/kb_history.jsp" />
				<portlet:param name="redirect" value="<%= redirect %>" />
				<portlet:param name="resourceClassNameId" value="<%= String.valueOf(kbArticle.getClassNameId()) %>" />
				<portlet:param name="resourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
				<portlet:param name="status" value="<%= String.valueOf(status) %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:icon
				icon="document"
				label="<%= true %>"
				markupView="lexicon"
				message="history"
				url="<%= historyURL %>"
			/>
		</c:if>

		<c:if test="<%= enableKBArticlePrint %>">
			<liferay-portlet:renderURL var="printURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcPath" value="/admin/common/print_kb_article.jsp" />
				<portlet:param name="resourceClassNameId" value="<%= String.valueOf(kbArticle.getClassNameId()) %>" />
				<portlet:param name="resourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
				<portlet:param name="status" value="<%= String.valueOf(status) %>" />
			</liferay-portlet:renderURL>

			<%
			String taglibURL = "javascript:var printKBArticleWindow = window.open('" + printURL + "', 'printKBArticle', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680'); void(''); printKBArticleWindow.focus();";
			%>

			<liferay-ui:icon
				icon="print"
				label="<%= true %>"
				markupView="lexicon"
				message="print"
				url="<%= taglibURL %>"
			/>
		</c:if>

		<c:if test="<%= (!rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_DISPLAY) || DisplayPermission.contains(permissionChecker, scopeGroupId, KBActionKeys.ADMINISTRATOR)) && KBArticlePermission.contains(permissionChecker, kbArticle, KBActionKeys.UPDATE) %>">
			<liferay-ui:icon
				icon="pencil"
				label="<%= true %>"
				markupView="lexicon"
				message="edit"
				method="get"
				url='<%=
					PortletURLBuilder.create(
						PortalUtil.getControlPanelPortletURL(renderRequest, KBPortletKeys.KNOWLEDGE_BASE_ADMIN, PortletRequest.RENDER_PHASE)
					).setMVCPath(
						"/admin/common/edit_kb_article.jsp"
					).setRedirect(
						redirect
					).setParameter(
						"resourceClassNameId", kbArticle.getClassNameId()
					).setParameter(
						"resourcePrimKey", kbArticle.getResourcePrimKey()
					).setParameter(
						"status", WorkflowConstants.STATUS_ANY
					).buildString()
				%>'
			/>
		</c:if>

		<c:if test="<%= (AdminPermission.contains(permissionChecker, scopeGroupId, KBActionKeys.ADD_KB_ARTICLE) && rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ADMIN)) || (DisplayPermission.contains(permissionChecker, scopeGroupId, KBActionKeys.ADD_KB_ARTICLE) && DisplayPermission.contains(permissionChecker, scopeGroupId, KBActionKeys.ADMINISTRATOR) && rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_DISPLAY)) %>">
			<liferay-ui:icon
				icon="plus"
				label="<%= true %>"
				markupView="lexicon"
				message="add-child-article"
				method="get"
				url='<%=
					PortletURLBuilder.create(
						PortalUtil.getControlPanelPortletURL(renderRequest, KBPortletKeys.KNOWLEDGE_BASE_ADMIN, PortletRequest.RENDER_PHASE)
					).setMVCPath(
						"/admin/common/edit_kb_article.jsp"
					).setRedirect(
						redirect
					).setParameter(
						"parentResourceClassNameId", kbArticle.getClassNameId()
					).setParameter(
						"parentResourcePrimKey", kbArticle.getResourcePrimKey()
					).buildString()
				%>'
			/>
		</c:if>

		<c:if test="<%= kbArticle.isRoot() && KBArticlePermission.contains(permissionChecker, kbArticle, KBActionKeys.PERMISSIONS) %>">
			<liferay-security:permissionsURL
				modelResource="<%= KBArticle.class.getName() %>"
				modelResourceDescription="<%= kbArticle.getTitle() %>"
				resourcePrimKey="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>"
				var="permissionsURL"
				windowState="<%= LiferayWindowState.POP_UP.toString() %>"
			/>

			<liferay-ui:icon
				icon="lock"
				label="<%= true %>"
				markupView="lexicon"
				message="permissions"
				method="get"
				url="<%= permissionsURL %>"
				useDialog="<%= true %>"
			/>
		</c:if>

		<c:if test="<%= KBArticlePermission.contains(permissionChecker, kbArticle, KBActionKeys.MOVE_KB_ARTICLE) %>">
			<liferay-ui:icon
				icon="move"
				label="<%= true %>"
				markupView="lexicon"
				message="move"
				method="get"
				url='<%=
					PortletURLBuilder.create(
						PortalUtil.getControlPanelPortletURL(renderRequest, KBPortletKeys.KNOWLEDGE_BASE_ADMIN, PortletRequest.RENDER_PHASE)
					).setMVCPath(
						"/admin/common/move_object.jsp"
					).setRedirect(
						redirect
					).setParameter(
						"parentResourceClassNameId", kbArticle.getParentResourceClassNameId()
					).setParameter(
						"parentResourcePrimKey", kbArticle.getParentResourcePrimKey()
					).setParameter(
						"resourceClassNameId", kbArticle.getClassNameId()
					).setParameter(
						"resourcePrimKey", kbArticle.getResourcePrimKey()
					).buildString()
				%>'
			/>
		</c:if>

		<c:if test="<%= KBArticlePermission.contains(permissionChecker, kbArticle, KBActionKeys.DELETE) %>">
			<liferay-portlet:renderURL var="homeURL">
				<portlet:param name="mvcRenderCommandName" value="/knowledge_base/view" />
			</liferay-portlet:renderURL>

			<liferay-portlet:actionURL name="/knowledge_base/delete_kb_article" var="deleteURL">
				<portlet:param name="redirect" value="<%= homeURL %>" />
				<portlet:param name="resourceClassNameId" value="<%= String.valueOf(kbArticle.getClassNameId()) %>" />
				<portlet:param name="resourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
				<portlet:param name="status" value="<%= String.valueOf(status) %>" />
			</liferay-portlet:actionURL>

			<liferay-ui:icon-delete
				label="<%= true %>"
				showIcon="<%= true %>"
				url="<%= deleteURL %>"
			/>
		</c:if>

		<%
		KBDropdownItemsProvider kbDropdownItemsProvider = new KBDropdownItemsProvider(liferayPortletRequest, liferayPortletResponse, kbGroupServiceConfiguration);
		%>

		<clay:dropdown-actions
			dropdownItems="<%= kbDropdownItemsProvider.getKBArticleDropdownItems(kbArticle, enableKBArticleHistory, enableKBArticlePrint, enableKBArticleSubscriptions) %>"
			propsTransformer="admin/js/KBDropdownPropsTransformer"
		/>
	</c:if>
</div>