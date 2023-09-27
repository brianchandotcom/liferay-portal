<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/modal_content/init.jsp" %>

	</div>

	<c:if test="<%= Validator.isNotNull(submitButtonLabel) || showCancelButton || showSubmitButton %>">
		<div class="modal-iframe-footer">
			<c:if test="<%= showCancelButton %>">
				<div class="btn btn-secondary ml-3 modal-closer"><liferay-ui:message key="cancel" /></div>
			</c:if>

			<c:if test="<%= showSubmitButton || Validator.isNotNull(submitButtonLabel) %>">
				<button class="btn btn-primary form-submitter ml-3" type="submit">
					<%= Validator.isNotNull(submitButtonLabel) ? HtmlUtil.escape(submitButtonLabel) : LanguageUtil.get(request, "submit") %>
				</button>
			</c:if>
		</div>
	</c:if>
</div>

<aui:script require="commerce-frontend-js/utilities/eventsDefinitions as events, frontend-js-web/index as frontendJsWeb">
	var {debounce} = frontendJsWeb;

	function closeModal(isSuccessful) {
		var eventDetail = {};

		if (isSuccessful) {
			eventDetail.successNotification = {
				message:
					'<%= LanguageUtil.get(request, "your-request-completed-successfully") %>',
				showSuccessNotification: true,
			};
		}

		<c:if test="<%= redirect != null %>">
			eventDetail.redirectURL = '<%= redirect %>';
		</c:if>

		window.top.Liferay.fire(events.CLOSE_MODAL, eventDetail);
	}

	window.addEventListener('keyup', (event) => {
		event.preventDefault();

		if (event.key === 'Escape') {
			closeModal(false);
		}
	});

	<c:choose>
		<c:when test='<%= SessionMessages.contains(renderRequest, "requestProcessed") %>'>
			closeModal(true);
		</c:when>
		<c:otherwise>
			window.top.Liferay.fire(events.IS_LOADING_MODAL, {isLoading: false});
		</c:otherwise>
	</c:choose>

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

		var debouncedAdjustBottomSpace = debounce(adjustBottomSpace, 300);

		adjustBottomSpace();

		window.addEventListener('resize', debouncedAdjustBottomSpace);
	}
</aui:script>