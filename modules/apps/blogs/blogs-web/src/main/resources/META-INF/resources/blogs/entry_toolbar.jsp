<div class="autofit-float autofit-row autofit-row-center widget-toolbar">
		<c:if test="<%= blogsPortletInstanceConfiguration.enableComments() %>">
			<div class="autofit-col">

				<%
				int messagesCount = CommentManagerUtil.getCommentsCount(BlogsEntry.class.getName(), entry.getEntryId());
				%>

				<portlet:renderURL var="viewEntryCommentsURL">
					<portlet:param name="mvcRenderCommandName" value="/blogs/view_entry" />
					<portlet:param name="scroll" value='<%= renderResponse.getNamespace() + "discussionContainer" %>' />

					<c:choose>
						<c:when test="<%= Validator.isNotNull(entry.getUrlTitle()) %>">
							<portlet:param name="urlTitle" value="<%= entry.getUrlTitle() %>" />
						</c:when>
						<c:otherwise>
							<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
						</c:otherwise>
					</c:choose>
				</portlet:renderURL>

				<liferay-util:whitespace-remover>
					<a class="btn btn-outline-borderless btn-outline-secondary btn-sm" href="<%= viewEntryCommentsURL.toString() %>">
						<span class="inline-item inline-item-before">
							<clay:icon
								symbol="comments"
							/>
						</span>

						<liferay-ui:message arguments="<%= messagesCount %>" key="comment-x" />
					</a>
				</liferay-util:whitespace-remover>
			</div>
		</c:if>

		<c:if test="<%= blogsPortletInstanceConfiguration.enableFlags() && blogsPortletInstanceConfiguration.displayStyle().equals(BlogsUtil.DISPLAY_STYLE_FULL_CONTENT) %>">
			<div class="autofit-col">
				<div class="flags">
					<liferay-flags:flags
						className="<%= BlogsEntry.class.getName() %>"
						classPK="<%= entry.getEntryId() %>"
						contentTitle="<%= BlogsEntryUtil.getDisplayTitle(resourceBundle, entry) %>"
						enabled="<%= !entry.isInTrash() %>"
						message='<%= entry.isInTrash() ? "flags-are-disabled-because-this-entry-is-in-the-recycle-bin" : StringPool.BLANK %>'
						reportedUserId="<%= entry.getUserId() %>"
					/>
				</div>
			</div>
		</c:if>

		<c:if test="<%= blogsPortletInstanceConfiguration.enableRatings() %>">
			<div class="autofit-col">
				<div class="ratings">
					<liferay-ui:ratings
						className="<%= BlogsEntry.class.getName() %>"
						classPK="<%= entry.getEntryId() %>"
						inTrash="<%= entry.isInTrash() %>"
						ratingsEntry="<%= ratingsEntry %>"
						ratingsStats="<%= ratingsStats %>"
					/>
				</div>
			</div>
		</c:if>

		<div class="autofit-col autofit-col-end">
			<%@ include file="/blogs/social_bookmarks.jspf" %>
		</div>
	</div>