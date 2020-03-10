/*eslint-disable*/
const tabItems = Array.from(fragmentElement.querySelectorAll('.nav-link'));
const tabContentItems = Array.from(fragmentElement.querySelectorAll('.tab-content-item'));
const dropdownButton = fragmentElement.querySelector('.navbar-toggler-link');
const dropdown = fragmentElement.querySelector('.navbar-collapse');

const activeTab = item => {
	tabItems.forEach(tabItem => tabItem.classList.remove('active'));
	item.classList.add('active');
};

const activeContentTab = item => {
	tabContentItems.forEach(
		tabContentItem => !tabContentItem.classList.contains('d-none') && tabContentItem.classList.add('d-none')
	);
	item.classList.remove('d-none');
};

const handleDropdown = item => {
	if (dropdown.classList.contains('show')) {
        dropdown.classList.remove('show');
        if (item) {
            handleDropdownButtonName(item);
        }
	} else {
		dropdown.classList.add('show');
	}
};

const handleDropdownButtonName = (item) => {
    dropdownButton.querySelector('.navbar-text-truncate').innerHTML = item.querySelector('lfr-editable').textContent;
}

const openTab = (event, i) => {
	const {currentTarget, target} = event;
	const isEditable = currentTarget.classList.contains('nav-link') &&
		(target.classList.contains('page-editor__editable') || target.classList.contains('cke_editable'));

	if (!isEditable) {
		handleDropdown(currentTarget);
	}

	activeTab(currentTarget, i);
	activeContentTab(tabContentItems[i]);

	this.count = i;
};

const main = () => {
    const notInitialState = this.count && this.count < tabItems.length;
    dropdownButton.addEventListener('click', () => handleDropdown());
    
    notInitialState
        ? handleDropdownButtonName(tabItems[this.count])
        : handleDropdownButtonName(tabItems[0]);

	tabItems.forEach((item, i) => {
		notInitialState
			? activeTab(tabItems[this.count])
			: !i && activeTab(item);

		item.addEventListener('click', event => openTab(event, i));
	});

	tabContentItems.forEach((item, i) =>
		notInitialState
			? activeContentTab(tabContentItems[this.count])
			: !i && activeContentTab(item)
	);
};

main();
