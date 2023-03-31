const dropdown = fragmentElement.querySelector('.navbar-collapse');
const dropdownButton = fragmentElement.querySelector('.navbar-toggler-link');
const editMode = layoutMode === 'edit';
const persistedTabKey = 'tabsFragment_' + fragmentNamespace + '_persistedTabId';

const tabItems = [].slice.call(
	fragmentElement.querySelectorAll(
		'[data-fragment-namespace="' + fragmentNamespace + '"].nav-link'
	)
);

const tabPanelItems = [].slice.call(
	fragmentElement.querySelectorAll(
		'[data-fragment-namespace="' + fragmentNamespace + '"].tab-panel-item'
	)
);

const persistedTab = (function () {
	if (!configuration.persistSelectedTab) {
		let persistedId;

		return {
			getId() {
				return persistedId;
			},

			setId(nextId) {
				persistedId = nextId;
			},
		};
	}

	return {
		getId() {
			return Number(
				Liferay.Util.SessionStorage.getItem(
					persistedTabKey,
					Liferay.Util.SessionStorage.TYPES.PERSONALIZATION
				)
			);
		},

		setId(id) {
			Liferay.Util.SessionStorage.setItem(
				persistedTabKey,
				id,
				Liferay.Util.SessionStorage.TYPES.PERSONALIZATION
			);
		},
	};
})();

function activeTab(item) {
	tabItems.forEach(function (tabItem) {
		tabItem.setAttribute('aria-selected', false);
		tabItem.classList.remove('active');
	});

	item.setAttribute('aria-selected', true);
	item.classList.add('active');
}

function activeTabPanel(item) {
	tabPanelItems.forEach(function (tabPanelItem) {
		if (!tabPanelItem.classList.contains('d-none')) {
			tabPanelItem.classList.add('d-none');
		}
	});

	item.classList.remove('d-none');
}

function handleDropdown(event, item) {
	event.preventDefault();
	dropdown.classList.toggle('show');

	const ariaExpanded = dropdownButton.getAttribute('aria-expanded');

	dropdownButton.setAttribute(
		'aria-expanded',
		ariaExpanded === 'false' ? true : false
	);

	if (item) {
		handleDropdownButtonName(item);
	}
}

function handleDropdownButtonName(item) {
	const tabText =
		item.querySelector('lfr-editable') ||
		item.querySelector('.navbar-text-truncate');

	if (tabText) {
		dropdownButton.querySelector('.navbar-text-truncate').innerHTML =
			tabText.textContent;
	}
}

function openTabPanel(event, i) {
	const currentTarget = event.currentTarget;
	const target = event.target;

	const isEditable =
		target.hasAttribute('data-lfr-editable-id') ||
		target.hasAttribute('contenteditable');

	const dropdownIsOpen = JSON.parse(
		dropdownButton.getAttribute('aria-expanded')
	);

	if (!isEditable || !editMode) {
		if (dropdownIsOpen) {
			handleDropdown(event, currentTarget);
		}
		else {
			handleDropdownButtonName(currentTarget);
		}

		currentTarget.focus();

		activeTab(currentTarget, i);
		activeTabPanel(tabPanelItems[i]);
		persistedTab.setId(i);

		Liferay.fire('tabsFragment:activePanel', {panel: tabPanelItems[i]});
	}
}

function main() {
	const tabItemId = tabItems[persistedTab.getId()] ? persistedTab.getId() : 0;

	tabItems.forEach(function (item, index) {
		item.addEventListener('click', function (event) {
			openTabPanel(event, index);
		});
	});

	dropdownButton.addEventListener('click', function (event) {
		handleDropdown(event);
	});

	activeTab(tabItems[tabItemId]);
	activeTabPanel(tabPanelItems[tabItemId]);
	handleDropdownButtonName(tabItems[tabItemId]);
}

main();
