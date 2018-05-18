<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = commerceOrderEditDisplayContext.getCommerceOrder();
long commerceOrderId = commerceOrderEditDisplayContext.getCommerceOrderId();
%>

<portlet:actionURL name="editCommerceOrderNote" var="editCommerceOrderNoteURL">
	<portlet:param name="mvcRenderCommandName" value="editCommerceOrder" />
	<portlet:param name="screenNavigationCategoryKey" value="<%= CommerceOrderScreenNavigationConstants.CATEGORY_KEY_COMMERCE_ORDER_NOTES %>" />
</portlet:actionURL>

<aui:form action="<%= editCommerceOrderNoteURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrderId %>" />

	<liferay-ui:error exception="<%= CommerceOrderNoteContentException.class %>" message="please-enter-a-valid-content" />

	<aui:model-context model="<%= CommerceOrderNote.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<div class="taglib-discussion">
			<aui:fieldset cssClass="add-comment">
				<div class="panel">
					<div class="panel-body">
						<div class="lfr-discussion-details">
							<liferay-ui:user-portrait
								cssClass="user-icon-lg"
								user="<%= user %>"
							/>
						</div>

						<div class="lfr-discussion-body">
							<aui:input autoFocus="<%= true %>" label="" name="content" placeholder="type-your-note-here" />

							<aui:input helpMessage="restricted-help" label="private" name="restricted" />

							<aui:button-row>
								<aui:button cssClass="btn-large btn-primary" type="submit" />
							</aui:button-row>
						</div>
					</div>
				</div>
			</aui:fieldset>

			<%
			Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);

			for (CommerceOrderNote commerceOrderNote : commerceOrderEditDisplayContext.getCommerceOrderNotes()) {
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

												<c:if test="<%= commerceOrderNote.isRestricted() %>">
													<aui:icon image="lock" markupView="lexicon" message="private" />
												</c:if>

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

								<div class="card-col-field">
									<liferay-ui:icon-menu
										direction="left-side"
										icon="<%= StringPool.BLANK %>"
										markupView="lexicon"
										message="<%= StringPool.BLANK %>"
										showWhenSingleIcon="<%= true %>"
									>
										<portlet:renderURL var="editURL">
											<portlet:param name="mvcRenderCommandName" value="editCommerceOrderNote" />
											<portlet:param name="redirect" value="<%= currentURL %>" />
											<portlet:param name="commerceOrderNoteId" value="<%= String.valueOf(commerceOrderNote.getCommerceOrderNoteId()) %>" />
										</portlet:renderURL>

										<liferay-ui:icon
											message="edit"
											url="<%= editURL %>"
										/>

										<portlet:actionURL name="editCommerceOrderNote" var="deleteURL">
											<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
											<portlet:param name="redirect" value="<%= currentURL %>" />
											<portlet:param name="commerceOrderNoteId" value="<%= String.valueOf(commerceOrderNote.getCommerceOrderNoteId()) %>" />
										</portlet:actionURL>

										<liferay-ui:icon-delete
											label="<%= true %>"
											url="<%= deleteURL %>"
										/>
									</liferay-ui:icon-menu>
								</div>
							</div>
						</div>
					</div>
				</article>

			<%
			}
			%>

		</div>
	</aui:fieldset-group>
</aui:form>