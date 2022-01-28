/* eslint-disable no-undef */
/* eslint-disable @liferay/portal/no-global-fetch */
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

const contactEmail = fragmentElement.querySelector(
	'#customer-portal-liferay-contact-email'
);
const contactName = fragmentElement.querySelector(
	'#customer-portal-liferay-contact-name'
);
const contactRole = fragmentElement.querySelector(
	'#customer-portal-liferay-contact-role'
);
const contactTitle = fragmentElement.querySelector(
	'#customer-portal-liferay-contact-title'
);
const eventName = 'customer-portal-project-loading';

(async () => {
	try {
		window.addEventListener(eventName, ({detail: project}) => {
			contactEmail.classList.toggle('skeleton');
			contactName.classList.toggle('skeleton');
			contactRole.classList.toggle('skeleton');
			contactTitle.classList.toggle('skeleton');

			contactEmail.innerHTML = project.liferayContactEmailAddress;
			contactName.innerHTML = project.liferayContactName;
			contactRole.innerHTML = project.liferayContactRole;
		});
	}
	catch (error) {
		console.error(error.message);
	}
})();
