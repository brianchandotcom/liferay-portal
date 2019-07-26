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

export default function(nameSpace) {
	const deleteMultipleSynonyms = function() {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-delete-the-selected-entries'
				)
			)
		) {
			const searchContainer = document.getElementById(
				`${nameSpace}SynonymSetsEntriesFm`
			);
			const selector = 'input[type=checkbox]';

			const checkedSynonyms = new Array();

			searchContainer.querySelectorAll(selector).forEach(item => {
				if (item.checked) {
					checkedSynonyms.push(item.value);
				}
			});

			const form = document.forms[`${nameSpace}SynonymSetsEntriesFm`];

			form.elements[
				`${nameSpace}deletedSynonymSetsString`
			].value = checkedSynonyms.join(';');

			form.submit();
		}
	};

	const ACTIONS = {
		deleteMultipleSynonyms
	};

	Liferay.componentReady('synonymSetsEntriesManagementToolbar').then(
		managementToolbar => {
			managementToolbar.on('actionItemClicked', event => {
				const itemData = event.data.item.data;

				if (itemData && itemData.action && ACTIONS[itemData.action]) {
					ACTIONS[itemData.action]();
				}
			});
		}
	);
}
