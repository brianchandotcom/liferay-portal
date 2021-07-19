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

import {liferayNavigate} from 'commerce-frontend-js/utilities/index';
import {createPortletURL} from 'frontend-js-web';

export default function ({currentURL, namespace}) {
	const portletURL = createPortletURL(currentURL);
	const type = document.getElementById(`${namespace}type`);
	const handleSelectChange = () => {
		portletURL.searchParams.append('type', type.value);
		liferayNavigate(portletURL.toString());
	};

	type.addEventListener('change', handleSelectChange);

	return {
		dispose() {
			type.removeEventListener('change', handleSelectChange);
		},
	};
}
