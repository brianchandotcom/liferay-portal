/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import React, {Component} from 'react';
import PropTypes from 'prop-types';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';

import {OTHER_REASON_VALUE} from '../constants.es';
import ThemeContext from '../ThemeContext.es';

import FlagsModal from './FlagsModal.es';

class Flags extends Component {
	static contextType = ThemeContext;

	static propTypes = {
		enabled: PropTypes.bool,
		message: PropTypes.string.isRequired,
		messageType: PropTypes.string
	};

	static defaultProps = {
		enabled: true,
		message: Liferay.Language.get('report')
	};

	constructor(props) {
		super(props);

		this.state = {
			otherReason: '',
			reason: Object.values(props.reasons)[0],
			reportDialogOpen: false,
			isSending: false,
			isSuccessful: false
		};

		this.handleClickClose = this.handleClickClose.bind(this);
		this.handleClickShow = this.handleClickShow.bind(this);
		this.handleInputChange = this.handleInputChange.bind(this);
		this.handleSubmitReport = this.handleSubmitReport.bind(this);
	}

	getReason() {
		const {reason, otherReason} = this.state;

		if (reason === OTHER_REASON_VALUE) {
			return otherReason || Liferay.Language.get('no-reason-specified');
		}
		return reason;
	}

	handleClickShow() {
		this.setState({reportDialogOpen: true});
	}

	handleClickClose() {
		this.setState({reportDialogOpen: false});
	}

	handleSubmitReport(event) {
		event.preventDefault();

		const {uri} = this.props;
		const {namespace} = this.context;

		this.setState({isSending: true}, () => {
			const baseData = {
				...this.props.baseData,
				[`${namespace}reason`]: this.getReason()
			};

			const formData = new FormData();

			for (const name in baseData) {
				formData.append(name, baseData[name]);
			}

			fetch(uri, {
				body: formData,
				credentials: 'include',
				method: 'post'
			})
				.then(() => this.setState({isSuccessful: true}))
				.catch(() => this.setState({isFailed: true}));
		});
	}

	handleInputChange(event) {
		const target = event.target;
		const value =
			target.type === 'checkbox' ? target.checked : target.value.trim();
		const name = target.name;

		this.setState({
			[name]: value
		});
	}

	render() {
		const {
			companyName,
			enabled,
			message,
			reasons,
			pathTermsOfUse
		} = this.props;
		const {spritemap} = this.context;

		const {reportDialogOpen, isSuccessful, isSending} = this.state;

		return (
			<div>
				<ClayButton
					disabled={!enabled}
					displayType="secondary"
					onClick={this.handleClickShow}
				>
					<span className="inline-item inline-item-before">
						<ClayIcon spritemap={spritemap} symbol="flag-empty" />
					</span>
					{message}
				</ClayButton>
				{reportDialogOpen && (
					<FlagsModal
						companyName={companyName}
						handleClose={this.handleClickClose}
						handleInputChange={this.handleInputChange}
						handleSubmit={this.handleSubmitReport}
						isSending={isSending}
						isSuccessful={isSuccessful}
						pathTermsOfUse={pathTermsOfUse}
						reason={this.state.reason}
						reasons={reasons}
					/>
				)}
			</div>
		);
	}
}

export default Flags;
