/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface ISelectedData {
	exclude?: boolean;
	value?: 'custom' | 'system';
}

interface ISetFilterArgs {
	active?: boolean;
	odataFilterString?: string;
	selectedData: Partial<ISelectedData>;
}

interface IHtmlElementBuilderProps {
	filter?: {
		selectedData?: ISelectedData;
	};
	setFilter: (args: ISetFilterArgs) => void;
}

let systemDataSetNames: string[] = [];
const EMPTY_FILTER_ODATA_QUERY = "fdsName ne '' or fdsName eq ''";

const escapeODataString = (value: string) => value.replace(/'/g, "''");

const getSystemDataSetFilter = () => {
	if (!systemDataSetNames.length) {
		return '';
	}

	const quotedNames = systemDataSetNames.map(
		(systemDataSetName) => `'${escapeODataString(systemDataSetName)}'`
	);

	return `fdsName in (${quotedNames.join(', ')})`;
};

export function setSystemDataSetNames(nextSystemDataSetNames: string[]) {
	systemDataSetNames = nextSystemDataSetNames.filter(Boolean);
}

const UserViewsTypeFilter = {
	descriptionBuilder: (selectedData: ISelectedData = {}) => {
		const {exclude, value} = selectedData;

		if (!value) {
			return '';
		}

		const label =
			value === 'system'
				? Liferay.Language.get('system')
				: Liferay.Language.get('custom');

		if (exclude) {
			return `(${Liferay.Language.get('exclude')}) ${label}`;
		}

		return label;
	},
	htmlElementBuilder: ({filter, setFilter}: IHtmlElementBuilderProps) => {
		const rootElement = document.createElement('div');
		const searchCaption = document.createElement('div');
		const searchInputGroup = document.createElement('div');
		const searchInputGroupItem = document.createElement('div');
		const searchInput = document.createElement('input');
		const excludeCaption = document.createElement('div');
		const excludeRow = document.createElement('div');
		const excludeLabelColumn = document.createElement('div');
		const excludeToggleColumn = document.createElement('div');
		const excludeLabel = document.createElement('label');
		const excludeToggle = document.createElement('label');
		const excludeToggleCheckBar = document.createElement('span');
		const excludeToggleInput = document.createElement('input');
		const excludeToggleBar = document.createElement('span');
		const excludeToggleHandle = document.createElement('span');
		const itemsWrapper = document.createElement('div');
		const listElement = document.createElement('ul');
		const noResultsElement = document.createElement('div');
		const footerCaption = document.createElement('div');
		const applyButton = document.createElement('button');
		const selectedElementsWrapper = document.createElement('div');
		const groupName = `userviews-type-filter-${Math.random().toString(36).slice(2)}`;
		const initialSelectedValue = filter?.selectedData?.value as
			| 'custom'
			| 'system'
			| undefined;
		const initialExclude = !!filter?.selectedData?.exclude;
		let selectedValue = initialSelectedValue;
		let exclude = initialExclude;
		const options = [
			{
				label: Liferay.Language.get('system'),
				value: 'system',
			},
			{
				label: Liferay.Language.get('custom'),
				value: 'custom',
			},
		];

		const createDivider = () => {
			const divider = document.createElement('div');

			divider.className = 'dropdown-divider';
			divider.setAttribute('role', 'separator');

			return divider;
		};

		const getActionType = () => {
			if (!initialSelectedValue) {
				return 'add';
			}

			if (!selectedValue) {
				return 'delete';
			}

			return 'edit';
		};

		const hasChanges = () =>
			selectedValue !== initialSelectedValue ||
			exclude !== initialExclude;

		const updateActionButton = () => {
			const actionType = getActionType();

			if (actionType === 'add') {
				applyButton.textContent = Liferay.Language.get('add-filter');
				applyButton.disabled = !selectedValue;

				return;
			}

			if (actionType === 'delete') {
				applyButton.textContent = Liferay.Language.get('delete-filter');
				applyButton.disabled = false;

				return;
			}

			applyButton.textContent = Liferay.Language.get('show-results');
			applyButton.disabled = !hasChanges();
		};

		const applyFilter = () => {
			if (getActionType() === 'delete') {
				setFilter({
					active: false,
					odataFilterString: '',
					selectedData: {
						exclude: false,
					},
				});

				return;
			}

			if (!selectedValue) {
				return;
			}

			setFilter({
				active: true,
				selectedData: {
					exclude,
					value: selectedValue,
				},
			});
		};

		searchCaption.className = 'dropdown-caption pb-0';
		searchInputGroup.className = 'input-group';
		searchInputGroupItem.className = 'input-group-item';
		searchInput.className = 'form-control';
		searchInput.placeholder = Liferay.Language.get('search');
		searchInput.type = 'text';
		selectedElementsWrapper.className = 'mt-2 selected-elements-wrapper';
		searchInputGroupItem.appendChild(searchInput);
		searchInputGroup.appendChild(searchInputGroupItem);
		searchCaption.appendChild(searchInputGroup);
		searchCaption.appendChild(selectedElementsWrapper);

		excludeCaption.className = 'dropdown-caption pb-0';
		excludeRow.className = 'row';
		excludeLabelColumn.className = 'col';
		excludeToggleColumn.className = 'col-auto';

		excludeLabel.htmlFor = `${groupName}-exclude`;
		excludeLabel.textContent = Liferay.Language.get('exclude');

		excludeToggle.className = 'simple-toggle-switch toggle-switch';
		excludeToggleCheckBar.className = 'toggle-switch-check-bar';
		excludeToggleInput.className = 'toggle-switch-check';
		excludeToggleInput.id = `${groupName}-exclude`;
		excludeToggleInput.checked = exclude;
		excludeToggleInput.setAttribute('role', 'switch');
		excludeToggleInput.type = 'checkbox';
		excludeToggleBar.className = 'toggle-switch-bar';
		excludeToggleBar.ariaHidden = 'true';
		excludeToggleHandle.className = 'toggle-switch-handle';

		excludeLabelColumn.appendChild(excludeLabel);
		excludeToggleBar.appendChild(excludeToggleHandle);
		excludeToggleCheckBar.appendChild(excludeToggleInput);
		excludeToggleCheckBar.appendChild(excludeToggleBar);
		excludeToggle.appendChild(excludeToggleCheckBar);
		excludeToggleColumn.appendChild(excludeToggle);
		excludeRow.appendChild(excludeLabelColumn);
		excludeRow.appendChild(excludeToggleColumn);
		excludeCaption.appendChild(excludeRow);

		itemsWrapper.className = 'pb-1 pl-3 pr-3 pt-1';
		listElement.className = 'inline-scroller mx-n2 px-2';
		noResultsElement.className = 'mt-2 p-2 text-muted';
		noResultsElement.textContent = Liferay.Language.get('no-results-found');
		noResultsElement.style.display = 'none';

		const items: Array<{element: HTMLLIElement; label: string}> = [];
		const renderSelectedLabel = () => {
			selectedElementsWrapper.innerHTML = '';

			if (!selectedValue) {
				return;
			}

			const selectedOption = options.find(
				(option) => option.value === selectedValue
			);

			if (!selectedOption) {
				return;
			}

			const labelElement = document.createElement('span');
			const labelExpand = document.createElement('span');
			const labelAfter = document.createElement('span');
			const closeButton = document.createElement('button');
			const closeIcon = document.createElement('span');

			labelElement.className = 'label label-dismissible label-secondary';

			labelExpand.className = 'label-item label-item-expand';
			labelExpand.textContent = selectedOption.label;

			labelAfter.className = 'label-item label-item-after';

			closeButton.className = 'close';
			closeButton.type = 'button';
			closeButton.setAttribute(
				'aria-label',
				Liferay.Language.get('remove-filter')
			);
			closeButton.onclick = () => {
				selectedValue = undefined;

				rootElement
					.querySelectorAll(`input[name="${groupName}"]`)
					.forEach((input) => {
						(input as HTMLInputElement).checked = false;
					});

				updateActionButton();
				renderSelectedLabel();
			};

			closeIcon.setAttribute('aria-hidden', 'true');
			closeIcon.textContent = '\u00d7';

			closeButton.appendChild(closeIcon);
			labelAfter.appendChild(closeButton);
			labelElement.appendChild(labelExpand);
			labelElement.appendChild(labelAfter);
			selectedElementsWrapper.appendChild(labelElement);
		};

		options.forEach((option) => {
			const item = document.createElement('li');
			const wrapper = document.createElement('div');
			const input = document.createElement('input');
			const labelWrapper = document.createElement('label');
			const label = document.createElement('span');
			const labelText = document.createElement('span');
			const id = `${groupName}-${option.value}`;

			item.className = 'pb-1 pt-1';

			wrapper.className =
				'custom-control custom-radio custom-control-outside';

			input.className = 'custom-control-input';
			input.id = id;
			input.name = groupName;
			input.setAttribute('role', 'radio');
			input.type = 'radio';
			input.value = option.value;
			input.checked = selectedValue === option.value;

			input.onchange = (event) => {
				selectedValue = (event.target as HTMLInputElement).value as
					| 'custom'
					| 'system';
				updateActionButton();
				renderSelectedLabel();
			};

			labelWrapper.htmlFor = id;
			label.className = 'custom-control-label';

			labelText.className = 'custom-control-label-text';
			labelText.textContent = option.label;

			label.appendChild(labelText);
			labelWrapper.appendChild(input);
			labelWrapper.appendChild(label);
			wrapper.appendChild(labelWrapper);
			item.appendChild(wrapper);
			listElement.appendChild(item);

			items.push({element: item, label: option.label.toLowerCase()});
		});

		searchInput.oninput = (event) => {
			const query = (event.target as HTMLInputElement).value
				.toLowerCase()
				.trim();
			let visibleItemsCount = 0;

			items.forEach((item) => {
				const isVisible = !query || item.label.includes(query);

				item.element.style.display = isVisible ? '' : 'none';

				if (isVisible) {
					visibleItemsCount++;
				}
			});

			noResultsElement.style.display =
				query && !visibleItemsCount ? '' : 'none';
		};

		excludeToggleInput.onchange = () => {
			exclude = excludeToggleInput.checked;
			updateActionButton();
		};

		itemsWrapper.appendChild(listElement);
		itemsWrapper.appendChild(noResultsElement);

		footerCaption.className = 'dropdown-caption';

		applyButton.className = 'btn btn-primary btn-sm';
		applyButton.type = 'button';

		applyButton.onclick = () => {
			applyFilter();
		};

		renderSelectedLabel();
		updateActionButton();

		footerCaption.appendChild(applyButton);

		rootElement.appendChild(searchCaption);
		rootElement.appendChild(createDivider());
		rootElement.appendChild(excludeCaption);
		rootElement.appendChild(createDivider());
		rootElement.appendChild(itemsWrapper);
		rootElement.appendChild(createDivider());
		rootElement.appendChild(footerCaption);

		return rootElement;
	},
	oDataQueryBuilder: (selectedData: ISelectedData = {}) => {
		const {exclude, value} = selectedData;

		if (!value) {
			return EMPTY_FILTER_ODATA_QUERY;
		}

		const systemDataSetFilter = getSystemDataSetFilter();

		if (!systemDataSetFilter) {
			return EMPTY_FILTER_ODATA_QUERY;
		}

		if (value === 'system') {
			return exclude
				? `not (${systemDataSetFilter})`
				: systemDataSetFilter;
		}

		if (value === 'custom') {
			return exclude
				? systemDataSetFilter
				: `not (${systemDataSetFilter})`;
		}

		return EMPTY_FILTER_ODATA_QUERY;
	},
};

export default UserViewsTypeFilter;
