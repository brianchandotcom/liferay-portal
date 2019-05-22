export default function(nameSpace) {
	var deleteMultipleSynonyms = function() {
		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-the-selected-entries'))) {
			var searchContainer = document.getElementById(`${nameSpace}SynonymSetsEntriesFm`);
			var selector = 'input[type=checkbox]';

			var checkedSynonyms = new Array();

			searchContainer.querySelectorAll(selector).forEach(item => {
				if (item.checked) {
					checkedSynonyms.push(item.value);
				}
			});

			var form = document.forms[`${nameSpace}SynonymSetsEntriesFm`];

			form.elements[`${nameSpace}deletedSynonymSetsString`].value = checkedSynonyms.join(';');

			form.submit();
		}
	};

	var ACTIONS = {
		deleteMultipleSynonyms
	};

	Liferay.componentReady('synonymSetsEntriesManagementToolbar').then(
		managementToolbar => {
			managementToolbar.on(
				'actionItemClicked',
				event => {
					var itemData = event.data.item.data;

					if (itemData && itemData.action && ACTIONS[itemData.action]) {
						ACTIONS[itemData.action]();
					}
				}
			);
		}
	);
}