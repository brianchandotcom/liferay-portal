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

import {fetch, objectToFormData} from 'frontend-js-web';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React from 'react';

import {
	OTHER_REASON_VALUE,
	STATUS_ERROR,
	STATUS_LOGIN,
	STATUS_REPORT,
	STATUS_SUCCESS
} from '../constants.es';

import ThemeContext from '../ThemeContext.es';
import FlagsModal from './FlagsModal.es';

class Flags extends React.PureComponent {
	static contextType = ThemeContext;

	static propTypes = {
		baseData: PropTypes.object.isRequired,
		companyName: PropTypes.string.isRequired,
		disabled: PropTypes.bool,
		forceLogin: PropTypes.bool,
		message: PropTypes.string,
		onlyIcon: PropTypes.bool,
		pathTermsOfUse: PropTypes.string.isRequired,
		reasons: PropTypes.object.isRequired,
		signedIn: PropTypes.bool,
		uri: PropTypes.string.isRequired
	};

	static defaultProps = {
		disabled: false,
		forceLogin: false,
		message: Liferay.Language.get('report'),
		onlyIcon: false,
		signedIn: false
	};

	constructor(props) {
		super(props);

		const {forceLogin, reasons} = this.props;

		this.state = {
			isSending: false,
			otherReason: '',
			reportDialogOpen: false,
			reporterEmailAddress: '',
			selectedReason: Object.keys(reasons)[0],
			status: forceLogin ? STATUS_LOGIN : STATUS_REPORT
		};
	}

	getReason() {
		const {selectedReason, otherReason} = this.state;

		if (selectedReason === OTHER_REASON_VALUE) {
			return otherReason || Liferay.Language.get('no-reason-specified');
		}
		return selectedReason;
	}

	handleClickClose = () => {
		this.setState({reportDialogOpen: false});
	};

	handleClickShow = () => {
		this.setState({reportDialogOpen: true});
	};

	handleInputChange = event => {
		const target = event.target;
		const value =
			target.type === 'checkbox' ? target.checked : target.value.trim();
		const name = target.name;

		this.setState({
			[name]: value
		});
	};

	handleSubmitReport = event => {
		event.preventDefault();

		const {baseData, uri, signedIn} = this.props;
		const {reporterEmailAddress} = this.state;
		const {namespace} = this.context;

		this.setState({isSending: true}, () => {
			const formDataObj = {
				...baseData,
				[`${namespace}reason`]: this.getReason()
			};

			if (!signedIn) {
				formDataObj[
					`${namespace}reporterEmailAddress`
				] = reporterEmailAddress;
			}

			fetch(uri, {
				body: objectToFormData(formDataObj),
				method: 'post'
			})
				.then(({status}) => {
					if (status === Liferay.STATUS_CODE.OK) {
						this.setState({status: STATUS_SUCCESS});
					} else {
						this.setState({status: STATUS_ERROR});
					}
				})
				.catch(() => this.setState({status: STATUS_ERROR}));
		});
	};

	render() {
		const {
			companyName,
			disabled,
			message,
			onlyIcon,
			pathTermsOfUse,
			reasons,
			signedIn
		} = this.props;
		const {isSending, reportDialogOpen, status} = this.state;

		return (
			<>
				<ClayButton
					className={`btn-outline-borderless btn-outline-secondary ${
						onlyIcon ? 'lfr-portal-tooltip' : ''
					}`}
					data-title={onlyIcon ? message : undefined}
					disabled={disabled}
					displayType="secondary"
					monospaced={onlyIcon}
					onClick={this.handleClickShow}
					small
				>
					<span
						className={
							!onlyIcon
								? 'inline-item inline-item-before'
								: undefined
						}
					>
						<ClayIcon symbol="flag-empty" />
					</span>
					<span className={onlyIcon ? 'sr-only' : undefined}>
						{message}
					</span>
				</ClayButton>
				{reportDialogOpen && (
					<FlagsModal
						companyName={companyName}
						handleClose={this.handleClickClose}
						handleInputChange={this.handleInputChange}
						handleSubmit={this.handleSubmitReport}
						isSending={isSending}
						pathTermsOfUse={pathTermsOfUse}
						reasons={reasons}
						selectedReason={this.state.selectedReason}
						signedIn={signedIn}
						status={status}
					/>
				)}
			</>
		);
	}
}

export default Flags;
