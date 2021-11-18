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

const eventName = `customer-portal-select-user-loading`;

const userIconWrapper = fragmentElement.querySelector(
	'#customer-portal-user-icon-application-wrapper'
);
const userIcon = fragmentElement.querySelector(
	'#customer-portal-user-icon-application'
);
const userName = fragmentElement.querySelector(
	'#customer-portal-user-name-application'
);

const getUserAccountById = (id) => {
	return `userAccount(userAccountId: ${id}) {
                 id
                 name
                 image
                 accountBriefs {
                   id
                   name
                   externalReferenceCode
                 }
                 roleBriefs {
                   id
                   name
                 }
               }`;
};

const fetchGraphQL = async (queryString) => {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(`${window.location.origin}/o/graphql`, {
		body: JSON.stringify({
			query: `{${queryString}}`,
		}),
		headers: {
			'Content-Type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method: 'POST',
	});

	const data = await response.json();

	return data;
};

(async () => {
	try {
		const userAccount = await fetchGraphQL(
			getUserAccountById(Liferay.ThemeDisplay.getUserId())
		);

		if (userAccount) {
			const userAccountData = userAccount.data.userAccount;

			window.dispatchEvent(
				new CustomEvent(eventName, {
					bubbles: true,
					composed: true,
					detail: userAccountData,
				})
			);

			userIconWrapper.classList.toggle('skeleton');
			userName.classList.toggle('skeleton');

			if (userAccountData.image) {
				userIcon.src = window.location.origin + userAccountData.image;
			}
			else {
				userIconWrapper.className =
					'mr sticker sticker-circle sticker-lg user-icon-color-1';
				userIconWrapper.innerHTML = `<svg class="lexicon-icon lexicon-icon-user" focusable="false" role="presentation">
			<use xlink:href="${
				Liferay.ThemeDisplay.getCDNBaseURL() +
				'/o/admin-theme/images/clay/icons.svg'
			}#user" />
		</svg>`;
			}

			userName.innerHTML = userAccountData.name;
		}
	}
	catch (error) {
		console.error(error.message);
	}
})();
