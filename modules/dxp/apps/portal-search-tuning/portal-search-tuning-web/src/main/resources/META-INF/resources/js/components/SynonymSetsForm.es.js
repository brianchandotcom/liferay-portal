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

import ClayButton from './shared/ClayButton.es';
import ClayMultiselect from './shared/ClayMultiselect.es';
import PropTypes from 'prop-types';
import React, {Component} from 'react';

class SynonymSetsForm extends Component {
	static propTypes = {
		formName: PropTypes.string,
		inputName: PropTypes.string,
		onClickSubmit: PropTypes.func,
		originalInputName: PropTypes.string,
		synonymSets: PropTypes.string
	};

	state = {
		synonyms: []
	};

	_handleCancel = () => {
		window.history.back();
	};

	_handleSubmit = () => {
		event.preventDefault();
		const form = document.forms[this.props.formName];
		const synonymSetsString = this.state.synonyms
			.map(s => s.value)
			.toString();

		form.elements[this.props.inputName].value = synonymSetsString;
		form.elements[
			this.props.originalInputName
		].value = this._originalSynonymSets;
		form.submit();
	};

	_handleUpdate = value => {
		this.setState({
			synonyms: value
		});
	};

	constructor(props) {
		super(props);

		if (props.synonymSets.length > 0) {
			this._originalSynonymSets = props.synonymSets;

			props.synonymSets.split(',').forEach(synonym => {
				this.state.synonyms.push({
					label: synonym,
					value: synonym
				});
			});
		}
	}

	render() {
		const {synonyms} = this.state;

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

						<label>{Liferay.Language.get('synonyms')}</label>
						<ClayMultiselect
							onAction={this._handleUpdate}
							onSubmit={this._handleSubmit}
							value={synonyms}
						/>

						<div className="form-feedback-group">
							<div className="form-text">
								{Liferay.Language.get(
									'type-a-comma-or-press-enter-to-input-a-synonym'
								)}
							</div>
						</div>

						<div className="sheet-footer">
							<ClayButton
								disabled={synonyms.length === 0}
								displayStyle="primary"
								label={Liferay.Language.get('publish')}
								onClick={this._handleSubmit}
							/>
							<ClayButton
								label={Liferay.Language.get('cancel')}
								onClick={this._handleCancel}
							/>
						</div>
					</div>
				</div>
			</div>
		);
	}
}

export default SynonymSetsForm;
