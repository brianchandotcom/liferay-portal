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

import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayMultiSelectInput from '@clayui/multi-select';
import PropTypes from 'prop-types';
import React, {Component} from 'react';

/**
 * Filters out empty strings from the passed in array.
 * @param {Array} list The list of strings to filter.
 * @returns {Array} The filtered list.
 */
function filterEmptyStrings(list) {
	return list.filter(item => item.trim());
}

class Alias extends Component {
	static propTypes = {
		keywords: PropTypes.arrayOf(String),
		onChange: PropTypes.func.isRequired
	};

	state = {
		inputValue: ''
	};

	_handleInputChange = value => {
		this.setState({inputValue: value});
	};

	_handleItemsChange = value => {
		this.props.onChange(filterEmptyStrings(value.map(item => item.trim())));
	};

	render() {
		const {keywords} = this.props;

		const {inputValue} = this.state;

		return (
			<ClayForm.Group>
				<label>
					{Liferay.Language.get('aliases')}

					<span
						className="input-label-help-icon lfr-portal-tooltip"
						data-title={Liferay.Language.get('add-an-alias-help')}
					>
						<ClayIcon symbol="question-circle-full" />
					</span>
				</label>

				<ClayMultiSelectInput
					helpText={Liferay.Language.get('add-an-alias-instruction')}
					inputValue={inputValue}
					items={keywords}
					onInputChange={this._handleInputChange}
					onItemsChange={this._handleItemsChange}
				/>
			</ClayForm.Group>
		);
	}
}

export default Alias;
