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

import React, {Component} from 'react';

import Connector from '../../utilities/data_connectors/Connector.es';
import Datasource from '../../utilities/data_connectors/Datasource.es';
import BaseDatalist from './BaseDatalist.es';

class Datalist extends Component {
	constructor(props) {
		super(props);
		const {connectorSettings, datasourceSettings} = props;

		this.state = {
			connector: this.initializeConnector(connectorSettings),
			data: null,
			datasource: this.initializeDatasource(datasourceSettings),
			selected: null,
		};
	}

	initializeDatasource(datasourceSettings) {
		const {on, ...settings} = datasourceSettings;

		return new Datasource({
			...settings,
			on: {
				read: (data) => {
					const formattedData = data.map((el) => ({
						label:
							el[
								this.props.datasourceSettings.labelField ||
									'label'
							],
						value:
							el[
								this.props.datasourceSettings.valueField ||
									'value'
							],
					}));
					this.setState({
						data: formattedData,
					});
				},
				...(on || null),
			},
		});
	}

	initializeConnector(connectorSettings) {
		const {on, ...settings} = connectorSettings;
		const {notified, ...actions} = on || {};

		return new Connector({
			...settings,
			on: {
				getValue: () =>
					this.state.selected
						? this.state.selected.map((selected) => selected.value)
						: null,
				notified: (values) => {
					if (notified) {
						notified(
							values,
							this.setState.bind(this),
							this.state.datasource
						);
					}
				},
				...(actions || null),
			},
		});
	}

	emit(eventName, payload) {
		switch (eventName) {
			case 'selectedValuesChanged':
				this.setState(
					{
						selected: payload.length ? payload : null,
					},
					() => {
						this.state.connector.notify();
						if (this.props.onchange) {
							this.props.onchange(payload);
						}
					}
				);
				break;
			case 'queryUpdated':
				this.state.datasource.setFilter('keyword', payload, 'contains');
				this.state.datasource.read();
				break;
			default:
				break;
		}
	}

	getDisabledState() {
		let disabled;
		switch (true) {
			case typeof this.state.disabled === 'boolean':
				disabled = this.state.disabled;
				break;
			case typeof this.props.disabled === 'boolean':
				disabled = this.props.disabled;
				break;
			default:
				disabled = false;
				break;
		}

		return disabled;
	}

	render() {
		const disabledState = this.getDisabledState();

		const {...baseProps} = this.props;

		return (
			<BaseDatalist
				data={this.state.data || this.props.data || null}
				disabled={disabledState}
				emit={(e, payload) => this.emit(e, payload)}
				value={this.state.selected}
				{...baseProps}
			/>
		);
	}
}

export default Datalist;
