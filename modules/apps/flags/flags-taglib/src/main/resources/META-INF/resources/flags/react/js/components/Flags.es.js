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
		className: PropTypes.string,
		companyName: PropTypes.string.isRequired,
		enabled: PropTypes.bool,
		formData: PropTypes.object.isRequired,
		message: PropTypes.string,
		pathTermsOfUse: PropTypes.string.isRequired,
		reasons: PropTypes.object.isRequired,
		signedIn: PropTypes.bool.isRequired,
		uri: PropTypes.string.isRequired
	};

	static defaultProps = {
		className: '',
		enabled: true,
		message: Liferay.Language.get('report')
	};

	constructor(props) {
		super(props);

		this.state = {
			isSending: false,
			isSuccessful: false,
			otherReason: '',
			reason: Object.values(props.reasons)[0],
			reportDialogOpen: false
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

	handleClickClose() {
		this.setState({reportDialogOpen: false});
	}

	handleClickShow() {
		this.setState({reportDialogOpen: true});
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

	render() {
		const {
			className,
			companyName,
			enabled,
			message,
			pathTermsOfUse,
			reasons
		} = this.props;

		const {reportDialogOpen, isSuccessful, isSending} = this.state;

		const {spritemap} = this.context;

		return (
			<div className={className}>
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
