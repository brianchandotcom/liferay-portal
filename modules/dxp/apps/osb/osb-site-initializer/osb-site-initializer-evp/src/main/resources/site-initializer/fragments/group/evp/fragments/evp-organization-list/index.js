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

const wrapper = document.querySelector('.wrapper');
const selectBtn = document.querySelector('.select-item-btn');
const selectText = selectBtn.querySelector('.select-item-btn input');
const searchInput = document.querySelector('.search-organization input');
const allOptions = document.querySelectorAll('.options-organizations li');
const itemSelected = document.querySelector('.item-selected');
const yourOrganizations = document.querySelector('#your-organizations');
const allOrganizations = document.querySelector('#all-organizations');

selectBtn.addEventListener('click', handleDropDown);

function handleDropDown() {
	wrapper.classList.toggle('active');
	if (wrapper.classList.contains('active')) {
		document.addEventListener('click', closeDropDown);
	} else {
		document.removeEventListener('click', closeDropDown);
	}
}

function closeDropDown(event) {
	const isOutsideWrapper =
		selectBtn.contains(event.target) || wrapper.contains(event.target);
	if (!isOutsideWrapper) {
		wrapper.classList.remove('active');
	}
}

allOptions.forEach((option) => {
	option.addEventListener('click', () => {
		wrapper.classList.remove('active');
		selectText.value = option.textContent;
		const inputID = document.querySelector(
			"input[name='r_organization_c_evpOrganizationId']"
		);
		const inputLabel = document.querySelector(
			"input[name='r_organization_c_evpOrganizationId-label']"
		);

		inputID.value = selectText.value.split(' - ')[0];
		inputLabel.value = selectText.value.split(' - ')[1];

		searchInput.value = '';
		filterData('');
	});
});

const filterData = (typing) => {
	allOptions.forEach((option) => {
		const text = option.textContent.replaceAll(' ', '').toUpperCase();
		const term = typing.replaceAll(' ', '').toUpperCase();
		option.style.display = text.includes(term) ? '' : 'none';
		getOrganizationsLabelControl();
	});
};

function handleTyping() {
	const typing = this.value;
	filterData(typing);
}

searchInput.addEventListener('keyup', handleTyping);
itemSelected.addEventListener('keyup', handleTyping);

const getOrganizationsLabelControl = () => {
	const checkVisibleOptions = (selector, element) => {
		const visibleOptions = document.querySelectorAll(
			`${selector} li:not([style='display: none;'])`
		);
		element.style.display = visibleOptions.length ? '' : 'none';
	};

	checkVisibleOptions(
		'.options-organizations.your-organizations',
		yourOrganizations
	);
	checkVisibleOptions(
		'.options-organizations.all-organizations',
		allOrganizations
	);
};

getOrganizationsLabelControl();

if (window.location.pathname.includes('edit-request-form')) {
	selectBtn.removeEventListener('click', handleDropDown);
	// eslint-disable-next-line no-undef
	const organizationInputBtn = fragmentElement.querySelector(
		'button.svg-caret-double'
	);

	organizationInputBtn.setAttribute('disabled', true);
	organizationInputBtn.style.borderColor = '#2B3A4B80';
}
