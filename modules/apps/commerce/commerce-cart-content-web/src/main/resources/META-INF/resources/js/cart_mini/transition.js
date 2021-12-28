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

export default function ({namespace}) {
	Liferay.provide(
		window,
		`${namespace}transition`,
		(event) => {
			var link = event.currentTarget;

			var workflowTaskId = parseInt(link.getData('workflowTaskId'), 10);

			var form = document.getElementById(`${namespace}transitionFm`);

			A.one('#<portlet:namespace />transitionCommerceOrderId').val(
				link.getData('commerceOrderId')
			);
			A.one('#<portlet:namespace />workflowTaskId').val(workflowTaskId);
			A.one('#<portlet:namespace />transitionName').val(
				link.getData('transitionName')
			);

			if (workflowTaskId <= 0) {
				submitForm(form);

				return;
			}

			var transitionComments = A.one(
				'#<portlet:namespace />transitionComments'
			);

			transitionComments.show();

			var dialog = Liferay.Util.Window.getWindow({
				dialog: {
					bodyContent: form,
					destroyOnHide: true,
					height: 400,
					resizable: false,
					toolbars: {
						footer: [
							{
								cssClass: 'btn-primary mr-2',
								label: '<liferay-ui:message key="done" />',
								on: {
									click() {
										submitForm(form);
									},
								},
							},
							{
								cssClass: 'btn-cancel',
								label: '<liferay-ui:message key="cancel" />',
								on: {
									click() {
										dialog.hide();
									},
								},
							},
						],
						header: [
							{
								cssClass: 'close',
								discardDefaultButtonCssClasses: true,
								labelHTML:
									'<span aria-hidden="true">&times;</span>',
								on: {
									click(event) {
										dialog.hide();
									},
								},
							},
						],
					},
					width: 720,
				},
				title: link.text(),
			});
		},
		['aui-base', 'liferay-util-window']
	);
}
