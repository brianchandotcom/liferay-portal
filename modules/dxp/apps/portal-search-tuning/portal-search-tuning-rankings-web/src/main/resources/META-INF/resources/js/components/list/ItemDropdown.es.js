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
import getCN from 'classnames';
import React, {Component} from 'react';
import {getPluralMessage} from '../../utils/language.es';
import {PropTypes} from 'prop-types';

class ItemDropdown extends Component {
	static propTypes = {
		hidden: PropTypes.bool,
		itemCount: PropTypes.number,
		onClickHide: PropTypes.func,
		onClickPin: PropTypes.func,
		pinned: PropTypes.bool
	};

	static defaultProps = {
		itemCount: 1
	};

	constructor(props) {
		super(props);

		this.setWrapperRef = this.setWrapperRef.bind(this);
	}

	state = {
		show: false
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
			this.setState({show: false});
		}
	};

	_handleDropdownToggle = event => {
		event.preventDefault();

		this.setState(state => ({show: !state.show}));
	};

	_handleDropdownAction = actionFn => event => {
		event.preventDefault();

		actionFn(event);

		this.setState({show: false});
	};

	render() {
		const {
			hidden,
			itemCount,
			onClickHide,
			onClickPin,
			pinned,
			...otherProps
		} = this.props;

		const {show} = this.state;

		const classHidden = getCN(
			'dropdown-menu',
			'dropdown-menu-indicator-start',
			'dropdown-menu-right',
			{
				show
			}
		);

		return (
			<div
				className="dropdown dropdown-action result-dropdown"
				ref={this.setWrapperRef}
				{...otherProps}
			>
				<ClayButton
					aria-expanded="false"
					aria-haspopup="true"
					className="btn-outline-borderless component-action dropdown-toggle"
					data-toggle="dropdown"
					onClick={this._handleDropdownToggle}
					title={Liferay.Language.get('toggle-dropdown')}
				>
					<ClayIcon symbol="ellipsis-v" />
				</ClayButton>

				<ul className={classHidden}>
					{onClickPin && (
						<li>
							<ClayButton
								className="dropdown-item"
								displayType="link"
								onClick={this._handleDropdownAction(onClickPin)}
								small
							>
								<div className="dropdown-item-indicator">
									{pinned ? (
										<ClayIcon key="UNPIN" symbol="unpin" />
									) : (
										<ClayIcon key="PIN" symbol="pin" />
									)}
								</div>

								{pinned
									? getPluralMessage(
											Liferay.Language.get(
												'unpin-result'
											),
											Liferay.Language.get(
												'unpin-results'
											),
											itemCount
									  )
									: getPluralMessage(
											Liferay.Language.get('pin-result'),
											Liferay.Language.get('pin-results'),
											itemCount
									  )}
							</ClayButton>
						</li>
					)}

					{onClickHide && (
						<li>
							<ClayButton
								className="dropdown-item"
								displayType="link"
								onClick={this._handleDropdownAction(
									onClickHide
								)}
								small
							>
								<div className="dropdown-item-indicator">
									<ClayIcon
										symbol={hidden ? 'view' : 'hidden'}
									/>
								</div>

								{hidden
									? getPluralMessage(
											Liferay.Language.get('show-result'),
											Liferay.Language.get(
												'show-results'
											),
											itemCount
									  )
									: getPluralMessage(
											Liferay.Language.get('hide-result'),
											Liferay.Language.get(
												'hide-results'
											),
											itemCount
									  )}
							</ClayButton>
						</li>
					)}
				</ul>
			</div>
		);
	}
}

export default ItemDropdown;
