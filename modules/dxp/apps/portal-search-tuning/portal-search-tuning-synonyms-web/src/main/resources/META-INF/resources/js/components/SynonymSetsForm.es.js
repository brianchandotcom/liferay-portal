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

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
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

class SynonymSetsForm extends Component {
	static propTypes = {
		formName: PropTypes.string,
		inputName: PropTypes.string,
		onClickSubmit: PropTypes.func,
		originalInputName: PropTypes.string,
		synonymSets: PropTypes.string
	};

	state = {
		inputValue: '',
		synonyms: []
	};

	constructor(props) {
		super(props);

		if (props.synonymSets.length > 0) {
			this._originalSynonymSets = props.synonymSets;

			props.synonymSets.split(',').forEach(synonym => {
				this.state.synonyms.push(synonym);
			});
		}
	}

	_handleCancel = () => {
		window.history.back();
	};

	_handleInputChange = value => {
		this.setState({inputValue: value});
	};

	_handleSubmit = event => {
		event.preventDefault();

		const form = document.forms[this.props.formName];

		const synonymSetsString = this.state.synonyms;

		form.elements[this.props.inputName].value = synonymSetsString;

		form.elements[
			this.props.originalInputName
		].value = this._originalSynonymSets;

		submitForm(form);
	};

	_handleItemsChange = value => {
		const newValue = filterEmptyStrings(value.map(item => item.trim()));

		this.setState({
			synonyms: newValue.filter(
				(item, index) => newValue.indexOf(item) === index
			)
		});
	};

	render() {
		const {inputValue, synonyms} = this.state;

		return (
			<div className="synonym-sets-form">
				<div className="container-fluid-max-xl">
					<div className="sheet-lg">
						<div className="sheet-title">
							{Liferay.Language.get('create-synonym-set')}
						</div>

						<div className="sheet-text">
							{Liferay.Language.get(
								'broaden-the-scope-of-search-by-treating-terms-equally-using-synonyms'
							)}
						</div>

						<ClayForm.MultiSelect
							helpText={Liferay.Language.get(
								'type-a-comma-or-press-enter-to-input-a-synonym'
							)}
							inputValue={inputValue}
							items={synonyms}
							label={Liferay.Language.get('synonyms')}
							onInputChange={this._handleInputChange}
							onItemsChange={this._handleItemsChange}
						/>

						<div className="sheet-footer">
							<ClayButton
								disabled={synonyms.length === 0}
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
						</div>
					</div>
				</div>
			</div>
		);
	}
}

export default SynonymSetsForm;
