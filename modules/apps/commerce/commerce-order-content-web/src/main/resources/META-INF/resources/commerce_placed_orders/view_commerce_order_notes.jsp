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

<%@ include file="/init.jsp" %>

<%
CommerceOrder commerceOrder = commerceOrderContentDisplayContext.getCommerceOrder();
%>

<div class="b2b-portlet-content-header">
	<div class="autofit-float autofit-row header-title-bar">
		<div class="autofit-col autofit-col-expand">
			<liferay-ui:header
				backURL="<%= redirect %>"
				title='<%= LanguageUtil.format(request, "order-x", commerceOrder.getCommerceOrderId()) %>'
			/>
		</div>
	</div>
</div>

<div class="taglib-discussion">

	<%
	Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);

	for (CommerceOrderNote commerceOrderNote : commerceOrderContentDisplayContext.getCommerceOrderNotes(commerceOrder)) {
	%>

		<article class="commerce-panel">
			<div class="commerce-panel__content">
				<div class="panel-body">
					<div class="card-row">
						<div class="card-col-content">
							<div class="lfr-discussion-details">
								<liferay-ui:user-portrait
									cssClass="sticker-lg"
									userId="<%= commerceOrderNote.getUserId() %>"
									userName="<%= commerceOrderNote.getUserName() %>"
								/>
							</div>

							<div class="lfr-discussion-body">
								<div class="lfr-discussion-message">
									<header class="lfr-discussion-message-author">

										<%
										User noteUser = commerceOrderNote.getUser();
										%>

										<aui:a cssClass="author-link" href="<%= ((noteUser != null) && noteUser.isActive()) ? noteUser.getDisplayURL(themeDisplay) : null %>">
											<%= HtmlUtil.escape(commerceOrderNote.getUserName()) %>

											<c:if test="<%= commerceOrderNote.getUserId() == user.getUserId() %>">
												(<liferay-ui:message key="you" />)
											</c:if>
										</aui:a>

										<c:if test="<%= commerceOrderNote.isRestricted() %>">
											<aui:icon image="lock" markupView="lexicon" message="private" />
										</c:if>

										<%
										Date createDate = commerceOrderNote.getCreateDate();
										%>

										<span class="small">
											<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />

											<c:if test="<%= createDate.before(commerceOrderNote.getModifiedDate()) %>">
												<strong onmouseover="Liferay.Portal.ToolTip.show(this, '<%= HtmlUtil.escapeJS(dateFormatDateTime.format(commerceOrderNote.getModifiedDate())) %>');">
													- <liferay-ui:message key="edited" />
												</strong>
											</c:if>
										</span>
									</header>

									<div class="lfr-discussion-message-body">
										<%= HtmlUtil.escape(commerceOrderNote.getContent()) %>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</article>

	<%
	}
	%>

</div>