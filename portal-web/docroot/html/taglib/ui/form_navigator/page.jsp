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

<%@ include file="/html/taglib/ui/form_navigator/init.jsp" %>

<div class="taglib-form-navigator" id="<portlet:namespace />tabsBoundingBox">
	<aui:input name="modifiedSections" type="hidden" />

	<c:choose>
		<c:when test='<%= displayStyle.equals("panel") %>'>
			<liferay-ui:panel-container accordion="<%= true %>" extended="<%= true %>" id="tabs" persistState="<%= true %>">
				<%@ include file="/html/taglib/ui/form_navigator/sections.jspf" %>
			</liferay-ui:panel-container>

			<aui:button-row>
				<aui:button cssClass="btn-primary" type="submit" />
			</aui:button-row>
		</c:when>
		<c:otherwise>

			<%
			String wrapperCssClass = StringPool.BLANK;

			if (displayStyle.equals("steps")) {
				wrapperCssClass = "form-steps";
			}
			%>

			<div class="<%= wrapperCssClass %>" id="<portlet:namespace />tabs">
				<liferay-util:buffer var="formNavigatorBottom">
					<c:if test="<%= showButtons %>">
						<aui:button-row>
							<aui:button primary="<%= true %>" type="submit" />

							<aui:button href="<%= backURL %>" type="cancel" />
						</aui:button-row>
					</c:if>

					<%= Validator.isNotNull(htmlBottom) ? htmlBottom : StringPool.BLANK %>
				</liferay-util:buffer>

				<liferay-util:buffer var="formSectionsBuffer">

					<%
					String contentCssClass = "form-navigator-content";

					if (!displayStyle.equals("steps")) {
						contentCssClass += " col-md-8 col-md-pull-4";
					}
					%>

					<div class="<%= contentCssClass %>">
						<%@ include file="/html/taglib/ui/form_navigator/sections.jspf" %>
					</div>
				</liferay-util:buffer>

				<%
				String listGroupCssClass = "form-navigator list-group nav";

				if (!displayStyle.equals("steps")) {
					listGroupCssClass += " col-md-4 col-md-push-8";
				}
				%>

				<ul class="<%= listGroupCssClass %>">
					<%= Validator.isNotNull(htmlTop) ? htmlTop : StringPool.BLANK %>

					<%
					String[] modifiedSections = StringUtil.split(ParamUtil.getString(request, "modifiedSections"));

					String errorSection = (String)request.getAttribute("errorSection");

					if (Validator.isNull(errorSection)) {
						modifiedSections = null;
					}

					boolean error = false;

					for (int i = 0; i < categoryLabels.length; i++) {
						String category = categoryLabels[i];
						String[] sectionKeys = categorySectionKeys[i];
						String[] sectionLabels = categorySectionLabels[i];

						if (sectionKeys.length > 0) {
					%>

							<c:if test="<%= Validator.isNotNull(category) %>">
								<li class="list-group-item nav-header "><liferay-ui:message key="<%= category %>" /></li>
							</c:if>

							<%
							if (Validator.isNotNull(errorSection)) {
								curSection = StringPool.BLANK;

								error = true;
							}

							int step = 1;

							for (int j = 0; j < sectionKeys.length; j++) {
								String sectionKey = sectionKeys[j];
								String sectionLabel = sectionLabels[j];

								String sectionId = namespace + _getSectionId(sectionKey);

								Boolean show = (Boolean)request.getAttribute(WebKeys.FORM_NAVIGATOR_SECTION_SHOW + sectionId);

								if ((show != null) && !show.booleanValue()) {
									continue;
								}

								String cssClass = "list-group-item tab";

								if (sectionId.equals(namespace + errorSection)) {
									cssClass += " section-error";

									curSection = sectionKey;
								}

								if (curSection.equals(sectionKey) || curSection.equals(sectionId)) {
									cssClass += " active";
								}

								if (ArrayUtil.contains(modifiedSections, sectionId)) {
									cssClass += " section-modified";
								}
							%>

								<li class="<%= cssClass %>" data-sectionId="<%= sectionId %>" id="<%= sectionId %>Tab">
									<a class="tab-label" href="#<%= sectionId %>" id="<%= sectionId %>Link">
										<span class="badge badge-important error-notice">!</span>

										<c:choose>
											<c:when test='<%= displayStyle.equals("steps") %>'>
												<span class="number"><liferay-ui:message key="<%= String.valueOf(step) %>" /></span>

												<span class="message"><liferay-ui:message key="<%= sectionLabel %>" /></span>

												<aui:icon cssClass="tab-icon" image="long-arrow-right" />
											</c:when>
											<c:otherwise>
												<liferay-ui:message key="<%= sectionLabel %>" />
											</c:otherwise>
										</c:choose>

										<span class="modified-notice"> (<liferay-ui:message key="modified" />) </span>
									</a>
								</li>

							<%
								step++;
							}
							%>

					<%
						}
					}
					%>

					<c:if test='<%= !displayStyle.equals("steps") %>'>
						<%= formNavigatorBottom %>
					</c:if>
				</ul>

				<%= formSectionsBuffer %>

				<c:if test='<%= displayStyle.equals("steps") %>'>
					<%= formNavigatorBottom %>
				</c:if>
			</div>

			<aui:script use="anim,aui-event-input,aui-tabview,aui-url,history,io-form,scrollview">
				var formNode = A.one('#<portlet:namespace /><%= formName %>');

				Liferay.component(
					'<portlet:namespace /><%= formName %>Tabview',
					function() {
						return new A.TabView(
							{
								boundingBox: '#<portlet:namespace />tabsBoundingBox',
								srcNode: '#<portlet:namespace />tabs',
								type: 'list'
							}
						).render();
					}
				);

				var tabview = Liferay.component('<portlet:namespace /><%= formName %>Tabview');

				<c:if test='<%= displayStyle.equals("steps") %>'>
					var listNode = tabview.get('listNode');

					var scrollAnim = new A.Anim(
						{
							duration: 0.3,
							node: listNode,
							to: {
								scrollLeft: function() {
									var activeTabNode = tabview.getActiveTab();

									var scrollLeft = listNode.get('scrollLeft');

									return activeTabNode.getX() + scrollLeft - listNode.getX();
								}
							}
						}
					);
				</c:if>

				function selectTabBySectionId(sectionId) {
					var instance = this;

					var tabNode = A.one('#' + sectionId + 'Tab');

					var tab = A.Widget.getByNode(tabNode);

					var tabIndex = tabview.indexOf(tab);

					if (tab && (tabIndex > -1)) {
						tabview.selectChild(tabIndex);
					}

					updateRedirectForSectionId(sectionId);

					<c:if test='<%= displayStyle.equals("steps") %>'>
						var listNodeRegion = listNode.get('region');

						if (tabNode && !tabNode.inRegion(listNodeRegion, true)) {
							scrollAnim.run();
						}
					</c:if>

					Liferay.fire('formNavigator:reveal' + sectionId);
				}

				function updateSectionError() {
					var tabNode = tabview.get('selection').get('boundingBox');

					var sectionId = tabNode.getData('sectionId');

					tabNode.toggleClass(
						'section-error',
						A.one('#' + sectionId).one('.error-field')
					);
				}

				function updateSectionStatus() {
					var tabNode = tabview.get('selection').get('boundingBox');

					var sectionId = tabNode.getData('sectionId');

					var modifiedSectionsNode = A.one('#<portlet:namespace/>modifiedSections');

					var modifiedSections = modifiedSectionsNode.val().split(',');

					modifiedSections.push(sectionId);
					modifiedSections = A.Array.dedupe(modifiedSections);
					modifiedSectionsNode.val(modifiedSections.join());

					tabNode.addClass('section-modified');
				}

				function updateRedirectForSectionId(sectionId) {
					var redirect = A.one('#<portlet:namespace />redirect');

					if (redirect) {
						var url = new A.Url(redirect.val() || '<%= portletURL %>');

						url.setAnchor(null);
						url.setParameter('<portlet:namespace />historyKey', sectionId);

						redirect.val(url.toString());
					}
				}

				var history = new A.HistoryHash();

				tabview.after(
					'selectionChange',
					function(event) {
						var tab = event.newVal;

						var boundingBox = tab.get('boundingBox');

						var sectionId = boundingBox.getData('sectionId');

						history.addValue('<portlet:namespace />tab', sectionId);
					}
				);

				A.on(
					'history:change',
					function(event) {
						var state = event.newVal;

						var changed = event.changed.<portlet:namespace />tab;

						var removed = event.removed.<portlet:namespace />tab;

						if (event.src === A.HistoryHash.SRC_HASH || event.src === A.HistoryBase.SRC_ADD) {
							if (changed) {
								selectTabBySectionId(changed.newVal);
							}
							else if (removed) {
								tabview.selectChild(0);
							}
							else if (state) {
								var sectionId = state.<portlet:namespace />tab;

								if (!sectionId) {
									sectionId = '<portlet:namespace />' + state.tab;
								}

								selectTabBySectionId(sectionId);
							}
						}
					}
				);

				if (<%= error %>) {
					history.addValue('<portlet:namespace />tab', '<portlet:namespace /><%= errorSection %>');
				}
				else {
					var currentUrl = new A.Url(location.href);

					var currentAnchor = currentUrl.getAnchor();

					if (!currentAnchor) {
						currentAnchor = currentUrl.getParameter('<portlet:namespace />historyKey');
					}

					if (currentAnchor) {
						var locationSectionId = currentAnchor.substring(currentAnchor.indexOf('=') + 1);

						if (locationSectionId.indexOf('<portlet:namespace />') === -1) {
							locationSectionId = '<portlet:namespace />' + locationSectionId;
						}

						selectTabBySectionId(locationSectionId);
					}
				}

				if (formNode) {

					<%
					String focusField = (String)request.getAttribute("liferay-ui:error:focusField");
					%>

					var focusField;

					<c:choose>
						<c:when test="<%= Validator.isNotNull(focusField) %>">
							focusField = formNode.one('#<portlet:namespace /><%= focusField %>');
						</c:when>
						<c:otherwise>
							focusField = formNode.one('.form-section.active input:not([type="hidden"]).field');
						</c:otherwise>
					</c:choose>

					if (focusField) {
						Liferay.Util.focusFormField(focusField);
					}

					formNode.all('.modify-link').on('click', updateSectionStatus);

					formNode.delegate('change', updateSectionStatus, 'input, select, textarea');

					formNode.on('blur', updateSectionError, 'input, select, textarea');

					formNode.on('autofields:update', updateSectionError);

					var updateSectionOnError = function(event) {
						var form = event.form;

						if (form.formNode.compareTo(formNode)) {
							var validator = form.formValidator;

							validator.on(
								'submitError',
								function() {
									var errorClass = validator.get('errorClass');

									var errorField = formNode.one('.' + errorClass);

									if (errorField) {
										var errorSection = errorField.ancestor('.form-section');

										var errorSectionId = errorSection.attr('id');

										selectTabBySectionId(errorSectionId);

										updateSectionError();
									}
								}
							);
						}
					};

					var detachUpdateSection = function(event) {
						if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
							Liferay.detach('form:registered', updateSectionOnError);

							Liferay.detach('destroyPortlet', detachUpdateSection);
						}
					};

					Liferay.after('form:registered', updateSectionOnError);

					Liferay.on('destroyPortlet', detachUpdateSection);
				}
			</aui:script>
		</c:otherwise>
	</c:choose>
</div>