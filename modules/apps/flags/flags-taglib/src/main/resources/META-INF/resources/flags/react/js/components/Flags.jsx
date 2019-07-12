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
import ClayButton from "@clayui/button";
import ClayIcon from "@clayui/icon";

import FlagsModal from "./FlagsModal";

const delay = ms => new Promise(resolve => setTimeout(resolve, ms));

class Flags extends React.Component {
	static propTypes = {
		className: PropTypes.string,
		dataJSONObject: PropTypes.object,
		enabled: PropTypes.bool
	};

	constructor(props) {
 		super(props);

		this.state = {
			reportDialogOpen: false,
			isSending: false,
			isSuccessful: false
		};

		this.handleClickClose = this.handleClickClose.bind(this);
		this.handleClickShow = this.handleClickShow.bind(this);
		this.handleInputChange = this.handleInputChange.bind(this);
		this.handleSubmitReport = this.handleSubmitReport.bind(this);
	}

	handleClickShow() {
		this.setState({ reportDialogOpen: true });
	}

	handleClickClose() {
		this.setState({ reportDialogOpen: false });
	}

	handleSubmitReport(event) {
		event.preventDefault();

		this.setState({ isSending: true }, () => {
			delay(2000).then(() => this.setState({ isSuccessful: true }));
		});
	}

	handleInputChange(event) {
		const target = event.target;
		const value = target.type === "checkbox" ? target.checked : target.value;
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
			urlTermsOfUse,
			spritemap
		} = this.props;

    	const { reportDialogOpen, isSuccessful, isSending } = this.state;

		return (
			<div>
				<ClayButton
					displayType="secondary"
					disabled={!enabled}
					onClick={this.handleClickShow}
				>
					<span className="inline-item inline-item-before">
						<ClayIcon spritemap={spritemap} symbol="flag-empty" />
					</span>
					{message}
				</ClayButton>

				{reportDialogOpen && (
					<FlagsModal
						companyName = {companyName}
						handleClose = {this.handleClickClose}
						handleInputChange = {this.handleInputChange}
						handleSubmit = {this.handleSubmitReport}
						isSending = {isSending}
						isSuccessful = {isSuccessful}
						reasons = {reasons}
						spritemap = {spritemap}
						urlTermsOfUse = {urlTermsOfUse}
					/>
				)}
			</div>
		);
	}
}

export default Flags;