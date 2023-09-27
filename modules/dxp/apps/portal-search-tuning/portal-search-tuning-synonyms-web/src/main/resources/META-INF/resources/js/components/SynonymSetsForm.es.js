/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayMultiSelect from '@clayui/multi-select';
import PropTypes from 'prop-types';
import React, {Component} from 'react';

/**
 * Filters out empty items and duplicate items. Compares both label and value
 * properties.
 * @param {Array} list A list of label-value objects.
 */
function filterDuplicates(list) {
	const cleanedList = filterEmptyStrings(trimListItems(list));

	return cleanedList.filter(
		(item, index) =>
			cleanedList.findIndex(
				(newVal) =>
					newVal.label.toLowerCase() === item.label.toLowerCase() &&
					newVal.value.toLowerCase() === item.value.toLowerCase()
			) === index
	);
}

/**
 * Filters out empty strings from the passed in array.
 * @param {Array} list The list of strings to filter.
 * @returns {Array} The filtered list.
 */
function filterEmptyStrings(list) {
	return list.filter(({label, value}) => label && value);
}

/**
 * Trims whitespace in list items for ClayMultiSelect.
 * @param {Array} list A list of label-value objects.
 */
function trimListItems(list) {
	return list.map(({label, value}) => ({
		label: label.trim(),
		value: value.trim(),
	}));
}

class SynonymSetsForm extends Component {
	static propTypes = {
		formName: PropTypes.string.isRequired,
		inputName: PropTypes.string.isRequired,
		synonymSets: PropTypes.string,
	};

	static defaultProps = {
		synonymSets: '',
	};

	state = {
		inputValue: '',
		synonyms: [],
	};

	constructor(props) {
		super(props);

		if (props.synonymSets.length) {
			props.synonymSets.split(',').forEach((synonym) => {
				this.state.synonyms.push({
					label: synonym,
					value: synonym,
				});
			});
		}
	}

	_handleCancel = () => {
		window.history.back();
	};

	_handleInputChange = (value) => {
		this.setState({inputValue: value});
	};

	_handleSubmit = (event) => {
		event.preventDefault();

		const form = document.forms[this.props.formName];

		const synonymSetsString = this.state.synonyms.map(
			(synonym) => synonym.value
		);

		form.elements[this.props.inputName].value = synonymSetsString;

		submitForm(form);
	};

	_handleItemsChange = (values) => {
		this.setState({
			synonyms: filterDuplicates(values),
		});
	};

	render() {
		const {inputValue, synonyms} = this.state;

		return (
			<div className="synonym-sets-form">
				<div className="sheet-title">
					{Liferay.Language.get('create-synonym-set')}
				</div>

				<div className="sheet-text">
					{Liferay.Language.get(
						'broaden-the-scope-of-search-by-treating-terms-equally-using-synonyms'
					)}
				</div>

				<ClayForm.Group>
					<label htmlFor="synonym-sets-input">
						{Liferay.Language.get('synonyms')}
					</label>

					<ClayInput.Group>
						<ClayInput.GroupItem>
							<ClayMultiSelect
								id="synonym-sets-input"
								items={synonyms}
								onChange={this._handleInputChange}
								onItemsChange={this._handleItemsChange}
								value={inputValue}
							/>

							<ClayForm.FeedbackGroup>
								<ClayForm.Text>
									{Liferay.Language.get(
										'type-a-comma-or-press-enter-to-input-a-synonym'
									)}
								</ClayForm.Text>
							</ClayForm.FeedbackGroup>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>

				<ClayLayout.SheetFooter>
					<ClayButton
						disabled={!synonyms.length}
						displayType="primary"
						onClick={this._handleSubmit}
						type="submit"
					>
						{Liferay.Language.get('publish')}
					</ClayButton>

					<ClayButton
						displayType="secondary"
						onClick={this._handleCancel}
					>
						{Liferay.Language.get('cancel')}
					</ClayButton>
				</ClayLayout.SheetFooter>
			</div>
		);
	}
}

export default SynonymSetsForm;
