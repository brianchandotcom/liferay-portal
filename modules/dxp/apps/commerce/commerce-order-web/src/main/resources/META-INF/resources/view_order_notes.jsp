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
CommerceOrderDetailDisplayContext commerceOrderDetailDisplayContext = (CommerceOrderDetailDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.format(request, "order-x", commerceOrderDetailDisplayContext.getCommerceOrderId()));
%>

<div class="taglib-discussion">

	<%
	Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);

	for (CommerceOrderNote commerceOrderNote : commerceOrderDetailDisplayContext.getCommerceOrderNotes()) {
	%>

		<article class="card-tab-group lfr-discussion">
			<div class="card list-group-card panel">
				<div class="panel-body">
					<div class="card-row">
						<div class="card-col-content">
							<div class="lfr-discussion-details">
								<liferay-ui:user-portrait
									cssClass="user-icon-lg"
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

										<%
										Date createDate = commerceOrderNote.getCreateDate();

										String createDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
										%>

										<span class="small">
											<liferay-ui:message arguments="<%= createDateDescription %>" key="x-ago" translateArguments="<%= false %>" />

											<%
											Date modifiedDate = commerceOrderNote.getModifiedDate();
											%>

											<c:if test="<%= createDate.before(modifiedDate) %>">
												<strong onmouseover="Liferay.Portal.ToolTip.show(this, '<%= HtmlUtil.escapeJS(dateFormatDateTime.format(modifiedDate)) %>');">
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