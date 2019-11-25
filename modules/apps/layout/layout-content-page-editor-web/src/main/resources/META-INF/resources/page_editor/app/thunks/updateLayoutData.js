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

import {fetch} from 'frontend-js-web';

export default function updateLayoutData({
	classNameId,
	classPK,
	layoutData,
	portletNamespace,
	segmentsExperienceId,
	updateLayoutPageTemplateDataURL
}) {
	return _dispatch => {
		const formData = new FormData();

		formData.append(`${portletNamespace}classNameId`, classNameId);
		formData.append(`${portletNamespace}classPK`, classPK);
		formData.append(`${portletNamespace}data`, JSON.stringify(layoutData));
		formData.append(
			`${portletNamespace}segmentsExperienceId`,
			segmentsExperienceId
		);

		fetch(updateLayoutPageTemplateDataURL, {
			body: formData,
			method: 'POST'
		});
	};
}
