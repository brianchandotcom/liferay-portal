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
import ClayIcon from '@clayui/icon';
import ClayMultiselect from '../shared/ClayMultiselect.es';
import PropTypes from 'prop-types';
import React, {Component} from 'react';
import ReactModal from 'react-modal';
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

	_handleSubmit = () => {
		this.props.onClickSubmit(
			this.state.modalKeywords.map(item => item.value)
		);

		this._handleCloseModal();
	};

	_handleUpdate = value => {
		this.setState({modalKeywords: value});
	};

	render() {
		const {keywords, onClickDelete} = this.props;

		const {modalKeywords} = this.state;

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

				<ReactModal
					className="modal-dialog modal-dialog-lg alias-modal-root"
					contentLabel="aliasModal"
					isOpen={this.state.showModal}
					onRequestClose={this._handleCloseModal}
					overlayClassName="modal-backdrop react-modal-backdrop"
					portalClassName="results-ranking-modal-root"
				>
					<div className="modal-content" data-testid="alias-modal">
						<div className="modal-header">
							<div className="modal-title">
								{Liferay.Language.get('add-an-alias')}
							</div>

							<ClayButton
								className="btn-outline-borderless"
								displayType="secondary"
								onClick={this._handleCloseModal}
							>
								<ClayIcon symbol="times" />
							</ClayButton>
						</div>

						<div className="modal-body">
							<div className="alias-modal-description">
								{Liferay.Language.get(
									'add-an-alias-description'
								)}
							</div>

							<div className="form-group">
								<label>{Liferay.Language.get('alias')}</label>

								<ClayMultiselect
									onAction={this._handleUpdate}
									value={modalKeywords}
								/>

								<div className="form-feedback-group">
									<div className="form-text">
										{Liferay.Language.get(
											'add-an-alias-instruction'
										)}
									</div>
								</div>
							</div>
						</div>

						<div className="modal-footer">
							<div className="modal-item-last">
								<div className="btn-group">
									<div className="btn-group-item">
										<ClayButton
											className="btn-outline-borderless"
											displayType="secondary"
											onClick={this._handleCloseModal}
										>
											{Liferay.Language.get('cancel')}
										</ClayButton>
									</div>

									<div className="btn-group-item">
										<ClayButton
											disabled={
												modalKeywords.length === 0
											}
											onClick={this._handleSubmit}
										>
											{Liferay.Language.get('add')}
										</ClayButton>
									</div>
								</div>
							</div>
						</div>
					</div>
				</ReactModal>
			</div>
		);
	}
}

export default Alias;
