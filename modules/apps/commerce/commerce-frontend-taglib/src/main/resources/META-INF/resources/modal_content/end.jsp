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

<%@ include file="/modal_content/init.jsp" %>

	</div>

	<c:if test="<%= Validator.isNotNull(submitButtonLabel) || showCancelButton || showSubmitButton %>">
		<div class="modal-iframe-footer">
			<c:if test="<%= showCancelButton %>">
				<div class="btn btn-secondary ml-3 modal-closer"><%= LanguageUtil.get(request, "cancel") %></div>
			</c:if>

			<c:if test="<%= showSubmitButton || Validator.isNotNull(submitButtonLabel) %>">
				<button class="btn btn-primary form-submitter ml-3" type="submit">
					<%= Validator.isNotNull(submitButtonLabel) ? HtmlUtil.escape(submitButtonLabel) : LanguageUtil.get(request, "submit") %>
				</button>
			</c:if>
		</div>
	</c:if>
</div>

<aui:script require="commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/utilities/debounce as debounce">
	function closeModal(isSuccessful) {
		var eventDetail = {};

		if (isSuccessful) {
			eventDetail.successNotification = {
				message:
					'<%= LanguageUtil.get(request, "your-request-completed-successfully") %>',
				showSuccessNotification: true,
			};
		}

		window.top.Liferay.fire(events.CLOSE_MODAL, eventDetail);
	}

	window.addEventListener('keyup', (event) => {
		event.preventDefault();

		if (event.key === 'Escape') {
			closeModal(false);
		}
	});

	<c:if test='<%= SessionMessages.contains(renderRequest, "requestProcessed") %>'>
		closeModal(true);
	</c:if>

	window.top.Liferay.fire(events.IS_LOADING_MODAL, {isLoading: false});

	document.querySelectorAll('.modal-closer').forEach((trigger) => {
		trigger.addEventListener('click', (e) => {
			e.preventDefault();
			window.top.Liferay.fire(events.CLOSE_MODAL);
		});
	});

	var iframeContent = window.document.querySelector('.modal-iframe-content'),
		iframeFooter = window.document.querySelector('.modal-iframe-footer'),
		iframeForm = iframeContent.querySelector('form');

	if (iframeForm) {
		iframeForm.appendChild(iframeFooter);

		iframeForm.addEventListener('submit', (e) => {
			window.top.Liferay.fire(events.IS_LOADING_MODAL, {isLoading: true});

			var form = Liferay.Form.get(iframeForm.id);

			if (!form || !form.formValidator || !form.formValidator.validate) {
				e.preventDefault();
				return window.top.Liferay.fire(events.IS_LOADING_MODAL, {
					isLoading: false,
				});
			}

			form.formValidator.validate();

			if (form.formValidator.hasErrors()) {
				e.preventDefault();
				return window.top.Liferay.fire(events.IS_LOADING_MODAL, {
					isLoading: false,
				});
			}

			return;
		});
	}

	if (iframeContent && iframeFooter) {
		function adjustBottomSpace() {
			iframeContent.style.marginBottom = iframeFooter.offsetHeight + 'px';
		}

		var debouncedAdjustBottomSpace = debounce.default(adjustBottomSpace, 300);

		adjustBottomSpace();

		window.addEventListener('resize', debouncedAdjustBottomSpace);
	}
</aui:script>