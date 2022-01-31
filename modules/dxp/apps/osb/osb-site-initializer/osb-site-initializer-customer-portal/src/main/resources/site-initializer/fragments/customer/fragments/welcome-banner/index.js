/* eslint-disable no-undef */
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

const userNameElement = fragmentElement.querySelector(
	'#customer-portal-user-name'
);
const WelcomeUserElement = fragmentElement.querySelector('#welcome-user');
const ProjectTitleElement = fragmentElement.querySelector('#project-title');
const WelcomeDescriptionElement = fragmentElement.querySelector(
	'#welcome-description'
);

(async () => {
	try {
		Liferay.once(
			'customer-portal-select-user-loading',
			({detail: userAccount}) => {
				userNameElement.innerHTML = `${
					userAccount.name.split(' ')[0]
				}!`;

				WelcomeUserElement.classList.toggle('skeleton');
				ProjectTitleElement.classList.toggle('skeleton');
				WelcomeDescriptionElement.classList.toggle('skeleton');
			}
		);
	}
	catch (error) {
		console.error(error.message);
	}
})();
