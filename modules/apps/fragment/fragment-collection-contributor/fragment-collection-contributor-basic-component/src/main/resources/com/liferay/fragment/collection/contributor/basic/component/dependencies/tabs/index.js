/*eslint-disable*/
const dropdown = fragmentElement.querySelector('.navbar-collapse');
const dropdownButton = fragmentElement.querySelector('.navbar-toggler-link');
const tabItems = [].slice.call(fragmentElement.querySelectorAll('.nav-link'));
const tabPanelItems = [].slice.call(
	fragmentElement.querySelectorAll('.tab-panel-item')
);

function activeTab(item) {
	tabItems.forEach(function(tabItem) {
		tabItem.setAttribute('aria-selected', false);
		tabItem.classList.remove('active');
	});
	item.setAttribute('aria-selected', true);
	item.classList.add('active');
}

function activeTabPanel(item) {
	tabPanelItems.forEach(function(tabPanelItem) {
		if (!tabPanelItem.classList.contains('d-none')) {
			tabPanelItem.classList.add('d-none');
		}
	});
	item.classList.remove('d-none');
}

function handleDropdown({event, item}) {
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
	dropdownButton.querySelector(
		'.navbar-text-truncate'
	).innerHTML = item.querySelector('lfr-editable').textContent;
}

function openTabPanel(event, i) {
	const {currentTarget, target} = event;
	const isEditable =
		target.classList.contains('page-editor__editable') ||
		target.parentElement.classList.contains('page-editor__editable');

	if (!isEditable) {
		handleDropdown({event, item: currentTarget});
	}

	activeTab(currentTarget, i);
	activeTabPanel(tabPanelItems[i]);

	this.tabIndex = i;
}

function main() {
	const initialState = !this.tabIndex || this.tabIndex >= tabItems.length;
	let tabItemSelected = tabItems[0];

	if (initialState) {
		tabItems.forEach(function(item, i) {
			if (!i) {
				activeTab(item);
			}
			item.addEventListener('click', function(event) {
				openTabPanel(event, i);
			});
		});
		tabPanelItems.forEach(function(item, i) {
			if (!i) {
				activeTabPanel(item);
			}
		});
	}
	else {
		tabItemSelected = tabItems[this.tabIndex];
		tabItems.forEach(function(item, i) {
			activeTab(tabItems[this.tabIndex]);
			item.addEventListener('click', function(event) {
				openTabPanel(event, i);
			});
		});
		tabPanelItems.forEach(function() {
			activeTabPanel(tabPanelItems[this.tabIndex]);
		});
	}

	dropdownButton.addEventListener('click', function(event) {
		handleDropdown({event});
	});
	handleDropdownButtonName(tabItemSelected);
}

main();