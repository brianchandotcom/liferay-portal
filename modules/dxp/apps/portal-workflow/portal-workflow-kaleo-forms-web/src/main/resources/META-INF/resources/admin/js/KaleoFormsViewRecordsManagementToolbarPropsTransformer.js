/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {getCheckedCheckboxes, postForm} from 'frontend-js-web';

import openConfirm from './openConfirm';

export default function propsTransformer({
	additionalProps: {deleteDDLRecordURL},
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			if (item?.data?.action === 'deleteRecords') {
				openConfirm({
					message: Liferay.Language.get(
						'are-you-sure-you-want-to-delete-this'
					),
					onConfirm: (isConfirmed) => {
						if (isConfirmed) {
							const form = document.getElementById(
								`${portletNamespace}searchContainerForm`
							);

							const searchContainer = document.getElementById(
								otherProps.searchContainerId
							);

							if (form && searchContainer) {
								postForm(form, {
									data: {
										ddlRecordIds: getCheckedCheckboxes(
											searchContainer,
											`${portletNamespace}allRowIds`
										),
									},
									url: deleteDDLRecordURL,
								});
							}
						}
					},
				});
			}
		},
	};
}
