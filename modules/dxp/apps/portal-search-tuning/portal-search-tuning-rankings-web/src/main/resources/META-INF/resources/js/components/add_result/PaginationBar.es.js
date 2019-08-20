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

import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import Pagination from './Pagination.es';
import React, {Component} from 'react';
import {PropTypes} from 'prop-types';
import {sub} from '../../utils/language.es';

const DELTAS = [5, 10, 20, 30, 50];

class DeltaItem extends React.Component {
	static propTypes = {
		delta: PropTypes.number.isRequired,
		href: PropTypes.string,
		onChange: PropTypes.func
	};

	_handleChange = () => {
		const {delta, onChange} = this.props;

		if (onChange) {
			onChange(delta);
		}
	};

	render() {
		const {href} = this.props;

		return (
			<a
				className="dropdown-item"
				href={href}
				onClick={this._handleChange}
			>
				{this.props.delta}
			</a>
		);
	}
}

class PaginationBar extends Component {
	static defaultProps = {
		deltas: DELTAS,
		selectedDelta: DELTAS[4],
		showDeltaDropdown: false
	};

	static propTypes = {
		deltas: PropTypes.array,
		href: PropTypes.string,
		onDeltaChange: PropTypes.func,
		onPageChange: PropTypes.func,
		page: PropTypes.number,
		selectedDelta: PropTypes.number,
		totalItems: PropTypes.number
	};

	constructor(props) {
		super(props);

		this.setWrapperRef = this.setWrapperRef.bind(this);
	}

	state = {
		showDeltaDropdown: false
	};

	componentDidMount() {
		document.addEventListener('mousedown', this._handleClickOutside);
	}

	componentWillUnmount() {
		document.removeEventListener('mousedown', this._handleClickOutside);
	}

	setWrapperRef(node) {
		this.wrapperRef = node;
	}

	_handleClickOutside = event => {
		if (this.wrapperRef && !this.wrapperRef.contains(event.target)) {
			this.setState({showDeltaDropdown: false});
		}
	};

	_handleDeltaChange = item => {
		this.props.onDeltaChange(item);

		this.setState({showDeltaDropdown: false});
	};

	_handleDropdownToggle = event => {
		event.preventDefault();

		this.setState(state => ({showDeltaDropdown: !state.showDeltaDropdown}));
	};

	render() {
		const {
			deltas,
			href,
			onPageChange,
			page,
			selectedDelta,
			totalItems
		} = this.props;

		const {showDeltaDropdown} = this.state;

		const classDeltaDropdown = getCN('dropdown-menu', 'dropdown-menu-top', {
			show: showDeltaDropdown
		});

		const start = page * selectedDelta;

		return (
			<div className="pagination-bar">
				<div
					className="dropdown pagination-items-per-page"
					data-testid="pagination-delta"
					ref={this.setWrapperRef}
				>
					<a
						aria-expanded="false"
						aria-haspopup="true"
						className="dropdown-toggle"
						data-toggle="dropdown"
						href={href}
						onClick={this._handleDropdownToggle}
					>
						{sub(Liferay.Language.get('x-items'), [selectedDelta])}
						<ClayIcon symbol="caret-double-l" />
					</a>

					<div className={classDeltaDropdown}>
						{deltas.map(item => (
							<DeltaItem
								delta={item}
								key={item}
								onChange={this._handleDeltaChange}
							/>
						))}
					</div>
				</div>

				<div className="pagination-results">
					{sub(Liferay.Language.get('showing-x-to-x-of-x-entries'), [
						start - selectedDelta + 1,
						Math.min(start, totalItems),
						totalItems
					])}
				</div>

				<Pagination
					href={href}
					onChange={onPageChange}
					page={page}
					total={Math.ceil(totalItems / selectedDelta)}
				/>
			</div>
		);
	}
}

export default PaginationBar;
