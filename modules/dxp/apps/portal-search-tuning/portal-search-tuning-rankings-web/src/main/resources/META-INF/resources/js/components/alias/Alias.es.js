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

import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayMultiSelect from '@clayui/multi-select';
import getCN from 'classnames';
import PropTypes from 'prop-types';
import React, {Component} from 'react';

import {PORTAL_TOOLTIP_TRIGGER_CLASS} from '../../utils/constants.es';

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
					newVal.label === item.label && newVal.value === item.value
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
 * Transforms a list of strings to label-value objects to pass into
 * ClayMultiSelect.
 * @param {Array} list A list of strings.
 * @returns {Array} A list of label-value objects.
 */
function transformListOfStringsToObjects(list) {
	return list.map((string) => ({label: string, value: string}));
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

class Alias extends Component {
	static propTypes = {
		keywords: PropTypes.arrayOf(String),
		onChange: PropTypes.func.isRequired,
	};

	state = {
		inputValue: '',
	};

	_handleInputChange = (value) => {
		this.setState({inputValue: value});
	};

	_handleItemsChange = (values) => {
		this.props.onChange(filterDuplicates(values));
	};

	render() {
		const {keywords} = this.props;

		const {inputValue} = this.state;

		return (
			<ClayForm.Group>
				<label htmlFor="aliases-input">
					{Liferay.Language.get('aliases')}

					<span
						className={getCN(
							'input-label-help-icon',
							PORTAL_TOOLTIP_TRIGGER_CLASS
						)}
						data-title={Liferay.Language.get('add-an-alias-help')}
					>
						<ClayIcon symbol="question-circle-full" />
					</span>
				</label>

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayMultiSelect
							id="aliases-input"
							inputValue={inputValue}
							items={transformListOfStringsToObjects(keywords)}
							onChange={this._handleInputChange}
							onItemsChange={this._handleItemsChange}
						/>

						<ClayForm.FeedbackGroup>
							<ClayForm.Text>
								{Liferay.Language.get(
									'add-an-alias-instruction'
								)}
							</ClayForm.Text>
						</ClayForm.FeedbackGroup>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>
		);
	}
}

export default Alias;
