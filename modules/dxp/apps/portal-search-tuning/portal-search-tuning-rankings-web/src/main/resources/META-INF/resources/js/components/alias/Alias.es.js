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
import ClayModal from '@clayui/modal';
import ClayForm from '@clayui/form';
import PropTypes from 'prop-types';
import React, {Component} from 'react';
import Tag from './Tag.es';

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
		onClickDelete: PropTypes.func.isRequired,
		onClickSubmit: PropTypes.func.isRequired
	};

	state = {
		inputValue: '',
		modalKeywords: [],
		showModal: false
	};

	_handleCloseModal = () => {
		this.setState({showModal: false});
	};

	_handleOpenModal = () => {
		this.setState({
			modalKeywords: [],
			showModal: true
		});
	};

	_handleSubmit = onClose => () => {
		this.props.onClickSubmit(this.state.modalKeywords);

		onClose();
	};

	_handleInputChange = value => {
		this.setState({inputValue: value});
	};

	_handleItemsChange = value => {
		this.setState({modalKeywords: value});
	};

	render() {
		const {keywords, onClickDelete} = this.props;

		const {inputValue, modalKeywords, showModal} = this.state;

		return (
			<div className="results-ranking-alias-root">
				<div className="sheet-text">
					<div className="alias-title">
						<strong>{Liferay.Language.get('aliases')}</strong>
					</div>

					<div className="input-group">
						<div className="input-group-item input-group-item-shrink">
							{filterEmptyStrings(keywords).map(word => (
								<Tag
									key={word}
									label={word}
									onClickDelete={onClickDelete}
								/>
							))}
						</div>

						<div className="input-group-item input-group-item-shrink">
							<ClayButton
								className="btn-outline-borderless"
								displayType="secondary"
								onClick={this._handleOpenModal}
								small
							>
								{Liferay.Language.get('add-an-alias')}
							</ClayButton>
						</div>
					</div>
				</div>

				{showModal && (
					<ClayModal
						className="results-ranking-modal-root"
						data-testid="alias-modal"
						onClose={this._handleCloseModal}
						size="lg"
					>
						{onClose => (
							<div className="alias-modal-root">
								<ClayModal.Header>
									{Liferay.Language.get('add-an-alias')}
								</ClayModal.Header>

								<ClayModal.Body>
									<div className="alias-modal-description">
										{Liferay.Language.get(
											'add-an-alias-description'
										)}
									</div>

									<ClayForm.MultiSelect
										helpText={Liferay.Language.get(
											'add-an-alias-instruction'
										)}
										inputValue={inputValue}
										items={modalKeywords}
										label={Liferay.Language.get('alias')}
										onInputChange={this._handleInputChange}
										onItemsChange={this._handleItemsChange}
									/>
								</ClayModal.Body>

								<ClayModal.Footer
									last={
										<ClayButton.Group spaced>
											<ClayButton
												className="btn-outline-borderless"
												displayType="secondary"
												onClick={onClose}
											>
												{Liferay.Language.get('cancel')}
											</ClayButton>

											<ClayButton
												disabled={
													modalKeywords.length === 0
												}
												onClick={this._handleSubmit(
													onClose
												)}
											>
												{Liferay.Language.get('add')}
											</ClayButton>
										</ClayButton.Group>
									}
								/>
							</div>
						)}
					</ClayModal>
				)}
			</div>
		);
	}
}

export default Alias;
