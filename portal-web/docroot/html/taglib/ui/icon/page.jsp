<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/ui/icon/init.jsp" %>

<%
boolean urlIsNotNull = Validator.isNotNull(url);

String listItemAnchorAriaHasPopup = "false";
String listItemAnchorAriaRole = "menuitem";
String listItemAriaRole = "presentation";

if (!linkCssClass.contains("keep-aria-attributes") && (useDialog || (urlIsNotNull && url.startsWith("javascript:")))) {
	listItemAnchorAriaHasPopup = "dialog";
	listItemAnchorAriaRole = null;
	listItemAriaRole = "";
}
%>

<c:choose>
	<c:when test="<%= (iconListIconCount != null) && ((iconListSingleIcon == null) || iconListShowWhenSingleIcon) %>">
		<li class="<%= cssClass %>" role="<%= listItemAriaRole %>">
			<c:choose>
				<c:when test="<%= urlIsNotNull %>">
					<aui:a aria-haspopup="<%= listItemAnchorAriaHasPopup %>" ariaRole="<%= listItemAnchorAriaRole %>" cssClass="<%= linkCssClass %>" data="<%= data %>" href="<%= url %>" icon="<%= icon %>" id="<%= id %>" lang="<%= lang %>" onClick="<%= onClick %>" target="<%= target %>">
						<%@ include file="/html/taglib/ui/icon/link_content.jspf" %>
					</aui:a>
				</c:when>
				<c:otherwise>
					<%@ include file="/html/taglib/ui/icon/link_content.jspf" %>
				</c:otherwise>
			</c:choose>
		</li>
	</c:when>
	<c:when test="<%= (iconMenuIconCount != null) && ((iconMenuSingleIcon == null) || iconMenuShowWhenSingleIcon) %>">
		<li class="<%= cssClass %>" role="<%= listItemAriaRole %>">
			<c:choose>
				<c:when test="<%= urlIsNotNull %>">
					<aui:a aria-haspopup="<%= listItemAnchorAriaHasPopup %>" ariaRole="<%= listItemAnchorAriaRole %>" cssClass="<%= linkCssClass %>" data="<%= data %>" href="<%= url %>" icon="<%= icon %>" id="<%= id %>" lang="<%= lang %>" onClick="<%= onClick %>" target="<%= target %>">
						<%@ include file="/html/taglib/ui/icon/link_content.jspf" %>
					</aui:a>
				</c:when>
				<c:otherwise>
					<span class="taglib-icon">
						<%@ include file="/html/taglib/ui/icon/link_content.jspf" %>
					</span>
				</c:otherwise>
			</c:choose>
		</li>
	</c:when>
	<c:otherwise>
		<span
			class="<%= cssClass %>"

			<c:if test="<%= toolTip && !urlIsNotNull %>">
				tabindex="0"
			</c:if>

			<c:if test="<%= !label && Validator.isNotNull(message) %>">
				title="<%= HtmlUtil.escapeAttribute(LanguageUtil.get(resourceBundle, HtmlUtil.stripHtml(message))) %>"
			</c:if>
		>
			<c:choose>
				<c:when test="<%= urlIsNotNull %>">
					<aui:a ariaRole="<%= ariaRole %>" cssClass="<%= linkCssClass %>" data="<%= data %>" href="<%= url %>" icon="<%= icon %>" id="<%= id %>" lang="<%= lang %>" onClick="<%= onClick %>" target="<%= target %>">
						<%@ include file="/html/taglib/ui/icon/link_content.jspf" %>
					</aui:a>
				</c:when>
				<c:otherwise>
					<%@ include file="/html/taglib/ui/icon/link_content.jspf" %>
				</c:otherwise>
			</c:choose>
		</span>
	</c:otherwise>
</c:choose>

<c:if test="<%= Validator.isNotNull(srcHover) || forcePost || useDialog %>">
	<aui:script>
		const ICON_REGISTRY = {};
		let docClickHandler;
		let mouseOverEvent;
		let mouseOutEvent;

		function forcePost(event) {
			if (!Liferay.SPA || !Liferay.SPA.app) {
				const currentElement = Liferay.Util.getElement(
					event.currentTarget
				);

				if (currentElement) {
					const url = currentElement.getAttribute('href');

					// LPS-127302

					if (url === 'javascript:void(0);') {
						return;
					}

					const newWindow =
						currentElement.getAttribute('target') === '_blank';

					const hrefFm = document.hrefFm;

					if (newWindow) {
						hrefFm.setAttribute('target', '_blank');
					}

					submitForm(hrefFm, url, !newWindow);

					Liferay.Util._submitLocked = null;
				}

				event.preventDefault();
			}
		}

		function getConfig(event) {
			return ICON_REGISTRY[event.currentTarget.attr('id')];
		}

		function handleDocClick(event) {
			const config = getConfig(event);

			if (config) {
				event.preventDefault();

				if (config.useDialog) {
					_useDialog(event);
				}
				else {
					_forcePost(event);
				}
			}
		}

		function handleDocMouseOut(event) {
			const config = getConfig(event);

			if (config && config.srcHover) {
				_onMouseHover(event, config.src);
			}
		}

		function handleDocMouseOver(event) {
			const config = getConfig(event);

			if (config && config.srcHover) {
				_onMouseHover(event, config.srcHover);
			}
		}

		function onMouseHover(event, src) {
			const image = event.currentTarget.one('img');

			if (image) {
				image.attr('src', src);
			}
		}

		function useDialog(event) {
			Liferay.Util.openInDialog(event, {
				dialog: {
					destroyOnHide: true,
				},
				dialogIframe: {
					bodyCssClass: 'cadmin dialog-with-footer',
				},
			});
		}

		function register(config) {
			_ICON_REGISTRY[config.id] = config;

			if (!_docClickHandler) {
				_docClickHandler = document.getElementById('<portlet:namespace /><%= id %>').addEventListener(
					'click',
					_handleDocClick,
				);
			}

			if (!_mouseOverEvent) {
				_mouseOverEvent = document.getElementById('<portlet:namespace /><%= id %>').addEventListener(
					'mouseover',
					_handleDocMouseOver,
				);
			}

			if (!_mouseOutEvent) {
				_mouseOutEvent = document.getElementById('<portlet:namespace /><%= id %>').addEventListener(
					'mouseout',
					_handleDocMouseOut,
				);
			}

			Liferay.once('screenLoad', () => {
				delete ICON_REGISTRY[config.id];
			});
		}

		register(
			{
				forcePost: <%= forcePost %>,
				id: '<portlet:namespace /><%= id %>',

				<c:if test="<%= Validator.isNotNull(srcHover) %>">
					src: '<%= src %>',
					srcHover: '<%= srcHover %>',
				</c:if>

				useDialog: <%= useDialog %>
			}
		);
	</aui:script>
</c:if>