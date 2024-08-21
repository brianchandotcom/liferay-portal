<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/returns/init.jsp" %>

<%
CommerceReturnContentDisplayContext commerceReturnContentDisplayContext = (CommerceReturnContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceReturnItem commerceReturnItem = commerceReturnContentDisplayContext.getCommerceReturnItem();

boolean disabled = ParamUtil.getBoolean(request, "disabled");
%>

<liferay-frontend:side-panel-content
	title='<%= LanguageUtil.format(request, "edit-x", commerceReturnContentDisplayContext.getCommerceReturnItemId()) %>'
>
	<commerce-ui:panel
		title='<%= LanguageUtil.get(request, "details") %>'
	>
		<aui:form name="commerceReturnItemsFm" onSubmit="event.preventDefault();">
			<aui:input name="commerceReturnItemId" type="hidden" value="<%= commerceReturnContentDisplayContext.getCommerceReturnItemId() %>" />

			<div class="row">
				<div class="col">
					<aui:input disabled="<%= disabled %>" ignoreRequestValue="<%= true %>" name="quantity" type="text" value="<%= commerceReturnContentDisplayContext.getFormattedQuantity() %>">
						<aui:validator name="min">0</aui:validator>
						<aui:validator name="number" />
					</aui:input>
				</div>

				<div class="col">
					<aui:field-wrapper label='<%= LanguageUtil.get(resourceBundle, "return-reason") %>' name="returnReasonFieldWrapper">
						<div id="autocomplete-root"></div>
					</aui:field-wrapper>
				</div>
			</div>

			<div id="<portlet:namespace />commerceReturnItemComments">

				<%
				for (DiscussionComment discussionComment : commerceReturnContentDisplayContext.getDiscussionComments()) {
				%>

					<article class="card-tab-group lfr-discussion">
						<div class="border-bottom m-0 panel">
							<div class="panel-body px-0 py-4">
								<div class="row">
									<div class="col-auto">
										<liferay-user:user-portrait
											size="lg"
											userId="<%= discussionComment.getUserId() %>"
										/>
									</div>

									<div class="col">
										<header class="lfr-discussion-message-author">

											<%
											User discussionCommentUser = discussionComment.getUser();

											String label = HtmlUtil.escape(discussionComment.getUserName());

											if (discussionCommentUser.getUserId() == themeDisplay.getUserId()) {
												label = StringBundler.concat(label, StringPool.SPACE, StringPool.OPEN_PARENTHESIS, LanguageUtil.get(request, "you"), StringPool.CLOSE_PARENTHESIS);
											}
											%>

											<clay:link
												cssClass="author-link"
												href="<%= ((discussionCommentUser != null) && discussionCommentUser.isActive()) ? discussionCommentUser.getDisplayURL(themeDisplay) : null %>"
												label="<%= label %>"
											/>
										</header>

										<div class="lfr-discussion-message-body">
											<%= HtmlUtil.escape(discussionComment.getBody()) %>
										</div>
									</div>

									<div class="align-items-center col-auto d-flex">
										<span class="small">

											<%
											Date discussionCommentCreateDate = discussionComment.getCreateDate();
											%>

											<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - discussionCommentCreateDate.getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />

											<c:if test="<%= discussionCommentCreateDate.before(discussionComment.getModifiedDate()) %>">

												<%
												Format format = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
												%>

												<strong onmouseover="Liferay.Portal.ToolTip.show(this, '<%= HtmlUtil.escapeJS(format.format(discussionComment.getModifiedDate())) %>');">
													- <liferay-ui:message key="edited" />
												</strong>
											</c:if>
										</span>
									</div>

									<div class="align-items-center col-auto d-flex">
										<clay:dropdown-actions
											aria-label='<%= LanguageUtil.get(request, "edit-comment") %>'
											dropdownItems="<%= commerceReturnContentDisplayContext.getCommerceReturnItemCommentDropdownItemList(discussionComment) %>"
										/>
									</div>
								</div>
							</div>
						</div>
					</article>

				<%
				}
				%>

				<article class="card-tab-group lfr-discussion">
					<div class="panel">
						<div class="panel-body px-0 py-4">
							<div class="row">
								<div class="col-auto">
									<div class="lfr-discussion-details">
										<liferay-user:user-portrait
											size="lg"
											user="<%= user %>"
										/>
									</div>
								</div>

								<div class="col">
									<div class="lfr-discussion-body">
										<aui:input name="className" type="hidden" value="<%= commerceReturnContentDisplayContext.getCommerceReturnItemClassName() %>" />
										<aui:input name="classPK" type="hidden" value="<%= commerceReturnItem.getId() %>" />

										<aui:input label="" name="content" placeholder="type-your-comment-here" type="textarea" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</article>
			</div>

			<aui:button-row>
				<aui:button cssClass="btn-lg" type="submit" value="save" />

				<aui:button cssClass="btn-lg" type="cancel" />
			</aui:button-row>
		</aui:form>
	</commerce-ui:panel>
</liferay-frontend:side-panel-content>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"autocompleteAPIURL", commerceReturnContentDisplayContext.getListTypeEntriesByExternalReferenceCodeURL()
		).put(
			"autocompleteInitialLabel", commerceReturnContentDisplayContext.getReturnReasonName()
		).put(
			"autocompleteInitialValue", (commerceReturnItem == null) ? StringPool.BLANK : commerceReturnItem.getReturnReason()
		).put(
			"commerceReturnItemId", commerceReturnContentDisplayContext.getCommerceReturnItemId()
		).put(
			"dataSetId", CommerceOrderFDSNames.RETURN_ITEMS
		).put(
			"readOnly", disabled
		).build()
	%>'
	module="{editCommerceReturnItem} from commerce-order-content-web"
/>